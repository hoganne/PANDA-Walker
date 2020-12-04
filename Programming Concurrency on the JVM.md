# Programming Concurrency on the JVM

速度。除了咖啡因之外，没有什么能像快速地执行一段代码那样加快程序员的工作了。我们如何满足对计算速度的需求？摩尔“定律把我们的一些方式，但多是真正的未来。要充分利用多核，我们需要在编程时考虑并发性。

在并发程序中，两个或多个动作同时发生。并发程序可以在执行计算和更新数据库的同时下载多个文件。我们经常使用Java中的线程编写并发程序。多线程Java虚拟机（JVM）从一开始就存在了，但我们的程序并发怎么还在不断发展，为我们'会在这本书中学习。

困难的部分是在不被烧毁的情况下收获并发的好处。启动线程很容易，但是它们的执行顺序是不确定的。我们“重新很快被拉入战来协调线程，并确保他们”再持续地处理数据。

为了快速从A点到达B点，我们基于行进时间的紧迫性，交通状况，预算等等，有几种选择。我们可以步行，乘公交车，驾驶那辆笨拙的敞篷车，乘坐子弹头火车或乘坐喷气式飞机。在速度编写Java，我们'以前也有选择。

JVM上有三种主要的并发选项：

•我所说的“同步和痛苦”模型

•软件事务存储模型

•基于参与者的并发模型

What I call the “synchronize and suffer” model

• The Software-Transactional Memory model

• The actor-based concurrency model

我将熟悉的Java开发工具包（JDK）同步模型称为“同步并受苦”，因为如果我们忘记同步共享的可变状态或以错误的级别对其进行同步，则结果是不可预测的。如果我们'重新幸运，我们赶上发展过程中的问题; 如果我们想念，它可以在生产过程中以奇怪和不幸的方式出现。我们不会得到编译错误，没有警告，并且不会遇到那些命运多code的代码。

在制作过程中以奇怪和不幸的方式出现。我们没有得到任何编译错误，没有任何警告，简单地说，没有任何迹象表明这个倒霉的代码有问题。无法同步访问共享可变状态的程序被破坏了，但是Java编译器不会告诉我们这一点。在纯Java中编写具有可变性的程序就像与等待您失败的婆婆一起工作。我相信你已经感受到了痛苦。

在编写并行程序时，有三种方法可以避免问题:

•正确同步。

•不要共享状态。

•不要发生变异状态。

如果我们用现代的JDK并发API，我们'将不得不投入显著努力正确地同步。STM隐含了同步，并大大减少了出错的机会。另一方面，基于参与者的模型可以帮助我们避免共享状态。避免易变的状态是赢得并发战斗的秘密武器。

在这本书中，我们'会举一个例子驱动的方法来学习的三种模式，以及如何与他们利用并发。

### Is it Concurrency or Parallelism?是并发还是并行

有“在行业中的这两个词之间没有明显的区别，而我们答案的数目” LL听到的是接近我们要求一个解释的人的数量（和Don '牛逼问他们同时......或者我应该并行说？）。

让“不在这里讨论的区别。我们可能会在具有多个线程的单个内核上运行程序，然后将它们部署在具有多个线程的多个内核上。当我们的代码在单个JVM中运行时，这两个部署选项都存在一些共同的问题-我们如何创建和管理线程，如何确保数据的完整性，如何处理锁和同步以及线程是否跨越内存？在适当的时间设置障碍...？

无论是并行还是并行，解决这些问题都是确保程序正确有效运行的核心。这“就是我们”会专注于这本书。



### 多语言程序员的并发

如今，*Java*一词代表的是平台而不是语言。多年来，Java虚拟机以及无处不在的库已演变成一个非常强大的平台。同时，Java语言正在显示其时代。今天，有相当一些有趣而强大的语言在JVM上-的Clojure，JRuby的，Groovy和Scala，仅举几例。

