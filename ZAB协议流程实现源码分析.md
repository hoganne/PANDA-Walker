# ZAB协议流程实现源码分析

## 什么是ZAB协议

`Zookeeper`主要操作数据的状态，为了保证状态的一致性，`Zookeeper`提出了两个安全属性

1,Zookeeper 使用一个单一主进程来接收并处理客户端的所有事务请求，即写请求, 当服务器数据的状态发生变更后， 集群采用 ZAB 原子广播协议，以事务提案 `Proposal` 的形式广 播到所有的副本进程上, `ZAB` 协议能够保证一个全局的变更序列、 即可以为每一个事务分配 一个全局的递增编号`xid`

2,当 `Zookeeper` 客户端连接到 `Zookeeper` 集群的一个节点后，若客户端提交的是读请求， 那么当前节点就直接根据自己保存的数据对其进行响应，如果是写请求且当前节点不是`Leader`，那么节点就会将该写请求转发给 `Leader`，`Leader` 会以提案的方式广播该写操作，只要有超过半数节点同意该写操作，则该写操作请求就会被提交，然后 `Leader` 会再次广播给 所有订阅者，即 `Learner`，通知它们同步数据

### ZAB协议原理

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk28.png)

### ZAB核心原理

Zab协议的核心：定义了事务请求的处理方式

1.所有的事务请求必须由一个全局唯一的服务器来协调处理，这样的服务器被叫做 Leader服务器 其他剩余的服务器则是 Follower服务器

2.Leader服务器 负责将一个客户端事务请求，转换成一个 事务`Proposal`，并将该` Proposal` 分发给集群中所有的 `Follower` 服务器，也就是向所有 `Follower` 节点发送数据广播请求（或数据复制）

3.分发之后`Leader`服务器需要等待所有Follower服务器的反馈（`Ack请求`），在`Zab`协议中，只要超过`半数的Follower`服务器进行了正确的反馈后（也就是收到半数以上的Follower的`Ack请求` 那么` Leader `就会再次向所有的 Follower服务器发送 `Commit`消息，要求其将上一个 事务`proposal` 进行提交

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk29.png)

### ZAB协议内容

`Zab`协议包括两种基本的模式：崩溃恢复 和 消息广播

**1.协议过程**

当整个集群启动过程中，或者当 Leader 服务器出现网络中弄断、崩溃退出或重启等异常时，Zab协议就会 进入`崩溃恢复模式`，选举产生新的Leader

当选举产生了新的 Leader，同时集群中有过半的机器与该 Leader 服务器完成了状态同步（即数据同步）之后，Zab协议就会退出崩溃恢复模式，进入消息`广播模式`

这时，如果有一台遵守`Zab协议`的服务器加入集群，因为此时集群中已经存在一个Leader服务器在广播消息，那么该新加入的服务器自动进入`恢复模式`：找到`Leader服务器`，并且完成数据同步，同步完成后，作为新的Follower一起参与到消息广播流程中

**2.协议状态切换**

当Leader出现崩溃退出或者机器重启，亦或是集群中不存在超过半数的服务器与Leader保存正常通信，`Zab`就会再一次进入`崩溃恢复`，发起新一轮`Leader`选举并实现`数据同步`，同步完成后又会进入消息广播模式，接收事务请求

**3.保证消息有序**

在整个消息广播中，Leader会将每一个事务请求转换成对应的 proposal 来进行广播，并且在广播事务Proposal 之前，Leader服务器会首先为这个事务Proposal分配一个全局单递增的唯一ID，称之为事务ID（即zxid），由于Zab协议需要保证每一个消息的严格的顺序关系，因此必须将每一个proposal按照其zxid的先后顺序进行排序和处理

### 崩溃恢复

一旦 Leader 服务器出现崩溃或者由于网络原因导致 Leader 服务器失去了与过半 Follower 的联系，那么就会进入崩溃恢复模式

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk30.png)

