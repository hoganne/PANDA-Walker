# spring5_design

Using a Spring container to manage beans with the Factory pattern

使用spring容器结合工厂模式

spring容器中spring bean的生命周期：

1.加载所有bean定义，创建一个有序图。

2.实例化并运行BeanFactoryPostProcessors（您可以在此处更新Bean定义）。

3.实例化每个bean。

4.Spring将值和bean引用注入bean的属性中。

5.Spring将Bean的ID传递给BeanNameAware的setBeanName（）方法。

接口（如果有）实现它。

6.Spring将bean工厂本身的引用传递给setBeanFactory（）

BeanFactoryAware的方法（如果有）实现它。

7.如果有任何bean实现，Spring会将应用程序上下文本身的引用传递给ApplicationContextAware的setApplicationContext（）方法。

8.BeanPostProcessor是一个接口，Spring允许您使用bean来实现它，并可以通过调用bean的postProcessBeforeInitialization（）在Spring bean容器中调用初始化程序之前修改bean的实例。

9.如果您的bean实现了InitializingBean接口，则Spring调用其afterPropertiesSet（）方法来初始化应用程序的任何进程或加载资源。它取决于您指定的初始化方法。还有其他的方法来实现这一步骤中，例如，可以使用的初始化方法的的<bean>标记中，和initMethod所述的属性@Bean注释和JSR 250的@PostConstruct注解。

10.BeanPostProcessor是一个接口，spring允许您使用bean实现它。在Spring bean容器中调用初始化程序之后，它通过调用其postProcessAfterInitialization（）来修改bean的实例。

11.现在，您的Bean已准备好在该步骤中使用，并且您的应用程序可以使用应用程序上下文的getBean（）方法访问此Bean 。您的bean将一直存在于应用程序上下文中，直到通过调用应用程序上下文的close（）方法将其关闭。

12.如果您的bean实现了DisposibleBean接口，则Spring调用其destroy（）方法来销毁任何进程或清理应用程序的资源。还有其他方法可以实现此步骤-例如，您可以使用

破坏法的的<bean>标签，该了destroyMethod所述的属性@Bean

注释和JSR 250的@PreDestroy注释。

13.这些步骤显示了容器中Spring bean的生命周期。

14.下一部分描述了Spring框架提供的模块。

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_101.jpg)

### singleton设计模式

*确保一个类只有一个实例，并提供对其的全局访问点*------gof设计模式

Singleton模式是一种创新的设计模式，它是Java中最简单的设计模式之一。根据单例设计模式，该类为每个调用提供相同的单个对象-也就是说，它将类的实例化限制为一个对象，并提供对该类的全局访问点。因此，该类负责创建对象，并确保为该对象的每个客户端调用仅创建一个对象。此类不允许直接实例化此类的对象。它仅允许您通过公开的静态方法获取对象实例。

当恰好需要一个对象来协调整个系统中的动作时，这很有用。您可以使用两种形式创建单个模式，如下所示：

早期实例化：在加载时创建实例

延迟实例化：需要时创建实例

### Singleton模式的好处

1.它提供对关键（通常是重对象）类的控制器访问，例如，数据库的连接类和休眠中的`SessionFactory`类

2.它节省了内存

3.这是用于多线程环境的非常有效的设计

4.它更加灵活，因为类控制实例化过程，并且类具有更改实例化过程的灵活性。

5.它具有低延迟

Singleton模式仅解决一个问题-如果您的资源只能有一个实例，并且需要管理该实例，那么就需要一个`Singleton`。通常，如果您要在分布式和多线程环境中使用给定的配置创建数据库连接，则可能是每个线程都可以使用不同的配置对象创建新的数据库连接，如果您不遵循单例设计的话。使用`Singleton`模式，每个线程在系统中都将获得相同的数据库连接对象和相同的配置对象。它主要用于多线程和数据库应用程序。它用于日志记录，缓存，线程池，配置设置等。

### 原型设计模式（Prototype design pattern）

