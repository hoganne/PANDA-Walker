# JVM-GC一篇

要谈GC的作用范围，首先要谈JVM的内存结构，JVM内存中主要有以下几个区域：堆、方法区（JVM规范中的叫法，Hotspot大致对应的是Metaspace）、栈、本地方法栈、PC等，其中GC主要作用在堆上，如下图所示：

![img](https://img-blog.csdn.net/20171206092538527?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY29kZWphcw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

JVM内存结构

其中堆和方法区是所有线程共享的，其他则为线程独有，HotSpot JVM使用基于分代的垃圾回收机制，所以在堆上又分为几个不同的区域（在G1中，各年龄代不再是连续的一整片内存，为了描述方便，这里还使用传统的表示方法），具体如下图所示：

![img](https://img-blog.csdn.net/20171206092554974?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY29kZWphcw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

![阿里P8技术官：搞 Java开发的，看懂JVM的GC日志真的很重要](https://pic2.zhimg.com/v2-01c63e72e0583d34a58509baffcb58e0_1440w.jpg?source=172ae18b)

JVM堆中的分区

### 二、GC负责的事情

GC的发展是随着JDK（Standard Edition）的发展一步步发展起来的，垃圾回收（Garbage Collection）可以说是JDK里最影响性能的行为了。GC做的事情，说白了就是「通过对内存进行管理，以保障在内存足够的时候，程序可以正常的使用内存」。具体而言，GC通常做的事情有以下3个：

#### 1. 分配对象和对象的年龄管理

通常而言，GC需要管理「在上图中的年轻代（Young）分配对象，然后通过一系列的年龄管理，将之销毁或晋升到老年代（Tenured）中去」的过程。这个过程会伴随着若干次的Minor GC(小gc)。

对于普通的对象而言，分配内存是一件很简单而且快速的事情。在对象还未创建时，其所占内存大小通过类的元数据就可以确定，而Eden区域的内存可以认为是连续的，所以给对象分配内存要做的只是在上图中Eden区域中把指针移动相应的长度，并将地址返回给对象的引用即可。当然实际的过程比这个复杂，在下文中会提到。

不过，有时候一个对象会直接在老年代中创建，这个点也会在后边提到。

#### 2. 在老年代中进行标记

老年代的GC算法可以大致是认为是一个标记-整理（Mark-Compact，其实是混合了标记-清理，标记-复制和标记-整理）算法，所以老年代的垃圾清理首先要做的就是在老年代对存活的对象（可达性分析，关于不同的可达性可以参考[JDK解构 - Java中的引用和动态代理的实现](http://www.jianshu.com/p/77dfeccac85d)）进行标记，对于寻求大吞吐量的服务器应用来说，这个过程往往需要是并发的。

标记的过程发生在Major GC被触发之后，不同的GC对于MajorGC的触发条件和标记过程的实现也不尽相同。

#### 3. 在老年代中进行压缩

在上一条的基础上，将还存活的对象进行压缩（CMS和G1的行为与此有些不同之处），压缩的过程就是将存活的对象从老年代的起点进行挨个复制，使得老年代维持在一片连续的内存中，消除内存碎片，对于内存分配速度的提升会有很大的帮助。

### 三、GC的种类

Hotspot会根据宿主机的硬件特性和操作系统类型，将之分为客户端型（client-class）或者服务器型（server-class），如果是服务器型主机，Java 9之前默认使用Parallel（并行） GC，Java 9中默认使用G1。对于服务器型主机的选择标准是「CPU核心数大于1，内存大于2GB」，所以现在大部分的主机都可以认为是服务器型主机。

**这里讨论的所有GC都是基于分代垃圾回收算法的。**

#### 1. Serail

Serail是最早的一款GC，它只使用一个线程来做所有的Minor和Major垃圾回收。它在运行时，其他所有的事情都会暂停。其工作方式十分简单，在需要GC的安全点，它会停止所有其他线程（Stop-The-World），对年轻代进行标记-复制，或对老年代进行标记-整理。

![img](https://pic2.zhimg.com/80/v2-b857780394973e621814d10b7df8c371_720w.jpg)

可以使用JVM参数`-XX:+UseSerialGC`来开启此GC，当使用此参数时，年轻代和老年代将都是用Serial来做垃圾回收。在年轻代使用标记-复制算法，将Eden中存活的对象和非空的Suvivor区（From）中存活的对象复制到空的Suvivor区（To）中去，同时将一部分Suvivor中的对象晋升到老年代去。在老年代则使用标记-整理算法。

看起来Serial古老而简陋，但在宿主机资源紧张或者JVM堆很小的情况下（比如堆内存大小只有不到100M），Serial反而可以达到更好的效果，因为其他并发或并行GC都是基于多线程的，会带来额外的线程切换和线程间通信的开销。

#### 2. Parallel/Throughput

Parallel在Java 9之前是服务器型宿主机中JVM的默认GC，其垃圾回收的算法和Serial基本相同，不同之处在与它使用多线程来执行。由于使用了多线程，可以享受多核CPU带来的优势，可以通过参数-XX:+UseParallelGC -XX:+UseParallelOldGC显示指定。

#### 3. CMS

![img](https://images2015.cnblogs.com/blog/603942/201610/603942-20161023104733982-2079371914.png)

CMS和G1都属于「Mostly Concurrent Mark and Sweep Garbage Collector」，可以使用-XX:+UseConcMarkSweepGC参数打开。CMS的年轻代垃圾回收使用的是`Parallel New`来做，其行为和Parallel中的差不多相同，他们的实现上有一些不同的地方，比如Parallel可以自动调节年轻代中各区的大小，用的是广度优先搜索等。

老年代使用CMS，CMS的回收和Parallel也基本类似，不同点在与，CMS使用的更复杂的可达性分析步骤，并且不是每次都做压缩的动作，这样达到的效果就是，Stop-The-World的时长会降低，JVM运行中断的时间减少，适合在对延迟敏感的场景下使用。

CMS在Java 9中已经被废弃，但了解CMS的行为对理解G1会有一些帮助，所以这里还是会简单的叙述一下。CMS的步骤大致如下：

1. 第一次标记（first mark）
   从`GC Roots`开始，找到它们在老年代中第一个可达的对象，这些对象或者是直接被GC Roots引用，或者通过年轻代中的对象被GC Roots引用。这一步会`Stop-The-World`。
2. 并发标记
   在第一次标记的基础上，进一步进行可达性分析，从而标记存活的对象。这一步叫「并发」标记，是因为做标记的线程是和应用的工作线程并发执行的，也就是说，这一步不会Stop-The-World。
3. 第二次标记
   在并发标记的过程中，由于程序仍在执行，会导致在并发标记完成后，有一些对象的可达性会发生变化，所以需要再次对他们进行标记。这一步会Stop-The-World。
4. 清理
   回收不使用的对象，留作以后使用。

CMS的设计比较复杂，所以也带来了一些问题，比如浮动垃圾（Floating Garbage，指的是在第一步标记可达，但在第二步执行的同时已经不可达的对象），由于不做老年代压缩，导致老年代会出现较多的内存碎片。

#### 4. G1

由于「引入了并发标记」和「不做老年代压缩」，CMS可以带来更好的响应时延表现，但同时也带来了一些问题。G1本身就是作为CMS的替代品出现的，在它的使用场景里，堆不再是连续的被分为上文所说的各种代，整个堆会被分为一个个区域（Region），每个区域可以是任何代。如下图所示：

![img](https://img-blog.csdn.net/20171206092630585?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY29kZWphcw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

使用G1的JVM某时刻的堆内存

其中有红色方框的为年轻代（标S的为Survivor区域，其他为Eden），其他蓝色底的区域为老年代（标H的为大对象区域，用以存储大对象）。

### 四、G1的一些细节

G1与以上3种GC相同，也是基于分代的垃圾回收器。它的垃圾回收步骤可以分为年轻代回收（Young-only phase，类似于Minor GC）和混合垃圾回收阶段（Space-reclamation phase）。下图是Oracle文档中对于此两个阶段的示意图：

![img](https://img-blog.csdn.net/20171206092653969?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY29kZWphcw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

##### G1设计目标和适用对象

G1的设计目标是让大型的JVM可以动态的控制GC的行为以满足用户配置的性能目标。G1会在平衡吞吐和响应时延的基础上，**尽可能**的满足用户的需求。它适用的JVM往往有以下特征：

1. 堆的大小可能达到数十G（或者更大），同时存活的对象数量也很多。
2. 对象的分配和年龄增长的行为随着程序的运行不断的变化
3. 堆上很容易形成碎片
4. 要求较少的Stop-The-World暂停时间，通常小于数百毫秒

##### 对G1的行为进行测试

如果想要看垃圾回收的具体执行过程，可以使用虚拟机参数`-Xlog:gc*=debug`或者`-Xlog:gc*=info`，前一个会打印更多的细节。注意传统的VM参数`-XX:+PrintGCDetails`在Java9中已经废弃，会有Warning信息。可以使用以下代码中的程序去测试：

```java
static int TOTAL_SIZE = 1024 * 5;

static Object[] floatingObjs= new Object[TOTAL_SIZE];

static LinkedList<Object> immortalObjs = new LinkedList<Object>();

//释放浮动垃圾


synchronized static void renewFloatingObjs() {

    System.err.println("存活对象满===========================");

    if (floatingSize + 5 >= TOTAL_SIZE) {

        floatingObjs= new Object[TOTAL_SIZE];

        floatingSize = 0;

    }

}

//添加浮动垃圾

synchronized static void addObjToFloating(Object obj) {

    if (floatingSize++ < TOTAL_SIZE) {

        floatingObjs[floatingSize] = obj;

        if (immortalSize++ < TOTAL_SIZE) {

            immortalObjs.add(obj);

        } else {

            immortalObjs.remove(new Random().nextInt(TOTAL_SIZE));

            immortalObjs.add(obj);

        }

    }

}

public static void main(String[] args) {

    for (int i = 0; i < 10; i++) {

        new Thread(() -> {
            while (true) {
                try {
                    
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Byte[] garbage = new Byte[1024 * (1 + new Random().nextInt(20))];
                if (new Random().nextInt(20) < 2) {

                    if (floatingSize + 5 >= TOTAL_SIZE) {
                        renewFloatingObjs();
                    }
                    addObjToFloating(garbage);
                }
            }
        }).start();
    }
}
```

在这段代码中，模拟了常规程序的使用情况。不断的生成新的大小不等的对象，这些对象中会有大约10%的机会进入浮动垃圾`floatingObjs`，浮动垃圾会被定期清除。同时会有一部分的对象进入`immortalObjs`，这些对象被释放的机会更少，它们大概率将成为老年代的常住用户。

从上边的测试可以得到如下GC日志1，这是一次完整的年轻代GC，从中可以看到，默认的区域大小为1M，同时将开始一次`Full GC`，其格式大致为`[<虚拟机运行的时长>][<日志级别>][<标签>] GC(<GC的标识>) <其他信息>`

```
//日志1
[0.014s][info][gc,heap] Heap region size: 1M

//一次完整的年轻代垃圾回收，伴随着一次暂停

[12.059s][info ][gc,start             ] GC(18) Pause Young (G1 Evacuation Pause)                            
[12.059s][info ][gc,task              ] GC(18) Using 8 workers of 8 for evacuation                          
[12.078s][info ][gc,phases            ] GC(18)   Pre Evacuate Collection Set: 0.0ms                         
[12.078s][info ][gc,phases            ] GC(18)   Evacuate Collection Set: 18.6ms                            
[12.079s][info ][gc,phases            ] GC(18)   Post Evacuate Collection Set: 0.3ms                            
[12.079s][info ][gc,phases            ] GC(18)   Other: 0.3ms                           

[12.079s][info ][gc,heap              ] GC(18) Eden regions: 342->0(315)                            
[12.079s][info ][gc,heap              ] GC(18) Survivor regions: 38->35(48)                         
[12.079s][info ][gc,heap              ] GC(18) Old regions: 425->463                            
[12.079s][info ][gc,heap              ] GC(18) Humongous regions: 0->0                   
[12.078s][debug][gc,ergo,ihop         ] GC(18) Request concurrent cycle initiation (occupancy higher than threshold) occupancy: 485490688B allocation request: 0B threshold: 472331059B (45.00) source: end of GC

[12.078s][debug][gc,ihop              ] GC(18) Basic information (value update), threshold: 472331059B (45.00), target occupancy: 1049624576B, current occupancy: 521069456B, recent allocation size: 20640B, recent allocation duration: 817.38ms, recent old gen allocation rate: 25251.50B/s, recent marking phase length: 0.00ms

[12.078s][debug][gc,ihop              ] GC(18) Adaptive IHOP information (value update), threshold: 472331059B (47.37), internal target occupancy: 997143347B, occupancy: 521069456B, additional buffer size: 367001600B, predicted old gen allocation rate: 318128.08B/s, predicted marking phase length: 0.00ms, prediction active: false

[12.078s][debug][gc,ergo,refine       ] GC(18) Updated Refinement Zones: green: 15, yellow: 45, red: 75

[12.079s][info ][gc,heap              ] GC(18) Eden regions: 342->0(315)

[12.079s][info ][gc,heap              ] GC(18) Survivor regions: 38->35(48)

[12.079s][info ][gc,heap              ] GC(18) Old regions: 425->463

[12.079s][info ][gc,heap              ] GC(18) Humongous regions: 0->0

[12.079s][info ][gc,metaspace         ] GC(18) Metaspace: 5172K->5172K(1056768K)

[12.079s][debug][gc,heap              ] GC(18) Heap after GC invocations=19 (full 0):       
[12.079s][info ][gc                   ] GC(18) Pause Young (G1 Evacuation Pause) 803M->496M(1001M) 19.391ms                             

[12.079s][info ][gc,cpu               ] GC(18) User=0.05s Sys=0.00s Real=0.02s          
```

##### 年轻代回收（Young-only）

对于纯粹的年轻代回收，其算法很简单，与Parallel和CMS的年轻代十分类似，这是一个多线程并行执行的过程，同样需要Stop-The-World（对应上边日志中的`Pause Young`），停下来所有的工作线程，然后将Eden上存活的对象拷贝到Suvivor区域，这里会将很多个对象从多个不同的区域拷贝到少数的几个区域内，所以这一步在G1中叫做疏散（Evacuation），同时把Suvivor上触及年龄阈值的对象晋升到老年代区域。

##### 老年代回收（concurrent cycle）

G1的老年代回收是在老年代空间触及一个阈值（Initiating Heap Occupancy Percent）之后，这个回收伴随着年轻代的回收工作，但与上边所说的回收有些不同。

1. 年轻代回收：伴随着年轻代的回收工作，同时会执行并发标记和一部分清理的工作，这样可以共用年轻代垃圾回收的Stop-The-World。
   1. 第一次标记：对应一次`Pause Initial Mark`
      和CMS的步骤类似，首先进行第一次标记。但实现方法上有很大的区别，G1首先对当前堆上的对象情况进行一个虚拟快照（Snapshot-At-The-Beginning），然后根据这个快照对老年代的对象和区域进行标记，并执行之后的垃圾回收。之后像CMS一样会有并发标记的过程。
      这样会产生一个问题，在这次回收结束之后，会有些对象在并发标记的过程中，它的可达性已经变化，导致**已经不可达的对象仍然没有被回收**。但是这样能带来更好的响应时间。
   2. 重新标记：对应一次`Pause Remark`
      在这个阶段，G1首先完成上一步开始的标记工作，之后会对特殊引用的对象进行处理（具体可以参考[JDK解构 - Java中的引用和动态代理的实现](http://www.jianshu.com/p/77dfeccac85d)），还有对Metaspace区域进行垃圾回收。这一步会进行Stop-The-World。
   3. 清理：对应一次`Pause Cleanup`
      这一步主要做的是收集当前堆中的内存区域信息，对空的区域进行回收，为接下来的空间回收做一些准备工作，清理结束之后，通常会伴随着一次年轻代回收，如果判断不需要进行空间回收，则会进入下一个年轻代回收的工作。这一步会进行Stop-The-World。
2. 混合垃圾回收：对应一次或多次`Pause Mixed`
   主要做的是对老年代的区域内存进行疏散（Evacuation），也包含对年轻代的区域回收工作。同时这一步也会动态地调整IHOP

从对G1的GC日志的分析，可以看到G1的垃圾回收行为是基于一个可预测的模型：GC会不断的主动触发垃圾回收，在这个过程中不断地进行信息统计和系统GC参数的设置，然后将上边这些步骤安排在这些垃圾回收过程中。

### 大对象的分配

正常情况下，一个对象会在年轻代的Eden中创建，然后通过垃圾回收和年龄管理之后，晋升到老年代。但对于某些比较大的对象，可能会直接分配到老年代去。

对于G1，对象大多数情况都会在Eden上分配，如果JVM判断一个对象为大对象（其阈值可以通过-XX:G1HeapRegionSize来设置），则会直接分配如老年代的大对象区域中。

对于其他的内存区域连续的GC，下面是从StackOverflow上搬运过来的对象在堆上的分配过程：

1. 使用 thread local allocation buffer (TLAB), 如果空间足够，则分配成功。
   从名称便可知，TLAB是线程独占的，所以线程安全，且速度非常快。如果一个TLAB满了，线程会被分配一个新的TLAB。
2. 如果TLAB 空间不够这次分配对象，但其中还有很多空间可用，则不使用TLAB，直接在Eden中分配对象。
   直接在Eden上分配对象要去抢占Eden中的指针操作，其代价较使用TLAB要大一些。
3. 如果Eden的对象分配失败，触发Minor GC。
4. 如果Minor GC完成后还不够，则直接分配到老年代。

### 一些简单的GC调优方法

##### 1. 使用不同的索引对象

引用的类型会直接影响其所引用对象的GC行为，当要做一些内存敏感的应用时，可以参考使用合适的引用类型。具体可以参考[JDK解构 - Java中的引用和动态代理的实现](http://www.jianshu.com/p/77dfeccac85d)。

##### 2. 使用Parallel

从上文中可知，Java 8默认的GC是Parallel，它也叫Throughput，所以它的目的是尽可能的增加系统的吞吐量。在Parallel里，可以通过参数调节最大停止时间（-XX:MaxGCPauseMillis，默认无设置）和吞吐量（-XX:GCTimeRatio，默认值是99，即最大使用1%的时间来做垃圾回收）来调优GC的行为。其中设置最大停止时间可能会导致GC调节各年龄代分区的尺寸（通过增量来实现）。

##### 3. 使用G1

从Java 9开始G1变成了默认的GC，G1中有一些细节的概念在上文中没有叙述，这里先介绍一下：

1. Remembered Sets（Rsets）：对于每个区域，都有一个集合记录这个区域中所有的引用。
2. G1 refinement：G1中需要有一系列的线程不断地维护Rsets。
3. Collection Sets（Csets）：在垃圾回收中需要被回收的区域，这些区域中的可达对象（活着的对象）会被疏散。这些区域可能是任何年龄代。
4. 写屏障（Write Barriers）：对于每一次赋值操作，G1都会有两个写屏障，写之前（Pre-Write）一个，写之后（Post-Write）一个。Pre-write主要与SATB相关，Post-write主要与Rsets相关
5. Dirty Card Queue：写屏障会将写的记录放入这个队列，会有线程将这里的对象不断的刷入Rsets。
6. Green/Yellow/Red Zone：三个会影响处理Dirty Card Queue线程数的阈值。根据Dirty Card Queue中元素的个数，可以来设置一些GC行为（可以认为是逻辑上将Dirty Card Queue分隔成多个区域）。Green表示超过此阈值则开始新建线程来处理这个队列，Yellow表示超过此阈值，强制启动这些线程，Red表示超过此阈值则会让写操作的线程自己来执行G1 refinement。

G1提供了丰富的基于不同目的的可调优的参数，列表如下：

| 参数                                   | 描述                                                         |
| :------------------------------------- | :----------------------------------------------------------- |
| -XX:+G1UseAdaptiveConcRefinement,      | 调节G1 refinement所使用的资源。                              |
| -XX:G1ConcRefinementGreenZone=<ergo>,  | 调节G1 refinement所使用的资源。                              |
| -XX:G1ConcRefinementYellowZone=<ergo>, | 调节G1 refinement所使用的资源。                              |
| -XX:G1ConcRefinementRedZone=<ergo>,    | 调节G1 refinement所使用的资源。                              |
| -XX:G1ConcRefinementThreads=<ergo>     | 调节G1 refinement所使用的资源。                              |
| -XX:G1RSetUpdatingPauseTimePercent=10  | 调节G1 refinement所需要的时间在整个垃圾回收时间的比例，G1会根据这个时间动态地调节第一行的各个参数。 |
| -XX:+ReduceInitialCardMarks            | 批量执行对象的生成，以减少初始标记的时间                     |
| -XX:-ParallelRefProcEnabled            | 使用多线程处理上文中所说的在重新标记阶段对引用的处理         |
| -XX:G1SummarizeRSetStatsPeriod=<n>     | 设置n次垃圾回收后，打印Rsets的总结性报告。                   |
| -XX:GCTimeRatio=<n>                    | 设置GC吞吐量。GC总共应该使用的时间是1 / (1 + n)，这个参数会影响不同年龄代尺寸的增长。 |
| -XX:G1HeapRegionSize                   | 设置区域的大小                                               |

主要参考文档：

1. [Getting Started with the G1 Garbage Collector](http://www.oracle.com/technetwork/tutorials/tutorials-1876574.html)
2. [Garbage-First Garbage Collector Tuning](https://docs.oracle.com/javase/9/gctuning/garbage-first-garbage-collector-tuning.htm)
3. [Evaluating and improving remembered sets in the HotSpot G1 garbage collector](http://www.diva-portal.se/smash/get/diva2:754515/FULLTEXT01.pdf)
4. [G1GC Internals](https://gist.github.com/hashbrowncipher/010d8b6fa458676fde84a8b48a1825d3)
5. [GC Algorithms: Basics](https://plumbr.eu/handbook/garbage-collection-algorithms)
6. [Java中几种常量池的区分](https://tangxman.github.io/2015/07/27/the-difference-of-java-string-pool/)