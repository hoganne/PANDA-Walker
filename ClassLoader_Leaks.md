# ClassLoader_Leaks

![Image Blog RJC201 Classloaders Leaks](https://www.jrebel.com/sites/rebel/files/image/2019-11/image-blog-rjc201-classloader-leaks.jpg)

### How Do ClassLoader Leaks Happen?

JAVA APPLICATION DEVELOPMENT

By [Jevgeni Kabanov](https://www.jrebel.com/blog/how-do-classloader-leaks-happen#authorjevgenikabanov)

In this article, the second of our Reloading Java Classes series, we will be looking at classloader leaks – why they happen, how to find them and how to troubleshoot them.

## From ClassLoaders to Classes

If you have programmed in Java for some time you know that memory leaks do happen. Usually it's the case of a collection somewhere with references to objects (e.g. listeners) that should have been cleared, but never were. Classloaders are a very special case of this, and unfortunately, with the current state of the Java platform, these leaks are both inevitable and costly: routinely causing `OutOfMemoryError`’s in production applications after just a few redeploys.

Let’s get started. Recalling [RJC101](https://www.jrebel.com/blog/how-to-use-java-class-loaders): to reload a class we threw away the old classloader and created a new one, copying the object graph as best we could:

![image blog jrebel how do classloader leaks happen](https://www.jrebel.com/sites/rebel/files/image/2020-05/reloading-object%20%281%29.png)

Every object had a reference to its class, which in turn had a reference to its classloader. However we didn’t mention that every classloader in turn has a reference to each of the classes it has loaded, each of which holds static fields defined in the class:

![image blog jrebel how do classloader leaks happen 2](https://www.jrebel.com/sites/rebel/files/image/2020-05/classloader-refs_0.png)

This means that

1. If a classloader is leaked it will hold on to all its classes and all their static fields. Static fields commonly hold caches, singleton objects, and various configuration and application states. Even if your application doesn’t have any large static caches, it doesn’t mean that the framework you use doesn’t hold them for you (e.g. Log4J is a common culprit as it's often put in the server classpath). This explains why leaking a classloader can be so expensive.
2. To leak a classloader it’s enough to leave a reference to any object, created from a class, loaded by that classloader. Even if that object seems completely harmless (e.g. doesn’t have a single field), it will still hold on to its classloader and all the application state. A single place in the application that survives the redeploy and doesn’t do a proper cleanup is enough to sprout the leak. In a typical application there will be several such places, some of them almost impossible to fix due to the way third-party libraries are built. Leaking a classloader is therefore, quite common.

To examine this from a different perspective let’s return to the code example from our [previous article](https://www.jrebel.com/blog/how-to-use-java-class-loaders). Breeze through it to quickly catch up.

### Introducing the Leak

We will use the exact same Main class as before to show what a simple leak could look like:

```java
public class Main {
  private static IExample example1;
  private static IExample example2;

  public static void main(String[] args)  {
    example1 = ExampleFactory.newInstance().copy();

    while (true) {
      example2 = ExampleFactory.newInstance().copy();

      System.out.println("1) " +
        example1.message() + " = " + example1.plusPlus());
      System.out.println("2) " +
        example2.message() + " = " + example2.plusPlus());
      System.out.println();

      Thread.currentThread().sleep(3000);
    }
  }
}
```


The `ExampleFactory` class is also exactly the same, but here’s where things get leaky. Let’s introduce a new class called `Leak` and a corresponding interface `ILeak`:

```java
interface ILeak {
}

public class Leak implements ILeak {
  private ILeak leak;

  public Leak(ILeak leak) {
    this.leak = leak;
  }
}
```


As you can see it's not a terribly complicated class: it just forms a chain of objects, with each doing nothing more than holding a reference to the previous one. We will modify the `Example` class to include a reference to the `Leak` object and throw in a large array to take up memory (it represents a large cache). Let's omit some methods shown in the previous article for brevity:

```java
public class Example implements IExample {
  private int counter;
  private ILeak leak;

  private static final long[] cache = new long[1000000];

  /* message(), counter(), plusPlus() impls */

  public ILeak leak() {
    return new Leak(leak);
  }

  public IExample copy(IExample example) {
    if (example != null) {
      counter = example.counter();
      leak = example.leak();
    }
    return this;
  }
}
```


The important things to note about `Example` class are:

1. `Example` holds a reference to `Leak`, but `Leak` has no references to `Example`.
2. When `Example` is copied (method `copy()` is called) a new `Leak` object is created holding a reference to the previous one.

If you try to run this code an OutOfMemoryError will be thrown after just a few iterations:

```text
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at example.Example.(Example.java:8)
```


With the right tools, we can look deeper and see how this happens.

### Post Mortem

Since Java 5.0, we’ve been able to use the `jmap` command line tool included in the JDK distribution to dump the heap of a running application (or for that matter even extract the Java heap from a core dump). However, since our application is crashing we will need a feature that was introduced in Java 6.0: dumping the heap on OutOfMemoryError. To do that we only need to add `-XX:+HeapDumpOnOutOfMemoryError` to the JVM command line:

```text
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid37266.hprof ...
Heap dump file created [57715044 bytes in 1.707 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at example.Example.(Example.java:8)
```


After we have the heap dump we can analyze it. There are a number of tools (including [jhat](http://java.sun.com/javase/6/docs/technotes/tools/share/jhat.html), a small web-based analyzer included with the JDK), but here we will use the more sophisticated [Eclipse Memory Analyzer](http://www.eclipse.org/mat/) (EMA).

After loading the heap dump into the EMA we can look at the *Dominator Tree* analysis. It is a very useful analysis that will usually reliably identify the biggest memory consumers in the heap and what objects hold a reference to them. In our case it seems quite obvious that the `Leak` class is the one that consumes most of the heap:

![image blog jrebel how do classloader leaks happen 3](https://www.jrebel.com/sites/rebel/files/image/2020-05/classloader-dominator-tree1-min.png)

Now let's run a search for all of the Leak objects and see what are they holding to. To do that we run a search **List objects -> with outgoing references** for "example.Leak":

![image blog jrebel how do classloader leaks happen 4](https://www.jrebel.com/sites/rebel/files/image/2020-05/ema-search.png)

The results include several `Leak` objects. Expanding the outgoing references we can see that each of them holds on to a separate instance of `Example` through a bunch of intermediate objects:

![image blog jrebel how do classloader leaks happen 5](https://www.jrebel.com/sites/rebel/files/image/2020-05/ema-analysis-min.png)

You may notice that one of the intermediate objects is `ExampleFactory$1`, which refers to the anonymous subclass of `URLClassLoader` we created in the `ExampleFactory` class. In fact what is happening is exactly the situation we described in the beginning of the article:

- Each `Leak` object is leaking. They are holding on to their classloaders
- The classloaders are holding onto the `Example` class they have loaded:

![image blog jrebel how do classloader leaks happen 6](https://www.jrebel.com/sites/rebel/files/image/2020-05/classloader-leak.png)

## Final Thoughts

Though this example is slightly contrived, the main idea to take away is that it’s easy to leak a single object in Java. Each leak has the potential to leak the whole classloader if the application is redeployed or otherwise a new classloader is created. Since preventing such leaks is very challenging, it's a better idea to use Eclipse Memory Analyzer and your understanding of classloaders to hunt them down after you get an `OutOfMemoryError` on redeploy. 

This article addressed the following questions:

本文讨论了以下问题:

- How does reloading a class cause the classloader to leak?

  重新加载类如何导致类加载器泄漏?

- What are some consequences of leaking classloaders?

  类装入器泄漏的一些后果是什么?

- What tools can be used to troubleshoot these memory leaks?

  可以使用哪些工具对这些内存泄漏进行故障排除?

### Additional Resources

Looking for more background on class loading? This video gives a good overview, and there are additional resources below that dive in on the details.

想了解更多关于类加载的背景知识吗?本视频提供了一个很好的概述，下面有更多的资源可以深入了解细节。

- [RJC101: Objects, Classes and ClassLoaders](https://www.jrebel.com/blog/how-to-use-java-class-loaders)
- [RJC201: How do Classloader leaks happen?](https://www.jrebel.com/blog/how-do-classloader-leaks-happen)
- [RJC301: Classloaders in Web Development -- Tomcat, GlassFish, OSGi, Tapestry 5 and so on](https://www.jrebel.com/blog/using-classloaders-in-web-development)
- [RJC401: HotSwap and JRebel — Behind the Scenes](https://www.jrebel.com/blog/java-hotswap-guide)
- [RJC501: How Much Does Turnaround Cost?](https://www.jrebel.com/blog/how-much-does-turnaround-cost)

Want to skip rebuilds and redeploys altogether? Use JRebel! It skips these time-consuming steps while maintaining application state.

想要跳过重建和重新部署?使用JRebel !它在维护应用程序状态时跳过了这些耗时的步骤。