```
具体过程如下：
1.为了保证 Leader 向 Learner 发送提案的有序，Leader 会为每一个 Learner 服务器准备一 个队列；
2.Leader 将那些没有被各个 Learner 同步的事务封装为 Proposal；
3.Leader 将这些 Proposal 逐条发给各个 Learner，并在每一个 Proposal 后都紧跟一个 COMMIT 消息，表示该事务已经被提交，Learner 可以直接接收并执行 ；
4.`Learner` 接收来自于 `Leader` 的 `Proposal`，并将其更新到本地；
5.当 `Learner` 更新成功后，会向准 `Leader` 发送 ACK 信息；
```

Leader 服务器在收到来自 Learner 的 ACK 后就会将该 Learner 加入到真正可用的 Follower 列表或 Observer 列表 没有反馈 ACK，或反馈了但 Leader 没有收到的 Learner，Leader 不会将其加入到相应列表

### 恢复模式的俩个原则

```
1. 已被处理过的消息不能丢
1.当 Leader 收到超过半数 Follower 的 ACKs 后，就向各个 Follower 广播 COMMIT 消息,批准各个 Server 执行该写操作事务,当各个 Server 在接收到 Leader 的 COMMIT 消息后就会在本地执行该写操作,然后会向客户端响应写操作成功

2.如果在非全部 Follower 收到 COMMIT 消息之前 Leader 就挂了，这将导致一种后果部分 Server 已经执行了该事务,而部分 Server 尚未收到 COMMIT 消息，所以其并没有执行该事务,当新的`Leader`被选举出,集群经过恢复模式后,需要保证所有Server 上都执行了那些已经被部分Server执行过的事务

2. 被丢弃的消息不能再现
当在Leader 新事务已经通过，其已经将该事务更新到了本地，但所有 Follower 还都没有收到 COMMIT 之前，Leader 宕机了（比前面叙述的宕机更早），此时，所有 Follower 根本 就不知道该 Proposal 的存在,当新的 Leader 选举出来，整个集群进入正常服务状态后，之前挂了的 Leader主机重新启动并注册成为了 Follower
若那个别人根本不知道的Proposal还保留在那个主机，那么其数据就会比其它主机多出了内容，导致整个系统状态的不一致 所以，该 Proposa 应该被丢弃 类似这样应该被丢弃的事务，是不能再次出现在集群中的， 应该被清除。
```

### 消息广播

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk31.png)

```
如果集群中的 Learner 节点收到客户端的事务请求，那么这些 Learner 会将请求转发给 Leader 服务器 然后再执行如下的具体过程：
1.Leader 接收到事务请求后，为事务赋予一个全局唯一的 64 位自增 id，即 zxid，通过 zxid 的大小比较即可实现事务的有序性管理，然后将事务封装为一个 Proposal 
2.Leader 根据Follower列表获取到所有 Follower，然后再将 Proposal 通过这些 Follower 的 队列将提案发送给各个Follower
3.当Follower 接收到提案后，会先将提案的 zxid 与本地记录的事务日志中的最大的 zxid 进行比较 若当前提案的 zxid 大于最大 zxid，则将当前提案记录到本地事务日志中，并 向 Leader 返回一个 ACK （提问学员）
4.当Leader 接收到过半的 ACKs 后，Leader 就会向所有 Follower 的队列发送 COMMIT 消息，向所有 Observer 的队列发送 Proposal
5.当Follower 收到 COMMIT 消息后，就会将日志中的事务正式更新到本地 当 Observer 收到 Proposal 后，会直接将事务更新到本地
6.无论是 Follower 还是 Observer，在同步完成后都需要向 Leader 发送成功 ACK
```

### 实现原理

#### 三类角色

