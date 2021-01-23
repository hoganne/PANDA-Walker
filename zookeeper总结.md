# zookeeper总结

## 一 什么是 ZooKeeper

**ZooKeeper 的由来**

**下面这段内容摘自《从Paxos到Zookeeper 》第四章第一节的某段内容，推荐大家阅读以下：**

> Zookeeper最早起源于雅虎研究院的一个研究小组。在当时，研究人员发现，在雅虎内部很多大型系统基本都需要依赖一个类似的系统来进行分布式协调，但是这些系统往往都存在分布式单点问题。所以，**雅虎的开发人员就试图开发一个通用的无单点问题的分布式协调框架，以便让开发人员将精力集中在处理业务逻辑上。**
> 关于“ZooKeeper”这个项目的名字，其实也有一段趣闻。在立项初期，考虑到之前内部很多项目都是使用动物的名字来命名的（例如著名的Pig项目),雅虎的工程师希望给这个项目也取一个动物的名字。时任研究院的首席科学家RaghuRamakrishnan开玩笑地说：“在这样下去，我们这儿就变成动物园了！”此话一出，大家纷纷表示就叫动物园管理员吧一一一因为各个以动物命名的分布式组件放在一起，**雅虎的整个分布式系统看上去就像一个大型的动物园了，而Zookeeper正好要用来进行分布式环境的协调一一于是，Zookeeper的名字也就由此诞生了。**

**1.1 ZooKeeper 概览**

ZooKeeper 是一个开源的分布式协调服务，ZooKeeper框架最初是在“Yahoo!"上构建的，用于以简单而稳健的方式访问他们的应用程序。 后来，Apache ZooKeeper成为Hadoop，HBase和其他分布式框架使用的有组织服务的标准。 例如，Apache HBase使用ZooKeeper跟踪分布式数据的状态。**ZooKeeper 的设计目标是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。**

> **原语：** 操作系统或计算机网络用语范畴。是由若干条指令组成的，用于完成一定功能的一个过程。具有不可分割性·即原语的执行必须是连续的，在执行过程中不允许被中断。

**ZooKeeper 是一个典型的分布式数据一致性解决方案，分布式应用程序可以基于 ZooKeeper 实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。**

**Zookeeper 一个最常用的使用场景就是用于担任服务生产者和服务消费者的注册中心。** 服务生产者将自己提供的服务注册到Zookeeper中心，服务的消费者在进行服务调用的时候先到Zookeeper中查找服务，获取到服务生产者的详细信息之后，再去调用服务生产者的内容与数据。如下图所示，在 Dubbo架构中 Zookeeper 就担任了注册中心这一角色。


