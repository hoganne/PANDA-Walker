# dubbo

## 背景

本文介绍了网站应用的演进

随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需一个治理系统确保架构有条不紊的演进。

![image](E:\oldF\learningDocument\dubbo\dubbo-architecture-roadmap.jpg)

#### 单一应用架构

当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键。

#### 垂直应用架构

当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，提升效率的方法之一是将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。

#### 分布式服务架构

当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的分布式服务框架(RPC)是关键。

#### 流动计算架构

当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于提高机器利用率的资源调度和治理中心(SOA)是关键。

## 需求

本文介绍了 Dubbo 要解决的需求

![image](E:\oldF\learningDocument\dubbo\dubbo-service-governance.jpg)

在大规模服务化之前，应用可能只是通过 RMI 或 Hessian 等工具，简单的暴露和引用远程服务，通过配置服务的URL地址进行调用，通过 F5 等硬件进行负载均衡。

**当服务越来越多时，服务 URL 配置管理变得非常困难，F5 硬件负载均衡器的单点压力也越来越大。** 此时需要一个服务注册中心，动态地注册和发现服务，使服务的位置透明。并通过在消费方获取服务提供方地址列表，实现软负载均衡和 Failover，降低对 F5 硬件负载均衡器的依赖，也能减少部分成本。

**当进一步发展，服务间依赖关系变得错踪复杂，甚至分不清哪个应用要在哪个应用之前启动，架构师都不能完整的描述应用的架构关系。** 这时，需要自动画出应用间的依赖关系图，以帮助架构师理清关系。

**接着，服务的调用量越来越大，服务的容量问题就暴露出来，这个服务需要多少机器支撑？什么时候该加机器？** 为了解决这些问题，第一步，要将服务现在每天的调用量，响应时间，都统计出来，作为容量规划的参考指标。其次，要可以动态调整权重，在线上，将某台机器的权重一直加大，并在加大的过程中记录响应时间的变化，直到响应时间到达阈值，记录此时的访问量，再以此访问量乘以机器数反推总容量。

以上是 Dubbo 最基本的几个需求。

## 架构



![dubbo-architucture](E:\oldF\learningDocument\dubbo\dubbo-architecture.jpg)

##### 节点角色说明

| 节点        | 角色说明                               |
| ----------- | -------------------------------------- |
| `Provider`  | 暴露服务的服务提供方                   |
| `Consumer`  | 调用远程服务的服务消费方               |
| `Registry`  | 服务注册与发现的注册中心               |
| `Monitor`   | 统计服务的调用次数和调用时间的监控中心 |
| `Container` | 服务运行容器                           |

##### 调用关系说明

1. 服务容器负责启动，加载，运行服务提供者。
2. 服务提供者在启动时，向注册中心注册自己提供的服务。
3. 服务消费者在启动时，向注册中心订阅自己所需的服务。
4. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

Dubbo 架构具有以下几个特点，分别是连通性、健壮性、伸缩性、以及向未来架构的升级性。

### 连通性

- `注册中心`负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求，压力较小
- `监控中心`负责统计各服务调用次数，调用时间等，统计先在内存汇总后每分钟一次发送到监控中心服务器，并以报表展示
- `服务提供者`向注册中心注册其提供的服务，并汇报调用时间到监控中心，此时间不包含网络开销
- `服务消费者`向注册中心获取服务提供者地址列表，并根据负载算法直接调用提供者，同时汇报调用时间到监控中心，此时间包含网络开销
- 注册中心，服务提供者，服务消费者三者之间均为`长连接`，监控中心除外
- 注册中心通过长连接感知服务提供者的存在，服务提供者宕机，注册中心将立即推送事件通知消费者
- 注册中心和监控中心全部宕机，不影响已运行的提供者和消费者，消费者在本地缓存了提供者列表
- 注册中心和监控中心都是可选的，服务消费者可以直连服务提供者

### 健壮性

- 监控中心宕掉不影响使用，只是丢失部分采样数据
- 数据库宕掉后，注册中心仍能通过缓存提供服务列表查询，但不能注册新服务
- 注册中心对等集群，任意一台宕掉后，将自动切换到另一台
- 注册中心全部宕掉后，服务提供者和服务消费者仍能通过本地缓存通讯
- 服务提供者无状态，任意一台宕掉后，不影响使用
- 服务提供者全部宕掉后，服务消费者应用将无法使用，并无限次重连等待服务提供者恢复

### 伸缩性

- 注册中心为对等集群，可动态增加机器部署实例，所有客户端将自动发现新的注册中心
- 服务提供者无状态，可动态增加机器部署实例，注册中心将推送新的服务提供者信息给消费者

### 升级性

当服务集群规模进一步扩大，带动IT治理结构进一步升级，需要实现动态部署，进行流动计算，现有分布式服务架构不会带来阻力。下图是未来可能的一种架构：

![dubbo-architucture-futures](E:\oldF\learningDocument\dubbo\dubbo-architecture-future.jpg)

##### 节点角色说明

| 节点         | 角色说明                               |
| ------------ | -------------------------------------- |
| `Deployer`   | 自动部署服务的本地代理                 |
| `Repository` | 仓库用于存储服务应用发布包             |
| `Scheduler`  | 调度中心基于访问压力自动增减服务提供者 |
| `Admin`      | 统一管理控制台                         |
| `Registry`   | 服务注册与发现的注册中心               |
| `Monitor`    | 统计服务的调用次数和调用时间的监控中心 |

## 用法

Dubbo 的简单实用入门

### 本地服务 Spring 配置

local.xml:

```xml
<bean id=“xxxService” class=“com.xxx.XxxServiceImpl” />
<bean id=“xxxAction” class=“com.xxx.XxxAction”>
    <property name=“xxxService” ref=“xxxService” />
</bean>
```

### 远程服务 Spring 配置

在本地服务的基础上，只需做简单配置，即可完成远程化：

- 将上面的 `local.xml` 配置拆分成两份，将服务定义部分放在服务提供方 `remote-provider.xml`，将服务引用部分放在服务消费方 `remote-consumer.xml`。
- 并在提供方增加暴露服务配置 `<dubbo:service>`，在消费方增加引用服务配置 `<dubbo:reference>`。

remote-provider.xml:

```xml
<!-- 和本地服务一样实现远程服务 -->
<bean id=“xxxService” class=“com.xxx.XxxServiceImpl” /> 
<!-- 增加暴露远程服务配置 -->
<dubbo:service interface=“com.xxx.XxxService” ref=“xxxService” /> 
```

remote-consumer.xml:

```xml
<!-- 增加引用远程服务配置 -->
<dubbo:reference id=“xxxService” interface=“com.xxx.XxxService” />
<!-- 和本地服务一样使用远程服务 -->
<bean id=“xxxAction” class=“com.xxx.XxxAction”> 
    <property name=“xxxService” ref=“xxxService” />
</bean>
```

## 快速开始

快速开始使用 Dubbo

Dubbo 采用全 Spring 配置方式，透明化接入应用，对应用没有任何 API 侵入，只需用 Spring 加载 Dubbo 的配置即可，Dubbo 基于 [Spring 的 Schema 扩展](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html) 进行加载。

如果不想使用 Spring 配置，可以通过 [API 的方式](http://dubbo.apache.org/zh/docs/v2.7/user/configuration/api) 进行调用。

### 服务提供者

完整安装步骤，请参见：[示例提供者安装](http://dubbo.apache.org/zh/docs/v2.7/admin/install/provider-demo)

定义服务接口

DemoService.java [1](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fn:1)：

```java
package org.apache.dubbo.demo;

public interface DemoService {
    String sayHello(String name);
}
```

在服务提供方实现接口

DemoServiceImpl.java [2](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fn:2)：

```java
package org.apache.dubbo.demo.provider;
 
import org.apache.dubbo.demo.DemoService;
 
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
```

#### 用 Spring 配置声明暴露服务

provider.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd        http://dubbo.apache.org/schema/dubbo        http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
 
    <!-- 提供方应用信息，用于计算依赖关系 -->
    <dubbo:application name="hello-world-app"  />
 
    <!-- 使用multicast广播注册中心暴露服务地址 -->
    <dubbo:registry address="multicast://224.5.6.7:1234" />
 
    <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />
 
    <!-- 声明需要暴露的服务接口 -->
    <dubbo:service interface="org.apache.dubbo.demo.DemoService" ref="demoService" />
 
    <!-- 和本地bean一样实现服务 -->
    <bean id="demoService" class="org.apache.dubbo.demo.provider.DemoServiceImpl" />
</beans>
```

#### 加载 Spring 配置

Provider.java：

```java
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
```

### 服务消费者

完整安装步骤，请参见：[示例消费者安装](http://dubbo.apache.org/zh/docs/v2.7/user/admin/install/consumer-demo.md)

#### 通过 Spring 配置引用远程服务

consumer.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd        http://dubbo.apache.org/schema/dubbo        http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
 
    <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
    <dubbo:application name="consumer-of-helloworld-app"  />
 
    <!-- 使用multicast广播注册中心暴露发现服务地址 -->
    <dubbo:registry address="multicast://224.5.6.7:1234" />
 
    <!-- 生成远程服务代理，可以和本地bean一样使用demoService -->
    <dubbo:reference id="demoService" interface="org.apache.dubbo.demo.DemoService" />
</beans>
```

#### 加载Spring配置，并调用远程服务

Consumer.java [3](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fn:3)：

```java
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.dubbo.demo.DemoService;
 
public class Consumer {
    public static void main(String[] args) throws Exception {
       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法
        System.out.println( hello ); // 显示调用结果
    }
}
```

------

1. 该接口需单独打包，在服务提供方和消费方共享 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fnref:1)
2. 对服务消费方隐藏实现 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fnref:2)
3. 也可以使用 IoC 注入 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/quick-start/#fnref:3)

# 用法示例

## 启动时检查

在启动时检查依赖的服务是否可用

Dubbo 缺省会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成，以便上线时，能及早发现问题，默认 `check="true"`。

可以通过 `check="false"` 关闭检查，比如，测试时，有些服务不关心，或者出现了循环依赖，必须有一方先启动。

另外，如果你的 Spring 容器是懒加载的，或者通过 API 编程延迟引用服务，请关闭 check，否则服务临时不可用时，会抛出异常，拿到 null 引用，如果 `check="false"`，总是会返回引用，当服务恢复时，能自动连上。

### 示例

### 通过 spring 配置文件

关闭某个服务的启动时检查 (没有提供者时报错)：

```xml
<dubbo:reference interface="com.foo.BarService" check="false" />
```

关闭所有服务的启动时检查 (没有提供者时报错)：

```xml
<dubbo:consumer check="false" />
```

关闭注册中心启动时检查 (注册订阅失败时报错)：

```xml
<dubbo:registry check="false" />
```

### 通过 dubbo.properties

```fallback
dubbo.reference.com.foo.BarService.check=false
dubbo.reference.check=false
dubbo.consumer.check=false
dubbo.registry.check=false
```

### 通过 -D 参数

```sh
java -Ddubbo.reference.com.foo.BarService.check=false
java -Ddubbo.reference.check=false
java -Ddubbo.consumer.check=false 
java -Ddubbo.registry.check=false
```

### 配置的含义

`dubbo.reference.check=false`，强制改变所有 reference 的 check 值，就算配置中有声明，也会被覆盖。

`dubbo.consumer.check=false`，是设置 check 的缺省值，如果配置中有显式的声明，如：`<dubbo:reference check="true"/>`，不会受影响。

`dubbo.registry.check=false`，前面两个都是指订阅成功，但提供者列表是否为空是否报错，如果注册订阅失败时，也允许启动，需使用此选项，将在后台定时重试。

## 集群容错

集群调用失败时，Dubbo 提供的容错方案

在集群调用失败时，Dubbo 提供了多种容错方案，缺省为 failover 重试。

![cluster](E:\oldF\learningDocument\dubbo\cluster.jpg)

各节点关系：

- 这里的 `Invoker` 是 `Provider` 的一个可调用 `Service` 的抽象，`Invoker` 封装了 `Provider` 地址及 `Service` 接口信息
- `Directory` 代表多个 `Invoker`，可以把它看成 `List<Invoker>` ，但与 `List` 不同的是，它的值可能是动态变化的，比如注册中心推送变更
- `Cluster` 将 `Directory` 中的多个 `Invoker` 伪装成一个 `Invoker`，对上层透明，伪装过程包含了容错逻辑，调用失败后，重试另一个
- `Router` 负责从多个 `Invoker` 中按路由规则选出子集，比如读写分离，应用隔离等
- `LoadBalance` 负责从多个 `Invoker` 中选出具体的一个用于本次调用，选的过程包含了负载均衡算法，调用失败后，需要重选

### 集群容错模式

可以自行扩展集群容错策略，参见：[集群扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/cluster)

### Failover Cluster

失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 `retries="2"` 来设置重试次数(不含第一次)。

重试次数配置如下：

```xml
<dubbo:service retries="2" />
```

或

```xml
<dubbo:reference retries="2" />
```

或

```xml
<dubbo:reference>
    <dubbo:method name="findFoo" retries="2" />
</dubbo:reference>
```

#### 提示

该配置为缺省配置

### Failfast Cluster

快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录。

### Failsafe Cluster

失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。

### Failback Cluster

失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。

### Forking Cluster

并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 `forks="2"` 来设置最大并行数。

### Broadcast Cluster

广播调用所有提供者，逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息。

#### 提示

`2.1.0` 开始支持

### 集群模式配置

按照以下示例在服务提供方和消费方配置集群模式

```xml
<dubbo:service cluster="failsafe" />
```

或

```xml
<dubbo:reference cluster="failsafe" />
```

## 负载均衡

Dubbo 提供的集群负载均衡策略

在集群负载均衡时，Dubbo 提供了多种均衡策略，缺省为 `random` 随机调用。

可以自行扩展负载均衡策略，参见：[负载均衡扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/load-balance)

### 负载均衡策略

### Random LoadBalance

- **随机**，按权重设置随机概率。
- 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。

### RoundRobin LoadBalance

- **轮询**，按公约后的权重设置轮询比率。
- 存在慢的提供者累积请求的问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。

### LeastActive LoadBalance

- **最少活跃调用数**，相同活跃数的随机，活跃数指调用前后计数差。
- 使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。

### ConsistentHash LoadBalance

- **一致性 Hash**，相同参数的请求总是发到同一提供者。
- 当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
- 算法参见：http://en.wikipedia.org/wiki/Consistent_hashing
- 缺省只对第一个参数 Hash，如果要修改，请配置 `<dubbo:parameter key="hash.arguments" value="0,1" />`
- 缺省用 160 份虚拟节点，如果要修改，请配置 `<dubbo:parameter key="hash.nodes" value="320" />`

### 配置

### 服务端服务级别

```xml
<dubbo:service interface="..." loadbalance="roundrobin" />
```

### 客户端服务级别

```xml
<dubbo:reference interface="..." loadbalance="roundrobin" />
```

### 服务端方法级别

```xml
<dubbo:service interface="...">
    <dubbo:method name="..." loadbalance="roundrobin"/>
</dubbo:service>
```

### 客户端方法级别

```xml
<dubbo:reference interface="...">
    <dubbo:method name="..." loadbalance="roundrobin"/>
</dubbo:reference>
```