```
Leader：接收和处理客户端的读请求；zk 集群中事务请求的唯一处理者，并负责发起决议和投票，然后将通过的事务请求在本地进行处理后，将处理结果同步给集群中的其它主机 
Follower：接收和处理客户端的读请求; 将事务请求转给Leader；同步Leader 中的数据；当 Leader 挂了，参与 Leader 的选举（具有选举权与被选举权）；
Observer：就是没有选举权与被选举权，且没有投票权的 Follower（临时工） 若 zk 集 群中的读压力很大，则需要增加 Observer，最好不要增加 Follower 因为增加 Follower 将会增大投票与统计选票的压力，降低写操作效率，及 Leader 选举的效率 
```

Learner = Follower + Observer

QuorumServer = Follower + Leader

在 ZAB 中有三个很重要的数据：

```
zxid：是一个 64 位长度的 Long 类型 其中高 32 位表示 epoch，低 32 表示 xid 
epoch：每个 Leader都会具有一个不同的 epoch，用于区分不同的时期（可以理解为朝代的年号）
xid：事务id，是一个流水号，（每次朝代更替，即leader更换），从0开始递增，每当选举产生一个新的 Leader ，就会从这个Leader服务器上取出本地事务日志中最大编号 Proposal 的 zxid，并从 zxid 中解析得到对应的 epoch 编号，然后再对其加1，之后该编号就作为新的 epoch 值，并将低32位数字归零，由0开始重新生成zxid
```

#### 三种状态

zk 集群中的每一台主机，在不同的阶段会处于不同的状态，每一台主机具有四种状态

```
LOOKING：选举状态
FOLLOWING：Follower 的正常工作状态，从 Leader 同步数据的状态
OBSERVING：Observer 的正常工作状态，从 Leader 同步数据的状态
LEADING：Leader 的正常工作状态，Leader 广播数据更新的状态
```

**代码实现中，多了一种状态：Observing 状态这是 Zookeeper 引入 Observer 之后加入的，Observer 不参与选举，是只读节点，实际上跟 Zab 协议没有关系 这里为了阅读源码加上此概念**

### ZAB 的四个阶段

```
myid:这是 zk 集群中服务器的唯一标识，称为 myid 例如，有三个 zk 服务器，那么编号分别 是 1,2,3 

逻辑时钟:逻辑时钟，Logicalclock，是一个整型数，该概念在选举时称为 logicalclock，而在选举结束后称为 epoch，即 epoch 与 logicalclock 是同一个值，在不同情况下的不同名称
```

#### 1.选举阶段

节点在一开始都处于选举节点，只要有一个节点得到超过半数节点的票数，它就可以当选准 Leader，只有到达第三个阶段（也就是同步阶段），这个准 Leader 才会成为真正的 Leader

Zookeeper 规定所有有效的投票都必须在同一个 轮次 中，每个服务器在开始新一轮投票时，都会对自己维护的 logicalClock 进行自增操作

每个服务器在广播自己的选票前，会将自己的投票箱（recvset）清空 该投票箱记录了所收到的选票

例如：`Server2` 投票给 `Server3`，`Server3` 投票给 Server1，则`Server_1`的投票箱为`(2,3)、(3,1)、(1,1)`（每个服务器都会默认给自己投票）

前一个数字表示投票者，后一个数字表示被选举者票箱中只会记录每一个投票者的最后一次投票记录，

如果投票者更新自己的选票，则其他服务器收到该新选票后会在自己的票箱中更新该服务器的选票 **思考下：这里在实现中应该怎么实现呢？等我们分析源码时就可以看到，非常的巧妙**

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk31.png)

这一阶段的目的就是为了选出一个准 Leader ，然后进入下一个阶段。

#### 2.发现阶段（Descovery）

在这个阶段，Followers 和上一轮选举出的准 Leader 进行通信，同步 Followers 最近接收的事务 Proposal 。

这个阶段的主要目的是发现当前大多数节点接收的最新 Proposal，并且准 Leader 生成新的 epoch ，让 Followers 接收，更新它们的 acceptedEpoch

#### 3.同步阶段（Synchronization)