![img](https://pic4.zhimg.com/80/v2-6067d217ef4515d80a57eb1102cc1f17_720w.jpg)

**1.2 结合个人使用情况的讲一下 ZooKeeper**

在我自己做过的项目中，主要使用到了 ZooKeeper 作为 Dubbo 的注册中心(Dubbo 官方推荐使用 ZooKeeper注册中心)。另外在搭建 solr 集群的时候，我使用 ZooKeeper 作为 solr 集群的管理工具。这时，ZooKeeper 主要提供下面几个功能：1、集群管理：容错、负载均衡。2、配置文件的集中管理3、集群的入口。

我个人觉得在使用 ZooKeeper 的时候，最好是使用 集群版的 ZooKeeper 而不是单机版的。官网给出的架构图就描述的是一个集群版的 ZooKeeper 。通常 3 台服务器就可以构成一个 ZooKeeper 集群了。

**为什么最好使用奇数台服务器构成 ZooKeeper 集群？**

我们知道在Zookeeper中 Leader 选举算法采用了Zab协议。Zab核心思想是当多数 Server 写成功，则任务数据写成功。

①如果有3个Server，则最多允许1个Server 挂掉。

②如果有4个Server，则同样最多允许1个Server挂掉。

既然3个或者4个Server，同样最多允许1个Server挂掉，那么它们的可靠性是一样的，所以选择奇数个ZooKeeper Server即可，这里选择3个Server。

## 二 关于 ZooKeeper 的一些重要概念

**2.1 重要概念总结**

**● ZooKeeper 本身就是一个分布式程序（只要半数以上节点存活，ZooKeeper 就能正常服务）。**

**● 为了保证高可用，最好是以集群形态来部署 ZooKeeper，这样只要集群中大部分机器是可用的（能够容忍一定的机器故障），那么 ZooKeeper 本身仍然是可用的。**

**● ZooKeeper 将数据保存在内存中，这也就保证了 高吞吐量和低延迟**（但是内存限制了能够存储的容量不太大，此限制也是保持znode中存储的数据量较小的进一步原因）。

**● ZooKeeper 是高性能的。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。**（“读”多于“写”是协调服务的典型场景。）

**● ZooKeeper有临时节点的概念。 当创建临时节点的客户端会话一直保持活动，瞬时节点就一直存在。而当会话终结时，瞬时节点被删除。持久节点是指一旦这个ZNode被创建了，除非主动进行ZNode的移除操作，否则这个ZNode将一直保存在Zookeeper上。**

**●** ZooKeeper 底层其实只提供了两个功能：①管理（存储、读取）用户程序提交的数据；②为用户程序提交数据节点监听服务。

**下面关于会话（Session）、 Znode、版本、Watcher、ACL概念的总结都在《从Paxos到Zookeeper 》第四章第一节以及第七章第八节有提到，感兴趣的可以看看！**

**2.2 会话（Session）**

Session 指的是 ZooKeeper 服务器与客户端会话。**在 ZooKeeper 中，一个客户端连接是指客户端和服务器之间的一个 TCP 长连接**。客户端启动的时候，首先会与服务器建立一个 TCP 连接，从第一次连接建立开始，客户端会话的生命周期也开始了。**通过这个连接，客户端能够通过心跳检测与服务器保持有效的会话，也能够向Zookeeper服务器发送请求并接受响应，同时还能够通过该连接接收来自服务器的Watch事件通知。** Session的sessionTimeout值用来设置一个客户端会话的超时时间。当由于服务器压力太大、网络故障或是客户端主动断开连接等各种原因导致客户端连接断开时，**只要在sessionTimeout规定的时间内能够重新连接上集群中任意一台服务器，那么之前创建的会话仍然有效。**

**在为客户端创建会话之前，服务端首先会为每个客户端都分配一个sessionID。由于 sessionID 是 Zookeeper 会话的一个重要标识，许多与会话相关的运行机制都是基于这个 sessionID 的，因此，无论是哪台服务器为客户端分配的 sessionID，都务必保证全局唯一。**

**2.3 Znode**

**在谈到分布式的时候，我们通常说的“节点"是指组成集群的每一台机器。然而，在Zookeeper中，“节点"分为两类，第一类同样是指构成集群的机器，我们称之为机器节点；第二类则是指数据模型中的数据单元，我们称之为数据节点一一ZNode。**

Zookeeper将所有数据存储在内存中，数据模型是一棵树（Znode Tree)，由斜杠（/）的进行分割的路径，就是一个Znode，例如/foo/path1。每个上都会保存自己的数据内容，同时还会保存一系列属性信息。

**在Zookeeper中，node可以分为持久节点和临时节点两类。所谓持久节点是指一旦这个ZNode被创建了，除非主动进行ZNode的移除操作，否则这个ZNode将一直保存在Zookeeper上。而临时节点就不一样了，它的生命周期和客户端会话绑定，一旦客户端会话失效，那么这个客户端创建的所有临时节点都会被移除。**另外，ZooKeeper还允许用户为每个节点添加一个特殊的属性：**SEQUENTIAL**.一旦节点被标记上这个属性，那么在这个节点被创建的时候，Zookeeper会自动在其节点名后面追加上一个整型数字，这个整型数字是一个由父节点维护的自增数字。

**2.4 版本**

在前面我们已经提到，Zookeeper 的每个 ZNode 上都会存储数据，对应于每个ZNode，Zookeeper 都会为其维护一个叫作 **Stat** 的数据结构，Stat中记录了这个 ZNode 的三个数据版本，分别是version（当前ZNode的版本）、cversion（当前ZNode子节点的版本）和 cversion（当前ZNode的ACL版本）。