指定要使用原型实例创建的对象的类型，并通过复制此原型来创建新对象。-GOF设计模式

以下列表显示了使用Prototype模式的好处：

1.通过使用原型模式减少创建耗时对象的时间

2.这种模式减少了子类化

3.该模式在运行时添加和删除对象

4.此模式动态地为应用程序配置类，让我们看一下Prototype设计模式的UML类结构。

![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_177.jpg)

Prototype：原型是一个接口。它使用clone方法创建此接口类型的实例。

ConcretePrototype ：这是Prototype接口的具体类，用于实现克隆自身的操作。

**client**：这是一个调用程序类，用于通过调用原型接口的克隆方法来创建原型接口的新对象。

### Builder design pattern(生成器模式)

将复杂对象的构造与其表示分开，以便同一构造过程可以创建不同的表示。-GOF设计模式

Builder设计模式用于逐步构造复杂的对象，最后它将返回完整的对象。对象创建的逻辑和过程应该是通用的，以便您可以使用它来创建相同对象类型的不同具体实现。这种模式简化了复杂对象的构造，并从客户端调用程序代码中隐藏了对象构造的详细信息。使用此模式时，请记住您一次必须构建一个步骤，这意味着您必须将对象构造登录分为多个阶段，这与其他模式（例如抽象工厂和工厂方法模式）不同。一个步骤中的对象。

#### Builder模式的好处：

此模式为您提供了对象的构造和表示之间的完全隔离

这种模式使您可以分多个阶段构造对象，因此可以更好地控制构造过程

这种模式提供了改变对象内部表示形式的灵活性

 ![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_189.jpg)

Builder设计模式的UML图：

Builder （AccountBuilder）：这是用于创建Account对象的详细信息的抽象类或接口。

ConcreteBuilder ：这是通过实现Builder接口来构造和组装帐户详细信息的实现。

Director ：这使用Builder接口构造一个对象。

product（帐户）：表示正在构造的复杂对象。AccountBuilder构建帐户的内部表示形式，并定义其组装过程。

#### 实现Spring框架

Spring框架在某些功能中透明地实现了Builder设计模式。以下类基于Spring Framework中的Builder设计模式：

EmbeddedDatabaseBuilder 

AuthenticationManagerBuilder 

UriComponentsBuilder

 BeanDefinitionBuilder

 MockMvcWebClientBuilder

##  Structural and Behavioral Patterns(结构模式和行为模式)

让我们继续我们的核心设计模式之旅：

结构设计模式：此类别下的模式用于处理类或对象的组成。在企业应用程序中，有两种常见的技术可以在面向对象的系统中重用功能，如下所示：

继承 Inheritance：用于继承其他类的常用状态和行为。

组成 composition：用于将其他对象组成类的实例变量。它定义了组成对象以获得新功能的方法。

行为设计模式：此类别下的模式表征类或对象与责任交互和分配的方式。这些模式定义企业应用程序中对象之间的通信方法。因此，在这里，您将学习如何使用行为模式来减少复杂的流量控制。此外，您将使用行为模式来封装算法并在运行时动态选择它们。

#### 结构设计模式

在上一节中，我们讨论了创意设计模式以及它们如何根据业务需求为对象创建提供最佳解决方案。创新设计模式仅提供一种在应用程序中创建对象的解决方案，这些对象如何针对特定的业务目标在应用程序中相互合并，结构设计模式就应运而生。

在本章中，我们将探讨结构模式，以及这些模式如何用于通过使用继承或组合来定义应用程序的较大结构来定义对象之间的关系。结构化模式使您能够解决与构建对象之间的关系有关的许多问题。它们向您展示了如何以灵活和可扩展的方式将系统的不同部分粘合在一起。

结构模式可帮助您确保当其中一部分发生更改时，整个结构都不需要更改；在汽车中，您可以用其他供应商更换轮胎，而不会影响汽车的其他部件。它们还向您展示了如何将系统中不适合（但需要使用）的部分重铸为适合的部分。

#### The adapter design pattern（适配器模式）