同步阶段主要是利用 Leader 前一阶段获得的最新 Proposal 历史，同步集群中所有的副本。

只有当 quorum（超过半数的节点） 都同步完成，准 Leader 才会成为真正的 Leader。Follower 只会接收 zxid 比自己 lastZxid 大的 Proposal

#### 4.广播阶段（Broadcast）

到了这个阶段，Zookeeper 集群才能正式对外提供事务服务，并且 Leader 可以进行消息广播。同时，如果有新的节点加入

还需要对新节点进行同步。需要注意的是，Zab 提交事务并不像 2PC 一样需要全部 Follower 都 Ack，只需要得到 quorum（超过半数的节点）的Ack 就可以

## 一、启动流程

1. 工程结构介绍
2. 启动流程宏观图
3. 集群启动详细流程
4. netty 服务工作机制

### 1.工程结构介绍

项目地址:https://github.com/apache/zookeeper.git

分支tag ：3.5.5

- zookeeper-recipes: 示例源码
- zookeeper-client: C语言客户端
- zookeeper-server：主体源码

### **2.启动宏观流程图：**

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk20.png)

- 启动示例演示：

**服务端：**ZooKeeperServerMain

**客户端：**ZooKeeperMain

### 3.集群启动详细流程

装载配置：

```
# zookeeper 启动流程堆栈
 >QuorumPeerMain#initializeAndRun //启动工程 
   >QuorumPeerConfig#parse // 加载config 配置
    >QuorumPeerConfig#parseProperties// 解析config配置
 >new DatadirCleanupManager // 构造一个数据清器
  >DatadirCleanupManager#start // 启动定时任务 清除过期的快照
```

**代码堆栈 ：**

```
>QuorumPeerMain#main  //启动main方法
 >QuorumPeerConfig#parse // 加载zoo.cfg 文件
   >QuorumPeerConfig#parseProperties // 解析配置
 >DatadirCleanupManager#start // 启动定时任务清除日志
 >QuorumPeerConfig#isDistributed // 判断是否为集群模式
  >ServerCnxnFactory#createFactory() // 创建服务默认为NIO，推荐netty
  
 //***创建 初始化集群管理器**/
 >QuorumPeerMain#getQuorumPeer
 >QuorumPeer#setTxnFactory 
 >new FileTxnSnapLog // 数据文件管理器，用于检测快照与日志文件
   /**初始化数据库**/
  >new ZKDatabase 
  >ZKDatabase#createDataTree //创建数据树，所有的节点都会存储在这
 // 启动集群：同时启动线程
  > QuorumPeer#start // 
    > QuorumPeer#loadDataBase // 从快照文件以及日志文件 加载节点并填充到dataTree中去
    > QuorumPeer#startServerCnxnFactory // 启动netty 或java nio 服务，对外开放2181 端口
    > AdminServer#start// 启动管理服务，netty http服务，默认端口是8080
    > QuorumPeer#startLeaderElection // 开始执行选举流程
    > quorumPeer.join()  // 防止主进程退出 
```

**流程说明:**

1. main方法启动
2. 加载zoo.cfg 配置文件
3. 解析配置
4. 创建服务工厂
5. 创建集群管理线程 1. 设置数据库文件管理器 2. 设置数据库 3. ....设置设置
6. start启动集群管理线程 
   1. 加载数据节点至内存 
   2.  启动netty 服务，对客户端开放端口 
   3.  启动管理员Http服务，默认8080端口 
   4.  启动选举流程
7. join 管理线程，防止main 进程退出

### 4.netty 服务启动流程：

服务UML类图

设置netty启动参数

```
-Dzookeeper.serverCnxnFactory=org.apache.zookeeper.server.NettyServerCnxnFactory
```

**初始化：**

关键代码：

```
#初始化管道流 
#channelHandler 是一个内部类是具体的消息处理器 
protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (secure) {
        initSSL(pipeline);
    }
    pipeline.addLast("servercnxnfactory", channelHandler);
}
```