**2.5 Watcher**

**Watcher（事件监听器），是Zookeeper中的一个很重要的特性。Zookeeper允许用户在指定节点上注册一些Watcher，并且在一些特定事件触发的时候，ZooKeeper服务端会将事件通知到感兴趣的客户端上去，该机制是Zookeeper实现分布式协调服务的重要特性。**

**2.6 ACL**

Zookeeper采用ACL（AccessControlLists）策略来进行权限控制，类似于 UNIX 文件系统的权限控制。Zookeeper 定义了如下5种权限。



![img](https://pic3.zhimg.com/80/v2-c3f8d834751418637e1e9b33f27a71f2_720w.jpg)


其中尤其需要注意的是，CREATE和DELETE这两种权限都是针对子节点的权限控制。

**三 ZooKeeper 特点**

**●** **顺序一致性：** 从同一客户端发起的事务请求，最终将会严格地按照顺序被应用到 ZooKeeper 中去。

**● 原子性：** 所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，也就是说，要么整个集群中所有的机器都成功应用了某一个事务，要么都没有应用。

**● 单一系统映像 ：** 无论客户端连到哪一个 ZooKeeper 服务器上，其看到的服务端数据模型都是一致的。

**● 可靠性：** 一旦一次更改请求被应用，更改的结果就会被持久化，直到被下一次更改覆盖。

## 四 ZooKeeper 设计目标 

**4.1 简单的数据模型**

ZooKeeper 允许分布式进程通过共享的层次结构命名空间进行相互协调，这与标准文件系统类似。 名称空间由 ZooKeeper 中的数据寄存器组成 - 称为znode，这些类似于文件和目录。 与为存储设计的典型文件系统不同，ZooKeeper数据保存在内存中，这意味着ZooKeeper可以实现高吞吐量和低延迟。

![img](https://pic4.zhimg.com/80/v2-a229d2c259f31d05a0255317b3728ddb_720w.jpg)


**4.2 可构建集群**

**为了保证高可用，最好是以集群形态来部署 ZooKeeper，这样只要集群中大部分机器是可用的（能够容忍一定的机器故障），那么zookeeper本身仍然是可用的。** 客户端在使用 ZooKeeper 时，需要知道集群机器列表，通过与集群中的某一台机器建立 TCP 连接来使用服务，客户端使用这个TCP链接来发送请求、获取结果、获取监听事件以及发送心跳包。如果这个连接异常断开了，客户端可以连接到另外的机器上。

**ZooKeeper 官方提供的架构图：**



![img](https://pic4.zhimg.com/80/v2-938d4f06a0b8d915e5ecafa89fd82e43_720w.jpg)


上图中每一个Server代表一个安装Zookeeper服务的服务器。组成 ZooKeeper 服务的服务器都会在内存中维护当前的服务器状态，并且每台服务器之间都互相保持着通信。集群间通过 Zab 协议（Zookeeper Atomic Broadcast）来保持数据的一致性。

**4.3 顺序访问**

**对于来自客户端的每个更新请求，ZooKeeper 都会分配一个全局唯一的递增编号，这个编号反应了所有事务操作的先后顺序，应用程序可以使用 ZooKeeper 这个特性来实现更高层次的同步原语。** **这个编号也叫做时间戳——zxid（Zookeeper Transaction Id）**

**4.4 高性能**

**ZooKeeper 是高性能的。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。（“读”多于“写”是协调服务的典型场景。）**

## 五 ZooKeeper 集群角色介绍

**最典型集群模式： Master/Slave 模式（主备模式）**。在这种模式中，通常 Master服务器作为主服务器提供写服务，其他的 Slave 服务器从服务器通过异步复制的方式获取 Master 服务器最新的数据提供读服务。

但是，**在 ZooKeeper 中没有选择传统的 Master/Slave 概念，而是引入了Leader、Follower 和 Observer 三种角色**。如下图所示



![img](https://pic3.zhimg.com/80/v2-c6f951b72882b24311a02fc4244a8dea_720w.jpg)


**ZooKeeper 集群中的所有机器通过一个 Leader 选举过程来选定一台称为 “Leader” 的机器，Leader 既可以为客户端提供写服务又能提供读服务。除了 Leader 外，Follower 和 Observer 都只能提供读服务。Follower 和 Observer 唯一的区别在于 Observer 机器不参与 Leader 的选举过程，也不参与写操作的“过半写成功”策略，因此 Observer 机器可以在不影响写性能的情况下提升集群的读性能。**



![img](https://pic4.zhimg.com/80/v2-2ea1381ae85c8b4e29bf98d59fb4ffb3_720w.jpg)

##  六 ZooKeeper ZAB 协议Paxos算法

**6.1 ZAB 协议&Paxos算法**

Paxos 算法应该可以说是 ZooKeeper 的灵魂了。但是，ZooKeeper 并没有完全采用 Paxos算法 ，而是使用 ZAB 协议作为其保证数据一致性的核心算法。另外，在ZooKeeper的官方文档中也指出，ZAB协议并不像 Paxos 算法那样，是一种通用的分布式一致性算法，它是一种特别为Zookeeper设计的崩溃可恢复的原子消息广播算法。

**6.2 ZAB 协议介绍**

**ZAB（ZooKeeper Atomic Broadcast 原子广播） 协议是为分布式协调服务 ZooKeeper 专门设计的一种支持崩溃恢复的原子广播协议。 在 ZooKeeper 中，主要依赖 ZAB 协议来实现分布式数据一致性，基于该协议，ZooKeeper 实现了一种主备模式的系统架构来保持集群中各个副本之间的数据一致性。**

**6.3 ZAB 协议两种基本的模式：崩溃恢复和消息广播**

ZAB协议包括两种基本的模式，分别是 **崩溃恢复和消息广播**。当整个服务框架在启动过程中，或是当 Leader 服务器出现网络中断、崩溃退出与重启等异常情况时，ZAB 协议就会进人恢复模式并选举产生新的Leader服务器。当选举产生了新的 Leader 服务器，同时集群中已经有过半的机器与该Leader服务器完成了状态同步之后，ZAB协议就会退出恢复模式。其中，**所谓的状态同步是指数据同步，用来保证集群中存在过半的机器能够和Leader服务器的数据状态保持一致**。

**当集群中已经有过半的Follower服务器完成了和Leader服务器的状态同步，那么整个服务框架就可以进人消息广播模式了。** 当一台同样遵守ZAB协议的服务器启动后加人到集群中时，如果此时集群中已经存在一个Leader服务器在负责进行消息广播，那么新加人的服务器就会自觉地进人数据恢复模式：找到Leader所在的服务器，并与其进行数据同步，然后一起参与到消息广播流程中去。正如上文介绍中所说的，ZooKeeper设计成只允许唯一的一个Leader服务器来进行事务请求的处理。Leader服务器在接收到客户端的事务请求后，会生成对应的事务提案并发起一轮广播协议；而如果集群中的其他机器接收到客户端的事务请求，那么这些非Leader服务器会首先将这个事务请求转发给Leader服务器。

关于 **ZAB 协议&Paxos算法** 需要讲和理解的东西太多了，说实话，笔主到现在不太清楚这俩兄弟的具体原理和实现过程。推荐阅读下面两篇文章：

- [图解 Paxos 一致性协议](https://link.zhihu.com/?target=http%3A//blog.xiaohansong.com/2016/09/30/Paxos/)
- [Zookeeper ZAB 协议分析](https://link.zhihu.com/?target=http%3A//blog.xiaohansong.com/2016/08/25/zab/)

关于如何使用 zookeeper 实现分布式锁，可以查看下面这篇文章：

- [10分钟看懂！基于Zookeeper的分布式锁](https://link.zhihu.com/?target=https%3A//blog.csdn.net/qiangcuo6087/article/details/79067136)

## 六 总结 

通过阅读本文，想必大家已从 **①ZooKeeper的由来。** -> **②ZooKeeper 到底是什么 。**-> **③ ZooKeeper 的一些重要概念**（会话（Session）、 Znode、版本、Watcher、ACL）-> **④ZooKeeper 的特点。** -> **⑤ZooKeeper 的设计目标。**-> **⑥ ZooKeeper 集群角色介绍** （Leader、Follower 和 Observer 三种角色）-> **⑦ZooKeeper &ZAB 协议&Paxos算法。** 这七点了解了 ZooKeeper 。

## 参考

- 《从Paxos到Zookeeper 》
- [https://cwiki.apache.org/confluence/display/ZOOKEEPER/ProjectDescription](https://link.zhihu.com/?target=https%3A//cwiki.apache.org/confluence/display/ZOOKEEPER/ProjectDescription)
- [https://cwiki.apache.org/confluence/display/ZOOKEEPER/Index](https://link.zhihu.com/?target=https%3A//cwiki.apache.org/confluence/display/ZOOKEEPER/Index)
- [https://www.cnblogs.com/raphael5200/p/5285583.html](https://link.zhihu.com/?target=https%3A//www.cnblogs.com/raphael5200/p/5285583.html)
- https://zhuanlan.zhihu.com/p/30024403

# 难以承受的异常处理

我们在阿里巴巴内部应用接入 ZooKeeper 时，有一个《ZooKeeper 应用接入必知必会》的 WIKI，其中关于异常处理有过如下的论述:

> 如果说要选出应用开发者在使用 ZooKeeper 的过程中，最需要了解清楚的事情？那么根据我们之前的支持经验，一定是异常处理。
>
> 当所有一切（宿主机，磁盘，网络等等）都很幸运的正常工作的时候，应用与 ZooKeeper 可能也会运行的很好，但不幸的是，我们整天会面对各种意外，而且这遵循墨菲定律，意料之外的坏事情总是在你最担心的时候发生。
>
> 所以务必仔细了解 ZooKeeper 在一些场景下会出现的异常和错误，确保您正确的理解了这些异常和错误，以及知道您的应用如何正确的处理这些情况。
>
> **![d47e62d2b349aca45e42305ed6714efbe5ed61d9](https://yqfile.alicdn.com/d47e62d2b349aca45e42305ed6714efbe5ed61d9.png)**ConnectionLossException 和 Disconnected 事件
>
> 简单来说，这是个可以在同一个 ZooKeeper Session 恢复的异常 (Recoverable), 但是应用开发者需要负责将应用恢复到正确的状态。
>
> 发生这个异常的原因有很多，例如`应用机器与 ZooKeeper 节点之间网络闪断`，`ZooKeeper 节点宕机`，`服务端 Full GC 时间超长`，甚至你的应用进程 Hang 死，`应用进程 Full GC 时间超长之后恢复`都有可能。
>
> 要理解这个异常，需要了解分布式应用中的一个典型的问题，如下图：
>
> ![677659054633661869a64c3adae8e18b7d3bfef8](https://yqfile.alicdn.com/677659054633661869a64c3adae8e18b7d3bfef8.png)
>
> 
>
> 在一个典型的客户端请求、服务端响应中，当它们之间的长连接闪断的时候，客户端感知到这个闪断事件的时候，会处在一个比较尴尬的境地，那就是无法确定该事件发生时附近的那个请求到底处在什么状态，Server 端到底收到这个请求了么？已经处理了么？因为无法确定这一点，所以当客户端重新连接上 Server 之后，这个请求是否应该重试（Retry）就也要打一个问号。
>
> 所以在处理连接断开事件中，应用开发者必须清楚处于闪断附近的那个请求是什么（这常常难以判断），该请求是否是幂等的，对于业务请求在 Server 端服务处理上对于"仅处理一次" "最多处理一次" "最少处理一次"语义要有选择和预期。
>
> 举个例子，如果应用在收到 ConnectionLossException 时，之前的请求是 Create 操作，那么应用的 catch 到这个异常，应用一个可能的恢复逻辑就是，判断之前请求创建的节点的是否已经存在了，如果存在就不要再创建了，否则就创建。
>
> 再比如，如果应用使用了 exists Watch 去监听一个不存在的节点的创建的事件，那么在 ConnectionLossException 的期间，有可能遇到的情况是，在这个闪断期间，其它的客户端进程可能已经创建了节点，并且又已经删除了，那么对于当前应用来说，就 miss 了一次关心的节点的创建事件，这种 miss 对应用的影响是什么？是可以忍受的还是不可接受？需要应用开发者自己根据业务语义去评估和处理。
>
> **![d47e62d2b349aca45e42305ed6714efbe5ed61d9](https://yqfile.alicdn.com/d47e62d2b349aca45e42305ed6714efbe5ed61d9.png)**SessionExpiredException 和 SessionExpired 事件
>
> Session 超时是一个不可恢复的异常，这是指应用 Catch 到这个异常的时候，应用不可能在同一个 Session 中恢复应用状态，必须要重新建立新 Session，老 Session 关联的临时节点也可能已经失效，拥有的锁可能已经失效。...

我们阿里巴巴的小伙伴在自行尝试使用 ZooKeeper 做服务发现的过程中，曾经在我们的内网技术论坛上总结过一篇自己踩坑的经验分享

![67fcb4e3d52a536beb44c6c23326464576e06132](https://yqfile.alicdn.com/67fcb4e3d52a536beb44c6c23326464576e06132.png)

在该文中中肯的提到:

... 在编码过程中发现很多可能存在的陷阱，毛估估，第一次使用 zk 来实现集群管理的人应该有 80% 以上会掉坑，有些坑比较隐蔽，在网络问题或者异常的场景时才会出现，可能很长一段时间才会暴露出来 ...

这篇文章已经分享到云栖社区, 你可以点击 **这里** 详细阅读。

向左走，向右走

阿里巴巴是不是完全没有使用 ZooKeeper？并不是！

熟悉阿里巴巴技术体系的都知道，其实阿里巴巴维护了目前国内乃至世界上最大规模的 ZooKeeper 集群，整体规模有近千台的 ZooKeeper 服务节点。

同时阿里巴巴中间件内部也维护了一个面向大规模生产的、高可用、更易监控和运维的 ZooKeeper 的代码分支 TaoKeeper，如果以我们近 10 年在各个业务线和生产上使用 ZooKeeper 的实践，给 ZooKeeper 用一个短语评价的话，那么我们认为 ZooKeeper 应该是 “The King Of Coordination for Big Data”！

![3092129be7f357f716db9b5ad5c3749ce3a7f8af](https://yqfile.alicdn.com/3092129be7f357f716db9b5ad5c3749ce3a7f8af.png)

在粗粒度分布式锁，分布式选主，主备高可用切换等不需要高 TPS 支持的场景下有不可替代的作用，而这些需求往往多集中在大数据、离线任务等相关的业务领域，因为大数据领域，讲究分割数据集，并且大部分时间分任务多进程 / 线程并行处理这些数据集，但是总是有一些点上需要将这些任务和进程统一协调，这时候就是 ZooKeeper 发挥巨大作用的用武之地。

但是在交易场景交易链路上，在主业务数据存取，大规模服务发现、大规模健康监测等方面有天然的短板，应该竭力避免在这些场景下引入 ZooKeeper，在阿里巴巴的生产实践中，应用对 ZooKeeper 申请使用的时候要进行严格的场景、容量、SLA 需求的评估。

所以可以使用 ZooKeeper，但是大数据请向左，而交易则向右，分布式协调向左，服务发现向右。

结语

感谢你耐心的阅读到这里，至此，我相信你已经理解，我们写这篇文章并不是全盘否定 ZooKeeper，而只是根据我们阿里巴巴近 10 年来在大规模服务化上的生产实践，对我们在服务发现和注册中心设计及使用上的经验教训进行一个总结，希望对业界就如何更好的使用 ZooKeeper，如何更好的设计自己的服务注册中心有所启发和帮助。

最后，条条大路通罗马，衷心祝愿你的注册中心直接就诞生在罗马。