## 线程模型

配置 Dubbo 中的线程模型

如果事件处理的逻辑能迅速完成，并且不会发起新的 IO 请求，比如只是在内存中记个标识，则直接在 IO 线程上处理更快，因为减少了线程池调度。

但如果事件处理逻辑较慢，或者需要发起新的 IO 请求，比如需要查询数据库，则必须派发到线程池，否则 IO 线程阻塞，将导致不能接收其它请求。

如果用 IO 线程处理事件，又在事件处理过程中发起新的 IO 请求，比如在连接事件中发起登录请求，会报“可能引发死锁”异常，但不会真死锁。

![dubbo-protocol](E:\oldF\learningDocument\dubbo\dubbo-protocol.jpg)

因此，需要通过不同的派发策略和不同的线程池配置的组合来应对不同的场景:

```xml
<dubbo:protocol name="dubbo" dispatcher="all" threadpool="fixed" threads="100" />
```

Dispatcher

- `all` 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件，心跳等。
- `direct` 所有消息都不派发到线程池，全部在 IO 线程上直接执行。
- `message` 只有请求响应消息派发到线程池，其它连接断开事件，心跳等消息，直接在 IO 线程上执行。
- `execution` 只有请求消息派发到线程池，不含响应，响应和其它连接断开事件，心跳等消息，直接在 IO 线程上执行。
- `connection` 在 IO 线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池。

ThreadPool

- `fixed` 固定大小线程池，启动时建立线程，不关闭，一直持有。(缺省)
- `cached` 缓存线程池，空闲一分钟自动删除，需要时重建。
- `limited` 可伸缩线程池，但池中的线程数只会增长不会收缩。只增长不收缩的目的是为了避免收缩时突然来了大流量引起的性能问题。
- `eager` 优先创建`Worker`线程池。在任务数量大于`corePoolSize`但是小于`maximumPoolSize`时，优先创建`Worker`来处理任务。当任务数量大于`maximumPoolSize`时，将任务放入阻塞队列中。阻塞队列充满时抛出`RejectedExecutionException`。(相比于`cached`:`cached`在任务数量超过`maximumPoolSize`时直接抛出异常而不是将任务放入阻塞队列)

## 直连提供者

Dubbo 中点对点的直连方式

在开发及测试环境下，经常需要绕过注册中心，只测试指定服务提供者，这时候可能需要点对点直连，点对点直连方式，将以服务接口为单位，忽略注册中心的提供者列表，A 接口配置点对点，不影响 B 接口从注册中心获取列表。

![/user-guide/images/dubbo-directly.jpg](E:\oldF\learningDocument\dubbo\dubbo-directly.jpg)

### 通过 XML 配置

如果是线上需求需要点对点，可在 `<dubbo:reference>` 中配置 url 指向提供者，将绕过注册中心，多个地址用分号隔开，配置如下：

```xml
<dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />
```

#### 提示

### 通过 -D 参数指定

在 JVM 启动参数中加入-D参数映射服务地址，如：

```sh
java -Dcom.alibaba.xxx.XxxService=dubbo://localhost:20890
```

#### 提示

key 为服务名，value 为服务提供者 url，此配置优先级最高，`1.0.15` 及以上版本支持

### 通过文件映射

如果服务比较多，也可以用文件映射，用 `-Ddubbo.resolve.file` 指定映射文件路径，此配置优先级高于 `<dubbo:reference>` 中的配置 [^3]，如：

```sh
java -Ddubbo.resolve.file=xxx.properties
```

然后在映射文件 `xxx.properties` 中加入配置，其中 key 为服务名，value 为服务提供者 URL：

```fallback
com.alibaba.xxx.XxxService=dubbo://localhost:20890
```

#### 提示

`1.0.15` 及以上版本支持，`2.0` 以上版本自动加载 ${user.home}/dubbo-resolve.properties文件，不需要配置

#### 注意

为了避免复杂化线上环境，不要在线上使用这个功能，只应在测试阶段使用。

## 只订阅

只订阅不注册

为方便开发测试，经常会在线下共用一个所有服务可用的注册中心，这时，如果一个正在开发中的服务提供者注册，可能会影响消费者不能正常运行。

可以让服务提供者开发方，只订阅服务(开发的服务可能依赖其它服务)，而不注册正在开发的服务，通过直连测试正在开发的服务。

![/user-guide/images/subscribe-only.jpg](E:\oldF\learningDocument\dubbo\subscribe-only.jpg)

禁用注册配置

```xml
<dubbo:registry address="10.20.153.10:9090" register="false" />
```

或者

```xml
<dubbo:registry address="10.20.153.10:9090?register=false" />
```

## 多协议

在 Dubbbo 中配置多协议

Dubbo 允许配置多协议，在不同服务上支持不同协议或者同一服务上同时支持多种协议。

### 不同服务不同协议

不同服务在性能上适用不同协议进行传输，比如大数据用短连接协议，小数据大并发用长连接协议

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd"> 
    <dubbo:application name="world"  />
    <dubbo:registry id="registry" address="10.20.141.150:9090" username="admin" password="hello1234" />
    <!-- 多协议配置 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:protocol name="rmi" port="1099" />
    <!-- 使用dubbo协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" protocol="dubbo" />
    <!-- 使用rmi协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.DemoService" version="1.0.0" ref="demoService" protocol="rmi" /> 
</beans>
```

### 多协议暴露服务

需要与 http 客户端互操作

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="world"  />
    <dubbo:registry id="registry" address="10.20.141.150:9090" username="admin" password="hello1234" />
    <!-- 多协议配置 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:protocol name="hessian" port="8080" />
    <!-- 使用多个协议暴露服务 -->
    <dubbo:service id="helloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" protocol="dubbo,hessian" />
</beans>
```

## 多注册中心

在 Dubbo 中把同一个服务注册到多个注册中心上