channelHandler 类结构!

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk22.png)

执行堆栈：

```
NettyServerCnxnFactory#NettyServerCnxnFactory 	// 初始化netty服务工厂
  > NettyUtils.newNioOrEpollEventLoopGroup 	// 创建IO线程组
  > NettyUtils#newNioOrEpollEventLoopGroup() 	// 创建工作线程组
  >ServerBootstrap#childHandler(io.netty.channel.ChannelHandler) // 添加管道流
>NettyServerCnxnFactory#start 			// 绑定端口，并启动netty服务
```

**创建连接：**

每当有客户端新连接进来，就会进入该方法 创建 NettyServerCnxn对象 并添加至cnxns对例

执行堆栈

```
CnxnChannelHandler#channelActive
 >new NettyServerCnxn 		     // 构建连接器
>NettyServerCnxnFactory#addCnxn     // 添加至连接器，并根据客户端IP进行分组
 >ipMap.get(addr) // 基于IP进行分组
```

**读取消息：** 执行堆栈

```
CnxnChannelHandler#channelRead
>NettyServerCnxn#processMessage //  处理消息 
 >NettyServerCnxn#receiveMessage // 接收消息
  >ZooKeeperServer#processPacket //处理消息包
   >org.apache.zookeeper.server.Request // 封装request 对象
    >org.apache.zookeeper.server.ZooKeeperServer#submitRequest // 提交request  
     >org.apache.zookeeper.server.RequestProcessor#processRequest // 处理请求
```

## 二、快照与事务日志存储结构

------

### 概要:

ZK中所有的数据都是存储在内存中，即zkDataBase中 但同时所有对ZK数据的变更都会记录到事物日志中，并且当写入到一定的次数就会进行一次快照的生成 已保证数据的备份 其后缀就是ZXID（唯一事物ID）

- 事物日志：每次增删改，的记录日志都会保存在文件当中
- 快照日志：存储了在指定时间节点下的所有的数据

### **存储结构:**

zkDdataBase 是zk数据库基类，所有节点都会保存在该类当中，而对Zk进行任何的数据变更都会基于该类进行 zk数据的存储是通过DataTree 对象进行，其用了一个map 来进行存储

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk23.png)

UML 类图：

![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/zk24.png)

读取快照日志：

```
org.apache.zookeeper.server.SnapshotFormatter
```

读取事物日志：

```
org.apache.zookeeper.server.LogFormatter
```

### 快照相关配置：

| dataLogDir                | 事物日志目录                                                 |
| ------------------------- | ------------------------------------------------------------ |
| zookeeper.preAllocSize    | 预先开辟磁盘空间，用于后续写入事务日志，默认64M              |
| zookeeper.snapCount       | 每进行snapCount次事务日志输出后，触发一次快照，默认是100,000 |
| autopurge.snapRetainCount | 自动清除时 保留的快照数                                      |
| autopurge.purgeInterval   | 清除时间间隔，小时为单位 -1 表示不自动清除                   |

### **快照装载流程：**

```
>ZooKeeperServer#loadData // 加载数据
>FileTxnSnapLog#restore // 恢复数据
>FileSnap#deserialize() // 反序列化数据
>FileSnap#findNValidSnapshots // 查找有效的快照
  >Util#sortDataDir // 基于后缀排序文件
    >persistence.Util#isValidSnapshot // 验证是否有效快照文件
```







# ZAB协议介绍

ZAB协议（Zookeeper Atomic Broadcast）时Zk中保证数据一致性的重要协议，是基于Paxos算法单独为Zk实现的协议。

ZAB协议是一种支持崩溃恢复和原子广播的协议，基于zab协议，zk实现了一种主备模式的系统架构，由单一的主进程来处理客户端的请求，并且为每个请求分配一个全局唯一单调递增的事务ID（zxid），然后由主进程将数据广播给副本进程上去。所以说zk是一个保证顺序一致性的服务。