将类的接口转换为客户期望的另一个接口。适配器使类可以协同工作，否则由于接口不兼容而无法实现。-----GoF设计模式：可重用的面向对象软件的元素

适配器设计模式属于`结构设计模式`，根据该设计模式，两个不兼容的类可以一起工作，由于接口不兼容，这些类不能一起工作。此模式充当两个不兼容接口之间的桥梁。当应用程序的两个推断的功能不兼容时，可以使用此模式，但是这些功能需要作为业务需求进行集成。

在许多实际的示例中，我们可以使用适配器模式。假设您有不同类型的电插头，例如圆柱形和矩形插头，如下图所示。如果满足电压要求，则可以在两者之间使用适配器将矩形插头插入圆柱形插座。

![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_210.jpg)

让我们看看在应用程序中使用适配器设计模式的以下好处。

适配器模式允许您与两个或更多不兼容的对象进行通信和交互

这种模式可提高应用程序中较旧的现有功能的可重用性



以下是此设计模式解决设计问题的常见要求：

如果要在应用程序中使用此模式，则需要使用具有不兼容接口的现有类。

在您的应用程序中，此模式的另一种用法是当您要创建可重用的类并与具有不兼容接口的类协作时。现有多个子类可以使用，但是通过对每个子类进行子类化来调整其接口是不切实际的。对象适配器可以调整其父类的接口。



让我们看看Spring如何在内部实现适配器设计模式。

 Spring Framework使用适配器设计模式来透明地在整个框架中实现许多功能。以下是一些基于Spring Framework中的适配器设计模式列出的类：

JpaVendorAdapter

HibernateJpaVendorAdapter

 HandlerInterceptorAdapter 

MessageListenerAdapter 

SpringContextResourceAdapter 

ClassPreProcessorAgentAdapter 

RequestMappingHandlerAdapter 

AnnotationMethodHandlerAdapter

WebMvcConfigurerAdapter



![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_225.jpg)

目标接口 target：这是客户端将使用的所需接口类

Adapter类：这个类是一个包装类，它实现所需的目标接口，并修改Adaptee类提供的特定请求

Adaptee类：适配器类使用这个类来重用现有功能，并根据需要修改它们

客户端：此类将与Adapter类进行交互

### The Bridge design pattern(桥接模式)

将抽象与其实现分离，以便二者可以独立变化-GoF设计模式：可重用的面向对象软件的元素

在软件工程中，最受欢迎的概念之一是优先于继承而不是继承。桥梁设计模式促进了这种流行的观念。与适配器模式相似，该模式也属于GoF设计模式的结构设计模式家族。

桥接模式的方法是将客户端代码使用的抽象与其实现分离开来。这意味着它将抽象及其实现分离为单独的类层次结构。而且，Bridge模式更喜欢使用组合而不是继承，因为继承并不总是很灵活，并且破坏了封装，因此在实现程序中进行的任何更改都会影响客户端代码使用的抽象。



桥提供了一种在软件开发中的两个不同独立组件之间进行通信的方式，而桥结构则为您提供了一种将抽象类与实现者类分离的方式。因此，对实现类或实现者（即接口）所做的任何更改都不会影响抽象类或其精炼的抽象类。通过使用接口和抽象之间的组合，可以实现这一点。桥接模式使用接口作为抽象类的具体类与该接口的实现类之间的桥梁。您可以在两种类型的类中进行更改，而不会影响客户端代码。

以下是网桥设计模式的好处：



Bridge设计模式允许您将实现和抽象分开

这种设计模式提供了灵活性，可以在客户端代码中更改两种类型的类而没有副作用

这种设计模式允许通过在客户端之间使用抽象来隐藏实际的实现细节。



以下是网桥设计模式解决的常见问题：

删除功能抽象及其实现之间的永久绑定

您可以更改实现类，而不会影响抽象和客户端代码

您可以使用子类扩展抽象及其实现

以下Spring模块基于Bridge设计模式：