Dubbo 支持同一服务向多注册中心同时注册，或者不同服务分别注册到不同的注册中心上去，甚至可以同时引用注册在不同注册中心上的同名服务。另外，注册中心是支持自定义扩展的 [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/multi-registry/#fn:1)。

### 多注册中心注册

比如：中文站有些服务来不及在青岛部署，只在杭州部署，而青岛的其它应用需要引用此服务，就可以将服务同时注册到两个注册中心。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="world"  />
    <!-- 多注册中心配置 -->
    <dubbo:registry id="hangzhouRegistry" address="10.20.141.150:9090" />
    <dubbo:registry id="qingdaoRegistry" address="10.20.141.151:9010" default="false" />
    <!-- 向多个注册中心注册 -->
    <dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" registry="hangzhouRegistry,qingdaoRegistry" />
</beans>
```

### 不同服务使用不同注册中心

比如：CRM 有些服务是专门为国际站设计的，有些服务是专门为中文站设计的。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="world"  />
    <!-- 多注册中心配置 -->
    <dubbo:registry id="chinaRegistry" address="10.20.141.150:9090" />
    <dubbo:registry id="intlRegistry" address="10.20.154.177:9010" default="false" />
    <!-- 向中文站注册中心注册 -->
    <dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" registry="chinaRegistry" />
    <!-- 向国际站注册中心注册 -->
    <dubbo:service interface="com.alibaba.hello.api.DemoService" version="1.0.0" ref="demoService" registry="intlRegistry" />
</beans>
```

### 多注册中心引用

比如：CRM 需同时调用中文站和国际站的 PC2 服务，PC2 在中文站和国际站均有部署，接口及版本号都一样，但连的数据库不一样。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="world"  />
    <!-- 多注册中心配置 -->
    <dubbo:registry id="chinaRegistry" address="10.20.141.150:9090" />
    <dubbo:registry id="intlRegistry" address="10.20.154.177:9010" default="false" />
    <!-- 引用中文站服务 -->
    <dubbo:reference id="chinaHelloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" registry="chinaRegistry" />
    <!-- 引用国际站站服务 -->
    <dubbo:reference id="intlHelloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" registry="intlRegistry" />
</beans>
```

如果只是测试环境临时需要连接两个不同注册中心，使用竖号分隔多个不同注册中心地址：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <dubbo:application name="world"  />
    <!-- 多注册中心配置，竖号分隔表示同时连接多个不同注册中心，同一注册中心的多个集群地址用逗号分隔 -->
    <dubbo:registry address="10.20.141.150:9090|10.20.154.177:9010" />
    <!-- 引用服务 -->
    <dubbo:reference id="helloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" />
</beans>
```

------

1. 可以自行扩展注册中心，参见：[注册中心扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/registry) [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/multi-registry/#fnref:1)

## 服务分组

使用服务分组区分服务接口的不同实现

当一个接口有多种实现时，可以用 group 区分。

### 服务

```xml
<dubbo:service group="feedback" interface="com.xxx.IndexService" />
<dubbo:service group="member" interface="com.xxx.IndexService" />
```

### 引用

```xml
<dubbo:reference id="feedbackIndexService" group="feedback" interface="com.xxx.IndexService" />
<dubbo:reference id="memberIndexService" group="member" interface="com.xxx.IndexService" />
```

任意组：

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" group="*" />
```

#### 提示

`2.2.0` 以上版本支持，总是只调一个可用组的实现

## 静态服务

将 Dubbo 服务标识为非动态管理模式

有时候希望人工管理服务提供者的上线和下线，此时需将注册中心标识为非动态管理模式。

```xml
<dubbo:registry address="10.20.141.150:9090" dynamic="false" />
```

或者

```xml
<dubbo:registry address="10.20.141.150:9090?dynamic=false" />
```

服务提供者初次注册时为禁用状态，需人工启用。断线时，将不会被自动删除，需人工禁用。

如果是一个第三方服务提供者，比如 memcached，可以直接向注册中心写入提供者地址信息，消费者正常使用 [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/static-service/#fn:1)：

```java
RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
registry.register(URL.valueOf("memcached://10.20.153.11/com.foo.BarService?category=providers&dynamic=false&application=foo"));
```

------

1. 通常由脚本监控中心页面等调用 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/static-service/#fnref:1)

## 多版本

在 Dubbo 中为同一个服务配置多个版本

当一个接口实现，出现不兼容升级时，可以用版本号过渡，版本号不同的服务相互间不引用。

可以按照以下的步骤进行版本迁移：

1. 在低压力时间段，先升级一半提供者为新版本
2. 再将所有消费者升级为新版本
3. 然后将剩下的一半提供者升级为新版本

老版本服务提供者配置：

```xml
<dubbo:service interface="com.foo.BarService" version="1.0.0" />
```

新版本服务提供者配置：

```xml
<dubbo:service interface="com.foo.BarService" version="2.0.0" />
```

老版本服务消费者配置：

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" version="1.0.0" />
```

新版本服务消费者配置：

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" version="2.0.0" />
```

如果不需要区分版本，可以按照以下的方式配置 [^1]：

#### 提示

`2.2.0` 以上版本支持

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" version="*" />
```

## 分组聚合

通过分组聚合按组合并返回结果

按组合并返回结果，比如菜单服务，接口一样，但有多种实现，用group区分，现在消费方需从每种group中调用一次返回结果，合并结果返回，这样就可以实现聚合菜单项。

相关代码可以参考 [dubbo 项目中的示例](https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-merge)

### 配置

搜索所有分组

```xml
<dubbo:reference interface="com.xxx.MenuService" group="*" merger="true" />
```

合并指定分组

```xml
<dubbo:reference interface="com.xxx.MenuService" group="aaa,bbb" merger="true" />
```

指定方法合并结果，其它未指定的方法，将只调用一个 Group

```xml
<dubbo:reference interface="com.xxx.MenuService" group="*">
    <dubbo:method name="getMenuItems" merger="true" />
</dubbo:reference>
```

某个方法不合并结果，其它都合并结果

```xml
<dubbo:reference interface="com.xxx.MenuService" group="*" merger="true">
    <dubbo:method name="getMenuItems" merger="false" />
</dubbo:reference>
```

指定合并策略，缺省根据返回值类型自动匹配，如果同一类型有两个合并器时，需指定合并器的名称

#### 提示

参见：[合并结果扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/merger)

```xml
<dubbo:reference interface="com.xxx.MenuService" group="*">
    <dubbo:method name="getMenuItems" merger="mymerge" />
</dubbo:reference>
```

指定合并方法，将调用返回结果的指定方法进行合并，合并方法的参数类型必须是返回结果类型本身

```xml
<dubbo:reference interface="com.xxx.MenuService" group="*">
    <dubbo:method name="getMenuItems" merger=".addAll" />
</dubbo:reference>
```

#### 提示

从 `2.1.0` 版本开始支持

## 参数验证

在 Dubbo 中进行参数验证

参数验证功能是基于 [JSR303](https://jcp.org/en/jsr/detail?id=303) 实现的，用户只需标识 JSR303 标准的验证 annotation，并通过声明 filter 来实现验证。

### Maven 依赖

```xml
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>1.0.0.GA</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>4.2.0.Final</version>
</dependency>
```

### 示例

### 参数标注示例

```java
import java.io.Serializable;
import java.util.Date;
 
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
 
public class ValidationParameter implements Serializable {
    private static final long serialVersionUID = 7158911668568000392L;
 
    @NotNull // 不允许为空
    @Size(min = 1, max = 20) // 长度或大小范围
    private String name;
 
    @NotNull(groups = ValidationService.Save.class) // 保存时不允许为空，更新时允许为空 ，表示不更新该字段
    @Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")
    private String email;
 
    @Min(18) // 最小值
    @Max(100) // 最大值
    private int age;
 
    @Past // 必须为一个过去的时间
    private Date loginDate;
 
    @Future // 必须为一个未来的时间
    private Date expiryDate;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public Date getLoginDate() {
        return loginDate;
    }
 
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
 
    public Date getExpiryDate() {
        return expiryDate;
    }
 
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
```

### 分组验证示例

```java
public interface ValidationService { // 缺省可按服务接口区分验证场景，如：@NotNull(groups = ValidationService.class)   
    @interface Save{} // 与方法同名接口，首字母大写，用于区分验证场景，如：@NotNull(groups = ValidationService.Save.class)，可选
    void save(ValidationParameter parameter);
    void update(ValidationParameter parameter);
}
```

### 关联验证示例

```java
import javax.validation.GroupSequence;
 
public interface ValidationService {   
    @GroupSequence(Update.class) // 同时验证Update组规则
    @interface Save{}
    void save(ValidationParameter parameter);
 
    @interface Update{} 
    void update(ValidationParameter parameter);
}
```

### 参数验证示例

```java
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
 
public interface ValidationService {
    void save(@NotNull ValidationParameter parameter); // 验证参数不为空
    void delete(@Min(1) int id); // 直接对基本类型参数验证
}
```

### 配置

### 在客户端验证参数

```xml
<dubbo:reference id="validationService" interface="org.apache.dubbo.examples.validation.api.ValidationService" validation="true" />
```

### 在服务器端验证参数

```xml
<dubbo:service interface="org.apache.dubbo.examples.validation.api.ValidationService" ref="validationService" validation="true" />
```

### 验证异常信息

```java
import javax.validation.ConstraintViolationException;
import javax.validation.ConstraintViolationException;
 
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import org.apache.dubbo.examples.validation.api.ValidationParameter;
import org.apache.dubbo.examples.validation.api.ValidationService;
import org.apache.dubbo.rpc.RpcException;
 
public class ValidationConsumer {   
    public static void main(String[] args) throws Exception {
        String config = ValidationConsumer.class.getPackage().getName().replace('.', '/') + "/validation-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
        ValidationService validationService = (ValidationService)context.getBean("validationService");
        // Error
        try {
            parameter = new ValidationParameter();
            validationService.save(parameter);
            System.out.println("Validation ERROR");
        } catch (RpcException e) { // 抛出的是RpcException
            ConstraintViolationException ve = (ConstraintViolationException) e.getCause(); // 里面嵌了一个ConstraintViolationException
            Set<ConstraintViolation<?>> violations = ve.getConstraintViolations(); // 可以拿到一个验证错误详细信息的集合
            System.out.println(violations);
        }
    } 
}
```

#### 提示

自 `2.1.0` 版本开始支持, 如何使用可以参考 [dubbo 项目中的示例代码](https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-validation)

验证方式可扩展，扩展方式参见开发者手册中的[验证扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/validation)



## 结果缓存

通过缓存结果加速访问速度

结果缓存，用于加速热门数据的访问速度，Dubbo 提供声明式缓存，以减少用户加缓存的工作量。

### 缓存类型

- `lru` 基于最近最少使用原则删除多余缓存，保持最热的数据被缓存。
- `threadlocal` 当前线程缓存，比如一个页面渲染，用到很多 portal，每个 portal 都要去查用户信息，通过线程缓存，可以减少这种多余访问。
- `jcache` 与 [JSR107](http://jcp.org/en/jsr/detail?id=107') 集成，可以桥接各种缓存实现。

缓存类型可扩展，参见：[缓存扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/cache)

### 配置

```xml
<dubbo:reference interface="com.foo.BarService" cache="lru" />
```

或：

```xml
<dubbo:reference interface="com.foo.BarService">
    <dubbo:method name="findBar" cache="lru" />
</dubbo:reference>
```

#### 提示

`2.1.0` 以上版本支持。

[示例代码](https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-cache)

## 使用泛化调用

实现一个通用的服务测试框架，可通过 `GenericService` 调用所有服务实现

泛化接口调用方式主要用于客户端没有 API 接口及模型类元的情况，参数及返回值中的所有 POJO 均用 `Map` 表示，通常用于框架集成，比如：实现一个通用的服务测试框架，可通过 `GenericService` 调用所有服务实现。

### 通过 Spring 使用泛化调用

在 Spring 配置申明 `generic="true"`：

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" generic="true" />
```

在 Java 代码获取 barService 并开始泛化调用：

```java
GenericService barService = (GenericService) applicationContext.getBean("barService");
Object result = barService.$invoke("sayHello", new String[] { "java.lang.String" }, new Object[] { "World" });
```

### 通过 API 方式使用泛化调用

```java
import org.apache.dubbo.rpc.service.GenericService; 
... 
 
// 引用远程服务 
// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>(); 
// 弱类型接口名
reference.setInterface("com.xxx.XxxService");  
reference.setVersion("1.0.0");
// 声明为泛化接口 
reference.setGeneric(true);  

// 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用  
GenericService genericService = reference.get(); 
 
// 基本类型以及Date,List,Map等不需要转换，直接调用 
Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"world"}); 
 
// 用Map表示POJO参数，如果返回值为POJO也将自动转成Map 
Map<String, Object> person = new HashMap<String, Object>(); 
person.put("name", "xxx"); 
person.put("password", "yyy"); 
// 如果返回POJO将自动转成Map 
Object result = genericService.$invoke("findPerson", new String[]
{"com.xxx.Person"}, new Object[]{person}); 
 
...
```

### 有关泛化类型的进一步解释

假设存在 POJO 如：

```java
package com.xxx;

public class PersonImpl implements Person {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

则 POJO 数据：

```java
Person person = new PersonImpl(); 
person.setName("xxx"); 
person.setPassword("yyy");
```

可用下面 Map 表示：

```java
Map<String, Object> map = new HashMap<String, Object>(); 
// 注意：如果参数类型是接口，或者List等丢失泛型，可通过class属性指定类型。
map.put("class", "com.xxx.PersonImpl"); 
map.put("name", "xxx"); 
map.put("password", "yyy");
```

## Protobuf

使用 IDL 定义服务

当前 Dubbo 的服务定义和具体的编程语言绑定，没有提供一种语言中立的服务描述格式，比如 Java 就是定义 Interface 接口，到了其他语言又得重新以另外的格式定义一遍。 2.7.5 版本通过支持 Protobuf IDL 实现了语言中立的服务定义。

日后，不论我们使用什么语言版本来开发 Dubbo 服务，都可以直接使用 IDL 定义如下服务，具体请[参见示例](https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-protobuf)

```go
syntax = "proto3";

option java_multiple_files = true;
option java_package = "org.apache.dubbo.demo";
option java_outer_classname = "DemoServiceProto";
option objc_class_prefix = "DEMOSRV";

package demoservice;

// The demo service definition.
service DemoService {
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```

## GoogleProtobuf 对象泛化调用

对 Goolgle Protobuf 对象进行泛化调用

泛化接口调用方式主要用于客户端没有 API 接口及模型类元的情况，参考 [泛化调用](http://dubbo.apache.org/zh/docs/v2.7/user/examples/generic-reference)。 一般泛化调用只能用于生成的服务参数为POJO的情况，而 GoogleProtobuf 的对象是基于 Builder 生成的非正常POJO，可以通过 protobuf-json 泛化调用。

GoogleProtobuf 序列化相关的Demo可以参考 [protobuf-demo](https://github.com/vio-lin/dubbo-samples/tree/protobuf-demo)

### 通过Spring对Goolgle Protobuf对象泛化调用

在 Spring 中配置声明 generic = “protobuf-json”

```xml
<dubbo:reference id="barService" interface="com.foo.BarService" generic="protobuf-json" />
```

在 Java 代码获取 barService 并开始泛化调用：

```java
GenericService barService = (GenericService) applicationContext.getBean("barService");
Object result = barService.$invoke("sayHello",new String[]{"org.apache.dubbo.protobuf.GooglePbBasic$CDubboGooglePBRequestType"}, new Object[]{"{\"double\":0.0,\"float\":0.0,\"bytesType\":\"Base64String\",\"int32\":0}"});
```

### 通过 API 方式对 Goolgle Protobuf 对象泛化调用

```java
ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
// 弱类型接口名
reference.setInterface(GenericService.class.getName());
reference.setInterface("com.xxx.XxxService");
// 声明为Protobuf-json
reference.setGeneric(Constants.GENERIC_SERIALIZATION_PROTOBUF);

GenericService genericService = reference.get();
Map<String, Object> person = new HashMap<String, Object>();
person.put("fixed64", "0");
person.put("int64", "0");
// 参考google官方的protobuf 3 的语法，服务的每个方法中只传输一个POJO对象
// protobuf的泛化调用只允许传递一个类型为String的json对象来代表请求参数
String requestString = new Gson().toJson(person);
// 返回对象是GoolgeProtobuf响应对象的json字符串。
Object result = genericService.$invoke("sayHello", new String[] {
    "com.xxx.XxxService.GooglePbBasic$CDubboGooglePBRequestType"},
    new Object[] {requestString});
```

### GoogleProtobuf 对象的处理

GoogleProtobuf 对象是由 Protocol 契约生成,相关知识请参考 [ProtocolBuffers 文档](https://developers.google.com/protocol-buffers/?hl=zh-CN)。假如有如下Protobuf 契约

```proto
syntax = "proto3";
package com.xxx.XxxService.GooglePbBasic.basic;
message CDubboGooglePBRequestType {
    double double = 1;
    float float = 2;
    int32 int32 = 3;
    bool bool = 13;
    string string = 14;
    bytes bytesType = 15;
}

message CDubboGooglePBResponseType {
    string msg = 1;
}

service CDubboGooglePBService {
    rpc sayHello (CDubboGooglePBRequestType) returns (CDubboGooglePBResponseType);
}
```

则对应请求按照如下方法构造

```java
Map<String, Object> person = new HashMap<>();
person.put("double", "1.000");
person.put("float", "1.00");
person.put("int32","1" );
person.put("bool","false" );
//String 的对象需要经过base64编码
person.put("string","someBaseString");
person.put("bytesType","150");
```

### GoogleProtobuf 服务元数据解析

Google Protobuf 对象缺少标准的 JSON 格式，生成的服务元数据信息存在错误。请添加如下依赖元数据解析的依赖。

```xml
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-metadata-definition-protobuf</artifactId>
    <version>${dubbo.version}</version>
</dependency>
```

从服务元数据中也可以比较容易构建泛化调用对象。

## 实现泛化调用

实现一个通用的远程服务 Mock 框架，可通过实现 GenericService 接口处理所有服务请求

泛接口实现方式主要用于服务器端没有 API 接口及模型类元的情况，参数及返回值中的所有 POJO 均用 Map 表示，通常用于框架集成，比如：实现一个通用的远程服务 Mock 框架，可通过实现 GenericService 接口处理所有服务请求。

在 Java 代码中实现 `GenericService` 接口：

```java
package com.foo;
public class MyGenericService implements GenericService {
 
    public Object $invoke(String methodName, String[] parameterTypes, Object[] args) throws GenericException {
        if ("sayHello".equals(methodName)) {
            return "Welcome " + args[0];
        }
    }
}
```

### 通过 Spring 暴露泛化实现

在 Spring 配置申明服务的实现：

```xml
<bean id="genericService" class="com.foo.MyGenericService" />
<dubbo:service interface="com.foo.BarService" ref="genericService" />
```

### 通过 API 方式暴露泛化实现

```java
... 
// 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口实现 
GenericService xxxService = new XxxGenericService(); 

// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存 
ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
// 弱类型接口名 
service.setInterface("com.xxx.XxxService");  
service.setVersion("1.0.0"); 
// 指向一个通用服务实现 
service.setRef(xxxService); 
 
// 暴露及注册服务 
service.export();
```

## 回声测试

通过回声测试检测 Dubbo 服务是否可用

回声测试用于检测服务是否可用，回声测试按照正常请求流程执行，能够测试整个调用是否通畅，可用于监控。

所有服务自动实现 `EchoService` 接口，只需将任意服务引用强制转型为 `EchoService`，即可使用。

Spring 配置：

```xml
<dubbo:reference id="memberService" interface="com.xxx.MemberService" />
```

代码：

```java
// 远程服务引用
MemberService memberService = ctx.getBean("memberService"); 
 
EchoService echoService = (EchoService) memberService; // 强制转型为EchoService

// 回声测试可用性
String status = echoService.$echo("OK"); 
 
assert(status.equals("OK"));
```

## 上下文信息

通过上下文存放当前调用过程中所需的环境信息

上下文中存放的是当前调用过程中所需的环境信息。所有配置信息都将转换为 URL 的参数，参见 [schema 配置参考手册](http://dubbo.apache.org/zh/docs/v2.7/user/references/xml/introduction) 中的**对应URL参数**一列。

RpcContext 是一个 ThreadLocal 的临时状态记录器，当接收到 RPC 请求，或发起 RPC 请求时，RpcContext 的状态都会变化。比如：A 调 B，B 再调 C，则 B 机器上，在 B 调 C 之前，RpcContext 记录的是 A 调 B 的信息，在 B 调 C 之后，RpcContext 记录的是 B 调 C 的信息。

### 服务消费方

```java
// 远程调用
xxxService.xxx();
// 本端是否为消费端，这里会返回true
boolean isConsumerSide = RpcContext.getContext().isConsumerSide();
// 获取最后一次调用的提供方IP地址
String serverIP = RpcContext.getContext().getRemoteHost();
// 获取当前服务配置信息，所有配置信息都将转换为URL的参数
String application = RpcContext.getContext().getUrl().getParameter("application");
// 注意：每发起RPC调用，上下文状态会变化
yyyService.yyy();
```

### 服务提供方

```java
public class XxxServiceImpl implements XxxService {
 
    public void xxx() {
        // 本端是否为提供端，这里会返回true
        boolean isProviderSide = RpcContext.getContext().isProviderSide();
        // 获取调用方IP地址
        String clientIP = RpcContext.getContext().getRemoteHost();
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getContext().getUrl().getParameter("application");
        // 注意：每发起RPC调用，上下文状态会变化
        yyyService.yyy();
        // 此时本端变成消费端，这里会返回false
        boolean isProviderSide = RpcContext.getContext().isProviderSide();
    } 
}
```

## 隐式参数

通过 Dubbo 中的 Attachment 在服务消费方和提供方之间隐式传递参数

可以通过 `RpcContext` 上的 `setAttachment` 和 `getAttachment` 在服务消费方和提供方之间进行参数的隐式传递。

#### 注意

path, group, version, dubbo, token, timeout 几个 key 是保留字段，请使用其它值。

![/user-guide/images/context.png](E:\oldF\learningDocument\dubbo\context.png)

#### 在服务消费方端设置隐式参数

`setAttachment` 设置的 KV 对，在完成下面一次远程调用会被清空，即多次远程调用要多次设置。

```xml
RpcContext.getContext().setAttachment("index", "1"); // 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用
xxxService.xxx(); // 远程调用
// ...
```

#### 在服务提供方端获取隐式参数

```java
public class XxxServiceImpl implements XxxService {
 
    public void xxx() {
        // 获取客户端隐式传入的参数，用于框架集成，不建议常规业务使用
        String index = RpcContext.getContext().getAttachment("index"); 
    }
}
```

## 异步执行

Dubbo 服务提供方的异步执行

Provider端异步执行将阻塞的业务从Dubbo内部线程池切换到业务自定义线程，避免Dubbo线程池的过度占用，有助于避免不同服务间的互相影响。异步执行无益于节省资源或提升RPC响应性能，因为如果业务执行需要阻塞，则始终还是要有线程来负责执行。

#### 注意

Provider 端异步执行和 Consumer 端异步调用是相互独立的，你可以任意正交组合两端配置

- Consumer同步 - Provider同步
- Consumer异步 - Provider同步
- Consumer同步 - Provider异步
- Consumer异步 - Provider异步

### 定义 CompletableFuture 签名的接口

服务接口定义：

```java
public interface AsyncService {
    CompletableFuture<String> sayHello(String name);
}
```

服务实现：

```java
public class AsyncServiceImpl implements AsyncService {
    @Override
    public CompletableFuture<String> sayHello(String name) {
        RpcContext savedContext = RpcContext.getContext();
        // 建议为supplyAsync提供自定义线程池，避免使用JDK公用线程池
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(savedContext.getAttachment("consumer-key1"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "async response from provider.";
        });
    }
}
```

通过 `return CompletableFuture.supplyAsync() `，业务执行已从 Dubbo 线程切换到业务线程，避免了对 Dubbo 线程池的阻塞。

### 使用AsyncContext

Dubbo 提供了一个类似 Serverlet 3.0 的异步接口`AsyncContext`，在没有 CompletableFuture 签名接口的情况下，也可以实现 Provider 端的异步执行。

服务接口定义：

```java
public interface AsyncService {
    String sayHello(String name);
}
```

服务暴露，和普通服务完全一致：

```xml
<bean id="asyncService" class="org.apache.dubbo.samples.governance.impl.AsyncServiceImpl"/>
<dubbo:service interface="org.apache.dubbo.samples.governance.api.AsyncService" ref="asyncService"/>
```

服务实现：

```java
public class AsyncServiceImpl implements AsyncService {
    public String sayHello(String name) {
        final AsyncContext asyncContext = RpcContext.startAsync();
        new Thread(() -> {
            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 写回响应
            asyncContext.write("Hello " + name + ", response from provider.");
        }).start();
        return null;
    }
}
```

## 异步调用

在 Dubbo 中发起异步调用

从 2.7.0 开始，Dubbo 的所有异步编程接口开始以 [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) 为基础

基于 NIO 的非阻塞实现并行调用，客户端不需要启动多线程即可完成并行调用多个远程服务，相对多线程开销较小。

![/user-guide/images/future.jpg](http://dubbo.apache.org/imgs/user/future.jpg)

### 使用 CompletableFuture 签名的接口

需要服务提供者事先定义 CompletableFuture 签名的服务，具体参见[服务端异步执行](http://dubbo.apache.org/zh/docs/v2.7/user/examples/async-call/async-execute-on-provider.md)接口定义：

```java
public interface AsyncService {
    CompletableFuture<String> sayHello(String name);
}
```

注意接口的返回类型是 `CompletableFuture<String>`。

XML引用服务：

```xml
<dubbo:reference id="asyncService" timeout="10000" interface="com.alibaba.dubbo.samples.async.api.AsyncService"/>
```

调用远程服务：

```java
// 调用直接返回CompletableFuture
CompletableFuture<String> future = asyncService.sayHello("async call request");
// 增加回调
future.whenComplete((v, t) -> {
    if (t != null) {
        t.printStackTrace();
    } else {
        System.out.println("Response: " + v);
    }
});
// 早于结果输出
System.out.println("Executed before response return.");
```

### 使用 RpcContext

在 consumer.xml 中配置：

```xml
<dubbo:reference id="asyncService" interface="org.apache.dubbo.samples.governance.api.AsyncService">
      <dubbo:method name="sayHello" async="true" />
</dubbo:reference>
```

调用代码:

```java
// 此调用会立即返回null
asyncService.sayHello("world");
// 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
// 为Future添加回调
helloFuture.whenComplete((retValue, exception) -> {
    if (exception == null) {
        System.out.println(retValue);
    } else {
        exception.printStackTrace();
    }
});
```

或者，你也可以这样做异步调用:

```java
CompletableFuture<String> future = RpcContext.getContext().asyncCall(
    () -> {
        asyncService.sayHello("oneway call request1");
    }
);

future.get();
```

### 重载服务接口

如果你只有这样的同步服务定义，而又不喜欢 RpcContext 的异步使用方式。

```java
public interface GreetingsService {
    String sayHi(String name);
}
```

那还有一种方式，就是利用 Java 8 提供的 default 接口实现，重载一个带有 CompletableFuture 签名的方法。

有两种方式来实现：

1. 提供方或消费方自己修改接口签名

```java
public interface GreetingsService {
    String sayHi(String name);
    
    // AsyncSignal is totally optional, you can use any parameter type as long as java allows your to do that.
    default CompletableFuture<String> sayHi(String name, AsyncSignal signal) {
        return CompletableFuture.completedFuture(sayHi(name));
    }
}
```

1. Dubbo 官方提供 compiler hacker，编译期自动重写同步方法，请[在此](https://github.com/dubbo/dubbo-async-processor#compiler-hacker-processer)讨论和跟进具体进展。

你也可以设置是否等待消息发出： [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/async-call/#fn:1)

- `sent="true"` 等待消息发出，消息发送失败将抛出异常。
- `sent="false"` 不等待消息发出，将消息放入 IO 队列，即刻返回。

```xml
<dubbo:method name="findFoo" async="true" sent="true" />
```

如果你只是想异步，完全忽略返回值，可以配置 `return="false"`，以减少 Future 对象的创建和管理成本：

```xml
<dubbo:method name="findFoo" async="true" return="false" />
```

------

1. 异步总是不等待返回 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/async-call/#fnref:1)

## 本地调用

在 Dubbo 中进行本地调用

本地调用使用了 injvm 协议，是一个伪协议，它不开启端口，不发起远程调用，只在 JVM 内直接关联，但执行 Dubbo 的 Filter 链。

### 配置

定义 injvm 协议

```xml
<dubbo:protocol name="injvm" />
```

设置默认协议

```xml
<dubbo:provider protocol="injvm" />
```

设置服务协议

```xml
<dubbo:service protocol="injvm" />
```

优先使用 injvm

```xml
<dubbo:consumer injvm="true" .../>
<dubbo:provider injvm="true" .../>
```

或

```xml
<dubbo:reference injvm="true" .../>
<dubbo:service injvm="true" .../>
```

#### 注意

Dubbo 从 `2.2.0` 每个服务默认都会在本地暴露，无需进行任何配置即可进行本地引用，如果不希望服务进行远程暴露，只需要在 provider 将 protocol 设置成 injvm 即可

### 自动暴露、引用本地服务

从 `2.2.0` 开始，每个服务默认都会在本地暴露。在引用服务的时候，默认优先引用本地服务。如果希望引用远程服务可以使用一下配置强制引用远程服务。

```xml
<dubbo:reference ... scope="remote" />
```

## 参数回调

通过参数回调从服务器端调用客户端逻辑

参数回调方式与调用本地 callback 或 listener 相同，只需要在 Spring 的配置文件中声明哪个参数是 callback 类型即可。Dubbo 将基于长连接生成反向代理，这样就可以从服务器端调用客户端逻辑。可以参考 [dubbo 项目中的示例代码](https://github.com/dubbo/dubbo-samples/tree/master/dubbo-samples-callback)。

#### 服务接口示例

###### CallbackService.java

```java
package com.callback;
 
public interface CallbackService {
    void addListener(String key, CallbackListener listener);
}
```

###### CallbackListener.java

```java
package com.callback;
 
public interface CallbackListener {
    void changed(String msg);
}
```

#### 服务提供者接口实现示例

```java
package com.callback.impl;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
import com.callback.CallbackListener;
import com.callback.CallbackService;
 
public class CallbackServiceImpl implements CallbackService {
     
    private final Map<String, CallbackListener> listeners = new ConcurrentHashMap<String, CallbackListener>();
  
    public CallbackServiceImpl() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        for(Map.Entry<String, CallbackListener> entry : listeners.entrySet()){
                           try {
                               entry.getValue().changed(getChanged(entry.getKey()));
                           } catch (Throwable t) {
                               listeners.remove(entry.getKey());
                           }
                        }
                        Thread.sleep(5000); // 定时触发变更通知
                    } catch (Throwable t) { // 防御容错
                        t.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
  
    public void addListener(String key, CallbackListener listener) {
        listeners.put(key, listener);
        listener.changed(getChanged(key)); // 发送变更通知
    }
     
    private String getChanged(String key) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
```

#### 服务提供者配置示例

```xml
<bean id="callbackService" class="com.callback.impl.CallbackServiceImpl" />
<dubbo:service interface="com.callback.CallbackService" ref="callbackService" connections="1" callbacks="1000">
    <dubbo:method name="addListener">
        <dubbo:argument index="1" callback="true" />
        <!--也可以通过指定类型的方式-->
        <!--<dubbo:argument type="com.demo.CallbackListener" callback="true" />-->
    </dubbo:method>
</dubbo:service>
```

#### 服务消费者配置示例

```xml
<dubbo:reference id="callbackService" interface="com.callback.CallbackService" />
```

#### 服务消费者调用示例

```java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");
context.start();
 
CallbackService callbackService = (CallbackService) context.getBean("callbackService");
 
callbackService.addListener("foo.bar", new CallbackListener(){
    public void changed(String msg) {
        System.out.println("callback1:" + msg);
    }
});
```

#### 提示

`2.0.6` 及其以上版本支持

## 事件通知

在调用之前、调用之后、出现异常时的时间通知

在调用之前、调用之后、出现异常时，会触发 `oninvoke`、`onreturn`、`onthrow` 三个事件，可以配置当事件发生时，通知哪个类的哪个方法。

#### 提示

支持版本：`2.0.7` 之后

#### 服务提供者与消费者共享服务接口

```java
interface IDemoService {
    public Person get(int id);
}
```

#### 服务提供者实现

```java
class NormalDemoService implements IDemoService {
    public Person get(int id) {
        return new Person(id, "charles`son", 4);
    }
}
```

#### 服务提供者配置

```xml
<dubbo:application name="rpc-callback-demo" />
<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
<bean id="demoService" class="org.apache.dubbo.callback.implicit.NormalDemoService" />
<dubbo:service interface="org.apache.dubbo.callback.implicit.IDemoService" ref="demoService" version="1.0.0" group="cn"/>
```

#### 服务消费者 Callback 接口

```java
interface Notify {
    public void onreturn(Person msg, Integer id);
    public void onthrow(Throwable ex, Integer id);
}
```

#### 服务消费者 Callback 实现

```java
class NotifyImpl implements Notify {
    public Map<Integer, Person>    ret    = new HashMap<Integer, Person>();
    public Map<Integer, Throwable> errors = new HashMap<Integer, Throwable>();
    
    public void onreturn(Person msg, Integer id) {
        System.out.println("onreturn:" + msg);
        ret.put(id, msg);
    }
    
    public void onthrow(Throwable ex, Integer id) {
        errors.put(id, ex);
    }
}
```

#### 服务消费者 Callback 配置

```xml
<bean id ="demoCallback" class = "org.apache.dubbo.callback.implicit.NofifyImpl" />
<dubbo:reference id="demoService" interface="org.apache.dubbo.callback.implicit.IDemoService" version="1.0.0" group="cn" >
      <dubbo:method name="get" async="true" onreturn = "demoCallback.onreturn" onthrow="demoCallback.onthrow" />
</dubbo:reference>
```

`callback` 与 `async` 功能正交分解，`async=true` 表示结果是否马上返回，`onreturn` 表示是否需要回调。

两者叠加存在以下几种组合情况：

- 异步回调模式：`async=true onreturn="xxx"`
- 同步回调模式：`async=false onreturn="xxx"`
- 异步无回调 ：`async=true`
- 同步无回调 ：`async=false`

#### 提示

`async=false` 默认

#### 测试代码

```java
IDemoService demoService = (IDemoService) context.getBean("demoService");
NofifyImpl notify = (NofifyImpl) context.getBean("demoCallback");
int requestId = 2;
Person ret = demoService.get(requestId);
Assert.assertEquals(null, ret);
//for Test：只是用来说明callback正常被调用，业务具体实现自行决定.
for (int i = 0; i < 10; i++) {
    if (!notify.ret.containsKey(requestId)) {
        Thread.sleep(200);
    } else {
        break;
    }
}
Assert.assertEquals(requestId, notify.ret.get(requestId).getId());
```

## 本地存根

在 Dubbo 中利用本地存根在客户端执行部分逻辑

远程服务后，客户端通常只剩下接口，而实现全在服务器端，但提供方有些时候想在客户端也执行部分逻辑，比如：做 ThreadLocal 缓存，提前验证参数，调用失败后伪造容错数据等等，此时就需要在 API 中带上 Stub，客户端生成 Proxy 实例，会把 Proxy 通过构造函数传给 Stub [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-stub/#fn:1)，然后把 Stub 暴露给用户，Stub 可以决定要不要去调 Proxy。

![/user-guide/images/stub.jpg](E:\oldF\learningDocument\dubbo\stub.jpg)

在 spring 配置文件中按以下方式配置：

```xml
<dubbo:service interface="com.foo.BarService" stub="true" />
```

或

```xml
<dubbo:service interface="com.foo.BarService" stub="com.foo.BarServiceStub" />
```

提供 Stub 的实现 [2](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-stub/#fn:2)：

```java
package com.foo;
public class BarServiceStub implements BarService {
    private final BarService barService;
    
    // 构造函数传入真正的远程代理对象
    public BarServiceStub(BarService barService){
        this.barService = barService;
    }
 
    public String sayHello(String name) {
        // 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
        try {
            return barService.sayHello(name);
        } catch (Exception e) {
            // 你可以容错，可以做任何AOP拦截事项
            return "容错数据";
        }
    }
}
```

------

1. Stub 必须有可传入 Proxy 的构造函数。 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-stub/#fnref:1)
2. 在 interface 旁边放一个 Stub 实现，它实现 BarService 接口，并有一个传入远程 BarService 实例的构造函数 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-stub/#fnref:2)

## 本地伪装

如何在 Dubbo 中利用本地伪装实现服务降级

本地伪装 [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-mock/#fn:1) 通常用于服务降级，比如某验权服务，当服务提供方全部挂掉后，客户端不抛出异常，而是通过 Mock 数据返回授权失败。

在 spring 配置文件中按以下方式配置：

```xml
<dubbo:reference interface="com.foo.BarService" mock="true" />
```

或

```xml
<dubbo:reference interface="com.foo.BarService" mock="com.foo.BarServiceMock" />
```

在工程中提供 Mock 实现 [2](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-mock/#fn:2)：

```java
package com.foo;
public class BarServiceMock implements BarService {
    public String sayHello(String name) {
        // 你可以伪造容错数据，此方法只在出现RpcException时被执行
        return "容错数据";
    }
}
```

如果服务的消费方经常需要 try-catch 捕获异常，如：

```java
Offer offer = null;
try {
    offer = offerService.findOffer(offerId);
} catch (RpcException e) {
   logger.error(e);
}
```

请考虑改为 Mock 实现，并在 Mock 实现中 return null。如果只是想简单的忽略异常，在 `2.0.11` 以上版本可用：

```xml
<dubbo:reference interface="com.foo.BarService" mock="return null" />
```

### 进阶用法

### return

使用 `return` 来返回一个字符串表示的对象，作为 Mock 的返回值。合法的字符串可以是：

- *empty*: 代表空，基本类型的默认值，或者集合类的空值
- *null*: `null`
- *true*: `true`
- *false*: `false`
- *JSON 格式*: 反序列化 JSON 所得到的对象

### throw

使用 `throw` 来返回一个 Exception 对象，作为 Mock 的返回值。

当调用出错时，抛出一个默认的 RPCException:

```xml
<dubbo:reference interface="com.foo.BarService" mock="throw" />
```

当调用出错时，抛出指定的 Exception：

```xml
<dubbo:reference interface="com.foo.BarService" mock="throw com.foo.MockException" />
```

### force 和 fail

在 `2.6.6` 以上的版本，可以开始在 Spring XML 配置文件中使用 `fail:` 和 `force:`。`force:` 代表强制使用 Mock 行为，在这种情况下不会走远程调用。`fail:` 与默认行为一致，只有当远程调用发生错误时才使用 Mock 行为。`force:` 和 `fail:` 都支持与 `throw` 或者 `return` 组合使用。

强制返回指定值：

```xml
<dubbo:reference interface="com.foo.BarService" mock="force:return fake" />
```

强制抛出指定异常：

```xml
<dubbo:reference interface="com.foo.BarService" mock="force:throw com.foo.MockException" />
```

### 在方法级别配置 Mock

Mock 可以在方法级别上指定，假定 `com.foo.BarService` 上有好几个方法，我们可以单独为 `sayHello()` 方法指定 Mock 行为。具体配置如下所示，在本例中，只要 `sayHello()` 被调用到时，强制返回 “fake”:

```xml
<dubbo:reference id="demoService" check="false" interface="com.foo.BarService">
    <dubbo:parameter key="sayHello.mock" value="force:return fake"/>
</dubbo:reference>
```

------

1. Mock 是 Stub 的一个子集，便于服务提供方在客户端执行容错逻辑，因经常需要在出现 RpcException (比如网络失败，超时等)时进行容错，而在出现业务异常(比如登录用户名密码错误)时不需要容错，如果用 Stub，可能就需要捕获并依赖 RpcException 类，而用 Mock 就可以不依赖 RpcException，因为它的约定就是只有出现 RpcException 时才执行。 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-mock/#fnref:1)
2. 在 interface 旁放一个 Mock 实现，它实现 BarService 接口，并有一个无参构造函数 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/local-mock/#fnref:2)

## 延迟暴露

延迟暴露 Dubbo 服务

如果你的服务需要预热时间，比如初始化缓存，等待相关资源就位等，可以使用 delay 进行延迟暴露。我们在 Dubbo 2.6.5 版本中对服务延迟暴露逻辑进行了细微的调整，将需要延迟暴露（delay > 0）服务的倒计时动作推迟到了 Spring 初始化完成后进行。你在使用 Dubbo 的过程中，并不会感知到此变化，因此请放心使用。

### Dubbo 2.6.5 之前版本

延迟到 Spring 初始化完成后，再暴露服务[1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/delay-publish/#fn:1)

```xml
<dubbo:service delay="-1" />
```

延迟 5 秒暴露服务

```xml
<dubbo:service delay="5000" />
```

### Dubbo 2.6.5 及以后版本

所有服务都将在 Spring 初始化完成后进行暴露，如果你不需要延迟暴露服务，无需配置 delay。

延迟 5 秒暴露服务

```xml
<dubbo:service delay="5000" />
```

### Spring 2.x 初始化死锁问题

### 触发条件

在 Spring 解析到 `<dubbo:service />` 时，就已经向外暴露了服务，而 Spring 还在接着初始化其它 Bean。如果这时有请求进来，并且服务的实现类里有调用 `applicationContext.getBean()` 的用法。

1. 请求线程的 applicationContext.getBean() 调用，先同步 singletonObjects 判断 Bean 是否存在，不存在就同步 beanDefinitionMap 进行初始化，并再次同步 singletonObjects 写入 Bean 实例缓存。

   ![deadlock](E:\oldF\learningDocument\dubbo\lock-get-bean.jpg)

2. 而 Spring 初始化线程，因不需要判断 Bean 的存在，直接同步 beanDefinitionMap 进行初始化，并同步 singletonObjects 写入 Bean 实例缓存。

   ![/user-guide/images/lock-init-context.jpg](http://dubbo.apache.org/imgs/user/lock-init-context.jpg)

   这样就导致 getBean 线程，先锁 singletonObjects，再锁 beanDefinitionMap，再次锁 singletonObjects。
   而 Spring 初始化线程，先锁 beanDefinitionMap，再锁 singletonObjects。反向锁导致线程死锁，不能提供服务，启动不了。

### 规避办法

1. 强烈建议不要在服务的实现类中有 applicationContext.getBean() 的调用，全部采用 IoC 注入的方式使用 Spring的Bean。
2. 如果实在要调 getBean()，可以将 Dubbo 的配置放在 Spring 的最后加载。
3. 如果不想依赖配置顺序，可以使用 `<dubbo:provider delay=”-1” />`，使 Dubbo 在 Spring 容器初始化完后，再暴露服务。
4. 如果大量使用 getBean()，相当于已经把 Spring 退化为工厂模式在用，可以将 Dubbo 的服务隔离单独的 Spring 容器。

------

1. 基于 Spring 的 ContextRefreshedEvent 事件触发暴露 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/delay-publish/#fnref:1)

## 并发控制

Dubbo 中的并发控制

### 配置样例

### 样例 1

限制 `com.foo.BarService` 的每个方法，服务器端并发执行（或占用线程池线程数）不能超过 10 个：

```xml
<dubbo:service interface="com.foo.BarService" executes="10" />
```

### 样例 2

限制 `com.foo.BarService` 的 `sayHello` 方法，服务器端并发执行（或占用线程池线程数）不能超过 10 个：

```xml
<dubbo:service interface="com.foo.BarService">
    <dubbo:method name="sayHello" executes="10" />
</dubbo:service>
```

### 样例 3

限制 `com.foo.BarService` 的每个方法，每客户端并发执行（或占用连接的请求数）不能超过 10 个：

```xml
<dubbo:service interface="com.foo.BarService" actives="10" />
```

或

```xml
<dubbo:reference interface="com.foo.BarService" actives="10" />
```

### 样例 4

限制 `com.foo.BarService` 的 `sayHello` 方法，每客户端并发执行（或占用连接的请求数）不能超过 10 个：

```xml
<dubbo:service interface="com.foo.BarService">
    <dubbo:method name="sayHello" actives="10" />
</dubbo:service>
```

或

```xml
<dubbo:reference interface="com.foo.BarService">
    <dubbo:method name="sayHello" actives="10" />
</dubbo:service>
```

如果 `<dubbo:service>` 和 `<dubbo:reference>` 都配了actives，`<dubbo:reference>` 优先，参见：[配置的覆盖策略](http://dubbo.apache.org/zh/docs/v2.7/user/configuration/xml)。

### Load Balance 均衡

配置服务的客户端的 `loadbalance` 属性为 `leastactive`，此 Loadbalance 会调用并发数最小的 Provider（Consumer端并发数）。

```xml
<dubbo:reference interface="com.foo.BarService" loadbalance="leastactive" />
```

或

```xml
<dubbo:service interface="com.foo.BarService" loadbalance="leastactive" />
```

## 连接控制

Dubbo 中服务端和客户端的连接控制

### 服务端连接控制

限制服务器端接受的连接不能超过 10 个 [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/config-connections/#fn:1)：

```xml
<dubbo:provider protocol="dubbo" accepts="10" />
```

或

```xml
<dubbo:protocol name="dubbo" accepts="10" />
```

### 客户端连接控制

限制客户端服务使用连接不能超过 10 个 [2](http://dubbo.apache.org/zh/docs/v2.7/user/examples/config-connections/#fn:2)：

```xml
<dubbo:reference interface="com.foo.BarService" connections="10" />
```

或

```xml
<dubbo:service interface="com.foo.BarService" connections="10" />
```

如果 `<dubbo:service>` 和 `<dubbo:reference>` 都配了 connections，`<dubbo:reference>` 优先，参见：[配置的覆盖策略](http://dubbo.apache.org/zh/docs/v2.7/user/configuration/xml)

------

1. 因为连接在 Server上，所以配置在 Provider 上 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/config-connections/#fnref:1)
2. 如果是长连接，比如 Dubbo 协议，connections 表示该服务对每个提供者建立的长连接数 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/config-connections/#fnref:2)

## 延迟连接

在 Dubbo 中配置延迟连接

延迟连接用于减少长连接数。当有调用发起时，再创建长连接。

```xml
<dubbo:protocol name="dubbo" lazy="true" />
```

#### 提示

该配置只对使用长连接的 dubbo 协议生效。

## 粘滞连接

为有状态服务配置粘滞连接

粘滞连接用于有状态服务，尽可能让客户端总是向同一提供者发起调用，除非该提供者挂了，再连另一台。

粘滞连接将自动开启[延迟连接](http://dubbo.apache.org/zh/docs/v2.7/user/examples/lazy-connect)，以减少长连接数。

```xml
<dubbo:reference id="xxxService" interface="com.xxx.XxxService" sticky="true" />
```

Dubbo 支持方法级别的粘滞连接，如果你想进行更细粒度的控制，还可以这样配置。

```xml
<dubbo:reference id="xxxService" interface="com.xxx.XxxService">
    <dubbo:mothod name="sayHello" sticky="true" />
</dubbo:reference>
```

## TLS

通过 TLS 保证传输安全

2.7.5 版本在传输链路的安全性上做了很多工作，对于内置的 Dubbo Netty Server 和新引入的 gRPC 协议都提供了基于 TLS 的安全链路传输机制。

TLS 的配置都有统一的入口，如下所示：

##### Provider 端

```java
SslConfig sslConfig = new SslConfig();
sslConfig.setServerKeyCertChainPath("path to cert");
sslConfig.setServerPrivateKeyPath(args[1]);
// 如果开启双向 cert 认证
if (mutualTls) {
  sslConfig.setServerTrustCertCollectionPath(args[2]);
}

ProtocolConfig protocolConfig = new ProtocolConfig("dubbo/grpc");
protocolConfig.setSslEnabled(true);
```

##### Consumer 端

```java
if (!mutualTls) {}
    sslConfig.setClientTrustCertCollectionPath(args[0]);
} else {
    sslConfig.setClientTrustCertCollectionPath(args[0]);
    sslConfig.setClientKeyCertChainPath(args[1]);
    sslConfig.setClientPrivateKeyPath(args[2]);
}
```

为尽可能保证应用启动的灵活性，TLS Cert 的指定还能通过 -D 参数或环境变量等方式来在启动阶段根据部署环境动态指定，具体请参见 Dubbo 配置读取规则与 TLS 示例

#### 提示

参考 Dubbo [配置读取规则](http://dubbo.apache.org/zh/docs/v2.7/user/configuration/configuration-load-process)，TLS [示例](https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-ssl)

如果要使用的是 gRPC 协议，在开启 TLS 时会使用到协议协商机制，因此必须使用支持 ALPN 机制的 Provider，推荐使用的是 netty-tcnative，具体可参见 gRPC Java 社区的[总结](https://github.com/grpc/grpc-java/blob/master/SECURITY.md)

在服务调用的安全性上，Dubbo 在后续的版本中会持续投入，其中服务发现/调用的鉴权机制预计在接下来的版本中就会和大家见面。

## 令牌验证

通过令牌验证在注册中心控制权限

通过令牌验证在注册中心控制权限，以决定要不要下发令牌给消费者，可以防止消费者绕过注册中心访问提供者，另外通过注册中心可灵活改变授权方式，而不需修改或升级提供者

![/user-guide/images/dubbo-token.jpg](E:\oldF\learningDocument\dubbo\dubbo-token.jpg)

可以全局设置开启令牌验证：

```xml
<!--随机token令牌，使用UUID生成-->
<dubbo:provider interface="com.foo.BarService" token="true" />
```

或

```xml
<!--固定token令牌，相当于密码-->
<dubbo:provider interface="com.foo.BarService" token="123456" />
```

也可在服务级别设置：

```xml
<!--随机token令牌，使用UUID生成-->
<dubbo:service interface="com.foo.BarService" token="true" />
```

或

```xml
<!--固定token令牌，相当于密码-->
<dubbo:service interface="com.foo.BarService" token="123456" />
```

## 路由规则

通过 Dubbo 中的路由规则做服务治理

路由规则在发起一次RPC调用前起到过滤目标服务器地址的作用，过滤后的地址列表，将作为消费端最终发起RPC调用的备选地址。

- 条件路由。支持以服务或 Consumer 应用为粒度配置路由规则。
- 标签路由。以 Provider 应用为粒度配置路由规则。

后续我们计划在 2.6.x 版本的基础上继续增强脚本路由功能。

### 条件路由

您可以随时在服务治理控制台 [Dubbo-Admin](https://github.com/apache/dubbo-admin) 写入路由规则

### 简介

- 应用粒度

  ```yaml
  # app1的消费者只能消费所有端口为20880的服务实例
  # app2的消费者只能消费所有端口为20881的服务实例
  ---
  scope: application
  force: true
  runtime: true
  enabled: true
  key: governance-conditionrouter-consumer
  conditions:
    - application=app1 => address=*:20880
    - application=app2 => address=*:20881
  ...
  ```

- 服务粒度

  ```yaml
  # DemoService的sayHello方法只能消费所有端口为20880的服务实例
  # DemoService的sayHi方法只能消费所有端口为20881的服务实例
  ---
  scope: service
  force: true
  runtime: true
  enabled: true
  key: org.apache.dubbo.samples.governance.api.DemoService
  conditions:
    - method=sayHello => address=*:20880
    - method=sayHi => address=*:20881
  ...
  ```

### 规则详解

#### 各字段含义

- ```
  scope
  ```

  表示路由规则的作用粒度，scope的取值会决定key的取值。

  必填

  。

  - service 服务粒度
  - application 应用粒度

- ```
  Key
  ```

  明确规则体作用在哪个服务或应用。

  必填

  。

  - scope=service时，key取值为[{group}:]{service}[:{version}]的组合
  - scope=application时，key取值为application名称

- `enabled=true` 当前路由规则是否生效，可不填，缺省生效。

- `force=false` 当路由结果为空时，是否强制执行，如果不强制执行，路由结果为空的路由规则将自动失效，可不填，缺省为 `false`。

- `runtime=false` 是否在每次调用时执行路由规则，否则只在提供者地址列表变更时预先执行并缓存结果，调用时直接从缓存中获取路由结果。如果用了参数路由，必须设为 `true`，需要注意设置会影响调用的性能，可不填，缺省为 `false`。

- `priority=1` 路由规则的优先级，用于排序，优先级越大越靠前执行，可不填，缺省为 `0`。

- `conditions` 定义具体的路由规则内容。**必填**。

#### Conditions规则体

```
`conditions`部分是规则的主体，由1到任意多条规则组成，下面我们就每个规则的配置语法做详细说明：
```

1. **格式**

- `=>` 之前的为消费者匹配条件，所有参数和消费者的 URL 进行对比，当消费者满足匹配条件时，对该消费者执行后面的过滤规则。
- `=>` 之后为提供者地址列表的过滤条件，所有参数和提供者的 URL 进行对比，消费者最终只拿到过滤后的地址列表。
- 如果匹配条件为空，表示对所有消费方应用，如：`=> host != 10.20.153.11`
- 如果过滤条件为空，表示禁止访问，如：`host = 10.20.153.10 =>`

1. **表达式**

参数支持：

- 服务调用信息，如：method, argument 等，暂不支持参数路由
- URL 本身的字段，如：protocol, host, port 等
- 以及 URL 上的所有参数，如：application, organization 等

条件支持：

- 等号 `=` 表示"匹配"，如：`host = 10.20.153.10`
- 不等号 `!=` 表示"不匹配"，如：`host != 10.20.153.10`

值支持：

- 以逗号 `,` 分隔多个值，如：`host != 10.20.153.10,10.20.153.11`
- 以星号 `*` 结尾，表示通配，如：`host != 10.20.*`
- 以美元符 `$` 开头，表示引用消费者参数，如：`host = $host`

1. **Condition示例**

- 排除预发布机：

```fallback
=> host != 172.22.3.91
```

- 白名单：

```fallback
host != 10.20.153.10,10.20.153.11 =>
```

#### 注意

一个服务只能有一条白名单规则，否则两条规则交叉，就都被筛选掉了

- 黑名单：

```fallback
host = 10.20.153.10,10.20.153.11 =>
```

- 服务寄宿在应用上，只暴露一部分的机器，防止整个集群挂掉：

```fallback
=> host = 172.22.3.1*,172.22.3.2*
```

- 为重要应用提供额外的机器：

```fallback
application != kylin => host != 172.22.3.95,172.22.3.96
```

- 读写分离：

```fallback
method = find*,list*,get*,is* => host = 172.22.3.94,172.22.3.95,172.22.3.96
method != find*,list*,get*,is* => host = 172.22.3.97,172.22.3.98
```

- 前后台分离：

```fallback
application = bops => host = 172.22.3.91,172.22.3.92,172.22.3.93
application != bops => host = 172.22.3.94,172.22.3.95,172.22.3.96
```

- 隔离不同机房网段：

```fallback
host != 172.22.3.* => host != 172.22.3.*
```

- 提供者与消费者部署在同集群内，本机只访问本机的服务：

```fallback
=> host = $host
```

### 标签路由规则

### 简介

标签路由通过将某一个或多个服务的提供者划分到同一个分组，约束流量只在指定分组中流转，从而实现流量隔离的目的，可以作为蓝绿发布、灰度发布等场景的能力基础。

#### Provider

标签主要是指对Provider端应用实例的分组，目前有两种方式可以完成实例分组，分别是`动态规则打标`和`静态规则打标`，其中动态规则相较于静态规则优先级更高，而当两种规则同时存在且出现冲突时，将以动态规则为准。

- 动态规则打标，可随时在**服务治理控制台**下发标签归组规则

  ```yaml
  # governance-tagrouter-provider应用增加了两个标签分组tag1和tag2
  # tag1包含一个实例 127.0.0.1:20880
  # tag2包含一个实例 127.0.0.1:20881
  ---
    force: false
    runtime: true
    enabled: true
    key: governance-tagrouter-provider
    tags:
      - name: tag1
        addresses: ["127.0.0.1:20880"]
      - name: tag2
        addresses: ["127.0.0.1:20881"]
   ...
  ```

- 静态打标

  ```xml
  <dubbo:provider tag="tag1"/>
  ```

  or

  ```xml
  <dubbo:service tag="tag1"/>
  ```

  or

  ```fallback
  java -jar xxx-provider.jar -Ddubbo.provider.tag={the tag you want, may come from OS ENV}
  ```

#### Consumer

```java
RpcContext.getContext().setAttachment(Constants.REQUEST_TAG_KEY,"tag1");
```

请求标签的作用域为每一次 invocation，使用 attachment 来传递请求标签，注意保存在 attachment 中的值将会在一次完整的远程调用中持续传递，得益于这样的特性，我们只需要在起始调用时，通过一行代码的设置，达到标签的持续传递。

> 目前仅仅支持 hardcoding 的方式设置 requestTag。注意到 RpcContext 是线程绑定的，优雅的使用 TagRouter 特性，建议通过 servlet 过滤器(在 web 环境下)，或者定制的 SPI 过滤器设置 requestTag。

### 规则详解

#### 格式

- `Key`明确规则体作用到哪个应用。**必填**。

- `enabled=true` 当前路由规则是否生效，可不填，缺省生效。

- `force=false` 当路由结果为空时，是否强制执行，如果不强制执行，路由结果为空的路由规则将自动失效，可不填，缺省为 `false`。

- `runtime=false` 是否在每次调用时执行路由规则，否则只在提供者地址列表变更时预先执行并缓存结果，调用时直接从缓存中获取路由结果。如果用了参数路由，必须设为 `true`，需要注意设置会影响调用的性能，可不填，缺省为 `false`。

- `priority=1` 路由规则的优先级，用于排序，优先级越大越靠前执行，可不填，缺省为 `0`。

- ```
  tags
  ```

   

  定义具体的标签分组内容，可定义任意n（n>=1）个标签并为每个标签指定实例列表。

  必填

  。

  - name， 标签名称

- addresses， 当前标签包含的实例列表

#### 降级约定

1. `request.tag=tag1` 时优先选择 标记了`tag=tag1` 的 provider。若集群中不存在与请求标记对应的服务，默认将降级请求 tag为空的provider；如果要改变这种默认行为，即找不到匹配tag1的provider返回异常，需设置`request.tag.force=true`。
2. `request.tag`未设置时，只会匹配tag为空的provider。即使集群中存在可用的服务，若 tag 不匹配也就无法调用，这与约定1不同，携带标签的请求可以降级访问到无标签的服务，但不携带标签/携带其他种类标签的请求永远无法访问到其他标签的服务。

#### 提示

`2.6.x` 版本以及更早的版本请使用[老版本路由规则](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated)

自定义路由参考[路由扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/router)

## 旧路由规则

在 Dubbo `2.6.x` 版本以及更早的版本中配置路由规则

路由规则 [1](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fn:1) 决定一次 dubbo 服务调用的目标服务器，分为条件路由规则和脚本路由规则，并且支持可扩展 [2](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fn:2)。

### 写入路由规则

向注册中心写入路由规则的操作通常由监控中心或治理中心的页面完成

```java
RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
registry.register(URL.valueOf("route://0.0.0.0/com.foo.BarService?category=routers&dynamic=false&rule=" + URL.encode("host = 10.20.153.10 => host = 10.20.153.11")));
```

其中：

- `route://` 表示路由规则的类型，支持条件路由规则和脚本路由规则，可扩展，**必填**。
- `0.0.0.0` 表示对所有 IP 地址生效，如果只想对某个 IP 的生效，请填入具体 IP，**必填**。
- `com.foo.BarService` 表示只对指定服务生效，**必填**。
- `group=foo` 对指定服务的指定group生效，不填表示对未配置group的指定服务生效
- `version=1.0`对指定服务的指定version生效，不填表示对未配置version的指定服务生效
- `category=routers` 表示该数据为动态配置类型，**必填**。
- `dynamic=false` 表示该数据为持久数据，当注册方退出时，数据依然保存在注册中心，**必填**。
- `enabled=true` 覆盖规则是否生效，可不填，缺省生效。
- `force=false` 当路由结果为空时，是否强制执行，如果不强制执行，路由结果为空的路由规则将自动失效，可不填，缺省为 `false`。
- `runtime=false` 是否在每次调用时执行路由规则，否则只在提供者地址列表变更时预先执行并缓存结果，调用时直接从缓存中获取路由结果。如果用了参数路由，必须设为 `true`，需要注意设置会影响调用的性能，可不填，缺省为 `false`。
- `priority=1` 路由规则的优先级，用于排序，优先级越大越靠前执行，可不填，缺省为 `0`。
- `rule=URL.encode("host = 10.20.153.10 => host = 10.20.153.11")` 表示路由规则的内容，**必填**。

### 条件路由规则

基于条件表达式的路由规则，如：`host = 10.20.153.10 => host = 10.20.153.11`

### 规则：

- `=>` 之前的为消费者匹配条件，所有参数和消费者的 URL 进行对比，当消费者满足匹配条件时，对该消费者执行后面的过滤规则。
- `=>` 之后为提供者地址列表的过滤条件，所有参数和提供者的 URL 进行对比，消费者最终只拿到过滤后的地址列表。
- 如果匹配条件为空，表示对所有消费方应用，如：`=> host != 10.20.153.11`
- 如果过滤条件为空，表示禁止访问，如：`host = 10.20.153.10 =>`

### 表达式：

参数支持：

- 服务调用信息，如：method, argument 等，暂不支持参数路由
- URL 本身的字段，如：protocol, host, port 等
- 以及 URL 上的所有参数，如：application, organization 等

条件支持：

- 等号 `=` 表示"匹配"，如：`host = 10.20.153.10`
- 不等号 `!=` 表示"不匹配"，如：`host != 10.20.153.10`

值支持：

- 以逗号 `,` 分隔多个值，如：`host != 10.20.153.10,10.20.153.11`
- 以星号 `*` 结尾，表示通配，如：`host != 10.20.*`
- 以美元符 `$` 开头，表示引用消费者参数，如：`host = $host`

### 示例：

1. 排除预发布机：

   ```fallback
   => host != 172.22.3.91
   ```

2. 白名单 [3](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fn:3)：

   ```fallback
   host != 10.20.153.10,10.20.153.11 =>
   ```

3. 黑名单：

   ```fallback
   host = 10.20.153.10,10.20.153.11 =>
   ```

4. 服务寄宿在应用上，只暴露一部分的机器，防止整个集群挂掉：

   ```fallback
   => host = 172.22.3.1*,172.22.3.2*
   ```

5. 为重要应用提供额外的机器：

   ```fallback
   application != kylin => host != 172.22.3.95,172.22.3.96
   ```

6. 读写分离：

   ```fallback
   method = find*,list*,get*,is* => host = 172.22.3.94,172.22.3.95,172.22.3.96
   method != find*,list*,get*,is* => host = 172.22.3.97,172.22.3.98
   ```

7. 前后台分离：

   ```fallback
   application = bops => host = 172.22.3.91,172.22.3.92,172.22.3.93
   application != bops => host = 172.22.3.94,172.22.3.95,172.22.3.96
   ```

8. 隔离不同机房网段：

   ```fallback
   host != 172.22.3.* => host != 172.22.3.*
   ```

9. 提供者与消费者部署在同集群内，本机只访问本机的服务：

   ```fallback
   => host = $host
   ```

### 脚本路由规则

脚本路由规则 [4](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fn:4) 支持 JDK 脚本引擎的所有脚本，比如：javascript, jruby, groovy 等，通过 `type=javascript` 参数设置脚本类型，缺省为 javascript。

```fallback
"script://0.0.0.0/com.foo.BarService?category=routers&dynamic=false&rule=" + URL.encode("（function route(invokers) { ... } (invokers)）")
```

基于脚本引擎的路由规则，如：

```javascript
（function route(invokers) {
    var result = new java.util.ArrayList(invokers.size());
    for (i = 0; i < invokers.size(); i ++) {
        if ("10.20.153.10".equals(invokers.get(i).getUrl().getHost())) {
            result.add(invokers.get(i));
        }
    }
    return result;
} (invokers)）; // 表示立即执行方法
```

### 标签路由规则

标签路由规则 [5](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fn:5) ，当应用选择装配标签路由(TagRouter)之后，一次 dubbo 调用能够根据请求携带的 tag 标签智能地选择对应 tag 的服务提供者进行调用。

### 服务提供者

1. 给应用装配标签路由器：

```Java
@Bean
public ApplicationConfig applicationConfig() {
    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setName("provider-book");
    applicationConfig.setQosEnable(false);
    // instruct tag router
    Map<String,String> parameters = new HashMap<>();
    parameters.put(Constants.ROUTER_KEY, "tag");
    applicationConfig.setParameters(parameters);
    return applicationConfig;
}
```

1. 给应用设置具体的标签：

```java
@Bean
public ProviderConfig providerConfig(){
	ProviderConfig providerConfig = new ProviderConfig();
	providerConfig.setTag("red");
	return providerConfig;
}
```

应用未装配 tag 属性或服务提供者未设置 tag 属性，都将被认为是默认的应用，这些默认应用将会在调用无法匹配 provider 时当作降级方案。

### 服务消费者

```Java
RpcContext.getContext().setAttachment(Constants.REQUEST_TAG_KEY,"red");
```

请求标签的作用域为每一次 invocation，使用 attachment 来传递请求标签，注意保存在 attachment 中的值将会在一次完整的远程调用中持续传递，得益于这样的特性，我们只需要在起始调用时，通过一行代码的设置，达到标签的持续传递。

> 目前仅仅支持 hardcoding 的方式设置 requestTag。注意到 RpcContext 是线程绑定的，优雅的使用 TagRouter 特性，建议通过 servlet 过滤器(在 web 环境下)，或者定制的 SPI 过滤器设置 requestTag。

### 规则描述

1. request.tag=red 时优先选择 tag=red 的 provider。若集群中不存在与请求标记对应的服务，可以降级请求 tag=null 的 provider，即默认 provider。
2. request.tag=null 时，只会匹配 tag=null 的 provider。即使集群中存在可用的服务，若 tag 不匹配就无法调用，这与规则1不同，携带标签的请求可以降级访问到无标签的服务，但不携带标签/携带其他种类标签的请求永远无法访问到其他标签的服务。

------

1. `2.2.0` 以上版本支持 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fnref:1)
2. 路由规则扩展点：[路由扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/router) [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fnref:2)
3. 注意：一个服务只能有一条白名单规则，否则两条规则交叉，就都被筛选掉了 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fnref:3)
4. 注意：脚本没有沙箱约束，可执行任意代码，存在后门风险 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fnref:4)
5. 该特性在 `2.7.0` 以上版本支持 [↩︎](http://dubbo.apache.org/zh/docs/v2.7/user/examples/routing-rule-deprecated/#fnref:5)

## 配置规则

在 Dubbo 中配置应用级治理规则和服务级治理规则

#### 提示

本文描述的是新版本规则配置，而不是[老版本配置规则](http://dubbo.apache.org/zh/docs/v2.7/user/examples/config-rule-deprecated)

覆盖规则是 Dubbo 设计的在无需重启应用的情况下，动态调整 RPC 调用行为的一种能力。2.7.0 版本开始，支持从**服务**和**应用**两个粒度来调整动态配置。

### 概览

请在[服务治理控制台](http://47.91.207.147/#/governance/config)查看或修改覆盖规则。

应用粒度

```yaml
# 将应用demo（key:demo）在20880端口上提供（side:provider）的所有服务（scope:application）的权重修改为1000（weight:1000）。
---
configVersion: v2.7
scope: application
key: demo
enabled: true
configs:
- addresses: ["0.0.0.0:20880"]
  side: provider
  parameters:
  weight: 1000
  ...
```

服务粒度

```yaml
# 所有消费（side:consumer）DemoService服务（key:org.apache.dubbo.samples.governance.api.DemoService）的应用实例（addresses:[0.0.0.0]），超时时间修改为6000ms
---
configVersion: v2.7
scope: service
key: org.apache.dubbo.samples.governance.api.DemoService
enabled: true
configs:
- addresses: [0.0.0.0]
  side: consumer
  parameters:
  timeout: 6000
  ...
```

### 规则详解

#### 配置模板

```yaml
---
configVersion: v2.7
scope: application/service
key: app-name/group+service+version
enabled: true
configs:
- addresses: ["0.0.0.0"]
  providerAddresses: ["1.1.1.1:20880", "2.2.2.2:20881"]
  side: consumer
  applications/services: []
  parameters:
    timeout: 1000
    cluster: failfase
    loadbalance: random
- addresses: ["0.0.0.0:20880"]
  side: provider
  applications/services: []
  parameters:
    threadpool: fixed
    threads: 200
    iothreads: 4
    dispatcher: all
    weight: 200
...
```

其中：

- `configVersion` 表示 dubbo 的版本

- `scope`表示配置作用范围，分别是应用（application）或服务（service）粒度。**必填**。

- ```
  key
  ```

   

  指定规则体作用在哪个服务或应用。

  必填

  。

  - scope=service时，key取值为[{group}:]{service}[:{version}]的组合

- scope=application时，key取值为application名称

- `enabled=true` 覆盖规则是否生效，可不填，缺省生效。

- ```
  configs
  ```

   

  定义具体的覆盖规则内容，可以指定n（n>=1）个规则体。

  必填

  。

  - side，
  - applications
  - services
  - parameters
  - addresses
  - providerAddresses

**对于绝大多数配置场景，只需要理清楚以下问题基本就知道配置该怎么写了：**

1. 要修改整个应用的配置还是某个服务的配置。
   - 应用：`scope: application, key: app-name`（还可使用`services`指定某几个服务）。
   - 服务：`scope: service, key:group+service+version `。
2. 修改是作用到消费者端还是提供者端。
   - 消费者：`side: consumer` ，作用到消费端时（你还可以进一步使用`providerAddress`, `applications`选定特定的提供者示例或应用）。
   - 提供者：`side: provider`。
3. 配置是否只对某几个特定实例生效。
   - 所有实例：`addresses: ["0.0.0.0"] `或`addresses: ["0.0.0.0:*"] `具体由side值决定。
   - 指定实例：`addersses[实例地址列表]`。
4. 要修改的属性是哪个。

#### 示例

1. 禁用提供者：(通常用于临时踢除某台提供者机器，相似的，禁止消费者访问请使用路由规则)

   ```yaml
   ---
   configVersion: v2.7
   scope: application
   key: demo-provider
   enabled: true
   configs:
   - addresses: ["10.20.153.10:20880"]
     side: provider
     parameters:
       disabled: true
   ...
   ```

2. 调整权重：(通常用于容量评估，缺省权重为 200)

   ```yaml
   ---
   configVersion: v2.7
   scope: application
   key: demo-provider
   enabled: true
   configs:
   - addresses: ["10.20.153.10:20880"]
     side: provider
     parameters:
       weight: 200
   ...
   ```

3. 调整负载均衡策略：(缺省负载均衡策略为 random)

   ```yaml
   ---
   configVersion: v2.7
   scope: application
   key: demo-consumer
   enabled: true
   configs:
   - side: consumer
     parameters:
       loadbalance: random
   ...
   ```

4. 服务降级：(通常用于临时屏蔽某个出错的非关键服务)

   ```yaml
   ---
   configVersion: v2.7
   scope: service
   key: org.apache.dubbo.samples.governance.api.DemoService
   enabled: true
   configs:
   - side: consumer
    parameters:
      force: return null
   ...
   ```

## 旧配置规则

Dubbo 中旧版本的规则配置方式

向注册中心写入动态配置覆盖规则。该功能通常由监控中心或治理中心的页面完成。

```java
RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
registry.register(URL.valueOf("override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=foo&timeout=1000"));
```

其中：

- `override://` 表示数据采用覆盖方式，支持 `override` 和 `absent`，可扩展，**必填**。
- `0.0.0.0` 表示对所有 IP 地址生效，如果只想覆盖某个 IP 的数据，请填入具体 IP，**必填**。
- `com.foo.BarService` 表示只对指定服务生效，**必填**。
- `category=configurators` 表示该数据为动态配置类型，**必填**。
- `dynamic=false` 表示该数据为持久数据，当注册方退出时，数据依然保存在注册中心，**必填**。
- `enabled=true` 覆盖规则是否生效，可不填，缺省生效。
- `application=foo` 表示只对指定应用生效，可不填，表示对所有应用生效。
- `timeout=1000` 表示将满足以上条件的 `timeout` 参数的值覆盖为 1000。如果想覆盖其它参数，直接加在 `override` 的 URL 参数上。

示例：

1. 禁用提供者：(通常用于临时踢除某台提供者机器，相似的，禁止消费者访问请使用路由规则)

   ```fallback
   override://10.20.153.10/com.foo.BarService?category=configurators&dynamic=false&disbaled=true
   ```

2. 调整权重：(通常用于容量评估，缺省权重为 100)

   ```fallback
   override://10.20.153.10/com.foo.BarService?category=configurators&dynamic=false&weight=200
   ```

3. 调整负载均衡策略：(缺省负载均衡策略为 random)

   ```fallback
   override://10.20.153.10/com.foo.BarService?category=configurators&dynamic=false&loadbalance=leastactive
   ```

4. 服务降级：(通常用于临时屏蔽某个出错的非关键服务)

   ```fallback
   override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=foo&mock=force:return+null
   ```

#### 提示

`2.2.0` 以上版本支持

## 服务降级

降级 Dubbo 服务

可以通过服务降级功能临时屏蔽某个出错的非关键服务，并定义降级后的返回策略。

向注册中心写入动态配置覆盖规则：

```java
RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
registry.register(URL.valueOf("override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=foo&mock=force:return+null"));
```

其中：

- `mock=force:return+null` 表示消费方对该服务的方法调用都直接返回 null 值，不发起远程调用。用来屏蔽不重要服务不可用时对调用方的影响。
- 还可以改为 `mock=fail:return+null` 表示消费方对该服务的方法调用在失败后，再返回 null 值，不抛异常。用来容忍不重要服务不稳定时对调用方的影响。

#### 提示

`2.2.0` 以上版本支持

## 消费端线程池模型

Dubbo 消费端线程池模型用法

2.7.5 版本对整个调用链路做了全面的优化，根据压测结果显示，总体 QPS 性能提升将近 30%，同时也减少了调用过程中的内存分配开销。其中一个值得提及的设计点是 2.7.5 引入了 Servicerepository 的概念，在服务注册阶段提前生成 ServiceDescriptor 和 MethodDescriptor，以减少 RPC 调用阶段计算 Service 原信息带来的资源消耗。

### 消费端线程池模型优化

对 2.7.5 版本之前的 Dubbo 应用，尤其是一些消费端应用，当面临需要消费大量服务且并发数比较大的大流量场景时（典型如网关类场景），经常会出现消费端线程数分配过多的问题，具体问题讨论可参见 [Need a limited Threadpool in consumer side #2013](https://github.com/apache/dubbo/issues/2013)

改进后的消费端线程池模型，通过复用业务端被阻塞的线程，很好的解决了这个问题。

#### 老的线程池模型

![消费端线程池.png](E:\oldF\learningDocument\dubbo\consumer-threadpool0.png)

我们重点关注 Consumer 部分：

1. 业务线程发出请求，拿到一个 Future 实例。
2. 业务线程紧接着调用 future.get 阻塞等待业务结果返回。
3. 当业务数据返回后，交由独立的 Consumer 端线程池进行反序列化等处理，并调用 future.set 将反序列化后的业务结果置回。
4. 业务线程拿到结果直接返回

**2.7.5 版本引入的线程池模型**

![消费端线程池新.png](http://dubbo.apache.org/imgs/user/consumer-threadpool1.png)

1. 业务线程发出请求，拿到一个 Future 实例。
2. 在调用 future.get() 之前，先调用 ThreadlessExecutor.wait()，wait 会使业务线程在一个阻塞队列上等待，直到队列中被加入元素。
3. 当业务数据返回后，生成一个 Runnable Task 并放入 ThreadlessExecutor 队列
4. 业务线程将 Task 取出并在本线程中执行：反序列化业务数据并 set 到 Future。
5. 业务线程拿到结果直接返回

这样，相比于老的线程池模型，由业务线程自己负责监测并解析返回结果，免去了额外的消费端线程池开销。

关于性能优化，在接下来的版本中将会持续推进，主要从以下两个方面入手：

1. RPC 调用链路。目前能看到的点包括：进一步减少执行链路的内存分配、在保证协议兼容性的前提下提高协议传输效率、提高 Filter、Router 等计算效率。
2. 服务治理链路。进一步减少地址推送、服务治理规则推送等造成的内存、cpu 资源消耗。

## 优雅停机

让 Dubbo 服务完成优雅停机

Dubbo 是通过 JDK 的 ShutdownHook 来完成优雅停机的，所以如果用户使用 `kill -9 PID` 等强制关闭指令，是不会执行优雅停机的，只有通过 `kill PID` 时，才会执行。

### 原理

服务提供方

- 停止时，先标记为不接收新请求，新请求过来时直接报错，让客户端重试其它机器。
- 然后，检测线程池中的线程是否正在运行，如果有，等待所有线程执行完成，除非超时，则强制关闭。

服务消费方

- 停止时，不再发起新的调用请求，所有新的调用在客户端即报错。
- 然后，检测有没有请求的响应还没有返回，等待响应返回，除非超时，则强制关闭。

### 设置方式

设置优雅停机超时时间，缺省超时时间是 10 秒，如果超时则强制关闭。

```fallback
# dubbo.properties
dubbo.service.shutdown.wait=15000
```

如果 ShutdownHook 不能生效，可以自行调用：

```java
DubboShutdownHook.destroyAll();
```

#### 建议

使用 tomcat 等容器部署的场景，建议通过扩展 ContextListener 等自行调用以下代码实现优雅停机

## 主机绑定

在 Dubbo 中绑定主机名

### 查找顺序

缺省主机 IP 查找顺序：

- 通过 `LocalHost.getLocalHost()` 获取本机地址。
- 如果是 `127.*` 等 loopback 地址，则扫描各网卡，获取网卡 IP。

### 主机配置

注册的地址如果获取不正确，比如需要注册公网地址，可以：

1. 可以在 `/etc/hosts` 中加入：机器名 公网 IP，比如：

   ```fallback
   test1 205.182.23.201
   ```

2. 在 `dubbo.xml` 中加入主机地址的配置：

   ```xml
   <dubbo:protocol host="205.182.23.201">
   ```

3. 或在 `dubbo.properties` 中加入主机地址的配置：

   ```fallback
   dubbo.protocol.host=205.182.23.201
   ```

### 端口配置

缺省主机端口与协议相关：

| 协议       | 端口  |
| ---------- | ----- |
| dubbo      | 20880 |
| rmi        | 1099  |
| http       | 80    |
| hessian    | 80    |
| webservice | 80    |
| memcached  | 11211 |
| redis      | 6379  |

可以按照下面的方式配置端口：

1. 在 `dubbo.xml` 中加入主机地址的配置：

   ```xml
   <dubbo:protocol name="dubbo" port="20880">
   ```

2. 或在 `dubbo.properties` 中加入主机地址的配置：

   ```fallback
   dubbo.protocol.dubbo.port=20880
   ```

## 主机配置

自定义 Dubbo 服务对外暴露的主机地址

### 背景

在 Dubbo 中， Provider 启动时主要做两个事情，一是启动 server，二是向注册中心注册服务。启动 server 时需要绑定 socket，向注册中心注册服务时也需要发送 socket 唯一标识服务地址。

1. `dubbo`中不设置`host`时默认`host`是什么?
2. 那在`dubbo`中如何指定服务的`host`,我们是否可以用hostname或domain代替IP地址作为`host`?
3. 在使用docker时,有时需要设置端口映射,此时,启动server时绑定的socket和向注册中心注册的socket使用不同的端口号,此时又该如何设置?

#### dubbo 中不设置 host 时默认 host 是什么

一般的 dubbo 协议配置如下:

```xml
    ...
    <dubbo:protocol name="dubbo" port="20890" />
    ...
```

可以看到,只配置了端口号,没有配置 host，此时设置的 host 又是什么呢?

查看代码发现,在 `org.apache.dubbo.config.ServiceConfig#findConfigedHosts()` 中,通过 `InetAddress.getLocalHost().getHostAddress()` 获取默认 host。其返回值如下：

1. 未联网时，返回 127.0.0.1
2. 在阿里云服务器中，返回私有地址,如: 172.18.46.234
3. 在本机测试时，返回公有地址，如: 30.5.10.11

#### 那在 dubbo 中如何指定服务的 socket?

除此之外,可以通过 `dubbo.protocol` 或 `dubbo.provider `的 `host` 属性对 `host` 进行配置,支持IP地址和域名,如下:

```xml
    ...
    <dubbo:protocol name="dubbo" port="20890" host="www.example.com"/>
    ...
```

#### 在使用 docker 时，有时需要设置端口映射，此时，启动 server 时绑定的 socket 和向注册中心注册的 socket 使用不同的端口号，此时又该如何设置？

见 [dubbo 通过环境变量设置 host](https://github.com/dubbo/dubbo-samples/tree/master/dubbo-samples-docker)

有些部署场景需要动态指定服务注册的地址，如 docker bridge 网络模式下要指定注册宿主机 ip 以实现外网通信。dubbo 提供了两对启动阶段的系统属性，用于设置对外通信的ip、port地址。

- DUBBO_IP_TO_REGISTRY — 注册到注册中心的ip地址
- DUBBO_PORT_TO_REGISTRY — 注册到注册中心的port端口
- DUBBO_IP_TO_BIND — 监听ip地址
- DUBBO_PORT_TO_BIND — 监听port端口

以上四个配置项均为可选项，如不配置 dubbo 会自动获取 ip 与端口，请根据具体的部署场景灵活选择配置。 dubbo 支持多协议，如果一个应用同时暴露多个不同协议服务，且需要为每个服务单独指定 ip 或 port，请分别在以上属性前加协议前缀。 如：

- HESSIAN_DUBBO_PORT_TO_BIND hessian协议绑定的port
- DUBBO_DUBBO_PORT_TO_BIND dubbo协议绑定的port
- HESSIAN_DUBBO_IP_TO_REGISTRY hessian协议注册的ip
- DUBBO_DUBBO_PORT_TO_BIND dubbo协议注册的ip

PORT_TO_REGISTRY 或 IP_TO_REGISTRY 不会用作默认 PORT_TO_BIND 或 IP_TO_BIND，但是反过来是成立的 如设置 PORT_TO_REGISTRY=20881 IP_TO_REGISTRY=30.5.97.6，则 PORT_TO_BIND IP_TO_BIND 不受影响 如果设置 PORT_TO_BIND=20881 IP_TO_BIND=30.5.97.6，则默认 PORT_TO_REGISTRY=20881 IP_TO_REGISTRY=30.5.97.6

### 总结

1. 可以通过`dubbo.protocol`或`dubbo.provider`的`host`属性对`host`进行配置,支持IP地址和域名.但此时注册到注册中心的IP地址和监听IP地址是同一个值
2. 为了解决在虚拟环境或局域网内consumer无法与provider通信的问题,可以通过环境变量分别设置注册到注册中心的IP地址和监听IP地址,其优先级高于`dubbo.protocol`或`dubbo.provider`的`host`配置

### 参考

1. [Proposal: support hostname or domain in service discovery.](https://github.com/apache/dubbo/issues/2043)
2. [dubbo通过环境变量设置host](https://github.com/dubbo/dubbo-samples/tree/master/dubbo-samples-docker)

## 注册信息简化

减少注册中心上服务的注册数据

### 背景

Dubbo provider 中的服务配置项有接近 [30 个配置项](http://dubbo.apache.org/en-us/docs/user/references/xml/dubbo-service.html)。 排除注册中心服务治理需要之外，很大一部分配置项是 provider 自己使用，不需要透传给消费者。这部分数据不需要进入注册中心，而只需要以 key-value 形式持久化存储。

Dubbo consumer 中的配置项也有 [20+个配置项](http://dubbo.apache.org/en-us/docs/user/references/xml/dubbo-reference.html)。在注册中心之中，服务消费者列表中只需要关注 application，version，group，ip，dubbo 版本等少量配置，其他配置也可以以 key-value 形式持久化存储。

这些数据是以服务为维度注册进入注册中心，导致了数据量的膨胀，进而引发注册中心(如 zookeeper)的网络开销增大，性能降低。

### 现有功能 sample

当前现状一个简单展示。通过这个展示，分析下为什么需要做简化配置。

参考 sample 子工程： dubbo-samples-simplified-registry/dubbo-samples-simplified-registry-nosimple （跑 sample 前，先跑下 ZKClean 进行配置项清理）

dubbo-provider.xml配置

```fallback
<dubbo:application name="simplified-registry-nosimple-provider"/>
<dubbo:registry address="zookeeper://127.0.0.1:2181"/>
<bean id="demoService" class="org.apache.dubbo.samples.simplified.registry.nosimple.impl.DemoServiceImpl"/>
<dubbo:service async="true" interface="org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService" 
               version="1.2.3" group="dubbo-simple" ref="demoService" 
               executes="4500" retries="7" owner="vict" timeout="5300"/>
```

启动 provider 的 main 方法之后，查看 zookeeper 的叶子节点（路径为：/dubbo/org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService/providers 目录下）的内容如下：

```fallback
dubbo%3A%2F%2F30.5.124.158%3A20880%2Forg.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService
%3Fanyhost%3Dtrue%26application%3Dsimplified-registry-xml-provider%26async%3Dtrue%26dubbo%3D
2.0.2%26**executes**%3D4500%26generic%3Dfalse%26group%3Ddubbo-simple%26interface%3D
org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService%26methods%3D
sayHello%26**owner**%3Dvict%26pid%3D2767%26**retries**%3D7%26revision%3D1.2.3%26side%3D
provider%26**timeout**%3D5300%26timestamp%3D1542361152795%26valid%3Dtrue%26version%3D1.2.3
```

从加粗字体中能看到有：executes, retries, owner, timeout。但是这些字段不是每个都需要传递给 dubbo ops 或者 dubbo consumer。 同样的，consumer 也有这个问题，可以在例子中启动 Consumer 的 main 方法进行查看。

### 设计目标和宗旨

期望简化进入注册中心的 provider 和 consumer 配置数量。 期望将部分配置项以其他形式存储。这些配置项需要满足：不在服务调用链路上，同时这些配置项不在注册中心的核心链路上(服务查询，服务列表)。

### 配置

简化注册中心的配置，只在 2.7 之后的版本中进行支持。 开启 provider 或者 consumer 简化配置之后，默认保留的配置项如下：

provider：

| Constant Key              | Key             | remark                           |
| ------------------------- | --------------- | -------------------------------- |
| APPLICATION_KEY           | application     |                                  |
| CODEC_KEY                 | codec           |                                  |
| EXCHANGER_KEY             | exchanger       |                                  |
| SERIALIZATION_KEY         | serialization   |                                  |
| CLUSTER_KEY               | cluster         |                                  |
| CONNECTIONS_KEY           | connections     |                                  |
| DEPRECATED_KEY            | deprecated      |                                  |
| GROUP_KEY                 | group           |                                  |
| LOADBALANCE_KEY           | loadbalance     |                                  |
| MOCK_KEY                  | mock            |                                  |
| PATH_KEY                  | path            |                                  |
| TIMEOUT_KEY               | timeout         |                                  |
| TOKEN_KEY                 | token           |                                  |
| VERSION_KEY               | version         |                                  |
| WARMUP_KEY                | warmup          |                                  |
| WEIGHT_KEY                | weight          |                                  |
| TIMESTAMP_KEY             | timestamp       |                                  |
| DUBBO_VERSION_KEY         | dubbo           |                                  |
| SPECIFICATION_VERSION_KEY | **specVersion** | 新增，用于表述dubbo版本，如2.7.0 |

consumer：

| Constant Key              | Key             | remark                           |
| ------------------------- | --------------- | -------------------------------- |
| APPLICATION_KEY           | application     |                                  |
| VERSION_KEY               | version         |                                  |
| GROUP_KEY                 | group           |                                  |
| DUBBO_VERSION_KEY         | dubbo           |                                  |
| SPECIFICATION_VERSION_KEY | **specVersion** | 新增，用于表述dubbo版本，如2.7.0 |

Constant Key 表示来自于类 org.apache.dubbo.common.Constants 的字段。

下面介绍几种常用的使用方式。所有的 sample，都可以查看[sample-2.7](https://github.com/dubbo/dubbo-samples/tree/master)

### 方式1. 配置dubbo.properties

sample 在 dubbo-samples-simplified-registry/dubbo-samples-simplified-registry-xml 工程下 （跑 sample 前，先跑下ZKClean 进行配置项清理）

dubbo.properties

```fallback
dubbo.registry.simplified=true
dubbo.registry.extra-keys=retries,owner
```

怎么去验证呢？

##### provider端验证

provider端配置

```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    <!-- optional -->
    <dubbo:application name="simplified-registry-xml-provider"/>
    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>
    <bean id="demoService" class="org.apache.dubbo.samples.simplified.registry.nosimple.impl.DemoServiceImpl"/>
    <dubbo:service async="true" interface="org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService" version="1.2.3" group="dubbo-simple"
                   ref="demoService" executes="4500" retries="7" owner="vict" timeout="5300"/>

</beans>
```

得到的 zookeeper 的叶子节点的值如下：

```fallback
dubbo%3A%2F%2F30.5.124.149%3A20880%2Forg.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService%3F
application%3Dsimplified-registry-xml-provider%26dubbo%3D2.0.2%26group%3Ddubbo-simple%26**owner**%3D
vict%26**retries**%3D7%26**timeout**%3D5300%26timestamp%3D1542594503305%26version%3D1.2.3
```

和上面的**现有功能 sample**进行对比，上面的 sample 中，executes, retries, owner, timeout 四个配置项都进入了注册中心。但是本实例不是：

- 配置了：dubbo.registry.simplified=true， 默认情况下，timeout 在默认的配置项列表，所以还是会进入注册中心；
- 配置了：dubbo.registry.extra-keys=retries,owner ， 所以 retries，owner 也会进入注册中心。

总结：timeout，retries,owner 进入了注册中心，而 executes 没有进入。

consumer 端配置

```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">

    <!-- optional -->
    <dubbo:application name="simplified-registry-xml-consumer"/>

    <dubbo:registry address="zookeeper://127.0.0.1:2181" username="xxx" password="yyy" check="true"/>

    <dubbo:reference id="demoService" interface="org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService"
                     owner="vvv" retries="4" actives="6" timeout="4500" version="1.2.3" group="dubbo-simple"/>

</beans>
```

得到的 zookeeper 的叶子节点的值如下：

```fallback
consumer%3A%2F%2F30.5.124.149%2Forg.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService%3F
actives%3D6%26application%3Dsimplified-registry-xml-consumer%26category%3D
consumers%26check%3Dfalse%26dubbo%3D2.0.2%26group%3Ddubbo-simple%26owner%3Dvvv%26version%3D1.2.3
```

- 配置了：dubbo.registry.simplified=true ， 默认情况下，application,version,group,dubbo 在默认的配置项列表，所以还是会进入注册中心；

### 方式2. 声明spring bean

sample在dubbo-samples-simplified-registry/dubbo-samples-simplified-registry-annotation 工程下 （跑 sample 前，先跑下ZKClean 进行配置项清理）

##### Provider配置

privide 端 bean 配置：

```java
// 等同于dubbo.properties配置，用@Bean形式进行配置
@Bean
public RegistryConfig registryConfig() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("zookeeper://127.0.0.1:2181");
    registryConfig.setSimplified(true);
    registryConfig.setExtraKeys("retries,owner");
    return registryConfig;
}
// 暴露服务
@Service(version = "1.1.8", group = "d-test", executes = 4500, retries = 7, owner = "victanno", timeout = 5300)
public class AnnotationServiceImpl implements AnnotationService {
    @Override
    public String sayHello(String name) {
        System.out.println("async provider received: " + name);
        return "annotation: hello, " + name;
    }
}
```

和上面 sample 中的 dubbo.properties 的效果是一致的。结果如下：

- 默认情况下，timeout 在默认的配置项列表，所以还是会进入注册中心；
- 配置了 retries,owner 作为额外的 key 进入注册中心 ， 所以 retries，owner 也会进入注册中心。

总结：timeout，retries,owner 进入了注册中心，而 executes 没有进入。

##### Consumer配置

consumer 端 bean 配置：

```java
@Bean
public RegistryConfig registryConfig() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("zookeeper://127.0.0.1:2181");
    registryConfig.setSimplified(true);
    return registryConfig;
  }
```

消费服务：

```java
@Component("annotationAction")
public class AnnotationAction {

    @Reference(version = "1.1.8", group = "d-test", owner = "vvvanno", retries = 4, actives = 6, timeout = 4500)
    private AnnotationService annotationService;
    public String doSayHello(String name) {
        return annotationService.sayHello(name);
    }
}
```

和上面 sample 中 consumer 端的配置是一样的。结果如下：

- 默认情况下，application,version,group,dubbo 在默认的配置项列表，所以还是会进入注册中心。

###### 注意：

如果一个应用中既有provider又有consumer，那么配置需要合并成：

```java
@Bean
public RegistryConfig registryConfig() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("zookeeper://127.0.0.1:2181");
    registryConfig.setSimplified(true);
    //只对provider生效
    registryConfig.setExtraKeys("retries,owner");
    return registryConfig;
}
```

### 后续规划

本版本还保留了大量的配置项，接下来的版本中，会逐渐删除所有的配置项。

## 日志适配

在 Dubbo 中适配日志框架

自 `2.2.1` 开始，dubbo 开始内置 log4j、slf4j、jcl、jdk 这些日志框架的适配[1]，也可以通过以下方式显式配置日志输出策略：

1. 命令行

   ```sh
     java -Ddubbo.application.logger=log4j
   ```

2. 在 `dubbo.properties` 中指定

   ```fallback
     dubbo.application.logger=log4j
   ```

3. 在 `dubbo.xml` 中配置

   ```xml
     <dubbo:application logger="log4j" />
   ```

[1]: 自定义扩展可以参考 [日志适配扩展](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/logger-adapter)

## 访问日志

配置 Dubbo 的访问日志

如果你想记录每一次请求信息，可开启访问日志，类似于apache的访问日志。**注意**：此日志量比较大，请注意磁盘容量。

将访问日志输出到当前应用的log4j日志：

```xml
<dubbo:protocol accesslog="true" />
```

将访问日志输出到指定文件：

```xml
<dubbo:protocol accesslog="http://10.20.160.198/wiki/display/dubbo/foo/bar.log" />
```

## 服务容器

使用 Dubbo 中的服务容器

服务容器是一个 standalone 的启动程序，因为后台服务不需要 Tomcat 或 JBoss 等 Web 容器的功能，如果硬要用 Web 容器去加载服务提供方，增加复杂性，也浪费资源。

服务容器只是一个简单的 Main 方法，并加载一个简单的 Spring 容器，用于暴露服务。

服务容器的加载内容可以扩展，内置了 spring, jetty, log4j 等加载，可通过[容器扩展点](http://dubbo.apache.org/zh/docs/v2.7/dev/impls/container)进行扩展。配置配在 java 命令的 -D 参数或者 `dubbo.properties` 中。

### 容器类型

### Spring Container

- 自动加载 `META-INF/spring` 目录下的所有 Spring 配置。

- 配置 spring 配置加载位置：

  ```fallback
  dubbo.spring.config=classpath*:META-INF/spring/*.xml
  ```

### Jetty Container

- 启动一个内嵌 Jetty，用于汇报状态。
- 配置：
  - `dubbo.jetty.port=8080`：配置 jetty 启动端口
  - `dubbo.jetty.directory=/foo/bar`：配置可通过 jetty 直接访问的目录，用于存放静态文件
  - `dubbo.jetty.page=log,status,system`：配置显示的页面，缺省加载所有页面

### Log4j Container

- 自动配置 log4j 的配置，在多进程启动时，自动给日志文件按进程分目录。
- 配置：
  - `dubbo.log4j.file=/foo/bar.log`：配置日志文件路径
  - `dubbo.log4j.level=WARN`：配置日志级别
  - `dubbo.log4j.subdirectory=20880`：配置日志子目录，用于多进程启动，避免冲突

### 容器启动

缺省只加载 spring

```sh
java org.apache.dubbo.container.Main
```

通过 main 函数参数传入要加载的容器

```sh
java org.apache.dubbo.container.Main spring jetty log4j
```

通过 JVM 启动参数传入要加载的容器

```sh
java org.apache.dubbo.container.Main -Ddubbo.container=spring,jetty,log4j
```

通过 classpath 下的 `dubbo.properties` 配置传入要加载的容器

```fallback
dubbo.container=spring,jetty,log4j
```

## ReferenceConfig 缓存

在 Dubbo 中缓存 ReferenceConfig

`ReferenceConfig` 实例很重，封装了与注册中心的连接以及与提供者的连接，需要缓存。否则重复生成 `ReferenceConfig` 可能造成性能问题并且会有内存和连接泄漏。在 API 方式编程时，容易忽略此问题。

因此，自 `2.4.0` 版本开始， dubbo 提供了简单的工具类 `ReferenceConfigCache`用于缓存 `ReferenceConfig` 实例。

使用方式如下：

```java
ReferenceConfig<XxxService> reference = new ReferenceConfig<XxxService>();
reference.setInterface(XxxService.class);
reference.setVersion("1.0.0");
......
ReferenceConfigCache cache = ReferenceConfigCache.getCache();
// cache.get方法中会缓存 Reference对象，并且调用ReferenceConfig.get方法启动ReferenceConfig
XxxService xxxService = cache.get(reference);
// 注意！ Cache会持有ReferenceConfig，不要在外部再调用ReferenceConfig的destroy方法，导致Cache内的ReferenceConfig失效！
// 使用xxxService对象
xxxService.sayHello();
```

消除 Cache 中的 `ReferenceConfig`，将销毁 `ReferenceConfig` 并释放对应的资源。

```java
ReferenceConfigCache cache = ReferenceConfigCache.getCache();
cache.destroy(reference);
```

缺省 `ReferenceConfigCache` 把相同服务 Group、接口、版本的 `ReferenceConfig` 认为是相同，缓存一份。即以服务 Group、接口、版本为缓存的 Key。

可以修改这个策略，在 `ReferenceConfigCache.getCache` 时，传一个 `KeyGenerator`。详见 `ReferenceConfigCache` 类的方法。

```java
KeyGenerator keyGenerator = new ...
ReferenceConfigCache cache = ReferenceConfigCache.getCache(keyGenerator );
```

## 只注册

只注册不订阅

如果有两个镜像环境，两个注册中心，有一个服务只在其中一个注册中心有部署，另一个注册中心还没来得及部署，而两个注册中心的其它应用都需要依赖此服务。这个时候，可以让服务提供者方只注册服务到另一注册中心，而不从另一注册中心订阅服务。

禁用订阅配置

```xml
<dubbo:registry id="hzRegistry" address="10.20.153.10:9090" />
<dubbo:registry id="qdRegistry" address="10.20.141.150:9090" subscribe="false" />
```

或者

```xml
<dubbo:registry id="hzRegistry" address="10.20.153.10:9090" />
<dubbo:registry id="qdRegistry" address="10.20.141.150:9090?subscribe=false" />
```