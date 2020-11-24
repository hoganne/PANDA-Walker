# kafka文档笔记

# 简介

10分钟内您需要了解的有关卡夫卡的所有信息

##### [什么是事件流？](http://kafka.apache.org/intro#intro_streaming)

事件流是人体中枢神经系统的数字等效形式。它是“永远在线”世界的技术基础，在这个世界中，企业越来越多地由软件定义和自动化，而软件的用户则更多。

从技术上讲，事件流是一种以事件流的形式从事件源例如数据库，传感器，移动设备，云服务和软件应用程序)实时捕获数据的实践。持久存储这些事件流以供以后检索；实时以及回顾性地处理，处理和响应事件流；并根据需要将事件流`路由`到不同的目标技术。因此，事件流确保了数据的`连续流`和`解释`，以便正确的信息在正确的时间，正确的位置。

##### [我可以将事件流用于什么？](http://kafka.apache.org/intro#intro_usage)

事件流适用于众多行业和组织的[各种各样的用例]http://kafka.apache.org/powered-by)。它的许多示例包括：

-实时处理付款和金融交易，例如在证券交易所，银行和保险中。
-实时跟踪和监视汽车，卡车，车队和货运，例如物流和汽车行业。
-连续捕获和分析来自IoT设备或其他设备(例如工厂和风电场)中的传感器数据。
-收集并立即响应客户的交互和订单，例如在零售，酒店和旅游行业以及移动应用程序中。
-监测患者的医院护理情况并预测病情变化，以确保在紧急情况下及时得到治疗。
-连接，存储和提供公司不同部门产生的数据。
-用作数据平台，事件驱动的体系结构和微服务的基础。

##### [Apache Kafka？是事件流平台。这是什么意思？]

`Kafka`结合了三个关键功能，因此您可以通过一个经过战斗验证的解决方案，为端到端的事件流实现[您的用例](http://kafka.apache.org/powered-by)：

1.要“publish”(write)和“subscribe”(read)事件流，包括从其他系统连续导入/导出数据。
2.持续存储(store)您想要的事件流，持续时间可靠。
3.在事件流发生或追溯时对它们进行“处理”(process)。

所有这些功能都以分布式(distributed)，高度可用(highly)，弹性(elastic)，容错(fault-tolerant)和安全(secure)的方式提供。 Kafka可以部署在裸机硬件，虚拟机和容器，本地以及云中。您可以在自我管理Kafka环境与使用各种供应商提供的完全托管服务之间进行选择。

##### [简而言之，Kafka如何工作？](http://kafka.apache.org/intro#intro_nutshell)

Kafka是由“服务器”和“客户端”组成的分布式系统，它们通过高性能[TCP网络协议](http://kafka.apache.org/protocol.html)进行通信。它可以部署在内部以及云环境中的裸机硬件，虚拟机和容器上。

**服务器**：Kafka作为一台或多台服务器的集群运行，可以跨越多个数据中心或云区域。其中一些服务器构成了存储层，称为代理。其他服务器运行[Kafka Connect](http://kafka.apache.org/documentation/#connect)连续导入和导出数据作为事件流，以将Kafka与现有系统(如关系数据库以及其他Kafka集群)集成。为了实现关键任务用例，Kafka群集具有高度的可扩展性和容错能力：如果其任何服务器发生故障，其他服务器将接管其工作，以确保连续运行而不会丢失任何数据。

**客户端**：即使在网络问题或机器故障的情况下，它们也允许您编写分布式应用程序和微服务，以并行，大规模和容错的方式读取，写入和处理事件流。 Kafka附带了一些这样的客户端，由Kafka社区提供的[数十个客户端](https://cwiki.apache.org/confluence/display/KAFKA/Clients)进行了扩充：客户端可用于Java和Scala，包括适用于Go，Python，C / C ++和许多其他编程语言以及REST API的高级[Kafka Streams](http://kafka.apache.org/documentation/streams/)库。

[主要概念和术语](http://kafka.apache.org/intro#intro_concepts_and_terms)

一项“事件”(event)记录了一个事实，即世界或您的企业中发生了“某些事情”。在文档中也称为记录或消息。当您向Kafka读取或写入数据时，您将以事件的形式进行操作。从概念上讲，事件具有键(key)，值(value)，(timestamp)时间戳和可选的元数据(optional metadata headers)标题。这是一个示例事件：

-事件键(event key)：“爱丽丝”
-事件价值(event value)：“向Bob支付了$ 200”
-活动时间戳记(event timestamp)：“ 2020年6月25日，下午2:06”。

生产者(producers)**是那些将事件发布(写入)到Kafka的客户端应用程序，**消费者(consumers)**是那些订阅(subscribe)(读取和处理)这些事件的客户端应用程序。在Kafka中，生产者和消费者之间完全脱钩且彼此不可知，这是实现Kafka众所周知的高可伸缩性的关键设计元素。例如，生产者永远不需要等待消费者。 Kafka提供了各种[保证](http://kafka.apache.org/documentation/#intro_guarantees)，例如能够一次准确地处理事件。

事件被组织并持久存储在**主题(topics)**中。非常简化，主题（topic）类似于文件系统中的文件夹，事件（event）是该文件夹中的文件。示例主题名称（topics name）可以是“付款”。 Kafka中的主题始终是多生产者和多用户的：一个主题可以有零个，一个或多个向其写入事件的生产者，以及零个，一个或多个订阅这些事件的使用者。可以随时根据需要读取主题中的事件...与传统的消息传递系统不同，使用后不会删除事件。相反，您可以通过按主题配置设置来定义Kafka将事件保留多长时间，之后旧的事件将被丢弃。 Kafka的性能相对于数据大小实际上是恒定的，因此长时间存储数据是完全可以的。

主题是“分区的”（topics are partitioned），这意味着主题分布在位于不同Kafka brokers（代理）上的多个“存储桶”（buckets）中。数据的这种分布式放置对于可伸缩性非常重要，因为它允许客户端应用程序同时从多个代理读取数据或向多个代理写入数据。将新事件发布到主题时，实际上会将其附加到主题的分区之一。具有相同事件密钥（event key）(例如，客户或车辆ID)的事件将写入同一分区，Kafka保证给定主题分区的任何使用者都将始终以与写入事件完全相同的顺序读取该分区的事件。

![img](http://kafka.apache.org/images/streams-and-tables-p1_p4.png)图（figure）：此示例主题具有四个分区P1--P4。通过在网络上将事件写入主题分区，两个不同的生产者客户端正在彼此独立地发布新事件。具有相同键(在图中由其颜色表示)的事件被写入同一分区。请注意，如果合适，两个生产者都可以写入同一分区。

为了使您的数据具有容错性（fault-tolerant）和(highly-available)高可用性，甚至可以跨地理区域或数据中心“复制”(replication)每个主题，以便始终有多个代理(brokers)拥有数据副本，以防万一出错，您想要对代理(brokers)进行维护，等等。常见的生产设置是3的复制因子，即，您的数据将始终有三个副本。此复制在主题分区级别执行。

该入门指南应该足够。本文档的[设计](http://kafka.apache.org/documentation/#design)部分会详细介绍Kafka的各种概念，如果您有兴趣的话。

##### [Kafka API](http://kafka.apache.org/intro#intro_apis)

除了用于管理和管理任务的命令行工具外，Kafka还具有用于Java和Scala的五个核心API：

-[Admin API](http://kafka.apache.org/documentation.html#adminapi)，用于管理和检查主题(topics)，代理(brokers)和其他Kafka对象(objects)。
-[生产者API](http://kafka.apache.org/documentation.html#producerapi)，(producer)用于发布(写入)针对一个或多个Kafka主题的事件流。
-[消费者API](http://kafka.apache.org/documentation.html#consumerapi)(consumer)订阅(阅读)一个或多个主题并处理向其产生的事件流。
-[Kafka Streams API](http://kafka.apache.org/documentation/streams)，用于实现流处理应用程序和微服务。它提供了更高级别的功能来处理事件流，包括转换，诸如聚合和联接之类的有状态操作，窗口，基于事件时间的处理等等。从一个或多个主题读取输入，以便生成一个或多个主题的输出，从而有效地将输入流转换为输出流。
-[Kafka Connect API](http://kafka.apache.org/documentation.html#connect)可以构建和运行可重复使用的数据导入/导出连接器，这些连接器在往返于彼此之间消耗(读取)或产生(写入)事件流。外部系统和应用程序，以便它们可以与Kafka集成。例如，关系数据库(如PostgreSQL)的连接器可能会捕获对一组表的所有更改。但是，实际上，您通常不需要实现自己的连接器，因为Kafka社区已经提供了数百个随时可用的连接器。

##### [哪里可以去](http://kafka.apache.org/intro#intro_more)

-要获得有关Kafka的动手经验，请遵循[快速入门](http://kafka.apache.org/quickstart)。
-要更详细地了解Kafka，请阅读[Documentation](http://kafka.apache.org/documentation/)。您还可以选择[Kafka书籍和学术论文](http://kafka.apache.org/books-and-papers)。
-浏览[用例](http://kafka.apache.org/powered-by)，以了解我们全球社区中的其他用户如何从Kafka中获得价值。
-加入主要会议[本地卡夫卡聚会小组](http://kafka.apache.org/events)和[观看卡夫卡峰会的演讲](https://kafka-summit.org/past-events/)。卡夫卡社区。

# 快速开始

Interested in getting started with Kafka? Follow the instructions in this quickstart, or watch the video below.

##### [STEP 1: GET KAFKA](http://kafka.apache.org/quickstart#quickstart_download)

[Download](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.6.0/kafka_2.13-2.6.0.tgz) the latest Kafka release and extract it:

下载最新的Kafka版本并解压缩：

```bash
$ tar -xzf kafka_2.13-2.6.0.tgz
$ cd kafka_2.13-2.6.0
```

##### [STEP 2: START THE KAFKA ENVIRONMENT](http://kafka.apache.org/quickstart#quickstart_startserver)

NOTE: Your local environment must have Java 8+ installed.

Run the following commands in order to start all services in the correct order:

注意：您的本地环境必须安装了Java 8+。

运行以下命令以正确的顺序启动所有服务：

```bash
# Start the ZooKeeper service
# Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
#注意：很快，Apache Kafka将不再需要ZooKeeper。
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

Open another terminal session and run:

```bash
# Start the Kafka broker service
$ bin/kafka-server-start.sh config/server.properties
```

Once all services have successfully launched, you will have a basic Kafka environment running and ready to use.

成功启动所有服务后，您将运行并可以使用基本的Kafka环境。

##### [STEP 3: CREATE A TOPIC TO STORE YOUR EVENTS](http://kafka.apache.org/quickstart#quickstart_createtopic)

建立主题以储存您的活动
Kafka是一个分布式事件流平台，可让您跨多台机器读取，写入，存储和处理事件（在文档中也称为记录或消息）。

Kafka is a distributed *event streaming platform* that lets you read, write, store, and process [*events*](http://kafka.apache.org/documentation/#messages) (also called *records* or *messages* in the documentation) across many machines.

Example events are payment transactions, geolocation updates from mobile phones, shipping orders, sensor measurements from IoT devices or medical equipment, and much more. These events are organized and stored in [*topics*](http://kafka.apache.org/documentation/#intro_topics). Very simplified, a topic is similar to a folder in a filesystem, and the events are the files in that folder.

示例事件包括付款交易，移动电话的地理位置更新，运输订单，物联网设备或医疗设备的传感器测量等等。这些事件被组织并存储在主题中。非常简化，主题类似于文件系统中的文件夹，事件是该文件夹中的文件。

So before you can write your first events, you must create a topic. Open another terminal session and run:

因此，在编写第一个事件之前，必须创建一个主题。打开另一个终端会话并运行：

```bash
$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
```

All of Kafka's command line tools have additional options: run the `kafka-topics.sh` command without any arguments to display usage information. For example, it can also show you [details such as the partition count](http://kafka.apache.org/documentation/#intro_topics) of the new topic:

Kafka的所有命令行工具都具有其他选项：不带任何参数的kafka-topics.sh命令以显示用法信息。例如，它还可以向您显示详细信息，例如新主题的分区数：

```bash
$ bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
Topic:quickstart-events  PartitionCount:1    ReplicationFactor:1 Configs:
    Topic: quickstart-events Partition: 0    Leader: 0   Replicas: 0 Isr: 0
```

##### [STEP 4: WRITE SOME EVENTS INTO THE TOPIC](http://kafka.apache.org/quickstart#quickstart_send)

A Kafka client communicates with the Kafka brokers via the network for writing (or reading) events. Once received, the brokers will store the events in a durable and fault-tolerant manner for as long as you need—even forever.

将一些事件写入主题
Kafka客户端通过网络与Kafka brokers（代理）进行通信，以编写（或读取）事件。一旦收到，代理将以持久和容错的方式存储事件，只要您需要甚至永远。

Run the console producer client to write a few events into your topic. By default, each line you enter will result in a separate event being written to the topic.

运行控制台生产者客户端，以将一些事件写入您的主题。默认情况下，您输入的每一行都会导致将一个单独的事件写入该主题。

```bash
$ bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
This is my first event
This is my second event
```

You can stop the producer client with `Ctrl-C` at any time.

您可以随时使用Ctrl-C停止生产者客户端。

##### [STEP 5: READ THE EVENTS](http://kafka.apache.org/quickstart#quickstart_consume)

Open another terminal session and run the console consumer client to read the events you just created:

打开另一个终端会话并运行控制台使用者客户端以读取您刚刚创建的事件：

```bash
$ bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
This is my first event
This is my second event
```

You can stop the consumer client with `Ctrl-C` at any time.

您可以随时使用Ctrl-C停止使用方客户端。

Feel free to experiment: for example, switch back to your producer terminal (previous step) to write additional events, and see how the events immediately show up in your consumer terminal.

随时尝试：例如，切换回生产者终端（上一步）以编写其他事件，并查看事件如何立即显示在消费者终端中。

Because events are durably stored in Kafka, they can be read as many times and by as many consumers as you want. You can easily verify this by opening yet another terminal session and re-running the previous command again.

因为事件被持久地存储在Kafka中，所以您可以根据需要任意多次地读取它们。您可以通过打开另一个终端会话并再次重新运行上一个命令来轻松地验证这一点。

##### [STEP 6: IMPORT/EXPORT YOUR DATA AS STREAMS OF EVENTS WITH KAFKA CONNECT](http://kafka.apache.org/quickstart#quickstart_kafkaconnect)

You probably have lots of data in existing systems like relational databases or traditional messaging systems, along with many applications that already use these systems. [Kafka Connect](http://kafka.apache.org/documentation/#connect) allows you to continuously ingest data from external systems into Kafka, and vice versa. It is thus very easy to integrate existing systems with Kafka. To make this process even easier, there are hundreds of such connectors readily available.

使用KAFKA CONNECT将数据作为事件流导入/导出
在诸如关系数据库或传统消息传递系统之类的现有系统中，您可能拥有大量数据，以及已经使用这些系统的许多应用程序。通过Kafka Connect，您可以将来自外部系统的数据连续地吸收到Kafka中，反之亦然。因此，将现有系统与Kafka集成非常容易。为了使此过程更加容易，有数百种此类连接器可供使用。

Take a look at the [Kafka Connect section](http://kafka.apache.org/documentation/#connect) learn more about how to continuously import/export your data into and out of Kafka.

看一下Kafka Connect部分，了解更多有关如何连续地将数据导入和导出Kafka的信息。

##### [STEP 7: PROCESS YOUR EVENTS WITH KAFKA STREAMS](http://kafka.apache.org/quickstart#quickstart_kafkastreams)

Once your data is stored in Kafka as events, you can process the data with the [Kafka Streams](http://kafka.apache.org/documentation/streams) client library for Java/Scala. It allows you to implement mission-critical real-time applications and microservices, where the input and/or output data is stored in Kafka topics. Kafka Streams combines the simplicity of writing and deploying standard Java and Scala applications on the client side with the benefits of Kafka's server-side cluster technology to make these applications highly scalable, elastic, fault-tolerant, and distributed. The library supports exactly-once processing, stateful operations and aggregations, windowing, joins, processing based on event-time, and much more.

使用kafka流处理您的事件
一旦将数据作为事件存储在Kafka中，就可以使用Java / Scala的Kafka Streams客户端库处理数据。它允许您实现关键任务实时应用程序和微服务，其中输入和/或输出数据存储在Kafka主题中。 Kafka Streams结合了在客户端编写和部署标准Java和Scala应用程序的简便性以及Kafka服务器端集群技术的优势，使这些应用程序具有高度可伸缩性（highly scalable），弹性(elastic)，容错性（fault-tolerant）和分布式性（distributed）。该库支持一次精确处理（exactly-once processing），有状态操作和聚合(stateful operations and aggregations)，开窗，联接(join)，基于事件时间的处理等等。

To give you a first taste, here's how one would implement the popular `WordCount` algorithm:

为了让您有一个初步的了解，以下是实现流行的WordCount算法的方法：

```bash
KStream<String, String> textLines = builder.stream("quickstart-events");

KTable<String, Long> wordCounts = textLines
            .flatMapValues(line -> Arrays.asList(line.toLowerCase().split(" ")))
            .groupBy((keyIgnored, word) -> word)
            .count();

wordCounts.toStream().to("output-topic"), Produced.with(Serdes.String(), Serdes.Long()));
```

The [Kafka Streams demo](http://kafka.apache.org/25/documentation/streams/quickstart) and the [app development tutorial](http://kafka.apache.org/25/documentation/streams/tutorial) demonstrate how to code and run such a streaming application from start to finish.

Kafka Streams演示和应用程序开发教程演示了如何从头到尾编写和运行这种流媒体应用程序。

##### [STEP 8: TERMINATE THE KAFKA ENVIRONMENT](http://kafka.apache.org/quickstart#quickstart_kafkaterminate)

Now that you reached the end of the quickstart, feel free to tear down the Kafka environment—or continue playing around.

终止KAFKA环境
既然您已经开始快速入门，请随时拆除Kafka环境...或继续玩耍。

1. Stop the producer and consumer clients with `Ctrl-C`, if you haven't done so already.

   如果尚未停止，请使用Ctrl-C停止生产者和消费者客户端。

2. Stop the Kafka broker with `Ctrl-C`.

   使用Ctrl-C停止Kafka代理。

3. Lastly, stop the ZooKeeper server with `Ctrl-C`.

   最后，使用Ctrl-C停止ZooKeeper服务器。

If you also want to delete any data of your local Kafka environment including any events you have created along the way, run the command:

如果您还想删除本地Kafka环境的任何数据，包括您在此过程中创建的所有事件，请运行以下命令：

```bash
$ rm -rf /tmp/kafka-logs /tmp/zookeeper
```

##### [CONGRATULATIONS!](http://kafka.apache.org/quickstart#quickstart_kafkacongrats)

You have successfully finished the Apache Kafka quickstart.

To learn more, we suggest the following next steps:

- Read through the brief [Introduction](http://kafka.apache.org/intro) to learn how Kafka works at a high level, its main concepts, and how it compares to other technologies. To understand Kafka in more detail, head over to the [Documentation](http://kafka.apache.org/documentation/).
- Browse through the [Use Cases](http://kafka.apache.org/powered-by) to learn how other users in our world-wide community are getting value out of Kafka.
- Join a [local Kafka meetup group](http://kafka.apache.org/events) and [watch talks from Kafka Summit](https://kafka-summit.org/past-events/), the main conference of the Kafka community.

您已成功完成Apache Kafka快速入门。

要了解更多信息，我们建议执行以下后续步骤：

通读简短的简介，以了解Kafka的工作原理，主要概念以及与其他技术的比较。要更详细地了解Kafka，请转至文档。
浏览用例，了解我们全球社区中的其他用户如何从Kafka中获得价值。
加入当地的卡夫卡聚会小组，观看卡夫卡社区主要会议卡夫卡峰会的演讲。

# document文档

## [2. APIS](http://kafka.apache.org/documentation/#api)

Kafka includes five core apis:

Kafka包含五个核心api：

1. The [Producer](http://kafka.apache.org/documentation/#producerapi) API allows applications to send streams of data to topics in the Kafka cluster.

   API允许应用程序将数据流发送到Kafka集群中的主题。

2. The [Consumer](http://kafka.apache.org/documentation/#consumerapi) API allows applications to read streams of data from topics in the Kafka cluster.

   API允许应用程序从Kafka集群中的主题读取数据流。

3. The [Streams](http://kafka.apache.org/documentation/#streamsapi) API allows transforming streams of data from input topics to output topics.

   API允许将数据流从输入主题转换为输出主题。

4. The [Connect](http://kafka.apache.org/documentation/#connectapi) API allows implementing connectors that continually pull from some source system or application into Kafka or push from Kafka into some sink system or application.

   API允许实现连接器，这些连接器不断地从某些源系统或应用程序拉入Kafka或从Kafka推入某些接收器系统或应用程序。

5. The [Admin](http://kafka.apache.org/documentation/#adminapi) API allows managing and inspecting topics, brokers, and other Kafka objects.

   API允许管理和检查主题，代理和其他Kafka对象。

Kafka exposes all its functionality over a language independent protocol which has clients available in many programming languages. However only the Java clients are maintained as part of the main Kafka project, the others are available as independent open source projects. A list of non-Java clients is available [here](https://cwiki.apache.org/confluence/display/KAFKA/Clients).

Kafka通过与语言无关的协议公开其所有功能，该协议具有可用于多种编程语言的客户端。但是，只有Java客户端是作为Kafka主项目的一部分维护的，其他Java客户端则可以作为独立的开源项目使用。可在[此处]获得非Java客户端列表。

### [2.1 Producer API](http://kafka.apache.org/documentation/#producerapi)

The Producer API allows applications to send streams of data to topics in the Kafka cluster.

生产者API允许应用程序将数据流发送到Kafka集群中的主题。

Examples showing how to use the producer are given in the [javadocs](http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html).

javadocs中提供了显示如何使用生产者的示例。

To use the producer, you can use the following maven dependency:

要使用生产者，可以使用以下Maven依赖项：

```xml
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.6.0</version>
		</dependency>
```

### [2.2 Consumer API](http://kafka.apache.org/documentation/#consumerapi)

The Consumer API allows applications to read streams of data from topics in the Kafka cluster.

Examples showing how to use the consumer are given in the [javadocs](http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html).

To use the consumer, you can use the following maven dependency:

消费者API允许应用程序从Kafka集群中的主题读取数据流。

[javadocs]中给出了显示使用消费者的示例。

要使用使用者，可以使用以下Maven依赖项：

```xml
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.6.0</version>
		</dependency>
```

### [2.3 Streams API](http://kafka.apache.org/documentation/#streamsapi)

The [Streams](http://kafka.apache.org/documentation/#streamsapi) API allows transforming streams of data from input topics to output topics.

Examples showing how to use this library are given in the [javadocs](http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/streams/KafkaStreams.html)

Additional documentation on using the Streams API is available [here](http://kafka.apache.org/26/documentation/streams).

To use Kafka Streams you can use the following maven dependency:

[Streams]API允许将数据流从输入主题转换为输出主题。

[javadocs]中提供了显示如何使用此库的示例。

有关使用Streams API的其他文档，请参见[此处]。

要使用Kafka Streams，可以使用以下Maven依赖项：

```xml
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-streams</artifactId>
			<version>2.6.0</version>
		</dependency>
```

When using Scala you may optionally include the `kafka-streams-scala` library. Additional documentation on using the Kafka Streams DSL for Scala is available [in the developer guide](http://kafka.apache.org/26/documentation/streams/developer-guide/dsl-api.html#scala-dsl).

To use Kafka Streams DSL for Scala for Scala 2.13 you can use the following maven dependency:

使用Scala时，您可以选择包含`kafka-streams-scala`库。可在[开发人员指南]中获得有关使用Kafka Streams DSL for Scala的其他文档。

要将Kafka Streams DSL for Scala用于Scala 2.13，可以使用以下maven依赖项：

```xml
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-streams-scala_2.13</artifactId>
			<version>2.6.0</version>
		</dependency>
```

### [2.4 Connect API](http://kafka.apache.org/documentation/#connectapi)

The Connect API allows implementing connectors that continually pull from some source data system into Kafka or push from Kafka into some sink data system.

Many users of Connect won't need to use this API directly, though, they can use pre-built connectors without needing to write any code. Additional information on using Connect is available [here](http://kafka.apache.org/documentation.html#connect).

Those who want to implement custom connectors can see the [javadoc](http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/connect).

Connect API允许实现连接器，这些连接器不断地从某些源数据系统拉入Kafka或从Kafka推入某些接收器数据系统。

尽管Connect的许多用户并不需要直接使用此API，但是他们可以使用预构建的连接器而无需编写任何代码。有关使用Connect的其他信息，请参见[此处]。

那些想要实现自定义连接器的人可以查看[javadoc]。

### [2.5 Admin API](http://kafka.apache.org/documentation/#adminapi)

The Admin API supports managing and inspecting topics, brokers, acls, and other Kafka objects.

To use the Admin API, add the following Maven dependency:

Admin API支持管理和检查主题，代理，ACL和其他Kafka对象。

要使用Admin API，请添加以下Maven依赖项：

```xml
		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.6.0</version>
		</dependency>
```

For more information about the Admin APIs, see the [javadoc](http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/admin/Admin.html).

## [3. CONFIGURATION](http://kafka.apache.org/documentation/#configuration)

Kafka uses key-value pairs in the [property file format](http://en.wikipedia.org/wiki/.properties) for configuration. These values can be supplied either from a file or programmatically.

Kafka使用[属性文件格式]的键/值对进行配置。这些值可以从文件或以编程方式提供。

### [3.1 Broker Configs](http://kafka.apache.org/documentation/#brokerconfigs)

The essential configurations are the following:

基本配置如下：

- `broker.id`
- `log.dirs`
- `zookeeper.connect`

Topic#### [below](http://kafka.apache.org/documentation/#topicconfigs).

主题级别的配置和默认设置将在下面详细讨论。

#### [zookeeper.connect](http://kafka.apache.org/documentation/#zookeeper.connect)

  Specifies the ZooKeeper connection string in the form `hostname:port` where host and port are the host and port of a ZooKeeper server. To allow connecting through other ZooKeeper nodes when that ZooKeeper machine is down you can also specify multiple hosts in the form `hostname1:port1,hostname2:port2,hostname3:port3`.
  The server can also have a ZooKeeper chroot path as part of its ZooKeeper connection string which puts its data under some path in the global ZooKeeper namespace. For example to give a chroot path of `/chroot/path` you would give the connection string as `hostname1:port1,hostname2:port2,hostname3:port3/chroot/path`.

  以`hostname:port`的形式指定ZooKeeper连接字符串，其中host和port是ZooKeeper服务器的主机和端口。要允许当ZooKeeper机器关闭时通过其他ZooKeeper节点进行连接，您还可以以`hostname1：port1，hostname2：port2，hostname3：port3`的形式指定多个主机。
    服务器还可以在其ZooKeeper连接字符串中包含一个ZooKeeper chroot路径，该路径将其数据放在全局ZooKeeper命名空间中的某个路径下。例如，要给chroot路径指定` / chroot / path`，您可以将连接字符串指定为` hostname1：port1，hostname2：port2，hostname3：port3 / chroot / path`。

|         Type: | string    |
| ------------: | --------- |
|      Default: |           |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [advertised.host.name](http://kafka.apache.org/documentation/#advertised.host.name)

  DEPRECATED: only used when `advertised.listeners` or `listeners` are not set. Use `advertised.listeners` instead.

  已弃用：仅在未设置` advertised.listeners`或` listeners`时使用。使用`advertised.listeners`。

  Hostname to publish to ZooKeeper for clients to use. In IaaS environments, this may need to be different from the interface to which the broker binds. If this is not set, it will use the value for `host.name` if configured. Otherwise it will use the value returned from java.net.InetAddress.getCanonicalHostName().

    要发布给ZooKeeper的主机名，供客户端使用。在IaaS环境中，这可能需要与代理(broker)绑定的接口不同。如果未设置，则将使用` host.name`的值（如果已配置）。否则，它将使用从java.net.InetAddress.getCanonicalHostName()返回的值。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [advertised.listeners](http://kafka.apache.org/documentation/#advertised.listeners)

  Listeners to publish to ZooKeeper for clients to use, if different than the `listeners` config property. In IaaS environments, this may need to be different from the interface to which the broker binds. If this is not set, the value for `listeners` will be used. Unlike `listeners` it is not valid to advertise the 0.0.0.0 meta-address.

    如果监听器配置与`listeners`配置属性不同，则发布给ZooKeeper的监听器供客户端使用。在IaaS环境中，这可能需要与代理绑定的接口不同。如果未设置，将使用` listeners`的值。与`listeners`不同，它不能有效地宣传0.0.0.0元地址。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | high       |
|  Update Mode: | per-broker |

#### [advertised.port](http://kafka.apache.org/documentation/#advertised.port)

  DEPRECATED: only used when `advertised.listeners` or `listeners` are not set. Use `advertised.listeners` instead.
  The port to publish to ZooKeeper for clients to use. In IaaS environments, this may need to be different from the port to which the broker binds. If this is not set, it will publish the same port that the broker binds to.

  已弃用：仅在未设置` advertised.listeners`或` listeners`时使用。使用`advertised.listeners`。

    发布到ZooKeeper供客户端使用的端口。在IaaS环境中，这可能需要与代理绑定的端口不同。如果未设置，它将发布代理绑定到的相同端口。

|         Type: | int       |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [auto.create.topics.enable](http://kafka.apache.org/documentation/#auto.create.topics.enable)

  Enable auto creation of topic on the server

  在服务器上启用主题自动创建

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [auto.leader.rebalance.enable](http://kafka.apache.org/documentation/#auto.leader.rebalance.enable)

  Enables auto leader balancing. A background thread checks the distribution of partition leaders at regular intervals, configurable by `leader.imbalance.check.interval.seconds`. If the leader imbalance exceeds `leader.imbalance.per.broker.percentage`, leader rebalance to the preferred leader for partitions is triggered.

    启用自动领导者平衡。后台线程定期检查分区领导者的分布，可通过` leader.imbalance.check.interval.seconds`进行配置。如果领导者不平衡超过` leader.imbalance.per.broker.percentage`，则会导致领导者重新平衡到分区的首选领导者。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [background.threads](http://kafka.apache.org/documentation/#background.threads)

  The number of threads to use for various background processing tasks

  用于各种后台处理任务的线程数

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 10           |
| Valid Values: | [1,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [broker.id](http://kafka.apache.org/documentation/#broker.id)

  The broker id for this server. If unset, a unique broker id will be generated.To avoid conflicts between zookeeper generated broker id's and user configured broker id's, generated broker ids start from reserved.broker.max.id + 1.

    该服务器的代理标识。如果未设置，将生成一个唯一的代理ID。为避免Zookeeper生成的代理ID与用户配置的 ID之间发生冲突，生成的代理ID从reserved.broker.max.id +1开始。

|         Type: | int       |
| ------------: | --------- |
|      Default: | -1        |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [compression.type](http://kafka.apache.org/documentation/#compression.type)

  Specify the final compression type for a given topic. This configuration accepts the standard compression codecs ('gzip', 'snappy', 'lz4', 'zstd'). It additionally accepts 'uncompressed' which is equivalent to no compression; and 'producer' which means retain the original compression codec set by the producer.

    指定给定主题的最终压缩类型。此配置接受标准压缩编解码器（` gzip`，` snappy`，` lz4`，` zstd`）。此外，它接受`未压缩（uncompressed）`，等同于不压缩。和`生产者(producer)`，表示保留生产者设置的原始压缩编解码器。

|         Type: | string       |
| ------------: | ------------ |
|      Default: | producer     |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [control.plane.listener.name](http://kafka.apache.org/documentation/#control.plane.listener.name)

  Name of listener used for communication between controller and brokers. Broker will use the control.plane.listener.name to locate the endpoint in listeners list, to listen for connections from the controller. For example, if a broker's config is :

  用于控制器和代理之间的通信的侦听器的名称。 Broker将使用control.plane.listener.name在侦听器列表中定位端点，以侦听来自控制器的连接。例如，如果代理的配置为：

  ```sh
  listeners = INTERNAL://192.1.1.8:9092, EXTERNAL://10.1.1.5:9093, CONTROLLER://192.1.1.8:9094
  listener.security.protocol.map = INTERNAL:PLAINTEXT, EXTERNAL:SSL, CONTROLLER:SSL
  control.plane.listener.name = CONTROLLER
  ```

  On startup, the broker will start listening on "192.1.1.8:9094" with security protocol "SSL".
  On controller side, when it discovers a broker's published endpoints through zookeeper, it will use the control.plane.listener.name to find the endpoint, which it will use to establish connection to the broker.
  For example, if the broker's published endpoints on zookeeper are :

  在启动时，代理将开始使用安全协议` SSL`侦听` 192.1.1.8:9094`。在控制器端，当它通过Zookeeper发现代理发布的终结点时，它将使用control.plane.listener.name查找终结点，它将用于建立与代理的连接。例如，如果代理在zookeeper上发布的端点为：·

  ```
  "endpoints" : ["INTERNAL://broker1.example.com:9092","EXTERNAL://broker1.example.com:9093","CONTROLLER://broker1.example.com:9094"]
  
  and the controller's config is :
  控制器的配置为：
  listener.security.protocol.map = INTERNAL:PLAINTEXT, EXTERNAL:SSL, CONTROLLER:SSL
  control.plane.listener.name = CONTROLLER
  
  then controller will use "broker1.example.com:9094" with security protocol "SSL" to connect to the broker.
  If not explicitly configured, the default value will be null and there will be no dedicated endpoints for controller connections.
  然后控制器将使用带有安全协议` SSL`的` broker1.example.com:9094`来连接到代理。如果未明确配置，则默认值将为null，并且将没有用于控制器连接的专用端点。
  ```

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [delete.topic.enable](http://kafka.apache.org/documentation/#delete.topic.enable)

  Enables delete topic. Delete topic through the admin tool will have no effect if this config is turned off

  启用删除主题。如果关闭此配置，则通过管理工具删除主题将无效。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [host.name](http://kafka.apache.org/documentation/#host.name)

  DEPRECATED: only used when `listeners` is not set. Use `listeners` instead.
  hostname of broker. If this is set, it will only bind to this address. If this is not set, it will bind to all interfaces

    已弃用：仅在未设置`监听器`时使用。改用`listeners`。

  代理的主机名。如果设置此选项，它将仅绑定到该地址。如果未设置，它将绑定到所有接口

|         Type: | string    |
| ------------: | --------- |
|      Default: | ""        |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [leader.imbalance.check.interval.seconds](http://kafka.apache.org/documentation/#leader.imbalance.check.interval.seconds)

  The frequency with which the partition rebalance check is triggered by the controller

  控制器触发分区重新平衡检查的频率

|         Type: | long      |
| ------------: | --------- |
|      Default: | 300       |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [leader.imbalance.per.broker.percentage](http://kafka.apache.org/documentation/#leader.imbalance.per.broker.percentage)

  The ratio of leader imbalance allowed per broker. The controller would trigger a leader balance if it goes above this value per broker. The value is specified in percentage.

  每个代理允许的领导者失衡比率。如果控制器超出每个代理的该值，则它将触发领导者余额。该值以百分比指定。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 10        |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [listeners](http://kafka.apache.org/documentation/#listeners)

  Listener List - Comma-separated list of URIs we will listen on and the listener names. If the listener name is not a security protocol, listener.security.protocol.map must also be set.

    侦听器列表-我们将在其上侦听的URI的逗号分隔列表和侦听器名称。如果侦听器名称不是安全协议，则还必须设置listener.security.protocol.map。

  Specify hostname as 0.0.0.0 to bind to all interfaces.

    将主机名指定为0.0.0.0以绑定到所有接口。

  Leave hostname empty to bind to default interface.

  将主机名保留为空以绑定到默认接口。

  Examples of legal listener lists:合法侦听器列表的示例：
  PLAINTEXT://myhost:9092,SSL://:9091
  CLIENT://0.0.0.0:9092,REPLICATION://localhost:9093

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | high       |
|  Update Mode: | per-broker |

#### [log.dir](http://kafka.apache.org/documentation/#log.dir)

  The directory in which the log data is kept (supplemental for log.dirs property)

    保留日志数据的目录（log.dirs属性的补充）

|         Type: | string          |
| ------------: | --------------- |
|      Default: | /tmp/kafka-logs |
| Valid Values: |                 |
|   Importance: | high            |
|  Update Mode: | read-only       |

#### [log.dirs](http://kafka.apache.org/documentation/#log.dirs)

  The directories in which the log data is kept. If not set, the value in log.dir is used

    保留日志数据的目录。如果未设置，则使用log.dir中的值

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [log.flush.interval.messages](http://kafka.apache.org/documentation/#log.flush.interval.messages)

  The number of messages accumulated on a log partition before messages are flushed to disk

  将消息刷新到磁盘之前，在日志分区上累积的消息数

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: | [1,...]             |
|   Importance: | high                |
|  Update Mode: | cluster-wide        |

#### [log.flush.interval.ms](http://kafka.apache.org/documentation/#log.flush.interval.ms)

  The maximum time in ms that a message in any topic is kept in memory before flushed to disk. If not set, the value in log.flush.scheduler.interval.ms is used

    刷新到磁盘之前，任何主题中的消息在内存中保留的最长时间（以毫秒为单位）。如果未设置，则使用log.flush.scheduler.interval.ms中的值

|         Type: | long         |
| ------------: | ------------ |
|      Default: | null         |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [log.flush.offset.checkpoint.interval.ms](http://kafka.apache.org/documentation/#log.flush.offset.checkpoint.interval.ms)

  The frequency with which we update the persistent record of the last flush which acts as the log recovery point

    我们更新上次刷新的持久记录的频率，该刷新作为日志恢复点

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | high             |
|  Update Mode: | read-only        |

#### [log.flush.scheduler.interval.ms](http://kafka.apache.org/documentation/#log.flush.scheduler.interval.ms)

  The frequency in ms that the log flusher checks whether any log needs to be flushed to disk

  日志刷新程序检查是否需要将任何日志刷新到磁盘的频率（毫秒）

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: |                     |
|   Importance: | high                |
|  Update Mode: | read-only           |

#### [log.flush.start.offset.checkpoint.interval.ms](http://kafka.apache.org/documentation/#log.flush.start.offset.checkpoint.interval.ms)

  The frequency with which we update the persistent record of log start offset

    更新日志开始偏移的持久记录的频率

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | high             |
|  Update Mode: | read-only        |

#### [log.retention.bytes](http://kafka.apache.org/documentation/#log.retention.bytes)

  The maximum size of the log before deleting it

  删除日志前的最大日志大小

|         Type: | long         |
| ------------: | ------------ |
|      Default: | -1           |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [log.retention.hours](http://kafka.apache.org/documentation/#log.retention.hours)

  The number of hours to keep a log file before deleting it (in hours), tertiary to log.retention.ms property

    删除日志文件之前保留日志文件的小时数（小时），与log.retention.ms属性对应。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 168       |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [log.retention.minutes](http://kafka.apache.org/documentation/#log.retention.minutes)

  The number of minutes to keep a log file before deleting it (in minutes), secondary to log.retention.ms property. If not set, the value in log.retention.hours is used

    保留日志文件之前删除日志文件的分钟数（以分钟为单位），仅次于log.retention.ms属性。如果未设置，则使用log.retention.hours中的值

|         Type: | int       |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [log.retention.ms](http://kafka.apache.org/documentation/#log.retention.ms)

  The number of milliseconds to keep a log file before deleting it (in milliseconds), If not set, the value in log.retention.minutes is used. If set to -1, no time limit is applied.

    删除日志文件之前保留日志文件的毫秒数（以毫秒为单位）。如果未设置，则使用log.retention.minutes中的值。如果设置为-1，则不应用时间限制。

|         Type: | long         |
| ------------: | ------------ |
|      Default: | null         |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [log.roll.hours](http://kafka.apache.org/documentation/#log.roll.hours)

  The maximum time before a new log segment is rolled out (in hours), secondary to log.roll.ms property

    新日志段推出之前的最长时间（以小时为单位），仅次于log.roll.ms属性

|         Type: | int       |
| ------------: | --------- |
|      Default: | 168       |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

- 

#### [log.roll.jitter.hours](http://kafka.apache.org/documentation/#log.roll.jitter.hours)

  The maximum jitter to subtract from logRollTimeMillis (in hours), secondary to log.roll.jitter.ms property

    从logRollTimeMillis中减去的最大抖动（以小时为单位），仅次于log.roll.jitter.ms属性

|         Type: | int       |
| ------------: | --------- |
|      Default: | 0         |
| Valid Values: | [0,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [log.roll.jitter.ms](http://kafka.apache.org/documentation/#log.roll.jitter.ms)

  The maximum jitter to subtract from logRollTimeMillis (in milliseconds). If not set, the value in log.roll.jitter.hours is used

    从logRollTimeMillis中减去的最大抖动（以毫秒为单位）。如果未设置，则使用log.roll.jitter.hours中的值

|         Type: | long         |
| ------------: | ------------ |
|      Default: | null         |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [log.roll.ms](http://kafka.apache.org/documentation/#log.roll.ms)

  The maximum time before a new log segment is rolled out (in milliseconds). If not set, the value in log.roll.hours is used

    推出新的日志段之前的最长时间（以毫秒为单位）。如果未设置，则使用log.roll.hours中的值

|         Type: | long         |
| ------------: | ------------ |
|      Default: | null         |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [log.segment.bytes](http://kafka.apache.org/documentation/#log.segment.bytes)

  The maximum size of a single log file

  单个日志文件的最大大小

|         Type: | int                     |
| ------------: | ----------------------- |
|      Default: | 1073741824 (1 gibibyte) |
| Valid Values: | [14,...]                |
|   Importance: | high                    |
|  Update Mode: | cluster-wide            |

#### [log.segment.delete.delay.ms](http://kafka.apache.org/documentation/#log.segment.delete.delay.ms)

  The amount of time to wait before deleting a file from the filesystem

  从文件系统删除文件之前要等待的时间

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | high             |
|  Update Mode: | cluster-wide     |

#### [message.max.bytes](http://kafka.apache.org/documentation/#message.max.bytes)

  The largest record batch size allowed by Kafka (after compression if compression is enabled). If this is increased and there are consumers older than 0.10.2, the consumers' fetch size must also be increased so that they can fetch record batches this large. In the latest message format version, records are always grouped into batches for efficiency. In previous message format versions, uncompressed records are not grouped into batches and this limit only applies to a single record in that case.This can be set per topic with the topic level `max.message.bytes` config.

    Kafka允许的最大记录批处理大小（如果启用了压缩，则压缩后）。如果增加了此数量，并且某些消费者的年龄大于0.10.2，则还必须增加消费者的获取大小，以便他们可以获取如此大的记录批次。在最新的消息格式版本中，为了提高效率，总是将记录分组。在以前的消息格式版本中，未压缩的记录不会分组，并且在这种情况下，此限制仅适用于单个记录。可以使用主题级别` max.message.bytes`配置对每个主题进行设置。

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 1048588      |
| Valid Values: | [0,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [min.insync.replicas](http://kafka.apache.org/documentation/#min.insync.replicas)

  When a producer sets acks to "all" (or "-1"), min.insync.replicas specifies the minimum number of replicas that must acknowledge a write for the write to be considered successful. If this minimum cannot be met, then the producer will raise an exception (either NotEnoughReplicas or NotEnoughReplicasAfterAppend).

    当生产者将acks设置为` all`（或` -1`）时，min.insync.replicas指定必须确认写入才能使成功写入的最小副本数。如果不能满足此最小值，则生产者将引发异常（NotEnoughReplicas或NotEnoughReplicasAfterAppend）。

  When used together, min.insync.replicas and acks allow you to enforce greater durability guarantees. A typical scenario would be to create a topic with a replication factor of 3, set min.insync.replicas to 2, and produce with acks of "all". This will ensure that the producer raises an exception if a majority of replicas do not receive a write.

    一起使用时，min.insync.replicas和acks可使您实施更大的耐用性保证。典型的情况是创建一个复制因子为3的主题，将min.insync.replicas设置为2，并生产者设为` all`。如果大多数副本未收到写入，这将确保生产者引发异常。

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 1            |
| Valid Values: | [1,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [num.io.threads](http://kafka.apache.org/documentation/#num.io.threads)

  The number of threads that the server uses for processing requests, which may include disk I/O

    服务器用于处理请求的线程数，其中可能包括磁盘I / O

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 8            |
| Valid Values: | [1,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [num.network.threads](http://kafka.apache.org/documentation/#num.network.threads)

  The number of threads that the server uses for receiving requests from the network and sending responses to the network

  服务器用于接收来自网络的请求并向网络发送响应的线程数

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 3            |
| Valid Values: | [1,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [num.recovery.threads.per.data.dir](http://kafka.apache.org/documentation/#num.recovery.threads.per.data.dir)

  The number of threads per data directory to be used for log recovery at startup and flushing at shutdown

  每个数据目录在启动时用于日志恢复以及在关机时用于刷新的线程数

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 1            |
| Valid Values: | [1,...]      |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [num.replica.alter.log.dirs.threads](http://kafka.apache.org/documentation/#num.replica.alter.log.dirs.threads)

  The number of threads that can move replicas between log directories, which may include disk I/O

    可以在日志目录之间移动副本的线程数，其中可能包括磁盘I / O

|         Type: | int       |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [num.replica.fetchers](http://kafka.apache.org/documentation/#num.replica.fetchers)

  Number of fetcher threads used to replicate messages from a source broker. Increasing this value can increase the degree of I/O parallelism in the follower broker.

    用于复制来自源代理的消息的访存线程的数量。增大此值可以增加关注代理中的I / O并行度。

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 1            |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [offset.metadata.max.bytes](http://kafka.apache.org/documentation/#offset.metadata.max.bytes)

  The maximum size for a metadata entry associated with an offset commit

  与偏移量提交关联的元数据条目的最大大小

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 4096 (4 kibibytes) |
| Valid Values: |                    |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [offsets.commit.required.acks](http://kafka.apache.org/documentation/#offsets.commit.required.acks)

  The required acks before the commit can be accepted. In general, the default (-1) should not be overridden

    可以接受提交之前所需的确认。通常，不应覆盖默认值（-1）

|         Type: | short     |
| ------------: | --------- |
|      Default: | -1        |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.commit.timeout.ms](http://kafka.apache.org/documentation/#offsets.commit.timeout.ms)

  Offset commit will be delayed until all replicas for the offsets topic receive the commit or this timeout is reached. This is similar to the producer request timeout.

  偏移提交将被延迟，直到偏移主题的所有副本都收到提交或达到此超时为止。这类似于生产者请求超时。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: | [1,...]          |
|   Importance: | high             |
|  Update Mode: | read-only        |

#### [offsets.load.buffer.size](http://kafka.apache.org/documentation/#offsets.load.buffer.size)

  Batch size for reading from the offsets segments when loading offsets into the cache (soft-limit, overridden if records are too large).

  将偏移量加载到缓存中时从偏移量段读取的批处理大小（软限制，如果记录太大，则覆盖）。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 5242880   |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.retention.check.interval.ms](http://kafka.apache.org/documentation/#offsets.retention.check.interval.ms)

  Frequency at which to check for stale offsets

  检查陈旧偏移的频率

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 600000 (10 minutes) |
| Valid Values: | [1,...]             |
|   Importance: | high                |
|  Update Mode: | read-only           |

#### [offsets.retention.minutes](http://kafka.apache.org/documentation/#offsets.retention.minutes)

  After a consumer group loses all its consumers (i.e. becomes empty) its offsets will be kept for this retention period before getting discarded. For standalone consumers (using manual assignment), offsets will be expired after the time of last commit plus this retention period.

  消费群体失去所有消费群体（即变空）后，其抵销将在此保留期内保留，直到被丢弃。对于独立使用者（使用手动分配），偏移量将在上次提交时间加上此保留期后过期。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 10080     |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.topic.compression.codec](http://kafka.apache.org/documentation/#offsets.topic.compression.codec)

  Compression codec for the offsets topic - compression may be used to achieve "atomic" commits

    偏移主题的压缩编解码器-压缩可用于实现`atomic`提交

|         Type: | int       |
| ------------: | --------- |
|      Default: | 0         |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.topic.num.partitions](http://kafka.apache.org/documentation/#offsets.topic.num.partitions)

  The number of partitions for the offset commit topic (should not change after deployment)

  偏移提交主题的分区数（部署后不应更改）

|         Type: | int       |
| ------------: | --------- |
|      Default: | 50        |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.topic.replication.factor](http://kafka.apache.org/documentation/#offsets.topic.replication.factor)

  The replication factor for the offsets topic (set higher to ensure availability). Internal topic creation will fail until the cluster size meets this replication factor requirement.

  偏移量主题的复制因子（设置较高以确保可用性）。内部主题创建将失败，直到群集大小满足此复制因子要求为止。

|         Type: | short     |
| ------------: | --------- |
|      Default: | 3         |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [offsets.topic.segment.bytes](http://kafka.apache.org/documentation/#offsets.topic.segment.bytes)

  The offsets topic segment bytes should be kept relatively small in order to facilitate faster log compaction and cache loads

  偏移量主题段字节应保持相对较小，以促进更快的日志压缩和缓存加载

|         Type: | int                       |
| ------------: | ------------------------- |
|      Default: | 104857600 (100 mebibytes) |
| Valid Values: | [1,...]                   |
|   Importance: | high                      |
|  Update Mode: | read-only                 |

#### [port](http://kafka.apache.org/documentation/#port)

  DEPRECATED: only used when `listeners` is not set. Use `listeners` instead.
  the port to listen and accept connections on

  已弃用：仅在未设置`监听器`时使用。改用`listeners`。
    用于侦听并接受连接的端口

|         Type: | int       |
| ------------: | --------- |
|      Default: | 9092      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [queued.max.requests](http://kafka.apache.org/documentation/#queued.max.requests)

  The number of queued requests allowed for data-plane, before blocking the network threads

  阻塞网络线程之前允许数据平面排队的请求数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 500       |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [quota.consumer.default](http://kafka.apache.org/documentation/#quota.consumer.default)

  DEPRECATED: Used only when dynamic default quotas are not configured for or in Zookeeper. Any consumer distinguished by clientId/consumer group will get throttled if it fetches more bytes than this value per-second

    已弃用：仅当未为Zookeeper或在Zookeeper中配置动态默认配额时使用。如果clientId / consumer组所区分的任何使用者每秒获取的字节数超过此值，则将受到限制

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: | [1,...]             |
|   Importance: | high                |
|  Update Mode: | read-only           |

#### [quota.producer.default](http://kafka.apache.org/documentation/#quota.producer.default)

  DEPRECATED: Used only when dynamic default quotas are not configured for , or in Zookeeper. Any producer distinguished by clientId will get throttled if it produces more bytes than this value per-second

  不推荐使用：仅当未为或在Zookeeper中配置动态默认配额时使用。如果由clientId区分的任何生产者每秒产生的字节数超过此值，则将受到限制

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: | [1,...]             |
|   Importance: | high                |
|  Update Mode: | read-only           |

#### [replica.fetch.min.bytes](http://kafka.apache.org/documentation/#replica.fetch.min.bytes)

  Minimum bytes expected for each fetch response. If not enough bytes, wait up to replicaMaxWaitTimeMs

  每个提取响应的最小字节数。如果没有足够的字节，请等待直到copyMaxMaxWaitTimeMs

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [replica.fetch.wait.max.ms](http://kafka.apache.org/documentation/#replica.fetch.wait.max.ms)

  max wait time for each fetcher request issued by follower replicas. This value should always be less than the replica.lag.time.max.ms at all times to prevent frequent shrinking of ISR for low throughput topics

    追随者副本发出的每个提取器请求的最大等待时间。此值应始终始终小于复制副本replica.lag.time.max.ms，以防止由于低吞吐量主题而导致ISR频繁缩小

|         Type: | int       |
| ------------: | --------- |
|      Default: | 500       |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [replica.high.watermark.checkpoint.interval.ms](http://kafka.apache.org/documentation/#replica.high.watermark.checkpoint.interval.ms)

  The frequency with which the high watermark is saved out to disk

  高水印保存到磁盘的频率

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: |                  |
|   Importance: | high             |
|  Update Mode: | read-only        |

#### [replica.lag.time.max.ms](http://kafka.apache.org/documentation/#replica.lag.time.max.ms)

  If a follower hasn't sent any fetch requests or hasn't consumed up to the leaders log end offset for at least this time, the leader will remove the follower from isr

  如果follower没有发送任何fetch请求，或者至少在这一次没有消耗到leader的日志结束偏移量，leader将从isr中删除follower

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: |                    |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [replica.socket.receive.buffer.bytes](http://kafka.apache.org/documentation/#replica.socket.receive.buffer.bytes)

  The socket receive buffer for network requests

  套接字接收网络请求的缓冲区

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 65536 (64 kibibytes) |
| Valid Values: |                      |
|   Importance: | high                 |
|  Update Mode: | read-only            |

#### [replica.socket.timeout.ms](http://kafka.apache.org/documentation/#replica.socket.timeout.ms)

  The socket timeout for network requests. Its value should be at least replica.fetch.wait.max.ms

    网络请求的套接字超时。它的值至少应为replica.fetch.wait.max.ms

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: |                    |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.

  该配置控制客户端等待请求响应的最长时间。如果超时之前仍未收到响应，则客户端将在必要时重新发送请求，如果重试已用尽，则会使请求失败。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: |                    |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [socket.receive.buffer.bytes](http://kafka.apache.org/documentation/#socket.receive.buffer.bytes)

  The SO_RCVBUF buffer of the socket server sockets. If the value is -1, the OS default will be used.

    套接字服务器套接字的SO_RCVBUF缓冲区。如果值为-1，则将使用操作系统默认值。

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 102400 (100 kibibytes) |
| Valid Values: |                        |
|   Importance: | high                   |
|  Update Mode: | read-only              |

#### [socket.request.max.bytes](http://kafka.apache.org/documentation/#socket.request.max.bytes)

  The maximum number of bytes in a socket request

  套接字请求中的最大字节数

|         Type: | int                       |
| ------------: | ------------------------- |
|      Default: | 104857600 (100 mebibytes) |
| Valid Values: | [1,...]                   |
|   Importance: | high                      |
|  Update Mode: | read-only                 |

#### [socket.send.buffer.bytes](http://kafka.apache.org/documentation/#socket.send.buffer.bytes)

  The SO_SNDBUF buffer of the socket server sockets. If the value is -1, the OS default will be used.

    套接字服务器套接字的SO_SNDBUF缓冲区。如果值为-1，则将使用操作系统默认值。

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 102400 (100 kibibytes) |
| Valid Values: |                        |
|   Importance: | high                   |
|  Update Mode: | read-only              |

#### [transaction.max.timeout.ms](http://kafka.apache.org/documentation/#transaction.max.timeout.ms)

  The maximum allowed timeout for transactions. If a client’s requested transaction time exceed this, then the broker will return an error in InitProducerIdRequest. This prevents a client from too large of a timeout, which can stall consumers reading from topics included in the transaction.

  事务允许的最大超时。如果客户请求的交易时间超过此时间，则代理将在InitProducerIdRequest中返回错误。这样可以防止客户的超时过大，否则可能会使消费者无法阅读交易中包含的主题。

|         Type: | int                 |
| ------------: | ------------------- |
|      Default: | 900000 (15 minutes) |
| Valid Values: | [1,...]             |
|   Importance: | high                |
|  Update Mode: | read-only           |

#### [transaction.state.log.load.buffer.size](http://kafka.apache.org/documentation/#transaction.state.log.load.buffer.size)

  Batch size for reading from the transaction log segments when loading producer ids and transactions into the cache (soft-limit, overridden if records are too large).

  将生产者ID和事务加载到缓存中时从事务日志段读取的批处理大小（软限制，如果记录太大则覆盖）。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 5242880   |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [transaction.state.log.min.isr](http://kafka.apache.org/documentation/#transaction.state.log.min.isr)

  Overridden min.insync.replicas config for the transaction topic.

    事务主题已覆盖min.insync.replicas配置。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 2         |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [transaction.state.log.num.partitions](http://kafka.apache.org/documentation/#transaction.state.log.num.partitions)

  The number of partitions for the transaction topic (should not change after deployment).

  事务主题的分区数（部署后不应更改）。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 50        |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [transaction.state.log.replication.factor](http://kafka.apache.org/documentation/#transaction.state.log.replication.factor)

  The replication factor for the transaction topic (set higher to ensure availability). Internal topic creation will fail until the cluster size meets this replication factor requirement.

  事务主题的复制因子（设置较高以确保可用性）。内部主题创建将失败，直到群集大小满足此复制因子要求为止。

|         Type: | short     |
| ------------: | --------- |
|      Default: | 3         |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [transaction.state.log.segment.bytes](http://kafka.apache.org/documentation/#transaction.state.log.segment.bytes)

  The transaction topic segment bytes should be kept relatively small in order to facilitate faster log compaction and cache loads

  事务主题段字节应保持相对较小，以促进更快的日志压缩和缓存加载

|         Type: | int                       |
| ------------: | ------------------------- |
|      Default: | 104857600 (100 mebibytes) |
| Valid Values: | [1,...]                   |
|   Importance: | high                      |
|  Update Mode: | read-only                 |

#### [transactional.id.expiration.ms](http://kafka.apache.org/documentation/#transactional.id.expiration.ms)

  The time in ms that the transaction coordinator will wait without receiving any transaction status updates for the current transaction before expiring its transactional id. This setting also influences producer id expiration - producer ids are expired once this time has elapsed after the last write with the given producer id. Note that producer ids may expire sooner if the last write from the producer id is deleted due to the topic's retention settings.

    事务协调器等待的时间（以毫秒为单位），直到该事务的事务ID到期之前，该事务协调器将不接收当前事务的任何事务状态更新。此设置还会影响生产者ID的到期时间-在使用给定生产者ID的最后一次写入之后，经过此时间，生产者ID就会过期。请注意，由于主题的保留设置，如果删除生产者ID的最后一次写操作，生产者ID可能会更快过期。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 604800000 (7 days) |
| Valid Values: | [1,...]            |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [unclean.leader.election.enable](http://kafka.apache.org/documentation/#unclean.leader.election.enable)

  Indicates whether to enable replicas not in the ISR set to be elected as leader as a last resort, even though doing so may result in data loss

  指示是否启用不在ISR集中的副本以选作领导者，即使这样做可能会导致数据丢失

|         Type: | boolean      |
| ------------: | ------------ |
|      Default: | false        |
| Valid Values: |              |
|   Importance: | high         |
|  Update Mode: | cluster-wide |

#### [zookeeper.connection.timeout.ms](http://kafka.apache.org/documentation/#zookeeper.connection.timeout.ms)

  The max time that the client waits to establish a connection to zookeeper. If not set, the value in zookeeper.session.timeout.ms is used

    客户端等待与Zookeeper建立连接的最长时间。如果未设置，则使用zookeeper.session.timeout.ms中的值

|         Type: | int       |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [zookeeper.max.in.flight.requests](http://kafka.apache.org/documentation/#zookeeper.max.in.flight.requests)

  The maximum number of unacknowledged requests the client will send to Zookeeper before blocking.

  客户端在阻止之前将发送给Zookeeper的未确认请求的最大数量。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 10        |
| Valid Values: | [1,...]   |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [zookeeper.session.timeout.ms](http://kafka.apache.org/documentation/#zookeeper.session.timeout.ms)

  Zookeeper session timeout

  Zookeeper会话超时

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 18000 (18 seconds) |
| Valid Values: |                    |
|   Importance: | high               |
|  Update Mode: | read-only          |

#### [zookeeper.set.acl](http://kafka.apache.org/documentation/#zookeeper.set.acl)

  Set client to use secure ACLs

  将客户端设置为使用安全ACL

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | false     |
| Valid Values: |           |
|   Importance: | high      |
|  Update Mode: | read-only |

#### [broker.id.generation.enable](http://kafka.apache.org/documentation/#broker.id.generation.enable)

  Enable automatic broker id generation on the server. When enabled the value configured for reserved.broker.max.id should be reviewed.

    在服务器上启用自动代理ID生成。启用后，应检查为reserved.broker.max.id配置的值。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [broker.rack](http://kafka.apache.org/documentation/#broker.rack)

  Rack of the broker. This will be used in rack aware replication assignment for fault tolerance. Examples: `RACK1`, `us-east-1d`

  代理的架子。这将用于机架感知复制分配中以实现容错功能。例如：`RACK1`，`us-east-1d`

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Idle connections timeout: the server socket processor threads close the connections that idle more than this

  空闲连接超时：服务器套接字处理器线程关闭的空闲连接超过此数量

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 600000 (10 minutes) |
| Valid Values: |                     |
|   Importance: | medium              |
|  Update Mode: | read-only           |

#### [connections.max.reauth.ms](http://kafka.apache.org/documentation/#connections.max.reauth.ms)

  When explicitly set to a positive number (the default is 0, not a positive number), a session lifetime that will not exceed the configured value will be communicated to v2.2.0 or later clients when they authenticate. The broker will disconnect any such connection that is not re-authenticated within the session lifetime and that is then subsequently used for any purpose other than re-authentication. Configuration names can optionally be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.oauthbearer.connections.max.reauth.ms=3600000

    当显式设置为正数（默认为0，而不是正数）时，会话寿命将不超过配置的值，并在进行身份验证时与v2.2.0或更高版本的客户端进行通信。代理将断开在会话生存期内未重新进行身份验证的任何此类连接，然后将该连接随后用于除重新身份验证以外的任何目的。可以选择为配置名称加上小写的侦听器前缀和SASL机制名称。例如，listener.name.sasl_ssl.oauthbearer.connections.max.reauth.ms = 3600000

|         Type: | long      |
| ------------: | --------- |
|      Default: | 0         |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [controlled.shutdown.enable](http://kafka.apache.org/documentation/#controlled.shutdown.enable)

  Enable controlled shutdown of the server

  启用服务器的受控关闭

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [controlled.shutdown.max.retries](http://kafka.apache.org/documentation/#controlled.shutdown.max.retries)

  Controlled shutdown can fail for multiple reasons. This determines the number of retries when such failure happens

  受控关机可能由于多种原因而失败。这确定了发生此类故障时的重试次数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 3         |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [controlled.shutdown.retry.backoff.ms](http://kafka.apache.org/documentation/#controlled.shutdown.retry.backoff.ms)

  Before each retry, the system needs time to recover from the state that caused the previous failure (Controller fail over, replica lag etc). This config determines the amount of time to wait before retrying.

  在每次重试之前，系统需要时间从引起先前故障的状态中恢复（控制器故障转移，副本滞后等）。此配置确定重试之前要等待的时间。

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: |                  |
|   Importance: | medium           |
|  Update Mode: | read-only        |

#### [controller.socket.timeout.ms](http://kafka.apache.org/documentation/#controller.socket.timeout.ms)

  The socket timeout for controller-to-broker channels

  控制器到代理通道的套接字超时

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: |                    |
|   Importance: | medium             |
|  Update Mode: | read-only          |

#### [default.replication.factor](http://kafka.apache.org/documentation/#default.replication.factor)

  default replication factors for automatically created topics

  自动创建的主题的默认复制因子

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [delegation.token.expiry.time.ms](http://kafka.apache.org/documentation/#delegation.token.expiry.time.ms)

  The token validity time in miliseconds before the token needs to be renewed. Default value 1 day.

  需要更新令牌之前的令牌有效时间（以毫秒为单位）。默认值1天。

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 86400000 (1 day) |
| Valid Values: | [1,...]          |
|   Importance: | medium           |
|  Update Mode: | read-only        |

#### [delegation.token.master.key](http://kafka.apache.org/documentation/#delegation.token.master.key)

  Master/secret key to generate and verify delegation tokens. Same key must be configured across all the brokers. If the key is not set or set to empty string, brokers will disable the delegation token support.

  主密钥/密钥，用于生成和验证委派令牌。必须在所有代理之间配置相同的密钥。如果未设置密钥或将密钥设置为空字符串，则代理将禁用委托令牌支持

|         Type: | password  |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [delegation.token.max.lifetime.ms](http://kafka.apache.org/documentation/#delegation.token.max.lifetime.ms)

  The token has a maximum lifetime beyond which it cannot be renewed anymore. Default value 7 days.

  令牌具有最大使用寿命，超过该期限将无法续签。默认值7天。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 604800000 (7 days) |
| Valid Values: | [1,...]            |
|   Importance: | medium             |
|  Update Mode: | read-only          |

#### [delete.records.purgatory.purge.interval.requests](http://kafka.apache.org/documentation/#delete.records.purgatory.purge.interval.requests)

  The purge interval (in number of requests) of the delete records request purgatory

  删除记录请求炼狱的清除时间间隔（以请求数为单位）

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [fetch.max.bytes](http://kafka.apache.org/documentation/#fetch.max.bytes)

  The maximum number of bytes we will return for a fetch request. Must be at least 1024.

  我们将为获取请求返回的最大字节数。必须至少为1024。

|         Type: | int                     |
| ------------: | ----------------------- |
|      Default: | 57671680 (55 mebibytes) |
| Valid Values: | [1024,...]              |
|   Importance: | medium                  |
|  Update Mode: | read-only               |

#### [fetch.purgatory.purge.interval.requests](http://kafka.apache.org/documentation/#fetch.purgatory.purge.interval.requests)

  The purge interval (in number of requests) of the fetch request purgatory

  提取请求炼狱的清除间隔（以请求数为单位）

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1000      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [group.initial.rebalance.delay.ms](http://kafka.apache.org/documentation/#group.initial.rebalance.delay.ms)

  The amount of time the group coordinator will wait for more consumers to join a new group before performing the first rebalance. A longer delay means potentially fewer rebalances, but increases the time until processing begins.

  组协调员在执行第一次重新平衡之前将等待更多消费者加入新组的时间。较长的延迟可能意味着较少的重新平衡，但会增加开始处理之前的时间。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 3000 (3 seconds) |
| Valid Values: |                  |
|   Importance: | medium           |
|  Update Mode: | read-only        |

#### [group.max.session.timeout.ms](http://kafka.apache.org/documentation/#group.max.session.timeout.ms)

  The maximum allowed session timeout for registered consumers. Longer timeouts give consumers more time to process messages in between heartbeats at the cost of a longer time to detect failures.

  注册使用者的最大允许会话超时。较长的超时时间使消费者有更多的时间来处理心跳之间的消息，但要花费较长的时间来检测故障。

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 1800000 (30 minutes) |
| Valid Values: |                      |
|   Importance: | medium               |
|  Update Mode: | read-only            |

#### [group.max.size](http://kafka.apache.org/documentation/#group.max.size)

  The maximum number of consumers that a single consumer group can accommodate.

  单个消费者组可以容纳的最大消费者数量。

|         Type: | int        |
| ------------: | ---------- |
|      Default: | 2147483647 |
| Valid Values: | [1,...]    |
|   Importance: | medium     |
|  Update Mode: | read-only  |

#### [group.min.session.timeout.ms](http://kafka.apache.org/documentation/#group.min.session.timeout.ms)

  The minimum allowed session timeout for registered consumers. Shorter timeouts result in quicker failure detection at the cost of more frequent consumer heartbeating, which can overwhelm broker resources.

  注册使用者的最小允许会话超时。较短的超时可以更快地进行故障检测，但需要付出更频繁的消费者心跳，这可能会使代理资源不堪重负。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 6000 (6 seconds) |
| Valid Values: |                  |
|   Importance: | medium           |
|  Update Mode: | read-only        |

#### [inter.broker.listener.name](http://kafka.apache.org/documentation/#inter.broker.listener.name)

  Name of listener used for communication between brokers. If this is unset, the listener name is defined by security.inter.broker.protocol. It is an error to set this and security.inter.broker.protocol properties at the same time.

    代理之间进行通信所使用的侦听器的名称。如果未设置，则侦听器名称由security.inter.broker.protocol定义。同时设置此属性和security.inter.broker.protocol属性是错误的。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [inter.broker.protocol.version](http://kafka.apache.org/documentation/#inter.broker.protocol.version)

  Specify which version of the inter-broker protocol will be used.
  This is typically bumped after all brokers were upgraded to a new version.
  Example of some valid values are: 0.8.0, 0.8.1, 0.8.1.1, 0.8.2, 0.8.2.0, 0.8.2.1, 0.9.0.0, 0.9.0.1 Check ApiVersion for the full list.

  指定将使用哪个版本的中间经纪人协议。
    在将所有代理升级到新版本后，通常会遇到这种情况。
    一些有效值的示例是：0.8.0、0.8.1、0.8.1.1、0.8.2、0.8.2.0、0.8.2.1、0.9.0.0、0.9.0.1检查ApiVersion的完整列表。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | 2.6-IV0                                                      |
| Valid Values: | [0.8.0, 0.8.1, 0.8.2, 0.9.0, 0.10.0-IV0, 0.10.0-IV1, 0.10.1-IV0, 0.10.1-IV1, 0.10.1-IV2, 0.10.2-IV0, 0.11.0-IV0, 0.11.0-IV1, 0.11.0-IV2, 1.0-IV0, 1.1-IV0, 2.0-IV0, 2.0-IV1, 2.1-IV0, 2.1-IV1, 2.1-IV2, 2.2-IV0, 2.2-IV1, 2.3-IV0, 2.3-IV1, 2.4-IV0, 2.4-IV1, 2.5-IV0, 2.6-IV0] |
|   Importance: | medium                                                       |
|  Update Mode: | read-only                                                    |

#### [log.cleaner.backoff.ms](http://kafka.apache.org/documentation/#log.cleaner.backoff.ms)

  The amount of time to sleep when there are no logs to clean

  没有日志可清理时的睡眠时间

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 15000 (15 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |
|  Update Mode: | cluster-wide       |

#### [log.cleaner.dedupe.buffer.size](http://kafka.apache.org/documentation/#log.cleaner.dedupe.buffer.size)

  The total memory used for log deduplication across all cleaner threads

  所有更干净的线程中用于日志重复数据删除的总内存

|         Type: | long         |
| ------------: | ------------ |
|      Default: | 134217728    |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleaner.delete.retention.ms](http://kafka.apache.org/documentation/#log.cleaner.delete.retention.ms)

  How long are delete records retained?

  删除记录会保留多长时间？

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 86400000 (1 day) |
| Valid Values: |                  |
|   Importance: | medium           |
|  Update Mode: | cluster-wide     |

#### [log.cleaner.enable](http://kafka.apache.org/documentation/#log.cleaner.enable)

  Enable the log cleaner process to run on the server. Should be enabled if using any topics with a cleanup.policy=compact including the internal offsets topic. If disabled those topics will not be compacted and continually grow in size.

    启用日志清除器进程以在服务器上运行。如果使用带有cleanup.policy = compact的任何主题（包括内部偏移量主题），则应启用该选项。如果禁用，这些主题将不会被压缩，并且会不断增长。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | true      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [log.cleaner.io.buffer.load.factor](http://kafka.apache.org/documentation/#log.cleaner.io.buffer.load.factor)

  Log cleaner dedupe buffer load factor. The percentage full the dedupe buffer can become. A higher value will allow more log to be cleaned at once but will lead to more hash collisions

  日志清除程序重复数据删除缓冲区负载系数。重复数据删除缓冲区可以充满的百分比。较高的值将允许立即清除更多日志，但会导致更多哈希冲突

|         Type: | double       |
| ------------: | ------------ |
|      Default: | 0.9          |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleaner.io.buffer.size](http://kafka.apache.org/documentation/#log.cleaner.io.buffer.size)

  The total memory used for log cleaner I/O buffers across all cleaner threads

    所有清理程序线程中用于日志清理程序I / O缓冲区的总内存

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 524288       |
| Valid Values: | [0,...]      |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleaner.io.max.bytes.per.second](http://kafka.apache.org/documentation/#log.cleaner.io.max.bytes.per.second)

  The log cleaner will be throttled so that the sum of its read and write i/o will be less than this value on average

    日志清除器将受到限制，以使其读和写I / O的总和平均小于该值

|         Type: | double                 |
| ------------: | ---------------------- |
|      Default: | 1.7976931348623157E308 |
| Valid Values: |                        |
|   Importance: | medium                 |
|  Update Mode: | cluster-wide           |

#### [log.cleaner.max.compaction.lag.ms](http://kafka.apache.org/documentation/#log.cleaner.max.compaction.lag.ms)

  The maximum time a message will remain ineligible for compaction in the log. Only applicable for logs that are being compacted.

  消息将不符合日志压缩条件的最长时间。仅适用于正在压缩的日志。

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: |                     |
|   Importance: | medium              |
|  Update Mode: | cluster-wide        |

#### [log.cleaner.min.cleanable.ratio](http://kafka.apache.org/documentation/#log.cleaner.min.cleanable.ratio)

  The minimum ratio of dirty log to total log for a log to eligible for cleaning. If the log.cleaner.max.compaction.lag.ms or the log.cleaner.min.compaction.lag.ms configurations are also specified, then the log compactor considers the log eligible for compaction as soon as either: (i) the dirty ratio threshold has been met and the log has had dirty (uncompacted) records for at least the log.cleaner.min.compaction.lag.ms duration, or (ii) if the log has had dirty (uncompacted) records for at most the log.cleaner.max.compaction.lag.ms period.

    符合清除条件的日志的脏日志与总日志的最小比率。如果还指定了log.cleaner.max.compaction.lag.ms或log.cleaner.min.compaction.lag.ms配置，则日志压缩程序将在以下任一情况下立即认为该日志符合压缩条件：（i）已达到脏率阈值，并且日志至少在log.cleaner.min.compaction.lag.ms持续时间内具有脏（未压缩）记录，或者（ii）日志最多具有脏（未压缩）记录log.cleaner.max.compaction.lag.ms周期。

|         Type: | double       |
| ------------: | ------------ |
|      Default: | 0.5          |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleaner.min.compaction.lag.ms](http://kafka.apache.org/documentation/#log.cleaner.min.compaction.lag.ms)

  The minimum time a message will remain uncompacted in the log. Only applicable for logs that are being compacted.

  消息在日志中保持不压缩的最短时间。仅适用于正在压缩的日志。

|         Type: | long         |
| ------------: | ------------ |
|      Default: | 0            |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleaner.threads](http://kafka.apache.org/documentation/#log.cleaner.threads)

  The number of background threads to use for log cleaning

  

  用于日志清理的后台线程数

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 1            |
| Valid Values: | [0,...]      |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.cleanup.policy](http://kafka.apache.org/documentation/#log.cleanup.policy)

  The default cleanup policy for segments beyond the retention window. A comma separated list of valid policies. Valid policies are: "delete" and "compact"

    保留窗口之外的段的默认清除策略。以逗号分隔的有效策略列表。有效策略为：`删除`和`紧凑`

|         Type: | list              |
| ------------: | ----------------- |
|      Default: | delete            |
| Valid Values: | [compact, delete] |
|   Importance: | medium            |
|  Update Mode: | cluster-wide      |

#### [log.index.interval.bytes](http://kafka.apache.org/documentation/#log.index.interval.bytes)

  The interval with which we add an entry to the offset index

  我们向偏移量索引添加条目的时间间隔

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 4096 (4 kibibytes) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |
|  Update Mode: | cluster-wide       |

#### [log.index.size.max.bytes](http://kafka.apache.org/documentation/#log.index.size.max.bytes)

  The maximum size in bytes of the offset index

    偏移索引的最大大小（以字节为单位）

|         Type: | int                     |
| ------------: | ----------------------- |
|      Default: | 10485760 (10 mebibytes) |
| Valid Values: | [4,...]                 |
|   Importance: | medium                  |
|  Update Mode: | cluster-wide            |

#### [log.message.format.version](http://kafka.apache.org/documentation/#log.message.format.version)

  Specify the message format version the broker will use to append messages to the logs. The value should be a valid ApiVersion. Some examples are: 0.8.2, 0.9.0.0, 0.10.0, check ApiVersion for more details. By setting a particular message format version, the user is certifying that all the existing messages on disk are smaller or equal than the specified version. Setting this value incorrectly will cause consumers with older versions to break as they will receive messages with a format that they don't understand.

    指定代理将消息添加到日志时将使用的消息格式版本。该值应为有效的ApiVersion。一些示例是：0.8.2、0.9.0.0、0.10.0，请检查ApiVersion以获取更多详细信息。通过设置特定的消息格式版本，用户可以证明磁盘上的所有现有消息均小于或等于指定的版本。错误地设置此值将导致使用较旧版本的使用者中断，因为他们将收到他们不理解的格式的消息。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | 2.6-IV0                                                      |
| Valid Values: | [0.8.0, 0.8.1, 0.8.2, 0.9.0, 0.10.0-IV0, 0.10.0-IV1, 0.10.1-IV0, 0.10.1-IV1, 0.10.1-IV2, 0.10.2-IV0, 0.11.0-IV0, 0.11.0-IV1, 0.11.0-IV2, 1.0-IV0, 1.1-IV0, 2.0-IV0, 2.0-IV1, 2.1-IV0, 2.1-IV1, 2.1-IV2, 2.2-IV0, 2.2-IV1, 2.3-IV0, 2.3-IV1, 2.4-IV0, 2.4-IV1, 2.5-IV0, 2.6-IV0] |
|   Importance: | medium                                                       |
|  Update Mode: | read-only                                                    |

#### [log.message.timestamp.difference.max.ms](http://kafka.apache.org/documentation/#log.message.timestamp.difference.max.ms)

  The maximum difference allowed between the timestamp when a broker receives a message and the timestamp specified in the message. If log.message.timestamp.type=CreateTime, a message will be rejected if the difference in timestamp exceeds this threshold. This configuration is ignored if log.message.timestamp.type=LogAppendTime.The maximum timestamp difference allowed should be no greater than log.retention.ms to avoid unnecessarily frequent log rolling.

    代理收到消息的时间戳与消息中指定的时间戳之间允许的最大差异。如果log.message.timestamp.type = CreateTime，则时间戳差异超过此阈值时，将拒绝一条消息。如果log.message.timestamp.type = LogAppendTime，则忽略此配置。允许的最大时间戳差异应不大于log.retention.ms，以避免不必要的频繁滚动日志。

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 9223372036854775807 |
| Valid Values: |                     |
|   Importance: | medium              |
|  Update Mode: | cluster-wide        |

#### [log.message.timestamp.type](http://kafka.apache.org/documentation/#log.message.timestamp.type)

  Define whether the timestamp in the message is message create time or log append time. The value should be either `CreateTime` or `LogAppendTime`

    定义消息中的时间戳是消息创建时间还是日志追加时间。该值应为` CreateTime`或` LogAppendTime`

|         Type: | string                      |
| ------------: | --------------------------- |
|      Default: | CreateTime                  |
| Valid Values: | [CreateTime, LogAppendTime] |
|   Importance: | medium                      |
|  Update Mode: | cluster-wide                |

#### [log.preallocate](http://kafka.apache.org/documentation/#log.preallocate)

  Should pre allocate file when create new segment? If you are using Kafka on Windows, you probably need to set it to true.

  创建新段时应该预分配文件吗？如果您在Windows上使用Kafka，则可能需要将其设置为true。

|         Type: | boolean      |
| ------------: | ------------ |
|      Default: | false        |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [log.retention.check.interval.ms](http://kafka.apache.org/documentation/#log.retention.check.interval.ms)

  The frequency in milliseconds that the log cleaner checks whether any log is eligible for deletion

  日志清除器检查是否有资格删除日志的频率（以毫秒为单位）

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [1,...]            |
|   Importance: | medium             |
|  Update Mode: | read-only          |

#### [max.connections](http://kafka.apache.org/documentation/#max.connections)

  The maximum number of connections we allow in the broker at any time. This limit is applied in addition to any per-ip limits configured using max.connections.per.ip. Listener-level limits may also be configured by prefixing the config name with the listener prefix, for example, `listener.name.internal.max.connections`. Broker-wide limit should be configured based on broker capacity while listener limits should be configured based on application requirements. New connections are blocked if either the listener or broker limit is reached. Connections on the inter-broker listener are permitted even if broker-wide limit is reached. The least recently used connection on another listener will be closed in this case.

    我们随时允许在代理中允许的最大连接数。除了使用max.connections.per.ip配置的每个IP限制之外，还应用此限制。侦听器级别的限制也可以通过在配置名称前添加侦听器前缀来配置，例如` listener.name.internal.max.connections`。应基于代理容量配置代理范围的限制，而应根据应用程序要求配置侦听器的限制。如果达到侦听器或代理限制，则新连接被阻止。即使已达到代理范围的限制，也允许在代理中间侦听器上进行连接。在这种情况下，将关闭另一个监听器上最近最少使用的连接。

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 2147483647   |
| Valid Values: | [0,...]      |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [max.connections.per.ip](http://kafka.apache.org/documentation/#max.connections.per.ip)

  The maximum number of connections we allow from each ip address. This can be set to 0 if there are overrides configured using max.connections.per.ip.overrides property. New connections from the ip address are dropped if the limit is reached.

    每个IP地址允许的最大连接数。如果使用max.connections.per.ip.overrides属性配置了替代，则可以将其设置为0。如果达到限制，则将丢弃来自IP地址的新连接。

|         Type: | int          |
| ------------: | ------------ |
|      Default: | 2147483647   |
| Valid Values: | [0,...]      |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [max.connections.per.ip.overrides](http://kafka.apache.org/documentation/#max.connections.per.ip.overrides)

  A comma-separated list of per-ip or hostname overrides to the default maximum number of connections. An example value is "hostName:100,127.0.0.1:200"

    每个IP或主机名的逗号分隔列表将覆盖默认的最大连接数。一个示例值是` hostName：100,127.0.0.1：200`

|         Type: | string       |
| ------------: | ------------ |
|      Default: | ""           |
| Valid Values: |              |
|   Importance: | medium       |
|  Update Mode: | cluster-wide |

#### [max.incremental.fetch.session.cache.slots](http://kafka.apache.org/documentation/#max.incremental.fetch.session.cache.slots)

  The maximum number of incremental fetch sessions that we will maintain.

  我们将维护的最大增量获取会话数。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1000      |
| Valid Values: | [0,...]   |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [num.partitions](http://kafka.apache.org/documentation/#num.partitions)

  The default number of log partitions per topic

  每个主题的默认日志分区数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: | [1,...]   |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [password.encoder.old.secret](http://kafka.apache.org/documentation/#password.encoder.old.secret)

  The old secret that was used for encoding dynamically configured passwords. This is required only when the secret is updated. If specified, all dynamically encoded passwords are decoded using this old secret and re-encoded using password.encoder.secret when broker starts up.

    用于对动态配置的密码进行编码的旧机密。仅在更新机密时才需要。如果指定，则在代理启动时，将使用此旧机密对所有动态编码的密码进行解码，并使用password.encoder.secret重新编码。

|         Type: | password  |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [password.encoder.secret](http://kafka.apache.org/documentation/#password.encoder.secret)

  The secret used for encoding dynamically configured passwords for this broker.

  用于编码此代理的动态配置的密码的机密。

|         Type: | password  |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [principal.builder.class](http://kafka.apache.org/documentation/#principal.builder.class)

  The fully qualified name of a class that implements the KafkaPrincipalBuilder interface, which is used to build the KafkaPrincipal object used during authorization. This config also supports the deprecated PrincipalBuilder interface which was previously used for client authentication over SSL. If no principal builder is defined, the default behavior depends on the security protocol in use. For SSL authentication, the principal will be derived using the rules defined by `ssl.principal.mapping.rules` applied on the distinguished name from the client certificate if one is provided; otherwise, if client authentication is not required, the principal name will be ANONYMOUS. For SASL authentication, the principal will be derived using the rules defined by `sasl.kerberos.principal.to.local.rules` if GSSAPI is in use, and the SASL authentication ID for other mechanisms. For PLAINTEXT, the principal will be ANONYMOUS.

    实现KafkaPrincipalBuilder接口的类的全限定名，该接口用于构建授权期间使用的KafkaPrincipal对象。此配置还支持不赞成使用的PrincipalBuilder接口，该接口以前用于通过SSL进行客户端身份验证。如果未定义任何主体构建器，则默认行为取决于所使用的安全协议。对于SSL身份验证，将使用由ssl.principal.mapping.rules定义的规则派生主体，该规则适用于来自客户端证书的专有名称（如果提供）；否则，如果不需要客户端身份验证，则主体名称将为匿名。对于SASL身份验证，如果正在使用GSSAPI，则将使用` sasl.kerberos.principal.to.local.rules`定义的规则派生主体，并使用其他机制的SASL身份验证ID。对于PLAINTEXT，主体将是匿名的。

|         Type: | class      |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [producer.purgatory.purge.interval.requests](http://kafka.apache.org/documentation/#producer.purgatory.purge.interval.requests)

  The purge interval (in number of requests) of the producer request purgatory

  生产者请求炼狱的清除间隔（以请求数为单位）

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1000      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [queued.max.request.bytes](http://kafka.apache.org/documentation/#queued.max.request.bytes)

  The number of queued bytes allowed before no more requests are read

  读取更多请求之前允许的排队字节数

|         Type: | long      |
| ------------: | --------- |
|      Default: | -1        |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [replica.fetch.backoff.ms](http://kafka.apache.org/documentation/#replica.fetch.backoff.ms)

  The amount of time to sleep when fetch partition error occurs.

  发生提取分区错误时的睡眠时间。

|         Type: | int             |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | medium          |
|  Update Mode: | read-only       |

#### [replica.fetch.max.bytes](http://kafka.apache.org/documentation/#replica.fetch.max.bytes)

  The number of bytes of messages to attempt to fetch for each partition. This is not an absolute maximum, if the first record batch in the first non-empty partition of the fetch is larger than this value, the record batch will still be returned to ensure that progress can be made. The maximum record batch size accepted by the broker is defined via `message.max.bytes` (broker config) or `max.message.bytes` (topic config).

    尝试为每个分区获取的消息的字节数。这不是绝对最大值，如果获取的第一个非空分区中的第一个记录批处理大于此值，那么仍将返回记录批处理以确保进度。代理可接受的最大记录批处理大小是通过` message.max.bytes`（代理配置）或` max.message.bytes`（主题配置）定义的。

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 1048576 (1 mebibyte) |
| Valid Values: | [0,...]              |
|   Importance: | medium               |
|  Update Mode: | read-only            |

#### [replica.fetch.response.max.bytes](http://kafka.apache.org/documentation/#replica.fetch.response.max.bytes)

  Maximum bytes expected for the entire fetch response. Records are fetched in batches, and if the first record batch in the first non-empty partition of the fetch is larger than this value, the record batch will still be returned to ensure that progress can be made. As such, this is not an absolute maximum. The maximum record batch size accepted by the broker is defined via `message.max.bytes` (broker config) or `max.message.bytes` (topic config).

    整个读取响应的最大预期字节数。记录是分批提取的，并且如果所提取的第一个非空分区中的第一个记录批处理大于此值，那么仍将返回记录批处理以确保进度。因此，这不是绝对最大值。代理可接受的最大记录批处理大小是通过` message.max.bytes`（代理配置）或` max.message.bytes`（主题配置）定义的。

|         Type: | int                     |
| ------------: | ----------------------- |
|      Default: | 10485760 (10 mebibytes) |
| Valid Values: | [0,...]                 |
|   Importance: | medium                  |
|  Update Mode: | read-only               |

#### [replica.selector.class](http://kafka.apache.org/documentation/#replica.selector.class)

  The fully qualified class name that implements ReplicaSelector. This is used by the broker to find the preferred read replica. By default, we use an implementation that returns the leader.

  实现ReplicaSelector的完全限定的类名。代理使用它来查找首选的只读副本。默认情况下，我们使用返回领导者的实现。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [reserved.broker.max.id](http://kafka.apache.org/documentation/#reserved.broker.max.id)

  Max number that can be used for a broker.id

    可以用于broker.id的最大数量

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1000      |
| Valid Values: | [0,...]   |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [sasl.client.callback.handler.class](http://kafka.apache.org/documentation/#sasl.client.callback.handler.class)

  The fully qualified name of a SASL client callback handler class that implements the AuthenticateCallbackHandler interface.

  实现AuthenticateCallbackHandler接口的SASL客户端回调处理程序类的全限定名称。

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [sasl.enabled.mechanisms](http://kafka.apache.org/documentation/#sasl.enabled.mechanisms)

  The list of SASL mechanisms enabled in the Kafka server. The list may contain any mechanism for which a security provider is available. Only GSSAPI is enabled by default.

  Kafka服务器中启用的SASL机制列表。该列表可以包含安全提供程序可用的任何机制。默认情况下，仅启用GSSAPI。

|         Type: | list       |
| ------------: | ---------- |
|      Default: | GSSAPI     |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.jaas.config](http://kafka.apache.org/documentation/#sasl.jaas.config)

  JAAS login context parameters for SASL connections in the format used by JAAS configuration files. JAAS configuration file format is described [here](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html). The format for the value is: '`loginModuleClass controlFlag (optionName=optionValue)*;`'. For brokers, the config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=com.example.ScramLoginModule required;

    SASL连接的JAAS登录上下文参数，采用JAAS配置文件使用的格式。 [此处]（http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html）中描述了JAAS配置文件格式。该值的格式为：`loginModuleClass controlFlag（optionName = optionValue）*;``。对于代理，配置必须以小写的前缀加上侦听器前缀和SASL机制名称。例如，需要listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config = com.example.ScramLoginModule；

|         Type: | password   |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.kerberos.kinit.cmd](http://kafka.apache.org/documentation/#sasl.kerberos.kinit.cmd)

  Kerberos kinit command path.

    Kerberos kinit命令路径。

|         Type: | string         |
| ------------: | -------------- |
|      Default: | /usr/bin/kinit |
| Valid Values: |                |
|   Importance: | medium         |
|  Update Mode: | per-broker     |

#### [sasl.kerberos.min.time.before.relogin](http://kafka.apache.org/documentation/#sasl.kerberos.min.time.before.relogin)

  Login thread sleep time between refresh attempts.

  两次刷新尝试之间的登录线程睡眠时间。

|         Type: | long       |
| ------------: | ---------- |
|      Default: | 60000      |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.kerberos.principal.to.local.rules](http://kafka.apache.org/documentation/#sasl.kerberos.principal.to.local.rules)

  A list of rules for mapping from principal names to short names (typically operating system usernames). The rules are evaluated in order and the first rule that matches a principal name is used to map it to a short name. Any later rules in the list are ignored. By default, principal names of the form {username}/{hostname}@{REALM} are mapped to {username}. For more details on the format please see [security authorization and acls](http://kafka.apache.org/documentation/#security_authz). Note that this configuration is ignored if an extension of KafkaPrincipalBuilder is provided by the `principal.builder.class` configuration.

    从主体名称到简称（通常是操作系统用户名）的映射规则列表。将按顺序评估规则，并且使用与主体名称匹配的第一条规则将其映射为简称。列表中以后的所有规则都将被忽略。默认情况下，格式为{username} / {hostname} @ {REALM}的主体名称映射到{username}。有关格式的更多详细信息，请参见[安全授权和ACL]（http://kafka.apache.org/documentation/#security_authz）。请注意，如果`principal.builder.class`配置提供了KafkaPrincipalBuilder的扩展名，则将忽略此配置。

|         Type: | list       |
| ------------: | ---------- |
|      Default: | DEFAULT    |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.kerberos.service.name](http://kafka.apache.org/documentation/#sasl.kerberos.service.name)

  The Kerberos principal name that Kafka runs as. This can be defined either in Kafka's JAAS config or in Kafka's config.

  Kafka运行时使用的Kerberos主体名称。这可以在Kafka的JAAS配置或Kafka的配置中定义。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.kerberos.ticket.renew.jitter](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.jitter)

  Percentage of random jitter added to the renewal time.

  添加到续订时间的随机抖动百分比。

|         Type: | double     |
| ------------: | ---------- |
|      Default: | 0.05       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.kerberos.ticket.renew.window.factor](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.window.factor)

  Login thread will sleep until the specified window factor of time from last refresh to ticket's expiry has been reached, at which time it will try to renew the ticket.

  登录线程将一直休眠，直到达到从上次刷新到票证到期的指定时间窗因素为止，此时它将尝试续订票证。

|         Type: | double     |
| ------------: | ---------- |
|      Default: | 0.8        |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.login.callback.handler.class](http://kafka.apache.org/documentation/#sasl.login.callback.handler.class)

  The fully qualified name of a SASL login callback handler class that implements the AuthenticateCallbackHandler interface. For brokers, login callback handler config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class=com.example.CustomScramLoginCallbackHandler

    实现AuthenticateCallbackHandler接口的SASL登录回调处理程序类的全限定名。对于代理，登录回调处理程序配置必须以侦听器前缀和小写的SASL机制名称作为前缀。例如，listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class = com.example.CustomScramLoginCallbackHandler

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [sasl.login.class](http://kafka.apache.org/documentation/#sasl.login.class)

  The fully qualified name of a class that implements the Login interface. For brokers, login config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.class=com.example.CustomScramLogin

    实现Login接口的类的完全限定名称。对于代理，登录配置必须以侦听器前缀和小写的SASL机制名称作为前缀。例如，listener.name.sasl_ssl.scram-sha-256.sasl.login.class = com.example.CustomScramLogin

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [sasl.login.refresh.buffer.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.buffer.seconds)

  The amount of buffer time before credential expiration to maintain when refreshing a credential, in seconds. If a refresh would otherwise occur closer to expiration than the number of buffer seconds then the refresh will be moved up to maintain as much of the buffer time as possible. Legal values are between 0 and 3600 (1 hour); a default value of 300 (5 minutes) is used if no value is specified. This value and sasl.login.refresh.min.period.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

    刷新凭证时要保留的凭证过期前的缓冲时间（以秒为单位）。如果否则刷新将比缓冲区秒数更接近到期，则刷新将上移以保持尽可能多的缓冲区时间。合法值介于0到3600（1小时）之间；如果未指定任何值，则使用默认值300（5分钟）。如果此值和sasl.login.refresh.min.period.seconds的总和超过凭据的剩余生存期，则两者都将被忽略。当前仅适用于OAUTHBEARER。

|         Type: | short      |
| ------------: | ---------- |
|      Default: | 300        |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.login.refresh.min.period.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.min.period.seconds)

  The desired minimum time for the login refresh thread to wait before refreshing a credential, in seconds. Legal values are between 0 and 900 (15 minutes); a default value of 60 (1 minute) is used if no value is specified. This value and sasl.login.refresh.buffer.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

    登录刷新线程在刷新凭证之前等待的最短时间（以秒为单位）。合法值在0到900之间（15分钟）；如果未指定任何值，则使用默认值60（1分钟）。如果此值和sasl.login.refresh.buffer.seconds的总和超过凭据的剩余生存期，则两者都将被忽略。当前仅适用于OAUTHBEARER。

|         Type: | short      |
| ------------: | ---------- |
|      Default: | 60         |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.login.refresh.window.factor](http://kafka.apache.org/documentation/#sasl.login.refresh.window.factor)

  Login refresh thread will sleep until the specified window factor relative to the credential's lifetime has been reached, at which time it will try to refresh the credential. Legal values are between 0.5 (50%) and 1.0 (100%) inclusive; a default value of 0.8 (80%) is used if no value is specified. Currently applies only to OAUTHBEARER.

    登录刷新线程将休眠，直到达到相对于凭据的生存期的指定窗口因子为止，此时它将尝试刷新凭据。合法值介于0.5（50％）至1.0（100％）之间；如果未指定任何值，则使用默认值0.8（80％）。当前仅适用于OAUTHBEARER。

|         Type: | double     |
| ------------: | ---------- |
|      Default: | 0.8        |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.login.refresh.window.jitter](http://kafka.apache.org/documentation/#sasl.login.refresh.window.jitter)

  The maximum amount of random jitter relative to the credential's lifetime that is added to the login refresh thread's sleep time. Legal values are between 0 and 0.25 (25%) inclusive; a default value of 0.05 (5%) is used if no value is specified. Currently applies only to OAUTHBEARER.

    相对于凭证生存期的最大随机抖动量，添加到登录刷新线程的睡眠时间中。合法值介于0到0.25（25％）之间（含）；如果未指定任何值，则使用默认值0.05（5％）。当前仅适用于OAUTHBEARER。

|         Type: | double     |
| ------------: | ---------- |
|      Default: | 0.05       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.mechanism.inter.broker.protocol](http://kafka.apache.org/documentation/#sasl.mechanism.inter.broker.protocol)

  SASL mechanism used for inter-broker communication. Default is GSSAPI.

  用于经纪人之间通信的SASL机制。默认值为GSSAPI。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | GSSAPI     |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [sasl.server.callback.handler.class](http://kafka.apache.org/documentation/#sasl.server.callback.handler.class)

  The fully qualified name of a SASL server callback handler class that implements the AuthenticateCallbackHandler interface. Server callback handlers must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.plain.sasl.server.callback.handler.class=com.example.CustomPlainCallbackHandler.

    实现AuthenticateCallbackHandler接口的SASL服务器回调处理程序类的全限定名。服务器回调处理程序必须使用小写的侦听器前缀和SASL机制名称作为前缀。例如，listener.name.sasl_ssl.plain.sasl.server.callback.handler.class = com.example.CustomPlainCallbackHandler。

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [security.inter.broker.protocol](http://kafka.apache.org/documentation/#security.inter.broker.protocol)

  Security protocol used to communicate between brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL. It is an error to set this and inter.broker.listener.name properties at the same time.

    用于在代理之间进行通信的安全协议。有效值为：PLAINTEXT，SSL，SASL_PLAINTEXT，SASL_SSL。同时设置此属性和inter.broker.listener.name属性是错误的。

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [ssl.cipher.suites](http://kafka.apache.org/documentation/#ssl.cipher.suites)

  A list of cipher suites. This is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. By default all the available cipher suites are supported.

  

|         Type: | list       |
| ------------: | ---------- |
|      Default: | ""         |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.client.auth](http://kafka.apache.org/documentation/#ssl.client.auth)

  Configures kafka broker to request client authentication. The following settings are common:

  配置kafka代理以请求客户端身份验证。共有以下设置：

  - `ssl.client.auth=required` If set to required client authentication is required.
  - `ssl.client.auth=requested` This means client authentication is optional. unlike requested , if this option is set client can choose not to provide authentication information about itself
  - `ssl.client.auth=none` This means client authentication is not needed.

   -`ssl.client.auth = required`如果设置为必需，则需要客户端认证。
    -`ssl.client.auth = requested`这意味着客户端身份验证是可选的。与请求不同，如果设置了此选项，则客户端可以选择不提供有关其自身的身份验证信息
    -`ssl.client.auth = none`这意味着不需要客户端认证。

|         Type: | string                      |
| ------------: | --------------------------- |
|      Default: | none                        |
| Valid Values: | [required, requested, none] |
|   Importance: | medium                      |
|  Update Mode: | per-broker                  |

#### [ssl.enabled.protocols](http://kafka.apache.org/documentation/#ssl.enabled.protocols)

  The list of protocols enabled for SSL connections. The default is 'TLSv1.2,TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. With the default value for Java 11, clients and servers will prefer TLSv1.3 if both support it and fallback to TLSv1.2 otherwise (assuming both support at least TLSv1.2). This default should be fine for most cases. Also see the config documentation for `ssl.protocol`.

    为SSL连接启用的协议列表。与Java 11或更高版本一起运行时，默认值为` TLSv1.2，TLSv1.3`，否则为` TLSv1.2`。如果使用Java 11的默认值，则如果客户端和服务器均支持TLSv1.3，则它们将优先使用TLSv1.3，否则，将优先使用TLSv1.2（假设两者均至少支持TLSv1.2）。在大多数情况下，此默认值应该很好。另请参阅ssl.protocol的配置文档。

|         Type: | list       |
| ------------: | ---------- |
|      Default: | TLSv1.2    |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.key.password](http://kafka.apache.org/documentation/#ssl.key.password)

  The password of the private key in the key store file. This is optional for client.

  密钥存储文件中私钥的密码。这对于客户端是可选的。

|         Type: | password   |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.keymanager.algorithm](http://kafka.apache.org/documentation/#ssl.keymanager.algorithm)

  The algorithm used by key manager factory for SSL connections. Default value is the key manager factory algorithm configured for the Java Virtual Machine.

  密钥管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的密钥管理器工厂算法。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | SunX509    |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.keystore.location](http://kafka.apache.org/documentation/#ssl.keystore.location)

  The location of the key store file. This is optional for client and can be used for two-way authentication for client.

  密钥库文件的位置。这对于客户端是可选的，并且可以用于客户端的双向身份验证。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.keystore.password](http://kafka.apache.org/documentation/#ssl.keystore.password)

  The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.

    密钥存储文件的存储密码。这对于客户端是可选的，并且仅在配置了ssl.keystore.location时才需要。

|         Type: | password   |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.keystore.type](http://kafka.apache.org/documentation/#ssl.keystore.type)

  The file format of the key store file. This is optional for client.

  密钥存储文件的文件格式。这对于客户端是可选的。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | JKS        |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.protocol](http://kafka.apache.org/documentation/#ssl.protocol)

  The SSL protocol used to generate the SSLContext. The default is 'TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. This value should be fine for most use cases. Allowed values in recent JVMs are 'TLSv1.2' and 'TLSv1.3'. 'TLS', 'TLSv1.1', 'SSL', 'SSLv2' and 'SSLv3' may be supported in older JVMs, but their usage is discouraged due to known security vulnerabilities. With the default value for this config and 'ssl.enabled.protocols', clients will downgrade to 'TLSv1.2' if the server does not support 'TLSv1.3'. If this config is set to 'TLSv1.2', clients will not use 'TLSv1.3' even if it is one of the values in ssl.enabled.protocols and the server only supports 'TLSv1.3'.

    用于生成SSLContext的SSL协议。与Java 11或更高版本一起运行时，默认值为` TLSv1.3`，否则为` TLSv1.2`。对于大多数用例，此值应该合适。最近的JVM中允许的值为` TLSv1.2`和` TLSv1.3`。较早的JVM中可能支持`TLS`，`TLSv1.1`，`SSL`，`SSLv2`和`SSLv3`，但是由于已知的安全漏洞，不鼓励使用它们。使用此配置的默认值和` ssl.enabled.protocols`，如果服务器不支持` TLSv1.3`，则客户端将降级到` TLSv1.2`。如果此配置设置为` TLSv1.2`，则即使它是ssl.enabled.protocols中的值之一，并且服务器仅支持` TLSv1.3`，客户端也不会使用` TLSv1.3`。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | TLSv1.2    |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.provider](http://kafka.apache.org/documentation/#ssl.provider)

  The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.

  用于SSL连接的安全提供程序的名称。缺省值是JVM的缺省安全提供程序。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.trustmanager.algorithm](http://kafka.apache.org/documentation/#ssl.trustmanager.algorithm)

  The algorithm used by trust manager factory for SSL connections. Default value is the trust manager factory algorithm configured for the Java Virtual Machine.

  信任管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的信任管理器工厂算法。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | PKIX       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.truststore.location](http://kafka.apache.org/documentation/#ssl.truststore.location)

  The location of the trust store file.

  信任库文件的位置。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.truststore.password](http://kafka.apache.org/documentation/#ssl.truststore.password)

  The password for the trust store file. If a password is not set access to the truststore is still available, but integrity checking is disabled.

  信任库文件的密码。如果未设置密码，则仍然可以访问信任库，但是将禁用完整性检查。

|         Type: | password   |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [ssl.truststore.type](http://kafka.apache.org/documentation/#ssl.truststore.type)

  The file format of the trust store file.

  信任库文件的文件格式。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | JKS        |
| Valid Values: |            |
|   Importance: | medium     |
|  Update Mode: | per-broker |

#### [zookeeper.clientCnxnSocket](http://kafka.apache.org/documentation/#zookeeper.clientCnxnSocket)

  Typically set to `org.apache.zookeeper.ClientCnxnSocketNetty` when using TLS connectivity to ZooKeeper. Overrides any explicit value set via the same-named `zookeeper.clientCnxnSocket` system property.

    当使用到ZooKeeper的TLS连接时，通常设置为org.apache.zookeeper.ClientCnxnSocketNetty。覆盖通过相同的`zookeeper.clientCnxnSocket`系统属性设置的任何显式值。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.client.enable](http://kafka.apache.org/documentation/#zookeeper.ssl.client.enable)

  Set client to use TLS when connecting to ZooKeeper. An explicit value overrides any value set via the `zookeeper.client.secure` system property (note the different name). Defaults to false if neither is set; when true, `zookeeper.clientCnxnSocket` must be set (typically to `org.apache.zookeeper.ClientCnxnSocketNetty`); other values to set may include `zookeeper.ssl.cipher.suites`, `zookeeper.ssl.crl.enable`, `zookeeper.ssl.enabled.protocols`, `zookeeper.ssl.endpoint.identification.algorithm`, `zookeeper.ssl.keystore.location`, `zookeeper.ssl.keystore.password`, `zookeeper.ssl.keystore.type`, `zookeeper.ssl.ocsp.enable`, `zookeeper.ssl.protocol`, `zookeeper.ssl.truststore.location`, `zookeeper.ssl.truststore.password`, `zookeeper.ssl.truststore.type`

    将客户端设置为在连接到ZooKeeper时使用TLS。显式值会覆盖通过` zookeeper.client.secure`系统属性设置的任何值（请注意其他名称）。如果两者均未设置，则默认为false；否则为false。如果为true，则必须设置` zookeeper.clientCnxnSocket`（通常设置为` org.apache.zookeeper.ClientCnxnSocketNetty`）；要设置的其他值可能包括` zookeeper.ssl.cipher.suites`，` zookeeper.ssl.crl.enable`，` zookeeper.ssl.enabled.protocols`，` zookeeper.ssl.endpoint.identification.algorithm`，` zookeeper` .ssl.keystore.location`，`zookeeper.ssl.keystore.password`，`zookeeper.ssl.keystore.type`，`zookeeper.ssl.ocsp.enable`，`zookeeper.ssl.protocol`，`zookeeper.ssl .truststore.location`，`zookeeper.ssl.truststore.password`，`zookeeper.ssl.truststore.type`

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | false     |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.keystore.location](http://kafka.apache.org/documentation/#zookeeper.ssl.keystore.location)

  Keystore location when using a client-side certificate with TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.keyStore.location` system property (note the camelCase).

    使用客户端证书和TLS连接到ZooKeeper时的密钥库位置。覆盖通过` zookeeper.ssl.keyStore.location`系统属性设置的任何显式值（请注意camelCase）。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.keystore.password](http://kafka.apache.org/documentation/#zookeeper.ssl.keystore.password)

  Keystore password when using a client-side certificate with TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.keyStore.password` system property (note the camelCase). Note that ZooKeeper does not support a key password different from the keystore password, so be sure to set the key password in the keystore to be identical to the keystore password; otherwise the connection attempt to Zookeeper will fail.

    使用通过TLS连接到ZooKeeper的客户端证书时的密钥库密码。覆盖通过“ zookeeper.ssl.keyStore.password”系统属性设置的任何显式值（请注意camelCase）。

|         Type: | password  |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.keystore.type](http://kafka.apache.org/documentation/#zookeeper.ssl.keystore.type)

  Keystore type when using a client-side certificate with TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.keyStore.type` system property (note the camelCase). The default value of `null` means the type will be auto-detected based on the filename extension of the keystore.

    使用客户端证书和TLS连接到ZooKeeper时的密钥库类型。覆盖通过“ zookeeper.ssl.keyStore.type”系统属性设置的任何显式值（请注意camelCase）。默认值'null'表示将根据密钥库的文件扩展名自动检测类型。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.truststore.location](http://kafka.apache.org/documentation/#zookeeper.ssl.truststore.location)

  Truststore location when using TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.trustStore.location` system property (note the camelCase).

    使用TLS连接到ZooKeeper时的信任库位置。覆盖通过“ zookeeper.ssl.trustStore.location”系统属性设置的任何显式值（请注意camelCase）。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.truststore.password](http://kafka.apache.org/documentation/#zookeeper.ssl.truststore.password)

  Truststore password when using TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.trustStore.password` system property (note the camelCase).

    使用TLS连接到ZooKeeper时的信任库密码。覆盖通过“ zookeeper.ssl.trustStore.password”系统属性设置的任何显式值（请注意camelCase）。

|         Type: | password  |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [zookeeper.ssl.truststore.type](http://kafka.apache.org/documentation/#zookeeper.ssl.truststore.type)

  Truststore type when using TLS connectivity to ZooKeeper. Overrides any explicit value set via the `zookeeper.ssl.trustStore.type` system property (note the camelCase). The default value of `null` means the type will be auto-detected based on the filename extension of the truststore.

    使用TLS连接到ZooKeeper时的信任库类型。覆盖通过“ zookeeper.ssl.trustStore.type”系统属性设置的任何显式值（请注意camelCase）。默认值'null'表示将根据信任库的文件扩展名自动检测类型。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | medium    |
|  Update Mode: | read-only |

#### [alter.config.policy.class.name](http://kafka.apache.org/documentation/#alter.config.policy.class.name)

  The alter configs policy class that should be used for validation. The class should implement the `org.apache.kafka.server.policy.AlterConfigPolicy` interface.

    应该用于验证的alter configs策略类。该类应该实现org.apache.kafka.server.policy.AlterConfigPolicy接口。

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [alter.log.dirs.replication.quota.window.num](http://kafka.apache.org/documentation/#alter.log.dirs.replication.quota.window.num)

  The number of samples to retain in memory for alter log dirs replication quotas

  为更改日志目录复制配额而保留在内存中的样本数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 11        |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [alter.log.dirs.replication.quota.window.size.seconds](http://kafka.apache.org/documentation/#alter.log.dirs.replication.quota.window.size.seconds)

  The time span of each sample for alter log dirs replication quotas

  更改日志目录复制配额的每个样本的时间跨度

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [authorizer.class.name](http://kafka.apache.org/documentation/#authorizer.class.name)

  The fully qualified name of a class that implements sorg.apache.kafka.server.authorizer.Authorizer interface, which is used by the broker for authorization. This config also supports authorizers that implement the deprecated kafka.security.auth.Authorizer trait which was previously used for authorization.

    实现sorg.apache.kafka.server.authorizer.Authorizer接口的类的标准名称，该接口由代理用于授权。此配置还支持授权者，这些授权者实现了过时的kafka.security.auth.Authorizer特性，该特性以前用于授权。

|         Type: | string    |
| ------------: | --------- |
|      Default: | ""        |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [client.quota.callback.class](http://kafka.apache.org/documentation/#client.quota.callback.class)

  The fully qualified name of a class that implements the ClientQuotaCallback interface, which is used to determine quota limits applied to client requests. By default, , or quotas stored in ZooKeeper are applied. For any given request, the most specific quota that matches the user principal of the session and the client-id of the request is applied.

  实现ClientQuotaCallback接口的类的全限定名，该接口用于确定应用于客户端请求的配额限制。默认情况下，应用或存储在ZooKeeper中的配额。对于任何给定的请求，将应用与会话的用户主体和请求的客户端ID相匹配的最具体的配额。

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [connection.failed.authentication.delay.ms](http://kafka.apache.org/documentation/#connection.failed.authentication.delay.ms)

  Connection close delay on failed authentication: this is the time (in milliseconds) by which connection close will be delayed on authentication failure. This must be configured to be less than connections.max.idle.ms to prevent connection timeout.

    身份验证失败时的连接关闭延迟：这是身份验证失败时延迟连接关闭的时间（以毫秒为单位）。必须将其配置为小于connections.max.idle.ms，以防止连接超时。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 100       |
| Valid Values: | [0,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [create.topic.policy.class.name](http://kafka.apache.org/documentation/#create.topic.policy.class.name)

  The create topic policy class that should be used for validation. The class should implement the `org.apache.kafka.server.policy.CreateTopicPolicy` interface.

    应该用于验证的创建主题策略类。该类应实现org.apache.kafka.server.policy.CreateTopicPolicy接口。

|         Type: | class     |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [delegation.token.expiry.check.interval.ms](http://kafka.apache.org/documentation/#delegation.token.expiry.check.interval.ms)

  Scan interval to remove expired delegation tokens.

  扫描间隔以删除过期的委托令牌。

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 3600000 (1 hour) |
| Valid Values: | [1,...]          |
|   Importance: | low              |
|  Update Mode: | read-only        |

#### [kafka.metrics.polling.interval.secs](http://kafka.apache.org/documentation/#kafka.metrics.polling.interval.secs)

  The metrics polling interval (in seconds) which can be used in kafka.metrics.reporters implementations.

    可以在kafka.metrics.reporters实现中使用的指标轮询间隔（以秒为单位）。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 10        |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [kafka.metrics.reporters](http://kafka.apache.org/documentation/#kafka.metrics.reporters)

  A list of classes to use as Yammer metrics custom reporters. The reporters should implement `kafka.metrics.KafkaMetricsReporter` trait. If a client wants to expose JMX operations on a custom reporter, the custom reporter needs to additionally implement an MBean trait that extends `kafka.metrics.KafkaMetricsReporterMBean` trait so that the registered MBean is compliant with the standard MBean convention.

    用作Yammer指标自定义报告程序的类的列表。报告者应实现“ kafka.metrics.KafkaMetricsReporter”特征。如果客户希望在自定义报告程序上公开JMX操作，则自定义报告程序还需要实现扩展`kafka.metrics.KafkaMetricsReporterMBean'特征的MBean特征，以使注册的MBean符合标准MBean约定。

|         Type: | list      |
| ------------: | --------- |
|      Default: | ""        |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [listener.security.protocol.map](http://kafka.apache.org/documentation/#listener.security.protocol.map)

  Map between listener names and security protocols. This must be defined for the same security protocol to be usable in more than one port or IP. For example, internal and external traffic can be separated even if SSL is required for both. Concretely, the user could define listeners with names INTERNAL and EXTERNAL and this property as: `INTERNAL:SSL,EXTERNAL:SSL`. As shown, key and value are separated by a colon and map entries are separated by commas. Each listener name should only appear once in the map. Different security (SSL and SASL) settings can be configured for each listener by adding a normalised prefix (the listener name is lowercased) to the config name. For example, to set a different keystore for the INTERNAL listener, a config with name `listener.name.internal.ssl.keystore.location` would be set. If the config for the listener name is not set, the config will fallback to the generic config (i.e. `ssl.keystore.location`).

    侦听器名称和安全协议之间的映射。必须对同一安全协议进行定义，才能在多个端口或IP中使用同一安全协议。例如，即使两者都需要SSL，也可以将内部和外部流量分开。具体来说，用户可以使用名称INTERNAL和EXTERNAL定义侦听器，并将此属性定义为：“ INTERNAL：SSL，EXTERNAL：SSL”。如图所示，键和值之间用冒号分隔，而地图项则用逗号分隔。每个侦听器名称在地图中应该只出现一次。通过在配置名称中添加规范化前缀（侦听器名称为小写），可以为每个侦听器配置不同的安全性（SSL和SASL）设置。例如，要为内部侦听器设置不同的密钥库，将设置名称为“ listener.name.internal.ssl.keystore.location”的配置。如果未设置侦听器名称的配置，则该配置将回退到通用配置（即ssl.keystore.location）。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL |
| Valid Values: |                                                              |
|   Importance: | low                                                          |
|  Update Mode: | per-broker                                                   |

#### [log.message.downconversion.enable](http://kafka.apache.org/documentation/#log.message.downconversion.enable)

  This configuration controls whether down-conversion of message formats is enabled to satisfy consume requests. When set to `false`, broker will not perform down-conversion for consumers expecting an older message format. The broker responds with `UNSUPPORTED_VERSION` error for consume requests from such older clients. This configurationdoes not apply to any message format conversion that might be required for replication to followers.

    此配置控制是否启用消息格式的下转换以满足消费请求。当设置为“ false”时，代理将不为期望旧消息格式的消费者执行下转换。对于此类较旧客户端的使用请求，代理会以“ UNSUPPORTED_VERSION”错误响应。此配置不适用于复制到关注者所需的任何消息格式转换。

|         Type: | boolean      |
| ------------: | ------------ |
|      Default: | true         |
| Valid Values: |              |
|   Importance: | low          |
|  Update Mode: | cluster-wide |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

    用作指标报告者的类列表。实现org.apache.kafka.common.metrics.MetricsReporter接口允许插入将通知新度量标准的类。始终包含JmxReporter来注册JMX统计信息。

|         Type: | list         |
| ------------: | ------------ |
|      Default: | ""           |
| Valid Values: |              |
|   Importance: | low          |
|  Update Mode: | cluster-wide |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

  维护以计算指标的样本数。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 2         |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

  指标的最高记录级别。

|         Type: | string    |
| ------------: | --------- |
|      Default: | INFO      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

  计算指标样本的时间窗口。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [1,...]            |
|   Importance: | low                |
|  Update Mode: | read-only          |

#### [password.encoder.cipher.algorithm](http://kafka.apache.org/documentation/#password.encoder.cipher.algorithm)

  The Cipher algorithm used for encoding dynamically configured passwords.

  用于对动态配置的密码进行编码的密码算法。

|         Type: | string               |
| ------------: | -------------------- |
|      Default: | AES/CBC/PKCS5Padding |
| Valid Values: |                      |
|   Importance: | low                  |
|  Update Mode: | read-only            |

#### [password.encoder.iterations](http://kafka.apache.org/documentation/#password.encoder.iterations)

  The iteration count used for encoding dynamically configured passwords.

  用于对动态配置的密码进行编码的迭代计数。

|         Type: | int        |
| ------------: | ---------- |
|      Default: | 4096       |
| Valid Values: | [1024,...] |
|   Importance: | low        |
|  Update Mode: | read-only  |

#### [password.encoder.key.length](http://kafka.apache.org/documentation/#password.encoder.key.length)

  The key length used for encoding dynamically configured passwords.

  用于对动态配置的密码进行编码的密钥长度。

|         Type: | int       |
| ------------: | --------- |
|      Default: | 128       |
| Valid Values: | [8,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [password.encoder.keyfactory.algorithm](http://kafka.apache.org/documentation/#password.encoder.keyfactory.algorithm)

  The SecretKeyFactory algorithm used for encoding dynamically configured passwords. Default is PBKDF2WithHmacSHA512 if available and PBKDF2WithHmacSHA1 otherwise.

  SecretKeyFactory算法用于对动态配置的密码进行编码。如果可用，默认值为PBKDF2WithHmacSHA512，否则为PBKDF2WithHmacSHA1。s

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [quota.window.num](http://kafka.apache.org/documentation/#quota.window.num)

  The number of samples to retain in memory for client quotas

  保留在内存中以供客户端配额使用的样本数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 11        |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [quota.window.size.seconds](http://kafka.apache.org/documentation/#quota.window.size.seconds)

  The time span of each sample for client quotas

  每个客户配额样本的时间跨度

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [replication.quota.window.num](http://kafka.apache.org/documentation/#replication.quota.window.num)

  The number of samples to retain in memory for replication quotas

  为复制配额保留在内存中的样本数

|         Type: | int       |
| ------------: | --------- |
|      Default: | 11        |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [replication.quota.window.size.seconds](http://kafka.apache.org/documentation/#replication.quota.window.size.seconds)

  The time span of each sample for replication quotas

  每个样本用于复制配额的时间跨度

|         Type: | int       |
| ------------: | --------- |
|      Default: | 1         |
| Valid Values: | [1,...]   |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [security.providers](http://kafka.apache.org/documentation/#security.providers)

  A list of configurable creator classes each returning a provider implementing security algorithms. These classes should implement the `org.apache.kafka.common.security.auth.SecurityProviderCreator` interface.

    一组可配置的创建者类的列表，每个创建者类返回一个实现安全算法的提供者。这些类应实现org.apache.kafka.common.security.auth.SecurityProviderCreator接口。

|         Type: | string    |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#ssl.endpoint.identification.algorithm)

  The endpoint identification algorithm to validate server hostname using server certificate.

  使用服务器证书验证服务器主机名的端点标识算法。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | https      |
| Valid Values: |            |
|   Importance: | low        |
|  Update Mode: | per-broker |

#### [ssl.engine.factory.class](http://kafka.apache.org/documentation/#ssl.engine.factory.class)

  The class of type org.apache.kafka.common.security.auth.SslEngineFactory to provide SSLEngine objects. Default value is org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

    用于提供SSLEngine对象的org.apache.kafka.common.security.auth.SslEngineFactory类型的类。默认值为org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

|         Type: | class      |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | low        |
|  Update Mode: | per-broker |

#### [ssl.principal.mapping.rules](http://kafka.apache.org/documentation/#ssl.principal.mapping.rules)

  A list of rules for mapping from distinguished name from the client certificate to short name. The rules are evaluated in order and the first rule that matches a principal name is used to map it to a short name. Any later rules in the list are ignored. By default, distinguished name of the X.500 certificate will be the principal. For more details on the format please see [security authorization and acls](http://kafka.apache.org/documentation/#security_authz). Note that this configuration is ignored if an extension of KafkaPrincipalBuilder is provided by the `principal.builder.class` configuration.

    从客户端证书的专有名称到短名称的映射规则列表。将按顺序评估规则，并且使用与主体名称匹配的第一条规则将其映射为简称。列表中以后的所有规则都将被忽略。默认情况下，X.500证书的专有名称将是主体。有关格式的更多详细信息，请参见[安全授权和ACL]（http://kafka.apache.org/documentation/#security_authz）。请注意，如果`principal.builder.class`配置提供了KafkaPrincipalBuilder的扩展名，则将忽略此配置。

|         Type: | string    |
| ------------: | --------- |
|      Default: | DEFAULT   |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [ssl.secure.random.implementation](http://kafka.apache.org/documentation/#ssl.secure.random.implementation)

  The SecureRandom PRNG implementation to use for SSL cryptography operations.

    用于SSL加密操作的SecureRandom PRNG实现。

|         Type: | string     |
| ------------: | ---------- |
|      Default: | null       |
| Valid Values: |            |
|   Importance: | low        |
|  Update Mode: | per-broker |

#### [transaction.abort.timed.out.transaction.cleanup.interval.ms](http://kafka.apache.org/documentation/#transaction.abort.timed.out.transaction.cleanup.interval.ms)

  The interval at which to rollback transactions that have timed out

  回滚已超时的事务的时间间隔

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 10000 (10 seconds) |
| Valid Values: | [1,...]            |
|   Importance: | low                |
|  Update Mode: | read-only          |

#### [transaction.remove.expired.transaction.cleanup.interval.ms](http://kafka.apache.org/documentation/#transaction.remove.expired.transaction.cleanup.interval.ms)

  The interval at which to remove transactions that have expired due to `transactional.id.expiration.ms` passing

    删除由于`transactional.id.expiration.ms`传递而过期的交易的时间间隔

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 3600000 (1 hour) |
| Valid Values: | [1,...]          |
|   Importance: | low              |
|  Update Mode: | read-only        |

#### [zookeeper.ssl.cipher.suites](http://kafka.apache.org/documentation/#zookeeper.ssl.cipher.suites)

  Specifies the enabled cipher suites to be used in ZooKeeper TLS negotiation (csv). Overrides any explicit value set via the `zookeeper.ssl.ciphersuites` system property (note the single word "ciphersuites"). The default value of `null` means the list of enabled cipher suites is determined by the Java runtime being used.

    指定在ZooKeeper TLS协商（csv）中使用的已启用密码套件。覆盖通过“ zookeeper.ssl.ciphersuites”系统属性设置的任何显式值（请注意单词“ ciphersuites”）。默认值'null'表示启用的密码套件列表由所使用的Java运行时确定。

|         Type: | list      |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.ssl.crl.enable](http://kafka.apache.org/documentation/#zookeeper.ssl.crl.enable)

  Specifies whether to enable Certificate Revocation List in the ZooKeeper TLS protocols. Overrides any explicit value set via the `zookeeper.ssl.crl` system property (note the shorter name).

    指定是否在ZooKeeper TLS协议中启用证书吊销列表。覆盖通过“ zookeeper.ssl.crl”系统属性设置的任何显式值（请注意简称）。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | false     |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.ssl.enabled.protocols](http://kafka.apache.org/documentation/#zookeeper.ssl.enabled.protocols)

  Specifies the enabled protocol(s) in ZooKeeper TLS negotiation (csv). Overrides any explicit value set via the `zookeeper.ssl.enabledProtocols` system property (note the camelCase). The default value of `null` means the enabled protocol will be the value of the `zookeeper.ssl.protocol` configuration property.

    指定ZooKeeper TLS协商（csv）中启用的协议。覆盖通过“ zookeeper.ssl.enabledProtocols”系统属性设置的任何显式值（请注意camelCase）。默认值为“ null”表示启用的协议将为“ zookeeper.ssl.protocol”配置属性的值。

|         Type: | list      |
| ------------: | --------- |
|      Default: | null      |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#zookeeper.ssl.endpoint.identification.algorithm)

  Specifies whether to enable hostname verification in the ZooKeeper TLS negotiation process, with (case-insensitively) "https" meaning ZooKeeper hostname verification is enabled and an explicit blank value meaning it is disabled (disabling it is only recommended for testing purposes). An explicit value overrides any "true" or "false" value set via the `zookeeper.ssl.hostnameVerification` system property (note the different name and values; true implies https and false implies blank).

    指定是否在ZooKeeper TLS协商过程中启用主机名验证，（不区分大小写）“ https”表示启用ZooKeeper主机名验证，而明确的空白值表示已禁用（仅出于测试目的，建议禁用该值）。显式值会覆盖通过“ zookeeper.ssl.hostnameVerification”系统属性设置的任何“ true”或“ false”值（请注意不同的名称和值； true表示https，false表示空白）。

|         Type: | string    |
| ------------: | --------- |
|      Default: | HTTPS     |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.ssl.ocsp.enable](http://kafka.apache.org/documentation/#zookeeper.ssl.ocsp.enable)

  Specifies whether to enable Online Certificate Status Protocol in the ZooKeeper TLS protocols. Overrides any explicit value set via the `zookeeper.ssl.ocsp` system property (note the shorter name).

    指定是否在ZooKeeper TLS协议中启用“在线证书状态协议”。覆盖通过“ zookeeper.ssl.ocsp”系统属性设置的任何显式值（请注意简称）。

|         Type: | boolean   |
| ------------: | --------- |
|      Default: | false     |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.ssl.protocol](http://kafka.apache.org/documentation/#zookeeper.ssl.protocol)

  Specifies the protocol to be used in ZooKeeper TLS negotiation. An explicit value overrides any value set via the same-named `zookeeper.ssl.protocol` system property.

    指定在ZooKeeper TLS协商中使用的协议。显式值会覆盖通过相同的`zookeeper.ssl.protocol`系统属性设置的任何值。

|         Type: | string    |
| ------------: | --------- |
|      Default: | TLSv1.2   |
| Valid Values: |           |
|   Importance: | low       |
|  Update Mode: | read-only |

#### [zookeeper.sync.time.ms](http://kafka.apache.org/documentation/#zookeeper.sync.time.ms)

  How far a ZK follower can be behind a ZK leader

  ZK追随者可以落后ZK领导者多远

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 2000 (2 seconds) |
| Valid Values: |                  |
|   Importance: | low              |
|  Update Mode: | read-only        |

More details about broker configuration can be found in the scala class `kafka.server.KafkaConfig`.

有关代理配置的更多详细信息，可以在scala类“ kafka.server.KafkaConfig”中找到。

#### [3.1.1 Updating Broker Configs](http://kafka.apache.org/documentation/#dynamicbrokerconfigs)

From Kafka version 1.1 onwards, some of the broker configs can be updated without restarting the broker. See the `Dynamic Update Mode` column in [Broker Configs](http://kafka.apache.org/documentation/#brokerconfigs) for the update mode of each broker config.

从Kafka版本1.1开始，可以在不重新启动代理的情况下更新一些代理配置。查看[Broker Configs](http://kafka.apache.org/documentation/#brokerconfigs)中的“动态更新模式”列，了解每个代理配置的更新模式。

- `read-only`: Requires a broker restart for update

  需要重新启动代理进行更新

- `per-broker`: May be updated dynamically for each broker

  可以为每个代理动态更新

- `cluster-wide`: May be updated dynamically as a cluster-wide default. May also be updated as a per-broker value for testing.

- 可以作为集群范围的默认值动态更新。也可以为测试更新为每个代理的值。

To alter the current broker configs for broker id 0 (for example, the number of log cleaner threads):

要更改代理id为0的当前代理配置(例如，日志清理器线程的数量):

```bash
> bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type brokers --entity-name 0 --alter --add-config log.cleaner.threads=2
```

To describe the current dynamic broker configs for broker id 0:

要描述代理id为0的当前动态代理配置:

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type brokers --entity-name 0 --describe
```

To delete a config override and revert to the statically configured or default value for broker id 0 (for example, the number of log cleaner threads):

删除一个配置覆盖并恢复到代理id 0的静态配置或默认值(例如，日志清理线程的数量):

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type brokers --entity-name 0 --alter --delete-config log.cleaner.threads
```

Some configs may be configured as a cluster-wide default to maintain consistent values across the whole cluster. All brokers in the cluster will process the cluster default update. For example, to update log cleaner threads on all brokers:

一些配置可能被配置为集群范围的默认值，以维护整个集群的一致值。集群中的所有代理将处理集群默认更新。例如，要更新所有代理上的日志清理器线程:

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type brokers --entity-default --alter --add-config log.cleaner.threads=2
```

To describe the currently configured dynamic cluster-wide default configs:

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type brokers --entity-default --describe
```

All configs that are configurable at cluster level may also be configured at per-broker level (e.g. for testing). If a config value is defined at different levels, the following order of precedence is used:

所有在集群级别上可配置的配置也可以在每个代理级别上配置(例如，用于测试)。如果配置值是在不同的级别定义的，则使用以下优先级顺序:

- Dynamic per-broker config stored in ZooKeeper

  存储在ZooKeeper中的动态代理配置

- Dynamic cluster-wide default config stored in ZooKeeper

  动态集群范围的默认配置存储在ZooKeeper中

- Static broker config from `server.properties`

  来自“server.properties”的静态代理配置

- Kafka default, see [broker configs](http://kafka.apache.org/documentation/#brokerconfigs)

  Kafka默认，请查看[broker configs]

##### Updating Password Configs Dynamically

动态更新密码配置

Password config values that are dynamically updated are encrypted before storing in ZooKeeper. The broker config `password.encoder.secret` must be configured in `server.properties` to enable dynamic update of password configs. The secret may be different on different brokers.

动态更新的密码配置值在存储在ZooKeeper中之前会进行加密。代理配置的password.encoder.secret '必须在' server.properties '中配置。属性来启用密码配置的动态更新。对于不同的经纪人，这个秘密可能是不同的。

The secret used for password encoding may be rotated with a rolling restart of brokers. The old secret used for encoding passwords currently in ZooKeeper must be provided in the static broker config `password.encoder.old.secret` and the new secret must be provided in `password.encoder.secret`. All dynamic password configs stored in ZooKeeper will be re-encoded with the new secret when the broker starts up.

用于密码编码的秘密可以通过滚动重新启动代理来旋转。当前在ZooKeeper中用于编码密码的旧秘密必须在静态代理配置的password.encode .old.secret中提供。和新密钥必须在`password.encoder.secret`中提供。当代理启动时，存储在ZooKeeper中的所有动态密码配置都将使用新密钥重新编码。

In Kafka 1.1.x, all dynamically updated password configs must be provided in every alter request when updating configs using `kafka-configs.sh` even if the password config is not being altered. This constraint will be removed in a future release.

在使用kafka-configs.sh更新配置时，必须在每个alter请求中提供所有动态更新的密码配置。即使密码配置没有被更改，也不需要更改。这个约束将在未来的版本中删除。

##### Updating Password Configs in ZooKeeper Before Starting Brokers

在启动代理之前更新ZooKeeper中的密码配置

From Kafka 2.0.0 onwards, `kafka-configs.sh` enables dynamic broker configs to be updated using ZooKeeper before starting brokers for bootstrapping. This enables all password configs to be stored in encrypted form, avoiding the need for clear passwords in `server.properties`. The broker config `password.encoder.secret` must also be specified if any password configs are included in the alter command. Additional encryption parameters may also be specified. Password encoder configs will not be persisted in ZooKeeper. For example, to store SSL key password for listener `INTERNAL` on broker 0:

从Kafka 2.0.0开始，' Kafka -configs.sh '允许在启动代理进行引导之前使用ZooKeeper更新动态代理配置。这使得所有的密码配置都以加密的形式存储，从而避免在' server.properties '中使用清晰的密码。代理配置的password.encoder.secret。如果alter命令中包含任何密码配置，也必须指定 '。还可以指定其他加密参数。密码编码器configs不会持久存在于ZooKeeper中。例如，要在代理0上存储侦听器“INTERNAL”的SSL密钥密码:

```bash
  > bin/kafka-configs.sh --zookeeper localhost:2182 --zk-tls-config-file zk_tls_config.properties --entity-type brokers --entity-name 0 --alter --add-config
    'listener.name.internal.ssl.key.password=key-password,password.encoder.secret=secret,password.encoder.iterations=8192'
```

The configuration `listener.name.internal.ssl.key.password` will be persisted in ZooKeeper in encrypted form using the provided encoder configs. The encoder secret and iterations are not persisted in ZooKeeper.

listener.name.internal.ssl.key.password '将使用提供的编码器configs以加密的形式保存在ZooKeeper中。编码器秘密和迭代不在ZooKeeper中持久化。

##### Updating SSL Keystore of an Existing Listener

更新现有侦听器的SSL密钥存储库

Brokers may be configured with SSL keystores with short validity periods to reduce the risk of compromised certificates. Keystores may be updated dynamically without restarting the broker. The config name must be prefixed with the listener prefix `listener.name.{listenerName}.` so that only the keystore config of a specific listener is updated. The following configs may be updated in a single alter request at per-broker level:

可以将代理配置为有效期较短的SSL密钥存储库，以降低证书受损的风险。密钥存储库可以在不重新启动代理的情况下动态更新。配置名必须以侦听器前缀' listener.name.{listenerName}. '作为前缀，以便只有特定侦听器的密钥存储库配置被更新。下面的配置可以在每个代理级别的一个alter请求中更新:

- `ssl.keystore.type`
- `ssl.keystore.location`
- `ssl.keystore.password`
- `ssl.key.password`

If the listener is the inter-broker listener, the update is allowed only if the new keystore is trusted by the truststore configured for that listener. For other listeners, no trust validation is performed on the keystore by the broker. Certificates must be signed by the same certificate authority that signed the old certificate to avoid any client authentication failures.

如果侦听器是代理间侦听器，则仅当新密钥存储库受到为该侦听器配置的信任存储库的信任时，才允许更新。对于其他侦听器，代理不会对密钥存储库执行信任验证。证书必须由签署旧证书的同一证书权威机构签署，以避免任何客户端身份验证失败。

##### Updating SSL Truststore of an Existing Listener

更新现有侦听器的SSL信任存储

Broker truststores may be updated dynamically without restarting the broker to add or remove certificates. Updated truststore will be used to authenticate new client connections. The config name must be prefixed with the listener prefix `listener.name.{listenerName}.` so that only the truststore config of a specific listener is updated. The following configs may be updated in a single alter request at per-broker level:

可以动态更新代理信任存储，而无需重新启动代理来添加或删除证书。更新后的信任库将用于对新客户机连接进行身份验证。配置名必须以侦听器前缀' listener.name.{listenerName}. '作为前缀，以便只有特定侦听器的truststore配置被更新。下面的配置可以在每个代理级别的一个alter请求中更新:

- `ssl.truststore.type`
- `ssl.truststore.location`
- `ssl.truststore.password`

If the listener is the inter-broker listener, the update is allowed only if the existing keystore for that listener is trusted by the new truststore. For other listeners, no trust validation is performed by the broker before the update. Removal of CA certificates used to sign client certificates from the new truststore can lead to client authentication failures.

如果侦听器是代理间侦听器，则仅当该侦听器的现有密钥存储库受到新的信任存储库的信任时才允许更新。对于其他侦听器，在更新之前代理不执行信任验证。从新的信任存储库中删除用于对客户机证书进行签名的CA证书可能导致客户机身份验证失败。

##### Updating Default Topic Configuration

更新默认主题配置

Default topic configuration options used by brokers may be updated without broker restart. The configs are applied to topics without a topic config override for the equivalent per-topic config. One or more of these configs may be overridden at cluster-default level used by all brokers.

代理使用的默认主题配置选项可以在不重启代理的情况下更新。配置被应用到主题，而不用为等效的每个主题配置覆盖主题配置。这些配置中的一个或多个可能会在所有代理使用的集群默认级别上被覆盖。

- `log.segment.bytes`
- `log.roll.ms`
- `log.roll.hours`
- `log.roll.jitter.ms`
- `log.roll.jitter.hours`
- `log.index.size.max.bytes`
- `log.flush.interval.messages`
- `log.flush.interval.ms`
- `log.retention.bytes`
- `log.retention.ms`
- `log.retention.minutes`
- `log.retention.hours`
- `log.index.interval.bytes`
- `log.cleaner.delete.retention.ms`
- `log.cleaner.min.compaction.lag.ms`
- `log.cleaner.max.compaction.lag.ms`
- `log.cleaner.min.cleanable.ratio`
- `log.cleanup.policy`
- `log.segment.delete.delay.ms`
- `unclean.leader.election.enable`
- `min.insync.replicas`
- `max.message.bytes`
- `compression.type`
- `log.preallocate`
- `log.message.timestamp.type`
- `log.message.timestamp.difference.max.ms`

From Kafka version 2.0.0 onwards, unclean leader election is automatically enabled by the controller when the config `unclean.leader.election.enable` is dynamically updated. In Kafka version 1.1.x, changes to `unclean.leader.election.enable` take effect only when a new controller is elected. Controller re-election may be forced by running:

从Kafka版本2.0.0开始，当配置unclean.leader.election。enable '是动态更新的时，控制器会自动启用不洁净领袖选举。。在Kafka 1.1版本中。改成“unclean. lead.election.enable”只在选择新控制器时生效。可通过下列方式迫使管制员改选:

```bash
  > bin/zookeeper-shell.sh localhost
  rmr /controller
```

##### Updating Log Cleaner Configs

更新日志清理器配置

Log cleaner configs may be updated dynamically at cluster-default level used by all brokers. The changes take effect on the next iteration of log cleaning. One or more of these configs may be updated:

日志清理器配置可以在所有代理使用的集群默认级别上动态更新。这些更改将在下一次日志清理迭代中生效。这些配置中的一个或多个可能会被更新:

- `log.cleaner.threads`
- `log.cleaner.io.max.bytes.per.second`
- `log.cleaner.dedupe.buffer.size`
- `log.cleaner.io.buffer.size`
- `log.cleaner.io.buffer.load.factor`
- `log.cleaner.backoff.ms`

##### Updating Thread Configs

更新线程配置

The size of various thread pools used by the broker may be updated dynamically at cluster-default level used by all brokers. Updates are restricted to the range `currentSize / 2` to `currentSize * 2` to ensure that config updates are handled gracefully.

代理使用的各种线程池的大小可以在所有代理使用的集群默认级别上动态更新。更新被限制在‘currentSize / 2’到‘currentSize * 2’的范围内，以确保配置更新被优雅地处理。

- `num.network.threads`
- `num.io.threads`
- `num.replica.fetchers`
- `num.recovery.threads.per.data.dir`
- `log.cleaner.threads`
- `background.threads`

##### Updating ConnectionQuota Configs

The maximum number of connections allowed for a given IP/host by the broker may be updated dynamically at cluster-default level used by all brokers. The changes will apply for new connection creations and the existing connections count will be taken into account by the new limits.

代理允许的给定IP/主机的最大连接数可以在所有代理使用的集群默认级别上动态更新。这些更改将适用于创建新的连接，现有的连接数将被新的限制考虑在内。

- `max.connections.per.ip`
- `max.connections.per.ip.overrides`

##### Adding and Removing Listeners

添加和删除侦听器

Listeners may be added or removed dynamically. When a new listener is added, security configs of the listener must be provided as listener configs with the listener prefix `listener.name.{listenerName}.`. If the new listener uses SASL, the JAAS configuration of the listener must be provided using the JAAS configuration property `sasl.jaas.config` with the listener and mechanism prefix. See [JAAS configuration for Kafka brokers](http://kafka.apache.org/documentation/#security_jaas_broker) for details.

侦听器可以动态添加或删除。当添加一个新的监听器时，监听器的安全性配置必须提供为带有监听器前缀' listener.name.{listenerName}. '的监听器配置。如果新的侦听器使用SASL，则必须使用JAAS配置属性' SASL . JAAS提供侦听器的JAAS配置。带有监听器和机制前缀的config。详细信息请参见[Kafka代理的JAAS配置](http://kafka.apache.org/documentation/#security_jaas_broker)。

In Kafka version 1.1.x, the listener used by the inter-broker listener may not be updated dynamically. To update the inter-broker listener to a new listener, the new listener may be added on all brokers without restarting the broker. A rolling restart is then required to update `inter.broker.listener.name`.

在Kafka 1.1版本中。代理间侦听器使用的侦听器可能不会动态更新。要将代理间侦听器更新为新侦听器，可以在所有代理上添加新侦听器，而无需重新启动代理。然后需要滚动重启来更新`inter.broker.listener.name `。

In addition to all the security configs of new listeners, the following configs may be updated dynamically at per-broker level:

除了新侦听器的所有安全配置外，下面的配置可以在每个代理级别上动态更新:

- `listeners`
- `advertised.listeners`
- `listener.security.protocol.map`

Inter-broker listener must be configured using the static broker configuration `inter.broker.listener.name` or `inter.broker.security.protocol`.

内部代理侦听器必须使用静态代理配置“inter.broker.listener.name”或“inter.broker.security.protocol”来配置。

### [3.2 Topic-Level Configs](http://kafka.apache.org/documentation/#topicconfigs)

Configurations pertinent to topics have both a server default as well an optional per-topic override. If no per-topic configuration is given the server default is used. The override can be set at topic creation time by giving one or more `--config` options. This example creates a topic named *my-topic* with a custom max message size and flush rate:

与主题相关的配置既有服务器默认值，也有可选的每个主题覆盖。如果没有为每个主题提供配置，则使用服务器默认值。通过提供一个或多个' --config '选项，可以在主题创建时设置覆盖。这个示例创建了一个名为*my-topic*的主题，它具有自定义的最大消息大小和刷新速度:

```bash
  > bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my-topic --partitions 1 \
      --replication-factor 1 --config max.message.bytes=64000 --config flush.messages=1
```

Overrides can also be changed or set later using the alter configs command. This example updates the max message size for *my-topic*:

稍后还可以使用alter configs命令更改或设置覆盖。这个例子更新了*my-topic*的最大消息大小:

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic
      --alter --add-config max.message.bytes=128000
```

To check overrides set on the topic you can do

要检查在主题上设置的覆盖，您可以这样做

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --describe
```

To remove an override you can do

要删除覆盖，您可以这样做

```bash
  > bin/kafka-configs.sh --bootstrap-server localhost:9092  --entity-type topics --entity-name my-topic
      --alter --delete-config max.message.bytes
```

The following are the topic-level configurations. The server's default configuration for this property is given under the Server Default Property heading. A given server default config value only applies to a topic if it does not have an explicit topic config override.

下面是主题级配置。此属性的服务器默认配置在服务器默认属性标题下给出。如果主题没有显式的主题配置覆盖，则给定的服务器默认配置值仅适用于该主题。

#### [cleanup.policy旧记录保留策略](http://kafka.apache.org/documentation/#cleanup.policy)

  A string that is either "delete" or "compact" or both. This string designates the retention policy to use on old log segments. The default policy ("delete") will discard old segments when their retention time or size limit has been reached. The "compact" setting will enable [log compaction](http://kafka.apache.org/documentation/#compaction) on the topic.

  字符串要么是“删除”，要么是“紧凑”，或者两者都是。此字符串指定要在旧日志段上使用的保留策略。默认策略(“delete”)将在达到保留时间或大小限制时丢弃旧段。“compact”设置将在该主题上启用[日志压缩](http://kafka.apache.org/documentation/#compaction)。

  下面是主题级配置。此属性的服务器默认配置在服务器默认属性标题下给出。如果主题没有显式的主题配置覆盖，则给定的服务器默认配置值仅适用于该主题。

|                    Type: | list               |
| -----------------------: | ------------------ |
|                 Default: | delete             |
|            Valid Values: | [compact, delete]  |
| Server Default Property: | log.cleanup.policy |
|              Importance: | medium             |

#### [compression.type压缩类型](http://kafka.apache.org/documentation/#compression.type)

  Specify the final compression type for a given topic. This configuration accepts the standard compression codecs ('gzip', 'snappy', 'lz4', 'zstd'). It additionally accepts 'uncompressed' which is equivalent to no compression; and 'producer' which means retain the original compression codec set by the producer.

  指定给定主题的最终压缩类型。该配置接受标准的压缩编解码器('gzip'、'snappy'、'lz4'、'zstd')。它另外接受“未压缩”，这相当于没有压缩;和“producer”，即保留由生成器设置的原始压缩编解码器

|                    Type: | string                                            |
| -----------------------: | ------------------------------------------------- |
|                 Default: | producer                                          |
|            Valid Values: | [uncompressed, zstd, lz4, snappy, gzip, producer] |
| Server Default Property: | compression.type                                  |
|              Importance: | medium                                            |

#### [delete.retention.ms](http://kafka.apache.org/documentation/#delete.retention.ms)

  The amount of time to retain delete tombstone markers for [log compacted](http://kafka.apache.org/documentation/#compaction) topics. This setting also gives a bound on the time in which a consumer must complete a read if they begin from offset 0 to ensure that they get a valid snapshot of the final stage (otherwise delete tombstones may be collected before they complete their scan).

  为[log compacted](http://kafka.apache.org/documentation/#compaction)主题保留delete tombstone标记所需的时间。如果消费者从偏移量0开始，这个设置还提供了一个时间的限制，即他们必须在这个时间内完成读取，以确保他们获得最后阶段的有效快照(否则删除tombstones可能会在他们完成扫描之前被收集)。

|                    Type: | long                            |
| -----------------------: | ------------------------------- |
|                 Default: | 86400000 (1 day)                |
|            Valid Values: | [0,...]                         |
| Server Default Property: | log.cleaner.delete.retention.ms |
|              Importance: | medium                          |

#### [file.delete.delay.ms](http://kafka.apache.org/documentation/#file.delete.delay.ms)

  The time to wait before deleting a file from the filesystem

  从文件系统中删除文件之前等待的时间

|                    Type: | long                        |
| -----------------------: | --------------------------- |
|                 Default: | 60000 (1 minute)            |
|            Valid Values: | [0,...]                     |
| Server Default Property: | log.segment.delete.delay.ms |
|              Importance: | medium                      |

#### [flush.messages](http://kafka.apache.org/documentation/#flush.messages)

  This setting allows specifying an interval at which we will force an fsync of data written to the log. For example if this was set to 1 we would fsync after every message; if it were 5 we would fsync after every five messages. In general we recommend you not set this and use replication for durability and allow the operating system's background flush capabilities as it is more efficient. This setting can be overridden on a per#### [the per-topic configuration section](http://kafka.apache.org/documentation/#topicconfigs)).

  此设置允许指定一个时间间隔，在该时间间隔，我们将强制将fsync数据写入日志。例如，如果这个被设为1我们会在每条消息后进行fsync;如果是5，我们会在每5条消息后进行fsync。一般来说，我们建议您不要设置这个值，而是使用复制（replication）来保证持久性，并允许操作系统的后台刷新功能，因为这样更有效。这个设置可以在每个主题的基础上被覆盖(请参阅[每个主题配置部分](http://kafka.apache.org/documentation/#topicconfigs))。

|                    Type: | long                        |
| -----------------------: | --------------------------- |
|                 Default: | 9223372036854775807         |
|            Valid Values: | [0,...]                     |
| Server Default Property: | log.flush.interval.messages |
|              Importance: | medium                      |

#### [flush.ms](http://kafka.apache.org/documentation/#flush.ms)

  This setting allows specifying a time interval at which we will force an fsync of data written to the log. For example if this was set to 1000 we would fsync after 1000 ms had passed. In general we recommend you not set this and use replication for durability and allow the operating system's background flush capabilities as it is more efficient.

  此设置允许指定一个时间间隔，在这个时间间隔，我们将强制将fsync数据写入日志。例如，如果这个值设置为1000，我们将在1000毫秒之后进行fsync。一般来说，我们建议您不要设置这个值，而是使用复制来保证持久性，并允许操作系统的后台刷新功能，因为这样更有效

|                    Type: | long                  |
| -----------------------: | --------------------- |
|                 Default: | 9223372036854775807   |
|            Valid Values: | [0,...]               |
| Server Default Property: | log.flush.interval.ms |
|              Importance: | medium                |

#### [follower.replication.throttled.replicas](http://kafka.apache.org/documentation/#follower.replication.throttled.replicas)

  A list of replicas for which log replication should be throttled on the follower side. The list should describe a set of replicas in the form [PartitionId]:[BrokerId],[PartitionId]:[BrokerId]:... or alternatively the wildcard '*' can be used to throttle all replicas for this topic.

应该在跟随者端对其日志复制进行限制的副本列表。该列表应该描述一组副本，其形式为[PartitionId]:[BrokerId]，[PartitionId]:[BrokerId]:…或者，也可以使用通配符'*'来限制此主题的所有副本。

|                    Type: | list                                                  |
| -----------------------: | ----------------------------------------------------- |
|                 Default: | ""                                                    |
|            Valid Values: | [partitionId]:[brokerId],[partitionId]:[brokerId],... |
| Server Default Property: | follower.replication.throttled.replicas               |
|              Importance: | medium                                                |

#### [index.interval.bytes多少字节建个索引](http://kafka.apache.org/documentation/#index.interval.bytes)

  This setting controls how frequently Kafka adds an index entry to its offset index. The default setting ensures that we index a message roughly every 4096 bytes. More indexing allows reads to jump closer to the exact position in the log but makes the index larger. You probably don't need to change this.

此设置控制Kafka向其偏移索引添加索引条目的频率。默认设置确保我们大约每4096字节索引一条消息。更多的索引允许读取更接近日志中的确切位置，但使索引更大。你可能不需要改变这一点。

|                    Type: | int                      |
| -----------------------: | ------------------------ |
|                 Default: | 4096 (4 kibibytes)       |
|            Valid Values: | [0,...]                  |
| Server Default Property: | log.index.interval.bytes |
|              Importance: | medium                   |

#### [leader.replication.throttled.replicas](http://kafka.apache.org/documentation/#leader.replication.throttled.replicas)

  A list of replicas for which log replication should be throttled on the leader side. The list should describe a set of replicas in the form [PartitionId]:[BrokerId],[PartitionId]:[BrokerId]:... or alternatively the wildcard '*' can be used to throttle all replicas for this topic.

一个副本列表，它的日志复制应该在leader端被限制。该列表应该描述一组副本，其形式为[PartitionId]:[BrokerId]，[PartitionId]:[BrokerId]:…或者，也可以使用通配符'*'来限制此主题的所有副本

|                    Type: | list                                                  |
| -----------------------: | ----------------------------------------------------- |
|                 Default: | ""                                                    |
|            Valid Values: | [partitionId]:[brokerId],[partitionId]:[brokerId],... |
| Server Default Property: | leader.replication.throttled.replicas                 |
|              Importance: | medium                                                |

#### [max.compaction.lag.ms](http://kafka.apache.org/documentation/#max.compaction.lag.ms)

  The maximum time a message will remain ineligible for compaction in the log. Only applicable for logs that are being compacted.

消息在日志中不符合压缩条件的最长时间。仅适用于正在压缩的日志。

|                    Type: | long                              |
| -----------------------: | --------------------------------- |
|                 Default: | 9223372036854775807               |
|            Valid Values: | [1,...]                           |
| Server Default Property: | log.cleaner.max.compaction.lag.ms |
|              Importance: | medium                            |

#### [max.message.bytes](http://kafka.apache.org/documentation/#max.message.bytes)

  The largest record batch size allowed by Kafka (after compression if compression is enabled). If this is increased and there are consumers older than 0.10.2, the consumers' fetch size must also be increased so that they can fetch record batches this large. In the latest message format version, records are always grouped into batches for efficiency. In previous message format versions, uncompressed records are not grouped into batches and this limit only applies to a single record in that case.

Kafka允许的最大记录批大小(如果启用压缩，则在压缩之后)。如果增加这个值，并且有超过0.10.2的使用者，那么使用者的获取大小也必须增加，以便他们能够获取这么大的记录批。在最新的消息格式版本中，为了提高效率，记录总是分组成批。在以前的消息格式版本中，未压缩的记录不会分组成批，在这种情况下，此限制仅适用于单个记录。

|                    Type: | int               |
| -----------------------: | ----------------- |
|                 Default: | 1048588           |
|            Valid Values: | [0,...]           |
| Server Default Property: | message.max.bytes |
|              Importance: | medium            |

#### [message.format.version](http://kafka.apache.org/documentation/#message.format.version)

  Specify the message format version the broker will use to append messages to the logs. The value should be a valid ApiVersion. Some examples are: 0.8.2, 0.9.0.0, 0.10.0, check ApiVersion for more details. By setting a particular message format version, the user is certifying that all the existing messages on disk are smaller or equal than the specified version. Setting this value incorrectly will cause consumers with older versions to break as they will receive messages with a format that they don't understand.

指定代理将用于将消息追加到日志的消息格式版本。该值应该是一个有效的ApiVersion。一些例子是:0.8.2,0.9.0.0,0.10.0，查看ApiVersion获得更多细节。通过设置特定的消息格式版本，用户可以确认磁盘上现有的所有消息都小于或等于指定的版本。不正确地设置此值将导致使用旧版本的用户崩溃，因为他们将接收到他们不理解的格式的消息

|                    Type: | string                                                       |
| -----------------------: | ------------------------------------------------------------ |
|                 Default: | 2.6-IV0                                                      |
|            Valid Values: | [0.8.0, 0.8.1, 0.8.2, 0.9.0, 0.10.0-IV0, 0.10.0-IV1, 0.10.1-IV0, 0.10.1-IV1, 0.10.1-IV2, 0.10.2-IV0, 0.11.0-IV0, 0.11.0-IV1, 0.11.0-IV2, 1.0-IV0, 1.1-IV0, 2.0-IV0, 2.0-IV1, 2.1-IV0, 2.1-IV1, 2.1-IV2, 2.2-IV0, 2.2-IV1, 2.3-IV0, 2.3-IV1, 2.4-IV0, 2.4-IV1, 2.5-IV0, 2.6-IV0] |
| Server Default Property: | log.message.format.version                                   |
|              Importance: | medium                                                       |

#### [message.timestamp.difference.max.ms](http://kafka.apache.org/documentation/#message.timestamp.difference.max.ms)

  The maximum difference allowed between the timestamp when a broker receives a message and the timestamp specified in the message. If message.timestamp.type=CreateTime, a message will be rejected if the difference in timestamp exceeds this threshold. This configuration is ignored if message.timestamp.type=LogAppendTime.

代理接收消息时的时间戳与消息中指定的时间戳之间允许的最大差异。如果message.timestamp.type=CreateTime，如果时间戳的差异超过此阈值，则消息将被拒绝。如果message.timestamp.type=LogAppendTime，则忽略此配置

|                    Type: | long                                    |
| -----------------------: | --------------------------------------- |
|                 Default: | 9223372036854775807                     |
|            Valid Values: | [0,...]                                 |
| Server Default Property: | log.message.timestamp.difference.max.ms |
|              Importance: | medium                                  |

#### [message.timestamp.type](http://kafka.apache.org/documentation/#message.timestamp.type)

  Define whether the timestamp in the message is message create time or log append time. The value should be either `CreateTime` or `LogAppendTime`

定义消息中的时间戳是消息创建时间还是日志追加时间。值应该是' CreateTime '或' LogAppendTime '

|                    Type: | string                      |
| -----------------------: | --------------------------- |
|                 Default: | CreateTime                  |
|            Valid Values: | [CreateTime, LogAppendTime] |
| Server Default Property: | log.message.timestamp.type  |
|              Importance: | medium                      |

#### [min.cleanable.dirty.ratio](http://kafka.apache.org/documentation/#min.cleanable.dirty.ratio)

  This configuration controls how frequently the log compactor will attempt to clean the log (assuming [log compaction](http://kafka.apache.org/documentation/#compaction) is enabled). By default we will avoid cleaning a log where more than 50% of the log has been compacted. This ratio bounds the maximum space wasted in the log by duplicates (at 50% at most 50% of the log could be duplicates). A higher ratio will mean fewer, more efficient cleanings but will mean more wasted space in the log. If the max.compaction.lag.ms or the min.compaction.lag.ms configurations are also specified, then the log compactor considers the log to be eligible for compaction as soon as either: (i) the dirty ratio threshold has been met and the log has had dirty (uncompacted) records for at least the min.compaction.lag.ms duration, or (ii) if the log has had dirty (uncompacted) records for at most the max.compaction.lag.ms period.

这个配置控制日志压缩器尝试清理日志的频率(假设启用了[log compaction](http://kafka.apache.org/documentation/#compaction))。默认情况下，我们将避免清理已压缩超过50%的日志。这个比率限制了重复日志所浪费的最大空间(最多50%的日志是重复的)。较高的比率意味着更少、更有效的清理，但也意味着日志中浪费的空间更多。如果max.compaction.lag.ms或min.compaction.lag.ms配置也指定,然后日志压缩机认为日志就有资格获得压实:(i)脏比率阈值满足和日志已经脏(未压实的)记录至少min.compaction.lag.ms持续时间,或(ii)如果日志已经脏(未压实的)记录最多max.compaction.lag.ms时期。

|                    Type: | double                          |
| -----------------------: | ------------------------------- |
|                 Default: | 0.5                             |
|            Valid Values: | [0,...,1]                       |
| Server Default Property: | log.cleaner.min.cleanable.ratio |
|              Importance: | medium                          |

#### [min.compaction.lag.ms](http://kafka.apache.org/documentation/#min.compaction.lag.ms)

  The minimum time a message will remain uncompacted in the log. Only applicable for logs that are being compacted.

消息在日志中保持未压缩状态的最小时间。仅适用于正在压缩的日志。

|                    Type: | long                              |
| -----------------------: | --------------------------------- |
|                 Default: | 0                                 |
|            Valid Values: | [0,...]                           |
| Server Default Property: | log.cleaner.min.compaction.lag.ms |
|              Importance: | medium                            |

#### [min.insync.replicas](http://kafka.apache.org/documentation/#min.insync.replicas)

  When a producer sets acks to "all" (or "-1"), this configuration specifies the minimum number of replicas that must acknowledge a write for the write to be considered successful. If this minimum cannot be met, then the producer will raise an exception (either NotEnoughReplicas or NotEnoughReplicasAfterAppend).
  When used together, `min.insync.replicas` and `acks` allow you to enforce greater durability guarantees. A typical scenario would be to create a topic with a replication factor of 3, set `min.insync.replicas` to 2, and produce with `acks` of "all". This will ensure that the producer raises an exception if a majority of replicas do not receive a write.

当生产者将acks设置为“all”(或“-1”)时，此配置指定必须确认写入操作的最小副本数量，以便认为写入操作成功。如果不能满足这个最小值，那么生成器将抛出一个异常(NotEnoughReplicas或NotEnoughReplicasAfterAppend)。当一起使用时，可以用min.insynco.replicas'和' acks '允许您强制执行更大的持久性保证。一个典型的场景是创建一个复制因子为3的主题，设置min.insync.replicas'到2，并与' acks '的“所有”。这将确保在大多数副本没有收到写操作时，生成程序会引发异常。

|                    Type: | int                 |
| -----------------------: | ------------------- |
|                 Default: | 1                   |
|            Valid Values: | [1,...]             |
| Server Default Property: | min.insync.replicas |
|              Importance: | medium              |

#### [preallocate](http://kafka.apache.org/documentation/#preallocate)

  True if we should preallocate the file on disk when creating a new log segment.

如果在创建新的日志段时应该在磁盘上预先分配文件，则为true。

|                    Type: | boolean         |
| -----------------------: | --------------- |
|                 Default: | false           |
|            Valid Values: |                 |
| Server Default Property: | log.preallocate |
|              Importance: | medium          |

#### [retention.bytes](http://kafka.apache.org/documentation/#retention.bytes)

  This configuration controls the maximum size a partition (which consists of log segments) can grow to before we will discard old log segments to free up space if we are using the "delete" retention policy. By default there is no size limit only a time limit. Since this limit is enforced at the partition level, multiply it by the number of partitions to compute the topic retention in bytes.

如果我们使用“删除”保留策略，在我们丢弃旧的日志段以释放空间之前，这个配置控制一个分区(由日志段组成)可以增长到的最大大小。默认情况下没有大小限制，只有时间限制。由于这个限制是在分区级别强制执行的，所以用它乘以分区数就可以计算以字节为单位的主题保留量。

|                    Type: | long                |
| -----------------------: | ------------------- |
|                 Default: | -1                  |
|            Valid Values: |                     |
| Server Default Property: | log.retention.bytes |
|              Importance: | medium              |

#### [retention.ms](http://kafka.apache.org/documentation/#retention.ms)

  This configuration controls the maximum time we will retain a log before we will discard old log segments to free up space if we are using the "delete" retention policy. This represents an SLA on how soon consumers must read their data. If set to -1, no time limit is applied.

如果使用“删除”保留策略，在丢弃旧日志段以释放空间之前，此配置控制保留日志的最大时间。这表示关于用户必须多长时间读取数据的SLA。如果设置为-1，则不应用时间限制。

|                    Type: | long               |
| -----------------------: | ------------------ |
|                 Default: | 604800000 (7 days) |
|            Valid Values: | [-1,...]           |
| Server Default Property: | log.retention.ms   |
|              Importance: | medium             |

#### [segment.bytes](http://kafka.apache.org/documentation/#segment.bytes)

  This configuration controls the segment file size for the log. Retention and cleaning is always done a file at a time so a larger segment size means fewer files but less granular control over retention.

此配置控制日志的段文件大小。保留和清理总是一次对一个文件进行，因此更大的段大小意味着更少的文件，但对保留的粒度控制更少

|                    Type: | int                     |
| -----------------------: | ----------------------- |
|                 Default: | 1073741824 (1 gibibyte) |
|            Valid Values: | [14,...]                |
| Server Default Property: | log.segment.bytes       |
|              Importance: | medium                  |

#### [segment.index.bytes](http://kafka.apache.org/documentation/#segment.index.bytes)

  This configuration controls the size of the index that maps offsets to file positions. We preallocate this index file and shrink it only after log rolls. You generally should not need to change this setting.

此配置控制将偏移量映射到文件位置的索引的大小。我们预先分配这个索引文件，只有在日志滚动之后才收缩它。通常不需要更改此设置。

|                    Type: | int                      |
| -----------------------: | ------------------------ |
|                 Default: | 10485760 (10 mebibytes)  |
|            Valid Values: | [0,...]                  |
| Server Default Property: | log.index.size.max.bytes |
|              Importance: | medium                   |

#### [segment.jitter.ms](http://kafka.apache.org/documentation/#segment.jitter.ms)

  The maximum random jitter subtracted from the scheduled segment roll time to avoid thundering herds of segment rolling

从预定分段滚动时间中减去的最大随机抖动，以避免轰鸣成群的分段滚动

|                    Type: | long               |
| -----------------------: | ------------------ |
|                 Default: | 0                  |
|            Valid Values: | [0,...]            |
| Server Default Property: | log.roll.jitter.ms |
|              Importance: | medium             |

#### [segment.ms](http://kafka.apache.org/documentation/#segment.ms)

  This configuration controls the period of time after which Kafka will force the log to roll even if the segment file isn't full to ensure that retention can delete or compact old data.

这个配置控制了Kafka强制日志滚动的时间，即使段文件没有满，以确保保留可以删除或压缩旧数据。

|                    Type: | long               |
| -----------------------: | ------------------ |
|                 Default: | 604800000 (7 days) |
|            Valid Values: | [1,...]            |
| Server Default Property: | log.roll.ms        |
|              Importance: | medium             |

#### [unclean.leader.election.enable](http://kafka.apache.org/documentation/#unclean.leader.election.enable)

  Indicates whether to enable replicas not in the ISR set to be elected as leader as a last resort, even though doing so may result in data loss.

指示是否在最后不得已的情况下启用ISR集中以外的副本作为leader，即使这样做可能导致数据丢失。

|                    Type: | boolean                        |
| -----------------------: | ------------------------------ |
|                 Default: | false                          |
|            Valid Values: |                                |
| Server Default Property: | unclean.leader.election.enable |
|              Importance: | medium                         |

#### [message.downconversion.enable](http://kafka.apache.org/documentation/#message.downconversion.enable)

  This configuration controls whether down-conversion of message formats is enabled to satisfy consume requests. When set to `false`, broker will not perform down-conversion for consumers expecting an older message format. The broker responds with `UNSUPPORTED_VERSION` error for consume requests from such older clients. This configurationdoes not apply to any message format conversion that might be required for replication to followers.

此配置控制是否启用消息格式的下转换以满足消费请求。当设置为“false”时，代理将不会为希望使用旧消息格式的用户执行下转换。对于来自这些较老客户端的消费请求，代理会以“UNSUPPORTED_VERSION”错误响应。此配置不适用于复制到关注者可能需要的任何消息格式转换。

|                    Type: | boolean                           |
| -----------------------: | --------------------------------- |
|                 Default: | true                              |
|            Valid Values: |                                   |
| Server Default Property: | log.message.downconversion.enable |
|              Importance: | low                               |

### [3.3 Producer Configs](http://kafka.apache.org/documentation/#producerconfigs)

Below is the configuration of the producer:

#### [key.serializer](http://kafka.apache.org/documentation/#key.serializer)

  Serializer class for key that implements the `org.apache.kafka.common.serialization.Serializer` interface.

实现了' org.apache.kafka.common.serialization.Serializer '接口的键的Serializer类。

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [value.serializer](http://kafka.apache.org/documentation/#value.serializer)

  Serializer class for value that implements the `org.apache.kafka.common.serialization.Serializer` interface.

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [acks](http://kafka.apache.org/documentation/#acks)

  The number of acknowledgments the producer requires the leader to have received before considering a request complete. This controls the durability of records that are sent. The following settings are allowed:

生产者要求领导者在考虑完成请求之前收到的确认数量。它控制发送的记录的持久性。允许以下设置:

  - `acks=0` If set to zero then the producer will not wait for any acknowledgment from the server at all. The record will be immediately added to the socket buffer and considered sent. No guarantee can be made that the server has received the record in this case, and the `retries` configuration will not take effect (as the client won't generally know of any failures). The offset given back for each record will always be set to `-1`.

    如果设置为0，那么生成器将根本不等待来自服务器的任何确认。该记录将立即添加到套接字缓冲区并被认为已发送。在这种情况下，不能保证服务器已经收到了记录，“retries”配置将不会生效(因为客户机通常不会知道任何失败)。为每条记录返回的偏移量将始终设置为“-1”。

  - `acks=1` This will mean the leader will write the record to its local log but will respond without awaiting full acknowledgement from all followers. In this case should the leader fail immediately after acknowledging the record but before the followers have replicated it then the record will be lost.

    acks=1这意味着领导者将把记录写入本地日志，但将不等待所有追随者的完全确认而响应。在这种情况下，如果领导者在确认记录后立即失败，但是在追随者复制它之前，那么记录将丢失。

  - `acks=all` This means the leader will wait for the full set of in-sync replicas to acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.

  ' acks=all '这意味着leader将等待完整的同步副本集合来确认记录。这保证了只要至少有一个同步副本保持活动状态，记录就不会丢失。这是现有的最有力的保证。这等价于ack =-1的设置。

|         Type: | string          |
| ------------: | --------------- |
|      Default: | 1               |
| Valid Values: | [all, -1, 0, 1] |
|   Importance: | high            |

#### [bootstrap.servers](http://kafka.apache.org/documentation/#bootstrap.servers)

  A list of host/port pairs to use for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2,...`. Since these servers are just used for the initial connection to discover the full cluster membership (which may change dynamically), this list need not contain the full set of servers (you may want more than one, though, in case a server is down).

用于建立到Kafka集群的初始连接的主机/端口对列表。客户机将使用所有服务器，而不管这里为引导指定了哪些服务器——此列表只影响用于发现完整服务器集的初始主机。该列表应该以“host1:port1,host2:port2，…”的形式出现。由于这些服务器仅用于初始连接，以发现完整的集群成员关系(可能会动态更改)，因此此列表不需要包含完整的服务器集(不过，如果服务器宕机，您可能需要多个服务器)。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | high            |

#### [buffer.memory](http://kafka.apache.org/documentation/#buffer.memory)

  The total bytes of memory the producer can use to buffer records waiting to be sent to the server. If records are sent faster than they can be delivered to the server the producer will block for `max.block.ms` after which it will throw an exception.

生成器可用于缓冲等待发送到服务器的记录的内存总字节。如果记录发送的速度比它们发送到服务器的速度快，producer将阻塞“max.block.ms”之后，它将抛出一个异常。

  This setting should correspond roughly to the total memory the producer will use, but is not a hard bound since not all memory the producer uses is used for buffering. Some additional memory will be used for compression (if compression is enabled) as well as for maintaining in-flight requests.

这个设置应该大致对应于producer将使用的总内存，但不是硬性限制，因为生成器使用的所有内存都用于缓冲。一些额外的内存将用于压缩(如果启用了压缩)以及维护动态请求。

|         Type: | long     |
| ------------: | -------- |
|      Default: | 33554432 |
| Valid Values: | [0,...]  |
|   Importance: | high     |

#### [compression.type](http://kafka.apache.org/documentation/#compression.type)

  The compression type for all data generated by the producer. The default is none (i.e. no compression). Valid values are `none`, `gzip`, `snappy`, `lz4`, or `zstd`. Compression is of full batches of data, so the efficacy of batching will also impact the compression ratio (more batching means better compression).

生成器生成的所有数据的压缩类型。默认值是none(即没有压缩)。有效值是' none '、' gzip '、' snappy '、' lz4 '或' zstd '。压缩是整批的数据，因此批处理的效率也会影响压缩比(批处理越多，压缩效果越好)。

|         Type: | string |
| ------------: | ------ |
|      Default: | none   |
| Valid Values: |        |
|   Importance: | high   |

#### [retries](http://kafka.apache.org/documentation/#retries)

  Setting a value greater than zero will cause the client to resend any record whose send fails with a potentially transient error. Note that this retry is no different than if the client resent the record upon receiving the error. Allowing retries without setting `max.in.flight.requests.per.connection` to 1 will potentially change the ordering of records because if two batches are sent to a single partition, and the first fails and is retried but the second succeeds, then the records in the second batch may appear first. Note additionally that produce requests will be failed before the number of retries has been exhausted if the timeout configured by `delivery.timeout.ms` expires first before successful acknowledgement. Users should generally prefer to leave this config unset and instead use `delivery.timeout.ms` to control retry behavior.

设置一个大于零的值将导致客户端重新发送任何发送失败并可能出现瞬态错误的记录。请注意，此重试与客户端在接收到错误后重新发送记录没有区别。允许在没有设置“max.in. flights .requests.per.connection”' to 1的情况下重试可能会改变记录的顺序，因为如果将两个批发送到单个分区，第一个批失败并重试，但是第二个批成功，那么第二个批中的记录可能会首先出现。另外请注意，如果‘delivery.timeout.ms’配置的超时，在重试次数耗尽之前，生成请求将失败。确认成功之前的文件先过期。用户通常应该选择不设置这个配置，而使用“delivery.timeout.ms”。控制重试行为。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 2147483647         |
| Valid Values: | [0,...,2147483647] |
|   Importance: | high               |

#### [ssl.key.password](http://kafka.apache.org/documentation/#ssl.key.password)

  The password of the private key in the key store file. This is optional for client.

密钥存储文件中私钥的密码。这对于客户机是可选的。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.keystore.location](http://kafka.apache.org/documentation/#ssl.keystore.location)

  The location of the key store file. This is optional for client and can be used for two-way authentication for client.

密钥存储文件的位置。这对于客户机是可选的，可以用于客户机的双向身份验证。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.keystore.password](http://kafka.apache.org/documentation/#ssl.keystore.password)

  The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.

密钥存储文件的存储密码。这对于客户机是可选的，只有在ssl.keystore中才需要。位置配置。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.truststore.location](http://kafka.apache.org/documentation/#ssl.truststore.location)

  The location of the trust store file.

信任存储库文件的位置。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.truststore.password](http://kafka.apache.org/documentation/#ssl.truststore.password)

  The password for the trust store file. If a password is not set access to the truststore is still available, but integrity checking is disabled.

信任存储文件的密码。如果没有设置密码，仍然可以访问信任存储库，但是禁用了完整性检查。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [batch.size](http://kafka.apache.org/documentation/#batch.size)

  The producer will attempt to batch records together into fewer requests whenever multiple records are being sent to the same partition. This helps performance on both the client and the server. This configuration controls the default batch size in bytes.

每当多个记录被发送到同一个分区时，生成器将尝试将记录批处理成更少的请求。这有助于提高客户机和服务器的性能。此配置以字节为单位控制默认批处理大小。

  No attempt will be made to batch records larger than this size.

不会尝试对大于此大小的记录进行批处理。

  Requests sent to brokers will contain multiple batches, one for each partition with data available to be sent.

发送到代理的请求将包含多个批处理，每个分区都有一个批处理，其中有可供发送的数据。

  A small batch size will make batching less common and may reduce throughput (a batch size of zero will disable batching entirely). A very large batch size may use memory a bit more wastefully as we will always allocate a buffer of the specified batch size in anticipation of additional records.

较小的批处理大小将使批处理不那么常见，并可能降低吞吐量(批处理大小为零将完全禁用批处理)。非常大的批处理可能会更加浪费内存，因为我们总是会分配指定批处理大小的缓冲区，以预期会有更多的记录。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 16384   |
| Valid Values: | [0,...] |
|   Importance: | medium  |

#### [client.dns.lookup](http://kafka.apache.org/documentation/#client.dns.lookup)

  Controls how the client uses DNS lookups. If set to `use_all_dns_ips`, connect to each returned IP address in sequence until a successful connection is established. After a disconnection, the next IP is used. Once all IPs have been used once, the client resolves the IP(s) from the hostname again (both the JVM and the OS cache DNS name lookups, however). If set to `resolve_canonical_bootstrap_servers_only`, resolve each bootstrap address into a list of canonical names. After the bootstrap phase, this behaves the same as `use_all_dns_ips`. If set to `default` (deprecated), attempt to connect to the first IP address returned by the lookup, even if the lookup returns multiple IP addresses.

控制客户端如何使用DNS查找。如果设置为' use_all_dns_ips '，依次连接到每个返回的IP地址，直到建立成功的连接。断开连接后，使用下一个IP。一旦所有IP都使用过一次，客户机就会再次从主机名解析IP(不过JVM和OS都会缓存DNS名称查找)。如果设置为' resolve_canonical_bootstrap_servers_only '，则将每个引导程序地址解析为一个规范名称列表。在引导阶段之后，它的行为与' use_all_dns_ips '相同。如果设置为‘default’(不赞成使用)，尝试连接到查找返回的第一个IP地址，即使查找返回多个IP地址。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | use_all_dns_ips                                              |
| Valid Values: | [default, use_all_dns_ips, resolve_canonical_bootstrap_servers_only] |
|   Importance: | medium                                                       |

#### [client.id](http://kafka.apache.org/documentation/#client.id)

  An id string to pass to the server when making requests. The purpose of this is to be able to track the source of requests beyond just ip/port by allowing a logical application name to be included in server-side request logging.

在发出请求时传递给服务器的id字符串。这样做的目的是通过允许在服务器端请求日志记录中包含逻辑应用程序名称，从而能够跟踪ip/端口之外的请求源。

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | medium |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Close idle connections after the number of milliseconds specified by this config.

在此配置指定的毫秒数之后关闭空闲连接。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 540000 (9 minutes) |
| Valid Values: |                    |
|   Importance: | medium             |

#### [delivery.timeout.ms](http://kafka.apache.org/documentation/#delivery.timeout.ms)

  An upper bound on the time to report success or failure after a call to `send()` returns. This limits the total time that a record will be delayed prior to sending, the time to await acknowledgement from the broker (if expected), and the time allowed for retriable send failures. The producer may report failure to send a record earlier than this config if either an unrecoverable error is encountered, the retries have been exhausted, or the record is added to a batch which reached an earlier delivery expiration deadline. The value of this config should be greater than or equal to the sum of `request.timeout.ms` and `linger.ms`.

调用' send() '后报告成功或失败的时间上限返回。这限制了记录在发送前延迟的总时间、等待代理确认的时间(如果预期的话)以及可恢复发送失败的允许时间。如果遇到不可恢复的错误，重试已经结束，或者记录被添加到到达较早交付截止日期的批处理中，生产者可能会报告在此配置之前发送记录失败。这个配置的值应该大于或等于' request.timeout '的总和。女士”和“linger.ms”。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 120000 (2 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |

#### [linger.ms](http://kafka.apache.org/documentation/#linger.ms)

  The producer groups together any records that arrive in between request transmissions into a single batched request. Normally this occurs only under load when records arrive faster than they can be sent out. However in some circumstances the client may want to reduce the number of requests even under moderate load. This setting accomplishes this by adding a small amount of artificial delay—that is, rather than immediately sending out a record the producer will wait for up to the given delay to allow other records to be sent so that the sends can be batched together. This can be thought of as analogous to Nagle's algorithm in TCP. This setting gives the upper bound on the delay for batching: once we get `batch.size` worth of records for a partition it will be sent immediately regardless of this setting, however if we have fewer than this many bytes accumulated for this partition we will 'linger' for the specified time waiting for more records to show up. This setting defaults to 0 (i.e. no delay). Setting `linger.ms=5`, for example, would have the effect of reducing the number of requests sent but would add up to 5ms of latency to records sent in the absence of load.

制作人将在请求传输之间到达的任何记录分组为单个批处理请求。通常情况下，只有当记录到达的速度比发送的速度快时，才会发生这种情况。然而，在某些情况下，即使在中等负载下，客户端也可能希望减少请求的数量。此设置通过添加少量的人为延迟来实现这一点，即，不是立即发送一个记录，生产者将等待到给定的延迟，以允许发送其他记录，以便发送的记录可以成批一起发送。这可以看作类似于TCP中的Nagle算法。这个设置给出了批处理延迟的上限:一旦我们得到“batch”。不管这个设置如何，它都会被立即发送，但是如果我们为这个分区积累的字节数少于这个数量，我们就会在指定的时间内等待更多的记录出现。此设置的默认值为0(即没有延迟)。设置的徘徊。例如，ms=5 '可以减少发送的请求数量，但在没有负载的情况下，发送的记录的延迟将增加到5ms。

|         Type: | long    |
| ------------: | ------- |
|      Default: | 0       |
| Valid Values: | [0,...] |
|   Importance: | medium  |

#### [max.block.ms](http://kafka.apache.org/documentation/#max.block.ms)

  The configuration controls how long `KafkaProducer.send()` and `KafkaProducer.partitionsFor()` will block.These methods can be blocked either because the buffer is full or metadata unavailable.Blocking in the user-supplied serializers or partitioner will not be counted against this timeout.

配置控制' KafkaProducer.send() '和' KafkaProducer.partitionsFor() '将阻塞多长时间。这些方法可能因为缓冲区已满或元数据不可用而被阻塞。用户提供的序列化器或分拆器中的阻塞将不计入此超时。

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | medium           |

#### [max.request.size](http://kafka.apache.org/documentation/#max.request.size)

  The maximum size of a request in bytes. This setting will limit the number of record batches the producer will send in a single request to avoid sending huge requests. This is also effectively a cap on the maximum uncompressed record batch size. Note that the server has its own cap on the record batch size (after compression if compression is enabled) which may be different from this.

请求的最大大小，以字节为单位。此设置将限制生成程序在单个请求中发送的记录批的数量，以避免发送大量请求。这也有效地限制了未压缩的最大记录批大小。注意，服务器对记录批处理大小有自己的上限(如果启用了压缩，则在压缩之后)，上限可能与此不同。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 1048576 |
| Valid Values: | [0,...] |
|   Importance: | medium  |

#### [partitioner.class](http://kafka.apache.org/documentation/#partitioner.class)

  Partitioner class that implements the `org.apache.kafka.clients.producer.Partitioner` interface.

|         Type: | class                                                        |
| ------------: | ------------------------------------------------------------ |
|      Default: | org.apache.kafka.clients.producer.internals.DefaultPartitioner |
| Valid Values: |                                                              |
|   Importance: | medium                                                       |

#### [receive.buffer.bytes](http://kafka.apache.org/documentation/#receive.buffer.bytes)

  The size of the TCP receive buffer (SO_RCVBUF) to use when reading data. If the value is -1, the OS default will be used.

读取数据时要使用的TCP接收缓冲区(SO_RCVBUF)的大小。如果值为-1，将使用OS默认值。

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 32768 (32 kibibytes) |
| Valid Values: | [-1,...]             |
|   Importance: | medium               |



#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted. This should be larger than `replica.lag.time.max.ms` (a broker configuration) to reduce the possibility of message duplication due to unnecessary producer retries.

配置控制客户端等待请求响应的最大时间量。如果在超时结束前没有收到响应，客户端将在必要时重新发送请求，或者在重试耗尽时请求失败。这个应该比copy .lag.time.max.ms大。减少由于不必要的生产者重试而导致的消息重复的可能性。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |

#### [sasl.client.callback.handler.class](http://kafka.apache.org/documentation/#sasl.client.callback.handler.class)

  The fully qualified name of a SASL client callback handler class that implements the AuthenticateCallbackHandler interface.

实现AuthenticateCallbackHandler接口的SASL客户端回调处理程序类的完全限定名

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.jaas.config](http://kafka.apache.org/documentation/#sasl.jaas.config)

  JAAS login context parameters for SASL connections in the format used by JAAS configuration files. JAAS configuration file format is described [here](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html). The format for the value is: '`loginModuleClass controlFlag (optionName=optionValue)*;`'. For brokers, the config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=com.example.ScramLoginModule required;

使用JAAS配置文件使用的格式为SASL连接的JAAS登录上下文参数。这里描述了JAAS配置文件格式。该值的格式是:' ' loginModuleClass controlFlag (optionName=optionValue)*; "。对于代理，配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如，listen .name.sasl_ssl. scramloginmodule required;

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | medium   |

#### [sasl.kerberos.service.name](http://kafka.apache.org/documentation/#sasl.kerberos.service.name)

  The Kerberos principal name that Kafka runs as. This can be defined either in Kafka's JAAS config or in Kafka's config.

Kafka运行时的Kerberos主体名。这可以在卡夫卡的JAAS配置或卡夫卡的配置中定义

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.callback.handler.class](http://kafka.apache.org/documentation/#sasl.login.callback.handler.class)

  The fully qualified name of a SASL login callback handler class that implements the AuthenticateCallbackHandler interface. For brokers, login callback handler config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class=com.example.CustomScramLoginCallbackHandler

实现AuthenticateCallbackHandler接口的SASL登录回调处理程序类的完全限定名。对于代理，登录回调处理程序配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如,listener.name.sasl_ssl.scram - sha - 256. - sasl.login.callback.handler.class = com.example.CustomScramLoginCallbackHandler

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |



#### [sasl.login.class](http://kafka.apache.org/documentation/#sasl.login.class)

  The fully qualified name of a class that implements the Login interface. For brokers, login config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.class=com.example.CustomScramLogin

实现登录接口的类的完全限定名。对于代理，登录配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如,listener.name.sasl_ssl.scram - sha - 256. - sasl.login.class = com.example.CustomScramLogin

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.mechanism](http://kafka.apache.org/documentation/#sasl.mechanism)

  SASL mechanism used for client connections. This may be any mechanism for which a security provider is available. GSSAPI is the default mechanism.

用于客户端连接的SASL机制。这可以是安全提供程序可用的任何机制。GSSAPI是默认机制。

|         Type: | string |
| ------------: | ------ |
|      Default: | GSSAPI |
| Valid Values: |        |
|   Importance: | medium |

#### [security.protocol](http://kafka.apache.org/documentation/#security.protocol)

  Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.

用于与代理通信的协议。有效值是:明文、SSL、sasl_明文、SASL_SSL。

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |

#### [send.buffer.bytes](http://kafka.apache.org/documentation/#send.buffer.bytes)

  The size of the TCP send buffer (SO_SNDBUF) to use when sending data. If the value is -1, the OS default will be used.

发送数据时要使用的TCP发送缓冲区(SO_SNDBUF)的大小。如果值为-1，将使用OS默认值。

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 131072 (128 kibibytes) |
| Valid Values: | [-1,...]               |
|   Importance: | medium                 |

#### [ssl.enabled.protocols](http://kafka.apache.org/documentation/#ssl.enabled.protocols)

  The list of protocols enabled for SSL connections. The default is 'TLSv1.2,TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. With the default value for Java 11, clients and servers will prefer TLSv1.3 if both support it and fallback to TLSv1.2 otherwise (assuming both support at least TLSv1.2). This default should be fine for most cases. Also see the config documentation for `ssl.protocol`.

为SSL连接启用的协议列表。在使用Java 11或更新版本运行时，默认为“TLSv1.2,TLSv1.3”，否则为“TLSv1.2”。对于Java 11的默认值，如果客户端和服务器都支持TLSv1.3，那么它们会更喜欢TLSv1.3，否则会退回到TLSv1.2(假设两者都至少支持TLSv1.2)。这个默认值应该适用于大多数情况。另外，请参阅“ssl.protocol”的配置文档。

|         Type: | list    |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.keystore.type](http://kafka.apache.org/documentation/#ssl.keystore.type)

  The file format of the key store file. This is optional for client.

密钥存储文件的文件格式。这对于客户机是可选的。

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.protocol](http://kafka.apache.org/documentation/#ssl.protocol)

  The SSL protocol used to generate the SSLContext. The default is 'TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. This value should be fine for most use cases. Allowed values in recent JVMs are 'TLSv1.2' and 'TLSv1.3'. 'TLS', 'TLSv1.1', 'SSL', 'SSLv2' and 'SSLv3' may be supported in older JVMs, but their usage is discouraged due to known security vulnerabilities. With the default value for this config and 'ssl.enabled.protocols', clients will downgrade to 'TLSv1.2' if the server does not support 'TLSv1.3'. If this config is set to 'TLSv1.2', clients will not use 'TLSv1.3' even if it is one of the values in ssl.enabled.protocols and the server only supports 'TLSv1.3'.

用于生成SSLContext的SSL协议。在使用Java 11或更新版本运行时，默认为“TLSv1.3”，否则为“TLSv1.2”。这个值对于大多数用例来说都是合适的。最近的jvm允许的值是“TLSv1.2”和“TLSv1.3”。旧的jvm可能支持“TLS”、“TLSv1.1”、“SSL”、“SSLv2”和“SSLv3”，但由于已知的安全漏洞，不建议使用它们。使用此配置的默认值和'ssl.enabled '。如果服务器不支持“TLSv1.3”，客户端将降级到“TLSv1.2”。如果这个配置被设置为'TLSv1.2'，客户端将不会使用'TLSv1.3'，即使它是ssl.enabled中的值之一。协议和服务器只支持“TLSv1.3”。

|         Type: | string  |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.provider](http://kafka.apache.org/documentation/#ssl.provider)

  The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.

用于SSL连接的安全提供程序的名称。缺省值是JVM的缺省安全提供程序。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.truststore.type](http://kafka.apache.org/documentation/#ssl.truststore.type)

  The file format of the trust store file.

信任存储区文件的文件格式

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [enable.idempotence](http://kafka.apache.org/documentation/#enable.idempotence)

  When set to 'true', the producer will ensure that exactly one copy of each message is written in the stream. If 'false', producer retries due to broker failures, etc., may write duplicates of the retried message in the stream. Note that enabling idempotence requires `max.in.flight.requests.per.connection` to be less than or equal to 5, `retries` to be greater than 0 and `acks` must be 'all'. If these values are not explicitly set by the user, suitable values will be chosen. If incompatible values are set, a `ConfigException` will be thrown.

当设置为“true”时，生成器将确保在流中准确地写入每个消息的一个副本。如果“false”，则由于代理失败等原因，生产者重试，可能会在流中写入重试消息的副本。注意，启用幂等性需要“max.in. flights .requests.per.connection'小于或等于5，'重试'大于0，' acks '必须是'all'。如果用户没有显式地设置这些值，那么将选择合适的值。如果设置了不兼容的值，就会抛出一个' ConfigException '。

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | low     |

#### [interceptor.classes](http://kafka.apache.org/documentation/#interceptor.classes)

  A list of classes to use as interceptors. Implementing the `org.apache.kafka.clients.producer.ProducerInterceptor` interface allows you to intercept (and possibly mutate) the records received by the producer before they are published to the Kafka cluster. By default, there are no interceptors.

用作拦截器的类的列表。实施“org.apache.kafka.clients.producer.ProducerInterceptor的接口允许您在将记录发布到Kafka集群之前拦截(并可能更改)生产者收到的记录。默认情况下，没有拦截器。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | low             |

#### [max.in.flight.requests.per.connection](http://kafka.apache.org/documentation/#max.in.flight.requests.per.connection)

  The maximum number of unacknowledged requests the client will send on a single connection before blocking. Note that if this setting is set to be greater than 1 and there are failed sends, there is a risk of message re-ordering due to retries (i.e., if retries are enabled).

客户端在阻塞之前在单个连接上发送的未确认请求的最大数量。请注意，如果将此设置设置为大于1，并且存在发送失败的情况，那么由于重试(即启用了重试)，将存在消息重新排序的风险。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 5       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metadata.max.age.ms](http://kafka.apache.org/documentation/#metadata.max.age.ms)

  The period of time in milliseconds after which we force a refresh of metadata even if we haven't seen any partition leadership changes to proactively discover any new brokers or partitions.

一段时间(以毫秒为单位)，在此之后，即使我们没有看到任何分区领导变更，我们也会强制刷新元数据，以主动发现任何新的代理或分区。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [metadata.max.idle.ms](http://kafka.apache.org/documentation/#metadata.max.idle.ms)

  Controls how long the producer will cache metadata for a topic that's idle. If the elapsed time since a topic was last produced to exceeds the metadata idle duration, then the topic's metadata is forgotten and the next access to it will force a metadata fetch request.

控制生成器为空闲主题缓存元数据的时间。如果自上次生成主题以来所经过的时间超过了元数据空闲时间，那么就会忘记该主题的元数据，下一次访问它时将强制执行元数据获取请求。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [5000,...]         |
|   Importance: | low                |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

用作度量报告器的类列表。实现' org.apache. kafaka .common. metricsreporter '接口允许插入将被通知新度量创建的类。始终包含JmxReporter来注册JMX统计信息。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | low             |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

为计算指标而维护的样本数量。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

度量标准的最高记录级别。

|         Type: | string        |
| ------------: | ------------- |
|      Default: | INFO          |
| Valid Values: | [INFO, DEBUG] |
|   Importance: | low           |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

度量样本计算结束的时间窗口。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [reconnect.backoff.max.ms](http://kafka.apache.org/documentation/#reconnect.backoff.max.ms)

  The maximum amount of time in milliseconds to wait when reconnecting to a broker that has repeatedly failed to connect. If provided, the backoff per host will increase exponentially for each consecutive connection failure, up to this maximum. After calculating the backoff increase, 20% random jitter is added to avoid connection storms.

重新连接到多次连接失败的代理时等待的最大时间(以毫秒为单位)。如果这样做，每台主机的回退量将在每次连续连接失败时呈指数增长，直到这个最大值。在计算回退增加后，增加了20%的随机抖动以避免连接风暴。

|         Type: | long            |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | low             |

#### [reconnect.backoff.ms](http://kafka.apache.org/documentation/#reconnect.backoff.ms)

  The base amount of time to wait before attempting to reconnect to a given host. This avoids repeatedly connecting to a host in a tight loop. This backoff applies to all connection attempts by the client to a broker.

尝试重新连接到给定主机之前等待的基本时间。这避免了在紧循环中重复连接到主机。此回退适用于客户端对代理的所有连接尝试。

|         Type: | long    |
| ------------: | ------- |
|      Default: | 50      |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [retry.backoff.ms](http://kafka.apache.org/documentation/#retry.backoff.ms)

  The amount of time to wait before attempting to retry a failed request to a given topic partition. This avoids repeatedly sending requests in a tight loop under some failure scenarios.

尝试对给定主题分区重试失败的请求之前等待的时间。这避免了在某些故障场景下，在紧密循环中重复发送请求。

|         Type: | long    |
| ------------: | ------- |
|      Default: | 100     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [sasl.kerberos.kinit.cmd](http://kafka.apache.org/documentation/#sasl.kerberos.kinit.cmd)

  Kerberos kinit command path.

Kerberos kinit命令路径。

|         Type: | string         |
| ------------: | -------------- |
|      Default: | /usr/bin/kinit |
| Valid Values: |                |
|   Importance: | low            |

#### [sasl.kerberos.min.time.before.relogin](http://kafka.apache.org/documentation/#sasl.kerberos.min.time.before.relogin)

  Login thread sleep time between refresh attempts.

刷新尝试之间的登录线程休眠时间。

|         Type: | long  |
| ------------: | ----- |
|      Default: | 60000 |
| Valid Values: |       |
|   Importance: | low   |

#### [sasl.kerberos.ticket.renew.jitter](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.jitter)

  Percentage of random jitter added to the renewal time.

随机抖动的百分比增加到更新时间

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.05   |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.kerberos.ticket.renew.window.factor](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.window.factor)

  Login thread will sleep until the specified window factor of time from last refresh to ticket's expiry has been reached, at which time it will try to renew the ticket.

登录线程将休眠，直到到达从上次刷新到票证到期的指定窗口时间因子，此时它将尝试更新票证。

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.8    |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.login.refresh.buffer.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.buffer.seconds)

  The amount of buffer time before credential expiration to maintain when refreshing a credential, in seconds. If a refresh would otherwise occur closer to expiration than the number of buffer seconds then the refresh will be moved up to maintain as much of the buffer time as possible. Legal values are between 0 and 3600 (1 hour); a default value of 300 (5 minutes) is used if no value is specified. This value and sasl.login.refresh.min.period.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

刷新凭据时在凭据过期前要维护的缓冲区时间，以秒为单位。如果刷新发生在接近过期的时间，而不是缓冲区秒数的时间，那么刷新将向上移动，以保持尽可能多的缓冲区时间。合法值在0 ~ 3600(1小时)之间;如果没有指定值，则使用默认值300(5分钟)。这个值和sasl.login.refresh.min.period。如果秒的总和超过了凭据的剩余生命周期，则会忽略秒。目前只适用于oauthholder。

|         Type: | short        |
| ------------: | ------------ |
|      Default: | 300          |
| Valid Values: | [0,...,3600] |
|   Importance: | low          |

#### [sasl.login.refresh.min.period.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.min.period.seconds)

  The desired minimum time for the login refresh thread to wait before refreshing a credential, in seconds. Legal values are between 0 and 900 (15 minutes); a default value of 60 (1 minute) is used if no value is specified. This value and sasl.login.refresh.buffer.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

登录刷新线程在刷新凭据之前等待的最小时间，以秒为单位。合法值在0到900之间(15分钟);如果没有指定值，则使用默认值60(1分钟)。此值和sasl.login.refresh.buffer。如果秒的总和超过了凭据的剩余生命周期，则会忽略秒。目前只适用于oauthholder。

|         Type: | short       |
| ------------: | ----------- |
|      Default: | 60          |
| Valid Values: | [0,...,900] |
|   Importance: | low         |

#### [sasl.login.refresh.window.factor](http://kafka.apache.org/documentation/#sasl.login.refresh.window.factor)

  Login refresh thread will sleep until the specified window factor relative to the credential's lifetime has been reached, at which time it will try to refresh the credential. Legal values are between 0.5 (50%) and 1.0 (100%) inclusive; a default value of 0.8 (80%) is used if no value is specified. Currently applies only to OAUTHBEARER.

登录刷新线程将处于休眠状态，直到到达与凭据的生存期相关的指定窗口因子，此时它将尝试刷新凭据。合法值在0.5(50%)和1.0(100%)之间;如果没有指定值，则使用缺省值0.8(80%)。目前只适用于oauthholder。

|         Type: | double        |
| ------------: | ------------- |
|      Default: | 0.8           |
| Valid Values: | [0.5,...,1.0] |
|   Importance: | low           |

#### [sasl.login.refresh.window.jitter](http://kafka.apache.org/documentation/#sasl.login.refresh.window.jitter)

  The maximum amount of random jitter relative to the credential's lifetime that is added to the login refresh thread's sleep time. Legal values are between 0 and 0.25 (25%) inclusive; a default value of 0.05 (5%) is used if no value is specified. Currently applies only to OAUTHBEARER.

添加到登录刷新线程睡眠时间中的相对于凭据生命周期的最大随机抖动量。法定值在0至0.25(含25%)之间;如果没有指定值，则使用默认值0.05(5%)。目前只适用于oauthholder。

|         Type: | double         |
| ------------: | -------------- |
|      Default: | 0.05           |
| Valid Values: | [0.0,...,0.25] |
|   Importance: | low            |

#### [security.providers](http://kafka.apache.org/documentation/#security.providers)

  A list of configurable creator classes each returning a provider implementing security algorithms. These classes should implement the `org.apache.kafka.common.security.auth.SecurityProviderCreator` interface.

可配置创建器类的列表，每个创建器类返回实现安全算法的提供程序。这些类应该实现' org.apache. kafga .common.security. au.securityprovidercreator '的接口。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |



#### [ssl.cipher.suites](http://kafka.apache.org/documentation/#ssl.cipher.suites)

  A list of cipher suites. This is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. By default all the available cipher suites are supported.

密码套件列表。这是一种命名的身份验证、加密、MAC和密钥交换算法的组合，用于使用TLS或SSL网络协议协商网络连接的安全设置。默认情况下，支持所有可用的密码套件

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#ssl.endpoint.identification.algorithm)

  The endpoint identification algorithm to validate server hostname using server certificate.

使用服务器证书验证服务器主机名的端点识别算法。

|         Type: | string |
| ------------: | ------ |
|      Default: | https  |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.engine.factory.class](http://kafka.apache.org/documentation/#ssl.engine.factory.class)

  The class of type org.apache.kafka.common.security.auth.SslEngineFactory to provide SSLEngine objects. Default value is org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

org.apache. kafcard .common.security. auc . sslenginefactory类型的类来提供SSLEngine对象。默认值为org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [ssl.keymanager.algorithm](http://kafka.apache.org/documentation/#ssl.keymanager.algorithm)

  The algorithm used by key manager factory for SSL connections. Default value is the key manager factory algorithm configured for the Java Virtual Machine.

密钥管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的密钥管理器工厂算法。

|         Type: | string  |
| ------------: | ------- |
|      Default: | SunX509 |
| Valid Values: |         |
|   Importance: | low     |

#### [ssl.secure.random.implementation](http://kafka.apache.org/documentation/#ssl.secure.random.implementation)

  The SecureRandom PRNG implementation to use for SSL cryptography operations.

用于SSL加密操作的SecureRandom PRNG实现。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.trustmanager.algorithm](http://kafka.apache.org/documentation/#ssl.trustmanager.algorithm)

  The algorithm used by trust manager factory for SSL connections. Default value is the trust manager factory algorithm configured for the Java Virtual Machine.

信任管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的信任管理器工厂算法。

|         Type: | string |
| ------------: | ------ |
|      Default: | PKIX   |
| Valid Values: |        |
|   Importance: | low    |

#### [transaction.timeout.ms](http://kafka.apache.org/documentation/#transaction.timeout.ms)

  The maximum amount of time in ms that the transaction coordinator will wait for a transaction status update from the producer before proactively aborting the ongoing transaction.If this value is larger than the transaction.max.timeout.ms setting in the broker, the request will fail with a `InvalidTransactionTimeout` error.

在ms中，事务协调器在主动中止正在进行的事务之前等待来自生成器的事务状态更新的最大时间。如果该值大于事务.max.timeout。在代理中设置ms，请求将失败，并出现“InvalidTransactionTimeout”错误。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: |                  |
|   Importance: | low              |

#### [transactional.id](http://kafka.apache.org/documentation/#transactional.id)

  The TransactionalId to use for transactional delivery. This enables reliability semantics which span multiple producer sessions since it allows the client to guarantee that transactions using the same TransactionalId have been completed prior to starting any new transactions. If no TransactionalId is provided, then the producer is limited to idempotent delivery. If a TransactionalId is configured, `enable.idempotence` is implied. By default the TransactionId is not configured, which means transactions cannot be used. Note that, by default, transactions require a cluster of at least three brokers which is the recommended setting for production; for development you can change this, by adjusting broker setting `transaction.state.log.replication.factor`.

用于事务传递的TransactionalId。这支持跨多个生产者会话的可靠性语义，因为它允许客户端保证使用相同TransactionalId的事务在启动任何新事务之前已经完成。如果没有提供交易立得，则生产者被限制在幂等交付。如果配置了TransactionalId，则“启用”。幂等性”是隐含的。默认情况下，没有配置TransactionId，这意味着不能使用事务。注意，默认情况下，事务需要至少三个代理的集群，这是生产时的建议设置;对于开发，您可以通过调整代理设置' transaction.state.log. copy .factor '来更改这一点。

|         Type: | string           |
| ------------: | ---------------- |
|      Default: | null             |
| Valid Values: | non-empty string |
|   Importance: | low              |

### [3.4 Consumer Configs](http://kafka.apache.org/documentation/#consumerconfigs)

Below is the configuration for the consumer:

以下是消费者的配置:

#### [key.deserializer](http://kafka.apache.org/documentation/#key.deserializer)

  Deserializer class for key that implements the `org.apache.kafka.common.serialization.Deserializer` interface.

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [value.deserializer](http://kafka.apache.org/documentation/#value.deserializer)

  Deserializer class for value that implements the `org.apache.kafka.common.serialization.Deserializer` interface.

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [bootstrap.servers](http://kafka.apache.org/documentation/#bootstrap.servers)

  A list of host/port pairs to use for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2,...`. Since these servers are just used for the initial connection to discover the full cluster membership (which may change dynamically), this list need not contain the full set of servers (you may want more than one, though, in case a server is down).

用于建立到Kafka集群的初始连接的主机/端口对列表。客户机将使用所有服务器，而不管这里为引导指定了哪些服务器——此列表只影响用于发现完整服务器集的初始主机。该列表应该以“host1:port1,host2:port2，…”的形式出现。由于这些服务器仅用于初始连接，以发现完整的集群成员关系(可能会动态更改)，因此此列表不需要包含完整的服务器集(不过，如果服务器宕机，您可能需要多个服务器)。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | high            |

#### [fetch.min.bytes](http://kafka.apache.org/documentation/#fetch.min.bytes)

  The minimum amount of data the server should return for a fetch request. If insufficient data is available the request will wait for that much data to accumulate before answering the request. The default setting of 1 byte means that fetch requests are answered as soon as a single byte of data is available or the fetch request times out waiting for data to arrive. Setting this to something greater than 1 will cause the server to wait for larger amounts of data to accumulate which can improve server throughput a bit at the cost of some additional latency.

服务器应该为获取请求返回的最小数据量。如果可用数据不足，请求将等待大量数据累积，然后再响应请求。1字节的默认设置意味着获取请求被回答，当一个数据的单个字节可用或获取请求超时等待数据到达。将这个值设置为大于1的值将导致服务器等待更大数量的数据积累，这会在增加一些延迟的代价下提高服务器吞吐量。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 1       |
| Valid Values: | [0,...] |
|   Importance: | high    |

#### [group.id](http://kafka.apache.org/documentation/#group.id)

  A unique string that identifies the consumer group this consumer belongs to. This property is required if the consumer uses either the group management functionality by using `subscribe(topic)` or the Kafka-based offset management strategy.

标识此使用者所属的使用者组的唯一字符串。如果使用者通过使用“subscribe(topic)”或基于kafka的偏移量管理策略来使用组管理功能，则需要此属性。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [heartbeat.interval.ms](http://kafka.apache.org/documentation/#heartbeat.interval.ms)

  The expected time between heartbeats to the consumer coordinator when using Kafka's group management facilities. Heartbeats are used to ensure that the consumer's session stays active and to facilitate rebalancing when new consumers join or leave the group. The value must be set lower than `session.timeout.ms`, but typically should be set no higher than 1/3 of that value. It can be adjusted even lower to control the expected time for normal rebalances.

使用Kafka的组管理工具时，心跳到使用者协调器之间的预期时间。心跳用于确保使用者会话保持活动状态，并在新使用者加入或离开组时促进再平衡。该值必须设置为低于' session.timeout。ms '，但通常应设置不超过该值的1/3。它可以调整更低，以控制正常重新平衡的预期时间。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 3000 (3 seconds) |
| Valid Values: |                  |
|   Importance: | high             |

#### [max.partition.fetch.bytes](http://kafka.apache.org/documentation/#max.partition.fetch.bytes)

  The maximum amount of data per-partition the server will return. Records are fetched in batches by the consumer. If the first record batch in the first non-empty partition of the fetch is larger than this limit, the batch will still be returned to ensure that the consumer can make progress. The maximum record batch size accepted by the broker is defined via `message.max.bytes` (broker config) or `max.message.bytes` (topic config). See fetch.max.bytes for limiting the consumer request size.

服务器将返回的每个分区的最大数据量。记录由消费者分批提取。如果fetch的第一个非空分区中的第一个记录批大于此限制，则仍将返回批以确保消费者能够继续执行。代理接受的最大记录批处理大小通过' message.max '定义。字节(broker config)或max.message。字节(主题配置)。看到fetch.max。限制使用者请求大小的字节。

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 1048576 (1 mebibyte) |
| Valid Values: | [0,...]              |
|   Importance: | high                 |

#### [session.timeout.ms](http://kafka.apache.org/documentation/#session.timeout.ms)

  The timeout used to detect client failures when using Kafka's group management facility. The client sends periodic heartbeats to indicate its liveness to the broker. If no heartbeats are received by the broker before the expiration of this session timeout, then the broker will remove this client from the group and initiate a rebalance. Note that the value must be in the allowable range as configured in the broker configuration by `group.min.session.timeout.ms` and `group.max.session.timeout.ms`.

使用Kafka的组管理工具时用于检测客户端故障的超时。客户端定期向代理发送心跳来表示其活动。如果在此会话超时过期之前代理没有收到心跳，则代理将从组中删除此客户机并启动重新平衡。注意，该值必须在代理配置中' group.min.session.timeout '配置的允许范围内。女士”和“group.max.session.timeout.ms”。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 10000 (10 seconds) |
| Valid Values: |                    |
|   Importance: | high               |

#### [ssl.key.password](http://kafka.apache.org/documentation/#ssl.key.password)

  The password of the private key in the key store file. This is optional for client.

密钥存储文件中私钥的密码。这对于客户机是可选的。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.keystore.location](http://kafka.apache.org/documentation/#ssl.keystore.location)

  The location of the key store file. This is optional for client and can be used for two-way authentication for client.

密钥存储文件的位置。这对于客户机是可选的，可以用于客户机的双向身份验证。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.keystore.password](http://kafka.apache.org/documentation/#ssl.keystore.password)

  The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.

密钥存储文件的存储密码。这对于客户机是可选的，只有在ssl.keystore中才需要。位置配置。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.truststore.location](http://kafka.apache.org/documentation/#ssl.truststore.location)

  The location of the trust store file.

信任存储库文件的位置。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.truststore.password](http://kafka.apache.org/documentation/#ssl.truststore.password)

  The password for the trust store file. If a password is not set access to the truststore is still available, but integrity checking is disabled.

信任存储文件的密码。如果没有设置密码，仍然可以访问信任存储库，但是禁用了完整性检查。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [allow.auto.create.topics](http://kafka.apache.org/documentation/#allow.auto.create.topics)

  Allow automatic topic creation on the broker when subscribing to or assigning a topic. A topic being subscribed to will be automatically created only if the broker allows for it using `auto.create.topics.enable` broker configuration. This configuration must be set to `false` when using brokers older than 0.11.0

当订阅或分配主题时，允许在代理(broker)上自动创建主题。只有在代理允许使用' auto.create.topics '时，订阅的主题才会被自动创建。启用代理配置。当使用大于0.11.0的代理（broker）时，这个配置必须设置为“false”

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | medium  |

#### [auto.offset.reset](http://kafka.apache.org/documentation/#auto.offset.reset)

  What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server (e.g. because that data has been deleted):

当Kafka中没有初始偏移量或者服务器上的当前偏移量不再存在时(例如，因为数据已经被删除):

  - earliest: automatically reset the offset to the earliest offset

    最早:自动重置偏移到最早偏移

  - latest: automatically reset the offset to the latest offset

    最新:自动重置偏移量为最新偏移量

  - none: throw exception to the consumer if no previous offset is found for the consumer's group

    none:如果没有为使用者的组找到以前的偏移量，则向使用者抛出异常

  - anything else: throw exception to the consumer.

    其他:向使用者抛出异常。

  

|         Type: | string                   |
| ------------: | ------------------------ |
|      Default: | latest                   |
| Valid Values: | [latest, earliest, none] |
|   Importance: | medium                   |

#### [client.dns.lookup](http://kafka.apache.org/documentation/#client.dns.lookup)

  Controls how the client uses DNS lookups. If set to `use_all_dns_ips`, connect to each returned IP address in sequence until a successful connection is established. After a disconnection, the next IP is used. Once all IPs have been used once, the client resolves the IP(s) from the hostname again (both the JVM and the OS cache DNS name lookups, however). If set to `resolve_canonical_bootstrap_servers_only`, resolve each bootstrap address into a list of canonical names. After the bootstrap phase, this behaves the same as `use_all_dns_ips`. If set to `default` (deprecated), attempt to connect to the first IP address returned by the lookup, even if the lookup returns multiple IP addresses.

控制客户端如何使用DNS查找。如果设置为' use_all_dns_ips '，依次连接到每个返回的IP地址，直到建立成功的连接。断开连接后，使用下一个IP。一旦所有IP都使用过一次，客户机就会再次从主机名解析IP(不过JVM和OS都会缓存DNS名称查找)。如果设置为' resolve_canonical_bootstrap_servers_only '，则将每个引导程序地址解析为一个规范名称列表。在引导阶段之后，它的行为与' use_all_dns_ips '相同。如果设置为‘default’(不赞成使用)，尝试连接到查找返回的第一个IP地址，即使查找返回多个IP地址。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | use_all_dns_ips                                              |
| Valid Values: | [default, use_all_dns_ips, resolve_canonical_bootstrap_servers_only] |
|   Importance: | medium                                                       |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Close idle connections after the number of milliseconds specified by this config.

在此配置指定的毫秒数之后关闭空闲连接。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 540000 (9 minutes) |
| Valid Values: |                    |
|   Importance: | medium             |

#### [default.api.timeout.ms](http://kafka.apache.org/documentation/#default.api.timeout.ms)

  Specifies the timeout (in milliseconds) for client APIs. This configuration is used as the default timeout for all client operations that do not specify a `timeout` parameter.

指定客户端api的超时(单位为毫秒)。此配置用于未指定“timeout”参数的所有客户端操作的默认超时。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | medium           |

#### [enable.auto.commit](http://kafka.apache.org/documentation/#enable.auto.commit)

  If true the consumer's offset will be periodically committed in the background.

如果为真，消费者的偏移量将定期在后台提交。

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | medium  |

#### [exclude.internal.topics](http://kafka.apache.org/documentation/#exclude.internal.topics)

  Whether internal topics matching a subscribed pattern should be excluded from the subscription. It is always possible to explicitly subscribe to an internal topic.

是否应该从订阅中排除与订阅模式匹配的内部主题。始终可以显式地订阅内部主题。

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | medium  |

#### [fetch.max.bytes](http://kafka.apache.org/documentation/#fetch.max.bytes)

  The maximum amount of data the server should return for a fetch request. Records are fetched in batches by the consumer, and if the first record batch in the first non-empty partition of the fetch is larger than this value, the record batch will still be returned to ensure that the consumer can make progress. As such, this is not a absolute maximum. The maximum record batch size accepted by the broker is defined via `message.max.bytes` (broker config) or `max.message.bytes` (topic config). Note that the consumer performs multiple fetches in parallel.

服务器应该为获取请求返回的最大数据量。记录是由使用者分批获取的，如果获取的第一个非空分区中的第一个记录批大于此值，则仍将返回该记录批，以确保使用者能够继续执行。因此，这不是一个绝对的最大值。代理接受的最大记录批处理大小通过' message.max.btyes(broker config) '定义或max.message.bytes字节(主题配置)。请注意，使用者并行执行多个读取操作。

|         Type: | int                     |
| ------------: | ----------------------- |
|      Default: | 52428800 (50 mebibytes) |
| Valid Values: | [0,...]                 |
|   Importance: | medium                  |

#### [group.instance.id](http://kafka.apache.org/documentation/#group.instance.id)

  A unique identifier of the consumer instance provided by the end user. Only non-empty strings are permitted. If set, the consumer is treated as a static member, which means that only one instance with this ID is allowed in the consumer group at any time. This can be used in combination with a larger session timeout to avoid group rebalances caused by transient unavailability (e.g. process restarts). If not set, the consumer will join the group as a dynamic member, which is the traditional behavior.

最终用户提供的使用者实例的唯一标识符。只允许非空字符串。如果设置，则将使用者视为静态成员，这意味着在任何时候使用者组中只允许一个具有此ID的实例。这可以与更大的会话超时结合使用，以避免由于暂时不可用(例如进程重启)而导致的组重新平衡。如果没有设置，消费者将作为动态成员加入组，这是传统行为。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [isolation.level](http://kafka.apache.org/documentation/#isolation.level)

  Controls how to read messages written transactionally. If set to `read_committed`, consumer.poll() will only return transactional messages which have been committed. If set to `read_uncommitted`' (the default), consumer.poll() will return all messages, even transactional messages which have been aborted. Non-transactional messages will be returned unconditionally in either mode.

控制如何读取以事务方式编写的消息。如果设置为' read_committed '， consumer.poll()将只返回已提交的事务性消息。如果设置为' read_uncommitted "(默认值)，consumer.poll()将返回所有消息，甚至是已经中止的事务性消息。非事务性消息将以两种模式无条件返回。

  Messages will always be returned in offset order. Hence, in `read_committed` mode, consumer.poll() will only return messages up to the last stable offset (LSO), which is the one less than the offset of the first open transaction. In particular any messages appearing after messages belonging to ongoing transactions will be withheld until the relevant transaction has been completed. As a result, `read_committed` consumers will not be able to read up to the high watermark when there are in flight transactions.

消息将总是按偏移顺序返回。因此，在' read_committed '模式中，consumer.poll()将只返回到最后一个稳定偏移量(LSO)的消息，LSO小于第一个打开的事务的偏移量。特别是，任何出现在属于正在进行的事务的消息之后的消息都将被扣留，直到相关事务完成。因此，当有飞行事务时，“read_committed”消费者将无法读取高水位。

  Further, when in `read_committed` the seekToEnd method will return the LSO

此外，在' read_committed '中，seekToEnd方法将返回LSO

|         Type: | string                             |
| ------------: | ---------------------------------- |
|      Default: | read_uncommitted                   |
| Valid Values: | [read_committed, read_uncommitted] |
|   Importance: | medium                             |

#### [max.poll.interval.ms](http://kafka.apache.org/documentation/#max.poll.interval.ms)

  The maximum delay between invocations of poll() when using consumer group management. This places an upper bound on the amount of time that the consumer can be idle before fetching more records. If poll() is not called before expiration of this timeout, then the consumer is considered failed and the group will rebalance in order to reassign the partitions to another member. For consumers using a non-null `group.instance.id` which reach this timeout, partitions will not be immediately reassigned. Instead, the consumer will stop sending heartbeats and partitions will be reassigned after expiration of `session.timeout.ms`. This mirrors the behavior of a static consumer which has shutdown.

使用使用者组管理时poll()调用之间的最大延迟。这就为使用者在获取更多记录之前的空闲时间设置了一个上限。如果在超时结束之前没有调用poll()，则认为使用者失败，组将重新进行平衡，以便将分区重新分配给另一个成员。对于使用非空' group.instance的消费者。如果到达超时，则不会立即重新分配分区。相反，消费者将停止发送心跳，并在' session.timeout.ms '过期后重新分配分区。这反映了已关闭的静态使用者的行为。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [1,...]            |
|   Importance: | medium             |

#### [max.poll.records](http://kafka.apache.org/documentation/#max.poll.records)

  The maximum number of records returned in a single call to poll().

对poll()的一次调用中返回的最大记录数。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 500     |
| Valid Values: | [1,...] |
|   Importance: | medium  |

#### [partition.assignment.strategy](http://kafka.apache.org/documentation/#partition.assignment.strategy)

  A list of class names or class types, ordered by preference, of supported partition assignment strategies that the client will use to distribute partition ownership amongst consumer instances when group management is used.

在使用组管理时，客户端将使用所支持的分区分配策略来在消费者实例之间分配分区所有权的类名或类类型列表(按偏好排序)。

  In addition to the default class specified below, you can use the `org.apache.kafka.clients.consumer.RoundRobinAssignor`class for round robin assignments of partitions to consumers.

除了下面指定的默认类之外，还可以使用' org.apache.kafka.clients.consumer。用于对分区进行循环分配给使用者的RoundRobinAssignor类。

  Implementing the `org.apache.kafka.clients.consumer.ConsumerPartitionAssignor` interface allows you to plug in a custom assignmentstrategy.

实施“org.apache.kafka.clients.consumer。ConsumerPartitionAssignor的接口允许您插入一个定制的分配策略。

|         Type: | list                                                  |
| ------------: | ----------------------------------------------------- |
|      Default: | class org.apache.kafka.clients.consumer.RangeAssignor |
| Valid Values: | non-null string                                       |
|   Importance: | medium                                                |

#### [receive.buffer.bytes](http://kafka.apache.org/documentation/#receive.buffer.bytes)

  The size of the TCP receive buffer (SO_RCVBUF) to use when reading data. If the value is -1, the OS default will be used.

读取数据时要使用的TCP接收缓冲区(SO_RCVBUF)的大小。如果值为-1，将使用OS默认值。

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 65536 (64 kibibytes) |
| Valid Values: | [-1,...]             |
|   Importance: | medium               |

#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.

配置控制客户端等待请求响应的最大时间量。如果在超时结束前没有收到响应，客户端将在必要时重新发送请求，或者在重试耗尽时请求失败。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |

#### [sasl.client.callback.handler.class](http://kafka.apache.org/documentation/#sasl.client.callback.handler.class)

  The fully qualified name of a SASL client callback handler class that implements the AuthenticateCallbackHandler interface.

实现AuthenticateCallbackHandler接口的SASL客户端回调处理程序类的完全限定名。

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.jaas.config](http://kafka.apache.org/documentation/#sasl.jaas.config)

  JAAS login context parameters for SASL connections in the format used by JAAS configuration files. JAAS configuration file format is described [here](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html). The format for the value is: '`loginModuleClass controlFlag (optionName=optionValue)*;`'. For brokers, the config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=com.example.ScramLoginModule required;

使用JAAS配置文件使用的格式为SASL连接的JAAS登录上下文参数。这里描述了JAAS配置文件格式。该值的格式是:' ' loginModuleClass controlFlag (optionName=optionValue)*; "。对于代理，配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如，listen .name.sasl_ssl.冲压-sha-256.sasl.jaas.config=com.example.ScramLoginModule是必需的

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | medium   |

#### [sasl.kerberos.service.name](http://kafka.apache.org/documentation/#sasl.kerberos.service.name)

  The Kerberos principal name that Kafka runs as. This can be defined either in Kafka's JAAS config or in Kafka's config.

Kafka运行时的Kerberos主体名。这可以在卡夫卡的JAAS配置或卡夫卡的配置中定义。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.callback.handler.class](http://kafka.apache.org/documentation/#sasl.login.callback.handler.class)

  The fully qualified name of a SASL login callback handler class that implements the AuthenticateCallbackHandler interface. For brokers, login callback handler config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class=com.example.CustomScramLoginCallbackHandler

实现AuthenticateCallbackHandler接口的SASL登录回调处理程序类的完全限定名。对于代理，登录回调处理程序配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如,listener.name.sasl_ssl.scram - sha - 256. - sasl.login.callback.handler.class = com.example.CustomScramLoginCallbackHandler

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.class](http://kafka.apache.org/documentation/#sasl.login.class)

  The fully qualified name of a class that implements the Login interface. For brokers, login config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.class=com.example.CustomScramLogin

实现登录接口的类的完全限定名。对于代理，登录配置必须使用监听器前缀和小写的SASL机制名称作为前缀。例如,listener.name.sasl_ssl.scram - sha - 256. - sasl.login.class = com.example.CustomScramLogin

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.mechanism](http://kafka.apache.org/documentation/#sasl.mechanism)

  SASL mechanism used for client connections. This may be any mechanism for which a security provider is available. GSSAPI is the default mechanism.

用于客户端连接的SASL机制。这可以是安全提供程序可用的任何机制。GSSAPI是默认机制。

|         Type: | string |
| ------------: | ------ |
|      Default: | GSSAPI |
| Valid Values: |        |
|   Importance: | medium |

#### [security.protocol](http://kafka.apache.org/documentation/#security.protocol)

  Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.

用于与代理通信的协议。有效值是:明文、SSL、sasl_明文、SASL_SSL。

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |

#### [send.buffer.bytes](http://kafka.apache.org/documentation/#send.buffer.bytes)

  The size of the TCP send buffer (SO_SNDBUF) to use when sending data. If the value is -1, the OS default will be used.

发送数据时要使用的TCP发送缓冲区(SO_SNDBUF)的大小。如果值为-1，将使用OS默认值。

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 131072 (128 kibibytes) |
| Valid Values: | [-1,...]               |
|   Importance: | medium                 |

#### [ssl.enabled.protocols](http://kafka.apache.org/documentation/#ssl.enabled.protocols)

  The list of protocols enabled for SSL connections. The default is 'TLSv1.2,TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. With the default value for Java 11, clients and servers will prefer TLSv1.3 if both support it and fallback to TLSv1.2 otherwise (assuming both support at least TLSv1.2). This default should be fine for most cases. Also see the config documentation for `ssl.protocol`.

为SSL连接启用的协议列表。在使用Java 11或更新版本运行时，默认为“TLSv1.2,TLSv1.3”，否则为“TLSv1.2”。对于Java 11的默认值，如果客户端和服务器都支持TLSv1.3，那么它们会更喜欢TLSv1.3，否则会退回到TLSv1.2(假设两者都至少支持TLSv1.2)。这个默认值应该适用于大多数情况。另外，请参阅“ssl.protocol”的配置文档。

|         Type: | list    |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.keystore.type](http://kafka.apache.org/documentation/#ssl.keystore.type)

  The file format of the key store file. This is optional for client.

密钥存储文件的文件格式。这对于客户机是可选的

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.protocol](http://kafka.apache.org/documentation/#ssl.protocol)

  The SSL protocol used to generate the SSLContext. The default is 'TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. This value should be fine for most use cases. Allowed values in recent JVMs are 'TLSv1.2' and 'TLSv1.3'. 'TLS', 'TLSv1.1', 'SSL', 'SSLv2' and 'SSLv3' may be supported in older JVMs, but their usage is discouraged due to known security vulnerabilities. With the default value for this config and 'ssl.enabled.protocols', clients will downgrade to 'TLSv1.2' if the server does not support 'TLSv1.3'. If this config is set to 'TLSv1.2', clients will not use 'TLSv1.3' even if it is one of the values in ssl.enabled.protocols and the server only supports 'TLSv1.3'.

用于生成SSLContext的SSL协议。在使用Java 11或更新版本运行时，默认为“TLSv1.3”，否则为“TLSv1.2”。这个值对于大多数用例来说都是合适的。最近的jvm允许的值是“TLSv1.2”和“TLSv1.3”。旧的jvm可能支持“TLS”、“TLSv1.1”、“SSL”、“SSLv2”和“SSLv3”，但由于已知的安全漏洞，不建议使用它们。使用此配置的默认值和'ssl.enabled '。如果服务器不支持“TLSv1.3”，客户端将降级到“TLSv1.2”。如果这个配置被设置为'TLSv1.2'，客户端将不会使用'TLSv1.3'，即使它是ssl.enabled中的值之一。协议和服务器只支持“TLSv1.3”。

|         Type: | string  |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.provider](http://kafka.apache.org/documentation/#ssl.provider)

  The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.

用于SSL连接的安全提供程序的名称。缺省值是JVM的缺省安全提供程序。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.truststore.type](http://kafka.apache.org/documentation/#ssl.truststore.type)

  The file format of the trust store file.

信任存储区文件的文件格式。

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [auto.commit.interval.ms](http://kafka.apache.org/documentation/#auto.commit.interval.ms)

  The frequency in milliseconds that the consumer offsets are auto-committed to Kafka if `enable.auto.commit` is set to `true`.

如果“enable.auto.commit”设置为“true”，那么消费者偏移量自动提交给Kafka的频率(以毫秒为单位)。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: | [0,...]          |
|   Importance: | low              |

#### [check.crcs](http://kafka.apache.org/documentation/#check.crcs)

  Automatically check the CRC32 of the records consumed. This ensures no on-the-wire or on-disk corruption to the messages occurred. This check adds some overhead, so it may be disabled in cases seeking extreme performance.

自动检查所使用记录的CRC32。这确保了消息不会在网络上或磁盘上发生损坏。这个检查会增加一些开销，因此在寻求极端性能的情况下可能会禁用它。

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | low     |

#### [client.id](http://kafka.apache.org/documentation/#client.id)

  An id string to pass to the server when making requests. The purpose of this is to be able to track the source of requests beyond just ip/port by allowing a logical application name to be included in server-side request logging.

在发出请求时传递给服务器的id字符串。这样做的目的是通过允许在服务器端请求日志记录中包含逻辑应用程序名称，从而能够跟踪ip/端口之外的请求源。

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [client.rack](http://kafka.apache.org/documentation/#client.rack)

  A rack identifier for this client. This can be any string value which indicates where this client is physically located. It corresponds with the broker config 'broker.rack'

此客户机的机架标识符。它可以是任何字符串值，指示此客户机的物理位置。它与代理配置'broker.rack'相对应。

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [fetch.max.wait.ms](http://kafka.apache.org/documentation/#fetch.max.wait.ms)

  The maximum amount of time the server will block before answering the fetch request if there isn't sufficient data to immediately satisfy the requirement given by fetch.min.bytes.

如果没有足够的数据来立即满足fetch.min.bytes给出的要求，那么服务器在回答取回请求之前阻塞的最大时间。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 500     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [interceptor.classes](http://kafka.apache.org/documentation/#interceptor.classes)

  A list of classes to use as interceptors. Implementing the `org.apache.kafka.clients.consumer.ConsumerInterceptor` interface allows you to intercept (and possibly mutate) records received by the consumer. By default, there are no interceptors.

用作拦截器的类的列表。实施“org.apache.kafka.clients.consumer。客户拦截器的接口允许您拦截(并可能更改)客户接收的记录。默认情况下，没有拦截器。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | low             |

#### [metadata.max.age.ms](http://kafka.apache.org/documentation/#metadata.max.age.ms)

  The period of time in milliseconds after which we force a refresh of metadata even if we haven't seen any partition leadership changes to proactively discover any new brokers or partitions.

一段时间(以毫秒为单位)，在此之后，即使我们没有看到任何分区领导变更，我们也会强制刷新元数据，以主动发现任何新的代理或分区。

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

用作度量报告器的类列表。实现' org.apache. kafaka .common. metricsreporter '接口允许插入将被通知新度量创建的类。始终包含JmxReporter来注册JMX统计信息。

|         Type: | list            |
| ------------: | --------------- |
|      Default: | ""              |
| Valid Values: | non-null string |
|   Importance: | low             |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

为计算指标而维护的样本数量。

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

度量标准的最高记录级别。

|         Type: | string        |
| ------------: | ------------- |
|      Default: | INFO          |
| Valid Values: | [INFO, DEBUG] |
|   Importance: | low           |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

度量样本计算结束的时间窗口

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [reconnect.backoff.max.ms](http://kafka.apache.org/documentation/#reconnect.backoff.max.ms)

  The maximum amount of time in milliseconds to wait when reconnecting to a broker that has repeatedly failed to connect. If provided, the backoff per host will increase exponentially for each consecutive connection failure, up to this maximum. After calculating the backoff increase, 20% random jitter is added to avoid connection storms.

重新连接到多次连接失败的代理时等待的最大时间(以毫秒为单位)。如果这样做，每台主机的回退量将在每次连续连接失败时呈指数增长，直到这个最大值。在计算回退增加后，增加了20%的随机抖动以避免连接风暴。

|         Type: | long            |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | low             |

#### [reconnect.backoff.ms](http://kafka.apache.org/documentation/#reconnect.backoff.ms)

  The base amount of time to wait before attempting to reconnect to a given host. This avoids repeatedly connecting to a host in a tight loop. This backoff applies to all connection attempts by the client to a broker.

尝试重新连接到给定主机之前等待的基本时间。这避免了在紧循环中重复连接到主机。此回退适用于客户端对代理的所有连接尝试。

|         Type: | long    |
| ------------: | ------- |
|      Default: | 50      |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [retry.backoff.ms](http://kafka.apache.org/documentation/#retry.backoff.ms)

  The amount of time to wait before attempting to retry a failed request to a given topic partition. This avoids repeatedly sending requests in a tight loop under some failure scenarios.

尝试对给定主题分区重试失败的请求之前等待的时间。这避免了在某些故障场景下，在紧密循环中重复发送请求。

|         Type: | long    |
| ------------: | ------- |
|      Default: | 100     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [sasl.kerberos.kinit.cmd](http://kafka.apache.org/documentation/#sasl.kerberos.kinit.cmd)

  Kerberos kinit command path.

Kerberos kinit命令路径

|         Type: | string         |
| ------------: | -------------- |
|      Default: | /usr/bin/kinit |
| Valid Values: |                |
|   Importance: | low            |

#### [sasl.kerberos.min.time.before.relogin](http://kafka.apache.org/documentation/#sasl.kerberos.min.time.before.relogin)

  Login thread sleep time between refresh attempts.

刷新尝试之间的登录线程休眠时间。

|         Type: | long  |
| ------------: | ----- |
|      Default: | 60000 |
| Valid Values: |       |
|   Importance: | low   |

#### [sasl.kerberos.ticket.renew.jitter](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.jitter)

  Percentage of random jitter added to the renewal time.

随机抖动的百分比增加到更新时间。

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.05   |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.kerberos.ticket.renew.window.factor](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.window.factor)

  Login thread will sleep until the specified window factor of time from last refresh to ticket's expiry has been reached, at which time it will try to renew the ticket.

登录线程将休眠，直到到达从上次刷新到票证到期的指定窗口时间因子，此时它将尝试更新票证。

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.8    |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.login.refresh.buffer.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.buffer.seconds)

  The amount of buffer time before credential expiration to maintain when refreshing a credential, in seconds. If a refresh would otherwise occur closer to expiration than the number of buffer seconds then the refresh will be moved up to maintain as much of the buffer time as possible. Legal values are between 0 and 3600 (1 hour); a default value of 300 (5 minutes) is used if no value is specified. This value and sasl.login.refresh.min.period.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

刷新凭据时在凭据过期前要维护的缓冲区时间，以秒为单位。如果刷新发生在接近过期的时间，而不是缓冲区秒数的时间，那么刷新将向上移动，以保持尽可能多的缓冲区时间。合法值在0 ~ 3600(1小时)之间;如果没有指定值，则使用默认值300(5分钟)。这个值和sasl.login.refresh.min.period。如果秒的总和超过了凭据的剩余生命周期，则会忽略秒。目前只适用于oauthholder。

|         Type: | short        |
| ------------: | ------------ |
|      Default: | 300          |
| Valid Values: | [0,...,3600] |
|   Importance: | low          |

#### [sasl.login.refresh.min.period.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.min.period.seconds)

  The desired minimum time for the login refresh thread to wait before refreshing a credential, in seconds. Legal values are between 0 and 900 (15 minutes); a default value of 60 (1 minute) is used if no value is specified. This value and sasl.login.refresh.buffer.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

登录刷新线程在刷新凭据之前等待的最小时间，以秒为单位。合法值在0到900之间(15分钟);如果没有指定值，则使用默认值60(1分钟)。此值和sasl.login.refresh.buffer。如果秒的总和超过了凭据的剩余生命周期，则会忽略秒。目前只适用于oauthholder

|         Type: | short       |
| ------------: | ----------- |
|      Default: | 60          |
| Valid Values: | [0,...,900] |
|   Importance: | low         |

#### [sasl.login.refresh.window.factor](http://kafka.apache.org/documentation/#sasl.login.refresh.window.factor)

  Login refresh thread will sleep until the specified window factor relative to the credential's lifetime has been reached, at which time it will try to refresh the credential. Legal values are between 0.5 (50%) and 1.0 (100%) inclusive; a default value of 0.8 (80%) is used if no value is specified. Currently applies only to OAUTHBEARER.

登录刷新线程将处于休眠状态，直到到达与凭据的生存期相关的指定窗口因子，此时它将尝试刷新凭据。合法值在0.5(50%)和1.0(100%)之间;如果没有指定值，则使用缺省值0.8(80%)。目前只适用于oauthholder。

|         Type: | double        |
| ------------: | ------------- |
|      Default: | 0.8           |
| Valid Values: | [0.5,...,1.0] |
|   Importance: | low           |

#### [sasl.login.refresh.window.jitter](http://kafka.apache.org/documentation/#sasl.login.refresh.window.jitter)

  The maximum amount of random jitter relative to the credential's lifetime that is added to the login refresh thread's sleep time. Legal values are between 0 and 0.25 (25%) inclusive; a default value of 0.05 (5%) is used if no value is specified. Currently applies only to OAUTHBEARER.

添加到登录刷新线程睡眠时间中的相对于凭据生命周期的最大随机抖动量。法定值在0至0.25(含25%)之间;如果没有指定值，则使用默认值0.05(5%)。目前只适用于oauthholder。

|         Type: | double         |
| ------------: | -------------- |
|      Default: | 0.05           |
| Valid Values: | [0.0,...,0.25] |
|   Importance: | low            |

#### [security.providers](http://kafka.apache.org/documentation/#security.providers)

  A list of configurable creator classes each returning a provider implementing security algorithms. These classes should implement the `org.apache.kafka.common.security.auth.SecurityProviderCreator` interface.

可配置创建器类的列表，每个创建器类返回实现安全算法的提供程序。这些类应该实现' org.apache. kafga .common.security. au.securityprovidercreator '的接口。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.cipher.suites](http://kafka.apache.org/documentation/#ssl.cipher.suites)

  A list of cipher suites. This is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. By default all the available cipher suites are supported.

密码套件列表。这是一种命名的身份验证、加密、MAC和密钥交换算法的组合，用于使用TLS或SSL网络协议协商网络连接的安全设置。默认情况下，支持所有可用的密码套件。

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#ssl.endpoint.identification.algorithm)

  The endpoint identification algorithm to validate server hostname using server certificate.

使用服务器证书验证服务器主机名的端点识别算法。

|         Type: | string |
| ------------: | ------ |
|      Default: | https  |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.engine.factory.class](http://kafka.apache.org/documentation/#ssl.engine.factory.class)

  The class of type org.apache.kafka.common.security.auth.SslEngineFactory to provide SSLEngine objects. Default value is org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

org.apache. kafcard .common.security. auc . sslenginefactory类型的类来提供SSLEngine对象。默认值为org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [ssl.keymanager.algorithm](http://kafka.apache.org/documentation/#ssl.keymanager.algorithm)

  The algorithm used by key manager factory for SSL connections. Default value is the key manager factory algorithm configured for the Java Virtual Machine.

密钥管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的密钥管理器工厂算法。

|         Type: | string  |
| ------------: | ------- |
|      Default: | SunX509 |
| Valid Values: |         |
|   Importance: | low     |

#### [ssl.secure.random.implementation](http://kafka.apache.org/documentation/#ssl.secure.random.implementation)

  The SecureRandom PRNG implementation to use for SSL cryptography operations.

用于SSL加密操作的SecureRandom PRNG实现。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.trustmanager.algorithm](http://kafka.apache.org/documentation/#ssl.trustmanager.algorithm)

  The algorithm used by trust manager factory for SSL connections. Default value is the trust manager factory algorithm configured for the Java Virtual Machine.

信任管理器工厂用于SSL连接的算法。默认值是为Java虚拟机配置的信任管理器工厂算法。

|         Type: | string |
| ------------: | ------ |
|      Default: | PKIX   |
| Valid Values: |        |
|   Importance: | low    |

### [3.5 Kafka Connect Configs](http://kafka.apache.org/documentation/#connectconfigs)

Below is the configuration of the Kafka Connect framework.

下面是Kafka Connect框架的配置。

#### [config.storage.topic](http://kafka.apache.org/documentation/#config.storage.topic)

  The name of the Kafka topic where connector configurations are stored

存储连接器配置的Kafka主题的名称

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [group.id](http://kafka.apache.org/documentation/#group.id)

  A unique string that identifies the Connect cluster group this worker belongs to.

标识此工作者所属的连接集群组的唯一字符串。

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [key.converter](http://kafka.apache.org/documentation/#key.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the keys in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

转换器类，用于在Kafka连接格式和写入Kafka的序列化格式之间进行转换。它控制写入或从Kafka读取的消息中的键的格式，并且由于它独立于连接器，它允许任何连接器使用任何序列化格式。常见格式的例子包括JSON和Avro

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [offset.storage.topic](http://kafka.apache.org/documentation/#offset.storage.topic)

  The name of the Kafka topic where connector offsets are stored

存储连接器偏移量的Kafka主题的名称

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [status.storage.topic](http://kafka.apache.org/documentation/#status.storage.topic)

  The name of the Kafka topic where connector and task status are stored

存储连接器和任务状态的Kafka主题的名称

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [value.converter](http://kafka.apache.org/documentation/#value.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

转换器类，用于在Kafka连接格式和写入Kafka的序列化格式之间进行转换。它控制写入或从Kafka读取的消息中的值的格式，并且由于它独立于连接器，所以它允许任何连接器使用任何序列化格式。常见格式的例子包括JSON和Avro。

|         Type: | class |
| ------------: | ----- |
|      Default: |       |
| Valid Values: |       |
|   Importance: | high  |

#### [bootstrap.servers](http://kafka.apache.org/documentation/#bootstrap.servers)

  A list of host/port pairs to use for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2,...`. Since these servers are just used for the initial connection to discover the full cluster membership (which may change dynamically), this list need not contain the full set of servers (you may want more than one, though, in case a server is down).

用于建立到Kafka集群的初始连接的主机/端口对列表。客户机将使用所有服务器，而不管这里为引导指定了哪些服务器——此列表只影响用于发现完整服务器集的初始主机。该列表应该以“host1:port1,host2:port2，…”的形式出现。由于这些服务器仅用于初始连接，以发现完整的集群成员关系(可能会动态更改)，因此此列表不需要包含完整的服务器集(不过，如果服务器宕机，您可能需要多个服务器)。

|         Type: | list           |
| ------------: | -------------- |
|      Default: | localhost:9092 |
| Valid Values: |                |
|   Importance: | high           |

#### [heartbeat.interval.ms](http://kafka.apache.org/documentation/#heartbeat.interval.ms)

  The expected time between heartbeats to the group coordinator when using Kafka's group management facilities. Heartbeats are used to ensure that the worker's session stays active and to facilitate rebalancing when new members join or leave the group. The value must be set lower than `session.timeout.ms`, but typically should be set no higher than 1/3 of that value. It can be adjusted even lower to control the expected time for normal rebalances.

使用Kafka的组管理工具时心跳到组协调器之间的预期时间。心跳用于确保工作者会话保持活动状态，并在新成员加入或离开组时促进重新平衡。该值必须设置为低于' session.timeout.ms '，但通常应设置不超过该值的1/3。它可以调整更低，以控制正常重新平衡的预期时间。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 3000 (3 seconds) |
| Valid Values: |                  |
|   Importance: | high             |

#### [rebalance.timeout.ms](http://kafka.apache.org/documentation/#rebalance.timeout.ms)

  The maximum allowed time for each worker to join the group once a rebalance has begun. This is basically a limit on the amount of time needed for all tasks to flush any pending data and commit offsets. If the timeout is exceeded, then the worker will be removed from the group, which will cause offset commit failures.

再平衡开始后，允许每个员工加入团队的最大时间。这基本上是对所有任务刷新任何挂起数据和提交偏移量所需的时间量的限制。如果超过超时，那么工作者将从组中删除，这将导致偏移提交失败。

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: |                  |
|   Importance: | high             |

#### [session.timeout.ms](http://kafka.apache.org/documentation/#session.timeout.ms)

  The timeout used to detect worker failures. The worker sends periodic heartbeats to indicate its liveness to the broker. If no heartbeats are received by the broker before the expiration of this session timeout, then the broker will remove the worker from the group and initiate a rebalance. Note that the value must be in the allowable range as configured in the broker configuration by `group.min.session.timeout.ms` and `group.max.session.timeout.ms`.

用于检测工作人员故障的超时。工人定期发送心跳，以表明其活跃的经纪人。如果在此会话超时过期之前代理没有收到心跳，则代理将从组中删除worker并启动重新平衡。注意，该值必须在代理配置中' group.min.session.timeout '配置的允许范围内。女士”和“group.max.session.timeout.ms”。

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 10000 (10 seconds) |
| Valid Values: |                    |
|   Importance: | high               |

#### [ssl.key.password](http://kafka.apache.org/documentation/#ssl.key.password)

  The password of the private key in the key store file. This is optional for client.

密钥存储文件中私钥的密码。这对于客户机是可选的

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.keystore.location](http://kafka.apache.org/documentation/#ssl.keystore.location)

  The location of the key store file. This is optional for client and can be used for two-way authentication for client.

密钥存储文件的位置。这对于客户机是可选的，可以用于客户机的双向身份验证。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.keystore.password](http://kafka.apache.org/documentation/#ssl.keystore.password)

  The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.

密钥存储文件的存储密码。这对于客户机是可选的，只有在ssl.keystore中才需要。位置配置。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.truststore.location](http://kafka.apache.org/documentation/#ssl.truststore.location)

  The location of the trust store file.

信任存储库文件的位置。

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.truststore.password](http://kafka.apache.org/documentation/#ssl.truststore.password)

  The password for the trust store file. If a password is not set access to the truststore is still available, but integrity checking is disabled.

信任存储文件的密码。如果没有设置密码，仍然可以访问信任存储库，但是禁用了完整性检查。

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [client.dns.lookup](http://kafka.apache.org/documentation/#client.dns.lookup)

  Controls how the client uses DNS lookups. If set to `use_all_dns_ips`, connect to each returned IP address in sequence until a successful connection is established. After a disconnection, the next IP is used. Once all IPs have been used once, the client resolves the IP(s) from the hostname again (both the JVM and the OS cache DNS name lookups, however). If set to `resolve_canonical_bootstrap_servers_only`, resolve each bootstrap address into a list of canonical names. After the bootstrap phase, this behaves the same as `use_all_dns_ips`. If set to `default` (deprecated), attempt to connect to the first IP address returned by the lookup, even if the lookup returns multiple IP addresses.

控制客户端如何使用DNS查找。如果设置为' use_all_dns_ips '，依次连接到每个返回的IP地址，直到建立成功的连接。断开连接后，使用下一个IP。一旦所有IP都使用过一次，客户机就会再次从主机名解析IP(不过JVM和OS都会缓存DNS名称查找)。如果设置为' resolve_canonical_bootstrap_servers_only '，则将每个引导程序地址解析为一个规范名称列表。在引导阶段之后，它的行为与' use_all_dns_ips '相同。如果设置为‘default’(不赞成使用)，尝试连接到查找返回的第一个IP地址，即使查找返回多个IP地址。

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | use_all_dns_ips                                              |
| Valid Values: | [default, use_all_dns_ips, resolve_canonical_bootstrap_servers_only] |
|   Importance: | medium                                                       |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Close idle connections after the number of milliseconds specified by this config.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 540000 (9 minutes) |
| Valid Values: |                    |
|   Importance: | medium             |

#### [connector.client.config.override.policy](http://kafka.apache.org/documentation/#connector.client.config.override.policy)

  Class name or alias of implementation of `ConnectorClientConfigOverridePolicy`. Defines what client configurations can be overriden by the connector. The default implementation is `None`. The other possible policies in the framework include `All` and `Principal`.

|         Type: | string |
| ------------: | ------ |
|      Default: | None   |
| Valid Values: |        |
|   Importance: | medium |

#### [receive.buffer.bytes](http://kafka.apache.org/documentation/#receive.buffer.bytes)

  The size of the TCP receive buffer (SO_RCVBUF) to use when reading data. If the value is -1, the OS default will be used.

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 32768 (32 kibibytes) |
| Valid Values: | [0,...]              |
|   Importance: | medium               |

#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 40000 (40 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |

#### [sasl.client.callback.handler.class](http://kafka.apache.org/documentation/#sasl.client.callback.handler.class)

  The fully qualified name of a SASL client callback handler class that implements the AuthenticateCallbackHandler interface.

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.jaas.config](http://kafka.apache.org/documentation/#sasl.jaas.config)

  JAAS login context parameters for SASL connections in the format used by JAAS configuration files. JAAS configuration file format is described [here](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html). The format for the value is: '`loginModuleClass controlFlag (optionName=optionValue)*;`'. For brokers, the config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=com.example.ScramLoginModule required;

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | medium   |

#### [sasl.kerberos.service.name](http://kafka.apache.org/documentation/#sasl.kerberos.service.name)

  The Kerberos principal name that Kafka runs as. This can be defined either in Kafka's JAAS config or in Kafka's config.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.callback.handler.class](http://kafka.apache.org/documentation/#sasl.login.callback.handler.class)

  The fully qualified name of a SASL login callback handler class that implements the AuthenticateCallbackHandler interface. For brokers, login callback handler config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class=com.example.CustomScramLoginCallbackHandler

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.class](http://kafka.apache.org/documentation/#sasl.login.class)

  The fully qualified name of a class that implements the Login interface. For brokers, login config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.class=com.example.CustomScramLogin

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.mechanism](http://kafka.apache.org/documentation/#sasl.mechanism)

  SASL mechanism used for client connections. This may be any mechanism for which a security provider is available. GSSAPI is the default mechanism.

|         Type: | string |
| ------------: | ------ |
|      Default: | GSSAPI |
| Valid Values: |        |
|   Importance: | medium |

#### [security.protocol](http://kafka.apache.org/documentation/#security.protocol)

  Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |

#### [send.buffer.bytes](http://kafka.apache.org/documentation/#send.buffer.bytes)

  The size of the TCP send buffer (SO_SNDBUF) to use when sending data. If the value is -1, the OS default will be used.

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 131072 (128 kibibytes) |
| Valid Values: | [0,...]                |
|   Importance: | medium                 |

#### [ssl.enabled.protocols](http://kafka.apache.org/documentation/#ssl.enabled.protocols)

  The list of protocols enabled for SSL connections. The default is 'TLSv1.2,TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. With the default value for Java 11, clients and servers will prefer TLSv1.3 if both support it and fallback to TLSv1.2 otherwise (assuming both support at least TLSv1.2). This default should be fine for most cases. Also see the config documentation for `ssl.protocol`.

|         Type: | list    |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.keystore.type](http://kafka.apache.org/documentation/#ssl.keystore.type)

  The file format of the key store file. This is optional for client.

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.protocol](http://kafka.apache.org/documentation/#ssl.protocol)

  The SSL protocol used to generate the SSLContext. The default is 'TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. This value should be fine for most use cases. Allowed values in recent JVMs are 'TLSv1.2' and 'TLSv1.3'. 'TLS', 'TLSv1.1', 'SSL', 'SSLv2' and 'SSLv3' may be supported in older JVMs, but their usage is discouraged due to known security vulnerabilities. With the default value for this config and 'ssl.enabled.protocols', clients will downgrade to 'TLSv1.2' if the server does not support 'TLSv1.3'. If this config is set to 'TLSv1.2', clients will not use 'TLSv1.3' even if it is one of the values in ssl.enabled.protocols and the server only supports 'TLSv1.3'.

|         Type: | string  |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.provider](http://kafka.apache.org/documentation/#ssl.provider)

  The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.truststore.type](http://kafka.apache.org/documentation/#ssl.truststore.type)

  The file format of the trust store file.

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [worker.sync.timeout.ms](http://kafka.apache.org/documentation/#worker.sync.timeout.ms)

  When the worker is out of sync with other workers and needs to resynchronize configurations, wait up to this amount of time before giving up, leaving the group, and waiting a backoff period before rejoining.

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 3000 (3 seconds) |
| Valid Values: |                  |
|   Importance: | medium           |

#### [worker.unsync.backoff.ms](http://kafka.apache.org/documentation/#worker.unsync.backoff.ms)

  When the worker is out of sync with other workers and fails to catch up within worker.sync.timeout.ms, leave the Connect cluster for this long before rejoining.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: |                    |
|   Importance: | medium             |

#### [access.control.allow.methods](http://kafka.apache.org/documentation/#access.control.allow.methods)

  Sets the methods supported for cross origin requests by setting the Access-Control-Allow-Methods header. The default value of the Access-Control-Allow-Methods header allows cross origin requests for GET, POST and HEAD.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [access.control.allow.origin](http://kafka.apache.org/documentation/#access.control.allow.origin)

  Value to set the Access-Control-Allow-Origin header to for REST API requests.To enable cross origin access, set this to the domain of the application that should be permitted to access the API, or '*' to allow access from any domain. The default value only allows access from the domain of the REST API.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [admin.listeners](http://kafka.apache.org/documentation/#admin.listeners)

  List of comma-separated URIs the Admin REST API will listen on. The supported protocols are HTTP and HTTPS. An empty or blank string will disable this feature. The default behavior is to use the regular listener (specified by the 'listeners' property).

|         Type: | list                                                         |
| ------------: | ------------------------------------------------------------ |
|      Default: | null                                                         |
| Valid Values: | org.apache.kafka.connect.runtime.WorkerConfig$AdminListenersValidator@7b1d7fff |
|   Importance: | low                                                          |

#### [client.id](http://kafka.apache.org/documentation/#client.id)

  An id string to pass to the server when making requests. The purpose of this is to be able to track the source of requests beyond just ip/port by allowing a logical application name to be included in server-side request logging.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [config.providers](http://kafka.apache.org/documentation/#config.providers)

  Comma-separated names of `ConfigProvider` classes, loaded and used in the order specified. Implementing the interface `ConfigProvider` allows you to replace variable references in connector configurations, such as for externalized secrets.

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | low  |

#### [config.storage.replication.factor](http://kafka.apache.org/documentation/#config.storage.replication.factor)

  Replication factor used when creating the configuration storage topic

|         Type: | short                                                        |
| ------------: | ------------------------------------------------------------ |
|      Default: | 3                                                            |
| Valid Values: | Positive number not larger than the number of brokers in the Kafka cluster, or -1 to use the broker's default |
|   Importance: | low                                                          |

#### [connect.protocol](http://kafka.apache.org/documentation/#connect.protocol)

  Compatibility mode for Kafka Connect Protocol

|         Type: | string                         |
| ------------: | ------------------------------ |
|      Default: | sessioned                      |
| Valid Values: | [eager, compatible, sessioned] |
|   Importance: | low                            |

#### [header.converter](http://kafka.apache.org/documentation/#header.converter)

  HeaderConverter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the header values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro. By default, the SimpleHeaderConverter is used to serialize header values to strings and deserialize them by inferring the schemas.

|         Type: | class                                                  |
| ------------: | ------------------------------------------------------ |
|      Default: | org.apache.kafka.connect.storage.SimpleHeaderConverter |
| Valid Values: |                                                        |
|   Importance: | low                                                    |

#### [inter.worker.key.generation.algorithm](http://kafka.apache.org/documentation/#inter.worker.key.generation.algorithm)

  The algorithm to use for generating internal request keys

|         Type: | string                                                 |
| ------------: | ------------------------------------------------------ |
|      Default: | HmacSHA256                                             |
| Valid Values: | Any KeyGenerator algorithm supported by the worker JVM |
|   Importance: | low                                                    |

#### [inter.worker.key.size](http://kafka.apache.org/documentation/#inter.worker.key.size)

  The size of the key to use for signing internal requests, in bits. If null, the default key size for the key generation algorithm will be used.

|         Type: | int  |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [inter.worker.key.ttl.ms](http://kafka.apache.org/documentation/#inter.worker.key.ttl.ms)

  The TTL of generated session keys used for internal request validation (in milliseconds)

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 3600000 (1 hour)   |
| Valid Values: | [0,...,2147483647] |
|   Importance: | low                |

#### [inter.worker.signature.algorithm](http://kafka.apache.org/documentation/#inter.worker.signature.algorithm)

  The algorithm used to sign internal requests

|         Type: | string                                        |
| ------------: | --------------------------------------------- |
|      Default: | HmacSHA256                                    |
| Valid Values: | Any MAC algorithm supported by the worker JVM |
|   Importance: | low                                           |

#### [inter.worker.verification.algorithms](http://kafka.apache.org/documentation/#inter.worker.verification.algorithms)

  A list of permitted algorithms for verifying internal requests

|         Type: | list                                                         |
| ------------: | ------------------------------------------------------------ |
|      Default: | HmacSHA256                                                   |
| Valid Values: | A list of one or more MAC algorithms, each supported by the worker JVM |
|   Importance: | low                                                          |

#### [internal.key.converter](http://kafka.apache.org/documentation/#internal.key.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the keys in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro. This setting controls the format used for internal bookkeeping data used by the framework, such as configs and offsets, so users can typically use any functioning Converter implementation. Deprecated; will be removed in an upcoming version.

|         Type: | class                                       |
| ------------: | ------------------------------------------- |
|      Default: | org.apache.kafka.connect.json.JsonConverter |
| Valid Values: |                                             |
|   Importance: | low                                         |

#### [internal.value.converter](http://kafka.apache.org/documentation/#internal.value.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro. This setting controls the format used for internal bookkeeping data used by the framework, such as configs and offsets, so users can typically use any functioning Converter implementation. Deprecated; will be removed in an upcoming version.

|         Type: | class                                       |
| ------------: | ------------------------------------------- |
|      Default: | org.apache.kafka.connect.json.JsonConverter |
| Valid Values: |                                             |
|   Importance: | low                                         |

#### [listeners](http://kafka.apache.org/documentation/#listeners)

  List of comma-separated URIs the REST API will listen on. The supported protocols are HTTP and HTTPS.
  Specify hostname as 0.0.0.0 to bind to all interfaces.
  Leave hostname empty to bind to default interface.
  Examples of legal listener lists: HTTP://myhost:8083,HTTPS://myhost:8084

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [metadata.max.age.ms](http://kafka.apache.org/documentation/#metadata.max.age.ms)

  The period of time in milliseconds after which we force a refresh of metadata even if we haven't seen any partition leadership changes to proactively discover any new brokers or partitions.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | low  |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

|         Type: | string        |
| ------------: | ------------- |
|      Default: | INFO          |
| Valid Values: | [INFO, DEBUG] |
|   Importance: | low           |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [offset.flush.interval.ms](http://kafka.apache.org/documentation/#offset.flush.interval.ms)

  Interval at which to try committing offsets for tasks.

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: |                  |
|   Importance: | low              |

#### [offset.flush.timeout.ms](http://kafka.apache.org/documentation/#offset.flush.timeout.ms)

  Maximum number of milliseconds to wait for records to flush and partition offset data to be committed to offset storage before cancelling the process and restoring the offset data to be committed in a future attempt.

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: |                  |
|   Importance: | low              |

#### [offset.storage.partitions](http://kafka.apache.org/documentation/#offset.storage.partitions)

  The number of partitions used when creating the offset storage topic

|         Type: | int                                                |
| ------------: | -------------------------------------------------- |
|      Default: | 25                                                 |
| Valid Values: | Positive number, or -1 to use the broker's default |
|   Importance: | low                                                |

#### [offset.storage.replication.factor](http://kafka.apache.org/documentation/#offset.storage.replication.factor)

  Replication factor used when creating the offset storage topic

|         Type: | short                                                        |
| ------------: | ------------------------------------------------------------ |
|      Default: | 3                                                            |
| Valid Values: | Positive number not larger than the number of brokers in the Kafka cluster, or -1 to use the broker's default |
|   Importance: | low                                                          |

#### [plugin.path](http://kafka.apache.org/documentation/#plugin.path)

  List of paths separated by commas (,) that contain plugins (connectors, converters, transformations). The list should consist of top level directories that include any combination of:
  a) directories immediately containing jars with plugins and their dependencies
  b) uber-jars with plugins and their dependencies
  c) directories immediately containing the package directory structure of classes of plugins and their dependencies
  Note: symlinks will be followed to discover dependencies or plugins.
  Examples: plugin.path=/usr/local/share/java,/usr/local/share/kafka/plugins,/opt/connectors
  Do not use config provider variables in this property, since the raw path is used by the worker's scanner before config providers are initialized and used to replace variables.

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [reconnect.backoff.max.ms](http://kafka.apache.org/documentation/#reconnect.backoff.max.ms)

  The maximum amount of time in milliseconds to wait when reconnecting to a broker that has repeatedly failed to connect. If provided, the backoff per host will increase exponentially for each consecutive connection failure, up to this maximum. After calculating the backoff increase, 20% random jitter is added to avoid connection storms.

|         Type: | long            |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | low             |

#### [reconnect.backoff.ms](http://kafka.apache.org/documentation/#reconnect.backoff.ms)

  The base amount of time to wait before attempting to reconnect to a given host. This avoids repeatedly connecting to a host in a tight loop. This backoff applies to all connection attempts by the client to a broker.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 50      |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [response.http.headers.config](http://kafka.apache.org/documentation/#response.http.headers.config)

  Rules for REST API HTTP response headers

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | ""                                                           |
| Valid Values: | Comma#### [action] [header name]:[header value]' and optionally surrounded by double quotes if any part of a header rule contains a comma |
|   Importance: | low                                                          |

#### [rest.advertised.host.name](http://kafka.apache.org/documentation/#rest.advertised.host.name)

  If this is set, this is the hostname that will be given out to other workers to connect to.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [rest.advertised.listener](http://kafka.apache.org/documentation/#rest.advertised.listener)

  Sets the advertised listener (HTTP or HTTPS) which will be given to other workers to use.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [rest.advertised.port](http://kafka.apache.org/documentation/#rest.advertised.port)

  If this is set, this is the port that will be given out to other workers to connect to.

|         Type: | int  |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [rest.extension.classes](http://kafka.apache.org/documentation/#rest.extension.classes)

  Comma-separated names of `ConnectRestExtension` classes, loaded and called in the order specified. Implementing the interface `ConnectRestExtension` allows you to inject into Connect's REST API user defined resources like filters. Typically used to add custom capability like logging, security, etc.

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | low  |

#### [rest.host.name](http://kafka.apache.org/documentation/#rest.host.name)

  Hostname for the REST API. If this is set, it will only bind to this interface.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [rest.port](http://kafka.apache.org/documentation/#rest.port)

  Port for the REST API to listen on.

|         Type: | int  |
| ------------: | ---- |
|      Default: | 8083 |
| Valid Values: |      |
|   Importance: | low  |

#### [retry.backoff.ms](http://kafka.apache.org/documentation/#retry.backoff.ms)

  The amount of time to wait before attempting to retry a failed request to a given topic partition. This avoids repeatedly sending requests in a tight loop under some failure scenarios.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 100     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [sasl.kerberos.kinit.cmd](http://kafka.apache.org/documentation/#sasl.kerberos.kinit.cmd)

  Kerberos kinit command path.

|         Type: | string         |
| ------------: | -------------- |
|      Default: | /usr/bin/kinit |
| Valid Values: |                |
|   Importance: | low            |

#### [sasl.kerberos.min.time.before.relogin](http://kafka.apache.org/documentation/#sasl.kerberos.min.time.before.relogin)

  Login thread sleep time between refresh attempts.

|         Type: | long  |
| ------------: | ----- |
|      Default: | 60000 |
| Valid Values: |       |
|   Importance: | low   |

#### [sasl.kerberos.ticket.renew.jitter](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.jitter)

  Percentage of random jitter added to the renewal time.

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.05   |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.kerberos.ticket.renew.window.factor](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.window.factor)

  Login thread will sleep until the specified window factor of time from last refresh to ticket's expiry has been reached, at which time it will try to renew the ticket.

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.8    |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.login.refresh.buffer.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.buffer.seconds)

  The amount of buffer time before credential expiration to maintain when refreshing a credential, in seconds. If a refresh would otherwise occur closer to expiration than the number of buffer seconds then the refresh will be moved up to maintain as much of the buffer time as possible. Legal values are between 0 and 3600 (1 hour); a default value of 300 (5 minutes) is used if no value is specified. This value and sasl.login.refresh.min.period.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

|         Type: | short        |
| ------------: | ------------ |
|      Default: | 300          |
| Valid Values: | [0,...,3600] |
|   Importance: | low          |

#### [sasl.login.refresh.min.period.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.min.period.seconds)

  The desired minimum time for the login refresh thread to wait before refreshing a credential, in seconds. Legal values are between 0 and 900 (15 minutes); a default value of 60 (1 minute) is used if no value is specified. This value and sasl.login.refresh.buffer.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

|         Type: | short       |
| ------------: | ----------- |
|      Default: | 60          |
| Valid Values: | [0,...,900] |
|   Importance: | low         |

#### [sasl.login.refresh.window.factor](http://kafka.apache.org/documentation/#sasl.login.refresh.window.factor)

  Login refresh thread will sleep until the specified window factor relative to the credential's lifetime has been reached, at which time it will try to refresh the credential. Legal values are between 0.5 (50%) and 1.0 (100%) inclusive; a default value of 0.8 (80%) is used if no value is specified. Currently applies only to OAUTHBEARER.

|         Type: | double        |
| ------------: | ------------- |
|      Default: | 0.8           |
| Valid Values: | [0.5,...,1.0] |
|   Importance: | low           |

#### [sasl.login.refresh.window.jitter](http://kafka.apache.org/documentation/#sasl.login.refresh.window.jitter)

  The maximum amount of random jitter relative to the credential's lifetime that is added to the login refresh thread's sleep time. Legal values are between 0 and 0.25 (25%) inclusive; a default value of 0.05 (5%) is used if no value is specified. Currently applies only to OAUTHBEARER.

|         Type: | double         |
| ------------: | -------------- |
|      Default: | 0.05           |
| Valid Values: | [0.0,...,0.25] |
|   Importance: | low            |

#### [scheduled.rebalance.max.delay.ms](http://kafka.apache.org/documentation/#scheduled.rebalance.max.delay.ms)

  The maximum delay that is scheduled in order to wait for the return of one or more departed workers before rebalancing and reassigning their connectors and tasks to the group. During this period the connectors and tasks of the departed workers remain unassigned

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...,2147483647] |
|   Importance: | low                |

#### [ssl.cipher.suites](http://kafka.apache.org/documentation/#ssl.cipher.suites)

  A list of cipher suites. This is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. By default all the available cipher suites are supported.

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [ssl.client.auth](http://kafka.apache.org/documentation/#ssl.client.auth)

  Configures kafka broker to request client authentication. The following settings are common:

  - `ssl.client.auth=required` If set to required client authentication is required.
  - `ssl.client.auth=requested` This means client authentication is optional. unlike requested , if this option is set client can choose not to provide authentication information about itself
  - `ssl.client.auth=none` This means client authentication is not needed.

  

|         Type: | string |
| ------------: | ------ |
|      Default: | none   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#ssl.endpoint.identification.algorithm)

  The endpoint identification algorithm to validate server hostname using server certificate.

|         Type: | string |
| ------------: | ------ |
|      Default: | https  |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.engine.factory.class](http://kafka.apache.org/documentation/#ssl.engine.factory.class)

  The class of type org.apache.kafka.common.security.auth.SslEngineFactory to provide SSLEngine objects. Default value is org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [ssl.keymanager.algorithm](http://kafka.apache.org/documentation/#ssl.keymanager.algorithm)

  The algorithm used by key manager factory for SSL connections. Default value is the key manager factory algorithm configured for the Java Virtual Machine.

|         Type: | string  |
| ------------: | ------- |
|      Default: | SunX509 |
| Valid Values: |         |
|   Importance: | low     |

#### [ssl.secure.random.implementation](http://kafka.apache.org/documentation/#ssl.secure.random.implementation)

  The SecureRandom PRNG implementation to use for SSL cryptography operations.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.trustmanager.algorithm](http://kafka.apache.org/documentation/#ssl.trustmanager.algorithm)

  The algorithm used by trust manager factory for SSL connections. Default value is the trust manager factory algorithm configured for the Java Virtual Machine.

|         Type: | string |
| ------------: | ------ |
|      Default: | PKIX   |
| Valid Values: |        |
|   Importance: | low    |

#### [status.storage.partitions](http://kafka.apache.org/documentation/#status.storage.partitions)

  The number of partitions used when creating the status storage topic

|         Type: | int                                                |
| ------------: | -------------------------------------------------- |
|      Default: | 5                                                  |
| Valid Values: | Positive number, or -1 to use the broker's default |
|   Importance: | low                                                |

#### [status.storage.replication.factor](http://kafka.apache.org/documentation/#status.storage.replication.factor)

  Replication factor used when creating the status storage topic

|         Type: | short                                                        |
| ------------: | ------------------------------------------------------------ |
|      Default: | 3                                                            |
| Valid Values: | Positive number not larger than the number of brokers in the Kafka cluster, or -1 to use the broker's default |
|   Importance: | low                                                          |

#### [task.shutdown.graceful.timeout.ms](http://kafka.apache.org/documentation/#task.shutdown.graceful.timeout.ms)

  Amount of time to wait for tasks to shutdown gracefully. This is the total amount of time, not per task. All task have shutdown triggered, then they are waited on sequentially.

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 5000 (5 seconds) |
| Valid Values: |                  |
|   Importance: | low              |

#### [topic.creation.enable](http://kafka.apache.org/documentation/#topic.creation.enable)

  Whether to allow automatic creation of topics used by source connectors, when source connectors are configured with `topic.creation.` properties. Each task will use an admin client to create its topics and will not depend on the Kafka brokers to create topics automatically.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | low     |

#### [topic.tracking.allow.reset](http://kafka.apache.org/documentation/#topic.tracking.allow.reset)

  If set to true, it allows user requests to reset the set of active topics per connector.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | low     |

#### [topic.tracking.enable](http://kafka.apache.org/documentation/#topic.tracking.enable)

  Enable tracking the set of active topics per connector during runtime.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | true    |
| Valid Values: |         |
|   Importance: | low     |

#### 

### [3.5.1 Source Connector Configs](http://kafka.apache.org/documentation/#sourceconnectconfigs)

Below is the configuration of a source connector.

#### [name](http://kafka.apache.org/documentation/#name)

  Globally unique name to use for this connector.

|         Type: | string                                          |
| ------------: | ----------------------------------------------- |
|      Default: |                                                 |
| Valid Values: | non-empty string without ISO control characters |
|   Importance: | high                                            |

#### [connector.class](http://kafka.apache.org/documentation/#connector.class)

  Name or alias of the class for this connector. Must be a subclass of org.apache.kafka.connect.connector.Connector. If the connector is org.apache.kafka.connect.file.FileStreamSinkConnector, you can either specify this full name, or use "FileStreamSink" or "FileStreamSinkConnector" to make the configuration a bit shorter

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [tasks.max](http://kafka.apache.org/documentation/#tasks.max)

  Maximum number of tasks to use for this connector.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 1       |
| Valid Values: | [1,...] |
|   Importance: | high    |

#### [key.converter](http://kafka.apache.org/documentation/#key.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the keys in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [value.converter](http://kafka.apache.org/documentation/#value.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [header.converter](http://kafka.apache.org/documentation/#header.converter)

  HeaderConverter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the header values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro. By default, the SimpleHeaderConverter is used to serialize header values to strings and deserialize them by inferring the schemas.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [config.action.reload](http://kafka.apache.org/documentation/#config.action.reload)

  The action that Connect should take on the connector when changes in external configuration providers result in a change in the connector's configuration properties. A value of 'none' indicates that Connect will do nothing. A value of 'restart' indicates that Connect should restart/reload the connector with the updated configuration properties.The restart may actually be scheduled in the future if the external configuration provider indicates that a configuration value will expire in the future.

|         Type: | string          |
| ------------: | --------------- |
|      Default: | restart         |
| Valid Values: | [none, restart] |
|   Importance: | low             |

#### [transforms](http://kafka.apache.org/documentation/#transforms)

  Aliases for the transformations to be applied to records.

|         Type: | list                                           |
| ------------: | ---------------------------------------------- |
|      Default: | ""                                             |
| Valid Values: | non-null string, unique transformation aliases |
|   Importance: | low                                            |

#### [predicates](http://kafka.apache.org/documentation/#predicates)

  Aliases for the predicates used by transformations.

|         Type: | list                                      |
| ------------: | ----------------------------------------- |
|      Default: | ""                                        |
| Valid Values: | non-null string, unique predicate aliases |
|   Importance: | low                                       |

#### [errors.retry.timeout](http://kafka.apache.org/documentation/#errors.retry.timeout)

  The maximum duration in milliseconds that a failed operation will be reattempted. The default is 0, which means no retries will be attempted. Use -1 for infinite retries.

|         Type: | long   |
| ------------: | ------ |
|      Default: | 0      |
| Valid Values: |        |
|   Importance: | medium |

#### [errors.retry.delay.max.ms](http://kafka.apache.org/documentation/#errors.retry.delay.max.ms)

  The maximum duration in milliseconds between consecutive retry attempts. Jitter will be added to the delay once this limit is reached to prevent thundering herd issues.

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: |                  |
|   Importance: | medium           |

#### [errors.tolerance](http://kafka.apache.org/documentation/#errors.tolerance)

  Behavior for tolerating errors during connector operation. 'none' is the default value and signals that any error will result in an immediate connector task failure; 'all' changes the behavior to skip over problematic records.

|         Type: | string      |
| ------------: | ----------- |
|      Default: | none        |
| Valid Values: | [none, all] |
|   Importance: | medium      |

#### [errors.log.enable](http://kafka.apache.org/documentation/#errors.log.enable)

  If true, write each error and the details of the failed operation and problematic record to the Connect application log. This is 'false' by default, so that only errors that are not tolerated are reported.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | medium  |

#### [errors.log.include.messages](http://kafka.apache.org/documentation/#errors.log.include.messages)

  Whether to the include in the log the Connect record that resulted in a failure. This is 'false' by default, which will prevent record keys, values, and headers from being written to log files, although some information such as topic and partition number will still be logged.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | medium  |

#### [topic.creation.groups](http://kafka.apache.org/documentation/#topic.creation.groups)

  Groups of configurations for topics created by source connectors

|         Type: | list                                          |
| ------------: | --------------------------------------------- |
|      Default: | ""                                            |
| Valid Values: | non-null string, unique topic creation groups |
|   Importance: | low                                           |

### 

### [3.5.2 Sink Connector Configs](http://kafka.apache.org/documentation/#sinkconnectconfigs)

Below is the configuration of a sink connector.

#### [name](http://kafka.apache.org/documentation/#name)

  Globally unique name to use for this connector.

|         Type: | string                                          |
| ------------: | ----------------------------------------------- |
|      Default: |                                                 |
| Valid Values: | non-empty string without ISO control characters |
|   Importance: | high                                            |

#### [connector.class](http://kafka.apache.org/documentation/#connector.class)

  Name or alias of the class for this connector. Must be a subclass of org.apache.kafka.connect.connector.Connector. If the connector is org.apache.kafka.connect.file.FileStreamSinkConnector, you can either specify this full name, or use "FileStreamSink" or "FileStreamSinkConnector" to make the configuration a bit shorter

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [tasks.max](http://kafka.apache.org/documentation/#tasks.max)

  Maximum number of tasks to use for this connector.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 1       |
| Valid Values: | [1,...] |
|   Importance: | high    |

#### [topics](http://kafka.apache.org/documentation/#topics)

  List of topics to consume, separated by commas

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | high |

#### [topics.regex](http://kafka.apache.org/documentation/#topics.regex)

  Regular expression giving topics to consume. Under the hood, the regex is compiled to a `java.util.regex.Pattern`. Only one of topics or topics.regex should be specified.

|         Type: | string      |
| ------------: | ----------- |
|      Default: | ""          |
| Valid Values: | valid regex |
|   Importance: | high        |

#### [key.converter](http://kafka.apache.org/documentation/#key.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the keys in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [value.converter](http://kafka.apache.org/documentation/#value.converter)

  Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [header.converter](http://kafka.apache.org/documentation/#header.converter)

  HeaderConverter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the header values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro. By default, the SimpleHeaderConverter is used to serialize header values to strings and deserialize them by inferring the schemas.

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [config.action.reload](http://kafka.apache.org/documentation/#config.action.reload)

  The action that Connect should take on the connector when changes in external configuration providers result in a change in the connector's configuration properties. A value of 'none' indicates that Connect will do nothing. A value of 'restart' indicates that Connect should restart/reload the connector with the updated configuration properties.The restart may actually be scheduled in the future if the external configuration provider indicates that a configuration value will expire in the future.

|         Type: | string          |
| ------------: | --------------- |
|      Default: | restart         |
| Valid Values: | [none, restart] |
|   Importance: | low             |

#### [transforms](http://kafka.apache.org/documentation/#transforms)

  Aliases for the transformations to be applied to records.

|         Type: | list                                           |
| ------------: | ---------------------------------------------- |
|      Default: | ""                                             |
| Valid Values: | non-null string, unique transformation aliases |
|   Importance: | low                                            |

#### [predicates](http://kafka.apache.org/documentation/#predicates)

  Aliases for the predicates used by transformations.

|         Type: | list                                      |
| ------------: | ----------------------------------------- |
|      Default: | ""                                        |
| Valid Values: | non-null string, unique predicate aliases |
|   Importance: | low                                       |

#### [errors.retry.timeout](http://kafka.apache.org/documentation/#errors.retry.timeout)

  The maximum duration in milliseconds that a failed operation will be reattempted. The default is 0, which means no retries will be attempted. Use -1 for infinite retries.

|         Type: | long   |
| ------------: | ------ |
|      Default: | 0      |
| Valid Values: |        |
|   Importance: | medium |

#### [errors.retry.delay.max.ms](http://kafka.apache.org/documentation/#errors.retry.delay.max.ms)

  The maximum duration in milliseconds between consecutive retry attempts. Jitter will be added to the delay once this limit is reached to prevent thundering herd issues.

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: |                  |
|   Importance: | medium           |

#### [errors.tolerance](http://kafka.apache.org/documentation/#errors.tolerance)

  Behavior for tolerating errors during connector operation. 'none' is the default value and signals that any error will result in an immediate connector task failure; 'all' changes the behavior to skip over problematic records.

|         Type: | string      |
| ------------: | ----------- |
|      Default: | none        |
| Valid Values: | [none, all] |
|   Importance: | medium      |

#### [errors.log.enable](http://kafka.apache.org/documentation/#errors.log.enable)

  If true, write each error and the details of the failed operation and problematic record to the Connect application log. This is 'false' by default, so that only errors that are not tolerated are reported.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | medium  |

#### [errors.log.include.messages](http://kafka.apache.org/documentation/#errors.log.include.messages)

  Whether to the include in the log the Connect record that resulted in a failure. This is 'false' by default, which will prevent record keys, values, and headers from being written to log files, although some information such as topic and partition number will still be logged.

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | medium  |

#### [errors.deadletterqueue.topic.name](http://kafka.apache.org/documentation/#errors.deadletterqueue.topic.name)

  The name of the topic to be used as the dead letter queue (DLQ) for messages that result in an error when processed by this sink connector, or its transformations or converters. The topic name is blank by default, which means that no messages are to be recorded in the DLQ.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | medium |

#### [errors.deadletterqueue.topic.replication.factor](http://kafka.apache.org/documentation/#errors.deadletterqueue.topic.replication.factor)

  Replication factor used to create the dead letter queue topic when it doesn't already exist.

|         Type: | short  |
| ------------: | ------ |
|      Default: | 3      |
| Valid Values: |        |
|   Importance: | medium |

#### [errors.deadletterqueue.context.headers.enable](http://kafka.apache.org/documentation/#errors.deadletterqueue.context.headers.enable)

  If true, add headers containing error context to the messages written to the dead letter queue. To avoid clashing with headers from the original record, all error context header keys, all error context header keys will start with `__connect.errors.`

|         Type: | boolean |
| ------------: | ------- |
|      Default: | false   |
| Valid Values: |         |
|   Importance: | medium  |

### 

### [3.6 Kafka Streams Configs](http://kafka.apache.org/documentation/#streamsconfigs)

Below is the configuration of the Kafka Streams client library.

#### [application.id](http://kafka.apache.org/documentation/#application.id)

  An identifier for the stream processing application. Must be unique within the Kafka cluster. It is used as 1) the default client-id prefix, 2) the group-id for membership management, 3) the changelog topic prefix.

|         Type: | string |
| ------------: | ------ |
|      Default: |        |
| Valid Values: |        |
|   Importance: | high   |

#### [bootstrap.servers](http://kafka.apache.org/documentation/#bootstrap.servers)

  A list of host/port pairs to use for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2,...`. Since these servers are just used for the initial connection to discover the full cluster membership (which may change dynamically), this list need not contain the full set of servers (you may want more than one, though, in case a server is down).

|         Type: | list |
| ------------: | ---- |
|      Default: |      |
| Valid Values: |      |
|   Importance: | high |

#### [replication.factor](http://kafka.apache.org/documentation/#replication.factor)

  The replication factor for change log topics and repartition topics created by the stream processing application.

|         Type: | int  |
| ------------: | ---- |
|      Default: | 1    |
| Valid Values: |      |
|   Importance: | high |

#### [state.dir](http://kafka.apache.org/documentation/#state.dir)

  Directory location for state store. This path must be unique for each streams instance sharing the same underlying filesystem.

|         Type: | string             |
| ------------: | ------------------ |
|      Default: | /tmp/kafka-streams |
| Valid Values: |                    |
|   Importance: | high               |

#### [acceptable.recovery.lag](http://kafka.apache.org/documentation/#acceptable.recovery.lag)

  The maximum acceptable lag (number of offsets to catch up) for a client to be considered caught-up for an active task.Should correspond to a recovery time of well under a minute for a given workload. Must be at least 0.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 10000   |
| Valid Values: | [0,...] |
|   Importance: | medium  |

#### [cache.max.bytes.buffering](http://kafka.apache.org/documentation/#cache.max.bytes.buffering)

  Maximum number of memory bytes to be used for buffering across all threads

|         Type: | long     |
| ------------: | -------- |
|      Default: | 10485760 |
| Valid Values: | [0,...]  |
|   Importance: | medium   |

#### [client.id](http://kafka.apache.org/documentation/#client.id)

  An ID prefix string used for the client IDs of internal consumer, producer and restore-consumer, with pattern '-StreamThread--'.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | medium |

#### [default.deserialization.exception.handler](http://kafka.apache.org/documentation/#default.deserialization.exception.handler)

  Exception handling class that implements the `org.apache.kafka.streams.errors.DeserializationExceptionHandler` interface.

|         Type: | class                                                      |
| ------------: | ---------------------------------------------------------- |
|      Default: | org.apache.kafka.streams.errors.LogAndFailExceptionHandler |
| Valid Values: |                                                            |
|   Importance: | medium                                                     |

#### [default.key.serde](http://kafka.apache.org/documentation/#default.key.serde)

  Default serializer / deserializer class for key that implements the `org.apache.kafka.common.serialization.Serde` interface. Note when windowed serde class is used, one needs to set the inner serde class that implements the `org.apache.kafka.common.serialization.Serde` interface via 'default.windowed.key.serde.inner' or 'default.windowed.value.serde.inner' as well

|         Type: | class                                                       |
| ------------: | ----------------------------------------------------------- |
|      Default: | org.apache.kafka.common.serialization.Serdes$ByteArraySerde |
| Valid Values: |                                                             |
|   Importance: | medium                                                      |

#### [default.production.exception.handler](http://kafka.apache.org/documentation/#default.production.exception.handler)

  Exception handling class that implements the `org.apache.kafka.streams.errors.ProductionExceptionHandler` interface.

|         Type: | class                                                        |
| ------------: | ------------------------------------------------------------ |
|      Default: | org.apache.kafka.streams.errors.DefaultProductionExceptionHandler |
| Valid Values: |                                                              |
|   Importance: | medium                                                       |

#### [default.timestamp.extractor](http://kafka.apache.org/documentation/#default.timestamp.extractor)

  Default timestamp extractor class that implements the `org.apache.kafka.streams.processor.TimestampExtractor` interface.

|         Type: | class                                                     |
| ------------: | --------------------------------------------------------- |
|      Default: | org.apache.kafka.streams.processor.FailOnInvalidTimestamp |
| Valid Values: |                                                           |
|   Importance: | medium                                                    |

#### [default.value.serde](http://kafka.apache.org/documentation/#default.value.serde)

  Default serializer / deserializer class for value that implements the `org.apache.kafka.common.serialization.Serde` interface. Note when windowed serde class is used, one needs to set the inner serde class that implements the `org.apache.kafka.common.serialization.Serde` interface via 'default.windowed.key.serde.inner' or 'default.windowed.value.serde.inner' as well

|         Type: | class                                                       |
| ------------: | ----------------------------------------------------------- |
|      Default: | org.apache.kafka.common.serialization.Serdes$ByteArraySerde |
| Valid Values: |                                                             |
|   Importance: | medium                                                      |

#### [max.task.idle.ms](http://kafka.apache.org/documentation/#max.task.idle.ms)

  Maximum amount of time a stream task will stay idle when not all of its partition buffers contain records, to avoid potential out-of-order record processing across multiple input streams.

|         Type: | long   |
| ------------: | ------ |
|      Default: | 0      |
| Valid Values: |        |
|   Importance: | medium |

#### [max.warmup.replicas](http://kafka.apache.org/documentation/#max.warmup.replicas)

  The maximum number of warmup replicas (extra standbys beyond the configured num.standbys) that can be assigned at once for the purpose of keeping the task available on one instance while it is warming up on another instance it has been reassigned to. Used to throttle how much extra broker traffic and cluster state can be used for high availability. Must be at least 1.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | medium  |

#### [num.standby.replicas](http://kafka.apache.org/documentation/#num.standby.replicas)

  The number of standby replicas for each task.

|         Type: | int    |
| ------------: | ------ |
|      Default: | 0      |
| Valid Values: |        |
|   Importance: | medium |

#### [num.stream.threads](http://kafka.apache.org/documentation/#num.stream.threads)

  The number of threads to execute stream processing.

|         Type: | int    |
| ------------: | ------ |
|      Default: | 1      |
| Valid Values: |        |
|   Importance: | medium |

#### [processing.guarantee](http://kafka.apache.org/documentation/#processing.guarantee)

  The processing guarantee that should be used. Possible values are `at_least_once` (default), `exactly_once` (requires brokers version 0.11.0 or higher), and `exactly_once_beta` (requires brokers version 2.5 or higher). Note that exactly-once processing requires a cluster of at least three brokers by default what is the recommended setting for production; for development you can change this, by adjusting broker setting `transaction.state.log.replication.factor` and `transaction.state.log.min.isr`.

|         Type: | string                                           |
| ------------: | ------------------------------------------------ |
|      Default: | at_least_once                                    |
| Valid Values: | [at_least_once, exactly_once, exactly_once_beta] |
|   Importance: | medium                                           |

#### [security.protocol](http://kafka.apache.org/documentation/#security.protocol)

  Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |

#### [topology.optimization](http://kafka.apache.org/documentation/#topology.optimization)

  A configuration telling Kafka Streams if it should optimize the topology, disabled by default

|         Type: | string      |
| ------------: | ----------- |
|      Default: | none        |
| Valid Values: | [none, all] |
|   Importance: | medium      |

#### [application.server](http://kafka.apache.org/documentation/#application.server)

  A host:port pair pointing to a user-defined endpoint that can be used for state store discovery and interactive queries on this KafkaStreams instance.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | low    |

#### [buffered.records.per.partition](http://kafka.apache.org/documentation/#buffered.records.per.partition)

  Maximum number of records to buffer per partition.

|         Type: | int  |
| ------------: | ---- |
|      Default: | 1000 |
| Valid Values: |      |
|   Importance: | low  |

#### [built.in.metrics.version](http://kafka.apache.org/documentation/#built.in.metrics.version)

  Version of the built-in metrics to use.

|         Type: | string               |
| ------------: | -------------------- |
|      Default: | latest               |
| Valid Values: | [0.10.0-2.4, latest] |
|   Importance: | low                  |

#### [commit.interval.ms](http://kafka.apache.org/documentation/#commit.interval.ms)

  The frequency with which to save the position of the processor. (Note, if `processing.guarantee` is set to `exactly_once`, the default value is `100`, otherwise the default value is `30000`.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Close idle connections after the number of milliseconds specified by this config.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 540000 (9 minutes) |
| Valid Values: |                    |
|   Importance: | low                |

#### [metadata.max.age.ms](http://kafka.apache.org/documentation/#metadata.max.age.ms)

  The period of time in milliseconds after which we force a refresh of metadata even if we haven't seen any partition leadership changes to proactively discover any new brokers or partitions.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | low  |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

|         Type: | string        |
| ------------: | ------------- |
|      Default: | INFO          |
| Valid Values: | [INFO, DEBUG] |
|   Importance: | low           |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [partition.grouper](http://kafka.apache.org/documentation/#partition.grouper)

  Partition grouper class that implements the `org.apache.kafka.streams.processor.PartitionGrouper` interface. WARNING: This config is deprecated and will be removed in 3.0.0 release.

|         Type: | class                                                      |
| ------------: | ---------------------------------------------------------- |
|      Default: | org.apache.kafka.streams.processor.DefaultPartitionGrouper |
| Valid Values: |                                                            |
|   Importance: | low                                                        |

#### [poll.ms](http://kafka.apache.org/documentation/#poll.ms)

  The amount of time in milliseconds to block waiting for input.

|         Type: | long |
| ------------: | ---- |
|      Default: | 100  |
| Valid Values: |      |
|   Importance: | low  |

#### [probing.rebalance.interval.ms](http://kafka.apache.org/documentation/#probing.rebalance.interval.ms)

  The maximum time to wait before triggering a rebalance to probe for warmup replicas that have finished warming up and are ready to become active. Probing rebalances will continue to be triggered until the assignment is balanced. Must be at least 1 minute.

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 600000 (10 minutes) |
| Valid Values: | [60000,...]         |
|   Importance: | low                 |

#### [receive.buffer.bytes](http://kafka.apache.org/documentation/#receive.buffer.bytes)

  The size of the TCP receive buffer (SO_RCVBUF) to use when reading data. If the value is -1, the OS default will be used.

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 32768 (32 kibibytes) |
| Valid Values: | [-1,...]             |
|   Importance: | low                  |

#### [reconnect.backoff.max.ms](http://kafka.apache.org/documentation/#reconnect.backoff.max.ms)

  The maximum amount of time in milliseconds to wait when reconnecting to a broker that has repeatedly failed to connect. If provided, the backoff per host will increase exponentially for each consecutive connection failure, up to this maximum. After calculating the backoff increase, 20% random jitter is added to avoid connection storms.

|         Type: | long            |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | low             |

#### [reconnect.backoff.ms](http://kafka.apache.org/documentation/#reconnect.backoff.ms)

  The base amount of time to wait before attempting to reconnect to a given host. This avoids repeatedly connecting to a host in a tight loop. This backoff applies to all connection attempts by the client to a broker.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 50      |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 40000 (40 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [retries](http://kafka.apache.org/documentation/#retries)

  Setting a value greater than zero will cause the client to resend any request that fails with a potentially transient error.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 0                  |
| Valid Values: | [0,...,2147483647] |
|   Importance: | low                |

#### [retry.backoff.ms](http://kafka.apache.org/documentation/#retry.backoff.ms)

  The amount of time to wait before attempting to retry a failed request to a given topic partition. This avoids repeatedly sending requests in a tight loop under some failure scenarios.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 100     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [rocksdb.config.setter](http://kafka.apache.org/documentation/#rocksdb.config.setter)

  A Rocks DB config setter class or class name that implements the `org.apache.kafka.streams.state.RocksDBConfigSetter` interface

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [send.buffer.bytes](http://kafka.apache.org/documentation/#send.buffer.bytes)

  The size of the TCP send buffer (SO_SNDBUF) to use when sending data. If the value is -1, the OS default will be used.

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 131072 (128 kibibytes) |
| Valid Values: | [-1,...]               |
|   Importance: | low                    |

#### [state.cleanup.delay.ms](http://kafka.apache.org/documentation/#state.cleanup.delay.ms)

  The amount of time in milliseconds to wait before deleting state when a partition has migrated. Only state directories that have not been modified for at least `state.cleanup.delay.ms` will be removed

|         Type: | long                |
| ------------: | ------------------- |
|      Default: | 600000 (10 minutes) |
| Valid Values: |                     |
|   Importance: | low                 |

#### [upgrade.from](http://kafka.apache.org/documentation/#upgrade.from)

  Allows upgrading in a backward compatible way. This is needed when upgrading from [0.10.0, 1.1] to 2.0+, or when upgrading from [2.0, 2.3] to 2.4+. When upgrading from 2.4 to a newer version it is not required to specify this config. Default is `null`. Accepted values are "0.10.0", "0.10.1", "0.10.2", "0.11.0", "1.0", "1.1", "2.0", "2.1", "2.2", "2.3" (for upgrading from the corresponding old version).

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | null                                                         |
| Valid Values: | [null, 0.10.0, 0.10.1, 0.10.2, 0.11.0, 1.0, 1.1, 2.0, 2.1, 2.2, 2.3] |
|   Importance: | low                                                          |

#### [windowstore.changelog.additional.retention.ms](http://kafka.apache.org/documentation/#windowstore.changelog.additional.retention.ms)

  Added to a windows maintainMs to ensure data is not deleted from the log prematurely. Allows for clock drift. Default is 1 day

|         Type: | long             |
| ------------: | ---------------- |
|      Default: | 86400000 (1 day) |
| Valid Values: |                  |
|   Importance: | low              |

### [3.7 Admin Configs](http://kafka.apache.org/documentation/#adminclientconfigs)

Below is the configuration of the Kafka Admin client library.

#### [bootstrap.servers](http://kafka.apache.org/documentation/#bootstrap.servers)

  A list of host/port pairs to use for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2,...`. Since these servers are just used for the initial connection to discover the full cluster membership (which may change dynamically), this list need not contain the full set of servers (you may want more than one, though, in case a server is down).

|         Type: | list |
| ------------: | ---- |
|      Default: |      |
| Valid Values: |      |
|   Importance: | high |

#### [ssl.key.password](http://kafka.apache.org/documentation/#ssl.key.password)

  The password of the private key in the key store file. This is optional for client.

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.keystore.location](http://kafka.apache.org/documentation/#ssl.keystore.location)

  The location of the key store file. This is optional for client and can be used for two-way authentication for client.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.keystore.password](http://kafka.apache.org/documentation/#ssl.keystore.password)

  The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [ssl.truststore.location](http://kafka.apache.org/documentation/#ssl.truststore.location)

  The location of the trust store file.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | high   |

#### [ssl.truststore.password](http://kafka.apache.org/documentation/#ssl.truststore.password)

  The password for the trust store file. If a password is not set access to the truststore is still available, but integrity checking is disabled.

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | high     |

#### [client.dns.lookup](http://kafka.apache.org/documentation/#client.dns.lookup)

  Controls how the client uses DNS lookups. If set to `use_all_dns_ips`, connect to each returned IP address in sequence until a successful connection is established. After a disconnection, the next IP is used. Once all IPs have been used once, the client resolves the IP(s) from the hostname again (both the JVM and the OS cache DNS name lookups, however). If set to `resolve_canonical_bootstrap_servers_only`, resolve each bootstrap address into a list of canonical names. After the bootstrap phase, this behaves the same as `use_all_dns_ips`. If set to `default` (deprecated), attempt to connect to the first IP address returned by the lookup, even if the lookup returns multiple IP addresses.

|         Type: | string                                                       |
| ------------: | ------------------------------------------------------------ |
|      Default: | use_all_dns_ips                                              |
| Valid Values: | [default, use_all_dns_ips, resolve_canonical_bootstrap_servers_only] |
|   Importance: | medium                                                       |

#### [client.id](http://kafka.apache.org/documentation/#client.id)

  An id string to pass to the server when making requests. The purpose of this is to be able to track the source of requests beyond just ip/port by allowing a logical application name to be included in server-side request logging.

|         Type: | string |
| ------------: | ------ |
|      Default: | ""     |
| Valid Values: |        |
|   Importance: | medium |

#### [connections.max.idle.ms](http://kafka.apache.org/documentation/#connections.max.idle.ms)

  Close idle connections after the number of milliseconds specified by this config.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: |                    |
|   Importance: | medium             |

#### [default.api.timeout.ms](http://kafka.apache.org/documentation/#default.api.timeout.ms)

  Specifies the timeout (in milliseconds) for client APIs. This configuration is used as the default timeout for all client operations that do not specify a `timeout` parameter.

|         Type: | int              |
| ------------: | ---------------- |
|      Default: | 60000 (1 minute) |
| Valid Values: | [0,...]          |
|   Importance: | medium           |

#### [receive.buffer.bytes](http://kafka.apache.org/documentation/#receive.buffer.bytes)

  The size of the TCP receive buffer (SO_RCVBUF) to use when reading data. If the value is -1, the OS default will be used.

|         Type: | int                  |
| ------------: | -------------------- |
|      Default: | 65536 (64 kibibytes) |
| Valid Values: | [-1,...]             |
|   Importance: | medium               |

#### [request.timeout.ms](http://kafka.apache.org/documentation/#request.timeout.ms)

  The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | medium             |

#### [sasl.client.callback.handler.class](http://kafka.apache.org/documentation/#sasl.client.callback.handler.class)

  The fully qualified name of a SASL client callback handler class that implements the AuthenticateCallbackHandler interface.

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.jaas.config](http://kafka.apache.org/documentation/#sasl.jaas.config)

  JAAS login context parameters for SASL connections in the format used by JAAS configuration files. JAAS configuration file format is described [here](http://docs.oracle.com/javase/8/docs/technotes/guides/security/jgss/tutorials/LoginConfigFile.html). The format for the value is: '`loginModuleClass controlFlag (optionName=optionValue)*;`'. For brokers, the config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=com.example.ScramLoginModule required;

|         Type: | password |
| ------------: | -------- |
|      Default: | null     |
| Valid Values: |          |
|   Importance: | medium   |

#### [sasl.kerberos.service.name](http://kafka.apache.org/documentation/#sasl.kerberos.service.name)

  The Kerberos principal name that Kafka runs as. This can be defined either in Kafka's JAAS config or in Kafka's config.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.callback.handler.class](http://kafka.apache.org/documentation/#sasl.login.callback.handler.class)

  The fully qualified name of a SASL login callback handler class that implements the AuthenticateCallbackHandler interface. For brokers, login callback handler config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.callback.handler.class=com.example.CustomScramLoginCallbackHandler

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.login.class](http://kafka.apache.org/documentation/#sasl.login.class)

  The fully qualified name of a class that implements the Login interface. For brokers, login config must be prefixed with listener prefix and SASL mechanism name in lower-case. For example, listener.name.sasl_ssl.scram-sha-256.sasl.login.class=com.example.CustomScramLogin

|         Type: | class  |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [sasl.mechanism](http://kafka.apache.org/documentation/#sasl.mechanism)

  SASL mechanism used for client connections. This may be any mechanism for which a security provider is available. GSSAPI is the default mechanism.

|         Type: | string |
| ------------: | ------ |
|      Default: | GSSAPI |
| Valid Values: |        |
|   Importance: | medium |

#### [security.protocol](http://kafka.apache.org/documentation/#security.protocol)

  Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.

|         Type: | string    |
| ------------: | --------- |
|      Default: | PLAINTEXT |
| Valid Values: |           |
|   Importance: | medium    |

#### [send.buffer.bytes](http://kafka.apache.org/documentation/#send.buffer.bytes)

  The size of the TCP send buffer (SO_SNDBUF) to use when sending data. If the value is -1, the OS default will be used.

|         Type: | int                    |
| ------------: | ---------------------- |
|      Default: | 131072 (128 kibibytes) |
| Valid Values: | [-1,...]               |
|   Importance: | medium                 |

#### [ssl.enabled.protocols](http://kafka.apache.org/documentation/#ssl.enabled.protocols)

  The list of protocols enabled for SSL connections. The default is 'TLSv1.2,TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. With the default value for Java 11, clients and servers will prefer TLSv1.3 if both support it and fallback to TLSv1.2 otherwise (assuming both support at least TLSv1.2). This default should be fine for most cases. Also see the config documentation for `ssl.protocol`.

|         Type: | list    |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.keystore.type](http://kafka.apache.org/documentation/#ssl.keystore.type)

  The file format of the key store file. This is optional for client.

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.protocol](http://kafka.apache.org/documentation/#ssl.protocol)

  The SSL protocol used to generate the SSLContext. The default is 'TLSv1.3' when running with Java 11 or newer, 'TLSv1.2' otherwise. This value should be fine for most use cases. Allowed values in recent JVMs are 'TLSv1.2' and 'TLSv1.3'. 'TLS', 'TLSv1.1', 'SSL', 'SSLv2' and 'SSLv3' may be supported in older JVMs, but their usage is discouraged due to known security vulnerabilities. With the default value for this config and 'ssl.enabled.protocols', clients will downgrade to 'TLSv1.2' if the server does not support 'TLSv1.3'. If this config is set to 'TLSv1.2', clients will not use 'TLSv1.3' even if it is one of the values in ssl.enabled.protocols and the server only supports 'TLSv1.3'.

|         Type: | string  |
| ------------: | ------- |
|      Default: | TLSv1.2 |
| Valid Values: |         |
|   Importance: | medium  |

#### [ssl.provider](http://kafka.apache.org/documentation/#ssl.provider)

  The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | medium |

#### [ssl.truststore.type](http://kafka.apache.org/documentation/#ssl.truststore.type)

  The file format of the trust store file.

|         Type: | string |
| ------------: | ------ |
|      Default: | JKS    |
| Valid Values: |        |
|   Importance: | medium |

#### [metadata.max.age.ms](http://kafka.apache.org/documentation/#metadata.max.age.ms)

  The period of time in milliseconds after which we force a refresh of metadata even if we haven't seen any partition leadership changes to proactively discover any new brokers or partitions.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 300000 (5 minutes) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [metric.reporters](http://kafka.apache.org/documentation/#metric.reporters)

  A list of classes to use as metrics reporters. Implementing the `org.apache.kafka.common.metrics.MetricsReporter` interface allows plugging in classes that will be notified of new metric creation. The JmxReporter is always included to register JMX statistics.

|         Type: | list |
| ------------: | ---- |
|      Default: | ""   |
| Valid Values: |      |
|   Importance: | low  |

#### [metrics.num.samples](http://kafka.apache.org/documentation/#metrics.num.samples)

  The number of samples maintained to compute metrics.

|         Type: | int     |
| ------------: | ------- |
|      Default: | 2       |
| Valid Values: | [1,...] |
|   Importance: | low     |

#### [metrics.recording.level](http://kafka.apache.org/documentation/#metrics.recording.level)

  The highest recording level for metrics.

|         Type: | string        |
| ------------: | ------------- |
|      Default: | INFO          |
| Valid Values: | [INFO, DEBUG] |
|   Importance: | low           |

#### [metrics.sample.window.ms](http://kafka.apache.org/documentation/#metrics.sample.window.ms)

  The window of time a metrics sample is computed over.

|         Type: | long               |
| ------------: | ------------------ |
|      Default: | 30000 (30 seconds) |
| Valid Values: | [0,...]            |
|   Importance: | low                |

#### [reconnect.backoff.max.ms](http://kafka.apache.org/documentation/#reconnect.backoff.max.ms)

  The maximum amount of time in milliseconds to wait when reconnecting to a broker that has repeatedly failed to connect. If provided, the backoff per host will increase exponentially for each consecutive connection failure, up to this maximum. After calculating the backoff increase, 20% random jitter is added to avoid connection storms.

|         Type: | long            |
| ------------: | --------------- |
|      Default: | 1000 (1 second) |
| Valid Values: | [0,...]         |
|   Importance: | low             |

#### [reconnect.backoff.ms](http://kafka.apache.org/documentation/#reconnect.backoff.ms)

  The base amount of time to wait before attempting to reconnect to a given host. This avoids repeatedly connecting to a host in a tight loop. This backoff applies to all connection attempts by the client to a broker.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 50      |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [retries](http://kafka.apache.org/documentation/#retries)

  Setting a value greater than zero will cause the client to resend any request that fails with a potentially transient error.

|         Type: | int                |
| ------------: | ------------------ |
|      Default: | 2147483647         |
| Valid Values: | [0,...,2147483647] |
|   Importance: | low                |

#### [retry.backoff.ms](http://kafka.apache.org/documentation/#retry.backoff.ms)

  The amount of time to wait before attempting to retry a failed request. This avoids repeatedly sending requests in a tight loop under some failure scenarios.

|         Type: | long    |
| ------------: | ------- |
|      Default: | 100     |
| Valid Values: | [0,...] |
|   Importance: | low     |

#### [sasl.kerberos.kinit.cmd](http://kafka.apache.org/documentation/#sasl.kerberos.kinit.cmd)

  Kerberos kinit command path.

|         Type: | string         |
| ------------: | -------------- |
|      Default: | /usr/bin/kinit |
| Valid Values: |                |
|   Importance: | low            |

#### [sasl.kerberos.min.time.before.relogin](http://kafka.apache.org/documentation/#sasl.kerberos.min.time.before.relogin)

  Login thread sleep time between refresh attempts.

|         Type: | long  |
| ------------: | ----- |
|      Default: | 60000 |
| Valid Values: |       |
|   Importance: | low   |

#### [sasl.kerberos.ticket.renew.jitter](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.jitter)

  Percentage of random jitter added to the renewal time.

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.05   |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.kerberos.ticket.renew.window.factor](http://kafka.apache.org/documentation/#sasl.kerberos.ticket.renew.window.factor)

  Login thread will sleep until the specified window factor of time from last refresh to ticket's expiry has been reached, at which time it will try to renew the ticket.

|         Type: | double |
| ------------: | ------ |
|      Default: | 0.8    |
| Valid Values: |        |
|   Importance: | low    |

#### [sasl.login.refresh.buffer.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.buffer.seconds)

  The amount of buffer time before credential expiration to maintain when refreshing a credential, in seconds. If a refresh would otherwise occur closer to expiration than the number of buffer seconds then the refresh will be moved up to maintain as much of the buffer time as possible. Legal values are between 0 and 3600 (1 hour); a default value of 300 (5 minutes) is used if no value is specified. This value and sasl.login.refresh.min.period.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

|         Type: | short        |
| ------------: | ------------ |
|      Default: | 300          |
| Valid Values: | [0,...,3600] |
|   Importance: | low          |

#### [sasl.login.refresh.min.period.seconds](http://kafka.apache.org/documentation/#sasl.login.refresh.min.period.seconds)

  The desired minimum time for the login refresh thread to wait before refreshing a credential, in seconds. Legal values are between 0 and 900 (15 minutes); a default value of 60 (1 minute) is used if no value is specified. This value and sasl.login.refresh.buffer.seconds are both ignored if their sum exceeds the remaining lifetime of a credential. Currently applies only to OAUTHBEARER.

|         Type: | short       |
| ------------: | ----------- |
|      Default: | 60          |
| Valid Values: | [0,...,900] |
|   Importance: | low         |

#### [sasl.login.refresh.window.factor](http://kafka.apache.org/documentation/#sasl.login.refresh.window.factor)

  Login refresh thread will sleep until the specified window factor relative to the credential's lifetime has been reached, at which time it will try to refresh the credential. Legal values are between 0.5 (50%) and 1.0 (100%) inclusive; a default value of 0.8 (80%) is used if no value is specified. Currently applies only to OAUTHBEARER.

|         Type: | double        |
| ------------: | ------------- |
|      Default: | 0.8           |
| Valid Values: | [0.5,...,1.0] |
|   Importance: | low           |

#### [sasl.login.refresh.window.jitter](http://kafka.apache.org/documentation/#sasl.login.refresh.window.jitter)

  The maximum amount of random jitter relative to the credential's lifetime that is added to the login refresh thread's sleep time. Legal values are between 0 and 0.25 (25%) inclusive; a default value of 0.05 (5%) is used if no value is specified. Currently applies only to OAUTHBEARER.

|         Type: | double         |
| ------------: | -------------- |
|      Default: | 0.05           |
| Valid Values: | [0.0,...,0.25] |
|   Importance: | low            |

#### [security.providers](http://kafka.apache.org/documentation/#security.providers)

  A list of configurable creator classes each returning a provider implementing security algorithms. These classes should implement the `org.apache.kafka.common.security.auth.SecurityProviderCreator` interface.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.cipher.suites](http://kafka.apache.org/documentation/#ssl.cipher.suites)

  A list of cipher suites. This is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. By default all the available cipher suites are supported.

|         Type: | list |
| ------------: | ---- |
|      Default: | null |
| Valid Values: |      |
|   Importance: | low  |

#### [ssl.endpoint.identification.algorithm](http://kafka.apache.org/documentation/#ssl.endpoint.identification.algorithm)

  The endpoint identification algorithm to validate server hostname using server certificate.

|         Type: | string |
| ------------: | ------ |
|      Default: | https  |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.engine.factory.class](http://kafka.apache.org/documentation/#ssl.engine.factory.class)

  The class of type org.apache.kafka.common.security.auth.SslEngineFactory to provide SSLEngine objects. Default value is org.apache.kafka.common.security.ssl.DefaultSslEngineFactory

|         Type: | class |
| ------------: | ----- |
|      Default: | null  |
| Valid Values: |       |
|   Importance: | low   |

#### [ssl.keymanager.algorithm](http://kafka.apache.org/documentation/#ssl.keymanager.algorithm)

  The algorithm used by key manager factory for SSL connections. Default value is the key manager factory algorithm configured for the Java Virtual Machine.

|         Type: | string  |
| ------------: | ------- |
|      Default: | SunX509 |
| Valid Values: |         |
|   Importance: | low     |

#### [ssl.secure.random.implementation](http://kafka.apache.org/documentation/#ssl.secure.random.implementation)

  The SecureRandom PRNG implementation to use for SSL cryptography operations.

|         Type: | string |
| ------------: | ------ |
|      Default: | null   |
| Valid Values: |        |
|   Importance: | low    |

#### [ssl.trustmanager.algorithm](http://kafka.apache.org/documentation/#ssl.trustmanager.algorithm)

  The algorithm used by trust manager factory for SSL connections. Default value is the trust manager factory algorithm configured for the Java Virtual Machine.

|         Type: | string |
| ------------: | ------ |
|      Default: | PKIX   |
| Valid Values: |        |
|   Importance: | low    |



## [4. DESIGN](http://kafka.apache.org/documentation/#design)

### [4.1 Motivation](http://kafka.apache.org/documentation/#majordesignelements)

目的，动机

We designed Kafka to be able to act as a unified platform for handling all the real-time data feeds [a large company might have](http://kafka.apache.org/documentation/#introduction). To do this we had to think through a fairly broad set of use cases.

我们设计Kafka是为了能够作为一个统一的平台来处理所有的实时数据(一个大公司可能拥有)。要做到这一点，我们必须考虑相当广泛的用例集。

It would have to have high-throughput to support high volume event streams such as real-time log aggregation.

它必须具有高吞吐量才能支持大容量事件流，比如实时日志聚合。

It would need to deal gracefully with large data backlogs to be able to support periodic data loads from offline systems.

It also meant the system would have to handle low-latency delivery to handle more traditional messaging use-cases.

We wanted to support partitioned, distributed, real-time processing of these feeds to create new, derived feeds. This motivated our partitioning and consumer model.

它需要优雅地处理大型数据积压，以便能够支持来自脱机系统的周期性数据加载。

这还意味着系统将不得不处理低延迟交付，以处理更传统的消息传递用例。

我们希望支持对这些提要的分区、分布式、实时处理，以创建新的派生提要。这激发了我们的分区和消费者模型

Finally in cases where the stream is fed into other data systems for serving, we knew the system would have to be able to guarantee fault-tolerance in the presence of machine failures.

最后，在将流输入到其他数据系统以提供服务的情况下，我们知道系统必须能够保证出现机器故障时的容错能力。

Supporting these uses led us to a design with a number of unique elements, more akin to a database log than a traditional messaging system. We will outline some elements of the design in the following sections.

对这些用途的支持使我们设计了具有许多独特元素的设计，更类似于数据库日志，而不是传统的消息传递系统。我们将在下面几节中概述设计的一些元素。

### [4.2 Persistence](http://kafka.apache.org/documentation/#persistence)

持久化

#### [Don't fear the filesystem!](http://kafka.apache.org/documentation/#design_filesystem)

Kafka relies heavily on the filesystem for storing and caching messages. There is a general perception that "disks are slow" which makes people skeptical that a persistent structure can offer competitive performance. In fact disks are both much slower and much faster than people expect depending on how they are used; and a properly designed disk structure can often be as fast as the network.

Kafka非常依赖文件系统来存储和缓存消息。人们普遍认为“磁盘很慢”，这使得人们怀疑持久的结构是否能够提供具有竞争力的性能。实际上，磁盘比人们预期的要慢得多，也快得多，这取决于它们的使用方式;一个适当设计的磁盘结构通常可以和网络一样快。

The key fact about disk performance is that the throughput of hard drives has been diverging from the latency of a disk seek for the last decade. As a result the performance of linear writes on a [JBOD](http://en.wikipedia.org/wiki/Non-RAID_drive_architectures) configuration with six 7200rpm SATA RAID-5 array is about 600MB/sec but the performance of random writes is only about 100k/sec—a difference of over 6000X. These linear reads and writes are the most predictable of all usage patterns, and are heavily optimized by the operating system. A modern operating system provides read-ahead and write-behind techniques that prefetch data in large block multiples and group smaller logical writes into large physical writes. A further discussion of this issue can be found in this [ACM Queue article](http://queue.acm.org/detail.cfm?id=1563874); they actually find that [sequential disk access can in some cases be faster than random memory access!](http://deliveryimages.acm.org/10.1145/1570000/1563874/jacobs3.jpg)

关于磁盘性能的关键事实是，在过去的十年中，硬盘驱动器的吞吐量已经偏离了磁盘查找的延迟。因此，在6个7200rpm的SATA RAID-5阵列上的[JBOD](http://en.wikipedia.org/wiki/nonraid_drive_architectures)配置上线性写操作的性能约为600MB/秒，而随机写操作的性能仅为100k/秒，差异超过6000X。这些线性读和写是所有使用模式中最可预测的，操作系统对它们进行了大量优化。现代操作系统提供了预读和后写技术，这些技术以大量块的倍数预取数据，并将较小的逻辑写入分组为较大的物理写入。关于这个问题的进一步讨论可以在[ACM队列文章]中找到;他们实际上发现[顺序磁盘访问在某些情况下比随机内存访问更快!]

To compensate for this performance divergence, modern operating systems have become increasingly aggressive in their use of main memory for disk caching. A modern OS will happily divert *all* free memory to disk caching with little performance penalty when the memory is reclaimed. All disk reads and writes will go through this unified cache. This feature cannot easily be turned off without using direct I/O, so even if a process maintains an in-process cache of the data, this data will likely be duplicated in OS pagecache, effectively storing everything twice.

为了弥补这种性能差异，现代操作系统越来越积极地使用主存来进行磁盘缓存。现代操作系统会很高兴地将*所有*空闲内存转移到磁盘缓存中，而在回收内存时性能损失很小。所有磁盘读写都将通过这个统一的缓存。如果不使用直接I/O，就无法轻易关闭该特性，因此，即使进程维护了数据的进程内缓存，该数据也可能会在OS pagecache中复制，有效地将所有数据存储两次。

Furthermore, we are building on top of the JVM, and anyone who has spent any time with Java memory usage knows two things:

此外，我们是在JVM之上构建的，任何花过时间研究Java内存使用的人都知道两件事:

1. The memory overhead of objects is very high, often doubling the size of the data stored (or worse).

   对象的内存开销非常高，通常会使存储的数据大小翻倍(或者更糟)。

2. Java garbage collection becomes increasingly fiddly and slow as the in-heap data increases.

   随着堆内数据的增加，Java垃圾收集变得越来越繁琐和缓慢。

As a result of these factors using the filesystem and relying on pagecache is superior to maintaining an in-memory cache or other structure—we at least double the available cache by having automatic access to all free memory, and likely double again by storing a compact byte structure rather than individual objects. Doing so will result in a cache of up to 28-30GB on a 32GB machine without GC penalties. Furthermore, this cache will stay warm even if the service is restarted, whereas the in-process cache will need to be rebuilt in memory (which for a 10GB cache may take 10 minutes) or else it will need to start with a completely cold cache (which likely means terrible initial performance). This also greatly simplifies the code as all logic for maintaining coherency between the cache and filesystem is now in the OS, which tends to do so more efficiently and more correctly than one-off in-process attempts. If your disk usage favors linear reads then read-ahead is effectively pre-populating this cache with useful data on each disk read.

由于这些因素使用文件系统和依赖pagecache优于维护一个内存中的缓存或其他结构，我们至少两倍可用缓存，通过自动访问所有可用内存,可能再翻一番,存储一个字节结构紧凑而不是单个对象。这样做将在没有GC惩罚的32GB机器上产生高达28-30GB的缓存。此外，即使服务重新启动，这个缓存也会保持热度，而进程内缓存则需要在内存中重新构建(对于10GB的缓存，这可能需要10分钟)，否则它将需要从一个完全冷的缓存开始(这可能意味着糟糕的初始性能)。这也极大地简化了代码，因为所有维护缓存和文件系统之间一致性的逻辑现在都在操作系统中，这样做往往比一次性的进程内尝试更有效、更正确。如果您的磁盘使用倾向于线性读取，那么预读可以有效地在每次磁盘读取时将有用的数据填充到缓存中。

This suggests a design which is very simple: rather than maintain as much as possible in-memory and flush it all out to the filesystem in a panic when we run out of space, we invert that. All data is immediately written to a persistent log on the filesystem without necessarily flushing to disk. In effect this just means that it is transferred into the kernel's pagecache.

这暗示了一种非常简单的设计:与其在内存中维护尽可能多的内存，并在耗尽空间时惊慌地将其全部清除到文件系统中，不如将其倒置。所有数据都立即写入文件系统上的持久日志，而不必刷新到磁盘。实际上，这仅仅意味着它被传输到内核的pagecache中。

This style of pagecache-centric design is described in an [article](http://varnish-cache.org/wiki/ArchitectNotes) on the design of Varnish here (along with a healthy dose of arrogance).

关于Varnish的设计的一篇文章描述了这种以页面为中心的设计风格

#### [Constant Time Suffices](http://kafka.apache.org/documentation/#design_constanttime)

The persistent data structure used in messaging systems are often a per-consumer queue with an associated BTree or other general-purpose random access data structures to maintain metadata about messages. BTrees are the most versatile data structure available, and make it possible to support a wide variety of transactional and non-transactional semantics in the messaging system. They do come with a fairly high cost, though: Btree operations are O(log N). Normally O(log N) is considered essentially equivalent to constant time, but this is not true for disk operations. Disk seeks come at 10 ms a pop, and each disk can do only one seek at a time so parallelism is limited. Hence even a handful of disk seeks leads to very high overhead. Since storage systems mix very fast cached operations with very slow physical disk operations, the observed performance of tree structures is often superlinear as data increases with fixed cache--i.e. doubling your data makes things much worse than twice as slow.

消息传递系统中使用的持久数据结构通常是每个使用者的队列，带有关联的BTree或其他通用的随机访问数据结构，以维护关于消息的元数据。btree是可用的最通用的数据结构，它使得在消息传递系统中支持各种各样的事务和非事务语义成为可能。不过，它们确实有相当高的代价:Btree操作是O(log N)，通常O(log N)被认为本质上等同于常数时间，但这对磁盘操作不成立。磁盘寻道速度为每次10毫秒，而且每个磁盘一次只能执行一次寻道，因此并行性受到限制。因此，即使是少量的磁盘查找也会导致非常高的开销。由于存储系统混合了非常快的缓存操作和非常慢的物理磁盘操作，所以当数据以固定缓存增长时，观察到的树结构的性能通常是超线性的。将你的数据翻倍会比两倍的速度慢得多。

Intuitively a persistent queue could be built on simple reads and appends to files as is commonly the case with logging solutions. This structure has the advantage that all operations are O(1) and reads do not block writes or each other. This has obvious performance advantages since the performance is completely decoupled from the data size—one server can now take full advantage of a number of cheap, low-rotational speed 1+TB SATA drives. Though they have poor seek performance, these drives have acceptable performance for large reads and writes and come at 1/3 the price and 3x the capacity.

直观地说，持久化队列可以构建在简单的读取和附加到文件上，这是日志解决方案的常见情况。这种结构的优点是，所有操作都是O(1)，读操作不会阻塞写操作，也不会相互阻塞写操作。这具有明显的性能优势，因为性能完全与数据大小分离——一台服务器现在可以充分利用大量廉价、低转速1+TB的SATA驱动器。虽然它们的寻道性能很差，但是这些驱动器对于大容量的读和写具有可接受的性能，并且价格是它的1/3，容量是它的3倍。

Having access to virtually unlimited disk space without any performance penalty means that we can provide some features not usually found in a messaging system. For example, in Kafka, instead of attempting to delete messages as soon as they are consumed, we can retain messages for a relatively long period (say a week). This leads to a great deal of flexibility for consumers, as we will describe.

可以访问几乎不受限制的磁盘空间而不会损害性能，这意味着我们可以提供消息传递系统中不常见的一些特性。例如，在Kafka中，我们可以保留消息一段相对较长的时间(比如一周)，而不是试图在消息被消费后立即删除消息。这为消费者带来了极大的灵活性，我们将对此进行描述。

### [4.3 Efficiency](http://kafka.apache.org/documentation/#maximizingefficiency)

We have put significant effort into efficiency. One of our primary use cases is handling web activity data, which is very high volume: each page view may generate dozens of writes. Furthermore, we assume each message published is read by at least one consumer (often many), hence we strive to make consumption as cheap as possible.

我们在提高效率方面付出了巨大努力。我们的主要用例之一是处理web活动数据，它的容量非常大:每个页面视图可能生成几十次写操作。此外，我们假设发布的每个消息至少有一个使用者(通常是很多)读取，因此我们尽量降低使用成本。

We have also found, from experience building and running a number of similar systems, that efficiency is a key to effective multi-tenant operations. If the downstream infrastructure service can easily become a bottleneck due to a small bump in usage by the application, such small changes will often create problems. By being very fast we help ensure that the application will tip-over under load before the infrastructure. This is particularly important when trying to run a centralized service that supports dozens or hundreds of applications on a centralized cluster as changes in usage patterns are a near-daily occurrence.

从构建和运行大量类似系统的经验中，我们还发现，效率是有效的多租户操作的关键。如果下游基础设施服务由于应用程序使用的一个小波动而很容易成为瓶颈，那么这样的小变化通常会产生问题。通过非常快的速度，我们有助于确保应用程序在加载基础设施之前就会在负载下崩溃。当试图在一个集中式集群上运行支持数十或数百个应用程序的集中式服务时，这一点尤其重要，因为几乎每天都要更改使用模式。

We discussed disk efficiency in the previous section. Once poor disk access patterns have been eliminated, there are two common causes of inefficiency in this type of system: too many small I/O operations, and excessive byte copying.

我们在前一节中讨论了磁盘效率。一旦消除了不良的磁盘访问模式，在这种类型的系统中存在两个导致效率低下的常见原因:太多的小I/O操作和过多的字节复制。

The small I/O problem happens both between the client and the server and in the server's own persistent operations.

小的I/O问题既发生在客户机和服务器之间，也发生在服务器自己的持久操作中。

To avoid this, our protocol is built around a "message set" abstraction that naturally groups messages together. This allows network requests to group messages together and amortize the overhead of the network roundtrip rather than sending a single message at a time. The server in turn appends chunks of messages to its log in one go, and the consumer fetches large linear chunks at a time.

为了避免这种情况，我们的协议是围绕“消息集message set”抽象构建的，该抽象自然地将消息分组在一起。这允许网络请求将消息分组在一起，分摊网络往返的开销，而不是一次发送一条消息。服务器依次将消息块追加到它的日志中，而使用者一次获取大的线性消息块。

This simple optimization produces orders of magnitude speed up. Batching leads to larger network packets, larger sequential disk operations, contiguous memory blocks, and so on, all of which allows Kafka to turn a bursty stream of random message writes into linear writes that flow to the consumers.

这个简单的优化产生了数量级的速度。批处理会导致更大的网络包、更大的顺序磁盘操作、连续的内存块等等，所有这些都允许Kafka将突发的随机消息写入流转换为线性写入流，然后发送给消费者。

The other inefficiency is in byte copying. At low message rates this is not an issue, but under load the impact is significant. To avoid this we employ a standardized binary message format that is shared by the producer, the broker, and the consumer (so data chunks can be transferred without modification between them).

另一个低效率是字节复制。在低消息率下，这不是一个问题，但在负载下，影响是显著的。为了避免这种情况，我们采用了由生产者、代理(broker)和使用者共享的标准化二进制消息格式(这样数据块就可以在它们之间传输而无需修改)。

The message log maintained by the broker is itself just a directory of files, each populated by a sequence of message sets that have been written to disk in the same format used by the producer and consumer. Maintaining this common format allows optimization of the most important operation: network transfer of persistent log chunks. Modern unix operating systems offer a highly optimized code path for transferring data out of pagecache to a socket; in Linux this is done with the [sendfile system call](http://man7.org/linux/man-pages/man2/sendfile.2.html).

由代理broker维护的消息日志本身就是一个文件目录，每个文件都由以生产者和消费者使用的相同格式写入磁盘的消息集序列填充。维护这种通用格式允许优化最重要的操作:持久日志块的网络传输。现代unix操作系统提供了一个高度优化的代码路径，用于将数据从pagecache传输到套接字;在Linux中，这是通过[sendfile系统调用](http://man7.org/linux/man-pages/man2/sendfile.2.html)完成的。

To understand the impact of sendfile, it is important to understand the common data path for transfer of data from file to socket:

要理解sendfile的影响，重要的是要理解从文件到套接字传输数据的通用数据路径:

1. The operating system reads data from the disk into pagecache in kernel space

   操作系统将数据从磁盘读取到内核空间中的pagecache

2. The application reads the data from kernel space into a user-space buffer

   应用程序将数据从内核空间读入用户空间缓冲区

3. The application writes the data back into kernel space into a socket buffer

   应用程序将数据写回内核空间到套接字缓冲区中

4. The operating system copies the data from the socket buffer to the NIC buffer where it is sent over the network

   操作系统将数据从套接字缓冲区复制到NIC缓冲区，在那里通过网络发送数据

This is clearly inefficient, there are four copies and two system calls. Using sendfile, this re-copying is avoided by allowing the OS to send the data from pagecache to the network directly. So in this optimized path, only the final copy to the NIC buffer is needed.

这显然是低效的，有四个副本和两个系统调用。通过使用sendfile，允许操作系统将数据从pagecache直接发送到网络，从而避免了这种重复复制。所以在这个优化的路径中，只需要最终的副本到NIC缓冲区。

We expect a common use case to be multiple consumers on a topic. Using the zero-copy optimization above, data is copied into pagecache exactly once and reused on each consumption instead of being stored in memory and copied out to user-space every time it is read. This allows messages to be consumed at a rate that approaches the limit of the network connection.

我们期望一个通用用例是一个主题的多个使用者。使用上面的零拷贝优化，数据被精确地复制到pagecache中一次，并在每次使用时重用，而不是在每次读取时存储在内存中并复制到用户空间。这允许以接近网络连接限制的速度使用消息。

This combination of pagecache and sendfile means that on a Kafka cluster where the consumers are mostly caught up you will see no read activity on the disks whatsoever as they will be serving data entirely from cache.

pagecache和sendfile的组合意味着在Kafka集群中，消费者大部分都被捕获了，你将看不到磁盘上的读取活动，因为他们将完全从缓存中提供数据。

For more background on the sendfile and zero-copy support in Java, see this [article](https://developer.ibm.com/articles/j-zerocopy/).

有关Java中sendfile和零复制支持的更多背景知识，请参阅本文。

#### [End-to-end Batch Compression](http://kafka.apache.org/documentation/#design_compression)

In some cases the bottleneck is actually not CPU or disk but network bandwidth. This is particularly true for a data pipeline that needs to send messages between data centers over a wide-area network. Of course, the user can always compress its messages one at a time without any support needed from Kafka, but this can lead to very poor compression ratios as much of the redundancy is due to repetition between messages of the same type (e.g. field names in JSON or user agents in web logs or common string values). Efficient compression requires compressing multiple messages together rather than compressing each message individually.

在某些情况下，瓶颈实际上不是CPU或磁盘，而是网络带宽。对于需要通过广域网在数据中心之间发送消息的数据管道来说，尤其如此。当然,用户可以压缩它的消息一次没有任何需要从卡夫卡的支持,但这可能导致非常贫穷的压缩比之间的冗余是由于重复相同类型的消息(例如字段名在JSON或用户代理在web日志或常见的字符串值)。有效的压缩需要将多个消息压缩在一起，而不是分别压缩每个消息。

Kafka supports this with an efficient batching format. A batch of messages can be clumped together compressed and sent to the server in this form. This batch of messages will be written in compressed form and will remain compressed in the log and will only be decompressed by the consumer.

Kafka以一种高效的批处理格式支持这一点。可以以这种形式将一批消息聚在一起、压缩并发送到服务器。这批消息将以压缩形式写入，并在日志中保持压缩状态，仅由使用者解压。

Kafka supports GZIP, Snappy, LZ4 and ZStandard compression protocols. More details on compression can be found [here](https://cwiki.apache.org/confluence/display/KAFKA/Compression).

Kafka支持GZIP, Snappy, LZ4和ZStandard压缩协议。更多关于压缩的细节可以在[这里]找到

### [4.4 The Producer](http://kafka.apache.org/documentation/#theproducer)

#### [Load balancing](http://kafka.apache.org/documentation/#design_loadbalancing)

负载均衡

The producer sends data directly to the broker that is the leader for the partition without any intervening routing tier. To help the producer do this all Kafka nodes can answer a request for metadata about which servers are alive and where the leaders for the partitions of a topic are at any given time to allow the producer to appropriately direct its requests.

生产者直接将数据发送到作为分区领导者的（broker）代理，而没有任何中间路由层。为了帮助生成器完成这一任务，所有Kafka节点都可以回答关于哪些服务器是活的以及某个主题分区的领导者在任何给定时间的元数据请求，从而允许生成器适当地指导其请求。

The client controls which partition it publishes messages to. This can be done at random, implementing a kind of random load balancing, or it can be done by some semantic partitioning function. We expose the interface for semantic partitioning by allowing the user to specify a key to partition by and using this to hash to a partition (there is also an option to override the partition function if need be). For example if the key chosen was a user id then all data for a given user would be sent to the same partition. This in turn will allow consumers to make locality assumptions about their consumption. This style of partitioning is explicitly designed to allow locality-sensitive processing in consumers.

客户端控制它将消息发布到哪个分区。这可以随机完成，实现一种随机的负载平衡，也可以通过一些语义分区函数来完成。我们通过允许用户为分区指定一个键，并使用这个键散列到一个分区来公开语义分区的接口(如果需要，还可以选择覆盖分区函数)。例如，如果选择的键是一个用户id，那么给定用户的所有数据将被发送到相同的分区。这反过来又允许消费者对他们的消费做出本地假设。这种分区样式被显式设计为允许在使用者中进行对位置敏感的处理。

#### [Asynchronous send](http://kafka.apache.org/documentation/#design_asyncsend)

异步发送

Batching is one of the big drivers of efficiency, and to enable batching the Kafka producer will attempt to accumulate data in memory and to send out larger batches in a single request. The batching can be configured to accumulate no more than a fixed number of messages and to wait no longer than some fixed latency bound (say 64k or 10 ms). This allows the accumulation of more bytes to send, and few larger I/O operations on the servers. This buffering is configurable and gives a mechanism to trade off a small amount of additional latency for better throughput.

批处理是效率的一大驱动因素，为了使批处理成为可能，Kafka生产者将尝试在内存中积累数据，并在单个请求中发送更大的批处理。批处理可以配置为积累不超过固定数量的消息，等待时间不超过某个固定的延迟限制(比如64k或10 ms)。这样就可以积累更多的字节来发送，并在服务器上进行更大的I/O操作。这种缓冲是可配置的，并提供了一种机制，可以用少量的额外延迟来换取更好的吞吐量。

Details on [configuration](http://kafka.apache.org/documentation/#producerconfigs) and the [api](http://kafka.apache.org/082/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) for the producer can be found elsewhere in the documentation.

关于生产者的[配置](http://kafka.apache.org/documentation/#producerconfigs)和[api](http://kafka.apache.org/082/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html)的详细信息可以在文档的其他地方找到。

### [4.5 The Consumer](http://kafka.apache.org/documentation/#theconsumer)

The Kafka consumer works by issuing "fetch" requests to the brokers leading the partitions it wants to consume. The consumer specifies its offset in the log with each request and receives back a chunk of log beginning from that position. The consumer thus has significant control over this position and can rewind it to re-consume data if need be.

Kafka使用者通过向引导它想要使用的分区的代理发出“fetch 拉取”请求来工作。使用者在日志中为每个请求指定它的偏移量，并从该位置开始接收一块日志。因此，用户对这个位置有很大的控制权，如果需要，可以将其倒回以重新使用数据。

#### [Push vs. pull](http://kafka.apache.org/documentation/#design_pull)

推vs拉

An initial question we considered is whether consumers should pull data from brokers or brokers should push data to the consumer. In this respect Kafka follows a more traditional design, shared by most messaging systems, where data is pushed to the broker from the producer and pulled from the broker by the consumer. Some logging-centric systems, such as [Scribe](http://github.com/facebook/scribe) and [Apache Flume](http://flume.apache.org/), follow a very different push-based path where data is pushed downstream. There are pros and cons to both approaches. However, a push-based system has difficulty dealing with diverse consumers as the broker controls the rate at which data is transferred. The goal is generally for the consumer to be able to consume at the maximum possible rate; unfortunately, in a push system this means the consumer tends to be overwhelmed when its rate of consumption falls below the rate of production (a denial of service attack, in essence). A pull-based system has the nicer property that the consumer simply falls behind and catches up when it can. This can be mitigated with some kind of backoff protocol by which the consumer can indicate it is overwhelmed, but getting the rate of transfer to fully utilize (but never over-utilize) the consumer is trickier than it seems. Previous attempts at building systems in this fashion led us to go with a more traditional pull model.

我们首先考虑的一个问题是，消费者是应该从代理(broker)拉取数据，还是代理(broker)应该将数据推给消费者。在这方面，Kafka遵循了大多数消息传递系统所共享的更传统的设计，其中数据从生产者推到代理broker，由消费者从代理提取。一些以日志为中心的系统，如[Scribe](http://github.com/facebook/scribe)和[Apache Flume](http://flume.apache.org/)，遵循完全不同的基于推的路径，其中数据被推往下游。这两种方法各有利弊。但是，基于推的系统在处理不同的使用者时存在困难，因为代理控制数据传输的速率。其目标通常是让使用者能够以最大的可能速度消耗;不幸的是，在推式系统中，这意味着当消费者的消费率低于生产率时(本质上是拒绝服务攻击)，消费者往往会不知所措。以拉力为基础的系统有一个更好的特性，那就是消费者只是落在后面，然后在可能的时候赶上来。这可以通过某种退出协议来缓解，通过这种协议，使用者可以表明它已经超负荷了，但是要让传输速率充分利用(而不是过度利用)使用者要比看起来更加棘手。以前以这种方式构建系统的尝试导致我们采用更传统的拉式模型。

Another advantage of a pull-based system is that it lends itself to aggressive batching of data sent to the consumer. A push-based system must choose to either send a request immediately or accumulate more data and then send it later without knowledge of whether the downstream consumer will be able to immediately process it. If tuned for low latency, this will result in sending a single message at a time only for the transfer to end up being buffered anyway, which is wasteful. A pull-based design fixes this as the consumer always pulls all available messages after its current position in the log (or up to some configurable max size). So one gets optimal batching without introducing unnecessary latency.

基于拉取的系统的另一个优点是，它便于对发送给消费者的数据进行积极的批处理。基于推的系统必须选择要么立即发送请求，要么积累更多数据，然后在不知道下游客户是否能够立即处理它的情况下再发送。如果调优为低延迟，这将导致一次只发送一条消息，传输最终将被缓冲，这是一种浪费。基于拉的设计解决了这个问题，因为使用者总是在消息在日志中的当前位置之后拉出所有可用消息(或者直到某个可配置的最大大小)。这样就可以在不引入不必要延迟的情况下获得最佳批处理。

The deficiency of a naive pull-based system is that if the broker has no data the consumer may end up polling in a tight loop, effectively busy-waiting for data to arrive. To avoid this we have parameters in our pull request that allow the consumer request to block in a "long poll" waiting until data arrives (and optionally waiting until a given number of bytes is available to ensure large transfer sizes).

基于拉取的系统的不足之处在于，如果代理没有数据，使用者可能最终会在一个紧密的循环中轮询，有效地忙碌地等待数据的到达。为了避免这种情况，我们在pull请求中使用了一些参数，这些参数允许用户请求以“长轮询”的方式阻塞，直到数据到达(也可以选择等待，直到给定的字节数可用，以确保较大的传输规模)。

You could imagine other possible designs which would be only pull, end-to-end. The producer would locally write to a local log, and brokers would pull from that with consumers pulling from them. A similar type of "store-and-forward" producer is often proposed. This is intriguing but we felt not very suitable for our target use cases which have thousands of producers. Our experience running persistent data systems at scale led us to feel that involving thousands of disks in the system across many applications would not actually make things more reliable and would be a nightmare to operate. And in practice we have found that we can run a pipeline with strong SLAs at large scale without a need for producer persistence.

你可以想象其他可能的设计，只需要端到端拉。生产者将从本地写入到本地日志，代理将从该日志中提取，而消费者从它们中提取。经常会提出类似的“存储并转发”生成器。这很有趣，但我们觉得不太适合我们有数千生产者的目标用例。大规模运行持久数据系统的经验让我们感到，跨许多应用程序在系统中涉及数千个磁盘实际上不会使事情更可靠，而且操作起来会是一场噩梦。在实践中，我们发现我们可以在不需要生产者持久性的情况下大规模运行具有强slas的管道。

#### [Consumer Position](http://kafka.apache.org/documentation/#design_consumerposition)

Keeping track of *what* has been consumed is, surprisingly, one of the key performance points of a messaging system.

令人惊讶的是，跟踪*使用了什么*是消息传递系统的关键性能点之一。

Most messaging systems keep metadata about what messages have been consumed on the broker. That is, as a message is handed out to a consumer, the broker either records that fact locally immediately or it may wait for acknowledgement from the consumer. This is a fairly intuitive choice, and indeed for a single machine server it is not clear where else this state could go. Since the data structures used for storage in many messaging systems scale poorly, this is also a pragmatic choice--since the broker knows what is consumed it can immediately delete it, keeping the data size small.

大多数消息传递系统保存关于在代理(broker)上使用了哪些消息的元数据。也就是说，当消息传递给使用者时，代理要么立即在本地记录该事实，要么等待使用者的确认。这是一个相当直观的选择，实际上对于单个机器服务器，不清楚该状态还会出现在哪里。由于在许多消息传递系统中用于存储的数据结构伸缩性很差，这也是一个实用的选择——因为代理知道消耗了什么，所以它可以立即删除它，保持数据大小较小。

What is perhaps not obvious is that getting the broker and consumer to come into agreement about what has been consumed is not a trivial problem. If the broker records a message as **consumed** immediately every time it is handed out over the network, then if the consumer fails to process the message (say because it crashes or the request times out or whatever) that message will be lost. To solve this problem, many messaging systems add an acknowledgement feature which means that messages are only marked as **sent** not **consumed** when they are sent; the broker waits for a specific acknowledgement from the consumer to record the message as **consumed**. This strategy fixes the problem of losing messages, but creates new problems. First of all, if the consumer processes the message but fails before it can send an acknowledgement then the message will be consumed twice. The second problem is around performance, now the broker must keep multiple states about every single message (first to lock it so it is not given out a second time, and then to mark it as permanently consumed so that it can be removed). Tricky problems must be dealt with, like what to do with messages that are sent but never acknowledged.

可能不太明显的是，让代理和消费者就所消费的内容达成协议并不是一个微不足道的问题。如果代理在每次通过网络分发消息时立即将其记录为**消费**，那么如果消费者未能处理该消息(比如由于它崩溃或请求超时或其他原因)，则该消息将丢失。为了解决这个问题，许多消息传递系统增加了一个确认特性，即消息发送时只被标记为**发送**不被标记为**消耗**;代理等待来自使用者的特定确认，以将消息记录为**消费**。这种策略解决了丢失消息的问题，但也产生了新的问题。首先，如果使用者处理消息但在发送确认消息之前失败，那么消息将被使用两次。第二个问题与性能有关，现在代理必须对每个消息保持多个状态(首先锁定它，这样它就不会再次发出，然后将其标记为永久使用，这样就可以删除它)。必须处理一些棘手的问题，比如如何处理已发送但从未得到确认的消息。

Kafka handles this differently. Our topic is divided into a set of totally ordered partitions, each of which is consumed by exactly one consumer within each subscribing consumer group at any given time. This means that the position of a consumer in each partition is just a single integer, the offset of the next message to consume. This makes the state about what has been consumed very small, just one number for each partition. This state can be periodically checkpointed. This makes the equivalent of message acknowledgements very cheap.

kafka对此有不同的处理方式。我们的主题被划分为一组完全有序的分区，每个订阅使用者组中只有一个使用者使用它们。这意味着使用者在每个分区中的位置只是一个整数，即要使用的下一条消息的偏移量。这使得关于消耗了什么的状态非常小，每个分区只有一个数字。可以定期对该状态进行检查点。这使得等价的消息确认非常便宜。

There is a side benefit of this decision. A consumer can deliberately *rewind* back to an old offset and re-consume data. This violates the common contract of a queue, but turns out to be an essential feature for many consumers. For example, if the consumer code has a bug and is discovered after some messages are consumed, the consumer can re-consume those messages once the bug is fixed.

这个决定还有一个附带的好处。使用者可以故意“倒回”到旧的偏移量并重新使用数据。这违反了队列的公共契约，但却是许多使用者的基本特性。例如，如果使用者代码有错误，并且是在使用了一些消息之后发现的，那么一旦错误得到修复，使用者可以重新使用这些消息。

#### [Offline Data Load](http://kafka.apache.org/documentation/#design_offlineload)

离线数据加载

Scalable persistence allows for the possibility of consumers that only periodically consume such as batch data loads that periodically bulk-load data into an offline system such as Hadoop or a relational data warehouse.

可伸缩持久性允许用户周期性地使用(比如批处理数据加载)，周期性地将数据批量加载到离线系统(比如Hadoop或关系数据仓库)。

In the case of Hadoop we parallelize the data load by splitting the load over individual map tasks, one for each node/topic/partition combination, allowing full parallelism in the loading. Hadoop provides the task management, and tasks which fail can restart without danger of duplicate data—they simply restart from their original position.

在Hadoop的情况下，我们通过将负载拆分到单个map任务(每个节点/主题/分区组合对应一个任务)来并行化数据加载，从而允许在加载时完全并行。Hadoop提供了任务管理，失败的任务可以重新启动，而不会有重复数据的危险——它们只是从原始位置重新启动。

#### [Static Membership](http://kafka.apache.org/documentation/#static_membership)

静态成员资格

Static membership aims to improve the availability of stream applications, consumer groups and other applications built on top of the group rebalance protocol. The rebalance protocol relies on the group coordinator to allocate entity ids to group members. These generated ids are ephemeral and will change when members restart and rejoin. For consumer based apps, this "dynamic membership" can cause a large percentage of tasks re-assigned to different instances during administrative operations such as code deploys, configuration updates and periodic restarts. For large state applications, shuffled tasks need a long time to recover their local states before processing and cause applications to be partially or entirely unavailable. Motivated by this observation, Kafka’s group management protocol allows group members to provide persistent entity ids. Group membership remains unchanged based on those ids, thus no rebalance will be triggered.

静态成员关系旨在提高流应用程序、消费者组和其他构建在组再平衡协议之上的应用程序的可用性。再平衡协议依赖于组协调器为组成员分配实体id。这些生成的id是临时的，在成员重新启动和重新加入时将会更改。对于基于消费者的应用程序，这种“动态成员关系”可能导致在管理操作(如代码部署、配置更新和定期重启)期间，大量任务被重新分配给不同的实例。对于大型状态应用程序，在处理和导致应用程序部分或全部不可用之前，混合的任务需要很长时间来恢复其本地状态。受此启发，Kafka s组管理协议允许组成员提供持久的实体id。基于这些id，组成员将保持不变，因此不会触发再平衡。

If you want to use static membership,

如果你想使用静态成员资格，

- Upgrade both broker cluster and client apps to 2.3 or beyond, and also make sure the upgraded brokers are using `inter.broker.protocol.version` of 2.3 or beyond as well.

  将代理集群和客户端应用程序升级到2.3或更高版本，还要确保升级后的代理使用的是“inter.broker.protocol”。或2.3或更高版本。

- Set the config `ConsumerConfig#GROUP_INSTANCE_ID_CONFIG` to a unique value for each consumer instance under one group.

  将配置' ConsumerConfig#GROUP_INSTANCE_ID_CONFIG '设置为一个组下每个消费者实例的唯一值。

- For Kafka Streams applications, it is sufficient to set a unique `ConsumerConfig#GROUP_INSTANCE_ID_CONFIG` per KafkaStreams instance, independent of the number of used threads for an instance.

  对于Kafka流应用程序，为每个KafkaStreams实例设置一个唯一的‘ConsumerConfig#GROUP_INSTANCE_ID_CONFIG’就足够了，它独立于一个实例使用的线程数量。

If your broker is on an older version than 2.3, but you choose to set `ConsumerConfig#GROUP_INSTANCE_ID_CONFIG` on the client side, the application will detect the broker version and then throws an UnsupportedException. If you accidentally configure duplicate ids for different instances, a fencing mechanism on broker side will inform your duplicate client to shutdown immediately by triggering a `org.apache.kafka.common.errors.FencedInstanceIdException`. For more details, see [KIP-345](https://cwiki.apache.org/confluence/display/KAFKA/KIP-345%3A+Introduce+static+membership+protocol+to+reduce+consumer+rebalances)

如果代理的版本比2.3更老，但是您选择在客户端设置‘ConsumerConfig#GROUP_INSTANCE_ID_CONFIG’，应用程序将检测代理版本，然后抛出UnsupportedException异常。如果您不小心为不同的实例配置了重复的id，代理端上的保护机制将通过触发“org. apache.kafaca .common.error . fencedinstanceidexception”来通知重复的客户端立即关闭。更多详细信息，请参见[KIP-345]

### [4.6 Message Delivery Semantics](http://kafka.apache.org/documentation/#semantics)

消息传递语义

消息分发语义

Now that we understand a little about how producers and consumers work, let's discuss the semantic guarantees Kafka provides between producer and consumer. Clearly there are multiple possible message delivery guarantees that could be provided:

现在我们对生产者和消费者的工作方式有了一些了解，让我们讨论Kafka在生产者和消费者之间提供的语义保证。显然，可以提供多种可能的消息传递保证:

- *At most once*—Messages may be lost but are never redelivered.

  最多一次* -消息可能会丢失，但永远不会重新发送

- *At least once*—Messages are never lost but may be redelivered.

  至少有一次* -消息不会丢失，但可能会被重新发送。

- *Exactly once*—this is what people actually want, each message is delivered once and only once.

  确切的一次*——这是人们真正想要的，每条消息只传递一次。

It's worth noting that this breaks down into two problems: the durability guarantees for publishing a message and the guarantees when consuming a message.

值得注意的是，这可以分解为两个问题:持久性保证了消息的发布 和 使用消息时的保证。。

Many systems claim to provide "exactly once" delivery semantics, but it is important to read the fine print, most of these claims are misleading (i.e. they don't translate to the case where consumers or producers can fail, cases where there are multiple consumer processes, or cases where data written to disk can be lost).

许多系统声称提供“精确一次”的交付语义，,但重要的是要阅读细则，大多数这些说法都是误导(它们不会转化为消费者或生产者可能失败的情况，有多个使用者进程的情况，或写入磁盘的数据可能丢失的情况)。

Kafka's semantics are straight-forward. When publishing a message we have a notion of the message being "committed" to the log. Once a published message is committed it will not be lost as long as one broker that replicates the partition to which this message was written remains "alive". The definition of committed message, alive partition as well as a description of which types of failures we attempt to handle will be described in more detail in the next section. For now let's assume a perfect, lossless broker and try to understand the guarantees to the producer and consumer. If a producer attempts to publish a message and experiences a network error it cannot be sure if this error happened before or after the message was committed. This is similar to the semantics of inserting into a database table with an autogenerated key.

kafka的语义学是直截了当的。当发布一条消息时，我们有一个消息被“提交”到日志的概念。一旦发布的消息被提交，只要复制该消息写入到的分区的代理保持“活动”，该消息就不会丢失。下一节将更详细地描述提交消息、活动分区的定义以及我们试图处理的失败类型的描述。现在，让我们假设一个完美的、无损失的broker，并尝试理解对生产者和消费者的保证。如果生产者试图发布消息并遇到网络错误，则不能确定该错误是在消息提交之前还是之后发生的。这类似于使用自动生成的键插入数据库表的语义。

Prior to 0.11.0.0, if a producer failed to receive a response indicating that a message was committed, it had little choice but to resend the message. This provides at-least-once delivery semantics since the message may be written to the log again during resending if the original request had in fact succeeded. Since 0.11.0.0, the Kafka producer also supports an idempotent delivery option which guarantees that resending will not result in duplicate entries in the log. To achieve this, the broker assigns each producer an ID and deduplicates messages using a sequence number that is sent by the producer along with every message. Also beginning with 0.11.0.0, the producer supports the ability to send messages to multiple topic partitions using transaction-like semantics: i.e. either all messages are successfully written or none of them are. The main use case for this is exactly-once processing between Kafka topics (described below).

在0.11.0.0之前，如果生产者未能收到指示消息已提交的响应，那么它除了重新发送消息外别无选择。这提供了至少一次的传递语义，因为如果原始请求实际上成功了，在重新发送期间可能会再次将消息写入日志。从0.11.0.0开始，Kafka producer还支持幂等发送选项，该选项保证重发不会导致日志中的重复条目。为此，代理为每个生产者分配一个ID，并使用生产者随每个消息一起发送的序列号对消息进行重复数据删除。同样从0.11.0.0开始，生产者支持使用类似事务的语义将消息发送到多个主题分区的能力:即，要么所有消息都成功编写，要么没有消息成功编写。这种方法的主要用例是在Kafka主题之间进行一次处理(如下所述)。

Not all use cases require such strong guarantees. For uses which are latency sensitive we allow the producer to specify the durability level it desires. If the producer specifies that it wants to wait on the message being committed this can take on the order of 10 ms. However the producer can also specify that it wants to perform the send completely asynchronously or that it wants to wait only until the leader (but not necessarily the followers) have the message.

并不是所有用例都需要这样强有力的保证。对于对延迟敏感的使用，我们允许生产者指定它想要的持久性级别。如果生产者指定它希望等待提交的消息，则可能需要10毫秒的时间。然而，生产者也可以指定它希望完全异步地执行发送，或者它希望只等待领导者(但不一定是追随者)获得消息。

Now let's describe the semantics from the point-of-view of the consumer. All replicas have the exact same log with the same offsets. The consumer controls its position in this log. If the consumer never crashed it could just store this position in memory, but if the consumer fails and we want this topic partition to be taken over by another process the new process will need to choose an appropriate position from which to start processing. Let's say the consumer reads some messages -- it has several options for processing the messages and updating its position.

现在让我们从使用者的角度来描述语义。所有副本具有完全相同的日志和相同的偏移量。使用者控制其在日志中的位置。如果使用者从未崩溃过，它可以将这个位置存储在内存中，但是如果使用者崩溃了，而我们希望这个主题分区被另一个进程接管，那么新进程将需要选择一个适当的位置来开始处理。假设使用者读取了一些消息——它有几种处理消息和更新其位置的选项。

1. It can read the messages, then save its position in the log, and finally process the messages. In this case there is a possibility that the consumer process crashes after saving its position but before saving the output of its message processing. In this case the process that took over processing would start at the saved position even though a few messages prior to that position had not been processed. This corresponds to "at-most-once" semantics as in the case of a consumer failure messages may not be processed.

   它可以读取消息，然后将其位置保存在日志中，最后处理消息。在这种情况下，使用者进程有可能在保存其位置之后，但在保存其消息处理的输出之前崩溃。在这种情况下，接管处理的进程将从保存的位置开始，即使在该位置之前的一些消息没有被处理。这对应于“至多一次”语义，因为在使用者失败的情况下，消息可能不会被处理。

2. It can read the messages, process the messages, and finally save its position. In this case there is a possibility that the consumer process crashes after processing messages but before saving its position. In this case when the new process takes over the first few messages it receives will already have been processed. This corresponds to the "at-least-once" semantics in the case of consumer failure. In many cases messages have a primary key and so the updates are idempotent (receiving the same message twice just overwrites a record with another copy of itself).

   它可以读取消息，处理消息，最后保存它的位置。在这种情况下，使用者进程有可能在处理消息之后但在保存其位置之前崩溃。在这种情况下，当新进程接管它接收的头几个消息时，它将已经被处理。这对应于用户失败时的“最少一次”语义。在许多情况下，消息有一个主键，因此更新是幂等的(接收两次相同的消息只是用它自己的另一个副本覆盖一条记录)。

So what about exactly once semantics (i.e. the thing you actually want)? When consuming from a Kafka topic and producing to another topic (as in a [Kafka Streams](https://kafka.apache.org/documentation/streams) application), we can leverage the new transactional producer capabilities in 0.11.0.0 that were mentioned above. The consumer's position is stored as a message in a topic, so we can write the offset to Kafka in the same transaction as the output topics receiving the processed data. If the transaction is aborted, the consumer's position will revert to its old value and the produced data on the output topics will not be visible to other consumers, depending on their "isolation level." In the default "read_uncommitted" isolation level, all messages are visible to consumers even if they were part of an aborted transaction, but in "read_committed," the consumer will only return messages from transactions which were committed (and any messages which were not part of a transaction).

那么仅仅一次语义呢(即你真正想要的东西)怎么样?当从Kafka主题消费并生成到另一个主题(在[Kafka Streams](https://kafka.apache.org/documentation/streams)应用程序中)时，我们可以利用上面提到的0.11.0.0中的新的事务生成功能。消费者的位置作为消息存储在主题中，因此我们可以在接收处理数据的输出主题的同一个事务中将偏移量写入Kafka。如果事务中止，使用者的位置将恢复到原来的值，并且根据其他使用者的“隔离级别”，在输出主题上生成的数据对其他使用者将不可见。在默认的“read_uncommitted”隔离级别中，所有消息对于使用者都是可见的，即使它们是中止的事务的一部分，但是在“read_committed”中，使用者将只返回来自已提交事务的消息(以及任何不属于事务的消息)。

When writing to an external system, the limitation is in the need to coordinate the consumer's position with what is actually stored as output. The classic way of achieving this would be to introduce a two-phase commit between the storage of the consumer position and the storage of the consumers output. But this can be handled more simply and generally by letting the consumer store its offset in the same place as its output. This is better because many of the output systems a consumer might want to write to will not support a two-phase commit. As an example of this, consider a [Kafka Connect](https://kafka.apache.org/documentation/#connect) connector which populates data in HDFS along with the offsets of the data it reads so that it is guaranteed that either data and offsets are both updated or neither is. We follow similar patterns for many other data systems which require these stronger semantics and for which the messages do not have a primary key to allow for deduplication.

当向外部系统写入时，限制在于需要协调消费者的位置和实际存储为输出的内容。实现这一点的经典方法是在消费者位置的存储和使用者输出的存储之间引入两阶段提交。但这可以通过让使用者将其偏移量存储在与其输出相同的位置来更简单地处理。这样做更好，因为消费者可能希望写入的许多输出系统都不支持两阶段提交。作为一个例子，考虑一个[Kafka Connect](https://kafka.apache.org/documentation/#connect)连接器，它在HDFS中填充数据以及它读取的数据的偏移量，这样就保证了数据和偏移量要么都更新了，要么都没有更新。我们对许多其他数据系统遵循类似的模式，这些系统需要这些更强的语义，并且消息没有主键来支持重复数据删除。

So effectively Kafka supports exactly-once delivery in [Kafka Streams](https://kafka.apache.org/documentation/streams), and the transactional producer/consumer can be used generally to provide exactly-once delivery when transferring and processing data between Kafka topics. Exactly-once delivery for other destination systems generally requires cooperation with such systems, but Kafka provides the offset which makes implementing this feasible (see also [Kafka Connect](https://kafka.apache.org/documentation/#connect)). Otherwise, Kafka guarantees at-least-once delivery by default, and allows the user to implement at-most-once delivery by disabling retries on the producer and committing offsets in the consumer prior to processing a batch of messages.

因此Kafka有效地支持在[Kafka流](https://kafka.apache.org/documentation/streams)中精确地一次交付，并且事务生产者/消费者通常可以在Kafka主题之间传输和处理数据时提供精确的一次交付。确切地说，对于其他目标系统，一次交付通常需要与此类系统合作，但是Kafka提供了使实现这一点可行的偏移量(参见[Kafka Connect](https://kafka.apache.org/documentation/#connect))。否则，Kafka默认情况下保证最少一次传递，并允许用户通过在处理一批消息之前在生产者上禁用重试和在消费者中提交偏移量来实现最多一次传递。

### [4.7 Replication](http://kafka.apache.org/documentation/#replication)

Kafka replicates the log for each topic's partitions across a configurable number of servers (you can set this replication factor on a topic-by-topic basis). This allows automatic failover to these replicas when a server in the cluster fails so messages remain available in the presence of failures.

Kafka跨可配置数量的服务器为每个主题的分区复制日志(您可以根据每个主题设置这个复制因子)。这允许在集群中的服务器出现故障时自动将故障转移到这些副本，从而在出现故障时消息仍然可用。

Other messaging systems provide some replication-related features, but, in our (totally biased) opinion, this appears to be a tacked-on thing, not heavily used, and with large downsides: replicas are inactive, throughput is heavily impacted, it requires fiddly manual configuration, etc. Kafka is meant to be used with replication by default—in fact we implement un-replicated topics as replicated topics where the replication factor is one.

其他消息传递系统提供了一些与复制相关的特性，但在我们(完全有偏见)看来，这似乎是附加的东西，没有大量使用，并且有很大的缺点:副本是不活动的，吞吐量受到严重影响，它需要繁琐的手动配置，等等。缺省情况下，Kafka是用于复制的，实际上，我们将未复制的主题实现为复制因子为1的复制主题。

The unit of replication is the topic partition. Under non-failure conditions, each partition in Kafka has a single leader and zero or more followers. The total number of replicas including the leader constitute the replication factor. All reads and writes go to the leader of the partition. Typically, there are many more partitions than brokers and the leaders are evenly distributed among brokers. The logs on the followers are identical to the leader's log—all have the same offsets and messages in the same order (though, of course, at any given time the leader may have a few as-yet unreplicated messages at the end of its log).

复制的单元是主题分区。在非故障条件下，Kafka中的每个分区都有一个领导者和零个或更多的追随者。包括先导的复制总数构成复制因子。所有的读写操作都由分区的leader执行。通常，分区比代理多很多，并且leader均匀地分布在代理之间。追随者上的日志与领导者的日志完全相同，都具有相同的偏移量和相同顺序的消息(当然，在任何给定时间，领导者在其日志的末尾可能有一些尚未复制的消息)。

Followers consume messages from the leader just as a normal Kafka consumer would and apply them to their own log. Having the followers pull from the leader has the nice property of allowing the follower to naturally batch together log entries they are applying to their log.

追随者像普通的卡夫卡消费者一样使用来自领导者的消息，并将其应用到自己的日志中。从领导者那里抽取追随者有一个很好的特性，即允许追随者自然地将他们应用到他们日志中的日志条目批处理在一起。

As with most distributed systems automatically handling failures requires having a precise definition of what it means for a node to be "alive". For Kafka node liveness has two conditions

与大多数分布式系统一样，自动处理故障需要对节点“活动”的含义有一个精确定义。对于Kafka节点，活性有两个条件

1. A node must be able to maintain its session with ZooKeeper (via ZooKeeper's heartbeat mechanism)

   节点必须能够维护与ZooKeeper的会话(通过ZooKeeper的心跳机制)

2. If it is a follower it must replicate the writes happening on the leader and not fall "too far" behind

   如果它是一个追随者，它必须复制发生在领导者身上的书写，而不是“远远”落在后面

We refer to nodes satisfying these two conditions as being "in sync" to avoid the vagueness of "alive" or "failed". The leader keeps track of the set of "in sync" nodes. If a follower dies, gets stuck, or falls behind, the leader will remove it from the list of in sync replicas. The determination of stuck and lagging replicas is controlled by the `replica.lag.time.max.ms `configuration.

我们将满足这两个条件的节点称为“同步”，以避免“活着”或“失败”的模糊性。leader跟踪“同步”节点的集合。如果一个追随者死了，卡住了，或者落后了，领导者将把它从同步副本列表中删除。粘滞复制的测定由replica.lag.time.max.ms配置文件控制

In distributed systems terminology we only attempt to handle a "fail/recover" model of failures where nodes suddenly cease working and then later recover (perhaps without knowing that they have died). Kafka does not handle so-called "Byzantine" failures in which nodes produce arbitrary or malicious responses (perhaps due to bugs or foul play).

在分布式系统术语中，我们只尝试处理故障的“失败/恢复”模型，即节点突然停止工作，然后恢复(可能不知道它们已经死亡)。Kafka不处理所谓的“拜占庭式”故障，即节点产生任意的或恶意的响应(可能是由于错误或不合理的操作)。

We can now more precisely define that a message is considered committed when all in sync replicas for that partition have applied it to their log. Only committed messages are ever given out to the consumer. This means that the consumer need not worry about potentially seeing a message that could be lost if the leader fails. Producers, on the other hand, have the option of either waiting for the message to be committed or not, depending on their preference for tradeoff between latency and durability. This preference is controlled by the acks setting that the producer uses. Note that topics have a setting for the "minimum number" of in-sync replicas that is checked when the producer requests acknowledgment that a message has been written to the full set of in-sync replicas. If a less stringent acknowledgement is requested by the producer, then the message can be committed, and consumed, even if the number of in-sync replicas is lower than the minimum (e.g. it can be as low as just the leader).

我们现在可以更精确地定义，当该分区的所有同步副本都将消息应用到它们的日志中时，会认为已提交了消息。只有已提交的消息才会发送给消费者。这意味着消费者不必担心领导者失败时可能丢失的消息。另一方面，生产者可以选择等待消息提交或不提交，这取决于他们偏好在延迟和持久性之间进行权衡。该首选项由生成器使用的acks设置控制。注意，主题为同步副本的“最小数目”设置了一个设置，当生产者请求确认消息已写入到完整的同步副本集时，将检查该设置。如果生产者请求一个不那么严格的确认，则可以提交并使用消息，即使同步副本的数量低于最小值(例如，可能只有leader值)。

The guarantee that Kafka offers is that a committed message will not be lost, as long as there is at least one in sync replica alive, at all times.

kafka提供的保证是，只要至少有一个同步副本在任何时候都是活动的，提交的消息就不会丢失。

Kafka will remain available in the presence of node failures after a short fail-over period, but may not remain available in the presence of network partitions.

在短暂的故障转移周期后，Kafka将在存在的节点故障中保持可用，但可能在存在的网络分区中不保持可用。

#### [Replicated Logs: Quorums, ISRs, and State Machines (Oh my!)](http://kafka.apache.org/documentation/#design_replicatedlog)

复制日志:Quorums、ISRs和状态机

At its heart a Kafka partition is a replicated log. The replicated log is one of the most basic primitives in distributed data systems, and there are many approaches for implementing one. A replicated log can be used by other systems as a primitive for implementing other distributed systems in the [state-machine style](http://en.wikipedia.org/wiki/State_machine_replication).

Kafka分区的核心是一个复制的日志。复制日志是分布式数据系统中最基本的原语之一，有很多方法可以实现它。其他系统可以使用复制的日志作为原语，以[状态机风格]实现其他分布式系统。

A replicated log models the process of coming into consensus on the order of a series of values (generally numbering the log entries 0, 1, 2, ...). There are many ways to implement this, but the simplest and fastest is with a leader who chooses the ordering of values provided to it. As long as the leader remains alive, all followers need to only copy the values and ordering the leader chooses.

复制日志模拟对一系列值的顺序达成一致的过程(通常将日志条目编号为0、1、2、…)。实现这一目标的方法有很多，但最简单和最快的方法是让领导者选择提供给它的价值观的顺序。只要领导者还活着，所有的追随者只需要复制领导者选择的价值观和命令。

Of course if leaders didn't fail we wouldn't need followers! When the leader does die we need to choose a new leader from among the followers. But followers themselves may fall behind or crash so we must ensure we choose an up-to-date follower. The fundamental guarantee a log replication algorithm must provide is that if we tell the client a message is committed, and the leader fails, the new leader we elect must also have that message. This yields a tradeoff: if the leader waits for more followers to acknowledge a message before declaring it committed then there will be more potentially electable leaders.

当然，如果领导者没有失败，我们就不需要追随者!当领导者死了，我们需要从追随者中选择一个新的领导者。但是跟随者本身可能会落后或者崩溃，所以我们必须确保我们选择一个最新的跟随者。日志复制算法必须提供的基本保证是，如果我们告诉客户机一条消息已经提交，而leader失败了，那么我们选出的新leader也必须拥有那条消息。这就产生了一种权衡:如果领导人要等待更多的追随者承认一个信息，然后才宣布它已承诺，那么就会有更多潜在的当选领导人。

If you choose the number of acknowledgements required and the number of logs that must be compared to elect a leader such that there is guaranteed to be an overlap, then this is called a Quorum.

如果选择所需的确认数量和必须比较的日志数量来选择leader，以确保存在重叠，那么这称为Quorum

A common approach to this tradeoff is to use a majority vote for both the commit decision and the leader election. This is not what Kafka does, but let's explore it anyway to understand the tradeoffs. Let's say we have 2*f*+1 replicas. If *f*+1 replicas must receive a message prior to a commit being declared by the leader, and if we elect a new leader by electing the follower with the most complete log from at least *f*+1 replicas, then, with no more than *f* failures, the leader is guaranteed to have all committed messages. This is because among any *f*+1 replicas, there must be at least one replica that contains all committed messages. That replica's log will be the most complete and therefore will be selected as the new leader. There are many remaining details that each algorithm must handle (such as precisely defined what makes a log more complete, ensuring log consistency during leader failure or changing the set of servers in the replica set) but we will ignore these for now.

一种常见的折衷方法是在commit决策和领导人选举中都使用多数票。这不是kafka所做的，但让我们无论如何探索它来理解权衡。假设有2*f*+1个副本。如果*f*+1副本必须在leader声明提交之前接收消息,如果我们通过从至少*f*+1个副本中选出日志最完整的follower来选出新的leader,然后,不超过* f *失败,保证领导拥有所有已提交的信息。这是因为在任何*f*+1副本中，必须至少有一个副本包含所有提交的消息。该副本的日志将是最完整的，因此将被选为新的领导者。每个算法都必须处理许多剩余的细节(比如精确定义什么使日志更完整，在leader故障期间确保日志的一致性，或者更改副本集中的服务器集)，但是我们现在将忽略这些。

This majority vote approach has a very nice property: the latency is dependent on only the fastest servers. That is, if the replication factor is three, the latency is determined by the faster follower not the slower one.

这种多数投票的方法有一个很好的特性:延迟只依赖于最快的服务器。也就是说，如果复制因子为3，则延迟由更快的追随者决定，而不是由更慢的追随者决定。

There are a rich variety of algorithms in this family including ZooKeeper's [Zab](http://web.archive.org/web/20140602093727/http://www.stanford.edu/class/cs347/reading/zab.pdf), [Raft](https://www.usenix.org/system/files/conference/atc14/atc14-paper-ongaro.pdf), and [Viewstamped Replication](http://pmg.csail.mit.edu/papers/vr-revisited.pdf). The most similar academic publication we are aware of to Kafka's actual implementation is [PacificA](http://research.microsoft.com/apps/pubs/default.aspx?id=66814) from Microsoft.

这类算法种类繁多，包括ZooKeeper's [Zab](http://web.archive.org/web/20140602093727/http://www.stanford.edu/class/cs347/reading/zab.pdf), [Raft](https://www.usenix.org/system/files/conference/atc14/atc14-paper-ongaro.pdf), and [Viewstamped Replication](http://pmg.csail.mit.edu/papers/vr-revisited.pdf)我们所知道的与Kafka实际实现最相似的学术出版物是来自微软的[PacificA]

The downside of majority vote is that it doesn't take many failures to leave you with no electable leaders. To tolerate one failure requires three copies of the data, and to tolerate two failures requires five copies of the data. In our experience having only enough redundancy to tolerate a single failure is not enough for a practical system, but doing every write five times, with 5x the disk space requirements and 1/5th the throughput, is not very practical for large volume data problems. This is likely why quorum algorithms more commonly appear for shared cluster configuration such as ZooKeeper but are less common for primary data storage. For example in HDFS the namenode's high-availability feature is built on a [majority-vote-based journal](http://blog.cloudera.com/blog/2012/10/quorum-based-journaling-in-cdh4-1), but this more expensive approach is not used for the data itself.

多数投票的不利之处在于，不需要很多次失败，你就没有可以当选的领导人。要容忍一次故障，需要数据的三份副本;要容忍两次故障，需要数据的五份副本。根据我们的经验，对于一个实际的系统来说，只有足够的冗余来容忍一个故障是不够的，但是在磁盘空间需求的5倍和吞吐量的1/5的情况下，每次写操作5次对于处理大容量数据问题是不太实际的。这可能就是为什么仲裁算法更常见地出现在共享集群配置(如ZooKeeper)中，而在主数据存储中不那么常见的原因。例如，在HDFS中，namenode的高可用性特性是建立在[基于多数选票的期刊](http://blog.cloudera.com/blog/2012/10/quorum- basedjournaling-in-cdh4 -1)的基础上的，但这种更昂贵的方法并不用于数据本身。

Kafka takes a slightly different approach to choosing its quorum set. Instead of majority vote, Kafka dynamically maintains a set of in-sync replicas (ISR) that are caught-up to the leader. Only members of this set are eligible for election as leader. A write to a Kafka partition is not considered committed until *all* in-sync replicas have received the write. This ISR set is persisted to ZooKeeper whenever it changes. Because of this, any replica in the ISR is eligible to be elected leader. This is an important factor for Kafka's usage model where there are many partitions and ensuring leadership balance is important. With this ISR model and *f+1* replicas, a Kafka topic can tolerate *f* failures without losing committed messages.

Kafka采用一种稍微不同的方法来选择它的quorum集。Kafka动态地维护一组同步副本(ISR)，而不是多数投票。只有这一组的成员才有资格当选为领导人。对Kafka分区的写操作在*所有*同步副本接收到写操作之前不会被认为是提交的。每当ISR设置发生变化时，它都会持久化到ZooKeeper。因此，任何复制ISR的人都有资格当选领导人。对于Kafka的使用模型来说，这是一个重要的因素，因为它有很多分区，所以确保领导层的平衡非常重要。有了这个ISR模型和*f+1*副本，Kafka主题可以容忍*f*失败而不会丢失提交的消息。

For most use cases we hope to handle, we think this tradeoff is a reasonable one. In practice, to tolerate *f* failures, both the majority vote and the ISR approach will wait for the same number of replicas to acknowledge before committing a message (e.g. to survive one failure a majority quorum needs three replicas and one acknowledgement and the ISR approach requires two replicas and one acknowledgement). The ability to commit without the slowest servers is an advantage of the majority vote approach. However, we think it is ameliorated by allowing the client to choose whether they block on the message commit or not, and the additional throughput and disk space due to the lower required replication factor is worth it.

对于我们希望处理的大多数用例，我们认为这种权衡是合理的。容忍* f *失败,在实践中,多数投票和ISR方法将等待相同数量的副本承认之前的消息(要在一个失败中存活，多数仲裁需要三个副本和一个确认，而ISR方法需要两个副本和一个确认)。能够在不使用最慢服务器的情况下提交是多数投票方式的一个优点。但是，我们认为，通过允许客户机选择是否阻塞消息提交，可以改善这种情况，并且由于所需复制因子较低而增加的吞吐量和磁盘空间是值得的。

Another important design distinction is that Kafka does not require that crashed nodes recover with all their data intact. It is not uncommon for replication algorithms in this space to depend on the existence of "stable storage" that cannot be lost in any failure-recovery scenario without potential consistency violations. There are two primary problems with this assumption. First, disk errors are the most common problem we observe in real operation of persistent data systems and they often do not leave data intact. Secondly, even if this were not a problem, we do not want to require the use of fsync on every write for our consistency guarantees as this can reduce performance by two to three orders of magnitude. Our protocol for allowing a replica to rejoin the ISR ensures that before rejoining, it must fully re-sync again even if it lost unflushed data in its crash.

另一个重要的设计区别是Kafka不要求崩溃的节点恢复时所有数据完好无损。在这个空间中，复制算法依赖于“稳定存储”的存在是很常见的，在任何故障恢复场景中，稳定存储不会丢失，而不会违反潜在的一致性。这种假设存在两个主要问题。首先，磁盘错误是我们在持久数据系统的实际操作中观察到的最常见的问题，它们通常不会使数据保持完整。其次，即使这不是问题，我们也不希望在每次写操作时都使用fsync来保证一致性，因为这会降低性能2到3个数量级。我们允许副本重新加入ISR的协议确保了在重新加入之前，它必须再次完全重新同步，即使它在崩溃时丢失了未刷新的数据。

#### [Unclean leader election: What if they all die?](http://kafka.apache.org/documentation/#design_uncleanleader)

Note that Kafka's guarantee with respect to data loss is predicated on at least one replica remaining in sync. If all the nodes replicating a partition die, this guarantee no longer holds.

注意，Kafka关于数据丢失的保证是基于至少一个副本保持同步。如果复制一个分区的所有节点都死亡，则此保证将不再成立。

However a practical system needs to do something reasonable when all the replicas die. If you are unlucky enough to have this occur, it is important to consider what will happen. There are two behaviors that could be implemented:

然而，实际的系统需要在所有副本都死亡时做一些合理的事情。如果你很不幸地遇到了这种情况，考虑一下会发生什么是很重要的。有两种行为可以实现

1. Wait for a replica in the ISR to come back to life and choose this replica as the leader (hopefully it still has all its data).

   等待ISR中的一个副本复活并选择这个副本作为leader(希望它仍然拥有它的所有数据)。

2. Choose the first replica (not necessarily in the ISR) that comes back to life as the leader.

   选择第一个副本(不一定是在ISR中)作为leader复活。

This is a simple tradeoff between availability and consistency. If we wait for replicas in the ISR, then we will remain unavailable as long as those replicas are down. If such replicas were destroyed or their data was lost, then we are permanently down. If, on the other hand, a non-in-sync replica comes back to life and we allow it to become leader, then its log becomes the source of truth even though it is not guaranteed to have every committed message. By default from version 0.11.0.0, Kafka chooses the first strategy and favor waiting for a consistent replica. This behavior can be changed using configuration property unclean.leader.election.enable, to support use cases where uptime is preferable to consistency.

这是可用性和一致性之间的一个简单的权衡。如果我们在ISR中等待副本，那么只要这些副本关闭，我们就会一直不可用。如果这样的副本被销毁或它们的数据丢失，那么我们将永久停机。另一方面，如果一个非同步的副本复活了，而我们允许它成为领导者，那么它的日志就会成为真相的来源，尽管它不能保证拥有所有承诺的信息。默认情况下，从版本0.11.0.0开始，Kafka选择第一种策略，并倾向于等待一致的副本。可以使用配置属性`unclean.leader.election`更改此行为。启用，以支持正常运行时间优于一致性的用例。

This dilemma is not specific to Kafka. It exists in any quorum-based scheme. For example in a majority voting scheme, if a majority of servers suffer a permanent failure, then you must either choose to lose 100% of your data or violate consistency by taking what remains on an existing server as your new source of truth.

这种困境并不是kafka特有的。它存在于任何基于quorum(大多数投票机制)的方案中。例如，在多数投票方案中，如果大多数服务器遭受永久故障，那么您必须选择要么丢失100%的数据，要么违反一致性，将现有服务器上的剩余数据作为新的真相来源。

#### [Availability and Durability Guarantees](http://kafka.apache.org/documentation/#design_ha)

可用性和持久性保证

When writing to Kafka, producers can choose whether they wait for the message to be acknowledged by 0,1 or all (-1) replicas. Note that "acknowledgement by all replicas" does not guarantee that the full set of assigned replicas have received the message. By default, when acks=all, acknowledgement happens as soon as all the current in-sync replicas have received the message. For example, if a topic is configured with only two replicas and one fails (i.e., only one in sync replica remains), then writes that specify acks=all will succeed. However, these writes could be lost if the remaining replica also fails. Although this ensures maximum availability of the partition, this behavior may be undesirable to some users who prefer durability over availability. Therefore, we provide two topic-level configurations that can be used to prefer message durability over availability:

当写给Kafka时，生产者可以选择他们是否等待消息被0,1或所有(-1)副本确认。注意，“所有副本的确认”并不保证已分配的副本的全部集合已接收到消息。默认情况下，当acks=all时，在所有当前`同步副本收`到消息后立即确认。例如，如果一个主题只配置了两个副本，而其中一个失败(即，只保留一个同步副本)，那么指定ack =all的写入操作将会成功。但是，如果剩余的副本也失败，这些写操作可能会丢失。尽管这确保了分区的最大可用性，但对于一些更喜欢持久性而不是可用性的用户来说，这种行为可能不受欢迎。因此，我们提供了两种主题级配置，可用于优先考虑消息的(p)持久性而不是(a)可用性

1. Disable `unclean leader election` - if all replicas become unavailable, then the partition will remain unavailable until the most recent leader becomes available again. This effectively prefers unavailability over the risk of message loss. See the previous section on Unclean Leader Election for clarification.

   禁用不干净leader选择——如果所有副本都不可用，那么分区将保持不可用，直到最近的leader再次可用。这实际上更倾向于不可用性，而不是消息丢失的风险。请参阅前一节关于不洁净领袖选举的说明。

2. Specify a minimum ISR size - the partition will only accept writes if the size of the ISR is above a certain minimum, in order to prevent the loss of messages that were written to just a single replica, which subsequently becomes unavailable. This setting only takes effect if the producer uses acks=all and guarantees that the message will be acknowledged by at least this many in-sync replicas. This setting offers a trade-off between consistency and availability. A higher setting for minimum ISR size guarantees better consistency since the message is guaranteed to be written to more replicas which reduces the probability that it will be lost. However, it reduces availability since the partition will be unavailable for writes if the number of in-sync replicas drops below the minimum threshold.

   指定最小ISR大小——如果ISR的大小大于某个最小值，分区将只接受写操作，以防止将消息写入到单个副本中，而该副本随后变得不可用。只有当生产者使用acks=all并保证消息至少被这么多同步副本确认时，此设置才会生效。此设置提供了一致性和可用性之间的权衡。更高的最小ISR大小设置可以保证更好的一致性，因为可以保证将消息写入更多的副本，从而降低丢失消息的概率。但是，它降低了可用性，因为如果同步副本的数量低于最小阈值，分区将不可用于写操作。

#### [Replica Management](http://kafka.apache.org/documentation/#design_replicamanagment)

复制管理

The above discussion on replicated logs really covers only a single log, i.e. one topic partition. However a Kafka cluster will manage hundreds or thousands of these partitions. We attempt to balance partitions within a cluster in a round-robin fashion to avoid clustering all partitions for high-volume topics on a small number of nodes. Likewise we try to balance leadership so that each node is the leader for a proportional share of its partitions.

上面关于复制日志的讨论实际上只涉及单个日志，即一个主题分区。然而，Kafka集群将管理数百或数千个分区。我们尝试以循环方式平衡集群中的分区，以避免在少量节点上为大容量主题集群化所有分区。同样，我们尝试平衡领导，以便每个节点都是其分区的比例份额的领导。

It is also important to optimize the leadership election process as that is the critical window of unavailability. A naive implementation of leader election would end up running an election per partition for all partitions a node hosted when that node failed. Instead, we elect one of the brokers as the "controller". This controller detects failures at the broker level and is responsible for changing the leader of all affected partitions in a failed broker. The result is that we are able to batch together many of the required leadership change notifications which makes the election process far cheaper and faster for a large number of partitions. If the controller fails, one of the surviving brokers will become the new controller.

优化领导层选举过程也很重要，因为这是不可用的关键窗口。领导人选举的幼稚实现将在节点失败时为该节点承载的所有分区执行每个分区的选举。相反，我们选择其中一个代理作为“控制器”。此控制器在代理级别检测故障，并负责更改故障代理中所有受影响分区的leader。其结果是，我们能够将许多所需的领导层变更通知批量处理在一起，这使得选举过程对于大量分区来说更加便宜和快速。如果控制器失败，幸存的一个代理将成为新的控制器。

### [4.8 Log Compaction](http://kafka.apache.org/documentation/#compaction)

Log compaction ensures that Kafka will always retain at least the last known value for each message key within the log of data for a single topic partition. It addresses use cases and scenarios such as restoring state after application crashes or system failure, or reloading caches after application restarts during operational maintenance. Let's dive into these use cases in more detail and then describe how compaction works.

日志压缩确保Kafka将始终至少保留单个主题分区数据日志中每个消息键的最新已知值。它处理用例和场景，例如在应用程序崩溃或系统故障后恢复状态，或在操作维护期间重新启动应用程序后重新加载缓存。让我们更详细地研究这些用例，然后描述压缩是如何工作的。

So far we have described only the simpler approach to data retention where old log data is discarded after a fixed period of time or when the log reaches some predetermined size. This works well for temporal event data such as logging where each record stands alone. However an important class of data streams are the log of changes to keyed, mutable data (for example, the changes to a database table).

到目前为止，我们只描述了一种更简单的数据保留方法，即在一段固定的时间之后或当日志达到某种预定的大小时丢弃旧的日志数据。这对于时态事件数据很有效，比如记录每条记录独立的位置。然而，一类重要的数据流是键控的、可变数据(例如，数据库表的更改)的更改日志。

Let's discuss a concrete example of such a stream. Say we have a topic containing user email addresses; every time a user updates their email address we send a message to this topic using their user id as the primary key. Now say we send the following messages over some time period for a user with id 123, each message corresponding to a change in email address (messages for other ids are omitted):

让我们讨论这样一个流的具体示例。假设我们有一个包含用户电子邮件地址的主题;每当用户更新他们的电子邮件地址时，我们使用他们的用户id作为主键向这个主题发送消息。现在假设我们在一段时间内为id为123的用户发送以下消息，每个消息对应于电子邮件地址的变化(省略其他id的消息):

```text
        123 => bill@microsoft.com
                .
                .
                .
        123 => bill@gatesfoundation.org
                .
                .
                .
        123 => bill@gmail.com
```

Log compaction gives us a more granular retention mechanism so that we are guaranteed to retain at least the last update for each primary key (e.g. `bill@gmail.com`). By doing this we guarantee that the log contains a full snapshot of the final value for every key not just keys that changed recently. This means downstream consumers can restore their own state off this topic without us having to retain a complete log of all changes.

日志压缩为我们提供了一种更细粒度的保留机制，这样我们就可以保证至少保留每个主键的最后一次更新(例如。“bill@gmail.com”)。通过这样做，我们可以保证日志包含每个键的最终值的完整快照，而不仅仅是最近更改的键。这意味着下游使用者可以在这个主题之外恢复他们自己的状态，而不需要我们保留所有更改的完整日志。

Let's start by looking at a few use cases where this is useful, then we'll see how it can be used.

让我们从一些有用的用例开始，然后我们将看到如何使用它。

1. *Database change subscription*. It is often necessary to have a data set in multiple data systems, and often one of these systems is a database of some kind (either a RDBMS or perhaps a new-fangled key-value store). For example you might have a database, a cache, a search cluster, and a Hadoop cluster. Each change to the database will need to be reflected in the cache, the search cluster, and eventually in Hadoop. In the case that one is only handling the real-time updates you only need recent log. But if you want to be able to reload the cache or restore a failed search node you may need a complete data set.
2. *Event sourcing*. This is a style of application design which co-locates query processing with application design and uses a log of changes as the primary store for the application.
3. *Journaling for high-availability*. A process that does local computation can be made fault-tolerant by logging out changes that it makes to its local state so another process can reload these changes and carry on if it should fail. A concrete example of this is handling counts, aggregations, and other "group by"-like processing in a stream query system. Samza, a real-time stream-processing framework, [uses this feature](http://samza.apache.org/learn/documentation/0.7.0/container/state-management.html) for exactly this purpose.

In each of these cases one needs primarily to handle the real-time feed of changes, but occasionally, when a machine crashes or data needs to be re-loaded or re-processed, one needs to do a full load. Log compaction allows feeding both of these use cases off the same backing topic. This style of usage of a log is described in more detail in [this blog post](http://engineering.linkedin.com/distributed-systems/log-what-every-software-engineer-should-know-about-real-time-datas-unifying).

The general idea is quite simple. If we had infinite log retention, and we logged each change in the above cases, then we would have captured the state of the system at each time from when it first began. Using this complete log, we could restore to any point in time by replaying the first N records in the log. This hypothetical complete log is not very practical for systems that update a single record many times as the log will grow without bound even for a stable dataset. The simple log retention mechanism which throws away old updates will bound space but the log is no longer a way to restore the current state—now restoring from the beginning of the log no longer recreates the current state as old updates may not be captured at all.

Log compaction is a mechanism to give finer-grained per-record retention, rather than the coarser-grained time-based retention. The idea is to selectively remove records where we have a more recent update with the same primary key. This way the log is guaranteed to have at least the last state for each key.

This retention policy can be set per-topic, so a single cluster can have some topics where retention is enforced by size or time and other topics where retention is enforced by compaction.

This functionality is inspired by one of LinkedIn's oldest and most successful pieces of infrastructure—a database changelog caching service called [Databus](https://github.com/linkedin/databus). Unlike most log-structured storage systems Kafka is built for subscription and organizes data for fast linear reads and writes. Unlike Databus, Kafka acts as a source-of-truth store so it is useful even in situations where the upstream data source would not otherwise be replayable.

#### [Log Compaction Basics](http://kafka.apache.org/documentation/#design_compactionbasics)

Here is a high-level picture that shows the logical structure of a Kafka log with the offset for each message.

![img](http://kafka.apache.org/26/images/log_cleaner_anatomy.png)

The head of the log is identical to a traditional Kafka log. It has dense, sequential offsets and retains all messages. Log compaction adds an option for handling the tail of the log. The picture above shows a log with a compacted tail. Note that the messages in the tail of the log retain the original offset assigned when they were first written—that never changes. Note also that all offsets remain valid positions in the log, even if the message with that offset has been compacted away; in this case this position is indistinguishable from the next highest offset that does appear in the log. For example, in the picture above the offsets 36, 37, and 38 are all equivalent positions and a read beginning at any of these offsets would return a message set beginning with 38.

Compaction also allows for deletes. A message with a key and a null payload will be treated as a delete from the log. This delete marker will cause any prior message with that key to be removed (as would any new message with that key), but delete markers are special in that they will themselves be cleaned out of the log after a period of time to free up space. The point in time at which deletes are no longer retained is marked as the "delete retention point" in the above diagram.

The compaction is done in the background by periodically recopying log segments. Cleaning does not block reads and can be throttled to use no more than a configurable amount of I/O throughput to avoid impacting producers and consumers. The actual process of compacting a log segment looks something like this:

![img](http://kafka.apache.org/26/images/log_compaction.png)



#### [What guarantees does log compaction provide](http://kafka.apache.org/documentation/#design_compactionguarantees)?

Log compaction guarantees the following:

1. Any consumer that stays caught-up to within the head of the log will see every message that is written; these messages will have sequential offsets. The topic's `min.compaction.lag.ms` can be used to guarantee the minimum length of time must pass after a message is written before it could be compacted. I.e. it provides a lower bound on how long each message will remain in the (uncompacted) head. The topic's `max.compaction.lag.ms` can be used to guarantee the maximum delay between the time a message is written and the time the message becomes eligible for compaction.
2. Ordering of messages is always maintained. Compaction will never re-order messages, just remove some.
3. The offset for a message never changes. It is the permanent identifier for a position in the log.
4. Any consumer progressing from the start of the log will see at least the final state of all records in the order they were written. Additionally, all delete markers for deleted records will be seen, provided the consumer reaches the head of the log in a time period less than the topic's `delete.retention.ms` setting (the default is 24 hours). In other words: since the removal of delete markers happens concurrently with reads, it is possible for a consumer to miss delete markers if it lags by more than `delete.retention.ms`.

#### [Log Compaction Details](http://kafka.apache.org/documentation/#design_compactiondetails)

Log compaction is handled by the log cleaner, a pool of background threads that recopy log segment files, removing records whose key appears in the head of the log. Each compactor thread works as follows:

1. It chooses the log that has the highest ratio of log head to log tail
2. It creates a succinct summary of the last offset for each key in the head of the log
3. It recopies the log from beginning to end removing keys which have a later occurrence in the log. New, clean segments are swapped into the log immediately so the additional disk space required is just one additional log segment (not a fully copy of the log).
4. The summary of the log head is essentially just a space-compact hash table. It uses exactly 24 bytes per entry. As a result with 8GB of cleaner buffer one cleaner iteration can clean around 366GB of log head (assuming 1k messages).



#### [Configuring The Log Cleaner](http://kafka.apache.org/documentation/#design_compactionconfig)

The log cleaner is enabled by default. This will start the pool of cleaner threads. To enable log cleaning on a particular topic, add the log-specific property

```text
 log.cleanup.policy=compact
```

The `log.cleanup.policy` property is a broker configuration setting defined in the broker's `server.properties` file; it affects all of the topics in the cluster that do not have a configuration override in place as documented [here](http://kafka.apache.org/documentation.html#brokerconfigs). The log cleaner can be configured to retain a minimum amount of the uncompacted "head" of the log. This is enabled by setting the compaction time lag.

```text
  log.cleaner.min.compaction.lag.ms
```

This can be used to prevent messages newer than a minimum message age from being subject to compaction. If not set, all log segments are eligible for compaction except for the last segment, i.e. the one currently being written to. The active segment will not be compacted even if all of its messages are older than the minimum compaction time lag. The log cleaner can be configured to ensure a maximum delay after which the uncompacted "head" of the log becomes eligible for log compaction.

```text
  log.cleaner.max.compaction.lag.ms
```

This can be used to prevent log with low produce rate from remaining ineligible for compaction for an unbounded duration. If not set, logs that do not exceed min.cleanable.dirty.ratio are not compacted. Note that this compaction deadline is not a hard guarantee since it is still subjected to the availability of log cleaner threads and the actual compaction time. You will want to monitor the uncleanable-partitions-count, max-clean-time-secs and max-compaction-delay-secs metrics.

Further cleaner configurations are described [here](http://kafka.apache.org/documentation.html#brokerconfigs).

### [4.9 Quotas](http://kafka.apache.org/documentation/#design_quotas)

限额，配额

Kafka cluster has the ability to enforce quotas on requests to control the broker resources used by clients. Two types of client quotas can be enforced by Kafka brokers for each group of clients sharing a quota:

Kafka集群能够对请求强制执行配额，以控制客户机使用的代理（broker)资源。Kafka代理（broker)可以为共享配额的每组客户执行两种类型的客户配额:

1. Network bandwidth quotas define byte-rate thresholds (since 0.9)

   网络带宽配额定义字节率阈值(自0.9起)

2. Request rate quotas define CPU utilization thresholds as a percentage of network and I/O threads (since 0.11)

   请求率配额将CPU利用率阈值定义为网络和I/O线程的百分比(从0.11开始)

#### [Why are quotas necessary](http://kafka.apache.org/documentation/#design_quotasnecessary)?

It is possible for producers and consumers to produce/consume very high volumes of data or generate requests at a very high rate and thus monopolize broker resources, cause network saturation and generally DOS other clients and the brokers themselves. Having quotas protects against these issues and is all the more important in large multi-tenant clusters where a small set of badly behaved clients can degrade user experience for the well behaved ones. In fact, when running Kafka as a service this even makes it possible to enforce API limits according to an agreed upon contract.

#### [Client groups](http://kafka.apache.org/documentation/#design_quotasgroups)

The identity of Kafka clients is the user principal which represents an authenticated user in a secure cluster. In a cluster that supports unauthenticated clients, user principal is a grouping of unauthenticated users chosen by the broker using a configurable `PrincipalBuilder`. Client-id is a logical grouping of clients with a meaningful name chosen by the client application. The tuple (user, client-id) defines a secure logical group of clients that share both user principal and client-id.

Quotas can be applied to (user, client-id), user or client-id groups. For a given connection, the most specific quota matching the connection is applied. All connections of a quota group share the quota configured for the group. For example, if (user="test-user", client-id="test-client") has a produce quota of 10MB/sec, this is shared across all producer instances of user "test-user" with the client-id "test-client".

#### [Quota Configuration](http://kafka.apache.org/documentation/#design_quotasconfig)

Quota configuration may be defined for (user, client-id), user and client-id groups. It is possible to override the default quota at any of the quota levels that needs a higher (or even lower) quota. The mechanism is similar to the per-topic log config overrides. User and (user, client-id) quota overrides are written to ZooKeeper under ***/config/users*** and client-id quota overrides are written under ***/config/clients***. These overrides are read by all brokers and are effective immediately. This lets us change quotas without having to do a rolling restart of the entire cluster. See [here](http://kafka.apache.org/documentation/#quotas) for details. Default quotas for each group may also be updated dynamically using the same mechanism.

The order of precedence for quota configuration is:

1. /config/users/<user>/clients/<client-id>
2. /config/users/<user>/clients/<default>
3. /config/users/<user>
4. /config/users/<default>/clients/<client-id>
5. /config/users/<default>/clients/<default>
6. /config/users/<default>
7. /config/clients/<client-id>
8. /config/clients/<default>

Broker properties (quota.producer.default, quota.consumer.default) can also be used to set defaults of network bandwidth quotas for client-id groups. These properties are being deprecated and will be removed in a later release. Default quotas for client-id can be set in Zookeeper similar to the other quota overrides and defaults.



#### [Network Bandwidth Quotas](http://kafka.apache.org/documentation/#design_quotasbandwidth)

Network bandwidth quotas are defined as the byte rate threshold for each group of clients sharing a quota. By default, each unique client group receives a fixed quota in bytes/sec as configured by the cluster. This quota is defined on a per-broker basis. Each group of clients can publish/fetch a maximum of X bytes/sec per broker before clients are throttled.

#### [Request Rate Quotas](http://kafka.apache.org/documentation/#design_quotascpu)

Request rate quotas are defined as the percentage of time a client can utilize on request handler I/O threads and network threads of each broker within a quota window. A quota of `n%` represents `n%` of one thread, so the quota is out of a total capacity of `((num.io.threads + num.network.threads) * 100)%`. Each group of clients may use a total percentage of upto `n%` across all I/O and network threads in a quota window before being throttled. Since the number of threads allocated for I/O and network threads are typically based on the number of cores available on the broker host, request rate quotas represent the total percentage of CPU that may be used by each group of clients sharing the quota.

#### [Enforcement](http://kafka.apache.org/documentation/#design_quotasenforcement)

By default, each unique client group receives a fixed quota as configured by the cluster. This quota is defined on a per-broker basis. Each client can utilize this quota per broker before it gets throttled. We decided that defining these quotas per broker is much better than having a fixed cluster wide bandwidth per client because that would require a mechanism to share client quota usage among all the brokers. This can be harder to get right than the quota implementation itself!

How does a broker react when it detects a quota violation? In our solution, the broker first computes the amount of delay needed to bring the violating client under its quota and returns a response with the delay immediately. In case of a fetch request, the response will not contain any data. Then, the broker mutes the channel to the client, not to process requests from the client anymore, until the delay is over. Upon receiving a response with a non-zero delay duration, the Kafka client will also refrain from sending further requests to the broker during the delay. Therefore, requests from a throttled client are effectively blocked from both sides. Even with older client implementations that do not respect the delay response from the broker, the back pressure applied by the broker via muting its socket channel can still handle the throttling of badly behaving clients. Those clients who sent further requests to the throttled channel will receive responses only after the delay is over.

Byte-rate and thread utilization are measured over multiple small windows (e.g. 30 windows of 1 second each) in order to detect and correct quota violations quickly. Typically, having large measurement windows (for e.g. 10 windows of 30 seconds each) leads to large bursts of traffic followed by long delays which is not great in terms of user experience.

## [5. IMPLEMENTATION](http://kafka.apache.org/documentation/#implementation)

### [5.1 Network Layer](http://kafka.apache.org/documentation/#networklayer)

The network layer is a fairly straight-forward NIO server, and will not be described in great detail. The sendfile implementation is done by giving the `MessageSet` interface a `writeTo` method. This allows the file-backed message set to use the more efficient `transferTo` implementation instead of an in-process buffered write. The threading model is a single acceptor thread and *N* processor threads which handle a fixed number of connections each. This design has been pretty thoroughly tested [elsewhere](http://sna-projects.com/blog/2009/08/introducing-the-nio-socketserver-implementation) and found to be simple to implement and fast. The protocol is kept quite simple to allow for future implementation of clients in other languages.

### [5.2 Messages](http://kafka.apache.org/documentation/#messages)

Messages consist of a variable-length header, a variable-length opaque key byte array and a variable-length opaque value byte array. The format of the header is described in the following section. Leaving the key and value opaque is the right decision: there is a great deal of progress being made on serialization libraries right now, and any particular choice is unlikely to be right for all uses. Needless to say a particular application using Kafka would likely mandate a particular serialization type as part of its usage. The `RecordBatch` interface is simply an iterator over messages with specialized methods for bulk reading and writing to an NIO `Channel`.

### [5.3 Message Format](http://kafka.apache.org/documentation/#messageformat)

Messages (aka Records) are always written in batches. The technical term for a batch of messages is a record batch, and a record batch contains one or more records. In the degenerate case, we could have a record batch containing a single record. Record batches and records have their own headers. The format of each is described below.

#### [5.3.1 Record Batch](http://kafka.apache.org/documentation/#recordbatch)

The following is the on-disk format of a RecordBatch.



```java
		baseOffset: int64
		batchLength: int32
		partitionLeaderEpoch: int32
		magic: int8 (current magic value is 2)
		crc: int32
		attributes: int16
			bit 0~2:
				0: no compression
				1: gzip
				2: snappy
				3: lz4
				4: zstd
			bit 3: timestampType
			bit 4: isTransactional (0 means not transactional)
			bit 5: isControlBatch (0 means not a control batch)
			bit 6~15: unused
		lastOffsetDelta: int32
		firstTimestamp: int64
		maxTimestamp: int64
		producerId: int64
		producerEpoch: int16
		baseSequence: int32
		records: [Record]
```



Note that when compression is enabled, the compressed record data is serialized directly following the count of the number of records.

The CRC covers the data from the attributes to the end of the batch (i.e. all the bytes that follow the CRC). It is located after the magic byte, which means that clients must parse the magic byte before deciding how to interpret the bytes between the batch length and the magic byte. The partition leader epoch field is not included in the CRC computation to avoid the need to recompute the CRC when this field is assigned for every batch that is received by the broker. The CRC-32C (Castagnoli) polynomial is used for the computation.

On compaction: unlike the older message formats, magic v2 and above preserves the first and last offset/sequence numbers from the original batch when the log is cleaned. This is required in order to be able to restore the producer's state when the log is reloaded. If we did not retain the last sequence number, for example, then after a partition leader failure, the producer might see an OutOfSequence error. The base sequence number must be preserved for duplicate checking (the broker checks incoming Produce requests for duplicates by verifying that the first and last sequence numbers of the incoming batch match the last from that producer). As a result, it is possible to have empty batches in the log when all the records in the batch are cleaned but batch is still retained in order to preserve a producer's last sequence number. One oddity here is that the firstTimestamp field is not preserved during compaction, so it will change if the first record in the batch is compacted away.

##### [5.3.1.1 Control Batches](http://kafka.apache.org/documentation/#controlbatch)

A control batch contains a single record called the control record. Control records should not be passed on to applications. Instead, they are used by consumers to filter out aborted transactional messages.

The key of a control record conforms to the following schema:



```java
       version: int16 (current version is 0)
       type: int16 (0 indicates an abort marker, 1 indicates a commit)
```



The schema for the value of a control record is dependent on the type. The value is opaque to clients.

#### [5.3.2 Record](http://kafka.apache.org/documentation/#record)

Record level headers were introduced in Kafka 0.11.0. The on-disk format of a record with Headers is delineated below.



```java
		length: varint
		attributes: int8
			bit 0~7: unused
		timestampDelta: varint
		offsetDelta: varint
		keyLength: varint
		key: byte[]
		valueLen: varint
		value: byte[]
		Headers => [Header]
```



##### [5.3.2.1 Record Header](http://kafka.apache.org/documentation/#recordheader)



```java
		headerKeyLength: varint
		headerKey: String
		headerValueLength: varint
		Value: byte[]
```



We use the same varint encoding as Protobuf. More information on the latter can be found [here](https://developers.google.com/protocol-buffers/docs/encoding#varints). The count of headers in a record is also encoded as a varint.

#### [5.3.3 Old Message Format](http://kafka.apache.org/documentation/#messageset)

Prior to Kafka 0.11, messages were transferred and stored in *message sets*. In a message set, each message has its own metadata. Note that although message sets are represented as an array, they are not preceded by an int32 array size like other array elements in the protocol.

**Message Set:**



```java
    MessageSet (Version: 0) => [offset message_size message]
        offset => INT64
        message_size => INT32
        message => crc magic_byte attributes key value
            crc => INT32
            magic_byte => INT8
            attributes => INT8
                bit 0~2:
                    0: no compression
                    1: gzip
                    2: snappy
                bit 3~7: unused
            key => BYTES
            value => BYTES
```





```java
    MessageSet (Version: 1) => [offset message_size message]
        offset => INT64
        message_size => INT32
        message => crc magic_byte attributes timestamp key value
            crc => INT32
            magic_byte => INT8
            attributes => INT8
                bit 0~2:
                    0: no compression
                    1: gzip
                    2: snappy
                    3: lz4
                bit 3: timestampType
                    0: create time
                    1: log append time
                bit 4~7: unused
            timestamp => INT64
            key => BYTES
            value => BYTES
```



In versions prior to Kafka 0.10, the only supported message format version (which is indicated in the magic value) was 0. Message format version 1 was introduced with timestamp support in version 0.10.

- Similarly to version 2 above, the lowest bits of attributes represent the compression type.
- In version 1, the producer should always set the timestamp type bit to 0. If the topic is configured to use log append time, (through either broker level config log.message.timestamp.type = LogAppendTime or topic level config message.timestamp.type = LogAppendTime), the broker will overwrite the timestamp type and the timestamp in the message set.
- The highest bits of attributes must be set to 0.



In message format versions 0 and 1 Kafka supports recursive messages to enable compression. In this case the message's attributes must be set to indicate one of the compression types and the value field will contain a message set compressed with that type. We often refer to the nested messages as "inner messages" and the wrapping message as the "outer message." Note that the key should be null for the outer message and its offset will be the offset of the last inner message.

When receiving recursive version 0 messages, the broker decompresses them and each inner message is assigned an offset individually. In version 1, to avoid server side re-compression, only the wrapper message will be assigned an offset. The inner messages will have relative offsets. The absolute offset can be computed using the offset from the outer message, which corresponds to the offset assigned to the last inner message.

The crc field contains the CRC32 (and not CRC-32C) of the subsequent message bytes (i.e. from magic byte to the value).

### [5.4 Log](http://kafka.apache.org/documentation/#log)

A log for a topic named "my_topic" with two partitions consists of two directories (namely `my_topic_0` and `my_topic_1`) populated with data files containing the messages for that topic. The format of the log files is a sequence of "log entries""; each log entry is a 4 byte integer *N* storing the message length which is followed by the *N* message bytes. Each message is uniquely identified by a 64-bit integer *offset* giving the byte position of the start of this message in the stream of all messages ever sent to that topic on that partition. The on-disk format of each message is given below. Each log file is named with the offset of the first message it contains. So the first file created will be 00000000000.kafka, and each additional file will have an integer name roughly *S* bytes from the previous file where *S* is the max log file size given in the configuration.

The exact binary format for records is versioned and maintained as a standard interface so record batches can be transferred between producer, broker, and client without recopying or conversion when desirable. The previous section included details about the on-disk format of records.



The use of the message offset as the message id is unusual. Our original idea was to use a GUID generated by the producer, and maintain a mapping from GUID to offset on each broker. But since a consumer must maintain an ID for each server, the global uniqueness of the GUID provides no value. Furthermore, the complexity of maintaining the mapping from a random id to an offset requires a heavy weight index structure which must be synchronized with disk, essentially requiring a full persistent random-access data structure. Thus to simplify the lookup structure we decided to use a simple per-partition atomic counter which could be coupled with the partition id and node id to uniquely identify a message; this makes the lookup structure simpler, though multiple seeks per consumer request are still likely. However once we settled on a counter, the jump to directly using the offset seemed natural—both after all are monotonically increasing integers unique to a partition. Since the offset is hidden from the consumer API this decision is ultimately an implementation detail and we went with the more efficient approach.

![img](http://kafka.apache.org/26/images/kafka_log.png)

#### [Writes](http://kafka.apache.org/documentation/#impl_writes)

The log allows serial appends which always go to the last file. This file is rolled over to a fresh file when it reaches a configurable size (say 1GB). The log takes two configuration parameters: *M*, which gives the number of messages to write before forcing the OS to flush the file to disk, and *S*, which gives a number of seconds after which a flush is forced. This gives a durability guarantee of losing at most *M* messages or *S* seconds of data in the event of a system crash.

#### [Reads](http://kafka.apache.org/documentation/#impl_reads)

Reads are done by giving the 64-bit logical offset of a message and an *S*-byte max chunk size. This will return an iterator over the messages contained in the *S*-byte buffer. *S* is intended to be larger than any single message, but in the event of an abnormally large message, the read can be retried multiple times, each time doubling the buffer size, until the message is read successfully. A maximum message and buffer size can be specified to make the server reject messages larger than some size, and to give a bound to the client on the maximum it needs to ever read to get a complete message. It is likely that the read buffer ends with a partial message, this is easily detected by the size delimiting.

The actual process of reading from an offset requires first locating the log segment file in which the data is stored, calculating the file-specific offset from the global offset value, and then reading from that file offset. The search is done as a simple binary search variation against an in-memory range maintained for each file.

The log provides the capability of getting the most recently written message to allow clients to start subscribing as of "right now". This is also useful in the case the consumer fails to consume its data within its SLA-specified number of days. In this case when the client attempts to consume a non-existent offset it is given an OutOfRangeException and can either reset itself or fail as appropriate to the use case.

The following is the format of the results sent to the consumer.

```text
    MessageSetSend (fetch result)

    total length     : 4 bytes
    error code       : 2 bytes
    message 1        : x bytes
    ...
    message n        : x bytes
    MultiMessageSetSend (multiFetch result)

    total length       : 4 bytes
    error code         : 2 bytes
    messageSetSend 1
    ...
    messageSetSend n
```

#### [Deletes](http://kafka.apache.org/documentation/#impl_deletes)

Data is deleted one log segment at a time. The log manager applies two metrics to identify segments which are eligible for deletion: time and size. For time-based policies, the record timestamps are considered, with the largest timestamp in a segment file (order of records is not relevant) defining the retention time for the entire segment. Size-based retention is disabled by default. When enabled the log manager keeps deleting the oldest segment file until the overall size of the partition is within the configured limit again. If both policies are enabled at the same time, a segment that is eligible for deletion due to either policy will be deleted. To avoid locking reads while still allowing deletes that modify the segment list we use a copy-on-write style segment list implementation that provides consistent views to allow a binary search to proceed on an immutable static snapshot view of the log segments while deletes are progressing.

#### [Guarantees](http://kafka.apache.org/documentation/#impl_guarantees)

The log provides a configuration parameter *M* which controls the maximum number of messages that are written before forcing a flush to disk. On startup a log recovery process is run that iterates over all messages in the newest log segment and verifies that each message entry is valid. A message entry is valid if the sum of its size and offset are less than the length of the file AND the CRC32 of the message payload matches the CRC stored with the message. In the event corruption is detected the log is truncated to the last valid offset.

Note that two kinds of corruption must be handled: truncation in which an unwritten block is lost due to a crash, and corruption in which a nonsense block is ADDED to the file. The reason for this is that in general the OS makes no guarantee of the write order between the file inode and the actual block data so in addition to losing written data the file can gain nonsense data if the inode is updated with a new size but a crash occurs before the block containing that data is written. The CRC detects this corner case, and prevents it from corrupting the log (though the unwritten messages are, of course, lost).

### [5.5 Distribution](http://kafka.apache.org/documentation/#distributionimpl)

#### [Consumer Offset Tracking](http://kafka.apache.org/documentation/#impl_offsettracking)

Kafka consumer tracks the maximum offset it has consumed in each partition and has the capability to commit offsets so that it can resume from those offsets in the event of a restart. Kafka provides the option to store all the offsets for a given consumer group in a designated broker (for that group) called the group coordinator. i.e., any consumer instance in that consumer group should send its offset commits and fetches to that group coordinator (broker). Consumer groups are assigned to coordinators based on their group names. A consumer can look up its coordinator by issuing a FindCoordinatorRequest to any Kafka broker and reading the FindCoordinatorResponse which will contain the coordinator details. The consumer can then proceed to commit or fetch offsets from the coordinator broker. In case the coordinator moves, the consumer will need to rediscover the coordinator. Offset commits can be done automatically or manually by consumer instance.

When the group coordinator receives an OffsetCommitRequest, it appends the request to a special [compacted](http://kafka.apache.org/documentation/#compaction) Kafka topic named *__consumer_offsets*. The broker sends a successful offset commit response to the consumer only after all the replicas of the offsets topic receive the offsets. In case the offsets fail to replicate within a configurable timeout, the offset commit will fail and the consumer may retry the commit after backing off. The brokers periodically compact the offsets topic since it only needs to maintain the most recent offset commit per partition. The coordinator also caches the offsets in an in-memory table in order to serve offset fetches quickly.

When the coordinator receives an offset fetch request, it simply returns the last committed offset vector from the offsets cache. In case coordinator was just started or if it just became the coordinator for a new set of consumer groups (by becoming a leader for a partition of the offsets topic), it may need to load the offsets topic partition into the cache. In this case, the offset fetch will fail with an CoordinatorLoadInProgressException and the consumer may retry the OffsetFetchRequest after backing off.

#### [ZooKeeper Directories](http://kafka.apache.org/documentation/#impl_zookeeper)

The following gives the ZooKeeper structures and algorithms used for co-ordination between consumers and brokers.

#### [Notation](http://kafka.apache.org/documentation/#impl_zknotation)

When an element in a path is denoted `[xyz]`, that means that the value of xyz is not fixed and there is in fact a ZooKeeper znode for each possible value of xyz. For example `/topics/[topic]` would be a directory named /topics containing a sub-directory for each topic name. Numerical ranges are also given such as `[0...5]` to indicate the subdirectories 0, 1, 2, 3, 4. An arrow `->` is used to indicate the contents of a znode. For example `/hello -> world` would indicate a znode /hello containing the value "world".

#### [Broker Node Registry](http://kafka.apache.org/documentation/#impl_zkbroker)

```json
    /brokers/ids/[0...N] --> {"jmx_port":...,"timestamp":...,"endpoints":[...],"host":...,"version":...,"port":...} (ephemeral node)
```

This is a list of all present broker nodes, each of which provides a unique logical broker id which identifies it to consumers (which must be given as part of its configuration). On startup, a broker node registers itself by creating a znode with the logical broker id under /brokers/ids. The purpose of the logical broker id is to allow a broker to be moved to a different physical machine without affecting consumers. An attempt to register a broker id that is already in use (say because two servers are configured with the same broker id) results in an error.

Since the broker registers itself in ZooKeeper using ephemeral znodes, this registration is dynamic and will disappear if the broker is shutdown or dies (thus notifying consumers it is no longer available).

#### [Broker Topic Registry](http://kafka.apache.org/documentation/#impl_zktopic)

```json
    /brokers/topics/[topic]/partitions/[0...N]/state --> {"controller_epoch":...,"leader":...,"version":...,"leader_epoch":...,"isr":[...]} (ephemeral node)
```

Each broker registers itself under the topics it maintains and stores the number of partitions for that topic.

#### [Cluster Id](http://kafka.apache.org/documentation/#impl_clusterid)

The cluster id is a unique and immutable identifier assigned to a Kafka cluster. The cluster id can have a maximum of 22 characters and the allowed characters are defined by the regular expression [a-zA-Z0-9_\-]+, which corresponds to the characters used by the URL-safe Base64 variant with no padding. Conceptually, it is auto-generated when a cluster is started for the first time.

Implementation-wise, it is generated when a broker with version 0.10.1 or later is successfully started for the first time. The broker tries to get the cluster id from the `/cluster/id` znode during startup. If the znode does not exist, the broker generates a new cluster id and creates the znode with this cluster id.

#### [Broker node registration](http://kafka.apache.org/documentation/#impl_brokerregistration)

The broker nodes are basically independent, so they only publish information about what they have. When a broker joins, it registers itself under the broker node registry directory and writes information about its host name and port. The broker also register the list of existing topics and their logical partitions in the broker topic registry. New topics are registered dynamically when they are created on the broker.

## [6. OPERATIONS](http://kafka.apache.org/documentation/#operations)

操作

Here is some information on actually running Kafka as a production system based on usage and experience at LinkedIn. Please send us any additional tips you know of.

下面是一些关于实际将Kafka作为生产系统运行的信息，这些信息基于LinkedIn的使用情况和经验。请将你所知道的任何其他提示发送给我们。

### [6.1 Basic Kafka Operations](http://kafka.apache.org/documentation/#basic_ops)

This section will review the most common operations you will perform on your Kafka cluster. All of the tools reviewed in this section are available under the `bin/` directory of the Kafka distribution and each tool will print details on all possible commandline options if it is run with no arguments.

本节将回顾在Kafka集群上执行的最常见操作。本节中介绍的所有工具都可以在Kafka发行版的“bin/”目录下使用，如果不带参数运行，每个工具都将打印所有可能命令行选项的详细信息。

#### [Adding and removing topics](http://kafka.apache.org/documentation/#basic_ops_add_topic)

You have the option of either adding topics manually or having them be created automatically when data is first published to a non-existent topic. If topics are auto-created then you may want to tune the default [topic configurations](http://kafka.apache.org/documentation/#topicconfigs) used for auto-created topics.

Topics are added and modified using the topic tool:

```bash
  > bin/kafka-topics.sh --bootstrap-server broker_host:port --create --topic my_topic_name \ --partitions 20 --replication-factor 3 --config x=y
```

The replication factor controls how many servers will replicate each message that is written. If you have a replication factor of 3 then up to 2 servers can fail before you will lose access to your data. We recommend you use a replication factor of 2 or 3 so that you can transparently bounce machines without interrupting data consumption.

The partition count controls how many logs the topic will be sharded into. There are several impacts of the partition count. First each partition must fit entirely on a single server. So if you have 20 partitions the full data set (and read and write load) will be handled by no more than 20 servers (not counting replicas). Finally the partition count impacts the maximum parallelism of your consumers. This is discussed in greater detail in the [concepts section](http://kafka.apache.org/documentation/#intro_consumers).

Each sharded partition log is placed into its own folder under the Kafka log directory. The name of such folders consists of the topic name, appended by a dash (-) and the partition id. Since a typical folder name can not be over 255 characters long, there will be a limitation on the length of topic names. We assume the number of partitions will not ever be above 100,000. Therefore, topic names cannot be longer than 249 characters. This leaves just enough room in the folder name for a dash and a potentially 5 digit long partition id.

The configurations added on the command line override the default settings the server has for things like the length of time data should be retained. The complete set of per-topic configurations is documented [here](http://kafka.apache.org/documentation/#topicconfigs).

#### [Modifying topics](http://kafka.apache.org/documentation/#basic_ops_modify_topic)

You can change the configuration or partitioning of a topic using the same topic tool.

To add partitions you can do

```bash
  > bin/kafka-topics.sh --bootstrap-server broker_host:port --alter --topic my_topic_name \
        --partitions 40
```

Be aware that one use case for partitions is to semantically partition data, and adding partitions doesn't change the partitioning of existing data so this may disturb consumers if they rely on that partition. That is if data is partitioned by `hash(key) % number_of_partitions` then this partitioning will potentially be shuffled by adding partitions but Kafka will not attempt to automatically redistribute data in any way.

To add configs:

```bash
  > bin/kafka-configs.sh --bootstrap-server broker_host:port --entity-type topics --entity-name my_topic_name --alter --add-config x=y
```

To remove a config:

```bash
  > bin/kafka-configs.sh --bootstrap-server broker_host:port --entity-type topics --entity-name my_topic_name --alter --delete-config x
```

And finally deleting a topic:

```bash
  > bin/kafka-topics.sh --bootstrap-server broker_host:port --delete --topic my_topic_name
```

Kafka does not currently support reducing the number of partitions for a topic.

Instructions for changing the replication factor of a topic can be found [here](http://kafka.apache.org/documentation/#basic_ops_increase_replication_factor).

#### [Graceful shutdown](http://kafka.apache.org/documentation/#basic_ops_restarting)

The Kafka cluster will automatically detect any broker shutdown or failure and elect new leaders for the partitions on that machine. This will occur whether a server fails or it is brought down intentionally for maintenance or configuration changes. For the latter cases Kafka supports a more graceful mechanism for stopping a server than just killing it. When a server is stopped gracefully it has two optimizations it will take advantage of:

1. It will sync all its logs to disk to avoid needing to do any log recovery when it restarts (i.e. validating the checksum for all messages in the tail of the log). Log recovery takes time so this speeds up intentional restarts.
2. It will migrate any partitions the server is the leader for to other replicas prior to shutting down. This will make the leadership transfer faster and minimize the time each partition is unavailable to a few milliseconds.

Syncing the logs will happen automatically whenever the server is stopped other than by a hard kill, but the controlled leadership migration requires using a special setting:

```text
      controlled.shutdown.enable=true
```

Note that controlled shutdown will only succeed if *all* the partitions hosted on the broker have replicas (i.e. the replication factor is greater than 1 *and* at least one of these replicas is alive). This is generally what you want since shutting down the last replica would make that topic partition unavailable.

#### [Balancing leadership](http://kafka.apache.org/documentation/#basic_ops_leader_balancing)

Whenever a broker stops or crashes, leadership for that broker's partitions transfers to other replicas. When the broker is restarted it will only be a follower for all its partitions, meaning it will not be used for client reads and writes.

To avoid this imbalance, Kafka has a notion of preferred replicas. If the list of replicas for a partition is 1,5,9 then node 1 is preferred as the leader to either node 5 or 9 because it is earlier in the replica list. By default the Kafka cluster will try to restore leadership to the restored replicas. This behaviour is configured with:

```text
      auto.leader.rebalance.enable=true
```

You can also set this to false, but you will then need to manually restore leadership to the restored replicas by running the command:

```bash
  > bin/kafka-preferred-replica-election.sh --bootstrap-server broker_host:port
```

#### [Balancing Replicas Across Racks](http://kafka.apache.org/documentation/#basic_ops_racks)

The rack awareness feature spreads replicas of the same partition across different racks. This extends the guarantees Kafka provides for broker-failure to cover rack-failure, limiting the risk of data loss should all the brokers on a rack fail at once. The feature can also be applied to other broker groupings such as availability zones in EC2.



You can specify that a broker belongs to a particular rack by adding a property to the broker config:

```text
   broker.rack=my-rack-id
```

When a topic is [created](http://kafka.apache.org/documentation/#basic_ops_add_topic), [modified](http://kafka.apache.org/documentation/#basic_ops_modify_topic) or replicas are [redistributed](http://kafka.apache.org/documentation/#basic_ops_cluster_expansion), the rack constraint will be honoured, ensuring replicas span as many racks as they can (a partition will span min(#racks, replication-factor) different racks).



The algorithm used to assign replicas to brokers ensures that the number of leaders per broker will be constant, regardless of how brokers are distributed across racks. This ensures balanced throughput.



However if racks are assigned different numbers of brokers, the assignment of replicas will not be even. Racks with fewer brokers will get more replicas, meaning they will use more storage and put more resources into replication. Hence it is sensible to configure an equal number of brokers per rack.

#### [Mirroring data between clusters](http://kafka.apache.org/documentation/#basic_ops_mirror_maker)

We refer to the process of replicating data *between* Kafka clusters "mirroring" to avoid confusion with the replication that happens amongst the nodes in a single cluster. Kafka comes with a tool for mirroring data between Kafka clusters. The tool consumes from a source cluster and produces to a destination cluster. A common use case for this kind of mirroring is to provide a replica in another datacenter. This scenario will be discussed in more detail in the next section.

You can run many such mirroring processes to increase throughput and for fault-tolerance (if one process dies, the others will take overs the additional load).

Data will be read from topics in the source cluster and written to a topic with the same name in the destination cluster. In fact the mirror maker is little more than a Kafka consumer and producer hooked together.

The source and destination clusters are completely independent entities: they can have different numbers of partitions and the offsets will not be the same. For this reason the mirror cluster is not really intended as a fault-tolerance mechanism (as the consumer position will be different); for that we recommend using normal in-cluster replication. The mirror maker process will, however, retain and use the message key for partitioning so order is preserved on a per-key basis.

Here is an example showing how to mirror a single topic (named *my-topic*) from an input cluster:

```bash
  > bin/kafka-mirror-maker.sh
        --consumer.config consumer.properties
        --producer.config producer.properties --whitelist my-topic
```

Note that we specify the list of topics with the `--whitelist` option. This option allows any regular expression using [Java-style regular expressions](http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html). So you could mirror two topics named *A* and *B* using `--whitelist 'A|B'`. Or you could mirror *all* topics using `--whitelist '*'`. Make sure to quote any regular expression to ensure the shell doesn't try to expand it as a file path. For convenience we allow the use of ',' instead of '|' to specify a list of topics. Combining mirroring with the configuration `auto.create.topics.enable=true` makes it possible to have a replica cluster that will automatically create and replicate all data in a source cluster even as new topics are added.

#### [Checking consumer position](http://kafka.apache.org/documentation/#basic_ops_consumer_lag)

Sometimes it's useful to see the position of your consumers. We have a tool that will show the position of all consumers in a consumer group as well as how far behind the end of the log they are. To run this tool on a consumer group named *my-group* consuming a topic named *my-topic* would look like this:

```bash
  > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group

  TOPIC                          PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG        CONSUMER-ID                                       HOST                           CLIENT-ID
  my-topic                       0          2               4               2          consumer-1-029af89c-873c-4751-a720-cefd41a669d6   /127.0.0.1                     consumer-1
  my-topic                       1          2               3               1          consumer-1-029af89c-873c-4751-a720-cefd41a669d6   /127.0.0.1                     consumer-1
  my-topic                       2          2               3               1          consumer-2-42c1abd4-e3b2-425d-a8bb-e1ea49b29bb2   /127.0.0.1                     consumer-2
```

#### [Managing Consumer Groups](http://kafka.apache.org/documentation/#basic_ops_consumer_group)

With the ConsumerGroupCommand tool, we can list, describe, or delete the consumer groups. The consumer group can be deleted manually, or automatically when the last committed offset for that group expires. Manual deletion works only if the group does not have any active members. For example, to list all consumer groups across all topics:

```bash
  > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

  test-consumer-group
```

To view offsets, as mentioned earlier, we "describe" the consumer group like this:

```bash
  > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group

  TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                    HOST            CLIENT-ID
  topic3          0          241019          395308          154289          consumer2-e76ea8c3-5d30-4299-9005-47eb41f3d3c4 /127.0.0.1      consumer2
  topic2          1          520678          803288          282610          consumer2-e76ea8c3-5d30-4299-9005-47eb41f3d3c4 /127.0.0.1      consumer2
  topic3          1          241018          398817          157799          consumer2-e76ea8c3-5d30-4299-9005-47eb41f3d3c4 /127.0.0.1      consumer2
  topic1          0          854144          855809          1665            consumer1-3fc8d6f1-581a-4472-bdf3-3515b4aee8c1 /127.0.0.1      consumer1
  topic2          0          460537          803290          342753          consumer1-3fc8d6f1-581a-4472-bdf3-3515b4aee8c1 /127.0.0.1      consumer1
  topic3          2          243655          398812          155157          consumer4-117fe4d3-c6c1-4178-8ee9-eb4a3954bee0 /127.0.0.1      consumer4
```

There are a number of additional "describe" options that can be used to provide more detailed information about a consumer group:

- --members: This option provides the list of all active members in the consumer group.

  ```bash
        > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group --members
  
        CONSUMER-ID                                    HOST            CLIENT-ID       #PARTITIONS
        consumer1-3fc8d6f1-581a-4472-bdf3-3515b4aee8c1 /127.0.0.1      consumer1       2
        consumer4-117fe4d3-c6c1-4178-8ee9-eb4a3954bee0 /127.0.0.1      consumer4       1
        consumer2-e76ea8c3-5d30-4299-9005-47eb41f3d3c4 /127.0.0.1      consumer2       3
        consumer3-ecea43e4-1f01-479f-8349-f9130b75d8ee /127.0.0.1      consumer3       0
  ```

- --members --verbose: On top of the information reported by the "--members" options above, this option also provides the partitions assigned to each member.

  ```bash
        > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group --members --verbose
  
        CONSUMER-ID                                    HOST            CLIENT-ID       #PARTITIONS     ASSIGNMENT
        consumer1-3fc8d6f1-581a-4472-bdf3-3515b4aee8c1 /127.0.0.1      consumer1       2               topic1(0), topic2(0)
        consumer4-117fe4d3-c6c1-4178-8ee9-eb4a3954bee0 /127.0.0.1      consumer4       1               topic3(2)
        consumer2-e76ea8c3-5d30-4299-9005-47eb41f3d3c4 /127.0.0.1      consumer2       3               topic2(1), topic3(0,1)
        consumer3-ecea43e4-1f01-479f-8349-f9130b75d8ee /127.0.0.1      consumer3       0               -
  ```

- --offsets: This is the default describe option and provides the same output as the "--describe" option.

- --state: This option provides useful group-level information.

  ```bash
        > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group --state
  
        COORDINATOR (ID)          ASSIGNMENT-STRATEGY       STATE                #MEMBERS
        localhost:9092 (0)        range                     Stable               4
  ```

To manually delete one or multiple consumer groups, the "--delete" option can be used:

```bash
  > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --delete --group my-group --group my-other-group

  Deletion of requested consumer groups ('my-group', 'my-other-group') was successful.
```

To reset offsets of a consumer group, "--reset-offsets" option can be used. This option supports one consumer group at the time. It requires defining following scopes: --all-topics or --topic. One scope must be selected, unless you use '--from-file' scenario. Also, first make sure that the consumer instances are inactive. See [KIP-122](https://cwiki.apache.org/confluence/display/KAFKA/KIP-122%3A+Add+Reset+Consumer+Group+Offsets+tooling) for more details.

It has 3 execution options:

- (default) to display which offsets to reset.
- --execute : to execute --reset-offsets process.
- --export : to export the results to a CSV format.

--reset-offsets also has following scenarios to choose from (at least one scenario must be selected):

- --to-datetime <String: datetime> : Reset offsets to offsets from datetime. Format: 'YYYY-MM-DDTHH:mm:SS.sss'
- --to-earliest : Reset offsets to earliest offset.
- --to-latest : Reset offsets to latest offset.
- --shift-by <Long: number-of-offsets> : Reset offsets shifting current offset by 'n', where 'n' can be positive or negative.
- --from-file : Reset offsets to values defined in CSV file.
- --to-current : Resets offsets to current offset.
- --by-duration <String: duration> : Reset offsets to offset by duration from current timestamp. Format: 'PnDTnHnMnS'
- --to-offset : Reset offsets to a specific offset.

Please note, that out of range offsets will be adjusted to available offset end. For example, if offset end is at 10 and offset shift request is of 15, then, offset at 10 will actually be selected.

For example, to reset offsets of a consumer group to the latest offset:

```bash
  > bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --group consumergroup1 --topic topic1 --to-latest

  TOPIC                          PARTITION  NEW-OFFSET
  topic1                         0          0
```

If you are using the old high-level consumer and storing the group metadata in ZooKeeper (i.e. `offsets.storage=zookeeper`), pass `--zookeeper` instead of `--bootstrap-server`:

```bash
  > bin/kafka-consumer-groups.sh --zookeeper localhost:2181 --list
```

#### [Expanding your cluster](http://kafka.apache.org/documentation/#basic_ops_cluster_expansion)

Adding servers to a Kafka cluster is easy, just assign them a unique broker id and start up Kafka on your new servers. However these new servers will not automatically be assigned any data partitions, so unless partitions are moved to them they won't be doing any work until new topics are created. So usually when you add machines to your cluster you will want to migrate some existing data to these machines.

The process of migrating data is manually initiated but fully automated. Under the covers what happens is that Kafka will add the new server as a follower of the partition it is migrating and allow it to fully replicate the existing data in that partition. When the new server has fully replicated the contents of this partition and joined the in-sync replica one of the existing replicas will delete their partition's data.

The partition reassignment tool can be used to move partitions across brokers. An ideal partition distribution would ensure even data load and partition sizes across all brokers. The partition reassignment tool does not have the capability to automatically study the data distribution in a Kafka cluster and move partitions around to attain an even load distribution. As such, the admin has to figure out which topics or partitions should be moved around.

The partition reassignment tool can run in 3 mutually exclusive modes:

- --generate: In this mode, given a list of topics and a list of brokers, the tool generates a candidate reassignment to move all partitions of the specified topics to the new brokers. This option merely provides a convenient way to generate a partition reassignment plan given a list of topics and target brokers.
- --execute: In this mode, the tool kicks off the reassignment of partitions based on the user provided reassignment plan. (using the --reassignment-json-file option). This can either be a custom reassignment plan hand crafted by the admin or provided by using the --generate option
- --verify: In this mode, the tool verifies the status of the reassignment for all partitions listed during the last --execute. The status can be either of successfully completed, failed or in progress

##### [Automatically migrating data to new machines](http://kafka.apache.org/documentation/#basic_ops_automigrate)

The partition reassignment tool can be used to move some topics off of the current set of brokers to the newly added brokers. This is typically useful while expanding an existing cluster since it is easier to move entire topics to the new set of brokers, than moving one partition at a time. When used to do this, the user should provide a list of topics that should be moved to the new set of brokers and a target list of new brokers. The tool then evenly distributes all partitions for the given list of topics across the new set of brokers. During this move, the replication factor of the topic is kept constant. Effectively the replicas for all partitions for the input list of topics are moved from the old set of brokers to the newly added brokers.

For instance, the following example will move all partitions for topics foo1,foo2 to the new set of brokers 5,6. At the end of this move, all partitions for topics foo1 and foo2 will *only* exist on brokers 5,6.

Since the tool accepts the input list of topics as a json file, you first need to identify the topics you want to move and create the json file as follows:

```bash
  > cat topics-to-move.json
  {"topics": [{"topic": "foo1"},
              {"topic": "foo2"}],
  "version":1
  }
```

Once the json file is ready, use the partition reassignment tool to generate a candidate assignment:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --topics-to-move-json-file topics-to-move.json --broker-list "5,6" --generate
  Current partition replica assignment

  {"version":1,
  "partitions":[{"topic":"foo1","partition":2,"replicas":[1,2]},
                {"topic":"foo1","partition":0,"replicas":[3,4]},
                {"topic":"foo2","partition":2,"replicas":[1,2]},
                {"topic":"foo2","partition":0,"replicas":[3,4]},
                {"topic":"foo1","partition":1,"replicas":[2,3]},
                {"topic":"foo2","partition":1,"replicas":[2,3]}]
  }

  Proposed partition reassignment configuration

  {"version":1,
  "partitions":[{"topic":"foo1","partition":2,"replicas":[5,6]},
                {"topic":"foo1","partition":0,"replicas":[5,6]},
                {"topic":"foo2","partition":2,"replicas":[5,6]},
                {"topic":"foo2","partition":0,"replicas":[5,6]},
                {"topic":"foo1","partition":1,"replicas":[5,6]},
                {"topic":"foo2","partition":1,"replicas":[5,6]}]
  }
```

The tool generates a candidate assignment that will move all partitions from topics foo1,foo2 to brokers 5,6. Note, however, that at this point, the partition movement has not started, it merely tells you the current assignment and the proposed new assignment. The current assignment should be saved in case you want to rollback to it. The new assignment should be saved in a json file (e.g. expand-cluster-reassignment.json) to be input to the tool with the --execute option as follows:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file expand-cluster-reassignment.json --execute
  Current partition replica assignment

  {"version":1,
  "partitions":[{"topic":"foo1","partition":2,"replicas":[1,2]},
                {"topic":"foo1","partition":0,"replicas":[3,4]},
                {"topic":"foo2","partition":2,"replicas":[1,2]},
                {"topic":"foo2","partition":0,"replicas":[3,4]},
                {"topic":"foo1","partition":1,"replicas":[2,3]},
                {"topic":"foo2","partition":1,"replicas":[2,3]}]
  }

  Save this to use as the --reassignment-json-file option during rollback
  Successfully started reassignment of partitions
  {"version":1,
  "partitions":[{"topic":"foo1","partition":2,"replicas":[5,6]},
                {"topic":"foo1","partition":0,"replicas":[5,6]},
                {"topic":"foo2","partition":2,"replicas":[5,6]},
                {"topic":"foo2","partition":0,"replicas":[5,6]},
                {"topic":"foo1","partition":1,"replicas":[5,6]},
                {"topic":"foo2","partition":1,"replicas":[5,6]}]
  }
```

Finally, the --verify option can be used with the tool to check the status of the partition reassignment. Note that the same expand-cluster-reassignment.json (used with the --execute option) should be used with the --verify option:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file expand-cluster-reassignment.json --verify
  Status of partition reassignment:
  Reassignment of partition [foo1,0] completed successfully
  Reassignment of partition [foo1,1] is in progress
  Reassignment of partition [foo1,2] is in progress
  Reassignment of partition [foo2,0] completed successfully
  Reassignment of partition [foo2,1] completed successfully
  Reassignment of partition [foo2,2] completed successfully
```

##### [Custom partition assignment and migration](http://kafka.apache.org/documentation/#basic_ops_partitionassignment)

The partition reassignment tool can also be used to selectively move replicas of a partition to a specific set of brokers. When used in this manner, it is assumed that the user knows the reassignment plan and does not require the tool to generate a candidate reassignment, effectively skipping the --generate step and moving straight to the --execute step

For instance, the following example moves partition 0 of topic foo1 to brokers 5,6 and partition 1 of topic foo2 to brokers 2,3:

The first step is to hand craft the custom reassignment plan in a json file:

```bash
  > cat custom-reassignment.json
  {"version":1,"partitions":[{"topic":"foo1","partition":0,"replicas":[5,6]},{"topic":"foo2","partition":1,"replicas":[2,3]}]}
```

Then, use the json file with the --execute option to start the reassignment process:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file custom-reassignment.json --execute
  Current partition replica assignment

  {"version":1,
  "partitions":[{"topic":"foo1","partition":0,"replicas":[1,2]},
                {"topic":"foo2","partition":1,"replicas":[3,4]}]
  }

  Save this to use as the --reassignment-json-file option during rollback
  Successfully started reassignment of partitions
  {"version":1,
  "partitions":[{"topic":"foo1","partition":0,"replicas":[5,6]},
                {"topic":"foo2","partition":1,"replicas":[2,3]}]
  }
```

The --verify option can be used with the tool to check the status of the partition reassignment. Note that the same custom-reassignment.json (used with the --execute option) should be used with the --verify option:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file custom-reassignment.json --verify
  Status of partition reassignment:
  Reassignment of partition [foo1,0] completed successfully
  Reassignment of partition [foo2,1] completed successfully
```

#### [Decommissioning brokers](http://kafka.apache.org/documentation/#basic_ops_decommissioning_brokers)

The partition reassignment tool does not have the ability to automatically generate a reassignment plan for decommissioning brokers yet. As such, the admin has to come up with a reassignment plan to move the replica for all partitions hosted on the broker to be decommissioned, to the rest of the brokers. This can be relatively tedious as the reassignment needs to ensure that all the replicas are not moved from the decommissioned broker to only one other broker. To make this process effortless, we plan to add tooling support for decommissioning brokers in the future.

#### [Increasing replication factor](http://kafka.apache.org/documentation/#basic_ops_increase_replication_factor)

Increasing the replication factor of an existing partition is easy. Just specify the extra replicas in the custom reassignment json file and use it with the --execute option to increase the replication factor of the specified partitions.

For instance, the following example increases the replication factor of partition 0 of topic foo from 1 to 3. Before increasing the replication factor, the partition's only replica existed on broker 5. As part of increasing the replication factor, we will add more replicas on brokers 6 and 7.

The first step is to hand craft the custom reassignment plan in a json file:

```bash
  > cat increase-replication-factor.json
  {"version":1,
  "partitions":[{"topic":"foo","partition":0,"replicas":[5,6,7]}]}
```

Then, use the json file with the --execute option to start the reassignment process:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file increase-replication-factor.json --execute
  Current partition replica assignment

  {"version":1,
  "partitions":[{"topic":"foo","partition":0,"replicas":[5]}]}

  Save this to use as the --reassignment-json-file option during rollback
  Successfully started reassignment of partitions
  {"version":1,
  "partitions":[{"topic":"foo","partition":0,"replicas":[5,6,7]}]}
```

The --verify option can be used with the tool to check the status of the partition reassignment. Note that the same increase-replication-factor.json (used with the --execute option) should be used with the --verify option:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --reassignment-json-file increase-replication-factor.json --verify
  Status of partition reassignment:
  Reassignment of partition [foo,0] completed successfully
```

You can also verify the increase in replication factor with the kafka-topics tool:

```bash
  > bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic foo --describe
  Topic:foo	PartitionCount:1	ReplicationFactor:3	Configs:
    Topic: foo	Partition: 0	Leader: 5	Replicas: 5,6,7	Isr: 5,6,7
```

#### [Limiting Bandwidth Usage during Data Migration](http://kafka.apache.org/documentation/#rep-throttle)

Kafka lets you apply a throttle to replication traffic, setting an upper bound on the bandwidth used to move replicas from machine to machine. This is useful when rebalancing a cluster, bootstrapping a new broker or adding or removing brokers, as it limits the impact these data-intensive operations will have on users.



There are two interfaces that can be used to engage a throttle. The simplest, and safest, is to apply a throttle when invoking the kafka-reassign-partitions.sh, but kafka-configs.sh can also be used to view and alter the throttle values directly.



So for example, if you were to execute a rebalance, with the below command, it would move partitions at no more than 50MB/s.

```bash
$ bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --execute --reassignment-json-file bigger-cluster.json --throttle 50000000
```

When you execute this script you will see the throttle engage:

```bash
  The throttle limit was set to 50000000 B/s
  Successfully started reassignment of partitions.
```

Should you wish to alter the throttle, during a rebalance, say to increase the throughput so it completes quicker, you can do this by re-running the execute command passing the same reassignment-json-file:

```bash
$ bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092  --execute --reassignment-json-file bigger-cluster.json --throttle 700000000
  There is an existing assignment running.
  The throttle limit was set to 700000000 B/s
```

Once the rebalance completes the administrator can check the status of the rebalance using the --verify option. If the rebalance has completed, the throttle will be removed via the --verify command. It is important that administrators remove the throttle in a timely manner once rebalancing completes by running the command with the --verify option. Failure to do so could cause regular replication traffic to be throttled.

When the --verify option is executed, and the reassignment has completed, the script will confirm that the throttle was removed:

```bash
  > bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092  --verify --reassignment-json-file bigger-cluster.json
  Status of partition reassignment:
  Reassignment of partition [my-topic,1] completed successfully
  Reassignment of partition [mytopic,0] completed successfully
  Throttle was removed.
```

The administrator can also validate the assigned configs using the kafka-configs.sh. There are two pairs of throttle configuration used to manage the throttling process. First pair refers to the throttle value itself. This is configured, at a broker level, using the dynamic properties:

```text
    leader.replication.throttled.rate
    follower.replication.throttled.rate
```

Then there is the configuration pair of enumerated sets of throttled replicas:

```text
    leader.replication.throttled.replicas
    follower.replication.throttled.replicas
```

Which are configured per topic.

All four config values are automatically assigned by kafka-reassign-partitions.sh (discussed below).

To view the throttle limit configuration:

```bash
  > bin/kafka-configs.sh --describe --bootstrap-server localhost:9092 --entity-type brokers
  Configs for brokers '2' are leader.replication.throttled.rate=700000000,follower.replication.throttled.rate=700000000
  Configs for brokers '1' are leader.replication.throttled.rate=700000000,follower.replication.throttled.rate=700000000
```

This shows the throttle applied to both leader and follower side of the replication protocol. By default both sides are assigned the same throttled throughput value.

To view the list of throttled replicas:

```bash
  > bin/kafka-configs.sh --describe --bootstrap-server localhost:9092 --entity-type topics
  Configs for topic 'my-topic' are leader.replication.throttled.replicas=1:102,0:101,
      follower.replication.throttled.replicas=1:101,0:102
```

Here we see the leader throttle is applied to partition 1 on broker 102 and partition 0 on broker 101. Likewise the follower throttle is applied to partition 1 on broker 101 and partition 0 on broker 102.

By default kafka-reassign-partitions.sh will apply the leader throttle to all replicas that exist before the rebalance, any one of which might be leader. It will apply the follower throttle to all move destinations. So if there is a partition with replicas on brokers 101,102, being reassigned to 102,103, a leader throttle, for that partition, would be applied to 101,102 and a follower throttle would be applied to 103 only.

If required, you can also use the --alter switch on kafka-configs.sh to alter the throttle configurations manually.

##### Safe usage of throttled replication

Some care should be taken when using throttled replication. In particular:

*(1) Throttle Removal:*

The throttle should be removed in a timely manner once reassignment completes (by running kafka-reassign-partitions.sh --verify).

*(2) Ensuring Progress:*

If the throttle is set too low, in comparison to the incoming write rate, it is possible for replication to not make progress. This occurs when:

```
max(BytesInPerSec) > throttle
```

Where BytesInPerSec is the metric that monitors the write throughput of producers into each broker.

The administrator can monitor whether replication is making progress, during the rebalance, using the metric:

```
kafka.server:type=FetcherLagMetrics,name=ConsumerLag,clientId=([-.\w]+),topic=([-.\w]+),partition=([0-9]+)
```

The lag should constantly decrease during replication. If the metric does not decrease the administrator should increase the throttle throughput as described above.

#### [Setting quotas](http://kafka.apache.org/documentation/#quotas)

Quotas overrides and defaults may be configured at (user, client-id), user or client-id levels as described [here](http://kafka.apache.org/documentation/#design_quotas). By default, clients receive an unlimited quota. It is possible to set custom quotas for each (user, client-id), user or client-id group.

Configure custom quota for (user=user1, client-id=clientA):

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type users --entity-name user1 --entity-type clients --entity-name clientA
  Updated config for entity: user-principal 'user1', client-id 'clientA'.
```

Configure custom quota for user=user1:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type users --entity-name user1
  Updated config for entity: user-principal 'user1'.
```

Configure custom quota for client-id=clientA:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type clients --entity-name clientA
  Updated config for entity: client-id 'clientA'.
```

It is possible to set default quotas for each (user, client-id), user or client-id group by specifying *--entity-default* option instead of *--entity-name*.

Configure default client-id quota for user=userA:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type users --entity-name user1 --entity-type clients --entity-default
  Updated config for entity: user-principal 'user1', default client-id.
```

Configure default quota for user:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type users --entity-default
  Updated config for entity: default user-principal.
```

Configure default quota for client-id:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200' --entity-type clients --entity-default
  Updated config for entity: default client-id.
```

Here's how to describe the quota for a given (user, client-id):

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --describe --entity-type users --entity-name user1 --entity-type clients --entity-name clientA
  Configs for user-principal 'user1', client-id 'clientA' are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
```

Describe quota for a given user:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --describe --entity-type users --entity-name user1
  Configs for user-principal 'user1' are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
```

Describe quota for a given client-id:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --describe --entity-type clients --entity-name clientA
  Configs for client-id 'clientA' are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
```

If entity name is not specified, all entities of the specified type are described. For example, describe all users:

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --describe --entity-type users
  Configs for user-principal 'user1' are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
  Configs for default user-principal are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
```

Similarly for (user, client):

```bash
  > bin/kafka-configs.sh  --bootstrap-server localhost:9092 --describe --entity-type users --entity-type clients
  Configs for user-principal 'user1', default client-id are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
  Configs for user-principal 'user1', client-id 'clientA' are producer_byte_rate=1024,consumer_byte_rate=2048,request_percentage=200
```

It is possible to set default quotas that apply to all client-ids by setting these configs on the brokers. These properties are applied only if quota overrides or defaults are not configured in Zookeeper. By default, each client-id receives an unlimited quota. The following sets the default quota per producer and consumer client-id to 10MB/sec.

```text
    quota.producer.default=10485760
    quota.consumer.default=10485760
```

Note that these properties are being deprecated and may be removed in a future release. Defaults configured using kafka-configs.sh take precedence over these properties.

### [6.2 Datacenters](http://kafka.apache.org/documentation/#datacenters)

Some deployments will need to manage a data pipeline that spans multiple datacenters. Our recommended approach to this is to deploy a local Kafka cluster in each datacenter with application instances in each datacenter interacting only with their local cluster and mirroring between clusters (see the documentation on the [mirror maker tool](http://kafka.apache.org/documentation/#basic_ops_mirror_maker) for how to do this).

This deployment pattern allows datacenters to act as independent entities and allows us to manage and tune inter-datacenter replication centrally. This allows each facility to stand alone and operate even if the inter-datacenter links are unavailable: when this occurs the mirroring falls behind until the link is restored at which time it catches up.

For applications that need a global view of all data you can use mirroring to provide clusters which have aggregate data mirrored from the local clusters in *all* datacenters. These aggregate clusters are used for reads by applications that require the full data set.

This is not the only possible deployment pattern. It is possible to read from or write to a remote Kafka cluster over the WAN, though obviously this will add whatever latency is required to get the cluster.

Kafka naturally batches data in both the producer and consumer so it can achieve high-throughput even over a high-latency connection. To allow this though it may be necessary to increase the TCP socket buffer sizes for the producer, consumer, and broker using the `socket.send.buffer.bytes` and `socket.receive.buffer.bytes` configurations. The appropriate way to set this is documented [here](http://en.wikipedia.org/wiki/Bandwidth-delay_product).

It is generally *not* advisable to run a *single* Kafka cluster that spans multiple datacenters over a high-latency link. This will incur very high replication latency both for Kafka writes and ZooKeeper writes, and neither Kafka nor ZooKeeper will remain available in all locations if the network between locations is unavailable.

### [6.3 Kafka Configuration](http://kafka.apache.org/documentation/#config)

#### [Important Client Configurations](http://kafka.apache.org/documentation/#clientconfig)

The most important producer configurations are:

- acks
- compression
- batch size

The most important consumer configuration is the fetch size.

All configurations are documented in the [configuration](http://kafka.apache.org/documentation/#configuration) section.



#### [A Production Server Config](http://kafka.apache.org/documentation/#prodconfig)

Here is an example production server configuration:

```text
  # ZooKeeper
  zookeeper.connect=[list of ZooKeeper servers]

  # Log configuration
  num.partitions=8
  default.replication.factor=3
  log.dir=[List of directories. Kafka should have its own dedicated disk(s) or SSD(s).]

  # Other configurations
  broker.id=[An integer. Start with 0 and increment by 1 for each new broker.]
  listeners=[list of listeners]
  auto.create.topics.enable=false
  min.insync.replicas=2
  queued.max.requests=[number of concurrent requests]
```

Our client configuration varies a fair amount between different use cases.

### [6.4 Java Version](http://kafka.apache.org/documentation/#java)

Java 8 and Java 11 are supported. Java 11 performs significantly better if TLS is enabled, so it is highly recommended (it also includes a number of other performance improvements: G1GC, CRC32C, Compact Strings, Thread-Local Handshakes and more). From a security perspective, we recommend the latest released patch version as older freely available versions have disclosed security vulnerabilities. Typical arguments for running Kafka with OpenJDK-based Java implementations (including Oracle JDK) are:

```text
  -Xmx6g -Xms6g -XX:MetaspaceSize=96m -XX:+UseG1GC
  -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:G1HeapRegionSize=16M
  -XX:MinMetaspaceFreeRatio=50 -XX:MaxMetaspaceFreeRatio=80 -XX:+ExplicitGCInvokesConcurrent
```

For reference, here are the stats for one of LinkedIn's busiest clusters (at peak) that uses said Java arguments:

- 60 brokers
- 50k partitions (replication factor 2)
- 800k messages/sec in
- 300 MB/sec inbound, 1 GB/sec+ outbound

All of the brokers in that cluster have a 90% GC pause time of about 21ms with less than 1 young GC per second.

### [6.5 Hardware and OS](http://kafka.apache.org/documentation/#hwandos)

We are using dual quad-core Intel Xeon machines with 24GB of memory.

You need sufficient memory to buffer active readers and writers. You can do a back-of-the-envelope estimate of memory needs by assuming you want to be able to buffer for 30 seconds and compute your memory need as write_throughput*30.

The disk throughput is important. We have 8x7200 rpm SATA drives. In general disk throughput is the performance bottleneck, and more disks is better. Depending on how you configure flush behavior you may or may not benefit from more expensive disks (if you force flush often then higher RPM SAS drives may be better).

#### [OS](http://kafka.apache.org/documentation/#os)

Kafka should run well on any unix system and has been tested on Linux and Solaris.

We have seen a few issues running on Windows and Windows is not currently a well supported platform though we would be happy to change that.

It is unlikely to require much OS-level tuning, but there are three potentially important OS-level configurations:

- File descriptor limits: Kafka uses file descriptors for log segments and open connections. If a broker hosts many partitions, consider that the broker needs at least (number_of_partitions)*(partition_size/segment_size) to track all log segments in addition to the number of connections the broker makes. We recommend at least 100000 allowed file descriptors for the broker processes as a starting point. Note: The mmap() function adds an extra reference to the file associated with the file descriptor fildes which is not removed by a subsequent close() on that file descriptor. This reference is removed when there are no more mappings to the file.
- Max socket buffer size: can be increased to enable high-performance data transfer between data centers as [described here](http://www.psc.edu/index.php/networking/641-tcp-tune).
- Maximum number of memory map areas a process may have (aka vm.max_map_count). [See the Linux kernel documentation](http://kernel.org/doc/Documentation/sysctl/vm.txt). You should keep an eye at this OS-level property when considering the maximum number of partitions a broker may have. By default, on a number of Linux systems, the value of vm.max_map_count is somewhere around 65535. Each log segment, allocated per partition, requires a pair of index/timeindex files, and each of these files consumes 1 map area. In other words, each log segment uses 2 map areas. Thus, each partition requires minimum 2 map areas, as long as it hosts a single log segment. That is to say, creating 50000 partitions on a broker will result allocation of 100000 map areas and likely cause broker crash with OutOfMemoryError (Map failed) on a system with default vm.max_map_count. Keep in mind that the number of log segments per partition varies depending on the segment size, load intensity, retention policy and, generally, tends to be more than one.



#### [Disks and Filesystem](http://kafka.apache.org/documentation/#diskandfs)

We recommend using multiple drives to get good throughput and not sharing the same drives used for Kafka data with application logs or other OS filesystem activity to ensure good latency. You can either RAID these drives together into a single volume or format and mount each drive as its own directory. Since Kafka has replication the redundancy provided by RAID can also be provided at the application level. This choice has several tradeoffs.

If you configure multiple data directories partitions will be assigned round-robin to data directories. Each partition will be entirely in one of the data directories. If data is not well balanced among partitions this can lead to load imbalance between disks.

RAID can potentially do better at balancing load between disks (although it doesn't always seem to) because it balances load at a lower level. The primary downside of RAID is that it is usually a big performance hit for write throughput and reduces the available disk space.

Another potential benefit of RAID is the ability to tolerate disk failures. However our experience has been that rebuilding the RAID array is so I/O intensive that it effectively disables the server, so this does not provide much real availability improvement.

#### [Application vs. OS Flush Management](http://kafka.apache.org/documentation/#appvsosflush)

Kafka always immediately writes all data to the filesystem and supports the ability to configure the flush policy that controls when data is forced out of the OS cache and onto disk using the flush. This flush policy can be controlled to force data to disk after a period of time or after a certain number of messages has been written. There are several choices in this configuration.

Kafka must eventually call fsync to know that data was flushed. When recovering from a crash for any log segment not known to be fsync'd Kafka will check the integrity of each message by checking its CRC and also rebuild the accompanying offset index file as part of the recovery process executed on startup.

Note that durability in Kafka does not require syncing data to disk, as a failed node will always recover from its replicas.

We recommend using the default flush settings which disable application fsync entirely. This means relying on the background flush done by the OS and Kafka's own background flush. This provides the best of all worlds for most uses: no knobs to tune, great throughput and latency, and full recovery guarantees. We generally feel that the guarantees provided by replication are stronger than sync to local disk, however the paranoid still may prefer having both and application level fsync policies are still supported.

The drawback of using application level flush settings is that it is less efficient in its disk usage pattern (it gives the OS less leeway to re-order writes) and it can introduce latency as fsync in most Linux filesystems blocks writes to the file whereas the background flushing does much more granular page-level locking.

In general you don't need to do any low-level tuning of the filesystem, but in the next few sections we will go over some of this in case it is useful.

#### [Understanding Linux OS Flush Behavior](http://kafka.apache.org/documentation/#linuxflush)

In Linux, data written to the filesystem is maintained in [pagecache](http://en.wikipedia.org/wiki/Page_cache) until it must be written out to disk (due to an application-level fsync or the OS's own flush policy). The flushing of data is done by a set of background threads called pdflush (or in post 2.6.32 kernels "flusher threads").

Pdflush has a configurable policy that controls how much dirty data can be maintained in cache and for how long before it must be written back to disk. This policy is described [here](http://web.archive.org/web/20160518040713/http://www.westnet.com/~gsmith/content/linux-pdflush.htm). When Pdflush cannot keep up with the rate of data being written it will eventually cause the writing process to block incurring latency in the writes to slow down the accumulation of data.

You can see the current state of OS memory usage by doing

```bash
 > cat /proc/meminfo 
```

The meaning of these values are described in the link above.

Using pagecache has several advantages over an in-process cache for storing data that will be written out to disk:

- The I/O scheduler will batch together consecutive small writes into bigger physical writes which improves throughput.
- The I/O scheduler will attempt to re-sequence writes to minimize movement of the disk head which improves throughput.
- It automatically uses all the free memory on the machine

#### [Filesystem Selection](http://kafka.apache.org/documentation/#filesystems)

Kafka uses regular files on disk, and as such it has no hard dependency on a specific filesystem. The two filesystems which have the most usage, however, are EXT4 and XFS. Historically, EXT4 has had more usage, but recent improvements to the XFS filesystem have shown it to have better performance characteristics for Kafka's workload with no compromise in stability.

Comparison testing was performed on a cluster with significant message loads, using a variety of filesystem creation and mount options. The primary metric in Kafka that was monitored was the "Request Local Time", indicating the amount of time append operations were taking. XFS resulted in much better local times (160ms vs. 250ms+ for the best EXT4 configuration), as well as lower average wait times. The XFS performance also showed less variability in disk performance.

##### [General Filesystem Notes](http://kafka.apache.org/documentation/#generalfs)

For any filesystem used for data directories, on Linux systems, the following options are recommended to be used at mount time:

- noatime: This option disables updating of a file's atime (last access time) attribute when the file is read. This can eliminate a significant number of filesystem writes, especially in the case of bootstrapping consumers. Kafka does not rely on the atime attributes at all, so it is safe to disable this.

##### [XFS Notes](http://kafka.apache.org/documentation/#xfs)

The XFS filesystem has a significant amount of auto-tuning in place, so it does not require any change in the default settings, either at filesystem creation time or at mount. The only tuning parameters worth considering are:

- largeio: This affects the preferred I/O size reported by the stat call. While this can allow for higher performance on larger disk writes, in practice it had minimal or no effect on performance.
- nobarrier: For underlying devices that have battery-backed cache, this option can provide a little more performance by disabling periodic write flushes. However, if the underlying device is well-behaved, it will report to the filesystem that it does not require flushes, and this option will have no effect.

##### [EXT4 Notes](http://kafka.apache.org/documentation/#ext4)

EXT4 is a serviceable choice of filesystem for the Kafka data directories, however getting the most performance out of it will require adjusting several mount options. In addition, these options are generally unsafe in a failure scenario, and will result in much more data loss and corruption. For a single broker failure, this is not much of a concern as the disk can be wiped and the replicas rebuilt from the cluster. In a multiple-failure scenario, such as a power outage, this can mean underlying filesystem (and therefore data) corruption that is not easily recoverable. The following options can be adjusted:

- data=writeback: Ext4 defaults to data=ordered which puts a strong order on some writes. Kafka does not require this ordering as it does very paranoid data recovery on all unflushed log. This setting removes the ordering constraint and seems to significantly reduce latency.
- Disabling journaling: Journaling is a tradeoff: it makes reboots faster after server crashes but it introduces a great deal of additional locking which adds variance to write performance. Those who don't care about reboot time and want to reduce a major source of write latency spikes can turn off journaling entirely.
- commit=num_secs: This tunes the frequency with which ext4 commits to its metadata journal. Setting this to a lower value reduces the loss of unflushed data during a crash. Setting this to a higher value will improve throughput.
- nobh: This setting controls additional ordering guarantees when using data=writeback mode. This should be safe with Kafka as we do not depend on write ordering and improves throughput and latency.
- delalloc: Delayed allocation means that the filesystem avoid allocating any blocks until the physical write occurs. This allows ext4 to allocate a large extent instead of smaller pages and helps ensure the data is written sequentially. This feature is great for throughput. It does seem to involve some locking in the filesystem which adds a bit of latency variance.

### [6.6 Monitoring](http://kafka.apache.org/documentation/#monitoring)

Kafka uses Yammer Metrics for metrics reporting in the server. The Java clients use Kafka Metrics, a built-in metrics registry that minimizes transitive dependencies pulled into client applications. Both expose metrics via JMX and can be configured to report stats using pluggable stats reporters to hook up to your monitoring system.

All Kafka rate metrics have a corresponding cumulative count metric with suffix `-total`. For example, `records-consumed-rate` has a corresponding metric named `records-consumed-total`.

The easiest way to see the available metrics is to fire up jconsole and point it at a running kafka client or server; this will allow browsing all metrics with JMX.

#### [Security Considerations for Remote Monitoring using JMX](http://kafka.apache.org/documentation/#remote_jmx)

Apache Kafka disables remote JMX by default. You can enable remote monitoring using JMX by setting the environment variable `JMX_PORT` for processes started using the CLI or standard Java system properties to enable remote JMX programmatically. You must enable security when enabling remote JMX in production scenarios to ensure that unauthorized users cannot monitor or control your broker or application as well as the platform on which these are running. Note that authentication is disabled for JMX by default in Kafka and security configs must be overridden for production deployments by setting the environment variable `KAFKA_JMX_OPTS` for processes started using the CLI or by setting appropriate Java system properties. See [Monitoring and Management Using JMX Technology](https://docs.oracle.com/javase/8/docs/technotes/guides/management/agent.html) for details on securing JMX.

We do graphing and alerting on the following metrics:

| DESCRIPTION                                                  | MBEAN NAME                                                   | NORMAL VALUE                                                 |
| :----------------------------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| Message in rate                                              | kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec   |                                                              |
| Byte in rate from clients                                    | kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec      |                                                              |
| Byte in rate from other brokers                              | kafka.server:type=BrokerTopicMetrics,name=ReplicationBytesInPerSec |                                                              |
| Request rate                                                 | kafka.network:type=RequestMetrics,name=RequestsPerSec,request={Produce\|FetchConsumer\|FetchFollower} |                                                              |
| Error rate                                                   | kafka.network:type=RequestMetrics,name=ErrorsPerSec,request=([-.\w]+),error=([-.\w]+) | Number of errors in responses counted per-request-type, per-error-code. If a response contains multiple errors, all are counted. error=NONE indicates successful responses. |
| Request size in bytes                                        | kafka.network:type=RequestMetrics,name=RequestBytes,request=([-.\w]+) | Size of requests for each request type.                      |
| Temporary memory size in bytes                               | kafka.network:type=RequestMetrics,name=TemporaryMemoryBytes,request={Produce\|Fetch} | Temporary memory used for message format conversions and decompression. |
| Message conversion time                                      | kafka.network:type=RequestMetrics,name=MessageConversionsTimeMs,request={Produce\|Fetch} | Time in milliseconds spent on message format conversions.    |
| Message conversion rate                                      | kafka.server:type=BrokerTopicMetrics,name={Produce\|Fetch}MessageConversionsPerSec,topic=([-.\w]+) | Number of records which required message format conversion.  |
| Byte out rate to clients                                     | kafka.server:type=BrokerTopicMetrics,name=BytesOutPerSec     |                                                              |
| Byte out rate to other brokers                               | kafka.server:type=BrokerTopicMetrics,name=ReplicationBytesOutPerSec |                                                              |
| Message validation failure rate due to no key specified for compacted topic | kafka.server:type=BrokerTopicMetrics,name=NoKeyCompactedTopicRecordsPerSec |                                                              |
| Message validation failure rate due to invalid magic number  | kafka.server:type=BrokerTopicMetrics,name=InvalidMagicNumberRecordsPerSec |                                                              |
| Message validation failure rate due to incorrect crc checksum | kafka.server:type=BrokerTopicMetrics,name=InvalidMessageCrcRecordsPerSec |                                                              |
| Message validation failure rate due to non-continuous offset or sequence number in batch | kafka.server:type=BrokerTopicMetrics,name=InvalidOffsetOrSequenceRecordsPerSec |                                                              |
| Log flush rate and time                                      | kafka.log:type=LogFlushStats,name=LogFlushRateAndTimeMs      |                                                              |
| # of under replicated partitions (the number of non-reassigning replicas - the number of ISR > 0) | kafka.server:type=ReplicaManager,name=UnderReplicatedPartitions | 0                                                            |
| # of under minIsr partitions (\|ISR\| < min.insync.replicas) | kafka.server:type=ReplicaManager,name=UnderMinIsrPartitionCount | 0                                                            |
| # of at minIsr partitions (\|ISR\| = min.insync.replicas)    | kafka.server:type=ReplicaManager,name=AtMinIsrPartitionCount | 0                                                            |
| # of offline log directories                                 | kafka.log:type=LogManager,name=OfflineLogDirectoryCount      | 0                                                            |
| Is controller active on broker                               | kafka.controller:type=KafkaController,name=ActiveControllerCount | only one broker in the cluster should have 1                 |
| Leader election rate                                         | kafka.controller:type=ControllerStats,name=LeaderElectionRateAndTimeMs | non-zero when there are broker failures                      |
| Unclean leader election rate                                 | kafka.controller:type=ControllerStats,name=UncleanLeaderElectionsPerSec | 0                                                            |
| Pending topic deletes                                        | kafka.controller:type=KafkaController,name=TopicsToDeleteCount |                                                              |
| Pending replica deletes                                      | kafka.controller:type=KafkaController,name=ReplicasToDeleteCount |                                                              |
| Ineligible pending topic deletes                             | kafka.controller:type=KafkaController,name=TopicsIneligibleToDeleteCount |                                                              |
| Ineligible pending replica deletes                           | kafka.controller:type=KafkaController,name=ReplicasIneligibleToDeleteCount |                                                              |
| Partition counts                                             | kafka.server:type=ReplicaManager,name=PartitionCount         | mostly even across brokers                                   |
| Leader replica counts                                        | kafka.server:type=ReplicaManager,name=LeaderCount            | mostly even across brokers                                   |
| ISR shrink rate                                              | kafka.server:type=ReplicaManager,name=IsrShrinksPerSec       | If a broker goes down, ISR for some of the partitions will shrink. When that broker is up again, ISR will be expanded once the replicas are fully caught up. Other than that, the expected value for both ISR shrink rate and expansion rate is 0. |
| ISR expansion rate                                           | kafka.server:type=ReplicaManager,name=IsrExpandsPerSec       | See above                                                    |
| Max lag in messages btw follower and leader replicas         | kafka.server:type=ReplicaFetcherManager,name=MaxLag,clientId=Replica | lag should be proportional to the maximum batch size of a produce request. |
| Lag in messages per follower replica                         | kafka.server:type=FetcherLagMetrics,name=ConsumerLag,clientId=([-.\w]+),topic=([-.\w]+),partition=([0-9]+) | lag should be proportional to the maximum batch size of a produce request. |
| Requests waiting in the producer purgatory                   | kafka.server:type=DelayedOperationPurgatory,name=PurgatorySize,delayedOperation=Produce | non-zero if ack=-1 is used                                   |
| Requests waiting in the fetch purgatory                      | kafka.server:type=DelayedOperationPurgatory,name=PurgatorySize,delayedOperation=Fetch | size depends on fetch.wait.max.ms in the consumer            |
| Request total time                                           | kafka.network:type=RequestMetrics,name=TotalTimeMs,request={Produce\|FetchConsumer\|FetchFollower} | broken into queue, local, remote and response send time      |
| Time the request waits in the request queue                  | kafka.network:type=RequestMetrics,name=RequestQueueTimeMs,request={Produce\|FetchConsumer\|FetchFollower} |                                                              |
| Time the request is processed at the leader                  | kafka.network:type=RequestMetrics,name=LocalTimeMs,request={Produce\|FetchConsumer\|FetchFollower} |                                                              |
| Time the request waits for the follower                      | kafka.network:type=RequestMetrics,name=RemoteTimeMs,request={Produce\|FetchConsumer\|FetchFollower} | non-zero for produce requests when ack=-1                    |
| Time the request waits in the response queue                 | kafka.network:type=RequestMetrics,name=ResponseQueueTimeMs,request={Produce\|FetchConsumer\|FetchFollower} |                                                              |
| Time to send the response                                    | kafka.network:type=RequestMetrics,name=ResponseSendTimeMs,request={Produce\|FetchConsumer\|FetchFollower} |                                                              |
| Number of messages the consumer lags behind the producer by. Published by the consumer, not broker. | kafka.consumer:type=consumer-fetch-manager-metrics,client-id={client-id} Attribute: records-lag-max |                                                              |
| The average fraction of time the network processors are idle | kafka.network:type=SocketServer,name=NetworkProcessorAvgIdlePercent | between 0 and 1, ideally > 0.3                               |
| The number of connections disconnected on a processor due to a client not re-authenticating and then using the connection beyond its expiration time for anything other than re-authentication | kafka.server:type=socket-server-metrics,listener=[SASL_PLAINTEXT\|SASL_SSL],networkProcessor=<#>,name=expired-connections-killed-count | ideally 0 when re-authentication is enabled, implying there are no longer any older, pre-2.2.0 clients connecting to this (listener, processor) combination |
| The total number of connections disconnected, across all processors, due to a client not re-authenticating and then using the connection beyond its expiration time for anything other than re-authentication | kafka.network:type=SocketServer,name=ExpiredConnectionsKilledCount | ideally 0 when re-authentication is enabled, implying there are no longer any older, pre-2.2.0 clients connecting to this broker |
| The average fraction of time the request handler threads are idle | kafka.server:type=KafkaRequestHandlerPool,name=RequestHandlerAvgIdlePercent | between 0 and 1, ideally > 0.3                               |
| Bandwidth quota metrics per (user, client-id), user or client-id | kafka.server:type={Produce\|Fetch},user=([-.\w]+),client-id=([-.\w]+) | Two attributes. throttle-time indicates the amount of time in ms the client was throttled. Ideally = 0. byte-rate indicates the data produce/consume rate of the client in bytes/sec. For (user, client-id) quotas, both user and client-id are specified. If per-client-id quota is applied to the client, user is not specified. If per-user quota is applied, client-id is not specified. |
| Request quota metrics per (user, client-id), user or client-id | kafka.server:type=Request,user=([-.\w]+),client-id=([-.\w]+) | Two attributes. throttle-time indicates the amount of time in ms the client was throttled. Ideally = 0. request-time indicates the percentage of time spent in broker network and I/O threads to process requests from client group. For (user, client-id) quotas, both user and client-id are specified. If per-client-id quota is applied to the client, user is not specified. If per-user quota is applied, client-id is not specified. |
| Requests exempt from throttling                              | kafka.server:type=Request                                    | exempt-throttle-time indicates the percentage of time spent in broker network and I/O threads to process requests that are exempt from throttling. |
| ZooKeeper client request latency                             | kafka.server:type=ZooKeeperClientMetrics,name=ZooKeeperRequestLatencyMs | Latency in millseconds for ZooKeeper requests from broker.   |
| ZooKeeper connection status                                  | kafka.server:type=SessionExpireListener,name=SessionState    | Connection status of broker's ZooKeeper session which may be one of Disconnected\|SyncConnected\|AuthFailed\|ConnectedReadOnly\|SaslAuthenticated\|Expired. |
| Max time to load group metadata                              | kafka.server:type=group-coordinator-metrics,name=partition-load-time-max | maximum time, in milliseconds, it took to load offsets and group metadata from the consumer offset partitions loaded in the last 30 seconds (including time spent waiting for the loading task to be scheduled) |
| Avg time to load group metadata                              | kafka.server:type=group-coordinator-metrics,name=partition-load-time-avg | average time, in milliseconds, it took to load offsets and group metadata from the consumer offset partitions loaded in the last 30 seconds (including time spent waiting for the loading task to be scheduled) |
| Max time to load transaction metadata                        | kafka.server:type=transaction-coordinator-metrics,name=partition-load-time-max | maximum time, in milliseconds, it took to load transaction metadata from the consumer offset partitions loaded in the last 30 seconds (including time spent waiting for the loading task to be scheduled) |
| Avg time to load transaction metadata                        | kafka.server:type=transaction-coordinator-metrics,name=partition-load-time-avg | average time, in milliseconds, it took to load transaction metadata from the consumer offset partitions loaded in the last 30 seconds (including time spent waiting for the loading task to be scheduled) |
| Consumer Group Offset Count                                  | kafka.server:type=GroupMetadataManager,name=NumOffsets       | Total number of committed offsets for Consumer Groups        |
| Consumer Group Count                                         | kafka.server:type=GroupMetadataManager,name=NumGroups        | Total number of Consumer Groups                              |
| Consumer Group Count, per State                              | kafka.server:type=GroupMetadataManager,name=NumGroups[PreparingRebalance,CompletingRebalance,Empty,Stable,Dead] | The number of Consumer Groups in each state: PreparingRebalance, CompletingRebalance, Empty, Stable, Dead |
| Number of reassigning partitions                             | kafka.server:type=ReplicaManager,name=ReassigningPartitions  | The number of reassigning leader partitions on a broker.     |
| Outgoing byte rate of reassignment traffic                   | kafka.server:type=BrokerTopicMetrics,name=ReassignmentBytesOutPerSec |                                                              |
| Incoming byte rate of reassignment traffic                   | kafka.server:type=BrokerTopicMetrics,name=ReassignmentBytesInPerSec |                                                              |



#### [Common monitoring metrics for producer/consumer/connect/streams](http://kafka.apache.org/documentation/#selector_monitoring)

The following metrics are available on producer/consumer/connector/streams instances. For specific metrics, please see following sections.

| METRIC/ATTRIBUTE NAME                     | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :---------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| connection-close-rate                     | Connections closed per second in the window.                 | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| connection-close-total                    | Total connections closed in the window.                      | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| connection-creation-rate                  | New connections established per second in the window.        | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| connection-creation-total                 | Total new connections established in the window.             | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| network-io-rate                           | The average number of network operations (reads or writes) on all connections per second. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| network-io-total                          | The total number of network operations (reads or writes) on all connections. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| outgoing-byte-rate                        | The average number of outgoing bytes sent per second to all servers. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| outgoing-byte-total                       | The total number of outgoing bytes sent to all servers.      | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| request-rate                              | The average number of requests sent per second.              | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| request-total                             | The total number of requests sent.                           | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| request-size-avg                          | The average size of all requests in the window.              | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| request-size-max                          | The maximum size of any request sent in the window.          | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| incoming-byte-rate                        | Bytes/second read off all sockets.                           | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| incoming-byte-total                       | Total bytes read off all sockets.                            | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| response-rate                             | Responses received per second.                               | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| response-total                            | Total responses received.                                    | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| select-rate                               | Number of times the I/O layer checked for new I/O to perform per second. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| select-total                              | Total number of times the I/O layer checked for new I/O to perform. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| io-wait-time-ns-avg                       | The average length of time the I/O thread spent waiting for a socket ready for reads or writes in nanoseconds. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| io-wait-ratio                             | The fraction of time the I/O thread spent waiting.           | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| io-time-ns-avg                            | The average length of time for I/O per select call in nanoseconds. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| io-ratio                                  | The fraction of time the I/O thread spent doing I/O.         | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| connection-count                          | The current number of active connections.                    | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| successful-authentication-rate            | Connections per second that were successfully authenticated using SASL or SSL. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| successful-authentication-total           | Total connections that were successfully authenticated using SASL or SSL. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| failed-authentication-rate                | Connections per second that failed authentication.           | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| failed-authentication-total               | Total connections that failed authentication.                | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| successful-reauthentication-rate          | Connections per second that were successfully re-authenticated using SASL. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| successful-reauthentication-total         | Total connections that were successfully re-authenticated using SASL. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| reauthentication-latency-max              | The maximum latency in ms observed due to re-authentication. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| reauthentication-latency-avg              | The average latency in ms observed due to re-authentication. | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| failed-reauthentication-rate              | Connections per second that failed re-authentication.        | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| failed-reauthentication-total             | Total connections that failed re-authentication.             | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |
| successful-authentication-no-reauth-total | Total connections that were successfully authenticated by older, pre-2.2.0 SASL clients that do not support re-authentication. May only be non-zero | kafka.[producer\|consumer\|connect]:type=[producer\|consumer\|connect]-metrics,client-id=([-.\w]+) |

#### [Common Per-broker metrics for producer/consumer/connect/streams](http://kafka.apache.org/documentation/#common_node_monitoring)

The following metrics are available on producer/consumer/connector/streams instances. For specific metrics, please see following sections.

| METRIC/ATTRIBUTE NAME | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :-------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| outgoing-byte-rate    | The average number of outgoing bytes sent per second for a node. | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| outgoing-byte-total   | The total number of outgoing bytes sent for a node.          | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-rate          | The average number of requests sent per second for a node.   | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-total         | The total number of requests sent for a node.                | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-size-avg      | The average size of all requests in the window for a node.   | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-size-max      | The maximum size of any request sent in the window for a node. | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| incoming-byte-rate    | The average number of bytes received per second for a node.  | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| incoming-byte-total   | The total number of bytes received for a node.               | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-latency-avg   | The average request latency in ms for a node.                | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| request-latency-max   | The maximum request latency in ms for a node.                | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| response-rate         | Responses received per second for a node.                    | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |
| response-total        | Total responses received for a node.                         | kafka.[producer\|consumer\|connect]:type=[consumer\|producer\|connect]-node-metrics,client-id=([-.\w]+),node-id=([0-9]+) |

#### [Producer monitoring](http://kafka.apache.org/documentation/#producer_monitoring)

The following metrics are available on producer instances.

| METRIC/ATTRIBUTE NAME  | DESCRIPTION                                                  | MBEAN NAME                                               |
| :--------------------- | :----------------------------------------------------------- | :------------------------------------------------------- |
| waiting-threads        | The number of user threads blocked waiting for buffer memory to enqueue their records. | kafka.producer:type=producer-metrics,client-id=([-.\w]+) |
| buffer-total-bytes     | The maximum amount of buffer memory the client can use (whether or not it is currently used). | kafka.producer:type=producer-metrics,client-id=([-.\w]+) |
| buffer-available-bytes | The total amount of buffer memory that is not being used (either unallocated or in the free list). | kafka.producer:type=producer-metrics,client-id=([-.\w]+) |
| bufferpool-wait-time   | The fraction of time an appender waits for space allocation. | kafka.producer:type=producer-metrics,client-id=([-.\w]+) |

##### [Producer Sender Metrics](http://kafka.apache.org/documentation/#producer_sender_monitoring)

| kafka.producer:type=producer-metrics,client-id="{client-id}" |                           |                                                              |
| :----------------------------------------------------------- | :------------------------ | :----------------------------------------------------------- |
|                                                              | ATTRIBUTE NAME            | DESCRIPTION                                                  |
|                                                              | batch-size-avg            | The average number of bytes sent per partition per-request.  |
|                                                              | batch-size-max            | The max number of bytes sent per partition per-request.      |
|                                                              | batch-split-rate          | The average number of batch splits per second                |
|                                                              | batch-split-total         | The total number of batch splits                             |
|                                                              | compression-rate-avg      | The average compression rate of record batches.              |
|                                                              | metadata-age              | The age in seconds of the current producer metadata being used. |
|                                                              | produce-throttle-time-avg | The average time in ms a request was throttled by a broker   |
|                                                              | produce-throttle-time-max | The maximum time in ms a request was throttled by a broker   |
|                                                              | record-error-rate         | The average per-second number of record sends that resulted in errors |
|                                                              | record-error-total        | The total number of record sends that resulted in errors     |
|                                                              | record-queue-time-avg     | The average time in ms record batches spent in the send buffer. |
|                                                              | record-queue-time-max     | The maximum time in ms record batches spent in the send buffer. |
|                                                              | record-retry-rate         | The average per-second number of retried record sends        |
|                                                              | record-retry-total        | The total number of retried record sends                     |
|                                                              | record-send-rate          | The average number of records sent per second.               |
|                                                              | record-send-total         | The total number of records sent.                            |
|                                                              | record-size-avg           | The average record size                                      |
|                                                              | record-size-max           | The maximum record size                                      |
|                                                              | records-per-request-avg   | The average number of records per request.                   |
|                                                              | request-latency-avg       | The average request latency in ms                            |
|                                                              | request-latency-max       | The maximum request latency in ms                            |
|                                                              | requests-in-flight        | The current number of in-flight requests awaiting a response. |
| kafka.producer:type=producer-topic-metrics,client-id="{client-id}",topic="{topic}" |                           |                                                              |
|                                                              | ATTRIBUTE NAME            | DESCRIPTION                                                  |
|                                                              | byte-rate                 | The average number of bytes sent per second for a topic.     |
|                                                              | byte-total                | The total number of bytes sent for a topic.                  |
|                                                              | compression-rate          | The average compression rate of record batches for a topic.  |
|                                                              | record-error-rate         | The average per-second number of record sends that resulted in errors for a topic |
|                                                              | record-error-total        | The total number of record sends that resulted in errors for a topic |
|                                                              | record-retry-rate         | The average per-second number of retried record sends for a topic |
|                                                              | record-retry-total        | The total number of retried record sends for a topic         |
|                                                              | record-send-rate          | The average number of records sent per second for a topic.   |
|                                                              | record-send-total         | The total number of records sent for a topic.                |

#### [consumer monitoring](http://kafka.apache.org/documentation/#consumer_monitoring)

The following metrics are available on consumer instances.

| METRIC/ATTRIBUTE NAME | DESCRIPTION                                                  | MBEAN NAME                                               |
| :-------------------- | :----------------------------------------------------------- | :------------------------------------------------------- |
| time-between-poll-avg | The average delay between invocations of poll().             | kafka.consumer:type=consumer-metrics,client-id=([-.\w]+) |
| time-between-poll-max | The max delay between invocations of poll().                 | kafka.consumer:type=consumer-metrics,client-id=([-.\w]+) |
| last-poll-seconds-ago | The number of seconds since the last poll() invocation.      | kafka.consumer:type=consumer-metrics,client-id=([-.\w]+) |
| poll-idle-ratio-avg   | The average fraction of time the consumer's poll() is idle as opposed to waiting for the user code to process records. | kafka.consumer:type=consumer-metrics,client-id=([-.\w]+) |

##### [Consumer Group Metrics](http://kafka.apache.org/documentation/#consumer_group_monitoring)

| METRIC/ATTRIBUTE NAME           | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :------------------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| commit-latency-avg              | The average time taken for a commit request                  | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| commit-latency-max              | The max time taken for a commit request                      | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| commit-rate                     | The number of commit calls per second                        | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| commit-total                    | The total number of commit calls                             | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| assigned-partitions             | The number of partitions currently assigned to this consumer | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| heartbeat-response-time-max     | The max time taken to receive a response to a heartbeat request | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| heartbeat-rate                  | The average number of heartbeats per second                  | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| heartbeat-total                 | The total number of heartbeats                               | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| join-time-avg                   | The average time taken for a group rejoin                    | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| join-time-max                   | The max time taken for a group rejoin                        | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| join-rate                       | The number of group joins per second                         | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| join-total                      | The total number of group joins                              | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| sync-time-avg                   | The average time taken for a group sync                      | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| sync-time-max                   | The max time taken for a group sync                          | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| sync-rate                       | The number of group syncs per second                         | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| sync-total                      | The total number of group syncs                              | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| rebalance-latency-avg           | The average time taken for a group rebalance                 | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| rebalance-latency-max           | The max time taken for a group rebalance                     | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| rebalance-latency-total         | The total time taken for group rebalances so far             | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| rebalance-total                 | The total number of group rebalances participated            | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| rebalance-rate-per-hour         | The number of group rebalance participated per hour          | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| failed-rebalance-total          | The total number of failed group rebalances                  | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| failed-rebalance-rate-per-hour  | The number of failed group rebalance event per hour          | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| last-rebalance-seconds-ago      | The number of seconds since the last rebalance event         | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| last-heartbeat-seconds-ago      | The number of seconds since the last controller heartbeat    | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-revoked-latency-avg  | The average time taken by the on-partitions-revoked rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-revoked-latency-max  | The max time taken by the on-partitions-revoked rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-assigned-latency-avg | The average time taken by the on-partitions-assigned rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-assigned-latency-max | The max time taken by the on-partitions-assigned rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-lost-latency-avg     | The average time taken by the on-partitions-lost rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |
| partitions-lost-latency-max     | The max time taken by the on-partitions-lost rebalance listener callback | kafka.consumer:type=consumer-coordinator-metrics,client-id=([-.\w]+) |

##### [Consumer Fetch Metrics](http://kafka.apache.org/documentation/#consumer_fetch_monitoring)

| kafka.consumer:type=consumer-fetch-manager-metrics,client-id="{client-id}" |                         |                                                              |
| :----------------------------------------------------------- | :---------------------- | :----------------------------------------------------------- |
|                                                              | ATTRIBUTE NAME          | DESCRIPTION                                                  |
|                                                              | bytes-consumed-rate     | The average number of bytes consumed per second              |
|                                                              | bytes-consumed-total    | The total number of bytes consumed                           |
|                                                              | fetch-latency-avg       | The average time taken for a fetch request.                  |
|                                                              | fetch-latency-max       | The max time taken for any fetch request.                    |
|                                                              | fetch-rate              | The number of fetch requests per second.                     |
|                                                              | fetch-size-avg          | The average number of bytes fetched per request              |
|                                                              | fetch-size-max          | The maximum number of bytes fetched per request              |
|                                                              | fetch-throttle-time-avg | The average throttle time in ms                              |
|                                                              | fetch-throttle-time-max | The maximum throttle time in ms                              |
|                                                              | fetch-total             | The total number of fetch requests.                          |
|                                                              | records-consumed-rate   | The average number of records consumed per second            |
|                                                              | records-consumed-total  | The total number of records consumed                         |
|                                                              | records-lag-max         | The maximum lag in terms of number of records for any partition in this window |
|                                                              | records-lead-min        | The minimum lead in terms of number of records for any partition in this window |
|                                                              | records-per-request-avg | The average number of records in each request                |
| kafka.consumer:type=consumer-fetch-manager-metrics,client-id="{client-id}",topic="{topic}" |                         |                                                              |
|                                                              | ATTRIBUTE NAME          | DESCRIPTION                                                  |
|                                                              | bytes-consumed-rate     | The average number of bytes consumed per second for a topic  |
|                                                              | bytes-consumed-total    | The total number of bytes consumed for a topic               |
|                                                              | fetch-size-avg          | The average number of bytes fetched per request for a topic  |
|                                                              | fetch-size-max          | The maximum number of bytes fetched per request for a topic  |
|                                                              | records-consumed-rate   | The average number of records consumed per second for a topic |
|                                                              | records-consumed-total  | The total number of records consumed for a topic             |
|                                                              | records-per-request-avg | The average number of records in each request for a topic    |
| kafka.consumer:type=consumer-fetch-manager-metrics,partition="{partition}",topic="{topic}",client-id="{client-id}" |                         |                                                              |
|                                                              | ATTRIBUTE NAME          | DESCRIPTION                                                  |
|                                                              | preferred-read-replica  | The current read replica for the partition, or -1 if reading from leader |
|                                                              | records-lag             | The latest lag of the partition                              |
|                                                              | records-lag-avg         | The average lag of the partition                             |
|                                                              | records-lag-max         | The max lag of the partition                                 |
|                                                              | records-lead            | The latest lead of the partition                             |
|                                                              | records-lead-avg        | The average lead of the partition                            |
|                                                              | records-lead-min        | The min lead of the partition                                |

#### [Connect Monitoring](http://kafka.apache.org/documentation/#connect_monitoring)

A Connect worker process contains all the producer and consumer metrics as well as metrics specific to Connect. The worker process itself has a number of metrics, while each connector and task have additional metrics.

| kafka.connect:type=connect-worker-metrics                    |                                      |                                                              |
| :----------------------------------------------------------- | :----------------------------------- | :----------------------------------------------------------- |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | connector-count                      | The number of connectors run in this worker.                 |
|                                                              | connector-startup-attempts-total     | The total number of connector startups that this worker has attempted. |
|                                                              | connector-startup-failure-percentage | The average percentage of this worker's connectors starts that failed. |
|                                                              | connector-startup-failure-total      | The total number of connector starts that failed.            |
|                                                              | connector-startup-success-percentage | The average percentage of this worker's connectors starts that succeeded. |
|                                                              | connector-startup-success-total      | The total number of connector starts that succeeded.         |
|                                                              | task-count                           | The number of tasks run in this worker.                      |
|                                                              | task-startup-attempts-total          | The total number of task startups that this worker has attempted. |
|                                                              | task-startup-failure-percentage      | The average percentage of this worker's tasks starts that failed. |
|                                                              | task-startup-failure-total           | The total number of task starts that failed.                 |
|                                                              | task-startup-success-percentage      | The average percentage of this worker's tasks starts that succeeded. |
|                                                              | task-startup-success-total           | The total number of task starts that succeeded.              |
| kafka.connect:type=connect-worker-metrics,connector="{connector}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | connector-destroyed-task-count       | The number of destroyed tasks of the connector on the worker. |
|                                                              | connector-failed-task-count          | The number of failed tasks of the connector on the worker.   |
|                                                              | connector-paused-task-count          | The number of paused tasks of the connector on the worker.   |
|                                                              | connector-running-task-count         | The number of running tasks of the connector on the worker.  |
|                                                              | connector-total-task-count           | The number of tasks of the connector on the worker.          |
|                                                              | connector-unassigned-task-count      | The number of unassigned tasks of the connector on the worker. |
| kafka.connect:type=connect-worker-rebalance-metrics          |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | completed-rebalances-total           | The total number of rebalances completed by this worker.     |
|                                                              | connect-protocol                     | The Connect protocol used by this cluster                    |
|                                                              | epoch                                | The epoch or generation number of this worker.               |
|                                                              | leader-name                          | The name of the group leader.                                |
|                                                              | rebalance-avg-time-ms                | The average time in milliseconds spent by this worker to rebalance. |
|                                                              | rebalance-max-time-ms                | The maximum time in milliseconds spent by this worker to rebalance. |
|                                                              | rebalancing                          | Whether this worker is currently rebalancing.                |
|                                                              | time-since-last-rebalance-ms         | The time in milliseconds since this worker completed the most recent rebalance. |
| kafka.connect:type=connector-metrics,connector="{connector}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | connector-class                      | The name of the connector class.                             |
|                                                              | connector-type                       | The type of the connector. One of 'source' or 'sink'.        |
|                                                              | connector-version                    | The version of the connector class, as reported by the connector. |
|                                                              | status                               | The status of the connector. One of 'unassigned', 'running', 'paused', 'failed', or 'destroyed'. |
| kafka.connect:type=connector-task-metrics,connector="{connector}",task="{task}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | batch-size-avg                       | The average size of the batches processed by the connector.  |
|                                                              | batch-size-max                       | The maximum size of the batches processed by the connector.  |
|                                                              | offset-commit-avg-time-ms            | The average time in milliseconds taken by this task to commit offsets. |
|                                                              | offset-commit-failure-percentage     | The average percentage of this task's offset commit attempts that failed. |
|                                                              | offset-commit-max-time-ms            | The maximum time in milliseconds taken by this task to commit offsets. |
|                                                              | offset-commit-success-percentage     | The average percentage of this task's offset commit attempts that succeeded. |
|                                                              | pause-ratio                          | The fraction of time this task has spent in the pause state. |
|                                                              | running-ratio                        | The fraction of time this task has spent in the running state. |
|                                                              | status                               | The status of the connector task. One of 'unassigned', 'running', 'paused', 'failed', or 'destroyed'. |
| kafka.connect:type=sink-task-metrics,connector="{connector}",task="{task}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | offset-commit-completion-rate        | The average per-second number of offset commit completions that were completed successfully. |
|                                                              | offset-commit-completion-total       | The total number of offset commit completions that were completed successfully. |
|                                                              | offset-commit-seq-no                 | The current sequence number for offset commits.              |
|                                                              | offset-commit-skip-rate              | The average per-second number of offset commit completions that were received too late and skipped/ignored. |
|                                                              | offset-commit-skip-total             | The total number of offset commit completions that were received too late and skipped/ignored. |
|                                                              | partition-count                      | The number of topic partitions assigned to this task belonging to the named sink connector in this worker. |
|                                                              | put-batch-avg-time-ms                | The average time taken by this task to put a batch of sinks records. |
|                                                              | put-batch-max-time-ms                | The maximum time taken by this task to put a batch of sinks records. |
|                                                              | sink-record-active-count             | The number of records that have been read from Kafka but not yet completely committed/flushed/acknowledged by the sink task. |
|                                                              | sink-record-active-count-avg         | The average number of records that have been read from Kafka but not yet completely committed/flushed/acknowledged by the sink task. |
|                                                              | sink-record-active-count-max         | The maximum number of records that have been read from Kafka but not yet completely committed/flushed/acknowledged by the sink task. |
|                                                              | sink-record-lag-max                  | The maximum lag in terms of number of records that the sink task is behind the consumer's position for any topic partitions. |
|                                                              | sink-record-read-rate                | The average per-second number of records read from Kafka for this task belonging to the named sink connector in this worker. This is before transformations are applied. |
|                                                              | sink-record-read-total               | The total number of records read from Kafka by this task belonging to the named sink connector in this worker, since the task was last restarted. |
|                                                              | sink-record-send-rate                | The average per-second number of records output from the transformations and sent/put to this task belonging to the named sink connector in this worker. This is after transformations are applied and excludes any records filtered out by the transformations. |
|                                                              | sink-record-send-total               | The total number of records output from the transformations and sent/put to this task belonging to the named sink connector in this worker, since the task was last restarted. |
| kafka.connect:type=source-task-metrics,connector="{connector}",task="{task}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | poll-batch-avg-time-ms               | The average time in milliseconds taken by this task to poll for a batch of source records. |
|                                                              | poll-batch-max-time-ms               | The maximum time in milliseconds taken by this task to poll for a batch of source records. |
|                                                              | source-record-active-count           | The number of records that have been produced by this task but not yet completely written to Kafka. |
|                                                              | source-record-active-count-avg       | The average number of records that have been produced by this task but not yet completely written to Kafka. |
|                                                              | source-record-active-count-max       | The maximum number of records that have been produced by this task but not yet completely written to Kafka. |
|                                                              | source-record-poll-rate              | The average per-second number of records produced/polled (before transformation) by this task belonging to the named source connector in this worker. |
|                                                              | source-record-poll-total             | The total number of records produced/polled (before transformation) by this task belonging to the named source connector in this worker. |
|                                                              | source-record-write-rate             | The average per-second number of records output from the transformations and written to Kafka for this task belonging to the named source connector in this worker. This is after transformations are applied and excludes any records filtered out by the transformations. |
|                                                              | source-record-write-total            | The number of records output from the transformations and written to Kafka for this task belonging to the named source connector in this worker, since the task was last restarted. |
| kafka.connect:type=task-error-metrics,connector="{connector}",task="{task}" |                                      |                                                              |
|                                                              | ATTRIBUTE NAME                       | DESCRIPTION                                                  |
|                                                              | deadletterqueue-produce-failures     | The number of failed writes to the dead letter queue.        |
|                                                              | deadletterqueue-produce-requests     | The number of attempted writes to the dead letter queue.     |
|                                                              | last-error-timestamp                 | The epoch timestamp when this task last encountered an error. |
|                                                              | total-errors-logged                  | The number of errors that were logged.                       |
|                                                              | total-record-errors                  | The number of record processing errors in this task.         |
|                                                              | total-record-failures                | The number of record processing failures in this task.       |
|                                                              | total-records-skipped                | The number of records skipped due to errors.                 |
|                                                              | total-retries                        | The number of operations retried.                            |

#### [Streams Monitoring](http://kafka.apache.org/documentation/#kafka_streams_monitoring)

A Kafka Streams instance contains all the producer and consumer metrics as well as additional metrics specific to Streams. By default Kafka Streams has metrics with two recording levels: `debug` and `info`.

Note that the metrics have a 4-layer hierarchy. At the top level there are client-level metrics for each started Kafka Streams client. Each client has stream threads, with their own metrics. Each stream thread has tasks, with their own metrics. Each task has a number of processor nodes, with their own metrics. Each task also has a number of state stores and record caches, all with their own metrics.

Use the following configuration option to specify which metrics you want collected:

```
metrics.recording.level="info"
```

##### [Client Metrics](http://kafka.apache.org/documentation/#kafka_streams_client_monitoring)

All of the following metrics have a recording level of `info`:

| METRIC/ATTRIBUTE NAME | DESCRIPTION                                                  | MBEAN NAME                                            |
| :-------------------- | :----------------------------------------------------------- | :---------------------------------------------------- |
| version               | The version of the Kafka Streams client.                     | kafka.streams:type=stream-metrics,client-id=([-.\w]+) |
| commit-id             | The version control commit ID of the Kafka Streams client.   | kafka.streams:type=stream-metrics,client-id=([-.\w]+) |
| application-id        | The application ID of the Kafka Streams client.              | kafka.streams:type=stream-metrics,client-id=([-.\w]+) |
| topology-description  | The description of the topology executed in the Kafka Streams client. | kafka.streams:type=stream-metrics,client-id=([-.\w]+) |
| state                 | The state of the Kafka Streams client.                       | kafka.streams:type=stream-metrics,client-id=([-.\w]+) |

##### [Thread Metrics](http://kafka.apache.org/documentation/#kafka_streams_thread_monitoring)

All of the following metrics have a recording level of `info`:

| METRIC/ATTRIBUTE NAME | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :-------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| commit-latency-avg    | The average execution time in ms, for committing, across all running tasks of this thread. | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| commit-latency-max    | The maximum execution time in ms, for committing, across all running tasks of this thread. | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| poll-latency-avg      | The average execution time in ms, for consumer polling.      | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| poll-latency-max      | The maximum execution time in ms, for consumer polling.      | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| process-latency-avg   | The average execution time in ms, for processing.            | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| process-latency-max   | The maximum execution time in ms, for processing.            | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| punctuate-latency-avg | The average execution time in ms, for punctuating.           | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| punctuate-latency-max | The maximum execution time in ms, for punctuating.           | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| commit-rate           | The average number of commits per second.                    | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| commit-total          | The total number of commit calls.                            | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| poll-rate             | The average number of consumer poll calls per second.        | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| poll-total            | The total number of consumer poll calls.                     | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| process-rate          | The average number of processed records per second.          | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| process-total         | The total number of processed records.                       | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| punctuate-rate        | The average number of punctuate calls per second.            | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| punctuate-total       | The total number of punctuate calls.                         | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| task-created-rate     | The average number of tasks created per second.              | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| task-created-total    | The total number of tasks created.                           | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| task-closed-rate      | The average number of tasks closed per second.               | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |
| task-closed-total     | The total number of tasks closed.                            | kafka.streams:type=stream-thread-metrics,thread-id=([-.\w]+) |

##### [Task Metrics](http://kafka.apache.org/documentation/#kafka_streams_task_monitoring)

All of the following metrics have a recording level of `debug`, except for metrics dropped-records-rate and dropped-records-total which have a recording level of `info`:

| METRIC/ATTRIBUTE NAME     | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :------------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| process-latency-avg       | The average execution time in ns, for processing.            | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| process-latency-max       | The maximum execution time in ns, for processing.            | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| process-rate              | The average number of processed records per second across all source processor nodes of this task. | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| process-total             | The total number of processed records across all source processor nodes of this task. | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| commit-latency-avg        | The average execution time in ns, for committing.            | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| commit-latency-max        | The maximum execution time in ns, for committing.            | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| commit-rate               | The average number of commit calls per second.               | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| commit-total              | The total number of commit calls.                            | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| record-lateness-avg       | The average observed lateness of records (stream time - record timestamp). | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| record-lateness-max       | The max observed lateness of records (stream time - record timestamp). | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| enforced-processing-rate  | The average number of enforced processings per second.       | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| enforced-processing-total | The total number enforced processings.                       | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| dropped-records-rate      | The average number of records dropped within this task.      | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |
| dropped-records-total     | The total number of records dropped within this task.        | kafka.streams:type=stream-task-metrics,thread-id=([-.\w]+),task-id=([-.\w]+) |

##### [Processor Node Metrics](http://kafka.apache.org/documentation/#kafka_streams_node_monitoring)

The following metrics are only available on certain types of nodes, i.e., process-rate and process-total are only available for source processor nodes and suppression-emit-rate and suppression-emit-total are only available for suppression operation nodes. All of the metrics have a recording level of `debug`:

| METRIC/ATTRIBUTE NAME  | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :--------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| process-rate           | The average number of records processed by a source processor node per second. | kafka.streams:type=stream-processor-node-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),processor-node-id=([-.\w]+) |
| process-total          | The total number of records processed by a source processor node per second. | kafka.streams:type=stream-processor-node-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),processor-node-id=([-.\w]+) |
| suppression-emit-rate  | The rate at which records that have been emitted downstream from suppression operation nodes. | kafka.streams:type=stream-processor-node-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),processor-node-id=([-.\w]+) |
| suppression-emit-total | The total number of records that have been emitted downstream from suppression operation nodes. | kafka.streams:type=stream-processor-node-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),processor-node-id=([-.\w]+) |

##### [State Store Metrics](http://kafka.apache.org/documentation/#kafka_streams_store_monitoring)

All of the following metrics have a recording level of `debug`. Note that the `store-scope` value is specified in `StoreSupplier#metricsScope()` for user's customized state stores; for built-in state stores, currently we have:

- `in-memory-state`
- `in-memory-lru-state`
- `in-memory-window-state`
- `in-memory-suppression` (for suppression buffers)
- `rocksdb-state` (for RocksDB backed key-value store)
- `rocksdb-window-state` (for RocksDB backed window store)
- `rocksdb-session-state` (for RocksDB backed session store)

Metrics suppression-buffer-size-avg, suppression-buffer-size-max, suppression-buffer-count-avg, and suppression-buffer-count-max are only available for suppression buffers. All other metrics are not available for suppression buffers.

| METRIC/ATTRIBUTE NAME        | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :--------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| put-latency-avg              | The average put execution time in ns.                        | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-latency-max              | The maximum put execution time in ns.                        | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-if-absent-latency-avg    | The average put-if-absent execution time in ns.              | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-if-absent-latency-max    | The maximum put-if-absent execution time in ns.              | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| get-latency-avg              | The average get execution time in ns.                        | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| get-latency-max              | The maximum get execution time in ns.                        | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| delete-latency-avg           | The average delete execution time in ns.                     | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| delete-latency-max           | The maximum delete execution time in ns.                     | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-all-latency-avg          | The average put-all execution time in ns.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-all-latency-max          | The maximum put-all execution time in ns.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| all-latency-avg              | The average all operation execution time in ns.              | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| all-latency-max              | The maximum all operation execution time in ns.              | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| range-latency-avg            | The average range execution time in ns.                      | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| range-latency-max            | The maximum range execution time in ns.                      | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| flush-latency-avg            | The average flush execution time in ns.                      | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| flush-latency-max            | The maximum flush execution time in ns.                      | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| restore-latency-avg          | The average restore execution time in ns.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| restore-latency-max          | The maximum restore execution time in ns.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-rate                     | The average put rate for this store.                         | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-if-absent-rate           | The average put-if-absent rate for this store.               | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| get-rate                     | The average get rate for this store.                         | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| delete-rate                  | The average delete rate for this store.                      | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| put-all-rate                 | The average put-all rate for this store.                     | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| all-rate                     | The average all operation rate for this store.               | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| range-rate                   | The average range rate for this store.                       | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| flush-rate                   | The average flush rate for this store.                       | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| restore-rate                 | The average restore rate for this store.                     | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| suppression-buffer-size-avg  | The average total size, in bytes, of the buffered data over the sampling window. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),in-memory-suppression-id=([-.\w]+) |
| suppression-buffer-size-max  | The maximum total size, in bytes, of the buffered data over the sampling window. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),in-memory-suppression-id=([-.\w]+) |
| suppression-buffer-count-avg | The average number of records buffered over the sampling window. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),in-memory-suppression-id=([-.\w]+) |
| suppression-buffer-count-max | The maximum number of records buffered over the sampling window. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),in-memory-suppression-id=([-.\w]+) |

##### [RocksDB Metrics](http://kafka.apache.org/documentation/#kafka_streams_rocksdb_monitoring)

All of the following metrics have a recording level of `debug`. The metrics are collected every minute from the RocksDB state stores. If a state store consists of multiple RocksDB instances as it is the case for aggregations over time and session windows, each metric reports an aggregation over the RocksDB instances of the state store. Note that the `store-scope` for built-in RocksDB state stores are currently the following:

- `rocksdb-state` (for RocksDB backed key-value store)
- `rocksdb-window-state` (for RocksDB backed window store)
- `rocksdb-session-state` (for RocksDB backed session store)

| METRIC/ATTRIBUTE NAME         | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :---------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| bytes-written-rate            | The average number of bytes written per second to the RocksDB state store. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| bytes-written-total           | The total number of bytes written to the RocksDB state store. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| bytes-read-rate               | The average number of bytes read per second from the RocksDB state store. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| bytes-read-total              | The total number of bytes read from the RocksDB state store. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| memtable-bytes-flushed-rate   | The average number of bytes flushed per second from the memtable to disk. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| memtable-bytes-flushed-total  | The total number of bytes flushed from the memtable to disk. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| memtable-hit-ratio            | The ratio of memtable hits relative to all lookups to the memtable. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| block-cache-data-hit-ratio    | The ratio of block cache hits for data blocks relative to all lookups for data blocks to the block cache. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| block-cache-index-hit-ratio   | The ratio of block cache hits for index blocks relative to all lookups for index blocks to the block cache. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| block-cache-filter-hit-ratio  | The ratio of block cache hits for filter blocks relative to all lookups for filter blocks to the block cache. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| write-stall-duration-avg      | The average duration of write stalls in ms.                  | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| write-stall-duration-total    | The total duration of write stalls in ms.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| bytes-read-compaction-rate    | The average number of bytes read per second during compaction. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| bytes-written-compaction-rate | The average number of bytes written per second during compaction. | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| number-open-files             | The number of current open files.                            | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |
| number-file-errors-total      | The total number of file errors occurred.                    | kafka.streams:type=stream-state-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),[store-scope]-id=([-.\w]+) |

##### [Record Cache Metrics](http://kafka.apache.org/documentation/#kafka_streams_cache_monitoring)

All of the following metrics have a recording level of `debug`:

| METRIC/ATTRIBUTE NAME | DESCRIPTION                                                  | MBEAN NAME                                                   |
| :-------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| hit-ratio-avg         | The average cache hit ratio defined as the ratio of cache read hits over the total cache read requests. | kafka.streams:type=stream-record-cache-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),record-cache-id=([-.\w]+) |
| hit-ratio-min         | The mininum cache hit ratio.                                 | kafka.streams:type=stream-record-cache-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),record-cache-id=([-.\w]+) |
| hit-ratio-max         | The maximum cache hit ratio.                                 | kafka.streams:type=stream-record-cache-metrics,thread-id=([-.\w]+),task-id=([-.\w]+),record-cache-id=([-.\w]+) |

#### [Others](http://kafka.apache.org/documentation/#others_monitoring)

We recommend monitoring GC time and other stats and various server stats such as CPU utilization, I/O service time, etc. On the client side, we recommend monitoring the message/byte rate (global and per topic), request rate/size/time, and on the consumer side, max lag in messages among all partitions and min fetch request rate. For a consumer to keep up, max lag needs to be less than a threshold and min fetch rate needs to be larger than 0.

### [6.7 ZooKeeper](http://kafka.apache.org/documentation/#zk)

#### [Stable version](http://kafka.apache.org/documentation/#zkversion)

The current stable branch is 3.5. Kafka is regularly updated to include the latest release in the 3.5 series.

#### [Operationalizing ZooKeeper](http://kafka.apache.org/documentation/#zkops)

Operationally, we do the following for a healthy ZooKeeper installation:

- Redundancy in the physical/hardware/network layout: try not to put them all in the same rack, decent (but don't go nuts) hardware, try to keep redundant power and network paths, etc. A typical ZooKeeper ensemble has 5 or 7 servers, which tolerates 2 and 3 servers down, respectively. If you have a small deployment, then using 3 servers is acceptable, but keep in mind that you'll only be able to tolerate 1 server down in this case.
- I/O segregation: if you do a lot of write type traffic you'll almost definitely want the transaction logs on a dedicated disk group. Writes to the transaction log are synchronous (but batched for performance), and consequently, concurrent writes can significantly affect performance. ZooKeeper snapshots can be one such a source of concurrent writes, and ideally should be written on a disk group separate from the transaction log. Snapshots are written to disk asynchronously, so it is typically ok to share with the operating system and message log files. You can configure a server to use a separate disk group with the dataLogDir parameter.
- Application segregation: Unless you really understand the application patterns of other apps that you want to install on the same box, it can be a good idea to run ZooKeeper in isolation (though this can be a balancing act with the capabilities of the hardware).
- Use care with virtualization: It can work, depending on your cluster layout and read/write patterns and SLAs, but the tiny overheads introduced by the virtualization layer can add up and throw off ZooKeeper, as it can be very time sensitive
- ZooKeeper configuration: It's java, make sure you give it 'enough' heap space (We usually run them with 3-5G, but that's mostly due to the data set size we have here). Unfortunately we don't have a good formula for it, but keep in mind that allowing for more ZooKeeper state means that snapshots can become large, and large snapshots affect recovery time. In fact, if the snapshot becomes too large (a few gigabytes), then you may need to increase the initLimit parameter to give enough time for servers to recover and join the ensemble.
- Monitoring: Both JMX and the 4 letter words (4lw) commands are very useful, they do overlap in some cases (and in those cases we prefer the 4 letter commands, they seem more predictable, or at the very least, they work better with the LI monitoring infrastructure)
- Don't overbuild the cluster: large clusters, especially in a write heavy usage pattern, means a lot of intracluster communication (quorums on the writes and subsequent cluster member updates), but don't underbuild it (and risk swamping the cluster). Having more servers adds to your read capacity.

Overall, we try to keep the ZooKeeper system as small as will handle the load (plus standard growth capacity planning) and as simple as possible. We try not to do anything fancy with the configuration or application layout as compared to the official release as well as keep it as self contained as possible. For these reasons, we tend to skip the OS packaged versions, since it has a tendency to try to put things in the OS standard hierarchy, which can be 'messy', for want of a better way to word it.

## [7. SECURITY](http://kafka.apache.org/documentation/#security)

### [7.1 Security Overview](http://kafka.apache.org/documentation/#security_overview)

In release 0.9.0.0, the Kafka community added a number of features that, used either separately or together, increases security in a Kafka cluster. The following security measures are currently supported:

1. Authentication of connections to brokers from clients (producers and consumers), other brokers and tools, using either SSL or SASL. Kafka supports the following SASL mechanisms:
   - SASL/GSSAPI (Kerberos) - starting at version 0.9.0.0
   - SASL/PLAIN - starting at version 0.10.0.0
   - SASL/SCRAM-SHA-256 and SASL/SCRAM-SHA-512 - starting at version 0.10.2.0
   - SASL/OAUTHBEARER - starting at version 2.0
2. Authentication of connections from brokers to ZooKeeper
3. Encryption of data transferred between brokers and clients, between brokers, or between brokers and tools using SSL (Note that there is a performance degradation when SSL is enabled, the magnitude of which depends on the CPU type and the JVM implementation.)
4. Authorization of read / write operations by clients
5. Authorization is pluggable and integration with external authorization services is supported

It's worth noting that security is optional - non-secured clusters are supported, as well as a mix of authenticated, unauthenticated, encrypted and non-encrypted clients. The guides below explain how to configure and use the security features in both clients and brokers.

### [7.2 Encryption and Authentication using SSL](http://kafka.apache.org/documentation/#security_ssl)

Apache Kafka allows clients to use SSL for encryption of traffic as well as authentication. By default, SSL is disabled but can be turned on if needed. The following paragraphs explain in detail how to set up your own PKI infrastructure, use it to create certificates and configure Kafka to use these.

1. #### [Generate SSL key and certificate for each Kafka broker](http://kafka.apache.org/documentation/#security_ssl_key)

   The first step of deploying one or more brokers with SSL support is to generate a public/private keypair for every server. Since Kafka expects all keys and certificates to be stored in keystores we will use Java's keytool command for this task. The tool supports two different keystore formats, the Java specific jks format which has been deprecated by now, as well as PKCS12. PKCS12 is the default format as of Java version 9, to ensure this format is being used regardless of the Java version in use all following commands explicitly specify the PKCS12 format.

   ```bash
                   keytool -keystore {keystorefile} -alias localhost -validity {validity} -genkey -keyalg RSA -storetype pkcs12
   ```

   You need to specify two parameters in the above command:

   1. keystorefile: the keystore file that stores the keys (and later the certificate) for this broker. The keystore file contains the private and public keys of this broker, therefore it needs to be kept safe. Ideally this step is run on the Kafka broker that the key will be used on, as this key should never be transmitted/leave the server that it is intended for.
   2. validity: the valid time of the key in days. Please note that this differs from the validity period for the certificate, which will be determined in [Signing the certificate](http://kafka.apache.org/documentation/#security_ssl_signing). You can use the same key to request multiple certificates: if your key has a validity of 10 years, but your CA will only sign certificates that are valid for one year, you can use the same key with 10 certificates over time.

   To obtain a certificate that can be used with the private key that was just created a certificate signing request needs to be created. This signing request, when signed by a trusted CA results in the actual certificate which can then be installed in the keystore and used for authentication purposes.

   To generate certificate signing requests run the following command for all server keystores created so far.

   ```bash
                   keytool -keystore server.keystore.jks -alias localhost -validity {validity} -genkey -keyalg RSA -destkeystoretype pkcs12 -ext SAN=DNS:{FQDN},IP:{IPADDRESS1}
   ```

   This command assumes that you want to add hostname information to the certificate, if this is not the case, you can omit the extension parameter

    

   ```
   -ext SAN=DNS:{FQDN},IP:{IPADDRESS1}
   ```

   . Please see below for more information on this.

   ##### Host Name Verification

   Host name verification, when enabled, is the process of checking attributes from the certificate that is presented by the server you are connecting to against the actual hostname or ip address of that server to ensure that you are indeed connecting to the correct server.

   The main reason for this check is to prevent man-in-the-middle attacks. For Kafka, this check has been disabled by default for a long time, but as of Kafka 2.0.0 host name verification of servers is enabled by default for client connections as well as inter-broker connections.

   Server host name verification may be disabled by setting

    

   ```
   ssl.endpoint.identification.algorithm
   ```

    

   to an empty string.

   For dynamically configured broker listeners, hostname verification may be disabled using

    

   ```
   kafka-configs.sh
   ```

   :

   ```text
                   bin/kafka-configs.sh --bootstrap-server localhost:9093 --entity-type brokers --entity-name 0 --alter --add-config "listener.name.internal.ssl.endpoint.identification.algorithm="
   ```

   **Note:**

   Normally there is no good reason to disable hostname verification apart from being the quickest way to "just get it to work" followed by the promise to "fix it later when there is more time"!

   Getting hostname verification right is not that hard when done at the right time, but gets much harder once the cluster is up and running - do yourself a favor and do it now!

   If host name verification is enabled, clients will verify the server's fully qualified domain name (FQDN) or ip address against one of the following two fields:

   1. Common Name (CN)
   2. [Subject Alternative Name (SAN)](https://tools.ietf.org/html/rfc5280#section-4.2.1.6)

   While Kafka checks both fields, usage of the common name field for hostname verification has been

    

   deprecated

    

   since 2000 and should be avoided if possible. In addition the SAN field is much more flexible, allowing for multiple DNS and IP entries to be declared in a certificate.

   Another advantage is that if the SAN field is used for hostname verification the common name can be set to a more meaningful value for authorization purposes. Since we need the SAN field to be contained in the signed certificate, it will be specified when generating the signing request. It can also be specified when generating the keypair, but this will not automatically be copied into the signing request.

   To add a SAN field append the following argument

    

   ```
   -ext SAN=DNS:{FQDN},IP:{IPADDRESS} 
   ```

   to the keytool command:

   ```bash
                   keytool -keystore server.keystore.jks -alias localhost -validity {validity} -genkey -keyalg RSA -destkeystoretype pkcs12 -ext SAN=DNS:{FQDN},IP:{IPADDRESS1}
   ```

2. #### [Creating your own CA](http://kafka.apache.org/documentation/#security_ssl_ca)

   After this step each machine in the cluster has a public/private key pair which can already be used to encrypt traffic and a certificate signing request, which is the basis for creating a certificate. To add authentication capabilities this signing request needs to be signed by a trusted authority, which will be created in this step.

   A certificate authority (CA) is responsible for signing certificates. CAs works likes a government that issues passports - the government stamps (signs) each passport so that the passport becomes difficult to forge. Other governments verify the stamps to ensure the passport is authentic. Similarly, the CA signs the certificates, and the cryptography guarantees that a signed certificate is computationally difficult to forge. Thus, as long as the CA is a genuine and trusted authority, the clients have a strong assurance that they are connecting to the authentic machines.

   For this guide we will be our own Certificate Authority. When setting up a production cluster in a corporate environment these certificates would usually be signed by a corporate CA that is trusted throughout the company. Please see [Common Pitfalls in Production](http://kafka.apache.org/documentation/#security_ssl_production) for some things to consider for this case.

   Due to a [bug](https://www.openssl.org/docs/man1.1.1/man1/x509.html#BUGS) in OpenSSL, the x509 module will not copy requested extension fields from CSRs into the final certificate. Since we want the SAN extension to be present in our certificate to enable hostname verification, we'll use the *ca* module instead. This requires some additional configuration to be in place before we generate our CA keypair.
   Save the following listing into a file called openssl-ca.cnf and adjust the values for validity and common attributes as necessary.

   ```bash
   HOME            = .
   RANDFILE        = $ENV::HOME/.rnd
   
   ####################################################################
   [ ca ]
   default_ca    = CA_default      # The default ca section
   
   [ CA_default ]
   
   base_dir      = .
   certificate   = $base_dir/cacert.pem   # The CA certifcate
   private_key   = $base_dir/cakey.pem    # The CA private key
   new_certs_dir = $base_dir              # Location for new certs after signing
   database      = $base_dir/index.txt    # Database index file
   serial        = $base_dir/serial.txt   # The current serial number
   
   default_days     = 1000         # How long to certify for
   default_crl_days = 30           # How long before next CRL
   default_md       = sha256       # Use public key default MD
   preserve         = no           # Keep passed DN ordering
   
   x509_extensions = ca_extensions # The extensions to add to the cert
   
   email_in_dn     = no            # Don't concat the email in the DN
   copy_extensions = copy          # Required to copy SANs from CSR to cert
   
   ####################################################################
   [ req ]
   default_bits       = 4096
   default_keyfile    = cakey.pem
   distinguished_name = ca_distinguished_name
   x509_extensions    = ca_extensions
   string_mask        = utf8only
   
   ####################################################################
   [ ca_distinguished_name ]
   countryName         = Country Name (2 letter code)
   countryName_default = DE
   
   stateOrProvinceName         = State or Province Name (full name)
   stateOrProvinceName_default = Test Province
   
   localityName                = Locality Name (eg, city)
   localityName_default        = Test Town
   
   organizationName            = Organization Name (eg, company)
   organizationName_default    = Test Company
   
   organizationalUnitName         = Organizational Unit (eg, division)
   organizationalUnitName_default = Test Unit
   
   commonName         = Common Name (e.g. server FQDN or YOUR name)
   commonName_default = Test Name
   
   emailAddress         = Email Address
   emailAddress_default = test@test.com
   
   ####################################################################
   [ ca_extensions ]
   
   subjectKeyIdentifier   = hash
   authorityKeyIdentifier = keyid:always, issuer
   basicConstraints       = critical, CA:true
   keyUsage               = keyCertSign, cRLSign
   
   ####################################################################
   [ signing_policy ]
   countryName            = optional
   stateOrProvinceName    = optional
   localityName           = optional
   organizationName       = optional
   organizationalUnitName = optional
   commonName             = supplied
   emailAddress           = optional
   
   ####################################################################
   [ signing_req ]
   subjectKeyIdentifier   = hash
   authorityKeyIdentifier = keyid,issuer
   basicConstraints       = CA:FALSE
   keyUsage               = digitalSignature, keyEncipherment
   ```

   Then create a database and serial number file, these will be used to keep track of which certificates were signed with this CA. Both of these are simply text files that reside in the same directory as your CA keys.

   ```bash
                   echo 01 > serial.txt
                   touch index.txt
   ```

   With these steps done you are now ready to generate your CA that will be used to sign certificates later.

   ```bash
               openssl req -x509 -config openssl-ca.cnf -newkey rsa:4096 -sha256 -nodes -out cacert.pem -outform PEM
   ```

   The CA is simply a public/private key pair and certificate that is signed by itself, and is only intended to sign other certificates.

   This keypair should be kept very safe, if someone gains access to it, they can create and sign certificates that will be trusted by your infrastructure, which means they will be able to impersonate anybody when connecting to any service that trusts this CA.

   The next step is to add the generated CA to the **clients' truststore** so that the clients can trust this CA:

   ```bash
                   keytool -keystore client.truststore.jks -alias CARoot -import -file ca-cert
   ```

   Note:

    

   If you configure the Kafka brokers to require client authentication by setting ssl.client.auth to be "requested" or "required" in the

    

   Kafka brokers config

    

   then you must provide a truststore for the Kafka brokers as well and it should have all the CA certificates that clients' keys were signed by.

   ```bash
                   keytool -keystore server.truststore.jks -alias CARoot -import -file ca-cert
   ```

   In contrast to the keystore in step 1 that stores each machine's own identity, the truststore of a client stores all the certificates that the client should trust. Importing a certificate into one's truststore also means trusting all certificates that are signed by that certificate. As the analogy above, trusting the government (CA) also means trusting all passports (certificates) that it has issued. This attribute is called the chain of trust, and it is particularly useful when deploying SSL on a large Kafka cluster. You can sign all certificates in the cluster with a single CA, and have all machines share the same truststore that trusts the CA. That way all machines can authenticate all other machines.

3. #### [Signing the certificate](http://kafka.apache.org/documentation/#security_ssl_signing)

   Then sign it with the CA:

   ```bash
                   openssl ca -config openssl-ca.cnf -policy signing_policy -extensions signing_req -out {server certificate} -infiles {certificate signing request}
   ```

   Finally, you need to import both the certificate of the CA and the signed certificate into the keystore:

   ```bash
                   keytool -keystore {keystore} -alias CARoot -import -file {CA certificate}
                   keytool -keystore {keystore} -alias localhost -import -file cert-signed
   ```

   The definitions of the parameters are the following:

   1. keystore: the location of the keystore
   2. CA certificate: the certificate of the CA
   3. certificate signing request: the csr created with the server key
   4. server certificate: the file to write the signed certificate of the server to

   This will leave you with one truststore called

    

   truststore.jks

    

   \- this can be the same for all clients and brokers and does not contain any sensitive information, so there is no need to secure this.

   Additionally you will have one

    

   server.keystore.jks

    

   file per node which contains that nodes keys, certificate and your CAs certificate, please refer to

    

   Configuring Kafka Brokers

    

   and

    

   Configuring Kafka Clients

    

   for information on how to use these files.

   For some tooling assistance on this topic, please check out the [easyRSA](https://github.com/OpenVPN/easy-rsa) project which has extensive scripting in place to help with these steps.

4. #### [Common Pitfalls in Production](http://kafka.apache.org/documentation/#security_ssl_production)

   The above paragraphs show the process to create your own CA and use it to sign certificates for your cluster. While very useful for sandbox, dev, test, and similar systems, this is usually not the correct process to create certificates for a production cluster in a corporate environment. Enterprises will normally operate their own CA and users can send in CSRs to be signed with this CA, which has the benefit of users not being responsible to keep the CA secure as well as a central authority that everybody can trust. However it also takes away a lot of control over the process of signing certificates from the user. Quite often the persons operating corporate CAs will apply tight restrictions on certificates that can cause issues when trying to use these certificates with Kafka.

   1. [Extended Key Usage](https://tools.ietf.org/html/rfc5280#section-4.2.1.12)

      Certificates may contain an extension field that controls the purpose for which the certificate can be used. If this field is empty, there are no restricitions on the usage, but if any usage is specified in here, valid SSL implementations have to enforce these usages.

      Relevant usages for Kafka are:

      - Client authentication
      - Server authentication

      Kafka brokers need both these usages to be allowed, as for intra-cluster communication every broker will behave as both the client and the server towards other brokers. It is not uncommon for corporate CAs to have a signing profile for webservers and use this for Kafka as well, which will only contain the

       

      serverAuth

       

      usage value and cause the SSL handshake to fail.

   2. **Intermediate Certificates**
      Corporate Root CAs are often kept offline for security reasons. To enable day-to-day usage, so called intermediate CAs are created, which are then used to sign the final certificates. When importing a certificate into the keystore that was signed by an intermediate CA it is necessarry to provide the entire chain of trust up to the root CA. This can be done by simply *cat*ing the certificate files into one combined certificate file and then importing this with keytool.

   3. Failure to copy extension fields

      CA operators are often hesitant to copy and requested extension fields from CSRs and prefer to specify these themselves as this makes it harder for a malicious party to obtain certificates with potentially misleading or fraudulent values. It is adviseable to double check signed certificates, whether these contain all requested SAN fields to enable proper hostname verification. The following command can be used to print certificate details to the console, which should be compared with what was originally requested:

      ```bash
                              openssl x509 -in certificate.crt -text -noout
      ```

5. #### [Configuring Kafka Brokers](http://kafka.apache.org/documentation/#security_configbroker)

   Kafka Brokers support listening for connections on multiple ports. We need to configure the following property in server.properties, which must have one or more comma-separated values:

   ```
   listeners
   ```

   If SSL is not enabled for inter-broker communication (see below for how to enable it), both PLAINTEXT and SSL ports will be necessary.

   ```text
               listeners=PLAINTEXT://host.name:port,SSL://host.name:port
   ```

   Following SSL configs are needed on the broker side

   ```text
               ssl.keystore.location=/var/private/ssl/server.keystore.jks
               ssl.keystore.password=test1234
               ssl.key.password=test1234
               ssl.truststore.location=/var/private/ssl/server.truststore.jks
               ssl.truststore.password=test1234
   ```

   Note: ssl.truststore.password is technically optional but highly recommended. If a password is not set access to the truststore is still available, but integrity checking is disabled. Optional settings that are worth considering:

   1. ssl.client.auth=none ("required" => client authentication is required, "requested" => client authentication is requested and client without certs can still connect. The usage of "requested" is discouraged as it provides a false sense of security and misconfigured clients will still connect successfully.)
   2. ssl.cipher.suites (Optional). A cipher suite is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol. (Default is an empty list)
   3. ssl.enabled.protocols=TLSv1.2,TLSv1.1,TLSv1 (list out the SSL protocols that you are going to accept from clients. Do note that SSL is deprecated in favor of TLS and using SSL in production is not recommended)
   4. ssl.keystore.type=JKS
   5. ssl.truststore.type=JKS
   6. ssl.secure.random.implementation=SHA1PRNG

   If you want to enable SSL for inter-broker communication, add the following to the server.properties file (it defaults to PLAINTEXT)

   ```text
               security.inter.broker.protocol=SSL
   ```

   Due to import regulations in some countries, the Oracle implementation limits the strength of cryptographic algorithms available by default. If stronger algorithms are needed (for example, AES with 256-bit keys), the [JCE Unlimited Strength Jurisdiction Policy Files](http://www.oracle.com/technetwork/java/javase/downloads/index.html) must be obtained and installed in the JDK/JRE. See the [JCA Providers Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html) for more information.

   The JRE/JDK will have a default pseudo-random number generator (PRNG) that is used for cryptography operations, so it is not required to configure the implementation used with the `ssl.secure.random.implementation`. However, there are performance issues with some implementations (notably, the default chosen on Linux systems, `NativePRNG`, utilizes a global lock). In cases where performance of SSL connections becomes an issue, consider explicitly setting the implementation to be used. The `SHA1PRNG` implementation is non-blocking, and has shown very good performance characteristics under heavy load (50 MB/sec of produced messages, plus replication traffic, per-broker).

   Once you start the broker you should be able to see in the server.log

   ```text
               with addresses: PLAINTEXT -> EndPoint(192.168.64.1,9092,PLAINTEXT),SSL -> EndPoint(192.168.64.1,9093,SSL)
   ```

   To check quickly if the server keystore and truststore are setup properly you can run the following command

   ```
   openssl s_client -debug -connect localhost:9093 -tls1
   ```

   (Note: TLSv1 should be listed under ssl.enabled.protocols)

   In the output of this command you should see server's certificate:

   ```text
               -----BEGIN CERTIFICATE-----
               {variable sized random bytes}
               -----END CERTIFICATE-----
               subject=/C=US/ST=CA/L=Santa Clara/O=org/OU=org/CN=Sriharsha Chintalapani
               issuer=/C=US/ST=CA/L=Santa Clara/O=org/OU=org/CN=kafka/emailAddress=test@test.com
   ```

   If the certificate does not show up or if there are any other error messages then your keystore is not setup properly.

6. #### [Configuring Kafka Clients](http://kafka.apache.org/documentation/#security_configclients)

   SSL is supported only for the new Kafka Producer and Consumer, the older API is not supported. The configs for SSL will be the same for both producer and consumer.

   If client authentication is not required in the broker, then the following is a minimal configuration example:

   ```text
               security.protocol=SSL
               ssl.truststore.location=/var/private/ssl/client.truststore.jks
               ssl.truststore.password=test1234
   ```

   Note: ssl.truststore.password is technically optional but highly recommended. If a password is not set access to the truststore is still available, but integrity checking is disabled. If client authentication is required, then a keystore must be created like in step 1 and the following must also be configured:

   ```text
               ssl.keystore.location=/var/private/ssl/client.keystore.jks
               ssl.keystore.password=test1234
               ssl.key.password=test1234
   ```

   Other configuration settings that may also be needed depending on our requirements and the broker configuration:

   1. ssl.provider (Optional). The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.
   2. ssl.cipher.suites (Optional). A cipher suite is a named combination of authentication, encryption, MAC and key exchange algorithm used to negotiate the security settings for a network connection using TLS or SSL network protocol.
   3. ssl.enabled.protocols=TLSv1.2,TLSv1.1,TLSv1. It should list at least one of the protocols configured on the broker side
   4. ssl.truststore.type=JKS
   5. ssl.keystore.type=JKS

   Examples using console-producer and console-consumer:

   ```bash
               kafka-console-producer.sh --bootstrap-server localhost:9093 --topic test --producer.config client-ssl.properties
               kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic test --consumer.config client-ssl.properties
   ```

### [7.3 Authentication using SASL](http://kafka.apache.org/documentation/#security_sasl)

1. #### [JAAS configuration](http://kafka.apache.org/documentation/#security_sasl_jaasconfig)

   Kafka uses the Java Authentication and Authorization Service ([JAAS](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASRefGuide.html)) for SASL configuration.

   1. ##### [JAAS configuration for Kafka brokers](http://kafka.apache.org/documentation/#security_jaas_broker)

      `KafkaServer` is the section name in the JAAS file used by each KafkaServer/Broker. This section provides SASL configuration options for the broker including any SASL client connections made by the broker for inter-broker communication. If multiple listeners are configured to use SASL, the section name may be prefixed with the listener name in lower-case followed by a period, e.g. `sasl_ssl.KafkaServer`.

      `Client` section is used to authenticate a SASL connection with zookeeper. It also allows the brokers to set SASL ACL on zookeeper nodes which locks these nodes down so that only the brokers can modify it. It is necessary to have the same principal name across all brokers. If you want to use a section name other than Client, set the system property `zookeeper.sasl.clientconfig` to the appropriate name (*e.g.*, `-Dzookeeper.sasl.clientconfig=ZkClient`).

      ZooKeeper uses "zookeeper" as the service name by default. If you want to change this, set the system property `zookeeper.sasl.client.username` to the appropriate name (*e.g.*, `-Dzookeeper.sasl.client.username=zk`).

      Brokers may also configure JAAS using the broker configuration property `sasl.jaas.config`. The property name must be prefixed with the listener prefix including the SASL mechanism, i.e. `listener.name.{listenerName}.{saslMechanism}.sasl.jaas.config`. Only one login module may be specified in the config value. If multiple mechanisms are configured on a listener, configs must be provided for each mechanism using the listener and mechanism prefix. For example,

      ```text
              listener.name.sasl_ssl.scram-sha-256.sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required \
                  username="admin" \
                  password="admin-secret";
              listener.name.sasl_ssl.plain.sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required \
                  username="admin" \
                  password="admin-secret" \
                  user_admin="admin-secret" \
                  user_alice="alice-secret";
      ```

      If JAAS configuration is defined at different levels, the order of precedence used is:

      - Broker configuration property `listener.name.{listenerName}.{saslMechanism}.sasl.jaas.config`
      - `{listenerName}.KafkaServer` section of static JAAS configuration
      - `KafkaServer` section of static JAAS configuration

      Note that ZooKeeper JAAS config may only be configured using static JAAS configuration.

      See [GSSAPI (Kerberos)](http://kafka.apache.org/documentation/#security_sasl_kerberos_brokerconfig), [PLAIN](http://kafka.apache.org/documentation/#security_sasl_plain_brokerconfig), [SCRAM](http://kafka.apache.org/documentation/#security_sasl_scram_brokerconfig) or [OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_brokerconfig) for example broker configurations.

   2. ##### [JAAS configuration for Kafka clients](http://kafka.apache.org/documentation/#security_jaas_client)

      Clients may configure JAAS using the client configuration property [sasl.jaas.config](http://kafka.apache.org/documentation/#security_client_dynamicjaas) or using the [static JAAS config file](http://kafka.apache.org/documentation/#security_client_staticjaas) similar to brokers.

      1. ###### [JAAS configuration using client configuration property](http://kafka.apache.org/documentation/#security_client_dynamicjaas)

         Clients may specify JAAS configuration as a producer or consumer property without creating a physical configuration file. This mode also enables different producers and consumers within the same JVM to use different credentials by specifying different properties for each client. If both static JAAS configuration system property `java.security.auth.login.config` and client property `sasl.jaas.config` are specified, the client property will be used.

         See [GSSAPI (Kerberos)](http://kafka.apache.org/documentation/#security_sasl_kerberos_clientconfig), [PLAIN](http://kafka.apache.org/documentation/#security_sasl_plain_clientconfig), [SCRAM](http://kafka.apache.org/documentation/#security_sasl_scram_clientconfig) or [OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_clientconfig) for example configurations.

      2. ###### [JAAS configuration using static config file](http://kafka.apache.org/documentation/#security_client_staticjaas)

         To configure SASL authentication on the clients using static JAAS config file:

         1. Add a JAAS config file with a client login section named

             

            KafkaClient

            . Configure a login module in

             

            KafkaClient

             

            for the selected mechanism as described in the examples for setting up

             

            GSSAPI (Kerberos)

            ,

             

            PLAIN

            ,

             

            SCRAM

             

            or

             

            OAUTHBEARER

            . For example,

             

            GSSAPI

             

            credentials may be configured as:

            ```text
                    KafkaClient {
                    com.sun.security.auth.module.Krb5LoginModule required
                    useKeyTab=true
                    storeKey=true
                    keyTab="/etc/security/keytabs/kafka_client.keytab"
                    principal="kafka-client-1@EXAMPLE.COM";
                };
            ```

         2. Pass the JAAS config file location as JVM parameter to each client JVM. For example:

            ```bash
                -Djava.security.auth.login.config=/etc/kafka/kafka_client_jaas.conf
            ```

2. #### [SASL configuration](http://kafka.apache.org/documentation/#security_sasl_config)

   SASL may be used with PLAINTEXT or SSL as the transport layer using the security protocol SASL_PLAINTEXT or SASL_SSL respectively. If SASL_SSL is used, then [SSL must also be configured](http://kafka.apache.org/documentation/#security_ssl).

   1. ##### [SASL mechanisms](http://kafka.apache.org/documentation/#security_sasl_mechanism)

      Kafka supports the following SASL mechanisms:

      - [GSSAPI](http://kafka.apache.org/documentation/#security_sasl_kerberos) (Kerberos)
      - [PLAIN](http://kafka.apache.org/documentation/#security_sasl_plain)
      - [SCRAM-SHA-256](http://kafka.apache.org/documentation/#security_sasl_scram)
      - [SCRAM-SHA-512](http://kafka.apache.org/documentation/#security_sasl_scram)
      - [OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer)

   2. ##### [SASL configuration for Kafka brokers](http://kafka.apache.org/documentation/#security_sasl_brokerconfig)

      1. Configure a SASL port in server.properties, by adding at least one of SASL_PLAINTEXT or SASL_SSL to the

          

         listeners

          

         parameter, which contains one or more comma-separated values:

         ```
             listeners=SASL_PLAINTEXT://host.name:port
         ```

         If you are only configuring a SASL port (or if you want the Kafka brokers to authenticate each other using SASL) then make sure you set the same SASL protocol for inter-broker communication:

         ```
             security.inter.broker.protocol=SASL_PLAINTEXT (or SASL_SSL)
         ```

      2. Select one or more [supported mechanisms](http://kafka.apache.org/documentation/#security_sasl_mechanism) to enable in the broker and follow the steps to configure SASL for the mechanism. To enable multiple mechanisms in the broker, follow the steps [here](http://kafka.apache.org/documentation/#security_sasl_multimechanism).

   3. ##### [SASL configuration for Kafka clients](http://kafka.apache.org/documentation/#security_sasl_clientconfig)

      SASL authentication is only supported for the new Java Kafka producer and consumer, the older API is not supported.

      To configure SASL authentication on the clients, select a SASL [mechanism](http://kafka.apache.org/documentation/#security_sasl_mechanism) that is enabled in the broker for client authentication and follow the steps to configure SASL for the selected mechanism.

3. #### [Authentication using SASL/Kerberos](http://kafka.apache.org/documentation/#security_sasl_kerberos)

   1. ##### [Prerequisites](http://kafka.apache.org/documentation/#security_sasl_kerberos_prereq)

      1. **Kerberos**
         If your organization is already using a Kerberos server (for example, by using Active Directory), there is no need to install a new server just for Kafka. Otherwise you will need to install one, your Linux vendor likely has packages for Kerberos and a short guide on how to install and configure it ([Ubuntu](https://help.ubuntu.com/community/Kerberos), [Redhat](https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/6/html/Managing_Smart_Cards/installing-kerberos.html)). Note that if you are using Oracle Java, you will need to download JCE policy files for your Java version and copy them to $JAVA_HOME/jre/lib/security.

      2. Create Kerberos Principals

         If you are using the organization's Kerberos or Active Directory server, ask your Kerberos administrator for a principal for each Kafka broker in your cluster and for every operating system user that will access Kafka with Kerberos authentication (via clients and tools).

         If you have installed your own Kerberos, you will need to create these principals yourself using the following commands:

         ```bash
                 sudo /usr/sbin/kadmin.local -q 'addprinc -randkey kafka/{hostname}@{REALM}'
                 sudo /usr/sbin/kadmin.local -q "ktadd -k /etc/security/keytabs/{keytabname}.keytab kafka/{hostname}@{REALM}"
         ```

      3. **Make sure all hosts can be reachable using hostnames** - it is a Kerberos requirement that all your hosts can be resolved with their FQDNs.

   2. ##### [Configuring Kafka Brokers](http://kafka.apache.org/documentation/#security_sasl_kerberos_brokerconfig)

      1. Add a suitably modified JAAS file similar to the one below to each Kafka broker's config directory, let's call it kafka_server_jaas.conf for this example (note that each broker should have its own keytab):

         ```text
                 KafkaServer {
                     com.sun.security.auth.module.Krb5LoginModule required
                     useKeyTab=true
                     storeKey=true
                     keyTab="/etc/security/keytabs/kafka_server.keytab"
                     principal="kafka/kafka1.hostname.com@EXAMPLE.COM";
                 };
         
                 // Zookeeper client authentication
                 Client {
                 com.sun.security.auth.module.Krb5LoginModule required
                 useKeyTab=true
                 storeKey=true
                 keyTab="/etc/security/keytabs/kafka_server.keytab"
                 principal="kafka/kafka1.hostname.com@EXAMPLE.COM";
                 };
         ```

      2. KafkaServer

      3.  

      4. section in the JAAS file tells the broker which principal to use and the location of the keytab where this principal is stored. It allows the broker to login using the keytab specified in this section. See

      5.  

      6. notes

      7.  

      8. for more details on Zookeeper SASL configuration.

      9. Pass the JAAS and optionally the krb5 file locations as JVM parameters to each Kafka broker (see

          

         here

          

         for more details):

         ```
             -Djava.security.krb5.conf=/etc/kafka/krb5.conf
                 -Djava.security.auth.login.config=/etc/kafka/kafka_server_jaas.conf
         ```

      10. Make sure the keytabs configured in the JAAS file are readable by the operating system user who is starting kafka broker.

      11. Configure SASL port and SASL mechanisms in server.properties as described

           

          here

          . For example:

          ```
              listeners=SASL_PLAINTEXT://host.name:port
                  security.inter.broker.protocol=SASL_PLAINTEXT
                  sasl.mechanism.inter.broker.protocol=GSSAPI
                  sasl.enabled.mechanisms=GSSAPI
          ```

      12. We must also configure the service name in server.properties, which should match the principal name of the kafka brokers. In the above example, principal is "kafka/kafka1.hostname.com@EXAMPLE.com", so:

      13. ```
              sasl.kerberos.service.name=kafka
          ```

   3. ##### [Configuring Kafka Clients](http://kafka.apache.org/documentation/#security_sasl_kerberos_clientconfig)

      To configure SASL authentication on the clients:

      1. Clients (producers, consumers, connect workers, etc) will authenticate to the cluster with their own principal (usually with the same name as the user running the client), so obtain or create these principals as needed. Then configure the JAAS configuration property for each client. Different clients within a JVM may run as different users by specifying different principals. The property

          

         ```
         sasl.jaas.config
         ```

          

         in producer.properties or consumer.properties describes how clients like producer and consumer can connect to the Kafka Broker. The following is an example configuration for a client using a keytab (recommended for long-running processes):

         ```text
             sasl.jaas.config=com.sun.security.auth.module.Krb5LoginModule required \
                 useKeyTab=true \
                 storeKey=true  \
                 keyTab="/etc/security/keytabs/kafka_client.keytab" \
                 principal="kafka-client-1@EXAMPLE.COM";
         ```

         For command-line utilities like kafka-console-consumer or kafka-console-producer, kinit can be used along with "useTicketCache=true" as in:

         ```text
             sasl.jaas.config=com.sun.security.auth.module.Krb5LoginModule required \
                 useTicketCache=true;
         ```

         JAAS configuration for clients may alternatively be specified as a JVM parameter similar to brokers as described

          

         here

         . Clients use the login section named

          

         KafkaClient

         . This option allows only one user for all client connections from a JVM.

      2. Make sure the keytabs configured in the JAAS configuration are readable by the operating system user who is starting kafka client.

      3. Optionally pass the krb5 file locations as JVM parameters to each client JVM (see

          

         here

          

         for more details):

         ```
             -Djava.security.krb5.conf=/etc/kafka/krb5.conf
         ```

      4. Configure the following properties in producer.properties or consumer.properties:

         ```text
             security.protocol=SASL_PLAINTEXT (or SASL_SSL)
             sasl.mechanism=GSSAPI
             sasl.kerberos.service.name=kafka
         ```

4. #### [Authentication using SASL/PLAIN](http://kafka.apache.org/documentation/#security_sasl_plain)

   SASL/PLAIN is a simple username/password authentication mechanism that is typically used with TLS for encryption to implement secure authentication. Kafka supports a default implementation for SASL/PLAIN which can be extended for production use as described [here](http://kafka.apache.org/documentation/#security_sasl_plain_production).

   The username is used as the authenticated

    

   ```
   Principal
   ```

    

   for configuration of ACLs etc.

   1. ##### [Configuring Kafka Brokers](http://kafka.apache.org/documentation/#security_sasl_plain_brokerconfig)

      1. Add a suitably modified JAAS file similar to the one below to each Kafka broker's config directory, let's call it kafka_server_jaas.conf for this example:

         ```text
                 KafkaServer {
                     org.apache.kafka.common.security.plain.PlainLoginModule required
                     username="admin"
                     password="admin-secret"
                     user_admin="admin-secret"
                     user_alice="alice-secret";
                 };
         ```

         This configuration defines two users (

         admin

          

         and

          

         alice

         ). The properties

          

         username

          

         and

          

         password

          

         in the

          

         KafkaServer

          

         section are used by the broker to initiate connections to other brokers. In this example,

          

         admin

          

         is the user for inter-broker communication. The set of properties

          

         user_*userName*

          

         defines the passwords for all users that connect to the broker and the broker validates all client connections including those from other brokers using these properties.

      2. Pass the JAAS config file location as JVM parameter to each Kafka broker:

         ```
             -Djava.security.auth.login.config=/etc/kafka/kafka_server_jaas.conf
         ```

      3. Configure SASL port and SASL mechanisms in server.properties as described

          

         here

         . For example:

         ```
             listeners=SASL_SSL://host.name:port
                 security.inter.broker.protocol=SASL_SSL
                 sasl.mechanism.inter.broker.protocol=PLAIN
                 sasl.enabled.mechanisms=PLAIN
         ```

   2. ##### [Configuring Kafka Clients](http://kafka.apache.org/documentation/#security_sasl_plain_clientconfig)

      To configure SASL authentication on the clients:

      1. Configure the JAAS configuration property for each client in producer.properties or consumer.properties. The login module describes how the clients like producer and consumer can connect to the Kafka Broker. The following is an example configuration for a client for the PLAIN mechanism:

         ```text
             sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required \
                 username="alice" \
                 password="alice-secret";
         ```

         The options `username` and `password` are used by clients to configure the user for client connections. In this example, clients connect to the broker as user *alice*. Different clients within a JVM may connect as different users by specifying different user names and passwords in `sasl.jaas.config`.

         JAAS configuration for clients may alternatively be specified as a JVM parameter similar to brokers as described [here](http://kafka.apache.org/documentation/#security_client_staticjaas). Clients use the login section named `KafkaClient`. This option allows only one user for all client connections from a JVM.

      2. Configure the following properties in producer.properties or consumer.properties:

         ```text
             security.protocol=SASL_SSL
             sasl.mechanism=PLAIN
         ```

   3. ##### [Use of SASL/PLAIN in production](http://kafka.apache.org/documentation/#security_sasl_plain_production)

      - SASL/PLAIN should be used only with SSL as transport layer to ensure that clear passwords are not transmitted on the wire without encryption.
      - The default implementation of SASL/PLAIN in Kafka specifies usernames and passwords in the JAAS configuration file as shown [here](http://kafka.apache.org/documentation/#security_sasl_plain_brokerconfig). From Kafka version 2.0 onwards, you can avoid storing clear passwords on disk by configuring your own callback handlers that obtain username and password from an external source using the configuration options `sasl.server.callback.handler.class` and `sasl.client.callback.handler.class`.
      - In production systems, external authentication servers may implement password authentication. From Kafka version 2.0 onwards, you can plug in your own callback handlers that use external authentication servers for password verification by configuring `sasl.server.callback.handler.class`.

5. #### [Authentication using SASL/SCRAM](http://kafka.apache.org/documentation/#security_sasl_scram)

   Salted Challenge Response Authentication Mechanism (SCRAM) is a family of SASL mechanisms that addresses the security concerns with traditional mechanisms that perform username/password authentication like PLAIN and DIGEST-MD5. The mechanism is defined in [RFC 5802](https://tools.ietf.org/html/rfc5802). Kafka supports [SCRAM-SHA-256](https://tools.ietf.org/html/rfc7677) and SCRAM-SHA-512 which can be used with TLS to perform secure authentication. The username is used as the authenticated `Principal` for configuration of ACLs etc. The default SCRAM implementation in Kafka stores SCRAM credentials in Zookeeper and is suitable for use in Kafka installations where Zookeeper is on a private network. Refer to [Security Considerations](http://kafka.apache.org/documentation/#security_sasl_scram_security) for more details.

   1. ##### [Creating SCRAM Credentials](http://kafka.apache.org/documentation/#security_sasl_scram_credentials)

      The SCRAM implementation in Kafka uses Zookeeper as credential store. Credentials can be created in Zookeeper using `kafka-configs.sh`. For each SCRAM mechanism enabled, credentials must be created by adding a config with the mechanism name. Credentials for inter-broker communication must be created before Kafka brokers are started. Client credentials may be created and updated dynamically and updated credentials will be used to authenticate new connections.

      Create SCRAM credentials for user *alice* with password *alice-secret*:

      ```bash
          > bin/kafka-configs.sh --zookeeper localhost:2182 --zk-tls-config-file zk_tls_config.properties --alter --add-config 'SCRAM-SHA-256=[iterations=8192,password=alice-secret],SCRAM-SHA-512=[password=alice-secret]' --entity-type users --entity-name alice
      ```

      The default iteration count of 4096 is used if iterations are not specified. A random salt is created and the SCRAM identity consisting of salt, iterations, StoredKey and ServerKey are stored in Zookeeper. See [RFC 5802](https://tools.ietf.org/html/rfc5802) for details on SCRAM identity and the individual fields.

      The following examples also require a user *admin* for inter-broker communication which can be created using:

      ```bash
          > bin/kafka-configs.sh --zookeeper localhost:2182 --zk-tls-config-file zk_tls_config.properties --alter --add-config 'SCRAM-SHA-256=[password=admin-secret],SCRAM-SHA-512=[password=admin-secret]' --entity-type users --entity-name admin
      ```

      Existing credentials may be listed using the *--describe* option:

      ```bash
          > bin/kafka-configs.sh --zookeeper localhost:2182 --zk-tls-config-file zk_tls_config.properties --describe --entity-type users --entity-name alice
      ```

      Credentials may be deleted for one or more SCRAM mechanisms using the *--alter --delete-config* option:

      ```bash
          > bin/kafka-configs.sh --zookeeper localhost:2182 --zk-tls-config-file zk_tls_config.properties --alter --delete-config 'SCRAM-SHA-512' --entity-type users --entity-name alice
      ```

   2. ##### [Configuring Kafka Brokers](http://kafka.apache.org/documentation/#security_sasl_scram_brokerconfig)

      1. Add a suitably modified JAAS file similar to the one below to each Kafka broker's config directory, let's call it kafka_server_jaas.conf for this example:

         ```text
             KafkaServer {
                 org.apache.kafka.common.security.scram.ScramLoginModule required
                 username="admin"
                 password="admin-secret";
             };
         ```

         The properties

          

         username

          

         and

          

         password

          

         in the

          

         KafkaServer

          

         section are used by the broker to initiate connections to other brokers. In this example,

          

         admin

          

         is the user for inter-broker communication.

      2. Pass the JAAS config file location as JVM parameter to each Kafka broker:

         ```
             -Djava.security.auth.login.config=/etc/kafka/kafka_server_jaas.conf
         ```

      3. Configure SASL port and SASL mechanisms in server.properties as described

          

         here

         . For example:

         ```text
             listeners=SASL_SSL://host.name:port
             security.inter.broker.protocol=SASL_SSL
             sasl.mechanism.inter.broker.protocol=SCRAM-SHA-256 (or SCRAM-SHA-512)
             sasl.enabled.mechanisms=SCRAM-SHA-256 (or SCRAM-SHA-512)
         ```

   3. ##### [Configuring Kafka Clients](http://kafka.apache.org/documentation/#security_sasl_scram_clientconfig)

      To configure SASL authentication on the clients:

      1. Configure the JAAS configuration property for each client in producer.properties or consumer.properties. The login module describes how the clients like producer and consumer can connect to the Kafka Broker. The following is an example configuration for a client for the SCRAM mechanisms:

         ```text
            sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required \
                 username="alice" \
                 password="alice-secret";
         ```

         The options `username` and `password` are used by clients to configure the user for client connections. In this example, clients connect to the broker as user *alice*. Different clients within a JVM may connect as different users by specifying different user names and passwords in `sasl.jaas.config`.

         JAAS configuration for clients may alternatively be specified as a JVM parameter similar to brokers as described [here](http://kafka.apache.org/documentation/#security_client_staticjaas). Clients use the login section named `KafkaClient`. This option allows only one user for all client connections from a JVM.

      2. Configure the following properties in producer.properties or consumer.properties:

         ```text
             security.protocol=SASL_SSL
             sasl.mechanism=SCRAM-SHA-256 (or SCRAM-SHA-512)
         ```

   4. ##### [Security Considerations for SASL/SCRAM](http://kafka.apache.org/documentation/#security_sasl_scram_security)

      - The default implementation of SASL/SCRAM in Kafka stores SCRAM credentials in Zookeeper. This is suitable for production use in installations where Zookeeper is secure and on a private network.
      - Kafka supports only the strong hash functions SHA-256 and SHA-512 with a minimum iteration count of 4096. Strong hash functions combined with strong passwords and high iteration counts protect against brute force attacks if Zookeeper security is compromised.
      - SCRAM should be used only with TLS-encryption to prevent interception of SCRAM exchanges. This protects against dictionary or brute force attacks and against impersonation if Zookeeper is compromised.
      - From Kafka version 2.0 onwards, the default SASL/SCRAM credential store may be overridden using custom callback handlers by configuring `sasl.server.callback.handler.class` in installations where Zookeeper is not secure.
      - For more details on security considerations, refer to [RFC 5802](https://tools.ietf.org/html/rfc5802#section-9).

6. #### [Authentication using SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer)

   The [OAuth 2 Authorization Framework](https://tools.ietf.org/html/rfc6749) "enables a third-party application to obtain limited access to an HTTP service, either on behalf of a resource owner by orchestrating an approval interaction between the resource owner and the HTTP service, or by allowing the third-party application to obtain access on its own behalf." The SASL OAUTHBEARER mechanism enables the use of the framework in a SASL (i.e. a non-HTTP) context; it is defined in [RFC 7628](https://tools.ietf.org/html/rfc7628). The default OAUTHBEARER implementation in Kafka creates and validates [Unsecured JSON Web Tokens](https://tools.ietf.org/html/rfc7515#appendix-A.5) and is only suitable for use in non-production Kafka installations. Refer to [Security Considerations](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_security) for more details.

   1. ##### [Configuring Kafka Brokers](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_brokerconfig)

      1. Add a suitably modified JAAS file similar to the one below to each Kafka broker's config directory, let's call it kafka_server_jaas.conf for this example:

         ```text
             KafkaServer {
                 org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required
                 unsecuredLoginStringClaim_sub="admin";
             };
         ```

         The property

          

         unsecuredLoginStringClaim_sub

          

         in the

          

         KafkaServer

          

         section is used by the broker when it initiates connections to other brokers. In this example,

          

         admin

          

         will appear in the subject (

         sub

         ) claim and will be the user for inter-broker communication.

      2. Pass the JAAS config file location as JVM parameter to each Kafka broker:

         ```
             -Djava.security.auth.login.config=/etc/kafka/kafka_server_jaas.conf
         ```

      3. Configure SASL port and SASL mechanisms in server.properties as described

          

         here

         . For example:

         ```text
             listeners=SASL_SSL://host.name:port (or SASL_PLAINTEXT if non-production)
             security.inter.broker.protocol=SASL_SSL (or SASL_PLAINTEXT if non-production)
             sasl.mechanism.inter.broker.protocol=OAUTHBEARER
             sasl.enabled.mechanisms=OAUTHBEARER
         ```

   2. ##### [Configuring Kafka Clients](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_clientconfig)

      To configure SASL authentication on the clients:

      1. Configure the JAAS configuration property for each client in producer.properties or consumer.properties. The login module describes how the clients like producer and consumer can connect to the Kafka Broker. The following is an example configuration for a client for the OAUTHBEARER mechanisms:

         ```text
            sasl.jaas.config=org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required \
                 unsecuredLoginStringClaim_sub="alice";
         ```

         The option `unsecuredLoginStringClaim_sub` is used by clients to configure the subject (`sub`) claim, which determines the user for client connections. In this example, clients connect to the broker as user *alice*. Different clients within a JVM may connect as different users by specifying different subject (`sub`) claims in `sasl.jaas.config`.

         JAAS configuration for clients may alternatively be specified as a JVM parameter similar to brokers as described [here](http://kafka.apache.org/documentation/#security_client_staticjaas). Clients use the login section named `KafkaClient`. This option allows only one user for all client connections from a JVM.

      2. Configure the following properties in producer.properties or consumer.properties:

         ```text
             security.protocol=SASL_SSL (or SASL_PLAINTEXT if non-production)
             sasl.mechanism=OAUTHBEARER
         ```

      3. The default implementation of SASL/OAUTHBEARER depends on the jackson-databind library. Since it's an optional dependency, users have to configure it as a dependency via their build tool.

   3. ##### [Unsecured Token Creation Options for SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_unsecured_retrieval)

      - The default implementation of SASL/OAUTHBEARER in Kafka creates and validates [Unsecured JSON Web Tokens](https://tools.ietf.org/html/rfc7515#appendix-A.5). While suitable only for non-production use, it does provide the flexibility to create arbitrary tokens in a DEV or TEST environment.

      - Here are the various supported JAAS module options on the client side (and on the broker side if OAUTHBEARER is the inter-broker protocol):

        | JAAS Module Option for Unsecured Token Creation   | Documentation                                                |
        | :------------------------------------------------ | :----------------------------------------------------------- |
        | `unsecuredLoginStringClaim_<claimname>="value"`   | Creates a `String` claim with the given name and value. Any valid claim name can be specified except '`iat`' and '`exp`' (these are automatically generated). |
        | `unsecuredLoginNumberClaim_<claimname>="value"`   | Creates a `Number` claim with the given name and value. Any valid claim name can be specified except '`iat`' and '`exp`' (these are automatically generated). |
        | `unsecuredLoginListClaim_<claimname>="value"`     | Creates a `String List` claim with the given name and values parsed from the given value where the first character is taken as the delimiter. For example: `unsecuredLoginListClaim_fubar="|value1|value2"`. Any valid claim name can be specified except '`iat`' and '`exp`' (these are automatically generated). |
        | `unsecuredLoginExtension_<extensionname>="value"` | Creates a `String` extension with the given name and value. For example: `unsecuredLoginExtension_traceId="123"`. A valid extension name is any sequence of lowercase or uppercase alphabet characters. In addition, the "auth" extension name is reserved. A valid extension value is any combination of characters with ASCII codes 1-127. |
        | `unsecuredLoginPrincipalClaimName`                | Set to a custom claim name if you wish the name of the `String` claim holding the principal name to be something other than '`sub`'. |
        | `unsecuredLoginLifetimeSeconds`                   | Set to an integer value if the token expiration is to be set to something other than the default value of 3600 seconds (which is 1 hour). The '`exp`' claim will be set to reflect the expiration time. |
        | `unsecuredLoginScopeClaimName`                    | Set to a custom claim name if you wish the name of the `String` or `String List` claim holding any token scope to be something other than '`scope`'. |

   4. ##### [Unsecured Token Validation Options for SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_unsecured_validation)

      - Here are the various supported JAAS module options on the broker side for

         

        Unsecured JSON Web Token

         

        validation:

        | JAAS Module Option for Unsecured Token Validation | Documentation                                                |
        | :------------------------------------------------ | :----------------------------------------------------------- |
        | `unsecuredValidatorPrincipalClaimName="value"`    | Set to a non-empty value if you wish a particular `String` claim holding a principal name to be checked for existence; the default is to check for the existence of the '`sub`' claim. |
        | `unsecuredValidatorScopeClaimName="value"`        | Set to a custom claim name if you wish the name of the `String` or `String List` claim holding any token scope to be something other than '`scope`'. |
        | `unsecuredValidatorRequiredScope="value"`         | Set to a space-delimited list of scope values if you wish the `String/String List` claim holding the token scope to be checked to make sure it contains certain values. |
        | `unsecuredValidatorAllowableClockSkewMs="value"`  | Set to a positive integer value if you wish to allow up to some number of positive milliseconds of clock skew (the default is 0). |

      - The default unsecured SASL/OAUTHBEARER implementation may be overridden (and must be overridden in production environments) using custom login and SASL Server callback handlers.

      - For more details on security considerations, refer to [RFC 6749, Section 10](https://tools.ietf.org/html/rfc6749#section-10).

   5. ##### [Token Refresh for SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_refresh)

      Kafka periodically refreshes any token before it expires so that the client can continue to make connections to brokers. The parameters that impact how the refresh algorithm operates are specified as part of the producer/consumer/broker configuration and are as follows. See the documentation for these properties elsewhere for details. The default values are usually reasonable, in which case these configuration parameters would not need to be explicitly set.

      | Producer/Consumer/Broker Configuration Property |
      | :---------------------------------------------- |
      | `sasl.login.refresh.window.factor`              |
      | `sasl.login.refresh.window.jitter`              |
      | `sasl.login.refresh.min.period.seconds`         |
      | `sasl.login.refresh.min.buffer.seconds`         |

   6. ##### [Secure/Production Use of SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_prod)

      Production use cases will require writing an implementation of

       

      org.apache.kafka.common.security.auth.AuthenticateCallbackHandler

       

      that can handle an instance of

       

      org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback

       

      and declaring it via either the

       

      sasl.login.callback.handler.class

       

      configuration option for a non-broker client or via the

       

      listener.name.sasl_ssl.oauthbearer.sasl.login.callback.handler.class

       

      configuration option for brokers (when SASL/OAUTHBEARER is the inter-broker protocol).

      Production use cases will also require writing an implementation of `org.apache.kafka.common.security.auth.AuthenticateCallbackHandler` that can handle an instance of `org.apache.kafka.common.security.oauthbearer.OAuthBearerValidatorCallback` and declaring it via the `listener.name.sasl_ssl.oauthbearer.sasl.server.callback.handler.class` broker configuration option.

   7. ##### [Security Considerations for SASL/OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_security)

      - The default implementation of SASL/OAUTHBEARER in Kafka creates and validates [Unsecured JSON Web Tokens](https://tools.ietf.org/html/rfc7515#appendix-A.5). This is suitable only for non-production use.
      - OAUTHBEARER should be used in production enviromnments only with TLS-encryption to prevent interception of tokens.
      - The default unsecured SASL/OAUTHBEARER implementation may be overridden (and must be overridden in production environments) using custom login and SASL Server callback handlers as described above.
      - For more details on OAuth 2 security considerations in general, refer to [RFC 6749, Section 10](https://tools.ietf.org/html/rfc6749#section-10).

7. #### [Enabling multiple SASL mechanisms in a broker](http://kafka.apache.org/documentation/#security_sasl_multimechanism)

   1. Specify configuration for the login modules of all enabled mechanisms in the

       

      KafkaServer

       

      section of the JAAS config file. For example:

      ```text
              KafkaServer {
                  com.sun.security.auth.module.Krb5LoginModule required
                  useKeyTab=true
                  storeKey=true
                  keyTab="/etc/security/keytabs/kafka_server.keytab"
                  principal="kafka/kafka1.hostname.com@EXAMPLE.COM";
      
                  org.apache.kafka.common.security.plain.PlainLoginModule required
                  username="admin"
                  password="admin-secret"
                  user_admin="admin-secret"
                  user_alice="alice-secret";
              };
      ```

   2. Enable the SASL mechanisms in server.properties:

      ```
          sasl.enabled.mechanisms=GSSAPI,PLAIN,SCRAM-SHA-256,SCRAM-SHA-512,OAUTHBEARER
      ```

   3. Specify the SASL security protocol and mechanism for inter-broker communication in server.properties if required:

      ```text
          security.inter.broker.protocol=SASL_PLAINTEXT (or SASL_SSL)
          sasl.mechanism.inter.broker.protocol=GSSAPI (or one of the other enabled mechanisms)
      ```

   4. Follow the mechanism-specific steps in [GSSAPI (Kerberos)](http://kafka.apache.org/documentation/#security_sasl_kerberos_brokerconfig), [PLAIN](http://kafka.apache.org/documentation/#security_sasl_plain_brokerconfig), [SCRAM](http://kafka.apache.org/documentation/#security_sasl_scram_brokerconfig) and [OAUTHBEARER](http://kafka.apache.org/documentation/#security_sasl_oauthbearer_brokerconfig) to configure SASL for the enabled mechanisms.

8. #### [Modifying SASL mechanism in a Running Cluster](http://kafka.apache.org/documentation/#saslmechanism_rolling_upgrade)

   SASL mechanism can be modified in a running cluster using the following sequence:

   1. Enable new SASL mechanism by adding the mechanism to `sasl.enabled.mechanisms` in server.properties for each broker. Update JAAS config file to include both mechanisms as described [here](http://kafka.apache.org/documentation/#security_sasl_multimechanism). Incrementally bounce the cluster nodes.
   2. Restart clients using the new mechanism.
   3. To change the mechanism of inter-broker communication (if this is required), set `sasl.mechanism.inter.broker.protocol` in server.properties to the new mechanism and incrementally bounce the cluster again.
   4. To remove old mechanism (if this is required), remove the old mechanism from `sasl.enabled.mechanisms` in server.properties and remove the entries for the old mechanism from JAAS config file. Incrementally bounce the cluster again.

9. #### [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token)

   Delegation token based authentication is a lightweight authentication mechanism to complement existing SASL/SSL methods. Delegation tokens are shared secrets between kafka brokers and clients. Delegation tokens will help processing frameworks to distribute the workload to available workers in a secure environment without the added cost of distributing Kerberos TGT/keytabs or keystores when 2-way SSL is used. See [KIP-48](https://cwiki.apache.org/confluence/display/KAFKA/KIP-48+Delegation+token+support+for+Kafka) for more details.

   Typical steps for delegation token usage are:

   1. User authenticates with the Kafka cluster via SASL or SSL, and obtains a delegation token. This can be done using Admin APIs or using `kafka-delegation-tokens.sh` script.
   2. User securely passes the delegation token to Kafka clients for authenticating with the Kafka cluster.
   3. Token owner/renewer can renew/expire the delegation tokens.

   1. ##### [Token Management](http://kafka.apache.org/documentation/#security_token_management)

      A master key/secret is used to generate and verify delegation tokens. This is supplied using config option `delegation.token.master.key`. Same secret key must be configured across all the brokers. If the secret is not set or set to empty string, brokers will disable the delegation token authentication.

      In current implementation, token details are stored in Zookeeper and is suitable for use in Kafka installations where Zookeeper is on a private network. Also currently, master key/secret is stored as plain text in server.properties config file. We intend to make these configurable in a future Kafka release.

      A token has a current life, and a maximum renewable life. By default, tokens must be renewed once every 24 hours for up to 7 days. These can be configured using `delegation.token.expiry.time.ms` and `delegation.token.max.lifetime.ms` config options.

      Tokens can also be cancelled explicitly. If a token is not renewed by the token’s expiration time or if token is beyond the max life time, it will be deleted from all broker caches as well as from zookeeper.

   2. ##### [Creating Delegation Tokens](http://kafka.apache.org/documentation/#security_sasl_create_tokens)

      Tokens can be created by using Admin APIs or using `kafka-delegation-tokens.sh` script. Delegation token requests (create/renew/expire/describe) should be issued only on SASL or SSL authenticated channels. Tokens can not be requests if the initial authentication is done through delegation token. `kafka-delegation-tokens.sh` script examples are given below.

      Create a delegation token:

      ```bash
          > bin/kafka-delegation-tokens.sh --bootstrap-server localhost:9092 --create   --max-life-time-period -1 --command-config client.properties --renewer-principal User:user1
      ```

      Renew a delegation token:

      ```bash
          > bin/kafka-delegation-tokens.sh --bootstrap-server localhost:9092 --renew    --renew-time-period -1 --command-config client.properties --hmac ABCDEFGHIJK
      ```

      Expire a delegation token:

      ```bash
          > bin/kafka-delegation-tokens.sh --bootstrap-server localhost:9092 --expire   --expiry-time-period -1   --command-config client.properties  --hmac ABCDEFGHIJK
      ```

      Existing tokens can be described using the --describe option:

      ```bash
          > bin/kafka-delegation-tokens.sh --bootstrap-server localhost:9092 --describe --command-config client.properties  --owner-principal User:user1
      ```

   3. ##### [Token Authentication](http://kafka.apache.org/documentation/#security_token_authentication)

      Delegation token authentication piggybacks on the current SASL/SCRAM authentication mechanism. We must enable SASL/SCRAM mechanism on Kafka cluster as described in [here](http://kafka.apache.org/documentation/#security_sasl_scram).

      Configuring Kafka Clients:

      1. Configure the JAAS configuration property for each client in producer.properties or consumer.properties. The login module describes how the clients like producer and consumer can connect to the Kafka Broker. The following is an example configuration for a client for the token authentication:

         ```text
            sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required \
                 username="tokenID123" \
                 password="lAYYSFmLs4bTjf+lTZ1LCHR/ZZFNA==" \
                 tokenauth="true";
         ```

         The options `username` and `password` are used by clients to configure the token id and token HMAC. And the option `tokenauth` is used to indicate the server about token authentication. In this example, clients connect to the broker using token id: *tokenID123*. Different clients within a JVM may connect using different tokens by specifying different token details in `sasl.jaas.config`.

         JAAS configuration for clients may alternatively be specified as a JVM parameter similar to brokers as described [here](http://kafka.apache.org/documentation/#security_client_staticjaas). Clients use the login section named `KafkaClient`. This option allows only one user for all client connections from a JVM.

   4. ##### [Procedure to manually rotate the secret:](http://kafka.apache.org/documentation/#security_token_secret_rotation)

      We require a re-deployment when the secret needs to be rotated. During this process, already connected clients will continue to work. But any new connection requests and renew/expire requests with old tokens can fail. Steps are given below.

      1. Expire all existing tokens.
      2. Rotate the secret by rolling upgrade, and
      3. Generate new tokens

      We intend to automate this in a future Kafka release.

   5. ##### [Notes on Delegation Tokens](http://kafka.apache.org/documentation/#security_token_notes)

      - Currently, we only allow a user to create delegation token for that user only. Owner/Renewers can renew or expire tokens. Owner/renewers can always describe their own tokens. To describe others tokens, we need to add DESCRIBE permission on Token Resource.

### [7.4 Authorization and ACLs](http://kafka.apache.org/documentation/#security_authz)

Kafka ships with a pluggable Authorizer and an out-of-box authorizer implementation that uses zookeeper to store all the acls. The Authorizer is configured by setting `authorizer.class.name` in server.properties. To enable the out of the box implementation use:

```
authorizer.class.name=kafka.security.authorizer.AclAuthorizer
```

Kafka acls are defined in the general format of "Principal P is [Allowed/Denied] Operation O From Host H on any Resource R matching ResourcePattern RP". You can read more about the acl structure in [KIP-11](https://cwiki.apache.org/confluence/display/KAFKA/KIP-11+-+Authorization+Interface) and resource patterns in [KIP-290](https://cwiki.apache.org/confluence/display/KAFKA/KIP-290%3A+Support+for+Prefixed+ACLs). In order to add, remove or list acls you can use the Kafka authorizer CLI. By default, if no ResourcePatterns match a specific Resource R, then R has no associated acls, and therefore no one other than super users is allowed to access R. If you want to change that behavior, you can include the following in server.properties.

```
allow.everyone.if.no.acl.found=true
```

One can also add super users in server.properties like the following (note that the delimiter is semicolon since SSL user names may contain comma). Default PrincipalType string "User" is case sensitive.

```
super.users=User:Bob;User:Alice
```

##### [Customizing SSL User Name](http://kafka.apache.org/documentation/#security_authz_ssl)

By default, the SSL user name will be of the form "CN=writeuser,OU=Unknown,O=Unknown,L=Unknown,ST=Unknown,C=Unknown". One can change that by setting `ssl.principal.mapping.rules` to a customized rule in server.properties. This config allows a list of rules for mapping X.500 distinguished name to short name. The rules are evaluated in order and the first rule that matches a distinguished name is used to map it to a short name. Any later rules in the list are ignored.
The format of `ssl.principal.mapping.rules` is a list where each rule starts with "RULE:" and contains an expression as the following formats. Default rule will return string representation of the X.500 certificate distinguished name. If the distinguished name matches the pattern, then the replacement command will be run over the name. This also supports lowercase/uppercase options, to force the translated result to be all lower/uppercase case. This is done by adding a "/L" or "/U' to the end of the rule.

```text
        RULE:pattern/replacement/
        RULE:pattern/replacement/[LU]
```

Example `ssl.principal.mapping.rules` values are:

```text
        RULE:^CN=(.*?),OU=ServiceUsers.*$/$1/,
        RULE:^CN=(.*?),OU=(.*?),O=(.*?),L=(.*?),ST=(.*?),C=(.*?)$/$1@$2/L,
        RULE:^.*[Cc][Nn]=([a-zA-Z0-9.]*).*$/$1/L,
        DEFAULT
```

Above rules translate distinguished name "CN=serviceuser,OU=ServiceUsers,O=Unknown,L=Unknown,ST=Unknown,C=Unknown" to "serviceuser" and "CN=adminUser,OU=Admin,O=Unknown,L=Unknown,ST=Unknown,C=Unknown" to "adminuser@admin".
For advanced use cases, one can customize the name by setting a customized PrincipalBuilder in server.properties like the following.

```
principal.builder.class=CustomizedPrincipalBuilderClass
```

##### [Customizing SASL User Name](http://kafka.apache.org/documentation/#security_authz_sasl)

By default, the SASL user name will be the primary part of the Kerberos principal. One can change that by setting `sasl.kerberos.principal.to.local.rules` to a customized rule in server.properties. The format of `sasl.kerberos.principal.to.local.rules` is a list where each rule works in the same way as the auth_to_local in [Kerberos configuration file (krb5.conf)](http://web.mit.edu/Kerberos/krb5-latest/doc/admin/conf_files/krb5_conf.html). This also support additional lowercase/uppercase rule, to force the translated result to be all lowercase/uppercase. This is done by adding a "/L" or "/U" to the end of the rule. check below formats for syntax. Each rules starts with RULE: and contains an expression as the following formats. See the kerberos documentation for more details.

```text
        RULE:[n:string](regexp)s/pattern/replacement/
        RULE:[n:string](regexp)s/pattern/replacement/g
        RULE:[n:string](regexp)s/pattern/replacement//L
        RULE:[n:string](regexp)s/pattern/replacement/g/L
        RULE:[n:string](regexp)s/pattern/replacement//U
        RULE:[n:string](regexp)s/pattern/replacement/g/U
```

An example of adding a rule to properly translate user@MYDOMAIN.COM to user while also keeping the default rule in place is:

```
sasl.kerberos.principal.to.local.rules=RULE:[1:$1@$0](.*@MYDOMAIN.COM)s/@.*//,DEFAULT
```

#### [Command Line Interface](http://kafka.apache.org/documentation/#security_authz_cli)

Kafka Authorization management CLI can be found under bin directory with all the other CLIs. The CLI script is called **kafka-acls.sh**. Following lists all the options that the script supports:



| OPTION                                 | DESCRIPTION                                                  | DEFAULT                                                      | OPTION TYPE     |
| :------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :-------------- |
| --add                                  | Indicates to the script that user is trying to add an acl.   |                                                              | Action          |
| --remove                               | Indicates to the script that user is trying to remove an acl. |                                                              | Action          |
| --list                                 | Indicates to the script that user is trying to list acls.    |                                                              | Action          |
| --authorizer                           | Fully qualified class name of the authorizer.                | kafka.security.authorizer.AclAuthorizer                      | Configuration   |
| --authorizer-properties                | key=val pairs that will be passed to authorizer for initialization. For the default authorizer the example values are: zookeeper.connect=localhost:2181 |                                                              | Configuration   |
| --bootstrap-server                     | A list of host/port pairs to use for establishing the connection to the Kafka cluster. Only one of --bootstrap-server or --authorizer option must be specified. |                                                              | Configuration   |
| --command-config                       | A property file containing configs to be passed to Admin Client. This option can only be used with --bootstrap-server option. |                                                              | Configuration   |
| --cluster                              | Indicates to the script that the user is trying to interact with acls on the singular cluster resource. |                                                              | ResourcePattern |
| --topic [topic-name]                   | Indicates to the script that the user is trying to interact with acls on topic resource pattern(s). |                                                              | ResourcePattern |
| --group [group-name]                   | Indicates to the script that the user is trying to interact with acls on consumer-group resource pattern(s) |                                                              | ResourcePattern |
| --transactional-id [transactional-id]  | The transactionalId to which ACLs should be added or removed. A value of * indicates the ACLs should apply to all transactionalIds. |                                                              | ResourcePattern |
| --delegation-token [delegation-token]  | Delegation token to which ACLs should be added or removed. A value of * indicates ACL should apply to all tokens. |                                                              | ResourcePattern |
| --resource-pattern-type [pattern-type] | Indicates to the script the type of resource pattern, (for --add), or resource pattern filter, (for --list and --remove), the user wishes to use. When adding acls, this should be a specific pattern type, e.g. 'literal' or 'prefixed'. When listing or removing acls, a specific pattern type filter can be used to list or remove acls from a specific type of resource pattern, or the filter values of 'any' or 'match' can be used, where 'any' will match any pattern type, but will match the resource name exactly, and 'match' will perform pattern matching to list or remove all acls that affect the supplied resource(s). WARNING: 'match', when used in combination with the '--remove' switch, should be used with care. | literal                                                      | Configuration   |
| --allow-principal                      | Principal is in PrincipalType:name format that will be added to ACL with Allow permission. Default PrincipalType string "User" is case sensitive. You can specify multiple --allow-principal in a single command. |                                                              | Principal       |
| --deny-principal                       | Principal is in PrincipalType:name format that will be added to ACL with Deny permission. Default PrincipalType string "User" is case sensitive. You can specify multiple --deny-principal in a single command. |                                                              | Principal       |
| --principal                            | Principal is in PrincipalType:name format that will be used along with --list option. Default PrincipalType string "User" is case sensitive. This will list the ACLs for the specified principal. You can specify multiple --principal in a single command. |                                                              | Principal       |
| --allow-host                           | IP address from which principals listed in --allow-principal will have access. | if --allow-principal is specified defaults to * which translates to "all hosts" | Host            |
| --deny-host                            | IP address from which principals listed in --deny-principal will be denied access. | if --deny-principal is specified defaults to * which translates to "all hosts" | Host            |
| --operation                            | Operation that will be allowed or denied. Valid values are:ReadWriteCreateDeleteAlterDescribeClusterActionDescribeConfigsAlterConfigsIdempotentWriteAll | All                                                          | Operation       |
| --producer                             | Convenience option to add/remove acls for producer role. This will generate acls that allows WRITE, DESCRIBE and CREATE on topic. |                                                              | Convenience     |
| --consumer                             | Convenience option to add/remove acls for consumer role. This will generate acls that allows READ, DESCRIBE on topic and READ on consumer-group. |                                                              | Convenience     |
| --idempotent                           | Enable idempotence for the producer. This should be used in combination with the --producer option. Note that idempotence is enabled automatically if the producer is authorized to a particular transactional-id. |                                                              | Convenience     |
| --force                                | Convenience option to assume yes to all queries and do not prompt. |                                                              | Convenience     |
| --zk-tls-config-file                   | Identifies the file where ZooKeeper client TLS connectivity properties for the authorizer are defined. Any properties other than the following (with or without an "authorizer." prefix) are ignored: zookeeper.clientCnxnSocket, zookeeper.ssl.cipher.suites, zookeeper.ssl.client.enable, zookeeper.ssl.crl.enable, zookeeper.ssl.enabled.protocols, zookeeper.ssl.endpoint.identification.algorithm, zookeeper.ssl.keystore.location, zookeeper.ssl.keystore.password, zookeeper.ssl.keystore.type, zookeeper.ssl.ocsp.enable, zookeeper.ssl.protocol, zookeeper.ssl.truststore.location, zookeeper.ssl.truststore.password, zookeeper.ssl.truststore.type |                                                              | Configuration   |

#### [Examples](http://kafka.apache.org/documentation/#security_authz_examples)

- Adding Acls

  Suppose you want to add an acl "Principals User:Bob and User:Alice are allowed to perform Operation Read and Write on Topic Test-Topic from IP 198.51.100.0 and IP 198.51.100.1". You can do that by executing the CLI with following options:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Bob --allow-principal User:Alice --allow-host 198.51.100.0 --allow-host 198.51.100.1 --operation Read --operation Write --topic Test-topic
  ```

  By default, all principals that don't have an explicit acl that allows access for an operation to a resource are denied. In rare cases where an allow acl is defined that allows access to all but some principal we will have to use the --deny-principal and --deny-host option. For example, if we want to allow all users to Read from Test-topic but only deny User:BadBob from IP 198.51.100.3 we can do so using following commands:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:* --allow-host * --deny-principal User:BadBob --deny-host 198.51.100.3 --operation Read --topic Test-topic
  ```

  Note that

   

  ```
  --allow-host
  ```

   

  and

   

  ```
  --deny-host
  ```

   

  only support IP addresses (hostnames are not supported). Above examples add acls to a topic by specifying --topic [topic-name] as the resource pattern option. Similarly user can add acls to cluster by specifying --cluster and to a consumer group by specifying --group [group-name]. You can add acls on any resource of a certain type, e.g. suppose you wanted to add an acl "Principal User:Peter is allowed to produce to any Topic from IP 198.51.200.0" You can do that by using the wildcard resource '*', e.g. by executing the CLI with following options:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Peter --allow-host 198.51.200.1 --producer --topic *
  ```

  You can add acls on prefixed resource patterns, e.g. suppose you want to add an acl "Principal User:Jane is allowed to produce to any Topic whose name starts with 'Test-' from any host". You can do that by executing the CLI with following options:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Jane --producer --topic Test- --resource-pattern-type prefixed
  ```

  Note, --resource-pattern-type defaults to 'literal', which only affects resources with the exact same name or, in the case of the wildcard resource name '*', a resource with any name.

- Removing Acls

  Removing acls is pretty much the same. The only difference is instead of --add option users will have to specify --remove option. To remove the acls added by the first example above we can execute the CLI with following options:

  ```bash
   bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --remove --allow-principal User:Bob --allow-principal User:Alice --allow-host 198.51.100.0 --allow-host 198.51.100.1 --operation Read --operation Write --topic Test-topic 
  ```

  If you want to remove the acl added to the prefixed resource pattern above we can execute the CLI with following options:

  ```bash
   bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --remove --allow-principal User:Jane --producer --topic Test- --resource-pattern-type Prefixed
  ```

- List Acls

  We can list acls for any resource by specifying the --list option with the resource. To list all acls on the literal resource pattern Test-topic, we can execute the CLI with following options:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --list --topic Test-topic
  ```

  However, this will only return the acls that have been added to this exact resource pattern. Other acls can exist that affect access to the topic, e.g. any acls on the topic wildcard '*', or any acls on prefixed resource patterns. Acls on the wildcard resource pattern can be queried explicitly:

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --list --topic *
  ```

  However, it is not necessarily possible to explicitly query for acls on prefixed resource patterns that match Test-topic as the name of such patterns may not be known. We can list

   

  all

   

  acls affecting Test-topic by using '--resource-pattern-type match', e.g.

  ```bash
  bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --list --topic Test-topic --resource-pattern-type match
  ```

  This will list acls on all matching literal, wildcard and prefixed resource patterns.

- Adding or removing a principal as producer or consumer

  The most common use case for acl management are adding/removing a principal as producer or consumer so we added convenience options to handle these cases. In order to add User:Bob as a producer of Test-topic we can execute the following command:

  ```bash
   bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Bob --producer --topic Test-topic
  ```

  Similarly to add Alice as a consumer of Test-topic with consumer group Group-1 we just have to pass --consumer option:

  ```bash
   bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Bob --consumer --topic Test-topic --group Group-1 
  ```

  Note that for consumer option we must also specify the consumer group. In order to remove a principal from producer or consumer role we just need to pass --remove option.

- Admin API based acl management

  Users having Alter permission on ClusterResource can use Admin API for ACL management. kafka-acls.sh script supports AdminClient API to manage ACLs without interacting with zookeeper/authorizer directly. All the above examples can be executed by using

   

  --bootstrap-server

   

  option. For example:

  ```bash
              bin/kafka-acls.sh --bootstrap-server localhost:9092 --command-config /tmp/adminclient-configs.conf --add --allow-principal User:Bob --producer --topic Test-topic
              bin/kafka-acls.sh --bootstrap-server localhost:9092 --command-config /tmp/adminclient-configs.conf --add --allow-principal User:Bob --consumer --topic Test-topic --group Group-1
              bin/kafka-acls.sh --bootstrap-server localhost:9092 --command-config /tmp/adminclient-configs.conf --list --topic Test-topic
  ```

#### [Authorization Primitives](http://kafka.apache.org/documentation/#security_authz_primitives)

Protocol calls are usually performing some operations on certain resources in Kafka. It is required to know the operations and resources to set up effective protection. In this section we'll list these operations and resources, then list the combination of these with the protocols to see the valid scenarios.

##### [Operations in Kafka](http://kafka.apache.org/documentation/#operations_in_kafka)

There are a few operation primitives that can be used to build up privileges. These can be matched up with certain resources to allow specific protocol calls for a given user. These are:

- Read
- Write
- Create
- Delete
- Alter
- Describe
- ClusterAction
- DescribeConfigs
- AlterConfigs
- IdempotentWrite
- All

##### [Resources in Kafka](http://kafka.apache.org/documentation/#resources_in_kafka)

The operations above can be applied on certain resources which are described below.

- **Topic:** this simply represents a Topic. All protocol calls that are acting on topics (such as reading, writing them) require the corresponding privilege to be added. If there is an authorization error with a topic resource, then a TOPIC_AUTHORIZATION_FAILED (error code: 29) will be returned.
- **Group:** this represents the consumer groups in the brokers. All protocol calls that are working with consumer groups, like joining a group must have privileges with the group in subject. If the privilege is not given then a GROUP_AUTHORIZATION_FAILED (error code: 30) will be returned in the protocol response.
- **Cluster:** this resource represents the cluster. Operations that are affecting the whole cluster, like controlled shutdown are protected by privileges on the Cluster resource. If there is an authorization problem on a cluster resource, then a CLUSTER_AUTHORIZATION_FAILED (error code: 31) will be returned.
- **TransactionalId:** this resource represents actions related to transactions, such as committing. If any error occurs, then a TRANSACTIONAL_ID_AUTHORIZATION_FAILED (error code: 53) will be returned by brokers.
- **DelegationToken:** this represents the delegation tokens in the cluster. Actions, such as describing delegation tokens could be protected by a privilege on the DelegationToken resource. Since these objects have a little special behavior in Kafka it is recommended to read [KIP-48](https://cwiki.apache.org/confluence/display/KAFKA/KIP-48+Delegation+token+support+for+Kafka#KIP-48DelegationtokensupportforKafka-DescribeDelegationTokenRequest) and the related upstream documentation at [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token).

##### [Operations and Resources on Protocols](http://kafka.apache.org/documentation/#operations_resources_and_protocols)

In the below table we'll list the valid operations on resources that are executed by the Kafka API protocols.

| PROTOCOL (API KEY)                 | OPERATION       | RESOURCE        | NOTE                                                         |
| :--------------------------------- | :-------------- | :-------------- | :----------------------------------------------------------- |
| PRODUCE (0)                        | Write           | TransactionalId | An transactional producer which has its transactional.id set requires this privilege. |
| PRODUCE (0)                        | IdempotentWrite | Cluster         | An idempotent produce action requires this privilege.        |
| PRODUCE (0)                        | Write           | Topic           | This applies to a normal produce action.                     |
| FETCH (1)                          | ClusterAction   | Cluster         | A follower must have ClusterAction on the Cluster resource in order to fetch partition data. |
| FETCH (1)                          | Read            | Topic           | Regular Kafka consumers need READ permission on each partition they are fetching. |
| LIST_OFFSETS (2)                   | Describe        | Topic           |                                                              |
| METADATA (3)                       | Describe        | Topic           |                                                              |
| METADATA (3)                       | Create          | Cluster         | If topic auto-creation is enabled, then the broker-side API will check for the existence of a Cluster level privilege. If it's found then it'll allow creating the topic, otherwise it'll iterate through the Topic level privileges (see the next one). |
| METADATA (3)                       | Create          | Topic           | This authorizes auto topic creation if enabled but the given user doesn't have a cluster level permission (above). |
| LEADER_AND_ISR (4)                 | ClusterAction   | Cluster         |                                                              |
| STOP_REPLICA (5)                   | ClusterAction   | Cluster         |                                                              |
| UPDATE_METADATA (6)                | ClusterAction   | Cluster         |                                                              |
| CONTROLLED_SHUTDOWN (7)            | ClusterAction   | Cluster         |                                                              |
| OFFSET_COMMIT (8)                  | Read            | Group           | An offset can only be committed if it's authorized to the given group and the topic too (see below). Group access is checked first, then Topic access. |
| OFFSET_COMMIT (8)                  | Read            | Topic           | Since offset commit is part of the consuming process, it needs privileges for the read action. |
| OFFSET_FETCH (9)                   | Describe        | Group           | Similarly to OFFSET_COMMIT, the application must have privileges on group and topic level too to be able to fetch. However in this case it requires describe access instead of read. Group access is checked first, then Topic access. |
| OFFSET_FETCH (9)                   | Describe        | Topic           |                                                              |
| FIND_COORDINATOR (10)              | Describe        | Group           | The FIND_COORDINATOR request can be of "Group" type in which case it is looking for consumergroup coordinators. This privilege would represent the Group mode. |
| FIND_COORDINATOR (10)              | Describe        | TransactionalId | This applies only on transactional producers and checked when a producer tries to find the transaction coordinator. |
| JOIN_GROUP (11)                    | Read            | Group           |                                                              |
| HEARTBEAT (12)                     | Read            | Group           |                                                              |
| LEAVE_GROUP (13)                   | Read            | Group           |                                                              |
| SYNC_GROUP (14)                    | Read            | Group           |                                                              |
| DESCRIBE_GROUPS (15)               | Describe        | Group           |                                                              |
| LIST_GROUPS (16)                   | Describe        | Cluster         | When the broker checks to authorize a list_groups request it first checks for this cluster level authorization. If none found then it proceeds to check the groups individually. This operation doesn't return CLUSTER_AUTHORIZATION_FAILED. |
| LIST_GROUPS (16)                   | Describe        | Group           | If none of the groups are authorized, then just an empty response will be sent back instead of an error. This operation doesn't return CLUSTER_AUTHORIZATION_FAILED. This is applicable from the 2.1 release. |
| SASL_HANDSHAKE (17)                |                 |                 | The SASL handshake is part of the authentication process and therefore it's not possible to apply any kind of authorization here. |
| API_VERSIONS (18)                  |                 |                 | The API_VERSIONS request is part of the Kafka protocol handshake and happens on connection and before any authentication. Therefore it's not possible to control this with authorization. |
| CREATE_TOPICS (19)                 | Create          | Cluster         | If there is no cluster level authorization then it won't return CLUSTER_AUTHORIZATION_FAILED but fall back to use topic level, which is just below. That'll throw error if there is a problem. |
| CREATE_TOPICS (19)                 | Create          | Topic           | This is applicable from the 2.0 release.                     |
| DELETE_TOPICS (20)                 | Delete          | Topic           |                                                              |
| DELETE_RECORDS (21)                | Delete          | Topic           |                                                              |
| INIT_PRODUCER_ID (22)              | Write           | TransactionalId |                                                              |
| INIT_PRODUCER_ID (22)              | IdempotentWrite | Cluster         |                                                              |
| OFFSET_FOR_LEADER_EPOCH (23)       | ClusterAction   | Cluster         | If there is no cluster level privilege for this operation, then it'll check for topic level one. |
| OFFSET_FOR_LEADER_EPOCH (23)       | Describe        | Topic           | This is applicable from the 2.1 release.                     |
| ADD_PARTITIONS_TO_TXN (24)         | Write           | TransactionalId | This API is only applicable to transactional requests. It first checks for the Write action on the TransactionalId resource, then it checks the Topic in subject (below). |
| ADD_PARTITIONS_TO_TXN (24)         | Write           | Topic           |                                                              |
| ADD_OFFSETS_TO_TXN (25)            | Write           | TransactionalId | Similarly to ADD_PARTITIONS_TO_TXN this is only applicable to transactional request. It first checks for Write action on the TransactionalId resource, then it checks whether it can Read on the given group (below). |
| ADD_OFFSETS_TO_TXN (25)            | Read            | Group           |                                                              |
| END_TXN (26)                       | Write           | TransactionalId |                                                              |
| WRITE_TXN_MARKERS (27)             | ClusterAction   | Cluster         |                                                              |
| TXN_OFFSET_COMMIT (28)             | Write           | TransactionalId |                                                              |
| TXN_OFFSET_COMMIT (28)             | Read            | Group           |                                                              |
| TXN_OFFSET_COMMIT (28)             | Read            | Topic           |                                                              |
| DESCRIBE_ACLS (29)                 | Describe        | Cluster         |                                                              |
| CREATE_ACLS (30)                   | Alter           | Cluster         |                                                              |
| DELETE_ACLS (31)                   | Alter           | Cluster         |                                                              |
| DESCRIBE_CONFIGS (32)              | DescribeConfigs | Cluster         | If broker configs are requested, then the broker will check cluster level privileges. |
| DESCRIBE_CONFIGS (32)              | DescribeConfigs | Topic           | If topic configs are requested, then the broker will check topic level privileges. |
| ALTER_CONFIGS (33)                 | AlterConfigs    | Cluster         | If broker configs are altered, then the broker will check cluster level privileges. |
| ALTER_CONFIGS (33)                 | AlterConfigs    | Topic           | If topic configs are altered, then the broker will check topic level privileges. |
| ALTER_REPLICA_LOG_DIRS (34)        | Alter           | Cluster         |                                                              |
| DESCRIBE_LOG_DIRS (35)             | Describe        | Cluster         | An empty response will be returned on authorization failure. |
| SASL_AUTHENTICATE (36)             |                 |                 | SASL_AUTHENTICATE is part of the authentication process and therefore it's not possible to apply any kind of authorization here. |
| CREATE_PARTITIONS (37)             | Alter           | Topic           |                                                              |
| CREATE_DELEGATION_TOKEN (38)       |                 |                 | Creating delegation tokens has special rules, for this please see the [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token) section. |
| RENEW_DELEGATION_TOKEN (39)        |                 |                 | Renewing delegation tokens has special rules, for this please see the [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token) section. |
| EXPIRE_DELEGATION_TOKEN (40)       |                 |                 | Expiring delegation tokens has special rules, for this please see the [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token) section. |
| DESCRIBE_DELEGATION_TOKEN (41)     | Describe        | DelegationToken | Describing delegation tokens has special rules, for this please see the [Authentication using Delegation Tokens](http://kafka.apache.org/documentation/#security_delegation_token) section. |
| DELETE_GROUPS (42)                 | Delete          | Group           |                                                              |
| ELECT_PREFERRED_LEADERS (43)       | ClusterAction   | Cluster         |                                                              |
| INCREMENTAL_ALTER_CONFIGS (44)     | AlterConfigs    | Cluster         | If broker configs are altered, then the broker will check cluster level privileges. |
| INCREMENTAL_ALTER_CONFIGS (44)     | AlterConfigs    | Topic           | If topic configs are altered, then the broker will check topic level privileges. |
| ALTER_PARTITION_REASSIGNMENTS (45) | Alter           | Cluster         |                                                              |
| LIST_PARTITION_REASSIGNMENTS (46)  | Describe        | Cluster         |                                                              |
| OFFSET_DELETE (47)                 | Delete          | Group           |                                                              |
| OFFSET_DELETE (47)                 | Read            | Topic           |                                                              |

### [7.5 Incorporating Security Features in a Running Cluster](http://kafka.apache.org/documentation/#security_rolling_upgrade)

You can secure a running cluster via one or more of the supported protocols discussed previously. This is done in phases:



- Incrementally bounce the cluster nodes to open additional secured port(s).
- Restart clients using the secured rather than PLAINTEXT port (assuming you are securing the client-broker connection).
- Incrementally bounce the cluster again to enable broker-to-broker security (if this is required)
- A final incremental bounce to close the PLAINTEXT port.



The specific steps for configuring SSL and SASL are described in sections [7.2](http://kafka.apache.org/documentation/#security_ssl) and [7.3](http://kafka.apache.org/documentation/#security_sasl). Follow these steps to enable security for your desired protocol(s).



The security implementation lets you configure different protocols for both broker-client and broker-broker communication. These must be enabled in separate bounces. A PLAINTEXT port must be left open throughout so brokers and/or clients can continue to communicate.



When performing an incremental bounce stop the brokers cleanly via a SIGTERM. It's also good practice to wait for restarted replicas to return to the ISR list before moving onto the next node.



As an example, say we wish to encrypt both broker-client and broker-broker communication with SSL. In the first incremental bounce, an SSL port is opened on each node:

```text
            listeners=PLAINTEXT://broker1:9091,SSL://broker1:9092
```

We then restart the clients, changing their config to point at the newly opened, secured port:

```text
            bootstrap.servers = [broker1:9092,...]
            security.protocol = SSL
            ...etc
```

In the second incremental server bounce we instruct Kafka to use SSL as the broker-broker protocol (which will use the same SSL port):

```text
            listeners=PLAINTEXT://broker1:9091,SSL://broker1:9092
            security.inter.broker.protocol=SSL
```

In the final bounce we secure the cluster by closing the PLAINTEXT port:

```text
            listeners=SSL://broker1:9092
            security.inter.broker.protocol=SSL
```

Alternatively we might choose to open multiple ports so that different protocols can be used for broker-broker and broker-client communication. Say we wished to use SSL encryption throughout (i.e. for broker-broker and broker-client communication) but we'd like to add SASL authentication to the broker-client connection also. We would achieve this by opening two additional ports during the first bounce:

```text
            listeners=PLAINTEXT://broker1:9091,SSL://broker1:9092,SASL_SSL://broker1:9093
```

We would then restart the clients, changing their config to point at the newly opened, SASL & SSL secured port:

```text
            bootstrap.servers = [broker1:9093,...]
            security.protocol = SASL_SSL
            ...etc
```

The second server bounce would switch the cluster to use encrypted broker-broker communication via the SSL port we previously opened on port 9092:

```text
            listeners=PLAINTEXT://broker1:9091,SSL://broker1:9092,SASL_SSL://broker1:9093
            security.inter.broker.protocol=SSL
```

The final bounce secures the cluster by closing the PLAINTEXT port.

```text
        listeners=SSL://broker1:9092,SASL_SSL://broker1:9093
        security.inter.broker.protocol=SSL
```

ZooKeeper can be secured independently of the Kafka cluster. The steps for doing this are covered in section [7.6.2](http://kafka.apache.org/documentation/#zk_authz_migration).

### [7.6 ZooKeeper Authentication](http://kafka.apache.org/documentation/#zk_authz)

ZooKeeper supports mutual TLS (mTLS) authentication beginning with the 3.5.x versions. Kafka supports authenticating to ZooKeeper with SASL and mTLS -- either individually or both together -- beginning with version 2.5. See [KIP-515: Enable ZK client to use the new TLS supported authentication](https://cwiki.apache.org/confluence/display/KAFKA/KIP-515%3A+Enable+ZK+client+to+use+the+new+TLS+supported+authentication) for more details.

When using mTLS alone, every broker and any CLI tools (such as the [ZooKeeper Security Migration Tool](http://kafka.apache.org/documentation/#zk_authz_migration)) should identify itself with the same Distinguished Name (DN) because it is the DN that is ACL'ed. This can be changed as described below, but it involves writing and deploying a custom ZooKeeper authentication provider. Generally each certificate should have the same DN but a different Subject Alternative Name (SAN) so that hostname verification of the brokers and any CLI tools by ZooKeeper will succeed.

When using SASL authentication to ZooKeeper together with mTLS, both the SASL identity and either the DN that created the znode (i.e. the creating broker's certificate) or the DN of the Security Migration Tool (if migration was performed after the znode was created) will be ACL'ed, and all brokers and CLI tools will be authorized even if they all use different DNs because they will all use the same ACL'ed SASL identity. It is only when using mTLS authentication alone that all the DNs must match (and SANs become critical -- again, in the absence of writing and deploying a custom ZooKeeper authentication provider as described below).



Use the broker properties file to set TLS configs for brokers as described below.

Use the `--zk-tls-config-file <file>` option to set TLS configs in the Zookeeper Security Migration Tool. The `kafka-acls.sh` and `kafka-configs.sh` CLI tools also support the `--zk-tls-config-file <file>` option.

Use the `-zk-tls-config-file <file>` option (note the single-dash rather than double-dash) to set TLS configs for the `zookeeper-shell.sh` CLI tool.

#### [7.6.1 New clusters](http://kafka.apache.org/documentation/#zk_authz_new)

##### [7.6.1.1 ZooKeeper SASL Authentication](http://kafka.apache.org/documentation/#zk_authz_new_sasl)

To enable ZooKeeper SASL authentication on brokers, there are two necessary steps:

1. Create a JAAS login file and set the appropriate system property to point to it as described above
2. Set the configuration property `zookeeper.set.acl` in each broker to true

The metadata stored in ZooKeeper for the Kafka cluster is world-readable, but can only be modified by the brokers. The rationale behind this decision is that the data stored in ZooKeeper is not sensitive, but inappropriate manipulation of that data can cause cluster disruption. We also recommend limiting the access to ZooKeeper via network segmentation (only brokers and some admin tools need access to ZooKeeper).

##### [7.6.1.2 ZooKeeper Mutual TLS Authentication](http://kafka.apache.org/documentation/#zk_authz_new_mtls)

ZooKeeper mTLS authentication can be enabled with or without SASL authentication. As mentioned above, when using mTLS alone, every broker and any CLI tools (such as the [ZooKeeper Security Migration Tool](http://kafka.apache.org/documentation/#zk_authz_migration)) must generally identify itself with the same Distinguished Name (DN) because it is the DN that is ACL'ed, which means each certificate should have an appropriate Subject Alternative Name (SAN) so that hostname verification of the brokers and any CLI tool by ZooKeeper will succeed.

It is possible to use something other than the DN for the identity of mTLS clients by writing a class that extends `org.apache.zookeeper.server.auth.X509AuthenticationProvider` and overrides the method `protected String getClientId(X509Certificate clientCert)`. Choose a scheme name and set `authProvider.[scheme]` in ZooKeeper to be the fully-qualified class name of the custom implementation; then set `ssl.authProvider=[scheme]` to use it.

Here is a sample (partial) ZooKeeper configuration for enabling TLS authentication. These configurations are described in the [ZooKeeper Admin Guide](https://zookeeper.apache.org/doc/r3.5.7/zookeeperAdmin.html#sc_authOptions).

```text
        secureClientPort=2182
        serverCnxnFactory=org.apache.zookeeper.server.NettyServerCnxnFactory
        authProvider.x509=org.apache.zookeeper.server.auth.X509AuthenticationProvider
        ssl.keyStore.location=/path/to/zk/keystore.jks
        ssl.keyStore.password=zk-ks-passwd
        ssl.trustStore.location=/path/to/zk/truststore.jks
        ssl.trustStore.password=zk-ts-passwd
```

**IMPORTANT**: ZooKeeper does not support setting the key password in the ZooKeeper server keystore to a value different from the keystore password itself. Be sure to set the key password to be the same as the keystore password.

Here is a sample (partial) Kafka Broker configuration for connecting to ZooKeeper with mTLS authentication. These configurations are described above in [Broker Configs](http://kafka.apache.org/documentation/#brokerconfigs).

```text
        # connect to the ZooKeeper port configured for TLS
        zookeeper.connect=zk1:2182,zk2:2182,zk3:2182
        # required to use TLS to ZooKeeper (default is false)
        zookeeper.ssl.client.enable=true
        # required to use TLS to ZooKeeper
        zookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty
        # define key/trust stores to use TLS to ZooKeeper; ignored unless zookeeper.ssl.client.enable=true
        zookeeper.ssl.keystore.location=/path/to/kafka/keystore.jks
        zookeeper.ssl.keystore.password=kafka-ks-passwd
        zookeeper.ssl.truststore.location=/path/to/kafka/truststore.jks
        zookeeper.ssl.truststore.password=kafka-ts-passwd
        # tell broker to create ACLs on znodes
        zookeeper.set.acl=true
```

**IMPORTANT**: ZooKeeper does not support setting the key password in the ZooKeeper client (i.e. broker) keystore to a value different from the keystore password itself. Be sure to set the key password to be the same as the keystore password.

#### [7.6.2 Migrating clusters](http://kafka.apache.org/documentation/#zk_authz_migration)

If you are running a version of Kafka that does not support security or simply with security disabled, and you want to make the cluster secure, then you need to execute the following steps to enable ZooKeeper authentication with minimal disruption to your operations:

1. Enable SASL and/or mTLS authentication on ZooKeeper. If enabling mTLS, you would now have both a non-TLS port and a TLS port, like this:

   ```text
       clientPort=2181
       secureClientPort=2182
       serverCnxnFactory=org.apache.zookeeper.server.NettyServerCnxnFactory
       authProvider.x509=org.apache.zookeeper.server.auth.X509AuthenticationProvider
       ssl.keyStore.location=/path/to/zk/keystore.jks
       ssl.keyStore.password=zk-ks-passwd
       ssl.trustStore.location=/path/to/zk/truststore.jks
       ssl.trustStore.password=zk-ts-passwd
   ```

2. Perform a rolling restart of brokers setting the JAAS login file and/or defining ZooKeeper mutual TLS configurations (including connecting to the TLS-enabled ZooKeeper port) as required, which enables brokers to authenticate to ZooKeeper. At the end of the rolling restart, brokers are able to manipulate znodes with strict ACLs, but they will not create znodes with those ACLs

3. If you enabled mTLS, disable the non-TLS port in ZooKeeper

4. Perform a second rolling restart of brokers, this time setting the configuration parameter `zookeeper.set.acl` to true, which enables the use of secure ACLs when creating znodes

5. Execute the ZkSecurityMigrator tool. To execute the tool, there is this script: `bin/zookeeper-security-migration.sh` with `zookeeper.acl` set to secure. This tool traverses the corresponding sub-trees changing the ACLs of the znodes. Use the `--zk-tls-config-file <file>` option if you enable mTLS.

It is also possible to turn off authentication in a secure cluster. To do it, follow these steps:

1. Perform a rolling restart of brokers setting the JAAS login file and/or defining ZooKeeper mutual TLS configurations, which enables brokers to authenticate, but setting `zookeeper.set.acl` to false. At the end of the rolling restart, brokers stop creating znodes with secure ACLs, but are still able to authenticate and manipulate all znodes
2. Execute the ZkSecurityMigrator tool. To execute the tool, run this script `bin/zookeeper-security-migration.sh` with `zookeeper.acl` set to unsecure. This tool traverses the corresponding sub-trees changing the ACLs of the znodes. Use the `--zk-tls-config-file <file>` option if you need to set TLS configuration.
3. If you are disabling mTLS, enable the non-TLS port in ZooKeeper
4. Perform a second rolling restart of brokers, this time omitting the system property that sets the JAAS login file and/or removing ZooKeeper mutual TLS configuration (including connecting to the non-TLS-enabled ZooKeeper port) as required
5. If you are disabling mTLS, disable the TLS port in ZooKeeper

Here is an example of how to run the migration tool:

```bash
    bin/zookeeper-security-migration.sh --zookeeper.acl=secure --zookeeper.connect=localhost:2181
```

Run this to see the full list of parameters:

```bash
    bin/zookeeper-security-migration.sh --help
```

#### [7.6.3 Migrating the ZooKeeper ensemble](http://kafka.apache.org/documentation/#zk_authz_ensemble)

It is also necessary to enable SASL and/or mTLS authentication on the ZooKeeper ensemble. To do it, we need to perform a rolling restart of the server and set a few properties. See above for mTLS information. Please refer to the ZooKeeper documentation for more detail:

1. [Apache ZooKeeper documentation](https://zookeeper.apache.org/doc/r3.5.7/zookeeperProgrammers.html#sc_ZooKeeperAccessControl)
2. [Apache ZooKeeper wiki](https://cwiki.apache.org/confluence/display/ZOOKEEPER/Zookeeper+and+SASL)

#### [7.6.4 ZooKeeper Quorum Mutual TLS Authentication](http://kafka.apache.org/documentation/#zk_authz_quorum)

It is possible to enable mTLS authentication between the ZooKeeper servers themselves. Please refer to the [ZooKeeper documentation](https://zookeeper.apache.org/doc/r3.5.7/zookeeperAdmin.html#Quorum+TLS) for more detail.

### [7.7 ZooKeeper Encryption](http://kafka.apache.org/documentation/#zk_encryption)

ZooKeeper connections that use mutual TLS are encrypted. Beginning with ZooKeeper version 3.5.7 (the version shipped with Kafka version 2.5) ZooKeeper supports a sever-side config `ssl.clientAuth` (case-insensitively: `want`/`need`/`none` are the valid options, the default is `need`), and setting this value to `none` in ZooKeeper allows clients to connect via a TLS-encrypted connection without presenting their own certificate. Here is a sample (partial) Kafka Broker configuration for connecting to ZooKeeper with just TLS encryption. These configurations are described above in [Broker Configs](http://kafka.apache.org/documentation/#brokerconfigs).

```text
        # connect to the ZooKeeper port configured for TLS
        zookeeper.connect=zk1:2182,zk2:2182,zk3:2182
        # required to use TLS to ZooKeeper (default is false)
        zookeeper.ssl.client.enable=true
        # required to use TLS to ZooKeeper
        zookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty
        # define trust stores to use TLS to ZooKeeper; ignored unless zookeeper.ssl.client.enable=true
        # no need to set keystore information assuming ssl.clientAuth=none on ZooKeeper
        zookeeper.ssl.truststore.location=/path/to/kafka/truststore.jks
        zookeeper.ssl.truststore.password=kafka-ts-passwd
        # tell broker to create ACLs on znodes (if using SASL authentication, otherwise do not set this)
        zookeeper.set.acl=true
```

## [8. KAFKA CONNECT](http://kafka.apache.org/documentation/#connect)

### [8.1 Overview](http://kafka.apache.org/documentation/#connect_overview)

Kafka Connect is a tool for scalably and reliably streaming data between Apache Kafka and other systems. It makes it simple to quickly define *connectors* that move large collections of data into and out of Kafka. Kafka Connect can ingest entire databases or collect metrics from all your application servers into Kafka topics, making the data available for stream processing with low latency. An export job can deliver data from Kafka topics into secondary storage and query systems or into batch systems for offline analysis.

Kafka Connect features include:

- **A common framework for Kafka connectors** - Kafka Connect standardizes integration of other data systems with Kafka, simplifying connector development, deployment, and management
- **Distributed and standalone modes** - scale up to a large, centrally managed service supporting an entire organization or scale down to development, testing, and small production deployments
- **REST interface** - submit and manage connectors to your Kafka Connect cluster via an easy to use REST API
- **Automatic offset management** - with just a little information from connectors, Kafka Connect can manage the offset commit process automatically so connector developers do not need to worry about this error prone part of connector development
- **Distributed and scalable by default** - Kafka Connect builds on the existing group management protocol. More workers can be added to scale up a Kafka Connect cluster.
- **Streaming/batch integration** - leveraging Kafka's existing capabilities, Kafka Connect is an ideal solution for bridging streaming and batch data systems

### [8.2 User Guide](http://kafka.apache.org/documentation/#connect_user)

The [quickstart](http://kafka.apache.org/quickstart) provides a brief example of how to run a standalone version of Kafka Connect. This section describes how to configure, run, and manage Kafka Connect in more detail.

#### [Running Kafka Connect](http://kafka.apache.org/documentation/#connect_running)

Kafka Connect currently supports two modes of execution: standalone (single process) and distributed.

In standalone mode all work is performed in a single process. This configuration is simpler to setup and get started with and may be useful in situations where only one worker makes sense (e.g. collecting log files), but it does not benefit from some of the features of Kafka Connect such as fault tolerance. You can start a standalone process with the following command:

```bash
    > bin/connect-standalone.sh config/connect-standalone.properties connector1.properties [connector2.properties ...]
```

The first parameter is the configuration for the worker. This includes settings such as the Kafka connection parameters, serialization format, and how frequently to commit offsets. The provided example should work well with a local cluster running with the default configuration provided by `config/server.properties`. It will require tweaking to use with a different configuration or production deployment. All workers (both standalone and distributed) require a few configs:

- `bootstrap.servers` - List of Kafka servers used to bootstrap connections to Kafka
- `key.converter` - Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the keys in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.
- `value.converter` - Converter class used to convert between Kafka Connect format and the serialized form that is written to Kafka. This controls the format of the values in messages written to or read from Kafka, and since this is independent of connectors it allows any connector to work with any serialization format. Examples of common formats include JSON and Avro.

The important configuration options specific to standalone mode are:

- `offset.storage.file.filename` - File to store offset data in

The parameters that are configured here are intended for producers and consumers used by Kafka Connect to access the configuration, offset and status topics. For configuration of the producers used by Kafka source tasks and the consumers used by Kafka sink tasks, the same parameters can be used but need to be prefixed with `producer.` and `consumer.` respectively. The only Kafka client parameter that is inherited without a prefix from the worker configuration is `bootstrap.servers`, which in most cases will be sufficient, since the same cluster is often used for all purposes. A notable exception is a secured cluster, which requires extra parameters to allow connections. These parameters will need to be set up to three times in the worker configuration, once for management access, once for Kafka sources and once for Kafka sinks.

Starting with 2.3.0, client configuration overrides can be configured individually per connector by using the prefixes `producer.override.` and `consumer.override.` for Kafka sources or Kafka sinks respectively. These overrides are included with the rest of the connector's configuration properties.

The remaining parameters are connector configuration files. You may include as many as you want, but all will execute within the same process (on different threads).

Distributed mode handles automatic balancing of work, allows you to scale up (or down) dynamically, and offers fault tolerance both in the active tasks and for configuration and offset commit data. Execution is very similar to standalone mode:

```bash
    > bin/connect-distributed.sh config/connect-distributed.properties
```

The difference is in the class which is started and the configuration parameters which change how the Kafka Connect process decides where to store configurations, how to assign work, and where to store offsets and task statues. In the distributed mode, Kafka Connect stores the offsets, configs and task statuses in Kafka topics. It is recommended to manually create the topics for offset, configs and statuses in order to achieve the desired the number of partitions and replication factors. If the topics are not yet created when starting Kafka Connect, the topics will be auto created with default number of partitions and replication factor, which may not be best suited for its usage.

In particular, the following configuration parameters, in addition to the common settings mentioned above, are critical to set before starting your cluster:

- `group.id` (default `connect-cluster`) - unique name for the cluster, used in forming the Connect cluster group; note that this **must not conflict** with consumer group IDs
- `config.storage.topic` (default `connect-configs`) - topic to use for storing connector and task configurations; note that this should be a single partition, highly replicated, compacted topic. You may need to manually create the topic to ensure the correct configuration as auto created topics may have multiple partitions or be automatically configured for deletion rather than compaction
- `offset.storage.topic` (default `connect-offsets`) - topic to use for storing offsets; this topic should have many partitions, be replicated, and be configured for compaction
- `status.storage.topic` (default `connect-status`) - topic to use for storing statuses; this topic can have multiple partitions, and should be replicated and configured for compaction

Note that in distributed mode the connector configurations are not passed on the command line. Instead, use the REST API described below to create, modify, and destroy connectors.

#### [Configuring Connectors](http://kafka.apache.org/documentation/#connect_configuring)

Connector configurations are simple key-value mappings. For standalone mode these are defined in a properties file and passed to the Connect process on the command line. In distributed mode, they will be included in the JSON payload for the request that creates (or modifies) the connector.

Most configurations are connector dependent, so they can't be outlined here. However, there are a few common options:

- `name` - Unique name for the connector. Attempting to register again with the same name will fail.
- `connector.class` - The Java class for the connector
- `tasks.max` - The maximum number of tasks that should be created for this connector. The connector may create fewer tasks if it cannot achieve this level of parallelism.
- `key.converter` - (optional) Override the default key converter set by the worker.
- `value.converter` - (optional) Override the default value converter set by the worker.

The `connector.class` config supports several formats: the full name or alias of the class for this connector. If the connector is org.apache.kafka.connect.file.FileStreamSinkConnector, you can either specify this full name or use FileStreamSink or FileStreamSinkConnector to make the configuration a bit shorter.

Sink connectors also have a few additional options to control their input. Each sink connector must set one of the following:

- `topics` - A comma-separated list of topics to use as input for this connector
- `topics.regex` - A Java regular expression of topics to use as input for this connector

For any other options, you should consult the documentation for the connector.

#### [Transformations](http://kafka.apache.org/documentation/#connect_transforms)

Connectors can be configured with transformations to make lightweight message-at-a-time modifications. They can be convenient for data massaging and event routing.

A transformation chain can be specified in the connector configuration.

- `transforms` - List of aliases for the transformation, specifying the order in which the transformations will be applied.
- `transforms.$alias.type` - Fully qualified class name for the transformation.
- `transforms.$alias.$transformationSpecificConfig` Configuration properties for the transformation

For example, lets take the built-in file source connector and use a transformation to add a static field.

Throughout the example we'll use schemaless JSON data format. To use schemaless format, we changed the following two lines in `connect-standalone.properties` from true to false:

```text
        key.converter.schemas.enable
        value.converter.schemas.enable
```

The file source connector reads each line as a String. We will wrap each line in a Map and then add a second field to identify the origin of the event. To do this, we use two transformations:

- **HoistField** to place the input line inside a Map
- **InsertField** to add the static field. In this example we'll indicate that the record came from a file connector

After adding the transformations, `connect-file-source.properties` file looks as following:

```text
        name=local-file-source
        connector.class=FileStreamSource
        tasks.max=1
        file=test.txt
        topic=connect-test
        transforms=MakeMap, InsertSource
        transforms.MakeMap.type=org.apache.kafka.connect.transforms.HoistField$Value
        transforms.MakeMap.field=line
        transforms.InsertSource.type=org.apache.kafka.connect.transforms.InsertField$Value
        transforms.InsertSource.static.field=data_source
        transforms.InsertSource.static.value=test-file-source
```

All the lines starting with `transforms` were added for the transformations. You can see the two transformations we created: "InsertSource" and "MakeMap" are aliases that we chose to give the transformations. The transformation types are based on the list of built-in transformations you can see below. Each transformation type has additional configuration: HoistField requires a configuration called "field", which is the name of the field in the map that will include the original String from the file. InsertField transformation lets us specify the field name and the value that we are adding.

When we ran the file source connector on my sample file without the transformations, and then read them using `kafka-console-consumer.sh`, the results were:

```text
        "foo"
        "bar"
        "hello world"
```

We then create a new file connector, this time after adding the transformations to the configuration file. This time, the results will be:

```json
        {"line":"foo","data_source":"test-file-source"}
        {"line":"bar","data_source":"test-file-source"}
        {"line":"hello world","data_source":"test-file-source"}
```

You can see that the lines we've read are now part of a JSON map, and there is an extra field with the static value we specified. This is just one example of what you can do with transformations.

##### [Included transformations](http://kafka.apache.org/documentation/#connect_included_transformation)

Several widely-applicable data and routing transformations are included with Kafka Connect:

- InsertField - Add a field using either static data or record metadata
- ReplaceField - Filter or rename fields
- MaskField - Replace field with valid null value for the type (0, empty string, etc) or custom replacement (non-empty string or numeric value only)
- ValueToKey - Replace the record key with a new key formed from a subset of fields in the record value
- HoistField - Wrap the entire event as a single field inside a Struct or a Map
- ExtractField - Extract a specific field from Struct and Map and include only this field in results
- SetSchemaMetadata - modify the schema name or version
- TimestampRouter - Modify the topic of a record based on original topic and timestamp. Useful when using a sink that needs to write to different tables or indexes based on timestamps
- RegexRouter - modify the topic of a record based on original topic, replacement string and a regular expression
- Filter - Removes messages from all further processing. This is used with a [predicate](http://kafka.apache.org/documentation/#connect_predicates) to selectively filter certain messages.

Details on how to configure each transformation are listed below:

##### org.apache.kafka.connect.transforms.InsertField

Insert field(s) using attributes from the record metadata or a configured static value.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.InsertField$Key`) or value (`org.apache.kafka.connect.transforms.InsertField$Value`).



- ###### [offset.field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_offset.field)

  Field name for Kafka offset - only applicable to sink connectors.
  Suffix with `!` to make this a required field, or `?` to keep it optional (the default).

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [partition.field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_partition.field)

  Field name for Kafka partition. Suffix with `!` to make this a required field, or `?` to keep it optional (the default).

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [static.field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_static.field)

  Field name for static data field. Suffix with `!` to make this a required field, or `?` to keep it optional (the default).

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [static.value](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_static.value)

  Static field value, if field name configured.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [timestamp.field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_timestamp.field)

  Field name for record timestamp. Suffix with `!` to make this a required field, or `?` to keep it optional (the default).

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [topic.field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.InsertField_topic.field)

  Field name for Kafka topic. Suffix with `!` to make this a required field, or `?` to keep it optional (the default).

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.ReplaceField

Filter or rename fields.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.ReplaceField$Key`) or value (`org.apache.kafka.connect.transforms.ReplaceField$Value`).



- ###### [blacklist](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.ReplaceField_blacklist)

  Fields to exclude. This takes precedence over the whitelist.

  |         Type: | list   |
  | ------------: | ------ |
  |      Default: | ""     |
  | Valid Values: |        |
  |   Importance: | medium |

- ###### [renames](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.ReplaceField_renames)

  Field rename mappings.

  |         Type: | list                                                  |
  | ------------: | ----------------------------------------------------- |
  |      Default: | ""                                                    |
  | Valid Values: | list of colon-delimited pairs, e.g. `foo:bar,abc:xyz` |
  |   Importance: | medium                                                |

- ###### [whitelist](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.ReplaceField_whitelist)

  Fields to include. If specified, only these fields will be used.

  |         Type: | list   |
  | ------------: | ------ |
  |      Default: | ""     |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.MaskField

Mask specified fields with a valid null value for the field type (i.e. 0, false, empty string, and so on).



For numeric and string fields, an optional replacement value can be specified that is converted to the correct type.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.MaskField$Key`) or value (`org.apache.kafka.connect.transforms.MaskField$Value`).



- ###### [fields](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.MaskField_fields)

  Names of fields to mask.

  |         Type: | list           |
  | ------------: | -------------- |
  |      Default: |                |
  | Valid Values: | non-empty list |
  |   Importance: | high           |

- ###### [replacement](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.MaskField_replacement)

  Custom value replacement, that will be applied to all 'fields' values (numeric or non-empty string values only).

  |         Type: | string           |
  | ------------: | ---------------- |
  |      Default: | null             |
  | Valid Values: | non-empty string |
  |   Importance: | low              |

##### org.apache.kafka.connect.transforms.ValueToKey

Replace the record key with a new key formed from a subset of fields in the record value.



- ###### [fields](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.ValueToKey_fields)

  Field names on the record value to extract as the record key.

  |         Type: | list           |
  | ------------: | -------------- |
  |      Default: |                |
  | Valid Values: | non-empty list |
  |   Importance: | high           |

##### org.apache.kafka.connect.transforms.HoistField

Wrap data using the specified field name in a Struct when schema present, or a Map in the case of schemaless data.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.HoistField$Key`) or value (`org.apache.kafka.connect.transforms.HoistField$Value`).



- ###### [field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.HoistField_field)

  Field name for the single field that will be created in the resulting Struct or Map.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: |        |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.ExtractField

Extract the specified field from a Struct when schema present, or a Map in the case of schemaless data. Any null values are passed through unmodified.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.ExtractField$Key`) or value (`org.apache.kafka.connect.transforms.ExtractField$Value`).



- ###### [field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.ExtractField_field)

  Field name to extract.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: |        |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.SetSchemaMetadata

Set the schema name, version or both on the record's key (`org.apache.kafka.connect.transforms.SetSchemaMetadata$Key`) or value (`org.apache.kafka.connect.transforms.SetSchemaMetadata$Value`) schema.



- ###### [schema.name](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.SetSchemaMetadata_schema.name)

  Schema name to set.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | null   |
  | Valid Values: |        |
  |   Importance: | high   |

- ###### [schema.version](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.SetSchemaMetadata_schema.version)

  Schema version to set.

  |         Type: | int  |
  | ------------: | ---- |
  |      Default: | null |
  | Valid Values: |      |
  |   Importance: | high |

##### org.apache.kafka.connect.transforms.TimestampRouter

Update the record's topic field as a function of the original topic value and the record timestamp.



This is mainly useful for sink connectors, since the topic field is often used to determine the equivalent entity name in the destination system(e.g. database table or search index name).



- ###### [timestamp.format](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.TimestampRouter_timestamp.format)

  Format string for the timestamp that is compatible with `java.text.SimpleDateFormat`.

  |         Type: | string   |
  | ------------: | -------- |
  |      Default: | yyyyMMdd |
  | Valid Values: |          |
  |   Importance: | high     |

- ###### [topic.format](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.TimestampRouter_topic.format)

  Format string which can contain `${topic}` and `${timestamp}` as placeholders for the topic and timestamp, respectively.

  |         Type: | string                |
  | ------------: | --------------------- |
  |      Default: | ${topic}-${timestamp} |
  | Valid Values: |                       |
  |   Importance: | high                  |

##### org.apache.kafka.connect.transforms.RegexRouter

Update the record topic using the configured regular expression and replacement string.



Under the hood, the regex is compiled to a `java.util.regex.Pattern`. If the pattern matches the input topic, `java.util.regex.Matcher#replaceFirst()` is used with the replacement string to obtain the new topic.



- ###### [regex](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.RegexRouter_regex)

  Regular expression to use for matching.

  |         Type: | string      |
  | ------------: | ----------- |
  |      Default: |             |
  | Valid Values: | valid regex |
  |   Importance: | high        |

- ###### [replacement](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.RegexRouter_replacement)

  Replacement string.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: |        |
  | Valid Values: |        |
  |   Importance: | high   |

##### org.apache.kafka.connect.transforms.Flatten

Flatten a nested data structure, generating names for each field by concatenating the field names at each level with a configurable delimiter character. Applies to Struct when schema present, or a Map in the case of schemaless data. The default delimiter is '.'.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.Flatten$Key`) or value (`org.apache.kafka.connect.transforms.Flatten$Value`).



- ###### [delimiter](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.Flatten_delimiter)

  Delimiter to insert between field names from the input record when generating field names for the output record

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | .      |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.Cast

Cast fields or the entire key or value to a specific type, e.g. to force an integer field to a smaller width. Only simple primitive types are supported -- integers, floats, boolean, and string.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.Cast$Key`) or value (`org.apache.kafka.connect.transforms.Cast$Value`).



- ###### [spec](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.Cast_spec)

  List of fields and the type to cast them to of the form field1:type,field2:type to cast fields of Maps or Structs. A single type to cast the entire value. Valid types are int8, int16, int32, int64, float32, float64, boolean, and string.

  |         Type: | list                                                  |
  | ------------: | ----------------------------------------------------- |
  |      Default: |                                                       |
  | Valid Values: | list of colon-delimited pairs, e.g. `foo:bar,abc:xyz` |
  |   Importance: | high                                                  |

##### org.apache.kafka.connect.transforms.TimestampConverter

Convert timestamps between different formats such as Unix epoch, strings, and Connect Date/Timestamp types.Applies to individual fields or to the entire value.



Use the concrete transformation type designed for the record key (`org.apache.kafka.connect.transforms.TimestampConverter$Key`) or value (`org.apache.kafka.connect.transforms.TimestampConverter$Value`).



- ###### [target.type](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.TimestampConverter_target.type)

  The desired timestamp representation: string, unix, Date, Time, or Timestamp

  |         Type: | string |
  | ------------: | ------ |
  |      Default: |        |
  | Valid Values: |        |
  |   Importance: | high   |

- ###### [field](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.TimestampConverter_field)

  The field containing the timestamp, or empty if the entire value is a timestamp

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | ""     |
  | Valid Values: |        |
  |   Importance: | high   |

- ###### [format](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.TimestampConverter_format)

  A SimpleDateFormat-compatible format for the timestamp. Used to generate the output when type=string or used to parse the input if the input is a string.

  |         Type: | string |
  | ------------: | ------ |
  |      Default: | ""     |
  | Valid Values: |        |
  |   Importance: | medium |

##### org.apache.kafka.connect.transforms.Filter

Drops all records, filtering them from subsequent transformations in the chain. This is intended to be used conditionally to filter out records matching (or not matching) a particular Predicate.





##### [Predicates](http://kafka.apache.org/documentation/#connect_predicates)

Transformations can be configured with predicates so that the transformation is applied only to messages which satisfy some condition. In particular, when combined with the **Filter** transformation predicates can be used to selectively filter out certain messages.

Predicates are specified in the connector configuration.

- `predicates` - Set of aliases for the predicates to be applied to some of the transformations.
- `predicates.$alias.type` - Fully qualified class name for the predicate.
- `predicates.$alias.$predicateSpecificConfig` - Configuration properties for the predicate.

All transformations have the implicit config properties `predicate` and `negate`. A predicular predicate is associated with a transformation by setting the transformation's `predicate` config to the predicate's alias. The predicate's value can be reversed using the `negate` configuration property.

For example, suppose you have a source connector which produces messages to many different topics and you want to:

- filter out the messages in the 'foo' topic entirely
- apply the ExtractField transformation with the field name 'other_field' to records in all topics *except* the topic 'bar'

To do this we need first to filter out the records destined for the topic 'foo'. The Filter transformation removes records from further processing, and can use the TopicNameMatches predicate to apply the transformation only to records in topics which match a certain regular expression. TopicNameMatches's only configuration property is `pattern` which is a Java regular expression for matching against the topic name. The configuration would look like this:

```text
        transforms=Filter
        transforms.Filter.type=org.apache.kafka.connect.transforms.Filter
        transforms.Filter.predicate=IsFoo

        predicates=IsFoo
        predicates.IsFoo.type=org.apache.kafka.connect.predicates.TopicNameMatches
        predicates.IsFoo.pattern=foo
```

Next we need to apply ExtractField only when the topic name of the record is not 'bar'. We can't just use TopicNameMatches directly, because that would apply the transformation to matching topic names, not topic names which do *not* match. The transformation's implicit `negate` config properties allows us to invert the set of records which a predicate matches. Adding the configuration for this to the previous example we arrive at:

```text
        transforms=Filter,Extract
        transforms.Filter.type=org.apache.kafka.connect.transforms.Filter
        transforms.Filter.predicate=IsFoo

        transforms.Extract.type=org.apache.kafka.connect.transforms.ExtractField$Key
        transforms.Extract.field=other_field
        transforms.Extract.predicate=IsBar
        transforms.Extract.negate=true

        predicates=IsFoo,IsBar
        predicates.IsFoo.type=org.apache.kafka.connect.predicates.TopicNameMatches
        predicates.IsFoo.pattern=foo

        predicates.IsBar.type=org.apache.kafka.connect.predicates.TopicNameMatches
        predicates.IsBar.pattern=bar
```

Kafka Connect includes the following predicates:

- `TopicNameMatches` - matches records in a topic with a name matching a particular Java regular expression.
- `HasHeaderKey` - matches records which have a header with the given key.
- `RecordIsTombstone` - matches tombstone records, that is records with a null value.

Details on how to configure each predicate are listed below:

##### org.apache.kafka.connect.transforms.predicates.HasHeaderKey

A predicate which is true for records with at least one header with the configured name.



- ###### [name](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.predicates.HasHeaderKey_name)

  The header name.

  |         Type: | string           |
  | ------------: | ---------------- |
  |      Default: |                  |
  | Valid Values: | non-empty string |
  |   Importance: | medium           |

##### org.apache.kafka.connect.transforms.predicates.RecordIsTombstone

A predicate which is true for records which are tombstones (i.e. have null value).





##### org.apache.kafka.connect.transforms.predicates.TopicNameMatches

A predicate which is true for records with a topic name that matches the configured regular expression.



- ###### [pattern](http://kafka.apache.org/documentation/#org.apache.kafka.connect.transforms.predicates.TopicNameMatches_pattern)

  A Java regular expression for matching against the name of a record's topic.

  |         Type: | string                        |
  | ------------: | ----------------------------- |
  |      Default: |                               |
  | Valid Values: | non-empty string, valid regex |
  |   Importance: | medium                        |

#### [REST API](http://kafka.apache.org/documentation/#connect_rest)

Since Kafka Connect is intended to be run as a service, it also provides a REST API for managing connectors. The REST API server can be configured using the `listeners` configuration option. This field should contain a list of listeners in the following format: `protocol://host:port,protocol2://host2:port2`. Currently supported protocols are `http` and `https`. For example:

```text
        listeners=http://localhost:8080,https://localhost:8443
```

By default, if no `listeners` are specified, the REST server runs on port 8083 using the HTTP protocol. When using HTTPS, the configuration has to include the SSL configuration. By default, it will use the `ssl.*` settings. In case it is needed to use different configuration for the REST API than for connecting to Kafka brokers, the fields can be prefixed with `listeners.https`. When using the prefix, only the prefixed options will be used and the `ssl.*` options without the prefix will be ignored. Following fields can be used to configure HTTPS for the REST API:

- `ssl.keystore.location`
- `ssl.keystore.password`
- `ssl.keystore.type`
- `ssl.key.password`
- `ssl.truststore.location`
- `ssl.truststore.password`
- `ssl.truststore.type`
- `ssl.enabled.protocols`
- `ssl.provider`
- `ssl.protocol`
- `ssl.cipher.suites`
- `ssl.keymanager.algorithm`
- `ssl.secure.random.implementation`
- `ssl.trustmanager.algorithm`
- `ssl.endpoint.identification.algorithm`
- `ssl.client.auth`

The REST API is used not only by users to monitor / manage Kafka Connect. It is also used for the Kafka Connect cross-cluster communication. Requests received on the follower nodes REST API will be forwarded to the leader node REST API. In case the URI under which is given host reachable is different from the URI which it listens on, the configuration options `rest.advertised.host.name`, `rest.advertised.port` and `rest.advertised.listener` can be used to change the URI which will be used by the follower nodes to connect with the leader. When using both HTTP and HTTPS listeners, the `rest.advertised.listener` option can be also used to define which listener will be used for the cross-cluster communication. When using HTTPS for communication between nodes, the same `ssl.*` or `listeners.https` options will be used to configure the HTTPS client.

The following are the currently supported REST API endpoints:

- `GET /connectors` - return a list of active connectors
- `POST /connectors` - create a new connector; the request body should be a JSON object containing a string `name` field and an object `config` field with the connector configuration parameters
- `GET /connectors/{name}` - get information about a specific connector
- `GET /connectors/{name}/config` - get the configuration parameters for a specific connector
- `PUT /connectors/{name}/config` - update the configuration parameters for a specific connector
- `GET /connectors/{name}/status` - get current status of the connector, including if it is running, failed, paused, etc., which worker it is assigned to, error information if it has failed, and the state of all its tasks
- `GET /connectors/{name}/tasks` - get a list of tasks currently running for a connector
- `GET /connectors/{name}/tasks/{taskid}/status` - get current status of the task, including if it is running, failed, paused, etc., which worker it is assigned to, and error information if it has failed
- `PUT /connectors/{name}/pause` - pause the connector and its tasks, which stops message processing until the connector is resumed
- `PUT /connectors/{name}/resume` - resume a paused connector (or do nothing if the connector is not paused)
- `POST /connectors/{name}/restart` - restart a connector (typically because it has failed)
- `POST /connectors/{name}/tasks/{taskId}/restart` - restart an individual task (typically because it has failed)
- `DELETE /connectors/{name}` - delete a connector, halting all tasks and deleting its configuration
- `GET /connectors/{name}/topics` - get the set of topics that a specific connector is using since the connector was created or since a request to reset its set of active topics was issued
- `PUT /connectors/{name}/topics/reset` - send a request to empty the set of active topics of a connector

Kafka Connect also provides a REST API for getting information about connector plugins:

- `GET /connector-plugins`- return a list of connector plugins installed in the Kafka Connect cluster. Note that the API only checks for connectors on the worker that handles the request, which means you may see inconsistent results, especially during a rolling upgrade if you add new connector jars
- `PUT /connector-plugins/{connector-type}/config/validate` - validate the provided configuration values against the configuration definition. This API performs per config validation, returns suggested values and error messages during validation.

The following is a supported REST request at the top-level (root) endpoint:

- `GET /`- return basic information about the Kafka Connect cluster such as the version of the Connect worker that serves the REST request (including git commit ID of the source code) and the Kafka cluster ID that is connected to.

#### [Error Reporting in Connect](http://kafka.apache.org/documentation/#connect_errorreporting)

Kafka Connect provides error reporting to handle errors encountered along various stages of processing. By default, any error encountered during conversion or within transformations will cause the connector to fail. Each connector configuration can also enable tolerating such errors by skipping them, optionally writing each error and the details of the failed operation and problematic record (with various levels of detail) to the Connect application log. These mechanisms also capture errors when a sink connector is processing the messages consumed from its Kafka topics, and all of the errors can be written to a configurable "dead letter queue" (DLQ) Kafka topic.

To report errors within a connector's converter, transforms, or within the sink connector itself to the log, set `errors.log.enable=true` in the connector configuration to log details of each error and problem record's topic, partition, and offset. For additional debugging purposes, set `errors.log.include.messages=true` to also log the problem record key, value, and headers to the log (note this may log sensitive information).

To report errors within a connector's converter, transforms, or within the sink connector itself to a dead letter queue topic, set `errors.deadletterqueue.topic.name`, and optionally `errors.deadletterqueue.context.headers.enable=true`.

By default connectors exhibit "fail fast" behavior immediately upon an error or exception. This is equivalent to adding the following configuration properties with their defaults to a connector configuration:

```text
        # disable retries on failure
        errors.retry.timeout=0

        # do not log the error and their contexts
        errors.log.enable=false

        # do not record errors in a dead letter queue topic
        errors.deadletterqueue.topic.name=

        # Fail on first error
        errors.tolerance=none
```

These and other related connector configuration properties can be changed to provide different behavior. For example, the following configuration properties can be added to a connector configuration to setup error handling with multiple retries, logging to the application logs and the `my-connector-errors` Kafka topic, and tolerating all errors by reporting them rather than failing the connector task:

```text
        # retry for at most 10 minutes times waiting up to 30 seconds between consecutive failures
        errors.retry.timeout=600000
        errors.retry.delay.max.ms=30000

        # log error context along with application logs, but do not include configs and messages
        errors.log.enable=true
        errors.log.include.messages=false

        # produce error context into the Kafka topic
        errors.deadletterqueue.topic.name=my-connector-errors

        # Tolerate all errors.
        errors.tolerance=all
```

### [8.3 Connector Development Guide](http://kafka.apache.org/documentation/#connect_development)

This guide describes how developers can write new connectors for Kafka Connect to move data between Kafka and other systems. It briefly reviews a few key concepts and then describes how to create a simple connector.

#### [Core Concepts and APIs](http://kafka.apache.org/documentation/#connect_concepts)

##### [Connectors and Tasks](http://kafka.apache.org/documentation/#connect_connectorsandtasks)

To copy data between Kafka and another system, users create a `Connector` for the system they want to pull data from or push data to. Connectors come in two flavors: `SourceConnectors` import data from another system (e.g. `JDBCSourceConnector` would import a relational database into Kafka) and `SinkConnectors` export data (e.g. `HDFSSinkConnector` would export the contents of a Kafka topic to an HDFS file).

`Connectors` do not perform any data copying themselves: their configuration describes the data to be copied, and the `Connector` is responsible for breaking that job into a set of `Tasks` that can be distributed to workers. These `Tasks` also come in two corresponding flavors: `SourceTask` and `SinkTask`.

With an assignment in hand, each `Task` must copy its subset of the data to or from Kafka. In Kafka Connect, it should always be possible to frame these assignments as a set of input and output streams consisting of records with consistent schemas. Sometimes this mapping is obvious: each file in a set of log files can be considered a stream with each parsed line forming a record using the same schema and offsets stored as byte offsets in the file. In other cases it may require more effort to map to this model: a JDBC connector can map each table to a stream, but the offset is less clear. One possible mapping uses a timestamp column to generate queries incrementally returning new data, and the last queried timestamp can be used as the offset.

##### [Streams and Records](http://kafka.apache.org/documentation/#connect_streamsandrecords)

Each stream should be a sequence of key-value records. Both the keys and values can have complex structure -- many primitive types are provided, but arrays, objects, and nested data structures can be represented as well. The runtime data format does not assume any particular serialization format; this conversion is handled internally by the framework.

In addition to the key and value, records (both those generated by sources and those delivered to sinks) have associated stream IDs and offsets. These are used by the framework to periodically commit the offsets of data that have been processed so that in the event of failures, processing can resume from the last committed offsets, avoiding unnecessary reprocessing and duplication of events.

##### [Dynamic Connectors](http://kafka.apache.org/documentation/#connect_dynamicconnectors)

Not all jobs are static, so `Connector` implementations are also responsible for monitoring the external system for any changes that might require reconfiguration. For example, in the `JDBCSourceConnector` example, the `Connector` might assign a set of tables to each `Task`. When a new table is created, it must discover this so it can assign the new table to one of the `Tasks` by updating its configuration. When it notices a change that requires reconfiguration (or a change in the number of `Tasks`), it notifies the framework and the framework updates any corresponding `Tasks`.

#### [Developing a Simple Connector](http://kafka.apache.org/documentation/#connect_developing)

Developing a connector only requires implementing two interfaces, the `Connector` and `Task`. A simple example is included with the source code for Kafka in the `file` package. This connector is meant for use in standalone mode and has implementations of a `SourceConnector`/`SourceTask` to read each line of a file and emit it as a record and a `SinkConnector`/`SinkTask` that writes each record to a file.

The rest of this section will walk through some code to demonstrate the key steps in creating a connector, but developers should also refer to the full example source code as many details are omitted for brevity.

##### [Connector Example](http://kafka.apache.org/documentation/#connect_connectorexample)

We'll cover the `SourceConnector` as a simple example. `SinkConnector` implementations are very similar. Start by creating the class that inherits from `SourceConnector` and add a couple of fields that will store parsed configuration information (the filename to read from and the topic to send data to):

```java
    public class FileStreamSourceConnector extends SourceConnector {
        private String filename;
        private String topic;
```

The easiest method to fill in is `taskClass()`, which defines the class that should be instantiated in worker processes to actually read the data:

```java
    @Override
    public Class<? extends Task> taskClass() {
        return FileStreamSourceTask.class;
    }
```

We will define the `FileStreamSourceTask` class below. Next, we add some standard lifecycle methods, `start()` and `stop()`:

```java
    @Override
    public void start(Map<String, String> props) {
        // The complete version includes error handling as well.
        filename = props.get(FILE_CONFIG);
        topic = props.get(TOPIC_CONFIG);
    }

    @Override
    public void stop() {
        // Nothing to do since no background monitoring is required.
    }
```

Finally, the real core of the implementation is in `taskConfigs()`. In this case we are only handling a single file, so even though we may be permitted to generate more tasks as per the `maxTasks` argument, we return a list with only one entry:

```java
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        // Only one input stream makes sense.
        Map<String, String> config = new HashMap<>();
        if (filename != null)
            config.put(FILE_CONFIG, filename);
        config.put(TOPIC_CONFIG, topic);
        configs.add(config);
        return configs;
    }
```

Although not used in the example, `SourceTask` also provides two APIs to commit offsets in the source system: `commit` and `commitRecord`. The APIs are provided for source systems which have an acknowledgement mechanism for messages. Overriding these methods allows the source connector to acknowledge messages in the source system, either in bulk or individually, once they have been written to Kafka. The `commit` API stores the offsets in the source system, up to the offsets that have been returned by `poll`. The implementation of this API should block until the commit is complete. The `commitRecord` API saves the offset in the source system for each `SourceRecord` after it is written to Kafka. As Kafka Connect will record offsets automatically, `SourceTask`s are not required to implement them. In cases where a connector does need to acknowledge messages in the source system, only one of the APIs is typically required.

Even with multiple tasks, this method implementation is usually pretty simple. It just has to determine the number of input tasks, which may require contacting the remote service it is pulling data from, and then divvy them up. Because some patterns for splitting work among tasks are so common, some utilities are provided in `ConnectorUtils` to simplify these cases.

Note that this simple example does not include dynamic input. See the discussion in the next section for how to trigger updates to task configs.

##### [Task Example - Source Task](http://kafka.apache.org/documentation/#connect_taskexample)

Next we'll describe the implementation of the corresponding `SourceTask`. The implementation is short, but too long to cover completely in this guide. We'll use pseudo-code to describe most of the implementation, but you can refer to the source code for the full example.

Just as with the connector, we need to create a class inheriting from the appropriate base `Task` class. It also has some standard lifecycle methods:

```java
    public class FileStreamSourceTask extends SourceTask {
        String filename;
        InputStream stream;
        String topic;

        @Override
        public void start(Map<String, String> props) {
            filename = props.get(FileStreamSourceConnector.FILE_CONFIG);
            stream = openOrThrowError(filename);
            topic = props.get(FileStreamSourceConnector.TOPIC_CONFIG);
        }

        @Override
        public synchronized void stop() {
            stream.close();
        }
```

These are slightly simplified versions, but show that these methods should be relatively simple and the only work they should perform is allocating or freeing resources. There are two points to note about this implementation. First, the `start()` method does not yet handle resuming from a previous offset, which will be addressed in a later section. Second, the `stop()` method is synchronized. This will be necessary because `SourceTasks` are given a dedicated thread which they can block indefinitely, so they need to be stopped with a call from a different thread in the Worker.

Next, we implement the main functionality of the task, the `poll()` method which gets events from the input system and returns a `List<SourceRecord>`:

```java
    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        try {
            ArrayList<SourceRecord> records = new ArrayList<>();
            while (streamValid(stream) && records.isEmpty()) {
                LineAndOffset line = readToNextLine(stream);
                if (line != null) {
                    Map<String, Object> sourcePartition = Collections.singletonMap("filename", filename);
                    Map<String, Object> sourceOffset = Collections.singletonMap("position", streamOffset);
                    records.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.STRING_SCHEMA, line));
                } else {
                    Thread.sleep(1);
                }
            }
            return records;
        } catch (IOException e) {
            // Underlying stream was killed, probably as a result of calling stop. Allow to return
            // null, and driving thread will handle any shutdown if necessary.
        }
        return null;
    }
```

Again, we've omitted some details, but we can see the important steps: the `poll()` method is going to be called repeatedly, and for each call it will loop trying to read records from the file. For each line it reads, it also tracks the file offset. It uses this information to create an output `SourceRecord` with four pieces of information: the source partition (there is only one, the single file being read), source offset (byte offset in the file), output topic name, and output value (the line, and we include a schema indicating this value will always be a string). Other variants of the `SourceRecord` constructor can also include a specific output partition, a key, and headers.

Note that this implementation uses the normal Java `InputStream` interface and may sleep if data is not available. This is acceptable because Kafka Connect provides each task with a dedicated thread. While task implementations have to conform to the basic `poll()` interface, they have a lot of flexibility in how they are implemented. In this case, an NIO-based implementation would be more efficient, but this simple approach works, is quick to implement, and is compatible with older versions of Java.

##### [Sink Tasks](http://kafka.apache.org/documentation/#connect_sinktasks)

The previous section described how to implement a simple `SourceTask`. Unlike `SourceConnector` and `SinkConnector`, `SourceTask` and `SinkTask` have very different interfaces because `SourceTask` uses a pull interface and `SinkTask` uses a push interface. Both share the common lifecycle methods, but the `SinkTask` interface is quite different:

```java
    public abstract class SinkTask implements Task {
        public void initialize(SinkTaskContext context) {
            this.context = context;
        }

        public abstract void put(Collection<SinkRecord> records);

        public void flush(Map<TopicPartition, OffsetAndMetadata> currentOffsets) {
        }
```

The `SinkTask` documentation contains full details, but this interface is nearly as simple as the `SourceTask`. The `put()` method should contain most of the implementation, accepting sets of `SinkRecords`, performing any required translation, and storing them in the destination system. This method does not need to ensure the data has been fully written to the destination system before returning. In fact, in many cases internal buffering will be useful so an entire batch of records can be sent at once, reducing the overhead of inserting events into the downstream data store. The `SinkRecords` contain essentially the same information as `SourceRecords`: Kafka topic, partition, offset, the event key and value, and optional headers.

The `flush()` method is used during the offset commit process, which allows tasks to recover from failures and resume from a safe point such that no events will be missed. The method should push any outstanding data to the destination system and then block until the write has been acknowledged. The `offsets` parameter can often be ignored, but is useful in some cases where implementations want to store offset information in the destination store to provide exactly-once delivery. For example, an HDFS connector could do this and use atomic move operations to make sure the `flush()` operation atomically commits the data and offsets to a final location in HDFS.

##### [Errant Record Reporter](http://kafka.apache.org/documentation/connect_errantrecordreporter)

When [error reporting](http://kafka.apache.org/documentation/#connect_errorreporting) is enabled for a connector, the connector can use an `ErrantRecordReporter` to report problems with individual records sent to a sink connector. The following example shows how a connector's `SinkTask` subclass might obtain and use the `ErrantRecordReporter`, safely handling a null reporter when the DLQ is not enabled or when the connector is installed in an older Connect runtime that doesn't have this reporter feature:

```java
        private ErrantRecordReporter reporter;

        @Override
        public void start(Map<String, String> props) {
            ...
            try {
                reporter = context.errantRecordReporter(); // may be null if DLQ not enabled
            } catch (NoSuchMethodException | NoClassDefFoundError e) {
                // Will occur in Connect runtimes earlier than 2.6
                reporter = null;
            }
        }

        @Override
        public void put(Collection<SinkRecord> records) {
            for (SinkRecord record: records) {
                try {
                    // attempt to process and send record to data sink
                    process(record);
                } catch(Exception e) {
                    if (reporter != null) {
                        // Send errant record to error reporter
                        reporter.report(record, e);
                    } else {
                        // There's no error reporter, so fail
                        throw new ConnectException("Failed on record", e);
                    }
                }
            }
        }
```

##### [Resuming from Previous Offsets](http://kafka.apache.org/documentation/#connect_resuming)

The `SourceTask` implementation included a stream ID (the input filename) and offset (position in the file) with each record. The framework uses this to commit offsets periodically so that in the case of a failure, the task can recover and minimize the number of events that are reprocessed and possibly duplicated (or to resume from the most recent offset if Kafka Connect was stopped gracefully, e.g. in standalone mode or due to a job reconfiguration). This commit process is completely automated by the framework, but only the connector knows how to seek back to the right position in the input stream to resume from that location.

To correctly resume upon startup, the task can use the `SourceContext` passed into its `initialize()` method to access the offset data. In `initialize()`, we would add a bit more code to read the offset (if it exists) and seek to that position:

```java
        stream = new FileInputStream(filename);
        Map<String, Object> offset = context.offsetStorageReader().offset(Collections.singletonMap(FILENAME_FIELD, filename));
        if (offset != null) {
            Long lastRecordedOffset = (Long) offset.get("position");
            if (lastRecordedOffset != null)
                seekToOffset(stream, lastRecordedOffset);
        }
```

Of course, you might need to read many keys for each of the input streams. The `OffsetStorageReader` interface also allows you to issue bulk reads to efficiently load all offsets, then apply them by seeking each input stream to the appropriate position.

#### [Dynamic Input/Output Streams](http://kafka.apache.org/documentation/#connect_dynamicio)

Kafka Connect is intended to define bulk data copying jobs, such as copying an entire database rather than creating many jobs to copy each table individually. One consequence of this design is that the set of input or output streams for a connector can vary over time.

Source connectors need to monitor the source system for changes, e.g. table additions/deletions in a database. When they pick up changes, they should notify the framework via the `ConnectorContext` object that reconfiguration is necessary. For example, in a `SourceConnector`:

```java
        if (inputsChanged())
            this.context.requestTaskReconfiguration();
```

The framework will promptly request new configuration information and update the tasks, allowing them to gracefully commit their progress before reconfiguring them. Note that in the `SourceConnector` this monitoring is currently left up to the connector implementation. If an extra thread is required to perform this monitoring, the connector must allocate it itself.

Ideally this code for monitoring changes would be isolated to the `Connector` and tasks would not need to worry about them. However, changes can also affect tasks, most commonly when one of their input streams is destroyed in the input system, e.g. if a table is dropped from a database. If the `Task` encounters the issue before the `Connector`, which will be common if the `Connector` needs to poll for changes, the `Task` will need to handle the subsequent error. Thankfully, this can usually be handled simply by catching and handling the appropriate exception.

`SinkConnectors` usually only have to handle the addition of streams, which may translate to new entries in their outputs (e.g., a new database table). The framework manages any changes to the Kafka input, such as when the set of input topics changes because of a regex subscription. `SinkTasks` should expect new input streams, which may require creating new resources in the downstream system, such as a new table in a database. The trickiest situation to handle in these cases may be conflicts between multiple `SinkTasks` seeing a new input stream for the first time and simultaneously trying to create the new resource. `SinkConnectors`, on the other hand, will generally require no special code for handling a dynamic set of streams.

#### [Connect Configuration Validation](http://kafka.apache.org/documentation/#connect_configs)

Kafka Connect allows you to validate connector configurations before submitting a connector to be executed and can provide feedback about errors and recommended values. To take advantage of this, connector developers need to provide an implementation of `config()` to expose the configuration definition to the framework.

The following code in `FileStreamSourceConnector` defines the configuration and exposes it to the framework.

```java
        private static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(FILE_CONFIG, Type.STRING, Importance.HIGH, "Source filename.")
            .define(TOPIC_CONFIG, Type.STRING, Importance.HIGH, "The topic to publish data to");

        public ConfigDef config() {
            return CONFIG_DEF;
        }
```

`ConfigDef` class is used for specifying the set of expected configurations. For each configuration, you can specify the name, the type, the default value, the documentation, the group information, the order in the group, the width of the configuration value and the name suitable for display in the UI. Plus, you can provide special validation logic used for single configuration validation by overriding the `Validator` class. Moreover, as there may be dependencies between configurations, for example, the valid values and visibility of a configuration may change according to the values of other configurations. To handle this, `ConfigDef` allows you to specify the dependents of a configuration and to provide an implementation of `Recommender` to get valid values and set visibility of a configuration given the current configuration values.

Also, the `validate()` method in `Connector` provides a default validation implementation which returns a list of allowed configurations together with configuration errors and recommended values for each configuration. However, it does not use the recommended values for configuration validation. You may provide an override of the default implementation for customized configuration validation, which may use the recommended values.

#### [Working with Schemas](http://kafka.apache.org/documentation/#connect_schemas)

The FileStream connectors are good examples because they are simple, but they also have trivially structured data -- each line is just a string. Almost all practical connectors will need schemas with more complex data formats.

To create more complex data, you'll need to work with the Kafka Connect `data` API. Most structured records will need to interact with two classes in addition to primitive types: `Schema` and `Struct`.

The API documentation provides a complete reference, but here is a simple example creating a `Schema` and `Struct`:

```java
    Schema schema = SchemaBuilder.struct().name(NAME)
        .field("name", Schema.STRING_SCHEMA)
        .field("age", Schema.INT_SCHEMA)
        .field("admin", SchemaBuilder.bool().defaultValue(false).build())
        .build();

    Struct struct = new Struct(schema)
        .put("name", "Barbara Liskov")
        .put("age", 75);
```

If you are implementing a source connector, you'll need to decide when and how to create schemas. Where possible, you should avoid recomputing them as much as possible. For example, if your connector is guaranteed to have a fixed schema, create it statically and reuse a single instance.

However, many connectors will have dynamic schemas. One simple example of this is a database connector. Considering even just a single table, the schema will not be predefined for the entire connector (as it varies from table to table). But it also may not be fixed for a single table over the lifetime of the connector since the user may execute an `ALTER TABLE` command. The connector must be able to detect these changes and react appropriately.

Sink connectors are usually simpler because they are consuming data and therefore do not need to create schemas. However, they should take just as much care to validate that the schemas they receive have the expected format. When the schema does not match -- usually indicating the upstream producer is generating invalid data that cannot be correctly translated to the destination system -- sink connectors should throw an exception to indicate this error to the system.

#### [Kafka Connect Administration](http://kafka.apache.org/documentation/#connect_administration)

Kafka Connect's [REST layer](http://kafka.apache.org/documentation/#connect_rest) provides a set of APIs to enable administration of the cluster. This includes APIs to view the configuration of connectors and the status of their tasks, as well as to alter their current behavior (e.g. changing configuration and restarting tasks).

When a connector is first submitted to the cluster, a rebalance is triggered between the Connect workers in order to distribute the load that consists of the tasks of the new connector. This same rebalancing procedure is also used when connectors increase or decrease the number of tasks they require, when a connector's configuration is changed, or when a worker is added or removed from the group as part of an intentional upgrade of the Connect cluster or due to a failure.

In versions prior to 2.3.0, the Connect workers would rebalance the full set of connectors and their tasks in the cluster as a simple way to make sure that each worker has approximately the same amount of work. This behavior can be still enabled by setting `connect.protocol=eager`.

Starting with 2.3.0, Kafka Connect is using by default a protocol that performs [incremental cooperative rebalancing](https://cwiki.apache.org/confluence/display/KAFKA/KIP-415%3A+Incremental+Cooperative+Rebalancing+in+Kafka+Connect) that incrementally balances the connectors and tasks across the Connect workers, affecting only tasks that are new, to be removed, or need to move from one worker to another. Other tasks are not stopped and restarted during the rebalance, as they would have been with the old protocol.

If a Connect worker leaves the group, intentionally or due to a failure, Connect waits for `scheduled.rebalance.max.delay.ms` before triggering a rebalance. This delay defaults to five minutes (`300000ms`) to tolerate failures or upgrades of workers without immediately redistributing the load of a departing worker. If this worker returns within the configured delay, it gets its previously assigned tasks in full. However, this means that the tasks will remain unassigned until the time specified by `scheduled.rebalance.max.delay.ms` elapses. If a worker does not return within that time limit, Connect will reassign those tasks among the remaining workers in the Connect cluster.

The new Connect protocol is enabled when all the workers that form the Connect cluster are configured with `connect.protocol=compatible`, which is also the default value when this property is missing. Therefore, upgrading to the new Connect protocol happens automatically when all the workers upgrade to 2.3.0. A rolling upgrade of the Connect cluster will activate incremental cooperative rebalancing when the last worker joins on version 2.3.0.

You can use the REST API to view the current status of a connector and its tasks, including the ID of the worker to which each was assigned. For example, the `GET /connectors/file-source/status` request shows the status of a connector named `file-source`:

```json
    {
    "name": "file-source",
    "connector": {
        "state": "RUNNING",
        "worker_id": "192.168.1.208:8083"
    },
    "tasks": [
        {
        "id": 0,
        "state": "RUNNING",
        "worker_id": "192.168.1.209:8083"
        }
    ]
    }
```

Connectors and their tasks publish status updates to a shared topic (configured with `status.storage.topic`) which all workers in the cluster monitor. Because the workers consume this topic asynchronously, there is typically a (short) delay before a state change is visible through the status API. The following states are possible for a connector or one of its tasks:

- **UNASSIGNED:** The connector/task has not yet been assigned to a worker.
- **RUNNING:** The connector/task is running.
- **PAUSED:** The connector/task has been administratively paused.
- **FAILED:** The connector/task has failed (usually by raising an exception, which is reported in the status output).
- **DESTROYED:** The connector/task has been administratively removed and will stop appearing in the Connect cluster.

In most cases, connector and task states will match, though they may be different for short periods of time when changes are occurring or if tasks have failed. For example, when a connector is first started, there may be a noticeable delay before the connector and its tasks have all transitioned to the RUNNING state. States will also diverge when tasks fail since Connect does not automatically restart failed tasks. To restart a connector/task manually, you can use the restart APIs listed above. Note that if you try to restart a task while a rebalance is taking place, Connect will return a 409 (Conflict) status code. You can retry after the rebalance completes, but it might not be necessary since rebalances effectively restart all the connectors and tasks in the cluster.

Starting with 2.5.0, Kafka Connect uses the `status.storage.topic` to also store information related to the topics that each connector is using. Connect Workers use these per-connector topic status updates to respond to requests to the REST endpoint `GET /connectors/{name}/topics` by returning the set of topic names that a connector is using. A request to the REST endpoint `PUT /connectors/{name}/topics/reset` resets the set of active topics for a connector and allows a new set to be populated, based on the connector's latest pattern of topic usage. Upon connector deletion, the set of the connector's active topics is also deleted. Topic tracking is enabled by default but can be disabled by setting `topic.tracking.enable=false`. If you want to disallow requests to reset the active topics of connectors during runtime, set the Worker property `topic.tracking.allow.reset=false`.

It's sometimes useful to temporarily stop the message processing of a connector. For example, if the remote system is undergoing maintenance, it would be preferable for source connectors to stop polling it for new data instead of filling logs with exception spam. For this use case, Connect offers a pause/resume API. While a source connector is paused, Connect will stop polling it for additional records. While a sink connector is paused, Connect will stop pushing new messages to it. The pause state is persistent, so even if you restart the cluster, the connector will not begin message processing again until the task has been resumed. Note that there may be a delay before all of a connector's tasks have transitioned to the PAUSED state since it may take time for them to finish whatever processing they were in the middle of when being paused. Additionally, failed tasks will not transition to the PAUSED state until they have been restarted.

## [9. KAFKA STREAMS](http://kafka.apache.org/documentation/streams)

Kafka Streams is a client library for processing and analyzing data stored in Kafka. It builds upon important stream processing concepts such as properly distinguishing between event time and processing time, windowing support, exactly-once processing semantics and simple yet efficient management of application state.

Kafka Streams has a **low barrier to entry**: You can quickly write and run a small-scale proof-of-concept on a single machine; and you only need to run additional instances of your application on multiple machines to scale up to high-volume production workloads. Kafka Streams transparently handles the load balancing of multiple instances of the same application by leveraging Kafka's parallelism model.

Learn More about Kafka Streams read [this](http://kafka.apache.org/documentation/streams) Section