ViewRendererServlet：它是一个桥servlet，主要用于Portlet MVC支持**桥设计模式**：桥设计模式用于Spring日志记录过程中

#####  不使用网桥设计模式的系统

让我们看一个不使用Bridge设计模式的示例。在下图中，您可以看到银行和帐户界面之间的关系：

![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_244.jpg)

让我们在不使用Bridge设计模式的情况下创建设计。首先创建一个接口或抽象类**Bank**。然后创建其派生类：**IciciBank**和**HdfcBank**。要在银行中开设帐户，首先要确定帐户类别的类型-**储蓄帐户**和**活期帐户**，这些类别扩展了特定的银行类别（**HdfcBank**和**IciciBank**）。此应用程序中有一个简单的深度继承层次结构。那么，与上图相比，这种设计有什么问题？您会注意到，在此设计中，有两个部分，一个是抽象部分，另一个是实现部分。客户端代码与抽象部分进行交互。当您更新抽象部分时，客户端代码只能访问实现部分的新更改或新功能，这意味着这些部分，抽象和实现是紧密结合的。



现在，让我们看看如何使用Bridge设计模式来改进此示例：

##### 具有网桥设计模式的系统

在下图中，我们使用Bridge设计模式在Bank and Account接口之间创建关系：

![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_245.jpg)

![img](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\1528771072-8457-5780d2384acdbb60ec07fc3c71a1.png)

让我们看一下下图，了解Bridge设计模式如何解决这些设计问题，如我们未使用Bridge设计模式的示例所示。桥接模式将抽象和实现分为两个类层次结构：

UML用于桥梁设计模式

![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_246.jpg)

我们有一个Account接口，它充当桥接器的实现者，而具体的类SavingAccount和CurrentAccount实现了Account接口。该银行是一个抽象类，它会使用帐户的对象。

### Composite design pattern(复合模式)

将对象组合成树结构以表示部分整个层次结构。复合可以使客户统一对待单个对象和对象组成。--GoF设计模式

在软件工程中，复合模式属于结构设计模式。根据此模式，客户端会将一组相同类型的对象视为单个对象。Composite设计模式背后的想法是将一组对象组合成一个树形结构，以表示更大的结构应用程序的模块。而且，这种针对客户的结构统一地是单个单元或实例。



Composite设计模式背后的动机是将系统中的对象分为树形结构，而树形结构是节点叶和分支的组合。在树结构中，节点具有许多叶子和其他节点。叶子没有任何东西，这意味着树上没有叶子的孩子。叶子被视为树状结构数据的终点。

让我们看一下下图，它以节点和叶的形式表示树形结构中的数据：

![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_255.jpg)





作为开发人员，设计应用程序以使客户端可以跨应用程序统一访问您的对象更加困难，即使该对象是对象的组合或单个对象也是如此。这种设计模式解决了难题，并允许您以某种方式设计对象，以便可以将该对象用作对象和单个单个对象的组合。



这种模式解决了创建分层树结构以为客户提供访问和操纵树中对象的统一方式时所面临的挑战。复合模式是一个不错的选择。在这种情况下，将基本体和复合体视为同质的复杂性较低。

组合设计模式是基于将相似类型的对象组成到树结构中的，您知道每棵树都有三个主要部分：分支，节点和叶。因此，让我们看一下此设计模式中使用的以下术语。

组件 Component：它基本上是树的一个分支，并且该分支还有其他分支，节点和叶子。组件为所有组件（包括复合对象）提供抽象。在该组合物的图案，组分被基本上声明为对象的接口。

Leaf ：这是一个实现所有组件方法的对象。

Composite 组合：它表示为树结构中的一个节点，它具有其他节点和叶子，并且表示一个复合组件。它具有添加子项的方法，也就是说，它表示相同类型对象的集合。它还有其他子组件方法。

让我们看一下下面这个设计模式的UML图：

![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_256.jpg)

复合设计模式的好处：

这种模式提供了添加新组件以进行处理的灵活性

动态地改变现有组件	

此模式使您可以创建包含单个对象和复合对象的类层次结构

92.html