这些现代JVM语言中的一些语言（例如Clojure，JRuby和Groovy）是动态键入的。某些功能（例如Clojure和Scala）受编程功能样式的影响很大。然而他们所有人都有一件事在常见的-他们'再简洁，极富表现力。虽然它可能需要一些努力来适应自己的语法，范式，或差异，我们'会大多需要在所有这些语言与Java编程相比，更少的代码。什么“就更好了，我们可以混合使用这些语言与Java代码，并真正成为一个通晓多国语言的程序员-看尼尔福特”小号“多语种程序员”[在](part160.htm#bookmark221)[附录2，](part160.htm#bookmark221)[Web资源](part160.htm#bookmark221)，255页。

在这本书中，我们'将学习如何使用的java.util.concurrent API中，STM，并使用Akka 和GPars的基于角色的模型（the actor-based model）。我们“也将学习如何用Clojure，Java中的JRuby，Groovy和Scala亲克并发。如果您编程或打算使用这些语言中的任何一种，本书将向您介绍其中的并发编程选项。

### 示例和性能衡量

本书中的大多数示例都使用Java。但是，您还将在Clojure，Groovy，JRuby和Scala中看到很多示例。我'已经采取额外的努力来保持语法的细微差别和特定语言的成语到最低限度。哪里有选择，我“已经俯身向着东西，”这样比较方便阅读和熟悉的程序员大多与Java舒服。

以下是本书中使用的语言和库的版本：

• [Akka 1.1.3（](http://akka.io/downloads)http://akka.io/downloads ）

• [Clojure 1.2.1（](http://clojure.org/downloads)http://clojure.org/downloads ）

• [Groovy 1.8（](http://groovy.codehaus.org/Download)http://groovy.codehaus.org/Download ）

• [GPars 0.12（](http://gpars.codehaus.org/)http://gpars.codehaus.org ）

• [Java SE 1.6（](http://www.java.com/en/download)http://www.java.com/en/download ）

• [JRuby 1.6.2（](http://jruby.org/download)http://jruby.org/download ）

• [Scala 2.9.0.1（](http://www.scala-lang.org/downloads)http://www.scala-lang.org/downloads ）

当显示的代码两个版本之间性能的措施，我'已经确定这些比较是在同一台机器上进行。对于大多数的例子我'已经使用与2.8GHz的英特尔双核处理器和4GB内存运行Mac OS X 10.6.6和Java 1.6版更新中的MacBook Pro

24.对于某些示例，我还使用具有8GB内存的八核Sunfire 2.33GHz处理器，运行64位Windows XP和Java 1.6版。

除非另有说明，否则所有示例均使用“ Java HotSpot TM 64位服务器VM ” Java虚拟机在服务器模式下运行。

所有示例均已编译并在前面提到的Mac和Windows计算机上运行。

在的代码示例上市，我还'吨所示的导入语句（和封装的语句），因为这些往往变得冗长。当试图代码示例，如果你“不能确定类属，不要这包”别担心，我'[已经包含代码的网站上的完整列表。继续并从其网站（](http://pragprog.com/titles/vspcon)）[下载本书的完整源代码](http://pragprog.com/titles/vspcon)。



### 1.1线程：执行流程 Threads: The Flow of Execution

众所周知，线程是流程中的执行流程。当我们运行一个程序时，其过程至少有一个执行线程。我们可以创建线程来启动其他执行流程，以便同时执行其他任务。我们使用的库或框架可能还会根据需要在后台启动其他线程。

当多个线程作为单个应用程序或JVM的一部分运行时，我们将同时运行多个任务或操作。并发应用程序利用多个线程或并发执行流。

在单个处理器上，这些并发任务通常是多路复用或多任务的。也就是说，处理器在每个执行流的上下文之间快速切换。但是，在任何给定实例上只执行一个线程，因此只有一个执行流。在多核处理器上，在任何给定的实例上都要执行多个执行流(线程)。这个数字取决于处理器上可用的内核数量，而应用程序的并发线程数量则取决于与其进程相关联的内核数量。

### 1.2并发的威力

 我们对并发感兴趣有两个原因:使应用程序响应/改善用户体验和使其更快。

 让应用程序响应更快**Making Apps More Responsive**

当我们开始一个应用程序,主线程的执行通常需要在多个责任顺序,取决于我们的行动让它执行:接收来自用户的输入,读取一个文件,执行一些它——一代长大成人,访问一个web服务,更新数据库,显示一个响应给用户,等等。如果每个操作只花费几分之一秒的时间，那么就没有必要引入额外的执行流;一个线程就足够满足需要了。

然而，在大多数重要的应用程序中，这些操作可能没有那么快。计算可能需要几秒钟到几分钟的时间。对该web服务的数据请求可能会遇到网络延迟，因此线程会等待响应的到达。当发生这种情况时，应用程序的用户无法与应用程序交互或中断应用程序，因为单个线程被扣留在某个要完成的操作上。

让我们考虑一个示例，该示例说明了需要多个线程以及它如何影响响应性。我们经常为事件计时，所以最好有秒表应用程序。我们可以点击一个按钮开始手表，它将运行，直到我们再次点击按钮。下面显示了为此编写的1位代码(只显示按钮的动作处理程序;你可以从这本书的网站下载完整的程序):

在示例中，我们将简单地传播异常，而不是记录或处理它们——但请确保在您的生产代码中正确地处理异常。

```java
//This will not work

public void actionPerformed(final ActionEvent event) {

if (running) 
    stopCounting(); 
    else startCounting();

}

private void startCounting() { 
    startStopButton.setText("Stop");
    running = true;

for(int count = 0; running; count++) { timeLabel.setText(String.format("%d", count));
try {

Thread.sleep(1000);

} catch(InterruptedException ex) {

throw new RuntimeException(ex);

}

}

}

private void stopCounting() { 
    running = false; 
    startStopButton.setText("Start");

}


```

当我们运行这个小小的秒表应用程序时，将出现一个带有开始按钮和0标签的窗口。不幸的是，当我们单击按钮时，我们不会看到任何更改按钮没有更改为停止，并且标签没有显示时间计数。更糟糕的是，应用程序甚至不会响应退出请求。主事件分派线程负责注意与ui相关的事件并委托要执行的操作。当单击开始按钮时，主事件调度线程进入事件处理程序actionPerformed();在开始计数时，它被startCounting()方法扣留。现在，当我们单击按钮或尝试退出时，这些事件会被拖放到事件队列中，但主线程太忙，永远不会响应这些事件。

我们需要一个额外的线程，或者一个计时器，反过来使用额外的线程，以使应用程序能够响应。我们需要委托计数任务，并减轻主要事件分派线程的责任。

线程不仅可以帮助应用程序响应，还可以帮助增强用户体验。应用程序可以预先查看用户接下来可能执行的操作，并执行必要的操作，例如索引或缓存用户需要的某些数据。

**Making Apps Faster**

看看你写的一些应用程序。您是否看到当前按顺序依次执行的操作可以并发执行?通过在不同的线程中运行这些操作，可以使应用程序运行得更快。

通过使用并发，相当多的应用程序可以运行得更快。其中包括服务、计算密集型应用程序和数据处理应用程序。

**Services**

假设我们的任务是构建一个需要处理来自不同供应商的大量发票的应用程序。这要求我们对每个发票应用规则和业务工作流，但是我们可以按照任何顺序处理它们。按顺序处理这些发票不会产生吞吐量，也不会很好地利用这些资源。我们的应用程序需要同时处理这些发票。

**Computationally Intensive Apps**  计算密集型应用

我曾经在化工行业工作过，在那里我编写了一些应用程序，用来计算流经炼油厂不同单元的化学物质的各种属性。这涉及到密集的计算，这些计算很容易从将问题划分为几个部分、并发地运行计算并最终合并部分结果中获益。各种各样的问题都适合采用分而治之的方法，它们很容易从我们编写并发程序的能力中获益。

**Data Crunchers** 计算数据

我曾经被要求构建一个个人财务应用程序，该应用程序必须通过web服务来获取大量股票的价格和其他细节。应用程序必须向用户提供总资产价值和每只股票交易量的详细信息。对于富有的用户，应用程序可以跟踪100种不同股票的股票。在流量大的时候，可能需要几秒钟才能从网络上接收到信息。在接收到所有数据并开始处理之前，用户需要等待几分钟。通过将请求委托给多个线程，等待时间可以缩短到一两秒，假设每个请求的网络延迟为一两秒，并且运行应用程序的系统有足够的资源和能力来产生数百个线程。

### 获得并发的好处

并发性可以帮助使应用程序具有响应性，减少延迟，并增加吞吐量。我们可以利用硬件的多核和应用程序中任务的一致性来提高速度和响应能力。然而,有一些困难的挑战,我们将讨论下,我们必须解决之前我们可以获得这些好处。‌

### 1.3 The Perils of Concurrency

Right now, you’re probably thinking “I can get better throughput by breaking up my problem and letting multiple threads work on these parts.” Unfortu- nately, problems rarely can be divided into isolated parts that can be run totally independent of each other. Often, we can perform some operations independently but then have to merge the partial results to get the final re- sult. This requires threads to communicate the partial results and sometimes wait for those results to be ready. This requires coordination between threads and can lead to synchronization and locking woes.

We encounter three problems when developing concurrent programs: star- vation, deadlock, and race conditions. The first two are somewhat easier to detect and even avoid. The last one, however, is a real nemesis that should be eliminated at the root.

现在，您可能在想，通过分解问题并让多个线程处理这些部分，我可以获得更好的吞吐量。不幸的是，问题很少能被划分为可以完全独立运行的独立部分。通常，我们可以独立地执行一些操作，但必须合并部分结果以得到最终结果。这需要线程通信部分结果，有时还要等待这些结果就绪。这需要线程之间的协调，并可能导致同步和锁定问题。

在开发并发程序时，我们会遇到三个问题:星化star- vation、死锁和竞态条件。前两种比较容易检测甚至避免。然而，最后一个问题是一个真正的宿敌，应该从根本上加以消除。

### Starvation and Deadlocks 饥饿和死锁

Running into thread starvation is unfortunately quite easy. For example, an application that is about to perform a critical task may prompt the user for confirmation just as the user steps out to lunch. While the user enjoys a good meal, the application has entered a phase of starvation. Starvation occurs when a thread waits for an event that may take a very long time or forever to happen. It can happen when a thread waits for input from a user, for some external event to occur, or for another thread to release a lock. The thread will stay alive while it waits, doing nothing. We can prevent starvation by placing a timeout. Design the solution in such a way that the thread waits for only a finite amount of time. If the input does not arrive, the event does not happen, or the thread does not gain the lock within that time, then the thread bails out and takes an alternate action to make progress.

不幸的是，线程饥饿很容易发生。例如，一个即将执行关键任务的应用程序可能会在用户出去吃午饭时提示用户进行确认。当用户享用一顿美餐时，应用程序已经进入了饥饿的阶段。当线程等待可能需要很长时间或永远发生的事件时，就会发生饥饿。当线程等待用户的输入、等待某个外部事件发生或等待另一个线程释放锁时，就会发生这种情况。线程在等待时保持活动状态，不做任何事情。我们可以通过设置超时来防止饥饿。在设计解决方案时，线程只等待有限的时间。如果输入没有到达，事件不会发生，或者线程在此时间内没有获得锁，那么线程将退出并采取另一个操作来继续执行。

We run into deadlock if two or more threads are waiting on each other for some action or resource. Placing a timeout, unfortunately, will not help avoid the deadlock. It’s possible that each thread will give up its resources, only to repeat its steps, which leads again into a deadlock—see “The Dining Philosophers Problem” [in ](part160.htm#bookmark221)[Appendix 2, ](part160.htm#bookmark221)[Web Resources](part160.htm#bookmark221), on page 255. Tools

如果两个或多个线程互相等待某个操作或资源，我们就会陷入死锁。不幸的是，设置超时将无助于避免死锁。每个线程可能会放弃它的资源，只是为了重复它的步骤，这将再次导致死锁——参见255页的“吃饭的哲学家问题”。工具

such as JConsole can help detect deadlocks, and we can prevent deadlock by acquiring resources in a specific order. A better alternative, in the first place, would be to avoid explicit locks and the mutable state that goes with them. We’ll see how to do that later in the book.

比如JConsole可以帮助检测死锁，我们可以通过以`特定的顺序获取资源`来防止死锁。首先，一个更好的替代方案是避免显式锁及其伴随的可变状态。我们会在书的后面看到如何做到这一点。

**Race Conditions** 竞态条件

如果两个线程竞争使用相同的资源或数据，我们就有一个竞争条件。竞争条件不仅仅发生在两个线程修改数据时。甚至当一方在修改数据而另一方试图读取数据时，也可能发生这种情况。竞争条件可以使程序的行为不可预测，产生不正确的执行，并产生不正确的结果。

有两种因素会导致竞态条件:即时(JIT)编译器(Just-in-Time (JIT) compiler)op- timization和Java内存模型(Java Memory Model)。关于Java内存模型及其如何影响并发的特殊论述，请参考Brian Goetz的重要著作《实践中的Java并发性》[Goe06]。让我们看一个相当简单的例子来说明这个问题。在下面的代码中，主线程创建一个线程，休眠两秒钟，并将done标志设置为true。同时，创建的线程在标志上循环，只要它是假的。让我们编译并运行代码，看看会发生什么

```java
public class RaceCondition {

private static boolean done;



public static void main(final String[] args) throws InterruptedException{

new Thread(

new Runnable() {

public void run() {

int i = 0;

while(!done) { i++; }

System.out.println("Done!");

}

}

).start();



System.out.println("OS: " + System.getProperty("os.name")); Thread.sleep(2000);

done = true;

System.out.println("flag done set to true");

}

}
```



等等，不要把书放下然后在推特上写“Windows好棒，Mac烂透了!”“这个问题比前两次所揭示的要更深一些。

让我们再试一次——这一次在Windows上，使用命令java -server RaceCondition(要求它在Windows上以服务器模式运行)运行程序，在Mac上，使用命令java -d32 RaceCondition(要求它在Mac上以客户端模式运行)运行程序。

在Windows上，我们会看到这样的东西:

默认情况下，Java在32位Windows上以客户端模式运行，在Mac上以服务器模式运行。我们的程序在两个平台上的行为是一致的，程序在客户端模式下终止，而不在服务器模式下终止。在服务器模式下运行时，即使主线程将其值设置为true，第二个线程也不会看到对标志的更改已经完成。这是因为Java服务器JIT编译器的优化。但是，让我们不要责怪JIT编译器，它是一个强大的工具，努力优化代码以使其运行得更快。我们从前面的例子中学到的是，坏了的程序可能在某些情况下工作，在其他情况下失败。

### 了解你的可见性:了解内存障碍**Know Your Visibility: Understand the Memory Barrier**

上一个示例的问题是，主线程对已完成字段的更改可能对我们创建的线程不可见。首先，JIT编译器可能会优化while循环;毕竟，它并没有看到变量在线程的上下文中改变。而且，第二个线程可能最终会从寄存器或缓存中读取标志的值，而不是进入内存。因此，它可能永远看不到第一个线程对这个标志所做的更改看到内存障碍是什么吗?，在第9页。我们可以通过将done标记为volatile来快速解决这个问题。我们可以改变这个

前一个示例的问题在于，主线程对完成的字段所做的更改可能对我们创建的线程不可见。首先，JIT编译器可以优化while循环；毕竟，它看不到变量在线程上下文中完成更改。此外，第二线程可能最终会从其寄存器或缓存中读取标志的值，而不是去内存。结果，它可能永远不会看到第一个线程对该标志所做的更改-[请参阅](part26.htm#bookmark19)[“此内存屏障是什么？” ](part26.htm#bookmark19)[，](part26.htm#bookmark19)第9页。

我们可以通过将标志标记为已完成来快速解决问题。我们可以更改此：

```java
private static boolean done;
```

to the following:

```java
private static volatile boolean done;
```

该volatile关键字告诉JIT编译器不执行，可能会影响访问该变量的排序任何优化。它警告该变量可能在线程后面更改，并且对该变量的每次访问（读或写）都应绕过高速缓存，并一直到达内存。我将此称为快速修复，因为任意地使所有变量变为volatile可能会避免问题，但会导致非常糟糕的性能，因为每次访问都必须跨越内存障碍，这将导致性能很差。同样，当访问多个字段时，volatile对原子性也无济于事，因为对每个volatile字段的访问是分开处理的，并且不协调为一个访问-这将使线程有很大的机会看到某些字段的部分更改，而不是其他字段。

我们还可以通过防止直接访问标志并通过同步的getter和setter引导所有访问来避免此问题，如下所示：

```java
private static boolean done;

public static synchronized boolean getFlag() { return done; }

public static synchronized void setFlag(boolean flag) { done = flag; }
```

synchronized标记在这里很有帮助，因为它是使调用线程在进入和退出同步块时都跨越内存障碍的原语之一。如果两个线程在同一个实例上同步，并且修改线程先于另一个线程执行，那么这个线程就保证能够看到另一个线程所做的修改;再一次，看看这个记忆障碍是什么?，在第9页。

What’s This Memory Barrier?

简而言之，它是从本地或工作内存复制到主内存。

只有当写线程跨越内存障碍，然后读线程跨越内存障碍时，一个线程所做的更改才能保证对另一个线程可见。同步的和易变的关键字强制更改在全局上及时可见;这些帮助跨越记忆障碍——不管是有意还是无意。

更改首先在本地寄存器和缓存中进行，然后在复制到主存时跨越内存障碍。这些交叉的顺序称为happens-before -参见“Java内存模型”，附录2,Web资源，255页，请参阅Brian Goetz的Java并发实践[Goe06]。

写必须在读之前发生，这意味着写线程必须在读线程之前跨越内存障碍，这样才能看到更改。

并发API中有相当多的操作隐式地跨越了内存障碍:volatile、synchronized、线程上的方法(如start()和interrupt())、ExecutorService上的方法，以及一些同步促进器(如CountDownLatch)。

a.请参阅Doug Lea的文章“JSR-133编译器作者烹饪书”，见Ap- pendix 2, Web资源，255页。

### 避免共享可变性  Avoid Shared Mutability

不幸的是，忘记这样做的后果——在需要的时候使用volatile或synchronized——是非常不可预测的。真正的担心不是我们会忘记同步。核心问题是，我们面对的是共同的易变性。

我们非常习惯使用可变性编程Java应用程序——通过更改对象的字段来创建和修改对象的状态。然而，像Joshua Bloch的有效Java [Blo08]这样的好书建议我们提倡不可变性。不变性可以帮助我们从根本上避免这个问题

突变本身并不完全是坏事，尽管它的使用方式经常会带来麻烦。分享是一件好事;毕竟，妈妈总是告诉我们要分享。虽然这两种东西本身是好的，但混合在一起就不好了。

When we have a nonfinal (mutable) field, each time a thread changes the value, we have to consider whether we have to put the change back to the memory or leave it in the registers/cache. Each time we read the field, we need to be concerned if we read the latest valid value or a stale value left behind in the cache. We need to ensure the changes to variables are atomic; that is, threads don’t see partial changes. Furthermore, we need to worry about protecting multiple threads from changing the data at the same time.

当我们有一个非最终的(可变的)字段时，每当线程更改值时，我们必须考虑是否必须将更改放回内存或留在寄存器/缓存中。每次读取字段时，我们都需要关注是读取最新的有效值还是缓存中遗留的陈旧值。我们需要确保对变量的更改是原子的;也就是说，线程不会看到部分更改。此外，我们还需要考虑如何保护多个线程同时更改数据。

For an application that deals with mutability, every single access to shared mutable state must be verified to be correct. Even if one of them is broken, the entire application is broken. This is a tall order—for our concurrent app to fall apart, only a single line of code that deals with concurrency needs to take a wrong step. In fact, a significant number of concurrent Java apps are broken, and we simply don’t know it.

对于处理可变性的应用程序，必须验证对共享可变状态的每次访问都是正确的。即使其中一个崩溃了，整个应用程序也会崩溃。这是一个艰巨的任务——要使我们的并发应用程序崩溃，只有一行处理并发性的代码需要采取错误的步骤。事实上，大量并发Java应用程序已经崩溃，而我们只是不知道而已。

Now if we have a final (immutable) field referring to an immutable instance3 and we let multiple threads access it, such sharing has no hidden problems. Any thread can read it and upon first access get a copy of the value that it can keep locally in its cache. Since the value is immutable, subsequent ac- cess to the value from the local cache is quite adequate, and we can even enjoy good performance.

现在，如果我们有一个final(不可变)字段引用一个不可变的instance3，并且我们让多个线程访问它，这样的共享没有隐藏的问题。任何线程都可以读取该值，并在第一次访问时获得该值的副本，该副本可以保存在本地缓存中。由于该值是不可变的，因此从本地缓存中调用该值已经足够了，我们甚至可以享受良好的性能。

Shared mutability is pure evil. Avoid it!共同突变是纯粹的邪恶。避免它!

所以，如果我们不能改变任何东西，我们如何让应用程序做任何事情?这是一个合理的担忧，但是我们需要围绕共享的不变性来设计应用程序。一种方法是保持可变状态良好的封装和只共享不可变的数据。纯函数式语言提倡的另一种方法是，让所有东西都不可变，但使用函数组合。在这种方法中，我们应用了一系列转换，其中我们从一个不可变状态转换到另一个不可变状态。还有另一种方法，那就是使用一个库来监视更改并警告我们任何违规。在本书中，我们将通过使用并发解决的问题示例来了解这些技术。

### 1.4 Recap

Whether we’re writing an interactive client-side desktop application or a high-performance service for the back end, concurrency will play a vital role in programming efforts should we reap the benefits of the evolving hardware trends and multicore processors. It’s a way to influence the user experience, responsiveness, and speed of applications that run on these powerful ma- chines. The traditional programming model of concurrency on the JVM—dealing with shared mutability—is froth with problems. Besides cre- ating threads, we have to work hard to prevent starvation, deadlocks, and race conditions—things that are hard to trace and easy to get wrong. By avoiding shared mutability, we remove the problems at the root. Lean toward shared immutability to make programming concurrency easy, safe, and fun; we’ll learn how to realize that later in this book.

无论我们是编写交互式的客户端桌面应用程序，还是为后端编写高性能的服务，如果我们从不断发展的硬件趋势和多核处理器中获益，并发性将在编程工作中发挥至关重要的作用。这是一种影响用户体验、响应能力和运行在这些强大的ma- chines上的应用程序速度的方式。JVM上处理共享可变性的传统并发编程模型存在很多问题。除了控制线程之外，我们还必须努力防止饥饿、死锁和竞态条件等难以跟踪且容易出错的情况。通过避免共享的易变性，我们从根本上解决了问题。倾向于共享的不变性，以使并发编程简单、安全、有趣;我们将在本书后面学习如何实现这一点。

Next, we’ll discuss ways to determine the number of threads and to partition applications.

接下来，我们将讨论确定线程数量和对应用程序进行分区的方法。

For example, instances of String, Integer, and Long are immutable in Java, while instances of StringBuilder and ArrayList are mutable

例如，String、Integer和Long的实例在Java中是不可变的，而StringBuilder和ArrayList的实例是可变的

### Strategies for Concurrency

分工Division of Labor

期待已久的多核处理器明天就会到来，你迫不及待地想看看你正在构建的应用程序是如何在上面运行的。您已经在单核上运行了几次，但是您渴望看到新机器上的加速。加速是否与内核的数量成比例?更多?少吗?少了很多?我曾经经历过这样的事情，也感受过为了达到一个合理的期望而付出的努力。您应该看看我第一次在多核上运行代码时的表情，结果比我预期的要糟糕得多。多内核如何导致速度变慢?那是许多年前的事了，从那以后我变得更聪明了，也学到了一些教训。现在我有更好的直觉和方法来衡量加速，我想在这一章与你分享。

### 2.1 From Sequential to Concurrent 从顺序到并发

我们不能指望在多核处理器上运行单线程应用程序就能得到更好的结果。我们必须分割它，同时运行多个任务。但是，程序不以同样的方式划分，并从相同数量的线程中获益。

我从事过计算密集型的科学应用程序，也从事过IO密集型的业务应用程序，因为它们涉及文件、数据库和web服务调用。这两种类型的应用程序的性质不同，使它们并发的方法也不同。

在本章中，我们将使用两种类型的应用程序。第一个是一个io密集型应用程序，它将为富有的用户计算资产净值。第二个程序将计算一定范围内质数的总数——这是并发计算密集型程序的一个非常简单但非常有用的示例。这两个应用程序将帮助我们了解要创建多少线程、如何划分问题以及预期的加速速度。

**Divide and Conquer**

如果我们有数百个股票要处理，一次取一个将是最简单的方法…失去工作。当应用程序依次处理每个股票时，用户会站在那里生气。为了加速我们的程序，我们需要将问题划分为并发运行的任务。这包括创建这些部分或任务，并将它们委托给线程，以便它们可以并发运行。对于一个大问题，我们可以创建任意多的部分，但我们不能创建太多的线程，因为我们的资源有限。

Determining the Number of Threads

对于大问题，我们希望线程的数量至少与可用内核的数量相等。这将确保进程可用的内核数量尽可能多，从而解决我们的问题。我们可以很容易地找到可用内核的数量;我们所需要的只是一个简单的代码调用:1

```java
Runtime.getRuntime().availableProcessors();
```

因此，线程的最小数量等于可用内核的数量。如果所有的任务都是计算密集型的，那么这就是我们所需要的。在这种情况下，拥有更多的线程实际上是有害的，因为内核会在线程之间进行上下文切换，而此时仍有工作要做。如果任务是IO密集型的，那么我们应该有更多的线程。

当一个任务执行一个IO操作时，它的线程被阻塞。处理器立即切换上下文以运行其他符合条件的线程。如果我们只有与可用内核数量相同的线程，即使我们有任务要执行，它们也无法运行，因为我们没有将它们安排在线程上，让处理器来处理。

如果任务有50%的时间被阻塞，那么线程的数量应该是可用内核数量的两倍。如果它们被阻塞的时间更少——也就是说，它们的计算强度更高——那么我们应该有更少的线程，但不应该少于内核的数量。如果它们被阻塞的时间更多——也就是说，它们的IO强度更高——那么我们应该有更多的线程，具体来说，是内核数量的几倍。

So, we can compute the total number of threads we’d need as follows:

因此，我们可以计算我们需要的线程总数如下:

 availableProcessors() reports the number of logical processors available to the JVM.

availableProcessors()报告JVM可用的逻辑处理器的数量。

Number of threads = Number of Available Cores / (1 - Blocking Coefficient)

```java
线程数=可用内核数/(1 -阻塞系数)
```

where the blocking coefficient is between 0 and 1.

其中阻塞系数在0到1之间。

A computation-intensive task has a blocking coefficient of 0, whereas an IO-intensive task has a value close to 1—a fully blocked task is doomed, so we don’t have to worry about the value reaching 1.

计算密集型任务的阻塞系数为0，而io密集型任务的阻塞系数接近于1——完全阻塞的任务注定要失败，因此我们不必担心值会达到1。

To determine the number of threads, we need to know two things:

为了确定线程的数量，我们需要知道两件事:

• The number of available cores 可用内核的数量

• The blocking coefficient of tasks 任务的阻塞系数

第一个很容易确定;我们可以查找这些信息，即使是在运行时，就像我们前面看到的那样。

它需要一些努力来确定阻塞系数。我们可以尝试猜测，或者我们可以使用分析工具 or the `java.lang.management API `确定线程在系统/IO操作和cpu密集型任务上花费的时间。

Determining the Number of Parts 确定部分的数量

我们知道如何计算并发应用程序的线程数。现在我们必须决定如何分割这个问题。每个部分都将并发运行，因此，首先考虑的是，我们可以拥有与线程数量相同的部分。这是一个好的开始，但还不够;我们忽略了正在解决的问题的性质。

在净资产价值应用程序中，为每只股票获取价格的努力是相同的。因此，将股票的总数量按照线程数量划分为相同的组应该足够了。

然而，在质数应用程序中，确定一个数是否是质数的努力对所有数来说并不相同。偶数很快就会失败，较大的质数比较小的质数花费更多的时间。使用数字的范围并将它们分割成与线程数量相同的组，这并不能帮助我们获得良好的性能。有些任务会比其他任务更快地完成，并且对核心的利用也很差。

换句话说，我们希望这些部分有均匀的功分布。我们可以花费大量的时间和精力来划分问题，以便各部分能够公平地分配负载。然而，会有两个问题。首先，这很困难;这将花费大量的精力和时间。其次，将问题划分为相等部分并将其分布到各个线程的代码会很复杂。

事实证明，让核心在这个问题上保持忙碌，比在各个部件之间平均分配负载更有益。从流程的角度来看，当有工作需要完成时，我们需要确保没有空闲的可用核心。因此，我们可以通过创建比线程数量多得多的部件来实现这一点，而不是将负载分散到各个部件上。设置足够大的部件数量，以便所有可用的内核都能在程序上执行足够的工作。

### 2.2 Concurrency in IO-Intensive Apps io密集型应用程序中的并发性

An IO-intensive application has a large blocking coefficient and will benefit from more threads than the number of available cores.

一个io密集型应用程序具有较大的阻塞系数，并且可以从比可用内核数量更多的线程中获益。

Let’s build the financial application I mentioned earlier. The (rich) users of the application want to determine the total net asset value of their shares at any given time. Let’s work with one user who has shares in forty stocks. We are given the ticker symbols and the number of shares for each stock. From the Web, we need to fetch the price of each share for each symbol. Let’s take a look at writing the code for calculating the net asset value.

让我们构建我前面提到的金融应用程序。应用程序的(富)用户希望在任何给定时间确定其股份的总资产净值。让我们以一个拥有40支股票的用户为例。我们得到了股票代码和每只股票的股份数。从网络上，我们需要为每个符号获取每一股的价格。让我们看一下如何编写计算净资产价值的代码。

*Sequential Computation of Net Asset Value资产净值的顺序计算*

作为业务的第一单，我们需要股票代码的价格。感谢雅虎提供了我们需要的历史数据。这里是代码与雅虎的金融网络服务，以获得最后交易价格的股票代码(前一天):

```java
public class YahooFinance {

public static double getPrice(final String ticker) throws IOException {

final URL url =new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticker);

final BufferedReader reader = new BufferedReader( new InputStreamReader(url.openStream()));

//Date,Open,High,Low,Close,Volume,Adj Close

//2011-03-17,336.83,339.61,330.66,334.64,23519400,334.64

final String discardHeader = reader.readLine();

final String data = reader.readLine(); final String[] dataItems = data.split(","); final double priceIsTheLastValue =
Double.valueOf(dataItems[dataItems.length - 1]);
return priceIsTheLastValue;

}

}
```

我们向http://ichart.finance.yahoo.com发送请求并解析结果以获得价格。

接下来，我们将获得用户拥有的每只股票的价格，并显示总资产净值。此外，我们还将显示完成此操作所需的时间。

```java
public abstract class AbstractNAV {

public static Map<String, Integer> readTickers() throws IOException {

final BufferedReader reader =

new BufferedReader(new FileReader("stocks.txt"));

final Map<String, Integer> stocks = new HashMap<String, Integer>(); String stockInfo = null;

while((stockInfo = reader.readLine()) != null) {

final String[] stockInfoData = stockInfo.split(",");

final String stockTicker = stockInfoData[0];

final Integer quantity = Integer.valueOf(stockInfoData[1]);



stocks.put(stockTicker, quantity);

}



return stocks;

}



public void timeAndComputeValue()

throws ExecutionException, InterruptedException, IOException {

final long start = System.nanoTime();



final Map<String, Integer> stocks = readTickers();

final double nav = computeNetAssetValue(stocks);



final long end = System.nanoTime();



final String value = new DecimalFormat("$##,##0.00").format(nav); System.out.println("Your net asset value is " + value); System.out.println("Time (seconds) taken " + (end - start)/1.0e9);

}



public abstract double computeNetAssetValue(

final Map<String, Integer> stocks)

throws ExecutionException, InterruptedException, IOException;

}
```

AbstractNAV的readTickers()方法从名为stocks的文件中读取股票代码和每个代码拥有的股票数量.txt，下面显示部分txt:

AAPL,2505 AMGN,3406 AMZN,9354 BAC,9839 BMY,5099

...

timeAndComputeValue()乘以对抽象方法computeNetAsset- Value()的调用，该方法将在派生类中实现。然后，它打印总资产净值和计算它所花费的时间。

最后，我们需要联系Yahoo Finance，计算总资产净值。让我们按顺序来做:

```java
public class SequentialNAV extends AbstractNAV {

public double computeNetAssetValue(

final Map<String, Integer> stocks) throws IOException {

double netAssetValue = 0.0;

for(String ticker : stocks.keySet()) {

netAssetValue += stocks.get(ticker) * YahooFinance.getPrice(ticker);

}

return netAssetValue;

}



public static void main(final String[] args)

throws ExecutionException, IOException, InterruptedException {

new SequentialNAV().timeAndComputeValue();

}

}
```

让我们运行SequentialNAV代码并观察输出:

Your net asset value is $13,661,010.17 Time (seconds) taken 19.776223

好消息是，我们设法帮助我们的用户获得总资产价值。然而，我们的用户不是很高兴。不满的部分原因可能是市场状况，但真正的原因主要是等待;在我的计算机上，由于运行时的网络延迟，用了将近20秒，才得到40个股票的结果。我确信使这个应用程序并发将有助于加速和拥有一个愉快的用户。

*Determining Number of Threads and Parts for Net Asset Value 为资产净值确定线程和部件的数量*

应用程序只需执行很少的计算，并且将大部分时间用于等待来自Web的响应。对于用户来说，没有理由让超过几秒钟的延迟感觉像永恒。

wait for one response to arrive before sending the next request. So, this application is a good candidate for concurrency: we’ll likely get a good bump in speed.

在发送下一个请求之前，等待一个响应到达。因此，这个应用程序是并发性的一个很好的候选者:我们可能会在速度上有一个很好的提升。

在样本运行中，我们有40支股票，但实际上我们可能有更多的股票，甚至是数百支。我们必须首先决定分区的数量和要使用的线程的数量。Web服务(在本例中为Yahoo Finance)非常能够接收和处理并发请求。

因此，我们的客户端设置了线程数量的实际限制。由于web服务请求将花费大量时间等待响应，阻塞系数相当高，因此我们可以通过几个核心数量的因素来增加线程数量。

Let’s say the blocking coefficient is 0.9—[each task blocks 90 percent of the time and works only 10 percent of its lifetime. Then on two cores, we can have (using the formula from ](part29.htm#bookmark41)[Determining the Number of Threads](part29.htm#bookmark41), on page 16) twenty threads. On an eight-core processor, we can go up to eighty threads, assuming we have a lot of ticker symbols.

假设阻塞系数是0.9 -[每个任务阻塞90%的时间，工作时间只有10%。然后在两个核心上，我们可以(使用公式)[确定线程的数量](part29.htm#bookmark41)，在第16页)有20个线程。在一个8核的处理器上，我们可以达到80个线程，假设我们有很多代码符号。

至于部门的数量，每个库存的工作量基本上是相同的。因此，我们可以简单地拥有和库存一样多的部件，并根据线程的数量对它们进行调度。

让我们让应用程序并发，然后研究线程和分区对代码的影响。

 **Concurrent Computation of Net Asset Value**资产净值并行计算

现在有两个挑战。首先，我们必须跨线程调度部件。其次，我们必须收到来自各个部分的部分结果，以计算总资产价值。

对于这个问题，我们可能有和库存数量一样多的部门。我们需要维护一个线程池来调度这些分区。与其创建和管理单独的线程，不如使用线程池——它们具有更好的生命周期和资源管理，减少启动和拆卸成本，并且能够快速启动计划任务。

作为Java程序员，我们已经习惯了线程化和同步化，但是自从Java 5的到来之后，我们有了一些替代方法——看[有什么理由吗]

为了防止拒绝服务攻击(和追加销售高级服务)，web服务可能会限制来自同一客户端的并发请求的数量。当并发请求超过50个时，您可能会注意到这一点。

#### Joe asks:

Is There a Reason to Use the Old Threading API in Java?在Java中使用旧的线程API有什么原因吗?

旧的线程API有几个缺陷。我们将使用并丢弃线程类的情况，因为它们不允许重启。为了处理多个任务，我们通常会创建多个线程，而不是重用它们。如果我们决定在一个线程上调度多个任务，我们必须编写相当多的额外代码来管理它。两种方式都没有效率和可扩展性。

像wait()和notify()这样的方法需要同步，并且在线程之间进行通信时很难正确操作。join()方法使我们关注的是线程的死亡，而不是正在完成的任务。

此外，synchronized关键字缺乏粒度。如果我们没有获得锁，它不会给我们一个超时的方法。它也不允许并发多个读取器。此外，如果我们使用synchronized，就很难对线程安全性进行单元测试。

util中的新一代并发api。由Doug Lea领导的并发包已经很好地取代了旧的线程API。

• Wherever we use the Thread class and its methods, we can now rely upon the ExecutorService class and related classes.

现在，在使用Thread类及其方法的任何地方，都可以依赖ExecutorService类和相关类。

• If we need better control over acquiring locks, we can rely upon the Lock interface and its methods.

如果我们需要更好地控制获取锁，我们可以依赖于锁接口及其方法。

• Wherever we use wait/notify, we can now use synchronizers such as CyclicBarrier and  CountdownLatch.

在我们使用wait/notify的地方，我们现在可以使用同步器，如CyclicBarrier和CountdownLatch。

[Use the Old Threading API in Java?](part33.htm#bookmark44), on page 22. The new-generation Java concurrency API in java.util.concurrent is far superior.

在现代并发API中，executor类作为工厂来创建不同类型的线程池，我们可以使用ExecutorService接口来管理这些线程池。其中一些风格包括单线程池，它在一个线程中一个接一个地运行所有计划的任务。

固定的线程池允许我们配置池的大小，并在一个可用的线程中并发地运行我们抛出的任务。如果任务多于线程，则任务将排队等待执行，并且只要有线程可用，每个排队的任务就会运行。缓存的线程池将根据需要创建线程，并在可能的情况下重用现有的线程。如果线程上没有计划活动超过一分钟，它将开始关闭不活动的线程。

固定线程池非常适合我们在资产净值应用程序中需要的线程池。我们根据内核的数量和预积的阻塞系数来确定线程池的大小。此池中的线程将执行属于每个部分的任务。在样本运行中，我们有40支股票;如果我们创建20个线程(对于一个双核处理器)，那么其中一半的部分将立即被调度。另一半则在线程可用时进入队列并运行。这对我们来说不需要什么努力;让我们编写代码并发地获得该股票价格。