> 所有事务请求必须由一个全局唯一的服务器来协调处理，这样的服务器称之为Leader服务器，而余下的服务器称之为Follower服务器。Leader服务器负责将一个客户端事务请求转化为一个事务Proposal（提议），并将该事务提议分发给Follower，之后Leader等待Follower的ACK反馈，一但超过半数的Follower正确反馈，那么Leader将向所有Follower发送一个commit消息，要求Follower把前一个事务提议进行提交。

Zab协议的过程如下图所示：
zk服务器由三节点构成，1主2从，客户端所有的事务请求由Leader进行处理，并且通过广播发送给Follower副本。
![ZAB流程](https://img-blog.csdnimg.cn/20200621191122799.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1l1X3V1dXV1,size_16,color_FFFFFF,t_70)

## ZAB协议基本特性

（1）高可用：由于采用主备模型，可以保证当Leader节点挂机或者网络分区后，集群依然工作对外提供服务。（当集群中超过半数节点存活，整个集群可用）

（2）数据一致性：一个事务请求只能由Leader节点处理，Leader节点将事务请求广播给副本节点。保证如果一个事务被提交成功，那么所有节点都应该被处理成功。

> 1、Zab协议确保在Leader服务器提交的事务最终被所有服务器提交
> 2、Zab协议需要确保丢弃那些只在 Leader 上被提出而没有被提交的事务。

（3）保证事务顺序：由于zk是树形目录结构，创建子节点时需要存在父节点，Zab要保证同一个Leader发起的事务按顺序执行，同时还要保证只有先前Leader的事务被执行之后，新的Leader才能再次发起事务。

## ZAB协议核心

### ZAB协议基本模式

ZAB协议有两种基本模式，分别为**崩溃恢复和消息广播**，接下来通过一个流程介绍两种模式分别是什么。

1. **服务器启动：** 当服务器第一次启动时，整个集群服务没有Leader节点，则此时进入恢复模式并选举一个Leader节点，选举成功后其他节点与Leader保持状态同步，当过半的节点与Leader状态同步后则退出恢复模式，进入消息广播模式。
2. **服务器运行：** 服务运行期间为消息广播模式阶段，客户端的每个事务提议请求（proposal）由Leader节点生成一个zxid转化为事务请求，然后通过消息广播发给Follower节点，当超过半数的Follower节点正确响应后，提交该事务。
3. **新节点加入：** 当一个新的zk服务节点加入时，如果此时集群中存在Leader节点，那么将自动切换为数据恢复模式，与Leader节点进行数据同步，然后参与到消息广播中去。
4. **Leader节点宕机、网络分区、超过半数节点状态不同步：** 当出现上述中的情况时，那么整个ZK服务停止，由广播模式切换到崩溃恢复模式，然后经过选举、同步一系列操作后，再次进入新一轮消息广播模式中。

### 崩溃恢复与消息广播

ZXID：为每一个事务请求分配的单调递增唯一ID标识，其中低32位标识Leader任期的epoch编号，高32位标识该任期内的事务id。每次Leader选举epoch都会增加，事务id归零。

**消息广播：**
ZAB协议消息广播，是类似于一个两阶段提交协议实现的，整体分为三个步骤。
1、首先Leader收到客户端的数据更新请求后，转化为一个事务提议，然后向Follower节点发送数据变更请求。
2、当Follower接受到事务请求后，将事务以日志的形式写入本地磁盘，并且返回给Leader一个ACK响应。
3、当Leader收到超过半数（半数中包括Leader）的正确ACK响应后，发送该事务的提交请求commit给Follower。
![消息广播](https://img-blog.csdnimg.cn/20200621200049416.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1l1X3V1dXV1,size_16,color_FFFFFF,t_70)
其实在实际的通信过程中，Leader会为每个事务请求分配一个全局单调递增的ZXID，因此每个事务按照ZXID的顺序进行排序处理。Leader还会为每个Follower创建一个FIFO的队列，用来存放Leader的请求，这样可以保证每个事务的顺序执行，ZK不要求每个Follower执行完毕，只需要保证写入FIFO队列中即可。

![完整广播](https://img-blog.csdnimg.cn/2020062120132787.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1l1X3V1dXV1,size_16,color_FFFFFF,t_70)

**崩溃恢复：**
当Leader节点崩溃或下线时，还可能由于网络分区导致Leader失去与超过一半节点的连接时，此时进入到崩溃恢复模式。

ZAB协议为了保证程序的正确运行，此时需要通过Leader选举算法选举出一个新的Leader节点，同时能够让其他所有节点快速感知到新选举的Leader节点。

ZAB协议还要确保，无论出现任何问题都需要保证数据一致性，在崩溃恢复的过程中，可能会出现两种情况出现数据不一致。
P1：代表Proposal1事务的请求
C1：代表Proposal1事务的提交Commit

情况一：
当Leader发送C2的时候崩溃退出，那么ZAB协议需要保证P2最终能够在所有服务器上执行，否则会出现不一致。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200621202707816.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1l1X3V1dXV1,size_16,color_FFFFFF,t_70)
情况2：
如果在崩溃恢复过程中，需要丢弃一个Proposal提案时，那么确保在崩溃恢复结算时跳过需要被丢弃的提案。

如下图所示，Proposal3 只有在Leader上提出后便发生崩溃，那么需要确保在集群恢复后，保证Proposal3被跳过，不会执行。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200621203133183.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1l1X3V1dXV1,size_16,color_FFFFFF,t_70)
结合上面的案例，ZAB协议决定Leader选举算法必须满足已上两个条件：1、确保提交被Leader提交的事务；2、确保丢弃已经被跳过的事务；
在Leader选举阶段，通过选取具有最大事务编号zxid的为Leader节点，这样保证新的Leader具有最全的数据，并且Leader服务器需要确保每一个Proposal都被半数以上机器提交。

当Leader选举完毕后，则崩溃恢复进入到数据恢复阶段。当其他服务器感知到Leader服务器出现后，则从Leader服务器同步数据，

完成 Leader 选举后（新的 Leader 具有最大的zxid），在开始对外提供服务之前，Leader 服务器会首先确认事务日志中的所有的 Proposal 是否已经被集群中过半的服务器 Commit。

Leader 服务器需要确保所有的 Follower 服务器能够接收到每一条事务的 Proposal ，并且能将所有已经提交的事务 Proposal 应用到内存数据中。等到 Follower 将所有尚未同步的事务 Proposal 都从 Leader 服务器上同步过啦并且应用到内存数据中以后，Leader 才会把该 Follower 加入到真正可用的 Follower 列表中。

等到Follower服务器保持与Leader同步后，Leader就会将该机器加入到可用Follower列表当中，列表中超过过半机器后，则进入消息广播模式，开始对外提供服务。

## ZAB协议内部原理

从算法角度描述，ZAB协议主要分为三个阶段，分别为发现、同步、广播，每一个进程都会循环执行三个阶段，一个循环称之为一个主进程周期。

**阶段一 发现：**
就是Leader选举阶段，该阶段选择具有最大zxid的服务器，并且会从过半Follower中选择最大的epoch然后+1 生成新的epoch发送给follower。

**阶段二 同步：**
该阶段，Leader将自己的epoch和事务集合发送给Follower，Follower判断自己的epoch是否和Leader一致，如果不一致说明自己还在之前的轮次，不参与同步，如果一致则会执行Leader的事务集合，然后返回给Leader响应。当Leader收到过半Follower的ACK响应后，会再次发送Commit信息。

**阶段三 广播：**
在完成同步阶段后，此时可以对外提供服务了，并且接受客户端新的事务请求，进入到广播流程。

**小结：**
ZK服务运行期间，会不断进行上述三个阶段的循环，每一次循环为一个周期。