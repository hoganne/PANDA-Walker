# JVM年轻代、老年代、永久代（元数据）

![这里写图片描述](https://img-blog.csdn.net/20170518171044703?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc3RlZF96eHo=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
在 Java 中，堆被划分成两个不同的区域：新生代 ( Young )、老年代 ( Old)。新生代 ( Young ) 又被划分为三个区域：Eden、S0、S1。 **这样划分的目的是为了使 JVM 能够更好的管理堆内存中的对象，包括内存的分配以及回收。**

Java 中的堆也是 GC 收集垃圾的主要区域。GC 分为两种：**Minor GC、Full GC ( 或称为 Major GC )**。

## 1.年轻代 

年轻代用来存放新近创建的对象，尺寸随堆大小的增大和减小而相应的变化，默认值是保持为堆大小的1/15，可以通过 -Xmn 参数设置年轻代为固定大小，也可以通过 -XX:NewRatio 来设置年轻代与年老代的大小比例，年青代的特点是对象更新速度快，在短时间内产生大量的“死亡对象”。

年轻代的特点是产生大量的死亡对象,并且要是产生连续可用的空间, 所以使用复制清除算法和并行收集器进行垃圾回收.对年轻代的垃圾回收称作初级回收 (minor gc)。初级回收将年轻代分为三个区域, 一个新生代 , 2个大小相同的复活代, 应用程序只能使用一个新生代和一个复活代, 当发生初级垃圾回收的时候,gc挂起程序, 然后将新生代和复活代中的存活对象复制到另外一个非活动的复活代中,然后一次性清除新生代和复活代，将原来的非复活代标记成为活动复活代。将在指定次数回收后仍然存在的对象移动到老年代中，初级回收后，得到一个空的可用的新生代。

新生代几乎是所有 Java 对象出生的地方，即 Java 对象申请的内存以及存放都是在这个地方。Java 中的大部分对象通常不需长久存活，具有朝生夕灭的性质。 当一个对象被判定为 “死亡” 的时候，GC 就有责任来回收掉这部分对象的内存空间。新生代是 GC 收集垃圾的频繁区域。 当对象在 Eden 出生后，在经过一次` Minor GC `后，如果对象还存活，并且能够被另外一块 `Survivor` 区域所容纳，则使用复制算法将这些仍然还存活的对象复制到另外一块` Survivor` 区域中，然后清理所使用过的 `Eden `以及 `Survivor` 区域，并且将这些对象的年龄设置为1，以后对象在 Survivor 区每熬过一次 Minor GC，就将对象的年龄 + 1，当对象的年龄达到某个值时 ( 默认是 15 岁，可以通过参数 -XX:MaxTenuringThreshold 来设定 )，这些对象就会成为老年代。 但这也不是一定的，对于一些较大的对象 ( 即需要分配一块较大的连续内存空间 ) 则是直接进入到老年代。

## 2.老年代 


Full GC 是发生在老年代的垃圾收集动作，所采用的是**标记-清除算法**。

现实的生活中，老年代的人通常会比新生代的人 “早死”。堆内存中的老年代(Old)不同于这个，老年代里面的对象几乎个个都是在 `Survivor` 区域中熬过来的，它们是不会那么容易就 “死掉” 了的。因此，Full GC 发生的次数不会有 Minor GC 那么频繁，并且做一次 Full GC 要比进行一次 Minor GC 的时间更长。 另外，标记-清除算法收集垃圾的时候会产生许多的内存碎片 ( 即不连续的内存空间 )，此后需要为较大的对象分配内存空间时，若无法找到足够的连续的内存空间，就会提前触发一次 GC 的收集动作。

## 3.永久代 

`永久代`是Hotspot虚拟机特有的概念，是`方法区`的一种实现，别的`JVM`都没有这个东西。在Java 8中，永久代被彻底移除，取而代之的是另一块与堆不相连的本地内存——元空间。 
永久代或者“Perm Gen”包含了JVM需要的应用元数据，这些元数据描述了在应用里使用的`类和方法`。注意，`永久代不是Java堆内存的一部分`。`永久代`存放JVM运行时使用的类。`永久代`同样包含了`Java SE`库的类和方法。永久代的对象在`full GC`时进行垃圾收集。

  Jvm区域总体分两类，`heap`区和`非heap区`。heap区又分：Eden Space（伊甸园）、Survivor Space(幸存者区)、Tenured Gen（老年代-养老区）。 非heap区又分：`Code Cache(代码缓存区)`、`Perm Gen（永久代）`、`Jvm Stack(java虚拟机栈)`、`Local Method Statck(本地方法栈)`。

#####   HotSpot虚拟机GC算法采用`分代收集算法`：

1、一个人（对象）出来（new 出来）后会在Eden Space（伊甸园）无忧无虑的生活，直到GC到来打破了他们平静的生活。GC会逐一问清楚每个对象的情况，有没有钱（此对象的引用）啊，因为GC想赚钱呀，有钱的才可以敲诈嘛。然后富人就会进入Survivor Space（幸存者区），穷人的就直接kill掉。

2、并不是进入Survivor Space（幸存者区）后就保证人身是安全的，但至少可以活段时间。GC会定期（可以自定义）会对这些人进行敲诈，亿万富翁每次都给钱，GC很满意，就让其进入了Genured Gen(养老区)。万元户经不住几次敲诈就没钱了，GC看没有啥价值啦，就直接kill掉了。

3、进入到养老区的人基本就可以保证人身安全啦，但是亿万富豪有的也会挥霍成穷光蛋，只要钱没了，GC还是kill掉。

分区的目的：新生区由于对象产生的比较多并且大都是朝生夕灭的，所以直接采用标记-清理算法。而养老区生命力很强，则采用复制算法，针对不同情况使用不同算法。

非heap区域中`Perm Gen`中放着`类、方法的定义`，`jvm Stack区域放着方法参数、局域变量等的引用`，`方法执行顺序按照栈的先入后出方式`。

## GC工作机制

SUN的jvm内存池被划分为以下几个部分：

**Eden** **Space (heap)**

内存最初从这个线程池分配给大部分对象。

**Survivor Space (heap)**

用于保存在eden space内存池中经过垃圾回收后没有被回收的对象。

**Tenured Generation (heap)**

用于保持已经在survivor space内存池中存在了一段时间的对象。

**Permanent Generation (non-heap)**

保存虚拟机自己的`静态(reflective)数据`，例如类（class）和方法（method）对象。Java虚拟机共享这些类数据。这个区域被分割为只读的和只写的。

**Code Cache (non-heap)**

HotSpot Java虚拟机包括一个用于编译和保存本地代码（native code）的内存，叫做“代码缓存区”（code cache）。

简单来讲，jvm的内存回收过程是这样的：

对象在Eden Space创建，当Eden Space满了的时候，gc就把所有在Eden Space中的对象扫描一次，把所有有效的对象复制到第一个Survivor Space，同时把无效的对象所占用的空间释放。当Eden Space再次变满了的时候，就启动移动程序把Eden Space中有效的对象复制到第二个Survivor Space，同时，也将第一个Survivor Space中的有效对象复制到第二个Survivor Space。如果填充到第二个Survivor Space中的有效对象被第一个Survivor Space或Eden Space中的对象引用，那么这些对象就是长期存在的，此时这些对象将被复制到Permanent Generation。

若垃圾收集器依据这种小幅度的调整收集不能腾出足够的空间，就会运行Full GC，此时jvm gc停止所有在堆中运行的线程并执行清除动作。

## metaspace的组成

metaspace其实由两大部分组成

- Klass Metaspace
- NoKlass Metaspace

Klass Metaspace就是用来存klass的，klass是我们熟知的class文件在jvm里的运行时数据结构，不过有点要提的是我们看到的类似A.class其实是存在heap里的，是java.lang.Class的一个对象实例。这块内存是紧接着Heap的，和我们之前的perm一样，这块内存大小可通过`-XX:CompressedClassSpaceSize`参数来控制，这个参数前面提到了默认是1G，但是这块内存也可以没有，假如没有开启压缩指针就不会有这块内存，这种情况下klass都会存在NoKlass Metaspace里，另外如果我们把-Xmx设置大于32G的话，其实也是没有这块内存的，因为会这么大内存会关闭压缩指针开关。还有就是这块内存最多只会存在一块。

NoKlass Metaspace专门来存klass相关的其他的内容，比如method，constantPool等，这块内存是由多块内存组合起来的，所以可以认为是不连续的内存块组成的。这块内存是必须的，虽然叫做NoKlass Metaspace，但是也其实可以存klass的内容，上面已经提到了对应场景。

Klass Metaspace和NoKlass Mestaspace都是所有classloader共享的，所以类加载器们要分配内存，但是每个类加载器都有一个SpaceManager，来管理属于这个类加载的内存小块。如果Klass Metaspace用完了，那就会OOM了，不过一般情况下不会，NoKlass Mestaspace是由一块块内存慢慢组合起来的，在没有达到限制条件的情况下，会不断加长这条链，让它可以持续工作。

### metaspace的几个参数

如果我们要改变metaspace的一些行为，我们一般会对其相关的一些参数做调整，因为metaspace的参数本身不是很多，所以我这里将涉及到的所有参数都做一个介绍，也许好些参数大家都是有误解的

- UseLargePagesInMetaspace
- InitialBootClassLoaderMetaspaceSize
- MetaspaceSize
- MaxMetaspaceSize
- CompressedClassSpaceSize
- MinMetaspaceExpansion
- MaxMetaspaceExpansion
- MinMetaspaceFreeRatio
- MaxMetaspaceFreeRatio

### UseLargePagesInMetaspace

默认false，这个参数是说是否在metaspace里使用LargePage，一般情况下我们使用4KB的page size，这个参数依赖于UseLargePages这个参数开启，不过这个参数我们一般不开。

### InitialBootClassLoaderMetaspaceSize

64位下默认4M，32位下默认2200K，metasapce前面已经提到主要分了两大块，Klass Metaspace以及NoKlass Metaspace，而NoKlass Metaspace是由一块块内存组合起来的，这个参数决定了NoKlass Metaspace的第一个内存Block的大小，即2*InitialBootClassLoaderMetaspaceSize，同时为bootstrapClassLoader的第一块内存chunk分配了InitialBootClassLoaderMetaspaceSize的大小

### MetaspaceSize

默认20.8M左右(x86下开启c2模式)，主要是控制metaspaceGC发生的初始阈值，也是最小阈值，但是触发metaspaceGC的阈值是不断变化的，与之对比的主要是指Klass Metaspace与NoKlass Metaspace两块committed的内存和。

### MaxMetaspaceSize

默认基本是无穷大，但是我还是建议大家设置这个参数，因为很可能会因为没有限制而导致metaspace被无止境使用(一般是内存泄漏)而被OS Kill。这个参数会限制metaspace(包括了Klass Metaspace以及NoKlass Metaspace)被committed的内存大小，会保证committed的内存不会超过这个值，一旦超过就会触发GC，这里要注意和MaxPermSize的区别，MaxMetaspaceSize并不会在jvm启动的时候分配一块这么大的内存出来，而MaxPermSize是会分配一块这么大的内存的。

### CompressedClassSpaceSize

默认1G，这个参数主要是设置Klass Metaspace的大小，不过这个参数设置了也不一定起作用，前提是能开启压缩指针，假如-Xmx超过了32G，压缩指针是开启不来的。如果有Klass Metaspace，那这块内存是和Heap连着的。

### MinMetaspaceExpansion

MinMetaspaceExpansion和MaxMetaspaceExpansion这两个参数或许和大家认识的并不一样，也许很多人会认为这两个参数不就是内存不够的时候，然后扩容的最小大小吗？其实不然

这两个参数和扩容其实并没有直接的关系，也就是并不是为了增大committed的内存，而是为了增大触发metaspace GC的阈值

这两个参数主要是在比较特殊的场景下救急使用，比如gcLocker或者`should_concurrent_collect`的一些场景，因为这些场景下接下来会做一次GC，相信在接下来的GC中可能会释放一些metaspace的内存，于是先临时扩大下metaspace触发GC的阈值，而有些内存分配失败其实正好是因为这个阈值触顶导致的，于是可以通过增大阈值暂时绕过去

默认332.8K，增大触发metaspace GC阈值的最小要求。假如我们要救急分配的内存很小，没有达到MinMetaspaceExpansion，但是我们会将这次触发metaspace GC的阈值提升MinMetaspaceExpansion，之所以要大于这次要分配的内存大小主要是为了防止别的线程也有类似的请求而频繁触发相关的操作，不过如果要分配的内存超过了MaxMetaspaceExpansion，那MinMetaspaceExpansion将会是要分配的内存大小基础上的一个增量

### MaxMetaspaceExpansion

默认5.2M，增大触发metaspace GC阈值的最大要求。假如说我们要分配的内存超过了MinMetaspaceExpansion但是低于MaxMetaspaceExpansion，那增量是MaxMetaspaceExpansion，如果超过了MaxMetaspaceExpansion，那增量是MinMetaspaceExpansion加上要分配的内存大小

注：每次分配只会给对应的线程一次扩展触发metaspace GC阈值的机会，如果扩展了，但是还不能分配，那就只能等着做GC了

### MinMetaspaceFreeRatio

MinMetaspaceFreeRatio和下面的MaxMetaspaceFreeRatio，主要是影响触发metaspaceGC的阈值

默认40，表示每次GC完之后，假设我们允许接下来metaspace可以继续被commit的内存占到了被commit之后总共committed的内存量的MinMetaspaceFreeRatio%，如果这个总共被committed的量比当前触发metaspaceGC的阈值要大，那么将尝试做扩容，也就是增大触发metaspaceGC的阈值，不过这个增量至少是MinMetaspaceExpansion才会做，不然不会增加这个阈值

这个参数主要是为了避免触发metaspaceGC的阈值和gc之后committed的内存的量比较接近，于是将这个阈值进行扩大

一般情况下在gc完之后，如果被committed的量还是比较大的时候，换个说法就是离触发metaspaceGC的阈值比较接近的时候，这个调整会比较明显

注：这里不用gc之后used的量来算，主要是担心可能出现committed的量超过了触发metaspaceGC的阈值，这种情况一旦发生会很危险，会不断做gc，这应该是jdk8在某个版本之后才修复的bug

### MaxMetaspaceFreeRatio

默认70，这个参数和上面的参数基本是相反的，是为了避免触发metaspaceGC的阈值过大，而想对这个值进行缩小。这个参数在gc之后committed的内存比较小的时候并且离触发metaspaceGC的阈值比较远的时候，调整会比较明显

### jstat里的metaspace字段

我们看GC是否异常，除了通过GC日志来做分析之外，我们还可以通过jstat这样的工具展示的数据来分析，前面我公众号里有篇文章介绍了jstat这块的实现，有兴趣的可以到我的公众号`你假笨`里去翻阅下jstat的这篇文章。

我们通过jstat可以看到metaspace相关的这么一些指标，分别是`M`，`CCS`，`MC`，`MU`，`CCSC`，`CCSU`，`MCMN`，`MCMX`，`CCSMN`，`CCSMX`

### MC & MU & CCSC & CCSU

- MC表示Klass Metaspace以及NoKlass Metaspace两者总共committed的内存大小，单位是KB，虽然从上面的定义里我们看到了是capacity，但是实质上计算的时候并不是capacity，而是committed，这个是要注意的
- MU这个无可厚非，说的就是Klass Metaspace以及NoKlass Metaspace两者已经使用了的内存大小
- CCSC表示的是Klass Metaspace的已经被commit的内存大小，单位也是KB
- CCSU表示Klass Metaspace的已经被使用的内存大小

### M & CCS

- M表示的是Klass Metaspace以及NoKlass Metaspace两者总共的使用率，其实可以根据上面的四个指标算出来，即(CCSU+MU)/(CCSC+MC)
- CCS表示的是NoKlass Metaspace的使用率，也就是CCSU/CCSC算出来的