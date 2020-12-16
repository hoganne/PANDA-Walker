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

现在，如果我们有一个final(不可变)字段引用一个不可变的instance，并且我们让多个线程访问它，这样的共享没有隐藏的问题。任何线程都可以读取该值，并在第一次访问时获得该值的副本，该副本可以保存在本地缓存中。由于该值是不可变的，因此从本地缓存中调用该值已经足够了，我们甚至可以享受良好的性能。

##### Shared mutability is pure evil. Avoid it!共享突变是纯粹的邪恶。避免它!

所以，如果我们不能改变任何东西，我们如何让应用程序做任何事情?这是一个合理的担忧，但是我们需要围绕共享的不变性来设计应用程序。一种方法是保持可变状态良好的封装和只共享不可变的数据。纯函数式语言提倡的另一种方法是，让所有东西都不可变，但使用函数组合。在这种方法中，我们应用了一系列转换，其中我们从一个不可变状态转换到另一个不可变状态。还有另一种方法，那就是使用一个库来监视更改并警告我们任何违规。在本书中，我们将通过使用并发解决的问题示例来了解这些技术。

### 1.4 Recap

Whether we’re writing an interactive client-side desktop application or a high-performance service for the back end, concurrency will play a vital role in programming efforts should we reap the benefits of the evolving hardware trends and multicore processors. It’s a way to influence the user experience, responsiveness, and speed of applications that run on these powerful ma- chines. The traditional programming model of concurrency on the JVM—dealing with shared mutability—is froth with problems. Besides cre- ating threads, we have to work hard to prevent starvation, deadlocks, and race conditions—things that are hard to trace and easy to get wrong. By avoiding shared mutability, we remove the problems at the root. Lean toward shared immutability to make programming concurrency easy, safe, and fun; we’ll learn how to realize that later in this book.

无论我们是编写交互式的客户端桌面应用程序，还是为后端编写高性能的服务，如果我们从不断发展的硬件趋势和多核处理器中获益，并发性将在编程工作中发挥至关重要的作用。这是一种影响用户体验、响应能力和运行在这些强大的ma- chines上的应用程序速度的方式。JVM上处理共享可变性的传统并发编程模型存在很多问题。除了控制线程之外，我们还必须努力防止饥饿、死锁和竞态条件等难以跟踪且容易出错的情况。通过避免共享的易变性，我们从根本上解决了问题。倾向于共享的不变性，以使并发编程简单、安全、有趣;我们将在本书后面学习如何实现这一点。

Next, we’ll discuss ways to determine the number of threads and to partition applications.

接下来，我们将讨论确定线程数量和对应用程序进行分区的方法。

For example, instances of String, Integer, and Long are immutable in Java, while instances of StringBuilder and ArrayList are mutable

例如，String、Integer和Long的实例在Java中是不可变的，而StringBuilder和ArrayList的实例是可变的

### Strategies for Concurrency并发策略

##### 分工Division of Labor

期待已久的多核处理器明天就会到来，你迫不及待地想看看你正在构建的应用程序是如何在上面运行的。您已经在单核上运行了几次，但是您渴望看到新机器上的加速。加速是否与内核的数量成比例?更多?少吗?少了很多?我曾经经历过这样的事情，也感受过为了达到一个合理的期望而付出的努力。您应该看看我第一次在多核上运行代码时的表情，结果比我预期的要糟糕得多。多内核如何导致速度变慢?那是许多年前的事了，从那以后我变得更聪明了，也学到了一些教训。现在我有更好的直觉和方法来衡量加速，我想在这一章与你分享。

###### 2.1 From Sequential to Concurrent 从顺序到并发

我们不能指望在多核处理器上运行单线程应用程序就能得到更好的结果。我们必须分割它，同时运行多个任务。但是，程序不以同样的方式划分，并从相同数量的线程中获益。

我从事过计算密集型的科学应用程序，也从事过IO密集型的业务应用程序，因为它们涉及文件、数据库和web服务调用。这两种类型的应用程序的性质不同，使它们并发的方法也不同。

在本章中，我们将使用两种类型的应用程序。第一个是一个io密集型应用程序，它将为富有的用户计算资产净值。第二个程序将计算一定范围内质数的总数——这是并发计算密集型程序的一个非常简单但非常有用的示例。这两个应用程序将帮助我们了解要创建多少线程、如何划分问题以及预期的加速速度。

###### **Divide and Conquer**分治法

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

### Determining the Number of Parts 确定部分（分隔任务）的数量

我们知道如何计算并发应用程序的线程数。现在我们必须决定如何分割这个问题。每个部分都将并发运行，因此，首先考虑的是，我们可以拥有与线程数量相同的部分。这是一个好的开始，但还不够;我们忽略了正在解决的问题的性质。

在净资产价值应用程序中，为每只股票获取价格的努力是相同的。因此，将股票的总数量按照线程数量划分为相同的组应该足够了。

然而，在质数应用程序中，确定一个数是否是质数的努力对所有数来说并不相同。偶数很快就会失败，较大的质数比较小的质数花费更多的时间。使用数字的范围并将它们分割成与线程数量相同的组，这并不能帮助我们获得良好的性能。有些任务会比其他任务更快地完成，并且对核心的利用也很差。

换句话说，我们希望这些部分有均匀的功分布。我们可以花费大量的时间和精力来划分问题，以便各部分能够公平地分配负载。然而，会有两个问题。首先，这很困难;这将花费大量的精力和时间。其次，将问题划分为相等部分并将其分布到各个线程的代码会很复杂。

事实证明，让核心在这个问题上保持忙碌，比在各个部件之间平均分配负载更有益。从流程的角度来看，当有工作需要完成时，我们需要确保没有空闲的可用核心。因此，我们可以通过创建比线程数量多得多的部件来实现这一点，而不是将负载分散到各个部件上。设置足够大的部件数量，以便所有可用的内核都能在程序上执行足够的工作。

### 2.2 Concurrency in IO-Intensive Apps  io密集型应用程序中的并发性

一个io密集型应用程序具有较大的阻塞系数，并且可以从比可用内核数量更多的线程中获益。

让我们构建我前面提到的金融应用程序。应用程序的(富)用户希望在任何给定时间确定其股份的总资产净值。让我们以一个拥有40支股票的用户为例。我们得到了股票代码和每只股票的股份数。从网络上，我们需要为每个符号获取每一股的价格。让我们看一下如何编写计算净资产价值的代码。

##### *Sequential Computation of Net Asset Value资产净值的顺序计算*

作为业务的第一单，我们需要股票代码的价格。感谢雅虎提供了我们需要的历史数据。这里是代码与雅虎的金融网络服务，以获得最后交易价格的股票代码(前一天):

```java
public class YahooFinance {

public static double getPrice(final String ticker) throws IOException {

final URL url =new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticker);

final BufferedReader reader = new BufferedReader( new InputStreamReader(url.openStream()));

//Date,Open,High,Low,Close,Volume,Adj Close
    
//2011-03-17,336.83,339.61,330.66,334.64,23519400,334.64

final String discardHeader = reader.readLine();

final String data = reader.readLine(); 
final String[] dataItems = data.split(","); final double priceIsTheLastValue =
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

AAPL,2505

 AMGN,3406 

AMZN,9354 

BAC,9839 

BMY,5099

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

###### *Determining Number of Threads and Parts for Net Asset Value 为资产净值确定线程和部件的数量*

应用程序只需执行很少的计算，并且将大部分时间用于等待来自Web的响应。对于用户来说，没有理由让超过几秒钟的延迟感觉像永恒。

在发送下一个请求之前，等待一个响应到达。因此，这个应用程序是并发性的一个很好的候选者:我们可能会在速度上有一个很好的提升。

在样本运行中，我们有40支股票，但实际上我们可能有更多的股票，甚至是数百支。我们必须首先决定分区的数量和要使用的线程的数量。Web服务(在本例中为Yahoo Finance)非常能够接收和处理并发请求。

因此，我们的客户端设置了线程数量的实际限制。由于web服务请求将花费大量时间等待响应，阻塞系数相当高，因此我们可以通过几个核心数量的因素来增加线程数量。

假设阻塞系数是0.9 -[每个任务阻塞90%的时间，工作时间只有10%。然后在两个核心上，我们可以(使用公式)[确定线程的数量](part29.htm#bookmark41)，在第16页)有20个线程。在一个8核的处理器上，我们可以达到80个线程，假设我们有很多代码符号。

至于部门的数量，每个库存的工作量基本上是相同的。因此，我们可以简单地拥有和库存一样多的部件，并根据线程的数量对它们进行调度。

让我们让应用程序并发，然后研究线程和分区对代码的影响。

#####  **Concurrent Computation of Net Asset Value**资产净值并行计算

现在有两个挑战。首先，我们必须跨线程调度部件。其次，我们必须收到来自各个部分的部分结果，以计算总资产价值。

对于这个问题，我们可能有和库存数量一样多的部门。我们需要维护一个线程池来调度这些分区。与其创建和管理单独的线程，不如使用线程池——它们具有更好的生命周期和资源管理，减少启动和拆卸成本，并且能够快速启动计划任务。

作为Java程序员，我们已经习惯了线程化和同步化，但是自从Java 5的到来之后，我们有了一些替代方法——看[有什么理由吗]

为了防止拒绝服务攻击(和追加销售高级服务)，web服务可能会限制来自同一客户端的并发请求的数量。当并发请求超过50个时，您可能会注意到这一点。

#### Joe asks:

Is There a Reason to Use the Old Threading API in Java?在Java中使用旧的线程API有什么原因吗?

旧的线程API有几个缺陷。我们将使用并丢弃线程类的情况，因为它们不允许重启。为了处理多个任务，我们通常会创建多个线程，而不是重用它们。如果我们决定在一个线程上调度多个任务，我们必须编写相当多的额外代码来管理它。两种方式都没有效率和可扩展性。

像wait()和notify()这样的方法需要同步，并且在线程之间进行通信时很难正确操作。join()方法使我们关注的是线程的死亡，而不是正在完成的任务。

此外，synchronized关键字缺乏粒度。如果我们没有获得锁，它不会给我们一个超时的方法。它也不允许并发多个读取器。此外，如果我们使用synchronized，就很难对线程安全性进行单元测试。

util并发api中的新一代i。由Doug Lea领导的并发包已经很好地取代了旧的线程API。

• Wherever we use the Thread class and its methods, we can now rely upon the ExecutorService class and related classes.

现在，在使用Thread类及其方法的任何地方，都可以依赖ExecutorService类和相关类。

• If we need better control over acquiring locks, we can rely upon the Lock interface and its methods.

如果我们需要更好地控制获取锁，我们可以依赖于lock接口及其方法。

• Wherever we use wait/notify, we can now use synchronizers such as CyclicBarrier and  CountdownLatch.

在我们使用wait/notify的地方，我们现在可以使用同步器，如CyclicBarrier和CountdownLatch。

[Use the Old Threading API in Java?](part33.htm#bookmark44), on page 22. The new-generation Java concurrency API in java.util.concurrent is far superior.

在现代并发API中，executor类作为工厂来创建不同类型的线程池，我们可以使用ExecutorService接口来管理这些线程池。其中一些风格包括单线程池，它在一个线程中一个接一个地运行所有计划的任务。

固定的线程池允许我们配置池的大小，并在一个可用的线程中并发地运行我们抛出的任务。如果任务多于线程，则任务将排队等待执行，并且只要有线程可用，每个排队的任务就会运行。

缓存的线程池将根据需要创建线程，并在可能的情况下重用现有的线程。如果线程上没有计划活动超过一分钟，它将开始关闭不活动的线程。

固定线程池非常适合我们在资产净值应用程序中需要的线程池。我们根据内核的数量和预积的阻塞系数来确定线程池的大小。此池中的线程将执行属于每个部分的任务。在样本运行中，我们有40支股票;如果我们创建20个线程(对于一个双核处理器)，那么其中一半的部分将立即被调度。另一半则在线程可用时进入队列并运行。这对我们来说不需要什么努力;让我们编写代码并发地获得该股票价格。

在computeNetAssetValue()方法中，我们根据假定的阻塞系数和核的数量来确定线程池的大小(Runtime 的availableProcessor()方法给出了该细节)。

We then place each part—to fetch the price for each ticker symbol—into the anonymous code block of the Callable interface

该接口提供了一个call()方法，该方法返回该接口的参数化类型的值(在示例中为Double)

然后我们使用invokeAll()方法在固定大小的池上调度这些部分。执行程序负责同时运行尽可能多的部分。如果分割的数量大于池的大小，它们就会排队等待执行。由于各部分是并发和异步运行的，调度主线程无法立即得到结果。invokeAll()方法在所有计划的任务完成后返回一个Future对象的集合。我们请求这些对象的部分结果，并将它们添加到资产净值中。让我们看看并发版本是如何执行的

```tsx
Number of Cores available is 2
Pool size is 20
Your net asset value is $13,661,010.17
Time (seconds) taken 0.967484
```

与顺序运行相比，并发运行只花了不到一秒的时间。

我们可以通过改变假定的阻塞系数来改变池中的线程数，并查看速度是否会发生变化。我们还可以尝试不同数量的股票，看看顺序版本和并发版本之间的结果和速度变化。

##### Isolated Mutability 孤立的可变性

在这个问题中，executor服务几乎消除了任何同步问题，它允许我们很好地委托任务并从一个协调线程接收它们的结果。前面代码中唯一的可变变量是`netAssetValue`，它是我们在第25行定义的。我们唯一改变这个变量的地方是在第27行。这种突变只发生在一个线程，主线程中，所以我们这里只有隔离的可变而没有共享的可变。由于没有共享状态，因此在此示例中没有需要同步的内容。在Future的帮助下，我们能够安全地将获取数据的线程的结果发送到主线程。

这个例子中的方法有一个限制。我们在第26行循环中遍历Fut ure对象。因此，我们请求结果一次一个部分。基本上是按照我们创建/安排部门的顺序。即使后面的部分首先完成，我们也不会处理它的结果，直到我们处理之前部分的结果。在这个特殊的例子中，这可能不是问题。但是，如果我们在接收响应时需要执行大量的计算，那么我们宁愿在结果可用时处理它们，而不是等待所有任务完成。

我们可以使用JDK `CompletionService`来完成这个任务。稍后我们将重新讨论这个问题，并查看一些替代解决方案。让我们换档分析一下加速过程。

##### 2.3 Speedup for the IO-Intensive App  io密集型App加速

io密集型应用程序的特性允许在内核较少的情况下实现更大程度的并发。当一个IO操作被阻塞时，我们可以切换到执行其他任务或请求启动其他IO操作。我们估计，在双核机器上，大约20个线程对于股票总资产价值应用程序来说是合理的。让我们分析从1个线程到40个线程的不同数量的双核处理器上的性能。因为分区的总数是40个，所以创建更多的线程没有任何意义。在图1中，我们可以观察到随着线程数量的增加而加速，在第26页，随着池大小的增加而加速。大约二十根线左右，曲线开始变平

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_108.jpg)

池中大约有二十根线，曲线开始变平。这告诉我们，我们的估计是相当不错的，超出我们的估计的线程更多是没有帮助的。

这个应用程序是并发的完美候选者——各部分之间的工作负载几乎相同，而且由于来自Web的数据请求延迟而导致的大阻塞，非常有利于利用线程。通过增加线程的数量，我们可以获得更大的速度提升。然而，并不是所有的问题都能通过这种方式加速，我们将在下面看到。

##### 2.4 Concurrency in Computationally Intensive Apps  计算密集型应用中的并发性

内核数量对计算密集型应用程序的加速的影响要大于对io绑定应用程序的影响，我们将在本节中看到这一点。我们将使用的例子非常简单;然而，它有一个隐藏的惊喜——不平衡的工作负载将影响加速。

让我们写一个程序来计算1到1000万之间的质数。我们先按顺序求解，然后并行求解。

Sequential Computation of Prime Numbers 质数的顺序计算

让我们从抽象类AbstractPrimeFinder开始，它将帮助对当前问题的一些常用方法进行分组。isPrime()方法将判断给定的数字是否为素数，countPrimesInRange()方法使用它来计算一个数字范围内的素数数量。最后，timeAndCompute()方法将记录计算质数所花费的时间:

Let’s exercise the code with 10000000 (10 million) as an argument and observe
the time taken for this sequential run.

```tx
Number of primes under 10000000 is 664579
Time (seconds) taken is 6.544368
```


 在我的双核处理器上，它采用顺序代码，在服务器模式下运行，用6秒多一点的时间数出1000万以下质数的数量。如果我们在客户端模式下运行，它将花费更长的时间。让我们看看我们可以通过在并发模式下运行来实现加速

##### Concurrent Computation of Prime Numbers 

由于这是一个计算密集型任务，因此在这个问题上抛出大量线程不会有帮助。阻塞系数为0，因此从第16页确定线程数量的公式中得出的建议线程数量等于核的数量。拥有更多的线程不会帮助加速;相反，它可能会让事情慢下来。这是因为它没有意义的非阻塞任务暂停为另一个线程运行我们自己的问题。当所有的内核都很忙时，我们不妨先完成任务，然后再开始下一个任务。让我们先写并发代码，给它两个线程和两个部分，看看会发生什么。我们现在已经回避了part数量的问题;取得一些进展后，我们会重新讨论它。

质数计数的并发版本在结构上类似于净资产值应用程序的并发版本。但是，这里我们不计算线程和部件的数量，而是将这两个参数作为素数计数的候选编号。下面是并行质数计数的代码:

```java
public class ConcurrentPrimeFinder extends AbstractPrimeFinder {
 private final int poolSize;
 private final int numberOfParts;

 public ConcurrentPrimeFinder(final int thePoolSize,
 final int theNumberOfParts) {
 poolSize = thePoolSize;
 numberOfParts = theNumberOfParts;
 }
 public int countPrimes(final int number) {
 int count = 0;
 try {
 final List<Callable<Integer>> partitions =
 new ArrayList<Callable<Integer>>();
 final int chunksPerPartition = number / numberOfParts;
 for(int i = 0; i < numberOfParts; i++) {
 final int lower = (i * chunksPerPartition) + 1;
 final int upper =
 (i == numberOfParts - 1) ? number : lower + chunksPerPartition - 1;
 partitions.add(new Callable<Integer>() {
 public Integer call() {
 return countPrimesInRange(lower, upper);
 }
 });
 }
 final ExecutorService executorPool =
 Executors.newFixedThreadPool(poolSize);
 final List<Future<Integer>> resultFromParts =
 executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);
 executorPool.shutdown();
 for(final Future<Integer> result : resultFromParts)
 count += result.get();
 } catch(Exception ex) { throw new RuntimeException(ex); }
 return count;
 }
 public static void main(final String[] args) {
 if (args.length < 3)
 System.out.println("Usage: number poolsize numberOfParts");
 else
 new ConcurrentPrimeFinder(
 Integer.parseInt(args[1]), Integer.parseInt(args[2])).timeAndCompute(Integer.parseInt(args[0]));
 }
 }
```

我们将范围从1到给定的数字划分为请求的部分数量，每个部分包含相同数量的值

(第13至26行)。然后，我们将计算每个部分中质数的数量的工作委托给Callable的单个实例。现在我们已经有了这些部件，让我们来安排它们的执行。我们将为此使用一个线程池，并将这些部分的执行提交给executor服务(第28至31行)。

在最后一步，第33行，我们计算了从各部分接收到的结果。

我们已经准备好了。我有两个内核，所以如果我创建两个线程和两个部分，我希望得到两倍的加速(它应该花费大约一半的时间，作为顺序运行)。让我们用命令java ConcurrentPrimeFinder 10000000 2来测试一下，在池大小和分区数量都为2的情况下运行程序，达到1000万个数字。

```tcl
Number of primes under 10000000 is 664579
Time (seconds) taken is 4.236507
```

在连续运行和并发运行中，质数的数量是相同的，这很好，而且在两个版本中完成的工作量是相同的。然而，我期望在两个核的情况下运行时大约是3秒，但是我们达到了4秒多一点——仅仅是1.5倍的加速，而不是两倍。是时候重新考虑一下我们错过了什么。

您可能会觉得这与分区的数量有关。当然，增加线程的数量而不增加分区的数量是没有用的。然而，由于这是计算密集型的，我们已经知道，将线程数量增加到超过内核数量将没有帮助。所以一定是part的数量。让我们试着证明这一点，并找到一个解决办法。

我们打开活动监视器(在Windows上打开任务管理器，在Linux上打开系统/资源监视器)来查看内核的活动。先按顺序模式运行主查找器，然后再按并发模式运行。结果应该类似于第30页的图2“核心在连续和并发质数计数中的活动”。



![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_115.png)

正如我们预期的那样，在连续运行期间内核没有得到充分利用。然而，在并发运行期间，大多数情况下，两个核都是非常活跃的。然而，大约60%的核只有一个是活动的。这告诉我们，跨两个线程/内核的工作负载并不是均匀分布的，前一半的完成速度比第二个快得多。如果我们考虑给定问题的性质，很明显，第二部分中检验较大质数的努力要大于第一部分中检验较小质数的努力。为了实现最大的加速，我们需要将问题均匀地分割到两个线程上。

一般来说，公平分配各part的工作量并不容易;它需要对问题及其在输入范围内的行为有相当好的理解。对于质数计数问题，我们采取的方法是将范围按顺序分割成相等的部分。相反，我们可以试着把它们分成大数和小数的组合来得到一个均匀分布。例如，要分成两部分，我们可以考虑把范围内的第一个和最后一个季度分成一个部分，中间两个季度分成第二个部分。我们目前拥有的代码并不适合这种分区。如果我们修改代码来实现这一点，我们会注意到运行时减少了。然而，当我们考虑更多的部分时，分割会变得更加困难——对于更多的核心，我们希望拥有更多的部分。幸运的是，有一个更简单的解决方案。

更少的部分(比如两个)的主要问题是，一个核做更多的工作，而其他核旋转他们的数字。我们将问题划分得越细，就越有可能有足够的片来让所有核心都忙碌起来。开始时比线程使用更多的部件，然后当线程完成更小的部件时，它们将获取其他部件来执行。一些内核可以处理长时间运行的任务，而其他内核可以处理几个短时间运行的任务。对于这个示例，让我们看看池大小和分区是如何影响性能的;我们将保持池大小为2，但改变部分的数量为100:

```tcl
Number of primes under 10000000 is 664579
Time (seconds) taken is 3.550659
```

这是1.85倍的加速;虽然不是理想的两次，但已经很接近了。我们可以尝试改变部分的数量或分区策略。然而，使用相当特别的分区，我们能够得到一个不错的速度提高。临时分区可能不会一直有效。关键是，我们应该确保核心具有相当统一的工作负载，以获得良好的利用率。

##### 2.5 Speedup for the Computationally Intensive App为计算密集型应用程序加速

计数素数的例子很简单，但它表明，对于计算密集型操作，各部分之间的工作负载的公平平衡是至关重要的。

我们必须选择分区大小和池大小。让我们看看这两个因素对性能有什么影响。我在客户端模式下运行程序在八核处理器上(因为它比服务器模式需要更长的运行时间，这使得比较结果更容易)。性能的度量如图3所示，在第32页，8核处理器上客户机模式下的素数计算:池大小和部件的影响。

有一些教训，我们可以从图中学到关于并行计算密集型应用程序:

我们至少需要有和内核数量一样多的分区看到性能差的不到八个部分。

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_123.jpg)

线程多于核心数无济于事–我们看到，对于每个分区，曲线趋于平坦，超过了池大小8。

拥有更多的分区总比拥有更少的分区好，因为更多的分区有助于使核心保持繁忙-从曲线上可以明显看出这一点。

•在一定数量的大分区之后，拥有更多分区几乎没有好处-例如，我观察到，有八个内核且池大小为八，则16、64、256、512和1024个分区所花费的时间为8.4s ，7.2s，6.9s，6.8s和6.8s。

关注解决问题的努力;如果这需要大量的计算，那么这是在实际计算中浪费时间。重要的是要有合理数量的部件，这样所有的核心都有足够的工作要做。设计一种简单的方法来划分问题，看看它是否提供了对所有核心的公平利用。

##### 2.6 Strategies for Effective Concurrency 有效并发的策略

一方面，我们希望在使用并发时确保一致性和准确性。在另一方面，我们要确保我们'重新获得尽可能多的性能，尽可能给定的硬件上。在本章中，我们研究了实现这两个目标的方法。

一旦完全消除了共享的可变状态，我们就可以很容易地避免竞争条件或一致性问题。当线程不竞争访问可变数据时，就不存在可见性和跨越内存障碍的问题。

我们也不必担心控制线程的执行顺序;由于它们不竞争，所以在代码中不存在需要保护的互斥部分。

尽可能提供`共享的不变性`。否则，请遵循`隔离的可变性`-确保只有一个线程可以访问该可变变量。我们再不是在谈论这里的同步共享的状态。我们重新确保只有一个线程访问该可变变量。

我们创建多少线程以及如何对问题进行分区会影响并发应用程序的性能。

首先，为了从并发中受益，我们必须能够将问题划分为可以同时运行的较小任务。如果一个问题有一个可以在显著部分“ T为分区，则该应用程序可能没有真正使其并发看到太多的性能提升。

[如果任务是IO密集型或具有大量IO操作，那么拥有更多线程将有所帮助。在这种情况下，线程数应比内核数大得多。我们可以使用](part29.htm#bookmark41)第16页上的“[确定线程数”中](part29.htm#bookmark41)[介绍的公式来估计线程](part29.htm#bookmark41)[数](part29.htm#bookmark41)。

对于需要大量计算的过程，实际上线程数多于核心数实际上可能会受到损害—[请参阅](part36.htm#bookmark48)[第20页的“质](part36.htm#bookmark48)[数的并行计算” ](part36.htm#bookmark48)[。](part36.htm#bookmark48)

但是，假设可以将问题划分为至少与线程数量相同的任务，那么至少拥有与内核数量相同的线程将使我们受益。

尽管线程数会影响性能，但这并不是唯一的事情。每个零件（part）的工作量以及每个零件相对于其他零件要花费多少时间都影响性能。对问题进行统一划分可能会花费过多的精力，并且可能不会比临时划分产生更好的结果。权衡努力与收益。我们应该尝试通过使用简单的分区方法来达到合理的工作量。无论如何，良好的分区需要了解问题的性质及其不同输入的行为。难度取决于手头问题的复杂性。‌

#####  2.7 Recap 	概述，回顾

以下是获得多核处理器好处的方法：

•我们必须将应用程序划分为可以同时运行的多个任务。

•我们应该选择至少与内核数一样多的线程，前提是问题足够大，可以从许多线程中受益。

•对于计算密集型应用程序，我们应将线程数限制为内核数。

•对于`io密集`的应用程序，用于阻塞的时间会影响我们创建的线程数量。

•使用以下公式估计线程数：

```java
Number of threads = Number of Available Cores / (1 - Blocking Coefficient)
where 0 ≤ blocking coefficient < 1.
```

线程数=可用核心数/（1-阻塞系数）

其中0≤阻塞系数<1。

我们应该把问题分成几个部分，这样内核就有足够的工作，它们也能得到很好的利用

我们必须避免共享的可变状态，而是使用隔离的可变或共享的不可变。

我们应该充分利用现代线程API和线程池。

接下来，我们将讨论处理状态的一些设计选项。

### Design Approaches 设计思路

我们无法避免操纵状态（state）;这对于我们创建的程序来说是不可或缺的。例如，编译器会获取一组源文件并创建字节码，而邮件应用程序会跟踪未读电子邮件等等。

随着我们在Java方面的经验积累，我们经常会改变设计程序的方式。对象封装状态，它们的方法帮助在选定的有效状态之间转换。当有人说我们可以创建不改变状态的重要代码时，我嗤之以鼻。后来当他向我展示的时候，我很好奇，也很开明，就像那个在书店里的孩子一样。

操纵状态不一定意味着改变状态。考虑状态转换而不是状态修改。这是一种设计状态更改而不修改任何东西的方法。在本章中，我们将探索创建不改变内存中任何东西的程序的方法。不要被本章的篇幅所欺骗，它很短，但是它抓住了一些基本的设计方法。

##### 3.1 Dealing with State

我们无法逃避处理状态的问题，但有三种方法可以做到这一点:

共享的可变性、孤立（隔离）的可变性和纯粹的不可变性。

一个极端是共享突变;当然，我们创建变量并允许任何线程以可控的方式修改它们。对我们大多数Java程序员来说，使用共享可变性进行编程是一种简单的生活方式，但是这会导致不希望的同步和模型受损。我们必须确保代码在适当的时间跨越内存障碍，并对变量有良好的可视性。我们也必须确保共享的易变性两个线程不能同时修改一个字段，对多个字段的修改是一致的

我们没有得到编译器或运行时的支持来确定正确性;

我们必须分析代码，以确保我们做了正确的事情。我们一接触到代码，就必须重新分析代码的正确性，因为同步很容易出错。幸运的是，我们还有其他选择。

处理状态的另一种中间方法是隔离的可变性，其中变量是可变的，但是从来不会被多个线程看到。我们确保线程之间共享的任何东西都是不可变的。Java程序员会发现这相当容易设计，因此隔离的可变性可能是一种合理的方法。纯粹的不变性是另一个极端，即不允许任何东西发生变化。为此进行设计并不容易，部分原因是问题的性质，但主要是因为我们对这种方法缺乏经验。语言也让这变得困难;用Java编写具有不变性的程序需要大量的努力和纪律。这是一个范式的转变，需要探索一些不同于我们在Java中习惯的数据结构和方法。然而，如果我们能实现这样一个实际的设计，结果是奖励简单和安全的并发。

我们选择的处理状态的方法取决于问题和我们团队探索设计选项的意愿。纯粹的不变性是最理想的。

然而，这说起来容易做起来难，特别是对于那些花了数年时间改变共享状态的程序员来说。至少，我们应该以孤立的变异为目标，避免纯粹邪恶的共同变异。

我们讨论了处理状态的三种方法。接下来，我们将学习如何使用单个问题应用这些方法。

##### 3.2 Exploring Design Options 探索设计选项

处理状态是一项需要在编程艺术中进行实践的活动。我们对接收到的输入、计算结果和更改的文件所做的操作都涉及到状态。我们无法回避状态，但我们可以选择如何处理它。

这里，我们将首先选择一个示例，在接下来的几节中探索处理其状态的不同选项。在最近的一次Java用户组会议上，讨论转向了成员之间的集体经验。我在这次聚会上看到了年轻和成熟，并自告奋勇地积累了多少年的经验。让我们探讨一下完成这个任务的可用选项。

乍一看，净年数会随着我对每个人的工作年数的合计而变化，这似乎无法避免突变。房间里有好几个人，我需要尽快想出一个办法来计算出总数。让我们讨论一下处理国家事务的三种选择的优缺点。

##### 3.3 Shared Mutable Design  共享可变的设计

我们计算总工作年数的第一个方法是一个熟悉的方法:在黑板上写上0，然后让每个人走上前把他们的工作年数加到这个总数上。与董事会关系最密切的弗雷德跳起来，把自己的年龄加到总数上。在我们还不知道的时候，已经有很多人排起了队，在董事会周围引起了争论。

弗雷德一说完，就轮到简了。我们必须密切关注，以确保恶作剧的人不会把入口变成不可能的东西，比如无限。共同的突变在这里起作用，伴随着它带来的挑战。我们必须在给定的时间内对不止一个试图更改号码的人进行监管。此外，每个人都要耐心地等待轮到自己在黑板上写字。我希望不要成为最后一个人。

在编程术语中，如果房间中的每个人都是一个单独的线程，我们就必须同步他们对共享的可变变量total的访问。一个表现不佳的参与者破坏了整个小组。而且，这种方法涉及到相当多的线程阻塞，线程安全性高，但并发性低。对于一个大的团队来说，这项任务可能会变得耗时且令人沮丧。我们想要减轻这种痛苦，所以让我们探索另一种方式。

##### 3.4 Isolated Mutable Design 孤立可变设计（方法隔离）

我可以走到黑板前，但我不写下最初的总数0，我可以把我的电话号码，这样房间里的每个人都可以用短信把他们的工作年数发给我。现在每个人都舒舒服服地坐在座位上把信息发送给我。他们不需要排队，而且只要按一下智能手机按键就能搞定。

当它们到达时，我将按顺序接收年份，但发送者是并发的和非阻塞的。

孤立的突变在这里起作用。总数是孤立的:只有我持有这个值，其他人不能访问它。通过分离可变状态，我们消除了前一种方法的问题。不用担心两个或两个以上的人同时改变东西。我将继续计算这些年来收到的答复。

我的运营商负责将并发消息转换为一个良好的顺序消息链，以便我处理。我可以在会议结束时或我认为结束时宣布总数。

在编程术语中，如果房间里的每个人，包括我在内，都是一个线程(实际上是一个参与者，我们将在第8章第163页中看到，它支持独立的可变性)，那么他们每个人都会向我发送一条异步消息。消息本身就像文本消息一样是不可变的，我接收到的数据将是我这边的一个副本，并且应该没有办法更改影响发送方视图的任何东西。我只是修改了一个本地封装良好的变量。因为我是唯一一个(线程)改变这个数据，没有必要同步的变化。如果我们确保共享数据是不可变的，并且可变数据是隔离的，那么在这种方法中我们就不会担心同步问题。

##### 3.5纯不可变设计 Purely Immutable Design

应对完全不变性需要一些习惯。改变事物感觉起来很自然，也很容易，因为我们已经习惯了用`Java`编程的方式。

很难想出不做任何改变就计算出工作年限的方法，但这是可能的。我们可以让房间里的每个人都站成一排，而不离开自己的座位。指示每个人从他们左边的人那里得到一个数字，把他们自己的工作年数加到这个数字上，然后把总数传给他们右边的人。然后，我就可以把自己的工作年数告诉房间里第一个热切等待我的人。

在这种方法中，没有人会改变任何事情。每个人都持有链中截至他们的总工作年数。我从链上最后一个人那里得到的总数是整个组的总工作年限。我们可以在不改变任何东西的情况下计算出总数。每个人取部分总数并创建一个新的总数。它们接收到的旧总价值可以按照自己的意愿保留或丢弃(垃圾收集)。最后一种方法在函数式编程中很常见，可以通过函数组合实现。我们使用诸如Scala、Clojure、Groovy和JRuby等语言中的foldLeft()、reduce()或inject()等方法来实现这些操作。

这种方法也不关心并发性。这需要花费一些精力来组织或组成这个操作序列，但我们能够实现完全不变性的结果。如果人数非常多，我们甚至可以将他们划分为更小的组—形成树而不是一行—以实现更大的并发性和速度。

##### 3.6 Persistent/Immutable Data Structures持久/不可变的数据结构

在纯不可变的net工作年设计示例中，我们要求每个人创建一个新的总数。

当我们通过人员链时，一个新的数字被创建，而旧的数字被丢弃，这并不是什么大问题，因为这些数字相对较小。我们需要处理的数据可能没有那么小;它可以是列表、树或矩阵。我们可能需要处理表示锅炉、卫星、城市等的对象。

我们不会天真地一次又一次地复制这些大对象，因为那样会导致糟糕的性能。不可变或持久的数据结构在这里起到了拯救作用。`Persistent(这里的持久一词与存储没有任何关系;它是关于数据在一段时间内保持不变。)`数据结构会对它们的值进行版本化处理，因此旧的和新的值会一直存在或持续存在，而不会降低性能。由于数据是不可变的，因此可以有效地对它们进行共享，以避免复制开销。持久数据结构被设计用来提供超级高效的更新。像Clojure和Scala这样的语言大量使用了这类数据结构。下面我们将讨论两种不同的持久数据结构的设计。

###### Immutable Lists 不可变列表

回到用户组会议，我们被要求收集与会者的名字。让我们探讨一下如何用纯粹的不变性来组织数据。

我们可以使用单个链表，其中每个节点有两个指针或引用。第一个引用是对person的引用，第二个引用是对列表中的下一个节点的引用，如第40页图4中的示例，Persistent list processing中所示。我们将看到如何使用这个不可变列表。

是时候改变名单了，因为苏珊刚刚参加了会议。我们可以创建一个持有对她的引用的节点。由于当前列表是不可变的，所以我们不能将这个新节点添加到列表的末尾。该操作需要更改列表中最后一个节点的第二个引用，这是不可变节点不允许的任务。

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_135.png)

我们可以将这个新节点添加到开始，而不是添加到结束。我们创建的新节点中的第二个引用可以指向当前列表的头。我们没有修改现有的列表，而是得到了一个新的列表。但是，新列表与现有列表共享除第一个节点之外的所有节点，如图4，第40页上的`Persistent list processing`所示。

在这种方法中，无论列表大小如何，加法只需要常量时间。

从列表开头删除也需要恒定的时间。新的列表包含的元素比原来的少一个，并且拥有一个指向原始列表中第二个节点的指针(可以通过简单的读取获得)。

由于从不可变列表的头部添加和删除的时间是恒定的，如果我们能设计问题和算法有效地在列表的头部操作，而不是在尾部或中间操作，我们将受益于它们。

##### Persistent Tries持久化树

不可变列表在保持状态的同时提供了很好的性能，但是我们不能一直围绕列表头组织操作。而且，数据通常不是简单的列表，而是更复杂的，比如树或hashmap，它们可以使用树实现。我们不能通过简单地改变树的根来摆脱问题;我们必须能够更改其他元素来支持插入或删除操作。

如果我们能把树弄平，那么对树的改变就变成了对它的一个短分支的改变。这正是瑞士EPFL的研究员Phil Bagwell所做的，他使用了一个高分支因子，即每个节点至少有三十二个子节点，在Web资源附录2 255页中创建了他所谓的“理想哈希树”。较高的分支系数减少了对尝试进行操作的时间，我们将很快讨论这一点。

除了32或更多的高分支因子之外，尝试使用一个特殊的属性来组织键。节点的键由它的路径决定。换句话说，我们不把键存储在节点中;它们的位置就是它们的钥匙。例如，让我们为路径使用数字，并将分支因子保持为3，与推荐的32或更多相比，这是一个相当小的数字，但这样便于讨论。我们将使用这个trie来存储参加部门会议的人员列表

(Joe、Jill、Bob、Sara、Brad、Kate、Paul、Jake、Fred和Bill，按此顺序)，如图5所示。

因为我们在本例中使用的分支因子是3，所以以3为基数的路径表示每个节点的索引。例如，Bill的路径是100，在以3为基数的情况下，它表示索引值9。类似地，Brad位于路径11

虽然Bagwell的树尝试不是不可变的，但Clojure的创建者Rich Hickey使用了这些尝试的一个变体来创建一个持久化散列实现Clojure。一个分支因子为32的trie最多需要4级才能容纳100万个元素。更改任何元素的操作只需要复制最多4个元素—接近常量时间操作。

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_148.png)

我们可以尝试实现不同的数据结构，比如树、hashmap甚至列表。我们在图4中看到的不可变列表，持久列表处理，在第40页允许我们只在头部添加和删除元素。树尝试移除这个限制，并允许我们在一个不可变列表的任何索引上“添加”和“移除”元素。

例如，让我们将新成员Sam添加到会议列表的末尾，我们使用图5中的trie实现会议列表。Sams索引为10转换为以3为基数的路径101，因此该节点应该作为Bill节点的兄弟节点，作为Sara节点的子节点。由于所有节点都是不可变的，为了完成这个任务，我们必须复制Sara、Jill和根节点;其余的节点不受影响。在此选择性复制之后，trie的结构如图6所示

![image-20201205183846154](E:\learningforalllife\git-workspace\PANDA-Walker\picture\image-20201205183846154.png)

我们不必复制所有元素，只需复制三个节点，即受影响的节点及其祖先节点。

复制本身是一个浅层的复制;我们将链接复制到子节点，但没有复制到子节点本身。当我们将分支因子增加到32或更多时，append操作受影响的节点数量仍然接近4，即级别的数量，而列表可以容纳的元素数量接近100万个。因此，添加到列表是一种固定时间的操作，用于所有实际目的。将元素插入到任意索引位置的开销会稍微大一些。然而，根据位置的不同，我们将需要一些额外的部分复制。未受影响的节点及其派生节点完整共享。

##### 3.7 Selecting a Design Approach选择设计方法

通过选择**隔离的可变性**或纯粹的不可变性，我们可以避免并发性的大多数问题。在大多数情况下，用**孤立(隔离)的可变性编程比用纯粹的不可变性编程更容易**。

通过隔离的可变性，我们必须确保可变变量实际上是隔离的，并且不会转义到多个线程。我们还需要确保线程之间传递的消息是不可变的。使用JDK提供的并发API或基于actor的并发框架来传递消息。

为纯粹的不变性而设计需要更多的努力。在使用面向对象分解而不是功能分解的应用程序中更难实现它。我们必须设计更好的算法，支持不变性，应用递归结构或功能组合，并利用持久数据结构。

### 3.8回顾	recap

我们无法避免与状态打交道，但我们有三种选择:

•共享的可变性 Shared mutability

•孤立（隔离）的可变性 Isolated mutability

•纯一成不变 Pure immutability

虽然我们已经习惯了共享的可变，但我们应该尽可能地避免它。消除共享的可变状态是避免同步问题的最简单方法。

选择这些设计方法要比选择一个库或一个API花费我们更多的精力。这也要求我们退后一步，思考如何设计应用程序。我们不希望为了保持状态而牺牲性能。因此，为了实现不变性设计，我们需要使用现代数据结构来保存状态，同时提供良好的性能。我们在本章讨论了处理状态的方法。接下来，我们将使用JDK中提供的工具对并发进行编程。

## Modern Java/JDK Concurrency

现代Java JDK /并发性

### Scalability and Thread Safety可伸缩性和线程安全性

如果您像我一样在上个世纪开始用Java编程，那么您已经忍受了早期Java版本附带的多线程和集合API。它处理线程并提供线程安全，但并不完全考虑性能或可伸缩性。即使线程是相对轻量级的，创建它们也需要时间和资源。

另外，线程安全是以牺牲可伸缩性为代价的。过度保守的同步限制了性能。因此，如果我们想要具有更好的可扩展性和吞吐量的可维护性代码，那么旧的线程API并不是正确的选择，即使它已经存在了很长时间并且仍然可用。这是一个新的世纪，Java 5发布了一个更新的并发API。它对线程API进行了相当大的改进，并且在三个主要方面有所改善:

1。处理线程(特别是线程池)不会那么痛苦，而且效率更高。

2.新的同步原语提供了更细的粒度，因此比原来的原语提供了更多的控制。

3.新的数据结构具有可伸缩性线程安全性和合理的并发性能

为了了解这个API的优点和挑战，让我们创建一个磁盘实用程序，它将查找目录中所有文件的总大小。对于大型目录层次结构，该程序的连续运行将花费很长时间。我们将不得不转向并发来加速它。

##### 4.1 Managing Threads with ExecutorService

为了找到目录大小，我们可以将操作分成几个部分，每个任务探索一个子目录。由于每个任务都必须在单独的线程中运行，所以我们可以启动thread的实例。

但是线程是不可重用的，我们不能重新启动它们，并且在它们上调度多个任务是不容易的。我们当然不想创建和子目录数量一样多的线程，因为我们发现它是不可扩展的，而且是最容易失败的方法。`java.util.concurrent` API正是为此目的而引入的，用于管理线程池。我们在第21页介绍了净资产值并发计算中的`ExecutorService`和不同类型的`execuorservice`。`ExecutorService、executor factory`和相关的API减轻了使用线程池的痛苦。它们方便地将池的类型与我们在池上执行的操作分离开来。每个`ExecutorService`表示一个线程池。ExecutorService不是将线程的生命周期与它运行的任务捆绑在一起，而是将创建线程的方式与它运行的内容分离开来。我们可以配置线程池的类型:单线程的、缓存的、基于优先级的、计划的/周期性的、或固定的大小，以及计划运行的任务的等待队列的大小。我们可以轻松地安排任何数量的任务运行。如果我们只是想要发送一个任务来运行，我们可以将该任务封装在Runnable中。对于将返回结果的任务，我们可以将它们封装在Callable中。

为了调度任意任务，我们使用ExecutorService的execute()或submit()方法。为了调度任务集合，我们使用invokeAll()方法。如果我们只关心要完成的任务之一，就像在优化问题中，许多可能结果中的一个已经足够了，那么我们可以使用invokeAny()方法。这些方法使用超时参数重载，超时参数是我们愿意等待结果的时间。一旦我们创建了执行器服务，线程池就准备就绪并活动起来提供服务。如果我们没有任务给它们，那么池中的线程就会空闲，除非是缓存池，在缓存池中，它们会在延迟之后死亡。如果不再需要线程池，则调用shutdown()方法。这种方法不会立即杀死池。它会在关闭池之前完成当前计划的所有任务，但我们不能计划任何新任务。调用shutdownNow()试图强制取消当前正在执行的任务。然而，这并不能保证，因为它依赖于任务对interrupt()调用做出良好的响应。

ExecutorService还提供了一些方法，允许我们检查服务是否已经终止或关闭。然而，最好不要依赖它们。我们应该为任务完成而不是线程/服务死亡而设计——关注工作的完成(应用程序逻辑)，而不是线程的终止(基础设施活动)。

##### 4.2 Coordinating Threads 协调线程

一旦我们将一个问题划分为几个部分，我们就可以在一个线程池中安排几个并发任务，并等待它们的结果。当这些任务完成后，我们可以按照第50页上的图7中的线程协调调度和连接进行处理。

我们不希望等待线程死亡，因为这些线程可能会被重用来运行多个任务。计划任务的结果是我们真正关心的。我们可以使用可调用接口和ExecutorService的submit()或invokeAll()方法轻松地实现这一点。让我们看一个例子。要查找目录层次结构中可能有数千个文件的所有文件的总大小，我们可以同时运行几个部分。当部分完成时，我们需要汇总部分结果。

让我们首先看一下文件大小的顺序代码:

```java
public class TotalFileSizeSequential {
private long getTotalSizeOfFilesInDir(final File file) {
if (file.isFile()) return file.length();
final File[] children = file.listFiles();
long total = 0;
if (children != null)
for(final File child : children)
total += getTotalSizeOfFilesInDir(child);
return total;
}
public static void main(final String[] args) {
final long start = System.nanoTime();
final long total = new TotalFileSizeSequential()
.getTotalSizeOfFilesInDir(new File(args[0]));
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

我们从一个给定的目录开始，计算所有文件的大小，然后递归地向下钻取子目录。

![image-20201205200350064](E:\learningforalllife\git-workspace\PANDA-Walker\picture\image-20201205200350064.png)

图7线程协调-调度和连接

本书中的这个程序和其他文件大小的程序在第一次运行时会花费很长时间，接下来的运行时间会在几分钟内下降。

这是因为文件系统的缓存。我已经丢弃了从第一次运行开始的时间，以便在比较中使用的所有运行都具有缓存的优势。让我们在几个目录上运行它。我们将使用输出来比较即将创建的并发版本的性能。

```java
>java TotalFileSizeSequential /etc
Total Size: 2266456
Time taken: 0.011836
```

```java
>java TotalFileSizeSequential /usr
Total Size: 3793911517
Time taken: 18.570144
```

我们当然希望通过使代码并发来提高速度。我们可以将问题划分为多个任务，其中每个任务接受一个目录并返回其总大小。

Callable接口非常适合这种情况，因为它的call()方法在完成时可以返回结果。在循环遍历目录中的每个文件时，可以使用`ExecutorService的submit()`方法调度任务。然后，我们可以通过调用Future对象的get()方法收集所有部分结果;此对象充当一个委托，在可用时向我们提供结果。让我们实现刚才在一个幼稚的`concurrenttotalfilesize`类中讨论的逻辑

```java
public class NaivelyConcurrentTotalFileSize {
- private long getTotalSizeOfFilesInDir(
- final ExecutorService service, final File file)
- throws InterruptedException, ExecutionException, TimeoutException {
5 if (file.isFile()) return file.length();
-
- long total = 0;
- final File[] children = file.listFiles();
-
10 if (children != null) {
- final List<Future<Long>> partialTotalFutures =
- new ArrayList<Future<Long>>();
- for(final File child : children) {
- partialTotalFutures.add(service.submit(new Callable<Long>() {
15 public Long call() throws InterruptedException,
- ExecutionException, TimeoutException {
- return getTotalSizeOfFilesInDir(service, child);
- }
- }));
20 }
-
- for(final Future<Long> partialTotalFuture : partialTotalFutures)
- total += partialTotalFuture.get(100, TimeUnit.SECONDS);
- }
25
- return total;
- }
```

代码开始探索一个给定的目录。对于每个文件或子目录，我们创建一个任务来查找其大小，并在第14行中将其调度到池中。一旦我们为一个目录下的所有文件和子目录调度了任务，我们就等待它们的大小通过Future对象返回。

为了计算目录的总大小，我们在第22行遍历futures。我们不希望无休止地阻塞以等待结果的到达，因此get()方法有响应的时间限制。换句话说，如果任务没有在该时间内完成，我们就会抛出一个错误。每次递归调用FilesInDir()方法的getTotalSize时，只要有更多的目录和文件要访问，我们就会在executor服务池上调度更多的任务。

让我们创建调用前面方法的代码

```java
private long getTotalSizeOfFile(final String fileName)
throws InterruptedException, ExecutionException, TimeoutException {
final ExecutorService service = Executors.newFixedThreadPool(100);
try {
return getTotalSizeOfFilesInDir(service, new File(fileName));
} finally {
    service.shutdown();
}
}
public static void main(final String[] args)
throws InterruptedException, ExecutionException, TimeoutException {
final long start = System.nanoTime();
final long total = new NaivelyConcurrentTotalFileSize()
.getTotalSizeOfFile(args[0]);
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

由于线程是一种有限的资源，我们不想创建太多的线程;关于如何估算，请参阅第2章，第15页的《劳动分工》。创建池后，我们使用getTotalSizeOfFilesInDir()方法设置对给定目录的搜索。

让我们拿一个驱动器的代码，看看它是如何执行的:

```java
>java NaivelyConcurrentTotalFileSize /etc
Total Size: 2266456
Time taken: 0.12506
```

这里报告的文件大小与顺序版本相同，但速度却不尽如人意。它实际上花了更长的时间运行。很多时间都花在了日程安排上，而不是为/etc下相当扁平的目录层次结构做实际工作。让我们不要让希望褪色。也许我们会看到/usr目录的改进，它在连续运行中花费了超过18秒的时间。

让我们试一试:

```java
>java NaivelyConcurrentTotalFileSize /usr
Exception in thread "main" java.util.concurrent.TimeoutException
at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:228)
at java.util.concurrent.FutureTask.get(FutureTask.java:91)
at NaivelyConcurrentTotalFileSize.getTotalSizeOfFilesInDir(
NaivelyConcurrentTotalFileSize.java:34)
at NaivelyConcurrentTotalFileSize.getTotalSizeOfFile(
NaivelyConcurrentTotalFileSize.java:45)
at NaivelyConcurrentTotalFileSize.main(
NaivelyConcurrentTotalFileSize.java:54)
```

嗯…这不是我们想看到的，一个超时问题。不要惊慌;我称这个版本幼稚是有原因的。

缺陷在`getTotalSizeOfFilesInDir()`方法中;它阻塞线程池。当这种方法发现子目录时，它安排了探索它们的任务到其他线程(第14行)。一旦调度了所有这些任务，该方法将等待每个任务的响应(第23行)。如果我们只有几个目录，那么这不是什么大问题。但如果我们有很深的层次结构，这个方法就会卡住。

当线程等待它们创建的任务的响应时，这些任务最终会在`ExecutorService`的队列中等待轮到它们运行。如果没有超时，这可能是由池引起的死锁。因为我们使用了超时，我们至少能够不受欢迎地终止，而不是永远等待。让代码并发并不是件容易的事，现在是时候回到绘图板来修复这些代码了。

我们希望将不同目录的大小计算委托给不同的线程，而不是在等待这些任务/线程响应时保留调用线程。

解决这个问题的一种方法是让每个任务返回它找到的子目录列表，而不是给定目录的完整大小。然后，我们可以从主任务分派其他任务来导航子目录。这将防止持有线程的时间超过简单地获取当前子目录的时间。

当任务获取子目录时，它们还可以合计目录中文件的大小。让我们把这个设计同时进行，看看它是否能给我们更好的结果。在发现子目录和文件时，需要将子目录列表和文件的总大小传递给主线程。我们需要一个不可变的对象来保存这些值，所以让我们创建一个名为SubDirectoriesAndSize的内部类来保存它。

```java
public class ConcurrentTotalFileSize {
class SubDirectoriesAndSize {
final public long size;
final public List<File> subDirectories;
public SubDirectoriesAndSize(
final long totalSize, 
final List<File> theSubDirs) {
size = totalSize;
subDirectories = Collections.unmodifiableList(theSubDirs);
}
}
```

接下来，我们将编写一个方法，给定一个目录，该方法将返回一个SubDirectoriesAndSize实例，该实例包含给定目录的子目录及其包含的文件的大小

```java
private SubDirectoriesAndSize getTotalAndSubDirs(final File file) {
long total = 0;
final List<File> subDirectories = new ArrayList<File>();
if(file.isDirectory()) {
final File[] children = file.listFiles();
if (children != null)
for(final File child : children) {
if (child.isFile())
total += child.length();
else
subDirectories.add(child);
}
}
return new SubDirectoriesAndSize(total, subDirectories);
}
```

在发现子目录时，我们希望将探索这些目录的任务委托给其他线程。并发性方面的大部分工作将涉及到新的方法getTotalSizeOfFilesInDir()。

```java
private long getTotalSizeOfFilesInDir(final File file)
throws InterruptedException, ExecutionException, TimeoutException {
final ExecutorService service = Executors.newFixedThreadPool(100);
try {
long total = 0;
final List<File> directories = new ArrayList<File>();
directories.add(file);
while(!directories.isEmpty()) {
final List<Future<SubDirectoriesAndSize>> partialResults =
new ArrayList<Future<SubDirectoriesAndSize>>();
for(final File directory : directories) {
partialResults.add(
service.submit(new Callable<SubDirectoriesAndSize>() {
public SubDirectoriesAndSize call() {
return getTotalAndSubDirs(directory);
}
}));
}
directories.clear();
for(final Future<SubDirectoriesAndSize> partialResultFuture :
partialResults) {
final SubDirectoriesAndSize subDirectoriesAndSize =
partialResultFuture.get(100, TimeUnit.SECONDS);
directories.addAll(subDirectoriesAndSize.subDirectories);
total += subDirectoriesAndSize.size;
}
}
return total;
    } finally {
service.shutdown();
}
}
```

我们创建一个大小为100的线程池，并将给定的顶级目录添加到要探索的目录列表中。然后，虽然还有文件需要研究，但我们在单独的线程中为手边的每个目录调用getTotalAndSubDirs()。当这些线程的响应到达时，我们将它们返回的部分文件大小添加为total，并将子目录添加到要研究的目录列表中。

一旦对所有子目录进行了分析，就会返回总的文件大小。对于最后一步，要移动此代码，我们需要main()方法。

```java
public static void main(final String[] args)
throws InterruptedException, ExecutionException, TimeoutException {
final long start = System.nanoTime();
final long total = new ConcurrentTotalFileSize()
.getTotalSizeOfFilesInDir(new File(args[0]));
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

这个版本的并发实现花费了不少精力，但是与旧的幼稚的ConcurrentTotalFileSize相比，新的ConcurrentTotalFileSize设计得更好——它不会长时间持有一个线程。它很快得到子目录的列表，所以我们可以单独安排访问他们。看起来应该会得到我们想要的结果;我们来看看这是不是真的。

```java
>java ConcurrentTotalFileSize /usr
Total Size: 3793911517
Time taken: 8.220475
```

首先，与原始版本不同，该版本成功地完成了。要找到/usr目录的总大小，只需要大约8秒，而顺序运行要花费18秒以上。时间庆祝一下呢?让我们回顾一下在这个例子中我们做了什么。我们将任务分派给线程，然后只在主线程中等待它们的结果。所有其他线程都很快，因为它们只需要花时间查找文件的总大小，并且返回给定目录下的子目录列表。虽然这个设计理念很简单，但实现起来却不容易。我们必须创建一个类来保存来自任务的不可变结果。它也需要一些努力来持续分派任务和协调它们的结果。

最终的结果是:更好的性能，但增加了相当多的复杂性。看看能不能化简。

##### Coordination Using CountDownLatch 协调使用CountDownLatch

在前面的例子中，Future有两种服务方式。首先，它帮助获得任务的结果。隐式地，它还帮助线程与这些任务/线程进行协调。它允许我们在线程继续其工作之前等待这些结果的到达。

然而，如果任务没有结果返回，Future作为协调工具就没有帮助。我们不希望仅仅为了协调而人为地返回结果。CountDownLatch可以作为这种情况下的协调工具。

NaivelyConcurrentTotalFileSize代码比ConcurrentTotalFileSize代码简单得多，也短得多。我更喜欢能够工作的简单代码。

NaivelyConcurrentTotalFileSize的问题是，每个线程都在等待它计划要完成的任务。这两个版本的代码的优点是它们没有可变的共享状态。如果我们在共享的可变性上妥协一点，我们可以保持代码简单并使其工作。让我们看看怎么做。

我们可以让每个线程更新一个共享变量，而不是返回子目录和文件大小。由于没有返回任何东西，代码会简单得多。我们仍然需要确保主线程等待所有的子目录被访问。我们可以使用CountDownLatch来表示等待结束。锁存器作为一个或多个线程的同步点，以等待其他线程到达一个完成点。这里我们简单地用门闩作为开关。

让我们创建一个名为`ConcurrentTotalFileSizeWLatch`的类，它将使`CountDownLatch`。我们将递归地将探索子目录的任务委托给不同的线程。当线程发现一个文件时，它不会返回结果，而是更新一个共享变量totalSize，其类型为AtomicLong。AtomicLong提供了线程安全的方法来修改和检索一个简单的长变量的值。此外，我们将使用另一个名为pendingFileVisits的AtomicLong变量来对仍要访问的文件数量保持一个选项卡。当这个计数变为零时，我们通过调用countDown()来释放锁存器。

```java
public class ConcurrentTotalFileSizeWLatch {
private ExecutorService service;
final private AtomicLong pendingFileVisits = new AtomicLong();
final private AtomicLong totalSize = new AtomicLong();
final private CountDownLatch latch = new CountDownLatch(1);
    private void updateTotalSizeOfFilesInDir(final File file) {
long fileSize = 0;
if (file.isFile())
fileSize = file.length();
else {
final File[] children = file.listFiles();
if (children != null) {
for(final File child : children) {
if (child.isFile())
fileSize += child.length();
else {
pendingFileVisits.incrementAndGet();
service.execute(new Runnable() {
public void run() { updateTotalSizeOfFilesInDir(child); }
});
}
}
}
}
totalSize.addAndGet(fileSize);
if(pendingFileVisits.decrementAndGet() == 0) latch.countDown();
}
```

探索目录的机制已经完成，因此我们现在需要代码来创建线程池，设置正在运行的目录的探索，并等待闩锁。当闩锁被updateTotalSizeOfFilesInDir()释放时，主线线程从它的await()调用中释放，并返回它知道的总大小。

```java
private long getTotalSizeOfFile(final String fileName)
throws InterruptedException {
service = Executors.newFixedThreadPool(100);
pendingFileVisits.incrementAndGet();
try {
updateTotalSizeOfFilesInDir(new File(fileName));
latch.await(100, TimeUnit.SECONDS);
return totalSize.longValue();
} finally {
service.shutdown();
}
}
public static void main(final String[] args) throws InterruptedException {
final long start = System.nanoTime();
final long total = new ConcurrentTotalFileSizeWLatch()
.getTotalSizeOfFile(args[0]);
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

这个版本的代码要少得多。让我们运行它。

>java ConcurrentTotalFileSizeWLatch /usr
>Total Size: 3793911517
>Time taken: 10.22789

花费的时间比`ConcurrentTotalFileSize`版本稍微多一些。这是因为所有线程共享的可变性都需要额外的同步，这就需要保护线程安全，这就降低了并发性。

花费的时间比`ConcurrentTotalFileSize`版本稍微多一些。这是因为共享的所有线程都需要额外的同步，易变性需要保护线程安全，这降低了并发性。在前面的示例中，通过将闩锁值设置为1，我们使用`CountDownLatch`作为一个简单的开关。我们还可以使用更高的值，让多个线程等待它。如果我们想让多个线程在继续执行某个任务之前达到一个协调点，那么这将非常有用。然而，`CountDownLatch`是不可重用的。一旦它被用于同步，它就必须被丢弃。如果我们想要一个可重用的同步点，我们应该使用一个`CyclicBarrier`。该代码的性能优于按顺序分配到大小相等的版本。它比`ConcurrentTotalFileSize`版本稍差一些，但简单得多。但是，访问共享的可变变量会有额外的风险，这是我经常警告的。如果我们能保持代码简单，同时避免共享的易变性，那就好得多了。我们将在第8章的第163页中看到如何做到这一点。

##### 4.3 Exchanging Data 交换数据

我们经常希望在多个协作线程之间交换数据。在前面的示例中，我们使用Future和AtomicLong。当我们想要在任务完成时得到一个响应时，Future是非常有用的。并发中的AtomicLong和其他原子类。原子包对于处理单个共享数据值非常有用。尽管它们对于交换数据很有用，但正如我们在上一个示例中看到的那样，它们可能会变得很笨拙。

为了处理多个数据值或频繁地交换数据，我们需要一种比这两种更好的机制。java.util.concurrent API有许多类，它们提供线程安全的方式来在线程之间通信任意数据。

如果只想在两个线程之间交换数据，则可以使用`Exchange class`。它充当一个同步点，在这里两个线程可以以线程安全的方式交换数据。更快的线程被阻塞，直到较慢的线程赶上同步点来交换数据。

如果我们想在线程之间发送一堆数据，`BlockingQueue`接口的实例可能会派上用场。顾名思义，插入被阻塞直到空间可用，删除被阻塞直到数据可用。JDK提供了相当多的BlockingQueue。例如，要匹配insert和remove，可以使用一个`SynchronousQueue`来协调每个insert操作和另一个线程对应的remove操作。如果我们想让数据基于某些优先级排序，我们可以使用`PriorityBlockingQueue`。对于一个简单的阻塞队列，我们可以从`LinkedBlockingQueue`选择一个链表风格，或者从`ArrayBlockingQueue`选择一个数组风格。我们可以使用阻塞队列来同时解决文件大小的问题。我们可以不使用`AtomicLong`可变变量，而是让每个线程将其计算的部分文件大小放入一个队列中。然后，主线程可以从队列中获取这些部分结果，并在本地计算总结果。让我们先编写代码来浏览目录:

```java
public class ConcurrentTotalFileSizeWQueue {
private ExecutorService service;
final private BlockingQueue<Long> fileSizes =
new ArrayBlockingQueue<Long>(500);
final AtomicLong pendingFileVisits = new AtomicLong();
private void startExploreDir(final File file) {
pendingFileVisits.incrementAndGet();
service.execute(new Runnable() {
public void run() { exploreDir(file); }
});
}
private void exploreDir(final File file) {
long fileSize = 0;
if (file.isFile())
fileSize = file.length();
else {
final File[] children = file.listFiles();
if (children != null)
for(final File child : children) {
if (child.isFile())
fileSize += child.length();
else {
startExploreDir(child);
}
}
}
try {
fileSizes.put(fileSize);
} catch(Exception ex) { throw new RuntimeException(ex); }
pendingFileVisits.decrementAndGet();
}
```

我们委托不同的任务来浏览子目录。每个任务直接在给定目录下保存文件总大小的选项卡，最后使用阻塞调用put()将总大小放入队列中。任务发现的任何子目录都在单独的任务/线程中进行研究。主线程设置前面的代码，并简单地循环队列，以计算它从任务接收到的文件大小，直到所有子目录都被搜索完为止。

```java
private long getTotalSizeOfFile(final String fileName)
throws InterruptedException {
service = Executors.newFixedThreadPool(100);
try {
startExploreDir(new File(fileName));
long totalSize = 0;
while(pendingFileVisits.get() > 0 || fileSizes.size() > 0)
{
final Long size = fileSizes.poll(10, TimeUnit.SECONDS);
totalSize += size;
}
return totalSize;
} finally {
service.shutdown();
}
}
public static void main(final String[] args) throws InterruptedException {
final long start = System.nanoTime();
final long total = new ConcurrentTotalFileSizeWQueue().getTotalSizeOfFile(args[0]);
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

Let’s see how this version performs:
>java ConcurrentTotalFileSizeWQueue /usr
>Total Size: 3793911517
>Time taken: 10.293993

这个版本的性能与前一个版本相当，但是代码更简单——阻塞队列帮助线程之间交换和同步数据。接下来，我们将看到如何使用Java 7中引入的新API进一步改进这些解决方案。

##### 4.4 Java 7 Fork-Join API

`ExecutorService`管理一个线程池，允许我们在它的线程池中调度任务来执行。然而，池中有多少线程是由我们决定的，我们调度的任务和这些任务创建的子任务之间没有区别。Java 7带来了一个专门化的`ExecutorService`，提高了`fork-join` API的效率和性能。

ForkJoinPool类根据可用处理器的数量和任务需求动态地管理线程。Fork-join使用了工作窃取，即线程拾取(窃取)由其他活动任务创建的任务。这提供了更好的性能和线程利用率。

活动任务创建的子任务使用与外部任务不同的方法进行调度。我们通常在整个应用程序中使用一个fork-join池来调度任务。另外，也不需要关闭池，因为它使用了守护线程。

为了调度任务，我们将`ForkJoinTask`的实例(通常是它的一个子类的实例)提供给`ForkJoinPool`的方法。`ForkJoinTask`允许我们fork任务，然后在完成任务后加入。`ForkJoinTask`有两个子类:`RecursiveAction`和`RecursiveTask`。为了调度不返回任何结果的任务，我们使用了`RecursiveAction`的一个子类。对于返回结果的任务，我们使用`RecursiveTask`的子类

`fork-join API`适用于规模合理的任务，这样开销可以平摊，但不会太大(或在循环中无限运行)，从而实现合理的吞吐量。`fork-join API`希望任务没有副作用(不改变共享状态)，也没有同步或阻塞方法。`fork-join API`对于可以递归地分解问题，直到小到可以按顺序运行的问题非常有用。

多个较小的部分被安排在同一时间运行，利用由`ForkJoinPool`管理的池中的线程。

让我们使用fork-join API来解决文件大小问题。回想一下我们在第49页的4.2节协调线程中开发的简单解决方案。这个解决方案非常简单，但是对于较大的目录层次结构，我们遇到了池引起的死锁问题。任务最终等待它们生成的任务，同时持有这些子任务运行所需的线程。fork-join API结束了这种窃取工作的问题。当一个任务等待子任务完成时，执行该任务的线程会挑选一个新任务来运行。让我们看看使用fork-join API解决文件大小问题的代码。

```java
public class FileSize {
private final static ForkJoinPool forkJoinPool = new ForkJoinPool();
private static class FileSizeFinder extends RecursiveTask<Long> {
final File file;
public FileSizeFinder(final File theFile) {
file = theFile;
}
@Override public Long compute() {
long size = 0;
if (file.isFile()) {
size = file.length();
} else {
final File[] children = file.listFiles();
if (children != null) {
List<ForkJoinTask<Long>> tasks =
new ArrayList<ForkJoinTask<Long>>();
for(final File child : children) {
if (child.isFile()) {
size += child.length();
} else {
tasks.add(new FileSizeFinder(child));
}
}
for(final ForkJoinTask<Long> task : invokeAll(tasks)) {
size += task.join();
}
}
}
return size;
}
}
public static void main(final String[] args) {
final long start = System.nanoTime();
final long total = forkJoinPool.invoke(
new FileSizeFinder(new File(args[0])));
final long end = System.nanoTime();
    System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

在`FileSize`类中，我们持有对`ForkJoinPool`实例的引用。这个实例被定义为静态的，因此它可以在整个程序中共享。我们定义了一个名为`FileSizeFinder`的静态内部类，它通过扩展RecursiveTask并实现其compute()方法来提供任务执行引擎。在这个方法中，我们将给定目录中文件的大小相加，并将查找子目录大小的任务委托给其他任务，即FileSizeFinder的其他实例。invokeAll()方法等待所有子任务完成后再继续。然而，当任务被阻塞时，线程会窃取更多的工作，而不是闲置(就像成功团队中高度负责的成员一样)。完成compute()方法后，该任务返回给定目录的总大小。让我们使用Java 7编译并运行该代码:

>java com.agiledeveloper.pcj.FileSize /etc
>Total Size: 2266456
>Time taken: 0.038218
>
>java com.agiledeveloper.pcj.FileSize /usr
>Total Size: 3793911517
>Time taken: 8.35158

从输出中我们可以看到，与我们在本章中看到的其他并发版本相比，这个版本的程序运行得相当好。我们还可以看到，对于大型目录层次结构，该程序没有出现原始版本所存在的问题。

在本例中，我们递归地分割，直到得到一个目录或文件。通常，我们不希望将问题分解得太小，因为这可能导致调度开销。

集合是java.util并发包的一部分,提供了线程安全性，并充当了同步点的角色。线程安全很重要，但是我们不想牺牲性能。接下来，我们将研究java.util并发面向性能的数据结构。

##### 4.5可伸缩的集合Scalable Collections

最初随Java而来的Vector等集合为我们提供了线程安全性以性能为代价。。我们所有的访问，不管需要什么，都是线程安全的，但是很慢。

后来的集合(如ArrayList)提供了速度，但缺乏线程安全性。同样，当我们使用一个集合的同步包装器时，比如使用集合的synchronizedList()，我们用性能换取了线程安全。底线是我们必须在线程安全和性能之间做出选择。Java 5 .util.concurrent用并发数据结构(如`ConcurrentHashMap`和`ConcurrentLinkedQueue`)改变了这一切。这些集合提供了更好的性能，但牺牲了空间，但是我们必须愿意接受语义上的一点变化。同步集合提供线程安全性，而并发集合既提供线程安全性，又提供更好的并发访问性能。使用同步的集合就像开车通过红绿灯控制的十字路口;并行收集就像一条不拥堵的高速公路。如果我们在迭代同步map时更改它，则会遇到一`ConcurrentModificationException`异常。

本质上，我们需要在map上持有一个排他锁，一个相当悲观的锁，当我们遍历它时。这增加了线程安全性，但明显降低了并发性。因此，这些同步收集的吞吐量很低。

另一方面，并发收集的设计目的是提供吞吐量。它们允许多个操作共存。我们欢迎修改一个map，例如，当我们重新迭代它。API保证了集合的完整性，并且在当前迭代中，我们不会看到相同的元素出现两次。这在语义上是一种妥协，因为我们必须愿意接受在迭代或获取元素时更改和删除元素。

让我们对比一下ConcurrentHashMap与旧map的行为。

在下面的代码中，我们在一个单独的线程中迭代一个分数映射。就在迭代的中间，我们将一个新键放入map中。

```java
public class AccessingMap {
private static void useMap(final Map<String, Integer> scores)
throws InterruptedException {
scores.put("Fred", 10);
scores.put("Sara", 12);
try {
for(final String key : scores.keySet()) {
System.out.println(key + " score " + scores.get(key));
scores.put("Joe", 14);
}
} catch(Exception ex) {
    System.out.println("Failed: " + ex);
}
System.out.println("Number of elements in the map: " +
scores.keySet().size());
}
```

如果映射是普通的`HashMap`或它的同步包装器，这段代码就会崩溃——它将导致我们讨论过的冲突。实际上，在对同步集合进行迭代之前，我们必须对其进行锁定，以防止这种违反。但是，如果map是一个`ConcurrentHashMap`，它将不会遭受`ConcurrentModificationException`的惩罚。为了查看行为上的差异，让我们首先在一个好的老HashMap()实例中使用useMap()，然后使用一个同步包装器，最后使用一个ConcurrentHashMap实例。

```java
public static void main(final String[] args) throws InterruptedException {
System.out.println("Using Plain vanilla HashMap");
useMap(new HashMap<String, Integer>());
System.out.println("Using Synchronized HashMap");
useMap(Collections.synchronizedMap(new HashMap<String, Integer>()));
System.out.println("Using Concurrent HashMap");
useMap(new ConcurrentHashMap<String, Integer>());
}
}
```

当我们运行这个示例时，我们将看到传统的map不能在迭代的中间处理这个修改(甚至在单个线程中)，但是`ConcurrentHashMap`处理得非常好。

> Using Plain vanilla HashMap
> Sara score 12
> Failed: java.util.ConcurrentModificationException
> Number of elements in the map: 3
> Using Synchronized HashMap
> Sara score 12
> Failed: java.util.ConcurrentModificationException
> Number of elements in the map: 3
> Using Concurrent HashMap
> Sara score 12
> Fred score 10
> Number of elements in the map: 3

除了允许交叉读和写之外，并发集合提供了比同步版本更好的吞吐量。这是因为并发集合没有持有排他锁允许多个更新和读取并发工作。读取会看到最新的值。如果读取发生在批量写入的中间，则不会阻塞我们的读取以完成整个更新。这意味着我们可能看到部分变化，但不是全部。

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_173.jpg)

在任何情况下，并发集合都保证了可见性或`happens-before`行为。让我们来看看并发map与同步版本的性能。在这个度量中，每个线程的工作负载大致相同，因此随着线程数量的增加，进程中的总体工作负载也会增加，从而有更多的争用机会。在每个线程中，我从映射中访问一个随机键。如果没有找到密钥，我大约80%的时间插入它，如果找到了密钥，我大约20%的时间删除它。我首先在一个`ConcurrentHashMap`上尝试前面的操作，然后在一个带有同步包装器的`HashMap`上尝试。

图8(8核处理器上的`ConcurrentHashMap`与synchronized HashMap的吞吐量)显示了8核处理器上的吞吐量。当线程数少于两个时，同步的`HashMap`比`ConcurrentHashMap`做得稍微好一些;也就是说，同步版本在没有并发性的情况下运行得很好。随着线程数量的增加，`ConcurrentHashMap`明显优于HashMap。如果我们愿意接受并发集合在语义上的差异，我们将受益于它们比同步集合更好的性能。

###### Lock vs. Synchronized

到目前为止，在本章的示例中，我们已经避免了显式同步。但是，如果我们将自己限制在JDK并发api上，我们就不能长时间地回避它——在我们必须对多个变量或多个对象进行协调更改时，它就会出现。

###### synchronized

在Java中，我们有两种获取锁的构造:古老的synchronized和现代的synchronized接口。与synchronized关键字相比，Lock接口为锁定提供了更多的控制。让我们来讨论一下怎么做。我们使用synchronized来获得对象上的显式监视/锁定。在抓取监视器并在块的末尾释放它时，它还帮助线程跨越内存障碍。然而，synchronized的功能非常原始，并且有很多限制。



不幸的是，synchronized使获取锁可能无限期地阻塞。这是因为没有简单的方法告诉synchronized等待一段有限的时间来获取锁。如果我们尝试对线程安全性进行`单元测试`，我们会发现同步会使它变得不可能。没有一种有效的、简单的、确定性的方法(比如用mock替换synchronized)来检查我们是否在同步部分或同步块中包装了一个互斥的代码块。

此外，synchronized会导致独占锁，没有其他线程可以访问所持有的监视器。这并不适合有多个读者和不经常写文章的情况。尽管读取器可以并发运行，但它们被重新序列化，这会导致较差的并发性能。正如我们所看到的，尽管synchronized是作为关键字加入到该语言中的，但它提供了相当保守的线程安全性，我们几乎没有能力进行控制。与synchronized关键字相比，Java 5的锁接口给了我们更好的控制。

###### The Lock Interface

与synchronized非常相似，Lock接口的实现者保证对其方法的调用跨越内存障碍。我们使用Lock接口的Lock()和unlock()方法获取和释放锁，如下所示:

```java
aMonitor.lock();
try {
//...
} finally {
aMonitor.unlock();
}
```

我们在finally块中执行unlock()，以确保即使在异常情况下也能进行适当的解锁。虽然lock()是一个阻塞调用，但我们可以调用非阻塞变量tryLock()来立即获取可用的锁。要等待有限时间，请向tryLock()方法提供一个超时参数。甚至还有一种变体允许我们在线程等待锁时中断它。让我们看看Lock接口是如何修复synchronized引起的每个问题的。

使用tryLock()方法，我们的锁请求`不会被强制阻塞`。相反，我们可以立即检查我们是否获得了锁。此外，如果我们决定等待一个锁，我们可以对等待时间设置一个限制。锁接口使得线程安全的`单元测试非常容易`;有关详细信息，请参阅附录2,Web资源，第255页中的测试驱动多线程代码。

我们可以简单地模拟锁接口的实现，并检查被测试的代码是否在适当的时间请求锁和解锁。我们可以使用Read-WriteLock.获取并发读锁和排他写锁。因此，多个读取器可以并发地继续，而不必等待，并且只有在冲突的写器处于活动状态时才会延迟。

让我们看一个使用`ReentrantLock`的示例，它实现了锁接口。顾名思义，它允许线程重新请求它们已经拥有的锁，从而允许它们重新进入它们的互斥部分。一家银行找到我们，要求我们编写在两个账户之间转账的代码，其中定义了Account类，如下所示

```java
public class Account implements Comparable<Account> {
private int balance;
public final Lock monitor = new ReentrantLock();
public Account(final int initialBalance) { balance = initialBalance; }

public int compareTo(final Account other) {
return new Integer(hashCode()).compareTo(other.hashCode());
}
public void deposit(final int amount) {
monitor.lock();
try {
if (amount > 0) balance += amount;
} finally { //In case there was an Exception we're covered
monitor.unlock();
}
}
public boolean withdraw(final int amount) {
try {
monitor.lock();
if(amount > 0 && balance >= amount)
{
balance -= amount;
return true;
}
return false;
} finally {
monitor.unlock();
}
}
}
```

会计课上讲了很多;让我们讨论一下其中的各个部分。如果两个线程试图同时在两个帐户之间转账，但顺序相反，比如:thread1:从account1转账到account2。thread2:从account2转账到account1，那么在这种情况下，thread1可以锁定account1，同时thread2可以锁定account2。这两个线程现在处于死锁状态，等待获得它们需要的另一个帐户上的锁，同时持有它们当前的锁。

我们可以通过让两个线程以相同的顺序请求帐户锁来防止这种情况。然后，其中一个可以继续获取两个锁。然后，另一个线程可以跟随在一个临时块之后，而不是被死锁持有。Account类实现了`Comparable`接口，以促进锁定的自然顺序(请参阅Brian Goetz s Java Concurrency in Practice [Goe06]中避免死锁的讨论)。

通过使用`ReentrantLock`获取和释放锁，我们确保了deposit()和withdraw()操作是互斥的。此外，诸如转账之类的操作可能必须将多个存款和提取组合到一个互斥操作中。为此，帐户公开其锁实例。因此，我们准备在AccountService类中编写transfer()方法。我们首先尝试以自然顺序获得两个给定帐户的锁，这就是排序的原因。如果我们不能在一秒钟内获得它们中的任何一个的锁，或者在我们所能适应的任何时间内，我们只需抛出一个LockException。如果我们获得锁，我们完成转移，如果有足够的资金可用。

```java
public class AccountService {
public boolean transfer(
final Account from, final Account to, final int amount)
throws LockException, InterruptedException {
final Account[] accounts = new Account[] {from, to};
Arrays.sort(accounts);
if(accounts[0].monitor.tryLock(1, TimeUnit.SECONDS)) {
try {
if (accounts[1].monitor.tryLock(1, TimeUnit.SECONDS)) {
try {
if(from.withdraw(amount)) {
to.deposit(amount);
return true;
} else {
return false;
}
} finally {
accounts[1].monitor.unlock();
}
}
} finally {
accounts[0].monitor.unlock();
}
}
throw new LockException("Unable to acquire locks on the accounts");
}
}
```

transfer()方法通过对帐户进行`排序`来避免死锁，并通过限制等待获取锁的时间来避免无限期等待(饥饿)。由于所使用的监视器是可重入的，因此随后在deposit()和withdraw()方法中调用lock()不会造成损害。

虽然transfer()方法避免了死锁和饥饿，但使用lock()的deposit()和withdraw()方法可能会在其他上下文中造成问题。作为练习，我建议您修改这些方法，使用try- Lock()而不是Lock()。这可能和你以前做的事情有所不同。但是，最好使用锁接口和支持类而不是旧的同步构造来更好地管理锁。尽管Lock接口缓解了人们的担忧，但仍然需要大量的工作，而且很容易出错。在后面的章节中，我们将实现一个有价值的设计目标，完全避免锁/同步，并实现显式无锁并发。

4.7概述 总结

> 现代并发API java.util.concurrent与旧的API相比，改进了许多方面。它允许我们做以下:
>
> 管理线程池
>
> 轻松安排任务并发执行
>
> 线程安全的方式在并发运行的线程之间交换数据
>
> 获得细粒度的同步
>
> 使用并发数据结构可以获得更好的并发性能



###  Taming Shared Mutability

驯服共享可变性

到目前为止，我已经在这本书中好几次反对共享突变。因此，你可能会问，为什么我要在本章进一步讨论它。原因很简单:这是Java的生活方式，您可能会遇到使用共享可变性的遗留代码。我当然希望您对任何新代码，甚至在现有的项目中，都非常倾向于`独立的可变性`或`纯粹的不可变性`。在本章中，我的目标是帮助处理遗留代码，即您克服困难重构的危险代码。

##### Shared Mutability != public

`共享可变性`不限于公共字段。你可能会想，天啊，我所有的领域都是私人的，所以我没什么可担心的，但事情没那么简单。

多个线程对共享变量进行读写访问。另一方面，一个从未被多个线程访问过的变量是隔离的，不能共享的。如果我们不能确保可见性或避免竞态条件，共享的可变变量真的会把事情搞得一团糟。

据传，共享的易变性是Java程序员失眠的主要原因。不管访问权限如何，我们必须确保作为`参数传递`给其他方法的`任何值`都是线程安全的。我们必须假设我们调用的方法将从多个线程访问传递的实例。因此，传递一个非线程安全的实例不会帮助你晚上睡得更好。同样的问题也存在于我们从`方法返回的引用`中。换句话说，不要让任何`非线程安全的引用转义`。有关如何处理转义的广泛讨论，请参阅Java并发实践[Goe06]。

逃跑是困难的;我们甚至可能没有意识到，直到我们仔细检查代码，它在那里。`除了传递和返回引用之外`，如果我们直接`将引用设置为其他对象或静态字段，则变量可能会转义`。如果我们将`一个变量传递到一个集合`中，它也可以转义，比如前面讨论的`BlockingQueue`。当您下次打开带有可变变量的代码时，如果您感到头发丝都竖起来了，不要感到惊讶。

##### Spotting Concurrency Issues	查找并发性问题

让我们通过一个示例来学习如何识别`共享可变性`的危险，并了解如何修复这些问题。我们将重构一段代码来控制一个花哨的能源。它允许用户消耗能量，并自动定期补充能量。让我们先看一下需要我们帮助的代码

```java
public class EnergySource {
private final long MAXLEVEL = 100;
private long level = MAXLEVEL;
private boolean keepRunning = true;
public EnergySource() {
new Thread(new Runnable() {
public void run() { replenish(); }
}).start();
}
public long getUnitsAvailable() { return level; }
public boolean useEnergy(final long units) {
if (units > 0 && level >= units) {
level -= units;
return true;
}
return false;
}
public void stopEnergySource() { keepRunning = false; }
private void replenish() {
while(keepRunning) {
if (level < MAXLEVEL) level++;
try { Thread.sleep(1000); } catch(InterruptedException ex) {}
}
}
}
```

识别`EnergySource`类中的并发问题。有一些容易发现的问题，但也有一些隐藏的宝藏，所以慢慢来。

做了什么?好的，我们过目一下。`EnergySource`的方法可以从任何线程调用。非最终私有变量`level`是一个共享的可变变量，但它不是线程安全的。

从线程安全的角度来看，我们可以在大多数方法中不受保护地访问它。这将导致调用线程可能看不到更改，因为它没有被要求跨越内存障碍和竞态条件。这很容易发现，但还有更多。

​		`replenish()`方法将大部分时间用于睡眠，但它浪费了整个线程。如果我们尝试创建大量的`energysource`，我们会得到`OutOfMemoryError`错误，因为创建了太多的线程，通常JVM只允许我们创建`几千个线程`。

`EnergySource`破坏了类的不变式。构造良好的对象确保在对象本身处于有效状态之前，它的任何方法都不会被调用。然而，当EnergySource的构造函数在构造函数完成之前从另一个线程调用了replenish()方法时，它违反了不变式。另外，`Thread的start()方法会自动插入一个内存屏障`，因此它会在完成初始化之前转义该对象。

从构造函数内部启动线程确实不是一个好主意，我们将在下一节中讨论。这么小的一段代码就有这么多问题，是吧?让我们一个一个地解决它们。我不喜欢同时解决问题，这样我就能集中精力依次解决每个问题。

##### 5.3 Preserve Invariant

我们可能会尝试从构造函数中启动线程，以便在实例化对象时立即运行后台任务。这是一个好的意图，但有副作用。对start()的调用强制设置内存屏障，将部分创建的对象暴露给其他线程。另外，我们启动的线程可能会在实例构造完成之前调用实例上的方法。

对象应该保持其不变，因此禁止从构造函数内部启动线程。

`EnergySource`显然违反了这一点。我们可以将线程启动代码从构造函数移到单独的实例方法中。然而，这带来了一系列新的问题。我们必须处理可能在调用线程启动方法之前到达的方法调用，或者程序员可能会忘记调用它。我们可以用一个标志来处理它，但这会导致丑陋的重复代码。我们还必须防止线程启动方法在任何实例上被多次调用。

一方面，我们不应该从构造函数中启动线程，另一方面，我们不希望在没有完全创建实例以满足需要的情况下打开该实例。一定有办法摆脱这个困境。答案在有效Java [Blo08]的第一项中:考虑静态工厂方法而不是构造函数。在静态工厂方法中创建实例，并在将实例返回给调用者之前启动线程。

```java
private EnergySource() {}
private void init() {
new Thread(new Runnable() {
public void run() { replenish(); }
}).start();
}
public static EnergySource create() {
final EnergySource energySource = new EnergySource();
energySource.init();
return energySource;
}
```

我们保持构造函数的私密性和简单性。我们可以在构造函数中执行简单的计算，但在这里避免任何方法调用。私有方法init()完成我们前面在构造函数中完成的大部分工作。从静态工厂方法create()中调用此方法。我们避免了不变量违反，同时确保实例在创建时后台任务启动时处于有效状态。看看你自己的项目;你看到线程在构造函数中被启动了吗?如果您这样做了，那么您需要将另一个清理任务添加到重构任务列表中。

##### 5.4 Mind Your Resources

线程是有限的资源，我们不应该任意创建它们。`EnergySource`的replenish()方法浪费了一个线程并限制我们可以创建实例的数量

如果更多的实例像这样创建自己的线程，我们就会遇到资源可用性问题。replenish操作简短而快速，因此它是在计时器中运行的理想候选。

我们可以使用java.util.Timer。为了获得更好的吞吐量，特别是当我们希望拥有大量`EnergySource`实例时，最好重用线程池中的线程。`ScheduledThreadPoolExecutor`提供了一种优雅的机制来运行周期性任务。我们必须确保任务处理它们的异常;否则，这将导致他们今后的执行受到压制。让我们重构`EnergySource`，以作为定期任务运行replenish()方法。

```java
public class EnergySource {
private final long MAXLEVEL = 100;
private long level = MAXLEVEL;
private static final ScheduledExecutorService replenishTimer =
Executors.newScheduledThreadPool(10);
private ScheduledFuture<?> replenishTask;
private EnergySource() {}
private void init() {
replenishTask = replenishTimer.scheduleAtFixedRate(new Runnable() {
public void run() { replenish(); }
}, 0, 1, TimeUnit.SECONDS);
}
public static EnergySource create() {
final EnergySource energySource = new EnergySource();
energySource.init();
return energySource;
}
public long getUnitsAvailable() { return level; }
public boolean useEnergy(final long units) {
if (units > 0 && level >= units) {
level -= units;
return true;
}
return false;
}
public void stopEnergySource() { replenishTask.cancel(false); }
private void replenish() { if (level < MAXLEVEL) level++; }
}
```

除了在资源使用方面表现良好之外，代码变得更简单了。我们去掉了`keepRunning`字段，只是在stopEnergySource()方法中取消了任务。init()方法没有为`EnergySource`的每个实例启动一个线程，而是安排计时器运行补充()方法。这种方法，反过来，变得更简单我们不关心睡眠或时间，相反，我们关注的逻辑，以提高能量水平。我们将reference replenishTimer设置为一个静态字段。这允许我们共享一个线程池，以便在EnergySource的多个实例上运行replenish()操作。我们可以根据定时任务的持续时间和实例的数量来改变这个线程池中的线程数，当前设置为10个。由于replenish()任务非常小，所以较小的池大小就足够了。将replenishTimer字段设置为静态有助于我们在`ScheduledThreadPoolExecutor`中共享线程池。然而，这导致了一个复杂的问题:我们必须想办法关闭它。默认情况下，执行程序线程作为非守护进程线程运行，如果我们不显式地关闭它们，它们将防止JVM的关闭。至少有两种方法来处理这个问题

•在EnergySource类中提供一个静态方法，像这样:

```java
public static void shutdown() { replenishTimer.shutdown(); }
```

这种方法有两个问题。`EnergySource`的用户必须记住调用这个方法。我们还必须添加逻辑来处理在调用`shutdown()`之后创建的`EnergySource`实例。我们可以向`newScheduledThreadPool()`方法传递额外的`ThreadFactory`参数。这个工厂可以确保创建的所有线程都是守护线程，就像这样

```java
private static final ScheduledExecutorService replenishTimer =
Executors.newScheduledThreadPool(10,
new java.util.concurrent.ThreadFactory() {
public Thread newThread(Runnable runnable) {
Thread thread = new Thread(runnable);
thread.setDaemon(true);
return thread;
}
});
```

这种方法的主要缺点是需要我们编写和维护的代码更多。

我们的`EnergySource`刚刚减掉了几磅，并且比我们在内部创建线程时更具可伸缩性。

检查您自己的项目，看看您在哪里创建线程，特别是使用Thread类。评估这些情况，看看是否可以像我们所做的那样使用周期性任务调度程序。

##### 5.5 Ensure Visibility

必须在适当的时间跨越内存障碍。在构造函数中跨越它是不好的，我们在示例中解决了这个问题。但是，我们必须确保访问共享可变变量级别的其他方法跨越内存障碍。看看这个记忆障碍是什么?在第9页，刷新你的记忆，为什么要跨越记忆障碍。如果我们只考虑竞争条件，我们可能会反对同步getter方法;

我们可以确信稍微老一点的变量s值副本是足够的。同步或锁定getter的动机与竞争条件一样重要，如果我们未能跨越内存障碍，我们的线程就不能保证在未来不可预测的一段时间内看到更改。让我们确保`EnergySource`的方法跨越内存障碍;

使用`synchronized`关键字是实现这一点的最简单方法。让我们现在就用它来提供可见性，然后改进它，之后我试着遵循，让它工作;让它成为更好的座右铭。

每个接触可变变量级别以进行读写的方法都需要跨越内存障碍，因此我们将它们都标记为synchronized。原始版本不允许将replenish()方法标记为synchronized，因为它是无限循环的。这里的最新版本没有这样的问题，因为它是从计时器中调用的一个简短、快速的方法。下面是确保可见性的新版本

```java
public synchronized long getUnitsAvailable() { return level; }
public synchronized boolean useEnergy(final long units) {
if (units > 0 && level >= units) {
level -= units;
return true;
}
return false;
}

public synchronized void stopEnergySource() {
replenishTask.cancel(false);
}
private synchronized void replenish() { 
    if (level < MAXLEVEL) level++; }
}
```

我们将`getUnitsAvailable()`、`useEnergy()`和`replenish()`方法标记为同步的，因为这些方法涉及level变量。尽管我们希望只有一个线程调用`stopEnergySource()`，但让我们继续同步它，以防它被多个线程调用。毕竟，这就是提供线程安全的全部意义所在。检查您自己的项目，查看对可变变量(包括getter和setter)的所有访问是否都使用synchronized或其他相关结构跨越了内存障碍。访问最终不可变变量不需要跨越内存障碍，因为它们不会改变并且缓存的值和内存中的值一样好。

##### 5.6 Enhance Concurrency

我可以在我的房子周围建造一条充满鳄鱼的护城河来提供安全，但这将使我每天进出成为一个挑战。过度保守的同步就像这样;它可以提供线程安全性，但会使代码变慢。我们希望确保同步发生在每个类的正确级别，这样我们就不会损害线程安全性，但仍然享受良好的并发性。

Synchronizing instances相当常见，但存在一些问题。首先，它的范围是整个对象，这就成为了并发的粒度级别。这限制了我们在任何时候最多只能对整个对象执行一个同步操作。如果对象上的所有操作都是互斥的，比如在集合上添加和删除操作，那么这并不是完全坏的，尽管它肯定会更好。但是，如果对象可以支持多个操作，比如drive()和sing()，这些操作可以并发运行，但是需要与其他操作同步，比如drive()和tweet()，这些操作应该是互斥的，那么在实例上同步将无助于提高速度。在这种情况下，我们应该在这些方法的对象中有多个同步点。

`EnergySource`设法确保了可见性和线程安全。然而，这种同步有些过头了。在这种情况下，几乎没有理由对实例进行同步。让我们来解决这个问题。

因为`level`是类中唯一的可变字段，所以我们可以直接将同步移到它上面。然而，这并不总是有效的。如果我们有`多个字段`，我们可能必须`保护对多个字段`更改的访问。因此，我们可能必须提供一个或多个显式锁实例，我们将在后面看到。我们已经确定，最好将同步移到`level`变量。但是，有一个小问题，Java不允许我们锁定long这样的原始类型。我们可以通过将变量从long更改`为AtomicLong`来绕过这个约束。这将在访问此变量时提供细粒度的线程安全。在进一步讨论之前，让我们先看看修改后的代码:

```java
public class EnergySource {
private final long MAXLEVEL = 100;
private final AtomicLong level = new AtomicLong(MAXLEVEL);
private static final ScheduledExecutorService replenishTimer =
Executors.newScheduledThreadPool(10);
private ScheduledFuture<?> replenishTask;
private EnergySource() {}
private void init() {
replenishTask = replenishTimer.scheduleAtFixedRate(new Runnable() {
public void run() { replenish(); }
}, 0, 1, TimeUnit.SECONDS);
}
public static EnergySource create() {
final EnergySource energySource = new EnergySource();
energySource.init();
return energySource;
}
public long getUnitsAvailable() { return level.get(); }
public boolean useEnergy(final long units) {
final long currentLevel = level.get();
if (units > 0 && currentLevel >= units) {
return level.compareAndSet(currentLevel, currentLevel - units);
}
return false;
}
public synchronized void stopEnergySource() {
replenishTask.cancel(false);
}

private void replenish() {
if (level.get() < MAXLEVEL) level.incrementAndGet();
}
}
```

我们从`getUnitsAvailable()`中去掉了同步标记，因为`AtomicLong`负责线程安全和访问它持有的值的可见性。

我们还从useEnergy()方法中删除了synchronized。但是，改进的并发性带来了语义上的细微变化。早些时候，当我们检查能源的可用性时，我们锁定了所有的访问。如果我们找到了足够的能量，我们就能保证拥有它。然而，这降低了并发性;

当一个线程占用了时间时，所有其他与`EnergySource`交互的线程都被阻塞了。在这个改进的版本中，多个线程可以同时竞争能量，而不会持有排他锁。如果两个或多个线程同时竞争，其中一个线程将会成功，其他线程将不得不重试。我们有更好的速度读取和安全写入。

replenish()也不请求排他锁。它以线程安全的方式获取级别，然后在不持有任何锁的情况下递增值。这是可以的，因为只有一个线程在增加能量级别。如果该方法发现该值低于MAXLEVEL，则该值将保持较低，即使该值可能会减少。增量本身是线程安全的，不存在一致性问题。

我们将`stopEnergySource()`方法标记为synchronized，因为对该方法的调用将非常少，因此此时无需使该锁定变得更细粒度。在稍后的第105页中，我们将研究实现此方法的另一种方法。

重新访问您自己的项目，寻找可以在不损害线程安全性的情况下改进并发性的地方。检查是否有地方可以引入锁对象，而不是在整个实例上同步。在此过程中，确保所有处理可变状态的方法(包括读和写)都有适当的同步。

##### 5.7 Ensure Atomicity

前面的示例在代码中没有显式的同步，但是任何相关的兴奋都是短暂的。如果我们的可变状态有一个以上的相关或因变量，并且我们使用JDK并发API，我们就无法避免同步。让我们看看怎么做。

到目前为止，我们在重构方面取得的成功并没有被忽视。现在要求我们做一项改进:记录能源的使用情况。每一次能量消耗殆尽，我们都需要计数。

这意味着我们需要确保对`level`和对新变量使用的更改是原子的。换句话说，在一个线程中对它们的更改应该被保留，或者将它们都丢弃。我们不应该看到其中一个领域的变化而另一个领域没有变化。这又带来了对代码中显式同步的需求。使用synchronized是最简单的选择，但是这会限制多个读取器之间的并发性。如果我们不介意一个阅读器阻塞另一个阅读器，那么我们肯定会喜欢这种简单性。但是，如果我们喜欢更大程度的并发并且不介意为此牺牲一些复杂性，那么我们可以使用`ReentrantReadWriteLock`。这提供了一对锁，一个读锁和一个写锁，读写锁可以分别使用。使用此锁将允许我们在任何给定实例上拥有多个并发读取器或一个独占写入器。让我们在示例中使用这个锁。因为我们使用的是显式锁，所以我们可以将`level`字段缩减为简单的long，而不是`AtomicLong`。

```java
public class EnergySource {
private final long MAXLEVEL = 100;
private long level = MAXLEVEL;
private long usage = 0;
private final ReadWriteLock monitor = new ReentrantReadWriteLock();
private static final ScheduledExecutorService replenishTimer =
Executors.newScheduledThreadPool(10);
private ScheduledFuture<?> replenishTask;
private EnergySource() {}
private void init() {
replenishTask = replenishTimer.scheduleAtFixedRate(new Runnable() {
public void run() { replenish(); }
}, 0, 1, TimeUnit.SECONDS);
}
public static EnergySource create() {
final EnergySource energySource = new EnergySource();
energySource.init();
return energySource;
}
public long getUnitsAvailable() {
monitor.readLock().lock();
try {
return level;
    } finally {
monitor.readLock().unlock();
}
}
public long getUsageCount() {
monitor.readLock().lock();
try {
return usage;
} finally {
monitor.readLock().unlock();
}
}
public boolean useEnergy(final long units) {
monitor.writeLock().lock();
try {
if (units > 0 && level >= units) {
level -= units;
usage++;
return true;
} else {
return false;
}
} finally {
monitor.writeLock().unlock();
}
}
public synchronized void stopEnergySource() {
replenishTask.cancel(false);
}
    private void replenish() {
monitor.writeLock().lock();
try {
if (level < MAXLEVEL) { level++; }
} finally {
monitor.writeLock().unlock();
}
}
}
```

我们引入了两个新字段:`usage`(用于计算能量源被消耗了多少次)和`monitor `(ReentrantReadWriteLock的一个实例)，以确保对这两个可变变量的更改是原子的。在`useEnergy()`方法中，我们首先获取一个写锁。如果需要，我们可以对锁使用超时。一旦获得了写锁，我们就改变级别和使用变量。在finally块的安全避风港内，我们释放获得的锁。

同样，我们也在replenish()方法中获得写锁。这确保了useEnergy()和supplement()所做的更改是互斥的。我们需要在get方法中使用锁，以确保字段的可见性，以及useEnergy()中的部分更改不会被看到。

通过使用读锁，我们允许多个get并发运行，并且只在写操作进行时阻塞。这段代码同时提供了相当不错的并发性和线程安全性。

我们必须保持警惕，确保我们在任何时候都没有危及安全。该代码比以前的版本更复杂，不幸的是，这使得它更容易出错。在下一章中，我们将看到如何避免显式同步。使用共享的可变状态是一个巨大的负担，需要我们忍受增加的复杂性和伴随而来的更大的出错几率。

重构代码时，要注意一些常见的与并发相关的错误:

不要从构造函数中创建线程;相反，在静态工厂方法中创建它们，参见第75页5.3节“保持不变”。

不要创建任意线程;相反，使用线程池来减少任务启动时间和资源使用，参见第76页5.4节“注意你的资源”。

确保对可变字段的访问跨越了内存障碍，并且对线程是可见的(参见第79页的5.5节，确保可见性)。评估锁的粒度并提高并发性。确保锁不是过于保守，而是处于正确的级别，以同时提供足够的线程安全和并发性，请参阅第80页的5.6节，增强并发性。当使用多个可变字段时，验证对这些变量的访问是原子的;也就是说，其他线程不会看到对这些字段的部分更改，参见第82页的5.7节“确保原子性”。在本章中，我们看到了显式同步是多么的困难。在下一章中，我们将讨论如何完全避免显式同步，这是JVM中相对较新的方法。

## Software Transactional Memory

软件事务内存

##### 软件事务性介绍内存 Introduction to Software Transactional Memory

​		还记得上次您完成一个必须同步共享可变变量的项目吗?你可能会有一种不安的感觉，怀疑自己是否在所有正确的地方做到了同步，而不是放松和享受工作做好的感觉。我的编程过程中出现过不少这样令人不安的时刻。

这主要是因为Java中的`共享可变状态`没有遵循最小意外原则。如果我们忘记同步，那么等待我们的将是不可预测的、潜在的灾难性结果。但是，人非圣贤孰能无过;忘记是我们的天性。我们所依赖的工具不应该惩罚我们，而应该弥补我们的不足，帮助我们实现创造性思维所寻求的目标。

对于可预测的行为和结果，我们需要超越JDK。在本章中，我们将学习如何使用由`Clojure`推广的软件事务性内存`(STM)`模型，在共享可变性的情况下安全地使用它。在可能的情况下，我们可以在项目中切换到或混合使用`Clojure`。但是我们并不是被迫使用`Clojure`，因为有一些方法可以直接在Java中使用`STM`，这要感谢像`Multiverse`和`Akka`这样的好工具。在本章中，我们将学习STM，稍微介绍一下它在`Clojure`中的样子，然后是如何在Java和Scala中编程事务性内存。当我们有频繁的读和非常少的写冲突时，这种编程模型非常适合，它使用简单但给出可预测的结果。

##### 6.1同步限制并发性  Synchronization Damns Concurrency

同步有一些根本的缺陷

如果我们不正确地使用它或完全忘记它，一个线程所做的更改可能对其他线程是不可见的。为了确保`可见性`和`避免竞态条件`，我们通常需要在哪些地方进行同步。

不幸的是，在同步时，我们迫使`竞争线程等待`。并发性受到同步粒度的影响，因此将其交给程序员会增加降低其效率或完全错误的机会。同步可能会导致许多`活性问题`。如果应用程序在等待其他锁时持有锁，那么它很容易死锁。当线程连续地无法获得特定的锁时，也很容易遇到活锁问题。

我们可以通过将锁设置成细粒度或细粒度来改进并发性。尽管这通常是个好主意，但最大的风险是无法在正确的级别上同步。更糟糕的是，没有任何迹象表明同步失败。此外，我们只移动了线程等待的位置:线程仍然在请求独占访问并期望其他线程等待。对于大多数使用JDK并发性设施的Java程序员来说，这就是大城市中的简单生活。我们在`可变状态`的命令式编程的道路上走了太久，很难看到同步的替代方案，但是有。

##### 6.2 The Deficiency of the Object Model 对象模型的不足

作为Java程序员，我们精通面向对象编程(`OOP`)。但是这种语言极大地影响了我们建模面向对象应用程序的方式。`OOP`并不是AlanKay在创造这个术语时所想到的东西。他的愿景主要是`消息传递`,他想摆脱数据(他认为系统是使用在执行操作但不持有任何状态的类似生物细胞的对象之间传递的消息来构建)

见附录2中面向对象编程的意义,网络资源,在255页。

在此过程中，OO语言开始沿着通过抽象数据类型(adt)隐藏数据、将数据与过程绑定或结合状态和行为的路径发展。这在很大程度上导致了我们`对状态的封装和突变`。在这个过程中，我们最终合并了身份和状态，合并了实例和它的数据。身份（实例）和状态的这种合并对许多Java程序员造成了威胁，其后果可能并不明显。

当我们遍历一个实例的指针或引用时，我们到达保存其状态的内存块。在那个位置操作数据感觉很自然。位置表示实例及其包含的内容。组合的同一性和状态是非常简单和容易理解的。

然而，从并发性的角度来看，这有一些严重的后果。例如，如果我们运行一个报告来打印关于银行账户的各种细节——数量、当前余额、交易、最低余额等等，我们将会遇到并发问题。手边的引用是通向随时可能改变的状态的门户。因此，我们不得不在查看帐户时锁定其他线程。其结果是低并发性。问题并不是从锁定开始的，而是从帐户身份与其状态的合并开始的。

我们被告知OO编程模型是真实的。遗憾的是，现实世界的行为并不完全像当前OO范式试图建模的那样。在现实世界中，状态不会改变;身份会变。下面我们将讨论这是怎么回事。

##### 6.3 Separation of Identity and State 身份和状态的分离

​		谷歌股票的价格是多少?我们可能会说股票的价值在市场开放时每分钟都在变化，但这在某种程度上只是在玩文字游戏。举个简单的例子，谷歌股票在2010年12月10日的收盘价是592.21美元，这是永远不会改变的，它是不可改变的，被写入了历史。我们正在查看它在指定时间的值的快照。当然，谷歌股票今天的价格和那天不一样。如果我们几分钟后再看(假设市场是开放的)，我们看到的是一个不同的价值，但旧的价值是不变的。

我们可以改变我们看待对象的方式，这就改变了我们使用它们的方式。将身份与其不可变状态值分开。我们将看到此更改如何启用无锁编程、改进并发性并将争用减少到最低限度。身份与状态的分离是Rich Hickey在实现Clojure‘s STM模型时迈出的关键一步;

请参阅附录2,Web资源，255页中的“值和更改:Clojure的身份和状态方法”。

假设我们的谷歌stock对象有两部分;第一部分表示股票的身份，它反过来有一个指向第二部分的指针，股票的最新值的不变状态，如图9所示，在第92页，可变身份与不变状态值的分离。

![image-20201205205314216](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201205205314216.png)

当我们收到一个新的价格，我们添加到历史价格指数，不改变任何现有的东西。由于旧的值是不可变的，所以我们共享现有的数据。因此，如果我们为此使用持久数据结构，我们就没有复制，也可以享受更快的操作，正如我们在第39页的3.6节，持久/不可变数据结构中讨论的那样。一旦有了新数据，我们就可以快速更改标识以指向新值。

身份与状态的分离是并发的纯粹幸福。我们不需要阻止任何对股票价格的请求。由于状态不会改变，我们可以轻易地将指针移交给请求线程。在我们更改标识后到达的任何请求都将看到更新。非阻塞读取意味着更高的可伸缩并发性。我们只需要确保线程对它们的世界有一致的看法。

最好的部分是，我们不需要;STM为我们做了这件事。我相信你现在渴望更多地了解STM。

##### 6.4 Software Transactional Memory 软件事务性内存

​		身份与状态的分离有助于STM解决同步的两个主要问题:`跨越内存障碍`和`防止竞态条件`。我们将首先在`Clojure`上下文中介绍`STM`，然后在Java中使用它

`Clojure`通过在事务中访问内存，消除了内存同步的危险细节(请参阅Programming Clojure [Hal09]和Clojure [FH11])。`Clojure`敏锐地监视和协调线程的活动。例如，如果没有冲突，使用不同帐户的线程就不会涉及到锁，也不会有延迟，从而实现最大并发性。当两个线程尝试访问相同的数据时，事务管理器将介入以解决冲突，同样，我们的代码中不涉及显式锁定。让我们探讨一下这是如何工作的。

​		按照设计，值是不可变的，而身份只能在`Clojure`中的事务中可变。在`Clojure`中根本没有办法`更改状态`，也没有为此而编写的编程工具。如果试图在任何事务之外更改对象的标识，它将失败，并出现严重的`IllegalStateException`异常。另一方面，当被事务绑定时，在没有冲突的情况下，更改是即时的。如果出现冲突，`Clojure`将自动回滚事务并重试。我们的责任是确保事务中的代码是`幂等的`。在函数式编程中，我们试图避免副作用，因此这与`Clojure`中的编程模型非常匹配。现在来看一个`Clojure STM`示例。我们使用refs在Clojure中创建可变身份。ref为它所表示的`不可变状态`的标识提供了协调的同步change。让我们创建一个ref并尝试改变它。

```java
(def balance (ref 0))
(println "Balance is" @balance)
(ref-set balance 100)
(println "Balance is now" @balance)
```

我们定义了一个名为balance的变量，并使用ref将其标记为可变的。

现在`balance`表示一个具有不可变值0的可变标识。

然后打印该变量的当前值。接下来，我们尝试使用命令`ref-set`修改`balance`。如果成功，我们应该看到最后一条语句打印的新余额。让我们看看结果是怎样的;我只需使用clj mutable.clj运行此脚本。关于如何在系统上安装和运行它，请参阅Clojure文档。

> Balance is 0
> Exception in thread "main"
> java.lang.IllegalStateException: No transaction running (mutate.clj:0)
> ...

前两个语句运行得很好:我们能够打印初始余额0。然而，我们试图改变这个值的努力以一个非法的例外而失败。我们在事务之外修改了变量，这让Clojure之神很生气。

​		将这种明显的失败与Java在不同步情况下修改共享的可变变量时的邪恶行为进行对比。这是一个相当大的改进，因为我们宁愿让我们的代码表现正确或大声地失败，也不愿悄悄地产生不可预测的结果。我想邀请您庆祝一下，但我看到您急于修复Clojure STM的这个错误，所以让我们继续前进。

​		在Clojure中创建事务很容易。我们只是将代码块包装在`dosync`调用中。从结构上看，这有点像Java中的`synchronized`块，但是有很多不同之处:如果我们忘记在变化的代码周围放置`dosync`，我们会得到一个明确的警告。`dosync`没有创建排他锁，而是通过在事务中包装代码，让代码公平地与其他线程竞争。由于我们不执行任何显式锁，因此不必担心锁顺序，并且可以享受无死锁的并发性。

STM提供了一个简单的锁运行时事务组合，而不是强制在设计时预先考虑谁锁了什么锁以及锁的顺序。没有显式锁意味着程序员不会放置保守的互斥代码块。

其结果是最大并发性直接和动态地由应用程序行为和数据访问决定。当事务在进行时，如果没有与其他线程/事务冲突，事务就可以完成，其更改可以写入到内存中。但是，一旦Clojure发现其他一些事务已经在某种程度上发展到危及该事务，它就会悄悄地回滚更改并重复该事务。让我们修复代码，这样我们就可以成功地更改balance变量。

```java
(def balance (ref 0))
(println "Balance is" @balance)
(dosync
(ref-set balance 100)
)
(println "Balance is now" @balance)
```

代码中唯一的更改是将对ref-set的调用包装为对dosync的调用。

在`dosync`中，我们当然没有被限制在一行代码中;我们可以在一个事务中包装整个代码块或几个表达式。让我们运行代码来查看更改。

> Balance is 0
> Balance is now 100

平衡的状态值0是不可变的，但是身份变量`balance`是可变的。

​		在事务中，我们首先创建了一个新值100(我们需要习惯不可变值的概念)，但是旧值0仍然存在，此时balance指向它。一旦我们创建了新值，我们要求`Clojure`快速修改指针以平衡到新值。如果没有对旧值的其他引用，垃圾收集器将适当地处理它。`Clojure`提供了三种修改可变标识的选项，都只在事务中进行:

`ref-set`设置标识的值并返回该值。

`alter`将标识的事务内值设置为应用指定函数得到的值，并返回该值。这是修改可变标识的最常用的形式。

`commute`将事务内的更改与提交点分离开来。它将标识的事务内值设置为应用指定函数得到的值，并返回该值。然后，在提交点期间，将标识的值设置为使用标识最近提交的值应用指定函数的结果。当我们对“最后一胜”的行为感到满意，并且提供比alter更大的并发性时，“commute”是有用的。然而，在大多数情况下，`alter`比`commute`更合适。

除了`refs`之外，Clojure还提供了`atoms`.。它们提供数据的同步更改;然而，与refs不同的是，这些更改是不协调的，不能与交易中的其他更改组合在一起。atoms不参与事务(我们可以把对原子的每次更改看作属于一个单独的事务)。对于孤立的、离散的变化，使用原子;对于更多分组或协调的更改，请使用refs。

##### 6.5 Transactions in STM

毫无疑问，您已经在数据库中使用过事务，并且熟悉它们的原子性(Atomicity,)、一致性(Consistency,)、隔离(Isolation,)和持久性(Durability)(ACID)属性。Clojure s STM提供了前三个属性，但不提供持久性，因为数据完全在内存中，而不是在数据库或文件系统中。

原子性:STM事务是原子的。我们在事务中所做的所有更改要么在事务外部可见，要么不可见。在一个事务中，对refs的所有更改要么保留，要么不保留。

一致性:要么事务完全运行到完成，然后我们看到它的净变化，要么事务失败，而事情不受影响。从这些事务的外部，我们看到一个变化不断地跟随另一个变化。例如，在两个独立且并发的存款和取款事务结束时，余额与这两个操作的累积效果保持一致。

隔离:事务不能看到其他事务的部分更改，只有在成功完成时才能看到更改。这些属性关注于数据的完整性和可见性。然而，这种孤立并不意味着缺乏协调。相反，STM密切监视所有事务的进展，并试图帮助每个事务运行到完成(禁止任何基于应用程序的异常)。

Clojure STM使用的多版本并发控制(MVCC)与数据库非常类似。STM并发控制类似于数据库中的乐观锁定。当我们启动一个事务时，STM记录时间戳并复制我们的事务将使用的refs。由于状态是不可变的，所以这种对refs的复制既快又便宜。当我们对任何不可变状态进行更改时，我们实际上并没有修改值。相反，我们将为这些值创建新的副本。副本保存在事务的本地，并且由于有持久的数据结构(第39页3.6节，持久/不可变数据结构)，这一步也很快。如果STM在任何时候确定我们更改的refs已经被其他人修改了事务，它中止并重试我们的事务。当事务成功完成时，更改被写入内存，时间戳被更新(参见图9，在第92页，可变身份与不可变状态值的分离)。

6.6 Concurrency Using STM 使用STM事务的并发性

事务很好，但是如果两个事务试图更改相同的身份会发生什么呢?对不起，我不是故意让你这么长时间坐在座位上的。在本节中，我们将看几个这样的例子。不过，在我们浏览这些示例之前，有一点要注意。在生产代码中，确保事务是`幂等`的并且没有任何副作用，因为它们可能会被重试很多次。这意味着不需要打印到控制台，不需要日志记录，不需要发送电子邮件，也不需要在事务中进行任何不可逆转的操作。如果我们做了其中任何一件事，我们将负责逆转操作或处理其后果。

通常，最好将这些副作用操作收集起来，并在事务成功后执行它们。与我的建议相反，我们将在示例中看到在事务中打印语句。这纯粹是为了说明目的。不要在办公室这么做!我们已经知道如何在事务中更改余额。现在让多个事务相互竞争以达到平衡

````java
(defn deposit [balance amount]
(dosync
(println "Ready to deposit..." amount)
(let [current-balance @balance]
(println "simulating delay in deposit...")
(. Thread sleep 2000)
(alter balance + amount)
(println "done with deposit of" amount))))
(defn withdraw [balance amount]
(dosync
(println "Ready to withdraw..." amount)
(let [current-balance @balance]
(println "simulating delay in withdraw...")
(. Thread sleep 2000)
(alter balance - amount)
(println "done with withdraw of" amount))))
(def balance1 (ref 100))
(println "Balance1 is" @balance1)
(future (deposit balance1 20))
(future (withdraw balance1 10))
(. Thread sleep 10000)
(println "Balance1 now is" @balance1)
````

在这个示例中，我们创建了两个事务，一个用于存款，另一个用于取款。在deposit()函数中，我们首先获得给定余额的本地副本。然后我们模拟一个延迟来在冲突过程中设置事务。延迟之后，我们增加余额。`withdraw()`与之相似，只是我们减少了余额。现在是时候练习这两种方法了。

让我们首先将变量balance1初始化为100，并使用future()函数让前两个方法在两个独立的线程中运行。继续运行代码并观察输出:

> Balance1 is 100
> Ready to deposit... 20
> simulating delay in deposit...
> Ready to withdraw... 10
> simulating delay in withdraw...
> done with deposit of 20
> Ready to withdraw... 10
> simulating delay in withdraw...
> done with withdraw of 10
> Balance1 now is 110

这两个函数都获得了它们自己的余额本地副本。在模拟延迟之后，deposit()事务就完成了，但是withdraw()事务就没那么幸运了。余额在它背后被更改了，因此它试图进行的更改不再有效。Clojure STM悄悄地中止事务并重试。如果我们没有这些打印出来的声明，我们就会忘记这些活动。重要的是余额的净效应是一致的，并且反映了存款和取款。在本例中，通过预取余额并延迟执行，我们有意将这两个事务设置为冲突进程。如果我们从两个事务中删除let语句，我们将注意到两个事务都以一致的方式完成，而不需要任何一个事务重复，这表明STM在保持一致性的同时提供了最大的并发性。我们现在知道如何改变一个简单的变量，但是如果我们有一个值的集合呢?列表在Clojure中是不可变的。然而，我们可以得到标识可以更改的可变引用，使其看起来好像我们更改了列表。名单没有改变;我们只是改变了对列表的看法。让我们通过一个示例来探讨这个问题。我家人的愿望清单原本只有一件东西，一台iPad。我想加一台新的MacBook Pro (MBP)，我的一个孩子想加一辆新自行车。因此，我们在两个不同的线程中将这些项添加到列表中。这里有一些代码:

```java
(defn add-item [wishlist item]
(dosync (alter wishlist conj item)))

(def family-wishlist (ref '("iPad")))
(def original-wishlist @family-wishlist)
(println "Original wish list is" original-wishlist)
(future (addItem family-wishlist "MBP"))
(future (addItem family-wishlist "Bike"))
(. Thread sleep 1000)
(println "Original wish list is" original-wishlist)
(println "Updated wish list is" @family-wishlist)
```

在`add-item()`函数中，我们将给定的项添加到列表中。alter函数通过应用提供的函数(在本例中是conj()函数)来修改事务内`ref`。函数conj()返回一个新的集合，其中包含连接或添加到集合中的给定元素。然后我们从两个不同的线程调用add-item()方法。运行代码看两个事务的最终效果:

原始愿望列表是(iPad)原始愿望列表是(iPad)更新的愿望列表是(自行车MBP iPad)原始列表是不可变的，所以它在代码的最后保持不变。当我们向这个列表中添加元素时，我们得到了这个列表的新副本。然而，由于列表是一种持久的数据结构，我们从共享项目(元素)中获得了内存和性能上的好处，如图10所示，在第100页向不可变的愿望列表中添加元素。列表和引用originalWishList的状态都是不可变的。然而，familyWishList是一个可变引用。每个添加项请求在它们自己的事务中运行。第一个成功更改了对新列表的可变引用(如图(2)所示)。但是，由于列表本身是不可变的，所以新列表能够共享元素iPad

![image-20201205210048162](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201205210048162.png)

原始列表。当第二个添加项成功时，它会在内部共享先前添加的其他两个项(如图(3)所示)。我们了解了STM如何处理事务之间的写冲突。有时冲突并不那么直接。假设我们在银行有一个支票账户和一个储蓄账户，两个账户之间的最低余额要求是1000美元。现在假设这两个账户的余额分别是500美元和600美元。我们最多可以从这两个账户中任意一个提取100美元，但不能同时从两个账户中提取。如果在每个帐户上依次尝试提取100美元，第一次尝试将会成功，但第二次将不会成功。如果这两个事务看到总余额超过$1,000，并且继续修改两个不同的值而没有写冲突，那么所谓的写倾斜异常将不会阻止这两个事务。最终的结果是，余额为900美元，低于允许的限额。让我们在代码中创建这个异常，然后找出如何修复它。

```java
(def checking-balance (ref 500))
(def savings-balance (ref 600))
(defn withdraw-account [from-balance constraining-balance amount]
(dosync
(let [total-balance (+ @from-balance @constraining-balance)]
(. Thread sleep 1000)
(if (>= (- total-balance amount) 1000)
(alter from-balance - amount)
(println "Sorry, can't withdraw due to constraint violation")))))
(println "checking-balance is" @checking-balance)
(println "savings-balance is" @savings-balance)
(println "Total balance is" (+ @checking-balance @savings-balance))
(future (withdraw-account checking-balance savings-balance 100))
(future (withdraw-account savings-balance checking-balance 100))
(. Thread sleep 2000)
(println "checking-balance is" @checking-balance)
(println "savings-balance is" @savings-balance)
(println "Total balance is" (+ @checking-balance @savings-balance))
```

我们从这两个账户的给定余额开始。在withdraw-account()函数中，我们首先读取两个帐户的余额并计算总余额。然后，在诱导延迟(这会使交易进入冲突过程)之后，只有当总余额不小于要求的最小值时，我们才更新由from-balance表示的其中一个帐户的余额。

在代码的其余部分中，我们并发地运行两个事务，其中第一个从支票余额中提取100美元，而第二个从储蓄中提取100美元。自两个事务运行在隔离和不修改任何共同点,他们完全无视的违反和落入写倾斜,正如我们可以看到从输出:

>checking-balance is 500
>
>savings-balance is 600 Total balance is 1100 checking-balance is 400
>
>savings-balance is 500 Total balance is 900

使用Clojure，我们可以很容易地使用ensure()方法避免写入偏差。我们可以告诉事务关注只被读取而不被修改的变量。STM将确保只有在我们读取的值没有在事务外部更改时才提交写操作;它以其他方式重试事务。

```java
(defn withdraw-account [from-balance constraining-balance amount]
(dosync
 (let [total-balance (+ @from-balance (ensure constraining-balance))]
 (. Thread sleep 1000)
 (if (>= (- total-balance amount) 1000)
 (alter from-balance - amount)
 (println "Sorry, can't withdraw due to constraint violation")))))
```

在第3行，我们对值constraining-balance调用了ensure()，我们在事务中读取它，但不修改它。此时，STM在该值上放置一个读锁，防止其他事务获得写锁。当事务接近完成时，STM在执行提交之前释放所有读锁。这样可以防止并发性增加时出现死锁。

即使这两个事务像以前一样并发运行，通过这个修改过的withdraw-account()方法调用ensure()，仍然保留了总余额的约束，正如我们在输出中看到的:

> checking-balance is 500
> savings-balance is 600
> Total balance is 1100
> checking-balance is 500
> savings-balance is 500
> Total balance is 1000
> Sorry, can't withdraw due to constraint violation

STM的显式无锁执行模型非常强大。当没有冲突时，就不会有阻碍。当存在冲突时，一个胜利者顺利地进行，而竞争者则不断重复。Clojure使用最大次数的尝试，并确保两个线程不会以相同的速度重复，从而导致重复失败。当我们有频繁的读冲突和非常少的写冲突时，STM是一个很好的模型。例如，这个模型非常适合典型的web应用程序，我们通常有几个并发用户执行他们自己数据的更新，但是共享状态的冲突很少。STM可以毫不费力地解决任何不常见的写入冲突。Clojure STM是并发编程领域的阿司匹林，它可以消除很多麻烦。如果我们忘记创建一个交易，我们会受到严厉的谴责。通过简单地将dosync放在正确的位置，我们获得了高并发性和线程之间的一致性。这种不讲究礼仪、简洁、高度表达和非常可预测的行为使Clojure STM成为一个值得认真考虑的选项。

##### 6.7 Concurrency Using Akka/Multiverse STM

我们了解了如何在Clojure中使用STM。我猜现在您对如何在Java代码中使用STM感到好奇。我们有几个选择:

> 1, 我们可以在Java内部使用Clojure STM。将事务代码包装在可调用接口的实现中是相当简单的。
>
> 2, 我们当中那些喜欢注释的人可能更喜欢Multiverse的STM API。
>
> 3, 除了使用STM之外，如果我们计划使用actor，那么我们还需要使用Akka库。

由Peter Veentjer创建的`Multiverse`是一个基于java的STM实现。在纯Java代码中，我们可以使用注释来表示事务边界。我们可以使用`@Transac- tionalMethod`将各个方法标记为事务性的。或者用`@TransactionalObject`标记类，以使其所有方法都是事务性的。为了与其他JVM语言集成，Multiverse提供了一组丰富的api来控制事务何时开始和结束。

Akka由Jonas Boner创建，是一个基于scala的解决方案，可以在包括Java在内的许多不同JVM语言中使用。Akka既提供了基于actor的并发性，也提供了STM，还提供了将二者混合使用的选项。Akka将`Multiverse`用于STM实现，并提供ACI (ACID的子集)属性。

Akka提供了出色的性能，而且由于它同时支持STM和基于actor的模型(在第8章第163页讨论了独立的可变性)，我们将在本章中使用它来演示Java STM示例

**Transactions in Akka/Multiverse**

Akka使用Multiverse的clojure风格的STM来编写Java代码。除了java引入的冗长之外，Akka与Clojure的主要区别在于，Akka不会强迫我们在修改可变标识之前创建事务。如果我们不提供事务，Akka/Multiverse会自动将访问包装在事务中。所以，当我们在一个交易中，Akka refs的行为就像Clojure refs，而当我们不在一个交易中，它们就像Clojure原子。换句话说，启动一个事务来协调同步更改;否则我们会得到不协调的同步变化。

在任何情况下，Akka确保对refs的更改仍然是原子的、隔离的和一致的，同时提供不同程度的协调粒度。在Akka中，我们可以在事务级别或在应用程序/JVM级别使用配置文件以编程方式配置事务。我们可以将事务定义为只读的，并且Akka不允许对该事务范围内的任何Akka引用进行任何更改。我们可以通过将非可变事务设置为只读来提高性能。我们可以控制发生冲突时Akka重试事务的最大次数。在那里我们可以配置的其他参数有很多，请参阅Akka文档了解详细信息。Akka扩展了嵌套事务(参见第111页的6.9节创建嵌套事务)对`Multiverse`的支持，因此从事务内部，我们可以方便地调用启动事务的方法。默认情况下，这些内部或嵌套事务被滚入外部事务。

**Using Akka Refs and Transactions**

与Clojure不同的是，使用Akka ref和事务(在Clojure中ref是在语言级别定义的)，Akka不能依赖任何现有的语言支持。相反，Akka提供，作为Akka的一部分。akka.stm包，托管事务引用ref<T>以及用于基本类型的专门化类，如IntRef、LongRef等。Ref&lt; T&gt;(以及专门的引用)将托管的可变标识表示为t类型的不可变值。类型如Integer、Long、Double、String和其他不可变类型很适合作为值对象。如果我们使用自己的一个类，我们必须确保它是不可变的，也就是说，它只包含final字段。通过提供初始值或省略默认为null的值，我们可以创建一个托管事务引用，它是Ref&lt;T&gt;的实例。要从引用中获取当前值，请使用get()方法。要将可变标识更改为指向新值，请使用swap()方法。这些调用在我们提供的事务中执行，如果没有提供，则在它们自己的单独事务中执行。当多个线程尝试更改相同的托管引用时，Akka确保将其中一个线程的更改写入内存，并重试其他线程。事务性工具负责跨越内存屏障。也就是说，通过Multiverse, Akka保证由事务提交的对托管ref的更改在其他事务中稍后对相同ref的任何读取都是可见的。

##### 6.8 Creating Transactions

我们创建事务来协调对多个托管引用的更改。事务将确保这些更改是原子性的;也就是说，所有托管引用都被提交或者全部被丢弃。在事务之外，我们将不会看到任何局部更改。我们还创建事务来协调对单个ref的读和写。Akka构建在Scala之上，如果我们使用Scala，我们可以享受它简洁的API。

对于不能进行转换的程序员，Akka还提供了一个方便的API使用Java语言的特性。或者，我们可以直接使用来自Java的Multiverse STM。在本节中，我们将看到如何使用Java和Scala中的Akka创建事务。首先，我们需要一个应用事务的示例。我们在第5章“驯服共享的可变性”中重构的`EnergySource`类，在第73页使用了显式的lock和unlock最终版本在第57节“确保原子性”中，在第82页)。让我们用这些显式的锁/解锁来交换Akka的事务API。

**Creating Transactions in Java**

支持事务，扩展原子类并将代码放到类的atomically()方法中，以将其包装到事务中。要运行事务代码，调用原子实例的execute()方法，如下所示:

```scala
return new Atomic<Object>() {
 public Object atomically() {
//code to run in a transaction... 
     return resultObject;
}
}.execute();
```

调用execute()方法的线程运行atomically()方法中的代码。但是，如果调用者尚未在事务中，则调用被包装在事务中。让我们使用Akka事务来实现`EnergySource`。作为第一步，让我们将不可变状态包装在可变Akka托管引用中。

```java
public class EnergySource {
private final long MAXLEVEL = 100;
final Ref<Long> level = new Ref<Long>(MAXLEVEL);
final Ref<Long> usageCount = new Ref<Long>(0L);
final Ref<Boolean> keepRunning = new Ref<Boolean>(true);
private static final ScheduledExecutorService replenishTimer =
Executors.newScheduledThreadPool(10);
```

level和usageCount被声明为Akka ref，它们中的每一个都持有一个不可变的长值。我们不能在Java中更改Long的值，但是我们可以安全地更改托管引用(标识)以指向一个新值。在`EnergySource`的前一个版本中，`ScheduledExecutorService`用于每秒钟定期运行一次补充()方法，直到它被取消。请记住，这需要同步`stopEnergySource()`方法。在这个版本中，我们没有定期运行该方法，而是进行调度在开始的时候只有一次。在每次调用时，我们根据`keepRunning`标志的值来决定是否应该再次调度该方法，以便稍后执行。这一更改消除了`stopEnergySource()`方法与调度器/计时器之间的耦合。相反，此方法现在依赖于标志，可以使用STM事务轻松地管理标志。我们从以前的版本中继承创建E`nergySource`实例的代码，并修改它以使用`keepRunning`标志的托管引用。在这个版本中，不需要同步`stopEnergySource()`方法，因为它依赖于事务。`swap()`方法在它自己的事务中运行就足够了，因此我们不必创建显式事务。

```java
private EnergySource() {}
private void init() {
replenishTimer.schedule(new Runnable() {
public void run() {
replenish();
if (keepRunning.get()) replenishTimer.schedule(
this, 1, TimeUnit.SECONDS);
}
}, 1, TimeUnit.SECONDS);
}
public static EnergySource create() {
final EnergySource energySource = new EnergySource();
energySource.init();
return energySource;
}
public void stopEnergySource() { keepRunning.swap(false); }
```

返回当前能量级别和使用计数的方法将使用托管引用，但这只需要调用get()方法

```java
public long getUnitsAvailable() { return level.get(); }
public long getUsageCount() { return usageCount.get(); }
```

在`getUnitsAvailable()`和`getUsageCount()`方法中，对get()的调用在它们自己的事务中运行，因为我们没有显式地将它们包装在事务中。`useEnergy()`方法将需要一个显式事务，因为我们将在这里更改能量级别和使用计数;我们需要确保更改与读取的值一致，并确保对这两个字段的更改是原子性的。我们将使用原子接口及其`atomically()`方法在事务中包装我们的代码。

```java
public boolean useEnergy(final long units) {
return new Atomic<Boolean>() {
public Boolean atomically() {
long currentLevel = level.get();
if(units > 0 && currentLevel >= units) {
level.swap(currentLevel - units);
usageCount.swap(usageCount.get() + 1);
return true;
} else {
return false;
}
}
}.execute();
}
```

在`useEnergy()`方法中，我们从当前级别递减。我们希望确保get和set都在同一个事务中。因此，我们将操作包装在atomically()方法中。最后调用execute()在一个事务中运行操作序列。我们还需要处理另一个方法，即补充()，它负责重新填充源。在这个方法中我们也有相同的事务需求，因此我们也将在其中使用Atomic。

```java
private void replenish() {
new Atomic() {
public Object atomically() {
long currentLevel = level.get();
if (currentLevel < MAXLEVEL) level.swap(currentLevel + 1);
return null;
}
}.execute();
}
}
```

让我们使用一个示例runner代码来练习EnergySource。这段代码同时使用来自不同线程的能量，单位为1。

```java
public class UseEnergySource {
private static final EnergySource energySource = EnergySource.create();
public static void main(final String[] args)
throws InterruptedException, ExecutionException {
System.out.println("Energy level at start: " +
energySource.getUnitsAvailable());
List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
    for(int i = 0; i < 10; i++) {
tasks.add(new Callable<Object>() {
public Object call() {
for(int j = 0; j < 7; j++) energySource.useEnergy(1);
return null;
}
});
}
final ExecutorService service = Executors.newFixedThreadPool(10);
service.invokeAll(tasks);
    System.out.println("Energy level at end: " +
energySource.getUnitsAvailable());
System.out.println("Usage: " + energySource.getUsageCount());
energySource.stopEnergySource();
service.shutdown();
}
}
```

要编译和运行代码，我们需要包括akka相关的jar，如下所示。

> export AKKA_JARS="$AKKA_HOME/lib/scala-library.jar:\
> $AKKA_HOME/lib/akka/akka-stm-1.1.3.jar:\
> $AKKA_HOME/lib/akka/akka-actor-1.1.3.jar:\
> $AKKA_HOME/lib/akka/multiverse-alpha-0.6.2.jar:\
> $AKKA_HOME/config:\
> ."

根据您的操作系统和位置定义类路径

Akka已安装在您的系统上。使用javac编译器编译代码并使用java命令运行它，如下所示:

> javac -classpath $AKKA_JARS -d . EnergySource.java UseEnergySource.java
> java -classpath $AKKA_JARS com.agiledeveloper.pcj.UseEnergySource

继续，编译并运行代码。一开始能量源有100个单元，我们从创建的不同线程中抽取了70个单元。最终结果应该是剩下30单位的能量。根据补充的时间不同，可能会出现不同的值，比如31而不是30。

> Energy level at start: 100
> Energy level at end: 30
> Usage: 70

默认情况下，Akka在标准输出中打印额外的日志消息。方法中简单地创建一个名为logback.xml的文件，即可使这些消息保持沉默$AKKA_HOME/config目录，只有元素&lt;configuration /&gt;在里面。由于此目录位于类路径中，因此日志记录器知道将消息静默。除了关闭日志之外，我们还可以使用有用的选项对其进行配置。有关详细信息，请参阅http://logback.qos.ch/manual/configuration.html上的说明。正如我们在这里看到的，Akka悄悄地在幕后管理事务。花些时间在代码上，并对事务和线程进行试验。

我们看到了如何在Java中创建事务(我假设您已经阅读了那部分，所以我们不必在这里重复细节)。在Scala中，我们需要更少的代码来编写相同的内容，这部分是因为Scala简洁的特性，也因为优雅的Akka API使用了闭包/函数值。与Java相比，用Scala创建事务花费的精力要少得多。我们所需要的是调用Stm的atomic()方法，像这样:

>atomic {
>
>//code to run in a transaction....
>
>/* return */ resultObject
>
>}

传递给atomic()的闭包/函数值在当前线程中运行，但在事务中运行。

下面是使用Akka事务的Scala版本的

```java
class EnergySource private() {
private val MAXLEVEL = 100L
val level = Ref(MAXLEVEL)
val usageCount = Ref(0L)
val keepRunning = Ref(true)
private def init() = {
EnergySource.replenishTimer.schedule(new Runnable() {
def run() = {
replenish
if (keepRunning.get) EnergySource.replenishTimer.schedule(
this, 1, TimeUnit.SECONDS)
}
}, 1, TimeUnit.SECONDS)
}
def stopEnergySource() = keepRunning.swap(false)
    def getUnitsAvailable() = level.get
def getUsageCount() = usageCount.get
def useEnergy(units : Long) = {
atomic {
val currentLevel = level.get
if(units > 0 && currentLevel >= units) {
level.swap(currentLevel - units)
usageCount.swap(usageCount.get + 1)
true
} else false
}
}
private def replenish() =
atomic { if(level.get < MAXLEVEL) level.swap(level.get + 1) }
}
object EnergySource {
val replenishTimer = Executors.newScheduledThreadPool(10)
def create() = {
val energySource = new EnergySource
energySource.init
energySource
}
}
```

作为一种完全面向对象的语言，Scala将类中的静态方法视为异常。因此，工厂方法create()被移动到同伴对象。代码的其余部分与Java版本非常相似，但是比较起来比较简洁。通过优雅的Atomic()方法调用，我们避免了原子类的仪式和execute()方法调用。下面显示执行Scala版本的EnergySource的代码。我们可以使用JDK ExecutorService来像Java版本中那样管理线程。或者，我们可以使用Scala actors2为每个并发任务派生线程。每个任务在完成时向调用者发送一个响应。调用者只是使用此响应阻塞，等待任务完成后再继续。

```java
object UseEnergySource {
val energySource = EnergySource.create()
def main(args : Array[String]) {
    println("Energy level at start: " + energySource.getUnitsAvailable())
val caller = self
for(i <- 1 to 10) actor {
for(j <- 1 to 7) energySource.useEnergy(1)
caller ! true
}
for(i <- 1 to 10) { receiveWithin(1000) { case message => } }
println("Energy level at end: " + energySource.getUnitsAvailable())
println("Usage: " + energySource.getUsageCount())
energySource.stopEnergySource()
}
}
```

使用与akka相关的jar编译并运行代码，如下所示，其中akka_jar与Java示例中定义的相同:

scalac -classpath $AKKA_JARS *.scala
java -classpath $AKKA_JARS com.agiledeveloper.pcj.UseEnergySource

输出应该与我们看到的Java版本没有什么不同;同样，根据补充的时间，有一个稍微不同的值

31而不是30可能会出现。

> Energy level at start: 100
> Energy level at end: 30
> Usage: 70

##### Creating Nested Transactions

我们调用的方法可以创建它们自己的事务，它们的更改将得到独立的提交。如果我们想要将这些方法中的事务协调成一个原子操作，这是不够的。我们可以通过嵌套事务实现这种协调。

在嵌套事务中，由我们调用的方法创建的所有事务在默认情况下都会滚入调用方法的事务中。使用Akka/Multiverse提供了配置其他选项的方法，比如新的隔离事务。因此，对于嵌套事务，只有在最外层事务提交时才提交所有更改。我们的职责是确保方法在可配置的超时时间内完成，从而使整个嵌套事务成功。第67页锁接口中的AccountService及其transfer()方法将受益于嵌套事务。transfer()的前一个版本方法必须按自然顺序对帐户排序并显式管理锁。STM消除了所有这些负担。让我们先在Java的例子中使用嵌套事务，然后看看在Scala中会是什么样子。Java中的嵌套事务让我们首先将Account类引入事务领域。帐户余额应该是一个托管引用，所以让我们从定义该字段和它的getter开始。

```java
public class Account {
final private Ref<Integer> balance = new Ref<Integer>();
public Account(int initialBalance) {
    balance.swap(initialBalance); 
}
public int getBalance() { return balance.get(); }
```

在构造函数中，我们通过调用ref上的swap()方法将初始余额设置为给定的值。这个操作将在它自己的事务中运行，因为我们没有提供一个事务(我们假设调用者也没有提供一个)。类似地，`getBalance()`将在其自己的事务中访问余额。整个`deposit()`方法必须在一个事务中运行，因为它涉及到读取和更改余额。因此，让我们把这两个操作封装到一个单独的事务中。

```java
public void deposit(final int amount) {
new Atomic<Boolean>() {
public Boolean atomically() {
System.out.println("Deposit " + amount);
if (amount > 0) {
balance.swap(balance.get() + amount);
return true;
}
throw new AccountOperationFailedException();
}
}.execute();
}
```

同样，withdraw()中的操作应该包装到单独的事务中。

```java
public void withdraw(final int amount) {
new Atomic<Boolean>() {
public Boolean atomically() {
	int currentBalance = balance.get();
    if (amount > 0 && currentBalance >= amount) {
	balance.swap(currentBalance - amount);
return true;
}
throw new AccountOperationFailedException();
}
}.execute();
}
}
```

事务在异常时被迫失败，因此我们使用此来指示当没有足够的余额可用或存款/取款金额无效时的失败。很简单,不是吗?不需要担心同步、锁定、死锁等问题。现在访问将要执行转移的`AccountService`类。让我们先看看转移方法

```java
public class AccountService {
public void transfer(
final Account from, final Account to, final int amount) {
new Atomic<Boolean>() {
public Boolean atomically() {
System.out.println("Attempting transfer...");
to.deposit(amount);
System.out.println("Simulating a delay in transfer...");
try { Thread.sleep(5000); } catch(Exception ex) {}
System.out.println("Uncommitted balance after deposit $" +
to.getBalance());
from.withdraw(amount);
return true;
}
}.execute();
}
```

在本例中，我们将在研究嵌套事务时将事务设置为冲突进程，以说明它们的行为。transfer()方法中的操作绑定在一个事务中。作为转帐的一部分，我们首先把钱存入目标账户。然后，在造成交易冲突的延迟之后，我们从源账户提款。当且仅当从源取款成功时，我们希望向目标账户的存款成功，这就是我们交易的任务。通过打印余额，可以看到转账是否成功。调用transfer()、处理异常并最终打印余额的方便方法会很好，所以让我们编写一个

```java
public static void transferAndPrintBalance(
final Account from, final Account to, final int amount) {
boolean result = true;
try {
new AccountService().transfer(from, to, amount);
} catch(AccountOperationFailedException ex) {
result = false;
}
System.out.println("Result of transfer is " + (result ? "Pass" : "Fail"));
System.out.println("From account has $" + from.getBalance());
System.out.println("To account has $" + to.getBalance());
}
```

我们需要的最后一个方法是main()来滚动传输:

```java
public static void main(final String[] args) throws Exception {
final Account account1 = new Account(2000);
final Account account2 = new Account(100);
final ExecutorService service = Executors.newSingleThreadExecutor();
service.submit(new Runnable() {
public void run() {
try { Thread.sleep(1000); } catch(Exception ex) {}
account2.deposit(20);
}
});
service.shutdown();
transferAndPrintBalance(account1, account2, 500);
System.out.println("Making large transfer...");
transferAndPrintBalance(account1, account2, 5000);
}
}
```

在main()方法中，我们创建了两个帐户，并在一个单独的线程中启动了$20的存款。与此同时，我们要求资金在账户之间转账。这将使两个事务发生冲突，因为它们都影响一个公共实例。其中一个会成功，另一个会重试。最后，在最后，我们尝试转移金额超过可用余额。这将说明存款和取款的两个事务不是独立的，而是嵌套的，并且在transfer s事务中是原子的。存款的效果应因取款失败而逆转。让我们看看输出中这些事务的行为。

> Attempting transfer...
> Deposit 500
> Attempting transfer...
> Deposit 500
> Simulating a delay in transfer...
> Deposit 20
> Uncommitted balance after deposit $600
> Attempting transfer...
> Deposit 500
> Simulating a delay in transfer...
> Uncommitted balance after deposit $620
> Result of transfer is Pass
> From account has $1500
> To account has $620
> Making large transfer...
> Attempting transfer...
> Deposit 5000
> Simulating a delay in transfer...
> Uncommitted balance after deposit $5620
> Result of transfer is Fail
> From account has $1500
> To account has $620

转会交易一开始就被重试，这有点奇怪。这种意外的重试是因为`Multiverse`对单个对象上的只读事务进行了默认优化(投机配置)。有一些方法可以配置这种行为，但是这会影响性能。请参考`Akka/Multiverse`文档以了解更改它的后果。20美元的定金先成功了。当发生这种情况时，传输事务处于其模拟延迟的中间。一旦事务意识到它管理的对象在背后发生了更改，它就会悄悄地回滚并开始另一次尝试。重试将继续，直到成功或超过超时。这一次，转账交易成功了。这两笔交易的净结果反映在所显示的余额中，第一个帐户在转帐中损失了500美元，而第二个帐户从同时进行的存款和转帐活动中共获得了520美元。我们的下一个也是最后一个尝试是转账5000美元。存款已经完成，但交易中的变化被扣留，以检查取款的命运。但是，由于余额不足，提款失败。这将导致挂起的存款事务回滚，使余额不受最终转移尝试的影响。

打印消息和延迟当然不是好的做法，但我在这里使用它们，以便我们可以看到事务序列和重试避免在实际代码中进行这类打印或登录。记住，事务应该没有副作用。将任何具有副作用的代码委托给post-commit处理程序，我们将在后面讨论。我承诺这些交易会减轻我们的负担，所以让我们来看看到底有多少。为了方便起见，请查看第67页中Lock接口的transfer()方法.

```java
public boolean transfer(
final Account from, final Account to, final int amount)
throws LockException, InterruptedException {
final Account[] accounts = new Account[] {from, to};
Arrays.sort(accounts);
if(accounts[0].monitor.tryLock(1, TimeUnit.SECONDS)) {
try {
if (accounts[1].monitor.tryLock(1, TimeUnit.SECONDS)) {
try {
if(from.withdraw(amount)) {
to.deposit(amount);
return true;
} else {
return false;
}
} finally {
accounts[1].monitor.unlock();
}
}
} finally {
accounts[0].monitor.unlock();
}
}
throw new LockException("Unable to acquire locks on the accounts");
}
```

将该代码与最新版本进行对比，但首先去掉延迟和打印语句:

```java
public void transfer(
final Account from, final Account to, final int amount) {
new Atomic<Boolean>() {
public Boolean atomically() {
to.deposit(amount);
from.withdraw(amount);
return true;
}
}.execute();
}
```

在旧版本中有太多的顺序和锁定的舞蹈，很容易搞砸。毫无疑问，我们不编写的代码bug最少。在新版本中，我们减少了代码和复杂性。这让我想起了C.A.R. Hoare的话:有两种方法来构建软件设计。一种方法是让它简单到没有明显的缺陷。另一种方法是把它变得非常复杂以至于没有明显的缺陷。更少的代码，更少的复杂性和更多的时间用于应用程序逻辑。Java版本的使用嵌套事务的传输方法已经很简洁了。当然，嵌套事务消除了同步的麻烦，但是仍然有一些Java语法可以使用清理。Scala的表现力和优雅将在其中发挥作用，我们接下来将看到:这是Account类的Scala版本

```java
class Account(val initialBalance : Int) {
val balance = Ref(initialBalance)
def getBalance() = balance.get()
def deposit(amount : Int) = {
atomic {
println("Deposit " + amount)
if(amount > 0)
balance.swap(balance.get() + amount)
else
throw new AccountOperationFailedException()
}
}
def withdraw(amount : Int) = {
atomic {
val currentBalance = balance.get()
if(amount > 0 && currentBalance >= amount)
balance.swap(currentBalance - amount)
else
throw new AccountOperationFailedException()
}
}
}
```

Account的Scala版本是从Java版本直接转换而来的

Scala和Akka的简洁。我们在Scala版本的AccountService中看到了同样的优势

```java
object AccountService {
def transfer(from : Account, to : Account, amount : Int) = {
atomic {
println("Attempting transfer...")
to.deposit(amount)
println("Simulating a delay in transfer...")
Thread.sleep(5000)
println("Uncommitted balance after deposit $" + to.getBalance())
from.withdraw(amount)
}
}
def transferAndPrintBalance(
from : Account, to : Account, amount : Int) = {
var result = "Pass"
try {
AccountService.transfer(from, to, amount)
} catch {
case ex => result = "Fail"
}
println("Result of transfer is " + result)
println("From account has $" + from.getBalance())
println("To account has $" + to.getBalance())
}
def main(args : Array[String]) = {
val account1 = new Account(2000)
val account2 = new Account(100)
actor {
Thread.sleep(1000)
account2.deposit(20)
}
transferAndPrintBalance(account1, account2, 500)
println("Making large transfer...")
transferAndPrintBalance(account1, account2, 5000)
}
}
```

这个版本将事务设置为冲突过程，就像Java版本一样。不出所料，输出结果与Java版本相同:

> Attempting transfer...
> Deposit 500
> Attempting transfer...
> Deposit 500
> Simulating a delay in transfer...

> Deposit 20
> Uncommitted balance after deposit $600
> Attempting transfer...
> Deposit 500
> Simulating a delay in transfer...
> Uncommitted balance after deposit $620
> Result of transfer is Pass
> From account has $1500
> To account has $620
> Making large transfer...
> Attempting transfer...
> Deposit 5000
> Simulating a delay in transfer...
> Uncommitted balance after deposit $5620
> Result of transfer is Fail
> From account has $1500
> To account has $620

我们已经比较了同步版本的传输和Java版本使用嵌套事务(下面将重复):

````java
public void transfer(
final Account from, final Account to, final int amount) {
new Atomic<Boolean>() {
public Boolean atomically() {
to.deposit(amount);
from.withdraw(amount);
return true;
}
}.execute();
}
````

现在将它与Scala版本进行比较:

```java
def transfer(from : Account, to : Account, amount : Int) = {
atomic {
to.deposit(amount)
from.withdraw(amount)
}
}
```

它只是最基本的代码。这让我想起Alan Perlis的一句话:当程序需要关注无关的东西时，编程语言就是低水平的。我们知道如何在Akka中创建事务，以及如何组合嵌套事务。这仅仅是个开始;Akka允许我们配置事务，我们将在下面看到。

##### Configuring Akka Transactions

Akka假定有许多默认设置，但它允许我们通过编程或通过配置文件Akka .conf更改这些设置。有关如何指定或更改配置文件位置的详细信息，请参阅Akka文档。我们可以使用aTr ansacti onFact或y以编程方式更改每个事务的设置。让我们分别从Java和Scala通过编程方式更改一些设置。

**Configuring Transactions in Java**

我们扩展了原子来实现Java中的事务。我们可以提供typeTr ansacti onFact或y的可选构造函数参数来更改事务属性。例如，我们可以将事务设置为readonly以获得性能，并通过配置事务防止对任何引用的更改。让我们创建一个名为CoffeePot的类，它存放许多杯咖啡，并尝试在只读事务中对其进行操作

```java
public class CoffeePot {
private static final Ref<Integer> cups = new Ref<Integer>(24);
public static int readWriteCups(final boolean write) {
final TransactionFactory factory =
new TransactionFactoryBuilder().setReadonly(true).build();
return new Atomic<Integer>(factory) {
public Integer atomically() {
if(write) cups.swap(20);
return cups.get();
}
}.execute();
}
```

要以编程方式设置事务配置，我们需要aTransaction - Fact或y。the tr ansacti onFact或yBuil der提供了方便的方法来创建工厂。我们创建了Tr ansacti onFact或yBuil der实例，使用setReadonly()方法配置Tr ansacti onFact或y的readonly选项。Tr ansacti onFact或yBuil der实现了级联设计模式，因此在调用build()方法之前，我们可以为希望设置的其他属性链接更多方法。将此工厂实例作为参数发送给构造函数

，并且我们再次保证事务内的任何操作都不能改变任何托管引用。我们的事务是只读的，但是让我们看看如果我们尝试修改引用会发生什么。因此，我们将调用readWriteCups()一次来读取引用，然后第二次来更改它。

```java
public static void main(final String[] args) {
System.out.println("Read only");
readWriteCups(false);
System.out.println("Attempt to write");
try {
readWriteCups(true);
} catch(Exception ex) {
System.out.println("Failed " + ex);
}
}
}
```

事务会对变更请求感到不高兴;在尝试更改时，它将抛出org.multiver .api.exceptions。ReadonlyException异常，事务将回滚

> Read only
> Attempt to write
> Failed org.multiverse.api.exceptions.ReadonlyException:
> Can't open for write transactional object 'akka.stm.Ref@1272670619'
> because transaction 'DefaultTransaction' is readonly'

对swap()的调用引发了运行时异常。只有当新值与当前值不同时，此方法才会修改ref保存的值;否则，它将忽略更改请求。因此，在本例中，如果我们将cups ref的当前值设置为24，而不是20，我们将不会得到任何异常

**Configuring Transactions in Scala**

在Scala中，我们使用atomic()方法而不是原子类来创建事务。该方法采用typeTr ansacti onFac - tory的可选参数。在Scala中创建工厂的实例也容易得多，因为我们可以在同伴对象上使用工厂方法。

```java
object CoffeePot {
val cups = Ref(24)
def readWriteCups(write : Boolean) = {
    val factory = TransactionFactory(readonly = true)
atomic(factory) {
if(write) cups.swap(20)
cups.get()
}
}
def main(args : Array[String]) : Unit = {
println("Read only")
readWriteCups(false)
println("Attempt to write")
try {
readWriteCups(true)
} catch {
case ex => println("Failed " + ex)
}
}
}
```

除了Scala和Akka的简易性之外，这里的Scala和Java版本之间没有太大区别，代码的行为应该与Java版本一样:只读尝试编写失败的org.multiver .api.exception。ReadonlyException:写事务对象的akka.stm无法打开。因为事务'DefaultTransaction'是只读的' 

##### 6.11 Blocking Transactions—Sensible Wait

一个事务可能取决于一个变量，预期会改变，并且事务的失败可能是暂时的。作为对该失败的响应，我们可能会返回一个错误代码，并要求事务在延迟之后重试。但是，在另一个任务更改了相关数据之前，没有必要重复请求。Akka为我们提供了一个简单的工具重试()，它将回滚并阻塞事务，直到事务所依赖的一个引用对象被更改或超过了可配置的阻塞超时。我喜欢称这为合理的等待，因为这听起来比阻塞好。让我们把阻塞，我的意思是合理的等待，先在Java的例子中使用，然后在Scala中使用。Java程序员中的阻塞事务通常对咖啡因上瘾，所以任何自愿去买咖啡的人都知道不要拿空杯子回来。而不是忙碌

在Akka的帮助下，他可以将自己置于“随时通知”模式，直到咖啡壶被重新装满。让我们写一个fillCup()方法与明智的等待使用重试():

```java
public class CoffeePot {
private static final long start = System.nanoTime();
private static final Ref<Integer> cups = new Ref<Integer>(24);
private static void fillCup(final int numberOfCups) {
final TransactionFactory factory =
new TransactionFactoryBuilder()
.setBlockingAllowed(true)
.setTimeout(new DurationInt(6).seconds())
.build();
new Atomic<Object>(factory) {
public Object atomically() {
if(cups.get() < numberOfCups) {
System.out.println("retry........ at " +
(System.nanoTime() - start)/1.0e9);
retry();
}
cups.swap(cups.get() - numberOfCups);
System.out.println("filled up...." + numberOfCups);
System.out.println("........ at " +
(System.nanoTime() - start)/1.0e9);
return null;
}
}.execute();
}
```

在fillCup()方法中，我们将事务配置为blockingallow = true，并设置了6秒的超时来完成事务。当fillCup()方法发现咖啡不够时，它不会返回错误代码，而是调用StmUtils的retry()方法。这将阻塞当前事务，直到参与的引用杯被更改。一旦任何参与的引用更改，包含重试的原子代码就会重试，从而启动另一个事务。让我们调用fillCup()方法来查看重试()的效果

```java
public static void main(final String[] args) {
final Timer timer = new Timer(true);
timer.schedule(new TimerTask() {
public void run() {
System.out.println("Refilling.... at " +
(System.nanoTime() - start)/1.0e9);
    }, 5000);
fillCup(20);
fillCup(10);
try {
fillCup(22);
} catch(Exception ex) {
System.out.println("Failed: " + ex.getMessage());
}
}
}
```

在main()中，我们启动一个计时器，它将在大约5秒内重新填充咖啡壶。第一个进去的那个人立刻喝了二十杯咖啡。当我们的志愿者去拿10个杯子时，他被挡住了，无法再续杯。这种等待比繁忙的编程重试操作更有效。一旦refill事务完成，他的请求将自动再次尝试，并且这次成功。如果在我们配置的超时中没有重新填充，事务将像try块中的最后一个请求一样失败。我们可以观察到这个行为以及重试()在输出中的好处

> filled up....20
> ........ at 0.423589
> retry........ at 0.425385
> retry........ at 0.427569
> Refilling.... at 5.130381
> filled up....10
> ........ at 5.131149
> retry........ at 5.131357
> retry........ at 5.131521
> Failed: Transaction DefaultTransaction has timed with a
> total timeout of 6000000000 ns

在比赛开始后的0.40秒内，10个杯子的加注请求就完成了。因为重新填充要在开始后5秒之后才会发生，所以这个请求被阻塞，因为调用了retry()。就在refill事务完成之后，fill-up事务被重新启动，这一次在开始后5秒后运行到完成。稍后的填充请求超时了，因为在此请求之后没有再填充。我们并不经常使用retry()。如果应用程序逻辑要求在依赖数据更改时执行某些操作，那么我们可以从监视数据更改的这个特性中获益。

> Blocking Transactions in Scala
> In the Java version, we used the convenience object StmUtils that provided a
> fluent Java interface. In Scala, we can directly use the StmUnit trait. We can
> also use the factory method to create theTr ansacti onFact or y :

```java
object CoffeePot {
val start = System.nanoTime()
val cups = Ref(24)
def fillCup(numberOfCups : Int) = {
val factory = TransactionFactory(blockingAllowed = true,
timeout = 6 seconds)
atomic(factory) {
if(cups.get() < numberOfCups) {
println("retry........ at " + (System.nanoTime() - start)/1.0e9)
retry()
}
cups.swap(cups.get() - numberOfCups)
println("filled up...." + numberOfCups)
println("........ at " + (System.nanoTime() - start)/1.0e9)
}
}
def main(args : Array[String]) : Unit = {
val timer = new Timer(true)
timer.schedule(new TimerTask() {
def run() {
println("Refilling.... at " + (System.nanoTime() - start)/1.0e9)
cups.swap(24)
}
}, 5000)
fillCup(20)
fillCup(10)
try {
fillCup(22)
} catch {
case ex => println("Failed: " + ex.getMessage())
}
}
}
```

当创建ingtr ansacti onFact或y时，为了配置超时，而不是直接使用DurationInt，我们使用intToDurationInt()方法从int进行隐式转换。这使得我们只需调用6秒，有点语法

Scala隐式转换提供的sugar。剩下的代码是简单的从Java到Scala的转换，输出如下所示:

> filled up....20
> ........ at 0.325964
> retry........ at 0.327425
> retry........ at 0.329587
> Refilling.... at 5.105191
> filled up....10
> ........ at 5.106074
> retry........ at 5.106296
> retry........ at 5.106466
> Failed: Transaction DefaultTransaction has timed with a
> total timeout of 6000000000 ns

##### 6.12 Commit and Rollback Events

Java的try-catch-finally功能允许我们处理异常，并且只有在出现异常时才有选择地运行一些代码。类似地，我们可以决定只有在事务提交时才运行一段代码，只有在事务回滚时才运行另一段代码。在StmUtils上，它们分别作为deferred()和compensation()方法提供。deferred()方法是执行所有为确保事务完成而延迟的副作用的好地方。在Java中，我们将事务成功时希望运行的代码放在传递给StmUtils的deferred()方法的代码块(Runnabl e)中。同样，我们将在事务失败时将希望运行的代码放在传递给compensation()方法的代码块中。因为这些方法必须在事务的上下文中运行，所以我们必须在atomically()方法的主体中调用它们。

```java
public class Counter {
private final Ref<Integer> value = new Ref<Integer>(1);
public void decrement() {
new Atomic<Integer>() {
public Integer atomically() {
deferred(new Runnable() {
public void run() {
System.out.println(
"Transaction completed...send email, log, etc.");
}
});
    compensating(new Runnable() {
public void run() {
System.out.println("Transaction aborted...hold the phone");
}
});
if(value.get() <= 0)
throw new RuntimeException("Operation not allowed");
value.swap(value.get() - 1);
return value.get();
}
}.execute();
}
}
```

Counter类有一个名为decrement()的实例方法。在这个方法中，我们从类Atomic扩展并实现所需的atomically()方法。

在前面的示例中，我们简单地编写了在事务中运行的代码。除此之外，我们还提供了在事务成功和事务回滚时运行的代码。让我们创建一个示例代码来尝试计数器:

```java
package com.agiledeveloper.pcj;
public class UseCounter {
public static void main(final String[] args) {
Counter counter = new Counter();
counter.decrement();
System.out.println("Let's try again...");
try {
counter.decrement();
} catch(Exception ex) {
System.out.println(ex.getMessage());
}
}
}
```

练习UseCounter，查看与事务成功和失败相关的代码块的运行情况

> Transaction aborted...hold the phone
> Transaction completed...send email, log, etc.
> Let's try again...
> Transaction aborted...hold the phone
> Operation not allowed

当事务在第一次调用decrement()时完成时，将调用提供给deferred()方法的代码块。在对decrement()的第二个调用中，一旦遇到异常，就回滚事务，并调用提供给compensation()方法的代码块。我们还看到了由于顶部意外重试导致的回滚，这是由于我们在前面的Java嵌套事务(在第112页)中讨论过的投机性配置导致的。deferred()处理程序是完成工作活动的好地方，可以使动作永久保存。因此，从这里可以随意打印、显示消息、发送通知、提交数据库事务，等等。如果我们想留下点什么，这里就是最好的去处。补偿()处理程序是记录故障的好地方。如果我们混淆了任何非托管对象(那些没有使用Akka Ref进行控制的对象)，那么这里就是恢复操作的地方，但是最好避免这种设计，因为它很混乱，而且容易出错。我们可以在Scala中处理提交和回滚事件，就像我们在Java中所做的那样，只是我们可以直接将闭包/函数值传递给deferred()和compensation()方法。让我们将Counter类从Java转换为Scala。

```java
class Counter {
private val value = Ref(1)
def decrement() = {
atomic {
deferred { println("Transaction completed...send email, log, etc.") }
compensating { println("Transaction aborted...hold the phone") }
if(value.get() <= 0)
throw new RuntimeException("Operation not allowed")
value.swap(value.get() - 1)
value.get()
}
}
}
```

事务成功时要运行的代码放在传递给deferred()方法的闭包中。同样，事务回滚时要运行的代码作为闭包呈现给compensation()方法。这两个对象被放在呈现给atomic()方法的闭包中，以及事务代码。同样，它非常简单和简明。让我们把UseCounter类从Java转换成Scala:

```java
package com.agiledeveloper.pcj
object UseCounter {
def main(args : Array[String]) : Unit = {
val counter = new Counter()
counter.decrement()
println("Let's try again...")
try {
counter.decrement()
} catch {
case ex => println(ex.getMessage())
}
}
}
```

Scala版本的输出应该和Java版本一样:

> Transaction aborted...hold the phone
> Transaction completed...send email, log, etc.
> Let's try again...
> Transaction aborted...hold the phone
> Operation not allowed

当我们在示例中工作时，很容易忘记我们正在处理的值是不变的，而且应该是不变的。只有身份是可变的，而不是状态值。尽管STM使工作变得简单，但在保持不变性的同时确保良好的性能可能是一个挑战。第一步是确保不变性。使value类为final，并将它们的所有字段标记为final (Scala中的vals)。然后传递地确保值使用的每个字段的类也是不可变的。首先应该使字段和类为final，这是避免并发性问题的第一步。尽管不变性会使代码更好、更安全，但程序员不愿使用它的一个突出原因是性能。如果没什么变化，我们就得复印了。我们在第39页的3.6节“持久/不可变数据结构”中讨论了持久数据结构以及它们如何缓解性能问题。我们可以使用第三方库或Scala中已经提供的持久数据结构。我们不必切换语言来利用这一点。我们可以直接从Java代码中使用这些持久数据结构。我们不仅想要不变性，而且还想要数据结构参与到事务中，它们的值是不可变的，但是在被管理的事务中身份会改变。Akka提供了两种被管理的数据结构:一种是区域映射，另一种是区域映射。它们的工作方式类似于Java列表和字典，但是是从高效的Scala数据结构中派生出来的。让我们来看看如何在Java和Scala中使用ansacti Map。在Java中使用来自Java的ansacti映射来使用事务集合是非常简单的。例如，让我们为不同的玩家保留分数，这些分数的更新将同时到达。我们决定在事务中处理更新，而不是处理同步和锁。下面是一些示例代码

```java
public class Scores {
final private TransactionalMap<String, Integer> scoreValues =
new TransactionalMap<String, Integer>();
final private Ref<Long> updates = new Ref<Long>(0L);
public void updateScore(final String name, final int score) {
new Atomic() {
public Object atomically() {
scoreValues.put(name, score);
updates.swap(updates.get() + 1);
if (score == 13)
throw new RuntimeException("Reject this score");
return null;
}
}.execute();
}
public Iterable<String> getNames() {
return asJavaIterable(scoreValues.keySet());
}
public long getNumberOfUpdates() { return updates.get(); }
public int getScore(final String name) {
return scoreValues.get(name).get();
}
}
```

在updateScore()方法中，我们为播放器设置分数值，并在事务中增加更新计数。typeTr ansac - tionalMap的字段scoreValue和类型Ref的更新都被管理。Tr ansactional映射支持我们在映射上期望的方法，但是这些方法是事务性的，如果事务回滚，我们对它们所做的任何更改都将被丢弃。为了查看此效果，如果得分值为13，则在进行更改后回滚事务。在Java中，如果集合实现了Iterable接口，我们可以使用for-each语句，比如for(String name: collectionOfNames)。TheTr ansacti onal Map是一个Scala集合，不直接支持该接口。不用担心，Scala提供了一个名为JavaConversions的外观，它提供了获得良好Java接口的便利方法。我们正在使用它的asJavaIterable()方法来获得getNames()方法中需要的接口。分数类准备好了;我们现在需要一个类来练习它的方法

```java
package com.agiledeveloper.pcj;
public class UseScores {
public static void main(final String[] args) {
final Scores scores = new Scores();
scores.updateScore("Joe", 14);
scores.updateScore("Sally", 15);
scores.updateScore("Bernie", 12);
System.out.println("Number of updates: " + scores.getNumberOfUpdates());
try {
scores.updateScore("Bill", 13);
} catch(Exception ex) {
System.out.println("update failed for score 13");
}
System.out.println("Number of updates: " + scores.getNumberOfUpdates());
for(String name : scores.getNames()) {
System.out.println(
String.format("Score for %s is %d", name, scores.getScore(name)));
}
}
}
```

我们先给三位玩家打分。然后我们添加另一个将导致事务回滚的分数值。最后的分数更新应该没有效果。最后，我们遍历事务性映射中的分数。让我们来观察这段代码的输出:

> Number of updates: 3
> update failed for score 13
> Number of updates: 3
> Score for Joe is 14
> Score for Bernie is 12
> Score for Sally is 15

在Scala中使用事务性集合

我们可以像在Java中那样在Scala中使用事务性集合。因为在Scala中我们将使用Scala的内部迭代器，所以我们不必使用JavaConversions facade。让我们把score类翻译成Scala:

```java
class Scores {
private val scoreValues = new TransactionalMap[String, Int]()
private val updates = Ref(0L)
def updateScore(name : String, score : Int) = {
atomic {
scoreValues.put(name, score)
updates.swap(updates.get() + 1)
if (score == 13) throw new RuntimeException("Reject this score")
}
}
def foreach(codeBlock : ((String, Int)) => Unit) =
scoreValues.foreach(codeBlock)
def getNumberOfUpdates() = updates.get()
}
```

updateScore()方法与Java版本相当。我们消除了getNames()和getScore方法，而是提供了一个foreach()内部迭代器来单步遍历分数值。的Scala版本

UseScores应该是一个直接翻译从Java版本:

```java
package com.agiledeveloper.pcj
object UseScores {
def main(args : Array[String]) : Unit = {
val scores = new Scores()
scores.updateScore("Joe", 14)
scores.updateScore("Sally", 15)
scores.updateScore("Bernie", 12)
println("Number of updates: " + scores.getNumberOfUpdates())
132 • Chapter 6. Introduction to Software Transactional Memory
report erratum • discuss
try {
scores.updateScore("Bill", 13)
} catch {
case ex => println("update failed for score 13")
}
println("Number of updates: " + scores.getNumberOfUpdates())
scores.foreach { mapEntry =>
val (name, score) = mapEntry
println("Score for " + name + " is " + score)
}
}
}
```

这个版本的输出应该与Java版本相同，正如我们所期望的:

> Number of updates: 3
> update failed for score 13
> Number of updates: 3
> Score for Joe is 14
> Score for Bernie is 12
> Score for Sally is 15

##### 6.13 Collections and Transactions收藏和交易

##### 6.14 Dealing with the Write Skew Anomaly 处理写倾斜异常

在处理写倾斜异常中，在第100页，我们讨论了写倾斜以及Clojure STM如何处理它。Akka还支持处理写倾斜，但我们必须对其进行配置。好吧，这个词可能听起来很可怕，但它真的很简单。让我们先看看没有任何配置的默认行为。让我们再次回顾前面讨论过的具有受限制组合余额的多个帐户的示例。创建一个包含支票账户余额和储蓄账户余额的组合。这两个账户的总余额必须不少于1,000美元。下面显示这个类和withdraw()方法。在这种方法中，我们首先获得两个余额，计算它们的总额，然后经过有意的延迟(引入来设置冲突过程中的交易)，如果总额不小于$1,000，我们从支票余额或储蓄余额中减去给定的金额。withdraw()方法在使用默认设置配置的事务中执行操作。

```java
public class Portfolio {
- final private Ref<Integer> checkingBalance = new Ref<Integer>(500);
- final private Ref<Integer> savingsBalance = new Ref<Integer>(600);
-
report erratum • discuss
Dealing with the Write Skew Anomaly • 133
5 public int getCheckingBalance() { return checkingBalance.get(); }
- public int getSavingsBalance() { return savingsBalance.get(); }
-
- public void withdraw(final boolean fromChecking, final int amount) {
- new Atomic<Object>() {
10 public Object atomically() {
- final int totalBalance =
- checkingBalance.get() + savingsBalance.get();
- try { Thread.sleep(1000); } catch(InterruptedException ex) {}
- if(totalBalance - amount >= 1000) {
15 if(fromChecking)
- checkingBalance.swap(checkingBalance.get() - amount);
- else
- savingsBalance.swap(savingsBalance.get() - amount);
- }
20 else
- System.out.println(
- "Sorry, can't withdraw due to constraint violation");
- return null;
- }
25 }.execute();
- }
- }
```

让我们在并发运动中设置两个交易来改变余额:

```java
public class UsePortfolio {
public static void main(final String[] args) throws InterruptedException {
final Portfolio portfolio = new Portfolio();
int checkingBalance = portfolio.getCheckingBalance();
int savingBalance = portfolio.getSavingsBalance();
System.out.println("Checking balance is " + checkingBalance);
System.out.println("Savings balance is " + savingBalance);
System.out.println("Total balance is " +
(checkingBalance + savingBalance));
final ExecutorService service = Executors.newFixedThreadPool(10);
service.execute(new Runnable() {
public void run() { portfolio.withdraw(true, 100); }
});
service.execute(new Runnable() {
public void run() { portfolio.withdraw(false, 100); }
});
service.shutdown();
Thread.sleep(4000);
checkingBalance = portfolio.getCheckingBalance();
134 • Chapter 6. Introduction to Software Transactional Memory
report erratum • discuss
savingBalance = portfolio.getSavingsBalance();
System.out.println("Checking balance is " + checkingBalance);
System.out.println("Savings balance is " + savingBalance);
System.out.println("Total balance is " +
(checkingBalance + savingBalance));
if(checkingBalance + savingBalance < 1000)
System.out.println("Oops, broke the constraint!");
}
}
```

默认情况下，Akka不会避免写倾斜，这两个事务会继续运行余额，使其违反约束，正如我们在输出中看到的:

> Checking balance is 500
> Savings balance is 600
> Total balance is 1100
> Checking balance is 400
> Savings balance is 500
> Total balance is 900
> Oops, broke the constraint!

是时候解决这个问题了。让我们求助于toTr ansacti onFact或y，它们将帮助我们以编程方式配置事务。修改Portfolio类中的第9行，以接受工厂的实例。也就是，改变这个:

```java
new Atomic<Object>() {
to the following:
akka.stm.TransactionFactory factory =
new akka.stm.TransactionFactoryBuilder()
.setWriteSkew(false)
.setTrackReads(true)
.build();
new Atomic<Object>(factory) {
```

我们创建了aTr ansacti onFact或yBuil der，并分别将writeSkew和trackReads属性设置为false和true。这告诉事务跟踪事务中的读操作，并在提交开始之前对读操作设置读锁，就像Clojure STM句柄所确保的那样。Portfolio中的其余代码和UsePortfolio中的代码保持不变。设置运行中的事务并观察输出。

> Checking balance is 500
> Savings balance is 600
> Total balance is 1100
> Sorry, can't withdraw due to constraint violation
> Checking balance is 400
> report erratum • discuss
> Dealing with the Write Skew Anomaly • 135
> Savings balance is 600
> Total balance is 1000

由于并发执行的不确定性，我们无法预测两个事务中哪一个会胜出。我们可以看到输出之间的差异，两个帐户的期末余额是不同的，而处理写倾斜异常的输出，在第100页，期末余额是相等的。当我们运行这两个示例几次时，可能会注意到它们的不同之处。前面的例子是Java;如果我们在使用Scala，我们可以使用第121页在Scala中配置事务的语法来配置事务属性writeSkew和trackReads。

##### 6.15 Limitations of STM STM的局限性

STM消除了显式同步。我们不再担心是否忘记同步或同步的级别不对。不存在无法跨越内存障碍或竞态条件的问题。我能听到你这个精明的程序员在问，有什么问题吗?

是的，STM是有局限性的，否则这本书就到此结束了。它只适用于写入冲突不频繁的情况。如果我们的应用程序有很多写争用，我们应该考虑STM之外的问题。让我们进一步讨论这个限制。STM提供了一个显式的无锁编程模型。它允许事务并发地运行，并且它们在没有冲突的情况下全部完成而不会出现故障。这同时提供了更高的并发性和线程安全性。当事务对同一对象或数据的写访问发生冲突时，允许其中一个事务完成，其他事务自动重试。重试延迟了碰撞的写入器的执行，但为读取器和获胜的写入器提供了更快的速度。当我们对同一个对象使用不频繁的并发写程序时，性能不会受到太大影响。然而，随着碰撞的增加，情况变得更糟。如果对相同数据的写冲突率很高，最好的情况是写操作速度很慢。在最坏的情况下，我们的写操作可能会因为重试太多而失败。到目前为止，我们在本章中看到的示例展示了STM的好处。尽管STM易于使用，但并非所有的使用都能产生良好的结果，我们将在下一个示例中看到这一点。为了配合使用CountDownLatch，在第56页，我们使用AtomicLong来同步多个线程探索目录时的并发更新到总文件大小。此外，我们将诉诸于使用同步

如果我们同时有一个以上的变量要改变。它看起来像是STM的一个不错的候选人，但是激烈的争论并不支持它。让我们通过修改文件大小程序来使用STM来看看这是否正确。我们将对文件大小查找器中的字段使用Akka托管引用，而不是使用AtomicLong。

```java
public class FileSizeWSTM {
private ExecutorService service;
final private Ref<Long> pendingFileVisits = new Ref<Long>(0L);
final private Ref<Long> totalSize = new Ref<Long>(0L);
final private CountDownLatch latch = new CountDownLatch(1);

```

pendingFileVisits需要在事务的安全港内递增或递减。对于AtomicLong，我们使用了一个简单的incrementAndGet()或decrementAndGet()。但是，由于托管引用是通用的，并不专门处理数字，所以我们必须付出更多的努力。如果我们把它分离到一个单独的方法中会更容易。

```java
private long updatePendingFileVisits(final int value) {
return new Atomic<Long>() {
public Long atomically() {
pendingFileVisits.swap(pendingFileVisits.get() + value);
return pendingFileVisits.get();
}
}.execute();
}
```

探索目录和查找文件大小的方法现在应该很容易实现了。从使用AtomicLong到使用托管引用是一个简单的转换:

```java
private void findTotalSizeOfFilesInDir(final File file) {
try {
if (!file.isDirectory()) {
new Atomic() {
public Object atomically() {
totalSize.swap(totalSize.get() + file.length());
return null;
}
}.execute();
} else {
final File[] children = file.listFiles();
if (children != null) {
for(final File child : children) {
    updatePendingFileVisits(1);
service.execute(new Runnable() {
public void run() {
findTotalSizeOfFilesInDir(child); }
});
}
}
}
if(updatePendingFileVisits(-1) == 0) latch.countDown();
} catch(Exception ex) {
System.out.println(ex.getMessage());
System.exit(1);
}
}
```

最后，我们需要代码来创建executor服务池并使其运行:

```java
private long getTotalSizeOfFile(final String fileName)
throws InterruptedException {
service = Executors.newFixedThreadPool(100);
updatePendingFileVisits(1);
try {
findTotalSizeOfFilesInDir(new File(fileName));
latch.await(100, TimeUnit.SECONDS);
return totalSize.get();
} finally {
service.shutdown();
}
}
public static void main(final String[] args) throws InterruptedException {
final long start = System.nanoTime();
final long total = new FileSizeWSTM().getTotalSizeOfFile(args[0]);
final long end = System.nanoTime();
System.out.println("Total Size: " + total);
System.out.println("Time taken: " + (end - start)/1.0e9);
}
}
```

我怀疑这段代码会遇到麻烦，因此在出现表示事务失败的异常时，我们终止应用程序。

如果在提交事务之前更改了值，则将自动重试事务。几个线程竞争修改这两个可变的变量，结果可能在缓慢运行的代码和彻底失败之间有所不同。继续并运行代码以探索不同的目录。我报告的输出在我的系统为/etc和/usr目录这里:

> Total file size for /etc
> Total Size: 2266408
> Time taken: 0.537082
> Total file size for /usr
> Too many retries on transaction 'DefaultTransaction', maxRetries = 1000
> Too many retries on transaction 'DefaultTransaction', maxRetries = 1000
> Too many retries on transaction 'DefaultTransaction', maxRetries = 1000
> ...

STM版本为/etc目录提供了与使用AtomicLong的早期版本相同的文件大小。但是，由于多次重试，STM版本要慢得多，大约慢了一个数量级。探索

结果发现/usr目录更糟糕——许多事务超过了默认的最大重试限制。尽管我们要求终止应用程序，但由于多个事务同时运行，在第一个故障有机会终止应用程序之前，我们可能会注意到更多的故障

您可能还记得，在第92页的6.4节“软件事务性内存”中，我们讨论了Clojure提供的用于修改托管引用的三个函数。由于不会重试事务，因此，commute提供了比alter更高的并发性;相反，它单独执行提交，而不保留调用事务。然而，对于文件大小的程序，使用通勤只能略微帮助，并且对于大的目录层次结构，它不能产生良好性能的一致结果。我们还可以尝试在交换中使用atom !方法。对atom的更改是不协调和同步的，但不需要事务。仅当我们想要更改一个变量(例如文件大小示例中的总大小)并且不会遇到任何事务性重试时，才可以使用它。但是，由于对atom的更改在幕后同步，我们仍然会遇到延迟。文件大小程序有非常高的写冲突频率，因为很多线程试图更新总大小。所以STM不适合这个问题。STM将很好地提供服务，并且当我们有非常频繁的读冲突和不太频繁的写冲突时，不需要进行同步。如果问题有巨大的写入冲突(考虑到一般应用程序中的其他延迟，这种情况很少发生)，那么不要使用STM。相反，我们可以使用actor来避免同步，正如我们将在第8章第163页中讨论的，支持隔离的可变性。



6.16概述

STM是一种非常强大的并发模型，具有许多优点:

> 提供由实际应用程序行为直接决定的最大并发性;也就是说，我们让STM动态地处理争用，而不是一个过于保守的预定义同步。
>
> 提供了显式无锁编程模型，具有良好的线程安全性和高并发性能。确保仅在事务中更改身份。缺少显式锁意味着我们不必担心锁顺序和相关问题。没有显式锁会导致无死锁的并发。减少了预先设计决策的需要，比如谁锁了什么锁;相反，我们依赖于动态隐式锁组合。该模型适合于并发读，并且对相同的数据很少发生或相当频繁的写入冲突。如果应用程序数据访问符合这种模式，STM提供了一种有效的方法来处理共享的可变性。然而，如果我们有巨大的写入冲突，我们可能会倾向于基于角色的模型，我们在第8章第163页讨论了它，它倾向于孤立的可变性。但首先，让我们在下一章中看看如何使用来自不同JVM语言的STM。

### STM in Clojure, Groovy,Java, JRuby, and Scala

JVM语言在很大程度上集成得相当好。然而，当我们跨越语言的边界时，总会遇到一些粗糙的边缘。我们有意识地根据某一种语言的优势和能力来选择它，希望它能让我们更有效率。所以，我们当然不希望这些小烦恼毁了我们的努力。如果STM是应用程序的正确选择，那么我们在JVM上使用的语言不应该阻止我们更好地使用它。STM是否真的是正确的选择取决于应用程序的数据访问模式，而不是我们用于编写它的语言。我们可以在JVM上使用来自任何语言的STM。我们有几个选择。我们可以直接使用最喜欢的语言中的Clojure STM。或者，我们可以直接或通过Akka库使用多元宇宙库。如果我们想使用Clojure STM，那就再简单不过了。Clojure STM中使用的两个关键组件ref和dosync是用于Clojure的糖衣Clojure api。ref和LockingTransaction的runInTransaction()方法。因此，当我们定义ref时，我们正在创建ref类的实例，当我们调用dosync()时，我们正在调用runInTransaction()方法。现在我们可以从Groovy、Java、JRuby和任何其他JVM语言直接访问Clojure STM。要使用Multiverse，我们可以使用它的Java API、Groovy API或JRuby集成API。或者，我们可以通过Akka库使用它。

在本章中，我们将看到如何在不同的JVM语言中使用STM。关注与你感兴趣的语言相关的部分，跳过其他部分。在我们继续之前请注意:交易应该是幂等的，没有任何副作用。这意味着我们应该确保所有的值都是不可变的，只有托管身份是可变的。这被烘焙到Clojure中;然而，如果我们正在使用其他语言，我们的责任是保证这些期望。尽管STM已经出现了一段时间，但Clojure以其革命性的身份和状态分离以及高效的持久数据结构使其备受关注。由于状态在Clojure中是完全不可变的，这使得使用STM非常容易，我们不必担心确保不变性，因为这已经是Clojure的操作方式。Clojure还不允许在事务外部对可变身份进行更改，这使得它非常安全。使用适合STM的访问模式，我们可以在具有良好并发性能和线程安全的应用程序中享受显式无锁编程。请参考第6章第89页“软件事务性内存介绍”中的示例，如果您选择的语言中有Clojure，请参考Clojure文档和书籍。如果我们正在寻找简单的元编程和动态类型，同时保留Java语义，那么Groovy是各种语言中一个很好的选择。在本节中，我们将通过Akka在Groovy应用程序中使用Clojure STM和Multiverse STM。要直接使用多元宇宙，请参考多元宇宙文档。在Groovy中使用Clojure STM与使用Clojure .lang的实例一样简单。Ref用于托管引用，并调用LockingTransaction的runInTransaction()方法来在事务中放置一段代码。让我们使用账户转账示例来尝试一下。创建一个名为Account的类来保存对其不可变状态(由变量currentBalance表示)的托管引用

```java
class Account {
final private Ref currentBalance
public Account(initialBalance) {
currentBalance = new Ref(initialBalance)
}
def getBalance() { currentBalance.deref() }
```

在构造函数中，我们用初始余额初始化引用。在getBalance()方法中，我们调用deref()方法。这相当于我们在Clojure中看到的使用@前缀的解引用。创建托管引用非常简单。现在让我们看看如何在事务中更新它。要在事务中运行一段代码，我们必须将其发送到run- InTransaction()方法。这个方法接受一个实现java.util.concurrent的实例作为参数。可调用的接口。Groovy提供了丰富的语法来实现接口，特别是只有一个方法的接口。只需在代码块中创建一个闭包，并对其调用as操作符。在幕后，Groovy将使用提供的代码块实现该接口所需的方法。让我们编写Account类的deposit()方法，该方法需要在事务中运行其操作。

```java
def deposit(amount) {
LockingTransaction.runInTransaction({
if(amount > 0) {
currentBalance.set(currentBalance.deref() + amount)
println "deposit ${amount}... will it stay"
} else {
throw new RuntimeException("Operation invalid")
}
} as Callable)
}
```

如果金额大于0，存款将修改交易中的当前余额。否则，它会通过抛出异常导致事务失败。实现withdraw()没有太大不同;我们只是有一个额外的检查要执行

```java
def withdraw(amount) {
LockingTransaction.runInTransaction({
if(amount > 0 && currentBalance.deref() >= amount)
currentBalance.set(currentBalance.deref() - amount)
else
throw new RuntimeException("Operation invalid")
} as Callable)
}
}
```

这就是Account类中的所有代码。让我们编写一个单独的函数来执行传输，这样我们就可以看到嵌套事务的实际操作。在转帐()法中，先存钱，然后取款。这样我们就可以看到取款失败时存款被放弃的影响。

```java
def transfer(from, to, amount) {
LockingTransaction.runInTransaction({
to.deposit(amount)
from.withdraw(amount)
} as Callable)
}
```

现在是执行所有代码的时候了，所以让我们编写一个简单的调用序列来在账户之间转移一些钱。我们会让第一次转账成功。然后我们将进行一笔大额转账，但由于资金不足，这笔转账将会失败。这样，我们就可以看到交易的效果。

```java
def transferAndPrint(from, to, amount) {
try {
transfer(from, to, amount)
} catch(Exception ex) {
println "transfer failed $ex"
}
println "Balance of from account is $from.balance"
println "Balance of to account is $to.balance"
}
def account1 = new Account(2000)
def account2 = new Account(100)
transferAndPrint(account1, account2, 500)
transferAndPrint(account1, account2, 5000)
```

> Run the code and study the output:
> deposit 500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposit 5000... will it stay
> transfer failed java.lang.RuntimeException: Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

第一次转移成功了，显示的余额反映了这一点。第二次转账完成了第一次存款，但是取款失败了。失败的事务的总体影响是使两个帐户的余额不受影响。

在Groovy中使用Akka STM在Groovy中使用Akka API只需要多付出一点努力和耐心。在Akka中，事务性方法atomic()是通过所谓的包对象公开的。Scala 2.8包对象以字节码的形式表现为一对类:package和package$类。Groovy并不喜欢看到这些小写名称和类中带有$ for的名称。所以，如果我们试图直接使用它们，我们会遇到麻烦。谢天谢地，有一个简单的解决办法。使用class . forname()获取对包对象的类引用。Groovy的动态特性将非常容易地引导我们完成剩下的部分。只需在包引用上调用atomic()方法，并向它传递一个闭包，该闭包实现了Scala . function0表示的Scala s闭包。我们还将向事务工厂发送一个引用。atomic()方法将第二个参数事务工厂定义为隐式可选参数。但是，Groovy不能识别它，所以我们需要发送它。我们可以从包对象中获取默认工厂来使用默认配置设置，或者我们可以用我们想要的配置参数创建我们自己的工厂实例。让我们编写Account类来使用所有这些

```java
class Account {
private final Ref currentBalance
private final stmPackage = Class.forName('akka.stm.package')
public Account(initialBalance) { currentBalance = new Ref(initialBalance) }
def getBalance() { currentBalance.get() }
def deposit(amount) {
stmPackage.atomic({
if(amount > 0) {
currentBalance.set(currentBalance.get() + amount)
println "deposit ${amount}... will it stay"
}
} as scala.Function0, stmPackage.DefaultTransactionFactory())
}
def withdraw(amount) {
stmPackage.atomic({
if(amount > 0 && currentBalance.get() >= amount)
currentBalance.set(currentBalance.get() - amount)
else
throw new RuntimeException("Operation invalid")
} as scala.Function0, stmPackage.DefaultTransactionFactory())
}
}
```

我们将Scala包对象的Java类名传递给forName()方法，并获得对其类元对象的引用stmPackage。然后我们简单地将这个类的atomic()方法调用为stmPackage.atomic()，并传递两个参数:一个闭包和stmPackage.DefaultTransactionFactory()。我们闭包中的代码将在Akka/Multiverse管理的事务中运行。接下来是transfer()方法的其余代码;我们再次在交易中进行存款和取款。

```java
def transfer(from, to, amount) {
def stmPackage = Class.forName('akka.stm.package')
stmPackage.atomic({
to.deposit(amount)
from.withdraw(amount)
} as scala.Function0, stmPackage.DefaultTransactionFactory())
}
def transferAndPrint(from, to, amount) {
try {
transfer(from, to, amount)
} catch(Exception ex) {
println "transfer failed $ex"
}
println "Balance of from account is $from.balance"
println "Balance of to account is $to.balance"
}
def account1 = new Account(2000)
def account2 = new Account(100)
```

> transferAndPrint(account1, account2, 500)
> transferAndPrint(account1, account2, 5000)
> Run the Groovy code and see the transactions in action:
> deposit 500... will it stay
> deposit 500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposit 5000... will it stay
> transfer failed java.lang.RuntimeException: Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

7.3 Java集成

尽管Java已经过时了，但它仍然是使用最广泛的语言之一。

在本节中，我们将看到选择一个并发模型并不需要迫使我们走上语言选择的道路。我们将从Java内部开始接触STM模型。我们将不得不忍受一些java导致的冗长，并必须确保不变性;然而，正如我们将看到的，使用STM API本身是一件轻而易举的事情。在Java中使用Clojure STM，我们可以很容易地将Clojure STM用于Java，因为Ref和LockingTransaction公开为简单类。runInTransaction()方法接受一个实现可调用接口的实例作为参数。因此，在事务中包装代码就像在可调用接口call()方法中包装代码一样简单。我们将使用Java中的Clojure STM实现帐户转账示例。首先，让我们创建一个类帐户，它将持有对其不可变状态的托管引用，该状态由变量balance表示

```java
public class Account {
final private Ref balance;
public Account(final int initialBalance) throws Exception {
balance = new Ref(initialBalance);
}
public int getBalance() { return (Integer) balance.deref(); }
```

我们使用初始平衡初始化托管引用。为了获得当前余额，我们使用了deref()方法，这是我们在Clojure中用于取消引用的@ prefix的java端API。打包在事务中的代码将位于传递给runInTransaction()的可调用代码中，正如我们将在deposit()方法中看到的那样:

```java
public void deposit(final int amount) throws Exception {
LockingTransaction.runInTransaction(new Callable<Boolean>() {
public Boolean call() {
if(amount > 0) {
final int currentBalance = (Integer) balance.deref();
balance.set(currentBalance + amount);
System.out.println("deposit " + amount + "... will it stay");
return true;
} else throw new RuntimeException("Operation invalid");
}
});
}
```

如果金额大于0，存款将修改交易中的当前余额。否则，它抛出异常使事务失败。

实现withdraw()没有太大不同;这只是一个额外的检查

```java
public void withdraw(final int amount) throws Exception {
LockingTransaction.runInTransaction(new Callable<Boolean>() {
public Boolean call() {
final int currentBalance = (Integer) balance.deref();
if(amount > 0 && currentBalance >= amount) {
balance.set(currentBalance - amount);
return true;
} else throw new RuntimeException("Operation invalid");
}
});
}
}
```

这就处理了Account类。现在让我们关注转移;我们将把它写在一个单独的类中。为了查看取款失败而放弃存款的影响，让我们先执行存款，然后再执行取款:

```java
public class Transfer {
public static void transfer(
final Account from, final Account to, final int amount)
throws Exception {
LockingTransaction.runInTransaction(new Callable<Boolean>() {
public Boolean call() throws Exception {
to.deposit(amount);
from.withdraw(amount);
return true;
}
});
}
```

我们将需要一个方便的方法，将处理失败的转移和报告状态的帐户转移后:

```java
public static void transferAndPrint(
final Account from, final Account to, final int amount) {
try {
transfer(from, to, amount);
} catch(Exception ex) {
System.out.println("transfer failed " + ex);
}
System.out.println("Balance of from account is " + from.getBalance());
System.out.println("Balance of to account is " + to.getBalance());
}
```

让我们先执行一个成功的转移，然后执行一个由于资金不足而失败的转移:

```java
public static void main(final String[] args) throws Exception {
final Account account1 = new Account(2000);
final Account account2 = new Account(100);
transferAndPrint(account1, account2, 500);
transferAndPrint(account1, account2, 5000);
}
}
```

> Run the code and study the output:
> deposit 500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposit 5000... will it stay
> transfer failed java.lang.RuntimeException: Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

如我们所料，第一次转移成功，显示的余额反映了这一点。由于资金不足，第二次转账应该会失败。我们看到，当事务回滚时，已生效的存款的效果因取款失败而被否定。转账失败后，两个账户的余额未受影响。在Java中使用Multiverse/Akka STM，如果不需要actor(参见第8章，支持独立的可变性，第163页)，可以考虑直接使用Multiverse STM。使用Multiverse，我们可以使用基于java的注释语法和api。如果计划涉及使用actor或将它们与STM混合使用，Akka是一个不错的选择。尽管Akka是在Scala上构建的，但他们在提供Java API方面做得很好。第6章，软件事务性内存介绍，在第89页充满了Akka的Java例子来帮助你开始。JRuby为Java平台带来了Ruby的强大功能，以及它的优雅、表现力和简洁性。在本节中，我们将通过Akka在JRuby代码中使用Clojure STM和Multiverse STM。如果您想直接使用Multiverse，请参考Multiverse文档中的JRuby集成API。报告

要从JRuby使用Clojure STM，我们将使用Clojure .lang。Ref用于托管引用，并使用LockingTransaction的runInTransaction()方法用于事务。让我们使用账户转账示例来尝试一下。创建一个名为Account的类，它将持有对其不可变状态的托管引用，该状态由JRuby字段@balance表示

```java
require 'java'
java_import 'clojure.lang.Ref'
java_import 'clojure.lang.LockingTransaction'
class Account
def initialize(initialBalance)
@balance = Ref.new(initialBalance)
end
def balance
@balance.deref
end
```

我们已经用初始余额初始化了托管引用。就像在Java集成中一样，我们使用deref()方法来获得平衡。要在事务中运行一段代码，我们必须将其发送到runInTransaction()方法，该方法接受一个可调用类作为参数。JRuby在实现接口方面非常流畅;只需发送一个代码块，它就会负责将其包装到接口实现中。让我们写Account类的deposit()方法，它需要在一个事务中运行它的操作:

```java
def deposit(amount)
LockingTransaction.run_in_transaction do
if amount > 0
@balance.set(@balance.deref + amount)
puts "deposited $#{amount}... will it stay"
else
raise "Operation invalid"
end
end
end
```

在金额合适的情况下，保证金在交易中调整余额;否则，它抛出异常使事务失败。

withdraw()方法类似:

```java
def withdraw(amount)
LockingTransaction.run_in_transaction do
if amount > 0 && @balance.deref >= amount
@balance.set(@balance.deref - amount)
else
raise "Operation invalid"
end
end
end
end
```

Account类已经准备好。现在我们需要一个方法来嵌套交易和转移资金之间的帐户;我们把它写成一个单独的函数。

为了查看存款的效果被失败的取款所抵消，我们将按照这个顺序执行这两个操作。

```java
def transfer(from, to, amount)
LockingTransaction.run_in_transaction do
to.deposit(amount)
from.withdraw(amount)
end
end
```

最后，是时候执行所有代码了，所以编写一个简单的调用序列来在帐户之间转移一些钱。让我们以这样一种方式来写:由于余额不足，第一次转移成功，而第二次转移失败。这应该能告诉我们交易的影响。

```java
def transfer_and_print(from, to, amount)
begin
transfer(from, to, amount)
rescue => ex
puts "transfer failed #{ex}"
end
puts "Balance of from account is #{from.balance}"
puts "Balance of to account is #{to.balance}"
end
```

> account1 = Account.new(2000)
> account2 = Account.new(100)
> transfer_and_print(account1, account2, 500)
> transfer_and_print(account1, account2, 5000)
> Run the code and study the output:

> deposited $500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposited $5000... will it stay
> transfer failed Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

代码的行为与我们预期的第一个交易成功是一致的，而余额反映了这一点。第二次转移失败，余额未受影响。作为该事务的一部分而发生的存款在事务失败时被丢弃。在JRuby中使用Multiverse STM只需要多一点努力和耐心。主要原因是Multiverse依赖异常来重试事务，但是JRuby将异常包装到NativeException中，这样如果我们不小心的话，Multiverse将看不到它所期望的异常，事务将无法提交。我们将在示例中首先面对这个问题，但是我们将找出解决这个问题的方法。在Akka中，事务性方法atomic()是通过所谓的包对象公开的。Scala包对象以字节码的形式表现为一对类:package和package$类。当我们在JRuby中导入package类时，我们会得到一个奇怪的错误:不能将类' package'导入为' package'。为了修复这个错误，让我们重新定义包名，使之不再是package，比如java_import 'akka.stm。包' do |pkgname, classname| "J#{classname}"结束。现在，当我们引用Jpackage时，我们引用的是Akka Akka .stm。包中包对象。我们将使用JRuby和Akka STM创建帐户转账示例。让我们将使用Akka事务的方法隔离到单独的模块中，以便更容易重用该代码

```java
require 'java'
java_import 'akka.stm.Ref'
java_import 'akka.stm.package' do |pkgname, classname| "J#{classname}" end
module AkkaStm
def atomic(&block)
Jpackage.atomic Jpackage.DefaultTransactionFactory, &block
end
end
```

我们导入Akka的Ref类和包对象Akka . sts .package。在我们的

在AkkaStm模块中，我们编写了一个名为atomic()的方法，该方法接收代码块并将其传递给akka.stm。包的原子()方法。由于该方法在Scala外部公开的API也需要工厂，所以我们从包对象本身获得默认的事务工厂。让我们用这个方法在交易中放置Account方法:

```java
require 'akka_stm'
class Account
include AkkaStm
def initialize(initialBalance)
@balance = Ref.new(initialBalance)
end
def balance
@balance.get
end
def deposit(amount)
atomic do
if amount > 0
@balance.set(@balance.get + amount)
puts "deposited $#{amount}... will it stay"
end
end
end
def withdraw(amount)
atomic do
raise "Operation invalid" if amount < 0 || @balance.get < amount
@balance.set(@balance.get - amount)
end
end
end
```

我们为@balance字段创建一个托管引用，并使用AkkaStm模块的atomic()方法将操作放置在事务中的deposit()和withdraw()方法中。我们使用incude-JRuby的mixin工具将该方法引入到Account类中。我们可以将transfer()方法编写为一个独立的方法，还可以将AkkaStm模块混合到其中以重用atomic()方法。

```java
def transfer(from, to, amount)
include AkkaStm
atomic do
to.deposit(amount)
from.withdraw(amount)
end
end
```

作为最后一步，让我们通过调用两个传输来练习所有这些代码:

```java
def transfer_and_print(from, to, amount)
begin
transfer(from, to, amount)
rescue => ex
puts "transfer failed #{ex}"
end
puts "Balance of from account is #{from.balance}"
puts "Balance of to account is #{to.balance}"
end
```

> account1 = Account.new(2000)
> account2 = Account.new(100)
> transfer_and_print(account1, account2, 500)
> transfer_and_print(account1, account2, 5000)

我们可以期待第一次转移成功，因为金额少于可用的资金。我们希望第二次转账因资金不足而失败，并且不影响两个账户的余额。让我们看看当我们运行代码时存储了什么:

> transfer failed
> org.multiverse.api.exceptions.SpeculativeConfigurationFailure: null
> ...

这不是我们想看到的。这个错误不仅使第一次转账交易失败，还使我们损失了大量的时间和脱发。这就是我们在本节开始时提到的错误。这个失败报告了一个SpeculativeConfigurationFailure异常，它是org.multiver .api的一部分。异常包。让我们来了解一下这从何而来，以及为什么在其他语言版本都能完美地工作时，JRuby版本却突然失败了。默认情况下，Multiverse (Akka STM)使用投机事务，我们在第112页的Java嵌套事务中讨论过。首先，Multiverse假定事务是只读的，在看到托管ref上的第一个set操作时，它抛出了SpeculativeConfigurationFailure异常。在Multiverse层，它处理这个异常并重试事务，这次允许更改为托管的ref.这就是我们在第6章，软件事务性内存介绍，第89页看到重试事务的原因。好了，这就是多元宇宙的工作，到目前为止，它在其他语言中似乎工作得很好，所以让我们来看看为什么JRuby会出现问题。

> If we replace this:
> puts "transfer failed #{ex}"
> with the following:
> puts "transfer failed #{ex.class}"

我们会看到异常类名不是SpeculativeConfigurationFailure而是Native- exception。这是JRuby将异常放在自己的包装器异常中。因此，我们从JRuby代码调用Akka, Akka又调用我们的JRuby闭包。这个闭包试图更新托管引用，这导致了Multiverse异常，就像它设计的那样。不幸的是，我们的JRuby闭包代码在内部将该异常包装为NativeException。因此，Multiverse没有看到我们熟悉的SpeculativeConfigurationFailure，而是看到了这个奇怪的意外的NativeException，并简单地让事务失败，然后在不重试事务的情况下向上传播这个异常。那么，解决办法是什么呢?这不是一个令人愉快的方法:我们必须显式地处理它。我们需要检查这个例外是否属于多元宇宙，如果属于，就把它解开。这是一个丑陋的解决方案，但它没有太多的代码。让我们修改AkkaStm s atomic()方法来补偿JRuby行为

```java
require 'java'
java_import 'akka.stm.Ref'
java_import 'akka.stm.package' do |pkgname, classname| "J#{classname}" end
module AkkaStm
def atomic(&block)
begin
Jpackage.atomic Jpackage.DefaultTransactionFactory, &block
rescue NativeException => ex
raise ex.cause if
ex.cause.java_class.package.name.include? "org.multiverse"
raise ex
end
end
end
```

我们封装了对akka. sts .package的调用。如果抛出的异常是一个NativeException，如果嵌入的真正原因是一个Multiverse异常，那么我们抛出解包异常，以便Multiverse可以处理它并采取适当的操作。

现在我们已经修复了它，让我们看看这两个事务是否按照预期的方式运行，第一个事务成功，第二个事务失败，余额不受影响:

> deposited $500... will it stay
> deposited $500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposited $5000... will it stay
> transfer failed Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

修复后，JRuby版本的行为与其他语言的版本相同

7.5 Scala的选择Scala是JVM上的静态类型语言，它连接了面向对象风格和函数风格的编程。在使用这种表达性很强的语言时，我们有很多选择。我们很可能倾向于Akka解决方案，因为它提供的Scala API将是最流畅的。但是，如果我们在一个已经在使用Clojure STM的多语言项目上，没有什么能阻止我们从Scala中使用它。让我们在这里探讨如何使用Clojure STM。在Scala中使用Clojure STM将会遵循Java的大部分代码。我们可以使用Ref类和LockingTransaction类的runInTransaction()方法。我们创建一个实现Callable的实例来包装应该在事务中运行的方法。让我们使用Scala和Clojure STM实现帐户转账示例。这几乎是代码从Java到Scala的直接转换。

```java
class Account(val initialBalance : Int) {
val balance = new Ref(initialBalance)
def getBalance() = balance.deref
```

在主构造函数(是Scala中类定义的一部分)中，我们使用初始余额值初始化引用。在getBalance()方法中，我们通过调用deref()方法来获得余额。要更新事务中的托管引用，只需将其包装到

可调用并将其传递给runInTransaction()方法。让我们写一个存款方法，它的操作需要在一个事务中运行:

```java
def deposit(amount : Int) = {
LockingTransaction runInTransaction new Callable[Boolean] {
def call() = {
if(amount > 0) {
156 • Chapter 7. STM in Clojure, Groovy, Java, JRuby, and Scala
report erratum • discuss
val currentBalance = balance.deref.asInstanceOf[Int]
balance.set(currentBalance + amount)
println("deposit " + amount + "... will it stay")
true
} else throw new RuntimeException("Operation invalid")
}
}
}
```

撤退将采取类似的步骤:

```java
def withdraw(amount : Int) = {
LockingTransaction runInTransaction new Callable[Boolean] {
def call() = {
val currentBalance = balance.deref.asInstanceOf[Int]
if(amount > 0 && currentBalance >= amount) {
balance.set(currentBalance - amount)
true
} else throw new RuntimeException("Operation invalid")
}
}
}
}
```

我们已经完成了Account类。我们可以将transfer()方法作为一个独立的方法来编写，因为在Scala中，如果我们以脚本的形式运行它，就不需要为此创建一个类。如果我们计划编译代码，那么我们必须将其封装到一个对象中，并编写main()方法:

```java
def transfer(from : Account, to : Account, amount : Int) = {
LockingTransaction runInTransaction new Callable[Boolean] {
def call() = {
to.deposit(amount)
from.withdraw(amount)
true
}
}
}
```

在transfer()法中，我们先存钱，然后取款。

在前面的例子中，我们想要研究交易的影响:

```java
def transferAndPrint(from : Account, to : Account, amount : Int) = {
try {
transfer(from, to, amount)
} catch {
case ex => println("transfer failed " + ex)
}
report erratum • discuss
Choices in Scala • 157
println("Balance of from account is " + from.getBalance())
println("Balance of to account is " + to.getBalance())
}
val account1 = new Account(2000)
val account2 = new Account(100)
```

> transferAndPrint(account1, account2, 5000)
> Let’s run the code and study the output:
> deposit 500... will it stay
> Balance of from account is 1500
> Balance of to account is 600
> deposit 5000... will it stay
> transfer failed java.lang.RuntimeException: Operation invalid
> Balance of from account is 1500
> Balance of to account is 600

这正是我们所期望的——成功的第一个事务适当地更改余额，然后失败的事务使余额不受影响。

在Scala Akka中使用Akka/Multiverse STM当然是一个不错的选择，因为它是用Scala编写的，它所公开的api对Scala程序员来说很自然。请参考第6章第89页的《软件事务性内存介绍》中的Scala示例来开始使用STM和Akka。Scala是一种混合的函数式编程语言，它允许我们创建可变(var)变量和不可变(val)值。作为一种好的做法，最好是促进不变:使用瓦尔斯多于瓦尔斯。做一个快速的grep/搜索，看看我们是否有变量，并仔细检查它们。确保状态是不可变的，只有身份引用是可变的。在本章中，我们学习了以下内容:并发性的选择与语言的选择是正交的;我们可以使用任何JVM语言的STM编程并发。在集成方面我们会遇到一些困难，但是解决方法相当简单。我们必须确保值是不可变的，事务是幂等的。158年第7章。Clojure、Groovy、Java、JRuby和Scala中的STM报告

对于经常发生读和写冲突的应用程序，我们可以使用任何JVM语言中的STM。但是，当出现巨大的写入冲突时，STM可能不适合。在这种情况下，为了避免同步和共享可变性的噩梦，我们可以从我们在下一章讨论的基于actor的模型中获益。

## Actor-Based Concurrency

基于actor的并发

### Favoring Isolated Mutability 倾向于孤立的可变性

“如果觉得疼，就停止做”是医生的忠告。在并发编程中，共享可变性就是“it”。

使用JDK线程API，创建线程很容易，但要防止线程冲突和混乱就很困难了。STM可以缓解疼痛;然而，在像Java这样的语言中，我们仍然必须非常小心地避免未管理的可变变量和副作用。令人惊讶的是，当共享的可变性消失时，斗争也消失了。

让多个线程在数据上聚合和碰撞是我们尝试过的一种方法，但没有成功。幸运的是，有一种更好的方式——基于事件的消息传递。

在这种方法中，我们将任务视为应用程序/JVM内部的轻量级进程。我们没有让它们获取数据，而是将不可变的消息传递给它们。一旦这些异步任务完成，它们将返回或将不可变的结果传递给其他协调任务。我们设计了具有协调actors的应用程序，该actors异步交换不可变消息。

这种方法已经存在了几十年，但在JVM领域还是相对较新的。基于角色的模型是非常成功和流行的Erlang(请参阅Erlang编程:用于并发世界的软件[Arm07]和Erlang [VWWA96]中的并发编程)。当Scala在2003年引入时，Erlang的基于actor的模型被采用并引入到JVM中(参见Scala编程[OSV08]和编程)Scala [Sub09])。

在Java中，我们可以从提供基于actor的并发性的6个库中进行选择:`ActorFoundary`、`Actorom`、`Actors Guild`、`Akka`、`FunctionalJava`、`Kilim``、Jetlang`，等等。其中一些库使用了面向方面的字节码编织。它们都处于不同的成熟度和采用水平。

在本章中，我们将学习如何编写基于actor的并发程序。在大多数情况下，我们将使用Akka作为一种车辆来驱动的概念。Akka是一个高性能的基于scala的解决方案，它公开了相当好的Java API。我们可以将它用于基于actor的并发性和STM(参见第6章，软件事务性内存介绍，第89页)。

##### Isolating Mutability Using Actors

Java将OOP转变为可变性驱动的开发，而函数式编程强调不可变性;这两个极端都存在问题。如果一切都是可变的，我们必须处理可见性和竞态条件。在实际的应用程序中，一切都不能是不可变的。即使是纯函数式语言也提供了受限的代码区域，允许副作用和排序方法。无论我们喜欢哪种编程模型，我们都必须避免共享的易变性。

并发性问题的根源在于多个线程可以修改一个变量。隔离的可变性是一种很好的折衷方案，它消除了大多数并发性问题，即只有一个线程(或actor)可以访问可变变量。

在OOP中，我们进行封装，以便只有实例方法才能操作对象的状态。然而，不同的线程可能会调用这些方法，这将导致并发性问题。在基于actor的编程模型中，我们只允许一个actor操作对象的状态。虽然应用程序是多线程的，但参与者本身是单线程的，因此不存在可见性和竞态条件问题。actor请求要执行的操作，但它们不会跨越由其他actor管理的可变状态。与只使用对象编程相比，使用actor编程时，我们采用了不同的设计方法。我们将问题划分为异步计算任务，并将它们分配给不同的参与者。每个参与者的注意力都集中在执行其指定的任务上。我们将任何可变状态限制在最多一个参与者之内时期，我们还确保我们在actor之间传递的消息是完全不可变的。

在这种设计方法中，我们让每个参与者处理问题的一部分。它们以不可变对象的形式接收必要的数据。一旦它们完成了分配的任务，它们就会将结果作为不可变对象发送给调用参与者或另一个指定的后处理参与者。我们可以把这看作是将OOP提升到下一个层次，其中select对象是可变的和活动的，它们在自己的线程中运行。我们操纵这些对象的唯一方法是向它们发送消息，而不是直接调用方法。

##### Actor Qualities

Actor是一个可以接收消息、处理请求和发送响应的自由运行的活动。actor被设计为支持异步和高效消息传递。

每个actor都有一个内置的消息队列，就像手机后面的消息队列一样。莎莉和西恩可以同时在鲍勃的手机上留言。移动电话提供商为Bob保存他们的消息，以便Bob在方便时检索。类似地，actor库允许多个actor并发地发送消息。发送方默认是非阻塞的;他们发出一个信息，并继续照顾他们的业务。库允许指定的参与者按顺序选择要处理的消息。一旦一个参与者处理了一条消息或委托给另一个参与者进行并发处理，它就准备好接收下一条消息了。参与者的生命周期如图12，参与者的生命周期，在第167页中所示。在创建时，可以启动或停止一个actor。

![image-20201209224720235](E:\learningforalllife\git-workspace\PANDA-Walker\picture\image-20201209224720235.png)

一旦启动，它就准备接收消息。在活动状态中，参与者要么正在处理消息，要么在等待新消息的到达。一旦停止，它将不再接收任何消息。参与者在等待和处理消息上花费的时间取决于他们所参与的应用程序的动态特性。

如果参与者在我们的设计中扮演重要的角色，我们会期望它们中的许多在应用程序的执行过程中四处浮动。

然而，线程是有限的资源，因此将actor绑定到它们的线程将非常有限。为了避免这种情况，角色库通常会将角色从线程中解耦。线程对于actor就像食堂座位对于办公室员工一样。鲍勃在公司的自助餐厅没有指定的座位(如果有的话，他需要找另一份工作)，每次他去吃饭时，他都找一个一个空的座位上坐着

![image-20201209224517004](E:\learningforalllife\git-workspace\PANDA-Walker\picture\image-20201209224517004.png)

actor隔离可变状态，并通过传递不可变状态进行通信消息。

当actor有消息要处理或有任务要运行时，它就会被提供一个可用的线程来运行。好的actor不会在不运行任务时保留线程。这允许更多的参与者在不同状态下处于活动状态，并有效地使用有限的可用线程。尽管多个参与者可以在任何时候处于活动状态，但在任何实例中，参与者中只有一个线程处于活动状态。这在参与者之间提供了并发性，同时消除了每个参与者之间的争用。

##### Creating Actors

正如我前面提到的，我们有很多角色库可供选择。在本书中，我们使用了Akka，这是一个基于scala的库，具有相当好的性能和可伸缩性，并且同时支持actor和STM。我们可以在JVM上使用多种语言。在这一章中，我们将继续讨论到Java和Scala。在下一章中，我们将研究Akka actors与其他语言的使用

Akka是用Scala编写的，所以从Scala创建和使用actor非常简单和自然。Akka API突出了Scala的简洁和习惯用法。与此同时，他们在公开传统Java API方面做得相当出色，因此我们可以轻松地在Java代码中创建和使用actor。

我们将首先看一下在Java中如何使用它，然后看看在Scala中使用它时，这种体验是如何简化和改变的。

##### Creating Actors in Java

在Java的抽象类`Akka .actor.UntypedActor`中创建actor。`UntypedActor`表示一个actor。只需扩展该方法并实现所需的`onReceive()`方法，每当actor的消息到达时，就会调用该方法。让我们试一试。我们将创建一个actor…找一个好莱坞演员来扮演不同的角色怎么样

```java
public class HollywoodActor extends UntypedActor {
public void onReceive(final Object role) {
System.out.println("Playing " + role +
" from Thread " + Thread.currentThread().getName());
}
}
```

`onReceive()`方法接受一个对象作为参数。在本例中，我们只是将它与处理消息的线程的详细信息一起打印出来。稍后我们将学习如何处理不同类型的消息。

我们的actor都准备好了，等着我们说“开始”。“我们需要创建actor的一个实例，并使用其角色发送消息，所以让我们开始:

```java
public class UseHollywoodActor {
public static void main(final String[] args) throws InterruptedException {
final ActorRef johnnyDepp = Actors.actorOf(HollywoodActor.class).start();
johnnyDepp.sendOneWay("Jack Sparrow");
Thread.sleep(100);
johnnyDepp.sendOneWay("Edward Scissorhands");
Thread.sleep(100);
johnnyDepp.sendOneWay("Willy Wonka");
Actors.registry().shutdownAll();
}
}
```

在Java中，我们通常使用new创建对象，但Akka actor不是简单对象，它们是活动对象。因此，我们使用特殊的方法`actorOf()`来创建它们。另外，我们也可以使用new创建一个实例，并将其封装在对`actorOf()`的调用中，以获取actor引用，不过我们稍后再讨论这个问题。一旦创建了actor，我们就通过调用start()方法启动它。当我们开始一个actor，Akka把它放入一个注册表;参与者可以通过注册表访问，直到参与者停止。在这个示例中，类型为`ActorRef`的johnnyDepp是对我们的actor实例的引用。

接下来，我们使用sendOneWay()方法向扮演角色的参与者发送一些消息。消息一旦发出，我们真的不必等待。然而，在这种情况下，延迟将帮助我们了解更多细节，即演员如何切换线程，我们将很快看到。最后，我们要求关闭所有运行的演员。除了调用shutdownAll()方法外，我们还可以对各个actor调用stop()方法，或者向它们发送一个kill消息。好了，要运行这个示例，让我们使用javac编译代码，并记住指定Akka库文件的类路径。我们可以像运行常规Java程序一样简单地运行该程序。同样，我们必须记住在类路径中提供必要的jar。下面是我在系统上使用的命令

>javac -d . -classpath $AKKA_JARS HollywoodActor.java UseHollywoodActor.java
>java -classpath $AKKA_JARS com.agiledeveloper.pcj.UseHollywoodActor

其中akka_jar定义如下:

```java
export AKKA_JARS="$AKKA_HOME/lib/scala-library.jar:\
$AKKA_HOME/lib/akka/akka-stm-1.1.3.jar:\
$AKKA_HOME/lib/akka/akka-actor-1.1.3.jar:\
$AKKA_HOME/lib/akka/multiverse-alpha-0.6.2.jar:\
$AKKA_HOME/lib/akka/akka-typed-actor-1.1.3.jar:\
$AKKA_HOME/lib/akka/aspectwerkz-2.2.3.jar:\
$AKKA_HOME/config:\
."
```

我们需要为操作系统恰当地定义AKKA_JARS环境变量，以匹配安装Scala和Akka的位置。我们可以使用Akka附带的Scala -library.jar文件，也可以从本地Scala安装中使用它

默认情况下，Akka在标准输出中打印额外的日志消息;我们在第105页的Java创建事务中看到了如何配置它。

让我们编译并运行代码来观察actor对消息的响应:

>Playing Jack Sparrow from Thread akka:event-driven:dispatcher:global-1
>Playing Edward Scissorhands from Thread akka:event-driven:dispatcher:global-2
>Playing Willy Wonka from Thread akka:event-driven:dispatcher:global-3

参与者一次响应一个消息。输出还允许我们查看运行actor的线程，而且每次都不是同一个线程。同一线程可能处理多个消息，也可能与示例输出不同，但在任何情况下，在任何时候只处理一条消息。关键的一点是，参与者是单线程的，但是不会劫持他们的线程。它们在等待消息时优雅地释放线程;我们添加的延迟帮助引入了这种等待并说明了这一点。我们创建的actor在构建时没有接受任何参数。如果需要，可以在actor创建期间发送参数。例如，使用Hollywoodactor的名称初始化actor

```java
public class HollywoodActor extends UntypedActor {
private final String name;
public HollywoodActor(final String theName) { name = theName; }
public void onReceive(final Object role) {
if(role instanceof String)
System.out.println(String.format("%s playing %s", name, role));
else
System.out.println(name + " plays no " + role);
}
}
```

类`HollywoodActor`的新版本采用类型为String的值名作为构造函数参数。在此过程中，让我们来处理未识别的传入消息格式。在本例中，我们只是打印一条消息，说明好莱坞演员不播放未被识别的消息。

我们可以采取其他操作，比如返回错误代码、登录、调用用户的母亲来报告，等等。让我们看看如何传递这个构造函数参数的实际参数:

```java
public class UseHollywoodActor {
public static void main(final String[] args) throws InterruptedException {
final ActorRef tomHanks = Actors.actorOf(new UntypedActorFactory() {
public UntypedActor create() {
    return new HollywoodActor("Hanks"); }
}).start();
tomHanks.sendOneWay("James Lovell");
tomHanks.sendOneWay(new StringBuilder("Politics"));
tomHanks.sendOneWay("Forrest Gump");
Thread.sleep(1000);
tomHanks.stop();
}
}
```

我们通过发送消息而不是直接调用方法与参与者通信。

Akka希望我们很难获得对actor的直接引用，并希望我们只获得对`ActorRef`的引用。这允许Akka确保我们不会向actor添加方法并直接与它们交互，因为这将把我们带回我们一直努力避免的共享可变性的邪恶土地。这种受控的actor创建还允许Akka对actor进行适当的垃圾收集。因此，如果我们尝试直接创建actor类的实例，我们将获得运行时异常`akka.actor.ActorInitializationException`。您不能使用new显式地创建actor的实例。

Akka允许我们在`create()`方法中以受控的方式创建实例。因此，让我们在实现`UntypedActorFactory`接口的匿名类中实现此方法，并在此方法中创建actor实例，发送适当的构造时参数。随后对`actorOf()`的调用将扩展自`UntypedActor`的常规对象转换为Akka actor。然后，我们可以像前面一样将消息传递给这个actor。我们的HollywoodActor只接受字符串类型的消息，但是在示例中，我们发送了一个`StringBuilder`实例，该实例具有值策略。我们在`onReceive()`中执行的运行时类型检查负责处理这个问题。最后,我们通过调用stop()方法停止actor。引入的延迟给了参与者在我们关闭它之前响应消息的时间。让我们来看看它的输出:

>Hanks playing James Lovell
>Hanks plays no Politics
>Hanks playing Forrest Gump

##### Creating Actors in Scala 在Scala中创建角色

要在Scala中创建Akka actor，而不是我们在Java版本中扩展的UntypedActor，我们将从actor特征进行扩展，并实现所需的receive()方法。让我们在Scala中实现我们之前在Java中编写的HollywoodActor actor类:

```java
class HollywoodActor extends Actor {
def receive = {
case role =>
println("Playing " + role +
" from Thread " + Thread.currentThread().getName())
}
}
```

receive()方法实现了一个功能部分，并采用了Scala模式匹配的形式，但是现在不要让这些细节分散我们的注意力。当消息到达时调用此方法;如果有帮助的话，现在可以把receive()看作一个美化过的switch语句。的实现与

Java版本。

现在我们已经看到了如何定义一个actor，让我们把注意力转向使用这个actor:

```java
object UseHollywoodActor {
def main(args : Array[String]) :Unit = {
val johnnyDepp = Actor.actorOf[HollywoodActor].start()
johnnyDepp ! "Jack Sparrow"
Thread.sleep(100)
johnnyDepp ! "Edward Scissorhands"
Thread.sleep(100)
johnnyDepp ! "Willy Wonka"
Actors.registry.shutdownAll
}
}
```

actorOf()方法有几种类型，这里我们使用的版本将actor类清单作为参数，显示为[HollywoodActor]。一旦创建了actor，我们就通过调用start()方法启动它。在这个例子中，类型为ActorRef的johnnyDepp是对actor实例的引用;然而，由于Scala有类型推断，我们不必指定类型。

接下来，我们向角色扮演的actor发送一些消息。哦，等等，还有一个细节;我们用一种特殊的方法!发送消息。当你看到演员!message，从右到左读取，就像发送消息给actor一样。Scala的简洁性再次发挥了作用。而不是叫演员。!(消息)，我们可以简单地放下点和括号，然后编写actor !消息。如果愿意，我们可以使用具有Scala简便性的java风格的方法，就像actor sendOneWay消息中那样。示例中的其余代码与Java示例类似。

让我们使用scalac编译器来编译代码，但是首先我们必须记住指定Akka库文件的类路径。我们可以像运行常规Java程序一样简单地运行该程序。同样，我们必须记住在类路径中提供必要的jar。下面是我在系统上使用的命令;我们需要根据Scala和Akka的安装位置，为系统替换合适的路径

>scalac -classpath $AKKA_JARS HollywoodActor.scala UseHollywoodActor.scala
>java -classpath $AKKA_JARS com.agiledeveloper.pcj.UseHollywoodActor

如果我们在标准输出中看到日志消息，并希望将它们静音，请在Java actor示例之后查看提供的详细信息。一旦我们编译并运行代码，我们将看到的输出将与

产生的Java版本:

>Playing Jack Sparrow from Thread akka:event-driven:dispatcher:global-1
>Playing Edward Scissorhands from Thread akka:event-driven:dispatcher:global-2
>Playing Willy Wonka from Thread akka:event-driven:dispatcher:global-3

如果我们想要传递参数，比如好莱坞演员的名字，给演员，在Scala中要比在Java版本中简单得多。让我们首先修改类HollywoodActor，让它接受一个构造函数参数:

```java
class HollywoodActor(val name : String) extends Actor {
def receive = {
case role : String => println(String.format("%s playing %s", name, role))
case msg => println(name + " plays no " + msg)
}
}
```

类HollywoodActor的新版本采用类型为String的值名作为构造函数参数。在此过程中，让我们来处理未识别的传入消息格式。与使用instanceof不同，case语句负责将消息与各种模式(在本例中是消息的类型)匹配。

创建接受构造函数参数的actor需要花费一些精力,但是在Scala中，它变成了一些非常简单的东西:

```java
object UseHollywoodActor {
def main(args : Array[String]) : Unit = {
val tomHanks = Actor.actorOf(new HollywoodActor("Hanks")).start()
tomHanks ! "James Lovell"
tomHanks ! new StringBuilder("Politics")
tomHanks ! "Forrest Gump"
Thread.sleep(1000)
tomHanks.stop()
}
}
```

我们使用new关键字实例化actor，然后将实例传递给actorOf()方法(这里，Akka再次阻止我们在调用actorOf()方法之外任意创建actor实例)。这将把从Actor扩展到Akka Actor的常规对象转换为一个Actor。然后像以前那样传递消息。其余代码类似于Java版本。让我们运行代码，确保输出类似于Java版本的输出:

>Hanks playing James Lovell
>Hanks plays no Politics
>Hanks playing Forrest Gump

##### 8.4发送和接收消息  Sending and Receiving Messages

我们可以向actor发送任何类型的消息:字符串、整数、长、双值、列表、映射、元组、Scala case类，所有这些都是不可变的。我特别喜欢元组，不是因为把它们读错为两组很有趣，而是因为它们是轻量级的、不可变的，而且是最容易创建的实例之一。例如，要在Scala中创建两个数字的元组，我们只需write(number1, number2)。Scala的case类是理想的消息类型，它们可以不可变，可以很好地与模式匹配，并且很容易复制。

在Java中，我们可以传递一个不可修改的集合作为消息来发送多个对象一条消息。我们传递一个消息给演员时,默认情况下我们传递消息的引用,如果发送方和接收方都是在同一个JVM.

确保传递的消息是不可变的是我们的责任，特别是当我们决定发送我们自己的类的实例时。我们还可以要求Akka序列化消息，这样传递的是消息的副本而不是引用。

与演员沟通最简单的方式就是“fire and forget.”。也就是说，发送一个信息，然后继续前进。从性能的角度来看，这也是首选的方法。发送是非阻塞的，调用actor/线程继续其工作。我们使用sendOneWay()方法，或者!方法，以发送单向消息。Akka还提供了双向通信，我们可以在其中发送消息并期待来自参与者的响应。在这种情况下，调用线程将阻塞，直到收到响应或超过超时。让我们先看看如何在Java中发送和接收消息，然后在Scala中。

##### Send/Receive in Java 发送/接收在Java中

我们使用sendRequestReply()方法发送消息并等待响应。如果响应没有在(可配置的)超时内到达，我们将接收一个ActorTimeoutException。让我们来看一个双向消息传递的例子:

```java
public class FortuneTeller extends UntypedActor {
public void onReceive(final Object name) {
getContext().replyUnsafe(String.format("%s you'll rock", name));
}
public static void main(final String[] args) {
final ActorRef fortuneTeller =
Actors.actorOf(FortuneTeller.class).start();
try {
final Object response = fortuneTeller.sendRequestReply("Joe");
System.out.println(response);
} catch(ActorTimeoutException ex) {
System.out.println("Never got a response before timeout");
} finally {
fortuneTeller.stop();
}
}
}
```

我们有一个算命的行动者想要对它收到的信息作出回应。它通过对通过getContext()获得的调用上下文调用replyUnsafe()方法来响应消息发送者。`replyUnsafe()`方法在不阻塞的情况下向调用者发送响应，但是代码中没有调用者。在main()方法中，我们调用了`sendRequestReply()`方法。这个方法在内部创建一个`Future`类并等待它，直到它得到一个结果、一个异常或一个超时。让我们通过运行代码来检查乔的财富:

> Joe you'll rock

这个算命的人有一点有点不幸:它依赖于发送者等待可用的响应。我们调用了`sendRequestReply()`方法，因此有一个用于等待响应的内部Future。如果我们调用`sendOneWay()`，那么`replyUnsafe()`方法将失败。为了避免这种情况发生，我们需要在调用`replyUnsafe()`方法之前检查阻塞发送方是否可用。我们可以通过从上下文获取sender引用来实现这一点。另外，我们可以使用`replySafe()`方法，如果发送方引用存在，它将返回true，如果没有发送方引用可用，它将返回false。因此，下面是修改后的算命器，它将处理没有发送者等待响应的情况

```java
public class FortuneTeller extends UntypedActor {
public void onReceive(final Object name) {
if(getContext().replySafe(String.format("%s you'll rock", name)))
System.out.println("Message sent for " + name);
else
System.out.println("Sender not found for " + name);
}
public static void main(final String[] args) {
final ActorRef fortuneTeller =
Actors.actorOf(FortuneTeller.class).start();
try {
fortuneTeller.sendOneWay("Bill");
final Object response = fortuneTeller.sendRequestReply("Joe");
System.out.println(response);
} catch(ActorTimeoutException ex) {
System.out.println("Never got a response before timeout");
} finally {
fortuneTeller.stop();
}
}
}
```

如果发件人不为人所知，新版《算命师》不会失败;它优雅地处理了不幸

>Sender not found for Bill
>Message sent for Joe
>Joe you'll rock

对`sendRequestReply()`的调用在等待响应时阻塞，但是对`sendOneWay()`的调用是非阻塞的，因此不会产生响应。如果我们想接收一个响应，但不想等待它，我们可以使用更精细的方法`sendRequestReplyFuture()`，它将返回一个Future对象。我们可以继续执行工作，直到我们想要得到响应，此时我们可以阻塞或查询future对象，以查看响应是否可用。类似地，在参与者的一边，我们可以从上下文引用获得senderFuture，并在响应就绪后立即通过它进行通信。我们将在后面的示例中介绍如何使用它们。

在使用sendRequestReply()和sendRequestReplyFuture()方法时要小心，因为对这些方法的调用会阻塞，并可能对性能和可伸缩性产生负面影响。

##### Send/Receive in Scala

在Scala中，如果我们想从Scala向actor发送/接收消息，我们必须准备好应对与Java API的一些差异:我们可以直接使用self属性来访问actor。使用这个属性，我们可以调用reply()方法，这是Scala端上的不安全的等效方法，或者我们可以使用replySafe()方法。

我们可以调用sendRequestReply()方法，或者我们可以调用更优雅的!!人们说情人眼里出西施。同样,! !可以用来代替sendRequestReplyFuture()方法。sendRequestReply()方法返回的不是一个对象，而是一个Scala选项。如果响应到达，这将是某个[T]的实例，它保存结果，如果超时，则为None。因此，与Java版本不同的是，在超时的情况下没有例外。让我们先使用不安全的reply()方法在Scala中实现算命器

```java
class FortuneTeller extends Actor {
def receive = {
case name : String =>
self.reply(String.format("%s you'll rock", name))
}
}
object FortuneTeller {
def main(args : Array[String]) : Unit = {
val fortuneTeller = Actor.actorOf[FortuneTeller].start()
val response = fortuneTeller !! "Joe"
response match {
case Some(responseMessage) => println(responseMessage)
case None => println("Never got a response before timeout")
}
fortuneTeller.stop()
}
}
```

在actor代码中，我们可以看到两种不同;一个是与self而不是getContext()相关的，另一个是reply()而不是replyUnsafe()。在调用方，我们对从调用接收到的响应应用模式匹配!!，它是sendRequestReply()方法。如果到达了实际响应，则使用第一种情况，如果超时，则使用无响应的第二种情况。

这段代码的输出与Java版本相同，正如我们所期望的:

> Joe you'll rock

除了我们讨论的更改之外，使用安全版本的reply()与Java版本没有太大区别。我们可以使用reply_?()或replySafe()。

```java
class FortuneTeller extends Actor {
def receive = {
case name : String =>
if(self.reply_?(String.format("%s you'll rock", name)))
println("Message sent for " + name)
else
println("Sender not found for " + name)
}
}
object FortuneTeller {
def main(args : Array[String]) : Unit = {
val fortuneTeller = Actor.actorOf[FortuneTeller].start()
fortuneTeller ! "Bill"
val response = fortuneTeller !! "Joe"
response match {
case Some(responseMessage) => println(responseMessage)
case None => println("Never got a response before timeout")
}
fortuneTeller.stop()
}
}
```

新版本的算命师不会失败，如果发件人是不知道的:

>Sender not found for Bill
>Message sent for Joe
>Joe you'll rock

当Akka发送消息时，在后台传递发送方引用是非常方便的。这样就不需要将发送方显式地作为消息的一部分传递，并在代码中消除了太多的干扰和工作。

如果使用方法名如!，!!, ! !和reply_?()的问题，我们可以分别使用其他名称，如sendOneWay()、sendRequestReply()、sendRequestReplyFuture()和replySafe()。

##### 8.5 Working with Multiple Actors 与多个角色合作

现在我们知道了如何创建一个actor并向它发送消息。让我们感受一下如何让多个参与者工作。在第2章，第15页的劳动分工中，我们创建了一个并行程序来计算范围内的质数。在质数并发计算的例子中，在第27页，我们使用ExecutorService、Callable和Fut ure，用代码填满了一页多一点的代码。让我们先看看Akka actor在Java中是如何形成的，然后再在Scala中。在Java中，给定一个像1000万这样的数字，我们将质数的计算划分为不同的范围，并将这些范围分布在多个线程上。这里我们将使用actor。让我们从actor的onReceive()方法开始

```java
public class Primes extends UntypedActor {
public void onReceive(final Object boundsList) {
final List<Integer> bounds = (List<Integer>) boundsList;
final int count =
PrimeFinder.countPrimesInRange(bounds.get(0), bounds.get(1));
getContext().replySafe(count);
}
```

为了计算一个范围内质数的数量，我们需要该范围的上界和下界。我们的参与者在onReceive()的消息参数中接收这个绑定为列表的消息。我们调用尚未编写的PrimeFinder的countPrimesInRange()方法，并使用replySafe()方法将结果发送回调用者。

给定一个数字，我们需要将它分成给定数量的部分，并将寻找质数的任务委托给不同的参与者。让我们通过countPrimes()静态方法来实现:

```java
public static int countPrimes(
final int number, final int numberOfParts) {
final int chunksPerPartition = number / numberOfParts;
final List<Future<?>> results = new ArrayList<Future<?>>();
for(int index = 0; index < numberOfParts; index++) {
final int lower = index * chunksPerPartition + 1;
final int upper = (index == numberOfParts - 1) ? number :
lower + chunksPerPartition - 1;
final List<Integer> bounds = Collections.unmodifiableList(
Arrays.asList(lower, upper));
final ActorRef primeFinder = Actors.actorOf(Primes.class).start();
results.add(primeFinder.sendRequestReplyFuture(bounds));
}
int count = 0;
for(Future<?> result : results)
count += (Integer)(result.await().result().get());
Actors.registry().shutdownAll();
return count;
}
```

一旦我们确定了每个部分的边界，我们将其包装到一个不可修改的集合记住，消息必须是不可变的。然后调用`sendRequestReplyFuture()`，这样就可以向所有分区发送请求而不会被阻塞。我们节约用水。)这是你的急件。而不是由该方法返回的JDK s `java.util.concurrent.Future`)，因此我们可以稍后查询每个部分产生的质数。我们在Fut ure上调用await()，并在await()返回的Fut ure实例上调用result()方法。这给了我们一个Scala选项的实例，可以把它想象成一个漂亮的union，保存可用的值。通过调用get()方法，我们最终从该对象获得整数值。好的，让我们使用数字和部件的命令行参数来驱动代码

```java
public static void main(final String[] args) {
if (args.length < 2)
System.out.println("Usage: number numberOfParts");
else {
final long start = System.nanoTime();
final int count = countPrimes(
Integer.parseInt(args[0]), Integer.parseInt(args[1]));
final long end = System.nanoTime();
System.out.println("Number of primes is " + count);
System.out.println("Time taken " + (end - start)/1.0e9);
}
}
}
```

main()方法执行代码并对其计时。我们的最后一步是编写`PrimeFinder`，它将完成计算范围内质数的实际工作:

```java
public class PrimeFinder {
public static boolean isPrime(final int number) {
if (number <= 1) return false;
final int limit = (int) Math.sqrt(number);
for(int i = 2; i <= limit; i++) if(number % i == 0) return false;
return true;
}
public static int countPrimesInRange(final int lower, final int upper) {
int count = 0;
for(int index = lower; index <= upper; index++)
if(isPrime(index)) count += 1;
return count;
}
}
```

让我们继续使用大量的例子代码，比如1000万和100个部分:

>Number of primes is 664579
>Time taken 3.890996

让我们将本节中的代码和输出与第27页中的素数并发计算中的代码和输出进行比较。在这两个版本中，我们都将分区数设置为100。在Akka版本的质数计数中不需要设置池大小。这是一个计算密集型的问题，将`ExecutorService`版本的池大小设置在核心数量之上不会产生什么影响。因此，它们在性能上相当接近，并且在Akka版本中比`ExecutorService`中稍微少一些繁冗。随着本章的深入，当我们需要在线程/参与者之间进行更多的协调时，这种差异就会变得更加突出。

##### Multiple Actors in Scala

如果我们使用Scala来实现`primes`示例，我们会享受到Scala在实现actor并与之交互方面的简洁性。让我们看看素数的Scala版本

```java
class Primes extends Actor {
def receive = {
case (lower : Int, upper : Int) =>
val count = PrimeFinder.countPrimesInRange(lower, upper)
self.replySafe(new Integer(count))
}
}
object Primes {
def countPrimes(number : Int, numberOfParts : Int) = {
val chunksPerPartition : Int = number / numberOfParts
val results = new Array[Future[Integer]](numberOfParts)
var index = 0
while(index < numberOfParts) {
val lower = index * chunksPerPartition + 1
val upper = if (index == numberOfParts - 1)
number else lower + chunksPerPartition - 1
val bounds = (lower, upper)
val primeFinder = Actor.actorOf[Primes].start()
results(index) = (primeFinder !!! bounds).asInstanceOf[Future[Integer]]
index += 1
}
var count = 0
index = 0
while(index < numberOfParts) {
count += results(index).await.result.get.intValue()
index += 1
}
Actors.registry.shutdownAll
count
}
def main(args : Array[String]) : Unit = {
if (args.length < 2)
println("Usage: number numberOfParts")
else {
val start = System.nanoTime
val count = countPrimes(args(0).toInt, args(1).toInt)
val end = System.nanoTime
println("Number of primes is " + count)
println("Time taken " + (end - start)/1.0e9)
}
}
}
```

在Java版本和这个版本之间有一些不同。消息格式是一个简单的元组，而不是不可修改的列表。`receive()`中的case可以很容易地匹配它。Java中的for循环在这里变成了while循环。Scala确实有一个非常优雅的for循环;但是，这会导致object到原语转换的开销。为了进行良好的性能比较，我在这里避免了这种优雅。类似地，在`PrimeFinder`中，我们将使用while循环而不是Scala for循环

```java
object PrimeFinder {
def isPrime(number : Int) : Boolean = {
if (number <= 1) return false
var limit = scala.math.sqrt(number).toInt
var i = 2
while(i <= limit) {
if(number % i == 0) return false
i += 1
}
return true
}
def countPrimesInRange(lower : Int, upper : Int) : Int = {
var count = 0
var index = lower
while(index <= upper) {
if(isPrime(index)) count += 1
index += 1
}
count
}
}
```

这个版本的质数示例在1000万件和100件这样的大数量上的性能与我们之前看到的类似:

```java
Number of primes is 664579
Time taken 3.88375
```

8.6 Coordinating Actors

真正的好处和乐趣是当参与者互相协调解决问题。为了利用并发性，我们将问题分成几个部分。

不同的演员可能扮演不同的角色，我们需要协调他们之间的沟通。这就是我们将在这里通过使用文件大小程序作为例子学到的。

在第49页的4.2节协调线程中，我们编写了一个程序来查找给定目录下所有文件的总大小。我们启动了100个线程，每个线程探索不同的子目录。然后合计异步计算的大小。我们看到了实现这一点的不同方法，比如atomiclong和队列。我们可以把这些方法总结为应对共同突变的艰苦工作。通过使用actor的独立可变性来解决这个问题，我们可以节省相当多的工作和麻烦。与那一章中的共享易变性解决方案相比，我们可以在不降低性能的情况下做到这一点。作为同步免费编码的额外好处，我们也会有更简单的代码，我们很快就会看到。作为第一步，让我们为具有多个协调参与者的问题创建一个设计。我们可以使用两种类型的参与者，如图13所示，在第184页，使用参与者的总文件大小问题的设计。我们将在SizeCollector actor中隔离可变状态。它将接收一些消息，以跟踪需要访问的目录，保持文件大小的总数，并为请求Fil ePr ocessor actors提供要访问的目录。主代码将启动这些参与者。我们将有100个filepr ocessor actors来导航给定目录下的文件。我们将首先使用Akka actors在Java中实现这个设计，然后在Scala中实现。让我们首先定义SizeCollector类将接收的消息

```java
class RequestAFile {}
class FileSize {
public final long size;
public FileSize(final long fileSize) { size = fileSize; }
}
class FileToProcess {
public final String fileName;
public FileToProcess(final String name) { fileName = name; }
}
```

消息由不可变类表示。每个Fil ePr ocessor将使用RequestAFil e类型的消息将自己放在具有SizeCollector的列表中。filesi ze是来自filepr ocessor s的消息，它将携带目录中文件的大小

![image-20201209230905420](E:\learningforalllife\git-workspace\PANDA-Walker\picture\image-20201209230905420.png)



他们探索。最后，Fil eToPr ocess是一条带有需要研究的文件名称的消息。

Fil ePr ocessor s是工作人员，探索一个给定的目录，并发回文件的总大小和他们找到的子目录的名称。一旦完成该任务，它们就发送RequestAFil e类，以让SizeCollector知道它们已经准备好承担探索另一个目录的任务。它们还需要首先在SizeCollector上注册，以接收要探索的第一个目录。一个很好的地方是preStart()方法，它在actor启动时被调用。让我们实现Fil ePr ocessor;我们必须记住接收对SizeCollector的引用作为构造函数参数

```java
class FileProcessor extends UntypedActor {
private final ActorRef sizeCollector;
public FileProcessor(final ActorRef theSizeCollector) {
sizeCollector = theSizeCollector;
}
@Override public void preStart() { registerToGetFile(); }
public void registerToGetFile() {
sizeCollector.sendOneWay(new RequestAFile(), getContext());
}
public void onReceive(final Object message) {
FileToProcess fileToProcess = (FileToProcess) message;
final File file = new java.io.File(fileToProcess.fileName);
long size = 0L;
if(file.isFile()) {
size = file.length();
} else {
File[] children = file.listFiles();
if (children != null)
for(File child : children)
if (child.isFile())
size += child.length();
else
sizeCollector.sendOneWay(new FileToProcess(child.getPath()));
}
sizeCollector.sendOneWay(new FileSize(size));
registerToGetFile();
}
}
```

在registerregitfile()方法中，我们向SizeCollector发送一个RequestAFil e消息。我们将一个自引用发送到Fil ePr ocessor actor实例，该实例是使用getContext()方法获得的。SizeCollector会将此引用添加到一个可用空闲Fil ePr ocessor s列表中，该列表将用于浏览目录。SizeCollector类将通过发送一条消息，要求一个Fil ePr ocessor浏览一个目录(我们将很快看到该目录的代码)。filepr ocessor s onReceive()方法将响应该消息。在onReceive()方法中，我们发现给定目录的子目录，并使用sendOneWay()方法将它们发送到SizeCollector。对于给定目录中的文件，我们计算它们的大小，并在任务结束时将其发送到SizeCollector。作为任务的最后一步，我们用SizeCollector注册Fil ePr ocessor类，以获得要探索的另一个目录。

filepr ocessor已经全部设置好，可以浏览目录了。SizeCollector管理孤立的可变状态，并使Fil ePr ocessor s忙于浏览目录，直到计算出最终结果。它处理我们讨论过的三种类型的消息。让我们先看看代码，然后讨论每个消息的操作:

```java
class SizeCollector extends UntypedActor {
private final List<String> toProcessFileNames = new ArrayList<String>();
private final List<ActorRef> idleFileProcessors =
new ArrayList<ActorRef>();
private long pendingNumberOfFilesToVisit = 0L;
private long totalSize = 0L;
private long start = System.nanoTime();
public void sendAFileToProcess() {
if(!toProcessFileNames.isEmpty() && !idleFileProcessors.isEmpty())
idleFileProcessors.remove(0).sendOneWay(
new FileToProcess(toProcessFileNames.remove(0)));
}
public void onReceive(final Object message) {
if (message instanceof RequestAFile) {
idleFileProcessors.add(getContext().getSender().get());
sendAFileToProcess();
}
if (message instanceof FileToProcess) {
toProcessFileNames.add(((FileToProcess)(message)).fileName);
pendingNumberOfFilesToVisit += 1;
sendAFileToProcess();
}
if (message instanceof FileSize) {
totalSize += ((FileSize)(message)).size;
pendingNumberOfFilesToVisit -= 1;
if(pendingNumberOfFilesToVisit == 0) {
long end = System.nanoTime();
System.out.println("Total size is " + totalSize);
System.out.println("Time taken is " + (end - start)/1.0e9);
Actors.registry().shutdownAll();
}
}
}
}
```

SizeCollector保存了两个列表，一个用于目录访问，另一个用于闲置Fil ePr ocessor s。这三个长变量用于跟踪如何访问许多目录在任何时候都可以被浏览，演进的总文件大小，以及计算获得总大小所需时间的开始时间。sendAFileToProcess()方法用于分配目录，以便对闲置的Fil ePr o - cessors进行探索。SizeCollector期望在onReceive()消息处理程序中接收三种类型的消息。每一条信息都有不同的目的。当Fil ePr ocessors变为空闲时，它们发送RequestAFil e消息，并且SizeCollector将actor引用保存在空闲处理器列表中。filetopr ocess是大小控制器同时发送和接收的消息。它将在sendAFileToProcess()方法中向一个空闲的Fil ePr ocessor发送此类型的消息。当Fil ePr ocessor s发现子目录时，它们使用这种类型的消息将目录发送到SizeCollector，这样它就可以进一步安排其他Fil ePr ocessor s进行探索。SizeCollector处理的最后一条消息是Fil eSi ze，它由Fil ePr ocessor s发送，它携带所探索目录中文件的大小。每次接收到目录名时，SizeCollector都会增加一个名为pendingNumberOfFilesToVisit的独立可变计数器。每次接收到一个filesi ze消息时，它都会使用目录的大小减小该计数器。如果它发现这个计数为零，它将打印总大小和花费的时间，并关闭所有参与者，实际上就是关闭程序。让我们实现设计的最后一部分，主代码

```java
public class ConcurrentFileSizeWAkka {
public static void main(final String[] args) {
final ActorRef sizeCollector =
Actors.actorOf(SizeCollector.class).start();
sizeCollector.sendOneWay(new FileToProcess(args[0]));
for(int i = 0; i < 100; i++)
Actors.actorOf(new UntypedActorFactory() {
public UntypedActor create() {
return new FileProcessor(sizeCollector);
}
}).start();
}
}
```

主代码首先创建SizeCollector的一个实例，并使用

filetopr ocess消息，查找目录的大小。主代码创建100个filepr ocessor演员。SizeCollector负责与filepr ocessor s进行协调，并完成查找文件大小的任务。

让我们让这些参与者开始并让他们探索/usr目录

```java
Total size is 3793911517
Time taken is 8.599308
```

比较这段使用独立可变的代码的输出与第49页4.2节协调线程中使用共享可变的代码的版本。所有版本都为/usr目录生成相同的文件大小，并且性能相当。基于actor的版本中最大的区别是代码中不涉及同步，没有锁存，没有队列，也没有需要处理的atomiclong。结果性能相当，更简单，不用担心。在Scala中协调角色我们得到了使用Akka Actors在Java中工作的文件大小的程序。我们可以用Scala实现这种设计，并受益于它的简洁性。与Java版本的第一个区别是消息。Scala有case类，这些类提供了高度表达性的语法来创建不可变类。这些非常适合于消息类型。因此，让我们使用case类来实现消息类型

```java
case object RequestAFile
case class FileSize(size : Long)
case class FileToProcess(fileName : String)
```

filepr ocessor类是从Java版本直接转换到Scala的;除了我们已经讨论过的事情之外，没有什么新的东西:

```java
class FileProcessor(val sizeCollector : ActorRef) extends Actor {
override def preStart = registerToGetFile
def registerToGetFile = { sizeCollector ! RequestAFile }
def receive = {
case FileToProcess(fileName) =>
val file = new java.io.File(fileName)
var size = 0L
if(file.isFile()) {
size = file.length()
} else {
val children = file.listFiles()
if (children != null)
    for(child <- children)
if (child.isFile())
size += child.length()
else
sizeCollector ! FileToProcess(child.getPath())
}
sizeCollector ! FileSize(size)
registerToGetFile
}
}
```

让我们来翻译这个SizeCollector actor。因为我们使用case类来表示消息类型，所以这里很好地提供了Scala模式匹配。它帮助轻松提取值，如文件名和文件大小从适当的消息:

```java
class SizeCollector extends Actor {
var toProcessFileNames = List.empty[String]
var fileProcessors = List.empty[ActorRef]
var pendingNumberOfFilesToVisit = 0L
var totalSize = 0L
val start = System.nanoTime()
def sendAFileToProcess() : Unit = {
if(!toProcessFileNames.isEmpty && !fileProcessors.isEmpty) {
fileProcessors.head ! FileToProcess(toProcessFileNames.head)
fileProcessors = fileProcessors.tail
toProcessFileNames = toProcessFileNames.tail
}
}
def receive = {
case RequestAFile =>
fileProcessors = self.getSender().get :: fileProcessors
sendAFileToProcess()
case FileToProcess(fileName) =>
toProcessFileNames = fileName :: toProcessFileNames
pendingNumberOfFilesToVisit += 1
sendAFileToProcess()
case FileSize(size) =>
totalSize += size
pendingNumberOfFilesToVisit -= 1
if(pendingNumberOfFilesToVisit == 0) {
val end = System.nanoTime()
println("Total size is " + totalSize)
println("Time taken is " + (end - start)/1.0e9)
Actors.registry.shutdownAll
}
}
}
```

最后，我们需要将主代码从Java转换为Scala;再一次，这是直接翻译。

```java
object ConcurrentFileSizeWAkka {
def main(args : Array[String]) : Unit = {
val sizeCollector = Actor.actorOf[SizeCollector].start()
sizeCollector ! FileToProcess(args(0))
for(i <- 1 to 100)
Actor.actorOf(new FileProcessor(sizeCollector)).start()
}
}
```

让我们尝试Scala版本的文件大小程序，就像我们对/usr目录所做的那样，观察性能是否相当，大小是否与Java版本相同

>Total size is 3793911517
>Time taken is 8.321386

8.7 Using Typed Actors

到目前为止，我们看到的参与者接受了消息。我们传递了不同类型的消息、字符串、元组、case类/定制消息，等等。然而，传递这些消息与我们在日常编程中重新使用的常规方法调用感觉很不一样。类型化actor通过允许我们进行常规的方法调用，并在幕后将其转换为消息，从而帮助我们跨越这一鸿沟。可以将类型化actor看作是一个活动对象，它运行在自己的单个轻量级事件驱动线程中，使用一个拦截代理将正常的方法调用转换为异步非阻塞消息。由于类型actor在幕后将常规方法调用转换为消息，所以我们可以最大限度地享受静态类型的好处。我们不必猜测参与者接收到的消息类型，而且我们可以依赖IDE的支持，比如代码完成。为了实现actor，我们简单地编写了一个扩展UntypedActor或actor trait/抽象类的类。要实现一个类型的actor，我们需要创建一个接口-实现对(在Scala中我们不编写接口;相反，我们使用没有实现的trait)。要实例化一个actor，我们使用actor类的actorOf()方法。要实例化一个类型化的actor，我们将使用pedAct或s newInstance()方法。

我们从theTy pedAct或接收到的引用是将方法转换为异步消息的拦截代理。void方法转换为sendOneWay()或!方法，而返回结果的方法被转换为sendRequestReply()或!!方法。返回Fut的方法被转换为sendRequestReplyFuture()或!!方法。我们在第5章“控制共享易变性”中重构的EnergySource类，在第73页使用现代Java并发API，在使用Akka Refs和事务时，在第104页使用STM，是一个很好的类型actor的候选者。它具有可变的状态，我们可以使用一个actor来隔离它。EnergySource的每个实例将只在单个线程中运行，因此不存在竞争条件问题。当多个线程调用EnergySource的一个实例时，调用将跳转线程并在该实例上顺序运行。请记住，actor并不占用线程，因此它们可以跨实例共享线程，并提供更好的吞吐量。EnergySource做了很多事情;它允许我们查询能量级别和使用计数和利用能量，甚至在后台自动补充能量。我们当然希望基于actor的版本能够实现所有这些功能，但我们不要仓促行事。我们将逐步构建它，这样我们就可以一次专注于一件事。让我们先构建Java版本，然后构建Scala版本。

在Java中使用类型演员

类型参与者需要一对接口和实现。那么，让我们从EnergySource的接口开始:

```java
public interface EnergySource {
long getUnitsAvailable();
long getUsageCount();
void useEnergy(final long units);
}
```

这个接口的配对实现是EnergySourceImpl。它和普通Java类的唯一区别是我们扩展了theTy pedActor类，把它变成了一个活动对象:

```java
public class EnergySourceImpl extends TypedActor implements EnergySource {
private final long MAXLEVEL = 100L;
private long level = MAXLEVEL;
private long usageCount = 0L;
public long getUnitsAvailable() { return level; }
    public long getUsageCount() { return usageCount; }
public void useEnergy(final long units) {
if (units > 0 && level - units >= 0) {
System.out.println(
"Thread in useEnergy: " + Thread.currentThread().getName());
level -= units;
usageCount++;
}
}
}
```

TypedAct或确保这些方法都是互斥的;也就是说，在任何给定的实例中，只有一个方法可以运行。因此，不需要同步或锁定对类中任何字段的访问。为了了解执行actor的线程，让我们在这个示例代码中添加一些打印语句。最后，我们准备使用类型化actor，因此让我们为UseEnergySource编写代码。

```java
public class UseEnergySource {
public static void main(final String[] args)
throws InterruptedException {
System.out.println("Thread in main: " +
Thread.currentThread().getName());
final EnergySource energySource =
TypedActor.newInstance(EnergySource.class, EnergySourceImpl.class);
System.out.println("Energy units " + energySource.getUnitsAvailable());
System.out.println("Firing two requests for use energy");
energySource.useEnergy(10);
energySource.useEnergy(10);
System.out.println("Fired two requests for use energy");
Thread.sleep(100);
System.out.println("Firing one more requests for use energy");
energySource.useEnergy(10);
Thread.sleep(1000);
System.out.println("Energy units " + energySource.getUnitsAvailable());
System.out.println("Usage " + energySource.getUsageCount());
TypedActor.stop(energySource);
}
}
```

我们使用theTy pedAct或其newInstance()方法创建类型actor的实例。然后我们首先调用getUnitsAvailable()方法，因为这个方法当返回一个值时，我们的调用线程将阻塞来自类型actor的响应。对useEnergy()的调用是非阻塞的，因为它是一个不返回响应的空方法。我们对这个方法连续进行了两次调用，一个接一个地放置它们。这些电话会立即回复。稍微延迟之后，我们将再次调用useEnergy()来研究actor和线程的行为。最后，在延迟之后，我们再次请求使用计数和能量级别，以允许异步消息完成。最后，我们要求演员停止演出。让我们看一下这段代码的输出

>Thread in main: main
>Energy units 100
>Firing two requests for use energy
>Fired two requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Firing one more requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-3
>Energy units 70
>Usage 3

类型actor EnergySourceImpl一次只执行一个方法。我们触发的前两个useEnergy()请求没有阻塞主线程。然而，这两个任务是在参与者s线程上按顺序运行的。代码优雅地在main中的调用和actor上的方法之间切换执行线程。虽然main()在主线程中运行，但actor的方法在Akka管理的不同线程中按顺序运行。我们还注意到，actor没有把它的线程作为人质;最后一个使用energy()的请求在另一个Akka托管线程中运行。可变状态的能量来源是孤立在EnergySourceImpl演员我说它孤立不是因为它年代封装在这个类,而是因为类型化角色访问控制可变状态最多只有一个演员,一个线程运行在任何时间。通过在Scala中使用类型actor，我们看到了类型actor是如何需要一对接口和实现的。在Scala中，我们不创建接口;相反，我们创建的trait没有实现。让我们把能量源定义为一种特性

>trait EnergySource {
>def getUnitsAvailable() : Long
>def getUsageCount() : Long
>def useEnergy(units : Long) : Unit
>}

EnergySourceImpl的实现是对类EnergySourceImpl从Java版本的相当直接的翻译。我们扩展了TypedAct或类，并混合了EnergySource特性:

```java
class EnergySourceImpl extends TypedActor with EnergySource {
val MAXLEVEL = 100L
var level = MAXLEVEL
var usageCount = 0L
def getUnitsAvailable() = level
def getUsageCount() = usageCount
def useEnergy(units : Long) = {
if (units > 0 && level - units >= 0) {
println("Thread in useEnergy: " + Thread.currentThread().getName())
level -= units
usageCount += 1
}
}
}
```

让我们从Java端口到UseEnergySource类:

```java
object UseEnergySource {
def main(args : Array[String]) : Unit = {
println("Thread in main: " + Thread.currentThread().getName())
val energySource = TypedActor.newInstance(
classOf[EnergySource], classOf[EnergySourceImpl])
println("Energy units " + energySource.getUnitsAvailable)
println("Firing two requests for use energy")
energySource.useEnergy(10)
energySource.useEnergy(10)
println("Fired two requests for use energy")
Thread.sleep(100)
println("Firing one more requests for use energy")
energySource.useEnergy(10)
Thread.sleep(1000);
println("Energy units " + energySource.getUnitsAvailable)
println("Usage " + energySource.getUsageCount)
TypedActor.stop(energySource)
}
}
```

```java
Thread in main: main
Energy units 100
Firing two requests for use energy
Fired two requests for use energy
Thread in useEnergy: akka:event-driven:dispatcher:global-2
Thread in useEnergy: akka:event-driven:dispatcher:global-2
Firing one more requests for use energy
Thread in useEnergy: akka:event-driven:dispatcher:global-3
Energy units 70
Usage 3
```

Scala版本除了具有活动对象的优点外，还具有一些简便性。

8.8 Typed Actors and Murmurs

EnergySource的类型actor版本允许我们调用方法，但在后台以异步消息的顺序运行它们，为我们提供线程安全，而不需要同步。这很容易创建，但我们的能源在这一点上是半生不活的，缺少一个关键的功能，能量级别需要自动补充。在我们在前几章实现的版本中，补充操作不需要任何用户干预;它是在后台自动完成的。当我们启动能量源时，一个计时器就会适当地提高每秒一个单位的能量级别。在类型actor版本中实现该特性将需要进行一些工作，以确保补充操作不会违反类型actor的单线程特性。在开始编写代码之前，让我们先研究一些选项。我们可以向EnergySource接口添加补充()方法。能源的用户可以每秒钟调用这个方法。不幸的是，这给能源使用者带来不必要的负担;他们可能会忘记，而且它在功能上也将不再与其他版本的EnergySource匹配。罢工,选项。我们可以创建一个定时器在类型的演员,和这个定时器可以定期补充能量levelTypedActor年代提供一个特殊的方法称为起动前的(),年代就创建了演员和一个方法称为postStop()年代叫做演员之后停止或关闭。这两种方法很有用;我们可以在preStart()方法的报告勘误表讨论类型演员和Murmurs 195中启动计时器，并在postStop()方法中取消它。这似乎是个好计划，但却带来了另一个问题。

问题是计时器在它们自己的线程中运行，我们不想从这些线程中接触actor的可变状态。记住，我们希望状态是孤立可变的，而不是共享可变的。我们需要的是一种方法，以导致内部方法调用(我称为这些杂音)由参与者正确执行。这些杂音对我们的类型actor的外部用户是不可见的，而是作为异步消息运行，就像那些被外部调用的消息一样，这些消息被排序并与其他消息交织在一起。让我们看看如何将其编码。记住，有类型的参与者实际上是具有附加便利的参与者。它们确实像演员一样接收消息。基clasTsy pedAct或s receive()方法接收来自代理的消息，并在我们的类上分派适当的方法。我们可以重写这个方法来实现一个针对内部操作的特殊消息。这样，actor的用户就可以调用通过接口发布的方法，而我们的类就可以在内部使用这个(未发布的)消息。如果我们愿意，我们甚至可以采取额外的步骤来确保这个消息的发送者是我们自己的参与者。在Java中完成这一点需要付出一些努力，但在Scala中会容易得多。让我们先看看Java实现，然后再看看Scala版本。当我们的EnergySourceImpl的外部用户通过EnergySource接口进行通信时，我们将在内部设置actor使用计时器每秒钟向自身发送一条Repl eni sh请求消息。我们将编写的补充()方法是私有的，不能从类外部直接调用，但我们也将避免从计时器直接调用它。计时器只会向参与者发送一条消息。让我们看一下这部分代码

```java
@SuppressWarnings("unchecked")
public class EnergySourceImpl extends TypedActor implements EnergySource {
private final long MAXLEVEL = 100L;
private long level = MAXLEVEL;
private long usageCount = 0L;
class Replenish {}
@Override public void preStart() {
Scheduler.schedule(
optionSelf().get(), new Replenish(), 1, 1, TimeUnit.SECONDS);
}@Override public void postStop() { Scheduler.shutdown(); }
private void replenish() {
System.out.println("Thread in replenish: " +
Thread.currentThread().getName());
if (level < MAXLEVEL) level += 1;
}
```

在preStart()方法中(在actor启动后自动调用)，我们启动一个计时器。我们使用akka提供的调度器，它是一个基于actor的计时器。这个计时器提供了一些重载的schedule()方法来一次性或重复地启动任务。我们可以使用它来执行任意函数，或者像本例中那样，在actor上触发消息。我们设置了计时器，在初始延迟一秒之后，每秒钟就会在actor上发送一条Repl eni sh消息。我们通过调用实例上的optionSelf()方法获得actor的ActorRef引用的句柄。当actor停止时，我们应该停止定时器;因此，我们有postStop()方法。在私有的补充()方法中，我们还没有绑定到Repl enish消息，我们增加了级别值。类型actor的用户使用的代理将方法调用转换为消息。TheTy pedAct或基类的receive()方法将这些消息转换为实现上的方法调用。如果我们检查receive()方法的签名，它将返回一个scala.PartialFunction。6为了我们这里的讨论，可以把一个部分函数看作是一个美化过的开关语句。它根据接收到的消息的类型分派不同的代码片段。因此，基类将消息映射到我们的方法，而我们希望承担映射一个额外的、但是私有的消息的额外责任。换句话说，我们希望将消息处理与基类的receive()方法结合起来。函数Parti al Functi的orElse()方法允许我们很容易地做到这一点，所以我们将使用它

```java
@Override public PartialFunction receive() {
return processMessage().orElse(super.receive());
}
```

我们覆盖了receive()方法，并在其中将尚未实现的processMessage()方法返回的部分函数与基函数的receive()返回的部分函数结合起来。现在我们可以将注意力转向processMessage()方法的实现。此方法应该接收

Repl enish消息并调用私有的补充()方法。因为这是消息处理序列的一部分，所以我们使用基于actor的通信来处理线程同步。如果咖啡不够喝，那就多喝点;你需要额外的咖啡因来实现这个方法。Parti al Functi on是Scala中的一个特性，其实现在Java中表现为一对接口和一个抽象类。因此，要在Java中实现一个trait，我们将实现该接口和委托调用，并适当地将调用委派给相应的抽象类。我们将实现的关键方法是apply()方法，我们在该方法中处理Repl eni sh消息。我们还将提供isDefinedAt()方法的实现，该方法告诉函数部分是否支持特定的消息格式或类型。此接口的其余方法可以委托。我们可以通过扩展AbstractFunction1来避免实现接口的某些方法，它与Parti al Functi共享公共接口Function1。

```java
private PartialFunction processMessage() {
class MyDispatch extends AbstractFunction1 implements PartialFunction {
public boolean isDefinedAt(Object message) {
return message instanceof Replenish;
}
public Object apply(Object message) {
if (message instanceof Replenish) replenish();
return null;
}
public Function1 lift() {
return PartialFunction$class.lift(this);
}
public PartialFunction andThen(Function1 function) {
return PartialFunction$class.andThen(this, function);
}
public PartialFunction orElse(PartialFunction function) {
return PartialFunction$class.orElse(this, function);
}
};
return new MyDispatch();
}
```

在apply()方法中，我们检查消息的类型是否为Repl enish，并调用了私有的补充()。isDefinedAt()表示我们只支持这一种消息类型，其余的消息由第198章第8章决定。支持独立的可变性报告勘误表讨论基类的receive()。好的，最后一步是不要忘记那些没有从以前类型的actor版本中更改的方法，所以让我们把它们处理完

```java
public long getUnitsAvailable() { return level; }
public long getUsageCount() { return usageCount; }
public void useEnergy(final long units) {
if (units > 0 && level - units >= 0) {
System.out.println(
"Thread in useEnergy: " + Thread.currentThread().getName());
level -= units;
usageCount++;
}
}
}
```

EnergySource接口没有变化，并且UseEnergySource继续像以前一样使用actor。因此，让我们编译EnergySourceImpl的新版本，并使用我们在前一节中编写的UseEnergySource运行它。在上一节中，在跑步结束的时候，我们剩下了70单位的能量。因为我们现在有自动补充的功能，单位应该增加一到两个点。

>Thread in main: main
>Energy units 100
>Firing two requests for use energy
>Fired two requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Firing one more requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-3
>Thread in replenish: akka:event-driven:dispatcher:global-4
>Energy units 71
>Usage 3

我们用来打印线程信息的打印语句显示了使用能量请求和补充请求在Akka s参与者的线程中运行，从而再次减轻了我们的同步问题。如果我们添加sleep调用来延迟这些任务中的任何一个，我们将看到对actor的后续调用的执行被延迟，因为actor是单线程的。这种方法的本质是将我们自己的局部函数实现与基类的receive()方法返回的局部函数结合起来。在Java中要做到这一点需要付出很大的努力，但在Scala中要容易得多。让我们来看看处理杂音的部分，内部信息。

```java
class EnergySourceImpl extends TypedActor with EnergySource {
val MAXLEVEL = 100L
var level = MAXLEVEL
var usageCount = 0L
case class Replenish()
override def preStart() =
Scheduler.schedule(self, Replenish, 1, 1, TimeUnit.SECONDS)
override def postStop() = Scheduler.shutdown
override def receive = processMessage orElse super.receive
def processMessage : Receive = {
case Replenish =>
println("Thread in replenish: " + Thread.currentThread.getName)
if (level < MAXLEVEL) level += 1
}
```

preStart()和postStop()方法是对Java版本的简单翻译。消息类Repl eni sh()在这里变成了case类。除了Scala的简洁性外，receive()与此基本相同。最大的变化是在processMessage()方法中。最后，它变成了Repl eni sh消息的简单模式匹配，没有像Java方面那样混淆继承和委托。由于这是如此简单，我们不妨在这里实现Repl eni sh()的逻辑，而不是创建一个私有函数。我们可以将processMessage()方法的返回类型定义为Parti al Functi on[Any, Unit]，或者，就像我们在示例中所做的那样，定义为Recei ve，它是processMessage()方法的别名。让我们引入之前Scala版本中EnergySourceImpl的其余代码

```java
def getUnitsAvailable() = level
def getUsageCount() = usageCount
def useEnergy(units : Long) = {
if (units > 0 && level - units >= 0) {
println("Thread in useEnergy: " + Thread.currentThread.getName)
level -= units
usageCount += 1
}
}
}
```

我们可以使用之前Scala版本中的EnergySource和UseEnergySource。

所以，让我们编译新版本的EnergySourceImpl并运行它来比较输出:

>Thread in main: main
>Energy units 100
>Firing two requests for use energy
>
>Fired two requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Thread in useEnergy: akka:event-driven:dispatcher:global-2
>Firing one more requests for use energy
>Thread in useEnergy: akka:event-driven:dispatcher:global-3
>Thread in replenish: akka:event-driven:dispatcher:global-4
>Energy units 71
>Usage 3

与Java相比，在Scala中实现这一点要容易得多。尽管这两段代码产生了相同的逻辑结果，但与Java版本相比，在Scala版本中花费的精力更少。

8.9 Mixing Actors and STM

actor很好地允许我们隔离可变状态。如果可以将一个问题划分为可独立运行并使用消息进行异步通信的并发任务，那么它们将非常有效。然而，参与者并不提供一种跨任务管理共识的方法。我们可能希望两个或更多参与者的行动能够同时成功或失败;也就是说，他们要么全部成功，要么全部失败。actor本身不能为我们做到这一点，但是我们可以通过将STM引入其中来实现这一点。在本节中，我假设您已经阅读了第6章，软件事务性内存介绍，在第89页，以及本章中对actor和类型actor的讨论。AccountService类帮助我们在页67和页111上创建嵌套事务的Lock接口中的两个帐户之间进行传输，它可以作为一个很好的示例来理解actor和STM之间的这种相互作用。存款和取款业务是独立于个人账户的。因此，Account可以使用简单的参与者或类型化的参与者来实现。然而，转账操作必须协调两个账户之间的存款和取款。换句话说，由一个参与者处理的存款操作，只有在由另一个参与者作为总体转移的一部分处理的相应的取款操作也成功时，才应该成功。让我们混合使用actor和STM来实现帐户转账示例。Akka提供了一些混合actor和STM的选项。我们可以创建一个单独的事务协调对象，并自己管理进入事务的各种参与者的顺序(关于控制级别，请参阅Akka文档)。另外，我们可以依赖两种方便的方法来管理参与者之间的事务。我们将研究这两种方法:如何使用它们的事务器以及如何协调类型参与者。

8.10 Using Transactors

Akka事务参与者或事务参与者将多个参与者的执行带入事务的折叠中。事务处理程序对来自多个协调角色的托管STM Ref对象的更改进行原子性处理。只有在包含的事务提交时，才保留对这些对象的更改;否则，更改将被丢弃。

事务处理程序提供了三个选项来处理消息:

•默认选项是在自己的事务中处理消息。

•我们可以实现一个正常()方法，可以独立处理select消息，也就是说，不作为任何事务的一部分。

•我们可以要求信息被协调处理，也就是说，作为一个总体事务的一部分。

事务处理程序为我们提供了将其他参与者链接到协调事务中的灵活性。此外，事务处理程序提供可选的事务前和事务后方法。我们可以使用这些方法为事务做准备，并执行任何提交后处理。

让我们先用Java，然后用Scala创建一个事务处理器。

##### Transactors in Java

要在Java中使用transactor，我们将扩展UntypedTransactor类并实现所需的atomically()方法。此外，如果我们想在事务中包含其他参与者，我们实现coordinate()方法。让我们使用交易器重新实现帐户转账示例。让我们首先从我们将需要的消息类开始。我们将使用一个存款消息来对帐户执行存款操作。该消息由一个不可变类表示，它将作为消息的一部分携带存款金额。

```java
public class Deposit {
public final int amount;
public Deposit(final int theAmount) { amount = theAmount; }
}
```

接下来，我们有一个取款消息，它在结构上类似于存款消息:

```java
public class Withdraw {
public final int amount;
public Withdraw(final int theAmount) { amount = theAmount; }
}
```

不需要为消息携带数据来获得平衡，所以一个空类就足够了:

```java
public class FetchBalance {}
```

对此类消息的响应将是嵌入在余额消息中的实际余额:

```java
public class Balance {
public final int amount;
public Balance(final int theBalance) { amount = theBalance; }
}
```

我们需要的最终信息定义是forTr ansfer，它将包含账户和转账金额的详细信息:

```java
public class Transfer {
public final ActorRef from;
public final ActorRef to;
public final int amount;
public Transfer(final ActorRef fromAccount,
final ActorRef toAccount, final int theAmount) {
from = fromAccount;
to = toAccount;
amount = theAmount;
}
}
```

AccountService事务处理程序将使用Tr ansf er消息，帐户事务处理程序将使用我们定义的其他消息。现在让我们看一下账户交易:

```java
public class Account extends UntypedTransactor {
private final Ref<Integer> balance = new Ref<Integer>(0);
public void atomically(final Object message) {
if(message instanceof Deposit) {
int amount = ((Deposit)(message)).amount;
if (amount > 0) {
balance.swap(balance.get() + amount);
System.out.println("Received Deposit request " + amount);
    }
}
if(message instanceof Withdraw) {
int amount = ((Withdraw)(message)).amount;
System.out.println("Received Withdraw request " + amount);
if (amount > 0 && balance.get() >= amount)
balance.swap(balance.get() - amount);
else {
System.out.println("...insufficient funds...");
throw new RuntimeException("Insufficient fund");
}
}
if(message instanceof FetchBalance) {
getContext().replySafe(new Balance(balance.get()));
}
}
}
```

Account类扩展了UntypedTransactor并实现了atomically()方法。此方法将在提供给它的事务的上下文中运行。这可能是调用者的事务，如果没有提供任何事务，则可能是单独的事务。如果接收到的消息是存款消息，则增加存储在stm管理的Ref对象中的余额。如果信息是取款，那么我们减少余额，只有当足够的钱可用。否则，我们抛出一个异常。如果抛出异常，将触发包含的事务的回滚。最后，如果消息是Fet chBal - ance，我们用balance的当前值响应发送者。由于整个方法在一个事务中运行，所以我们不必担心对事务器内的Ref对象的多次访问。只有在提交了包含的事务时，我们对Ref对象所做的更改才会被保留。记住，我们负责维护不可变状态。协调一个交易者(账户)的存款操作和另一个交易者(账户)的取款操作属于AccountService交易者，我们将在下面写

```java
public class AccountService extends UntypedTransactor {
@Override public Set<SendTo> coordinate(final Object message) {
if(message instanceof Transfer) {
Set<SendTo> coordinations = new java.util.HashSet<SendTo>();
Transfer transfer = (Transfer) message;
coordinations.add(sendTo(transfer.to, new Deposit(transfer.amount)));
    coordinations.add(sendTo(transfer.from,
new Withdraw(transfer.amount)));
return java.util.Collections.unmodifiableSet(coordinations);
}
return nobody();
}
public void atomically(final Object message) {}
}
```

协调存款和取款是AccountService的唯一职责，因此在coordinate()方法中，我们将适当的消息发送到这两个帐户。我们通过分组演员和为每个演员为一组消息。当我们从坐标()方法返回这个集合,AccountService办理人年代基本实现将发送适当的消息中的每个处理者集。一旦消息被发送,它将调用自己的自动()实现。因为我们在这里没有更多的事情要做，所以我们将这个方法留空。让我们使用一些示例代码来练习这些transactor

```java
public class UseAccountService {
public static void printBalance(
final String accountName, final ActorRef account) {
Balance balance =
(Balance)(account.sendRequestReply(new FetchBalance()));
System.out.println(accountName + " balance is " + balance.amount);
}
public static void main(final String[] args)
throws InterruptedException {
final ActorRef account1 = Actors.actorOf(Account.class).start();
final ActorRef account2 = Actors.actorOf(Account.class).start();
final ActorRef accountService =
Actors.actorOf(AccountService.class).start();
account1.sendOneWay(new Deposit(1000));
account2.sendOneWay(new Deposit(1000));
Thread.sleep(1000);
printBalance("Account1", account1);
printBalance("Account2", account2);
System.out.println("Let's transfer $20... should succeed");
accountService.sendOneWay(new Transfer(account1, account2, 20));
    Thread.sleep(1000);
printBalance("Account1", account1);
printBalance("Account2", account2);
System.out.println("Let's transfer $2000... should not succeed");
accountService.sendOneWay(new Transfer(account1, account2, 2000));
Thread.sleep(6000);
printBalance("Account1", account1);
printBalance("Account2", account2);
Actors.registry().shutdownAll();
}
}
```

我们与行动者和交易者之间的互动方式没有区别。如果我们发送一条常规消息(如new Deposit(1000))，它会自动被包装到一个事务中。我们还可以通过创建akka.transactor的实例来创建自己的协调事务。协调并将消息包装到其中(例如，new coordination (new Deposit(1000)))。由于在本例中我们只处理单向消息，因此在进行下一个查询之前，我们为处理消息提供了足够的时间。这将为协调事务的成功或失败提供时间，并且我们可以在后续调用中看到事务的效果。由于协调事务只能在所有发送给参与事务者的消息无一例外地完成时才能提交，因此协调请求最多等待事务完成的超时时间(可配置)。代码的输出如下所示

```java
Received Deposit request 1000
Received Deposit request 1000
Account1 balance is 1000
Account2 balance is 1000
Let's transfer $20... should succeed
Received Deposit request 20
Received Withdraw request 20
Account1 balance is 980
Account2 balance is 1020
Let's transfer $2000... should not succeed
Received Withdraw request 2000
...insufficient funds...
Received Deposit request 2000
Account1 balance is 980
Account2 balance is 1020
```

前两笔存款和第一次转账顺利完成。但是，最近的转帐数额大于从账户中可得的余额。转账的存款部分已经执行，我们可以从输出中打印的消息中看到(存款和提取同时运行，因此每次运行代码时打印的顺序可能不同)。但是，取款没有完成，因此整个转移被回滚。放弃了对存款的更改，第二次尝试转账没有影响两个账户的余额。

##### Transactors in Scala

要在Scala中使用transactor，我们将扩展transact或类，并实现所需的atomically()方法。此外，如果我们想在事务中包括其他参与者，我们实现coordinate()方法。让我们将这个示例从Java转换为Scala。我们将从message类开始，这些类可以用Scala简洁地写成case类。

```java
case class Deposit(val amount : Int)
case class Withdraw(val amount : Int)
case class FetchBalance()
case class Balance(val amount : Int)
case class Transfer(val from : ActorRef, val to : ActorRef, val amount : Int)
```

接下来，我们将帐户交易器转换为Scala。我们可以使用模式匹配来处理这三条消息

```java
class Account extends Transactor {
val balance = Ref(0)
def atomically = {
case Deposit(amount) =>
if (amount > 0) {
balance.swap(balance.get() + amount)
println("Received Deposit request " + amount)
}
case Withdraw(amount) =>
println("Received Withdraw request " + amount)
if (amount > 0 && balance.get() >= amount)
balance.swap(balance.get() - amount)
else {
println("...insufficient funds...")
    throw new RuntimeException("Insufficient fund")
}
case FetchBalance =>
self.replySafe(Balance(balance.get()))
}
}
```

接下来，我们翻译AccountService交易程序。同样，这里我们将atomically()方法保留为空，在coordinate()方法中，我们将提到哪些对象进行协调以参与事务。在Scala这一边的语法要比在Java代码中简洁得多:

```java
class AccountService extends Transactor {
override def coordinate = {
case Transfer(from, to, amount) =>
sendTo(to -> Deposit(amount), from -> Withdraw(amount))
}
def atomically = { case message => }
}
```

让我们使用一些示例代码来练习这些transactor:

```java
object UseAccountService {
def printBalance(accountName : String, account : ActorRef) = {
(account !! FetchBalance) match {
case Some(Balance(amount)) =>
println(accountName + " balance is " + amount)
case None =>
println("Error getting balance for " + accountName)
}
}
def main(args : Array[String]) = {
val account1 = Actor.actorOf[Account].start()
val account2 = Actor.actorOf[Account].start()
val accountService = Actor.actorOf[AccountService].start()
account1 ! Deposit(1000)
account2 ! Deposit(1000)
Thread.sleep(1000)
printBalance("Account1", account1)
printBalance("Account2", account2)
    println("Let's transfer $20... should succeed")
accountService ! Transfer(account1, account2, 20)
Thread.sleep(1000)
printBalance("Account1", account1)
printBalance("Account2", account2)
println("Let's transfer $2000... should not succeed")
accountService ! Transfer(account1, account2, 2000)
Thread.sleep(6000)
printBalance("Account1", account1)
printBalance("Account2", account2)
Actors.registry.shutdownAll
}
}
```

这是从Java直接翻译过来的;再一次，我们看到了Scala的简洁性发挥着很好的作用。当我们观察输出时，这个版本的行为就像Java版本一样。

>Received Deposit request 1000
>Received Deposit request 1000
>Account1 balance is 1000
>Account2 balance is 1000
>Let's transfer $20... should succeed
>Received Deposit request 20
>Received Withdraw request 20
>Account1 balance is 980
>Account2 balance is 1020
>Let's transfer $2000... should not succeed
>Received Deposit request 2000
>Received Withdraw request 2000
>...insufficient funds...
>Account1 balance is 980
>Account2 balance is 1020

##### Using Transactors

我们了解了如何在Java和Scala中实现transactor。事务处理程序结合了actor和STM的优点，允许我们在独立运行的actor之间提供一致意见。就像在STMs的使用中一样，当写入冲突非常少见时，事务器非常有用。理想情况下，如果多个参与者必须参加某种形式的法定人数或投票，那么交易者可以非常方便

#### 8.11 Coordinating Typed Actors

正如我们在第8.7节中所看到的，使用类型化的actor(第190页)是面向对象编程和基于actor编程的迷人结晶。它们结合了非常熟悉的、方便的方法调用和参与者的优点。因此，在oo应用程序中，我们可能会尝试使用有类型的actor，而不是actor。但是，就像actor一样，有类型的actor是独立运行的，不提供事务协调，为此我们必须使用协调类型的actor。Akka使得将一个类型的参与者变成一个协调类型的参与者变得非常简单;我们只需用一个特殊的协调注释标记相应的接口方法。要指示调用序列应该在协调事务下运行，只需将调用包装到一个coordinate()方法中。该方法默认情况下等待我们在其中调用的所有方法提交或回滚，然后再继续。有一个限制;只有void方法才能被标记为特殊的协调注释。Void方法转换为单向调用，这些调用可以参与到事务中。返回值的方法转换为两个阻塞调用，因此不能参与自由运行的并发事务。让我们在第202页的8.10节中使用交易者实现我们已经使用过很多次的货币转移示例。

##### Coordinating Typed Actors in Java

类型演员使用一对接口和实现，所以让我们从我们需要的两个接口开始，Account和AccountService接口:

```java
public interface Account {
int getBalance();
@Coordinated void deposit(final int amount);
@Coordinated void withdraw(final int amount);
}
```

```java
public interface AccountService {
void transfer(final Account from, final Account to, final int amount);
}
```

@Coordinated注释标记是Account接口中唯一特殊的地方。通过标记这些方法，我们表示这些方法应该在它们自己的事务中运行，或者参与调用者的事务。属性之后，AccountService中的方法没有使用注释标记该类的实现者将管理自己的事务。现在让我们看看Account接口的实现:

```java
public class AccountImpl extends TypedActor implements Account {
private final Ref<Integer> balance = new Ref<Integer>(0);
public int getBalance() { return balance.get(); }
public void deposit(final int amount) {
if (amount > 0) {
balance.swap(balance.get() + amount);
System.out.println("Received Deposit request " + amount);
}
}
public void withdraw(final int amount) {
System.out.println("Received Withdraw request " + amount);
if (amount > 0 && balance.get() >= amount)
balance.swap(balance.get() - amount);
else {
System.out.println("...insufficient funds...");
throw new RuntimeException("Insufficient fund");
}
}
}
```

通过扩展pedAct or，我们表明这是一个行为人。我们没有使用简单的本地字段，而是使用托管的STM Refs。这里我们不编写任何特定于事务的代码;然而，这个类中的所有方法都将在事务中运行，因为相关的接口方法被标记为@ coordination。如果金额大于0，则deposit()会增加余额。如果有足够的钱可用，withdraw()方法会减少余额。否则，它抛出一个异常，指示操作和包含的事务失败。如果没有提供，这些方法将参与它们自己的事务。然而，在转账的情况下，我们希望存款和取款操作同时在一个交易中运行。这是由AccountServiceImpl管理的，这是我们接下来编写的

```java
public class AccountServiceImpl
extends TypedActor implements AccountService {
public void transfer(
final Account from, final Account to, final int amount) {
coordinate(true, new Atomically() {
public void atomically() {to.deposit(amount);
from.withdraw(amount);
}
});
}
}
```

transfer()方法在一个事务中运行存款和取款两个操作。在事务中运行的代码被包装到atomically()方法中，该方法是atomically接口的一个方法。从akkar .transactor静态导入的coordinate()。协调类，在atomically()中运行事务代码块。第一个参数true告诉方法coordinate()在返回之前等待其事务完成(成功或回滚)。不过，在事务中调用的方法是单向消息，并且coordinate()方法不会阻塞它们的结果，而只是阻塞它们的事务来完成。除了这些对象的创建之外，使用这些类型参与者的代码看起来应该与使用常规对象的代码没有任何不同。我们使用工厂来创建它们，而不是使用new，如下所示

```java
public class UseAccountService {
public static void main(final String[] args)
throws InterruptedException {
final Account account1 =
TypedActor.newInstance(Account.class, AccountImpl.class);
final Account account2 =
TypedActor.newInstance(Account.class, AccountImpl.class);
final AccountService accountService =
TypedActor.newInstance(AccountService.class, AccountServiceImpl.class);
account1.deposit(1000);
account2.deposit(1000);
System.out.println("Account1 balance is " + account1.getBalance());
System.out.println("Account2 balance is " + account2.getBalance());
System.out.println("Let's transfer $20... should succeed");
accountService.transfer(account1, account2, 20);
Thread.sleep(1000);
System.out.println("Account1 balance is " + account1.getBalance());
System.out.println("Account2 balance is " + account2.getBalance());
    System.out.println("Let's transfer $2000... should not succeed");
accountService.transfer(account1, account2, 2000);
Thread.sleep(6000);
System.out.println("Account1 balance is " + account1.getBalance());
System.out.println("Account2 balance is " + account2.getBalance());
Actors.registry().shutdownAll();
}
}
```

前面代码的操作与部分中的示例相同

8.10，使用交易器，在202页。这个例子的输出是:

>Received Deposit request 1000
>Account1 balance is 1000
>Received Deposit request 1000
>Account2 balance is 1000
>Let's transfer $20... should succeed
>Received Deposit request 20
>Received Withdraw request 20
>Account1 balance is 980
>Account2 balance is 1020
>Let's transfer $2000... should not succeed
>Received Deposit request 2000
>Received Withdraw request 2000
>...insufficient funds...
>Account1 balance is 980
>Account2 balance is 1020

正如我们所期望的，协调类型的actor版本产生的输出类似于事务处理程序版本产生的输出——在失败的事务中所做的任何更改都将被丢弃。

##### Coordinating Typed Actors in Scala

让我们将Java版本转换为Scala。Scala使用特性而不是接口，所以这是我们看到的第一个区别:

```java
trait Account {
def getBalance() : Int
@Coordinated def deposit(amount : Int) : Unit
@Coordinated def withdraw(amount : Int) : Unit
}
trait AccountService {
def transfer(from : Account, to : Account, amount : Int) : Unit
}
```

Account trait的实现是对Java版本的直接翻译:

```java
class AccountImpl extends TypedActor with Account {
val balance = Ref(0)
def getBalance() = balance.get()
def deposit(amount : Int) = {
if (amount > 0) {
balance.swap(balance.get() + amount)
println("Received Deposit request " + amount)
}
}
def withdraw(amount : Int) = {
println("Received Withdraw request " + amount)
if (amount > 0 && balance.get() >= amount)
balance.swap(balance.get() - amount)
else {
println("...insufficient funds...")
throw new RuntimeException("Insufficient fund")
}
}
}
```

同样，让我们实现AccountService类:

```java
class AccountServiceImpl extends TypedActor with AccountService {
def transfer(from : Account, to : Account, amount : Int) = {
coordinate {
to.deposit(amount)
from.withdraw(amount)
}
}
}
```

在Scala版本中，对coordinage()的调用(它定义事务的参与者)比Java版本简单得多。默认情况下，coordinate()方法在返回之前等待其事务完成(成功或回滚)。不过，在事务中调用的方法是单向消息，并且coordinate()不会阻塞它们的结果，而只是阻塞它们的事务来完成。我们可以传递一个可选的参数，比如坐标(wait = false){…告诉方法不要等待事务完成。最后，让我们编写示例代码来使用前面的代码

```java
object UseAccountService {
def main(args : Array[String]) = {
val account1 =
TypedActor.newInstance(classOf[Account], classOf[AccountImpl])
val account2 =
TypedActor.newInstance(classOf[Account], classOf[AccountImpl])
val accountService =
TypedActor.newInstance(
classOf[AccountService], classOf[AccountServiceImpl])
account1.deposit(1000)
account2.deposit(1000)
println("Account1 balance is " + account1.getBalance())
println("Account2 balance is " + account2.getBalance())
println("Let's transfer $20... should succeed")
accountService.transfer(account1, account2, 20)
Thread.sleep(1000)
println("Account1 balance is " + account1.getBalance())
println("Account2 balance is " + account2.getBalance())
println("Let's transfer $2000... should not succeed")
accountService.transfer(account1, account2, 2000)
Thread.sleep(6000)
println("Account1 balance is " + account1.getBalance())
println("Account2 balance is " + account2.getBalance())
Actors.registry.shutdownAll
}
}
```

Scala版本的行为和Java版本一样，正如我们从输出中看到的

>Received Deposit request 1000
>Received Deposit request 1000
>Account1 balance is 1000
>Account2 balance is 1000
>Let's transfer $20... should succeed
>Received Deposit request 20
>Received Withdraw request 20
>Account1 balance is 980
>
>Account2 balance is 1020
>Let's transfer $2000... should not succeed
>Received Deposit request 2000
>Received Withdraw request 2000
>...insufficient funds...
>Account1 balance is 980
>Account2 balance is 1020

### 8.12 Remote Actors

在我们到目前为止编写的参与者示例中，参与者及其客户端都在单个JVM进程中。一个阵营的开发人员认为actor应该真正用于进程间通信，就像Erlang一样。另一个阵营的开发人员使用参与者进行进程内通信，就像我们看到的那样。Scala和Akka迎合了这两个阵营。在Akka中使用远程角色很像使用进程内角色;唯一的区别在于我们如何访问参与者。在后台，Akka使用JBoss Netty和谷歌协议缓冲库来实现无缝远程操作。我们可以跨进程边界将任何可序列化的消息和对任何参与者的引用传递给远程参与者。Akka提供了编程和配置选项来配置主机名、端口号、消息框架大小、安全设置等。有关这些细节，请参阅Akka文档;我们将在本节的示例中简单地使用缺省值。除了创建一个actor之外，我们还需要为远程访问注册它。这将参与者绑定到客户机将用于标识参与者的名称或ID。客户端将使用注册的ID、主机名和端口号来请求参与者。然后它可以向参与者发送单向或双向消息，就像它与本地参与者交互的方式一样。让我们使用远程参与者创建一个示例。在我过去的生活中，我曾经是一个系统管理员。关于系统的各种细节，如可用磁盘空间、性能、CPU利用率等，是管理员经常需要的信息。根据所连接的传感器，我们经常监控诸如室温、湿度水平等细节，以确保高性能计算实验室一直在运行。如果有一个可以从所有远程机器收集这些详细信息的应用程序，那就太好了，所以让我们创建一个。我们将让监视器参与者从远程客户端接收系统信息。不同机器上的客户机可以决定何时将此信息发送到监视器。在本例中，它们将在每次重新运行时发送一些数据。

让我们从监视器开始，它是客户机将与之交互的远程参与者:

```java
public class Monitor extends UntypedActor {
public void onReceive(Object message) {
System.out.println(message);
}
public static void main(final String[] args) {
Actors.remote().start("localhost", 8000)
.register("system-monitor", Actors.actorOf(Monitor.class));
System.out.println("Press key to stop");
System.console().readLine();
Actors.registry().shutdownAll();
Actors.remote().shutdown();
}
}
```

我们创建远程actor的方式与我们创建进程内actor的方式没有区别，我们扩展UntypedActor并实现onReceive()方法。如果我们愿意，我们可以用表格和图表生成复杂的报告，但这里让我们将重点放在参与者上。我们将简单地打印收到的消息。在main()方法中，我们首先通过指示主机名和端口号来启动远程服务。然后，我们使用ID系统监视器为远程访问注册参与者。当需要关闭监视服务时，我们调用将帮助我们终止远程服务的方法。这就是我们在monitor服务中需要的所有代码。现在让我们创建客户机代码

```java
public class Client {
public static void main(final String[] args) {
ActorRef systemMonitor = remote().actorFor(
"system-monitor", "localhost", 8000);
systemMonitor.sendOneWay("Cores: " +
Runtime.getRuntime().availableProcessors());
systemMonitor.sendOneWay("Total Space: " +
new File("/").getTotalSpace());
systemMonitor.sendOneWay("Free Space: " +
new File("/").getFreeSpace());
}
}
```

为了获得对远程参与者的访问，客户端指出远程参与者所在的ID、主机名和端口号。获得对ActorRef的引用后，我们将带有适当信息的消息发送给它，在本例中作为字符串。让我们打开两个命令窗口，在一个命令窗口中运行监视器，在另一个命令窗口中运行客户机。让我们首先启动监视器，然后启动客户机。每次运行客户机时，监视器将打印发送给它的信息，如下所示

>Press key to stop
>Cores: 2
>Total Space: 499763888128
>Free Space: 141636308992

要编译和运行监视器和客户机，我们必须包含一些额外的JAR文件:akka-remote-1.1.2。protobuf-java-2.3.0 jar。netty-3.2.3.Final jar。jar和commons-io-2.0.1.jar。

让我们不要让轻易创造远程参与者欺骗了我们。我们仍然需要确保消息是不可变的(和可序列化的)，并且我们的actor不会修改任何共享的可变状态。

### 8.13 Limitations of the Actor-Based Model

基于actor的模型使其易于编程，具有独立的可变性，但它确实有一些限制。参与者通过消息相互通信。在不强制不可变的语言中，我们必须非常小心地确保消息是不可变的。传递可变消息可能会导致线程安全问题，并最终导致共享可变性的危险。工具可以进化来验证消息是不可变的;在那之前，我们有责任确保不变性。参与者异步运行，但通过传递消息进行协调。参与者的意外失败可能导致饥饿。一个或多个参与者可能正在等待由于失败而永远不会到达的消息。我们必须防御性地编程并在参与者中处理异常，并将错误消息传播给等待响应的参与者。演员不能防止死锁;两个或更多的参与者可以相互等待消息。我们必须仔细设计，以确保参与者之间的协调不会陷入死锁状态。此外，我们应该使用超时来确保我们不会无休止地等待来自参与者的响应。

actor一次只能处理一个消息请求。因此，操作消息和请求响应或状态的消息都按顺序运行。这可能导致只对读取值感兴趣的任务的并发性降低。最好使用粗粒度消息来设计应用程序，而不是使用细粒度消息。此外，我们可以通过使用单向触发和遗忘消息而不是双向消息来设计来减少等待。并不是所有的应用程序都适合基于actor的模型。当我们可以将问题划分为可以独立运行且只需要偶尔通信的部分时，参与者就可以很好地发挥作用。如果需要频繁的交互，或者部件或任务需要进行协调以形成仲裁，则基于actor的模型不适合。我们可能不得不混合使用其他并发模型，或者考虑进行重大的重新设计。

### 8.14 Recap

参与者是单线程的，通过传递消息彼此通信。我们了解到，actors做了以下工作:使其易于工作，隔离的可变性消除根上的同步问题，提供高效的单向消息传递，但也提供了效率较低的可伸缩的发送和等待机制;虽然是单线程的，但它们高效地共享线程池，允许我们发送消息，而且还提供由接口支持的类型版本(在Akka中)，允许我们使用事务与其他参与者进行协调。尽管参与者提供了一个强大的模型，但它们也有局限性。如果我们在设计时不小心，就有可能出现角色饥饿和死锁。另外，我们必须确保消息是不可变的。在下一章中，我们将学习如何使用来自各种JVM语言的actor。

`在商业中，正如在战争中一样，出其不意与武力同等重要`

### Actors in Groovy, Java, JRuby, and Scala

我们在前一章中与演员合作过。在本章中，我们将学习如何在一些流行的JVM语言中使用它们编程。image Erlang推广了基于进程间actor的模型。Scala为进程内通信引入了一个erlang风格的模型。Clojure并不直接支持actor。Rich Hickey在http://clojure上解释了他的原因。org/state #演员。Clojure支持表示共享的可变身份但具有异步、独立、互斥更新的代理。我们将在第249页的附录1 Clojure代理中讨论Clojure代理。我们可以使用来自Groovy的Akka actors;相反，我们将使用GPars，它是一个与Groovy密切相关的并发库。然后，我们将研究如何使用来自其他语言的Akka actor。向参与者发送消息似乎不是什么大事;真正的问题是创建actor，这涉及到从类扩展和重写某些语言中的方法。我们还会遇到一些小问题，但对于活到这本书这么久的人来说，没有什么是无法处理的。专注于与你感兴趣的语言相关的部分，可以跳过其他部分。

#### 9.1 Actors in Groovy with GPars

要创建actor，在Groovy中至少有和Java中一样多的选项。我们可以通过扩展Akka UntypedActor在Groovy中实现Akka actor。Groovy允许我们扩展用其他JVM语言编写的类，因此它直接覆盖了UntypedActor的onReceive()方法。在这个方法中，我们可以检查消息类型并执行适当的操作。我们还可以使用Java可用的其他角色框架之一。然而，在本章中，我们将使用一个更适合Groovy gpar的并发库。GPars是一个用Java编写的库，它为Groovy和Java带来了对编程共识的喜爱。Java的性能和Groovy纯粹的优雅在gpar中熠熠发光。它提供了许多功能，包括异步、并行数组操作、fork/join、代理、数据流和actor。我不会在这里完整地讨论GPars。我将展示如何使用GPars参与者实现前面看到的一些示例。有关GPars的全面讨论，请参阅GPars文档。首先，gpar中的参与者负责基本原则:它们在适当的时间跨越内存障碍，在任何给定的时间只允许一个线程运行它们的接收器方法，并且由消息队列支持。gpar具有不同类型的参与者，并允许我们配置各种参数，比如线程关联的公平性。用于创建和使用actor的API是连贯的，我们将在下面看到。

为了在GPars中创建一个actor，我们从DefaultActor进行扩展，或者简单地将一个闭包传递给actor的actor()方法。如果有一个简单的消息循环，则使用后者。如果我们有更复杂的逻辑，或者想调用actor类的其他方法，那么我们应该采取扩展De- faultActor的路径。我们必须在actor能够接受消息之前启动它;在此之前发送消息的任何尝试都将导致异常。让我们先使用DefaultActor类创建一个actor，然后使用actor()方法。让我们创建一个actor，它将接收一个字符串消息并通过简单地打印消息来进行响应。我们扩展了DefaultActor并实现了它的act()方法，如下面的代码所示。该方法运行消息循环，因此我们调用一个永不结束的loop()方法并调用react()来接收消息。当我们的actor遇到react()时，它不会阻塞线程。当消息到达时，线程池中的一个线程被调度运行我们提供给react()方法的clo- sure。一旦线程完成了提供给react()的代码块，它就会重新运行包含的loop()，再次将参与者放在消息等待队列上。在示例中，我们只是打印接收到的消息以及处理该消息的线程上的信息

```java
class HollywoodActor extends DefaultActor {

def name



public HollywoodActor(actorName) { name = actorName }



void act() { loop {

react { role ->

println "$name playing the role $role"

println "$name runs in ${Thread.currentThread()}"

}

}

}

```

我们创建一个actor实例的方式与创建任何类的实例非常相似，但是要记住调用start()方法。让我们创建一对参与者:

```java
depp = new HollywoodActor("Johnny Depp").start() hanks = new HollywoodActor("Tom Hanks").start()
```

我们还可以使用重载操作符<<来发送消息，而不是使用传统的java风格的方法调用。如果噪音太大，我们可以简单地把信息放在演员旁边，如下例所示:

```java
depp << "Sparrow"

hanks "Gump"
```

默认情况下，参与者在守护进程线程中运行，因此在主方法完成后，代码将立即终止。我们希望它活得足够长，以便我们能够看到正在处理的消息，因此我们可以在末尾添加延迟，或者使用带有超时的join()方法。join()方法将等待参与者终止;因为我们没有使用terminate()调用，所以它们不会终止，但是超时将使主线程脱离阻塞调用。

> [depp, hanks]*.join(1, java.util.concurrent.TimeUnit.SECONDS)

让我们以这个例子为例，运行groovy，并在类路径中提供GPars JAR，如下所示:

> groovy -classpath $GPARS_HOME/gpars-0.11.jar createActor.groovy

参与者通过打印我们要求他们扮演的角色来响应消息。这两个actor并发运行，但是每个actor一次只处理一条消息。在不同的实例中，线程池中的不同线程可能会运行actor方法来响应发送的消息。每次运行代码时，由于并发执行的不确定性，我们可能会在输出中看到略微不同的调用序列。巧合的是，在我设法捕获的一个输出中，我们看到两个actor在不同的实例中共享同一个线程，还有一个actor在消息之间切换线程。

Johnny Depp playing the role Wonka

Johnny Depp runs in Thread[Actor Thread 3,5,main] Tom Hanks playing the role Lovell

Tom Hanks runs in Thread[Actor Thread 3,5,main] Tom Hanks playing the role Gump

Tom Hanks runs in Thread[Actor Thread 2,5,main] Johnny Depp playing the role Sparrow

Johnny Depp runs in Thread[Actor Thread 3,5,main]

如果我们想无限期地处理消息，或者直到一个actor终止，我们使用loop()，正如我们在前面的示例中看到的那样。如果我们希望只运行有限次循环，或者直到满足某些条件，我们可以使用loop()方法的变体，从而获得更多的控制。要运行有限次数，请使用loop(count){}。如果我们想指定一个条件，使用loop ({-&gt;expression})，只要表达式为真，actor就会重复这个循环。我们还可以提供一个可选的闭包，以便在actor发生故障时运行，我们将在下一个示例中看到。这次让我们使用Actors act()方法创建一个actor。我们将调用act()方法，并将消息循环作为闭包发送给它。在前面的示例中，我们使用了loop()，只使用了通过调用react()关闭消息处理主体的方法。在本例中，我们向loop()传递了两个额外的参数。第一个参数可以是条件或计数。这里我们传递3来告诉actor响应三条消息，然后终止。第二个参数是可选的，是在actor终止时执行的一段代码(作为闭包给出)。

```java
depp = Actors.actor {

loop(3, { println "Done acting" }) {

react { println "Johnny Depp playing the role $it" }

}

}
```

行动者被设定为只接收三条消息。让我们发送比预期更多的消息，看看会发生什么:

```java
depp << "Sparrow"

depp << "Wonka"

depp << "Scissorhands"

depp << "Cesar"

depp.join(1, java.util.concurrent.TimeUnit.SECONDS)
```

当我们运行前面的示例时，我们将看到actor收到三个mes- sages，然后终止。发送给参与者的最后一条消息将被忽略，因为参与者已经在此时终止了。

```java
Johnny Depp playing the role Sparrow Johnny Depp playing the role Wonka

Johnny Depp playing the role Scissorhands Done acting
```

我们看到了GPars在创建参与者和控制消息循环方面提供的灵活性。现在让我们看看双向消息传递的选项。

**Sending and Receiving Messages**发送和接收消息

除了单向消息之外，GPars还允许我们发送消息和接收响应。我们可以使用一个带有可选超时的阻塞sendAndWait()，或者我们可以使用非阻塞的sendAndContinue()，它接受一个闭包，当应答到达时将调用该闭包。这个非阻塞版本将使我们更容易向多个参与者发送消息，然后等待他们的响应。为了方便向消息发送方发送回复，Actor提供了一个方便的发送方属性。我们将在发送方上调用send()方法来发送回复，如下一个示例所示

```java
fortuneTeller = Actors.actor { loop {

react { name ->

sender.send("$name, you have a bright future")

}

}

}
```

算命的演员收到一个名字和回答与一个蹩脚的财富圣人。从用户的角度来看，我们调用sendAndWait()来发送一个响应的名称和block:

```java
message = fortuneTeller.sendAndWait("Joe", 1, TimeUnit.SECONDS) println message
```

尽管sendAndWait()方法很简单，但如果我们想一次发送多条消息，它就不起作用了。我们可以使用非阻塞的sendAndContinue()发送多条消息，但是一旦发送了消息，我们需要一种方法来阻止所有响应的到达。我们可以使用CountDownLatch。当收到答复时，我们可以对那个门闩倒数。在闩锁上阻塞的主线程可以在所有响应到达时继续执行。如果在此之前发生超时，我们可以通过await()方法的返回值来处理:

```java
latch = new CountDownLatch(2)



fortuneTeller.sendAndContinue("Bob") { println it; latch.countDown() } fortuneTeller.sendAndContinue("Fred") { println it; latch.countDown() }



println "Bob and Fred are keeping their fingers crossed"



if (!latch.await(1, TimeUnit.SECONDS))

println "Fortune teller didn't respond before timeout!"

else

println "Bob and Fred are happy campers"
```

示例运行的输出如下所示:

Joe, you have a bright future Bob, you have a bright future

Bob and Fred are keeping their fingers crossed Fred, you have a bright future

Bob and Fred are happy campers

我们可以看到，对于双向消息传递，Groovy的表现力以及闭包的便利性在gpar中发挥得很好。现在让我们超越简单的字符串消息，看看发送其他类型的消息。

**Handling Discrete Messages**处理离散消息

到目前为止，消息并不仅限于GPars示例中使用的简单字符串。我们可以将任何对象作为消息发送，但是我们需要确保它们是不可变的。让我们创建一个模拟股票交易的示例actor;它可以处理几种不同类型的消息。但是首先我们需要定义消息。我们可以使用groovy定义的不可变注释来确保消息是不可变的。这个注释不仅确保所有字段都是final;它还引入了一个构造函数(加上一些方法)。

```java
@Immutable class LookUp {

String ticker

}



@Immutable class Buy {

String ticker

int quantity

}


```

该查询包含一个股票代码并表示请求股票价格的消息。Buy类持有一个股票代码和我们想买的股票数量。我们的actor需要对这两个消息采取不同的操作。让我们现在开始:

```java
trader = Actors.actor { loop {

react { message ->

if(message instanceof Buy)

println "Buying ${message.quantity} shares of ${message.ticker}"



if(message instanceof LookUp) sender.send((int)(Math.random() * 1000))

}

}

}


```

当actor接收到一条消息时，我们使用运行时类型identifi- cation s (RTTI s) instanceof检查该消息是否是我们期望的消息类型之一的实例。我们会根据消息类型采取必要的行动，如果消息是我们没有预料到的，我们会忽略它;另外，如果接收到无法识别的消息，也可以抛出异常。在本例中，如果消息是Buy，我们只打印一条消息，如果是查询，我们返回一个表示股票价格的随机数。正如我们在这里所看到的，使用具有不同类型mes-圣贤的actor没有什么特殊之处

trader.sendAndContinue(**new** LookUp(*"XYZ"*)) { println *"Price of XYZ sock is* $it*"*

}

trader << **new** Buy(*"XYZ"*, 200)

trader.join(1, java.util.concurrent.TimeUnit.SECONDS)

The output from the code is shown next:

Price of XYZ sock is 27 Buying 200 shares of XYZ

这很简单，但是我们的参与者处理消息的方式可以改进。我们可以利用Groovy的类型系统来执行分支，而不是使用条件语句来检查类型。为此，我们将使用DynamicDis- patchActor作为基本actor类，如下所示:

```java
class Trader extends DynamicDispatchActor {

void onMessage(Buy message) {

println "Buying ${message.quantity} shares of ${message.ticker}"

}

def onMessage(LookUp message) { sender.send((int)(Math.random() * 1000))
}
}
```

我们从DynamicDispatchActor扩展了actor，并为actor处理的每种类型的消息重载了onMessage()方法。基类Dynam- icDispatchActor将检查消息类型，然后分派actor的适当的onMessage()。因为我们定义了一个单独的类，所以我们必须记住调用start()来初始化actor消息循环，如下所示:

```java
trader = new Trader().start() trader.sendAndContinue(new LookUp("XYZ")) {

println "Price of XYZ sock is $it"

}
trader << new Buy("XYZ", 200)
trader.join(1, java.util.concurrent.TimeUnit.SECONDS)
```

为了提供离散的消息处理程序，我们不必创建单独的类。相反，我们可以将一个处理方法链的闭包传递给DynamicDispatchActor的构造函数，如下所示:

```java
trader = new DynamicDispatchActor({ when { Buy message ->

println "Buying ${message.quantity} shares of ${message.ticker}"

}
when { LookUp message -> sender.send((int)(Math.random() * 1000))
}
}).start()
```

作为构造函数参数发送的闭包具有流畅的语法，其中每种类型的消息都在when子句中表示。乍一看，这看起来很像条件语句if，但有一些显著的区别。首先，我们没有RTTI的坏味道。其次，这些when子句在幕后转换为特定的消息处理程序。无效或未处理的消息会导致异常，而对于显式条件语句，我们必须编写代码来处理。

**Using GPars**使用GPars

我们掌握了创建和协调GPars参与者的窍门。让我们使用这些知识来重写文件大小程序。我们在第182页的第8.6节协调角色中讨论了该程序的基于角色的设计，之前我们看到了使用Scala和Java的实现。让我们比较一下该设计的Groovy和GPars实现。文件大小程序设计要求有两个角色，SizeCollector和文件处理器。每个文件处理器将接收一个目录作为消息，并将该目录中的文件大小和子目录列表作为消息发送。演员是主角，是策划者。我们需要定义消息类型，因此让我们首先从它开始

```java
@Immutable class RequestAFile {} @Immutable class FileSize { long size }

@Immutable class FileToProcess { String fileName }
```

我们将消息类型RequestAFile、FileSize和FileToProcess定义为im可变类。接下来，我们需要创建文件处理器。我们将扩展DefaultActor，为该类提供actor功能。此actor需要向SizeCollector actor注册以接收目录。我们可以在afterStart()方法中做到这一点。GPars提供事件方法，如afterStart()和afterStop()，就像Akka的preStart()和postStop()方法一样。afterStart()在actor启动后但在第一个消息被处理之前被调用。让我们实现文件处理器actor

```java
class FileProcessor extends DefaultActor {
def sizeCollector
public FileProcessor(theSizeCollector) { sizeCollector = theSizeCollector }
void afterStart() { registerToGetFile() }
void registerToGetFile() { sizeCollector << new RequestAFile() }
    void act() { loop {

react { message ->

def file = new File(message.fileName) def size = 0

if(!file.isDirectory()) size = file.length()

else {

def children = file.listFiles()

if (children != null) { children.each { child ->

if(child.isFile())

size += child.length()

else

sizeCollector << new FileToProcess(child.path)

}

}

}

sizeCollector << new FileSize(size) registerToGetFile()

}

}

}

}
```

SizeCollector在与文件处理器通信时将接收三种不同类型的消息。因为我们有三种不同类型的消息，所以如果我们从DynamicDispatchActor扩展来实现这个actor，就可以避免使用switch语句并编写三个独立的方法。与此设计的前一个实现一样，我们维护要处理的文件列表、空闲文件处理器列表、等待访问的文件计数以及要计算的最重要的总文件大小。在接收文件和文件请求到达时，我们将它们分派到空闲的文件处理器。让我们看一下SizeCollector的代码

```java
class SizeCollector extends DynamicDispatchActor {

def toProcessFileNames = [] def idleFileProcessors = []

def pendingNumberOfFilesToVisit = 0

def totalSize = 0L

final def start = System.nanoTime()



def sendAFileToProcess() {

if(toProcessFileNames && idleFileProcessors) { idleFileProcessors.first() <<

new FileToProcess(toProcessFileNames.first()) idleFileProcessors = idleFileProcessors.tail() toProcessFileNames = toProcessFileNames.tail()
}

}



void onMessage(RequestAFile message) { idleFileProcessors.add(sender) sendAFileToProcess()

}



void onMessage(FileToProcess message) { toProcessFileNames.add(message.fileName) pendingNumberOfFilesToVisit += 1 sendAFileToProcess()

}



void onMessage(FileSize message) { totalSize += message.size pendingNumberOfFilesToVisit -= 1 if(pendingNumberOfFilesToVisit == 0) {

def end = System.nanoTime() println "Total size is $totalSize"

println "Time taken is ${(end - start)/1.0e9}" terminate()

}

}

}
```

当pendingNumberOfFilesToVisit降为0时，actor将打印总的文件大小和所花费的时间。然后它终止自己。是时候使用这些角色了，这是主要代码的工作:

```java
sizeCollector = new SizeCollector().start() sizeCollector << new FileToProcess(args[0])

100.times { new FileProcessor(sizeCollector).start() } sizeCollector.join(100, java.util.concurrent.TimeUnit.SECONDS)
```

主代码将一个SizeCollector和100个文件处理器设置为运动状态，并使用超时等待SizeCollector终止。运行/usr目录代码的输出如下所示:

Total size is 3793911517 Time taken is 8.69052900

Groovy-GPars版本报告的文件大小与其他版本报告的文件大小相同，所花费的时间也非常相似。Groovy版本在语法上的简洁性也与Scala版本相当。总的来说，这是非常棒的。

**Data Flow**

数据流编程在几十年前风靡一时。各公司都急于构建数据流处理器，以利用应用程序中的并行性，而不受编程构造的限制。一旦这些处理器需要的数据可用，它们就可以进行计算。通过应用程序的数据流将决定要执行的指令集，而不是von Newmann风格的指令执行。尽管数据流处理器从未起飞，但数据流编程再次吸引了人们的注意力。在数据流程序中，计算或任务会阻塞数据，并在它们所依赖的所有数据可用时立即运行。没有同步或锁定。只要我们保持任务的纯粹性(即，它们是幂等的，没有副作用，不改变任何数据;换句话说，它们在风格上是功能性的)，正如我们将看到的，数据流程序非常容易编写。Akka提供了用于数据流编程的API，但是在本节中，我们将使用fluent GPars API进行数据流编程。我们的任务是从不同的网站抓取内容，并报告每个网站内容的大小。打印或报告内容大小的代码必须按顺序运行，因为它涉及到更新控制台或GUI，这些控制台或GUI通常是单线程的。但是，我们可以同时从网站获取数据。一旦数据可用，就可以运行用于打印信息的代码。让我们先编写代码，然后再讨论细节

```java
def fetchContent(String url, DataFlowVariable content) { println("Requesting data from $url")

content << url.toURL().text println("Set content from $url")

}
content1 = new DataFlowVariable() content2 = new DataFlowVariable()

task { fetchContent("http://www.agiledeveloper.com", content1) } task { fetchContent("http://pragprog.com", content2) }

println("Waiting for data to be set")

println("Size of content1 is ${content1.val.size()}") println("Size of content2 is ${content2.val.size()}")
```

GPars中的DataFlowVariable类是一个写一次的变量，可以读取任意次数。第一次读取将阻塞，如果需要，直到数据可用。后续读取将返回预先写入的值。变量在第一次写入时被绑定到一个值，任何尝试写入绑定变量的操作都会导致异常。由于读取块直到数据绑定，因此不需要同步来从DataFlowVariable获取数据。在本例中，`fetchContent()`方法接收一个URL和一个`DataFlowVar`i可选内容作为参数。它通过使用toURL()和文本调用的优雅组合来获取网站的内容。然后使用&lt;&lt;将内容写入`DataFlowVariable`类操作符。在定义了这个方法之后，我们创建了两个数据流变量来保存我们将要访问的两个站点的内容。然后使用`DataFlow`类的task()方法创建两个数据流任务。这些任务中的每一个都可以并发运行。当从这两个任务访问网站时，我们调用第一个`DataFlowVariable` content1的val属性。这将阻塞执行，直到数据可用为止。一旦数据可用，我们打印内容的大小，我们可以从下面的输出中看到

```java
Waiting for data to be set
Requesting data from http://pragprog.com Requesting data from http://www.agiledeveloper.com Set content from http://www.agiledeveloper.com Size of content1 is 2914
Set content from http://pragprog.com Size of content2 is 13003
```

尽管示例相当简单，但它揭示了数据流程序的本质。执行顺序完全由可用性和数据流决定。DataFlowVariable消除了同步的需要;然而，它的一次性写的性质是相当有限的。GPars提供了一些替代方案，比如数据流队列。它允许我们将多个读取器或单个读取器连接起来，因为流中的每个数据值都引用GPars文档以获取各种选项。我们使用task来创建异步任务。这对于少量任务来说很方便，但是如果我们需要创建大量的任务，我们就需要使用一个线程池来管理这些任务的线程。GPars为此提供了一个线程组类DefaultPGroup。我们将不调用DataFlow的静态方法task()，而是使用具有相同名称的组s实例方法。让我们使用数据流API重写文件大小程序，如下所示

```java
class FileSize {

private final pendingFiles = new DataFlowQueue() private final sizes = new DataFlowQueue() private final group = new DefaultPGroup()
def findSize(File file) { def size = 0 if(!file.isDirectory())

size = file.length()

else {

def children = file.listFiles()

if (children != null) { children.each { child ->

if(child.isFile())

size += child.length()

else {

pendingFiles << 1

group.task { findSize(child) }
}
}
}
}
pendingFiles << -1 sizes << size
}
def findTotalFileSize(File file) { pendingFiles << 1
group.task { findSize(file) }
int filesToVisit = 0 long totalSize = 0 while(true) {
totalSize += sizes.val
if(!(filesToVisit += (pendingFiles.val + pendingFiles.val))) break
}
totalSize
}
}
start = System.nanoTime()
totalSize = new FileSize().findTotalFileSize(new File(args[0])) println("Total size $totalSize")
println("Time taken ${(System.nanoTime() - start) / 1.0e9}")
```

我们创建了两个DataFlowQueue实例，一个用于保存活动文件访问的选项卡，另一个用于接收文件和子目录的大小。我们想在一个线程池中运行我们的任务，因此我们创建一个DefaultPGroup。在findSize()方法中，我们将给定目录中所有文件的大小加起来，并使用&lt;&lt;操作符。对于在给定目录下找到的每个子目录，我们创建一个新任务，通过调用group.task来调用该目录。在开始一个任务之前，我们将一个1放入挂起的文件中，当一个任务完成时，我们将一个-1放入文件中。这将允许我们跟踪要探索目录的所有任务何时完成。在findTotalFileSize()方法中，我们创建一个任务来研究给定的文件/direc- tory。然后，我们从任务和在探索每个子目录时创建的所有子任务中接收文件大小。当我们确定没有更多挂起的目录要探索时，循环终止。我们最终从这个方法返回总的文件大小。最后的代码是执行这些函数并计时。让我们运行代码并观察输出

Total size is 3793911517 Time taken is 9.46729800

文件大小程序非常适合数据流方法。代码非常简单，性能与其他解决方案相当。

#### 9.2 Java Integration   Java集成

如前所述，在Java中有一些基于actor的并发的选择。我们从ActorFoundary、Actorom、Actors Guild、Akka、FunctionalJava、Kilim、Jetlang等等中选。在第8章中，我们相当频繁地使用了Akka，在163页中，我们更倾向于孤立的可变性。Akka是用Scala编写的，但是他们已经做了相当不错的工作，为我们在Java中使用提供了方便的接口。要使用Akka，请参考Akka文档和本书中给出的示例。对于其他库，请参考它们各自的文档。

#### 9.3 JRuby Akka Integration

为了在JRuby中使用Akka，我们将遵循在Java中所做的相同操作。主要的区别是，我们将能够受益于JRuby的简洁性。在第8章中，我们已经熟悉了Akka的功能和它强大的API，在163页，它支持独立的可变性。让我们直接进入第182页中关于JRuby中文件大小程序的第8.6节“协调参与者”中的图像实现。请记住，我们需要一组消息类型、两种actor类型和一些主代码来执行这些操作。让我们先从消息类型开始

```java
class RequestAFile; end
class FileSize attr_reader :size def initialize(size)
@size = size
end end
class FileToProcess attr_reader :file_name def initialize(file_name)
@file_name = file_name
end end
```

这些表示消息的类是我们前面看到的Java版本中相应类的直接转换。编写文件处理器actor涉及到更多的工作。当我们扩展Java类时，JRuby不允许我们更改构造函数签名。基于Akka actor的类UntypedActor有一个无参数构造函数，但是我们的文件处理器需要为SizeCollector接受一个参数。如果我们继续在这个类中编写initialize()方法，就会遇到运行时错误。诀窍是调用带有括号的super()()来解决这个问题。除此之外，这个类的其余部分只是将Java代码简单地转换为JRuby

```java
require 'java'

java_import java.lang.System java_import 'akka.actor.ActorRegistry' java_import 'akka.actor.Actors' java_import 'akka.actor.UntypedActor'



class FileProcessor < UntypedActor attr_accessor :size_collector



def initialize(size_collector)

super()

@size_collector = size_collector

end



def preStart



register_to_get_file

end



def register_to_get_file @size_collector.send_one_way(RequestAFile.new, context)

end



def onReceive(fileToProcess)

file = java.io.File.new(fileToProcess.file_name) size = 0

if !file.isDirectory() size = file.length()

else

children = file.listFiles()

if children != nil children.each do |child|

if child.isFile()

size += child.length()

else

@size_collector.send_one_way(FileToProcess.new(child.getPath()))

end end

end end

@size_collector.send_one_way(FileSize.new(size)) register_to_get_file

end end
```

接下来，让我们准备好第二个actor SizeCollector。当我们使用UntypedActor的actor_of()方法的一个版本创建这个actor的实例时，我们会得到一个错误，即这个类缺少create()方法。让我们提供那个工厂方法。除了这一点，其余的代码是直接翻译的Java版本到JRuby:

```java
class SizeCollector < UntypedActor

def self.create(*args) self.new(*args)
end
def initialize @to_process_file_names = [] @file_processors = [] @fetch_size_future = nil

@pending_number_of_files_to_visit = 0
@total_size = 0
@start_time = System.nano_time
end
def send_a_file_to_process
if !@to_process_file_names.empty? && !@file_processors.empty? @file_processors.first.send_one_way(

FileToProcess.new(@to_process_file_names.first)) @file_processors = @file_processors.drop(1) @to_process_file_names = @to_process_file_names.drop(1)
end end
def onReceive(message)

case message

when RequestAFile

@file_processors << context.sender.get send_a_file_to_process
when FileToProcess

@to_process_file_names << message.file_name @pending_number_of_files_to_visit += 1 send_a_file_to_process



when FileSize

@total_size += message.size @pending_number_of_files_to_visit -= 1 if @pending_number_of_files_to_visit == 0

end_time = System.nano_time()

puts "Total size is #{@total_size}"

puts "Time taken is #{(end_time - @start_time)/1.0e9}" Actors.registry().shutdownAll()

end end

end end
```

作为最后一步，让我们编写代码来练习这两个参与者:

```java
size_collector = Actors.actor_of(SizeCollector).start() size_collector.send_one_way(FileToProcess.new(ARGV[0]))



100.times do

Actors.actor_of(lambda { FileProcessor.new(size_collector) }).start

end
```

Actors类提供了一些Java术语中的actor_of()或actorOf()方法。我们使用该方法的版本，该版本要求具有create()方法的工厂创建actor SizeCollector的实例。我们在SizeCollector中提供的静态create()方法在这里使用。因为我们需要在构造时为FileProcessor的实例设置一个参数，所以我们使用actor_of()的版本，该版本接受一个在内部创建actor实例的闭包。JRuby允许我们在需要闭包的地方流畅地传递lambdas，因此我们在这里利用了这一点。剩下的代码只是将Java版本简单地转换为JRuby。让我们以文件大小程序的JRuby版本为例

/usr directory:

Total size is 3793911517 Time taken is 9.69524

为了实现JRuby版本，我们不得不经历一些小麻烦。一旦我们知道了如何绕过JRuby的继承限制，我们就能够利用Ruby的简洁性和流畅性。

### 9.4 Choices in Scala

在Scala中，我们有一些基于actor的并发选项;其中一个是scala。随Scala安装提供的actor库。Akka库提供了更好的性能，更适合企业应用程序(请参阅附录2,Web资源，255页)。要使用Scala actor库，请参考Scala文档、Scala [OSV08]编程或Scala [Sub09]编程。要使用Akka，请参考Akka的文档和在第8章中提供的示例，在163页中支持独立的可变性。

### 9.5 Recap

我们可以在JVM上用任何语言编写并发程序。在本章中，我们了解到:Akka Java API可以很容易地从包括JRuby在内的多种JVM语言中使用。GPars为并发编程带来了Groovy的优雅和Java性能。模式匹配消息不是唯一的选择;我们可以根据gpar中的消息类型重载消息处理程序方法。当混合使用api时，我们会遇到一些语言级的集成问题，但没有什么是不可克服的。在编写并发程序时，我们可以继续享受这些现代JVM语言的表达性和简明性语法。我们已经走了很长的路!在下一章中，我们将以并发编程的建议来结束。