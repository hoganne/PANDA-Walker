## Core Technologies 核心技术

This part of the reference documentation covers all the technologies that are absolutely integral to the Spring Framework.

参考文档的这一部分涵盖了Spring框架绝对不可或缺的所有技术。

Foremost amongst these is the Spring Framework’s Inversion of Control (IoC) container. A thorough treatment of the Spring Framework’s IoC container is closely followed by comprehensive coverage of Spring’s Aspect-Oriented Programming (AOP) technologies. The Spring Framework has its own AOP framework, which is conceptually easy to understand and which successfully addresses the 80% sweet spot of AOP requirements in Java enterprise programming.

其中最重要的是Spring框架的控制反转（inversion of control，IoC）容器。在对Spring框架的IoC容器进行彻底处理之后，我们将全面介绍Spring面向方面编程（AOP）技术。Spring框架有自己的AOP框架，在概念上很容易理解，并且成功地解决了Java企业编程中AOP需求的80%的最佳点。

Coverage of Spring’s integration with AspectJ (currently the richest — in terms of features — and certainly most mature AOP implementation in the Java enterprise space) is also provided.

本文还介绍了Spring与AspectJ的集成（就特性而言，目前是最丰富的，当然也是Java企业领域最成熟的AOP实现）。

### 1.The IoC Container 控制反转容器

#### 1.1. Introduction to the Spring IoC Container and Beans

springioc容器和bean简介

This chapter covers Spring’s Inversion of Control (IoC) container.

本章介绍Spring的控制反转（inversionofcontrol，IoC）容器。

This chapter covers the Spring Framework implementation of the Inversion of Control (IoC) principle. IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

本章介绍控制反转（IoC）原则的Spring框架实现。IoC也称为依赖注入（dependency injection，DI）。这是一个过程，在这个过程中，对象只通过构造函数参数、工厂方法的参数或在对象实例构造或从工厂方法返回后在对象实例上设置的属性来定义它们的依赖项（即它们使用的其他对象）。然后容器在创建bean时注入这些依赖项。这个过程基本上与bean本身相反（因此称为控制反转），它使用类的直接构造或服务定位器模式等机制来控制其依赖项的实例化或位置。

The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container. The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object. ApplicationContext is a sub-interface of BeanFactory. It adds:

这个org.springframework.beans以及org.springframework.context包是Spring框架的IoC容器的基础。BeanFactory接口提供了一种高级配置机制，能够管理任何类型的对象。ApplicationContext是BeanFactory的一个子接口。它补充了：

1，Easier integration with Spring’s AOP features

更容易与Spring的AOP特性集成

2，Message resource handling (for use in internationalization)

消息资源处理（用于国际化）

3，Event publication

事件发布

4，Application-layer specific contexts such as the WebApplicationContext for use in web applications.

特定于应用程序层的上下文，如用于web应用程序的WebApplicationContext。



In short, the BeanFactory provides the configuration framework and basic functionality, and the ApplicationContext adds more enterprise-specific functionality. The ApplicationContext is a complete superset of the BeanFactory and is used exclusively in this chapter in descriptions of Spring’s IoC container. For more information on using the BeanFactory instead of the ApplicationContext, see The BeanFactory.

简而言之，BeanFactory提供了配置框架和基本功能，而ApplicationContext则添加了更多特定于企业的功能。ApplicationContext是BeanFactory的一个完整的超集，在本章中专门用于描述Spring的IoC容器。有关使用BeanFactory而不是ApplicationContext的更多信息，请参阅[The `BeanFactory`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanfactory).。



In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. Beans, and the dependencies among them, are reflected in the configuration metadata used by a container.

在Spring中，构成应用程序主干并由springioc容器管理的对象称为bean。bean是由springioc容器实例化、组装和管理的对象。否则，bean只是应用程序中众多对象中的一个。bean以及它们之间的依赖关系反映在容器使用的配置元数据中。

#### 1.2. Container Overview

The `org.springframework.context.ApplicationContext` interface represents the Spring IoC container and is responsible for instantiating, configuring, and assembling the beans. The container gets its instructions on what objects to instantiate, configure, and assemble by reading configuration metadata. The configuration metadata is represented in XML, Java annotations, or Java code. It lets you express the objects that compose your application and the rich interdependencies between those objects.

在`org.springframework.context.ApplicationContext`接口表示SpringIoC容器，负责实例化、配置和组装bean。容器通过读取配置元数据获取有关实例化、配置和组装哪些对象的指令。配置元数据用XML、Java注释或Java代码表示。它允许您表达组成应用程序的对象以及这些对象之间丰富的相互依赖关系。

Several implementations of the `ApplicationContext` interface are supplied with Spring. In stand-alone applications, it is common to create an instance of [`ClassPathXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/ClassPathXmlApplicationContext.html) or [`FileSystemXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/FileSystemXmlApplicationContext.html). While XML has been the traditional format for defining configuration metadata, you can instruct the container to use Java annotations or code as the metadata format by providing a small amount of XML configuration to declaratively enable support for these additional metadata formats.

Spring提供了“ApplicationContext”接口的几个实现。在独立应用程序中，创建 [`ClassPathXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/ClassPathXmlApplicationContext.html) 的实例是常见的或者[`FileSystemXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/FileSystemXmlApplicationContext.html). 虽然XML是定义配置元数据的传统格式，但是您可以通过提供少量的XML配置来指示容器使用Java注释或代码作为元数据格式，以声明方式启用对这些附加元数据格式的支持。

In most application scenarios, explicit user code is not required to instantiate one or more instances of a Spring IoC container. For example, in a web application scenario, a simple eight (or so) lines of boilerplate web descriptor XML in the `web.xml` file of the application typically suffices (see [Convenient ApplicationContext Instantiation for Web Applications](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-create)). If you use the [Spring Tools for Eclipse](https://spring.io/tools) (an Eclipse-powered development environment), you can easily create this boilerplate configuration with a few mouse clicks or keystrokes.

在大多数应用程序场景中，不需要显式用户代码来实例化springioc容器的一个或多个实例。例如，在web应用程序场景中，在web.xml文件应用程序的文件通常就足够了（请参阅Web应用程序的便捷应用程序上下文实例化）。如果您使用Eclipse的Spring工具（一个Eclipse驱动的开发环境），只需单击几次鼠标或击键，就可以轻松地创建这个样板配置。

The following diagram shows a high-level view of how Spring works. Your application classes are combined with configuration metadata so that, after the `ApplicationContext` is created and initialized, you have a fully configured and executable system or application.

下面的图表展示了Spring如何工作的高级视图。应用程序类与配置元数据相结合，这样，在创建和初始化ApplicationContext之后，您就拥有了一个完全配置并可执行的系统或应用程序。

![container magic](https://docs.spring.io/spring-framework/docs/current/reference/html/images/container-magic.png)

Figure 1. The Spring IoC container

##### 1.2.1. Configuration Metadata 配置元数据

As the preceding diagram shows, the Spring IoC container consumes a form of configuration metadata. This configuration metadata represents how you, as an application developer, tell the Spring container to instantiate, configure, and assemble the objects in your application.

如上图所示，springioc容器使用一种形式的配置元数据。这个配置元数据表示作为应用程序开发人员，如何告诉Spring容器实例化、配置和组装应用程序中的对象。

Configuration metadata is traditionally supplied in a simple and intuitive XML format, which is what most of this chapter uses to convey key concepts and features of the Spring IoC container.

传统上，配置元数据是以简单直观的XML格式提供的，这也是本章大部分内容用来传达springioc容器的关键概念和特性的。

|      | XML-based metadata is not the only allowed form of configuration metadata. The Spring IoC container itself is totally decoupled from the format in which this configuration metadata is actually written. These days, many developers choose [Java-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java) for their Spring applications. |
| ---- | ------------------------------------------------------------ |
|      | 基于XML的元数据不是配置元数据的唯一允许形式。springioc容器本身与实际编写配置元数据的格式完全分离。现在，许多开发人员为他们的Spring应用程序选择基于Java的配置。 |

For information about using other forms of metadata with the Spring container, see:

有关在Spring容器中使用其他形式的元数据的信息，请参见：

- [Annotation-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config): Spring 2.5 introduced support for annotation-based configuration metadata.
- 基于注释的配置：Spring2.5引入了对基于注释的配置元数据的支持。
- [Java-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java): Starting with Spring 3.0, many features provided by the Spring JavaConfig project became part of the core Spring Framework. Thus, you can define beans external to your application classes by using Java rather than XML files. To use these new features, see the [`@Configuration`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html), [`@Bean`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html), [`@Import`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Import.html), and [`@DependsOn`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/DependsOn.html) annotations.
- 基于Java的配置：从spring3.0开始，spring javaconfig项目提供的许多特性成为Spring核心框架的一部分。因此，您可以使用Java而不是XML文件来定义应用程序类外部的bean。要使用这些新特性，请参阅@Configuration、@Bean、@Import和@DependsOn注释

Spring configuration consists of at least one and typically more than one bean definition that the container must manage. XML-based configuration metadata configures these beans as `<bean/>` elements inside a top-level `<beans/>` element. Java configuration typically uses `@Bean`-annotated methods within a `@Configuration` class.

Spring配置至少包含一个容器必须管理的bean定义，通常不止一个。基于XML的配置元数据将这些bean配置为顶层<beans/>元素中的<bean/>元素。Java配置通常在@configuration类中使用@Bean注释的方法

These bean definitions correspond to the actual objects that make up your application. Typically, you define service layer objects, data access objects (DAOs), presentation objects such as Struts `Action` instances, infrastructure objects such as Hibernate `SessionFactories`, JMS `Queues`, and so forth. Typically, one does not configure fine-grained domain objects in the container, because it is usually the responsibility of DAOs and business logic to create and load domain objects. However, you can use Spring’s integration with AspectJ to configure objects that have been created outside the control of an IoC container. See [Using AspectJ to dependency-inject domain objects with Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-atconfigurable).

这些bean定义对应于组成应用程序的实际对象。通常，您需要定义服务层对象、数据访问对象（dao）、表示层对象（如Struts操作实例）、基础结构对象（如Hibernate SessionFactories、JMS队列等）。通常，不在容器中配置细粒度的域对象（也叫entity对象），因为创建和加载域对象通常由dao和业务逻辑负责。但是，您可以使用Spring与AspectJ的集成来配置在IoC容器控制之外创建的对象。请参阅使用AspectJ对Spring进行依赖注入域对象。

The following example shows the basic structure of XML-based configuration metadata:

以下示例显示基于XML的配置元数据的基本结构： 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="..." class="...">  
        <!-- collaborators and configuration for this bean go here -->
    </bean>
    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
		<!--这里是这个bean的协作者和配置 -->        
    </bean>
    <!-- more bean definitions go here -->
</beans>
```

|      | The `id` attribute is a string that identifies the individual bean definition. |
| ---- | ------------------------------------------------------------ |
|      | id属性是标识单个bean定义的字符串。                           |
|      | The `class` attribute defines the type of the bean and uses the fully qualified classname. |
|      | class属性定义bean的类型并使用完全限定的类名                  |

The value of the `id` attribute refers to collaborating objects. The XML for referring to collaborating objects is not shown in this example. See [Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-dependencies) for more information.

id属性的值引用协作对象。本例中没有显示用于引用协作对象的XML。有关详细信息，请参见依赖项。

##### 1.2.2. Instantiating a Container

The location path or paths supplied to an `ApplicationContext` constructor are resource strings that let the container load configuration metadata from a variety of external resources, such as the local file system, the Java `CLASSPATH`, and so on.

提供给“ApplicationContext”构造函数的一个或多个位置路径是资源字符串，允许容器从各种外部资源（如本地文件系统、Java“CLASSPATH”）加载配置元数据。

Java

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

|      | After you learn about Spring’s IoC container, you may want to know more about Spring’s `Resource` abstraction (as described in [Resources](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources)), which provides a convenient mechanism for reading an InputStream from locations defined in a URI syntax. In particular, `Resource` paths are used to construct applications contexts, as described in [Application Contexts and Resource Paths](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources-app-ctx). |
| ---- | ------------------------------------------------------------ |
|      | 在了解了Spring的IoC容器之后，您可能想进一步了解Spring的资源抽象（如参考资料中所述），它为从URI语法中定义的位置读取InputStream提供了一种方便的机制。特别是，资源路径用于构造应用程序上下文，如应用程序上下文和资源路径中所述 |

The following example shows the service layer objects `(services.xml)` configuration file:

下面的示例显示了服务层对象`(services.xml)`配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- services -->

    <bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for services go here -->

</beans>
```

The following example shows the data access objects `daos.xml` file:

以下示例显示数据访问对象`daos.xml文件`文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountDao"
        class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for data access objects go here -->

</beans>
```

In the preceding example, the service layer consists of the `PetStoreServiceImpl` class and two data access objects of the types `JpaAccountDao` and `JpaItemDao` (based on the JPA Object-Relational Mapping standard). The `property name` element refers to the name of the JavaBean property, and the `ref` element refers to the name of another bean definition. This linkage between `id` and `ref` elements expresses the dependency between collaborating objects. For details of configuring an object’s dependencies, see [Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-dependencies).

在前面的示例中，服务层由PetStoreServiceImpl类和两个JpaAccountDao和JPAIItemDAO类型的数据访问对象（基于JPA对象关系映射标准）组成。property name元素引用JavaBean属性的名称，ref元素引用另一个bean定义的名称。id和ref元素之间的这种链接表示协作对象之间的依赖关系。有关配置对象依赖项的详细信息，请参见依赖项。

Composing XML-based Configuration Metadata

基于XML的配置元数据的合成

It can be useful to have bean definitions span multiple XML files. Often, each individual XML configuration file represents a logical layer or module in your architecture.

bean定义跨越多个XML文件可能很有用。通常，每个单独的XML配置文件都表示体系结构中的逻辑层或模块。

You can use the application context constructor to load bean definitions from all these XML fragments. This constructor takes multiple `Resource` locations, as was shown in the [previous section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-instantiation). Alternatively, use one or more occurrences of the `<import/>` element to load bean definitions from another file or files. The following example shows how to do so:

您可以使用应用程序上下文构造函数从所有这些XML片段加载bean定义。这个构造函数接受多个资源位置，如前一节所示。或者，使用<import/>元素的一个或多个引用从另一个文件或文件加载bean定义。以下示例显示如何执行此操作：

```xml
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>

    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```

In the preceding example, external bean definitions are loaded from three files: `services.xml`, `messageSource.xml`, and `themeSource.xml`. All location paths are relative to the definition file doing the importing, so `services.xml` must be in the same directory or classpath location as the file doing the importing, while `messageSource.xml` and `themeSource.xml` must be in a `resources` location below the location of the importing file. As you can see, a leading slash is ignored. However, given that these paths are relative, it is better form not to use the slash at all. The contents of the files being imported, including the top level `<beans/>` element, must be valid XML bean definitions, according to the Spring Schema.

在前面的示例中，外部bean定义从三个文件加载：service.xml,messageSource.xml，和themeSource.xml. 所有位置路径都与执行导入的定义文件相关，因此services.xml必须与执行导入的文件位于相同的目录或类路径位置，而messageSource.xml以及themeSource.xml必须位于导入文件位置下方的资源位置。如您所见，正斜杠被忽略。但是，考虑到这些路径是相对的，最好不要使用斜杠。根据Spring模式，导入的文件的内容（包括顶层的<beans/>元素）必须是有效的xmlbean定义。

|      | It is possible, but not recommended, to reference files in parent directories using a relative "../" path. Doing so creates a dependency on a file that is outside the current application. In particular, this reference is not recommended for `classpath:` URLs (for example, `classpath:../services.xml`), where the runtime resolution process chooses the “nearest” classpath root and then looks into its parent directory. Classpath configuration changes may lead to the choice of a different, incorrect directory.                                                                  You can always use fully qualified resource locations instead of relative paths: for example, `file:C:/config/services.xml` or `classpath:/config/services.xml`. However, be aware that you are coupling your application’s configuration to specific absolute locations. It is generally preferable to keep an indirection for such absolute locations — for example, through "${…}" placeholders that are resolved against JVM system properties at runtime. |
| ---- | ------------------------------------------------------------ |
|      | 可以（但不建议）使用相对“../”路径引用父目录中的文件。这样做会对当前应用程序之外的文件产生依赖关系。特别是，不建议将此引用用于classpath:url（例如，classpath：../services.xml)，运行时解析进程选择“最近的”classpath root(根)，然后查找其父目录。类路径配置更改可能导致选择不同的、不正确的目录。 |
|      | 您可以始终使用完全限定的资源位置而不是相对路径：例如，`file:C:/config/services.xml`或`classpath:/config/services.xml`.. 但是，请注意，您将应用程序的配置耦合到特定的绝对位置。通常情况下，最好保持这种绝对位置的间接寻址，例如，通过在运行时根据JVM系统属性解析的“${…}”占位符。 |

The namespace itself provides the import directive feature. Further configuration features beyond plain bean definitions are available in a selection of XML namespaces provided by Spring — for example, the `context` and `util` namespaces.

命名空间本身提供了import指令功能。除了普通bean定义之外，还可以在Spring - 提供的一系列XML名称空间中使用其他配置特性，例如context和util名称空间。

The Groovy Bean Definition DSL

As a further example for externalized configuration metadata, bean definitions can also be expressed in Spring’s Groovy Bean Definition DSL, as known from the Grails framework. Typically, such configuration live in a ".groovy" file with the structure shown in the following example:

作为外部化配置元数据的进一步示例，bean定义也可以用Spring的groovy bean definition dsl来表示，正如Grails框架中所说的那样。通常，此类配置位于“.groovy”文件中，其结构如以下示例所示：

```groovy
beans {
    dataSource(BasicDataSource) {
        driverClassName = "org.hsqldb.jdbcDriver"
        url = "jdbc:hsqldb:mem:grailsDB"
        username = "sa"
        password = ""
        settings = [mynew:"setting"]
    }
    sessionFactory(SessionFactory) {
        dataSource = dataSource
    }
    myService(MyService) {
        nestedBean = { AnotherBean bean ->
            dataSource = dataSource
        }
    }
}
```

This configuration style is largely equivalent to XML bean definitions and even supports Spring’s XML configuration namespaces. It also allows for importing XML bean definition files through an `importBeans` directive.

这种配置风格在很大程度上等同于xmlbean定义，甚至支持Spring的XML配置名称空间。它还允许通过importBeans指令导入xmlbean定义文件。

##### 1.2.3. Using the Container

The `ApplicationContext` is the interface for an advanced factory capable of maintaining a registry of different beans and their dependencies. By using the method `T getBean(String name, Class<T> requiredType)`, you can retrieve instances of your beans.

ApplicationContext是一个高级工厂的接口，它能够维护不同bean及其依赖项的注册表。通过使用方法getBean（String name，Class<T>requiredType），您可以检索bean的实例。

The `ApplicationContext` lets you read bean definitions and access them, as the following example shows:

ApplicationContext允许您读取bean定义并访问它们，如下例所示：

Java

```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);
// use configured instance
List<String> userList = service.getUsernameList();
```

With Groovy configuration, bootstrapping looks very similar. It has a different context implementation class which is Groovy-aware (but also understands XML bean definitions). The following example shows Groovy configuration:

使用Groovy配置，引导看起来非常相似。它有一个不同的上下文实现类，它支持Groovy（但也理解xmlbean定义）。以下示例显示Groovy配置：

Java

```java
ApplicationContext context = new GenericGroovyApplicationContext("services.groovy", "daos.groovy");
```

The most flexible variant is `GenericApplicationContext` in combination with reader delegates — for example, with `XmlBeanDefinitionReader` for XML files, as the following example shows:

最灵活的变体是GenericApplicationContext与reader delegates - - 的组合，例如，使用XmlBeanDefinitionReader for XML文件，如下例所示：

Java

```java
GenericApplicationContext context = new GenericApplicationContext();
new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
context.refresh();
```

You can also use the `GroovyBeanDefinitionReader` for Groovy files, as the following example shows:

您还可以将GroovyBeanDefinitionReader用于Groovy文件，如下例所示：

```java
GenericApplicationContext context = new GenericApplicationContext();
new GroovyBeanDefinitionReader(context).loadBeanDefinitions("services.groovy", "daos.groovy");
context.refresh();
```

You can mix and match such reader delegates on the same `ApplicationContext`, reading bean definitions from diverse configuration sources.

您可以在同一个ApplicationContext上混合和匹配这样的读卡器委托，从不同的配置源读取bean定义。

You can then use `getBean` to retrieve instances of your beans. The `ApplicationContext` interface has a few other methods for retrieving beans, but, ideally, your application code should never use them. Indeed, your application code should have no calls to the `getBean()` method at all and thus have no dependency on Spring APIs at all. For example, Spring’s integration with web frameworks provides dependency injection for various web framework components such as controllers and JSF-managed beans, letting you declare a dependency on a specific bean through metadata (such as an autowiring annotation).

然后可以使用getBean检索bean的实例。ApplicationContext接口有一些用于检索bean的其他方法，但是理想情况下，您的应用程序代码不应该使用它们。实际上，您的应用程序代码根本不应该调用getBean（）方法，因此完全不依赖于SpringAPI。例如，Spring与web框架的集成为各种web框架组件（如controllers和JSF-managed beans）提供了依赖注入，允许您通过元数据（比如自动连接注释）声明对特定bean的依赖性。

#### 1.3. Bean Overview Bean概述(`BeanDefinition` )

A Spring IoC container manages one or more beans. These beans are created with the configuration metadata that you supply to the container (for example, in the form of XML `<bean/>` definitions).

springioc容器管理一个或多个bean。这些bean是使用您提供给容器的配置元数据创建的（例如，以XML<bean/>定义的形式）。

Within the container itself, these bean definitions are represented as `BeanDefinition` objects, which contain (among other information) the following metadata:

在容器本身中，这些bean定义表示为BeanDefinition对象，其中包含（除其他信息外）以下元数据：

- A package-qualified class name: typically, the actual implementation class of the bean being defined.
- 包限定类名：通常是定义的bean的实际实现类。
- Bean behavioral configuration elements, which state how the bean should behave in the container (scope, lifecycle callbacks, and so forth).
- Bean行为配置元素，说明Bean在容器中的行为（范围(作用域)、生命周期回调等等）。
- References to other beans that are needed for the bean to do its work. These references are also called collaborators or dependencies.
- 对bean执行其工作所需的其他bean的引用。这些引用也称为协作者或依赖项。
- Other configuration settings to set in the newly created object — for example, the size limit of the pool or the number of connections to use in a bean that manages a connection pool.
- 要在新创建的对象 - 中设置的其他配置设置，例如，池的大小限制或要在管理连接池的bean中使用的连接数。

This metadata translates to a set of properties that make up each bean definition. The following table describes these properties:

此元数据转换为组成每个bean定义的一组属性。下表描述了这些属性：

| Property                 | Explained in…                                                |
| :----------------------- | :----------------------------------------------------------- |
| Class                    | [Instantiating Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class) |
| Name                     | [Naming Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanname) |
| Scope                    | [Bean Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes) |
| Constructor arguments    | [Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators) |
| Properties               | [Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators) |
| Autowiring mode          | [Autowiring Collaborators](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire) |
| Lazy initialization mode | [Lazy-initialized Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lazy-init) |
| Initialization method    | [Initialization Callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean) |
| Destruction method       | [Destruction Callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-disposablebean) |

In addition to bean definitions that contain information on how to create a specific bean, the `ApplicationContext` implementations also permit the registration of existing objects that are created outside the container (by users). This is done by accessing the ApplicationContext’s BeanFactory through the `getBeanFactory()` method, which returns the BeanFactory `DefaultListableBeanFactory` implementation. `DefaultListableBeanFactory` supports this registration through the `registerSingleton(..)` and `registerBeanDefinition(..)` methods. However, typical applications work solely with beans defined through regular bean definition metadata.

除了包含有关如何创建特定bean的信息的bean定义之外，ApplicationContext实现还允许注册在容器外部（由用户创建）的现有对象。这是通过getBeanFactory（）方法访问ApplicationContext的BeanFactory来完成的，该方法返回BeanFactory DefaultListableBeanFactory实现。DefaultListableBeanFactory通过registerSingleton（..）和registerBeanDefinition（..）方法支持此注册。然而，典型的应用程序只处理通过常规bean定义元数据定义的bean。

|      | Bean metadata and manually supplied singleton instances need to be registered as early as possible, in order for the container to properly reason about them during autowiring and other introspection steps. While overriding existing metadata and existing singleton instances is supported to some degree, the registration of new beans at runtime (concurrently with live access to the factory) is not officially supported and may lead to concurrent access exceptions, inconsistent state in the bean container, or both. |
| ---- | ------------------------------------------------------------ |
|      | Bean元数据和手动提供的单例实例需要尽早注册，以便容器在自动连接和其他内省步骤中正确地对它们进行推理。虽然在一定程度上支持重写现有元数据和现有的单例实例，但在运行时注册新的bean（同时对工厂进行实时访问）不受官方支持，并且可能导致并发访问异常、bean容器中的状态不一致，或两者兼而有之。 |

##### 1.3.1. Naming Beans

bean的命名

Every bean has one or more identifiers. These identifiers must be unique within the container that hosts the bean. A bean usually has only one identifier. However, if it requires more than one, the extra ones can be considered aliases.

每个bean都有一个或多个标识符。这些标识符在承载bean的容器中必须是唯一的。一个bean通常只有一个标识符。但是，如果它需要一个以上的，额外的可以被视为别名。

In XML-based configuration metadata, you use the `id` attribute, the `name` attribute, or both to specify the bean identifiers. The `id` attribute lets you specify exactly one id. Conventionally, these names are alphanumeric ('myBean', 'someService', etc.), but they can contain special characters as well. If you want to introduce other aliases for the bean, you can also specify them in the `name` attribute, separated by a comma (`,`), semicolon (`;`), or white space. As a historical note, in versions prior to Spring 3.1, the `id` attribute was defined as an `xsd:ID` type, which constrained possible characters. As of 3.1, it is defined as an `xsd:string` type. Note that bean `id` uniqueness is still enforced by the container, though no longer by XML parsers.

在基于XML的配置元数据中，可以使用id属性和/或name属性来指定bean标识符。id属性允许您只指定一个id。通常，这些名称是字母数字的（“myBean”、“someService”等），但是它们也可以包含特殊字符。如果要为bean引入其他别名，也可以在name属性中指定它们，用逗号（，）、分号（；）或空格隔开。作为历史记录，在spring3.1之前的版本中，id属性被定义为xsd:ID类型，约束了可能的字符。从3.1开始，它被定义为xsd:字符串类型。注意，bean id的唯一性仍然由容器强制执行，尽管不再由XML解析器执行。

You are not required to supply a `name` or an `id` for a bean. If you do not supply a `name` or `id` explicitly, the container generates a unique name for that bean. However, if you want to refer to that bean by name, through the use of the `ref` element or a Service Locator style lookup, you must provide a name. Motivations for not supplying a name are related to using [inner beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-inner-beans) and [autowiring collaborators](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire).

不需要为bean提供名称或id。如果不显式地提供名称或id，容器将为该bean生成一个唯一的名称。但是，如果希望通过使用ref元素或服务定位器样式的查找按名称引用该bean，则必须提供名称。不提供名称的动机与使用内部bean和自动连接协作器有关。

Bean Naming Conventions

Bean命名约定

The convention is to use the standard Java convention for instance field names when naming beans. That is, bean names start with a lowercase letter and are camel-cased from there. Examples of such names include `accountManager`, `accountService`, `userDao`, `loginController`, and so forth.

约定是在命名bean时使用标准Java约定作为实例字段名。也就是说，bean名称以一个小写字母开头，然后用驼峰大小写。这些名称的示例包括accountManager、accountService、userDao、loginController等。

Naming beans consistently makes your configuration easier to read and understand. Also, if you use Spring AOP, it helps a lot when applying advice to a set of beans related by name.

一致地命名bean使您的配置更易于阅读和理解。另外，如果使用spring-aop，那么在对一组按名称相关的bean应用建议时，它会有很大帮助。

|      | With component scanning in the classpath, Spring generates bean names for unnamed components, following the rules described earlier: essentially, taking the simple class name and turning its initial character to lower-case. However, in the (unusual) special case when there is more than one character and both the first and second characters are upper case, the original casing gets preserved. These are the same rules as defined by `java.beans.Introspector.decapitalize` (which Spring uses here). |
| ---- | ------------------------------------------------------------ |
|      | 通过在类路径中进行组件扫描，Spring为未命名的组件生成bean名称，遵循前面描述的规则：本质上，使用简单类名并将其初始字符改为小写。但是，在特殊情况下，如果有多个字符，并且第一个和第二个字符都是大写的，那么原始的大小写将得到保留。这些规则与java.beans.Introspector.decapitalize（Spring在这里使用）。 |

Aliasing a Bean outside the Bean Definition

在Bean定义之外别名Bean

In a bean definition itself, you can supply more than one name for the bean, by using a combination of up to one name specified by the `id` attribute and any number of other names in the `name` attribute. These names can be equivalent aliases to the same bean and are useful for some situations, such as letting each component in an application refer to a common dependency by using a bean name that is specific to that component itself.

在bean定义本身中，通过使用id属性指定的最多一个名称和name属性中任意数量的其他名称的组合，可以为bean提供多个名称。这些名称可以是同一个bean的等效别名，在某些情况下非常有用，例如通过使用特定于该组件本身的bean名称，让应用程序中的每个组件引用一个公共依赖项。

Specifying all aliases where the bean is actually defined is not always adequate, however. It is sometimes desirable to introduce an alias for a bean that is defined elsewhere. This is commonly the case in large systems where configuration is split amongst each subsystem, with each subsystem having its own set of object definitions. In XML-based configuration metadata, you can use the `<alias/>` element to accomplish this. The following example shows how to do so:

但是，指定实际定义bean的所有别名并不总是足够的。有时需要为在别处定义的bean引入别名。在大型系统中，这种情况通常是这样的：配置在每个子系统中被拆分，每个子系统都有自己的对象定义集。在基于XML的配置元数据中，您可以使用<alias/>元素来实现这一点。下面的示例演示如何执行此操作：

```xml
<alias name="fromName" alias="toName"/>
```

In this case, a bean (in the same container) named `fromName` may also, after the use of this alias definition, be referred to as `toName`.

在这种情况下，一个名为fromName的bean（在同一个容器中）在使用了这个别名定义之后，也可以被称为toName。

For example, the configuration metadata for subsystem A may refer to a DataSource by the name of `subsystemA-dataSource`. The configuration metadata for subsystem B may refer to a DataSource by the name of `subsystemB-dataSource`. When composing the main application that uses both these subsystems, the main application refers to the DataSource by the name of `myApp-dataSource`. To have all three names refer to the same object, you can add the following alias definitions to the configuration metadata:

例如，子系统A的配置元数据可以通过subsystemA DataSource的名称引用数据源。子系统的元数据可以通过子系统的名称引用。当组合使用这两个子系统的主应用程序时，主应用程序通过myApp DataSource的名称引用数据源。要使所有三个名称都引用同一个对象，可以将以下别名定义添加到配置元数据中：

```xml
<alias name="myApp-dataSource" alias="subsystemA-dataSource"/>
<alias name="myApp-dataSource" alias="subsystemB-dataSource"/>
```

Now each component and the main application can refer to the dataSource through a name that is unique and guaranteed not to clash with any other definition (effectively creating a namespace), yet they refer to the same bean.

现在，每个组件和主应用程序都可以通过一个唯一的名称来引用数据源，并保证不会与任何其他定义冲突（实际上创建了一个命名空间），但它们引用的是同一个bean。

Java-configuration

Java配置

If you use Javaconfiguration, the `@Bean` annotation can be used to provide aliases. See [Using the `@Bean` Annotation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-bean-annotation) for details.

如果使用Javaconfiguration，@Bean注释可以用来提供别名。有关详细信息，请参阅使用@Bean注释。

##### 1.3.2. Instantiating Beans 实例化bean

A bean definition is essentially a recipe for creating one or more objects. The container looks at the recipe for a named bean when asked and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.

bean定义本质上是创建一个或多个对象的配方（方法）。当被请求和使用由该bean定义封装的配置元数据来创建（或获取）实际对象时，容器会查看命名bean的配方。

If you use XML-based configuration metadata, you specify the type (or class) of object that is to be instantiated in the `class` attribute of the `<bean/>` element. This `class` attribute (which, internally, is a `Class` property on a `BeanDefinition` instance) is usually mandatory. (For exceptions, see [Instantiation by Using an Instance Factory Method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class-instance-factory-method) and [Bean Definition Inheritance](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-child-bean-definitions).) You can use the `Class` property in one of two ways:

如果使用基于XML的配置元数据，则指定要在<bean/>元素的class属性中实例化的对象的类型（或类）。这个类属性（在内部是BeanDefinition实例上的class属性）通常是必需的。（有关异常，请参阅使用实例工厂方法实例化和Bean定义继承。）可以使用以下两种方法之一使用Class属性：

- Typically, to specify the bean class to be constructed in the case where the container itself directly creates the bean by calling its constructor reflectively, somewhat equivalent to Java code with the `new` operator.
- 通常，在容器自身通过反射式调用其构造函数直接创建bean的情况下，指定要构造的bean类，这在某种程度上相当于使用new运算符的Java代码
- To specify the actual class containing the `static` factory method that is invoked to create the object, in the less common case where the container invokes a `static` factory method on a class to create the bean. The object type returned from the invocation of the `static` factory method may be the same class or another class entirely.
- 指定包含静态工厂方法的实际类，该方法被调用来创建对象，在不太常见的情况下，容器调用类上的静态工厂方法来创建bean。从调用静态工厂方法返回的对象类型可以是同一个类，也可以完全是另一个类。

Inner class names

内部类名

If you want to configure a bean definition for a `static` nested class, you have to use the binary name of the nested class.

如果要为静态嵌套类配置bean定义，则必须使用嵌套类的二进制名称。

For example, if you have a class called `SomeThing` in the `com.example` package, and this `SomeThing` class has a `static` nested class called `OtherThing`, the value of the `class` attribute on a bean definition would be `com.example.SomeThing$OtherThing`.

例如，如果在com.example这个SomeThing类有一个名为OtherThing的静态嵌套类，bean定义上的class属性的值为com.example.someThing$OtherThing。

Notice the use of the `$` character in the name to separate the nested class name from the outer class name.

请注意在名称中使用$字符将嵌套类名与外部类名分开。

Instantiation with a Constructor

用构造函数实例化

When you create a bean by the constructor approach, all normal classes are usable by and compatible with Spring. That is, the class being developed does not need to implement any specific interfaces or to be coded in a specific fashion. Simply specifying the bean class should suffice. However, depending on what type of IoC you use for that specific bean, you may need a default (empty) constructor.

当您使用构造函数方法创建bean时，所有普通类都可以被Spring使用并与之兼容。也就是说，正在开发的类不需要实现任何特定的接口或以特定的方式进行编码。简单地指定bean类就足够了。但是，根据您为特定bean使用的IoC类型，您可能需要一个默认（空）构造函数。

The Spring IoC container can manage virtually any class you want it to manage. It is not limited to managing true JavaBeans. Most Spring users prefer actual JavaBeans with only a default (no-argument) constructor and appropriate setters and getters modeled after the properties in the container. You can also have more exotic non-bean-style classes in your container. If, for example, you need to use a legacy connection pool that absolutely does not adhere to the JavaBean specification, Spring can manage it as well.

springioc容器实际上可以管理任何你想要它管理的类。它不仅限于管理真正的javabean。大多数Spring用户更喜欢实际的JavaBeans，它只有一个默认的（无参数）构造函数，以及根据容器中的属性建模的适当的setter和getter。您还可以在容器中有更多异国情调的非bean风格的类。例如，如果您需要使用完全不符合JavaBean规范的遗留连接池，Spring也可以管理它。

With XML-based configuration metadata you can specify your bean class as follows:

使用基于XML的配置元数据，您可以指定bean类，如下所示：

```xml
<bean id="exampleBean" class="examples.ExampleBean"/>

<bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

For details about the mechanism for supplying arguments to the constructor (if required) and setting object instance properties after the object is constructed, see [Injecting Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators).

有关在构造对象后向构造函数提供参数（如果需要）和设置对象实例属性的机制的详细信息，请参见注入依赖项。

Instantiation with a Static Factory Method

使用静态工厂方法实例化

When defining a bean that you create with a static factory method, use the `class` attribute to specify the class that contains the `static` factory method and an attribute named `factory-method` to specify the name of the factory method itself. You should be able to call this method (with optional arguments, as described later) and return a live object, which subsequently is treated as if it had been created through a constructor. One use for such a bean definition is to call `static` factories in legacy code.

定义使用静态工厂方法创建的bean时，使用class属性指定包含静态工厂方法的类，使用名为factory method的属性指定工厂方法本身的名称。您应该能够调用此方法（使用可选参数，如后面所述）并返回一个活动对象，该对象随后将被视为是通过构造函数创建的。这种bean定义的一个用途是在遗留代码中调用静态工厂。

The following bean definition specifies that the bean be created by calling a factory method. The definition does not specify the type (class) of the returned object, only the class containing the factory method. In this example, the `createInstance()` method must be a static method. The following example shows how to specify a factory method:

下面的bean定义指定通过调用工厂方法创建bean。定义没有指定返回对象的类型（类），只指定包含工厂方法的类。在本例中，createInstance（）方法必须是静态方法。以下示例说明如何指定工厂方法：

```xml
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```

The following example shows a class that would work with the preceding bean definition:

下面的示例显示了一个可以使用前面的bean定义的类：

Java

```java
public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}

    public static ClientService createInstance() {
        return clientService;
    }
}
```

For details about the mechanism for supplying (optional) arguments to the factory method and setting object instance properties after the object is returned from the factory, see [Dependencies and Configuration in Detail](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-properties-detailed).

有关在从工厂返回对象后向工厂方法提供（可选）参数和设置对象实例属性的机制的详细信息，请参阅依赖项和配置的详细信息。

Instantiation by Using an Instance Factory Method

使用实例工厂方法实例化

Similar to instantiation through a [static factory method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class-static-factory-method), instantiation with an instance factory method invokes a non-static method of an existing bean from the container to create a new bean. To use this mechanism, leave the `class` attribute empty and, in the `factory-bean` attribute, specify the name of a bean in the current (or parent or ancestor) container that contains the instance method that is to be invoked to create the object. Set the name of the factory method itself with the `factory-method` attribute. The following example shows how to configure such a bean:

与通过静态工厂方法实例化类似，使用实例工厂方法的实例化从容器调用现有bean的非静态方法来创建新的bean。要使用此机制，请将class属性留空，并在factory bean属性中指定当前（或父级或祖先）容器中的bean的名称，该容器包含要调用以创建对象的实例方法。使用factory method属性设置工厂方法本身的名称。下面的示例演示如何配置此类bean：

```xml
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```

The following example shows the corresponding class:

以下示例显示了相应的类：

Java

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();
    public ClientService createClientServiceInstance() {
        return clientService;
    }
}
```

One factory class can also hold more than one factory method, as the following example shows:

一个工厂类也可以包含多个工厂方法，如下例所示：

```xml
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>

<bean id="accountService"
    factory-bean="serviceLocator"
    factory-method="createAccountServiceInstance"/>
```

The following example shows the corresponding class:

以下示例显示了相应的类：

Java

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    private static AccountService accountService = new AccountServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }

    public AccountService createAccountServiceInstance() {
        return accountService;
    }
}
```

This approach shows that the factory bean itself can be managed and configured through dependency injection (DI). See [Dependencies and Configuration in Detail](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-properties-detailed).

这种方法表明，工厂bean本身可以通过依赖注入（dependency injection，DI）进行管理和配置。请详细查看依赖项和配置。

|      | In Spring documentation, “factory bean” refers to a bean that is configured in the Spring container and that creates objects through an [instance](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class-instance-factory-method) or [static](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class-static-factory-method) factory method. By contrast, `FactoryBean` (notice the capitalization) refers to a Spring-specific [`FactoryBean` ](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-factorybean)implementation class. |
| ---- | ------------------------------------------------------------ |
|      | 在Spring文档中，“factory bean”是指在Spring容器中配置的、通过实例或静态工厂方法创建对象的bean。比照FactoryBean（注意）实现了特定的FactoryBean。 |

Determining a Bean’s Runtime Type

确定Bean的运行时类型

The runtime type of a specific bean is non-trivial to determine. A specified class in the bean metadata definition is just an initial class reference, potentially combined with a declared factory method or being a `FactoryBean` class which may lead to a different runtime type of the bean, or not being set at all in case of an instance-level factory method (which is resolved via the specified `factory-bean` name instead). Additionally, AOP proxying may wrap a bean instance with an interface-based proxy with limited exposure of the target bean’s actual type (just its implemented interfaces).

特定bean的运行时类型的确定非常重要。bean元数据定义中的指定类只是一个初始类引用，可能与声明的工厂方法组合在一起，或者是一个FactoryBean类，这可能会导致bean的不同运行时类型，或者在实例级工厂方法（通过指定的工厂bean名称解析）的情况下根本没有设置。另外，AOP代理可以用基于接口的代理来包装bean实例，但目标bean的实际类型（只是它实现的接口）的公开有限。

The recommended way to find out about the actual runtime type of a particular bean is a `BeanFactory.getType` call for the specified bean name. This takes all of the above cases into account and returns the type of object that a `BeanFactory.getBean` call is going to return for the same bean name.

了解特定bean的实际运行时类型的推荐方法是BeanFactory.getType调用指定的bean名称。这将考虑上述所有情况并返回BeanFactory.getBean调用将返回相同的bean名称。

#### 1.4. Dependencies 依赖关系

A typical enterprise application does not consist of a single object (or bean in the Spring parlance). Even the simplest application has a few objects that work together to present what the end-user sees as a coherent application. This next section explains how you go from defining a number of bean definitions that stand alone to a fully realized application where objects collaborate to achieve a goal.

典型的企业应用程序不是由单个对象（或者用Spring的说法是bean）组成的。即使是最简单的应用程序也有一些对象协同工作，以呈现最终用户所认为的一致应用程序。下一节将解释如何从定义多个独立的bean定义到一个完全实现的应用程序，在这个应用程序中，对象协作以实现目标。

##### 1.4.1. Dependency Injection（DI，依赖注入）

Dependency injection (DI) is a process whereby objects define their dependencies (that is, the other objects with which they work) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes or the Service Locator pattern.

依赖注入（DI）是一个过程，通过该过程，对象只通过构造函数参数、工厂方法的参数或在对象实例构造或从工厂方法返回后再对象实例上设置的属性来定义其依赖项（即，它们与之一起工作的其他对象）。然后容器在创建bean时注入这些依赖项。这个过程基本上是bean本身的逆过程（因此称为控制反转），它通过直接构造类或服务定位器模式来控制依赖项的实例化或位置。

Code is cleaner with the DI principle, and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies and does not know the location or class of the dependencies. As a result, your classes become easier to test, particularly when the dependencies are on interfaces or abstract base classes, which allow for stub or mock implementations to be used in unit tests.

使用DI原则，代码更简洁，当对象与其依赖项一起提供时，去耦更有效。对象不查找其依赖项，并且不知道依赖项的位置或类。因此，您的类变得更容易测试，特别是当依赖关系位于接口或抽象基类上时，这允许在单元测试中使用存根或模拟实现。

DI exists in two major variants: [Constructor-based dependency injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection) and [Setter-based dependency injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-setter-injection).

DI存在于两个主要变体中：基于构造函数的依赖注入和基于Setter的依赖注入。

Constructor-based Dependency Injection

基于构造函数的依赖注入

Constructor-based DI is accomplished by the container invoking a constructor with a number of arguments, each representing a dependency. Calling a `static` factory method with specific arguments to construct the bean is nearly equivalent, and this discussion treats arguments to a constructor and to a `static` factory method similarly. The following example shows a class that can only be dependency-injected with constructor injection:

基于构造函数的DI是通过容器调用一个构造函数来实现的，每个参数都代表一个依赖项。调用一个带有特定参数的静态工厂方法来构造bean几乎是等价的，本讨论同样处理构造函数和静态工厂方法的参数。以下示例显示的类只能通过构造函数注入进行依赖注入：

Java

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on a MovieFinder
    private MovieFinder movieFinder;

    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

Notice that there is nothing special about this class. It is a POJO that has no dependencies on container specific interfaces, base classes or annotations.

注意这个类没有什么特别之处。它是一个POJO，它不依赖于特定于容器的接口、基类或注释。

Constructor Argument Resolution

构造函数参数解析

Constructor argument resolution matching occurs by using the argument’s type. If no potential ambiguity exists in the constructor arguments of a bean definition, the order in which the constructor arguments are defined in a bean definition is the order in which those arguments are supplied to the appropriate constructor when the bean is being instantiated. Consider the following class:

构造函数参数解析匹配通过使用参数的类型进行。如果bean定义的构造函数参数中不存在潜在的歧义，那么在bean定义中定义构造函数参数的顺序就是在实例化bean时将这些参数提供给适当的构造函数的顺序。考虑以下类别：

Java

```java
package x.y;

public class ThingOne {

    public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
        // ...
    }
}
```

Assuming that `ThingTwo` and `ThingThree` classes are not related by inheritance, no potential ambiguity exists. Thus, the following configuration works fine, and you do not need to specify the constructor argument indexes or types explicitly in the `<constructor-arg/>` element.

假设ThingTwo和thingtree类没有继承关系，则不存在潜在的歧义。因此，以下配置工作正常，您不需要在<constructor arg/>元素中显式指定构造函数参数索引或类型。

```xml
<beans>
    <bean id="beanOne" class="x.y.ThingOne">
        <constructor-arg ref="beanTwo"/>
        <constructor-arg ref="beanThree"/>
    </bean>

    <bean id="beanTwo" class="x.y.ThingTwo"/>

    <bean id="beanThree" class="x.y.ThingThree"/>
</beans>
```

When another bean is referenced, the type is known, and matching can occur (as was the case with the preceding example). When a simple type is used, such as `<value>true</value>`, Spring cannot determine the type of the value, and so cannot match by type without help. Consider the following class:

引用另一个bean时，类型是已知的，并且可以进行匹配（就像前面的例子一样）。当使用简单类型时，例如<value>true</value>，Spring无法确定值的类型，因此在没有帮助的情况下无法按类型进行匹配。考虑以下类别：

Java

```java
package examples;

public class ExampleBean {

    // Number of years to calculate the Ultimate Answer
    private int years;

    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;

    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

Constructor argument type matching

构造函数参数类型匹配

In the preceding scenario, the container can use type matching with simple types if you explicitly specify the type of the constructor argument by using the `type` attribute. as the following example shows:

在前面的场景中，如果通过使用type属性显式指定构造函数参数的类型，则容器可以将类型匹配与简单类型一起使用。如下例所示：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

Constructor argument index

构造函数参数索引

You can use the `index` attribute to specify explicitly the index of constructor arguments, as the following example shows:

可以使用index属性显式指定构造函数参数的索引，如下例所示：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

In addition to resolving the ambiguity of multiple simple values, specifying an index resolves ambiguity where a constructor has two arguments of the same type.

除了解决多个简单值的模糊性外，指定索引还可以解决构造函数具有两个相同类型参数的模糊性。

|      | The index is 0-based. |
| ---- | --------------------- |
|      | 索引从0开始。         |

Constructor argument name

构造函数参数名

You can also use the constructor parameter name for value disambiguation, as the following example shows:

也可以使用构造函数参数名来消除值歧义，如下例所示：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

Keep in mind that, to make this work out of the box, your code must be compiled with the debug flag enabled so that Spring can look up the parameter name from the constructor. If you cannot or do not want to compile your code with the debug flag, you can use the [@ConstructorProperties](https://download.oracle.com/javase/8/docs/api/java/beans/ConstructorProperties.html) JDK annotation to explicitly name your constructor arguments. The sample class would then have to look as follows:

请记住，要使这项工作开箱即用，必须在编译代码时启用调试标志，这样Spring才能从构造函数中查找参数名。如果您不能或不想使用debug标志编译代码，可以使用@ConstructorProperties JDK注释显式地命名构造函数参数。然后示例类必须如下所示：

Java

```java
package examples;

public class ExampleBean {
    // Fields omitted
    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

Setter-based Dependency Injection

基于Setter的依赖注入

Setter-based DI is accomplished by the container calling setter methods on your beans after invoking a no-argument constructor or a no-argument `static` factory method to instantiate your bean.

基于Setter的DI是由容器在调用无参数构造函数或无参数静态工厂方法实例化bean后调用bean上的Setter方法来实现的。

The following example shows a class that can only be dependency-injected by using pure setter injection. This class is conventional Java. It is a POJO that has no dependencies on container specific interfaces, base classes, or annotations.

只有通过下面的示例说明了只能使用下面的setter类注入的纯依赖项。这个类是传统的Java。它是一个POJO，它不依赖于特定于容器的接口、基类或注释。

Java

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;

    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

The `ApplicationContext` supports constructor-based and setter-based DI for the beans it manages. It also supports setter-based DI after some dependencies have already been injected through the constructor approach. You configure the dependencies in the form of a `BeanDefinition`, which you use in conjunction with `PropertyEditor` instances to convert properties from one format to another. However, most Spring users do not work with these classes directly (that is, programmatically) but rather with XML `bean` definitions, annotated components (that is, classes annotated with `@Component`, `@Controller`, and so forth), or `@Bean` methods in Java-based `@Configuration` classes. These sources are then converted internally into instances of `BeanDefinition` and used to load an entire Spring IoC container instance.

ApplicationContext支持其管理的bean基于构造函数和基于setter的DI。在一些依赖项已经通过构造函数方法注入之后，它还支持基于setter的DI。以BeanDefinition的形式配置依赖项，该定义与PropertyEditor实例一起使用，以将属性从一种格式转换为另一种格式。然而，大多数Spring用户并不直接使用这些类（即以编程方式），而是使用xml bean定义、带注释的组件（即用@Component、@Controller等注释的类）或基于Java的 @Configuration类中的 @bean方法。然后，这些源代码在内部转换为BeanDefinition的实例，并用于加载整个SpringIoC容器实例。

Constructor-based or setter-based DI?

基于构造函数还是基于setter的DI？

Since you can mix constructor-based and setter-based DI, it is a good rule of thumb to use constructors for mandatory dependencies and setter methods or configuration methods for optional dependencies. Note that use of the [@Required](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-required-annotation) annotation on a setter method can be used to make the property be a required dependency; however, constructor injection with programmatic validation of arguments is preferable.

由于您可以混合基于构造函数和基于setter的DI，所以最好的经验法则是将构造函数用于强制依赖项，而setter方法或配置方法用于可选依赖项。注意，在setter方法上使用@Required注释可以使属性成为必需的依赖项；但是，最好使用参数编程验证的构造函数注入。

The Spring team generally advocates constructor injection, as it lets you implement application components as immutable objects and ensures that required dependencies are not `null`. Furthermore, constructor-injected components are always returned to the client (calling) code in a fully initialized state. As a side note, a large number of constructor arguments is a bad code smell, implying that the class likely has too many responsibilities and should be refactored to better address proper separation of concerns.

Spring团队通常提倡构造函数注入，因为它允许您将应用程序组件实现为不可变的对象，并确保所需的依赖项不为空。此外，构造函数注入的组件总是以完全初始化的状态返回给客户端（调用）代码。顺便说一句，大量的构造函数参数是一种糟糕的代码气味，这意味着类可能有太多的责任，应该进行重构以更好地解决关注点的正确分离。

Setter injection should primarily only be used for optional dependencies that can be assigned reasonable default values within the class. Otherwise, not-null checks must be performed everywhere the code uses the dependency. One benefit of setter injection is that setter methods make objects of that class amenable to reconfiguration or re-injection later. Management through [JMX MBeans](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#jmx) is therefore a compelling use case for setter injection.

Setter注入应该主要用于可选的依赖项，这些依赖项可以在类中分配合理的默认值。否则，必须在代码使用依赖项的任何地方执行not null检查。setter注入的一个好处是setter方法使该类的对象能够在以后重新配置或重新注入。因此，通过jmx mbean进行管理是setter注入的一个引人注目的用例。

Use the DI style that makes the most sense for a particular class. Sometimes, when dealing with third-party classes for which you do not have the source, the choice is made for you. For example, if a third-party class does not expose any setter methods, then constructor injection may be the only available form of DI.

使用对特定类最有意义的DI样式。有时，在处理第三方类时，如果您没有源代码，则会为您做出选择。例如，如果第三方类不公开任何setter方法，那么构造函数注入可能是DI的唯一可用形式。

Dependency Resolution Process

依赖关系解决过程

The container performs bean dependency resolution as follows:

容器执行bean依赖解析，如下所示：

- The `ApplicationContext` is created and initialized with configuration metadata that describes all the beans. Configuration metadata can be specified by XML, Java code, or annotations.
- ApplicationContext是用描述所有bean的配置元数据 创建和初始化的。配置元数据可以由XML、Java代码或注释指定。
- For each bean, its dependencies are expressed in the form of properties, constructor arguments, or arguments to the static-factory method (if you use that instead of a normal constructor). These dependencies are provided to the bean, when the bean is actually created.
- 对于每个bean，它的依赖关系以属性、构造函数参数或静态工厂方法的参数的形式表示（如果您使用静态工厂方法而不是普通构造函数）。当实际创建bean时，这些依赖项被提供给bean。
- Each property or constructor argument is an actual definition of the value to set, or a reference to another bean in the container.
- 每个属性或构造函数参数都是要设置的值的实际定义，或者是对容器中另一个bean的引用。
- Each property or constructor argument that is a value is converted from its specified format to the actual type of that property or constructor argument. By default, Spring can convert a value supplied in string format to all built-in types, such as `int`, `long`, `String`, `boolean`, and so forth.
- 作为值的每个属性或构造函数参数都从其指定的格式转换为该属性或构造函数参数的实际类型。默认情况下，Spring可以将以字符串格式提供的值转换为所有内置类型，例如int、long、string、boolean等等。

The Spring container validates the configuration of each bean as the container is created. However, the bean properties themselves are not set until the bean is actually created. Beans that are singleton-scoped and set to be pre-instantiated (the default) are created when the container is created. Scopes are defined in [Bean Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes). Otherwise, the bean is created only when it is requested. Creation of a bean potentially causes a graph of beans to be created, as the bean’s dependencies and its dependencies' dependencies (and so on) are created and assigned. Note that resolution mismatches among those dependencies may show up late — that is, on first creation of the affected bean.

Spring容器在创建容器时验证每个bean的配置。但是，在实际创建bean之前，不会设置bean属性本身。在创建容器时，将创建单独作用域并设置为预实例化（默认值）的bean。作用域是在Bean作用域中定义的。否则，只有在请求时才会创建bean。创建bean可能会导致创建一个bean图（查看算法遍历图使用栈stack），因为bean的依赖项及其依赖项（等等）被创建和分配。请注意，这些依赖项之间的解析不匹配可能会出现在较晚的情况—— ，也就是说，在第一次创建受影响的bean时。

Circular dependencies

循环依赖项

If you use predominantly constructor injection, it is possible to create an unresolvable circular dependency scenario.

如果主要使用构造函数注入，则有可能创建无法解决的循环依赖场景。

For example: Class A requires an instance of class B through constructor injection, and class B requires an instance of class A through constructor injection. If you configure beans for classes A and B to be injected into each other, the Spring IoC container detects this circular reference at runtime, and throws a `BeanCurrentlyInCreationException`.

例如：类A通过构造函数注入需要一个类B的实例，而B类通过构造函数注入需要一个类A的实例。如果为类A和类B配置bean以相互注入，springioc容器将在运行时检测到这个循环引用，并抛出一个beancurrentlyIncrementationException。

One possible solution is to edit the source code of some classes to be configured by setters rather than constructors. Alternatively, avoid constructor injection and use setter injection only. In other words, although it is not recommended, you can configure circular dependencies with setter injection.

一种可能的解决方案是编辑一些类的源代码，这些类由setter而不是构造函数配置。或者，避免构造函数注入，只使用setter注入。换句话说，尽管不建议这样做，但您可以使用setter注入配置循环依赖项。

Unlike the typical case (with no circular dependencies), a circular dependency between bean A and bean B forces one of the beans to be injected into the other prior to being fully initialized itself (a classic chicken-and-egg scenario).

与典型的情况（没有循环依赖）不同，BeanA和BeanB之间的循环依赖迫使一个bean在被完全初始化之前被注入另一个（一个典型的鸡和蛋场景）。

You can generally trust Spring to do the right thing. It detects configuration problems, such as references to non-existent beans and circular dependencies, at container load-time. Spring sets properties and resolves dependencies as late as possible, when the bean is actually created. This means that a Spring container that has loaded correctly can later generate an exception when you request an object if there is a problem creating that object or one of its dependencies — for example, the bean throws an exception as a result of a missing or invalid property. This potentially delayed visibility of some configuration issues is why `ApplicationContext` implementations by default pre-instantiate singleton beans. At the cost of some upfront time and memory to create these beans before they are actually needed, you discover configuration issues when the `ApplicationContext` is created, not later. You can still override this default behavior so that singleton beans initialize lazily, rather than being pre-instantiated.

你可以相信Spring会做正确的事情。它在容器加载时检测配置问题，例如对不存在的bean的引用和循环依赖关系。当实际创建bean时，Spring设置属性并尽可能晚地解析依赖项。这意味着如果在创建对象或它的某个依赖项时出现问题，那么正确加载的Spring容器以后可以在请求对象时生成异常。例如，由于缺少属性或属性无效，bean抛出异常。某些配置问题的潜在延迟可见性就是ApplicationContext实现在默认情况下预先实例化singleton bean的原因。在实际需要这些bean之前，需要花费一些前期时间和内存来创建这些bean，但是在创建ApplicationContext时（而不是以后）会发现配置问题。您仍然可以重写此默认行为，以便单例bean延迟初始化，而不是预先实例化。

If no circular dependencies exist, when one or more collaborating beans are being injected into a dependent bean, each collaborating bean is totally configured prior to being injected into the dependent bean. This means that, if bean A has a dependency on bean B, the Spring IoC container completely configures bean B prior to invoking the setter method on bean A. In other words, the bean is instantiated (if it is not a pre-instantiated singleton), its dependencies are set, and the relevant lifecycle methods (such as a [configured init method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean) or the [InitializingBean callback method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean)) are invoked.

如果不存在循环依赖关系，那么当一个或多个协作bean被注入到依赖bean中时，每个协作bean在被注入到依赖bean之前被完全配置。这意味着，如果BeanA对BeanB有依赖关系，那么SpringIoC容器在调用BeanA上的setter方法之前完全配置了BeanB。换句话说，bean被实例化（如果它不是预先实例化的单例），它的依赖关系被设置，并调用相关的生命周期方法（例如配置的init方法或initializationBean回调方法）。

###### Examples of Dependency Injection

依赖注入示例

The following example uses XML-based configuration metadata for setter-based DI. A small part of a Spring XML configuration file specifies some bean definitions as follows:

下面的示例将基于XML的配置元数据用于基于setter的DI。Spring-XML配置文件的一小部分指定了一些bean定义，如下所示：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

The following example shows the corresponding `ExampleBean` class:

以下示例显示了相应的ExampleBean类：

Java

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```

In the preceding example, setters are declared to match against the properties specified in the XML file. The following example uses constructor-based DI:

在前面的示例中，setter声明为与XML文件中指定的属性相匹配。以下示例使用基于构造函数的DI：

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

The following example shows the corresponding `ExampleBean` class:

以下示例显示了相应的ExampleBean类：

Java

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }
}
```

The constructor arguments specified in the bean definition are used as arguments to the constructor of the `ExampleBean`.

bean定义中指定的构造函数参数用作ExampleBean的构造函数的参数。

Now consider a variant of this example, where, instead of using a constructor, Spring is told to call a `static` factory method to return an instance of the object:

现在考虑这个例子的一个变体，其中告诉Spring调用静态工厂方法来返回对象的实例，而不是使用构造函数：

```xml
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

The following example shows the corresponding `ExampleBean` class:

以下示例显示了相应的ExampleBean类：

Java

```java
public class ExampleBean {

    // a private constructor
    private ExampleBean(...) {
        ...
    }

    // a static factory method; the arguments to this method can be
    // considered the dependencies of the bean that is returned,
    // regardless of how those arguments are actually used.
    public static ExampleBean createInstance (
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {

        ExampleBean eb = new ExampleBean (...);
        // some other operations...
        return eb;
    }
}
```

Arguments to the `static` factory method are supplied by `<constructor-arg/>` elements, exactly the same as if a constructor had actually been used. The type of the class being returned by the factory method does not have to be of the same type as the class that contains the `static` factory method (although, in this example, it is). An instance (non-static) factory method can be used in an essentially identical fashion (aside from the use of the `factory-bean` attribute instead of the `class` attribute), so we do not discuss those details here.

静态工厂方法的参数由<constructor arg/>元素提供，与实际使用构造函数时完全相同。工厂方法返回的类的类型不必与包含静态工厂方法的类具有相同的类型（尽管在本例中是这样）。实例（非静态）工厂方法可以以本质上相同的方式使用（除了使用factory bean属性而不是class属性），因此我们不在这里讨论这些细节。

##### 1.4.2. Dependencies and Configuration in Detail 详细的依赖关系和配置

As mentioned in the [previous section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators), you can define bean properties and constructor arguments as references to other managed beans (collaborators) or as values defined inline. Spring’s XML-based configuration metadata supports sub-element types within its `<property/>` and `<constructor-arg/>` elements for this purpose.

如前一节所述，可以将bean属性和构造函数参数定义为对其他托管bean（协作者）的引用，也可以定义为内联定义的值。Spring基于XML的配置元数据支持其<property/>和<constructor arg/>元素中的子元素类型。

###### Straight Values (Primitives, Strings, and so on)

直接值（基元、字符串等）

The `value` attribute of the `<property/>` element specifies a property or constructor argument as a human-readable string representation. Spring’s [conversion service](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#core-convert-ConversionService-API) is used to convert these values from a `String` to the actual type of the property or argument. The following example shows various values being set:

将<property/>属性的值指定为<property/>的值。Spring的转换服务用于将这些值从字符串转换为属性或参数的实际类型。以下示例显示了正在设置的各种值：

```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <!-- results in a setDriverClassName(String) call -->
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="misterkaoli"/>
</bean>
```

The following example uses the [p-namespace](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-p-namespace) for even more succinct XML configuration:

以下示例使用p-namespace进行更简洁的XML配置：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close"
        p:driverClassName="com.mysql.jdbc.Driver"
        p:url="jdbc:mysql://localhost:3306/mydb"
        p:username="root"
        p:password="misterkaoli"/>

</beans>
```

The preceding XML is more succinct. However, typos are discovered at runtime rather than design time, unless you use an IDE (such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or the [Spring Tools for Eclipse](https://spring.io/tools)) that supports automatic property completion when you create bean definitions. Such IDE assistance is highly recommended.

前面的XML更简洁。但是，除非使用在创建bean定义时支持自动属性完成的IDE（如intellijidea或Eclipse的springtools），否则在运行时而不是在设计时发现打字错误。强烈建议使用这种IDE帮助。

You can also configure a `java.util.Properties` instance, as follows:

也可以配置java.util.Properties属性实例如下：

```xml
<bean id="mappings"
    class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">

    <!-- typed as a java.util.Properties -->
    <property name="properties">
        <value>
            jdbc.driver.className=com.mysql.jdbc.Driver
            jdbc.url=jdbc:mysql://localhost:3306/mydb
        </value>
    </property>
</bean>
```

The Spring container converts the text inside the `<value/>` element into a `java.util.Properties` instance by using the JavaBeans `PropertyEditor` mechanism. This is a nice shortcut, and is one of a few places where the Spring team do favor the use of the nested `<value/>` element over the `value` attribute style.

将Spring容器元素中的值转换为java.util.Properties属性通过使用JavaBeans PropertyEditor机制。这是一个很好的快捷方式，也是Spring团队支持使用嵌套的<value/>元素而不是value属性样式的几个地方之一。

The `idref` element

The `idref` element is simply an error-proof way to pass the `id` (a string value - not a reference) of another bean in the container to a `<constructor-arg/>` or `<property/>` element. The following example shows how to use it:

idref元素只是将容器中另一个bean的id（字符串值-不是引用）传递给<constructor arg/>或<property/>元素的一种简单的防错误方法。下面的示例演示如何使用它：

```xml
<bean id="theTargetBean" class="..."/>

<bean id="theClientBean" class="...">
    <property name="targetName">
        <idref bean="theTargetBean"/>
    </property>
</bean>
```

The preceding bean definition snippet is exactly equivalent (at runtime) to the following snippet:

前面的bean定义片段（在运行时）与以下片段完全等效：

```xml
<bean id="theTargetBean" class="..." />

<bean id="client" class="...">
    <property name="targetName" value="theTargetBean"/>
</bean>
```

The first form is preferable to the second, because using the `idref` tag lets the container validate at deployment time that the referenced, named bean actually exists. In the second variation, no validation is performed on the value that is passed to the `targetName` property of the `client` bean. Typos are only discovered (with most likely fatal results) when the `client` bean is actually instantiated. If the `client` bean is a [prototype](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes) bean, this typo and the resulting exception may only be discovered long after the container is deployed.

第一种形式优于第二种形式，因为使用idref标记可以让容器在部署时验证引用的、命名的bean是否实际存在。在第二个变体中，不对传递给客户机bean的targetName属性的值执行任何验证。只有当客户机bean被实际实例化时，才会发现输入错误（最有可能是致命的结果）。如果客户机bean是一个原型bean，那么只有在部署容器很久之后才会发现这个输入错误和产生的异常。

|      | The `local` attribute on the `idref` element is no longer supported in the 4.0 beans XSD, since it does not provide value over a regular `bean` reference any more. Change your existing `idref local` references to `idref bean` when upgrading to the 4.0 schema. |
| ---- | ------------------------------------------------------------ |
|      | 4.0beansxsd不再支持idref元素上的local属性，因为它不再提供常规bean引用的值。升级到4.0模式时，将现有的idref本地引用更改为idref bean。 |

A common place (at least in versions earlier than Spring 2.0) where the `<idref/>` element brings value is in the configuration of [AOP interceptors](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pfb-1) in a `ProxyFactoryBean` bean definition. Using `<idref/>` elements when you specify the interceptor names prevents you from misspelling an interceptor ID.

<idref/>元素带来价值的一个常见地方（至少在spring2.0之前的版本中）是在ProxyFactoryBean定义中配置AOP拦截器。在指定拦截器名称时使用<idref/>元素可防止拼写错误的拦截器ID。

###### References to Other Beans (Collaborators)

对其他bean的引用（协作者）

The `ref` element is the final element inside a `<constructor-arg/>` or `<property/>` definition element. Here, you set the value of the specified property of a bean to be a reference to another bean (a collaborator) managed by the container. The referenced bean is a dependency of the bean whose property is to be set, and it is initialized on demand as needed before the property is set. (If the collaborator is a singleton bean, it may already be initialized by the container.) All references are ultimately a reference to another object. Scoping and validation depend on whether you specify the ID or name of the other object through the `bean` or `parent` attribute.

ref元素是`<constructor-arg/>`或<property/>定义元素中的最后一个元素。在这里，您将bean的指定属性的值设置为对容器管理的另一个bean（协作者）的引用。被引用的bean是要设置其属性的bean的依赖项，并且在设置属性之前根据需要对其进行初始化。（如果collaborator是一个单例bean，那么它可能已经被容器初始化了。）所有引用最终都是对另一个对象的引用。作用域和验证取决于是否通过bean或父属性指定另一个对象的ID或名称。

Specifying the target bean through the `bean` attribute of the `<ref/>` tag is the most general form and allows creation of a reference to any bean in the same container or parent container, regardless of whether it is in the same XML file. The value of the `bean` attribute may be the same as the `id` attribute of the target bean or be the same as one of the values in the `name` attribute of the target bean. The following example shows how to use a `ref` element:

通过<ref/>标记的bean属性指定目标bean是最常见的形式，它允许创建对同一容器或父容器中任何bean的引用，而不管它是否在同一个XML文件中。bean属性的值可以与目标bean的id属性相同，也可以与目标bean的name属性中的某个值相同。以下示例显示如何使用ref元素：

```xml
<ref bean="someBean"/>
```

Specifying the target bean through the `parent` attribute creates a reference to a bean that is in a parent container of the current container. The value of the `parent` attribute may be the same as either the `id` attribute of the target bean or one of the values in the `name` attribute of the target bean. The target bean must be in a parent container of the current one. You should use this bean reference variant mainly when you have a hierarchy of containers and you want to wrap an existing bean in a parent container with a proxy that has the same name as the parent bean. The following pair of listings shows how to use the `parent` attribute:

通过parent属性指定目标bean将创建对当前容器的父容器中的bean的引用。父属性的值可以与目标bean的id属性或目标bean的name属性中的某个值相同。目标bean必须位于当前bean的父容器中。您应该使用这个bean引用变量，主要是当您有一个容器的层次结构，并且您想用一个与父bean同名的代理将现有的bean包装在父容器中。以下两个列表显示了如何使用parent属性：

```xml
<!-- in the parent context -->
<bean id="accountService" class="com.something.SimpleAccountService">
    <!-- insert dependencies as required as here -->
</bean>
<!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target">
        <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
    </property>
    <!-- insert other configuration and dependencies as required here -->
</bean>
```

|      | The `local` attribute on the `ref` element is no longer supported in the 4.0 beans XSD, since it does not provide value over a regular `bean` reference any more. Change your existing `ref local` references to `ref bean` when upgrading to the 4.0 schema. |
| ---- | ------------------------------------------------------------ |
|      | 4.0beansxsd不再支持ref元素上的local属性，因为它不再提供常规bean引用的值。在升级到4.0模式时，将现有的ref本地引用更改为ref bean。 |

###### Inner Beans

A `<bean/>` element inside the `<property/>` or `<constructor-arg/>` elements defines an inner bean, as the following example shows:

```xml
<bean id="outer" class="...">
    <!-- instead of using a reference to a target bean, simply define the target bean inline -->
    <property name="target">
        <bean class="com.example.Person"> <!-- this is the inner bean -->
            <property name="name" value="Fiona Apple"/>
            <property name="age" value="25"/>
        </bean>
    </property>
</bean>
```

An inner bean definition does not require a defined ID or name. If specified, the container does not use such a value as an identifier. The container also ignores the `scope` flag on creation, because inner beans are always anonymous and are always created with the outer bean. It is not possible to access inner beans independently or to inject them into collaborating beans other than into the enclosing bean.

内部bean定义不需要定义的ID或名称。如果指定，则容器不使用此值作为标识符。容器在创建时也会忽略范围标志，因为内部bean总是匿名的，并且总是与外部bean一起创建。不可能独立地访问内部bean，也不可能将它们注入到除封闭bean之外的协作bean中。

As a corner case, it is possible to receive destruction callbacks from a custom scope — for example, for a request-scoped inner bean contained within a singleton bean. The creation of the inner bean instance is tied to its containing bean, but destruction callbacks let it participate in the request scope’s lifecycle. This is not a common scenario. Inner beans typically simply share their containing bean’s scope.

例如，从一个单独的bean到一个单独的bean的作用域，比如一个调用bean。内部bean实例的创建与其包含的bean相关联，但是销毁回调允许它参与请求范围的生命周期。这种情况并不常见。内部bean通常只共享它们的包含bean的范围。

###### Collections

The `<list/>`, `<set/>`, `<map/>`, and `<props/>` elements set the properties and arguments of the Java `Collection` types `List`, `Set`, `Map`, and `Properties`, respectively. The following example shows how to use them:

<list/>、<set/>、<map/>和<props/>元素分别设置Java集合类型list、set、map和properties的属性和参数。下面的示例演示如何使用它们：

```xml
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
```

The value of a map key or value, or a set value, can also be any of the following elements:

映射键或值的值，或设置值，也可以是以下任何元素：

```xml
bean | ref | idref | list | set | map | props | value | null
```

Collection Merging

集合合并

The Spring container also supports merging collections. An application developer can define a parent <list/>, <map/>, <set/> or <props/> element and have child <list/>, <map/>, <set/> or <props/> elements inherit and override values from the parent collection. That is, the child collection’s values are the result of merging the elements of the parent and child collections, with the child’s collection elements overriding values specified in the parent collection.

Spring容器还支持合并集合。应用程序开发人员可以定义父元素<list/>、<map/>、<set/>或<props/>元素，并让子元素<list/>、<map/>、<set/>或<props/>继承并重写父集合中的值。也就是说，子集合的值是合并父集合和子集合的元素的结果，子集合元素覆盖父集合中指定的值。

This section on merging discusses the parent-child bean mechanism. Readers unfamiliar with parent and child bean definitions may wish to read the [relevant section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-child-bean-definitions) before continuing.

关于合并的这一节讨论了父子bean机制。不熟悉父bean和子bean定义的读者可能希望在继续之前阅读相关部分。

The following example demonstrates collection merging:

以下示例演示集合合并：

```xml
<beans>
    <bean id="parent" abstract="true" class="example.ComplexObject">
        <property name="adminEmails">
            <props>
                <prop key="administrator">administrator@example.com</prop>
                <prop key="support">support@example.com</prop>
            </props>
        </property>
    </bean>
    <bean id="child" parent="parent">
        <property name="adminEmails">
            <!-- the merge is specified on the child collection definition -->
            <props merge="true">
                <prop key="sales">sales@example.com</prop>
                <prop key="support">support@example.co.uk</prop>
            </props>
        </property>
    </bean>
<beans>
```

Notice the use of the `merge=true` attribute on the `<props/>` element of the `adminEmails` property of the `child` bean definition. When the `child` bean is resolved and instantiated by the container, the resulting instance has an `adminEmails` `Properties` collection that contains the result of merging the child’s `adminEmails` collection with the parent’s `adminEmails` collection. The following listing shows the result:

注意在子bean定义的adminEmails属性的<props/>元素上使用了merge=true属性。当容器解析并实例化子bean时，生成的实例具有一个adminEmails Properties集合，该集合包含将子级的adminEmails集合与父级的adminEmails集合合并的结果。下面的列表显示了结果：

```
administrator=administrator@example.com
sales=sales@example.com
support=support@example.co.uk
```

The child `Properties` collection’s value set inherits all property elements from the parent `<props/>`, and the child’s value for the `support` value overrides the value in the parent collection.

子Properties集合的值集继承父集合<props/>中的所有属性元素，子级的支持值的值覆盖父集合中的值。

This merging behavior applies similarly to the `<list/>`, `<map/>`, and `<set/>` collection types. In the specific case of the `<list/>` element, the semantics associated with the `List` collection type (that is, the notion of an `ordered` collection of values) is maintained. The parent’s values precede all of the child list’s values. In the case of the `Map`, `Set`, and `Properties` collection types, no ordering exists. Hence, no ordering semantics are in effect for the collection types that underlie the associated `Map`, `Set`, and `Properties` implementation types that the container uses internally.

此合并行为类似于<list/>、<map/>和<set/>集合类型。在<list/>元素的特定情况下，与list collection类型相关联的语义（即值的有序集合的概念）被维护。父项的值先于所有子项列表的值。对于Map、Set和Properties集合类型，不存在排序。因此，对于容器内部使用的关联映射、集和属性实现类型的集合类型，没有有效的排序语义。

Limitations of Collection Merging

集合合并的局限性

You cannot merge different collection types (such as a `Map` and a `List`). If you do attempt to do so, an appropriate `Exception` is thrown. The `merge` attribute must be specified on the lower, inherited, child definition. Specifying the `merge` attribute on a parent collection definition is redundant and does not result in the desired merging.

不能合并不同的集合类型（如映射和列表）。如果您确实尝试这样做，则会引发适当的异常。必须在较低的继承子定义上指定merge属性。在父集合定义上指定merge属性是多余的，不会导致所需的合并。

Strongly-typed collection

强类型集合

With the introduction of generic types in Java 5, you can use strongly typed collections. That is, it is possible to declare a `Collection` type such that it can only contain (for example) `String` elements. If you use Spring to dependency-inject a strongly-typed `Collection` into a bean, you can take advantage of Spring’s type-conversion support such that the elements of your strongly-typed `Collection` instances are converted to the appropriate type prior to being added to the `Collection`. The following Java class and bean definition show how to do so:

随着Java5中泛型类型的引入，您可以使用强类型集合。也就是说，可以声明一个集合类型，使其只能包含（例如）字符串元素。如果使用Spring将强类型集合注入bean中，那么可以利用Spring的类型转换支持，以便在将强类型集合实例的元素添加到集合之前将其转换为适当的类型。下面的Java类的定义如下：

Java

```java
public class SomeClass {

    private Map<String, Float> accounts;

    public void setAccounts(Map<String, Float> accounts) {
        this.accounts = accounts;
    }
}
```

```xml
<beans>
    <bean id="something" class="x.y.SomeClass">
        <property name="accounts">
            <map>
                <entry key="one" value="9.99"/>
                <entry key="two" value="2.75"/>
                <entry key="six" value="3.99"/>
            </map>
        </property>
    </bean>
</beans>
```

When the `accounts` property of the `something` bean is prepared for injection, the generics information about the element type of the strongly-typed `Map<String, Float>` is available by reflection. Thus, Spring’s type conversion infrastructure recognizes the various value elements as being of type `Float`, and the string values (`9.99, 2.75`, and `3.99`) are converted into an actual `Float` type.

当something bean的accounts属性准备好注入时，关于强类型Map<String，Float>的元素类型的泛型信息可以通过反射获得。因此，Spring的类型转换基础设施将各种值元素识别为Float类型，字符串值（9.99、2.75和3.99）转换为实际的Float类型。

###### Null and Empty String Values

空字符串值

Spring treats empty arguments for properties and the like as empty `Strings`. The following XML-based configuration metadata snippet sets the `email` property to the empty `String` value ("").

Spring将属性等的空参数视为空字符串。以下基于XML的配置元数据片段将email属性设置为空字符串值（“”）。

```xml
<bean class="ExampleBean">
    <property name="email" value=""/>
</bean>
```

The preceding example is equivalent to the following Java code:

前面的示例等效于以下Java代码：

Java

```java
exampleBean.setEmail("");
```

The `<null/>` element handles `null` values. The following listing shows an example:

<null/>元素处理空值。下面的列表显示了一个示例：

```xml
<bean class="ExampleBean">
    <property name="email">
        <null/>
    </property>
</bean>
```

The preceding configuration is equivalent to the following Java code:

前面的配置相当于以下Java代码：

Java

```java
exampleBean.setEmail(null);
```

###### XML Shortcut with the p-namespace

The p-namespace lets you use the `bean` element’s attributes (instead of nested `<property/>` elements) to describe your property values collaborating beans, or both.

p-namespace允许您使用bean元素的属性（而不是嵌套的<property/>元素）来描述协作bean的属性值，或者两者兼而有之。

Spring supports extensible configuration formats [with namespaces](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas), which are based on an XML Schema definition. The `beans` configuration format discussed in this chapter is defined in an XML Schema document. However, the p-namespace is not defined in an XSD file and exists only in the core of Spring.

Spring支持带有名称空间的可扩展配置格式，名称空间基于XML模式定义。本章中讨论的bean配置格式是在XML模式文档中定义的。但是，p-namespace不是在XSD文件中定义的，它只存在于Spring的核心中。

The following example shows two XML snippets (the first uses standard XML format and the second uses the p-namespace) that resolve to the same result:

以下示例显示了两个XML片段（第一个使用标准XML格式，第二个使用p命名空间），它们解析为相同的结果：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="classic" class="com.example.ExampleBean">
        <property name="email" value="someone@somewhere.com"/>
    </bean>

    <bean name="p-namespace" class="com.example.ExampleBean"
        p:email="someone@somewhere.com"/>
</beans>
```



The example shows an attribute in the p-namespace called `email` in the bean definition. This tells Spring to include a property declaration. As previously mentioned, the p-namespace does not have a schema definition, so you can set the name of the attribute to the property name.

该示例显示了p名称空间中bean定义中名为email的属性。这告诉Spring包含一个属性声明。如前所述，p-namespace没有模式定义，因此可以将属性的名称设置为属性名称。

This next example includes two more bean definitions that both have a reference to another bean:

下一个示例包括另外两个bean定义，它们都引用了另一个bean：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="john-classic" class="com.example.Person">
        <property name="name" value="John Doe"/>
        <property name="spouse" ref="jane"/>
    </bean>

    <bean name="john-modern"
        class="com.example.Person"
        p:name="John Doe"
        p:spouse-ref="jane"/>

    <bean name="jane" class="com.example.Person">
        <property name="name" value="Jane Doe"/>
    </bean>
</beans>
```



This example includes not only a property value using the p-namespace but also uses a special format to declare property references. Whereas the first bean definition uses `<property name="spouse" ref="jane"/>` to create a reference from bean `john` to bean `jane`, the second bean definition uses `p:spouse-ref="jane"` as an attribute to do the exact same thing. In this case, `spouse` is the property name, whereas the `-ref` part indicates that this is not a straight value but rather a reference to another bean.

这个例子不仅包含一个使用p命名空间的属性值，而且还使用一种特殊的格式来声明属性引用。第一个bean定义使用<property name=“spose”ref=“jane”/>来创建一个从bean john到bean jane的引用，而第二个bean定义使用p:spoise-ref=“jane”作为一个属性来执行相同的操作。在本例中，spoose是属性名，而-ref部分表示这不是一个直接值，而是对另一个bean的引用。

|      | The p-namespace is not as flexible as the standard XML format. For example, the format for declaring property references clashes with properties that end in `Ref`, whereas the standard XML format does not. We recommend that you choose your approach carefully and communicate this to your team members to avoid producing XML documents that use all three approaches at the same time. |
| ---- | ------------------------------------------------------------ |
|      | p-namespace不如标准XML格式灵活。例如，声明属性引用的格式与以Ref结尾的属性冲突，而标准XML格式则不冲突。我们建议您谨慎地选择您的方法，并与您的团队成员进行沟通，以避免生成同时使用这三种方法的XML文档。 |

###### XML Shortcut with the c-namespace

带有c命名空间的XML快捷方式

Similar to the [XML Shortcut with the p-namespace](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-p-namespace), the c-namespace, introduced in Spring 3.1, allows inlined attributes for configuring the constructor arguments rather then nested `constructor-arg` elements.

与p-namespace的XML快捷方式类似，spring3.1中引入的c-namespace允许内联属性来配置构造函数参数，而不是嵌套的构造函数arg元素。

The following example uses the `c:` namespace to do the same thing as the from [Constructor-based Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection):

以下示例使用c:命名空间执行与基于from构造函数的依赖项注入相同的操作：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:c="http://www.springframework.org/schema/c"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="beanTwo" class="x.y.ThingTwo"/>
    <bean id="beanThree" class="x.y.ThingThree"/>
    <!-- traditional declaration with optional argument names -->
    <bean id="beanOne" class="x.y.ThingOne">
        <constructor-arg name="thingTwo" ref="beanTwo"/>
        <constructor-arg name="thingThree" ref="beanThree"/>
        <constructor-arg name="email" value="something@somewhere.com"/>
    </bean>
    <!-- c-namespace declaration with argument names -->
    <bean id="beanOne" class="x.y.ThingOne" c:thingTwo-ref="beanTwo"
        c:thingThree-ref="beanThree" c:email="something@somewhere.com"/>
</beans>
```

The `c:` namespace uses the same conventions as the `p:` one (a trailing `-ref` for bean references) for setting the constructor arguments by their names. Similarly, it needs to be declared in the XML file even though it is not defined in an XSD schema (it exists inside the Spring core).

命名空间使用与p:one（bean引用的尾部-ref）相同的约定，用于按名称设置构造函数参数。类似地，它需要在XML文件中声明，即使它没有在XSD模式中定义（它存在于Spring内核中）。

For the rare cases where the constructor argument names are not available (usually if the bytecode was compiled without debugging information), you can use fallback to the argument indexes, as follows:

对于构造函数参数名不可用的少数情况（通常，如果编译字节码时没有调试信息），可以使用回退到参数索引，如下所示：

```xml
<!-- c-namespace index declaration -->
<bean id="beanOne" class="x.y.ThingOne" c:_0-ref="beanTwo" c:_1-ref="beanThree"
    c:_2="something@somewhere.com"/>
```

|      | Due to the XML grammar, the index notation requires the presence of the leading `_`, as XML attribute names cannot start with a number (even though some IDEs allow it). A corresponding index notation is also available for `<constructor-arg>` elements but not commonly used since the plain order of declaration is usually sufficient there. |
| ---- | ------------------------------------------------------------ |
|      | 由于XML语法的原因，索引表示法要求出现前导的U，因为XML属性名不能以数字开头（即使某些IDE允许这样做）。相应的索引表示法也可用于<constructor arg>元素，但不常用，因为声明的简单顺序通常就足够了。 |

In practice, the constructor resolution [mechanism](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-ctor-arguments-resolution) is quite efficient in matching arguments, so unless you really need to, we recommend using the name notation through-out your configuration.

实际上，构造函数解析机制在匹配参数方面非常有效，因此除非您确实需要，否则我们建议您在整个配置中使用名称表示法。

###### Compound Property Names

复合属性名称

You can use compound or nested property names when you set bean properties, as long as all components of the path except the final property name are not `null`. Consider the following bean definition:

在设置bean属性时，可以使用复合属性名或嵌套属性名，只要路径中除最终属性名之外的所有组件都不为空。考虑下面的bean定义：

```xml
<bean id="something" class="things.ThingOne">
    <property name="fred.bob.sammy" value="123" />
</bean>
```

The `something` bean has a `fred` property, which has a `bob` property, which has a `sammy` property, and that final `sammy` property is being set to a value of `123`. In order for this to work, the `fred` property of `something` and the `bob` property of `fred` must not be `null` after the bean is constructed. Otherwise, a `NullPointerException` is thrown.

something bean有一个fred属性，它有一个bob属性，还有一个sammy属性，最后一个sammy属性被设置为123。为了使其工作，在构建bean之后，something的fred属性和fred的bob属性不能为null。否则，将引发NullPointerException。

##### 1.4.3. Using `depends-on`

If a bean is a dependency of another bean, that usually means that one bean is set as a property of another. Typically you accomplish this with the [`` element](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-ref-element) in XML-based configuration metadata. However, sometimes dependencies between beans are less direct. An example is when a static initializer in a class needs to be triggered, such as for database driver registration. The `depends-on` attribute can explicitly force one or more beans to be initialized before the bean using this element is initialized. The following example uses the `depends-on` attribute to express a dependency on a single bean:

如果一个bean是另一个bean的依赖项，这通常意味着一个bean被设置为另一个bean的属性。通常使用基于xml的配置元数据中的<ref/>元素来完成此任务。然而，有时bean之间的依赖关系并不那么直接。例如，需要触发类中的静态初始化器时，例如数据库驱动程序注册时。depends-on属性可以显式地强制在初始化使用此元素的bean之前初始化一个或多个bean。下面的示例使用depends-on属性来表示对单个对象的依赖:

```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
<bean id="manager" class="ManagerBean" />
```

To express a dependency on multiple beans, supply a list of bean names as the value of the `depends-on` attribute (commas, whitespace, and semicolons are valid delimiters):

要表达对多个bean的依赖关系，请提供一个bean名称列表作为depends-on属性的值（逗号、空格和分号是有效的分隔符）：

```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>

<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```

|      | The `depends-on` attribute can specify both an initialization-time dependency and, in the case of [singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton) beans only, a corresponding destruction-time dependency. Dependent beans that define a `depends-on` relationship with a given bean are destroyed first, prior to the given bean itself being destroyed. Thus, `depends-on` can also control shutdown order. |
| ---- | ------------------------------------------------------------ |
|      | depends-on属性既可以指定初始化时的依赖项，也可以指定对应的销毁时依赖项(仅在单例bean中)。在销毁给定bean本身之前，首先销毁与给定bean定义依赖关系的依赖bean。因此，依赖还可以控制关机顺序。 |

##### 1.4.4. Lazy-initialized Beans延迟初始化

By default, `ApplicationContext` implementations eagerly create and configure all [singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton) beans as part of the initialization process. Generally, this pre-instantiation is desirable, because errors in the configuration or surrounding environment are discovered immediately, as opposed to hours or even days later. When this behavior is not desirable, you can prevent pre-instantiation of a singleton bean by marking the bean definition as being lazy-initialized. A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.

默认情况下，ApplicationContext实现在初始化过程中急切地创建和配置所有单例bean。一般来说，这种预实例化是可取的，因为配置或周围环境中的错误会立即被发现，而不是几个小时甚至几天后。当这种行为不可取时，可以通过将bean定义标记为延迟初始化来防止单例bean的预实例化。一个延迟初始化的bean告诉IoC容器在第一次被请求时而不是在启动时创建一个bean实例。

In XML, this behavior is controlled by the `lazy-init` attribute on the `<bean/>` element, as the following example shows:

在XML中，此行为由<bean/>元素上的lazy init属性控制，如下例所示：

```xml
<bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>
<bean name="not.lazy" class="com.something.AnotherBean"/>
```

When the preceding configuration is consumed by an `ApplicationContext`, the `lazy` bean is not eagerly pre-instantiated when the `ApplicationContext` starts, whereas the `not.lazy` bean is eagerly pre-instantiated.

当前面的配置被ApplicationContext使用时，`lazy `bean不会在ApplicationContext启动时预先实例化，而`not.lazy`bean被急切地预先实例化。

However, when a lazy-initialized bean is a dependency of a singleton bean that is not lazy-initialized, the `ApplicationContext` creates the lazy-initialized bean at startup, because it must satisfy the singleton’s dependencies. The lazy-initialized bean is injected into a singleton bean elsewhere that is not lazy-initialized.

但是，当一个延迟初始化的bean作为不是延迟初始化的singleton bean的依赖项时，ApplicationContext会在启动时创建 lazy initialized bean，因为它必须满足singleton的依赖关系。惰性初始化的bean被注入到非惰性初始化的其他地方的单例bean中。

You can also control lazy-initialization at the container level by using the `default-lazy-init` attribute on the `<beans/>` element, as the following example shows:

还可以通过使用<beans/>元素上的 `default-lazy-init`属性在容器级别控制lazy初始化，如下例所示：

```xml
<beans default-lazy-init="true">
    <!-- no beans will be pre-instantiated... -->
</beans>
```

##### 1.4.5. Autowiring Collaborators  自动装配合作者

The Spring container can autowire relationships between collaborating beans. You can let Spring resolve collaborators (other beans) automatically for your bean by inspecting the contents of the `ApplicationContext`. Autowiring has the following advantages:

Spring容器可以自动装配协作bean之间的关系。通过检查ApplicationContext的内容，可以让Spring为bean自动解析协作者（其他bean）。自动装配具有以下优点：

- Autowiring can significantly reduce the need to specify properties or constructor arguments. (Other mechanisms such as a bean template [discussed elsewhere in this chapter](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-child-bean-definitions) are also valuable in this regard.)
- 自动装配可以显著减少指定属性或构造函数参数的需要。（本章其他部分讨论的其他机制（如bean模板）在这方面也很有价值。）
- Autowiring can update a configuration as your objects evolve. For example, if you need to add a dependency to a class, that dependency can be satisfied automatically without you needing to modify the configuration. Thus autowiring can be especially useful during development, without negating the option of switching to explicit wiring when the code base becomes more stable.
- 自动装配可以随着对象的发展而更新配置。例如，如果需要向类添加依赖项，则无需修改配置即可自动满足该依赖项。因此，在开发过程中，autowiring 尤其有用，而不必在代码库变得更稳定时切换到显式连接。

When using XML-based configuration metadata (see [Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators)), you can specify the autowire mode for a bean definition with the `autowire` attribute of the `<bean/>` element. The autowiring functionality has four modes. You specify autowiring per bean and can thus choose which ones to autowire. The following table describes the four autowiring modes:

当使用基于XML的配置元数据（请参见依赖注入）时，可以使用<bean/>元素的autowire属性为bean定义指定autowire模式。自动装配功能有四种模式。您可以为每个bean指定自动装配，因此可以选择要自动装配的bean。下表介绍了四种自动装配模式:

| Mode          | Explanation                                                  |
| :------------ | :----------------------------------------------------------- |
| `no`          | (Default) No autowiring. Bean references must be defined by `ref` elements. Changing the default setting is not recommended for larger deployments, because specifying collaborators explicitly gives greater control and clarity. To some extent, it documents the structure of a system. |
|               | （默认）无自动装配。Bean引用必须由ref元素定义。对于较大的部署，不建议更改默认设置，因为显式指定协作者可以提供更好的控制和清晰度。在某种程度上，它记录了一个系统的结构。 |
| `byName`      | Autowiring by property name. Spring looks for a bean with the same name as the property that needs to be autowired. For example, if a bean definition is set to autowire by name and it contains a `master` property (that is, it has a `setMaster(..)` method), Spring looks for a bean definition named `master` and uses it to set the property. |
|               | 按特性名称自动装配。Spring寻找一个与需要自动装配的属性同名的bean。例如，如果一个bean定义被设置为autowire by name，并且它包含一个master属性（也就是说，它有一个setMaster（..）方法），那么Spring会查找名为master的bean定义并使用它来设置属性。 |
| `byType`      | Lets a property be autowired if exactly one bean of the property type exists in the container. If more than one exists, a fatal exception is thrown, which indicates that you may not use `byType` autowiring for that bean. If there are no matching beans, nothing happens (the property is not set). |
|               | 如果容器中恰好存在该属性类型的一个bean，则允许该属性自动实现。如果存在多个，就会抛出一个致命异常，这表明您不能对该bean使用byType自动装配。如果没有匹配的bean，则什么也不会发生(没有设置属性)。 |
| `constructor` | Analogous to `byType` but applies to constructor arguments. If there is not exactly one bean of the constructor argument type in the container, a fatal error is raised. |
|               | 类似于byType，但适用于构造函数参数。如果容器中没有构造函数参数类型的确切bean，就会引发致命错误。 |

With `byType` or `constructor` autowiring mode, you can wire arrays and typed collections. In such cases, all autowire candidates within the container that match the expected type are provided to satisfy the dependency. You can autowire strongly-typed `Map` instances if the expected key type is `String`. An autowired `Map` instance’s values consist of all bean instances that match the expected type, and the `Map` instance’s keys contain the corresponding bean names.

使用byType或构造函数自动装配模式，您可以连接数组和类型化集合。在这种情况下，提供容器中与预期类型匹配的所有自动装配候选对象来满足依赖关系。如果期望的键类型是String，您可以自动连接强类型映射实例。自动生成的映射实例的值由与预期类型匹配的所有bean实例组成，映射实例的键包含相应的bean名称。

###### Limitations and Disadvantages of Autowiring 自动装配的局限性和缺点

Autowiring works best when it is used consistently across a project. If autowiring is not used in general, it might be confusing to developers to use it to wire only one or two bean definitions.

自动装配在项目中一致使用时工作得最好。如果自动装配没有被普遍使用，那么使用它来连接一个或两个bean定义可能会使开发人员感到困惑。

Consider the limitations and disadvantages of autowiring:

考虑自动装配的局限性和缺点:

- Explicit dependencies in `property` and `constructor-arg` settings always override autowiring. You cannot autowire simple properties such as primitives, `Strings`, and `Classes` (and arrays of such simple properties). This limitation is by-design.
- 属性和构造参数设置中的显式依赖关系总是覆盖自动装配。您不能自动连接简单属性，如原语、字符串和类(以及此类简单属性的数组)。这种限制是设计出来的。
- Autowiring is less exact than explicit wiring. Although, as noted in the earlier table, Spring is careful to avoid guessing in case of ambiguity that might have unexpected results. The relationships between your Spring-managed objects are no longer documented explicitly.
- 自动装配不如显式装配精确。不过，正如前面的表中所指出的，Spring小心地避免猜测可能会产生意外结果的歧义。spring管理对象之间的关系不再被明确地记录。
- Wiring information may not be available to tools that may generate documentation from a Spring container.
- 连接信息对于可能从Spring容器生成文档的工具来说可能是不可用的。
- Multiple bean definitions within the container may match the type specified by the setter method or constructor argument to be autowired. For arrays, collections, or `Map` instances, this is not necessarily a problem. However, for dependencies that expect a single value, this ambiguity is not arbitrarily resolved. If no unique bean definition is available, an exception is thrown.
- 容器中的多个bean定义可能与要自动实现的setter方法或构造函数参数指定的类型相匹配。对于数组、集合或映射实例，这不一定是个问题。但是，对于期望使用单个值的依赖项，这种模糊性不能任意解决。如果没有可用的唯一bean定义，则抛出异常。

In the latter scenario, you have several options:

在后一种情况下，您有几个选项:

- Abandon autowiring in favor of explicit wiring.
- 放弃自动装配，支持显式。
- Avoid autowiring for a bean definition by setting its `autowire-candidate` attributes to `false`, as described in the [next section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire-candidate).
- 通过将一个bean定义的自动装配候选属性设置为false来避免自动装配，如下一节所述。
- Designate a single bean definition as the primary candidate by setting the `primary` attribute of its `<bean/>` element to `true`.
- 通过将单个bean定义的<bean/>元素的`primary` 属性设置为true，将其指定为主候选bean定义。
- Implement the more fine-grained control available with annotation-based configuration, as described in [Annotation-based Container Configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config).
- 使用基于注释的配置实现更细粒度的控制，如在基于注释的容器配置中所述。

###### Excluding a Bean from Autowiring 不包括自动装配的bean

On a per-bean basis, you can exclude a bean from autowiring. In Spring’s XML format, set the `autowire-candidate` attribute of the `<bean/>` element to `false`. The container makes that specific bean definition unavailable to the autowiring infrastructure (including annotation style configurations such as [`@Autowired`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation)).

在每个bean的基础上，您可以从自动装配中排除一个bean。在Spring的XML格式中，将<bean/>元素的自动装配`autowire-candidate`属性设置为false。容器使得特定的bean定义对自动装配基础设施不可用(包括注释风格配置，如@Autowired)。

|      | The `autowire-candidate` attribute is designed to only affect type-based autowiring. It does not affect explicit references by name, which get resolved even if the specified bean is not marked as an autowire candidate. As a consequence, autowiring by name nevertheless injects a bean if the name matches. |
| ---- | ------------------------------------------------------------ |
|      | autowire-candidate属性被设计为只影响基于类型的自动装配。它不影响按名称的显式引用，即使指定的bean没有标记为自动装配(`autowire-candidate=false`)，也会解析显式引用。因此，如果名称匹配，按名称自动装配仍然会注入一个bean。 |

You can also limit autowire candidates based on pattern-matching against bean names. The top-level `<beans/>` element accepts one or more patterns within its `default-autowire-candidates` attribute. For example, to limit autowire candidate status to any bean whose name ends with `Repository`, provide a value of `*Repository`. To provide multiple patterns, define them in a comma-separated list. An explicit value of `true` or `false` for a bean definition’s `autowire-candidate` attribute always takes precedence. For such beans, the pattern matching rules do not apply.

您还可以基于对bean名称的模式匹配来限制自动连接候选项。顶级的<beans/>元素在其`default-autowire-candidates`属性中接受一个或多个模式。例如，要将自动装配候选状态限制为名称以Repository结尾的任何bean，可以提供一个值*Repository。要提供多个模式，请在逗号分隔的列表中定义它们。bean定义的自动候选属性的显式值true或false总是优先(高于容器级别的default-autowire-candidates)。对于这样的bean，模式匹配规则不适用。

These techniques are useful for beans that you never want to be injected into other beans by autowiring. It does not mean that an excluded bean cannot itself be configured by using autowiring. Rather, the bean itself is not a candidate for autowiring other beans.

这些技术对于那些您永远不希望通过自动装配被注入到其他bean中的bean非常有用。这并不意味着被排除的bean本身不能使用自动装配进行配置。相反，该bean本身不是自动装配其他bean的候选对象。

##### 1.4.6. Method Injection 方法注入

In most application scenarios, most beans in the container are [singletons](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton). When a singleton bean needs to collaborate with another singleton bean or a non-singleton bean needs to collaborate with another non-singleton bean, you typically handle the dependency by defining one bean as a property of the other. A problem arises when the bean lifecycles are different. Suppose singleton bean A needs to use non-singleton (prototype) bean B, perhaps on each method invocation on A. The container creates the singleton bean A only once, and thus only gets one opportunity to set the properties. The container cannot provide bean A with a new instance of bean B every time one is needed.

在大多数应用程序场景中，容器中的大多数bean都是单例的。当一个单例bean需要与另一个单例bean协作时，或者一个非单例bean需要与另一个非单例bean协作时，通常通过将一个bean定义为另一个bean的属性来处理依赖关系。当bean的生命周期不同时，问题就出现了。假设单例bean A需要使用非单例(prototype)bean B，可能在对A的每次方法调用上都是如此，容器只创建一次单例bean A，因此只获得一次设置属性的机会。容器不能在每次需要bean B的时候都向bean A提供一个新的实例。

A solution is to forego some inversion of control. You can [make bean A aware of the container](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware) by implementing the `ApplicationContextAware` interface, and by [making a `getBean("B")` call to the container](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-client) ask for (a typically new) bean B instance every time bean A needs it. The following example shows this approach:

一个解决办法是放弃一些控制反转。您可以通过实现`ApplicationContextAware`接口，以及在每次bean A需要bean B实例时，通过对容器进行getBean(“B”)调用来让bean A知道容器。下面的例子展示了这种方法:

```java
// a class that uses a stateful Command-style class to perform some processing
package fiona.apple;

// Spring-API imports
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CommandManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Object process(Map commandState) {
        // grab a new instance of the appropriate Command
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    protected Command createCommand() {
        // notice the Spring API dependency!
        return this.applicationContext.getBean("command", Command.class);
    }

    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```

The preceding is not desirable, because the business code is aware of and coupled to the Spring Framework. Method Injection, a somewhat advanced feature of the Spring IoC container, lets you handle this use case cleanly.

前面的方法并不可取，因为业务代码知道Spring框架并与之耦合。方法注入是Spring IoC容器的一种高级特性，它允许您干净地处理这个用例。

You can read more about the motivation for Method Injection in [this blog entry](https://spring.io/blog/2004/08/06/method-injection/).

您可以在这篇博客文章中阅读更多关于方法注入动机的内容。

###### Lookup Method Injection查找方法注入

Lookup method injection is the ability of the container to override methods on container-managed beans and return the lookup result for another named bean in the container. The lookup typically involves a prototype bean, as in the scenario described in [the preceding section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-method-injection). The Spring Framework implements this method injection by using bytecode generation from the CGLIB library to dynamically generate a subclass that overrides the method.

查找方法注入是指容器覆盖容器管理bean上的方法并返回容器中另一个已命名bean的查找结果的能力。查找通常涉及prototype bean，如上一节所述的场景。Spring框架通过使用来自CGLIB库的字节码生成动态生成覆盖该方法的子类来实现这种方法注入。

|      | For this dynamic subclassing to work, the class that the Spring bean container subclasses cannot be `final`, and the method to be overridden cannot be `final`, either. |
| ---- | ------------------------------------------------------------ |
|      | 要使这个动态子类工作，Spring bean容器子类的类不能是final，要覆盖的方法也不能是final。 |
|      | Unit-testing a class that has an `abstract` method requires you to subclass the class yourself and to supply a stub implementation of the `abstract` method. |
|      | 单元测试具有抽象方法的类需要您自己创建类的子类，并提供抽象方法的存根实现。 |
|      | Concrete methods are also necessary for component scanning, which requires concrete classes to pick up. |
|      | 具体的方法对于组件扫描也是必要的，这需要具体的类来拾取。     |
|      | A further key limitation is that lookup methods do not work with factory methods and in particular not with `@Bean` methods in configuration classes, since, in that case, the container is not in charge of creating the instance and therefore cannot create a runtime-generated subclass on the fly. |
|      | 另一个关键的限制是，查找方法不能与工厂方法一起工作，特别是不与配置类中的@Bean方法一起工作，因为在这种情况下，容器不负责创建实例，因此不能动态地创建运行时生成的子类。 |
|      |                                                              |

In the case of the `CommandManager` class in the previous code snippet, the Spring container dynamically overrides the implementation of the `createCommand()` method. The `CommandManager` class does not have any Spring dependencies, as the reworked example shows:

对于前面代码片段中的CommandManager类，Spring容器动态覆盖createCommand()方法的实现。CommandManager类没有任何Spring依赖项，正如修改后的示例所示:

```java
package fiona.apple;

// no more Spring imports!

public abstract class CommandManager {

    public Object process(Object commandState) {
        // grab a new instance of the appropriate Command interface
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    // okay... but where is the implementation of this method?
    protected abstract Command createCommand();
}
```

In the client class that contains the method to be injected (the `CommandManager` in this case), the method to be injected requires a signature of the following form:

在包含要注入的方法的客户端类中(本例为CommandManager)，要注入的方法需要以下形式的签名:

```xml
<public|protected> [abstract] <return-type> theMethodName(no-arguments);
```

If the method is `abstract`, the dynamically-generated subclass implements the method. Otherwise, the dynamically-generated subclass overrides the concrete method defined in the original class. Consider the following example:

如果方法是抽象的，则动态生成的子类实现该方法。否则，动态生成的子类将覆盖在原始类中定义的具体方法。考虑下面的例子:

```xml
<!-- a stateful bean deployed as a prototype (non-singleton) -->
<bean id="myCommand" class="fiona.apple.AsyncCommand" scope="prototype">
    <!-- inject dependencies here as required -->
</bean>

<!-- commandProcessor uses statefulCommandHelper -->
<bean id="commandManager" class="fiona.apple.CommandManager">
    <lookup-method name="createCommand" bean="myCommand"/>
</bean>
```

The bean identified as `commandManager` calls its own `createCommand()` method whenever it needs a new instance of the `myCommand` bean. You must be careful to deploy the `myCommand` bean as a prototype if that is actually what is needed. If it is a [singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton), the same instance of the `myCommand` bean is returned each time.

标识为`commandManager`的bean在需要`myCommand` bean的新实例时调用它自己的`createCommand()`方法。如果真的需要将`myCommand` bean部署为prototype ，则必须小心。如果是单例，则每次都返回相同的`myCommand` bean实例。

Alternatively, within the annotation-based component model, you can declare a lookup method through the `@Lookup` annotation, as the following example shows:

或者，在基于注释的组件模型中，您可以通过@Lookup注释声明一个查找方法，如下面的示例所示:

```java
public abstract class CommandManager {

    public Object process(Object commandState) {
        Command command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    @Lookup("myCommand")
    protected abstract Command createCommand();
}
```

Or, more idiomatically, you can rely on the target bean getting resolved against the declared return type of the lookup method:

或者，更习惯的做法是，您可以依赖于根据查找方法声明的`返回类型`解析目标bean:

```java
public abstract class CommandManager {

    public Object process(Object commandState) {
        MyCommand command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    @Lookup(注意这里在spring扫描规则时会失效，最好声明具体实现)
    protected abstract MyCommand createCommand();
}
```

Note that you should typically declare such annotated lookup methods with a concrete stub implementation, in order for them to be compatible with Spring’s component scanning rules where abstract classes get ignored by default. This limitation does not apply to explicitly registered or explicitly imported bean classes.

请注意，您通常应该使用具体的存根实现来声明这种带注释的查找方法，以便它们与Spring的组件扫描规则兼容，其中抽象类在默认情况下会被忽略。此限制不适用于显式注册或显式导入的bean类。

|      | Another way of accessing differently scoped target beans is an `ObjectFactory`/ `Provider` injection point. See [Scoped Beans as Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection).You may also find the `ServiceLocatorFactoryBean` (in the `org.springframework.beans.factory.config` package) to be useful. |
| ---- | ------------------------------------------------------------ |
|      | 访问作用域不同的目标bean的另一种方法是`ObjectFactory/ Provider`注入点。请将作用域bean视为依赖项。您还可以在`org.springframework.beans.factory package`中找到`ServiceLocatorFactoryBean`)是有用的 |

###### Arbitrary Method Replacement 任意的方法替换

A less useful form of method injection than lookup method injection is the ability to replace arbitrary methods in a managed bean with another method implementation. You can safely skip the rest of this section until you actually need this functionality.

方法注入的一种不如`查找方法注入`有用的形式是用另一个方法实现替换托管bean中的任意方法的能力。您可以安全地跳过本节的其余部分，直到您真正需要此功能。

With XML-based configuration metadata, you can use the `replaced-method` element to replace an existing method implementation with another, for a deployed bean. Consider the following class, which has a method called `computeValue` that we want to override:

对于基于xml的配置元数据，您可以使用`replaced-method`元素将一个已部署bean的现有方法实现替换为另一个方法实现。考虑下面的类，它有一个名为`computeValue`的方法，我们想要覆盖它:

```java
public class MyValueCalculator {

    public String computeValue(String input) {
        // some real code...
    }

    // some other methods...
}
```

A class that implements the `org.springframework.beans.factory.support.MethodReplacer` interface provides the new method definition, as the following example shows:

实现`org.springframework.bean .factory.support.MethodReplacer`接口提供了新的方法定义的类，如下面的示例所示:

```java
/**
 * meant to be used to override the existing computeValue(String)
 * implementation in MyValueCalculator
 */
public class ReplacementComputeValue implements MethodReplacer {

    public Object reimplement(Object o, Method m, Object[] args) throws Throwable {
        // get the input value, work with it, and return a computed result
        String input = (String) args[0];
        ...
        return ...;
    }
}
```

The bean definition to deploy the original class and specify the method override would resemble the following example:

部署原始类并指定方法覆盖的bean定义类似于下面的示例:

```xml
<bean id="myValueCalculator" class="x.y.z.MyValueCalculator">
    <!-- arbitrary method replacement -->
    <replaced-method name="computeValue" replacer="replacementComputeValue">
        <arg-type>String</arg-type>
    </replaced-method>
</bean>

<bean id="replacementComputeValue" class="a.b.c.ReplacementComputeValue"/>
```

You can use one or more `<arg-type/>` elements within the `<replaced-method/>` element to indicate the method signature of the method being overridden. The signature for the arguments is necessary only if the method is overloaded and multiple variants exist within the class. For convenience, the type string for an argument may be a substring of the fully qualified type name. For example, the following all match `java.lang.String`:

可以使用`< replacement -method/>`元素中的一个或多个`< argt-ype />`元素来指示被重写的方法的方法签名。只有当方法被重载并且类中存在多个变体时，参数的签名才有必要。为了方便，参数的类型字符串可以是完全限定类型名的子字符串。例如，以下所有匹配

```java
java.lang.String
String
Str
```

Because the number of arguments is often enough to distinguish between each possible choice, this shortcut can save a lot of typing, by letting you type only the shortest string that matches an argument type.

由于参数的数量通常足以区分每种可能的选择，因此通过只输入与参数类型匹配的最短字符串，此快捷方式可以节省大量输入。

#### 1.5. Bean Scopes Bean作用域

When you create a bean definition, you create a recipe for creating actual instances of the class defined by that bean definition. The idea that a bean definition is a recipe is important, because it means that, as with a class, you can create many object instances from a single recipe.

当您创建一个bean定义时，您将创建一个用于创建由该`bean定义`定义的类的实际实例的方法。`bean定义`是一个`配方`的想法很重要，因为这意味着，与类一样，您可以从单个配方创建多个对象实例。

You can control not only the various dependencies and configuration values that are to be plugged into an object that is created from a particular bean definition but also control the scope of the objects created from a particular bean definition. This approach is powerful and flexible, because you can choose the scope of the objects you create through configuration instead of having to bake in the scope of an object at the Java class level. Beans can be defined to be deployed in one of a number of scopes. The Spring Framework supports six scopes, four of which are available only if you use a web-aware `ApplicationContext`. You can also create [a custom scope.](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-custom)

您不仅可以控制要插入到从特定bean定义创建的对象中的各种依赖关系`(dependencies )`和配置值`( configuration values)`，还可以控制从特定bean定义创建的对象的`scope `。这种方法功能强大且灵活，因为您可以选择通过配置创建的对象的`scope `，而不必在Java类级别烘焙对象的`scope `。可以将bean定义为部署在多个作用域中的一个。Spring框架支持6个作用域，其中4个作用域仅在使用支持web的`ApplicationContext`时可用。也可以创建自定义`scope`。

The following table describes the supported scopes:

| Scope                                                        | Description                                                  |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-singleton) | (Default) Scopes a single bean definition to a single object instance for each Spring IoC container. |
| 单列                                                         | （默认）将单个bean定义的范围限定为每个springioc容器的单个对象实例。 |
| [prototype](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-prototype) | Scopes a single bean definition to any number of object instances. |
| 原型                                                         | 将单个bean定义的范围限定为任意数量的对象实例。               |
| [request](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-request) | Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring `ApplicationContext`. |
|                                                              | 将单个bean定义限定为单个HTTP请求的生命周期。也就是说，每个HTTP请求都有一个在单个bean定义后面创建的bean实例。仅在支持web的Spring应用程序上下文（`ApplicationContext`）中有效。(是单列的，bean实例只有一个) |
| [session](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-session) | Scopes a single bean definition to the lifecycle of an HTTP `Session`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
|                                                              | 将单个bean定义限定为HTTP`会话`的生命周期。仅在支持web的Spring应用程序上下文(`ApplicationContext`)中有效。 |
| [application](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-application) | Scopes a single bean definition to the lifecycle of a `ServletContext`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
|                                                              | 将单个bean定义限定为`ServletContext`的生命周期。仅在支持web的Spring应用程序上下文中有效。 |
| [websocket](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-websocket-scope) | Scopes a single bean definition to the lifecycle of a `WebSocket`. Only valid in the context of a web-aware Spring `ApplicationContext`. |
|                                                              | 将单个bean定义限定为`WebSocket`的生命周期。仅在支持web的Spring`ApplicationContext`中有效。 |

As of Spring 3.0, a thread scope is available but is not registered by default. For more information, see the documentation for [`SimpleThreadScope`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/SimpleThreadScope.html). For instructions on how to register this or any other custom scope, see [Using a Custom Scope](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-custom-using).

从spring3.0开始，线程范围是可用的，但默认情况下不注册。有关更多信息，请参阅`SimpleThreadScope`的文档。有关如何注册此自定义范围或任何其他自定义范围的说明，请参阅使`用自定义范围(Using a Custom Scope)`。

##### 1.5.1. The Singleton Scope (单列域)

Only one shared instance of a singleton bean is managed, and all requests for beans with an ID or IDs that match that bean definition result in that one specific bean instance being returned by the Spring container.

一个单例bean只有一个共享实例是被管理的，所有对具有与该bean定义相匹配的ID的bean的请求都会导致Spring容器返回一个特定的bean实例。

To put it another way, when you define a bean definition and it is scoped as a singleton, the Spring IoC container creates exactly one instance of the object defined by that bean definition. This single instance is stored in a cache of such singleton beans, and all subsequent requests and references for that named bean return the cached object. The following image shows how the singleton scope works:

换句话说，当您定义一个bean定义并将其限定为一个singleton时，springioc容器将创建由该bean定义定义的对象的一个实例。这个单个实例存储在这样的单例bean的缓存中，并且该命名bean的所有后续请求和引用都返回缓存的对象。下图显示了singleton作用域的工作方式：

![singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/images/singleton.png)

Spring’s concept of a singleton bean differs from the singleton pattern as defined in the Gang of Four (GoF) patterns book. The GoF singleton hard-codes the scope of an object such that one and only one instance of a particular class is created per ClassLoader. The scope of the Spring singleton is best described as being per-container and per-bean. This means that, if you define one bean for a particular class in a single Spring container, the Spring container creates one and only one instance of the class defined by that bean definition. The singleton scope is the default scope in Spring. To define a bean as a singleton in XML, you can define a bean as shown in the following example:

Spring的singleton bean概念不同于`gang of four（GoF）`patterns一书中定义的singleton模式。GoF singleton硬编码对象的范围,每个类加载器只创建一个特定类的实例。`spring singleton`的范围最好描述为每个容器和每个bean(bean在容器中是唯一的就行)。这意味着，如果您在单个Spring容器中为特定类定义一个bean，那么Spring容器将创建一个且仅一个由该bean定义定义的类的实例。singleton作用域是Spring中的默认作用域。要在XML中将bean定义为单例，可以定义bean，如下例所示：

```xml
<bean id="accountService" class="com.something.DefaultAccountService"/>

<!-- the following is equivalent, though redundant (singleton scope is the default) -->
<bean id="accountService" class="com.something.DefaultAccountService" scope="singleton"/>
```

1.5.2. The Prototype Scope （原型范围）

The non-singleton prototype scope of bean deployment results in the creation of a new bean instance every time a request for that specific bean is made. That is, the bean is injected into another bean or you request it through a `getBean()` method call on the container. As a rule, you should use the prototype scope for all stateful beans and the singleton scope for stateless beans.

bean部署的非单例原型范围导致每次对特定bean发出请求时都会创建一个新的bean实例。也就是说，该bean被注入到另一个bean中，或者您通过对容器的getBean（）方法调用来请求它。通常，您应该为所有有状态bean使用prototype范围，而为无状态bean使用singleton范围。

The following diagram illustrates the Spring prototype scope:

![prototype](https://docs.spring.io/spring-framework/docs/current/reference/html/images/prototype.png)

(A data access object (DAO) is not typically configured as a prototype, because a typical DAO does not hold any conversational state. It was easier for us to reuse the core of the singleton diagram.)

（数据访问对象`（DAO）`通常不配置为原型，因为典型的DAO不具有任何会话状态。我们更容易重用singleton图的核心。）

The following example defines a bean as a prototype in XML:

以下示例将bean定义为XML中的原型：

```xml
<bean id="accountService" class="com.something.DefaultAccountService" scope="prototype"/>
```

In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean. The container instantiates, configures, and otherwise assembles a prototype object and hands it to the client, with no further record of that prototype instance. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured destruction lifecycle callbacks are not called. The client code must clean up prototype-scoped objects and release expensive resources that the prototype beans hold. To get the Spring container to release resources held by prototype-scoped beans, try using a custom [bean post-processor](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-bpp), which holds a reference to beans that need to be cleaned up.

与其他作用域不同，`Spring不管理原型bean的完整生命周期`。容器实例化、配置和组装一个原型对象并将其交给客户机，而不需要进一步记录该原型实例。因此，尽管初始化生命周期回调方法在所有对象上都被调用，而不管范围如何，对于原型，配置的销毁生命周期回调不会被调用。`客户机代码必须清理原型范围内的对象，并释放原型bean所持有的昂贵资源`。为了让Spring容器释放原型作用域bean所包含的资源，请尝试使用一个自定义`bean后处理程序`，它保存了对需要清理的bean的引用。

In some respects, the Spring container’s role in regard to a prototype-scoped bean is a replacement for the Java `new` operator. All lifecycle management past that point must be handled by the client. (For details on the lifecycle of a bean in the Spring container, see [Lifecycle Callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle).)

在某些方面，Spring容器在`原型范围bean` 中的角色是java `new`操作符的替代品。超过该点的所有生命周期管理都必须由客户端处理。（有关Spring容器中bean生命周期的详细信息，请参阅生命周期回调）

##### 1.5.3. Singleton Beans with Prototype-bean Dependencies

具有原型bean依赖关系的单例bean

When you use singleton-scoped beans with dependencies on prototype beans, be aware that dependencies are resolved at instantiation time. Thus, if you dependency-inject a prototype-scoped bean into a singleton-scoped bean, a new prototype bean is instantiated and then dependency-injected into the singleton bean. The prototype instance is the sole instance that is ever supplied to the singleton-scoped bean.

当您使用单例范围的bean时，要注意依赖关系是在实例化时解析的。因此，如果依赖关系将原型范围的bean注入到单例范围的bean中，则会实例化一个新的原型bean，然后将依赖关系注入到单例bean中。原型实例是提供给单例范围bean的唯一实例。

However, suppose you want the singleton-scoped bean to acquire a new instance of the prototype-scoped bean repeatedly at runtime. You cannot dependency-inject a prototype-scoped bean into your singleton bean, because that injection occurs only once, when the Spring container instantiates the singleton bean and resolves and injects its dependencies. If you need a new instance of a prototype bean at runtime more than once, see [Method Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-method-injection)

但是，假设您希望singleton范围的bean在运行时反复获取原型范围bean的新实例。不能将原型范围的bean通过依赖关系注入到singleton bean中，因为这种注入只发生一次，当Spring容器实例化singleton bean并解析和注入它的依赖项时。如果在运行时多次需要原型bean的新实例，请参见方法注入

##### 1.5.4. Request, Session, Application, and WebSocket Scopes

请求、会话、应用程序和WebSocket作用域

The `request`, `session`, `application`, and `websocket` scopes are available only if you use a web-aware Spring `ApplicationContext` implementation (such as `XmlWebApplicationContext`). If you use these scopes with regular Spring IoC containers, such as the `ClassPathXmlApplicationContext`, an `IllegalStateException` that complains about an unknown bean scope is thrown.

只有使用支持web的`Spring ApplicationContext`实现（如`XmlWebApplicationContext`）， `request`, `session`, `application`, and `websocket` 作用域才可用。如果您将这些作用域与常规springioc容器（例如`ClassPathXmlApplicationContext`）一起使用，则会抛出一个`illeglStasteException`，该异常抱怨未知的bean作用域。

##### Initial Web Configuration

初始Web配置

To support the scoping of beans at the `request`, `session`, `application`, and `websocket` levels (web-scoped beans), some minor initial configuration is required before you define your beans. (This initial setup is not required for the standard scopes: `singleton` and `prototype`.)

为了在`request`, `session`, `application`, and `websocket`级别（web范围的bean）支持bean的作用域，在定义bean之前，需要一些小的初始配置。（对于标准作用域：singleton和prototype，不需要这种初始设置。）

How you accomplish this initial setup depends on your particular Servlet environment.

如何完成初始设置取决于特定的Servlet环境。

If you access scoped beans within Spring Web MVC, in effect, within a request that is processed by the Spring `DispatcherServlet`, no special setup is necessary. `DispatcherServlet` already exposes all relevant state.

如果您在`spring web mvc`中访问作用域bean，实际上是在spring `dispatcherservlet`处理的请求中，那么不需要进行特殊设置。`DispatcherServlet`已公开所有相关状态。

If you use a Servlet 2.5 web container, with requests processed outside of Spring’s `DispatcherServlet` (for example, when using JSF or Struts), you need to register the `org.springframework.web.context.request.RequestContextListener` `ServletRequestListener`. For Servlet 3.0+, this can be done programmatically by using the `WebApplicationInitializer` interface. Alternatively, or for older containers, add the following declaration to your web application’s `web.xml` file:

如果您使用`Servlet2.5`Web容器，并在Spring的`DispatcherServlet`之外处理请求（例如，在使用`JSF`或`Struts`时），则需要注册`org.springframework.web.context.request.RequestContextListener` `ServletRequestListener`。对于`Servlet3.0+`，这可以通过使用`WebApplicationInitializer`接口以编程方式完成。或者，对于较旧的容器，将以下声明添加到web应用程序的`web.xml`文件文件：

```xml
<web-app>
    ...
    <listener>
        <listener-class>
            org.springframework.web.context.request.RequestContextListener
        </listener-class>
    </listener>
    ...
</web-app>
```

Alternatively, if there are issues with your listener setup, consider using Spring’s `RequestContextFilter`. The filter mapping depends on the surrounding web application configuration, so you have to change it as appropriate. The following listing shows the filter part of a web application:

或者，如果侦听器设置有问题，可以考虑使用Spring的`RequestContextFilter`。过滤器映射取决于周围的web应用程序配置，因此您必须根据需要对其进行更改。下表显示了web应用程序的过滤器部分：

```xml
<web-app>
    ...
    <filter>
        <filter-name>requestContextFilter</filter-name>
        <filter-class>org.springframework.web.filter.RequestContextFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>requestContextFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ...
</web-app>
```

`DispatcherServlet`, `RequestContextListener`, and `RequestContextFilter` all do exactly the same thing, namely bind the HTTP request object to the `Thread` that is servicing that request. This makes beans that are request- and session-scoped available further down the call chain.

`DispatcherServlet`、`RequestContextListener`和`RequestContextFilter`都执行相同的操作，即将`HTTP请求对象`绑定到为该请求提供服务的`线程`。这使得请求和会话作用域的bean在调用链的下一级可用。

##### Request scope

Consider the following XML configuration for a bean definition:

考虑一下bean定义的以下XML配置：

```xml
<bean id="loginAction" class="com.something.LoginAction" scope="request"/>
```

The Spring container creates a new instance of the `LoginAction` bean by using the `loginAction` bean definition for each and every HTTP request. That is, the `loginAction` bean is scoped at the HTTP request level. You can change the internal state of the instance that is created as much as you want, because other instances created from the same `loginAction` bean definition do not see these changes in state. They are particular to an individual request. When the request completes processing, the bean that is scoped to the request is discarded.

Spring容器通过为每个HTTP请求使用`loginaction`bean定义来创建`loginaction`bean的新实例。也就是说，`loginaction`bean的作用域是在HTTP请求级别。您可以随意更改创建的实例的内部状态，因为从同一个`LoginAction`Bean定义创建的其他实例在状态中看不到这些更改。它们是针对单个请求的。当请求完成处理时，将丢弃该请求范围内的bean。

When using annotation-driven components or Java configuration, the `@RequestScope` annotation can be used to assign a component to the `request` scope. The following example shows how to do so:

当使用注释驱动的组件或Java配置时，`@RequestScope`注释可用于将组件分配给 `request` scope。下面的示例演示如何执行此操作：

Java

```java
@RequestScope
@Component
public class LoginAction {
    // ...
}
```

##### Session Scope

Consider the following XML configuration for a bean definition:

```xml
<bean id="userPreferences" class="com.something.UserPreferences" scope="session"/>
```

The Spring container creates a new instance of the `UserPreferences` bean by using the `userPreferences` bean definition for the lifetime of a single HTTP `Session`. In other words, the `userPreferences` bean is effectively scoped at the HTTP `Session` level. As with request-scoped beans, you can change the internal state of the instance that is created as much as you want, knowing that other HTTP `Session` instances that are also using instances created from the same `userPreferences` bean definition do not see these changes in state, because they are particular to an individual HTTP `Session`. When the HTTP `Session` is eventually discarded, the bean that is scoped to that particular HTTP `Session` is also discarded.

Spring容器通过在单个HTTP会话的生命周期内使用`UserPreferences` bean定义来创建`UserPreferences` bean的新实例。换句话说，`userPreferences` bean在HTTP会话级别有效地限定了作用域。与请求作用域bean一样，您可以根据需要更改创建的实例的内部状态，因为其他HTTP会话实例也在使用从同一个`userPreferences` bean定义创建的实例，但不会看到这些状态更改，因为它们是特定于单个HTTP会话的。当HTTP会话最终被丢弃时，作用域为该特定HTTP会话的bean也将被丢弃。

When using annotation-driven components or Java configuration, you can use the `@SessionScope` annotation to assign a component to the `session` scope.

使用注释驱动的组件或Java配置时，可以使用`@SessionScope`注释将组件分配给`session` scope。

Java

```java
@SessionScope
@Component
public class UserPreferences {
    // ...
}
```

##### Application Scope

Consider the following XML configuration for a bean definition:

```xml
<bean id="appPreferences" class="com.something.AppPreferences" scope="application"/>
```

The Spring container creates a new instance of the `AppPreferences` bean by using the `appPreferences` bean definition once for the entire web application. That is, the `appPreferences` bean is scoped at the `ServletContext` level and stored as a regular `ServletContext` attribute. This is somewhat similar to a Spring singleton bean but differs in two important ways: It is a singleton per `ServletContext`, not per Spring 'ApplicationContext' (for which there may be several in any given web application), and it is actually exposed and therefore visible as a `ServletContext` attribute.

Spring容器通过对整个web应用程序使用一次`AppPreferences` bean定义来创建`AppPreferences `bean的新实例。也就是说，`appPreferences` bean的作用域在`ServletContext`级别，并存储为常规的`ServletContext`属性。这有点类似于Spring singleton bean，但在两个重要方面有所不同：它是每个`ServletContext`的单例，而不是每个Spring的“`ApplicationContext`”（在任何给定的web应用程序中可能有多个），它实际上是公开的，因此作为`ServletContext`属性可见。

When using annotation-driven components or Java configuration, you can use the `@ApplicationScope` annotation to assign a component to the `application` scope. The following example shows how to do so:

Java

```java
@ApplicationScope
@Component
public class AppPreferences {
    // ...
}
```

##### Scoped Beans as Dependencies

The Spring IoC container manages not only the instantiation of your objects (beans), but also the wiring up of collaborators (or dependencies). If you want to inject (for example) an HTTP request-scoped bean into another bean of a longer-lived scope, you may choose to inject an AOP proxy in place of the scoped bean. That is, you need to inject a proxy object that exposes the same public interface as the scoped object but that can also retrieve the real target object from the relevant scope (such as an HTTP request) and delegate method calls onto the real object.

springioc容器不仅管理对象（bean）的实例化，还管理协作者（或依赖项）的连接。如果您想（例如）将一个HTTP请求作用域的bean注入到另一个具有较长生存期的bean中，那么您可以选择注入一个AOP代理来代替作用域bean。也就是说，您需要注入一个代理对象，该对象公开与作用域对象相同的公共接口，但它也可以从相关的作用域（如一个HTTP请求）检索真实的目标对象，并将方法调用委托给实际对象。

You may also use `<aop:scoped-proxy/>` between beans that are scoped as `singleton`, with the reference then going through an intermediate proxy that is serializable and therefore able to re-obtain the target singleton bean on deserialization.

您也可以使用`<aop:scoped-proxy/>`在作用域为`singleton`的bean之间，引用通过可序列化的中间代理，因此能够在`反序列化`时重新获取目标`singleton` bean。

When declaring `<aop:scoped-proxy/>` against a bean of scope `prototype`, every method call on the shared proxy leads to the creation of a new target instance to which the call is then being forwarded.

申报`<aop:scoped-proxy/>`时对于作用域为`prototype`的bean，对共享代理的每个方法调用都会导致创建一个新的目标实例，然后将调用转发到该实例。

Also, scoped proxies are not the only way to access beans from shorter scopes in a lifecycle-safe fashion. You may also declare your injection point (that is, the constructor or setter argument or autowired field) as `ObjectFactory<MyTargetBean>`, allowing for a `getObject()` call to retrieve the current instance on demand every time it is needed — without holding on to the instance or storing it separately.

此外，作用域代理并不是以生命周期安全的方式从较短范围访问bean的唯一方法。您还可以将注入点（即构造函数或setter参数或`autowired`字段）声明为`ObjectFactory<MyTargetBean>`，允许每次需要时使用`getObject（）`调用来检索当前实例，而无需保留实例或单独存储它。

As an extended variant, you may declare `ObjectProvider<MyTargetBean>`, which delivers several additional access variants, including `getIfAvailable` and `getIfUnique`.

作为一个扩展变量，您可以声明`ObjectProvider<MyTargetBean>`，它提供了几个附加的访问变量，包括`getIfAvailable`和`getIfUnique`。

The JSR-330 variant of this is called `Provider` and is used with a `Provider<MyTargetBean>` declaration and a corresponding `get()` call for every retrieval attempt. See [here](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-standard-annotations) for more details on JSR-330 overall.

它的JSR-330变体称为`Provider`，并与`Provider<MyTargetBean>`声明和每次检索尝试对应的`get（）`调用一起使用。关于JSR-330的更多细节，请参阅这里。

The configuration in the following example is only one line, but it is important to understand the “why” as well as the “how” behind it:

以下示例中的配置仅为一行，但了解其背后的“为什么”和“如何”非常重要：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- an HTTP Session-scoped bean exposed as a proxy -->
    <bean id="userPreferences" class="com.something.UserPreferences" scope="session">
        <!-- instructs the container to proxy the surrounding bean -->
        <aop:scoped-proxy/> (1)
    </bean>

    <!-- a singleton-scoped bean injected with a proxy to the above bean -->
    <bean id="userService" class="com.something.SimpleUserService">
        <!-- a reference to the proxied userPreferences bean -->
        <property name="userPreferences" ref="userPreferences"/>
    </bean>
</beans>
```

| (1)  | The line that defines the proxy. |
| ---- | -------------------------------- |
|      |                                  |

To create such a proxy, you insert a child `<aop:scoped-proxy/>` element into a scoped bean definition (see [Choosing the Type of Proxy to Create](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection-proxies) and [XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas)). Why do definitions of beans scoped at the `request`, `session` and custom-scope levels require the `<aop:scoped-proxy/>` element? Consider the following singleton bean definition and contrast it with what you need to define for the aforementioned scopes (note that the following `userPreferences` bean definition as it stands is incomplete):

要创建这样的代理，需要插入一个子代 `<aop:scoped-proxy/>` 元素放入作用域bean定义中（请参阅选择要创建的代理类型和基于XML模式的配置）。为什么在 `request`, `session`和自定义范围级别定义bean需要 `<aop:scoped-proxy/>` 元素？考虑下面的singleton bean定义，并将其与您需要为上述范围定义的内容进行对比（请注意，下面的`userPreferences` bean定义是不完整的）：

```xml
<bean id="userPreferences" class="com.something.UserPreferences" scope="session"/>

<bean id="userManager" class="com.something.UserManager">
    <property name="userPreferences" ref="userPreferences"/>
</bean>
```

In the preceding example, the singleton bean (`userManager`) is injected with a reference to the HTTP `Session`-scoped bean (`userPreferences`). The salient point here is that the `userManager` bean is a singleton: it is instantiated exactly once per container, and its dependencies (in this case only one, the `userPreferences` bean) are also injected only once. This means that the `userManager` bean operates only on the exact same `userPreferences` object (that is, the one with which it was originally injected.

在前面的示例中，单例bean（`userManager`）被注入一个对HTTP会话作用域bean（`userPreferences`）的引用。这里突出的一点是，`usermanager`bean是一个单例：它在每个容器中只实例化一次，它的依赖项（在本例中只有一个，`userPreferences` bean）也只被注入一次。这意味着`usermanager`bean只对完全相同的`userPreferences`对象（即最初注入它的对象）进行操作。

This is not the behavior you want when injecting a shorter-lived scoped bean into a longer-lived scoped bean (for example, injecting an HTTP `Session`-scoped collaborating bean as a dependency into singleton bean). Rather, you need a single `userManager` object, and, for the lifetime of an HTTP `Session`, you need a `userPreferences` object that is specific to the HTTP `Session`. Thus, the container creates an object that exposes the exact same public interface as the `UserPreferences` class (ideally an object that is a `UserPreferences` instance), which can fetch the real `UserPreferences` object from the scoping mechanism (HTTP request, `Session`, and so forth). The container injects this proxy object into the `userManager` bean, which is unaware that this `UserPreferences` reference is a proxy. In this example, when a `UserManager` instance invokes a method on the dependency-injected `UserPreferences` object, it is actually invoking a method on the proxy. The proxy then fetches the real `UserPreferences` object from (in this case) the HTTP `Session` and delegates the method invocation onto the retrieved real `UserPreferences` object.

将作用域较短的bean注入到一个作用域较长的bean中是不合理的（例如，将HTTP会话范围内的协作bean作为依赖项注入到singleton bean中）。相反，您需要一个`userManager`对象，并且处于HTTP会话的生存期，您需要一个特定于HTTP会话的`userPreferences`对象。因此，容器创建一个对象，该对象公开与`UserPreferences`类完全相同的公共接口（理想情况下是`UserPreferences`实例的对象），该对象可以从作用域机制（HTTP请求、会话等）获取真正的`UserPreferences`对象。容器将这个代理对象注入到`usermanager`bean中，它不知道这个`UserPreferences`引用是一个代理。在本例中，当`UserManager`实例调用依赖注入的`UserPreferences`对象上的方法时，它实际上是在调用代理上的方法。然后代理从HTTP会话（在本例中）获取真实的`UserPreferences`对象，并将方法调用委托给检索到的真的`UserPreferences`对象。

Thus, you need the following (correct and complete) configuration when injecting `request-` and `session-scoped` beans into collaborating objects, as the following example shows:

因此，当向协作对象中注入`请求`和`会话`范围的bean时，需要以下配置（正确且完整），如下例所示：

```xml
<bean id="userPreferences" class="com.something.UserPreferences" scope="session">
    <aop:scoped-proxy/>
</bean>

<bean id="userManager" class="com.something.UserManager">
    <property name="userPreferences" ref="userPreferences"/>
</bean>
```

###### Choosing the Type of Proxy to Create

选择要创建的代理类型

By default, when the Spring container creates a proxy for a bean that is marked up with the `<aop:scoped-proxy/>` element, a CGLIB-based class proxy is created.

默认情况下，当Spring容器为标记为`<aop:scoped-proxy/>`元素，则创建基于CGLIB的类代理。

CGLIB proxies intercept only public method calls! Do not call non-public methods on such a proxy. They are not delegated to the actual scoped target object.

CGLIB代理只拦截公共方法调用！不要对此类代理调用非公共方法。它们不会委托给实际作用域的目标对象。

Alternatively, you can configure the Spring container to create standard JDK interface-based proxies for such scoped beans, by specifying `false` for the value of the `proxy-target-class` attribute of the `<aop:scoped-proxy/>` element. Using JDK interface-based proxies means that you do not need additional libraries in your application classpath to affect such proxying. However, it also means that the class of the scoped bean must implement at least one interface and that all collaborators into which the scoped bean is injected must reference the bean through one of its interfaces. The following example shows a proxy based on an interface:

或者，您可以配置Spring容器，为此类作用域bean创建基于`JDK接口`的标准代理，`proxy-target-class`属性的值指定为false`<aop:scoped-proxy/>`元素。使用基于JDK接口的代理意味着您不需要在应用程序类路径中添加其他库来影响这种代理。但是，这也意味着作用域bean的类必须实现至少一个接口，并且将作用域bean注入其中的所有协作者都必须通过它的一个接口引用该bean。以下示例显示基于接口的代理：

```xml
<!-- DefaultUserPreferences implements the UserPreferences interface -->
<bean id="userPreferences" class="com.stuff.DefaultUserPreferences" scope="session">
    <aop:scoped-proxy proxy-target-class="false"/>
</bean>

<bean id="userManager" class="com.stuff.UserManager">
    <property name="userPreferences" ref="userPreferences"/>
</bean>
```

For more detailed information about choosing class-based or interface-based proxying, see [Proxying Mechanisms](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-proxying).

有关选择基于类或基于接口的代理的详细信息，请参阅代理机制。

##### 1.5.5. Custom Scopes

The bean scoping mechanism is extensible. You can define your own scopes or even redefine existing scopes, although the latter is considered bad practice and you cannot override the built-in `singleton` and `prototype` scopes.

bean作用域机制是可扩展的。您可以定义自己的作用域，甚至可以重新定义现有的作用域，尽管后者被认为是不好的做法，并且您不能重写内置的`singleton`和`prototype`作用域。

##### Creating a Custom Scope

To integrate your custom scopes into the Spring container, you need to implement the `org.springframework.beans.factory.config.Scope` interface, which is described in this section. For an idea of how to implement your own scopes, see the `Scope` implementations that are supplied with the Spring Framework itself and the [`Scope`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/config/Scope.html) javadoc, which explains the methods you need to implement in more detail.

要将自定义范围集成到Spring容器中，需要实现`org.springframework.beans.factory.config.Scope`接口，这将在本节中描述。关于如何实现您自己的作用域的想法，请参阅Spring框架本身和范围javadoc提供的范围实现，后者详细解释了需要实现的方法。

The `Scope` interface has four methods to get objects from the scope, remove them from the scope, and let them be destroyed.

Scope接口有四种方法可以从作用域中获取对象，从作用域中删除对象，然后销毁它们。

The session scope implementation, for example, returns the session-scoped bean (if it does not exist, the method returns a new instance of the bean, after having bound it to the session for future reference). The following method returns the object from the underlying scope:

例如，会话作用域实现返回会话作用域bean（如果它不存在，则该方法在将其绑定到会话以供将来参考后返回该bean的新实例）。以下方法从基础范围返回对象：

Java

```java
Object get(String name, ObjectFactory<?> objectFactory)
```

The session scope implementation, for example, removes the session-scoped bean from the underlying session. The object should be returned, but you can return `null` if the object with the specified name is not found. The following method removes the object from the underlying scope:

例如，会话作用域实现从底层会话中删除会话作用域bean。应返回该对象，但如果找不到具有指定名称的对象，则可以返回null。以下方法从基础作用域中删除对象：

Java

```java
Object remove(String name)
```

The following method registers a callback that the scope should invoke when it is destroyed or when the specified object in the scope is destroyed:

以下方法注册了一个回调，当作用域被销毁或作用域中的指定对象被销毁时，该回调应被调用：

Java

```java
void registerDestructionCallback(String name, Runnable destructionCallback)
```

See the [javadoc](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/config/Scope.html#registerDestructionCallback) or a Spring scope implementation for more information on destruction callbacks.

The following method obtains the conversation identifier for the underlying scope:

以下方法获取基础作用域的会话标识符：

Java

```java
String getConversationId()
```

This identifier is different for each scope. For a session scoped implementation, this identifier can be the session identifier.

每个作用域的标识符都不同。对于会话范围的实现，此标识符可以是会话标识符。

##### Using a Custom Scope

After you write and test one or more custom `Scope` implementations, you need to make the Spring container aware of your new scopes. The following method is the central method to register a new `Scope` with the Spring container:

在编写和测试一个或多个自定义范围实现之后，您需要使Spring容器知道您的新范围。以下方法是向Spring容器注册新作用域的中心方法：

Java

```java
void registerScope(String scopeName, Scope scope);
```

This method is declared on the `ConfigurableBeanFactory` interface, which is available through the `BeanFactory` property on most of the concrete `ApplicationContext` implementations that ship with Spring.

此方法在ConfigurableBeanFactory接口上声明，该接口可通过Spring附带的大多数具体ApplicationContext实现的BeanFactory属性获得。

The first argument to the `registerScope(..)` method is the unique name associated with a scope. Examples of such names in the Spring container itself are `singleton` and `prototype`. The second argument to the `registerScope(..)` method is an actual instance of the custom `Scope` implementation that you wish to register and use.

registerScope（..）方法的第一个参数是与作用域关联的唯一名称。Spring容器本身中的此类名称的例子有singleton和prototype。registerScope（..）方法的第二个参数是希望注册和使用的自定义范围实现的实际实例。

Suppose that you write your custom `Scope` implementation, and then register it as shown in the next example.

假设您编写了自定义范围实现，然后按照下一个示例所示注册它。

The next example uses `SimpleThreadScope`, which is included with Spring but is not registered by default. The instructions would be the same for your own custom `Scope` implementations.

下一个示例使用SimpleThreadScope，它包含在Spring中，但默认情况下不注册。对于您自己的自定义范围实现，这些说明将是相同的。

Java

Kotlin

```java
Scope threadScope = new SimpleThreadScope();
beanFactory.registerScope("thread", threadScope);
```

You can then create bean definitions that adhere to the scoping rules of your custom `Scope`, as follows:

然后，您可以创建遵循自定义范围的作用域规则的bean定义，如下所示：

```xml
<bean id="..." class="..." scope="thread">
```

With a custom `Scope` implementation, you are not limited to programmatic registration of the scope. You can also do the `Scope` registration declaratively, by using the `CustomScopeConfigurer` class, as the following example shows:

对于自定义范围实现，您不局限于范围的编程注册。您还可以使用CustomScopeConfigurer类以声明方式进行作用域注册，如下例所示：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
        <property name="scopes">
            <map>
                <entry key="thread">
                    <bean class="org.springframework.context.support.SimpleThreadScope"/>
                </entry>
            </map>
        </property>
    </bean>

    <bean id="thing2" class="x.y.Thing2" scope="thread">
        <property name="name" value="Rick"/>
        <aop:scoped-proxy/>
    </bean>

    <bean id="thing1" class="x.y.Thing1">
        <property name="thing2" ref="thing2"/>
    </bean>

</beans>
```

When you place `<aop:scoped-proxy/>` in a `FactoryBean` implementation, it is the factory bean itself that is scoped, not the object returned from `getObject()`.

当你把`<aop:scoped-proxy/>`在`FactoryBean`实现中，限定作用域的是工厂bean本身，而不是从`getObject（）`返回的对象。

#### 1.6. Customizing the Nature of a Bean

##### 1.6.2. `ApplicationContextAware` and `BeanNameAware`

When an `ApplicationContext` creates an object instance that implements the `org.springframework.context.ApplicationContextAware` interface, the instance is provided with a reference to that `ApplicationContext`. The following listing shows the definition of the `ApplicationContextAware` interface:

当ApplicationContext创建一个实现`org.springframework.context.applicationcontextaware`接口，该实例通过对ApplicationContext的引用提供。下面的清单显示了`applicationcontextaware`接口的定义:

Java

```java
public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
```

Thus, beans can programmatically manipulate the `ApplicationContext` that created them, through the `ApplicationContext` interface or by casting the reference to a known subclass of this interface (such as `ConfigurableApplicationContext`, which exposes additional functionality). One use would be the programmatic retrieval of other beans. Sometimes this capability is useful. However, in general, you should avoid it, because it couples the code to Spring and does not follow the Inversion of Control style, where collaborators are provided to beans as properties. Other methods of the `ApplicationContext` provide access to file resources, publishing application events, and accessing a `MessageSource`. These additional features are described in [Additional Capabilities of the `ApplicationContext`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-introduction).

因此，bean可以通过`ApplicationContext`接口或通过将引用转换为该接口的已知子类(比如`ConfigurableApplicationContext`，它公开了其他功能)，以编程方式操作创建它们的`ApplicationContext`。一种用途是对其他bean进行编程检索。有时这个功能是有用的。但是，通常应该避免使用它，因为它将代码与Spring结合在一起，并且不遵循控制反转风格，在这种风格中协作者作为属性提供给bean。`ApplicationContext`的其他方法提供了对文件资源的访问、发布应用程序事件和访问消息源。这些附加特性在`ApplicationContext`的附加功能中进行了描述。

Autowiring is another alternative to obtain a reference to the `ApplicationContext`. The *traditional* `constructor` and `byType` autowiring modes (as described in [Autowiring Collaborators](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire)) can provide a dependency of type `ApplicationContext` for a constructor argument or a setter method parameter, respectively. For more flexibility, including the ability to autowire fields and multiple parameter methods, use the annotation-based autowiring features. If you do, the `ApplicationContext` is autowired into a field, constructor argument, or method parameter that expects the `ApplicationContext` type if the field, constructor, or method in question carries the `@Autowired` annotation. For more information, see [Using `@Autowired`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation).

自动装配是获得对`ApplicationContext`的引用的另一种选择。传统的`constructor` and `byType`自动装配模式(如在自动装配协作器中所描述的)可以分别为构造函数参数或setter方法参数提供类型`ApplicationContext`的依赖关系。为了获得更大的灵活性，包括自动装配字段和多个参数方法的能力，可以使用基于注解的自动装配特性。如果你这样做了，ApplicationContext就会自动生成一个字段、构造函数参数或方法参数，如果有问题的字段、构造函数或方法带有@Autowired注解，那么这些参数期望得到ApplicationContext类型。更多信息，请参见使用@Autowired。

When an `ApplicationContext` creates a class that implements the `org.springframework.beans.factory.BeanNameAware` interface, the class is provided with a reference to the name defined in its associated object definition. The following listing shows the definition of the BeanNameAware interface:

当`ApplicationContext`创建一个实现`org.springframework.beans.factory.BeanNameAware`的类时。BeanNameAware接口，为类提供对其关联对象定义中定义的名称的引用。下面的清单显示了BeanNameAware接口的定义:

Java

```java
public interface BeanNameAware {

    void setBeanName(String name) throws BeansException;
}
```

The callback is invoked after population of normal bean properties but before an initialization callback such as `InitializingBean`, `afterPropertiesSet`, or a custom init-method.

在填充普通bean属性之后，但在初始化回调(如`InitializingBean`、`afterPropertiesSet`或自定义初始化方法)之前调用回调。

##### 1.6.3. Other `Aware` Interfaces

Besides `ApplicationContextAware` and `BeanNameAware` (discussed [earlier](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware)), Spring offers a wide range of `Aware` callback interfaces that let beans indicate to the container that they require a certain infrastructure dependency. As a general rule, the name indicates the dependency type. The following table summarizes the most important `Aware` interfaces:

除了`applicationcontextaware`和`BeanNameAware`(前面讨论过)之外，Spring还提供了广泛的可感知回调接口，让bean向容器表明它们需要某种基础设施依赖关系。作为一般规则，名称表示依赖类型。下表总结了最重要的感知接口:

| Name                             | Injected Dependency                                          | Explained in…                                                |
| :------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `ApplicationContextAware`        | Declaring `ApplicationContext`.                              | [`ApplicationContextAware` and `BeanNameAware`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware) |
| `ApplicationEventPublisherAware` | Event publisher of the enclosing `ApplicationContext`.       | [Additional Capabilities of the `ApplicationContext`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-introduction) |
|                                  | 封闭ApplicationContext的事件发布程序。                       |                                                              |
| `BeanClassLoaderAware`           | Class loader used to load the bean classes.                  | [Instantiating Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class) |
| `BeanFactoryAware`               | Declaring `BeanFactory`.                                     | [`ApplicationContextAware` and `BeanNameAware`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware) |
|                                  | 声明BeanFactory。                                            |                                                              |
| `BeanNameAware`                  | Name of the declaring bean.                                  | [`ApplicationContextAware` and `BeanNameAware`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware) |
|                                  | 声明bean的名称。                                             |                                                              |
| `LoadTimeWeaverAware`            | Defined weaver for processing class definition at load time. | [Load-time Weaving with AspectJ in the Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw) |
|                                  | 定义了在加载时处理类定义的weaver。                           |                                                              |
| `MessageSourceAware`             | Configured strategy for resolving messages (with support for parametrization and internationalization). | [Additional Capabilities of the `ApplicationContext`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-introduction) |
|                                  | 已配置用于解析消息的策略(支持参数化和国际化)。               |                                                              |
| `NotificationPublisherAware`     | Spring JMX notification publisher.                           | [Notifications](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#jmx-notifications) |
|                                  | Spring JMX通知发布程序。                                     |                                                              |
| `ResourceLoaderAware`            | Configured loader for low-level access to resources.         | [Resources](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources) |
|                                  | 配置了用于低级访问资源的加载程序                             |                                                              |
| `ServletConfigAware`             | Current `ServletConfig` the container runs in. Valid only in a web-aware Spring `ApplicationContext`. | [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc) |
|                                  | 容器运行的当前ServletConfig。仅在支持web的Spring ApplicationContext中有效。 |                                                              |
| `ServletContextAware`            | Current `ServletContext` the container runs in. Valid only in a web-aware Spring `ApplicationContext`. | [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc) |
|                                  | Current `ServletContext` the container runs in. Valid only in a web-aware Spring `ApplicationContext`. |                                                              |

Note again that using these interfaces ties your code to the Spring API and does not follow the Inversion of Control style. As a result, we recommend them for infrastructure beans that require programmatic access to the container.

请再次注意，使用这些接口将您的代码绑定到Spring API，并且不遵循控制反转样式。因此，我们建议将它们用于需要对容器进行编程访问的基础设施bean。

1.6条。定制Bean的性质

#### 1.7. Bean Definition Inheritance

1.7条。Bean定义继承

#### 1.8. Container Extension Points

Typically, an application developer does not need to subclass `ApplicationContext` implementation classes. Instead, the Spring IoC container can be extended by plugging in implementations of special integration interfaces. The next few sections describe these integration interfaces.

通常，应用程序开发人员不需要子类化ApplicationContext实现类。相反，可以通过插入特殊集成接口的实现来扩展Spring IoC容器。接下来的几节将描述这些集成接口。

##### 1.8.1. Customizing Beans by Using a `BeanPostProcessor`

The `BeanPostProcessor` interface defines callback methods that you can implement to provide your own (or override the container’s default) instantiation logic, dependency resolution logic, and so forth. If you want to implement some custom logic after the Spring container finishes instantiating, configuring, and initializing a bean, you can plug in one or more custom `BeanPostProcessor` implementations.

`BeanPostProcessor`接口定义了回调方法，您可以实现这些方法来提供您自己的(或覆盖容器的默认值)实例化逻辑、依赖项解析逻辑等等。如果您想在Spring容器完成实例化、配置和初始化bean之后实现一些自定义逻辑，您可以插入一个或多个自定义`BeanPostProcessor`实现。

You can configure multiple `BeanPostProcessor` instances, and you can control the order in which these `BeanPostProcessor` instances run by setting the `order` property. You can set this property only if the `BeanPostProcessor` implements the `Ordered` interface. If you write your own `BeanPostProcessor`, you should consider implementing the `Ordered` interface, too. For further details, see the javadoc of the [`BeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) and [`Ordered`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/Ordered.html) interfaces. See also the note on [programmatic registration of `BeanPostProcessor` instances](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-programmatically-registering-beanpostprocessors).

您可以配置多个`BeanPostProcessor`实例，并且可以通过设置order属性来控制这些`BeanPostProcessor`实例运行的顺序。只有当`BeanPostProcessor`实现了有序接口时，才能设置此属性。如果您编写自己的`BeanPostProcessor`，也应该考虑实现`Ordered`接口。有关详细信息，请参阅`BeanPostProcessor`和`Ordered`接口的javadoc。请参见`BeanPostProcessor`实例的编程注册说明。

`BeanPostProcessor` instances operate on bean (or object) instances. That is, the Spring IoC container instantiates a bean instance and then `BeanPostProcessor` instances do their work.

`BeanPostProcessor`实例操作bean(或对象)实例。也就是说，Spring IoC容器实例化一个bean实例，然后`BeanPostProcessor`实例执行它们的工作。

`BeanPostProcessor` instances are scoped per-container. This is relevant only if you use container hierarchies. If you define a `BeanPostProcessor` in one container, it post-processes only the beans in that container. In other words, beans that are defined in one container are not post-processed by a `BeanPostProcessor` defined in another container, even if both containers are part of the same hierarchy.To change the actual bean definition (that is, the blueprint that defines the bean), you instead need to use a `BeanFactoryPostProcessor`, as described in [Customizing Configuration Metadata with a `BeanFactoryPostProcessor`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-factory-postprocessors).

BeanPostProcessor实例的作用域为每个容器。只有当您使用容器层次结构时，这才是相关的。如果在一个容器中定义`BeanPostProcessor`，那么它只对该容器中的bean进行后处理。换句话说，在一个容器中定义的bean不会被另一个容器中定义的`BeanPostProcessor`进行后处理，即使这两个容器属于同一层次结构。要更改实际的bean定义(即定义bean的蓝图)，您需要使用`BeanFactoryPostProcessor`，正如在使用`BeanFactoryPostProcessor`自定义配置元数据中所描述的那样。

The `org.springframework.beans.factory.config.BeanPostProcessor` interface consists of exactly two callback methods. When such a class is registered as a post-processor with the container, for each bean instance that is created by the container, the post-processor gets a callback from the container both before container initialization methods (such as `InitializingBean.afterPropertiesSet()` or any declared `init` method) are called, and after any bean initialization callbacks. The post-processor can take any action with the bean instance, including ignoring the callback completely. A bean post-processor typically checks for callback interfaces, or it may wrap a bean with a proxy. Some Spring AOP infrastructure classes are implemented as bean post-processors in order to provide proxy-wrapping logic.

`org.springframework.beans.factory.config.BeanPostProcessor`接口恰好由两个回调方法组成。当这样一个类注册为后处理器的容器,每个容器创建bean实例,后处理器从容器之前得到一个回调容器初始化方法(如`InitializingBean.afterPropertiesSet()`或任何声明`init`方法),任何bean初始化后回调。后处理器可以对bean实例采取任何操作，包括完全忽略回调。bean后处理器通常检查回调接口，或者用代理包装bean。为了提供代理包装逻辑，一些Spring AOP基础设施类被实现为bean后处理器。

An `ApplicationContext` automatically detects any beans that are defined in the configuration metadata that implements the `BeanPostProcessor` interface. The `ApplicationContext` registers these beans as post-processors so that they can be called later, upon bean creation. Bean post-processors can be deployed in the container in the same fashion as any other beans.

`ApplicationContext`自动检测在实现`BeanPostProcessor`接口的配置元数据中定义的任何bean。`ApplicationContext`将这些bean注册为后处理器，以便稍后在bean创建时调用它们。Bean后处理器可以以与其他Bean相同的方式部署在容器中。

Note that, when declaring a `BeanPostProcessor` by using an `@Bean` factory method on a configuration class, the return type of the factory method should be the implementation class itself or at least the `org.springframework.beans.factory.config.BeanPostProcessor` interface, clearly indicating the post-processor nature of that bean. Otherwise, the `ApplicationContext` cannot autodetect it by type before fully creating it. Since a `BeanPostProcessor` needs to be instantiated early in order to apply to the initialization of other beans in the context, this early type detection is critical.

注意，当通过在配置类上使用`@Bean`工厂方法声明`BeanPostProcessor`时，工厂方法的返回类型应该是实现类本身，或者至少是`org.springframework.bean .factory.config.BeanPostProcessor`接口，清楚地表明该bean的后处理器特性。否则，`ApplicationContext`不能在完全创建它之前按类型自动检测它。由于`BeanPostProcessor`需要尽早实例化，以便应用于上下文中其他bean的初始化，因此这种早期类型检测非常关键。

Programmatically registering `BeanPostProcessor` instancesWhile the recommended approach for `BeanPostProcessor` registration is through `ApplicationContext` auto-detection (as described earlier), you can register them programmatically against a `ConfigurableBeanFactory` by using the `addBeanPostProcessor` method. This can be useful when you need to evaluate conditional logic before registration or even for copying bean post processors across contexts in a hierarchy. Note, however, that `BeanPostProcessor` instances added programmatically do not respect the `Ordered` interface. Here, it is the order of registration that dictates the order of execution. Note also that `BeanPostProcessor` instances registered programmatically are always processed before those registered through auto-detection, regardless of any explicit ordering.

通过程序注册`BeanPostProcessor`实例虽然`BeanPostProcessor`注册的推荐方法是通过`ApplicationContext`自动检测(如前所述)，你可以通过使用“addBeanPostProcessor”方法通过程序注册一个“ConfigurableBeanFactory”。当您需要在注册前计算条件逻辑时，或者甚至在跨层次结构中的上下文复制bean post处理器时，这可能非常有用。但是请注意，通过编程方式添加的“BeanPostProcessor”实例不尊重“Ordered”接口。在这里，注册的顺序决定了执行的顺序。还请注意，无论任何显式的顺序，以编程方式注册的“BeanPostProcessor”实例总是在那些通过自动检测注册的实例之前处理。

`BeanPostProcessor` instances and AOP auto-proxyingClasses that implement the `BeanPostProcessor` interface are special and are treated differently by the container. All `BeanPostProcessor` instances and beans that they directly reference are instantiated on startup, as part of the special startup phase of the `ApplicationContext`. Next, all `BeanPostProcessor` instances are registered in a sorted fashion and applied to all further beans in the container. Because AOP auto-proxying is implemented as a `BeanPostProcessor` itself, neither `BeanPostProcessor` instances nor the beans they directly reference are eligible for auto-proxying and, thus, do not have aspects woven into them.For any such bean, you should see an informational log message: `Bean someBean is not eligible for getting processed by all BeanPostProcessor interfaces (for example: not eligible for auto-proxying)`.If you have beans wired into your `BeanPostProcessor` by using autowiring or `@Resource` (which may fall back to autowiring), Spring might access unexpected beans when searching for type-matching dependency candidates and, therefore, make them ineligible for auto-proxying or other kinds of bean post-processing. For example, if you have a dependency annotated with `@Resource` where the field or setter name does not directly correspond to the declared name of a bean and no name attribute is used, Spring accesses other beans for matching them by type.

实现“BeanPostProcessor”接口的“BeanPostProcessor”实例和AOP自动代理类是特殊的，容器会以不同的方式处理它们。作为“ApplicationContext”特殊启动阶段的一部分，所有的“BeanPostProcessor”实例和它们直接引用的bean都在启动时被实例化。接下来，所有' BeanPostProcessor '实例将以排序的方式注册，并应用于容器中进一步的所有bean。因为AOP自动代理是作为“BeanPostProcessor”本身实现的，所以“BeanPostProcessor”实例和它们直接引用的bean都不适合自动代理，因此，它们没有将方面编入其中。对于任何这样的bean，您都应该看到一个信息日志消息:“bean someBean不适合被所有BeanPostProcessor接口处理(例如:不适合自动代理)”。如果您使用自动装配或@Resource(可能会回到自动装配)将bean连接到您的“BeanPostProcessor”中，Spring可能会在搜索类型匹配依赖项候选时访问意外的bean，因此，使它们不符合自动代理或其他类型的bean后处理的条件。例如，如果您有一个注释为“@Resource”的依赖项，其中字段或setter名称没有直接对应于bean的声明名称，并且没有使用name属性，那么Spring将访问其他bean，以按类型匹配它们。

The following examples show how to write, register, and use `BeanPostProcessor` instances in an `ApplicationContext`.

下面的例子展示了如何在一个“ApplicationContext”中编写、注册和使用“BeanPostProcessor”实例。

##### Example: Hello World, `BeanPostProcessor`-style

This first example illustrates basic usage. The example shows a custom `BeanPostProcessor` implementation that invokes the `toString()` method of each bean as it is created by the container and prints the resulting string to the system console.

第一个示例演示了基本用法。这个示例展示了一个自定义的“BeanPostProcessor”实现，它在容器创建每个bean时调用它的“toString()”方法，并将结果字符串打印到系统控制台。

The following listing shows the custom `BeanPostProcessor` implementation class definition:

下面的清单显示了自定义的“BeanPostProcessor”实现类定义:

Java

```java
package scripting;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {

    // simply return the instantiated bean as-is
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("Bean '" + beanName + "' created : " + bean.toString());
        return bean;
    }
}
```

The following `beans` element uses the `InstantiationTracingBeanPostProcessor`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:lang="http://www.springframework.org/schema/lang"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/lang
        https://www.springframework.org/schema/lang/spring-lang.xsd">

    <lang:groovy id="messenger"
            script-source="classpath:org/springframework/scripting/groovy/Messenger.groovy">
        <lang:property name="message" value="Fiona Apple Is Just So Dreamy."/>
    </lang:groovy>

    <!--
    when the above bean (messenger) is instantiated, this custom
    BeanPostProcessor implementation will output the fact to the system console
    -->
    <bean class="scripting.InstantiationTracingBeanPostProcessor"/>

</beans>
```

Notice how the `InstantiationTracingBeanPostProcessor` is merely defined. It does not even have a name, and, because it is a bean, it can be dependency-injected as you would any other bean. (The preceding configuration also defines a bean that is backed by a Groovy script. The Spring dynamic language support is detailed in the chapter entitled [Dynamic Language Support](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#dynamic-language).)

注意`InstantiationTracingBeanPostProcessor`是如何定义的。它甚至没有名称，而且，因为它是一个bean，所以可以像其他bean一样进行依赖注入。(前面的配置还定义了一个由Groovy脚本支持的bean。Spring动态语言支持在[动态语言支持]一章中有详细说明(https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#dynamic-language)。

The following Java application runs the preceding code and configuration:

Java

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scripting.Messenger;

public final class Boot {

    public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("scripting/beans.xml");
        Messenger messenger = ctx.getBean("messenger", Messenger.class);
        System.out.println(messenger);
    }

}
```

The output of the preceding application resembles the following:

```
Bean 'messenger' created : org.springframework.scripting.groovy.GroovyMessenger@272961
org.springframework.scripting.groovy.GroovyMessenger@272961
```

##### Example: The `RequiredAnnotationBeanPostProcessor`

Using callback interfaces or annotations in conjunction with a custom `BeanPostProcessor` implementation is a common means of extending the Spring IoC container. An example is Spring’s `RequiredAnnotationBeanPostProcessor` — a `BeanPostProcessor` implementation that ships with the Spring distribution and that ensures that JavaBean properties on beans that are marked with an (arbitrary) annotation are actually (configured to be) dependency-injected with a value.

将回调接口或注释与自定义的“BeanPostProcessor”实现结合使用是扩展Spring IoC容器的一种常见方法。一个例子是Spring的“RequiredAnnotationBeanPostProcessor”——随Spring发行版一起发布的“BeanPostProcessor”实现，它确保用(任意)注释标记的bean上的JavaBean属性实际上(配置为)依赖注入了一个值。

##### 1.8.2. Customizing Configuration Metadata with a `BeanFactoryPostProcessor`

The next extension point that we look at is the `org.springframework.beans.factory.config.BeanFactoryPostProcessor`. The semantics of this interface are similar to those of the `BeanPostProcessor`, with one major difference: `BeanFactoryPostProcessor` operates on the bean configuration metadata. That is, the Spring IoC container lets a `BeanFactoryPostProcessor` read the configuration metadata and potentially change it *before* the container instantiates any beans other than `BeanFactoryPostProcessor` instances.

我们要查看的下一个扩展点是org.springframework.beans.factory.config.BeanFactoryPostProcessor。此接口的语义与BeanPostProcessor类似，但有一个主要区别:BeanFactoryPostProcessor操作bean配置元数据。也就是说，Spring IoC容器允许BeanFactoryPostProcessor在容器实例化BeanFactoryPostProcessor实例之外的任何bean之前读取配置元数据并可能更改它。

You can configure multiple `BeanFactoryPostProcessor` instances, and you can control the order in which these `BeanFactoryPostProcessor` instances run by setting the `order` property. However, you can only set this property if the `BeanFactoryPostProcessor` implements the `Ordered` interface. If you write your own `BeanFactoryPostProcessor`, you should consider implementing the `Ordered` interface, too. See the javadoc of the [`BeanFactoryPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) and [`Ordered`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/Ordered.html) interfaces for more details.

您可以配置多个BeanFactoryPostProcessor实例，并且您可以通过设置order属性来控制这些BeanFactoryPostProcessor实例运行的顺序。但是，您只能在BeanFactoryPostProcessor实现了有序接口的情况下设置此属性。如果您编写自己的BeanFactoryPostProcessor，也应该考虑实现Ordered接口。有关更多细节，请参阅BeanFactoryPostProcessor和有序接口的javadoc。

If you want to change the actual bean instances (that is, the objects that are created from the configuration metadata), then you instead need to use a `BeanPostProcessor` (described earlier in [Customizing Beans by Using a `BeanPostProcessor`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-bpp)). While it is technically possible to work with bean instances within a `BeanFactoryPostProcessor` (for example, by using `BeanFactory.getBean()`), doing so causes premature bean instantiation, violating the standard container lifecycle. This may cause negative side effects, such as bypassing bean post processing.Also, `BeanFactoryPostProcessor` instances are scoped per-container. This is only relevant if you use container hierarchies. If you define a `BeanFactoryPostProcessor` in one container, it is applied only to the bean definitions in that container. Bean definitions in one container are not post-processed by `BeanFactoryPostProcessor` instances in another container, even if both containers are part of the same hierarchy.

如果希望更改实际bean实例(即从配置元数据创建的对象)，则需要使用BeanPostProcessor(在前面通过使用BeanPostProcessor自定义bean中进行了描述)。虽然在BeanFactoryPostProcessor中使用bean实例在技术上是可行的(例如，通过使用BeanFactory.getBean())，但是这样做会导致过早的bean实例化，违反标准的容器生命周期。这可能会导致负面的副作用，比如绕过bean的后处理。

A bean factory post-processor is automatically run when it is declared inside an `ApplicationContext`, in order to apply changes to the configuration metadata that define the container. Spring includes a number of predefined bean factory post-processors, such as `PropertyOverrideConfigurer` and `PropertySourcesPlaceholderConfigurer`. You can also use a custom `BeanFactoryPostProcessor` — for example, to register custom property editors.

另外，BeanFactoryPostProcessor实例的作用域为每个容器。这只有在使用容器层次结构时才有用。如果您在一个容器中定义了BeanFactoryPostProcessor，那么它只应用于该容器中的bean定义。一个容器中的Bean定义不会被另一个容器中的BeanFactoryPostProcessor实例进行后处理，即使这两个容器属于同一层次结构。

An `ApplicationContext` automatically detects any beans that are deployed into it that implement the `BeanFactoryPostProcessor` interface. It uses these beans as bean factory post-processors, at the appropriate time. You can deploy these post-processor beans as you would any other bean.

当bean工厂后处理器在ApplicationContext中声明时，它将自动运行，以便对定义容器的配置元数据应用更改。Spring包括许多预定义的bean工厂后处理器，如PropertyOverrideConfigurer和PropertySourcesPlaceholderConfigurer。您还可以使用自定义BeanFactoryPostProcessor—例如，用于注册自定义属性编辑器。

As with `BeanPostProcessor`s , you typically do not want to configure `BeanFactoryPostProcessor`s for lazy initialization. If no other bean references a `Bean(Factory)PostProcessor`, that post-processor will not get instantiated at all. Thus, marking it for lazy initialization will be ignored, and the `Bean(Factory)PostProcessor` will be instantiated eagerly even if you set the `default-lazy-init` attribute to `true` on the declaration of your `<beans />` element.

ApplicationContext自动检测部署到其中实现BeanFactoryPostProcessor接口的任何bean。在适当的时候，它将这些bean用作bean工厂的后处理器。可以像部署任何其他bean一样部署这些后处理bean。

与BeanPostProcessors一样，您通常不希望将BeanFactoryPostProcessors配置为延迟初始化。如果没有其他bean引用bean(工厂)后处理器，则该后处理器根本不会实例化。因此，将其标记为延迟初始化将被忽略，并且即使在声明<beans />元素时将default-lazy-init属性设置为true, Bean(工厂)后处理器也将被快速实例化。

##### Example: The Class Name Substitution `PropertySourcesPlaceholderConfigurer`

You can use the `PropertySourcesPlaceholderConfigurer` to externalize property values from a bean definition in a separate file by using the standard Java `Properties` format. Doing so enables the person deploying an application to customize environment-specific properties, such as database URLs and passwords, without the complexity or risk of modifying the main XML definition file or files for the container.

您可以使用PropertySourcesPlaceholderConfigurer使用标准的Java属性格式将bean定义中的属性值外部化到单独的文件中。这样，部署应用程序的人员就可以自定义特定于环境的属性，比如数据库url和密码，而无需修改主XML定义文件或容器文件的复杂性或风险。

Consider the following XML-based configuration metadata fragment, where a `DataSource` with placeholder values is defined:

考虑以下基于xml的配置元数据片段，其中定义了具有占位符值的数据源:

```xml
<bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
    <property name="locations" value="classpath:com/something/jdbc.properties"/>
</bean>

<bean id="dataSource" destroy-method="close"
        class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

The example shows properties configured from an external `Properties` file. At runtime, a `PropertySourcesPlaceholderConfigurer` is applied to the metadata that replaces some properties of the DataSource. The values to replace are specified as placeholders of the form `${property-name}`, which follows the Ant and log4j and JSP EL style.

The actual values come from another file in the standard Java `Properties` format:

该示例显示了从外部属性文件配置的属性。在运行时，PropertySourcesPlaceholderConfigurer应用于替换数据源的某些属性的元数据。要替换的值指定为表单${property-name}的占位符，该表单遵循Ant、log4j和JSP EL样式。

实际值来自另一个标准Java属性格式的文件:

```
jdbc.driverClassName=org.hsqldb.jdbcDriver
jdbc.url=jdbc:hsqldb:hsql://production:9002
jdbc.username=sa
jdbc.password=root
```

Therefore, the `${jdbc.username}` string is replaced at runtime with the value, 'sa', and the same applies for other placeholder values that match keys in the properties file. The `PropertySourcesPlaceholderConfigurer` checks for placeholders in most properties and attributes of a bean definition. Furthermore, you can customize the placeholder prefix and suffix.

因此,$ {jdbc。字符串在运行时被替换为值'sa'，对于属性文件中匹配键的其他占位符值也适用同样的方法。PropertySourcesPlaceholderConfigurer检查bean定义的大多数属性和属性中的占位符。此外，您还可以自定义占位符前缀和后缀。

With the `context` namespace introduced in Spring 2.5, you can configure property placeholders with a dedicated configuration element. You can provide one or more locations as a comma-separated list in the `location` attribute, as the following example shows:

使用Spring 2.5中引入的上下文名称空间，您可以用一个专用的配置元素配置属性占位符。您可以在location属性中以逗号分隔的列表形式提供一个或多个位置，如下面的示例所示:

```xml
<context:property-placeholder location="classpath:com/something/jdbc.properties"/>
```

The `PropertySourcesPlaceholderConfigurer` not only looks for properties in the `Properties` file you specify. By default, if it cannot find a property in the specified properties files, it checks against Spring `Environment` properties and regular Java `System` properties.

PropertySourcesPlaceholderConfigurer不仅在您指定的属性文件中查找属性。默认情况下，如果在指定的属性文件中找不到属性，它将检查Spring环境属性和常规Java系统属性。

You can use the `PropertySourcesPlaceholderConfigurer` to substitute class names, which is sometimes useful when you have to pick a particular implementation class at runtime. The following example shows how to do so:

您可以使用PropertySourcesPlaceholderConfigurer来替换类名，当您必须在运行时选择特定的实现类时，这有时很有用。下面的例子展示了如何做到这一点:

```xml
<bean class="org.springframework.beans.factory.config.PropertySourcesPlaceholderConfigurer">
    <property name="locations">
        <value>classpath:com/something/strategy.properties</value>
    </property>
    <property name="properties">
        <value>custom.strategy.class=com.something.DefaultStrategy</value>
    </property>
</bean>

<bean id="serviceStrategy" class="${custom.strategy.class}"/>
```

If the class cannot be resolved at runtime to a valid class, resolution of the bean fails when it is about to be created, which is during the `preInstantiateSingletons()` phase of an `ApplicationContext` for a non-lazy-init bean.

如果不能在运行时将类解析为有效类，则在即将创建bean时，即在ApplicationContext非延迟init bean的预实例化esingletons()阶段，对bean的解析将失败。

##### Example: The `PropertyOverrideConfigurer`

The `PropertyOverrideConfigurer`, another bean factory post-processor, resembles the `PropertySourcesPlaceholderConfigurer`, but unlike the latter, the original definitions can have default values or no values at all for bean properties. If an overriding `Properties` file does not have an entry for a certain bean property, the default context definition is used.

PropertyOverrideConfigurer是另一个bean工厂后处理器，它类似于PropertySourcesPlaceholderConfigurer，但与后者不同的是，原始定义可以有bean属性的默认值，也可以没有值。如果覆盖属性文件没有针对某个bean属性的条目，则使用默认上下文定义。

请注意，bean定义不知道被覆盖，因此从XML定义文件中不能立即看出正在使用覆盖配置器。在多个PropertyOverrideConfigurer实例为同一个bean属性定义不同值的情况下，由于覆盖机制，最后一个实例胜出。

Note that the bean definition is not aware of being overridden, so it is not immediately obvious from the XML definition file that the override configurer is being used. In case of multiple `PropertyOverrideConfigurer` instances that define different values for the same bean property, the last one wins, due to the overriding mechanism.

Properties file configuration lines take the following format:

属性文件配置行采用以下格式:

```
beanName.property=value
```

The following listing shows an example of the format:

下面的清单显示了这种格式的一个示例:

```
dataSource.driverClassName=com.mysql.jdbc.Driver
dataSource.url=jdbc:mysql:mydb
```

This example file can be used with a container definition that contains a bean called `dataSource` that has `driver` and `url` properties.

Compound property names are also supported, as long as every component of the path except the final property being overridden is already non-null (presumably initialized by the constructors). In the following example, the `sammy` property of the `bob` property of the `fred` property of the `tom` bean is set to the scalar value `123`:

这个示例文件可以与一个容器定义一起使用，该容器定义包含一个名为dataSource的bean，该bean具有驱动程序和url属性。

还支持复合属性名，只要路径的每个组件(除了被覆盖的final属性)都是非空的(假定由构造函数初始化)。在下面的示例中，tom bean的fred属性的bob属性的sammy属性被设置为标量值123:

```
tom.fred.bob.sammy=123
```

Specified override values are always literal values. They are not translated into bean references. This convention also applies when the original value in the XML bean definition specifies a bean reference.

指定的重写值总是文字值。它们没有被转换为bean引用。当XML bean定义中的原始值指定了一个bean引用时，这个约定也适用。

With the `context` namespace introduced in Spring 2.5, it is possible to configure property overriding with a dedicated configuration element, as the following example shows:

在spring2.5中引入了上下文名称空间，可以用一个专用的配置元素来配置property override，如下面的例子所示:

```xml
<context:property-override location="classpath:override.properties"/>
```

##### 1.8.3. Customizing Instantiation Logic with a `FactoryBean`

You can implement the `org.springframework.beans.factory.FactoryBean` interface for objects that are themselves factories.

您可以实现org.springframework.beans.factory.FactoryBean为本身是工厂的对象提供接口。

The `FactoryBean` interface is a point of pluggability into the Spring IoC container’s instantiation logic. If you have complex initialization code that is better expressed in Java as opposed to a (potentially) verbose amount of XML, you can create your own `FactoryBean`, write the complex initialization inside that class, and then plug your custom `FactoryBean` into the container.

FactoryBean接口是Spring IoC容器实例化逻辑的可插入点。如果您有复杂的初始化代码(用Java来表达比(可能)冗长的XML更好)，那么您可以创建自己的FactoryBean，在类中编写复杂的初始化，然后将定制的FactoryBean插入到容器中。

The `FactoryBean` interface provides three methods:

FactoryBean接口提供了三种方法

- `Object getObject()`: Returns an instance of the object this factory creates. The instance can possibly be shared, depending on whether this factory returns singletons or prototypes.

  对象getObject():返回此工厂创建的对象的实例。实例可能被共享，这取决于这个工厂返回的是单例还是原型。

- `boolean isSingleton()`: Returns `true` if this `FactoryBean` returns singletons or `false` otherwise.

  boolean isSingleton():如果FactoryBean返回单例，返回true;否则返回false。

- `Class getObjectType()`: Returns the object type returned by the `getObject()` method or `null` if the type is not known in advance.

  返回getObject()方法返回的对象类型，如果事先不知道该类型，则返回null。

The `FactoryBean` concept and interface is used in a number of places within the Spring Framework. More than 50 implementations of the `FactoryBean` interface ship with Spring itself.

FactoryBean的概念和接口在Spring框架的许多地方使用。Spring本身附带了50多个FactoryBean接口的实现。

When you need to ask a container for an actual `FactoryBean` instance itself instead of the bean it produces, preface the bean’s `id` with the ampersand symbol (`&`) when calling the `getBean()` method of the `ApplicationContext`. So, for a given `FactoryBean` with an `id` of `myBean`, invoking `getBean("myBean")` on the container returns the product of the `FactoryBean`, whereas invoking `getBean("&myBean")` returns the `FactoryBean` instance itself.

当您需要向容器请求实际的FactoryBean实例本身而不是它生成的bean时，在调用`ApplicationContext`的`getBean()`方法时，在`bean`的id前面加上&符号(&)。因此，对于id为`myBean`的给定`FactoryBean`，在容器上调用getBean(“myBean”)将返回FactoryBean的产品，而调用getBean(“&myBean”)将返回FactoryBean实例本身。

#### 1.9. Annotation-based Container Configuration 基于注释的容器配置

###### Are annotations better than XML for configuring Spring?

在配置Spring时，注释比XML更好吗?

> The introduction of annotation-based configuration raised the question of whether this approach is “better” than XML. The short answer is “it depends.” The long answer is that each approach has its pros and cons, and, usually, it is up to the developer to decide which strategy suits them better. Due to the way they are defined, annotations provide a lot of context in their declaration, leading to shorter and more concise configuration. However, XML excels at wiring up components without touching their source code or recompiling them. Some developers prefer having the wiring close to the source while others argue that annotated classes are no longer POJOs and, furthermore, that the configuration becomes decentralized and harder to control.
>
> 基于注释的配置的引入提出了这样一个问题:这种方法是否比XML更好?简短的回答是视情况而定。很长的答案是每种方法都有其优缺点，通常由开发人员决定哪种策略更适合他们。由于其定义方式，注释在其声明中提供了大量上下文，从而使配置更短、更简洁。但是，XML擅长在不改动源代码或重新编译组件的情况下连接组件。一些开发人员更喜欢连接到接近源代码的地方，而另一些人则认为带注释的类不再是pojo，而且配置变得分散且难以控制。
>
> 

> No matter the choice, Spring can accommodate both styles and even mix them together. It is worth pointing out that through its [JavaConfig](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java) option, Spring lets annotations be used in a non-invasive way, without touching the target components source code and that, in terms of tooling, all configuration styles are supported by the [Spring Tools for Eclipse](https://spring.io/tools).
>
> 无论选择什么，Spring都可以容纳这两种风格，甚至可以将它们混合在一起。值得指出的是，通过它的`JavaConfig`选项，Spring允许以一种非侵入性的方式使用注释，而不涉及目标组件的源代码，而且，就工具而言，Eclipse的Spring工具支持所有配置样式。

An alternative to XML setup is provided by annotation-based configuration, which relies on the bytecode metadata for wiring up components instead of angle-bracket declarations. Instead of using XML to describe a bean wiring, the developer moves the configuration into the component class itself by using annotations on the relevant class, method, or field declaration. As mentioned in [Example: The `RequiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-bpp-examples-rabpp), using a `BeanPostProcessor` in conjunction with annotations is a common means of extending the Spring IoC container. For example, Spring 2.0 introduced the possibility of enforcing required properties with the [`@Required`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-required-annotation) annotation. Spring 2.5 made it possible to follow that same general approach to drive Spring’s dependency injection. Essentially, the `@Autowired` annotation provides the same capabilities as described in [Autowiring Collaborators](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire) but with more fine-grained control and wider applicability. Spring 2.5 also added support for JSR-250 annotations, such as `@PostConstruct` and `@PreDestroy`. Spring 3.0 added support for JSR-330 (Dependency Injection for Java) annotations contained in the `javax.inject` package such as `@Inject` and `@Named`. Details about those annotations can be found in the [relevant section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-standard-annotations).

基于注释的配置提供了XML设置的另一种选择，它依赖字节码元数据来连接组件，而不是使用尖括号声明。开发人员没有使用XML来描述bean连接，而是通过在相关类、方法或字段声明上使用注释，将配置转移到组件类本身。如示例中所述:`RequiredAnnotationBeanPostProcessor`使用`BeanPostProcessor`与注释结合使用是扩展Spring IoC容器的常见方法。例如，Spring 2.0引入了使用`@Required`注释强制执行必需属性的可能性。Spring 2.5使遵循相同的通用方法来驱动Spring的依赖项注入成为可能。从本质上讲，`@Autowired`注解提供了与`Autowiring`协作器中描述的相同的功能，但是拥有更细粒度的控制和更广泛的适用性。Spring 2.5还增加了对`JSR-250`注释的支持，比如`@PostConstruct`和`@PreDestroy`。Spring 3.0增加了对`javax.inject`中包含的JSR-330 (Java依赖注入)注释的支持。注入包，如`@Inject`和`@Named`。有关这些注释的详细信息可以在相关部分找到。

|      | Annotation injection is performed before XML injection. Thus, the XML configuration overrides the annotations for properties wired through both approaches. |
| ---- | ------------------------------------------------------------ |
|      | 注释注入在XML注入之前执行。因此，XML配置覆盖了通过这两种方法连接的属性的注释。 |

As always, you can register them as individual bean definitions, but they can also be implicitly registered by including the following tag in an XML-based Spring configuration (notice the inclusion of the `context` namespace):

和往常一样，你可以将它们注册为单独的bean定义，但是也可以通过在基于xml的Spring配置中包含以下标签(注意包含`上下文名称空间`)来隐式地注册它们:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
</beans>
```

(The implicitly registered post-processors include [`AutowiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.html), [`CommonAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/CommonAnnotationBeanPostProcessor.html), [`PersistenceAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/orm/jpa/support/PersistenceAnnotationBeanPostProcessor.html), and the aforementioned [`RequiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/RequiredAnnotationBeanPostProcessor.html).)

隐式注册的后处理器包括[`AutowiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.html), [`CommonAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/CommonAnnotationBeanPostProcessor.html), [`PersistenceAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/orm/jpa/support/PersistenceAnnotationBeanPostProcessor.html)和前面提到的 [`RequiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/RequiredAnnotationBeanPostProcessor.html).

|      | `<context:annotation-config/>` only looks for annotations on beans in the same application context in which it is defined. This means that, if you put `<context:annotation-config/>` in a `WebApplicationContext` for a `DispatcherServlet`, it only checks for `@Autowired` beans in your controllers, and not your services. See [The DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet) for more information. |
| ---- | ------------------------------------------------------------ |
|      | `<context:annotation-config/>`只在定义它的应用程序上下文中的bean上寻找注解。这意味着，如果你在WebApplicationContext中为DispatcherServlet添加`<context:annotation-config/>`，它只检查控制器中的`@Autowired` bean，而不是你的服务。有关更多信息，请参阅DispatcherServlet。 |

##### 1.9.1. @Required

The `@Required` annotation applies to bean property setter methods, as in the following example:

The `@Required` 注解应用于bean属性setter方法，如下例所示:

Java

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Required
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

This annotation indicates that the affected bean property must be populated at configuration time, through an explicit property value in a bean definition or through autowiring. The container throws an exception if the affected bean property has not been populated. This allows for eager and explicit failure, avoiding `NullPointerException` instances or the like later on. We still recommend that you put assertions into the bean class itself (for example, into an init method). Doing so enforces those required references and values even when you use the class outside of a container.

这个注释指出，必须在配置时通过bean定义中的显式属性值或自动装配来填充`受影响的bean属性`。如果未填充受影响的bean属性，容器将抛出异常。这允许出现迫切的和显式的失败，避免了`NullPointerException实例`或以后出现类似的情况。我们仍然建议将断言放到bean类本身中(例如，放到`init`方法中)。这样做会强制执行那些必需的引用和值，即使您在容器之外使用该类。

> The `@Required` annotation is formally deprecated as of Spring Framework 5.1, in favor of using constructor injection for required settings (or a custom implementation of `InitializingBean.afterPropertiesSet()` along with bean property setter methods).
>
> 从Spring Framework 5.1开始，`@Required注释正式被弃用`，支持使用构造函数注入来进行必需的设置(或者使用`InitializingBean.afterPropertiesSet()`的自定义实现以及bean属性setter方法)。

##### 1.9.2. Using `@Autowired`

> JSR 330’s `@Inject` annotation can be used in place of Spring’s `@Autowired` annotation in the examples included in this section. See [here](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-standard-annotations) for more details.
>
> 在本节包含的示例中，JSR 330的`@Inject`注释可以用来替代Spring的`@Autowired`注释。更多细节请看这里。

You can apply the `@Autowired` annotation to constructors, as the following example shows:

Java

```java
public class MovieRecommender {

    private final CustomerPreferenceDao customerPreferenceDao;

    @Autowired
    public MovieRecommender(CustomerPreferenceDao customerPreferenceDao) {
        this.customerPreferenceDao = customerPreferenceDao;
    }

    // ...
}
```

> As of Spring Framework 4.3, an `@Autowired` annotation on such a constructor is no longer necessary if the target bean defines only one constructor to begin with. However, if several constructors are available and there is no primary/default constructor, at least one of the constructors must be annotated with `@Autowired` in order to instruct the container which one to use. See the discussion on [constructor resolution](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation-constructor-resolution) for details.
>
> 从Spring Framework 4.3开始，如果目标bean只定义了一个构造函数，就不再需要在构造函数上使用`@Autowired`注解。但是，如果有几个构造函数可用，并且没有`主/默认构造函数`，那么至少有一个构造函数必须用`@Autowired`注解，以指示容器使用哪一个。有关构造函数解析的详细信息，请参阅讨论。

You can also apply the `@Autowired` annotation to *traditional* setter methods, as the following example shows:

你也可以对传统的setter方法应用@Autowired注解，如下例所示:

Java

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Autowired
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

You can also apply the annotation to methods with arbitrary names and multiple arguments, as the following example shows:

你也可以将注释应用到具有任意名称和多个参数的方法上，如下面的例子所示:

Java

```java
public class MovieRecommender {

    private MovieCatalog movieCatalog;

    private CustomerPreferenceDao customerPreferenceDao;

    @Autowired
    public void prepare(MovieCatalog movieCatalog,
            CustomerPreferenceDao customerPreferenceDao) {
        this.movieCatalog = movieCatalog;
        this.customerPreferenceDao = customerPreferenceDao;
    }

    // ...
}
```

You can apply `@Autowired` to fields as well and even mix it with constructors, as the following example shows:

你也可以在字段上应用`@Autowired`，甚至可以和构造器混合使用，如下面的例子所示:

Java

```java
public class MovieRecommender {

    private final CustomerPreferenceDao customerPreferenceDao;

    @Autowired
    private MovieCatalog movieCatalog;

    @Autowired
    public MovieRecommender(CustomerPreferenceDao customerPreferenceDao) {
        this.customerPreferenceDao = customerPreferenceDao;
    }

    // ...
}
```

>Make sure that your target components (for example, `MovieCatalog` or `CustomerPreferenceDao`) are consistently declared by the type that you use for your `@Autowired`-annotated injection points. Otherwise, injection may fail due to a "no type match found" error at runtime.
>
>For XML-defined beans or component classes found via classpath scanning, the container usually knows the concrete type up front. However, for `@Bean` factory methods, you need to make sure that the declared return type is sufficiently expressive. For components that implement several interfaces or for components potentially referred to by their implementation type, consider declaring the most specific return type on your factory method (at least as specific as required by the injection points referring to your bean).
>
>确保您的目标组件(例如，`MovieCatalog`或`CustomerPreferenceDao`)由您为`@Autowire`注释的注入点使用的类型一致地声明。否则，注入可能会由于运行时出现“没有找到类型匹配”错误而失败。
>
>对于通过类路径扫描找到的xml定义的bean或组件类，容器通常预先知道具体的类型。但是，对于`@Bean`工厂方法，您需要确保声明的返回类型具有足够的表达能力。对于实现多个接口的组件，或者对于可能由其实现类型引用的组件，考虑在您的工厂方法上声明最特定的返回类型(至少与引用您的bean的注入点所要求的那样特定)。

You can also instruct Spring to provide all beans of a particular type from the `ApplicationContext` by adding the `@Autowired` annotation to a field or method that expects an array of that type, as the following example shows:

您还可以通过向需要该类型数组的字段或方法添加`@Autowired`注解来指示Spring从`ApplicationContext`中提供所有特定类型的bean，如下面的例子所示:

Java

```java
public class MovieRecommender {

    @Autowired
    private MovieCatalog[] movieCatalogs;

    // ...
}
```

The same applies for typed collections, as the following example shows:

Java

```java
public class MovieRecommender {

    private Set<MovieCatalog> movieCatalogs;

    @Autowired
    public void setMovieCatalogs(Set<MovieCatalog> movieCatalogs) {
        this.movieCatalogs = movieCatalogs;
    }

    // ...
}
```

>Your target beans can implement the `org.springframework.core.Ordered` interface or use the `@Order` or standard `@Priority` annotation if you want items in the array or list to be sorted in a specific order. Otherwise, their order follows the registration order of the corresponding target bean definitions in the container.
>
>您的目标bean可以实现`org.springframework.core.ordered`如果希望数组或列表中的项按特定顺序排序，则可以使用`@Order`或标准`@Priority`注释。否则，它们的顺序遵循容器中相应目标bean定义的注册顺序。
>
>You can declare the `@Order` annotation at the target class level and on `@Bean` methods, potentially for individual bean definitions (in case of multiple definitions that use the same bean class). `@Order` values may influence priorities at injection points, but be aware that they do not influence singleton startup order, which is an orthogonal concern determined by dependency relationships and `@DependsOn` declarations.
>
>您可以在目标类级别和`@Bean`方法上声明`@Order`注释，可能是针对单个bean定义(在使用相同bean类的多个定义的情况下)。`@Order`值可能会影响注入点的优先级，但是要注意它们不会影响单例启动顺序，单例启动顺序是由依赖关系和`@DependsOn`声明决定的正交关系。
>
>Note that the standard `javax.annotation.Priority` annotation is not available at the `@Bean` level, since it cannot be declared on methods. Its semantics can be modeled through `@Order` values in combination with `@Primary` on a single bean for each type.
>
>请注意标准`javax.annotation.Priority`注释在`@Bean`级别不可用，因为它不能在方法上声明。它的语义可以通过在每种类型的单个bean上结合@Order值和@Primary来建模。

Even typed `Map` instances can be autowired as long as the expected key type is `String`. The map values contain all beans of the expected type, and the keys contain the corresponding bean names, as the following example shows:

即使是类型化的Map实例也可以自动实现，只要期望的键类型是String。映射值包含所有期望类型的bean，键包含相应的bean名称，如下面的示例所示:

Java

```java
public class MovieRecommender {

    private Map<String, MovieCatalog> movieCatalogs;

    @Autowired
    public void setMovieCatalogs(Map<String, MovieCatalog> movieCatalogs) {
        this.movieCatalogs = movieCatalogs;
    }

    // ...
}
```

By default, autowiring fails when no matching candidate beans are available for a given injection point. In the case of a declared array, collection, or map, at least one matching element is expected.

The default behavior is to treat annotated methods and fields as indicating required dependencies. You can change this behavior as demonstrated in the following example, enabling the framework to skip a non-satisfiable injection point through marking it as non-required (i.e., by setting the `required` attribute in `@Autowired` to `false`):

默认情况下，当给定注入点没有匹配的候选bean可用时，自动装配将失败。对于已声明的数组、集合或映射，至少需要一个匹配元素。

默认行为是将带注释的方法和字段视为指示所需的依赖项。你可以改变这个行为，如下例所示，让框架跳过一个不满足的注入点，通过标记为非必需(例如，通过在@Autowired中设置required属性为false):

Java

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Autowired(required = false)
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

A non-required method will not be called at all if its dependency (or one of its dependencies, in case of multiple arguments) is not available. A non-required field will not get populated at all in such cases, leaving its default value in place.

Injected constructor and factory method arguments are a special case since the `required` attribute in `@Autowired` has a somewhat different meaning due to Spring’s constructor resolution algorithm that may potentially deal with multiple constructors. Constructor and factory method arguments are effectively required by default but with a few special rules in a single-constructor scenario, such as multi-element injection points (arrays, collections, maps) resolving to empty instances if no matching beans are available. This allows for a common implementation pattern where all dependencies can be declared in a unique multi-argument constructor — for example, declared as a single public constructor without an `@Autowired` annotation.

如果一个非必需的方法的依赖项(或者它的一个依赖项，在有多个参数的情况下)不可用，那么它将根本不会被调用。在这种情况下，将根本不填充非必需字段，保留其默认值。注入的构造函数和工厂方法参数是一种特殊情况，因为@Autowired中的必需属性有某种不同的含义，这是由于Spring的构造函数解析算法可能会处理多个构造函数。默认情况下，构造函数和工厂方法参数是有效的，但在单构造函数场景中有一些特殊规则，比如如果没有匹配的bean可用，则多元素注入点(数组、集合、映射)解析为空实例。这允许一种常见的实现模式，其中所有依赖可以在一个唯一的多参数构造函数中声明，例如，声明为单个公共构造函数，而不需要@Autowired注解。

> Only one constructor of any given bean class may declare `@Autowired` with the `required` attribute set to `true`, indicating *the* constructor to autowire when used as a Spring bean. As a consequence, if the `required` attribute is left at its default value `true`, only a single constructor may be annotated with `@Autowired`. If multiple constructors declare the annotation, they will all have to declare `required=false` in order to be considered as candidates for autowiring (analogous to `autowire=constructor` in XML). The constructor with the greatest number of dependencies that can be satisfied by matching beans in the Spring container will be chosen. If none of the candidates can be satisfied, then a primary/default constructor (if present) will be used. Similarly, if a class declares multiple constructors but none of them is annotated with `@Autowired`, then a primary/default constructor (if present) will be used. If a class only declares a single constructor to begin with, it will always be used, even if not annotated. Note that an annotated constructor does not have to be public.The `required` attribute of `@Autowired` is recommended over the deprecated `@Required` annotation on setter methods. Setting the `required` attribute to `false` indicates that the property is not required for autowiring purposes, and the property is ignored if it cannot be autowired. `@Required`, on the other hand, is stronger in that it enforces the property to be set by any means supported by the container, and if no value is defined, a corresponding exception is raised.
>
> 在任何给定bean类中，只有一个构造函数可以声明@Autowired，并将required属性设置为true，以指示当作为Spring bean使用时要自动装配的构造函数。因此，如果required属性的默认值为true，那么只有一个构造函数可以使用@Autowired注解。如果有多个构造函数声明注释，那么它们都必须声明required=false，才能被认为是自动装配的候选者(类似于XML中的autowire=constructor)。通过在Spring容器中匹配bean可以满足的依赖关系最多的构造函数将被选择。如果没有一个候选函数可以满足，那么将使用主/默认构造函数(如果存在)。类似地，如果一个类声明了多个构造函数，但是没有一个是用@Autowired注解的，那么一个主/默认构造函数(如果有的话)将会被使用。如果一个类只声明了一个构造函数，那么它将始终被使用，即使没有注释。请注意，带注释的构造函数不必是公共的。建议在setter方法上的已弃用的@Required注释上使用@Autowired属性。将required属性设置为false表示该属性对于自动装配目的是不需要的，并且如果该属性不能自动装配，则忽略它。另一方面，@Required更强，因为它强制用容器支持的任何方法设置属性，如果没有定义值，则会引发相应的异常。

Alternatively, you can express the non-required nature of a particular dependency through Java 8’s `java.util.Optional`, as the following example shows:

或者，您可以通过Java 8的Java .util表示特定依赖项的非必需性质。可选，如下面的示例所示

```java
public class SimpleMovieLister {

    @Autowired
    public void setMovieFinder(Optional<MovieFinder> movieFinder) {
        ...
    }
}
```

As of Spring Framework 5.0, you can also use a `@Nullable` annotation (of any kind in any package — for example, `javax.annotation.Nullable` from JSR-305) or just leverage Kotlin builtin null-safety support:

在Spring Framework 5.0中，还可以使用@Nullable注释(在任何包中使用任何类型的注释——例如javax.annotation)。或者只是利用Kotlin内置的空安全支持:

Java

```java
public class SimpleMovieLister {

    @Autowired
    public void setMovieFinder(@Nullable MovieFinder movieFinder) {
        ...
    }
}
```

You can also use `@Autowired` for interfaces that are well-known resolvable dependencies: `BeanFactory`, `ApplicationContext`, `Environment`, `ResourceLoader`, `ApplicationEventPublisher`, and `MessageSource`. These interfaces and their extended interfaces, such as `ConfigurableApplicationContext` or `ResourcePatternResolver`, are automatically resolved, with no special setup necessary. The following example autowires an `ApplicationContext` object:

你也可以使用`@Autowired`的接口是众所周知的可解析依赖:`BeanFactory`, `ApplicationContext`,` Environment`, `ResourceLoader`, `ApplicationEventPublishe`r，和`MessageSource`。这些接口及其扩展接口，如`ConfigurableApplicationContext`或`ResourcePatternResolver`，会自动解析，不需要特殊设置。下面的例子自动生成一个`ApplicationContext`对象:

Java

```java
public class MovieRecommender {

    @Autowired
    private ApplicationContext context;

    public MovieRecommender() {
    }

    // ...
}
```

> The `@Autowired`, `@Inject`, `@Value`, and `@Resource` annotations are handled by Spring `BeanPostProcessor` implementations. This means that you cannot apply these annotations within your own `BeanPostProcessor` or `BeanFactoryPostProcessor` types (if any). These types must be 'wired up' explicitly by using XML or a Spring `@Bean` method.
>
> `@Autowired`， `@Inject`， `@Value`，和`@Resource`注解是由Spring `BeanPostProcessor`实现处理的。这意味着您不能在自己的`BeanPostProcessor`或`BeanFactoryPostProcessor`类型(如果有的话)中应用这些注释。这些类型必须通过使用`XML`或Spring `@Bean`方法显式地“连接起来”。

##### 1.9.3. Fine-tuning Annotation-based Autowiring with `@Primary`

使用`@Primary`微调基于注释的自动装配

Because autowiring by type may lead to multiple candidates, it is often necessary to have more control over the selection process. One way to accomplish this is with Spring’s `@Primary` annotation. `@Primary` indicates that a particular bean should be given preference when multiple beans are candidates to be autowired to a single-valued dependency. If exactly one primary bean exists among the candidates, it becomes the autowired value.

由于按类型自动装配可能会导致多个候选，因此通常有必要对选择过程进行更多的控制。实现这一点的一种方法是使用Spring的`@Primary`注释。`@Primary`表示当多个bean是要自动传递到单值依赖项的候选bean时，应该优先给予特定bean。如果候选对象中恰好有一个主bean，它就成为自动生成的值。

Consider the following configuration that defines `firstMovieCatalog` as the primary `MovieCatalog`:

考虑以下配置，将`firstMovieCatalog`定义为主要的`MovieCatalog`:

Java

```java
@Configuration
public class MovieConfiguration {

    @Bean
    @Primary
    public MovieCatalog firstMovieCatalog() { ... }

    @Bean
    public MovieCatalog secondMovieCatalog() { ... }

    // ...
}
```

With the preceding configuration, the following `MovieRecommender` is autowired with the `firstMovieCatalog`:

通过以上配置，下面的`MovieRecommender`是使用`firstMovieCatalog`自动生成的:

Java

```java
public class MovieRecommender {

    @Autowired
    private MovieCatalog movieCatalog;

    // ...
}
```

The corresponding bean definitions follow:

对应的bean定义如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <bean class="example.SimpleMovieCatalog" primary="true">
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean id="movieRecommender" class="example.MovieRecommender"/>

</beans>
```

##### 1.9.4. Fine-tuning Annotation-based Autowiring with Qualifiers

微调基于注释的自动装配，使用限定符

`@Primary` is an effective way to use autowiring by type with several instances when one primary candidate can be determined. When you need more control over the selection process, you can use Spring’s `@Qualifier` annotation. You can associate qualifier values with specific arguments, narrowing the set of type matches so that a specific bean is chosen for each argument. In the simplest case, this can be a plain descriptive value, as shown in the following example:

当一个主要候选对象可以被确定时，`@Primary`是一种通过类型使用多个实例的自动装配的有效方法。当您需要更多地控制选择过程时，您可以使用Spring的`@Qualifier`注释。您可以将限定符值与特定的参数相关联，从而缩小类型匹配的范围，以便为每个参数选择特定的bean。在最简单的情况下，这可以是一个普通的描述性值，如下面的示例所示

Java

```java
public class MovieRecommender {

    @Autowired
    @Qualifier("main")
    private MovieCatalog movieCatalog;

    // ...
}
```

You can also specify the `@Qualifier` annotation on individual constructor arguments or method parameters, as shown in the following example:

您还可以在单独的构造函数参数或方法参数上指定`@Qualifier注释，如下面的例子所示:

Java

```java
public class MovieRecommender {

    private MovieCatalog movieCatalog;

    private CustomerPreferenceDao customerPreferenceDao;

    @Autowired
    public void prepare(@Qualifier("main") MovieCatalog movieCatalog,
            CustomerPreferenceDao customerPreferenceDao) {
        this.movieCatalog = movieCatalog;
        this.customerPreferenceDao = customerPreferenceDao;
    }

    // ...
}
```

The following example shows corresponding bean definitions.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <bean class="example.SimpleMovieCatalog">
        <qualifier value="main"/> 

        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <qualifier value="action"/> 

        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean id="movieRecommender" class="example.MovieRecommender"/>

</beans>
```

> The bean with the `main` qualifier value is wired with the constructor argument that is qualified with the same value.
>
> The bean with the `action` qualifier value is wired with the constructor argument that is qualified with the same value.
>
> 具有main限定符值的bean与使用相同值限定的构造函数参数连接。
>
> 具有action限定符值的bean与使用相同值限定的构造函数参数连接。

For a fallback match, the bean name is considered a default qualifier value. Thus, you can define the bean with an `id` of `main` instead of the nested qualifier element, leading to the same matching result. However, although you can use this convention to refer to specific beans by name, `@Autowired` is fundamentally about type-driven injection with optional semantic qualifiers. This means that qualifier values, even with the bean name fallback, always have narrowing semantics within the set of type matches. They do not semantically express a reference to a unique bean `id`. Good qualifier values are `main` or `EMEA` or `persistent`, expressing characteristics of a specific component that are independent from the bean `id`, which may be auto-generated in case of an anonymous bean definition such as the one in the preceding example.

对于回退匹配，bean名称被认为是默认的限定符值。因此，您可以使用`id`为`main`而不是嵌套的`qualifier`元素来定义bean，从而得到相同的匹配结果。然而，尽管您可以使用这种约定来按名称引用特定的bean，但`@Autowired`基本上是关于类型驱动的注入，带有可选的语义限定符。这意味着，即使使用了bean名称回退，限定符值也总是在类型匹配集中具有收缩语义。他们没有语义表达独特的bean的引用id。良好的限定符值`main,EMEA`或`persistent`,表达某个特定组件的特点是独立于bean` id,`这可能是在匿名的情况下自动生成的bean定义等在前面的一个例子。

Qualifiers also apply to typed collections, as discussed earlier — for example, to `Set<MovieCatalog>`. In this case, all matching beans, according to the declared qualifiers, are injected as a collection. This implies that qualifiers do not have to be unique. Rather, they constitute filtering criteria. For example, you can define multiple `MovieCatalog` beans with the same qualifier value “action”, all of which are injected into a `Set<MovieCatalog>` annotated with `@Qualifier("action")`.

限定符也适用于类型化集合，如前所述—例如，用于设置`set<MovieCatalog>`。在这种情况下，根据声明的限定符，所有匹配的bean都作为一个集合注入。这意味着限定符不必是惟一的。相反，它们构成了过滤标准。例如，您可以定义具有相同限定符值“action”的多个`MovieCatalog` bean，所有这些bean都被注入到带有`@Qualifier(“action”)`注释的`set<MovieCatalog>`集合中。

|      | Letting qualifier values select against target bean names, within the type-matching candidates, does not require a `@Qualifier` annotation at the injection point. If there is no other resolution indicator (such as a qualifier or a primary marker), for a non-unique dependency situation, Spring matches the injection point name (that is, the field name or parameter name) against the target bean names and choose the same-named candidate, if any. |
| ---- | ------------------------------------------------------------ |
|      | 让限定符值在类型匹配候选中针对目标bean名称进行选择，不需要在注入点使用@Qualifier注释。如果没有其他解析指示器(例如限定符或主标记)，对于非唯一依赖情况，Spring将注入点名称(即字段名称或参数名称)与目标bean名称匹配，并选择同名的候选对象(如果有的话)。 |

That said, if you intend to express annotation-driven injection by name, do not primarily use `@Autowired`, even if it is capable of selecting by bean name among type-matching candidates. Instead, use the JSR-250 `@Resource` annotation, which is semantically defined to identify a specific target component by its unique name, with the declared type being irrelevant for the matching process. `@Autowired` has rather different semantics: After selecting candidate beans by type, the specified `String` qualifier value is considered within those type-selected candidates only (for example, matching an `account` qualifier against beans marked with the same qualifier label).

也就是说，如果您打算通过名称表示注释驱动的注入，请不要主要使用`@Autowired`，即使它能够通过bean名称在类型匹配的候选者中进行选择。相反，使用JSR-250 `@Resource`注释，它在语义上定义为通过惟一名称标识特定的目标组件，声明的类型与匹配过程无关。`@Autowired`有相当不同的语义:在按类型选择候选bean之后，指定的字符串限定符值只在那些类型选择的候选者中被考虑(例如，将一个帐户限定符与用相同限定符标签标记的bean相匹配)。`

For beans that are themselves defined as a collection, `Map`, or array type, `@Resource` is a fine solution, referring to the specific collection or array bean by unique name. That said, as of 4.3, collection, you can match `Map`, and array types through Spring’s `@Autowired` type matching algorithm as well, as long as the element type information is preserved in `@Bean` return type signatures or collection inheritance hierarchies. In this case, you can use qualifier values to select among same-typed collections, as outlined in the previous paragraph.

对于本身定义为集合、映射或数组类型的bean， @Resource是一个很好的解决方案，它通过惟一的名称引用特定的集合或数组bean。也就是说，在4.3，集合中，您也可以通过Spring的@Autowired类型匹配算法来匹配Map和数组类型，只要元素类型信息在@Bean返回类型签名或集合继承层次结构中保留。在这种情况下，您可以使用限定符值在相同类型的集合中进行选择，如前一段所述。

As of 4.3, `@Autowired` also considers self references for injection (that is, references back to the bean that is currently injected). Note that self injection is a fallback. Regular dependencies on other components always have precedence. In that sense, self references do not participate in regular candidate selection and are therefore in particular never primary. On the contrary, they always end up as lowest precedence. In practice, you should use self references as a last resort only (for example, for calling other methods on the same instance through the bean’s transactional proxy). Consider factoring out the affected methods to a separate delegate bean in such a scenario. Alternatively, you can use `@Resource`, which may obtain a proxy back to the current bean by its unique name.

在4.3中，@Autowired也考虑注入的自我引用(也就是说，对当前被注入的bean的引用)。注意，self注入是一种退路。对其他组件的常规依赖始终具有优先级。从这个意义上说，自我参照不参与候选人的定期选择，因此，尤其从来没有初选。相反，它们总是优先级最低的。在实践中，您应该仅将self引用用作最后的手段(例如，通过bean s事务代理调用同一实例上的其他方法)。在这种情况下，考虑将受影响的方法分解为单独的委托bean。或者，您可以使用@Resource，它可以通过当前bean的惟一名称获得回它的代理。

|      | Trying to inject the results from `@Bean` methods on the same configuration class is effectively a self-reference scenario as well. Either lazily resolve such references in the method signature where it is actually needed (as opposed to an autowired field in the configuration class) or declare the affected `@Bean` methods as `static`, decoupling them from the containing configuration class instance and its lifecycle. Otherwise, such beans are only considered in the fallback phase, with matching beans on other configuration classes selected as primary candidates instead (if available). |
| ---- | ------------------------------------------------------------ |
|      | 尝试在同一个配置类上注入来自@Bean方法的结果实际上也是一个自引用场景。要么在实际需要的方法签名中惰性地解析这些引用(与配置类中的自动生成字段相反)，要么将受影响的@Bean方法声明为静态方法，将它们与包含的配置类实例及其生命周期解耦。否则，只在回退阶段考虑这样的bean，而选择其他配置类上的匹配bean作为主要候选对象(如果可用)。 |

`@Autowired` applies to fields, constructors, and multi-argument methods, allowing for narrowing through qualifier annotations at the parameter level. In contrast, `@Resource` is supported only for fields and bean property setter methods with a single argument. As a consequence, you should stick with qualifiers if your injection target is a constructor or a multi-argument method.

You can create your own custom qualifier annotations. To do so, define an annotation and provide the `@Qualifier` annotation within your definition, as the following example shows:

@Autowired适用于领域，构造器，和多参数方法，允许通过限定符注解在参数级别上缩小范围。相比之下，@Resource只支持具有单个参数的字段和bean属性设置器方法。因此，如果注入目标是构造函数或多参数方法，则应该坚持使用限定符。

您可以创建自己的自定义限定符注释。为此，定义一个注释并在您的定义中提供@Qualifier注释，如下例所示:

Java

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Genre {

    String value();
}
```

Then you can provide the custom qualifier on autowired fields and parameters, as the following example shows:

然后你可以在autowired字段和参数上提供自定义限定符，如下面的例子所示:

Java

```java
public class MovieRecommender {

    @Autowired
    @Genre("Action")
    private MovieCatalog actionCatalog;

    private MovieCatalog comedyCatalog;

    @Autowired
    public void setComedyCatalog(@Genre("Comedy") MovieCatalog comedyCatalog) {
        this.comedyCatalog = comedyCatalog;
    }

    // ...
}
```

Next, you can provide the information for the candidate bean definitions. You can add `<qualifier/>` tags as sub-elements of the `<bean/>` tag and then specify the `type` and `value` to match your custom qualifier annotations. The type is matched against the fully-qualified class name of the annotation. Alternately, as a convenience if no risk of conflicting names exists, you can use the short class name. The following example demonstrates both approaches:

接下来，您可以为候选bean定义提供信息。您可以添加<qualifier/>标记作为<bean/>标记的子元素，然后指定类型和值来匹配您的自定义qualifier注释。该类型与注释的全限定类名匹配。另外，为了方便起见，如果不存在名称冲突的风险，您可以使用简短的类名。下面的例子演示了这两种方法:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <bean class="example.SimpleMovieCatalog">
        <qualifier type="Genre" value="Action"/>
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <qualifier type="example.Genre" value="Comedy"/>
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean id="movieRecommender" class="example.MovieRecommender"/>

</beans>
```

In [Classpath Scanning and Managed Components](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-classpath-scanning), you can see an annotation-based alternative to providing the qualifier metadata in XML. Specifically, see [Providing Qualifier Metadata with Annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-scanning-qualifiers).

In some cases, using an annotation without a value may suffice. This can be useful when the annotation serves a more generic purpose and can be applied across several different types of dependencies. For example, you may provide an offline catalog that can be searched when no Internet connection is available. First, define the simple annotation, as the following example shows:

在类路径扫描和托管组件中，可以看到以XML提供限定符元数据的基于注释的替代方法。具体地说，请参见提供带有注释的限定符元数据。

在某些情况下，使用没有值的注释就足够了。当注释用于更通用的目的，并且可以跨几种不同类型的依赖项应用时，这一点非常有用。例如，您可以提供一个脱机目录，当没有可用的Internet连接时可以搜索该目录。首先，定义简单注释，如下例所示:

Java

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Offline {

}
```

Then add the annotation to the field or property to be autowired, as shown in the following example:

然后将注释添加到要自动实现的字段或属性，如下面的例子所示:

Java

```java
public class MovieRecommender {

    @Autowired
    @Offline 
    private MovieCatalog offlineCatalog;

    // ...
}
```

|      | This line adds the `@Offline` annotation. |
| ---- | ----------------------------------------- |
|      |                                           |

Now the bean definition only needs a qualifier `type`, as shown in the following example:

```xml
<bean class="example.SimpleMovieCatalog">
    <qualifier type="Offline"/> 
    <!-- inject any dependencies required by this bean -->
</bean>
```

|      | This element specifies the qualifier. |
| ---- | ------------------------------------- |
|      |                                       |

You can also define custom qualifier annotations that accept named attributes in addition to or instead of the simple `value` attribute. If multiple attribute values are then specified on a field or parameter to be autowired, a bean definition must match all such attribute values to be considered an autowire candidate. As an example, consider the following annotation definition:

您还可以定义自定义限定符注释，这些注释除了简单的value属性外，还接受指定的属性。如果在要自动装配的字段或参数上指定了多个属性值，那么bean定义必须匹配所有这些属性值，才能被认为是自动装配的候选者。作为一个例子，考虑下面的注释定义:

Java

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface MovieQualifier {

    String genre();

    Format format();
}
```

In this case `Format` is an enum, defined as follows:

Java

```java
public enum Format {
    VHS, DVD, BLURAY
}
```

The fields to be autowired are annotated with the custom qualifier and include values for both attributes: `genre` and `format`, as the following example shows:

要自动生成的字段用自定义限定符进行注释，并包括两个属性的值:genre和format，如下面的例子所示:

Java

```java
public class MovieRecommender {

    @Autowired
    @MovieQualifier(format=Format.VHS, genre="Action")
    private MovieCatalog actionVhsCatalog;

    @Autowired
    @MovieQualifier(format=Format.VHS, genre="Comedy")
    private MovieCatalog comedyVhsCatalog;

    @Autowired
    @MovieQualifier(format=Format.DVD, genre="Action")
    private MovieCatalog actionDvdCatalog;

    @Autowired
    @MovieQualifier(format=Format.BLURAY, genre="Comedy")
    private MovieCatalog comedyBluRayCatalog;

    // ...
}
```

Finally, the bean definitions should contain matching qualifier values. This example also demonstrates that you can use bean meta attributes instead of the `<qualifier/>` elements. If available, the `<qualifier/>` element and its attributes take precedence, but the autowiring mechanism falls back on the values provided within the `<meta/>` tags if no such qualifier is present, as in the last two bean definitions in the following example:

最后，bean定义应该包含匹配的限定符值。这个例子还演示了您可以使用bean元属性来代替<qualifier/>元素。如果可用，<qualifier/>元素及其属性优先，但是如果没有这样的限定符，自动装配机制就会回到<meta/>标签中提供的值上，就像下面例子中的最后两个bean定义一样:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <bean class="example.SimpleMovieCatalog">
        <qualifier type="MovieQualifier">
            <attribute key="format" value="VHS"/>
            <attribute key="genre" value="Action"/>
        </qualifier>
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <qualifier type="MovieQualifier">
            <attribute key="format" value="VHS"/>
            <attribute key="genre" value="Comedy"/>
        </qualifier>
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <meta key="format" value="DVD"/>
        <meta key="genre" value="Action"/>
        <!-- inject any dependencies required by this bean -->
    </bean>

    <bean class="example.SimpleMovieCatalog">
        <meta key="format" value="BLURAY"/>
        <meta key="genre" value="Comedy"/>
        <!-- inject any dependencies required by this bean -->
    </bean>

</beans>
```

##### 1.9.5. Using Generics as Autowiring Qualifiers

使用泛型作为自动装配限定符

In addition to the `@Qualifier` annotation, you can use Java generic types as an implicit form of qualification. For example, suppose you have the following configuration:

除了@Qualifier注释之外，您还可以使用Java泛型类型作为一种隐式的限定形式。例如，假设您有以下配置:

Java

```java
@Configuration
public class MyConfiguration {

    @Bean
    public StringStore stringStore() {
        return new StringStore();
    }

    @Bean
    public IntegerStore integerStore() {
        return new IntegerStore();
    }
}
```

Assuming that the preceding beans implement a generic interface, (that is, `Store<String>` and `Store<Integer>`), you can `@Autowire` the `Store` interface and the generic is used as a qualifier, as the following example shows:

假设前面的bean实现了一个通用接口(即，Store<String>和Store<Integer>)，您可以@Autowire Store接口，该通用被用作一个限定符，如下面的示例所示:

Java

```java
@Autowired
private Store<String> s1; // <String> qualifier, injects the stringStore bean

@Autowired
private Store<Integer> s2; // <Integer> qualifier, injects the integerStore bean
```

Generic qualifiers also apply when autowiring lists, `Map` instances and arrays. The following example autowires a generic `List`:

泛型限定符在自动装配列表、映射实例和数组时也适用。下面的例子自动生成一个通用列表:

Java

```java
// Inject all Store beans as long as they have an <Integer> generic
// Store<String> beans will not appear in this list
@Autowired
private List<Store<Integer>> s;
```

##### 1.9.6. Using `CustomAutowireConfigurer`

[`CustomAutowireConfigurer`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/CustomAutowireConfigurer.html) is a `BeanFactoryPostProcessor` that lets you register your own custom qualifier annotation types, even if they are not annotated with Spring’s `@Qualifier` annotation. The following example shows how to use `CustomAutowireConfigurer`:

`customautowiresfigurer`是一个`BeanFactoryPostProcessor`，它允许您注册自己的自定义限定符注释类型，即使它们没有使用Spring的@Qualifier注释。下面的例子展示了如何使用`customautowiresfigurer`:

```xml
<bean id="customAutowireConfigurer"
        class="org.springframework.beans.factory.annotation.CustomAutowireConfigurer">
    <property name="customQualifierTypes">
        <set>
            <value>example.CustomQualifier</value>
        </set>
    </property>
</bean>
```

The `AutowireCandidateResolver` determines autowire candidates by:

AutowireCandidateResolver决定autowire候选者的方法是:

- The `autowire-candidate` value of each bean definition

  每个bean定义的自动候选值

- Any `default-autowire-candidates` patterns available on the `<beans/>` element

  <beans/>元素上可用的任何默认自动加载候选模式

- The presence of `@Qualifier` annotations and any custom annotations registered with the `CustomAutowireConfigurer`

  @Qualifier注解和任何在customautowiresfigurer注册的自定义注解的存在

When multiple beans qualify as autowire candidates, the determination of a “primary” is as follows: If exactly one bean definition among the candidates has a `primary` attribute set to `true`, it is selected.

当多个bean符合自动装配候选时，确定“主”的方法如下:如果候选中恰好有一个bean定义的主属性设置为true，则选择它。

##### 1.9.7. Injection with `@Resource`

用`@Resource`注入

Spring also supports injection by using the JSR-250 `@Resource` annotation (`javax.annotation.Resource`) on fields or bean property setter methods. This is a common pattern in Java EE: for example, in JSF-managed beans and JAX-WS endpoints. Spring supports this pattern for Spring-managed objects as well.

Spring还通过在字段或bean属性设置器方法上使用`JSR-250 @Resource`注释来支持注入。这是Java EE中的常见模式:例如，在JSF-managed的bean和JAX-WS端点中。Spring也为Spring管理的对象支持此模式。

`@Resource` takes a name attribute. By default, Spring interprets that value as the bean name to be injected. In other words, it follows by-name semantics, as demonstrated in the following example:

`@Resource`具有name属性。默认情况下，Spring将该值解释为要注入的bean名。换句话说，它遵循了名称语义，如下面的示例所示:

Java

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Resource(name="myMovieFinder") 
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
}
```

> This line injects a `@Resource`.

If no name is explicitly specified, the default name is derived from the field name or setter method. In case of a field, it takes the field name. In case of a setter method, it takes the bean property name. The following example is going to have the bean named `movieFinder` injected into its setter method:

如果没有显式指定名称，则默认名称派生自字段名称或setter方法。对于字段，它采用字段名。对于setter方法，它采用bean属性名。下面的例子将把名为`movieFinder`的bean注入到它的setter方法中:

Java

```java
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Resource
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
}
```

|      | The name provided with the annotation is resolved as a bean name by the `ApplicationContext` of which the `CommonAnnotationBeanPostProcessor` is aware. The names can be resolved through JNDI if you configure Spring’s [`SimpleJndiBeanFactory`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/jndi/support/SimpleJndiBeanFactory.html) explicitly. However, we recommend that you rely on the default behavior and use Spring’s JNDI lookup capabilities to preserve the level of indirection. |
| ---- | ------------------------------------------------------------ |
|      | 随注释提供的名称由`CommonAnnotationBeanPostProcessor`感知的ApplicationContext解析为一个bean名称。如果显式配置Spring的`SimpleJndiBeanFactory`，则可以通过JNDI解析名称。但是，我们建议您依赖于默认行为并使用Spring的`JNDI`查找功能来保持间接级别。 |

In the exclusive case of `@Resource` usage with no explicit name specified, and similar to `@Autowired`, `@Resource` finds a primary type match instead of a specific named bean and resolves well known resolvable dependencies: the `BeanFactory`, `ApplicationContext`, `ResourceLoader`, `ApplicationEventPublisher`, and `MessageSource` interfaces.

在使用@Resource没有指定显式名称的独占情况下，类似于`@Autowired， @Resource`找到一个主类型匹配而不是一个特定的命名bean，并解析众所周知的可解析依赖项:`BeanFactory`,` ApplicationContext, ResourceLoader, ApplicationEventPublisher`，和`MessageSource`接口。

Thus, in the following example, the `customerPreferenceDao` field first looks for a bean named "customerPreferenceDao" and then falls back to a primary type match for the type `CustomerPreferenceDao`:

因此，在下例中，`customerPreferenceDao`字段首先查找名为`“customerPreferenceDao”`的bean，然后返回到与类型`customerPreferenceDao`匹配的主类型:

Java

```java
public class MovieRecommender {

    @Resource
    private CustomerPreferenceDao customerPreferenceDao;

    @Resource
    private ApplicationContext context; 

    public MovieRecommender() {
    }

    // ...
}
```

|      | The `context` field is injected based on the known resolvable dependency type: `ApplicationContext`. |
| ---- | ------------------------------------------------------------ |
|      | 上下文字段是基于已知的可解析依赖类型注入的:                  |

##### 1.9.8. Using `@Value`

`@Value` is typically used to inject externalized properties:

`@Value`通常用于注入外部化属性:

Java

```java
@Component
public class MovieRecommender {

    private final String catalog;

    public MovieRecommender(@Value("${catalog.name}") String catalog) {
        this.catalog = catalog;
    }
}
```

With the following configuration:

Java

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig { }
```

And the following `application.properties` file:

```java
catalog.name=MovieCatalog
```

In that case, the `catalog` parameter and field will be equal to the `MovieCatalog` value.

A default lenient embedded value resolver is provided by Spring. It will try to resolve the property value and if it cannot be resolved, the property name (for example `${catalog.name}`) will be injected as the value. If you want to maintain strict control over nonexistent values, you should declare a `PropertySourcesPlaceholderConfigurer` bean, as the following example shows:

在这种情况下，目录参数和字段将等于`MovieCatalog`值。

Spring提供了缺省的宽松嵌入式值解析器。它将尝试解析属性值，如果无法解析，将注入属性名(例如`${catalog.name}`)作为值。如果你想对不存在的值保持严格的控制，你应该声明一个`PropertySourcesPlaceholderConfigurer` bean，如下面的例子所示:

Java

```java
@Configuration
public class AppConfig {

     @Bean
     public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
           return new PropertySourcesPlaceholderConfigurer();
     }
}
```

|      | When configuring a `PropertySourcesPlaceholderConfigurer` using JavaConfig, the `@Bean` method must be `static`. |
| ---- | ------------------------------------------------------------ |
|      | 当使用JavaConfig配置`PropertySourcesPlaceholderConfigurer`时，@Bean方法必须是静态的。 |

Using the above configuration ensures Spring initialization failure if any `${}` placeholder could not be resolved. It is also possible to use methods like `setPlaceholderPrefix`, `setPlaceholderSuffix`, or `setValueSeparator` to customize placeholders.

如果无法解决任何${}占位符，使用上述配置将确保Spring初始化失败。也可以使用`setPlaceholderPrefix`、`setPlaceholderSuffix`或`setValueSeparator`等方法来定制占位符。

|      | Spring Boot configures by default a `PropertySourcesPlaceholderConfigurer` bean that will get properties from `application.properties` and `application.yml` files. |
| ---- | ------------------------------------------------------------ |
|      | Spring引导(boot)默认配置一个`PropertySourcesPlaceholderConfigurer` bean，该bean将从`application.properties` ,`application.yml`中获取属性。 |

Built-in converter support provided by Spring allows simple type conversion (to `Integer` or `int` for example) to be automatically handled. Multiple comma-separated values can be automatically converted to String array without extra effort.

It is possible to provide a default value as following:

Spring提供的内置转换器支持允许自动处理简单类型转换(例如到整型或整型)。`多个逗号分隔的值`可以自动转换为`字符串数组`，而不需要额外的努力。

可以提供如下默认值:

Java

```java
@Component
public class MovieRecommender {

    private final String catalog;

    public MovieRecommender(@Value("${catalog.name:defaultCatalog}") String catalog) {
        this.catalog = catalog;
    }
}
```

A Spring `BeanPostProcessor` uses a `ConversionService` behind the scene to handle the process for converting the String value in `@Value` to the target type. If you want to provide conversion support for your own custom type, you can provide your own `ConversionService` bean instance as the following example shows:

Spring `BeanPostProcessor`在后台使用`ConversionService`来处理将`@Value`中的字符串值转换为目标类型的过程。如果你想为自己的自定义类型提供转换支持，你可以提供自己的`ConversionService` bean实例，如下面的例子所示:

Java

```java
@Configuration
public class AppConfig {

    @Bean
    public ConversionService conversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new MyCustomConverter());
        return conversionService;
    }
}
```

When `@Value` contains a [`SpEL` expression](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions) the value will be dynamically computed at runtime as the following example shows:

当@Value包含SpEL表达式时，该值将在运行时动态计算，如下例所示:

Java

```java
@Component
public class MovieRecommender {

    private final String catalog;

    public MovieRecommender(@Value("#{systemProperties['user.catalog'] + 'Catalog' }") String catalog) {
        this.catalog = catalog;
    }
}
```

SpEL also enables the use of more complex data structures:

SpEL还允许使用更复杂的数据结构:

Java

```java
@Component
public class MovieRecommender {

    private final Map<String, Integer> countOfMoviesPerCatalog;

    public MovieRecommender(
            @Value("#{{'Thriller': 100, 'Comedy': 300}}") Map<String, Integer> countOfMoviesPerCatalog) {
        this.countOfMoviesPerCatalog = countOfMoviesPerCatalog;
    }
}
```

##### 1.9.9. Using `@PostConstruct` and `@PreDestroy`

The `CommonAnnotationBeanPostProcessor` not only recognizes the `@Resource` annotation but also the JSR-250 lifecycle annotations: `javax.annotation.PostConstruct` and `javax.annotation.PreDestroy`. Introduced in Spring 2.5, the support for these annotations offers an alternative to the lifecycle callback mechanism described in [initialization callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean) and [destruction callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-disposablebean). Provided that the `CommonAnnotationBeanPostProcessor` is registered within the Spring `ApplicationContext`, a method carrying one of these annotations is invoked at the same point in the lifecycle as the corresponding Spring lifecycle interface method or explicitly declared callback method. In the following example, the cache is pre-populated upon initialization and cleared upon destruction:

`CommonAnnotationBeanPostProcessor`不仅识别`@Resource`注释，还识别JSR-250生命周期注释:`javax.annotation.PostConstruct` `javax.annotation.PreDestroy`。在spring2.5中引入的对这些注释的支持为`初始化回调`和`销毁回调`中描述的生命周期回调机制提供了另一种选择。如果在Spring `ApplicationContext`中注册了`CommonAnnotationBeanPostProcessor`，那么在生命周期中与对应的Spring`生命周期接口方法`或`显式声明的回调方法`在同一点调用`携带这些注释之一的方法`。在下面的示例中，缓存在初始化时被预填充，在销毁时被清除

Java

```java
public class CachingMovieLister {

    @PostConstruct
    public void populateMovieCache() {
        // populates the movie cache upon initialization...
    }

    @PreDestroy
    public void clearMovieCache() {
        // clears the movie cache upon destruction...
    }
}
```

For details about the effects of combining various lifecycle mechanisms, see [Combining Lifecycle Mechanisms](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-combined-effects).

有关组合各种生命周期机制的效果的详细信息，请参见组合生命周期机制。

|      | Like `@Resource`, the `@PostConstruct` and `@PreDestroy` annotation types were a part of the standard Java libraries from JDK 6 to 8. However, the entire `javax.annotation` package got separated from the core Java modules in JDK 9 and eventually removed in JDK 11. If needed, the `javax.annotation-api` artifact needs to be obtained via Maven Central now, simply to be added to the application’s classpath like any other library. |
| ---- | ------------------------------------------------------------ |
|      | 与@Resource一样，@PostConstruct和@PreDestroy注释类型也是JDK 6到8中标准Java库的一部分。但是，整个javax。JDK 9中的注释包与核心Java模块分离，最终在JDK 11中被删除。如果需要，可以使用javax。现在需要通过Maven Central获得注释api构件，只需像其他库一样将其添加到应用程序的类路径即可。 |

#### 1.10. Classpath Scanning and Managed Components 类路径扫描和托管组件

Most examples in this chapter use XML to specify the configuration metadata that produces each `BeanDefinition` within the Spring container. The previous section ([Annotation-based Container Configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config)) demonstrates how to provide a lot of the configuration metadata through source-level annotations. Even in those examples, however, the “base” bean definitions are explicitly defined in the XML file, while the annotations drive only the dependency injection. This section describes an option for implicitly detecting the candidate components by scanning the classpath. Candidate components are classes that match against a filter criteria and have a corresponding bean definition registered with the container. This removes the need to use XML to perform bean registration. Instead, you can use annotations (for example, `@Component`), AspectJ type expressions, or your own custom filter criteria to select which classes have bean definitions registered with the container.

本章中的大多数示例使用XML指定在Spring容器中生成每个`BeanDefinition`的配置元数据。上一节(基于注释的容器配置)演示了如何通过源代码级注释提供大量配置元数据。然而，即使在这些示例中，基本bean定义也显式地定义在XML文件中，而注释仅驱动依赖项注入。本节描述通过扫描类路径隐式检测候选组件的选项。候选组件是与筛选标准匹配的类，并有相应的bean定义注册到容器中。这样就不需要使用XML来执行bean注册。相反，您可以使用注释(例如`@Component`)、`AspectJ`类型表达式或您自己的自定义筛选标准来选择哪些类已经向容器注册了bean定义。

> Starting with Spring 3.0, many features provided by the Spring JavaConfig project are part of the core Spring Framework. This allows you to define beans using Java rather than using the traditional XML files. Take a look at the `@Configuration`, `@Bean`, `@Import`, and `@DependsOn` annotations for examples of how to use these new features.
>
> 从Spring 3.0开始，Spring JavaConfig项目提供的许多特性都是核心Spring框架的一部分。这允许您使用Java而不是传统的XML文件定义bean。查看@Configuration、@Bean、@Import和@DependsOn注释，了解如何使用这些新特性。

##### 1.10.1. `@Component` and Further Stereotype Annotations

`@Component`以及进一步的原型注释

The `@Repository` annotation is a marker for any class that fulfills the role or stereotype of a repository (also known as Data Access Object or DAO). Among the uses of this marker is the automatic translation of exceptions, as described in [Exception Translation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#orm-exception-translation).

`@Repository`注释是满足存储库角色或构造型的任何类的标记(也称为数据访问对象或DAO)。该标记的使用包括异常的自动翻译，如异常翻译中所述。

Spring provides further stereotype annotations: `@Component`, `@Service`, and `@Controller`. `@Component` is a generic stereotype for any Spring-managed component. `@Repository`, `@Service`, and `@Controller` are specializations of `@Component` for more specific use cases (in the persistence, service, and presentation layers, respectively). Therefore, you can annotate your component classes with `@Component`, but, by annotating them with `@Repository`, `@Service`, or `@Controller` instead, your classes are more properly suited for processing by tools or associating with aspects. For example, these stereotype annotations make ideal targets for pointcuts. `@Repository`, `@Service`, and `@Controller` can also carry additional semantics in future releases of the Spring Framework. Thus, if you are choosing between using `@Component` or `@Service` for your service layer, `@Service` is clearly the better choice. Similarly, as stated earlier, `@Repository` is already supported as a marker for automatic exception translation in your persistence layer.

Spring提供了进一步的构造型注解:`@Component`，` @Service`和`@Controller`。`@Component`是任何spring管理组件的通用原型。`@Repository`、`@Service和@Controller`是针对更具体用例(分别在持久性、服务和表示层中)的@Component的专门化。因此，您可以使用@Component来注释组件类，但是，通过使用@Repository、@Service或@Controller来注释它们，您的类更适合通过工具进行处理或与方面关联。例如，这些构造型注释是切入点的理想目标。`@Repository、@Service和@Controller`还可以在Spring框架的未来版本中附带额外的语义。因此，如果您要在您的服务层使用`@Component或@Service之`间进行选择，那么@Service显然是更好的选择。类似地，如前所述，`@Repository`已经被支持作为持久化层中自动异常转换的标记。

##### 1.10.2. Using Meta-annotations and Composed Annotations

使用元注释和组合注释

Many of the annotations provided by Spring can be used as meta-annotations in your own code. A meta-annotation is an annotation that can be applied to another annotation. For example, the `@Service` annotation mentioned [earlier](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-stereotype-annotations) is meta-annotated with `@Component`, as the following example shows:

Spring提供的许多注释都可以在您自己的代码中用作元注释。元注释是可以应用于另一个注释的注释。例如，前面提到的`@Service`注释是用`@Component`进行元注释的，如下面的示例所示:

Java

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component 
public @interface Service {

    // ...
}
```

> The `Component` causes `@Service` to be treated in the same way as `@Component`.

You can also combine meta-annotations to create “composed annotations”. For example, the `@RestController` annotation from Spring MVC is composed of `@Controller` and `@ResponseBody`.

In addition, composed annotations can optionally redeclare attributes from meta-annotations to allow customization. This can be particularly useful when you want to only expose a subset of the meta-annotation’s attributes. For example, Spring’s `@SessionScope` annotation hardcodes the scope name to `session` but still allows customization of the `proxyMode`. The following listing shows the definition of the `SessionScope` annotation:

您还可以组合元注释来创建复合注释。例如，Spring MVC的`@RestController`注释由`@Controller`和`@ResponseBody`组成。此外，组合注释可以选择性地从元注释中重新声明属性，从而允许定制。当您只想公开元注释属性的一个子集时，这可能特别有用。例如，Spring s` @SessionScope`注释将作用域名称硬编码为session，但仍然允许定制`proxyMode`。下面的清单显示了`SessionScope`注释的定义

Java

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(WebApplicationContext.SCOPE_SESSION)
public @interface SessionScope {

    /**
     * Alias for {@link Scope#proxyMode}.
     * <p>Defaults to {@link ScopedProxyMode#TARGET_CLASS}.
     */
    @AliasFor(annotation = Scope.class)
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;

}
```

You can then use `@SessionScope` without declaring the `proxyMode` as follows:

然后你可以使用@SessionScope而不用像下面这样声明proxyMode:

Java

```java
@Service
@SessionScope
public class SessionScopedService {
    // ...
}
```

You can also override the value for the `proxyMode`, as the following example shows:

也可以覆盖proxyMode的值，如下例所示:

Java

```java
@Service
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class SessionScopedUserService implements UserService {
    // ...
}
```

For further details, see the [Spring Annotation Programming Model](https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model) wiki page.

有关更多细节，请参见Spring注释编程模型wiki页面。

##### 1.10.3. Automatically Detecting Classes and Registering Bean Definitions

自动检测类并注册Bean定义

Spring can automatically detect stereotyped classes and register corresponding `BeanDefinition` instances with the `ApplicationContext`. For example, the following two classes are eligible for such autodetection:

Spring可以自动检测原型类，并向ApplicationContext注册相应的BeanDefinition实例。例如，以下两个类符合这种自动检测的条件:

Java

```java
@Service
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
}
```

Java

```java
@Repository
public class JpaMovieFinder implements MovieFinder {
    // implementation elided for clarity
}
```

To autodetect these classes and register the corresponding beans, you need to add `@ComponentScan` to your `@Configuration` class, where the `basePackages` attribute is a common parent package for the two classes. (Alternatively, you can specify a comma- or semicolon- or space-separated list that includes the parent package of each class.)

要自动检测这些类并注册相应的bean，您需要将`@ComponentScan`添加到您的`@Configuration`类中，其中`basePackages`属性是这两个类的公共父包。(或者，您可以指定一个逗号、分号或空格分隔的列表，其中包含每个类的父包。)

Java

```java
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig  {
    // ...
}
```

|      | For brevity, the preceding example could have used the `value` attribute of the annotation (that is, `@ComponentScan("org.example")`). |
| ---- | ------------------------------------------------------------ |
|      | 为简便起见，前面的示例可以使用注释的value属性(即@ComponentScan("org.example"))。 |

The following alternative uses XML:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="org.example"/>

</beans>
```

|      | The use of `<context:component-scan>` implicitly enables the functionality of `<context:annotation-config>`. There is usually no need to include the `<context:annotation-config>` element when using `<context:component-scan>`. |
| ---- | ------------------------------------------------------------ |
|      | 使用`<context:component-scan>`隐式地启用了`<context:annotation-config>`的功能。在使用`<context: componentent -scan>`时，通常不需要包含`<context:annotation-config>`元素。 |

|      | The scanning of classpath packages requires the presence of corresponding directory entries in the classpath. When you build JARs with Ant, make sure that you do not activate the files-only switch of the JAR task. Also, classpath directories may not be exposed based on security policies in some environments — for example, standalone apps on JDK 1.7.0_45 and higher (which requires 'Trusted-Library' setup in your manifests — see https://stackoverflow.com/questions/19394570/java-jre-7u45-breaks-classloader-getresources). |
| ---- | ------------------------------------------------------------ |
|      | On JDK 9’s module path (Jigsaw), Spring’s classpath scanning generally works as expected. However, make sure that your component classes are exported in your `module-info` descriptors. If you expect Spring to invoke non-public members of your classes, make sure that they are 'opened' (that is, that they use an `opens` declaration instead of an `exports` declaration in your `module-info` descriptor). |
|      | 类路径包的扫描要求类路径中存在相应的目录项。当您使用Ant构建JAR时，请确保没有激活JAR任务的文件切换。另外，在某些环境中，基于安全策略可能不会公开类路径目录，例如JDK 1.7.0_45或更高版本上的独立应用程序(需要在清单中设置'Trusted-Library'，请参见https://stackoverflow.com/questions/19394570/java-jre-7u45-breaks-classloader-getresources)。在JDK 9的模块路径(Jigsaw)上，Spring的类路径扫描通常按预期工作。但是，请确保将组件类导出到模块信息描述符中。如果您希望Spring调用类的非公共成员，请确保它们是“打开的”(也就是说，它们在模块信息描述符中使用open声明而不是export声明)。 |

Furthermore, the `AutowiredAnnotationBeanPostProcessor` and `CommonAnnotationBeanPostProcessor` are both implicitly included when you use the component-scan element. That means that the two components are autodetected and wired together — all without any bean configuration metadata provided in XML.

此外，当您使用组件扫描元素时，`AutowiredAnnotationBeanPostProcessor`和`CommonAnnotationBeanPostProcessor`都是隐式包含的。这意味着这两个组件是自动检测并连接在一起的——所有这些都不需要XML提供任何bean配置元数据。

|      | You can disable the registration of `AutowiredAnnotationBeanPostProcessor` and `CommonAnnotationBeanPostProcessor` by including the `annotation-config` attribute with a value of `false`. |
| ---- | ------------------------------------------------------------ |
|      | 你可以禁用`AutowiredAnnotationBeanPostProcessor和CommonAnnotationBeanPostProcessor`的注册，方法是将注解-配置属性的值设为false |

##### 1.10.4. Using Filters to Customize Scanning

使用过滤器自定义扫描

By default, classes annotated with `@Component`, `@Repository`, `@Service`, `@Controller`, `@Configuration`, or a custom annotation that itself is annotated with `@Component` are the only detected candidate components. However, you can modify and extend this behavior by applying custom filters. Add them as `includeFilters` or `excludeFilters` attributes of the `@ComponentScan` annotation (or as `<context:include-filter />` or `<context:exclude-filter />` child elements of the `<context:component-scan>` element in XML configuration). Each filter element requires the `type` and `expression` attributes. The following table describes the filtering options:

默认情况下，仅检测到用@Component、@Repository、@Service、@Controller、@Configuration注释的类或本身用@Component注释的自定义注释。但是，您可以通过应用自定义筛选器来修改和扩展此行为。添加它们作为`@ComponentScan`注释的`includeFilters或excludeFilters`属性(或作为`<context:include-filter /><context:exclude-filter />`上下文:排他过滤器/比;上下文的子元素`<context:component-scan>`)。每个筛选器元素都需要类型和表达式属性。下表描述了筛选选项

| Filter Type          | Example Expression           | Description                                                  |
| :------------------- | :--------------------------- | :----------------------------------------------------------- |
| annotation (default) | `org.example.SomeAnnotation` | An annotation to be *present* or *meta-present* at the type level in target components. |
|                      |                              | 在目标组件的类型级别上呈现或元呈现的注释。                   |
| assignable           | `org.example.SomeClass`      | A class (or interface) that the target components are assignable to (extend or implement). |
|                      |                              | 目标组件可分配给(扩展或实现)的类(或接口)。                   |
| aspectj              | `org.example..*Service+`     | An AspectJ type expression to be matched by the target components. |
|                      |                              | 目标组件匹配的AspectJ类型表达式                              |
| regex                | `org\.example\.Default.*`    | A regex expression to be matched by the target components' class names. |
|                      |                              | 与目标组件的类名匹配的正则表达式。                           |
| custom               | `org.example.MyTypeFilter`   | A custom implementation of the `org.springframework.core.type.TypeFilter` interface. |
|                      |                              | org.springframework.core.type的自定义实现。TypeFilter接口。  |

The following example shows the configuration ignoring all `@Repository` annotations and using “stub” repositories instead:

下面的示例显示了忽略所有@Repository注释并使用“存根”存储库的配置:

Java

```java
@Configuration
@ComponentScan(basePackages = "org.example",
        includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
        excludeFilters = @Filter(Repository.class))
public class AppConfig {
    ...
}
```

The following listing shows the equivalent XML:

```xml
<beans>
    <context:component-scan base-package="org.example">
        <context:include-filter type="regex"
                expression=".*Stub.*Repository"/>
        <context:exclude-filter type="annotation"
                expression="org.springframework.stereotype.Repository"/>
    </context:component-scan>
</beans>
```

|      | You can also disable the default filters by setting `useDefaultFilters=false` on the annotation or by providing `use-default-filters="false"` as an attribute of the `<component-scan/>` element. This effectively disables automatic detection of classes annotated or meta-annotated with `@Component`, `@Repository`, `@Service`, `@Controller`, `@RestController`, or `@Configuration`. |
| ---- | ------------------------------------------------------------ |
|      | 您还可以通过在注释上设置`useDefaultFilters=false`来禁用默认过滤器，或者通过提供`use-default-filters="false"`作为<component-scan/>元素的属性来禁用默认过滤器。这有效地禁止了对用@Component、@Repository、@Service、@Controller、@RestController或@Configuration注释或元注释的类的自动检测。 |
|      |                                                              |

##### 1.10.5. Defining Bean Metadata within Components

在组件中定义Bean元数据

Spring components can also contribute bean definition metadata to the container. You can do this with the same `@Bean` annotation used to define bean metadata within `@Configuration` annotated classes. The following example shows how to do so:

Spring组件还可以向容器提供bean定义元数据。您可以使用与在@Configuration注释类中定义bean元数据相同的@Bean注释来做到这一点。下面的例子展示了如何做到这一点:

Java

```java
@Component
public class FactoryMethodComponent {

    @Bean
    @Qualifier("public")
    public TestBean publicInstance() {
        return new TestBean("publicInstance");
    }

    public void doWork() {
        // Component method implementation omitted
    }
}
```

The preceding class is a Spring component that has application-specific code in its `doWork()` method. However, it also contributes a bean definition that has a factory method referring to the method `publicInstance()`. The `@Bean` annotation identifies the factory method and other bean definition properties, such as a qualifier value through the `@Qualifier` annotation. Other method-level annotations that can be specified are `@Scope`, `@Lazy`, and custom qualifier annotations.

前面的类是一个Spring组件，在它的doWork()方法中有特定于应用程序的代码。但是，它也提供了一个bean定义，其中有一个引用方法publicInstance()的工厂方法。@Bean注释标识工厂方法和其他bean定义属性，例如通过@Qualifier注释标识的限定符值。其他可以指定的方法级注释是@Scope、@Lazy和自定义限定符注释。

|      | In addition to its role for component initialization, you can also place the `@Lazy` annotation on injection points marked with `@Autowired` or `@Inject`. In this context, it leads to the injection of a lazy-resolution proxy. |
| ---- | ------------------------------------------------------------ |
|      | 除了它用于组件初始化的角色之外，您还可以将@Lazy注释放在用@Autowired或@Inject标记的注入点上。在这个上下文中，它会导致注入一个延迟解析代理。 |

Autowired fields and methods are supported, as previously discussed, with additional support for autowiring of `@Bean` methods. The following example shows how to do so:

如前所述，支持自动生成的字段和方法，另外还支持@Bean方法的自动生成。下面的例子展示了如何做到这一点:

Java

```java
@Component
public class FactoryMethodComponent {

    private static int i;

    @Bean
    @Qualifier("public")
    public TestBean publicInstance() {
        return new TestBean("publicInstance");
    }

    // use of a custom qualifier and autowiring of method parameters
    @Bean
    protected TestBean protectedInstance(
            @Qualifier("public") TestBean spouse,
            @Value("#{privateInstance.age}") String country) {
        TestBean tb = new TestBean("protectedInstance", 1);
        tb.setSpouse(spouse);
        tb.setCountry(country);
        return tb;
    }

    @Bean
    private TestBean privateInstance() {
        return new TestBean("privateInstance", i++);
    }

    @Bean
    @RequestScope
    public TestBean requestScopedInstance() {
        return new TestBean("requestScopedInstance", 3);
    }
}
```

The example autowires the `String` method parameter `country` to the value of the `age` property on another bean named `privateInstance`. A Spring Expression Language element defines the value of the property through the notation `#{ <expression> }`. For `@Value` annotations, an expression resolver is preconfigured to look for bean names when resolving expression text.

As of Spring Framework 4.3, you may also declare a factory method parameter of type `InjectionPoint` (or its more specific subclass: `DependencyDescriptor`) to access the requesting injection point that triggers the creation of the current bean. Note that this applies only to the actual creation of bean instances, not to the injection of existing instances. As a consequence, this feature makes most sense for beans of prototype scope. For other scopes, the factory method only ever sees the injection point that triggered the creation of a new bean instance in the given scope (for example, the dependency that triggered the creation of a lazy singleton bean). You can use the provided injection point metadata with semantic care in such scenarios. The following example shows how to use `InjectionPoint`:

该示例将字符串方法参数country自动转换为另一个名为privateInstance的bean上的age属性的值。一个Spring Expression语言元素通过符号#{&lt;expression&gt;来定义属性的值。}。对于@Value注释，表达式解析器被预先配置为在解析表达式文本时查找bean名称。在Spring Framework 4.3中，您还可以声明一个类型为InjectionPoint的工厂方法参数(或者它更具体的子类:DependencyDescriptor)来访问触发当前bean创建的请求注入点。注意，这只适用于bean实例的实际创建，不适用于现有实例的注入。因此，这个特性对原型范围的bean最有意义。对于其他范围，工厂方法只能看到在给定范围内触发新bean实例创建的注入点(例如，触发惰性单例bean创建的依赖项)。您可以在这样的场景中谨慎使用提供的注入点元数据。下面的示例展示了如何使用InjectionPoint

Java

```java
@Component
public class FactoryMethodComponent {

    @Bean @Scope("prototype")
    public TestBean prototypeInstance(InjectionPoint injectionPoint) {
        return new TestBean("prototypeInstance for " + injectionPoint.getMember());
    }
}
```

The `@Bean` methods in a regular Spring component are processed differently than their counterparts inside a Spring `@Configuration` class. The difference is that `@Component` classes are not enhanced with CGLIB to intercept the invocation of methods and fields. CGLIB proxying is the means by which invoking methods or fields within `@Bean` methods in `@Configuration` classes creates bean metadata references to collaborating objects. Such methods are not invoked with normal Java semantics but rather go through the container in order to provide the usual lifecycle management and proxying of Spring beans, even when referring to other beans through programmatic calls to `@Bean` methods. In contrast, invoking a method or field in a `@Bean` method within a plain `@Component` class has standard Java semantics, with no special CGLIB processing or other constraints applying.

常规Spring组件中的@Bean方法与Spring @Configuration类中的对应方法处理方式不同。区别在于@Component类没有通过CGLIB增强来拦截方法和字段的调用。CGLIB代理是通过调用@Configuration类中@Bean方法中的方法或字段来创建对协作对象的bean元数据引用的方法。这些方法不是用普通的Java语义调用的，而是通过容器来提供通常的生命周期管理和Spring bean的代理，甚至在通过编程调用@Bean方法引用其他bean时也是如此。相反，在普通的@Component类中调用@Bean方法或字段具有标准的Java语义，不应用特殊的CGLIB处理或其他约束。

> You may declare `@Bean` methods as `static`, allowing for them to be called without creating their containing configuration class as an instance. This makes particular sense when defining post-processor beans (for example, of type `BeanFactoryPostProcessor` or `BeanPostProcessor`), since such beans get initialized early in the container lifecycle and should avoid triggering other parts of the configuration at that point.Calls to static `@Bean` methods never get intercepted by the container, not even within `@Configuration` classes (as described earlier in this section), due to technical limitations: CGLIB subclassing can override only non-static methods. As a consequence, a direct call to another `@Bean` method has standard Java semantics, resulting in an independent instance being returned straight from the factory method itself.The Java language visibility of `@Bean` methods does not have an immediate impact on the resulting bean definition in Spring’s container. You can freely declare your factory methods as you see fit in non-`@Configuration` classes and also for static methods anywhere. However, regular `@Bean` methods in `@Configuration` classes need to be overridable — that is, they must not be declared as `private` or `final`.`@Bean` methods are also discovered on base classes of a given component or configuration class, as well as on Java 8 default methods declared in interfaces implemented by the component or configuration class. This allows for a lot of flexibility in composing complex configuration arrangements, with even multiple inheritance being possible through Java 8 default methods as of Spring 4.2.Finally, a single class may hold multiple `@Bean` methods for the same bean, as an arrangement of multiple factory methods to use depending on available dependencies at runtime. This is the same algorithm as for choosing the “greediest” constructor or factory method in other configuration scenarios: The variant with the largest number of satisfiable dependencies is picked at construction time, analogous to how the container selects between multiple `@Autowired` constructors.
>
> 您可以将@Bean方法声明为静态的，允许在不将其包含的配置类创建为实例的情况下调用它们。这在定义后处理器bean(例如，BeanFactoryPostProcessor或BeanPostProcessor类型)时特别有意义，因为这样的bean在容器生命周期的早期被初始化，应该避免在此时触发配置的其他部分。容器永远不会拦截对静态@Bean方法的调用，甚至在@Configuration类中也不会(如本节前面所述)，由于技术限制:CGLIB子类化只能覆盖非静态方法。因此，对另一个@Bean方法的直接调用具有标准的Java语义，从而直接从工厂方法本身返回一个独立的实例。Java语言中@Bean方法的可见性对Spring s容器中生成的bean定义没有直接影响。您可以自由地在non-@Configuration类中声明您的工厂方法，也可以在任何地方声明静态方法。但是，@Configuration类中的常规@Bean方法需要被重写，也就是说，它们不能被声明为private或final。@Bean方法也可以在给定组件或配置类的基类中发现，也可以在Java 8中在组件或配置类实现的接口中声明的默认方法中发现。这为组合复杂配置安排提供了很大的灵活性，甚至可以通过Spring 4.2中的Java 8默认方法实现多重继承。最后，一个类可能为同一个bean保留多个@Bean方法，作为多个工厂方法的安排，在运行时根据可用的依赖项使用。这与在其他配置场景中选择最贪婪的构造函数或工厂方法是相同的算法:在构建时选择可满足依赖关系最多的变量，类似于容器在多个@Autowired构造函数之间进行选择。

##### 1.10.6. Naming Autodetected Components 命名一个组件

When a component is autodetected as part of the scanning process, its bean name is generated by the `BeanNameGenerator` strategy known to that scanner. By default, any Spring stereotype annotation (`@Component`, `@Repository`, `@Service`, and `@Controller`) that contains a name `value` thereby provides that name to the corresponding bean definition.

当一个组件作为扫描过程的一部分被自动检测时，它的bean名称是由扫描器知道的`BeanNameGenerator`策略生成的。默认情况下，任何包含`名称值`的Spring原型注释(`@Component`，` @Repository`， `@Service`，和`@Controller`)将该名称提供给相应的bean定义。

If such an annotation contains no name `value` or for any other detected component (such as those discovered by custom filters), the default bean name generator returns the uncapitalized non-qualified class name. For example, if the following component classes were detected, the names would be `myMovieLister` and `movieFinderImpl`:

如果这样的注释不包含任何`名称值`，或者不包含任何其他检测到的组件(比如那些由自定义过滤器发现的组件)，那么默认的bean名称生成器将返回未大写且不合格的类名。例如，如果检测到以下组件类，名称将是`myMovieLister和movieFinderImpl`:

Java

```java
@Service("myMovieLister")
public class SimpleMovieLister {
    // ...
}
```

Java

```java
@Repository
public class MovieFinderImpl implements MovieFinder {
    // ...
}
```

If you do not want to rely on the default bean-naming strategy, you can provide a custom bean-naming strategy. First, implement the [`BeanNameGenerator`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/support/BeanNameGenerator.html) interface, and be sure to include a default no-arg constructor. Then, provide the fully qualified class name when configuring the scanner, as the following example annotation and bean definition show.

如果不希望依赖默认的bean命名策略，可以提供自定义bean命名策略。首先，实现`BeanNameGenerator`接口，并确保包含默认的无参数构造函数。然后，在配置扫描器时提供完全限定的类名，如下面的示例注释和bean定义所示。

| If you run into naming conflicts due to multiple autodetected components having the same non-qualified class name (i.e., classes with identical names but residing in different packages), you may need to configure a `BeanNameGenerator` that defaults to the fully qualified class name for the generated bean name. As of Spring Framework 5.2.3, the `FullyQualifiedAnnotationBeanNameGenerator` located in package `org.springframework.context.annotation` can be used for such purposes. |
| :----------------------------------------------------------- |
| 如果您由于多个自动检测到的组件具有相同的非限定类名(即具有相同名称但位于不同包中的类)而遇到命名冲突，您可能需要配置一个`BeanNameGenerator`，该`BeanNameGenerator`默认为生成的bean名的完全限定类名。在Spring Framework 5.2.3中，位于package `org.springframework.contextannotation`中的完全限定的`FullyQualifiedAnnotationBeanNameGenerator`。注释可用于此类目的。 |

Java

```java
@Configuration
@ComponentScan(basePackages = "org.example", nameGenerator = MyNameGenerator.class)
public class AppConfig {
    // ...
}
<beans>
    <context:component-scan base-package="org.example"
        name-generator="org.example.MyNameGenerator" />
</beans>
```

As a general rule, consider specifying the name with the annotation whenever other components may be making explicit references to it. On the other hand, the auto-generated names are adequate whenever the container is responsible for wiring.

作为一般规则，当其他组件可能显式地引用该注释时，请考虑使用该注释指定名称。另一方面，只要容器负责连接，自动生成的名称就足够了。

##### 1.10.7. Providing a Scope for Autodetected Components 为自动检测的组件提供范围

As with Spring-managed components in general, the default and most common scope for autodetected components is `singleton`. However, sometimes you need a different scope that can be specified by the `@Scope` annotation. You can provide the name of the scope within the annotation, as the following example shows:

与一般的spring管理组件一样，自动检测组件的默认和最常见的作用域是`singleton`。但是，有时您需要一个不同的范围，可以由`@Scope`注释指定。您可以在注释中提供作用域的名称，如下面的示例所示:

Java

```java
@Scope("prototype")
@Repository
public class MovieFinderImpl implements MovieFinder {
    // ...
}
```

|      | `@Scope` annotations are only introspected on the concrete bean class (for annotated components) or the factory method (for `@Bean` methods). In contrast to XML bean definitions, there is no notion of bean definition inheritance, and inheritance hierarchies at the class level are irrelevant for metadata purposes. |
| ---- | ------------------------------------------------------------ |
|      | `@Scope`注释仅在具体bean类(对于带注释的组件)或工厂方法(对于@Bean方法)上自省。与XML bean定义相反，不存在bean定义继承的概念，而且类级别的继承层次结构与元数据目的无关。 |

For details on web-specific scopes such as “request” or “session” in a Spring context, see [Request, Session, Application, and WebSocket Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other). As with the pre-built annotations for those scopes, you may also compose your own scoping annotations by using Spring’s meta-annotation approach: for example, a custom annotation meta-annotated with `@Scope("prototype")`, possibly also declaring a custom scoped-proxy mode.

有关特定于web的作用域的详细信息，如在Spring上下文中的“请求”或“会话”，请参阅请求、会话、应用程序和WebSocket作用域。与为这些作用域预构建的注释一样，您也可以通过使用Spring的元注释方法来编写自己的作用域注释:例如，使用@Scope(“prototype”)注释的自定义注释元注释，也可能声明自定义作用域代理模式。

| To provide a custom strategy for scope resolution rather than relying on the annotation-based approach, you can implement the [`ScopeMetadataResolver`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/ScopeMetadataResolver.html) interface. Be sure to include a default no-arg constructor. Then you can provide the fully qualified class name when configuring the scanner, as the following example of both an annotation and a bean definition shows: |
| ------------------------------------------------------------ |
| 要为范围解析提供自定义策略，而不是依赖于基于注释的方法，您可以实现`ScopeMetadataResolver`接口。确保包含默认的无参数构造函数。然后，在配置扫描器时，您可以提供完全限定的类名，如下面的注释和bean定义示例所示: |

```java
@Configuration
@ComponentScan(basePackages = "org.example", scopeResolver = MyScopeResolver.class)
public class AppConfig {
    // ...
}
<beans>
    <context:component-scan base-package="org.example" scope-resolver="org.example.MyScopeResolver"/>
</beans>
```

When using certain non-singleton scopes, it may be necessary to generate proxies for the scoped objects. The reasoning is described in [Scoped Beans as Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection). For this purpose, a scoped-proxy attribute is available on the component-scan element. The three possible values are: `no`, `interfaces`, and `targetClass`. For example, the following configuration results in standard JDK dynamic proxies:

在使用某些非单例作用域时，可能需要为作用域对象生成代理。其原因在作用域bean中描述为依赖关系。为此，component-scan元素上有一个作用域代理属性。三个可能的值是:`no、interface和targetClass`。例如，以下配置导致了标准`JDK`动态代理:

```java
@Configuration
@ComponentScan(basePackages = "org.example", scopedProxy = ScopedProxyMode.INTERFACES)
public class AppConfig {
    // ...
}
<beans>
    <context:component-scan base-package="org.example" scoped-proxy="interfaces"/>
</beans>
```

##### 1.10.8. Providing Qualifier Metadata with Annotations 提供带有注释的限定符元数据

The `@Qualifier` annotation is discussed in [Fine-tuning Annotation-based Autowiring with Qualifiers](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation-qualifiers). The examples in that section demonstrate the use of the `@Qualifier` annotation and custom qualifier annotations to provide fine-grained control when you resolve autowire candidates. Because those examples were based on XML bean definitions, the qualifier metadata was provided on the candidate bean definitions by using the `qualifier` or `meta` child elements of the `bean` element in the XML. When relying upon classpath scanning for auto-detection of components, you can provide the qualifier metadata with type-level annotations on the candidate class. The following three examples demonstrate this technique:

`@Qualifier`注解将在基于注释的微调自动装配限定符中讨论。这一节中的示例演示了@Qualifier注释和custom qualifier注释的使用，以在解析自动装配候选时提供细粒度的控制。因为这些示例基于XML bean定义，所以限定符元数据是通过使用XML中bean元素的限定符或元子元素在候选bean定义上提供的。当依赖于类路径扫描来自动检测组件时，您可以在候选类上提供带有类型级别注释的限定符元数据。下面的三个示例演示了这种技术

```java
@Component
@Qualifier("Action")
public class ActionMovieCatalog implements MovieCatalog {
    // ...
}
```

```java
@Component
@Genre("Action")
public class ActionMovieCatalog implements MovieCatalog {
    // ...
}
```

```java
@Component
@Offline
public class CachingMovieCatalog implements MovieCatalog {
    // ...
}
```

| As with most annotation-based alternatives, keep in mind that the annotation metadata is bound to the class definition itself, while the use of XML allows for multiple beans of the same type to provide variations in their qualifier metadata, because that metadata is provided per-instance rather than per-class. |
| ------------------------------------------------------------ |
| 与大多数基于注释的替代方法一样，请记住，注释元数据绑定到类定义本身，而XML的使用允许相同类型的多个bean在其限定符元数据中提供变体，因为元数据是按实例而不是按类提供的。 |

##### 1.10.9. Generating an Index of Candidate Components 生成候选组件的索引

While classpath scanning is very fast, it is possible to improve the startup performance of large applications by creating a static list of candidates at compilation time. In this mode, all modules that are target of component scan must use this mechanism.

虽然类路径扫描非常快，但是可以通过在编译时创建一个静态候选列表来提高大型应用程序的启动性能。在这种模式下，所有作为组件扫描目标的模块都必须使用这种机制。

| Your existing `@ComponentScan` or `<context:component-scan` directives must stay as is to request the context to scan candidates in certain packages. When the `ApplicationContext` detects such an index, it automatically uses it rather than scanning the classpath. |
| ------------------------------------------------------------ |
| 您现有的`@ComponentScan`或`<context:component-scan`指令必须保持原样，以请求上下文扫描某些包中的候选内容。当`ApplicationContext`检测到这样一个索引时，它会自动使用它，而不是扫描类路径。 |

To generate the index, add an additional dependency to each module that contains components that are targets for component scan directives. The following example shows how to do so with Maven:

要生成索引，请向包含组件扫描指令目标组件的每个模块添加额外的依赖项。下面的例子展示了如何使用Maven来实现这一点:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-indexer</artifactId>
        <version>5.3.0</version>
        <optional>true</optional>
    </dependency>
</dependencies>
```

With Gradle 4.5 and earlier, the dependency should be declared in the `compileOnly` configuration, as shown in the following example:

在Gradle 4.5和更早的版本中，依赖关系应该在`compileOnly`配置中声明，如下面的例子所示:

```groovy
dependencies {
    compileOnly "org.springframework:spring-context-indexer:5.3.0"
}
```

With Gradle 4.6 and later, the dependency should be declared in the `annotationProcessor` configuration, as shown in the following example:

```groovy
dependencies {
    annotationProcessor "org.springframework:spring-context-indexer:{spring-version}"
}
```

That process generates a `META-INF/spring.components` file that is included in the jar file.

该过程生成包含在jar文件中的META-INF/spring.components文件。

When working with this mode in your IDE, the `spring-context-indexer` must be registered as an annotation processor to make sure the index is up-to-date when candidate components are updated.

在IDE中使用此模式时，必须将spring上下文索引器注册为注释处理程序，以确保在更新候选组件时索引是最新的。

| The index is enabled automatically when a `META-INF/spring.components` is found on the classpath. If an index is partially available for some libraries (or use cases) but could not be built for the whole application, you can fallback to a regular classpath arrangement (as though no index was present at all) by setting `spring.index.ignore` to `true`, either as a system property or in a `spring.properties` file at the root of the classpath. |
| ------------------------------------------------------------ |
| 当在类路径中找到`META-INF/spring.components`时，索引会自动启用。如果索引对于某些库(或用例)是部分可用的，但不能为整个应用程序构建，那么您可以通过设置`spring.index.ignore` to `true`到常规的类路径安排(就像根本没有索引一样)，无论是作为系统属性还是在位于类路径的根`spring.properties`文件。 |

#### 1.11. Using JSR 330 Standard Annotations

Starting with Spring 3.0, Spring offers support for JSR-330 standard annotations (Dependency Injection). Those annotations are scanned in the same way as the Spring annotations. To use them, you need to have the relevant jars in your classpath.

从Spring 3.0开始，Spring提供了对JSR-330标准注解(依赖注入)的支持。这些注释的扫描方式与Spring注释相同。要使用它们，您需要在类路径中包含相关的jar。



| If you use Maven, the `javax.inject` artifact is available in the standard Maven repository ( https://repo1.maven.org/maven2/javax/inject/javax.inject/1/). You can add the following dependency to your file pom.xml: |
| ------------------------------------------------------------ |
| `<dependency>    <groupId>javax.inject</groupId>    <artifactId>javax.inject</artifactId>    <version>1</version> </dependency>` |
|                                                              |

##### 1.11.1. Dependency Injection with `@Inject` and `@Named`

Instead of `@Autowired`, you can use `@javax.inject.Inject` as follows:

```java
import javax.inject.Inject;

public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    public void listMovies() {
        this.movieFinder.findMovies(...);
        // ...
    }
}
```

As with `@Autowired`, you can use `@Inject` at the field level, method level and constructor-argument level. Furthermore, you may declare your injection point as a `Provider`, allowing for on-demand access to beans of shorter scopes or lazy access to other beans through a `Provider.get()` call. The following example offers a variant of the preceding example:

与`@Autowired`一样，你可以在`字段级`、`方法级`和`构造参数级`使用@Inject。此外，您可以将注入点声明为提供者，从而允许按需访问作用域较短的bean，或者通过`Provider.get()`调用延迟访问其他bean。下面的示例提供了前面示例的变体:

```java
import javax.inject.Inject;
import javax.inject.Provider;

public class SimpleMovieLister {

    private Provider<MovieFinder> movieFinder;

    @Inject
    public void setMovieFinder(Provider<MovieFinder> movieFinder) {
        this.movieFinder = movieFinder;
    }

    public void listMovies() {
        this.movieFinder.get().findMovies(...);
        // ...
    }
}
```

If you would like to use a qualified name for the dependency that should be injected, you should use the `@Named` annotation, as the following example shows:

如果您想为要注入的依赖项使用一个限定名，您应该使用`@Named`注释，如下面的示例所示:

```java
import javax.inject.Inject;
import javax.inject.Named;

public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(@Named("main") MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

As with `@Autowired`, `@Inject` can also be used with `java.util.Optional` or `@Nullable`. This is even more applicable here, since `@Inject` does not have a `required` attribute. The following pair of examples show how to use `@Inject` and `@Nullable`:

与`@Autowired`一样，`@Inject`也可以用在`java.util.Optional`或`@Nullable`。这在这里甚至更适用，因为@Inject没有required属性。下面两个例子展示了如何使用@Inject和@Nullable:

```java
public class SimpleMovieLister {

    @Inject
    public void setMovieFinder(Optional<MovieFinder> movieFinder) {
        // ...
    }
}
```



```java
public class SimpleMovieLister {

    @Inject
    public void setMovieFinder(@Nullable MovieFinder movieFinder) {
        // ...
    }
}
```

##### 1.11.2. `@Named` and `@ManagedBean`: Standard Equivalents to the `@Component` Annotation

@Named和@ManagedBean:与@Component注释的标准等价物

Instead of `@Component`, you can use `@javax.inject.Named` or `javax.annotation.ManagedBean`, as the following example shows:

```java
import javax.inject.Inject;
import javax.inject.Named;

@Named("movieListener")  
// @ManagedBean("movieListener") could be used as well
public class SimpleMovieLister {
    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

It is very common to use `@Component` without specifying a name for the component. `@Named` can be used in a similar fashion, as the following example shows:

```java
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

When you use `@Named` or `@ManagedBean`, you can use component scanning in the exact same way as when you use Spring annotations, as the following example shows:

当您使用@Named或@ManagedBean时，您可以使用与使用Spring注释完全相同的方式来使用组件扫描，如下面的示例所示:

```java
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig  {
    // ...
}
```

|      | In contrast to `@Component`, the JSR-330 `@Named` and the JSR-250 `ManagedBean` annotations are not composable. You should use Spring’s stereotype model for building custom component annotations. |
| ---- | ------------------------------------------------------------ |
|      | 与@Component相反，JSR-330 @Named和JSR-250 ManagedBean注释是不可组合的。您应该使用Spring的原型模型来构建定制的组件注解。 |

##### 1.11.3. Limitations of JSR-330 Standard Annotations JSR-330标准注释的限制

When you work with standard annotations, you should know that some significant features are not available, as the following table shows:

当你使用标准注释时，你应该知道一些重要的特性是不可用的，如下表所示:

| Spring              | javax.inject.*        | javax.inject restrictions / comments                         |
| :------------------ | :-------------------- | :----------------------------------------------------------- |
| @Autowired          | @Inject               | `@Inject` has no 'required' attribute. Can be used with Java 8’s `Optional` instead. |
| @Component          | @Named / @ManagedBean | JSR-330 does not provide a composable model, only a way to identify named components. |
|                     |                       | JSR-330没有提供可组合模型，只是提供了一种识别已命名组件的方法。 |
| @Scope("singleton") | @Singleton            | The JSR-330 default scope is like Spring’s `prototype`. However, in order to keep it consistent with Spring’s general defaults, a JSR-330 bean declared in the Spring container is a `singleton` by default. In order to use a scope other than `singleton`, you should use Spring’s `@Scope` annotation. `javax.inject` also provides a [@Scope](https://download.oracle.com/javaee/6/api/javax/inject/Scope.html) annotation. Nevertheless, this one is only intended to be used for creating your own annotations. |
|                     |                       | JSR-330的默认范围类似于Spring的原型。但是，为了保持它与Spring的一般默认值一致，在Spring容器中声明的JSR-330 bean在默认情况下是单例的。为了使用除singleton之外的作用域，您应该使用Spring的@Scope注释。javax。inject还提供了一个@Scope注释。不过，这个注释仅用于创建您自己的注释。 |
| @Qualifier          | @Qualifier / @Named   | `javax.inject.Qualifier` is just a meta-annotation for building custom qualifiers. Concrete `String` qualifiers (like Spring’s `@Qualifier` with a value) can be associated through `javax.inject.Named`. |
|                     |                       | `javax.inject.Qualifier`只是一个用于构建定制限定符的元注释。具体的字符串限定符(比如Spring的带有值的`@Qualifier`)可以通过`javax. injec. named`关联起来。 |
| @Value              | -                     | no equivalent                                                |
| @Required           | -                     | no equivalent                                                |
| @Lazy               | -                     | no equivalent                                                |
| ObjectFactory       | Provider              | `javax.inject.Provider` is a direct alternative to Spring’s `ObjectFactory`, only with a shorter `get()` method name. It can also be used in combination with Spring’s `@Autowired` or with non-annotated constructors and setter methods. |
|                     |                       | `javax.inject.Provider`是Spring的`ObjectFactory`的直接替代品，只是它的`get()`方法名更短。它还可以与Spring的`@Autowired`或者非注释的构造函数和setter方法结合使用。 |

#### 1.12. Java-based Container Configuration 基于Java的容器配置

This section covers how to use annotations in your Java code to configure the Spring container. It includes the following topics:
本节介绍如何在Java代码中使用注释（注解）来配置Spring容器。它包括以下主题:

- [Basic Concepts: `@Bean` and `@Configuration`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts)
- [Instantiating the Spring Container by Using `AnnotationConfigApplicationContext`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-instantiating-container)
- [Using the `@Bean` Annotation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-bean-annotation)
- [Using the `@Configuration` annotation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-configuration-annotation)
- [Composing Java-based Configurations](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-composing-configuration-classes)
- [Bean Definition Profiles](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition-profiles)
- [`PropertySource` Abstraction](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-property-source-abstraction)
- [Using `@PropertySource`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-using-propertysource)
- [Placeholder Resolution in Statements](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-placeholder-resolution-in-statements)

##### 1.12.1. Basic Concepts: `@Bean` and `@Configuration` 基本概念

The central artifacts in Spring’s new Java-configuration support are `@Configuration`-annotated classes and `@Bean`-annotated methods.

The `@Bean` annotation is used to indicate that a method instantiates, configures, and initializes a new object to be managed by the Spring IoC container. For those familiar with Spring’s `<beans/>` XML configuration, the `@Bean` annotation plays the same role as the `<bean/>` element. You can use `@Bean`-annotated methods with any Spring `@Component`. However, they are most often used with `@Configuration` beans.

Annotating a class with `@Configuration` indicates that its primary purpose is as a source of bean definitions. Furthermore, `@Configuration` classes let inter-bean dependencies be defined by calling other `@Bean` methods in the same class. The simplest possible `@Configuration` class reads as follows:

Spring新的java配置支持中的核心构件是“@Configuration”注释的类和“@Bean”注释的方法。' @Bean '注释用于指示方法实例化、配置和初始化将由Spring IoC容器管理的新对象。对于那些熟悉Spring s ' <beans/> ' XML配置的人来说，' @Bean '注释的作用与' <bean/> '元素相同。您可以对任何Spring的“@Component”使用“@Bean”注释的方法。但是，它们最常与‘@Configuration’bean一起使用。
用“@Configuration”注释一个类表明它的主要用途是作为bean定义的源。此外，“@Configuration”类允许通过调用同一类中的其他“@Bean”方法来定义bean间的依赖关系。最简单的' @Configuration '类如下所示

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

The preceding `AppConfig` class is equivalent to the following Spring `<beans/>` XML:
前面的“AppConfig”类相当于下面的Spring“<beans/>”XML:

```xml
<beans>
    <bean id="myService" class="com.acme.services.MyServiceImpl"/>
</beans>
```

Full @Configuration vs “lite” @Bean mode?

When `@Bean` methods are declared within classes that are not annotated with `@Configuration`, they are referred to as being processed in a “lite” mode. Bean methods declared in a `@Component` or even in a plain old class are considered to be “lite”, with a different primary purpose of the containing class and a `@Bean` method being a sort of bonus there. For example, service components may expose management views to the container through an additional `@Bean` method on each applicable component class. In such scenarios, `@Bean` methods are a general-purpose factory method mechanism.

当“@Bean”方法在没有使用“@Configuration”注释的类中声明时，它们被称为在“lite”模式下处理。在' @Component '中声明的Bean方法或者在普通的旧类中声明的Bean方法被认为是“lite”的，因为包含类的主要目的不同，而' @Bean '方法在这里是一种奖励。例如，服务组件可以通过每个适用组件类上的附加“@Bean”方法向容器公开管理视图。在这种情况下，' @Bean '方法是一种通用工厂方法机制。

Unlike full `@Configuration`, lite `@Bean` methods cannot declare inter-bean dependencies. Instead, they operate on their containing component’s internal state and, optionally, on arguments that they may declare. Such a `@Bean` method should therefore not invoke other `@Bean` methods. Each such method is literally only a factory method for a particular bean reference, without any special runtime semantics. The positive side-effect here is that no CGLIB subclassing has to be applied at runtime, so there are no limitations in terms of class design (that is, the containing class may be `final` and so forth).

与完整的“@Configuration”不同，lite“@Bean”方法不能声明bean之间的依赖关系。相反，它们对包含它们的组件的内部状态进行操作，并可选地对它们可能声明的参数进行操作。因此，这样的“@Bean”方法不应该调用其他的“@Bean”方法。每个这样的方法字面上只是一个特定bean引用的工厂方法，没有任何特殊的运行时语义。这里的正面副作用是，在运行时不需要应用CGLIB子类，因此在类设计方面没有限制(也就是说，包含的类可能是“final”之类的)。

In common scenarios, `@Bean` methods are to be declared within `@Configuration` classes, ensuring that “full” mode is always used and that cross-method references therefore get redirected to the container’s lifecycle management. This prevents the same `@Bean` method from accidentally being invoked through a regular Java call, which helps to reduce subtle bugs that can be hard to track down when operating in “lite” mode.

在常见的场景中，' @Bean '方法将在' @Configuration '类中声明，以确保始终使用“full”模式，并因此将交叉方法引用重定向到容器的生命周期管理。这可以防止通过常规Java调用意外地调用相同的' @Bean '方法，这有助于减少在“lite”模式下操作时难以跟踪的细微错误。

The `@Bean` and `@Configuration` annotations are discussed in depth in the following sections. First, however, we cover the various ways of creating a spring container using by Java-based configuration.

下面几节将深入讨论' @Bean '和' @Configuration '注释。但是，首先，我们将介绍使用基于java的配置创建spring容器的各种方法。

##### 1.12.2. Instantiating the Spring Container by Using `AnnotationConfigApplicationContext`通过使用“AnnotationConfigApplicationContext”实例化Spring容器

The following sections document Spring’s `AnnotationConfigApplicationContext`, introduced in Spring 3.0. This versatile `ApplicationContext` implementation is capable of accepting not only `@Configuration` classes as input but also plain `@Component` classes and classes annotated with JSR-330 metadata.

下面的章节描述了Spring的“AnnotationConfigApplicationContext”，它是在Spring 3.0中引入的。这个通用的“ApplicationContext”实现不仅能够接受“@Configuration”类作为输入，还能够接受普通的“@Component”类和带有JSR-330元数据注释的类。

When `@Configuration` classes are provided as input, the `@Configuration` class itself is registered as a bean definition and all declared `@Bean` methods within the class are also registered as bean definitions.

当提供' @Configuration '类作为输入时，' @Configuration '类本身被注册为一个bean定义，类中所有声明的' @Bean '方法也被注册为bean定义。

When `@Component` and JSR-330 classes are provided, they are registered as bean definitions, and it is assumed that DI metadata such as `@Autowired` or `@Inject` are used within those classes where necessary.

当“@Component”和JSR-330类被提供时，它们被注册为bean定义，并且假设如“@Autowired”或“@Inject”这样的DI元数据在这些类中被必要地使用。

##### Simple Construction

In much the same way that Spring XML files are used as input when instantiating a `ClassPathXmlApplicationContext`, you can use `@Configuration` classes as input when instantiating an `AnnotationConfigApplicationContext`. This allows for completely XML-free usage of the Spring container, as the following example shows:

与实例化' ClassPathXmlApplicationContext '时使用Spring XML文件作为输入的方式非常相似，您可以在实例化' AnnotationConfigApplicationContext '时使用' @Configuration '类作为输入。这允许使用完全无xml的Spring容器，如下面的示例所示:

```java
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}
```

As mentioned earlier, `AnnotationConfigApplicationContext` is not limited to working only with `@Configuration` classes. Any `@Component` or JSR-330 annotated class may be supplied as input to the constructor, as the following example shows:

正如前面提到的，‘AnnotationConfigApplicationContext’并不仅限于处理‘@Configuration’类。任何' @Component '或JSR-330注释类都可以作为输入提供给构造函数，如下面的示例所示:

```java
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MyServiceImpl.class, Dependency1.class, Dependency2.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}
```

The preceding example assumes that `MyServiceImpl`, `Dependency1`, and `Dependency2` use Spring dependency injection annotations such as `@Autowired`.

前面的示例假设`MyServiceImpl `、' Dependency1 '和' Dependency2 '使用Spring依赖注入注释，如' @Autowired '。

##### Building the Container Programmatically by Using `register(Class<?>…)`

通过使用' register(Class<?>…)'以编程方式构建容器

You can instantiate an `AnnotationConfigApplicationContext` by using a no-arg constructor and then configure it by using the `register()` method. This approach is particularly useful when programmatically building an `AnnotationConfigApplicationContext`. The following example shows how to do so:

您可以通过使用无参数构造函数实例化一个' AnnotationConfigApplicationContext '，然后通过使用' register() '方法配置它。当以编程方式构建一个“AnnotationConfigApplicationContext”时，这种方法特别有用。下面的例子展示了如何做到这一点:


```java
public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(AppConfig.class, OtherConfig.class);
    ctx.register(AdditionalConfig.class);
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}
```

##### Enabling Component Scanning with `scan(String…)`

使用“scan(String…)”启用组件扫描
To enable component scanning, you can annotate your `@Configuration` class as follows:

要启用组件扫描，您可以注释您的' @Configuration '类如下:

```java
@Configuration
@ComponentScan(basePackages = "com.acme") 
public class AppConfig  {
    ...
}
```

|      | This annotation enables component scanning. |
| ---- | ------------------------------------------- |
|      | 该注释支持组件扫描。                        |

|      | Experienced Spring users may be familiar with the XML declaration equivalent from Spring’s `context:` namespace, shown in the following example:`<beans>    <context:component-scan base-package="com.acme"/> </beans>` |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

有经验的Spring用户可能熟悉来自Spring的“context:”名称空间的等价XML声明，如下面的示例所示:“<beans> <context:component-scan base-package=”com.acme " / > < /beans>”

In the preceding example, the `com.acme` package is scanned to look for any `@Component`-annotated classes, and those classes are registered as Spring bean definitions within the container. `AnnotationConfigApplicationContext` exposes the `scan(String…)` method to allow for the same component-scanning functionality, as the following example shows:
在前面的例子中，' com.acme '包被扫描查找任何' @Component '注释的类，并且这些类被注册为容器内的Spring bean定义。“AnnotationConfigApplicationContext”公开了“scan(String…)”方法，允许相同的组件扫描功能，如下面的示例所示:

```java
public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.scan("com.acme");
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
}
```

|      | Remember that `@Configuration` classes are [meta-annotated](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-meta-annotations) with `@Component`, so they are candidates for component-scanning. In the preceding example, assuming that `AppConfig` is declared within the `com.acme` package (or any package underneath), it is picked up during the call to `scan()`. Upon `refresh()`, all its `@Bean` methods are processed and registered as bean definitions within the container. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

记住，“@configuration”类是带有“@Component”的[元注释](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-meta-annotations)，因此它们是组件扫描的候选对象。在前面的示例中，假设“AppConfig”在“com”.cme '中声明的包(或下面的任何包)，它在' scan() '调用期间被拾取。在' refresh() '上，它的所有' @Bean '方法都被处理并在容器中注册为bean定义。

##### Support for Web Applications with `AnnotationConfigWebApplicationContext`

支持“AnnotationConfigWebApplicationContext”的Web应用程序

A `WebApplicationContext` variant of `AnnotationConfigApplicationContext` is available with `AnnotationConfigWebApplicationContext`. You can use this implementation when configuring the Spring `ContextLoaderListener` servlet listener, Spring MVC `DispatcherServlet`, and so forth. The following `web.xml` snippet configures a typical Spring MVC web application (note the use of the `contextClass` context-param and init-param):

“AnnotationConfigApplicationContext”的“WebApplicationContext”变体可以通过“AnnotationConfigWebApplicationContext”获得。您可以在配置Spring ' ContextLoaderListener ' servlet监听器、Spring MVC ' DispatcherServlet '等等时使用这个实现。以下的网站。xml的代码片段配置了一个典型的Spring MVC web应用程序(注意使用了“contextClass”上下文参数和init参数):

```xml
<web-app>
    <!-- Configure ContextLoaderListener to use AnnotationConfigWebApplicationContext
        instead of the default XmlWebApplicationContext -->
    <context-param>
        <param-name>contextClass</param-name>
        <param-value>
            org.springframework.web.context.support.AnnotationConfigWebApplicationContext
        </param-value>
    </context-param>

    <!-- Configuration locations must consist of one or more comma- or space-delimited
        fully-qualified @Configuration classes. Fully-qualified packages may also be
        specified for component-scanning -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>com.acme.AppConfig</param-value>
    </context-param>

    <!-- Bootstrap the root application context as usual using ContextLoaderListener -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- Declare a Spring MVC DispatcherServlet as usual -->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- Configure DispatcherServlet to use AnnotationConfigWebApplicationContext
            instead of the default XmlWebApplicationContext -->
        <init-param>
            <param-name>contextClass</param-name>
            <param-value>
                org.springframework.web.context.support.AnnotationConfigWebApplicationContext
            </param-value>
        </init-param>
        <!-- Again, config locations must consist of one or more comma- or space-delimited
            and fully-qualified @Configuration classes -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>com.acme.web.MvcConfig</param-value>
        </init-param>
    </servlet>

    <!-- map all requests for /app/* to the dispatcher servlet -->
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/app/*</url-pattern>
    </servlet-mapping>
</web-app>
```

##### 1.12.3. Using the `@Bean` Annotation
使用@Bean注解
`@Bean` is a method-level annotation and a direct analog of the XML `<bean/>` element. The annotation supports some of the attributes offered by `<bean/>`, such as: * [init-method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean) * [destroy-method](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-disposablebean) * [autowiring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire) * `name`.

You can use the `@Bean` annotation in a `@Configuration`-annotated or in a `@Component`-annotated class.

您可以在` @Configuration `注释的类中或` @Component `注释的类中使用` @Bean `注释。

###### Declaring a Bean

To declare a bean, you can annotate a method with the `@Bean` annotation. You use this method to register a bean definition within an `ApplicationContext` of the type specified as the method’s return value. By default, the bean name is the same as the method name. The following example shows a `@Bean` method declaration:

要声明一个bean，您可以使用` @Bean `注释一个方法。您可以使用这个方法在`ApplicationContext`中注册一个指定为方法返回值的类型的bean定义。默认情况下，bean名称与方法名称相同。下面的示例显示了一个`@Bean`方法声明:

```java
@Configuration
public class AppConfig {

    @Bean
    public TransferServiceImpl transferService() {
        return new TransferServiceImpl();
    }
}
```

The preceding configuration is exactly equivalent to the following Spring XML:
前面的配置与下面的Spring XML完全相同:

```xml
<beans>
    <bean id="transferService" class="com.acme.TransferServiceImpl"/>
</beans>
```

Both declarations make a bean named `transferService` available in the `ApplicationContext`, bound to an object instance of type `TransferServiceImpl`, as the following text image shows:

这两个声明都使名为`transferService`的bean在`ApplicationContext`中可用，绑定到类型为`TransferServiceImpl`的对象实例，如下面的文本图像所示:
```
transferService -> com.acme.TransferServiceImpl
```

You can also declare your `@Bean` method with an interface (or base class) return type, as the following example shows:
你也可以用接口(或基类)返回类型来声明你的`@Bean`方法，如下面的例子所示:

```java
@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl();
    }
}
```

However, this limits the visibility for advance type prediction to the specified interface type (`TransferService`). Then, with the full type (`TransferServiceImpl`) known to the container only once, the affected singleton bean has been instantiated. Non-lazy singleton beans get instantiated according to their declaration order, so you may see different type matching results depending on when another component tries to match by a non-declared type (such as `@Autowired TransferServiceImpl`, which resolves only once the `transferService` bean has been instantiated).

但是，这将提前类型预测的可见性限制为指定的接口类型(`TransferService`)。然后，容器只知道完整类型(` TransferServiceImpl `)一次，受影响的单例bean就被实例化了。非惰性的单例bean根据其声明顺序被实例化，因此您可能会看到不同的类型匹配结果，这取决于其他组件何时尝试通过一个未声明的类型进行匹配(例如‘@Autowired TransferServiceImpl’，它只在‘transferService’bean被实例化之后才解析)。

|      | If you consistently refer to your types by a declared service interface, your `@Bean` return types may safely join that design decision. However, for components that implement several interfaces or for components potentially referred to by their implementation type, it is safer to declare the most specific return type possible (at least as specific as required by the injection points that refer to your bean). |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

如果您始终通过声明的服务接口引用您的类型，那么您的` @Bean `返回类型可以安全地加入设计决策。但是，对于实现多个接口的组件，或者对于可能由其实现类型引用的组件，声明可能最具体的返回类型(至少与引用bean的注入点所要求的一样具体)更为安全。
###### Bean Dependencies

A `@Bean`-annotated method can have an arbitrary number of parameters that describe the dependencies required to build that bean. For instance, if our `TransferService` requires an `AccountRepository`, we can materialize that dependency with a method parameter, as the following example shows:
带`@Bean`注释的方法可以有任意数量的参数，这些参数描述了构建该bean所需的依赖关系。例如，如果我们的`TransferService`需要一个`AccountRepository`，我们可以用一个方法参数来具体化这个依赖，如下面的例子所示:

```java
@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService(AccountRepository accountRepository) {
        return new TransferServiceImpl(accountRepository);
    }
}
```

The resolution mechanism is pretty much identical to constructor-based dependency injection. See [the relevant section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection) for more details.

解析机制与基于构造器的依赖项注入非常相似。更多细节请参见[相关部分]
###### Receiving Lifecycle Callbacks

接收生命周期回调

Any classes defined with the `@Bean` annotation support the regular lifecycle callbacks and can use the `@PostConstruct` and `@PreDestroy` annotations from JSR-250. See [JSR-250 annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-postconstruct-and-predestroy-annotations) for further details.

任何用`@Bean`注释定义的类都支持常规的生命周期回调，并且可以使用JSR-250中的`@PostConstruct`和`@PreDestroy`注释。参见[JSR-250 annotations]
The regular Spring [lifecycle](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-nature) callbacks are fully supported as well. If a bean implements `InitializingBean`, `DisposableBean`, or `Lifecycle`, their respective methods are called by the container.

常规的Spring[生命周期](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#bean -factory-nature)回调也得到了完全的支持。如果一个bean实现了`InitializingBean`、`DisposableBean`或`Lifecycle`，容器会调用它们各自的方法。

The standard set of `*Aware` interfaces (such as [BeanFactoryAware](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanfactory), [BeanNameAware](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware), [MessageSourceAware](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-messagesource), [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-aware), and so on) are also fully supported.

标准组*意识到的接口(如[BeanFactoryAware] (https://docs.spring.io/spring-framework/docs/current/reference/html/core.html # beans-beanfactory), [BeanNameAware] (https://docs.spring.io/spring-framework/docs/current/reference/html/core.html # beans-factory-aware), [MessageSourceAware] (https://docs.spring.io/spring-framework/docs/current/reference/html/core.html # context-functionality-messagesource),[applicationcontexts taware](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#bean -factory-aware)，等等)也得到了完全的支持。

The `@Bean` annotation supports specifying arbitrary initialization and destruction callback methods, much like Spring XML’s `init-method` and `destroy-method` attributes on the `bean` element, as the following example shows:

@Bean注释支持指定任意的初始化和销毁回调方法，就像Spring XML的` init-method `和` destroy-method `属性在` bean `元素上一样，如下面的例子所示:
```java
public class BeanOne {

    public void init() {
        // initialization logic
    }
}

public class BeanTwo {

    public void cleanup() {
        // destruction logic
    }
}

@Configuration
public class AppConfig {

    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }

    @Bean(destroyMethod = "cleanup")
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```

|      | By default, beans defined with Java configuration that have a public `close` or `shutdown` method are automatically enlisted with a destruction callback. If you have a public `close` or `shutdown` method and you do not wish for it to be called when the container shuts down, you can add `@Bean(destroyMethod="")` to your bean definition to disable the default `(inferred)` mode.You may want to do that by default for a resource that you acquire with JNDI, as its lifecycle is managed outside the application. In particular, make sure to always do it for a `DataSource`, as it is known to be problematic on Java EE application servers.The following example shows how to prevent an automatic destruction callback for a `DataSource`:JavaKotlin`@Bean(destroyMethod="") public DataSource dataSource() throws NamingException {    return (DataSource) jndiTemplate.lookup("MyDS"); }`Also, with `@Bean` methods, you typically use programmatic JNDI lookups, either by using Spring’s `JndiTemplate` or `JndiLocatorDelegate` helpers or straight JNDI `InitialContext` usage but not the `JndiObjectFactoryBean` variant (which would force you to declare the return type as the `FactoryBean` type instead of the actual target type, making it harder to use for cross-reference calls in other `@Bean` methods that intend to refer to the provided resource here). |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

默认情况下，使用Java配置定义的具有公共`关闭`或`关闭`方法的bean将与销毁回调一起自动登记。如果您有一个公共的` close `或` shutdown `方法，并且您不希望在容器关闭时调用它，那么您可以在bean定义中添加` @Bean(destroyMethod="") `来禁用默认的` (inferred) `模式。默认情况下，您可能希望对使用JNDI获取的资源执行此操作，因为它的生命周期是在应用程序之外管理的。特别是，确保总是为`数据源`这样做，因为它在Java EE应用服务器上是有问题的。下面的例子展示了如何防止一个数据源的自动销毁回调:JavaKotlin ` @Bean(destroyMethod="") public DataSource DataSource()抛出NamingException {return (DataSource) jndiTemplate.lookup("MyDS");}`也,`@ bean的方法,您通常使用程序化的JNDI查找,通过使用Spring年代JndiTemplate`或`JndiLocatorDelegate帮手或直JNDI的InitialContext的使用而不是`JndiObjectFactoryBean的变体(这将迫使你声明返回类型作为`FactoryBean`类型,而不是实际的目标类型,因此很难使用交叉引用调用其他的@ bean方法,打算参考所提供的资源)

In the case of `BeanOne` from the example above the preceding note, it would be equally valid to call the `init()` method directly during construction, as the following example shows:
对于上面例子中的`BeanOne`，在构造过程中直接调用`init()`方法同样有效，如下面的例子所示:

```java
@Configuration
public class AppConfig {

    @Bean
    public BeanOne beanOne() {
        BeanOne beanOne = new BeanOne();
        beanOne.init();
        return beanOne;
    }

    // ...
}
```

|      | When you work directly in Java, you can do anything you like with your objects and do not always need to rely on the container lifecycle. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |
当您直接在Java中工作时，您可以对对象做任何您喜欢的事情，而不总是需要依赖容器的生命周期。

###### Specifying Bean Scope

Spring includes the `@Scope` annotation so that you can specify the scope of a bean.
Spring包含了` @Scope `注释，这样您就可以指定bean的范围。

###### Using the `@Scope` Annotation

You can specify that your beans defined with the `@Bean` annotation should have a specific scope. You can use any of the standard scopes specified in the [Bean Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes) section.

The default scope is `singleton`, but you can override this with the `@Scope` annotation, as the following example shows:

您可以指定用` @Bean `注释定义的bean应该具有特定的范围。您可以使用[Bean作用域](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#beans-factory-scopes)部分中指定的任何标准作用域。
默认范围是` singleton `，但是你可以用` @Scope `注释覆盖它，如下面的例子所示:

```java
@Configuration
public class MyConfiguration {

    @Bean
    @Scope("prototype")
    public Encryptor encryptor() {
        // ...
    }
}
```

###### `@Scope` and `scoped-proxy`

Spring offers a convenient way of working with scoped dependencies through [scoped proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection). The easiest way to create such a proxy when using the XML configuration is the `<aop:scoped-proxy/>` element. Configuring your beans in Java with a `@Scope` annotation offers equivalent support with the `proxyMode` attribute. The default is no proxy (`ScopedProxyMode.NO`), but you can specify `ScopedProxyMode.TARGET_CLASS` or `ScopedProxyMode.INTERFACES`.

Spring通过[作用域代理](https://docs.springing.io/springing-framework/docs/current/reference/html/core.html #bean -工厂-作用域-other-injection)提供了一种使用作用域依赖关系的方便方法。在使用XML配置时，创建这样一个代理的最简单方法是` <aop:作用域-代理/> `元素。用`@Scope`注释配置Java中的bean可以提供与`proxyMode`属性同等的支持。默认是没有代理(` ScopedProxyMode. no `)，但您可以指定` ScopedProxyMode。TARGET_CLASS’或‘ScopedProxyMode.INTERFACES`

If you port the scoped proxy example from the XML reference documentation (see [scoped proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection)) to our `@Bean` using Java, it resembles the following:

如果您使用Java从XML参考文档(参见[scoped proxy](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#bean -factory-scopes-other-injection)中移植scoped代理示例到我们的`@Bean`，它类似于以下情况:

```java
// an HTTP Session-scoped bean exposed as a proxy
@Bean
@SessionScope
public UserPreferences userPreferences() {
    return new UserPreferences();
}

@Bean
public Service userService() {
    UserService service = new SimpleUserService();
    // a reference to the proxied userPreferences bean
    service.setUserPreferences(userPreferences());
    return service;
}
```

###### Customizing Bean Naming

自定义Bean命名

By default, configuration classes use a `@Bean` method’s name as the name of the resulting bean. This functionality can be overridden, however, with the `name` attribute, as the following example shows:

默认情况下，配置类使用` @Bean `方法的名称作为生成的bean的名称。但是，可以使用`name`属性覆盖此功能，如下面的示例所示:

```java
@Configuration
public class AppConfig {

    @Bean(name = "myThing")
    public Thing thing() {
        return new Thing();
    }
}
```

###### Bean Aliasing

As discussed in [Naming Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanname), it is sometimes desirable to give a single bean multiple names, otherwise known as bean aliasing. The `name` attribute of the `@Bean` annotation accepts a String array for this purpose. The following example shows how to set a number of aliases for a bean:

正如在[命名bean](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#beans-beanname)中所讨论的，有时希望为单个bean提供多个名称，也称为bean别名。` @Bean `注释的` name `属性为此接受一个字符串数组。下面的例子展示了如何为一个bean设置多个别名:

```java
@Configuration
public class AppConfig {

    @Bean({"dataSource", "subsystemA-dataSource", "subsystemB-dataSource"})
    public DataSource dataSource() {
        // instantiate, configure and return DataSource bean...
    }
}
```

###### Bean Description

Sometimes, it is helpful to provide a more detailed textual description of a bean. This can be particularly useful when beans are exposed (perhaps through JMX) for monitoring purposes.
有时，为bean提供更详细的文本描述是有帮助的。当为了监视目的而公开bean(可能通过JMX)时，这尤其有用。

To add a description to a `@Bean`, you can use the [`@Description`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Description.html) annotation, as the following example shows:

要向`@Bean`添加描述，可以使用[` @Description `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Description.html)注释，如下面的例子所示:


```java
@Configuration
public class AppConfig {

    @Bean
    @Description("Provides a basic example of a bean")
    public Thing thing() {
        return new Thing();
    }
}
```

##### 1.12.4. Using the `@Configuration` annotation

使用` @Configuration `注释

`@Configuration` is a class-level annotation indicating that an object is a source of bean definitions. `@Configuration` classes declare beans through public `@Bean` annotated methods. Calls to `@Bean` methods on `@Configuration` classes can also be used to define inter-bean dependencies. See [Basic Concepts: `@Bean` and `@Configuration`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts) for a general introduction.

` @Configuration `是一个类级注释，指示对象是bean定义的源。`@Configuration`类通过公共的`@Bean`注释方法声明bean。调用` @Configuration `类上的` @Bean `方法也可以用来定义bean间的依赖关系。请参见[基本概念:` @Bean `和` @Configuration `](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-basic-concepts)获得一般的介绍。

###### Injecting Inter-bean Dependencies

注入bean之间的依赖关系

When beans have dependencies on one another, expressing that dependency is as simple as having one bean method call another, as the following example shows:

当bean相互依赖时，表达这种依赖就像让一个bean方法调用另一个bean方法一样简单，如下面的例子所示:

```java
@Configuration
public class AppConfig {

    @Bean
    public BeanOne beanOne() {
        return new BeanOne(beanTwo());
    }

    @Bean
    public BeanTwo beanTwo() {
        return new BeanTwo();
    }
}
```

In the preceding example, `beanOne` receives a reference to `beanTwo` through constructor injection.

在前面的示例中，`beanOne`通过构造函数注入接收到对`beanTwo`的引用。

|      | This method of declaring inter-bean dependencies works only when the `@Bean` method is declared within a `@Configuration` class. You cannot declare inter-bean dependencies by using plain `@Component` classes. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

只有当`@Bean`方法在`@Configuration`类中声明时，这种声明bean间依赖关系的方法才有效。不能通过使用纯的`@Component`类来声明bean之间的依赖关系。
###### Lookup Method Injection

查找方法注入

As noted earlier, [lookup method injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-method-injection) is an advanced feature that you should use rarely. It is useful in cases where a singleton-scoped bean has a dependency on a prototype-scoped bean. Using Java for this type of configuration provides a natural means for implementing this pattern. The following example shows how to use lookup method injection:
如前所述，[查找方法注入](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#beans-factory-method-injection)是一个高级特性，应该很少使用。在单例作用域bean与原型作用域bean有依赖关系的情况下，它非常有用。将Java用于这种类型的配置提供了一种实现这种模式的自然方法。下面的例子展示了如何使用查找方法注入:

```java
public abstract class CommandManager {
    public Object process(Object commandState) {
        // grab a new instance of the appropriate Command interface
		//获取适当命令接口的新实例
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
		//在命令实例(希望是全新的)上设置状态
        command.setState(commandState);
        return command.execute();
    }

    // okay... but where is the implementation of this method?
	/ /好吧…但是这个方法的实现在哪里呢?
    protected abstract Command createCommand();
}
```

By using Java configuration, you can create a subclass of `CommandManager` where the abstract `createCommand()` method is overridden in such a way that it looks up a new (prototype) command object. The following example shows how to do so:
通过使用Java配置，您可以创建一个` CommandManager `的子类，其中抽象的` createCommand() `方法被重写，它将查找一个新的(原型)命令对象。下面的例子展示了如何做到这一点:

```java
@Bean
@Scope("prototype")
public AsyncCommand asyncCommand() {
    AsyncCommand command = new AsyncCommand();
    // inject dependencies here as required
	//根据需要在这里注入依赖关系
    return command;
}

@Bean
public CommandManager commandManager() {
    // return new anonymous implementation of CommandManager with createCommand()
    // overridden to return a new prototype Command object
	//使用createCommand()返回CommandManager的新匿名实现
	//重写后返回一个新的原型命令对象
    return new CommandManager() {
        protected Command createCommand() {
            return asyncCommand();
        }
    }
}
```

###### Further Information About How Java-based Configuration Works Internally

关于基于java的配置如何在内部工作的进一步信息

Consider the following example, which shows a `@Bean` annotated method being called twice:

考虑下面的例子，它显示了一个被调用了两次的`@Bean`注释方法:
```java
@Configuration
public class AppConfig {

    @Bean
    public ClientService clientService1() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientService clientService2() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDaoImpl();
    }
}
```

`clientDao()` has been called once in `clientService1()` and once in `clientService2()`. Since this method creates a new instance of `ClientDaoImpl` and returns it, you would normally expect to have two instances (one for each service). That definitely would be problematic: In Spring, instantiated beans have a `singleton` scope by default. This is where the magic comes in: All `@Configuration` classes are subclassed at startup-time with `CGLIB`. In the subclass, the child method checks the container first for any cached (scoped) beans before it calls the parent method and creates a new instance.

` clientDao() `在` clientService1() `和` clientService2() `中都被调用过一次。因为这个方法创建了一个新的`ClientDaoImpl`实例并返回它，所以通常会有两个实例(每个服务一个)。这肯定是有问题的:在Spring中，实例化的bean在默认情况下有一个`单例`作用域。这就是神奇之处:所有` @Configuration `类在启动时都用` CGLIB `子类化。在子类中，子方法在调用父方法并创建一个新实例之前，首先检查容器是否有缓存的(作用域限定的)bean。

|      | The behavior could be different according to the scope of your bean. We are talking about singletons here. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

根据您的bean的范围，行为可能不同。我们在这里讨论的是单例。
|      | As of Spring 3.2, it is no longer necessary to add CGLIB to your classpath because CGLIB classes have been repackaged under `org.springframework.cglib` and included directly within the spring-core JAR. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

从Spring 3.2开始，不再需要将CGLIB添加到类路径中，因为CGLIB类已经在` org.springframework下重新打包。并直接包含在spring-core JAR中
|      | There are a few restrictions due to the fact that CGLIB dynamically adds features at startup-time. In particular, configuration classes must not be final. However, as of 4.3, any constructors are allowed on configuration classes, including the use of `@Autowired` or a single non-default constructor declaration for default injection.If you prefer to avoid any CGLIB-imposed limitations, consider declaring your `@Bean` methods on non-`@Configuration` classes (for example, on plain `@Component` classes instead). Cross-method calls between `@Bean` methods are not then intercepted, so you have to exclusively rely on dependency injection at the constructor or method level there. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

由于CGLIB在启动时动态添加特性，所以有一些限制。特别是，配置类不能是最终的。然而，在4.3版本中，配置类允许使用任何构造函数，包括使用`@Autowired`或为默认注入使用单一的非默认构造函数声明。如果您希望避免任何cglib强加的限制，可以考虑在非` @Configuration `类上声明` @Bean `方法(例如，在纯` @Component `类上声明)。然后，`@Bean`方法之间的交叉方法调用就不会被拦截，因此您必须在构造函数或方法级别独家依赖于依赖注入。
##### 1.12.5. Composing Java-based Configurations

编写基于java的配置

Spring’s Java-based configuration feature lets you compose annotations, which can reduce the complexity of your configuration.

Spring的基于java的配置特性允许编写注释，这可以降低配置的复杂性。

###### Using the `@Import` Annotation

使用` @Import `注释

Much as the `<import/>` element is used within Spring XML files to aid in modularizing configurations, the `@Import` annotation allows for loading `@Bean` definitions from another configuration class, as the following example shows:

就像在Spring XML文件中使用` <import/> `元素来帮助模块化配置一样，` @Import `注释允许从另一个配置类加载` @Bean `定义，如下面的例子所示:

```java
@Configuration
public class ConfigA {

    @Bean
    public A a() {
        return new A();
    }
}

@Configuration
@Import(ConfigA.class)
public class ConfigB {

    @Bean
    public B b() {
        return new B();
    }
}
```

Now, rather than needing to specify both `ConfigA.class` and `ConfigB.class` when instantiating the context, only `ConfigB` needs to be supplied explicitly, as the following example shows:

现在，我们不需要同时指定` ConfigA。类`和`ConfigB。在实例化上下文时，只需要显式地提供` ConfigB `，如下面的示例所示:

```java
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);

    // now both beans A and B will be available...
    A a = ctx.getBean(A.class);
    B b = ctx.getBean(B.class);
}
```

This approach simplifies container instantiation, as only one class needs to be dealt with, rather than requiring you to remember a potentially large number of `@Configuration` classes during construction.

这种方法简化了容器实例化，因为只需要处理一个类，而不是要求您在构造期间记住可能大量的` @Configuration `类。

|      | As of Spring Framework 4.2, `@Import` also supports references to regular component classes, analogous to the `AnnotationConfigApplicationContext.register` method. This is particularly useful if you want to avoid component scanning, by using a few configuration classes as entry points to explicitly define all your components. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

在Spring Framework 4.2中，‘@Import’还支持对常规组件类的引用，类似于‘AnnotationConfigApplicationContext’。注册的方法。如果您想要避免组件扫描，通过使用几个配置类作为入口点显式地定义所有组件，这一点特别有用。
###### Injecting Dependencies on Imported `@Bean` Definitions

在导入的`@Bean`定义上注入依赖关系

The preceding example works but is simplistic. In most practical scenarios, beans have dependencies on one another across configuration classes. When using XML, this is not an issue, because no compiler is involved, and you can declare `ref="someBean"` and trust Spring to work it out during container initialization. When using `@Configuration` classes, the Java compiler places constraints on the configuration model, in that references to other beans must be valid Java syntax.

前面的示例可以工作，但过于简单。在大多数实际场景中，bean跨配置类相互依赖。在使用XML时，这不是问题，因为不涉及任何编译器，而且您可以声明` ref="someBean" `并相信Spring会在容器初始化期间解决这个问题。在使用` @Configuration `类时，Java编译器会对配置模型施加约束，因为对其他bean的引用必须是有效的Java语法。

Fortunately, solving this problem is simple. As [we already discussed](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-dependencies), a `@Bean` method can have an arbitrary number of parameters that describe the bean dependencies. Consider the following more real-world scenario with several `@Configuration` classes, each depending on beans declared in the others:

幸运的是，解决这个问题很简单。正如[我们已经讨论过的](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java-dependencies)，一个`@Bean`方法可以有任意数量的参数来描述bean依赖关系。考虑以下更真实的场景，有几个` @Configuration `类，每个类都依赖于其他类中声明的bean:

```java
@Configuration
public class ServiceConfig {

    @Bean
    public TransferService transferService(AccountRepository accountRepository) {
        return new TransferServiceImpl(accountRepository);
    }
}

@Configuration
public class RepositoryConfig {

    @Bean
    public AccountRepository accountRepository(DataSource dataSource) {
        return new JdbcAccountRepository(dataSource);
    }
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class})
public class SystemTestConfig {

    @Bean
    public DataSource dataSource() {
        // return new DataSource
    }
}

public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
    // everything wires up across configuration classes...
    TransferService transferService = ctx.getBean(TransferService.class);
    transferService.transfer(100.00, "A123", "C456");
}
```

There is another way to achieve the same result. Remember that `@Configuration` classes are ultimately only another bean in the container: This means that they can take advantage of `@Autowired` and `@Value` injection and other features the same as any other bean.

还有另一种方法可以达到同样的效果。请记住，` @Configuration `类最终只是容器中的另一个bean:这意味着它们可以利用` @Autowired `和` @Value `注入以及其他与任何其他bean相同的特性。

|      | Make sure that the dependencies you inject that way are of the simplest kind only. `@Configuration` classes are processed quite early during the initialization of the context, and forcing a dependency to be injected this way may lead to unexpected early initialization. Whenever possible, resort to parameter-based injection, as in the preceding example.Also, be particularly careful with `BeanPostProcessor` and `BeanFactoryPostProcessor` definitions through `@Bean`. Those should usually be declared as `static @Bean` methods, not triggering the instantiation of their containing configuration class. Otherwise, `@Autowired` and `@Value` may not work on the configuration class itself, since it is possible to create it as a bean instance earlier than [`AutowiredAnnotationBeanPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.html). |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

确保以这种方式注入的依赖项只属于最简单的类型。在上下文初始化过程中，` @Configuration `类很早就被处理了，强制以这种方式注入依赖可能会导致意外的早期初始化。尽可能使用基于参数的注入，如前面的示例所示。另外，要特别注意通过`@Bean`对`BeanPostProcessor`和`BeanFactoryPostProcessor`的定义。这些通常应该声明为`静态@Bean`方法，而不是触发它们包含configura的实例化

The following example shows how one bean can be autowired to another bean:

下面的例子展示了一个bean如何自动到另一个bean:


```java
@Configuration
public class ServiceConfig {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl(accountRepository);
    }
}

@Configuration
public class RepositoryConfig {

    private final DataSource dataSource;

    public RepositoryConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public AccountRepository accountRepository() {
        return new JdbcAccountRepository(dataSource);
    }
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class})
public class SystemTestConfig {

    @Bean
    public DataSource dataSource() {
        // return new DataSource
    }
}

public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
    // everything wires up across configuration classes...
	//所有东西都是跨配置类连接的…
    TransferService transferService = ctx.getBean(TransferService.class);
    transferService.transfer(100.00, "A123", "C456");
}
```

|      | Constructor injection in `@Configuration` classes is only supported as of Spring Framework 4.3. Note also that there is no need to specify `@Autowired` if the target bean defines only one constructor. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

`@Configuration`类中的| |构造函数注入只在Spring Framework 4.3中得到支持。还要注意，如果目标bean只定义了一个构造函数，就没有必要指定` @Autowired `。

Fully-qualifying imported beans for ease of navigation

完全限定导入的bean以方便导航

In the preceding scenario, using `@Autowired` works well and provides the desired modularity, but determining exactly where the autowired bean definitions are declared is still somewhat ambiguous. For example, as a developer looking at `ServiceConfig`, how do you know exactly where the `@Autowired AccountRepository` bean is declared? It is not explicit in the code, and this may be just fine. Remember that the [Spring Tools for Eclipse](https://spring.io/tools) provides tooling that can render graphs showing how everything is wired, which may be all you need. Also, your Java IDE can easily find all declarations and uses of the `AccountRepository` type and quickly show you the location of `@Bean` methods that return that type.

在前面的场景中，使用`@Autowired`工作得很好，并且提供了所需的模块化，但是确定autowired bean定义在哪里声明仍然有些模棱两可。例如，作为一个开发人员，您如何知道` @Autowired AccountRepository ` bean是在哪里声明的?它在代码中没有明确表示，这可能很好。请记住，[Eclipse的Spring Tools](https://spring.io/tools)提供了一些工具，可以呈现显示如何连接所有内容的图形，这可能是您所需要的全部。而且，您的Java IDE可以很容易地找到` AccountRepository `类型的所有声明和使用，并快速地向您显示返回该类型的` @Bean `方法的位置。

In cases where this ambiguity is not acceptable and you wish to have direct navigation from within your IDE from one `@Configuration` class to another, consider autowiring the configuration classes themselves. The following example shows how to do so:

在这种模棱两可的情况下，如果你想在IDE中直接从一个` @Configuration `类导航到另一个，可以考虑自动装配配置类本身。下面的例子展示了如何做到这一点:

```java
@Configuration
public class ServiceConfig {

    @Autowired
    private RepositoryConfig repositoryConfig;

    @Bean
    public TransferService transferService() {
        // navigate `through` the config class to the @Bean method!
		//通过配置类导航到@Bean方法!
        return new TransferServiceImpl(repositoryConfig.accountRepository());
    }
}
```

In the preceding situation, where `AccountRepository` is defined is completely explicit. However, `ServiceConfig` is now tightly coupled to `RepositoryConfig`. That is the tradeoff. This tight coupling can be somewhat mitigated by using interface-based or abstract class-based `@Configuration` classes. Consider the following example:

在前面的情况下，`AccountRepository`的定义是完全明确的。但是，‘ServiceConfig’现在与‘RepositoryConfig’紧密耦合。这就是权衡。通过使用基于接口或基于抽象类的`@Configuration`类，可以在一定程度上减轻这种紧密耦合。考虑下面的例子:

```java
@Configuration
public class ServiceConfig {

    @Autowired
    private RepositoryConfig repositoryConfig;

    @Bean
    public TransferService transferService() {
        return new TransferServiceImpl(repositoryConfig.accountRepository());
    }
}

@Configuration
public interface RepositoryConfig {

    @Bean
    AccountRepository accountRepository();
}

@Configuration
public class DefaultRepositoryConfig implements RepositoryConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new JdbcAccountRepository(...);
    }
}

@Configuration
@Import({ServiceConfig.class, DefaultRepositoryConfig.class})  // import the concrete config!
public class SystemTestConfig {

    @Bean
    public DataSource dataSource() {
        // return DataSource
    }

}

public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
    TransferService transferService = ctx.getBean(TransferService.class);
    transferService.transfer(100.00, "A123", "C456");
}
```

Now `ServiceConfig` is loosely coupled with respect to the concrete `DefaultRepositoryConfig`, and built-in IDE tooling is still useful: You can easily get a type hierarchy of `RepositoryConfig` implementations. In this way, navigating `@Configuration` classes and their dependencies becomes no different than the usual process of navigating interface-based code.

现在，‘ServiceConfig’与具体的‘DefaultRepositoryConfig’是松散耦合的，而且内置的IDE工具仍然有用:你可以很容易地获得‘RepositoryConfig’实现的类型层次结构。这样，导航` @Configuration `类及其依赖项与导航基于接口的代码的通常过程没有什么不同。

|      | If you want to influence the startup creation order of certain beans, consider declaring some of them as `@Lazy` (for creation on first access instead of on startup) or as `@DependsOn` certain other beans (making sure that specific other beans are created before the current bean, beyond what the latter’s direct dependencies imply). |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

如果你想影响某些bean类的启动创建订单,考虑将其中一些声明为`@Lazy`(用于创建在第一次访问,而不是在启动时)或`@DependsOn`某些其他bean(确保特定的其他bean创建当前bean之前,超出后者的直接依赖关系暗示)。

###### Conditionally Include `@Configuration` Classes or `@Bean` Methods

It is often useful to conditionally enable or disable a complete `@Configuration` class or even individual `@Bean` methods, based on some arbitrary system state. One common example of this is to use the `@Profile` annotation to activate beans only when a specific profile has been enabled in the Spring `Environment` (see [Bean Definition Profiles](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition-profiles) for details).

根据任意的系统状态，有条件地启用或禁用一个完整的`@Configuration`类，甚至单个的`@Bean`方法，这通常很有用。一个常见的例子是，只有在Spring`环境`中启用了特定的概要文件时，才使用`@Profile`注释来激活Bean(详细信息请参见[Bean定义概要文件](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# beans-definiation-profiles)。

The `@Profile` annotation is actually implemented by using a much more flexible annotation called [`@Conditional`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Conditional.html). The `@Conditional` annotation indicates specific `org.springframework.context.annotation.Condition` implementations that should be consulted before a `@Bean` is registered.

`@Profile`注释实际上是通过使用一个更灵活的注释[` @Conditional `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Conditional.html)来实现的。`@Conditional`注释表示特定的`org.springframework.context.annotation`。在注册` @Bean `之前应该咨询的条件`实现。

Implementations of the `Condition` interface provide a `matches(…)` method that returns `true` or `false`. For example, the following listing shows the actual `Condition` implementation used for `@Profile`:

`Condition`接口的实现提供了一个`matches(…)`方法，返回`true`或`false`。例如，下面的清单显示了用于` @Profile `的实际` Condition `实现:

```java
@Override
public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    // Read the @Profile annotation attributes
    MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
    if (attrs != null) {
        for (Object value : attrs.get("value")) {
            if (context.getEnvironment().acceptsProfiles(((String[]) value))) {
                return true;
            }
        }
        return false;
    }
    return true;
}
```

See the [`@Conditional`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Conditional.html) javadoc for more detail.
参见[` @Conditional `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Conditional.html) javadoc获得更多细节。

###### Combining Java and XML Configuration

结合Java和XML配置

Spring’s `@Configuration` class support does not aim to be a 100% complete replacement for Spring XML. Some facilities, such as Spring XML namespaces, remain an ideal way to configure the container. In cases where XML is convenient or necessary, you have a choice: either instantiate the container in an `XML-centric` way by using, for example, `ClassPathXmlApplicationContext`, or instantiate it in a `Java-centric` way by using `AnnotationConfigApplicationContext` and the `@ImportResource` annotation to import XML as needed.

Spring的` @Configuration `类支持的目标并不是100%完全替代Spring XML。有些工具(如Spring XML名称空间)仍然是配置容器的理想方式。在这种情况下,XML是方便还是必要的,你有一个选择:要么在容器实例化在一个`以XML为中心`的方式使用,例如,`ClassPathXmlApplicationContext`,或`以java为中心`的方式实例化它通过使用`所`和`@ImportResource`注释导入XML。

###### XML-centric Use of `@Configuration` Classes

以xml为中心使用` @Configuration `类

It may be preferable to bootstrap the Spring container from XML and include `@Configuration` classes in an ad-hoc fashion. For example, in a large existing codebase that uses Spring XML, it is easier to create `@Configuration` classes on an as-needed basis and include them from the existing XML files. Later in this section, we cover the options for using `@Configuration` classes in this kind of `XML-centric` situation.

最好是从XML引导Spring容器，并以特别的方式包含` @Configuration `类。例如，在使用Spring XML的大型现有代码库中，根据需要创建` @Configuration `类并从现有XML文件中包含它们更容易。在本节的后面，我们将介绍在这种`以xml为中心`的情况下使用`@Configuration`类的选项。

Declaring `@Configuration` classes as plain Spring `<bean/>` elements

将` @Configuration `类声明为普通的Spring ` <bean/> `元素

Remember that `@Configuration` classes are ultimately bean definitions in the container. In this series examples, we create a `@Configuration` class named `AppConfig` and include it within `system-test-config.xml` as a `<bean/>` definition. Because `<context:annotation-config/>` is switched on, the container recognizes the `@Configuration` annotation and processes the `@Bean` methods declared in `AppConfig` properly.

请记住，` @Configuration `类最终是容器中的bean定义。在本系列示例中，我们创建一个名为`AppConfig`的`@Configuration`类，并将其包含在`system-test-config`中。xml `作为` <bean/> `定义。因为` <context:annotation-config/> `是打开的，容器识别` @Configuration `注释并正确处理` AppConfig `中声明的` @Bean `方法。

The following example shows an ordinary configuration class in Java:
下面的例子展示了一个普通的Java配置类:

```java
@Configuration
public class AppConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public AccountRepository accountRepository() {
        return new JdbcAccountRepository(dataSource);
    }

    @Bean
    public TransferService transferService() {
        return new TransferService(accountRepository());
    }
}
```

The following example shows part of a sample `system-test-config.xml` file:

下面的示例显示了示例系统测试配置的一部分。xml的文件:

```xml
<beans>
    <!-- enable processing of annotations such as @Autowired and @Configuration -->
    <context:annotation-config/>
    <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>

    <bean class="com.acme.AppConfig"/>

    <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```

The following example shows a possible `jdbc.properties` file:
下面的例子展示了一个可能的` jdbc `。属性的文件:
```
jdbc.url=jdbc:hsqldb:hsql://localhost/xdb
jdbc.username=sa
jdbc.password=
```


```java
public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/acme/system-test-config.xml");
    TransferService transferService = ctx.getBean(TransferService.class);
    // ...
}
```

|      | In `system-test-config.xml` file, the `AppConfig` `<bean/>` does not declare an `id` element. While it would be acceptable to do so, it is unnecessary, given that no other bean ever refers to it, and it is unlikely to be explicitly fetched from the container by name. Similarly, the `DataSource` bean is only ever autowired by type, so an explicit bean `id` is not strictly required. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |
在`system-test-config。` AppConfig ` ` <bean/> `没有声明` id `元素。虽然这样做是可以接受的，但是没有必要这样做，因为没有其他bean引用过它，而且不太可能通过名称显式地从容器中获取它。类似地，`DataSource`bean仅仅是通过类型自动生成的，所以显式的bean`id`并不是严格要求的。
Using <context:component-scan/> to pick up `@Configuration` classes

Because `@Configuration` is meta-annotated with `@Component`, `@Configuration`-annotated classes are automatically candidates for component scanning. Using the same scenario as describe in the previous example, we can redefine `system-test-config.xml` to take advantage of component-scanning. Note that, in this case, we need not explicitly declare `<context:annotation-config/>`, because `<context:component-scan/>` enables the same functionality.

因为‘@Configuration’是用‘@Component’元注释的，所以‘@Configuration’注释的类会自动成为组件扫描的候选对象。使用与前面示例中描述的相同的场景，我们可以重新定义`system-test-config`。利用组件扫描的优势。注意，在这种情况下，我们不需要显式地声明` <context: annotent -config/> `，因为` <context: componentent -scan/> `支持相同的功能。
The following example shows the modified `system-test-config.xml` file:
下面的示例显示修改后的` system-test-config.xml`的文件:
```xml
<beans>
    <!-- picks up and registers AppConfig as a bean definition -->
    <context:component-scan base-package="com.acme"/>
    <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>

    <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```

###### `@Configuration` Class-centric Use of XML with `@ImportResource`

使用`@ImportResource`以类为中心使用XML

In applications where `@Configuration` classes are the primary mechanism for configuring the container, it is still likely necessary to use at least some XML. In these scenarios, you can use `@ImportResource` and define only as much XML as you need. Doing so achieves a `Java-centric` approach to configuring the container and keeps XML to a bare minimum. The following example (which includes a configuration class, an XML file that defines a bean, a properties file, and the `main` class) shows how to use the `@ImportResource` annotation to achieve `Java-centric` configuration that uses XML as needed:

在以` @Configuration `类作为配置容器的主要机制的应用程序中，仍然可能需要使用至少一些XML。在这些场景中，您可以使用` @ImportResource `并只定义所需的XML。这样做可以实现以java为中心的方法来配置容器，并将XML保持在最低限度。下面的示例(包括一个配置类、一个定义bean的XML文件、一个属性文件和`main`类)展示了如何使用`@ImportResource`注释来实现以java为中心的配置，从而根据需要使用XML

```java
@Configuration
@ImportResource("classpath:/com/acme/properties-config.xml")
public class AppConfig {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(url, username, password);
    }
}
properties-config.xml
<beans>
    <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>
</beans>
jdbc.properties
jdbc.url=jdbc:hsqldb:hsql://localhost/xdb
jdbc.username=sa
jdbc.password=
```

```java
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    TransferService transferService = ctx.getBean(TransferService.class);
    // ...
}
```

#### 1.13. Environment Abstraction 环境抽象

The [`Environment`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/env/Environment.html) interface is an abstraction integrated in the container that models two key aspects of the application environment: [profiles](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition-profiles) and [properties](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-property-source-abstraction).
`Environment`接口是抽象集成模型的容器应用程序环境的两个关键方面:[profiles](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html beans-definition-profiles)和[properties](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html # beans-property-source-abstraction)。

A profile is a named, logical group of bean definitions to be registered with the container only if the given profile is active. Beans may be assigned to a profile whether defined in XML or with annotations. The role of the `Environment` object with relation to profiles is in determining which profiles (if any) are currently active, and which profiles (if any) should be active by default.

概要文件(配置文件)是一个命名的bean定义逻辑组，只有在给定的概要文件处于活动状态时才向容器注册。bean可以被分配给一个配置文件，无论这个配置文件是用XML定义的还是用注释定义的。与概要文件相关的`Environment`对象的作用是确定哪些概要文件(如果有的话)当前处于活动状态，以及哪些概要文件(如果有的话)在默认情况下应该处于活动状态。

Properties play an important role in almost all applications and may originate from a variety of sources: properties files, JVM system properties, system environment variables, JNDI, servlet context parameters, ad-hoc `Properties` objects, `Map` objects, and so on. The role of the `Environment` object with relation to properties is to provide the user with a convenient service interface for configuring property sources and resolving properties from them.

属性在几乎所有的应用程序中都扮演着重要的角色，并且可能来自各种各样的来源:属性文件、JVM系统属性、系统环境变量、JNDI、servlet上下文参数、特别的`Properties`对象、`Map`对象等等。与属性相关的`Environment`对象的作用是为用户提供一个方便的服务接口，用于`配置`属性源和从属性源`解析`属性。

##### 1.13.1. Bean Definition Profiles
Bean定义概要文件
Bean definition profiles provide a mechanism in the core container that allows for registration of different beans in different environments. The word, `environment,` can mean different things to different users, and this feature can help with many use cases, including:

Bean定义概要文件在核心容器中提供了一种机制，允许在不同环境中注册不同的Bean。`environment`这个词对不同的用户可能意味着不同的东西，这个特性可以帮助很多用例，包括:

Working against an in-memory datasource in development versus looking up that same datasource from JNDI when in QA or production.
在开发中使用内存中的数据源，而在QA或生产中从JNDI查找相同的数据源。

Registering monitoring infrastructure only when deploying an application into a performance environment.
注册监控基础设施时，只有部署一个应用程序到一个性能环境。

Registering customized implementations of beans for customer A versus customer B deployments.
为客户A和客户B的部署注册定制的bean实现。
Consider the first use case in a practical application that requires a `DataSource`. In a test environment, the configuration might resemble the following:
考虑需要`数据源`的实际应用程序中的第一个用例。在测试环境中，配置可能类似如下:

```java
@Bean
public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .addScript("my-schema.sql")
        .addScript("my-test-data.sql")
        .build();
}
```

Now consider how this application can be deployed into a QA or production environment, assuming that the datasource for the application is registered with the production application server’s JNDI directory. Our `dataSource` bean now looks like the following listing:

现在考虑如何将该应用程序部署到QA（QUALITY ASSURANCE ）或生产环境中，假设应用程序的数据源已注册到生产应用程序服务器的*JNDI*(Java Naming and Directory Interface,Java命名和目录接口)目录中。我们的`dataSource`bean现在看起来如下所示:

```java
@Bean(destroyMethod="")
public DataSource dataSource() throws Exception {
    Context ctx = new InitialContext();
    return (DataSource) ctx.lookup("java:comp/env/jdbc/datasource");
}
```

The problem is how to switch between using these two variations based on the current environment. Over time, Spring users have devised a number of ways to get this done, usually relying on a combination of system environment variables and XML `<import/>` statements containing `${placeholder}` tokens that resolve to the correct configuration file path depending on the value of an environment variable. Bean definition profiles is a core container feature that provides a solution to this problem.

问题是如何根据当前环境在使用这两种变体之间切换。随着时间的推移，Spring用户已经设计了许多方法来实现这一点，通常依赖于系统环境变量和XML ` <import/> `语句的组合，这些语句包含` ${placeholder（占位符）}`令牌，这些令牌根据环境变量的值解析为正确的配置文件路径。Bean定义概要文件是为这个问题提供解决方案的核心容器特性。

If we generalize the use case shown in the preceding example of environment-specific bean definitions, we end up with the need to register certain bean definitions in certain contexts but not in others. You could say that you want to register a certain profile of bean definitions in situation A and a different profile in situation B. We start by updating our configuration to reflect this need.

如果我们泛化前面环境特定bean定义示例中所示的用例，我们最终需要在某些上下文中注册某些bean定义，而不是在其他上下文中。您可以说，您希望在情形a中注册某个bean定义的概要文件，在情形b中注册一个不同的概要文件。我们首先更新配置以反映这一需求。

###### Using `@Profile`

The [`@Profile`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Profile.html) annotation lets you indicate that a component is eligible for registration when one or more specified profiles are active. Using our preceding example, we can rewrite the `dataSource` configuration as follows:
[` @Profile `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/Profile.html)注释允许您指出，当一个或多个指定的概要文件是有效的时候，一个组件是合格的注册。使用我们前面的例子，我们可以重写`dataSource`配置如下:


```java
@Configuration
@Profile("development")
public class StandaloneDataConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("classpath:com/bank/config/sql/schema.sql")
            .addScript("classpath:com/bank/config/sql/test-data.sql")
            .build();
    }
}
```



```java
@Configuration
@Profile("production")
public class JndiDataConfig {

    @Bean(destroyMethod="")
    public DataSource dataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:comp/env/jdbc/datasource");
    }
}
```

As mentioned earlier, with `@Bean` methods, you typically choose to use programmatic JNDI lookups, by using either Spring's `JndiTemplate`/`JndiLocatorDelegate` helpers or the straight JNDI `InitialContext` usage shown earlier but not the `JndiObjectFactoryBean` variant, which would force you to declare the return type as the `FactoryBean` type.

与@ bean的方法,如前所述,您通常选择使用程序化的JNDI查找,通过使用Spring的`JndiTemplate`/`JndiLocatorDelegate`助手或直接使用JNDI的`InitialContext` `JndiObjectFactoryBean`变体,但不是早些时候显示这将迫使你声明返回类型作为`FactoryBean`类型。

The profile string may contain a simple profile name (for example, `production`) or a profile expression. A profile expression allows for more complicated profile logic to be expressed (for example, `production & us-east`). The following operators are supported in profile expressions:

概要文件字符串可以包含一个简单的概要文件名称(例如，` production `)或一个概要文件表达式。配置文件表达式允许表达更复杂的配置文件逻辑(例如，`production & us-east`)。配置文件表达式支持以下操作符:

- `!`: A logical `not` of the profile
- `&`: A logical `and` of the profiles
- `|`: A logical `or` of the profiles

You cannot mix the `&` and `|` operators without using parentheses. For example, `production & us-east | eu-central` is not a valid expression. It must be expressed as `production & (us-east eu-central)`.

如果不使用括号，就不能混合使用` & `和` | `操作符。例如，`production & us-east | eu-central`不是一个有效的表达式。

You can use `@Profile` as a [meta-annotation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-meta-annotations) for the purpose of creating a custom composed annotation. The following example defines a custom `@Production` annotation that you can use as a drop-in replacement for `@Profile("production")`:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("production")
public @interface Production {
}
```

If a `@Configuration` class is marked with `@Profile`, all of the `@Bean` methods and `@Import` annotations associated with that class are bypassed unless one or more of the specified profiles are active. If a `@Component` or `@Configuration` class is marked with `@Profile({"p1", "p2"})`, that class is not registered or processed unless profiles `p1` or `p2` have been activated. If a given profile is prefixed with the NOT operator (`!`), the annotated element is registered only if the profile is not active. For example, given `@Profile({"p1", "!p2"})`, registration will occur if profile `p1` is active or if profile `p2` is not active. 

如果一个` @Configuration `类被标记为` @Profile `，那么与该类关联的所有` @Bean `方法和` @Import `注释都将被绕过，除非一个或多个指定的概要文件是活动的。如果一个` @Component `或` @Configuration `类被标记为` @Profile({"p1"， "p2"}) `，那么除非配置文件`p1`或`p2`已经被激活，否则该类不会被注册或处理。如果给定的概要文件以NOT操作符(` ! `)作为前缀，那么仅当概要文件不活动时，注释元素才会被注册。例如，给定` @Profile({"p1"， "!p2"}) `，如果配置文件`p1`是活动的，或者配置文件`p2`不是活动的，就会进行注册。

`@Profile` can also be declared at the method level to include only one particular bean of a configuration class (for example, for alternative variants of a particular bean), as the following example shows:

`@Profile`也可以在方法级别声明，只包含一个配置类的一个特定bean(例如，一个特定bean的其他变体)，如下面的例子所示:


```java
@Configuration
public class AppConfig {

    @Bean("dataSource")
    @Profile("development") 
    public DataSource standaloneDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("classpath:com/bank/config/sql/schema.sql")
            .addScript("classpath:com/bank/config/sql/test-data.sql")
            .build();
    }

    @Bean("dataSource")
    @Profile("production") 
    public DataSource jndiDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:comp/env/jdbc/datasource");
    }
}
```

The `standaloneDataSource` method is available only in the `development` profile. 
The `jndiDataSource` method is available only in the `production` profile.
`standaloneDataSource`方法只在`development`配置文件中可用。
`jndiDataSource`方法只在`production`配置文件中可用。

With `@Profile` on `@Bean` methods, a special scenario may apply: In the case of overloaded `@Bean` methods of the same Java method name (analogous to constructor overloading), a `@Profile` condition needs to be consistently declared on all overloaded methods. If the conditions are inconsistent, only the condition on the first declaration among the overloaded methods matters. Therefore, `@Profile` can not be used to select an overloaded method with a particular argument signature over another. Resolution between all factory methods for the same bean follows Spring`s constructor resolution algorithm at creation time.If you want to define alternative beans with different profile conditions, use distinct Java method names that point to the same bean name by using the `@Bean` name attribute, as shown in the preceding example. If the argument signatures are all the same (for example, all of the variants have no-arg factory methods), this is the only way to represent such an arrangement in a valid Java class in the first place (since there can only be one method of a particular name and argument signature). 

在`@Bean`方法上使用`@Profile`时，可能会应用一种特殊的场景:在重载了相同Java方法名的`@Bean`方法的情况下(类似于构造函数重载)，需要在所有重载的方法上一致地声明一个`@Profile`条件。如果条件不一致，则只有重载方法中第一个声明的条件才会起作用。因此，` @Profile `不能用于选择具有特定参数签名的重载方法。同一个bean的所有工厂方法之间的解析在创建时遵循Spring构造函数解析算法。如果您希望定义具有不同配置文件条件的可选bean，请使用不同的Java方法名称，通过使用` @Bean ` name属性指向相同的bean名称，如前面的示例所示。如果参数签名都是相同的(例如,所有的变量都不带参数工厂方法),这是唯一的方式来表示这种安排在第一时间有效的Java类(因为只能有一个方法的名称和参数签名)。

###### XML Bean Definition Profiles
XML Bean定义配置文件

The XML counterpart is the `profile` attribute of the `<beans>` element. Our preceding sample configuration can be rewritten in two XML files, as follows:
XML对应元素是` <beans> `元素的` profile `属性。我们前面的示例配置可以在两个XML文件中重写，如下:

```xml
<beans profile="development"
    xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xsi:schemaLocation="...">

    <jdbc:embedded-database id="dataSource">
        <jdbc:script location="classpath:com/bank/config/sql/schema.sql"/>
        <jdbc:script location="classpath:com/bank/config/sql/test-data.sql"/>
    </jdbc:embedded-database>
</beans>

<beans profile="production"
    xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xsi:schemaLocation="...">

    <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>
</beans>
```

It is also possible to avoid that split and nest `<beans/>` elements within the same file, as the following example shows:

也可以避免在同一个文件中分割和嵌套` <beans/> `元素，如下面的例子所示:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xsi:schemaLocation="...">

    <!-- other bean definitions -->

    <beans profile="development">
        <jdbc:embedded-database id="dataSource">
            <jdbc:script location="classpath:com/bank/config/sql/schema.sql"/>
            <jdbc:script location="classpath:com/bank/config/sql/test-data.sql"/>
        </jdbc:embedded-database>
    </beans>

    <beans profile="production">
        <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>
    </beans>
</beans>
```

The `spring-bean.xsd` has been constrained to allow such elements only as the last ones in the file. This should help provide flexibility without incurring clutter in the XML files.

`spring-bean.xsd `被限制为只允许这样的元素作为文件中的最后一个元素。这将有助于提供灵活性，而不会导致XML文件的混乱。

The XML counterpart does not support the profile expressions described earlier. It is possible, however, to negate a profile by using the `!` operator. It is also possible to apply a logical `and` by nesting the profiles, as the following example shows:
XML对等物不支持前面描述的概要文件表达式。这是可能的，然而，通过使用` !`的运营商。也可以通过嵌套配置文件来应用逻辑`和`，如下面的例子所示:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"    
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
xmlns:jdbc="http://www.springframework.org/schema/jdbc"    
xmlns:jee="http://www.springframework.org/schema/jee"    
xsi:schemaLocation="...">     
<!-- other bean definitions -->     
<beans profile="production">        
<beans profile="us-east">            
<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>        
</beans>    
</beans> </beans>
```


`In the preceding example, the `dataSource` bean is exposed if both the `production` and `us-east'profiles are active.
在前面的示例中，如果`production`和`us-east`配置文件都是活动的，那么`dataSource`bean将被公开。

###### Activating a Profile

激活一个配置文件

Now that we have updated our configuration, we still need to instruct Spring which profile is active. If we started our sample application right now, we would see a `NoSuchBeanDefinitionException` thrown, because the container could not find the Spring bean named `dataSource`.

现在我们已经更新了配置，我们仍然需要指示Spring哪个配置文件是活动的。如果我们现在开始我们的示例应用程序，我们会看到一个`NoSuchBeanDefinitionException`抛出，因为容器无法找到名为`dataSource`的Spring bean。

Activating a profile can be done in several ways, but the most straightforward is to do it programmatically against the `Environment` API which is available through an `ApplicationContext`. The following example shows how to do so:

激活一个概要文件可以通过几种方式完成，但最直接的是通过`ApplicationContext`可用的`Environment`API编程来完成。下面的例子展示了如何做到这一点:

```java
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.getEnvironment().setActiveProfiles("development");
ctx.register(SomeConfig.class, StandaloneDataConfig.class, JndiDataConfig.class);
ctx.refresh();
```

In addition, you can also declaratively activate profiles through the `spring.profiles.active` property, which may be specified through system environment variables, JVM system properties, servlet context parameters in `web.xml`, or even as an entry in JNDI (see [`PropertySource` Abstraction](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-property-source-abstraction)). In integration tests, active profiles can be declared by using the `@ActiveProfiles` annotation in the `spring-test` module (see [context configuration with environment profiles](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testcontext-ctx-management-env-profiles)).

此外，您还可以通过`spring.profiles.active`声明性地激活配置文件。属性，可以通过`web.xml`中的系统环境变量、JVM系统属性和servlet上下文参数来指定。或者甚至作为JNDI中的一个条目(请参见[` PropertySource `抽象](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#beans- propert-source-abstract))。在集成测试中，可以通过在spring-test模块中使用`@ActiveProfiles`注释来声明活动概要文件(参见[环境概要文件的上下文配置](https://docs.spring.io/springing-framework/docs/current/reference/html/testing.html #testcontext-ctx-management-env-profiles))。

Note that profiles are not an `either-or` proposition. You can activate multiple profiles at once. Programmatically, you can provide multiple profile names to the `setActiveProfiles()` method, which accepts `String…` varargs. The following example activates multiple profiles:

请注意，配置文件不是一个`either-or`的命题。您可以同时激活多个配置文件。通过编程，您可以为` setActiveProfiles() `方法提供多个配置文件名称，该方法接受` String…`可变参数。下面的示例激活多个配置文件:

```java
ctx.getEnvironment().setActiveProfiles("profile1", "profile2");
```

Declaratively, `spring.profiles.active` may accept a comma-separated list of profile names, as the following example shows:

以声明的方式,`spring.profiles.active`可以接受以逗号分隔的配置文件名称列表，如下例所示:

```
    -Dspring.profiles.active="profile1,profile2"
```

###### Default Profile

默认的配置

The default profile represents the profile that is enabled by default. Consider the following example:

默认配置文件表示默认情况下启用的配置文件。考虑下面的例子:

```java
@Configuration
@Profile("default")
public class DefaultDataConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("classpath:com/bank/config/sql/schema.sql")
            .build();
    }
}
```

If no profile is active, the `dataSource` is created. You can see this as a way to provide a default definition for one or more beans. If any profile is enabled, the default profile does not apply.

如果没有配置文件是有效的，则创建`数据源`。您可以将此看作为一个或多个bean提供默认定义的一种方法。如果启用了任何配置文件，则不应用默认配置文件。

You can change the name of the default profile by using `setDefaultProfiles()` on the `Environment` or ,declaratively, by using the `spring.profiles.default` property.

您可以通过在`Environment`上使用`setDefaultProfiles()`来更改默认配置文件的名称，或者通过声明性地使用`spring.profile .default`属性。

s it is available in the `Environment`:

```xml
<beans>
    <import resource="com/bank/service/${customer}-config.xml"/>
</beans>
```

##### 1.13.2. `PropertySource` Abstraction `PropertySource`抽象

Spring's `Environment` abstraction provides search operations over a configurable hierarchy of property sources. Consider the following listing:

Spring的`Environment`抽象在属性源的可配置层次结构上提供搜索操作。考虑下面的清单:

```java
ApplicationContext ctx = new GenericApplicationContext();
Environment env = ctx.getEnvironment();
boolean containsMyProperty = env.containsProperty("my-property");
System.out.println("Does my environment contain the `my-property` property? " + containsMyProperty);
```

In the preceding snippet, we see a high-level way of asking Spring whether the `my-property` property is defined for the current environment. To answer this question, the `Environment` object performs a search over a set of [`PropertySource`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/env/PropertySource.html) objects. A `PropertySource` is a simple abstraction over any source of key-value pairs, and Spring's [`StandardEnvironment`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/env/StandardEnvironment.html) is configured with two PropertySource objects — one representing the set of JVM system properties (`System.getProperties()`) and one representing the set of system environment variables (`System.getenv()`).

在前面的代码片段中，我们看到了一种高级方法，可以询问Spring是否为当前环境定义了`my-property`属性。要回答这个问题，`Environment`对象对一组[` PropertySource `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/env/PropertySource.html)对象执行搜索。`PropertySource`是一个简单的抽象在任何键-值对的来源,和spring的(`StandardEnvironment`)配置了两个PropertySource对象代表一个JVM系统属性的集合(`System.getProperties()`)和一个代表系统环境变量的设置(`System.getenv()`)。

These default property sources are present for `StandardEnvironment`, for use in standalone applications. [`StandardServletEnvironment`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/web/context/support/StandardServletEnvironment.html) is populated with additional default property sources including servlet config and servlet context parameters. It can optionally enable a [`JndiPropertySource`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/jndi/JndiPropertySource.html). See the javadoc for details. 

这些默认属性源是为`StandardEnvironment`提供的，用于独立应用程序。` standardservletenvirment`由附加的默认属性源填充，包括servlet配置和servlet上下文参数。它可以选择启用一个[` JndiPropertySource `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/jndi/JndiPropertySource.html)。详细信息请参阅javadoc

Concretely, when you use the `StandardEnvironment`, the call to `env.containsProperty("my-property")` returns true if a `my-property` system property or `my-property` environment variable is present at runtime.

具体地说，当你使用`StandardEnvironment`时，如果`my-property`系统属性或`my-property`环境变量在运行时出现，那么对`env.containsProperty(`my-property`)的调用将返回true。

The search performed is hierarchical. By default, system properties have precedence over environment variables. So, if the `my-property` property happens to be set in both places during a call to `env.getProperty("my-property")`, the system property value `wins` and is returned. Note that property values are not merged but rather completely overridden by a preceding entry.For a common `StandardServletEnvironment`, the full hierarchy is as follows, with the highest-precedence entries at the top:

ServletConfig parameters (if applicable — for example, in case of a `DispatcherServlet` context)

ServletContext parameters (web.xml context-param entries)

JNDI environment variables (`java:comp/env/` entries)

JVM system properties (`-D` command-line arguments)

JVM system environment (operating system environment variables)

所执行的搜索是分层的。默认情况下，系统属性优先于环境变量。因此，如果在调用env.getProperty(“my-property”)期间，恰好在两个位置都设置了my-property属性，则系统属性值“胜出”并被返回。注意，属性值没有被合并，而是被前面的条目完全覆盖。为了一个共同的`StandardServletEnvironment`,完整的层次结构如下所示，最高优先级的条目位于顶部:

1 ServletConfig参数(如果适用——例如，在DispatcherServlet上下文的情况下)

2 ServletContext参数(web.xml上下文参数项)

3 JNDI环境变量(java:comp/env/ entries)

4 JVM系统属性(-D命令行参数)

5 JVM系统环境(操作系统环境变量)

Most importantly, the entire mechanism is configurable. Perhaps you have a custom source of properties that you want to integrate into this search. To do so, implement and instantiate your own `PropertySource` and add it to the set of `PropertySources` for the current `Environment`. The following example shows how to do so:

最重要的是，整个机制是可配置的。也许您有一个想要集成到这个搜索中的自定义属性源。为此，实现并实例化您自己的` PropertySource `，并将其添加到当前`Environment`的` PropertySources `集合中。下面的例子展示了如何做到这一点:

```java
ConfigurableApplicationContext ctx = new GenericApplicationContext();
MutablePropertySources sources = ctx.getEnvironment().getPropertySources();
sources.addFirst(new MyPropertySource());
```

In the preceding code, `MyPropertySource` has been added with highest precedence in the search. If it contains a `my-property` property, the property is detected and returned, in favor of any `my-property` property in any other `PropertySource`. The [`MutablePropertySources`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/env/MutablePropertySources.html) API exposes a number of methods that allow for precise manipulation of the set of property sources.

在前面的代码中，在搜索中以最高的优先级添加了MyPropertySource。如果它包含my-property属性，则检测并返回该属性，从而有利于任何其他PropertySource中的任何my-property属性。MutablePropertySources API公开了许多允许精确操作属性源集的方法。

##### 1.13.3. Using `@PropertySource`
使用`@PropertySource`
The [`@PropertySource`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/PropertySource.html) annotation provides a convenient and declarative mechanism for adding a `PropertySource` to Spring's `Environment`.

[` @PropertySource `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/annotation/PropertySource.html)注释提供了一个方便的声明性机制，用于向Spring的`Environment`添加一个` PropertySource `。

Given a file called `app.properties` that contains the key-value pair `testbean.name=myTestBean`, the following `@Configuration` class uses `@PropertySource` in such a way that a call to `testBean.getName()` returns `myTestBean`:

给定一个名为app.properties的文件。属性包含键值对` testbean.name=myTestBean `，下面的` @Configuration `类使用` @PropertySource `，调用` testBean.getName() `返回` myTestBean `:

```java
@Configuration
@PropertySource("classpath:/com/myco/app.properties")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }
}
```

Any `${…}` placeholders present in a `@PropertySource` resource location are resolved against the set of property sources already registered against the environment, as the following example shows:

任何出现在`@PropertySource`资源位置中的`${…}`占位符都会根据已经在环境中注册的属性源集进行解析，如下面的示例所示:

```java
@Configuration
@PropertySource("classpath:/com/${my.placeholder:default/path}/app.properties")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }
}
```

Assuming that `my.placeholder` is present in one of the property sources already registered (for example, system properties or environment variables), the placeholder is resolved to the corresponding value. If not, then `default/path` is used as a default. If no default is specified and a property cannot be resolved, an `IllegalArgumentException` is thrown.

假设我的。`my.placeholder`出现在一个已注册的属性源中(例如，系统属性或环境变量)，则占位符解析为相应的值。如果不是，则使用`default/path`作为默认值。如果没有指定默认值，且无法解析属性，则抛出`IllegalArgumentException`。

The `@PropertySource` annotation is repeatable, according to Java 8 conventions. However, all such `@PropertySource` annotations need to be declared at the same level, either directly on the configuration class or as meta-annotations within the same custom annotation. Mixing direct annotations and meta-annotations is not recommended, since direct annotations effectively override meta-annotations.

根据Java 8的约定，`@PropertySource`注释是可重复的。但是，所有这样的`@PropertySource`注释都需要在同一级别声明，要么直接在配置类中声明，要么作为同一自定义注释中的元注释声明。不推荐混合使用直接注释和元注释，因为直接注释有效地覆盖了元注释。
##### 1.13.4. Placeholder Resolution in Statements
语句中的占位符解析
Historically, the value of placeholders in elements could be resolved only against JVM system properties or environment variables. This is no longer the case. Because the `Environment` abstraction is integrated throughout the container, it is easy to route resolution of placeholders through it. This means that you may configure the resolution process in any way you like. You can change the precedence of searching through system properties and environment variables or remove them entirely. You can also add your own property sources to the mix, as appropriate.

过去，元素中的占位符的值只能根据JVM系统属性或环境变量解析。现在情况已经不一样了。因为`Environment`抽象集成在整个容器中，所以很容易通过它来解析占位符。这意味着您可以以任何您喜欢的方式配置解析过程。您可以更改搜索系统属性和环境变量的优先级，或者完全删除它们。您还可以在适当的情况下添加您自己的属性源。

Concretely, the following statement works regardless of where the `customer` property is defined, as long as it is available in the `Environment`:

具体地说，无论`customer`属性定义在哪里，只要它在`Environment`中可用，以下语句都适用:

```xml
<beans>
    <import resource="com/bank/service/${customer}-config.xml"/>
</beans>
```

#### 1.14. Registering a LoadTimeWeaver 注册LoadTimeWeaver

The `LoadTimeWeaver` is used by Spring to dynamically transform classes as they are loaded into the Java virtual machine (JVM).

当类被加载到Java虚拟机(JVM)中时，Spring使用`oadTimeWeaver`动态地转换类。

To enable load-time weaving, you can add the `@EnableLoadTimeWeaving` to one of your `@Configuration` classes, as the following example shows:

要启用加载时编织，您可以将`@ enableloadtime`织造加到您的`@Configuration `中，如下面的示例所示:

```java
@Configuration
@EnableLoadTimeWeaving
public class AppConfig {
}
```

Alternatively, for XML configuration, you can use the `context:load-time-weaver` element:
或者，对于XML配置，您可以使用`ontext:load-time-weaver`素:

```xml
<beans>
    <context:load-time-weaver/>
</beans>
```

Once configured for the `ApplicationContext`, any bean within that `ApplicationContext` may implement `LoadTimeWeaverAware`, thereby receiving a reference to the load-time weaver instance. This is particularly useful in combination with [Spring’s JPA support](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#orm-jpa) where load-time weaving may be necessary for JPA class transformation. Consult the [`LocalContainerEntityManagerFactoryBean`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html) javadoc for more detail. For more on AspectJ load-time weaving, see [Load-time Weaving with AspectJ in the Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw).

一旦为`applicationContext`置好，`applicationContext`的任何bean都可以实现`loadTimeWeaverAware`从而接收到load-time weaver实例的引用。这在与[Spring s JPA支持](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#orm-jpa)的结合中特别有用，其中加载时编织可能对JPA类转换是必要的。查看[`LocalContainerEntityManagerFactoryBean ` javadoc获得更多细节。有关AspectJ加载时编织的更多信息，请参见[在Spring框架中使用AspectJ进行加载时编织](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw)。



#### 1.15. Additional Capabilities of the ApplicationContext

`applicationContext`附加功能

As discussed in the [chapter introduction](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans), the `org.springframework.beans.factory` package provides basic functionality for managing and manipulating beans, including in a programmatic way. The `org.springframework.context` package adds the [`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/ApplicationContext.html) interface, which extends the `BeanFactory` interface, in addition to extending other interfaces to provide additional functionality in a more application framework-oriented style. Many people use the `ApplicationContext` in a completely declarative fashion, not even creating it programmatically, but instead relying on support classes such as `ContextLoader` to automatically instantiate an `ApplicationContext` as part of the normal startup process of a Java EE web application.

正如在[章节介绍](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#beans)中所讨论的，`org.springframework.beans.factory`的包提供了管理和操作bean的基本功能，包括以编程的方式。`org.springframework.context`的包添加了`applicationContext`接口,扩展了`beanFactory`接口,除了扩展其他的接口来提供额外的功能在多个应用程序面向框架样式。许多人以一种完全声明式的方式使用`ApplicationContext `甚至不是通过编程来创建它，而是依赖于支持类如`ContextLoader `自动实例化`ApplicationContext `为Java EE web应用程序的正常启动过程的一部分。

To enhance `BeanFactory` functionality in a more framework-oriented style, the context package also provides the following functionality:
为了以更面向框架的风格增强`beanFactory`工能，上下文包还提供了以下功能:

- Access to messages in i18n-style, through the `MessageSource` interface.
- Access to resources, such as URLs and files, through the `ResourceLoader` interface.
- Event publication, namely to beans that implement the `ApplicationListener` interface, through the use of the `ApplicationEventPublisher` interface.
- Loading of multiple (hierarchical) contexts, letting each be focused on one particular layer, such as the web layer of an application, through the `HierarchicalBeanFactory` interface.

通过`messageSource`口访问i18n风格的消息。
-透过`resourceLoader`面访问资源，例如网址及档案。
-事件发布，即通过使用`ApplicationEventPublisher `接口向实现`ApplicationListener `接口的bean发布。
-通过`hierarchicalBeanFactory`接口加载多个(分层的)上下文，让每个上下文都集中在一个特定的层上，比如应用程序的web层。

##### 1.15.1. Internationalization using `MessageSource`

使用`messageSource`际化
The `ApplicationContext` interface extends an interface called `MessageSource` and, therefore, provides internationalization (`18n` functionality. Spring also provides the `HierarchicalMessageSource` interface, which can resolve messages hierarchically. Together, these interfaces provide the foundation upon which Spring effects message resolution. The methods defined on these interfaces include:

`applicationContext`口扩展了一个名为`messageSource`接口，因此提供了国际化(`i18n`功能。Spring还提供了`hierarchicalMessageSource`接口，它可以分层地解析消息。这些接口一起提供了Spring实现消息解析的基础。在这些接口上定义的方法包括:

- `String getMessage(String code, Object[] args, String default, Locale loc)`: The basic method used to retrieve a message from the `MessageSource`. When no message is found for the specified locale, the default message is used. Any arguments passed in become replacement values, using the `MessageFormat` functionality provided by the standard library.

  `String getMessage(String code, Object[] args, String default, Locale loc) `用于从`MessageSource `搜索消息的基本方法。如果未找到指定语言环境的消息，则使用默认消息。通过使用标准库提供的`messageFormat`能，传入的任何参数都将成为替换值。

- `String getMessage(String code, Object[] args, Locale loc)`: Essentially the same as the previous method but with one difference: No default message can be specified. If the message cannot be found, a `NoSuchMessageException` is thrown.
  `String getMessage(String code, Object[] args, Locale loc) `本质上与前面的方法相同，但有一个区别:不能指定默认消息。如果找不到消息，则抛出`noSuchMessageException`

- `String getMessage(MessageSourceResolvable resolvable, Locale locale)`: All properties used in the preceding methods are also wrapped in a class named `MessageSourceResolvable`, which you can use with this method.
  `String getMessage(MessageSourceResolvable resolvable, Locale Locale) `在前面方法中使用的所有属性也包装在一个名为`MessageSourceResolvable `中，你可以在此方法中使用它。
  When an `ApplicationContext` is loaded, it automatically searches for a `MessageSource` bean defined in the context. The bean must have the name `messageSource`. If such a bean is found, all calls to the preceding methods are delegated to the message source. If no message source is found, the `ApplicationContext` attempts to find a parent containing a bean with the same name. If it does, it uses that bean as the `MessageSource`. If the `ApplicationContext` cannot find any source for messages, an empty `DelegatingMessageSource` is instantiated in order to be able to accept calls to the methods defined above.
  当加载一个`applicationContext`，它会自动搜索上下文中定义的`messageSource`bean。bean的名称必须是`messageSource`如果找到这样一个bean，对前面方法的所有调用都将委托给消息源。如果没有找到消息源，`applicationContext`尝试查找包含同名bean的父消息源。如果是这样，它就使用那个bean作为`messageSource`如果`applicationContext`不到任何消息源，则实例化一个空的`delegatingMessageSource`以便能够接受对上面定义的方法的调用。

Spring provides two `MessageSource` implementations, `ResourceBundleMessageSource` and `StaticMessageSource`. Both implement `HierarchicalMessageSource` in order to do nested messaging. The `StaticMessageSource` is rarely used but provides programmatic ways to add messages to the source. The following example shows `ResourceBundleMessageSource`:

Spring提供了两个`messageSource`现，`resourceBundleMessageSource`“`StaticMessageSource`为了实现嵌套消息传递，两者都实现了`hierarchicalMessageSource` `StaticMessageSource`很少使用，但提供了编程方式来添加消息到源。下面的例子显示了`resourceBundleMessageSource`

```xml
<beans>
    <bean id="messageSource"
            class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basenames">
            <list>
                <value>format</value>
                <value>exceptions</value>
                <value>windows</value>
            </list>
        </property>
    </bean>
</beans>
```

The example assumes that you have three resource bundles called `format`, `exceptions` and `windows` defined in your classpath. Any request to resolve a message is handled in the JDK-standard way of resolving messages through `ResourceBundle` objects. For the purposes of the example, assume the contents of two of the above resource bundle files are as follows:

该示例假设您在类路径中定义了三个资源包，分别称为“format”、“exceptions”和“windows”。任何解析消息的请求都以通过“ResourceBundle”对象解析消息的jdk标准方式处理。对于示例，假设上述两个资源包文件的内容如下:

```
    # in format.properties
    message=Alligators rock!
    # in exceptions.properties
    argument.required=The {0} argument is required.
```

The next example shows a program to run the `MessageSource` functionality. Remember that all `ApplicationContext` implementations are also `MessageSource` implementations and so can be cast to the `MessageSource` interface.

下一个示例展示了一个运行“MessageSource”功能的程序。记住，所有的' ApplicationContext '实现也是' MessageSource '实现，因此可以转换到' MessageSource '接口。。

```java
public static void main(String[] args) {
    MessageSource resources = new ClassPathXmlApplicationContext("beans.xml");
    String message = resources.getMessage("message", null, "Default", Locale.ENGLISH);
    System.out.println(message);
}
```

The resulting output from the above program is as follows:

由上述程序得到的结果如下:

```
Alligators rock!
```

To summarize, the `MessageSource` is defined in a file called `beans.xml`, which exists at the root of your classpath.. The three files that are passed in the list to the `basenames` property exist as files at the root of your classpath and are called `format.properties`, `exceptions.properties`, and `windows.properties`, respectively.

总而言之，“MessageSource”定义在一个名为“bean”的文件中。它存在于类路径的根目录中。在列表中传递给“basenames”属性的三个文件作为类路径的根文件存在，它们被称为“format”。属性”、“例外。属性”和“窗口。属性的分别。

The next example shows arguments passed to the message lookup. These arguments are converted into `String` objects and inserted into placeholders in the lookup message.

下一个示例显示了传递给消息查找的参数。这些参数被转换为“String`象，并插入到查找消息中的占位符中。

```xml
<beans>

    <!-- this MessageSource is being used in a web application -->
    <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="exceptions"/>
    </bean>

    <!-- lets inject the above MessageSource into this POJO -->
    <bean id="example" class="com.something.Example">
        <property name="messages" ref="messageSource"/>
    </bean>

</beans>
```

```java
public class Example {

    private MessageSource messages;

    public void setMessages(MessageSource messages) {
        this.messages = messages;
    }

    public void execute() {
        String message = this.messages.getMessage("argument.required",
            new Object [] {"userDao"}, "Required", Locale.ENGLISH);
        System.out.println(message);
    }
}
```

The resulting output from the invocation of the `execute()` method is as follows:

调用`execute() `法的结果输出如下:

```
The userDao argument is required.
```

With regard to internationalization (`i18n`, Spring’s various `MessageSource` implementations follow the same locale resolution and fallback rules as the standard JDK `ResourceBundle`. In short, and continuing with the example `messageSource` defined previously, if you want to resolve messages against the British (`en-GB`) locale, you would create files called `format_en_GB.properties`, `exceptions_en_GB.properties`, and `windows_en_GB.properties`, respectively.
关于国际化(`18n`，Spring的各种`messageSource`现遵循与标准JDK`resourceBundle`同的语言环境解析和回退规则。简而言之，继续前面定义的示例`messageSource`如果您希望根据英国(`en-GB `地区解析消息，您将创建名为`format_en_GB的文件。属性``exceptions_en_GB。属性``windows_en_GB。属性的分别。

Typically, locale resolution is managed by the surrounding environment of the application. In the following example, the locale against which (British) messages are resolved is specified manually:

通常，语言环境解析由应用程序的周围环境管理。在下面的示例中，手动指定解析(英国)消息所对应的语言环境:

```
# in exceptions_en_GB.properties
argument.required=Ebagum lad, the `{0}` argument is required, I say, required.
```

```java
public static void main(final String[] args) {
    MessageSource resources = new ClassPathXmlApplicationContext("beans.xml");
    String message = resources.getMessage("argument.required",
        new Object [] {"userDao"}, "Required", Locale.UK);
    System.out.println(message);
}
```

The resulting output from the running of the above program is as follows:

运行上述程序得到的结果如下:

```
Ebagum lad, the `userDao`argument is required, I say, required.
```

You can also use the `MessageSourceAware` interface to acquire a reference to any `MessageSource` that has been defined. Any bean that is defined in an `ApplicationContext` that implements the `MessageSourceAware` interface is injected with the application context’s `MessageSource` when the bean is created and configured.

您还可以使用`MessageSourceAware `口来获取对已定义的任何`MessageSource `引用。当创建和配置bean时，在实现了MessageSourceAware接口的`applicationContext`定义的任何bean都会被注入应用上下文的`messageSource`

As an alternative to `ResourceBundleMessageSource`, Spring provides a `ReloadableResourceBundleMessageSource` class. This variant supports the same bundle file format but is more flexible than the standard JDK based `ResourceBundleMessageSource` implementation. In particular, it allows for reading files from any Spring resource location (not only from the classpath) and supports hot reloading of bundle property files (while efficiently caching them in between). See the [`ReloadableResourceBundleMessageSource`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/ReloadableResourceBundleMessageSource.html) javadoc for details. 

作为`resourceBundleMessageSource`替代方案，Spring提供了一个`reloadableResourceBundleMessageSource`。这个变体支持相同的bundle文件格式，但是比标准的基于JDK的`resourceBundleMessageSource`现更加灵活。特别是，它允许从任何Spring资源位置读取文件(不仅仅是从类路径)，并支持bundle属性文件的热重新加载(同时有效地缓存它们)。参见[`ReloadableResourceBundleMessageSource ` javadoc获取详细信息。

##### 1.15.2. Standard and Custom Events

##### 1.15.3. Convenient Access to Low-level Resources
方便地访问底层资源
For optimal usage and understanding of application contexts, you should familiarize yourself with Spring's `Resource` abstraction, as described in [Resources](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources).

为了优化使用和理解应用程序上下文，您应该熟悉Spring的`resource`抽象，如在[Resources]中描述的那样(https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# Resources)。

An application context is a `ResourceLoader`, which can be used to load `Resource` objects. A `Resource` is essentially a more feature rich version of the JDK `java.net.URL` class. In fact, the implementations of the `Resource` wrap an instance of `java.net.URL`, where appropriate. A `Resource` can obtain low-level resources from almost any location in a transparent fashion, including from the classpath, a filesystem location, anywhere describable with a standard URL, and some other variations. If the resource location string is a simple path without any special prefixes, where those resources come from is specific and appropriate to the actual application context type.

应用程序上下文是一个`ResourceLoader`，它可以用来加载`Resource`对象。`Resource`本质上是JDK ` java.net.URL `类的一个功能更丰富的版本。事实上，`Resource`的实现在适当的地方包装了一个`java.net.URL`的实例。`resourcee`可以以透明的方式从几乎任何位置获取底层资源，包括从类路径、文件系统位置、可以用标准URL描述的任何位置，以及其他一些变体。如果资源位置字符串是没有任何特殊前缀的简单路径，那么这些资源的来源是特定的，并且适合于实际的应用程序上下文类型。

You can configure a bean deployed into the application context to implement the special callback interface, `ResourceLoaderAware`, to be automatically called back at initialization time with the application context itself passed in as the `ResourceLoader`. You can also expose properties of type `Resource`, to be used to access static resources. They are injected into it like any other properties. You can specify those `Resource` properties as simple `String` paths and rely on automatic conversion from those text strings to actual `Resource` objects when the bean is deployed.

您可以配置一个部署到应用程序上下文中的bean来实现特殊的回调接口`ResourceLoaderAware`，在初始化时自动回调，而应用程序上下文本身作为`ResourceLoader`传入。您还可以公开类型为`Resource`的属性，用于访问静态资源。它们像其他属性一样被注入。您可以将那些`资源`属性指定为简单的`字符串`路径，并在部署bean时依赖于从这些文本字符串到实际的`资源`对象的自动转换。
The location path or paths supplied to an `ApplicationContext` constructor are actually resource strings and, in simple form, are treated appropriately according to the specific context implementation. For example `ClassPathXmlApplicationContext` treats a simple location path as a classpath location. You can also use location paths (resource strings) with special prefixes to force loading of definitions from the classpath or a URL, regardless of the actual context type.

提供给` ApplicationContext `构造函数的位置路径或路径实际上是资源字符串，并且以简单的形式，根据特定的上下文实现进行适当的处理。例如，`ClassPathXmlApplicationContext`将简单的位置路径视为类路径位置。您还可以使用带有特殊前缀的位置路径(资源字符串)来强制从类路径或URL加载定义，而不管实际上下文类型是什么。

##### 1.15.4. Application Startup tracking
应用程序启动跟踪

The `ApplicationContext` manages the lifecycle of Spring applications and provides a rich programming model around components. As a result, complex applications can have equally complex component graphs and startup phases.

`ApplicationContext`管理Spring应用程序的生命周期，并围绕组件提供丰富的编程模型。因此，复杂的应用程序可能具有同样复杂的组件图和启动阶段。

Tracking the application startup steps with specific metrics can help understand where time is being spent during the startup phase, but it can also be used as a way to better understand the context lifecycle as a whole.

使用特定的度量来跟踪应用程序的启动步骤可以帮助理解启动阶段的时间花费在哪里，但是它也可以作为一种更好地理解整个上下文生命周期的方法。

The `AbstractApplicationContext` (and its subclasses) is instrumented with an `ApplicationStartup`, which collects `StartupStep` data about various startup phases:

`AbstractApplicationContext`(及其子类)通过一个`ApplicationStartup`检测，它收集关于不同启动阶段的`StartupStep`数据:

- application context lifecycle (base packages scanning, config classes management)
-应用程序上下文生命周期(基本包扫描，配置类管理)
- beans lifecycle (instantiation, smart initialization, post processing)
- bean生命周期(实例化、智能初始化、后处理)
- application events processing
-应用事件处理
Here is an example of instrumentation in the `AnnotationConfigApplicationContext`:
下面是在`AnnotationConfigApplicationContext`中插装的一个例子:


```java
// create a startup step and start recording
StartupStep scanPackages = this.getApplicationStartup().start("spring.context.base-packages.scan");
// add tagging information to the current step
scanPackages.tag("packages", () -> Arrays.toString(basePackages));
// perform the actual phase we`re instrumenting
this.scanner.scan(basePackages);
// end the current step
scanPackages.end();
```

The application context is already instrumented with multiple steps. Once recorded, these startup steps can be collected, displayed and analyzed with specific tools. For a complete list of existing startup steps, you can check out the [dedicated appendix section](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#application-startup-steps).

应用程序上下文已经包含了多个步骤。一旦记录下来，就可以使用特定的工具收集、显示和分析这些启动步骤。要获得现有启动步骤的完整列表，您可以查看[专用附录部分](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# applications -startup-steps)。

The default `ApplicationStartup` implementation is a no-op variant, for minimal overhead. This means no metrics will be collected during application startup by default. Spring Framework ships with an implementation for tracking startup steps with Java Flight Recorder: `FlightRecorderApplicationStartup`. To use this variant, you must configure an instance of it to the `ApplicationContext` as soon as it`s been created.

默认的`ApplicationStartup`实现是一个无操作变体，以实现最小的开销。这意味着默认情况下，在应用程序启动期间不会收集任何指标。Spring Framework附带了一个实现，用于跟踪Java飞行记录器的启动步骤:`FlightRecorderApplicationStartup`。要使用这个变体，您必须在它被创建后立即将它的实例配置为`ApplicationContext`。

Developers can also use the `ApplicationStartup` infrastructure if they’re providing their own `AbstractApplicationContext` subclass, or if they wish to collect more precise data.

如果开发人员正在提供他们自己的` AbstractApplicationContext `子类，或者他们想要收集更精确的数据，他们也可以使用` ApplicationStartup `基础设施。

`ApplicationStartup` is meant to be only used during application startup and for the core container; this is by no means a replacement for Java profilers or metrics libraries like [Micrometer](https://micrometer.io/).

 `ApplicationStartup`意味着只在应用程序启动期间使用，并用于核心容器;这绝不是Java分析器或度量库(如[Micrometer])的替代品(https://micrometer.io/)。

To start collecting custom `StartupStep`, components can either get the `ApplicationStartup` instance from the application context directly, make their component implement `ApplicationStartupAware`, or ask for the `ApplicationStartup` type on any injection point.

要开始收集自定义的`StartupStep`，组件可以直接从应用程序上下文获取`ApplicationStartup`实例，让他们的组件实现`ApplicationStartupAware`，或者在任何注入点请求`ApplicationStartup`类型。

Developers should not use the `"spring.*"` namespace when creating custom startup steps. This namespace is reserved for internal Spring usage and is subject to change. 

开发人员不应该使用``spring。*" `创建自定义启动步骤时的命名空间。这个名称空间是为内部Spring使用保留的，并且可以随时更改。

##### 1.15.5. Convenient ApplicationContext Instantiation for Web Applications
方便的ApplicationContext实例化Web应用程序

You can create `ApplicationContext` instances declaratively by using, for example, a `ContextLoader`. Of course, you can also create `ApplicationContext` instances programmatically by using one of the `ApplicationContext` implementations.

你可以通过使用一个ContextLoader来声明地创建` ApplicationContext `实例。当然，你也可以通过使用一个`ApplicationContext`实现以编程的方式创建`ApplicationContext`实例。

You can register an `ApplicationContext` by using the `ContextLoaderListener`, as the following example shows:

你可以通过使用ContextLoaderListener来注册一个`ApplicationContext`，如下面的例子所示:

```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/daoContext.xml /WEB-INF/applicationContext.xml</param-value>
</context-param>

<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

The listener inspects the `contextConfigLocation` parameter. If the parameter does not exist, the listener uses `/WEB-INF/applicationContext.xml` as a default. When the parameter does exist, the listener separates the `String` by using predefined delimiters (comma, semicolon, and whitespace) and uses the values as locations where application contexts are searched. Ant-style path patterns are supported as well. Examples are `/WEB-INF/*Context.xml` (for all files with names that end with `Context.xml` and that reside in the `WEB-INF` directory) and `/WEB-INF/**/*Context.xml` (for all such files in any subdirectory of `WEB-INF`).

侦听器检查` contextConfigLocation `参数。如果该参数不存在，侦听器将使用` /WEB-INF/applicationContext`。作为默认值。当参数确实存在时，侦听器通过使用预定义的分隔符(逗号、分号和空格)分隔`字符串`，并使用这些值作为搜索应用程序上下文的位置。也支持ant样式的路径模式。例子是` / web - inf / *context.xml`。(用于所有名称以`Context.xml`结尾的文件。和/WEB-INF/**/*上下文。(用于`WEB-INF`任何子目录下的所有此类文件)。

##### 1.15.6. Deploying a Spring `ApplicationContext` as a Java EE RAR File
部署一个Spring ` ApplicationContext `作为一个Java EE RAR文件

It is possible to deploy a Spring `ApplicationContext` as a RAR file, encapsulating the context and all of its required bean classes and library JARs in a Java EE RAR deployment unit. This is the equivalent of bootstrapping a stand-alone `ApplicationContext` (only hosted in Java EE environment) being able to access the Java EE servers facilities. RAR deployment is a more natural alternative to a scenario of deploying a headless WAR file — in effect, a WAR file without any HTTP entry points that is used only for bootstrapping a Spring `ApplicationContext` in a Java EE environment.

可以将Spring ` ApplicationContext `部署为RAR文件，将上下文及其所有必需的bean类和库jar封装在Java EE RAR部署单元中。这相当于引导一个独立的`ApplicationContext`(仅托管在Java EE环境中)来访问Java EE服务器设施。RAR部署是部署一个无头WAR文件的更自然的替代方案，一个没有任何HTTP入口点的WAR文件，仅用于在Java EE环境中引导一个Spring ` ApplicationContext `。

RAR deployment is ideal for application contexts that do not need HTTP entry points but rather consist only of message endpoints and scheduled jobs. Beans in such a context can use application server resources such as the JTA transaction manager and JNDI-bound JDBC `DataSource` instances and JMS `ConnectionFactory` instances and can also register with the platform‘s JMX server — all through Spring’s standard transaction management and JNDI and JMX support facilities. Application components can also interact with the application server‘s JCA `WorkManager` through Spring’s `TaskExecutor` abstraction.

RAR部署对于不需要HTTP入口点，而只包含消息端点和计划作业的应用程序上下文非常理想。在这样的上下文中，bean可以使用应用服务器资源，比如JTA事务管理器和JNDI绑定的JDBC`数据源`实例和JMS`ConnectionFactory`实例，还可以通过Spring标准事务管理和JNDI和JMX支持设施向平台的JMX服务器注册。应用组件还可以通过Spring的`任务执行器`抽象与应用服务器的JCA`WorkManager`交互。

See the javadoc of the [`SpringContextResourceAdapter`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/jca/context/SpringContextResourceAdapter.html) class for the configuration details involved in RAR deployment.

查看[` SpringContextResourceAdapter `](https://docs.spring.io/springing-framework/docs/5.3.0 / javadoc-api/org/springframework/jca/context/springcontextresourceadapter.html)类的javadoc，了解RAR部署中涉及的配置细节。

For a simple deployment of a Spring ApplicationContext as a Java EE RAR file:

对于一个简单的部署Spring ApplicationContext作为Java EE RAR文件:

1. Package all application classes into a RAR file (which is a standard JAR file with a different file extension). .Add all required library JARs into the root of the RAR archive. .Add a `META-INF/ra.xml` deployment descriptor (as shown in the [javadoc for `SpringContextResourceAdapter`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/jca/context/SpringContextResourceAdapter.html)) and the corresponding Spring XML bean definition file(s) (typically `META-INF/applicationContext.xml`).
1. 将所有的应用程序类打包到一个RAR文件中(这是一个标准的JAR文件，具有不同的文件扩展名).将所有需要的库JAR添加到RAR归档的根目录中。xml部署描述符(见[javadoc的SpringContextResourceAdapter)和相应的Spring xml bean定义文件(s)(通常的meta - inf /中)。
2. Drop the resulting RAR file into your application server`s deployment directory.
 将生成的RAR文件放到应用程序服务器的部署目录中。

Such RAR deployment units are usually self-contained. They do not expose components to the outside world, not even to other modules of the same application. Interaction with a RAR-based `ApplicationContext` usually occurs through JMS destinations that it shares with other modules. A RAR-based `ApplicationContext` may also, for example, schedule some jobs or react to new files in the file system (or the like). If it needs to allow synchronous access from the outside, it could (for example) export RMI endpoints, which may be used by other application modules on the same machine. 

这样的RAR部署单元通常是自包含的。它们不向外部世界公开组件，甚至不向同一应用程序的其他模块公开组件。与基于rar的`ApplicationContext`的交互通常是通过它与其他模块共享的JMS目的地进行的。例如，基于rar的`ApplicationContext`还可以调度一些作业或对文件系统中的新文件(或类似的东西)作出反应。如果它需要允许从外部进行同步访问，那么它可以(例如)导出RMI端点，这些端点可由同一台机器上的其他应用程序模块使用。



#### 1.16. The BeanFactory

The `BeanFactory` API provides the underlying basis for Spring‘s IoC functionality. Its specific contracts are mostly used in integration with other parts of Spring and related third-party frameworks, and its `DefaultListableBeanFactory` implementation is a key delegate within the higher-level `GenericApplicationContext` container.

`BeanFactory`API为Spring的IoC功能提供了底层基础。它的特定契约主要用于与Spring的其他部分和相关的第三方框架的集成，它的` DefaultListableBeanFactory `实现是高层` GenericApplicationContext `容器中的一个关键委托。

`BeanFactory` and related interfaces (such as `BeanFactoryAware`, `InitializingBean`, `DisposableBean`) are important integration points for other framework components. By not requiring any annotations or even reflection, they allow for very efficient interaction between the container and its components. Application-level beans may use the same callback interfaces but typically prefer declarative dependency injection instead, either through annotations or through programmatic configuration.

`BeanFactory`和相关接口(如`BeanFactoryAware`、`InitializingBean`、`DisposableBean`)是其他框架组件的重要集成点。由于不需要任何注释甚至反射，它们允许容器与其组件之间进行非常有效的交互。应用程序级bean可能使用相同的回调接口，但通常更倾向于通过注释或编程配置进行声明性依赖注入。

Note that the core `BeanFactory` API level and its `DefaultListableBeanFactory` implementation do not make assumptions about the configuration format or any component annotations to be used. All of these flavors come in through extensions (such as `XmlBeanDefinitionReader` and `AutowiredAnnotationBeanPostProcessor`) and operate on shared `BeanDefinition` objects as a core metadata representation. This is the essence of what makes Spring`s container so flexible and extensible.

注意，核心的`BeanFactory`API级别和它的`DefaultListableBeanFactory`实现不会对配置格式或要使用的任何组件注释做出假设。所有这些风格都是通过扩展(比如`XmlBeanDefinitionReader`和`AutowiredAnnotationBeanPostProcessor`)来实现的，并对共享的`BeanDefinition`对象进行操作，作为核心元数据表示。这是使Spring s容器如此灵活和可扩展的本质所在。

##### 1.16.1. `BeanFactory` or `ApplicationContext`?

This section explains the differences between the `BeanFactory` and `ApplicationContext` container levels and the implications on bootstrapping.

本节解释`BeanFactory`和`ApplicationContext`容器级别之间的差异，以及引导的含义。

You should use an `ApplicationContext` unless you have a good reason for not doing so, with `GenericApplicationContext` and its subclass `AnnotationConfigApplicationContext` as the common implementations for custom bootstrapping. These are the primary entry points to Spring`s core container for all common purposes: loading of configuration files, triggering a classpath scan, programmatically registering bean definitions and annotated classes, and (as of 5.0) registering functional bean definitions.

你应该使用一个` ApplicationContext `，除非你有很好的理由不这样做，用` GenericApplicationContext `和它的子类` AnnotationConfigApplicationContext `作为自定义引导的常见实现。这些是用于所有常见目的的Spring核心容器的主要入口点:加载配置文件、触发类路径扫描、以编程方式注册bean定义和带注释的类，以及(从5.0开始)注册功能性bean定义。

Because an `ApplicationContext` includes all the functionality of a `BeanFactory`, it is generally recommended over a plain `BeanFactory`, except for scenarios where full control over bean processing is needed. Within an `ApplicationContext` (such as the `GenericApplicationContext` implementation), several kinds of beans are detected by convention (that is, by bean name or by bean type — in particular, post-processors), while a plain `DefaultListableBeanFactory` is agnostic about any special beans.

因为一个`ApplicationContext`包含了一个`BeanFactory`的所有功能，所以一般建议它优于一个普通的`BeanFactory`，除了需要对bean处理进行完全控制的场景。在一个`ApplicationContext`(如`GenericApplicationContext`实现),几种检测到bean按照惯例(也就是说,由bean名称或bean类型—特别是,后处理器),而普通的`DefaultListableBeanFactory`不知道任何特殊bean。

For many extended container features, such as annotation processing and AOP proxying, the [`BeanPostProcessor` extension point](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-bpp) is essential. If you use only a plain `DefaultListableBeanFactory`, such post-processors do not get detected and activated by default. This situation could be confusing, because nothing is actually wrong with your bean configuration. Rather, in such a scenario, the container needs to be fully bootstrapped through additional setup.

对于许多扩展容器特性，如注释处理和AOP代理，[` BeanPostProcessor `扩展点](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# beans-factory-extend-bpp)是必不可少的。如果只使用普通的`DefaultListableBeanFactory`，默认情况下，这样的后处理器不会被检测到并激活。这种情况可能令人困惑，因为您的bean配置实际上没有任何错误。相反，在这样的场景中，容器需要通过附加的设置进行完全引导。

The following table lists features provided by the `BeanFactory` and `ApplicationContext` interfaces and implementations.
下表列出了` BeanFactory `和` ApplicationContext `接口和实现提供的特性。

| Feature                                                      | `BeanFactory` | `ApplicationContext` |
| :----------------------------------------------------------- | :------------ | :------------------- |
| Bean instantiation/wiring Bean实例化/布线                    | Yes           | Yes                  |
| Integrated lifecycle management集成的生命周期管理            | No            | Yes                  |
| Automatic `BeanPostProcessor` registration自动“BeanPostProcessor”登记 | No            | Yes                  |
| Automatic `BeanFactoryPostProcessor` registration自动“BeanFactoryPostProcessor”登记 | No            | Yes                  |
| Convenient `MessageSource` access (for internalization)方便的“MessageSource”访问(用于内部化) | No            | Yes                  |
| Built-in `ApplicationEvent` publication mechanism内置的' ApplicationEvent '发布机制 | No            | Yes                  |

To explicitly register a bean post-processor with a `DefaultListableBeanFactory`, you need to programmatically call `addBeanPostProcessor`, as the following example shows:

要显式注册一个带有` DefaultListableBeanFactory `的bean后处理器，你需要通过编程调用` addBeanPostProcessor `，如下面的例子所示:


```java
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
// populate the factory with bean definitions

// now register any needed BeanPostProcessor instances
factory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
factory.addBeanPostProcessor(new MyBeanPostProcessor());

// now start using the factory
```

To apply a `BeanFactoryPostProcessor` to a plain `DefaultListableBeanFactory`, you need to call its `postProcessBeanFactory` method, as the following example shows:

要将一个`BeanFactoryPostProcessor`应用到一个普通的`DefaultListableBeanFactory`上，你需要调用它的`postProcessBeanFactory`方法，如下面的例子所示:

```java
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinitions(new FileSystemResource("beans.xml"));

// bring in some property values from a Properties file
从属性文件中引入一些属性值
PropertySourcesPlaceholderConfigurer cfg = new PropertySourcesPlaceholderConfigurer();
cfg.setLocation(new FileSystemResource("jdbc.properties"));

// now actually do the replacement
现在实际做替换
cfg.postProcessBeanFactory(factory);
```

In both cases, the explicit registration steps are inconvenient, which is why the various `ApplicationContext` variants are preferred over a plain `DefaultListableBeanFactory` in Spring-backed applications, especially when relying on `BeanFactoryPostProcessor` and `BeanPostProcessor` instances for extended container functionality in a typical enterprise setup.

在这两种情况下,明确登记步骤是不方便的,这就是为什么各种ApplicationContext的变异要优于普通的`DefaultListableBeanFactory`回弹应用程序,尤其是当依靠`BeanFactoryPostProcessor`和`BeanPostProcessor`实例扩展容器功能在一个典型的企业设置。

An `AnnotationConfigApplicationContext` has all common annotation post-processors registered and may bring in additional processors underneath the covers through configuration annotations, such as `@EnableTransactionManagement`. At the abstraction level of Spring's annotation-based configuration model, the notion of bean post-processors becomes a mere internal container detail. 

一个`AnnotationConfigApplicationContext`注册了所有公共注释后处理器，并可能通过配置注释(如`@EnableTransactionManagement`)在后台引入额外的处理器。在Spring的基于注解的配置模型的抽象层上，bean后处理器的概念仅仅成为内部容器的细节。

### 2.Resources 资源

### 3.Validation, Data Binding, and Type Conversion验证、数据绑定和类型转换

### 4.Spring Expression Language (SpEL) Spring表达式语言（SpEL）

### 5.Aspect Oriented Programming with Spring Spring面向方面编程

Aspect-oriented Programming (AOP) complements Object-oriented Programming (OOP) by providing another way of thinking about program structure. The key unit of modularity in OOP is the class, whereas in AOP the unit of modularity is the aspect. Aspects enable the modularization of concerns (such as transaction management) that cut across multiple types and objects. (Such concerns are often termed `crosscutting` concerns in AOP literature.)

面向方面程序设计(AOP)通过提供另一种考虑程序结构的方法来补充面向对象程序设计(OOP)。OOP中模块性的关键单元是类，而AOP中模块性的单元是方面。方面支持跨多个类型和对象的关注点(如事务管理)的模块化。(在AOP文献中，这样的关注点通常被称为`横切`关注点。)

One of the key components of Spring is the AOP framework. While the Spring IoC container does not depend on AOP (meaning you do not need to use AOP if you don’t want to), AOP complements Spring IoC to provide a very capable middleware solution.

Spring的关键组件之一是AOP框架。虽然Spring IoC容器不依赖于AOP(这意味着如果您不想使用AOP，就不需要使用AOP)，但AOP对Spring IoC进行了补充，提供了一个非常强大的中间件解决方案。

Spring AOP with AspectJ pointcuts

带有AspectJ切入点的Spring AOP

Spring provides simple and powerful ways of writing custom aspects by using either a [schema-based approach](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-schema) or the [@AspectJ annotation style](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj). Both of these styles offer fully typed advice and use of the AspectJ pointcut language while still using Spring AOP for weaving.

Spring提供了简单而强大的方法编写自定义方面的使用(基于方法)或(@ aspectj注释风格)。这两种风格都提供了完全类型的通知和AspectJ切入点语言的使用，同时仍然使用Spring AOP进行编织。

This chapter discusses the schema- and @AspectJ-based AOP support. The lower-level AOP support is discussed in [the following chapter]

本章讨论基于模式和@ aspectj的AOP支持。底层AOP支持将在[下一章]中讨论

AOP is used in the Spring Framework to:

- Provide declarative enterprise services. The most important such service is [declarative transaction management](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction-declarative).
  -提供声明性企业服务。这类服务中最重要的是[声明式事务管理]
- Let users implement custom aspects, complementing their use of OOP with AOP.
  -让用户实现自定义方面，用AOP补充他们对OOP的使用。

If you are interested only in generic declarative services or other pre-packaged declarative middleware services such as pooling, you do not need to work directly with Spring AOP, and can skip most of this chapter.

如果您只对通用的声明性服务或其他预先打包的声明性中间件服务(如池)感兴趣，那么您不需要直接使用Spring AOP，可以跳过本章的大部分内容。

#### 5.1. AOP Concepts AOP概念

Let us begin by defining some central AOP concepts and terminology. These terms are not Spring-specific. Unfortunately, AOP terminology is not particularly intuitive. However, it would be even more confusing if Spring used its own terminology.

让我们从定义一些核心的AOP概念和术语开始。这些术语并不是spring特有的。不幸的是，AOP术语不是特别直观。但是，如果Spring使用自己的术语，就更令人困惑了。

- Aspect: A modularization of a concern that cuts across multiple classes. Transaction management is a good example of a crosscutting concern in enterprise Java applications. In Spring AOP, aspects are implemented by using regular classes (the [schema-based approach](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-schema)) or regular classes annotated with the `@Aspect` annotation (the [@AspectJ style](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj)).

  方面:跨多个类的关注点的模块化。事务管理是企业级Java应用程序中横切关注点的一个很好的例子。在Spring AOP方面实现通过使用常规类([基于方法]或普通类注释与`@Aspect`注释([@ aspectj风格])。

- Join point: A point during the execution of a program, such as the execution of a method or the handling of an exception. In Spring AOP, a join point always represents a method execution.

​       连接点:程序执行期间的一个点，例如方法执行或异常处理期间的一个点。在Spring AOP中，连接点总是表示方法执行。（spring中表现形式是方法执行）

- Advice: Action taken by an aspect at a particular join point. Different types of advice include `around`, `before` and `after` advice. (Advice types are discussed later.) Many AOP frameworks, including Spring, model an advice as an interceptor and maintain a chain of interceptors around the join point.

-建议:方面在特定连接点采取的行动。不同类型的建议（通知）包括`around`、`before`和`after`的建议。(稍后讨论通知类型。)许多AOP框架(包括Spring)将建议（通知）建模为拦截器，并围绕连接点维护拦截器链。

- Pointcut: A predicate that matches join points. Advice is associated with a pointcut expression and runs at any join point matched by the pointcut (for example, the execution of a method with a certain name). The concept of join points as matched by pointcut expressions is central to AOP, and Spring uses the AspectJ pointcut expression language by default.

-切入点:匹配连接点的谓词。通知与切入点表达式相关联，并在切入点匹配的任何连接点上运行(例如，具有特定名称的方法的执行)。由切入点表达式匹配的连接点的概念是AOP的核心，Spring在默认情况下使用AspectJ切入点表达式语言。

- Introduction: Declaring additional methods or fields on behalf of a type. Spring AOP lets you introduce new interfaces (and a corresponding implementation) to any advised object. For example, you could use an introduction to make a bean implement an `IsModified` interface, to simplify caching. (An introduction is known as an inter-type declaration in the AspectJ community.)

-介绍:声明代表类型的附加方法或字段。Spring AOP允许向任何被通知的对象引入新的接口(以及相应的实现)。例如，您可以使用一个介绍来让一个bean实现一个`IsModified`接口，以简化缓存。(在AspectJ社区中，介绍称为类型间声明。)

- Target object: An object being advised by one or more aspects. Also referred to as the `advised object`. Since Spring AOP is implemented by using runtime proxies, this object is always a proxied object.

-目标对象:被一个或多个方面通知的对象。也称为`advised object`。因为Spring AOP是通过使用运行时代理实现的，所以这个对象始终是一个代理对象。

- AOP proxy: An object created by the AOP framework in order to implement the aspect contracts (advise method executions and so on). In the Spring Framework, an AOP proxy is a JDK dynamic proxy or a CGLIB proxy.

-AOP代理:一个由AOP框架创建的对象，用来实现方面契约(建议方法的执行等等)。在Spring框架中，       AOP代理是JDK动态代理或CGLIB代理。

- Weaving: linking aspects with other application types or objects to create an advised object. This can be done at compile time (using the AspectJ compiler, for example), load time, or at runtime. Spring AOP, like other pure Java AOP frameworks, performs weaving at runtime.

  -编织:链接方面与其他应用类型或对象，以创建一个建议对象。这可以在编译时(例如使用AspectJ编译器)、加载时或运行时完成。与其他纯Java AOP框架一样，Spring AOP在运行时执行编织。

![f4janzdhyv](E:\oldF\learningDocument\spring\笔记\f4janzdhyv.png)

![761c23d694c06aabd520acee8f654d1d185](E:\oldF\learningDocument\spring\笔记\761c23d694c06aabd520acee8f654d1d185.png)

Spring `AOP` includes the following types of advice:

Spring AOP包括以下类型的通知:

- Before advice: Advice that runs before a join point but that does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).

  Before advice:在连接点之前运行但不能阻止执行流继续到连接点的通知(除非它抛出异常)。

- After returning advice: Advice to be run after a join point completes normally (for example, if a method returns without throwing an exception).

  返回通知后:通知将在连接点正常完成后运行(例如，如果方法返回时没有抛出异常)。

- After throwing advice: Advice to be run if a method exits by throwing an exception.

  抛出建议后:如果方法抛出异常而退出，通知将运行。

- After (finally) advice: Advice to be run regardless of the means by which a join point exits (normal or exceptional return).

  After (finally) advice:无论连接点以何种方式退出(正常或异常返回)，都要运行的advice。

- Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.

  环绕通知:围绕连接点(如方法调用)的通知。这是最有力（最强的）的通知。Around通知可以在方法调用之前和之后执行自定义行为。它还负责选择是继续到连接点，还是通过返回自己的返回值或抛出异常来简化通知的方法的执行。

Around advice is the most general kind of advice. Since Spring AOP, like AspectJ, provides a full range of advice types, we recommend that you use the least powerful advice type that can implement the required behavior. For example, if you need only to update a cache with the return value of a method, you are better off implementing an after returning advice than an around advice, although an around advice can accomplish the same thing. Using the most specific advice type provides a simpler programming model with less potential for errors. For example, you do not need to invoke the `proceed()` method on the `JoinPoint` used for around advice, and, hence, you cannot fail to invoke it.

Around advice是最常见的一种建议。由于Spring AOP像AspectJ一样提供了完整的通知类型，所以我们建议您使用功能最弱的通知类型来实现所需的行为。例如，如果您只需要用方法的返回值更新缓存，那么最好实现一个`after return advice`，而不是`around advice`，尽管`around advice`可以完成同样的事情。使用最特定的通知类型可以提供更简单的编程模型，出错的可能性更小。例如，您不需要在用于around通知的` JoinPoint `上调用` proceed() `方法，因此，您不会失败调用它。

All advice parameters are statically typed so that you work with advice parameters of the appropriate type (e.g. the type of the return value from a method execution) rather than `Object` arrays.

所有通知参数都是静态类型的，这样您就可以使用适当类型的通知参数(例如，从一个方法执行返回值的类型)，而不是`Object`数组。

The concept of join points matched by pointcuts is the key to AOP, which distinguishes it from older technologies offering only interception. Pointcuts enable advice to be targeted independently of the object-oriented hierarchy. For example, you can apply an around advice providing declarative transaction management to a set of methods that span multiple objects (such as all business operations in the service layer).

由`切入点`匹配的`连接点`的概念是AOP的关键，这将它与只提供拦截的旧技术区别开来。`切入点`使通知能够独立于面向对象的层次结构作为目标。例如，您可以应用around通知，为一组跨越多个对象(如服务层中的所有业务操作)的方法提供声明性事务管理。



#### 5.2. Spring AOP Capabilities and Goals

Spring AOP的功能和目标

Spring AOP is implemented in pure Java. There is no need for a special compilation process. Spring AOP does not need to control the class loader hierarchy and is thus suitable for use in a servlet container or application server.

Spring AOP是用纯Java实现的。不需要特殊的编译过程。Spring AOP不需要控制类加载器的层次结构，因此适合在servlet容器或应用服务器中使用。

Spring AOP currently supports only method execution join points (advising the execution of methods on Spring beans). Field interception is not implemented, although support for field interception could be added without breaking the core Spring AOP APIs. If you need to advise field access and update join points, consider a language such as AspectJ.

Spring AOP目前只支持方法执行连接点(通知方法在Spring bean上的执行)。虽然可以在不破坏Spring AOP核心api的情况下添加对字段拦截的支持，但没有实现字段拦截。如果需要通知字段访问和更新连接点，请考虑使用AspectJ之类的语言。

Spring AOP’s approach to AOP differs from that of most other AOP frameworks. The aim is not to provide the most complete AOP implementation (although Spring AOP is quite capable). Rather, the aim is to provide a close integration between AOP implementation and Spring IoC, to help solve common problems in enterprise applications.

Spring AOP实现AOP的方法不同于大多数其他AOP框架。其目的不是提供最完整的AOP实现(尽管Spring AOP非常有能力)。相反，其目的是提供AOP实现和Spring IoC之间的紧密集成，以帮助解决企业应用程序中的常见问题。

Thus, for example, the Spring Framework’s AOP functionality is normally used in conjunction with the Spring IoC container. Aspects are configured by using normal bean definition syntax (although this allows powerful `auto-proxying` capabilities). This is a crucial difference from other AOP implementations. You cannot do some things easily or efficiently with Spring AOP, such as advise very fine-grained objects (typically, domain objects). AspectJ is the best choice in such cases. However, our experience is that Spring AOP provides an excellent solution to most problems in enterprise Java applications that are amenable to AOP.

因此，例如，Spring框架的AOP功能通常与Spring IoC容器一起使用。方面是通过使用普通的bean定义语法配置的(尽管这允许强大的自动代理功能)。这是与其他AOP实现的一个关键区别。使用Spring AOP不能轻松或有效地完成一些事情，比如通知非常细粒度的对象(通常是域对象)。在这种情况下，AspectJ是最好的选择。然而，我们的经验是，Spring AOP为企业级Java应用程序中的大多数问题提供了一个优秀的解决方案，而这些问题都是符合AOP的。

Spring AOP never strives to compete with AspectJ to provide a comprehensive AOP solution. We believe that both proxy-based frameworks such as Spring AOP and full-blown frameworks such as AspectJ are valuable and that they are complementary, rather than in competition. Spring seamlessly integrates Spring AOP and IoC with AspectJ, to enable all uses of AOP within a consistent Spring-based application architecture. This integration does not affect the Spring AOP API or the AOP Alliance API. Spring AOP remains backward-compatible. See [the following chapter](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-api) for a discussion of the Spring AOP APIs.

Spring AOP从来没有努力与AspectJ竞争来提供一个全面的AOP解决方案。我们相信基于代理的框架(如Spring AOP)和成熟的框架(如AspectJ)都是有价值的，它们是互补的，而不是竞争的。Spring与AspectJ无缝地集成了Spring AOP和IoC，以便在一致的基于Spring的应用程序体系结构中启用AOP的所有使用。这种集成不会影响Spring AOP API或AOP Alliance API。Spring AOP保持向后兼容。有关Spring AOP api的讨论，请参阅[下面的章节](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html# AOP -api)。

>One of the central tenets of the Spring Framework is that of non-invasiveness. This is the idea that you should not be forced to introduce framework-specific classes and interfaces into your business or domain model. However, in some places, the Spring Framework does give you the option to introduce Spring Framework-specific dependencies into your codebase. The rationale in giving you such options is because, in certain scenarios, it might be just plain easier to read or code some specific piece of functionality in such a way. However, the Spring Framework (almost) always offers you the choice: You have the freedom to make an informed decision as to which option best suits your particular use case or scenario.One such choice that is relevant to this chapter is that of which AOP framework (and which AOP style) to choose. You have the choice of AspectJ, Spring AOP, or both. You also have the choice of either the @AspectJ annotation-style approach or the Spring XML configuration-style approach. The fact that this chapter chooses to introduce the @AspectJ-style approach first should not be taken as an indication that the Spring team favors the @AspectJ annotation-style approach over the Spring XML configuration-style.See [Choosing which AOP Declaration Style to Use](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-choosing) for a more complete discussion of the `whys and wherefores` of each style. 
>
>Spring框架的核心原则之一是非侵入性。这种思想认为，您不应该被迫将特定于框架的类和接口引入到您的业务或领域模型中。然而，在某些地方，Spring框架确实为您提供了将特定于Spring框架的依赖项引入代码库的选项。给您提供这些选项的理由是，在某些场景中，用这种方式可能更容易阅读或编写特定的功能片段。然而，Spring框架(几乎)总是为您提供选择:您可以自由地做出明智的决定，以确定哪个选项最适合您的特定用例或场景。与本章相关的一个选择是选择哪个AOP框架(以及哪种AOP风格)。您可以选择AspectJ、Spring AOP或两者都有。您还可以选择@AspectJ注释风格的方法或Spring XML配置风格的方法。本章选择首先介绍@AspectJ风格的方法并不意味着Spring团队更喜欢@AspectJ注释风格的方法，而不是Spring XML配置风格的方法。参见[选择使用哪种AOP声明风格](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# AOP - choose)获得关于每种风格的原因和位置的更完整的讨论。

#### 5.3. AOP Proxies

Spring AOP defaults to using standard JDK dynamic proxies for AOP proxies. This enables any interface (or set of interfaces) to be proxied.

Spring AOP默认为AOP代理使用标准JDK动态代理。这允许代理任何接口(或一组接口)

Spring AOP can also use CGLIB proxies. This is necessary to proxy classes rather than interfaces. By default, CGLIB is used if a business object does not implement an interface. As it is good practice to program to interfaces rather than classes, business classes normally implement one or more business interfaces. It is possible to [force the use of CGLIB](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-proxying), in those (hopefully rare) cases where you need to advise a method that is not declared on an interface or where you need to pass a proxied object to a method as a concrete type.

Spring AOP也可以使用CGLIB代理。这对于代理类而不是接口是必要的。默认情况下，如果业务对象没有实现接口，则使用CGLIB。由于使用接口而不是类编程是一种很好的实践，因此业务类通常实现一个或多个业务接口。是可能的(力量使用CGLIB) ,(希望罕见)的情况下,你需要建议一个方法,不声明一个接口或者你需要一个代理对象传递给一个方法作为一种具体类型。

It is important to grasp the fact that Spring AOP is proxy-based. See [Understanding AOP Proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-understanding-aop-proxies) for a thorough examination of exactly what this implementation detail actually means.

掌握Spring AOP是基于代理的这一事实很重要。请参见[理解AOP代理](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html# AOP -understand - AOP - proxy)，了解这个实现细节的确切含义。

#### 5.4. @AspectJ support

@ aspectj的支持

@AspectJ refers to a style of declaring aspects as regular Java classes annotated with annotations. The @AspectJ style was introduced by the [AspectJ project](https://www.eclipse.org/aspectj) as part of the AspectJ 5 release. Spring interprets the same annotations as AspectJ 5, using a library supplied by AspectJ for pointcut parsing and matching. The AOP runtime is still pure Spring AOP, though, and there is no dependency on the AspectJ compiler or weaver.

@AspectJ引用了一种将方面声明为带有注释的常规Java类的样式。@AspectJ风格是由[AspectJ项目](https://www.eclipse.org/aspectj)作为AspectJ 5版本的一部分引入的。Spring使用AspectJ提供的用于切入点解析和匹配的库来解释与AspectJ 5相同的注释。然而，AOP运行时仍然是纯粹的Spring AOP，并且不依赖于AspectJ编译器或weaver。

Using the AspectJ compiler and weaver enables use of the full AspectJ language and is discussed in [Using AspectJ with Spring Applications](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-using-aspectj). 
使用AspectJ编译器和weaver可以使用完整的AspectJ语言，在[使用AspectJ与Spring应用程序](https://docs.springing.io/spring-framework/docs/current/reference/html/core.html #aop-using-aspectj)中讨论了这一点。



#### 5.4.1. Enabling @AspectJ Support

支持@ aspectj的支持

To use @AspectJ aspects in a Spring configuration, you need to enable Spring support for configuring Spring AOP based on @AspectJ aspects and auto-proxying beans based on whether or not they are advised by those aspects. By auto-proxying, we mean that, if Spring determines that a bean is advised by one or more aspects, it automatically generates a proxy for that bean to intercept method invocations and ensures that advice is run as needed.

要在Spring配置中使用@AspectJ方面，您需要启用Spring支持来配置基于@AspectJ方面的Spring AOP，以及基于这些方面是否建议bean的自动代理bean。通过自动代理，我们的意思是，如果Spring确定一个bean被一个或多个方面通知，它会自动为该bean生成一个代理来拦截方法调用，并确保通知在需要时运行。

The @AspectJ support can be enabled with XML- or Java-style configuration. In either case, you also need to ensure that AspectJ’s `aspectjweaver.jar` library is on the classpath of your application (version 1.8 or later). This library is available in the `lib` directory of an AspectJ distribution or from the Maven Central repository.

可以通过XML或java风格的配置启用@AspectJ支持。在任何一种情况下，您还需要确保AspectJ的` aspectjweaver.jar`的库位于应用程序的类路径中(版本1.8或更高版本)。这个库可以在AspectJ发行版的`lib`目录中或从Maven中央存储库中获得。

##### Enabling @AspectJ Support with Java Configuration

使用Java配置启用@AspectJ支持

To enable @AspectJ support with Java `@Configuration`, add the `@EnableAspectJAutoProxy` annotation, as the following example shows:

要使用Java ` @Configuration `来启用@AspectJ支持，添加` @EnableAspectJAutoProxy `注释，如下面的示例所示:

```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

}
```

##### Enabling @AspectJ Support with XML Configuration

使用XML配置启用@AspectJ支持

To enable @AspectJ support with XML-based configuration, use the `aop:aspectj-autoproxy` element, as the following example shows:
要使用基于xml的配置来启用@AspectJ支持，使用`aop:aspectj-autoproxy`元素，如下面的例子所示:

```xml
<aop:aspectj-autoproxy/>
```

This assumes that you use schema support as described in [XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas). See [the AOP schema](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas-aop) for how to import the tags in the `aop` namespace.

这假设您使用[基于XML模式的配置]中描述的模式支持。查看[AOP模式](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#xsd-schemas-aop)了解如何在`AOP`名称空间中导入标记。

#### 5.4.2. Declaring an Aspect

声明一个方面

With @AspectJ support enabled, any bean defined in your application context with a class that is an @AspectJ aspect (has the `@Aspect` annotation) is automatically detected by Spring and used to configure Spring AOP. The next two examples show the minimal definition required for a not-very-useful aspect.

启用了@AspectJ支持后，在您的应用程序上下文中定义的带有@AspectJ方面(具有` @Aspect `注释)类的任何bean都将被Spring自动检测并用于配置Spring AOP。接下来的两个示例展示了一个不太有用的方面所需的最小定义。

The first of the two example shows a regular bean definition in the application context that points to a bean class that has the `@Aspect` annotation:

两个示例中的第一个显示了应用程序上下文中的一个常规bean定义，它指向一个具有` @Aspect `注释的bean类:

```xml
<bean id="myAspect" class="org.xyz.NotVeryUsefulAspect">
    <!-- configure properties of the aspect here -->
</bean>
```

The second of the two examples shows the `NotVeryUsefulAspect` class definition, which is annotated with the `org.aspectj.lang.annotation.Aspect` annotation;

两个示例中的第二个展示了`NotVeryUsefulAspect`类定义，它由`org.aspectj.lang.annotation`注释。方面的注释;

```java
package org.xyz;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class NotVeryUsefulAspect {

}
```

Aspects (classes annotated with `@Aspect`) can have methods and fields, the same as any other class. They can also contain pointcut, advice, and introduction (inter-type) declarations.

方面(用` @Aspect `注释的类)可以有方法和字段，和其他任何类一样。它们还可以包含切入点、通知和引入(类型间)声明。

Autodetecting aspects through component scanningYou can register aspect classes as regular beans in your Spring XML configuration or autodetect them through classpath scanning — the same as any other Spring-managed bean. However, note that the `@Aspect` annotation is not sufficient for autodetection in the classpath. For that purpose, you need to add a separate `@Component` annotation (or, alternatively, a custom stereotype annotation that qualifies, as per the rules of Spring’s component scanner). 

通过组件扫描自动检测方面您可以将方面类注册为Spring XML配置中的常规bean，或者通过类路径扫描自动检测它们—与任何其他Spring管理的bean相同。但是，请注意，`@Aspect`注释不足以在类路径中进行自动检测。为此，您需要添加一个单独的` @Component `注释(或者，或者，一个定制的构造型注释，根据Spring的组件扫描器的规则进行限定)。

Advising aspects with other aspects?In Spring AOP, aspects themselves cannot be the targets of advice from other aspects. The `@Aspect` annotation on a class marks it as an aspect and, hence, excludes it from auto-proxying. 

对其他方面提出建议?在Spring AOP中，aspects本身不能成为来自其他方面的通知的目标。类上的` @Aspect `注释将其标记为方面，因此将其从自动代理中排除。

#### 5.4.3. Declaring a Pointcut

声明一个切入点

Pointcuts determine join points of interest and thus enable us to control when advice runs. Spring AOP only supports method execution join points for Spring beans, so you can think of a pointcut as matching the execution of methods on Spring beans. A pointcut declaration has two parts:

 a signature comprising a name and any parameters and a pointcut expression that determines exactly which method executions we are interested in. 

In the @AspectJ annotation-style of AOP, a pointcut signature is provided by a regular method definition, and the pointcut expression is indicated by using the `@Pointcut` annotation (the method serving as the pointcut signature must have a `void` return type).

切入点确定感兴趣的连接点，从而使我们能够控制通知何时运行。Spring AOP只支持Spring bean的`方法执行连接点`，所以您可以将切入点看作是匹配Spring bean上方法的执行。切入点声明有两个部分:

由名称、任何参数和切入点表达式组成的签名，它准确地确定我们感兴趣的方法执行。

在@AspectJ注释风格的AOP中，一个切入点签名是由一个常规方法定义提供的，切入点表达式是通过使用`@Pointcut`注释来表示的(作为切入点签名的方法必须有一个`void`返回类型)。

An example may help make this distinction between a pointcut signature and a pointcut expression clear. The following example defines a pointcut named `anyOldTransfer` that matches the execution of any method named `transfer`:

一个示例可能有助于清晰地区分切入点签名(方法签名)和切入点表达式。下面的例子定义了一个名为`anyOldTransfer`的切入点，它匹配任何名为`transfer`的方法的执行:

```java
@Pointcut("execution(* transfer(..))") // the pointcut expression
private void anyOldTransfer() {} // the pointcut signature
```

The pointcut expression that forms the value of the `@Pointcut` annotation is a regular AspectJ 5 pointcut expression. For a full discussion of AspectJ’s pointcut language, see the [AspectJ Programming Guide](https://www.eclipse.org/aspectj/doc/released/progguide/index.html) (and, for extensions, the [AspectJ 5 Developer’s Notebook](https://www.eclipse.org/aspectj/doc/released/adk15notebook/index.html)) or one of the books on AspectJ (such as *Eclipse AspectJ*, by Colyer et. al., or *AspectJ in Action*, by Ramnivas Laddad).

形成` @Pointcut `注释值的切入点表达式是一个常规的AspectJ 5切入点表达式。充分讨论的AspectJ切入点语言,看到[AspectJ编程指南](https://www.eclipse.org/aspectj/doc/released/progguide/index.html)(,扩展(AspectJ 5开发者s笔记本)或一个AspectJ的书籍(如Eclipse AspectJ,by colyer等,或者在 AspectJ in action, Ramnivas Laddad)

##### Supported Pointcut Designators

支持切入点指示器

Spring AOP supports the following AspectJ pointcut designators (PCD) for use in pointcut expressions:

Spring AOP支持在切入点表达式中使用以下AspectJ切入点指示器(PCD):

- `execution`: For matching method execution join points. This is the primary pointcut designator to use when working with Spring AOP.
  -`执行`:用于匹配方法执行连接点。这是在使用Spring AOP时要使用的主要切入点指示器。
- `within`: Limits matching to join points within certain types (the execution of a method declared within a matching type when using Spring AOP).
  -`within`:限制匹配特定类型内的连接点(使用Spring AOP时，在匹配类型内声明的方法的执行)。
- `this`: Limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type.
  -`this`:限制对连接点的匹配(使用Spring AOP时方法的执行)，其中bean引用(Spring AOP代理)是给定类型的实例。
- `target`: Limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type.
  -`target`:将匹配限制为连接点(使用Spring AOP时方法的执行)，其中目标对象(被代理的应用程序对象)是给定类型的实例。
- `args`: Limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types.
- ` args `:限制对连接点(使用Spring AOP时方法的执行)的匹配，其中参数是给定类型的实例。
- `@target`: Limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type.
- ` @target `:将匹配限制为连接点(使用Spring AOP时方法的执行)，其中执行对象的类具有给定类型的注释。
- `@args`: Limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given types.
- ` @args `:限制对连接点(使用Spring AOP时方法的执行)的匹配，因为实际传递的参数的运行时类型具有给定类型的注释。
- `@within`: Limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP).
- ` @within `:限制匹配具有给定注释的类型中的连接点(在使用Spring AOP时，使用给定注释在类型中声明的方法的执行)。
- `@annotation`: Limits matching to join points where the subject of the join point (the method being run in Spring AOP) has the given annotation.
- ` @annotation `:将匹配限制为连接点的主题(在Spring AOP中运行的方法)具有给定注释的连接点。

Other pointcut types 其他类型的切入点

The full AspectJ pointcut language supports additional pointcut designators that are not supported in Spring: `call`, `get`, `set`, `preinitialization`, `staticinitialization`, `initialization`, `handler`, `adviceexecution`, `withincode`, `cflow`, `cflowbelow`, `if`, `@this`, and `@withincode`. Use of these pointcut designators in pointcut expressions interpreted by Spring AOP results in an `IllegalArgumentException` being thrown.

完整的AspectJ切入点语言支持Spring不支持的额外的切入点指示器:`call`、`get`、`set`、`preinitialization`、`staticinitialize`、`initialization`、`handler`、`adviceexecution`、`withincode`、`cflow`、`cflowbelow`、`if`、`@this`和`@withincode`。在由Spring AOP解释的切入点表达式中使用这些切入点指示器会导致抛出一个`IllegalArgumentException`。

The set of pointcut designators supported by Spring AOP may be extended in future releases to support more of the AspectJ pointcut designators.

Spring AOP支持的切入点指示器集可能在未来的版本中得到扩展，以支持更多的AspectJ切入点指示器。

Because Spring AOP limits matching to only method execution join points, the preceding discussion of the pointcut designators gives a narrower definition than you can find in the AspectJ programming guide. In addition, AspectJ itself has type-based semantics and, at an execution join point, both `this` and `target` refer to the same object: the object executing the method. Spring AOP is a proxy-based system and differentiates between the proxy object itself (which is bound to `this`) and the target object behind the proxy (which is bound to `target`).

因为Spring AOP将匹配限制为只匹配方法执行连接点，所以前面对切入点指示器的讨论给出了比AspectJ编程指南中更窄的定义。此外，AspectJ本身具有基于类型的语义，并且在一个执行连接点上，` this `和` target `都引用同一个对象:执行方法的对象。Spring AOP是一个基于代理的系统，区分了代理对象本身(绑定到`this`)和代理背后的目标对象(绑定到`target`)。

Due to the proxy-based nature of Spring’s AOP framework, calls within the target object are, by definition, not intercepted. For JDK proxies, only public interface method calls on the proxy can be intercepted. With CGLIB, public and protected method calls on the proxy are intercepted (and even package-visible methods, if necessary). However, common interactions through proxies should always be designed through public signatures.Note that pointcut definitions are generally matched against any intercepted method. If a pointcut is strictly meant to be public-only, even in a CGLIB proxy scenario with potential non-public interactions through proxies, it needs to be defined accordingly.If your interception needs include method calls or even constructors within the target class, consider the use of Spring-driven [native AspectJ weaving](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw) instead of Spring’s proxy-based AOP framework. This constitutes a different mode of AOP usage with different characteristics, so be sure to make yourself familiar with weaving before making a decision. 

由于Spring s AOP框架`基于代理`的本质，根据定义，目标对象中的调用不会被截获。对于JDK代理，只能拦截代理上的`公共接口方法`调用。使用CGLIB，可以拦截代理上的公共和受保护的方法调用(如果需要，甚至可以拦截包可见的方法)。但是，通过代理的常见交互应该始终通过公共签名(方法，签名也可以理解为方法)来设计。注意，切入点定义通常与任何截获的方法相匹配。如果严格意义上说切入点是只公开的，即使是在CGLIB代理场景中，通过代理进行潜在的非公开交互，也需要相应地定义它。如果您的拦截需要包括方法调用甚至是目标类中的构造函数，请考虑使用Spring驱动的[本机AspectJ编织](https://docs.springing.io/springing-framework/docs/current/reference/html/core.html # AOP - ajax -ltw)而不是基于Spring代理的AOP框架。这构成了具有不同特征的不同AOP使用模式，因此在做出决定之前一定要熟悉编织。

Spring AOP also supports an additional PCD named `bean`. This PCD lets you limit the matching of join points to a particular named Spring bean or to a set of named Spring beans (when using wildcards). The `bean` PCD has the following form:

Spring AOP还支持一个名为`bean`的附加PCD。这个PCD允许您将连接点的匹配限制为一个特定的命名Spring bean或一组命名Spring bean(当使用通配符时)。`bean`PCD的形式如下:

```java
bean(idOrNameOfBean)
```

The `idOrNameOfBean` token can be the name of any Spring bean. Limited wildcard support that uses the `*` character is provided, so, if you establish some naming conventions for your Spring beans, you can write a `bean` PCD expression to select them. As is the case with other pointcut designators, the `bean` PCD can be used with the `&&` (and), `||` (or), and `!` (negation) operators, too.

`idOrNameofbean`标记可以是任何Spring bean的名称。提供了使用` * `字符的有限通配符支持，因此，如果为Spring bean建立一些命名约定，可以编写` bean ` PCD表达式来选择它们。与其他切入点指示器一样，`bean`PCD可以与`&&`(和)、`||`(或)和`!`一起使用。`(否定)运算符也是。

The `bean` PCD is supported only in Spring AOP and not in native AspectJ weaving. It is a Spring-specific extension to the standard PCDs that AspectJ defines and is, therefore, not available for aspects declared in the `@Aspect` model.The `bean` PCD operates at the instance level (building on the Spring bean name concept) rather than at the type level only (to which weaving-based AOP is limited). Instance-based pointcut designators are a special capability of Spring’s proxy-based AOP framework and its close integration with the Spring bean factory, where it is natural and straightforward to identify specific beans by name. 

`bean`PCD只在Spring AOP中得到支持，而在本地AspectJ编织中不受支持。它是AspectJ定义的标准pcd的一个特定于spring的扩展，因此，对于在` @Aspect `模型中声明的方面不可用。`bean`PCD在实例级操作(构建在Spring bean名称概念上)，而不是仅在类型级操作(基于编织的AOP仅限于此)。基于实例的切入点指示器是Spring s基于代理的AOP框架及其与Spring bean工厂的紧密集成的一种特殊功能，在Spring bean工厂中，通过名称识别特定的bean是自然而直接的。

##### Combining Pointcut Expressions 组合切入点表达式

You can combine pointcut expressions by using `&&,` `||` and `!`. You can also refer to pointcut expressions by name. The following example shows three pointcut expressions:

您可以通过使用` && `、` ||` 和` ! `来组合切入点表达式。您还可以通过名称引用切入点表达式。下面的例子显示了三个切入点表达式:

```java
`执行`:用于匹配方法执行连接点。这是在使用Spring AOP时要使用的主要切入点指示器。
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {} 
`within`:限制匹配特定类型内的连接点(使用Spring AOP时，在匹配类型内声明的方法的执行)。
@Pointcut("within(com.xyz.myapp.trading..*)")
private void inTrading() {} 

@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {} 
```

`anyPublicOperation` matches if a method execution join point represents the execution of any public method. 
如果一个方法执行连接点表示任何公共方法的执行，则 ` anyPublicOperation `匹配。
`inTrading` matches if a method execution is in the trading module. 
如果一个方法执行在trading 模块中，则 ` inTrading `匹配。
 `tradingOperation` matches if a method execution represents any public method in the trading module. 
如果一个方法执行表示trading 模块中的任何公共方法，则 ` tradingOperation `匹配。

It is a best practice to build more complex pointcut expressions out of smaller named components, as shown earlier. When referring to pointcuts by name, normal Java visibility rules apply (you can see private pointcuts in the same type, protected pointcuts in the hierarchy, public pointcuts anywhere, and so on). Visibility does not affect pointcut matching.

从较小的已命名组件构建更复杂的切入点表达式是一种最佳实践，如前面所示。当通过名称引用切入点时，应用普通的Java可见性规则(您可以看到相同类型的私有切入点、层次结构中的受保护切入点、任何地方的公共切入点，等等)。可见性不影响切入点匹配。

##### Sharing Common Pointcut Definitions

共享公共切入点定义

When working with enterprise applications, developers often want to refer to modules of the application and particular sets of operations from within several aspects. We recommend defining a `CommonPointcuts` aspect that captures common pointcut expressions for this purpose. Such an aspect typically resembles the following example:

在使用企业应用程序时，开发人员通常希望从几个方面引用应用程序的模块和特定的操作集。我们建议定义一个`CommonPointcuts`方面，它可以捕获用于此目的的公共切入点表达式。这样的方面通常类似于下面的例子:

```java
package com.xyz.myapp;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.myapp.web package or any sub-package
     * under that.
	    *如果定义了方法，那么连接点在web层中
		*在com。xyz。myapp中的一个类型。网络包或任何子包
     	*下。
     */
    @Pointcut("within(com.xyz.myapp.web..*)")
    public void inWebLayer() {}

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.myapp.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.myapp.service..*)")
    public void inServiceLayer() {}

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.myapp.dao package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.myapp.dao..*)")
    public void inDataAccessLayer() {}

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     *
     * If you group service interfaces by functional area (for example,
     * in packages com.xyz.myapp.abc.service and com.xyz.myapp.def.service) then
     * the pointcut expression "execution(* com.xyz.myapp..service.*.*(..))"
     * could be used instead.
     *
     * Alternatively, you can write the expression using the `bean`
     * PCD, like so "bean(*Service)". (This assumes that you have
     * named your Spring service beans in a consistent fashion.)
     */
    	业务服务是在服务上定义的任何方法的执行
        接口。此定义假设接口位于
        “服务”包，实现类型在子包中。
        如果您按功能区域对服务接口进行分组(例如，
        在包com.xyz.myapp.abc。然后是service和com.xyz.myapp.def.service
        切入点表达式“execution(com.xyz.myapp..service..(..)”)
        可以用来代替。 或者，您可以使用“bean”编写表达式PCD，类似于“bean(服务)”。
    @Pointcut("execution(* com.xyz.myapp..service.*.*(..))")
    public void businessService() {}

    /**
     * A data access operation is the execution of any method defined on a
     * dao interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* com.xyz.myapp.dao.*.*(..))")
    public void dataAccessOperation() {}

}
```

You can refer to the pointcuts defined in such an aspect anywhere you need a pointcut expression. For example, to make the service layer transactional, you could write the following:

您可以在需要切入点表达式的任何地方引用这样一个方面中定义的切入点。例如，要使服务层具有事务性，您可以编写以下代码:

```xml
<aop:config>
    <aop:advisor
        pointcut="com.xyz.myapp.CommonPointcuts.businessService()"
        advice-ref="tx-advice"/>
</aop:config>

<tx:advice id="tx-advice">
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
```

The `<aop:config>` and `<aop:advisor>` elements are discussed in [Schema-based AOP Support](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-schema). The transaction elements are discussed in [Transaction Management](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction).

`<aop:config> `和` <aop:advisor> `元素在[基于模式的aop支持]中被讨论。事务元素在[事务管理](https://docs.spring.io/springing-framework/docs/current/reference/html/dataaccess.html #transaction)中讨论。

##### Examples

Spring AOP users are likely to use the `execution` pointcut designator the most often. The format of an execution expression follows:

Spring AOP用户最常使用的可能是`执行`切入点指示器。执行表达式的格式如下:

```
    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
```

All parts except the returning type pattern (`ret-type-pattern` in the preceding snippet), the name pattern, and the parameters pattern are optional. The returning type pattern determines what the return type of the method must be in order for a join point to be matched. `*` is most frequently used as the returning type pattern. It matches any return type. A fully-qualified type name matches only when the method returns the given type. The name pattern matches the method name. You can use the `*` wildcard as all or part of a name pattern. If you specify a declaring type pattern, include a trailing `.` to join it to the name pattern component. The parameters pattern is slightly more complex: `()` matches a method that takes no parameters, whereas `(..)` matches any number (zero or more) of parameters. The `(*)` pattern matches a method that takes one parameter of any type. `(*,String)` matches a method that takes two parameters. The first can be of any type, while the second must be a `String`. Consult the [Language Semantics](https://www.eclipse.org/aspectj/doc/released/progguide/semantics-pointcuts.html) section of the AspectJ Programming Guide for more information.

除了返回类型模式(前面代码片段中的‘ret-type-pattern’)、名称模式和参数模式之外，所有部分都是可选的。返回类型模式确定要匹配连接点的方法的返回类型必须是什么。` * `是最常用的返回类型模式，它匹配任何返回类型。只有当方法返回给定类型时，全限定类型名称才匹配。名称模式与方法名称匹配。您可以使用` * `通配符作为名称模式的全部或部分。如果指定声明类型模式，请包含一个尾部`。`将其连接到名称模式组件。parameters模式稍微复杂一些:`()`匹配不接受参数的方法，而`(..)`匹配任意数量(零或更多)的参数。`(*)`模式匹配接受任意类型参数的方法。` (*，String) `匹配一个有两个参数的方法。第一个可以是任何类型，而第二个必须是一个`字符串`。查阅AspectJ编程指南的[语言语义](https://www.eclipse.org/aspectj/doc/released/progguide/semantics-pointcuts.html)部分以获得更多信息。

The following examples show some common pointcut expressions:

下面的例子展示了一些常见的切入点表达式:

- The execution of any public method:
  执行任何公共方法:

  ```
      execution(public * *(..))
  ```

- The execution of any method with a name that begins with `set`:
  执行任何名称以`set`开头的方法:

  ```
      execution(* set*(..))
  ```

- The execution of any method defined by the `AccountService` interface:
  执行`AccountService`接口定义的任何方法:

  ```
      execution(* com.xyz.service.AccountService.*(..))
  ```

- The execution of any method defined in the `service` package:
  执行`service`包中定义的任何方法:

  ```
      execution(* com.xyz.service.*.*(..))
  ```

- The execution of any method defined in the service package or one of its sub-packages:
  执行服务包或其其中一个子包中定义的任何方法:

  ```
      execution(* com.xyz.service..*.*(..))
  ```

- Any join point (method execution only in Spring AOP) within the service package:
  服务包内的任何连接点(仅在Spring AOP中执行方法):

  ```
      within(com.xyz.service.*)
  ```

- Any join point (method execution only in Spring AOP) within the service package or one of its sub-packages:
  服务包或其子包内的任何连接点(仅在Spring AOP中执行方法):

  ```
      within(com.xyz.service..*)
  ```

- Any join point (method execution only in Spring AOP) where the proxy implements the `AccountService` interface:
  代理实现`AccountService`接口的任何连接点(仅在Spring AOP中执行方法):

  ```
      this(com.xyz.service.AccountService)
  ```

```
  
  `this` is more commonly used in a binding form. See the section on [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) for how to make the proxy object available in the advice body. 
   `this`更常用在绑定形式中。参见[声明通知](https://docs.springing.io/spring -framework/docs/current/reference/html/core.html#aop-advice)一节，了解如何使代理对象在通知主体中可用。
  
- Any join point (method execution only in Spring AOP) where the target object implements the `AccountService` interface:
目标对象实现`AccountService`接口的任何连接点(仅在Spring AOP中执行方法):
```

      target(com.xyz.service.AccountService)

  ```
   `target` is more commonly used in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section for how to make the target object available in the advice body. |
  
  `target`更常用在绑定形式中。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分，了解如何在通知主体中提供目标对象。
  
- Any join point (method execution only in Spring AOP) that takes a single parameter and where the argument passed at runtime is `Serializable`:
任何具有单个参数的连接点(仅在Spring AOP中执行方法)，并且在运行时传递的参数是`Serializable`:
  
  ```

      args(java.io.Serializable)

```
  
  `args` is more commonly used in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section for how to make the method arguments available in the advice body. 
  
  `args`更常用在绑定形式中。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分，了解如何在通知主体中提供方法参数。
  
  Note that the pointcut given in this example is different from `execution(* *(java.io.Serializable))`. The args version matches if the argument passed at runtime is `Serializable`, and the execution version matches if the method signature declares a single parameter of type `Serializable`.

注意，这个例子中给出的切入点不同于`execution(* *(java.io.Serializable)`。如果在运行时传递的参数是`Serializable`，那么args版本就匹配;如果方法签名声明了一个类型为`Serializable`的参数，那么execution版本就匹配。

- Any join point (method execution only in Spring AOP) where the target object has a `@Transactional` annotation:
  任何目标对象有`@Transactional`注释的连接点(仅在Spring AOP中执行方法):

  ```sh
  @target(org.springframework.transaction.annotation.Transactional)
```

  You can also use `@target` in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section for how to make the annotation object available in the advice body.

  还可以在绑定表单中使用`@target`。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分，了解如何在通知主体中提供注释对象。

- Any join point (method execution only in Spring AOP) where the declared type of the target object has an `@Transactional` annotation:
  任何目标对象的声明类型有一个` @Transactional `注释的连接点(仅在Spring AOP中执行方法):

  ```
      @within(org.springframework.transaction.annotation.Transactional)
  ```

  You can also use `@within` in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section for how to make the annotation object available in the advice body. 

  您还可以在绑定表单中使用`@within`。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分，了解如何在通知主体中提供注释对象。

- Any join point (method execution only in Spring AOP) where the executing method has an `@Transactional` annotation:
  任何连接点(仅在Spring AOP中执行方法)，其中执行方法有一个` @Transactional `注释:

  ```
      @annotation(org.springframework.transaction.annotation.Transactional)
  ```

   You can also use `@annotation` in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section for how to make the annotation object available in the advice body. 

   您还可以在绑定形式中使用`@annotation`。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分，了解如何在通知主体中提供注释对象。

- Any join point (method execution only in Spring AOP) which takes a single parameter, and where the runtime type of the argument passed has the `@Classified` annotation:
  任何接受单个参数的连接点(仅在Spring AOP中执行方法)，并且在运行时传递的参数类型有` @Classified `注释:

  ```
      @args(com.xyz.security.Classified)
  ```

  You can also use `@args` in a binding form. See the [Declaring Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice) section how to make the annotation object(s) available in the advice body. 
  您还可以在绑定形式中使用`@args`。参见[声明通知](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice)部分如何使注释对象在通知主体中可用。

- Any join point (method execution only in Spring AOP) on a Spring bean named `tradeService`:
  在名为`tradeService`的Spring bean上的任何连接点(仅在Spring AOP中执行方法):

  ```
      bean(tradeService)
  ```

- Any join point (method execution only in Spring AOP) on Spring beans having names that match the wildcard expression `*Service`:
  任何名称与通配符表达式` *Service `匹配的Spring bean上的连接点(仅在Spring AOP中执行方法):

  ```
      bean(*Service)
  ```

##### Writing Good Pointcuts

编写好的切入点

During compilation, AspectJ processes pointcuts in order to optimize matching performance. Examining code and determining if each join point matches (statically or dynamically) a given pointcut is a costly process. (A dynamic match means the match cannot be fully determined from static analysis and that a test is placed in the code to determine if there is an actual match when the code is running). On first encountering a pointcut declaration, AspectJ rewrites it into an optimal form for the matching process. What does this mean? Basically, pointcuts are rewritten in DNF (Disjunctive Normal Form) and the components of the pointcut are sorted such that those components that are cheaper to evaluate are checked first. This means you do not have to worry about understanding the performance of various pointcut designators and may supply them in any order in a pointcut declaration.

在编译期间，AspectJ处理切入点，以优化匹配性能。检查代码并确定每个连接点是否(静态或动态)匹配给定的切入点是一个代价高昂的过程。(动态匹配意味着不能从静态分析完全确定匹配，并且在代码中放置一个测试，以确定在代码运行时是否存在实际匹配)。在第一次遇到切入点声明时，AspectJ将其重写为用于匹配过程的最佳形式。这是什么意思?基本上，用DNF(析取范式)重写切入点，对切入点的组件进行排序，以便首先检查那些计算成本较低的组件。这意味着您不必担心理解各种切入点指示器的性能，并且可以在切入点声明中以任何顺序提供它们。

However, AspectJ can work only with what it is told. For optimal performance of matching, you should think about what they are trying to achieve and narrow the search space for matches as much as possible in the definition. The existing designators naturally fall into one of three groups: kinded, scoping, and contextual:

但是，AspectJ只能使用它被告知的内容。为了优化匹配性能，您应该考虑它们试图实现什么，并在定义中尽可能地缩小匹配的搜索空间。现有的指示器自然分为三组:kinded, scoping, and context:

- Kinded designators select a particular kind of join point: `execution`, `get`, `set`, `call`, and `handler`.
  种类指示符选择特定类型的连接点:`execution`、`get`、`set`、`call`和`handler`。
- Scoping designators select a group of join points of interest (probably of many kinds): `within` and `withincode`
  范围指示器选择一组兴趣(可能很多种)：`within` and `withincode`
- Contextual designators match (and optionally bind) based on context: `this`, `target`, and `@annotation`
  上下文指示符根据上下文匹配(和可选绑定):`this`、`target`和`@annotation`

A well written pointcut should include at least the first two types (kinded and scoping). You can include the contextual designators to match based on join point context or bind that context for use in the advice. Supplying only a kinded designator or only a contextual designator works but could affect weaving performance (time and memory used), due to extra processing and analysis. Scoping designators are very fast to match, and using them usage means AspectJ can very quickly dismiss groups of join points that should not be further processed. A good pointcut should always include one if possible.

一个编写良好的切入点应该至少包括前两种类型(kinded和scoping)。您可以包含上下文指示符来基于连接点上下文进行匹配，或者绑定上下文以便在通知中使用。仅提供kinded指示符或仅提供上下文指示符可以工作，但由于额外的处理和分析，可能会影响编织性能(时间和内存使用)。范围指示符匹配起来非常快，使用它们意味着AspectJ可以非常迅速地删除不应该进一步处理的连接点组。一个好的切入点应该尽可能包含一个切入点。

#### 5.4.4. Declaring Advice

Advice is associated with a pointcut expression and runs before, after, or around method executions matched by the pointcut. The pointcut expression may be either a simple reference to a named pointcut or a pointcut expression declared in place.

通知与切入点表达式相关联，并在切入点匹配的方法执行之前、之后或周围运行。切入点表达式可以是对命名切入点的简单引用，也可以是在适当位置声明的切入点表达式。

##### Before Advice

前置通知(增强)

You can declare before advice in an aspect by using the `@Before` annotation:

你可以通过使用` @Before `注释在一个方面声明before通知:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BeforeExample {
    @Before("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }

}
```

If we use an in-place pointcut expression, we could rewrite the preceding example as the following example:

如果我们使用一个就地切入点表达式，我们可以将前面的示例重写为下面的示例:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BeforeExample {

    @Before("execution(* com.xyz.myapp.dao.*.*(..))")
    public void doAccessCheck() {
        // ...
    }

}
```

##### After Returning Advice

After returning advice runs when a matched method execution returns normally. You can declare it by using the `@AfterReturning` annotation:

在返回通知后，当匹配的方法执行正常返回时运行。你可以使用`@ afterreturn`注释来声明它:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;

@Aspect
public class AfterReturningExample {

    @AfterReturning("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }

}
```

 You can have multiple advice declarations (and other members as well), all inside the same aspect. We show only a single advice declaration in these examples to focus the effect of each one. 
 您可以有多个通知声明(以及其他成员)，都在同一个方面内。在这些示例中，我们只展示了一个通知声明，以集中说明每个通知的效果。

Sometimes, you need access in the advice body to the actual value that was returned. You can use the form of `@AfterReturning` that binds the return value to get that access, as the following example shows:

有时，您需要在通知正文中访问返回的实际值。您可以使用` @ afterreturn `的形式绑定返回值来获得访问，如下面的示例所示:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;

@Aspect
public class AfterReturningExample {

    @AfterReturning(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        returning="retVal")
    public void doAccessCheck(Object retVal) {
        // ...
    }

}
```

The name used in the `returning` attribute must correspond to the name of a parameter in the advice method. When a method execution returns, the return value is passed to the advice method as the corresponding argument value. A `returning` clause also restricts matching to only those method executions that return a value of the specified type (in this case, `Object`, which matches any return value).

`return`属性中使用的名称必须与通知方法中参数的名称相对应。当一个方法执行返回时，返回值作为相应的实参值传递给通知方法。` return `子句还限制只匹配那些返回指定类型值的方法执行(在本例中是` Object `，它匹配任何返回值)。

Please note that it is not possible to return a totally different reference when using after returning advice.
请注意，它是不可能返回一个完全不同的参考时，使用后返回建议。

##### After Throwing Advice

在抛出通知后
After throwing advice runs when a matched method execution exits by throwing an exception. You can declare it by using the `@AfterThrowing` annotation, as the following example shows:

在抛出通知后，当匹配的方法执行通过抛出异常退出时运行。你可以使用` @ afterthrow `注释来声明它，如下面的例子所示:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;

@Aspect
public class AfterThrowingExample {

    @AfterThrowing("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doRecoveryActions() {
        // ...
    }

}
```

Often, you want the advice to run only when exceptions of a given type are thrown, and you also often need access to the thrown exception in the advice body. You can use the `throwing` attribute to both restrict matching (if desired — use `Throwable` as the exception type otherwise) and bind the thrown exception to an advice parameter. The following example shows how to do so:

通常，您希望仅在抛出给定类型的异常时才运行通知，而且您还经常需要访问通知主体中抛出的异常。您可以使用` throw `属性来限制匹配(如果需要—使用` Throwable `作为异常类型)，并将抛出的异常绑定到一个通知参数。下面的例子展示了如何做到这一点:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;

@Aspect
public class AfterThrowingExample {

    @AfterThrowing(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        throwing="ex")
    public void doRecoveryActions(DataAccessException ex) {
        // ...
    }

}
```

The name used in the `throwing` attribute must correspond to the name of a parameter in the advice method. When a method execution exits by throwing an exception, the exception is passed to the advice method as the corresponding argument value. A `throwing` clause also restricts matching to only those method executions that throw an exception of the specified type ( `DataAccessException`, in this case).

在`抛出`属性中使用的名称必须与通知方法中的参数名称相对应。当方法执行通过抛出异常而退出时，异常将作为相应的参数值传递给通知方法。`throw`子句还限制只匹配那些抛出指定类型异常(在本例中为`DataAccessException`)的方法执行。

##### After (Finally) Advice

(最后)后的通知

After (finally) advice runs when a matched method execution exits. It is declared by using the `@After` annotation. After advice must be prepared to handle both normal and exception return conditions. It is typically used for releasing resources and similar purposes. The following example shows how to use after finally advice:

当匹配的方法执行退出时，通知运行。它是通过使用` @After `注释声明的。After advice必须准备好处理正常和异常返回条件。它通常用于释放资源和类似的目的。下面的例子展示了如何使用after finally advice:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.After;

@Aspect
public class AfterFinallyExample {

    @After("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doReleaseLock() {
        // ...
    }

}
```

##### Around Advice

环绕通知

The last kind of advice is around advice. Around advice runs `around` a matched method`s execution. It has the opportunity to do work both before and after the method runs and to determine when, how, and even if the method actually gets to run at all. Around advice is often used if you need to share state before and after a method execution in a thread-safe manner (starting and stopping a timer, for example). Always use the least powerful form of advice that meets your requirements (that is, do not use around advice if before advice would do).

最后一种建议是关于建议的。Around advice`绕过`匹配的方法执行。它有机会在方法运行之前和之后执行工作，并确定方法何时、如何运行，甚至是否真正运行。如果您需要以线程安全的方式(例如启动和停止计时器)在方法执行之前和之后共享状态，则经常使用Around通知。总是使用最弱的形式的建议来满足你的要求(也就是说，不要使用around advice，如果before advice可以做的话)。

Around advice is declared by using the `@Around` annotation. The first parameter of the advice method must be of type `ProceedingJoinPoint`. Within the body of the advice, calling `proceed()` on the `ProceedingJoinPoint` causes the underlying method to run. The `proceed` method can also pass in an `Object[]`. The values in the array are used as the arguments to the method execution when it proceeds.

Around通知是使用` @Around `注释声明的。通知方法的第一个参数必须是` ProceedingJoinPoint `类型。在通知的主体中，对` ProceedingJoinPoint `调用` proceed() `导致底层方法运行。`proceed`方法还可以传入一个`Object[]`。数组中的值用作方法执行时的参数。

The behavior of `proceed` when called with an `Object[]` is a little different than the behavior of `proceed` for around advice compiled by the AspectJ compiler. For around advice written using the traditional AspectJ language, the number of arguments passed to `proceed` must match the number of arguments passed to the around advice (not the number of arguments taken by the underlying join point), and the value passed to proceed in a given argument position supplants the original value at the join point for the entity the value was bound to (do not worry if this does not make sense right now). The approach taken by Spring is simpler and a better match to its proxy-based, execution-only semantics. You only need to be aware of this difference if you compile @AspectJ aspects written for Spring and use `proceed` with arguments with the AspectJ compiler and weaver. There is a way to write such aspects that is 100% compatible across both Spring AOP and AspectJ, and this is discussed in the [following section on advice parameters](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params). 

当`Object[]`被调用时，`proceed`的行为与AspectJ编译器编译的around通知的`proceed`的行为略有不同。左右建议使用传统的AspectJ语言编写,参数传递给`进行`的数量必须匹配的参数传递到周围的建议(不是参数由底层连接点的数量),并继续在一个给定的参数传递的价值立场取代原来的价值实体价值的连接点是绑定到(不要担心如果现在没有意义)。

The following example shows how to use around advice:

下面的例子展示了如何使用around advice:

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
public class AroundExample {

    @Around("com.xyz.myapp.CommonPointcuts.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }

}
```

The value returned by the around advice is the return value seen by the caller of the method. For example, a simple caching aspect could return a value from a cache if it has one and invoke `proceed()` if it does not. Note that `proceed` may be invoked once, many times, or not at all within the body of the around advice. All of these are legal.

around通知返回的值是方法的调用者看到的返回值。例如，一个简单的缓存方面可以从缓存中返回一个值(如果有)，如果没有，则调用` proceed() `。注意，`proceed`可能被调用一次、多次，或者在around通知的主体中根本不被调用。所有这些都是合法的。

##### Advice Parameters

通知参数

Spring offers fully typed advice, meaning that you declare the parameters you need in the advice signature (as we saw earlier for the returning and throwing examples) rather than work with `Object[]` arrays all the time. We see how to make argument and other contextual values available to the advice body later in this section. First, we take a look at how to write generic advice that can find out about the method the advice is currently advising.

Spring提供了完全类型的通知，这意味着您可以在通知签名中声明所需的参数(正如我们在前面的返回和抛出示例中看到的那样)，而不是一直使用`Object[]`数组。在本节的后面，我们将看到如何使参数和其他上下文值对advice主体可用。首先，我们看一下如何编写通用的建议，以找出该建议目前正在建议的方法。

###### Access to the Current `JoinPoint`

访问当前的`JoinPoint`

Any advice method may declare, as its first parameter, a parameter of type `org.aspectj.lang.JoinPoint` (note that around advice is required to declare a first parameter of type `ProceedingJoinPoint`, which is a subclass of `JoinPoint`. The `JoinPoint` interface provides a number of useful methods:

任何通知方法都可以声明一个类型为` org.aspectj.lang的参数作为它的第一个参数。(注意around通知需要声明类型为` ProceedingJoinPoint `的第一个参数，它是` JoinPoint `的子类。`JoinPoint`接口提供了许多有用的方法:

- `getArgs()`: Returns the method arguments.
  返回方法参数。
- `getThis()`: Returns the proxy object.
  返回代理对象。
- `getTarget()`: Returns the target object.
  返回目标对象。
- `getSignature()`: Returns a description of the method that is being advised.
  返回被通知的方法的描述。
- `toString()`: Prints a useful description of the method being advised.
  打印被建议的方法的有用描述。

See the [javadoc](https://www.eclipse.org/aspectj/doc/released/runtime-api/org/aspectj/lang/JoinPoint.html) for more detail.

###### Passing Parameters to Advice

向通知传递参数

We have already seen how to bind the returned value or exception value (using after returning and after throwing advice). To make argument values available to the advice body, you can use the binding form of `args`. If you use a parameter name in place of a type name in an args expression, the value of the corresponding argument is passed as the parameter value when the advice is invoked. An example should make this clearer. Suppose you want to advise the execution of DAO operations that take an `Account` object as the first parameter, and you need access to the account in the advice body. You could write the following:

我们已经了解了如何绑定返回值或异常值(在返回和抛出通知之后使用)。要使通知主体可用参数值，可以使用` args `的绑定形式。如果在args表达式中使用参数名代替类型名，则在调用通知时将相应参数的值作为参数值传递。一个例子应该会使这一点更清楚。假设您希望通知以`Account`对象作为第一个参数的DAO操作的执行，并且需要访问通知主体中的Account。你可以这样写

```java
@Before("com.xyz.myapp.CommonPointcuts.dataAccessOperation() && args(account,..)")
public void validateAccount(Account account) {
    // ...
}
```

The `args(account,..)` part of the pointcut expression serves two purposes. First, it restricts matching to only those method executions where the method takes at least one parameter, and the argument passed to that parameter is an instance of `Account`. Second, it makes the actual `Account` object available to the advice through the `account` parameter.

切入点表达式的`args(account，..)`部分有两个目的。首先，它限制只匹配那些方法执行时，该方法至少有一个参数，并且传递给该参数的参数是` Account `的实例。其次，它通过` Account `参数使通知可以使用实际的` Account `对象。

Another way of writing this is to declare a pointcut that `provides` the `Account` object value when it matches a join point, and then refer to the named pointcut from the advice. This would look as follows:

写这个的另一种方法是声明一个切入点，在它匹配连接点时`提供``Account`对象值，然后从通知中引用命名的切入点。这看起来如下:

```java
@Pointcut("com.xyz.myapp.CommonPointcuts.dataAccessOperation() && args(account,..)")
private void accountDataAccessOperation(Account account) {}

@Before("accountDataAccessOperation(account)")
public void validateAccount(Account account) {
    // ...
}
```

See the AspectJ programming guide for more details.

The proxy object ( `this`), target object ( `target`), and annotations ( `@within`, `@target`, `@annotation`, and `@args`) can all be bound in a similar fashion. The next two examples show how to match the execution of methods annotated with an `@Auditable` annotation and extract the audit code:

代理对象(` this `)、目标对象(` target `)和注释(` @within `、` @target `、` @annotation `和` @args `)都可以以类似的方式绑定。下面两个例子展示了如何匹配带有`@Auditable`注释的方法的执行，并提取审计代码:

The first of the two examples shows the definition of the `@Auditable` annotation:

两个示例中的第一个展示了` @Auditable `注释的定义:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {
    AuditCode value();
}
```

The second of the two examples shows the advice that matches the execution of `@Auditable` methods:

第二个示例显示了匹配` @Auditable `方法执行的通知:

```java
@Before("com.xyz.lib.Pointcuts.anyPublicMethod() && @annotation(auditable)")
public void audit(Auditable auditable) {
    AuditCode code = auditable.value();
    // ...
}
```

###### Advice Parameters and Generics

通知参数和泛型

Spring AOP can handle generics used in class declarations and method parameters. Suppose you have a generic type like the following:

Spring AOP可以处理类声明和方法参数中使用的泛型。假设您有一个像下面这样的泛型类型:

```java
public interface Sample<T> {
    void sampleGenericMethod(T param);
    void sampleGenericCollectionMethod(Collection<T> param);
}
```

You can restrict interception of method types to certain parameter types by typing the advice parameter to the parameter type for which you want to intercept the method:

你可以通过将advice参数输入到你想要拦截的参数类型来限制对方法类型的拦截:

```java
@Before("execution(* ..Sample+.sampleGenericMethod(*)) && args(param)")
public void beforeSampleMethod(MyType param) {
    // Advice implementation
}
```

This approach does not work for generic collections. So you cannot define a pointcut as follows:

这种方法不适用于泛型集合。因此不能像下面这样定义切入点:

```java
@Before("execution(* ..Sample+.sampleGenericCollectionMethod(*)) && args(param)")
public void beforeSampleMethod(Collection<MyType> param) {
    // Advice implementation
}
```

To make this work, we would have to inspect every element of the collection, which is not reasonable, as we also cannot decide how to treat `null` values in general. To achieve something similar to this, you have to type the parameter to `Collection<?>` and manually check the type of the elements.

要做到这一点，我们必须检查集合的每个元素，这是不合理的，因为我们也不能决定如何处理`null`值一般。要实现类似的功能，您必须键入参数` Collection<?然后手动检查元素的类型。

###### Determining Argument Names

确定参数的名字
The parameter binding in advice invocations relies on matching names used in pointcut expressions to declared parameter names in advice and pointcut method signatures. Parameter names are not available through Java reflection, so Spring AOP uses the following strategy to determine parameter names:

通知调用中的参数绑定依赖于将切入点表达式中使用的名称与通知和切入点方法签名中声明的参数名称相匹配。参数名在Java反射中是不可用的，所以Spring AOP使用以下策略来确定参数名:

- If the parameter names have been explicitly specified by the user, the specified parameter names are used. Both the advice and the pointcut annotations have an optional `argNames` attribute that you can use to specify the argument names of the annotated method. These argument names are available at runtime. The following example shows how to use the `argNames` attribute:

如果用户显式指定了参数名，则使用指定的参数名。通知和切入点注释都有一个可选的` argNames `属性，您可以使用它来指定带注释的方法的参数名称。这些参数名在运行时可用。下面的例子展示了如何使用` argNames `属性:

```java
@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
        argNames="bean,auditable")
public void audit(Object bean, Auditable auditable) {
    AuditCode code = auditable.value();
    // ... use code and bean
}
```

If the first parameter is of the `JoinPoint`, `ProceedingJoinPoint`, or `JoinPoint.StaticPart` type, you can leave out the name of the parameter from the value of the `argNames` attribute. For example, if you modify the preceding advice to receive the join point object, the `argNames` attribute need not include it:

如果第一个参数是` JoinPoint `的，则是` ProceedingJoinPoint `或` JoinPoint `。类型，则可以从` argNames `属性的值中省略参数的名称。例如，如果您修改了前面的通知来接收连接点对象，那么` argNames `属性就不需要包含它:

```java
@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
        argNames="bean,auditable")
public void audit(JoinPoint jp, Object bean, Auditable auditable) {
    AuditCode code = auditable.value();
    // ... use code, bean, and jp
}
```

The special treatment given to the first parameter of the `JoinPoint`, `ProceedingJoinPoint`, and `JoinPoint.StaticPart` types is particularly convenient for advice instances that do not collect any other join point context. In such situations, you may omit the `argNames` attribute. For example, the following advice need not declare the `argNames` attribute:

对`JoinPoint`的第一个参数、`ProceedingJoinPoint`和`JoinPoint`的特殊处理。对于不收集任何其他连接点上下文的通知实例，StaticPart的类型特别方便。在这种情况下，可以省略` argNames `属性。例如，下面的建议不需要声明` argNames `属性:

```java
@Before("com.xyz.lib.Pointcuts.anyPublicMethod()")
public void audit(JoinPoint jp) {
    // ... use jp
}
```

- Using the ``argNames`` attribute is a little clumsy, so if the ``argNames`` attribute has not been specified, Spring AOP looks at the debug information for the class and tries to determine the parameter names from the local variable table. This information is present as long as the classes have been compiled with debug information ( ``-g:vars`` at a minimum). The consequences of compiling with this flag on are: (1) your code is slightly easier to understand (reverse engineer), (2) the class file sizes are very slightly bigger (typically inconsequential), (3) the optimization to remove unused local variables is not applied by your compiler. In other words, you should encounter no difficulties by building with this flag on.
  使用" argNames` `属性有点笨拙，因此如果没有指定" argNames` `属性，Spring AOP会查看类的调试信息，并尝试从本地变量表中确定参数名。只要类是用调试信息编译的，这个信息就会出现(" -g:vars` `至少是这样)。使用这个标志进行编译的结果是:(1)您的代码稍微容易理解(反向工程)，(2)类文件大小稍微大了一点(通常不重要)，(3)删除未使用的局部变量的优化没有被编译器应用。换句话说，您在使用这个旗帜进行构建时应该不会遇到任何困难。

   If an @AspectJ aspect has been compiled by the AspectJ compiler (ajc) even without the debug information, you need not add the `argNames` attribute, as the compiler retain the needed information. 
   如果AspectJ编译器(ajc)已经编译了一个@AspectJ方面，即使没有调试信息，也不需要添加` argNames `属性，因为编译器会保留所需的信息。

- If the code has been compiled without the necessary debug information, Spring AOP tries to deduce the pairing of binding variables to parameters (for example, if only one variable is bound in the pointcut expression, and the advice method takes only one parameter, the pairing is obvious). If the binding of variables is ambiguous given the available information, an `AmbiguousBindingException` is thrown.

如果代码是在没有必要的调试信息的情况下编译的，Spring AOP会尝试推断出绑定变量到参数的配对(例如，如果在切入点表达式中只有一个变量被绑定，而advice方法只接受一个参数，这种配对是显而易见的)。如果给定可用信息，变量的绑定是不明确的，则抛出一个`AmbiguousBindingException`。

- If all of the above strategies fail, an `IllegalArgumentException` is thrown.
  如果上述所有策略都失败了，就会抛出一个`IllegalArgumentException`。

###### Proceeding with Arguments

继续争论

We remarked earlier that we would describe how to write a `proceed` call with arguments that works consistently across Spring AOP and AspectJ. The solution is to ensure that the advice signature binds each of the method parameters in order. The following example shows how to do so:

我们前面提到过，我们将描述如何使用在Spring AOP和AspectJ中一致工作的参数来编写`proceed`调用。解决方案是确保通知签名按顺序绑定每个方法参数。下面的例子展示了如何做到这一点:

```java
@Around("execution(List<Account> find*(..)) && " +
        "com.xyz.myapp.CommonPointcuts.inDataAccessLayer() && " +
        "args(accountHolderNamePattern)")
public Object preProcessQueryPattern(ProceedingJoinPoint pjp,
        String accountHolderNamePattern) throws Throwable {
    String newPattern = preProcess(accountHolderNamePattern);
    return pjp.proceed(new Object[] {newPattern});
}
```

In many cases, you do this binding anyway (as in the preceding example).

在许多情况下，无论如何都要进行绑定(如前面的示例所示)。

##### Advice Ordering

建议订购
What happens when multiple pieces of advice all want to run at the same join point? Spring AOP follows the same precedence rules as AspectJ to determine the order of advice execution. The highest precedence advice runs first "on the way in" (so, given two pieces of before advice, the one with highest precedence runs first). "On the way out" from a join point, the highest precedence advice runs last (so, given two pieces of after advice, the one with the highest precedence will run second).

如果多个通知都希望在同一个连接点上运行，会发生什么情况?Spring AOP遵循与AspectJ相同的优先规则来决定通知的执行顺序。优先级最高的建议在`进入时`首先运行(因此，给定两个before建议，优先级最高的建议首先运行)。从连接点`在退出的路上`，优先级最高的通知最后运行(因此，给定两个after通知，优先级最高的通知将排在第二)。

When two pieces of advice defined in different aspects both need to run at the same join point, unless you specify otherwise, the order of execution is undefined. You can control the order of execution by specifying precedence. This is done in the normal Spring way by either implementing the `org.springframework.core.Ordered` interface in the aspect class or annotating it with the `@Order` annotation. Given two aspects, the aspect returning the lower value from `Ordered.getOrder()` (or the annotation value) has the higher precedence.

在不同方面定义的两个通知都需要在同一个连接点上运行时，除非另行指定，否则执行顺序是未定义的。您可以通过指定优先级来控制执行顺序。这是通过实现` org.springframework.core.Ordered`以正常的Spring方式完成的接口，或者用` `@Order `注释它。对于两个方面，从` order . getorder() `(或注释值)返回较低值的方面具有较高的优先级。

As of Spring Framework 5.2.7, advice methods defined in the same `@Aspect` class that need to run at the same join point are assigned precedence based on their advice type in the following order, from highest to lowest precedence: `@Around`, `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`. Note, however, that due to the implementation style in Spring‘s `AspectJAfterAdvice`, an `@After` advice method will effectively be invoked after any `@AfterReturning` or `@AfterThrowing` advice methods in the same aspect.When two pieces of the same type of advice (for example, two `@After` advice methods) defined in the same `@Aspect` class both need to run at the same join point, the ordering is undefined (since there is no way to retrieve the source code declaration order through reflection for javac-compiled classes). Consider collapsing such advice methods into one advice method per join point in each `@Aspect` class or refactor the pieces of advice into separate `@Aspect` classes that you can order at the aspect level via `Ordered` or `@Order`. 

在Spring Framework 5.2.7中，定义在同一个`@Aspect`类中需要在同一个连接点运行的通知方法的优先级是基于它们的通知类型，按照以下顺序从最高到最低的优先级分配的:`@Around`、`@Before`、`@After`、`@ afterreturn`、`@AfterThrowing`。但是请注意，由于Spring s ` AspectJAfterAdvice `中的实现风格，一个` @After `通知方法将有效地在同一方面的任何` @ afterreturn `或` @ afterthrows `通知方法之后被调用。当两块相同类型的建议(例如,两个`@After`建议方法)定义在相同的`@Aspect`类都需要运行在同一连接点,定是未定义的(因为没有办法获取源代码javac-compiled类的声明顺序通过反射)。考虑将这样的通知方法分解为每个`@Aspect`类中的每个连接点的一个通知方法，或者将通知片段重构为单独的`@Aspect`类，您可以通过`Ordered`或`@Order`在方面级别对这些类进行排序。

#### 5.4.5. Introductions

Introductions (known as inter-type declarations in AspectJ) enable an aspect to declare that advised objects implement a given interface, and to provide an implementation of that interface on behalf of those objects.

Introductions （在AspectJ中称为类型间声明）使方面可以声明通知对象实现给定的接口，并代表那些对象提供该接口的实现。

You can make an introduction by using the `@DeclareParents` annotation. This annotation is used to declare that matching types have a new parent (hence the name). For example, given an interface named `UsageTracked` and an implementation of that interface named `DefaultUsageTracked`, the following aspect declares that all implementors of service interfaces also implement the `UsageTracked` interface (to expose statistics via JMX for example):

您可以通过使用@DeclareParents批注进行引言。此批注用于声明匹配类型具有新的父代（因此具有名称）。例如，给定名为“ UsageTracked”的接口和该接口的“ DefaultUsageTracked”的实现，以下方面声明服务接口的所有实现者也都实现“ UsageTracked”接口（例如，通过JMX公开统计信息）：

```java
@Aspect
public class UsageTracking {

    @DeclareParents(value="com.xzy.myapp.service.*+", defaultImpl=DefaultUsageTracked.class)
    public static UsageTracked mixin;

    @Before("com.xyz.myapp.CommonPointcuts.businessService() && this(usageTracked)")
    public void recordUsage(UsageTracked usageTracked) {
        usageTracked.incrementUseCount();
    }

}
```

The interface to be implemented is determined by the type of the annotated field. The `value` attribute of the `@DeclareParents` annotation is an AspectJ type pattern. Any bean of a matching type implements the `UsageTracked` interface. Note that, in the before advice of the preceding example, service beans can be directly used as implementations of the `UsageTracked` interface. If accessing a bean programmatically, you would write the following:

要实现的接口由带注释的字段的类型确定。 @DeclareParents批注的value属性是AspectJ类型的模式。任何匹配类型的bean都会实现“ UsageTracked”接口。注意，在前面示例的前置通知中，服务bean可以直接用作“ UsageTracked”接口的实现。如果以编程方式访问bean，则应编写以下内容：

```java
UsageTracked usageTracked = (UsageTracked) context.getBean("myService");
```

#### 5.4.6. Aspect Instantiation Models

|      | This is an advanced topic. If you are just starting out with AOP, you can safely skip it until later. |
| ---- | ------------------------------------------------------------ |
|      | 这是一个高级主题。如果您刚开始使用AOP，则可以放心地跳过它，直到以后。 |

By default, there is a single instance of each aspect within the application context. AspectJ calls this the singleton instantiation model. It is possible to define aspects with alternate lifecycles. Spring supports AspectJ’s `perthis` and `pertarget` instantiation models; `percflow`, `percflowbelow`, and `pertypewithin` are not currently supported.

默认情况下，应用程序上下文中每个方面都有一个实例。 AspectJ将此称为单例实例化模型。可以使用备用生命周期来定义方面。 Spring支持AspectJ的perthis和pertarget实例化模型；目前不支持` percflow`，` percflowbelow`和` pertypewithin`。

You can declare a `perthis` aspect by specifying a `perthis` clause in the `@Aspect` annotation. Consider the following example:

您可以通过在@Aspect批注中指定一个perthis子句来声明一个perthis方面。考虑以下示例：

```java
@Aspect("perthis(com.xyz.myapp.CommonPointcuts.businessService())")
public class MyAspect {

    private int someState;

    @Before("com.xyz.myapp.CommonPointcuts.businessService()")
    public void recordServiceUsage() {
        // ...
    }

}
```

In the preceding example, the effect of the `perthis` clause is that one aspect instance is created for each unique service object that performs a business service (each unique object bound to `this` at join points matched by the pointcut expression). The aspect instance is created the first time that a method is invoked on the service object. The aspect goes out of scope when the service object goes out of scope. Before the aspect instance is created, none of the advice within it runs. As soon as the aspect instance has been created, the advice declared within it runs at matched join points, but only when the service object is the one with which this aspect is associated. See the AspectJ Programming Guide for more information on `per` clauses.

在前面的示例中，`perthis`子句的作用是为每个执行业务服务的唯一服务对象（每个唯一对象在与切入点表达式匹配的连接点处绑定到` this`的对象）创建一个方面实例。方面实例是在服务对象上首次调用方法时创建的。当服务对象超出范围时，方面将超出范围。在创建方面实例之前，其中的任何通知都不会运行。创建方面实例后，在其中声明的通知将在匹配的连接点处运行，但仅当服务对象是与此方面相关联的对象时才运行。有关`per`子句的更多信息，请参见AspectJ编程指南。

The `pertarget` instantiation model works in exactly the same way as `perthis`, but it creates one aspect instance for each unique target object at matched join points.

` pertarget`实例化模型的工作方式与` perthis`完全相同，但是它在匹配的连接点为每个唯一目标对象创建一个方面实例。

#### 5.4.7. An AOP Example

Now that you have seen how all the constituent parts work, we can put them together to do something useful.

既然您已经了解了所有组成部分是如何工作的，那么我们可以将它们放在一起做一些有用的事情。

The execution of business services can sometimes fail due to concurrency issues (for example, a deadlock loser). If the operation is retried, it is likely to succeed on the next try. For business services where it is appropriate to retry in such conditions (idempotent operations that do not need to go back to the user for conflict resolution), we want to transparently retry the operation to avoid the client seeing a `PessimisticLockingFailureException`. This is a requirement that clearly cuts across multiple services in the service layer and, hence, is ideal for implementing through an aspect.

有时由于并发问题（例如，死锁失败者），业务服务的执行可能会失败。如果重试该操作，则很可能在下一次尝试中成功。对于适合在这种情况下重试的业务服务（不需要为解决冲突而需要返回给用户的幂等操作），我们希望透明地重试该操作，以避免客户端看到` PessimisticLockingFailureException`。这项要求清楚地跨越了服务层中的多个服务，因此非常适合通过一个方面实施。

Because we want to retry the operation, we need to use around advice so that we can call `proceed` multiple times. The following listing shows the basic aspect implementation:

因为我们想重试该操作，所以我们需要使用around通知，以便我们可以多次调用` proceded`。以下清单显示了基本方面的实现：

```java
@Aspect
public class ConcurrentOperationExecutor implements Ordered {

    private static final int DEFAULT_MAX_RETRIES = 2;

    private int maxRetries = DEFAULT_MAX_RETRIES;
    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Around("com.xyz.myapp.CommonPointcuts.businessService()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        PessimisticLockingFailureException lockFailureException;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            }
            catch(PessimisticLockingFailureException ex) {
                lockFailureException = ex;
            }
        } while(numAttempts <= this.maxRetries);
        throw lockFailureException;
    }

}
```

Note that the aspect implements the `Ordered` interface so that we can set the precedence of the aspect higher than the transaction advice (we want a fresh transaction each time we retry). The `maxRetries` and `order` properties are both configured by Spring. The main action happens in the `doConcurrentOperation` around advice. Notice that, for the moment, we apply the retry logic to each `businessService()`. We try to proceed, and if we fail with a `PessimisticLockingFailureException`, we try again, unless we have exhausted all of our retry attempts.

请注意，方面实现了` Ordered`接口，因此我们可以将方面的优先级设置为高于交易建议（每次重试时都希望有新的交易）。 `maxRetries`和`order`属性都是由Spring配置的。通知的主要动作发生在`doConcurrentOperation`中。注意，目前，我们将重试逻辑应用于每个`businessService（）`。我们尝试继续，如果失败并出现`PessimisticLockingFailureException`，我们将重试，除非我们用尽了所有重试尝试。

The corresponding Spring configuration follows:

```xml
<aop:aspectj-autoproxy/>

<bean id="concurrentOperationExecutor" class="com.xyz.myapp.service.impl.ConcurrentOperationExecutor">
    <property name="maxRetries" value="3"/>
    <property name="order" value="100"/>
</bean>
```

To refine the aspect so that it retries only idempotent operations, we might define the following `Idempotent` annotation:

为了完善方面，使其仅重试幂等运算，我们可以定义以下`Idempotent`注释：

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation//标记注释
}
```

We can then use the annotation to annotate the implementation of service operations. The change to the aspect to retry only idempotent operations involves refining the pointcut expression so that only `@Idempotent` operations match, as follows:

然后，我们可以使用注释来注释服务操作的实现。方面的更改仅重试幂等操作涉及到完善切入点表达式，以便只有`@ Idempotent`操作匹配，如下所示：

```java
@Around("com.xyz.myapp.CommonPointcuts.businessService() && " +
        "@annotation(com.xyz.myapp.service.Idempotent)")
public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
    // ...
}
```

### 5.5. Schema-based AOP Support

基于架构的AOP支持

If you prefer an XML-based format, Spring also offers support for defining aspects using the `aop` namespace tags. The exact same pointcut expressions and advice kinds as when using the @AspectJ style are supported. Hence, in this section we focus on that syntax and refer the reader to the discussion in the previous section ([@AspectJ support](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj)) for an understanding of writing pointcut expressions and the binding of advice parameters.

如果您喜欢基于XML的格式，Spring还提供了对使用aop名称空间标签定义方面的支持。支持与使用@AspectJ样式时完全相同的切入点表达式和通知类型。因此，在本节中，我们将重点放在该语法上，并使读者参考上一节中的讨论（@AspectJ支持），以了解编写切入点表达式和通知参数的绑定。

To use the aop namespace tags described in this section, you need to import the `spring-aop` schema, as described in [XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas). See [the AOP schema](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas-aop) for how to import the tags in the `aop` namespace.

要使用本节中描述的aop名称空间标签，您需要导入spring-aop模式，如基于XML Schema的配置中所述。有关如何在aop名称空间中导入标签的信息，请参见AOP模式。

Within your Spring configurations, all aspect and advisor elements must be placed within an `<aop:config>` element (you can have more than one `<aop:config>` element in an application context configuration). An `<aop:config>` element can contain pointcut, advisor, and aspect elements (note that these must be declared in that order).

在您的Spring配置中，所有方面和顾问程序元素都必须放在<aop：config>元素内（在应用程序上下文配置中可以有多个<aop：config>元素）。 <aop：config>元素可以包含切入点，顾问程序和aspect元素（请注意，必须按此顺序声明它们）。

|      | The `<aop:config>` style of configuration makes heavy use of Spring’s [auto-proxying](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-autoproxy) mechanism. This can cause issues (such as advice not being woven) if you already use explicit auto-proxying through the use of `BeanNameAutoProxyCreator` or something similar. The recommended usage pattern is to use either only the `<aop:config>` style or only the `AutoProxyCreator` style and never mix them. |
| ---- | ------------------------------------------------------------ |
|      | <aop：config>的配置风格大量使用了Spring的自动代理机制。如果您已经通过使用BeanNameAutoProxyCreator或类似方法使用显式自动代理，则可能会导致问题（例如，未编制通知）。推荐的使用模式是仅使用<aop：config>样式或仅使用AutoProxyCreator样式，并且不要混合使用。 |

#### 5.5.1. Declaring an Aspect

When you use the schema support, an aspect is a regular Java object defined as a bean in your Spring application context. The state and behavior are captured in the fields and methods of the object, and the pointcut and advice information are captured in the XML.

You can declare an aspect by using the `<aop:aspect>` element, and reference the backing bean by using the `ref` attribute, as the following example shows:

使用模式支持时，方面是在Spring应用程序上下文中定义为Bean的常规Java对象。状态和行为在对象的字段和方法中捕获，切入点和建议信息在XML中捕获。

您可以使用<aop：aspect>元素声明一个方面，并使用ref属性引用该支持bean，如以下示例所示：

```xml
<aop:config>
    <aop:aspect id="myAspect" ref="aBean">
        ...
    </aop:aspect>
</aop:config>

<bean id="aBean" class="...">
    ...
</bean>
```

The bean that backs the aspect (`aBean` in this case) can of course be configured and dependency injected just like any other Spring bean.

支持方面的bean（在本例中为aBean）当然可以像配置其他Spring bean一样进行配置并注入依赖项。

#### 5.5.2. Declaring a Pointcut

You can declare a named pointcut inside an `<aop:config>` element, letting the pointcut definition be shared across several aspects and advisors.

A pointcut that represents the execution of any business service in the service layer can be defined as follows:

您可以在<aop：config>元素内声明一个命名的切入点，让切入点定义在多个方面和顾问程序之间共享。

可以定义代表服务层中任何业务服务的执行的切入点：

```xml
<aop:config>

    <aop:pointcut id="businessService"
        expression="execution(* com.xyz.myapp.service.*.*(..))"/>

</aop:config>
```

Note that the pointcut expression itself is using the same AspectJ pointcut expression language as described in [@AspectJ support](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj). If you use the schema based declaration style, you can refer to named pointcuts defined in types (@Aspects) within the pointcut expression. Another way of defining the above pointcut would be as follows:

注意，切入点表达式本身使用的是@AspectJ支持中所述的AspectJ切入点表达式语言。如果使用基于架构的声明样式，则可以引用在切入点表达式中的类型（@Aspects）中定义的命名切入点。定义上述切入点的另一种方法如下：

```xml
<aop:config>

    <aop:pointcut id="businessService"
        expression="com.xyz.myapp.CommonPointcuts.businessService()"/>

</aop:config>
```

Assume that you have a `CommonPointcuts` aspect as described in [Sharing Common Pointcut Definitions](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-common-pointcuts).

Then declaring a pointcut inside an aspect is very similar to declaring a top-level pointcut, as the following example shows:

假定您具有共享公共切入点定义中所述的CommonPointcuts方面。

然后，在方面中声明切入点与声明顶级切入点非常相似，如以下示例所示：

```xml
<aop:config>

    <aop:aspect id="myAspect" ref="aBean">

        <aop:pointcut id="businessService"
            expression="execution(* com.xyz.myapp.service.*.*(..))"/>

        ...

    </aop:aspect>

</aop:config>
```

In much the same way as an @AspectJ aspect, pointcuts declared by using the schema based definition style can collect join point context. For example, the following pointcut collects the `this` object as the join point context and passes it to the advice:

与@AspectJ方面几乎相同，使用基于架构的定义样式声明的切入点可以收集连接点上下文。例如，以下切入点收集“ this”对象作为连接点上下文，并将其传递给建议：

```xml
<aop:config>

    <aop:aspect id="myAspect" ref="aBean">

        <aop:pointcut id="businessService"
            expression="execution(* com.xyz.myapp.service.*.*(..)) &amp;&amp; this(service)"/>

        <aop:before pointcut-ref="businessService" method="monitor"/>

        ...

    </aop:aspect>

</aop:config>
```

The advice must be declared to receive the collected join point context by including parameters of the matching names, as follows:

必须声明通知，以通过包含匹配名称的参数来接收收集的连接点上下文，如下所示：

```java
public void monitor(Object service) {
    // ...
}
```

When combining pointcut sub-expressions, `&&` is awkward within an XML document, so you can use the `and`, `or`, and `not` keywords in place of `&&`, `||`, and `!`, respectively. For example, the previous pointcut can be better written as follows:

当组合切入点子表达式时，XML文档中的&&笨拙，因此可以使用关键字and和，or或not来代替&&，|||和`!！分别。例如，上一个切入点可以更好地编写如下：

```xml
<aop:config>

    <aop:aspect id="myAspect" ref="aBean">

        <aop:pointcut id="businessService"
            expression="execution(* com.xyz.myapp.service.*.*(..)) and this(service)"/>

        <aop:before pointcut-ref="businessService" method="monitor"/>

        ...
    </aop:aspect>
</aop:config>
```

Note that pointcuts defined in this way are referred to by their XML `id` and cannot be used as named pointcuts to form composite pointcuts. The named pointcut support in the schema-based definition style is thus more limited than that offered by the @AspectJ style.

请注意，以这种方式定义的切入点由其XML`id`引用，并且不能用作命名切入点以形成复合切入点。因此，基于架构的定义样式中的命名切入点支持比@AspectJ样式所提供的更受限制。

#### 5.5.3. Declaring Advice 声明通知

The schema-based AOP support uses the same five kinds of advice as the @AspectJ style, and they have exactly the same semantics.

基于模式的AOP支持使用与@AspectJ样式相同的五种建议，并且它们具有完全相同的语义。

##### Before Advice

Before advice runs before a matched method execution. It is declared inside an `<aop:aspect>` by using the `<aop:before>` element, as the following example shows:

在执行匹配的方法之前，先运行前置通知。使用<aop：before>元素在<aop：aspect>中声明它，如以下示例所示：

```xml
<aop:aspect id="beforeExample" ref="aBean">

    <aop:before
        pointcut-ref="dataAccessOperation"
        method="doAccessCheck"/>

    ...

</aop:aspect>
```

Here, `dataAccessOperation` is the `id` of a pointcut defined at the top (`<aop:config>`) level. To define the pointcut inline instead, replace the `pointcut-ref` attribute with a `pointcut` attribute, as follows:

在这里，dataAccessOperation是在最高（<aop：config>）级别定义的切入点的ID。要定义内联切入点，请使用以下方法将pointcut-ref属性替换为pointcut属性：

```xml
<aop:aspect id="beforeExample" ref="aBean">

    <aop:before
        pointcut="execution(* com.xyz.myapp.dao.*.*(..))"
        method="doAccessCheck"/>

    ...

</aop:aspect>
```

As we noted in the discussion of the @AspectJ style, using named pointcuts can significantly improve the readability of your code.

The `method` attribute identifies a method (`doAccessCheck`) that provides the body of the advice. This method must be defined for the bean referenced by the aspect element that contains the advice. Before a data access operation is performed (a method execution join point matched by the pointcut expression), the `doAccessCheck` method on the aspect bean is invoked.

正如我们在@AspectJ样式的讨论中所指出的那样，使用命名的切入点可以显着提高代码的可读性。

method属性标识提供通知正文的方法（doAccessCheck）。必须为包含通知的Aspect元素所引用的bean定义此方法。在执行数据访问操作（与切入点表达式匹配的方法执行连接点）之前，将调用Aspect Bean上的doAccessCheck方法。

##### After Returning Advice

After returning advice runs when a matched method execution completes normally. It is declared inside an `<aop:aspect>` in the same way as before advice. The following example shows how to declare it:

返回的通知在匹配的方法执行正常完成时运行。它在<aop：aspect>内以与之前建议相同的方式声明。以下示例显示了如何声明它：

```xml
<aop:aspect id="afterReturningExample" ref="aBean">

    <aop:after-returning
        pointcut-ref="dataAccessOperation"
        method="doAccessCheck"/>

    ...

</aop:aspect>
```

As in the @AspectJ style, you can get the return value within the advice body. To do so, use the `returning` attribute to specify the name of the parameter to which the return value should be passed, as the following example shows:

与@AspectJ样式一样，您可以在建议正文中获取返回值。为此，使用returning属性指定返回值应传递到的参数的名称，如以下示例所示：

```xml
<aop:aspect id="afterReturningExample" ref="aBean">

    <aop:after-returning
        pointcut-ref="dataAccessOperation"
        returning="retVal"
        method="doAccessCheck"/>
    ...

</aop:aspect>
```

The `doAccessCheck` method must declare a parameter named `retVal`. The type of this parameter constrains matching in the same way as described for `@AfterReturning`. For example, you can declare the method signature as follows:

doAccessCheck方法必须声明一个名为retVal的参数。该参数的类型以与@AfterReturning中所述相同的方式约束匹配。例如，您可以按以下方式声明方法签名：

```java
public void doAccessCheck(Object retVal) {...
```

##### After Throwing Advice

After throwing advice runs when a matched method execution exits by throwing an exception. It is declared inside an `<aop:aspect>` by using the `after-throwing` element, as the following example shows:

抛出建议后，当匹配的方法执行通过抛出异常退出时，运行建议。使用后抛元素在<aop：aspect>中声明它，如以下示例所示：

```xml
<aop:aspect id="afterThrowingExample" ref="aBean">

    <aop:after-throwing
        pointcut-ref="dataAccessOperation"
        method="doRecoveryActions"/>
    ...

</aop:aspect>
```

As in the @AspectJ style, you can get the thrown exception within the advice body. To do so, use the `throwing` attribute to specify the name of the parameter to which the exception should be passed as the following example shows:

与@AspectJ样式一样，您可以在通知正文中获取引发的异常。为此，请使用throwing属性指定异常应传递到的参数的名称，如以下示例所示：

```xml
<aop:aspect id="afterThrowingExample" ref="aBean">

    <aop:after-throwing
        pointcut-ref="dataAccessOperation"
        throwing="dataAccessEx"
        method="doRecoveryActions"/>

    ...

</aop:aspect>
```

The `doRecoveryActions` method must declare a parameter named `dataAccessEx`. The type of this parameter constrains matching in the same way as described for `@AfterThrowing`. For example, the method signature may be declared as follows:

doRecoveryActions方法必须声明一个名为dataAccessEx的参数。此参数的类型以与@AfterThrowing中所述相同的方式约束匹配。例如，方法签名可以声明如下：

```java
public void doRecoveryActions(DataAccessException dataAccessEx) {...
```

##### After (Finally) Advice

After (finally) advice runs no matter how a matched method execution exits. You can declare it by using the `after` element, as the following example shows:

无论最终如何执行匹配的方法，建议（最终）都会运行。您可以使用after元素声明它，如以下示例所示：

```xml
<aop:aspect id="afterFinallyExample" ref="aBean">

    <aop:after
        pointcut-ref="dataAccessOperation"
        method="doReleaseLock"/>

    ...

</aop:aspect>
```

##### Around Advice

The last kind of advice is around advice. Around advice runs "around" a matched method execution. It has the opportunity to do work both before and after the method runs and to determine when, how, and even if the method actually gets to run at all. Around advice is often used to share state before and after a method execution in a thread-safe manner (starting and stopping a timer, for example). Always use the least powerful form of advice that meets your requirements. Do not use around advice if before advice can do the job.

最后一种建议是围绕建议。围绕建议在匹配的方法执行过程中“围绕”运行。它有机会在方法运行之前和之后进行工作，并确定何时，如何以及什至根本不运行该方法。周围建议通常用于以线程安全的方式（例如，启动和停止计时器）在方法执行之前和之后共享状态。始终使用最不强大的建议形式，以满足您的要求。如果建议可以完成这项工作，请不要在建议周围使用。

You can declare around advice by using the `aop:around` element. The first parameter of the advice method must be of type `ProceedingJoinPoint`. Within the body of the advice, calling `proceed()` on the `ProceedingJoinPoint` causes the underlying method to run. The `proceed` method may also be called with an `Object[]`. The values in the array are used as the arguments to the method execution when it proceeds. See [Around Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-around-advice) for notes on calling `proceed` with an `Object[]`. The following example shows how to declare around advice in XML:

您可以使用aop：around元素声明周围建议。咨询方法的第一个参数必须是ProceedingJoinPoint类型。在建议的正文中，在ProceedingJoinPoint上调用proce（）会使底层方法运行。还可以使用Object []调用proce方法。数组中的值用作方法执行时的参数。有关调用Object []的注意事项，请参见“周围建议”。以下示例显示了如何在XML中围绕建议进行声明：

```xml
<aop:aspect id="aroundExample" ref="aBean">

    <aop:around
        pointcut-ref="businessService"
        method="doBasicProfiling"/>

    ...

</aop:aspect>
```

The implementation of the `doBasicProfiling` advice can be exactly the same as in the @AspectJ example (minus the annotation, of course), as the following example shows:

doBasicProfiling通知的实现可以与@AspectJ示例完全相同（当然要减去注释），如以下示例所示：

```java
public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
    // start stopwatch
    Object retVal = pjp.proceed();
    // stop stopwatch
    return retVal;
}
```

##### Advice Parameters

The schema-based declaration style supports fully typed advice in the same way as described for the @AspectJ support — by matching pointcut parameters by name against advice method parameters. See [Advice Parameters](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params) for details. If you wish to explicitly specify argument names for the advice methods (not relying on the detection strategies previously described), you can do so by using the `arg-names` attribute of the advice element, which is treated in the same manner as the `argNames` attribute in an advice annotation (as described in [Determining Argument Names](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params-names)). The following example shows how to specify an argument name in XML:

基于架构的声明样式以与@AspectJ支持相同的方式支持完全类型的建议，即通过名称与建议方法参数匹配切入点参数。有关详细信息，请参见建议参数。如果您希望显式指定建议方法的参数名称（不依赖于先前描述的检测策略），则可以通过使用advice元素的arg-names属性来实现，该属性的处理方式与argNames属性相同在建议注释中（如确定参数名称中所述）。以下示例显示如何在XML中指定参数名称：

```xml
<aop:before
    pointcut="com.xyz.lib.Pointcuts.anyPublicMethod() and @annotation(auditable)"
    method="audit"
    arg-names="auditable"/>
```

The `arg-names` attribute accepts a comma-delimited list of parameter names.

The following slightly more involved example of the XSD-based approach shows some around advice used in conjunction with a number of strongly typed parameters:

arg-names属性接受以逗号分隔的参数名称列表。

以下是基于XSD的方法中涉及程度稍高的示例，显示了一些与一些强类型参数结合使用的建议：

```java
package x.y.service;

public interface PersonService {

    Person getPerson(String personName, int age);
}

public class DefaultPersonService implements PersonService {

    public Person getPerson(String name, int age) {
        return new Person(name, age);
    }
}
```

Next up is the aspect. Notice the fact that the `profile(..)` method accepts a number of strongly-typed parameters, the first of which happens to be the join point used to proceed with the method call. The presence of this parameter is an indication that the `profile(..)` is to be used as `around` advice, as the following example shows:

接下来是方面。请注意profile（..）方法接受许多强类型参数的事实，其中第一个恰好是用于进行方法调用的连接点。此参数的存在表明profile（..）将用作建议，如以下示例所示：

```java
package x.y;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class SimpleProfiler {

    public Object profile(ProceedingJoinPoint call, String name, int age) throws Throwable {
        StopWatch clock = new StopWatch("Profiling for '" + name + "' and '" + age + "'");
        try {
            clock.start(call.toShortString());
            return call.proceed();
        } finally {
            clock.stop();
            System.out.println(clock.prettyPrint());
        }
    }
}
```

Finally, the following example XML configuration effects the execution of the preceding advice for a particular join point:

最后，以下示例XML配置影响了特定连接点的上述建议的执行：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- this is the object that will be proxied by Spring's AOP infrastructure -->
    <！-这是Spring的AOP基础结构将代理的对象->
    <bean id="personService" class="x.y.service.DefaultPersonService"/>

    <!-- this is the actual advice itself -->
        <！-这是实际的建议本身->
    <bean id="profiler" class="x.y.SimpleProfiler"/>
    <aop:config>
        <aop:aspect ref="profiler">
            <aop:pointcut id="theExecutionOfSomePersonServiceMethod"
                expression="execution(* x.y.service.PersonService.getPerson(String,int))
                and args(name, age)"/>

            <aop:around pointcut-ref="theExecutionOfSomePersonServiceMethod"
                method="profile"/>

        </aop:aspect>
    </aop:config>

</beans>
```

Consider the following driver script:

考虑以下驱动程序脚本：

```java
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import x.y.service.PersonService;

public final class Boot {

    public static void main(final String[] args) throws Exception {
        BeanFactory ctx = new ClassPathXmlApplicationContext("x/y/plain.xml");
        PersonService person = (PersonService) ctx.getBean("personService");
        person.getPerson("Pengo", 12);
    }
}
```

With such a Boot class, we would get output similar to the following on standard output:

有了这样的Boot类，我们将在标准输出上获得类似于以下内容的输出：

```
StopWatch 'Profiling for 'Pengo' and '12'': running time (millis) = 0
-----------------------------------------
ms     %     Task name
-----------------------------------------
00000  ?  execution(getFoo)
```

##### Advice Ordering

When multiple pieces of advice need to run at the same join point (executing method) the ordering rules are as described in [Advice Ordering](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-ordering). The precedence between aspects is determined via the `order` attribute in the `<aop:aspect>` element or by either adding the `@Order` annotation to the bean that backs the aspect or by having the bean implement the `Ordered` interface.

当需要在同一连接点（执行方法）上运行多个建议时，排序规则如“建议排序”中所述。方面之间的优先级是通过<aop：aspect>元素中的order属性或通过将@Order批注添加到支持方面的bean或通过使bean实现Ordered接口来确定的。

|      | In contrast to the precedence rules for advice methods defined in the same `@Aspect` class, when two pieces of advice defined in the same `<aop:aspect>` element both need to run at the same join point, the precedence is determined by the order in which the advice elements are declared within the enclosing `<aop:aspect>` element, from highest to lowest precedence.. |
| ---- | ------------------------------------------------------------ |
|      | For example, given an `around` advice and a `before` advice defined in the same `<aop:aspect>` element that apply to the same join point, to ensure that the `around` advice has higher precedence than the `before` advice, the `<aop:around>` element must be declared before the `<aop:before>` element |
|      | As a general rule of thumb, if you find that you have multiple pieces of advice defined in the same `<aop:aspect>` element that apply to the same join point, consider collapsing such advice methods into one advice method per join point in each `<aop:aspect>` element or refactor the pieces of advice into separate `<aop:aspect>` elements that you can order at the aspect level. |
|      | 与在同一@Aspect类中定义的通知方法的优先规则相反，当在同一<aop：aspect>元素中定义的两条建议都需要在同一连接点上运行时，优先级由中的顺序确定在封闭的<aop：aspect>元素中声明的通知元素，从最高优先级到最低优先级。 |
|      | 例如，给定一个绕行建议和一个在同一<aop：aspect>元素中定义的，适用于同一联接点的事前通知，以确保绕行建议的优先级高于事前通知的<aop：around>元素必须在<aop：before>元素之前声明。 |
|      | 根据一般经验，如果发现在同一<aop：aspect>元素中定义了多个建议，这些建议适用于同一连接点，请考虑将这些建议方法折叠为每个< aop：aspect>元素，或将建议重构为单独的<aop：aspect>元素，您可以在方面级别进行排序。 |

#### 5.5.4. Introductions

Introductions (known as inter-type declarations in AspectJ) let an aspect declare that advised objects implement a given interface and provide an implementation of that interface on behalf of those objects.

You can make an introduction by using the `aop:declare-parents` element inside an `aop:aspect`. You can use the `aop:declare-parents` element to declare that matching types have a new parent (hence the name). For example, given an interface named `UsageTracked` and an implementation of that interface named `DefaultUsageTracked`, the following aspect declares that all implementors of service interfaces also implement the `UsageTracked` interface. (In order to expose statistics through JMX for example.)

简介（在AspectJ中称为类型间声明）使方面可以声明建议的对象实现给定的接口，并代表那些对象提供该接口的实现。

您可以通过在aop：aspect中使用aop：declare-parents元素进行介绍。您可以使用aop：declare-parents元素来声明匹配类型具有新的父代（因此而得名）。例如，给定一个名为UsageTracked的接口和该接口名为DefaultUsageTracked的实现，以下方面声明服务接口的所有实现者也都实现UsageTracked接口。 （例如，为了通过JMX公开统计信息。）

```xml
<aop:aspect id="usageTrackerAspect" ref="usageTracking">

    <aop:declare-parents
        types-matching="com.xzy.myapp.service.*+"
        implement-interface="com.xyz.myapp.service.tracking.UsageTracked"
        default-impl="com.xyz.myapp.service.tracking.DefaultUsageTracked"/>
    <aop:before
        pointcut="com.xyz.myapp.CommonPointcuts.businessService()
            and this(usageTracked)"
            method="recordUsage"/>
</aop:aspect>
```

The class that backs the `usageTracking` bean would then contain the following method:

支持usageTracking bean的类将包含以下方法：

```java
public void recordUsage(UsageTracked usageTracked) {
    usageTracked.incrementUseCount();
}
```

The interface to be implemented is determined by the `implement-interface` attribute. The value of the `types-matching` attribute is an AspectJ type pattern. Any bean of a matching type implements the `UsageTracked` interface. Note that, in the before advice of the preceding example, service beans can be directly used as implementations of the `UsageTracked` interface. To access a bean programmatically, you could write the following:

要实现的接口由Implement-interface属性确定。类型匹配属性的值是AspectJ类型模式。匹配类型的任何bean均实现UsageTracked接口。请注意，在前面示例的前置建议中，服务Bean可以直接用作UsageTracked接口的实现。要以编程方式访问bean，可以编写以下代码：

```java
UsageTracked usageTracked = (UsageTracked) context.getBean("myService");
```

#### 5.5.5. Aspect Instantiation Models

The only supported instantiation model for schema-defined aspects is the singleton model. Other instantiation models may be supported in future releases.

模式定义方面唯一受支持的实例化模型是单例模型。在将来的版本中可能会支持其他实例化模型。

#### 5.5.6. Advisors

The concept of “advisors” comes from the AOP support defined in Spring and does not have a direct equivalent in AspectJ. An advisor is like a small self-contained aspect that has a single piece of advice. The advice itself is represented by a bean and must implement one of the advice interfaces described in [Advice Types in Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-api-advice-types). Advisors can take advantage of AspectJ pointcut expressions.

“顾问”的概念来自Spring中定义的AOP支持，并且在AspectJ中没有直接等效的概念。顾问就像一个独立的小方面，只有一条建议。通知本身由bean表示，并且必须实现Spring的“通知类型”中描述的通知接口之一。顾问可以利用AspectJ切入点表达式。

Spring supports the advisor concept with the `<aop:advisor>` element. You most commonly see it used in conjunction with transactional advice, which also has its own namespace support in Spring. The following example shows an advisor:

Spring通过<aop：advisor>元素支持顾问程序概念。您通常会看到它与事务建议结合使用，事务建议在Spring中也有自己的名称空间支持。以下示例显示顾问程序：

```xml
<aop:config>

    <aop:pointcut id="businessService"
        expression="execution(* com.xyz.myapp.service.*.*(..))"/>

    <aop:advisor
        pointcut-ref="businessService"
        advice-ref="tx-advice"/>

</aop:config>

<tx:advice id="tx-advice">
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
```

As well as the `pointcut-ref` attribute used in the preceding example, you can also use the `pointcut` attribute to define a pointcut expression inline.

To define the precedence of an advisor so that the advice can participate in ordering, use the `order` attribute to define the `Ordered` value of the advisor.

除了在前面的示例中使用的pointcut-ref属性，您还可以使用pointcut属性内联定义一个pointcut表达式。

要定义顾问程序的优先级，以便该建议书可以参与排序，请使用order属性定义顾问程序的Ordered值。

#### 5.5.7. An AOP Schema Example

This section shows how the concurrent locking failure retry example from [An AOP Example](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-example) looks when rewritten with the schema support.

The execution of business services can sometimes fail due to concurrency issues (for example, a deadlock loser). If the operation is retried, it is likely to succeed on the next try. For business services where it is appropriate to retry in such conditions (idempotent operations that do not need to go back to the user for conflict resolution), we want to transparently retry the operation to avoid the client seeing a `PessimisticLockingFailureException`. This is a requirement that clearly cuts across multiple services in the service layer and, hence, is ideal for implementing through an aspect.

本节说明使用模式支持重写时，AOP示例中的并发锁定失败重试示例的外观。

有时由于并发问题（例如，死锁失败者），业务服务的执行可能会失败。如果重试该操作，则很可能在下一次尝试中成功。对于适合在这种情况下重试的业务（不需要为解决冲突而需要返回给用户的幂等操作），我们希望透明地重试该操作，以避免客户端看到PessimisticLockingFailureException。这项要求清楚地跨越了服务层中的多个服务，因此非常适合通过一个方面实施。

Because we want to retry the operation, we need to use around advice so that we can call `proceed` multiple times. The following listing shows the basic aspect implementation (which is a regular Java class that uses the schema support):

因为我们想重试该操作，所以我们需要使用围绕建议，以便可以多次调用proced。以下清单显示了基本方面的实现（这是使用模式支持的常规Java类）：

```java
public class ConcurrentOperationExecutor implements Ordered {

    private static final int DEFAULT_MAX_RETRIES = 2;

    private int maxRetries = DEFAULT_MAX_RETRIES;
    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        PessimisticLockingFailureException lockFailureException;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            }
            catch(PessimisticLockingFailureException ex) {
                lockFailureException = ex;
            }
        } while(numAttempts <= this.maxRetries);
        throw lockFailureException;
    }

}
```

Note that the aspect implements the `Ordered` interface so that we can set the precedence of the aspect higher than the transaction advice (we want a fresh transaction each time we retry). The `maxRetries` and `order` properties are both configured by Spring. The main action happens in the `doConcurrentOperation` around advice method. We try to proceed. If we fail with a `PessimisticLockingFailureException`, we try again, unless we have exhausted all of our retry attempts.

请注意，方面实现了Ordered接口，因此我们可以将方面的优先级设置为高于事务建议（每次重试时都希望有新的事务）。 maxRetries和order属性都由Spring配置。主要操作发生在建议方法周围的doConcurrentOperation中。我们尝试继续。如果由于PessimisticLockingFailureException失败，则将重试，除非我们用尽了所有重试尝试。

|      | This class is identical to the one used in the @AspectJ example, but with the annotations removed. |
| ---- | ------------------------------------------------------------ |
|      | 该类与@AspectJ示例中使用的类相同，但是删除了注释。           |

The corresponding Spring configuration is as follows:

```xml
<aop:config>

    <aop:aspect id="concurrentOperationRetry" ref="concurrentOperationExecutor">

        <aop:pointcut id="idempotentOperation"
            expression="execution(* com.xyz.myapp.service.*.*(..))"/>

        <aop:around
            pointcut-ref="idempotentOperation"
            method="doConcurrentOperation"/>

    </aop:aspect>

</aop:config>

<bean id="concurrentOperationExecutor"
    class="com.xyz.myapp.service.impl.ConcurrentOperationExecutor">
        <property name="maxRetries" value="3"/>
        <property name="order" value="100"/>
</bean>
```

Notice that, for the time, being we assume that all business services are idempotent. If this is not the case, we can refine the aspect so that it retries only genuinely idempotent operations, by introducing an `Idempotent` annotation and using the annotation to annotate the implementation of service operations, as the following example shows:

请注意，目前我们假设所有业务服务都是幂等的。如果不是这种情况，我们可以改进方面，以便通过引入幂等注释并使用该注释来注释服务操作的实现，使其仅重试真正的幂等操作，如以下示例所示：

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation
}
```

The change to the aspect to retry only idempotent operations involves refining the pointcut expression so that only `@Idempotent` operations match, as follows:

方面的更改仅重试幂等操作涉及到改进切入点表达式，以便仅@Idempotent操作匹配，如下所示：

```xml
<aop:pointcut id="idempotentOperation"
        expression="execution(* com.xyz.myapp.service.*.*(..)) and
        @annotation(com.xyz.myapp.service.Idempotent)"/>
```

### 5.6. Choosing which AOP Declaration Style to Use

选择要使用的AOP声明样式

Once you have decided that an aspect is the best approach for implementing a given requirement, how do you decide between using Spring AOP or AspectJ and between the Aspect language (code) style, the @AspectJ annotation style, or the Spring XML style? These decisions are influenced by a number of factors including application requirements, development tools, and team familiarity with AOP.

一旦确定方面是实现给定需求的最佳方法，您如何在使用Spring AOP或AspectJ以及在Aspect语言（代码）样式，@ AspectJ批注样式或Spring XML样式之间做出选择？这些决定受许多因素的影响，包括应用程序需求，开发工具和团队对AOP的熟悉程度。

#### 5.6.1. Spring AOP or Full AspectJ?

Use the simplest thing that can work. Spring AOP is simpler than using full AspectJ, as there is no requirement to introduce the AspectJ compiler / weaver into your development and build processes. If you only need to advise the execution of operations on Spring beans, Spring AOP is the right choice. If you need to advise objects not managed by the Spring container (such as domain objects, typically), you need to use AspectJ. You also need to use AspectJ if you wish to advise join points other than simple method executions (for example, field get or set join points and so on).

使用最简单的方法即可。 Spring AOP比使用完整的AspectJ更简单，因为不需要在开发和构建过程中引入AspectJ编译器/编织器。如果您只需要建议在Spring Bean上执行操作，则Spring AOP是正确的选择。如果您需要建议不受Spring容器管理的对象（通常是域对象），则需要使用AspectJ。如果您希望建议除简单方法执行之外的连接点（例如，字段获取或设置连接点等），则还需要使用AspectJ。

When you use AspectJ, you have the choice of the AspectJ language syntax (also known as the “code style”) or the @AspectJ annotation style. Clearly, if you do not use Java 5+, the choice has been made for you: Use the code style. If aspects play a large role in your design, and you are able to use the [AspectJ Development Tools (AJDT)](https://www.eclipse.org/ajdt/) plugin for Eclipse, the AspectJ language syntax is the preferred option. It is cleaner and simpler because the language was purposefully designed for writing aspects. If you do not use Eclipse or have only a few aspects that do not play a major role in your application, you may want to consider using the @AspectJ style, sticking with regular Java compilation in your IDE, and adding an aspect weaving phase to your build script.

使用AspectJ时，可以选择AspectJ语言语法（也称为“代码样式”）或@AspectJ注释样式。显然，如果您不使用Java 5+，则可以为您做出选择：使用代码样式。如果方面在您的设计中起着重要作用，并且您能够将AspectJ开发工具（AJDT）插件用于Eclipse，则AspectJ语言语法是首选。它更干净，更简单，因为该语言是专为编写方面而设计的。如果您不使用Eclipse或只有少数几个方面在您的应用程序中不起作用，那么您可能要考虑使用@AspectJ样式，在IDE中坚持常规Java编译，并向其中添加方面编织阶段您的构建脚本。

#### 5.6.2. @AspectJ or XML for Spring AOP?

If you have chosen to use Spring AOP, you have a choice of @AspectJ or XML style. There are various tradeoffs to consider.

如果选择使用Spring AOP，则可以选择@AspectJ或XML样式。有各种折衷考虑。

The XML style may be most familiar to existing Spring users, and it is backed by genuine POJOs. When using AOP as a tool to configure enterprise services, XML can be a good choice (a good test is whether you consider the pointcut expression to be a part of your configuration that you might want to change independently). With the XML style, it is arguably clearer from your configuration which aspects are present in the system.

XML样式可能是现有Spring用户最熟悉的，并且得到了真正的POJO的支持。当使用AOP作为配置企业服务的工具时，XML可能是一个不错的选择（一个很好的测试是您是否将切入点表达式视为您可能希望独立更改的配置的一部分）。使用XML样式，可以说从您的配置中可以更清楚地了解系统中存在哪些方面。

The XML style has two disadvantages. First, it does not fully encapsulate the implementation of the requirement it addresses in a single place. The DRY principle says that there should be a single, unambiguous, authoritative representation of any piece of knowledge within a system. When using the XML style, the knowledge of how a requirement is implemented is split across the declaration of the backing bean class and the XML in the configuration file. When you use the @AspectJ style, this information is encapsulated in a single module: the aspect. Secondly, the XML style is slightly more limited in what it can express than the @AspectJ style: Only the “singleton” aspect instantiation model is supported, and it is not possible to combine named pointcuts declared in XML. For example, in the @AspectJ style you can write something like the following:

XML样式有两个缺点。首先，它没有完全将要解决的需求的实现封装在一个地方。 DRY原则说，系统中的任何知识都应该有一个单一，明确，权威的表示形式。当使用XML样式时，关于如何实现需求的知识会在配置文件中的后备bean类的声明和XML中分散。当您使用@AspectJ样式时，此信息将封装在一个模块中：方面。其次，与@AspectJ样式相比，XML样式在表达能力上有更多限制：仅支持“单例”方面实例化模型，并且无法组合以XML声明的命名切入点。例如，使用@AspectJ样式，您可以编写如下内容：

```java
@Pointcut("execution(* get*())")
public void propertyAccess() {}

@Pointcut("execution(org.xyz.Account+ *(..))")
public void operationReturningAnAccount() {}

@Pointcut("propertyAccess() && operationReturningAnAccount()")
public void accountPropertyAccess() {}
```

In the XML style you can declare the first two pointcuts:

```xml
<aop:pointcut id="propertyAccess"
        expression="execution(* get*())"/>

<aop:pointcut id="operationReturningAnAccount"
        expression="execution(org.xyz.Account+ *(..))"/>
```

The downside of the XML approach is that you cannot define the `accountPropertyAccess` pointcut by combining these definitions.

XML方法的缺点是您无法通过组合这些定义来定义accountPropertyAccess切入点。

The @AspectJ style supports additional instantiation models and richer pointcut composition. It has the advantage of keeping the aspect as a modular unit. It also has the advantage that the @AspectJ aspects can be understood (and thus consumed) both by Spring AOP and by AspectJ. So, if you later decide you need the capabilities of AspectJ to implement additional requirements, you can easily migrate to a classic AspectJ setup. On balance, the Spring team prefers the @AspectJ style for custom aspects beyond simple configuration of enterprise services.

@AspectJ样式支持其他实例化模型和更丰富的切入点组合。它具有将方面保持为模块化单元的优势。它还具有的优点是，Spring AOP和AspectJ都可以理解@AspectJ方面。因此，如果您以后决定需要AspectJ的功能来实现其他要求，则可以轻松地迁移到经典的AspectJ设置。总而言之，Spring团队在自定义方面更喜欢@AspectJ样式，而不是简单地配置企业服务。

### 5.7. Mixing Aspect Types

It is perfectly possible to mix @AspectJ style aspects by using the auto-proxying support, schema-defined `<aop:aspect>` aspects, `<aop:advisor>` declared advisors, and even proxies and interceptors in other styles in the same configuration. All of these are implemented by using the same underlying support mechanism and can co-exist without any difficulty.

通过使用自动代理支持，模式定义的<aop：aspect>方面，<aop：advisor>声明的顾问程序，甚至是同一配置中其他样式的代理和拦截器，完全可以混合@AspectJ样式的方面。所有这些都是通过使用相同的基础支持机制实现的，并且可以毫无困难地共存。

### 5.8. Proxying Mechanisms

Spring AOP uses either JDK dynamic proxies or CGLIB to create the proxy for a given target object. JDK dynamic proxies are built into the JDK, whereas CGLIB is a common open-source class definition library (repackaged into `spring-core`).

Spring AOP使用JDK动态代理或CGLIB创建给定目标对象的代理。 JDK内置了JDK动态代理，而CGLIB是常见的开源类定义库（重新包装到spring-core中）。

If the target object to be proxied implements at least one interface, a JDK dynamic proxy is used. All of the interfaces implemented by the target type are proxied. If the target object does not implement any interfaces, a CGLIB proxy is created.

如果要代理的目标对象实现至少一个接口，则使用JDK动态代理。代理了由目标类型实现的所有接口。如果目标对象未实现任何接口，则将创建CGLIB代理。

If you want to force the use of CGLIB proxying (for example, to proxy every method defined for the target object, not only those implemented by its interfaces), you can do so. However, you should consider the following issues:

如果要强制使用CGLIB代理（例如，代理为目标对象定义的每个方法，而不仅是由其接口实现的方法），都可以这样做。但是，您应该考虑以下问题：

- With CGLIB, `final` methods cannot be advised, as they cannot be overridden in runtime-generated subclasses.
- 使用CGLIB，不能建议final方法，因为不能在运行时生成的子类中覆盖它们。
- As of Spring 4.0, the constructor of your proxied object is NOT called twice anymore, since the CGLIB proxy instance is created through Objenesis. Only if your JVM does not allow for constructor bypassing, you might see double invocations and corresponding debug log entries from Spring’s AOP support.
- 从Spring 4.0开始，由于CGLIB代理实例是通过Objenesis创建的，因此不再调用代理对象的构造函数两次。仅当您的JVM不允许绕过构造函数时，您才可能从Spring的AOP支持中看到两次调用和相应的调试日志条目。

To force the use of CGLIB proxies, set the value of the `proxy-target-class` attribute of the `<aop:config>` element to true, as follows:

要强制使用CGLIB代理，请将<aop：config>元素的proxy-target-class属性的值设置为true，如下所示：

```xml
<aop:config proxy-target-class="true">
    <!-- other beans defined here... -->
</aop:config>
```

To force CGLIB proxying when you use the @AspectJ auto-proxy support, set the `proxy-target-class` attribute of the `<aop:aspectj-autoproxy>` element to `true`, as follows:

要在使用@AspectJ自动代理支持时强制CGLIB代理，请将<aop：aspectj-autoproxy>元素的proxy-target-class属性设置为true，如下所示：

```xml
<aop:aspectj-autoproxy proxy-target-class="true"/>
```

|      | Multiple `<aop:config/>` sections are collapsed into a single unified auto-proxy creator at runtime, which applies the *strongest* proxy settings that any of the `<aop:config/>` sections (typically from different XML bean definition files) specified. This also applies to the `<tx:annotation-driven/>` and `<aop:aspectj-autoproxy/>` elements. |
| ---- | ------------------------------------------------------------ |
|      | To be clear, using `proxy-target-class="true"` on `<tx:annotation-driven/>`, `<aop:aspectj-autoproxy/>`, or `<aop:config/>` elements forces the use of CGLIB proxies *for all three of them*. |
|      | 多个<aop：config />部分在运行时折叠到一个统一的自动代理创建器中，该创建器将应用任何<aop：config />部分（通常来自不同XML Bean定义文件）指定的最强代理设置。这也适用于<tx：annotation-driven />和<aop：aspectj-autoproxy />元素。 |
|      | 明确地说，在<tx：annotation-driven />，<aop：aspectj-autoproxy />或<aop：config />元素上使用proxy-target-class =“ true”会强制对所有三个元素使用CGLIB代理其中。 |

#### 5.8.1. Understanding AOP Proxies

Spring AOP is proxy-based. It is vitally important that you grasp the semantics of what that last statement actually means before you write your own aspects or use any of the Spring AOP-based aspects supplied with the Spring Framework.

Spring AOP是基于代理的。在编写自己的方面或使用Spring框架随附的任何基于Spring AOP的方面之前，掌握最后一条语句实际含义的语义至关重要。

Consider first the scenario where you have a plain-vanilla, un-proxied, nothing-special-about-it, straight object reference, as the following code snippet shows:

首先考虑您有一个普通的，未经代理的，没有什么特别的，直接的对象引用的情况，如以下代码片段所示：

```java
public class SimplePojo implements Pojo {
    public void foo() {
        // this next method invocation is a direct call on the 'this' reference
        //此下一个方法调用是对“ this”引用的直接调用
        this.bar();
    }
    public void bar() {
        // some logic...
    }
}
```

If you invoke a method on an object reference, the method is invoked directly on that object reference, as the following image and listing show:

如果在对象引用上调用方法，则直接在该对象引用上调用该方法，如下图和清单所示：

![aop proxy plain pojo call](https://docs.spring.io/spring-framework/docs/current/reference/html/images/aop-proxy-plain-pojo-call.png)

```java
public class Main {

    public static void main(String[] args) {
        Pojo pojo = new SimplePojo();
        // this is a direct method call on the 'pojo' reference
        //这是对'pojo'引用的直接方法调用
        pojo.foo();
    }
}
```

Things change slightly when the reference that client code has is a proxy. Consider the following diagram and code snippet:

当客户端代码具有的引用是代理时，情况会稍有变化。考虑以下图表和代码片段：

![aop proxy call](https://docs.spring.io/spring-framework/docs/current/reference/html/images/aop-proxy-call.png)



```java
public class Main {

    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        factory.addAdvice(new RetryAdvice());

        Pojo pojo = (Pojo) factory.getProxy();
        // this is a method call on the proxy!
        pojo.foo();
    }
}
```

The key thing to understand here is that the client code inside the `main(..)` method of the `Main` class has a reference to the proxy. This means that method calls on that object reference are calls on the proxy. As a result, the proxy can delegate to all of the interceptors (advice) that are relevant to that particular method call. However, once the call has finally reached the target object (the `SimplePojo` reference in this case), any method calls that it may make on itself, such as `this.bar()` or `this.foo()`, are going to be invoked against the `this` reference, and not the proxy. This has important implications. It means that self-invocation is not going to result in the advice associated with a method invocation getting a chance to run.

此处要理解的关键是Main类的main（..）方法中的客户端代码具有对代理的引用。这意味着该对象引用上的方法调用是代理上的调用。结果，代理可以委派给与该特定方法调用相关的所有拦截器（建议）。但是，一旦调用最终到达目标对象（在本例中为SimplePojo引用），则可能会针对它调用可能对其自身进行的任何方法调用，例如this.bar（）或this.foo（）。此参考，而不是代理。这具有重要意义。这意味着自调用不会导致与方法调用相关的advice（增强）得到运行的机会。

Okay, so what is to be done about this? The best approach (the term "best" is used loosely here) is to refactor your code such that the self-invocation does not happen. This does entail some work on your part, but it is the best, least-invasive approach. The next approach is absolutely horrendous, and we hesitate to point it out, precisely because it is so horrendous. You can (painful as it is to us) totally tie the logic within your class to Spring AOP, as the following example shows:

好吧，那么该怎么办？最佳方法（此处宽松地使用术语“最佳”）是重构代码，以免发生自调用。这确实需要您做一些工作，但这是最好的，侵入性最小的方法。下一种方法绝对可怕，我们正要指出这一点，恰恰是因为它是如此可怕。您可以（对我们来说是痛苦的）完全将类中的逻辑与Spring AOP绑定在一起，如以下示例所示：

```java
public class SimplePojo implements Pojo {

    public void foo() {
        // this works, but... gah!
        ((Pojo) AopContext.currentProxy()).bar();
    }

    public void bar() {
        // some logic...
    }
}
```

This totally couples your code to Spring AOP, and it makes the class itself aware of the fact that it is being used in an AOP context, which flies in the face of AOP. It also requires some additional configuration when the proxy is being created, as the following example shows:

这将您的代码完全耦合到Spring AOP，并且使类本身意识到在AOP上下文中使用它这一事实，而AOP却是事实。创建代理时，还需要一些其他配置，如以下示例所示：

```java
public class Main {

    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        factory.addAdvice(new RetryAdvice());
        factory.setExposeProxy(true);

        Pojo pojo = (Pojo) factory.getProxy();
        // this is a method call on the proxy!
        pojo.foo();
    }
}
```

Finally, it must be noted that AspectJ does not have this self-invocation issue because it is not a proxy-based AOP framework.

最后，必须指出，AspectJ没有此自调用问题，因为它不是基于代理的AOP框架。

### 5.9. Programmatic Creation of @AspectJ Proxies

In addition to declaring aspects in your configuration by using either `<aop:config>` or `<aop:aspectj-autoproxy>`, it is also possible to programmatically create proxies that advise target objects. For the full details of Spring’s AOP API, see the [next chapter](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-api). Here, we want to focus on the ability to automatically create proxies by using @AspectJ aspects.

You can use the `org.springframework.aop.aspectj.annotation.AspectJProxyFactory` class to create a proxy for a target object that is advised by one or more @AspectJ aspects. The basic usage for this class is very simple, as the following example shows:

Java

Kotlin

```java
// create a factory that can generate a proxy for the given target object
AspectJProxyFactory factory = new AspectJProxyFactory(targetObject);

// add an aspect, the class must be an @AspectJ aspect
// you can call this as many times as you need with different aspects
factory.addAspect(SecurityManager.class);

// you can also add existing aspect instances, the type of the object supplied must be an @AspectJ aspect
factory.addAspect(usageTracker);

// now get the proxy object...
MyInterfaceType proxy = factory.getProxy();
```

See the [javadoc](https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/aop/aspectj/annotation/AspectJProxyFactory.html) for more information.

### 5.10. Using AspectJ with Spring Applications

Everything we have covered so far in this chapter is pure Spring AOP. In this section, we look at how you can use the AspectJ compiler or weaver instead of or in addition to Spring AOP if your needs go beyond the facilities offered by Spring AOP alone.

到目前为止，本章介绍的所有内容都是纯Spring AOP。在本节中，我们将研究如果您的需求超出了Spring AOP所提供的功能，那么您可以如何使用AspectJ编译器或weaver代替Spring AOP或除Spring AOP之外使用。

Spring ships with a small AspectJ aspect library, which is available stand-alone in your distribution as `spring-aspects.jar`. You need to add this to your classpath in order to use the aspects in it. [Using AspectJ to Dependency Inject Domain Objects with Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-atconfigurable) and [Other Spring aspects for AspectJ](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ajlib-other) discuss the content of this library and how you can use it. [Configuring AspectJ Aspects by Using Spring IoC](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-configure) discusses how to dependency inject AspectJ aspects that are woven using the AspectJ compiler. Finally, [Load-time Weaving with AspectJ in the Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw) provides an introduction to load-time weaving for Spring applications that use AspectJ.

Spring附带了一个小的AspectJ方面库，该库在您的发行版中可以作为spring-aspects.jar独立使用。您需要将其添加到类路径中才能使用其中的方面。使用AspectJ依赖于Spring和AspectJ的其他Spring方面来注入域对象以及AspectJ讨论了该库的内容以及如何使用它。使用Spring IoC配置AspectJ方面讨论了如何依赖注入使用AspectJ编译器编织的AspectJ方面。最后，Spring Framework中使用AspectJ进行的加载时编织为使用AspectJ的Spring应用程序提供了加载时编织的介绍。

#### 5.10.1. Using AspectJ to Dependency Inject Domain Objects with Spring

使用AspectJ通过Spring依赖注入域对象

The Spring container instantiates and configures beans defined in your application context. It is also possible to ask a bean factory to configure a pre-existing object, given the name of a bean definition that contains the configuration to be applied. `spring-aspects.jar` contains an annotation-driven aspect that exploits this capability to allow dependency injection of any object. The support is intended to be used for objects created outside of the control of any container. Domain objects often fall into this category because they are often created programmatically with the `new` operator or by an ORM tool as a result of a database query.

Spring容器实例化并配置在您的应用程序上下文中定义的bean。给定包含要应用的配置的Bean定义的名称，也可以要求Bean工厂配置预先存在的对象。 spring-aspects.jar包含一个注释驱动的方面，该方面利用此功能允许依赖项注入任何对象。该支架旨在用于在任何容器的控制范围之外创建的对象。域对象通常属于此类，因为它们通常是通过数据库查询的结果，使用新操作符或ORM工具以编程方式创建的。

The `@Configurable` annotation marks a class as being eligible for Spring-driven configuration. In the simplest case, you can use purely it as a marker annotation, as the following example shows:

@Configurable注释将一个类标记为符合Spring驱动的配置。在最简单的情况下，您可以将其纯粹用作标记注释，如以下示例所示：

```java
package com.xyz.myapp.domain;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Account {
    // ...
}
```

When used as a marker interface in this way, Spring configures new instances of the annotated type (`Account`, in this case) by using a bean definition (typically prototype-scoped) with the same name as the fully-qualified type name (`com.xyz.myapp.domain.Account`). Since the default name for a bean is the fully-qualified name of its type, a convenient way to declare the prototype definition is to omit the `id` attribute, as the following example shows:

当以这种方式用作标记接口时，Spring通过使用具有与完全限定类型名称（com）相同名称的bean定义（通常为原型作用域）来配置带注释类型的新实例（在这种情况下为Account）。 xyz.myapp.domain.Account）。由于bean的默认名称是其类型的全限定名，因此声明原型定义的便捷方法是省略id属性，如以下示例所示：

```xml
<bean class="com.xyz.myapp.domain.Account" scope="prototype">
    <property name="fundsTransferService" ref="fundsTransferService"/>
</bean>
```

If you want to explicitly specify the name of the prototype bean definition to use, you can do so directly in the annotation, as the following example shows:

如果要显式指定要使用的原型bean定义的名称，则可以直接在批注中这样做，如以下示例所示：

```java
package com.xyz.myapp.domain;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable("account")
public class Account {
    // ...
}
```

Spring now looks for a bean definition named `account` and uses that as the definition to configure new `Account` instances.

Spring现在寻找一个名为account的bean定义，并将其用作配置新Account实例的定义。

You can also use autowiring to avoid having to specify a dedicated bean definition at all. To have Spring apply autowiring, use the `autowire` property of the `@Configurable` annotation. You can specify either `@Configurable(autowire=Autowire.BY_TYPE)` or `@Configurable(autowire=Autowire.BY_NAME` for autowiring by type or by name, respectively. As an alternative, it is preferable to specify explicit, annotation-driven dependency injection for your `@Configurable` beans through `@Autowired` or `@Inject` at the field or method level (see [Annotation-based Container Configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config) for further details).

您也可以使用自动装配来避免完全指定专用的bean定义。要让Spring应用自动装配，请使用@Configurable批注的autowire属性。您可以指定@Configurable（autowire = Autowire.BY_TYPE）或@Configurable（autowire = Autowire.BY_NAME）分别按类型或名称进行自动装配。作为替代方案，最好为您的对象指定显式的，注释驱动的依赖项注入。通过@Autowired或@Inject在字段或方法级别进行@Configurable Bean（有关更多详细信息，请参见基于注释的容器配置）。

Finally, you can enable Spring dependency checking for the object references in the newly created and configured object by using the `dependencyCheck` attribute (for example, `@Configurable(autowire=Autowire.BY_NAME,dependencyCheck=true)`). If this attribute is set to `true`, Spring validates after configuration that all properties (which are not primitives or collections) have been set.

最后，您可以使用dependencyCheck属性（例如，@Configurable（autowire = Autowire.BY_NAME，dependencyCheck = true））为新创建和配置的对象中的对象引用启用Spring依赖项检查。如果此属性设置为true，则Spring在配置后验证是否已设置所有属性（不是基元或集合）。

Note that using the annotation on its own does nothing. It is the `AnnotationBeanConfigurerAspect` in `spring-aspects.jar` that acts on the presence of the annotation. In essence, the aspect says, “after returning from the initialization of a new object of a type annotated with `@Configurable`, configure the newly created object using Spring in accordance with the properties of the annotation”. In this context, “initialization” refers to newly instantiated objects (for example, objects instantiated with the `new` operator) as well as to `Serializable` objects that are undergoing deserialization (for example, through [readResolve()](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)).

请注意，单独使用注释不会执行任何操作。 spring-aspects.jar中的`AnnotationBeanConfigurerAspect`对注释的存在起作用。从本质上讲，该方面说：“从带有@Configurable注释的类型的新对象的初始化返回之后，使用Spring根据注释的属性配置新创建的对象”。在这种情况下，“初始化”是指新实例化的对象（例如，用new运算符实例化的对象）以及正在进行反序列化（例如，通过readResolve（）的可序列化的对象）。

|      | One of the key phrases in the above paragraph is “in essence”. For most cases, the exact semantics of “after returning from the initialization of a new object” are fine. In this context, “after initialization” means that the dependencies are injected after the object has been constructed. This means that the dependencies are not available for use in the constructor bodies of the class. If you want the dependencies to be injected before the constructor bodies run and thus be available for use in the body of the constructors, you need to define this on the `@Configurable` declaration, as follows:JavaKotlin`@Configurable(preConstruction = true)`You can find more information about the language semantics of the various pointcut types in AspectJ [in this appendix](https://www.eclipse.org/aspectj/doc/next/progguide/semantics-joinPoints.html) of the [AspectJ Programming Guide](https://www.eclipse.org/aspectj/doc/next/progguide/index.html). |
| ---- | ------------------------------------------------------------ |
|      | 上段中的关键短语之一是“本质上”。在大多数情况下，“从新对象的初始化返回后”的确切语义是可以的。在这种情况下，“初始化之后”是指在构造对象之后注入依赖项。这意味着该依赖项不可在类的构造函数体中使用。如果您希望在构造函数主体运行之前注入依赖项，从而可以在构造函数主体中使用这些依赖项，则需要在@Configurable声明中对此进行定义，如下所示：<br/><br/>JavaKotlin<br/>@Configurable（preConstruction = true）<br/>您可以在《 AspectJ编程指南》的此附录中找到有关各种切入点类型的语言语义的更多信息。 |

For this to work, the annotated types must be woven with the AspectJ weaver. You can either use a build-time Ant or Maven task to do this (see, for example, the [AspectJ Development Environment Guide](https://www.eclipse.org/aspectj/doc/released/devguide/antTasks.html)) or load-time weaving (see [Load-time Weaving with AspectJ in the Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw)). The `AnnotationBeanConfigurerAspect` itself needs to be configured by Spring (in order to obtain a reference to the bean factory that is to be used to configure new objects). If you use Java-based configuration, you can add `@EnableSpringConfigured` to any `@Configuration` class, as follows:

为此，必须将带注释的类型与AspectJ编织器编织在一起。您可以使用构建时的Ant或Maven任务来执行此操作（例如，请参阅《 AspectJ开发环境指南》），也可以使用加载时编织（请参见Spring Framework中的使用AspectJ进行加载时编织）。 Spring需要配置`AnnotationBeanConfigurerAspect`本身（以便获得对将用于配置新对象的bean工厂的引用）。如果使用基于Java的配置，则可以将`@EnableSpringConfigured`添加到任何`@Configuration`类中，如下所示：

```java
@Configuration
@EnableSpringConfigured
public class AppConfig {
}
```

If you prefer XML based configuration, the Spring [`context` namespace](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas-context) defines a convenient `context:spring-configured` element, which you can use as follows:

如果您更喜欢基于XML的配置，则Spring上下文名称空间定义了一个方便的`context：spring-configured`元素，您可以按以下方式使用它：

```xml
<context:spring-configured/>
```

Instances of `@Configurable` objects created before the aspect has been configured result in a message being issued to the debug log and no configuration of the object taking place. An example might be a bean in the Spring configuration that creates domain objects when it is initialized by Spring. In this case, you can use the `depends-on` bean attribute to manually specify that the bean depends on the configuration aspect. The following example shows how to use the `depends-on` attribute:

在配置方面之前创建的@Configurable对象实例会导致向调试日志发出消息，并且未进行对象配置。一个示例可能是Spring配置中的一个bean，当它由Spring初始化时会创建域对象。在这种情况下，您可以使用depends-on bean属性来手动指定bean取决于配置方面。下面的示例演示如何使用depends-on属性：

```xml
<bean id="myService"
        class="com.xzy.myapp.service.MyService"
        depends-on="org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect">

    <!-- ... -->

</bean>
```

|      | Do not activate `@Configurable` processing through the bean configurer aspect unless you really mean to rely on its semantics at runtime. In particular, make sure that you do not use `@Configurable` on bean classes that are registered as regular Spring beans with the container. Doing so results in double initialization, once through the container and once through the aspect. |
| ---- | ------------------------------------------------------------ |
|      | 除非您真的想在运行时依赖它的语义，否则不要通过bean配置器方面激活@Configurable处理。特别是，请确保不要在通过容器注册为常规Spring bean的bean类上使用@Configurable。这样做会导致两次初始化，一次是通过容器，一次是通过方面。 |

##### Unit Testing `@Configurable` Objects

单元测试@Configurable对象

One of the goals of the `@Configurable` support is to enable independent unit testing of domain objects without the difficulties associated with hard-coded lookups. If `@Configurable` types have not been woven by AspectJ, the annotation has no affect during unit testing. You can set mock or stub property references in the object under test and proceed as normal. If `@Configurable` types have been woven by AspectJ, you can still unit test outside of the container as normal, but you see a warning message each time that you construct a `@Configurable` object indicating that it has not been configured by Spring.

@Configurable支持的目标之一是实现域对象的独立单元测试，而不会遇到与硬编码查找相关的困难。如果AspectJ尚未编织@Configurable类型，则注释在单元测试期间不起作用。您可以在被测对象中设置模拟或存根属性引用，然后照常进行。如果AspectJ编织了@Configurable类型，您仍然可以像往常一样在容器外部进行单元测试，但是每次构造@Configurable对象时，您都会看到一条警告消息，指示该对象尚未由Spring配置。

##### Working with Multiple Application Contexts

使用多个应用程序上下文

The `AnnotationBeanConfigurerAspect` that is used to implement the `@Configurable` support is an AspectJ singleton aspect. The scope of a singleton aspect is the same as the scope of `static` members: There is one aspect instance per classloader that defines the type. This means that, if you define multiple application contexts within the same classloader hierarchy, you need to consider where to define the `@EnableSpringConfigured` bean and where to place `spring-aspects.jar` on the classpath.

用于实现@Configurable支持的AnnotationBeanConfigurerAspect是AspectJ单例方面。单例方面的范围与静态成员的范围相同：每个类加载器都有一个方面实例来定义类型。这意味着，如果您在同一个类加载器层次结构中定义多个应用程序上下文，则需要考虑在何处定义@EnableSpringConfigured bean以及在类路径中放置spring-aspects.jar的位置。

Consider a typical Spring web application configuration that has a shared parent application context that defines common business services, everything needed to support those services, and one child application context for each servlet (which contains definitions particular to that servlet). All of these contexts co-exist within the same classloader hierarchy, and so the `AnnotationBeanConfigurerAspect` can hold a reference to only one of them. In this case, we recommend defining the `@EnableSpringConfigured` bean in the shared (parent) application context. This defines the services that you are likely to want to inject into domain objects. A consequence is that you cannot configure domain objects with references to beans defined in the child (servlet-specific) contexts by using the @Configurable mechanism (which is probably not something you want to do anyway).

考虑一个典型的Spring Web应用程序配置，该配置具有一个共享的父应用程序上下文，该上下文定义了通用的业务服务，支持那些服务所需的一切，以及每个Servlet的一个子应用程序上下文（其中包含该Servlet的特定定义）。所有这些上下文共存于同一类加载器层次结构中，因此`AnnotationBeanConfigurerAspect`只能保存对其中一个的引用。在这种情况下，我们建议在共享（父）应用程序上下文中定义@EnableSpringConfigured bean。这定义了您可能想注入域对象的服务。结果是，您无法使用@Configurable机制来配置域对象，而该域对象将引用在子（特定于servlet的）上下文中定义的bean的引用（无论如何，这都不是您想做的事情）。

When deploying multiple web applications within the same container, ensure that each web application loads the types in `spring-aspects.jar` by using its own classloader (for example, by placing `spring-aspects.jar` in `'WEB-INF/lib'`). If `spring-aspects.jar` is added only to the container-wide classpath (and hence loaded by the shared parent classloader), all web applications share the same aspect instance (which is probably not what you want).

在同一容器中部署多个Web应用程序时，请确保每个Web应用程序通过使用其自己的类加载器（例如，将spring-aspects.jar放置在“ WEB-INF / lib”中）在spring-aspects.jar中加载类型。如果将spring-aspects.jar仅添加到容器级的类路径中（并因此由共享的父类加载器加载），则所有Web应用程序都共享相同的方面实例（这可能不是您想要的）。

#### 5.10.2. Other Spring aspects for AspectJ

AspectJ的其他Spring方面

In addition to the `@Configurable` aspect, `spring-aspects.jar` contains an AspectJ aspect that you can use to drive Spring’s transaction management for types and methods annotated with the `@Transactional` annotation. This is primarily intended for users who want to use the Spring Framework’s transaction support outside of the Spring container.

除了@Configurable方面之外，spring-aspects.jar包含一个AspectJ方面，您可以使用它来驱动Spring的事务管理，该事务管理使用@Transactional注释进行注释的类型和方法。这主要适用于希望在Spring容器之外使用Spring Framework的事务支持的用户。

The aspect that interprets `@Transactional` annotations is the `AnnotationTransactionAspect`. When you use this aspect, you must annotate the implementation class (or methods within that class or both), not the interface (if any) that the class implements. AspectJ follows Java’s rule that annotations on interfaces are not inherited.

解释@Transactional批注的方面是`AnnotationTransactionAspect`。使用此方面时，必须注释实现类（或该类中的方法，或两者），而不是注释该类所实现的接口（如果有）。 AspectJ遵循Java的规则，即不继承接口上的注释。

A `@Transactional` annotation on a class specifies the default transaction semantics for the execution of any public operation in the class.

类上的@Transactional批注指定用于执行该类中任何公共操作的默认事务语义。

A `@Transactional` annotation on a method within the class overrides the default transaction semantics given by the class annotation (if present). Methods of any visibility may be annotated, including private methods. Annotating non-public methods directly is the only way to get transaction demarcation for the execution of such methods.

类中方法上的@Transactional注释会覆盖类注释（如果存在）给出的默认事务语义。可以注释任何可见性的方法，包括私有方法。直接注释非公共方法是执行此类方法而获得事务划分的唯一方法。

|      | Since Spring Framework 4.2, `spring-aspects` provides a similar aspect that offers the exact same features for the standard `javax.transaction.Transactional` annotation. Check `JtaAnnotationTransactionAspect` for more details. |
| ---- | ------------------------------------------------------------ |
|      | 从Spring Framework 4.2开始，spring-aspects提供了一个相似的方面，为标准javax.transaction.Transactional注释提供了完全相同的功能。检查JtaAnnotationTransactionAspect了解更多详细信息。 |

For AspectJ programmers who want to use the Spring configuration and transaction management support but do not want to (or cannot) use annotations, `spring-aspects.jar` also contains `abstract` aspects you can extend to provide your own pointcut definitions. See the sources for the `AbstractBeanConfigurerAspect` and `AbstractTransactionAspect` aspects for more information. As an example, the following excerpt shows how you could write an aspect to configure all instances of objects defined in the domain model by using prototype bean definitions that match the fully qualified class names:

对于希望使用Spring配置和事务管理支持但又不想（或不能）使用注释的AspectJ程序员，spring-aspects.jar还包含抽象方面，您可以扩展它们以提供自己的切入点定义。有关更多信息，请参见AbstractBeanConfigurerAspect和AbstractTransactionAspect的来源。作为示例，以下摘录显示了如何编写方面来使用与完全限定的类名匹配的原型Bean定义来配置域模型中定义的对象的所有实例：

```java
public aspect DomainObjectConfiguration extends AbstractBeanConfigurerAspect {

    public DomainObjectConfiguration() {
        setBeanWiringInfoResolver(new ClassNameBeanWiringInfoResolver());
    }

    // the creation of a new bean (any object in the domain model)
    protected pointcut beanCreation(Object beanInstance) :
        initialization(new(..)) &&
        CommonPointcuts.inDomainModel() &&
        this(beanInstance);
}
```

#### 5.10.3. Configuring AspectJ Aspects by Using Spring IoC

使用Spring IoC配置AspectJ Aspects

When you use AspectJ aspects with Spring applications, it is natural to both want and expect to be able to configure such aspects with Spring. The AspectJ runtime itself is responsible for aspect creation, and the means of configuring the AspectJ-created aspects through Spring depends on the AspectJ instantiation model (the `per-xxx` clause) used by the aspect.

当您将AspectJ方面与Spring应用程序一起使用时，既自然又希望能够使用Spring配置这些方面。 AspectJ运行时本身负责方面的创建，通过Spring配置AspectJ创建的方面的方法取决于方面使用的AspectJ实例化模型（per-xxx子句）。

The majority of AspectJ aspects are singleton aspects. Configuration of these aspects is easy. You can create a bean definition that references the aspect type as normal and include the `factory-method="aspectOf"` bean attribute. This ensures that Spring obtains the aspect instance by asking AspectJ for it rather than trying to create an instance itself. The following example shows how to use the `factory-method="aspectOf"` attribute:

AspectJ的大多数方面都是单例方面。这些方面的配置很容易。您可以创建一个bean定义，该bean定义按常规引用方面类型，并包括factory-method =“ aspectOf” bean属性。这可以确保Spring通过向AspectJ索要长宽比实例，而不是尝试自己创建实例来获得长宽比实例。以下示例显示如何使用factory-method =“ aspectOf”属性：

```xml
<bean id="profiler" class="com.xyz.profiler.Profiler"
        factory-method="aspectOf"> 

    <property name="profilingStrategy" ref="jamonProfilingStrategy"/>
</bean>
```

|      | Note the `factory-method="aspectOf"` attribute |
| ---- | ---------------------------------------------- |
|      | 注意factory-method =“ aspectOf”属性            |

Non-singleton aspects are harder to configure. However, it is possible to do so by creating prototype bean definitions and using the `@Configurable` support from `spring-aspects.jar` to configure the aspect instances once they have bean created by the AspectJ runtime.

非单一方面更难配置。但是，可以通过创建原型Bean定义并使用spring-aspects.jar中的@Configurable支持来实现，一旦它们由AspectJ运行时创建了Bean，就可以配置方面实例。

If you have some @AspectJ aspects that you want to weave with AspectJ (for example, using load-time weaving for domain model types) and other @AspectJ aspects that you want to use with Spring AOP, and these aspects are all configured in Spring, you need to tell the Spring AOP @AspectJ auto-proxying support which exact subset of the @AspectJ aspects defined in the configuration should be used for auto-proxying. You can do this by using one or more `<include/>` elements inside the `<aop:aspectj-autoproxy/>` declaration. Each `<include/>` element specifies a name pattern, and only beans with names matched by at least one of the patterns are used for Spring AOP auto-proxy configuration. The following example shows how to use `<include/>` elements:

如果您有一些要与AspectJ编织的@AspectJ方面（例如，对域模型类型使用加载时编织）以及要与Spring AOP一起使用的其他@AspectJ方面，那么这些方面都已在Spring中配置，您需要告诉Spring AOP @AspectJ自动代理支持，应使用配置中定义的@AspectJ方面的确切子集进行自动代理。您可以通过在<aop：aspectj-autoproxy />声明中使用一个或多个<include />元素来执行此操作。每个<include />元素都指定一个名称模式，并且只有名称与至少一种模式匹配的bean才可用于Spring AOP自动代理配置。以下示例显示了如何使用<include />元素：

```xml
<aop:aspectj-autoproxy>
    <aop:include name="thisBean"/>
    <aop:include name="thatBean"/>
</aop:aspectj-autoproxy>
```

|      | Do not be misled by the name of the `<aop:aspectj-autoproxy/>` element. Using it results in the creation of Spring AOP proxies. The @AspectJ style of aspect declaration is being used here, but the AspectJ runtime is not involved. |
| ---- | ------------------------------------------------------------ |
|      | 不要被<aop：aspectj-autoproxy />元素的名称所迷惑。使用它可以创建Spring AOP代理。这里使用了@AspectJ样式的声明，但是不涉及AspectJ运行时。<br/>5.10.4。在Spring Framework中使用AspectJ进行加载时编织<br/>加载时编织（LTW）是指将AspectJ方面加载到应用程序的类文件中时将其编织到Java虚拟机（JVM）中的过程。本节的重点是在Spring框架的特定上下文中配置和使用LTW。本节不是LTW的一般介绍。有关LTW的详细信息以及仅使用AspectJ配置LTW（完全不涉及Spring）的详细信息，请参阅《 AspectJ开发环境指南》的LTW部分。 |

#### 5.10.4. Load-time Weaving with AspectJ in the Spring Framework

在Spring Framework中使用AspectJ进行加载时编织

Load-time weaving (LTW) refers to the process of weaving AspectJ aspects into an application’s class files as they are being loaded into the Java virtual machine (JVM). The focus of this section is on configuring and using LTW in the specific context of the Spring Framework. This section is not a general introduction to LTW. For full details on the specifics of LTW and configuring LTW with only AspectJ (with Spring not being involved at all), see the [LTW section of the AspectJ Development Environment Guide](https://www.eclipse.org/aspectj/doc/released/devguide/ltw.html).

加载时编织（LTW）是指将AspectJ方面加载到应用程序的类文件中时将其编织到Java虚拟机（JVM）中的过程。本节的重点是在Spring框架的特定上下文中配置和使用LTW。本节不是LTW的一般介绍。有关LTW的详细信息以及仅使用AspectJ配置LTW（完全不涉及Spring）的详细信息，请参阅《 AspectJ开发环境指南》的LTW部分。

The value that the Spring Framework brings to AspectJ LTW is in enabling much finer-grained control over the weaving process. 'Vanilla' AspectJ LTW is effected by using a Java (5+) agent, which is switched on by specifying a VM argument when starting up a JVM. It is, thus, a JVM-wide setting, which may be fine in some situations but is often a little too coarse. Spring-enabled LTW lets you switch on LTW on a per-`ClassLoader` basis, which is more fine-grained and which can make more sense in a 'single-JVM-multiple-application' environment (such as is found in a typical application server environment).

Spring框架为AspectJ LTW带来的价值在于能够对编织过程进行更精细的控制。 “ Vanilla” AspectJ LTW是通过使用Java（5+）代理实现的，该代理在启动JVM时通过指定VM参数来打开。因此，它是一个JVM范围的设置，在某些情况下可能很好，但通常有点过于粗糙。启用Spring的LTW可让您基于每个ClassLoader开启LTW，它的粒度更细，并且在“单个JVM-多个应用程序”环境中（例如在典型的应用程序服务器中可以找到）更有意义。环境）。

Further, [in certain environments](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw-environments), this support enables load-time weaving without making any modifications to the application server’s launch script that is needed to add `-javaagent:path/to/aspectjweaver.jar` or (as we describe later in this section) `-javaagent:path/to/spring-instrument.jar`. Developers configure the application context to enable load-time weaving instead of relying on administrators who typically are in charge of the deployment configuration, such as the launch script.

此外，在某些环境中，此支持无需在添加-javaagent：path / to / aspectjweaver.jar或（如本节后面所述）-javaagent所需的应用程序服务器的启动脚本进行任何修改的情况下即可进行加载时编织。 ：path / to / spring-instrument.jar。开发人员将应用程序上下文配置为启用加载时编织，而不是依赖通常负责部署配置（例如启动脚本）的管理员。

Now that the sales pitch is over, let us first walk through a quick example of AspectJ LTW that uses Spring, followed by detailed specifics about elements introduced in the example. For a complete example, see the [Petclinic sample application](https://github.com/spring-projects/spring-petclinic).

现在，销售工作已经结束，让我们首先浏览一个使用Spring的AspectJ LTW的快速示例，然后详细介绍示例中引入的元素。有关完整的示例，请参见Petclinic示例应用程序。

##### A First Example

Assume that you are an application developer who has been tasked with diagnosing the cause of some performance problems in a system. Rather than break out a profiling tool, we are going to switch on a simple profiling aspect that lets us quickly get some performance metrics. We can then apply a finer-grained profiling tool to that specific area immediately afterwards.

假设您是一位负责诊断系统中某些性能问题的原因的应用程序开发人员。我们将打开一个简单的配置方面，而不是使用配置文件工具，让我们快速获取一些性能指标。然后，我们可以立即在该特定区域应用更细粒度的分析工具。

|      | The example presented here uses XML configuration. You can also configure and use @AspectJ with [Java configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java). Specifically, you can use the `@EnableLoadTimeWeaving` annotation as an alternative to `<context:load-time-weaver/>` (see [below](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw-spring) for details). |
| ---- | ------------------------------------------------------------ |
|      | 此处提供的示例使用XML配置。您还可以配置@AspectJ并将其与Java配置一起使用。具体来说，您可以使用@EnableLoadTimeWeaving批注代替<context：load-time-weaver />（有关详细信息，请参见下文）。 |

The following example shows the profiling aspect, which is not fancy. It is a time-based profiler that uses the @AspectJ-style of aspect declaration:

下面的示例显示了配置方面的信息，这并不理想。这是一个基于时间的探查器，它使用@AspectJ样式的方面声明：



```java
package foo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;
import org.springframework.core.annotation.Order;

@Aspect
public class ProfilingAspect {

    @Around("methodsToBeProfiled()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try {
            sw.start(pjp.getSignature().getName());
            return pjp.proceed();
        } finally {
            sw.stop();
            System.out.println(sw.prettyPrint());
        }
    }

    @Pointcut("execution(public * foo..*.*(..))")
    public void methodsToBeProfiled(){}
}
```

We also need to create an `META-INF/aop.xml` file, to inform the AspectJ weaver that we want to weave our `ProfilingAspect` into our classes. This file convention, namely the presence of a file (or files) on the Java classpath called `META-INF/aop.xml` is standard AspectJ. The following example shows the `aop.xml` file:

我们还需要创建一个META-INF / aop.xml文件，以通知AspectJ编织者我们希望将ProfilingAspect编织到类中。此文件约定，即在Java类路径上称为META-INF / aop.xml的一个或多个文件的存在，是标准的AspectJ。以下示例显示aop.xml文件：

```xml
<!DOCTYPE aspectj PUBLIC "-//AspectJ//DTD//EN" "https://www.eclipse.org/aspectj/dtd/aspectj.dtd">
<aspectj>

    <weaver>
        <!-- only weave classes in our application-specific packages -->
        <include within="foo.*"/>
    </weaver>

    <aspects>
        <!-- weave in just this aspect -->
        <aspect name="foo.ProfilingAspect"/>
    </aspects>

</aspectj>
```

Now we can move on to the Spring-specific portion of the configuration. We need to configure a `LoadTimeWeaver` (explained later). This load-time weaver is the essential component responsible for weaving the aspect configuration in one or more `META-INF/aop.xml` files into the classes in your application. The good thing is that it does not require a lot of configuration (there are some more options that you can specify, but these are detailed later), as can be seen in the following example:

现在，我们可以继续进行配置中特定于Spring的部分。我们需要配置一个LoadTimeWeaver（稍后说明）。该加载时织布器是必不可少的组件，负责将一个或多个META-INF / aop.xml文件中的方面配置编织到应用程序的类中。好处是，它不需要很多配置（您可以指定一些其他选项，但是稍后会详细介绍），如以下示例所示：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- a service object; we will be profiling its methods -->
    <bean id="entitlementCalculationService"
            class="foo.StubEntitlementCalculationService"/>

    <!-- this switches on the load-time weaving -->
    <context:load-time-weaver/>
</beans>
```

Now that all the required artifacts (the aspect, the `META-INF/aop.xml` file, and the Spring configuration) are in place, we can create the following driver class with a `main(..)` method to demonstrate the LTW in action:

现在，所有必需的工件（方面，META-INF / aop.xml文件和Spring配置）均已就绪，我们可以使用main（..）方法创建以下驱动程序类，以演示LTW的实际作用：

```java
package foo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml", Main.class);

        EntitlementCalculationService entitlementCalculationService =
                (EntitlementCalculationService) ctx.getBean("entitlementCalculationService");

        // the profiling aspect is 'woven' around this method execution
        entitlementCalculationService.calculateEntitlement();
    }
}
```

We have one last thing to do. The introduction to this section did say that one could switch on LTW selectively on a per-`ClassLoader` basis with Spring, and this is true. However, for this example, we use a Java agent (supplied with Spring) to switch on LTW. We use the following command to run the `Main` class shown earlier:

我们还有最后一件事要做。本节的引言确实说过，可以使用Spring在每个ClassLoader的基础上选择性地打开LTW，这是事实。但是，对于此示例，我们使用Java代理（Spring随附）打开LTW。我们使用以下命令来运行前面显示的Main类：

```
java -javaagent:C:/projects/foo/lib/global/spring-instrument.jar foo.Main
```

The `-javaagent` is a flag for specifying and enabling [agents to instrument programs that run on the JVM](https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html). The Spring Framework ships with such an agent, the `InstrumentationSavingAgent`, which is packaged in the `spring-instrument.jar` that was supplied as the value of the `-javaagent` argument in the preceding example.

-javaagent是一个标志，用于指定和启用代理以对在JVM上运行的程序进行检测。 Spring框架附带有这样的代理工具InstrumentationSavingAgent，该代理文件打包在spring-instrument.jar中，在上一示例中作为-javaagent参数的值提供。

The output from the execution of the `Main` program looks something like the next example. (I have introduced a `Thread.sleep(..)` statement into the `calculateEntitlement()` implementation so that the profiler actually captures something other than 0 milliseconds (the `01234` milliseconds is not an overhead introduced by the AOP). The following listing shows the output we got when we ran our profiler:

执行主程序的输出类似于下一个示例。 （我已经在calculateEntitlement（）实现中引入了Thread.sleep（..）语句，以便探查器实际捕获的不是0毫秒（01234毫秒不是AOP引入的开销）。下面的清单显示了输出我们在运行分析器时得到了：

```
Calculating entitlement

StopWatch 'ProfilingAspect': running time (millis) = 1234
------ ----- ----------------------------
ms     %     Task name
------ ----- ----------------------------
01234  100%  calculateEntitlement
```

Since this LTW is effected by using full-blown AspectJ, we are not limited only to advising Spring beans. The following slight variation on the `Main` program yields the same result:

J由于此LTW是通过使用成熟的AspectJ来实现的，因此我们不仅限于建议Spring Bean。在Main程序上进行以下细微改动会产生相同的结果：

```java
package foo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml", Main.class);

        EntitlementCalculationService entitlementCalculationService =
                new StubEntitlementCalculationService();

        // the profiling aspect will be 'woven' around this method execution
        entitlementCalculationService.calculateEntitlement();
    }
}
```

Notice how, in the preceding program, we bootstrap the Spring container and then create a new instance of the `StubEntitlementCalculationService` totally outside the context of Spring. The profiling advice still gets woven in.

请注意，在前面的程序中，我们如何引导Spring容器，然后完全在Spring上下文之外创建StubEntitlementCalculationService的新实例。剖析建议仍会被应用。

Admittedly, the example is simplistic. However, the basics of the LTW support in Spring have all been introduced in the earlier example, and the rest of this section explains the “why” behind each bit of configuration and usage in detail.

诚然，这个例子很简单。但是，在前面的示例中已经介绍了Spring中LTW支持的基础知识，本节的其余部分详细解释了每一位配置和用法背后的“原因”。

|      | The `ProfilingAspect` used in this example may be basic, but it is quite useful. It is a nice example of a development-time aspect that developers can use during development and then easily exclude from builds of the application being deployed into UAT or production. |
| ---- | ------------------------------------------------------------ |
|      | 在此示例中使用的ProfilingAspect可能是基本的，但它非常有用。这是开发时方面的一个很好的示例，开发人员可以在开发过程中使用它，然后轻松地将其从部署到UAT或生产中的应用程序构建中排除。 |

##### Aspects

The aspects that you use in LTW have to be AspectJ aspects. You can write them in either the AspectJ language itself, or you can write your aspects in the @AspectJ-style. Your aspects are then both valid AspectJ and Spring AOP aspects. Furthermore, the compiled aspect classes need to be available on the classpath.

您在LTW中使用的方面必须是AspectJ方面。您可以使用AspectJ语言本身来编写它们，也可以使用@AspectJ风格来编写方面。这样，您的方面就是有效的AspectJ和Spring AOP方面。此外，编译的方面类需要在类路径上可用。

##### 'META-INF/aop.xml'

The AspectJ LTW infrastructure is configured by using one or more `META-INF/aop.xml` files that are on the Java classpath (either directly or, more typically, in jar files).

通过使用Java类路径上的一个或多个META-INF / aop.xml文件（直接或通常在jar文件中）来配置AspectJ LTW基础结构。

The structure and contents of this file is detailed in the LTW part of the [AspectJ reference documentation](https://www.eclipse.org/aspectj/doc/released/devguide/ltw-configuration.html). Because the `aop.xml` file is 100% AspectJ, we do not describe it further here.

该文件的结构和内容在AspectJ参考文档的LTW部分中进行了详细说明。由于aop.xml文件是100％AspectJ，因此在此不再赘述。

##### Required libraries (JARS)

At minimum, you need the following libraries to use the Spring Framework’s support for AspectJ LTW:

所需的库（JARS）
至少，您需要以下库来使用Spring Framework对AspectJ LTW的支持：

- `spring-aop.jar`
- `aspectjweaver.jar`

If you use the [Spring-provided agent to enable instrumentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-aj-ltw-environments-generic), you also need:

如果您使用Spring提供的代理来启用检测，则还需要：

- `spring-instrument.jar`

##### Spring Configuration

The key component in Spring’s LTW support is the `LoadTimeWeaver` interface (in the `org.springframework.instrument.classloading` package), and the numerous implementations of it that ship with the Spring distribution. A `LoadTimeWeaver` is responsible for adding one or more `java.lang.instrument.ClassFileTransformers` to a `ClassLoader` at runtime, which opens the door to all manner of interesting applications, one of which happens to be the LTW of aspects.

弹簧配置
Spring的LTW支持中的关键组件是LoadTimeWeaver接口（在org.springframework.instrument.classloading包中），以及Spring发行版附带的众多实现。 LoadTimeWeaver负责在运行时将一个或多个java.lang.instrument.ClassFileTransformers添加到ClassLoader，这为各种有趣的应用程序打开了大门，其中之一就是方面的LTW。

|      | If you are unfamiliar with the idea of runtime class file transformation, see the javadoc API documentation for the `java.lang.instrument` package before continuing. While that documentation is not comprehensive, at least you can see the key interfaces and classes (for reference as you read through this section). |
| ---- | ------------------------------------------------------------ |
|      | 如果您不熟悉运行时类文件转换的概念，请在继续操作之前参阅java.lang.instrument软件包的javadoc API文档。虽然该文档并不全面，但是至少您可以看到关键的接口和类（在您阅读本节时作为参考）。 |

Configuring a `LoadTimeWeaver` for a particular `ApplicationContext` can be as easy as adding one line. (Note that you almost certainly need to use an `ApplicationContext` as your Spring container — typically, a `BeanFactory` is not enough because the LTW support uses `BeanFactoryPostProcessors`.)

为特定的ApplicationContext配置LoadTimeWeaver就像添加一行一样容易。 （请注意，您几乎肯定需要将ApplicationContext用作Spring容器？-通常，仅BeanFactory是不够的，因为LTW支持使用BeanFactoryPostProcessors。）

To enable the Spring Framework’s LTW support, you need to configure a `LoadTimeWeaver`, which typically is done by using the `@EnableLoadTimeWeaving` annotation, as follows:

为了启用Spring Framework的LTW支持，您需要配置一个LoadTimeWeaver，通常通过使用@EnableLoadTimeWeaving批注来完成，如下所示：

```java
@Configuration
@EnableLoadTimeWeaving
public class AppConfig {
}
```

Alternatively, if you prefer XML-based configuration, use the `<context:load-time-weaver/>` element. Note that the element is defined in the `context` namespace. The following example shows how to use `<context:load-time-weaver/>`:

另外，如果您喜欢基于XML的配置，请使用<context：load-time-weaver />元素。注意，该元素在上下文名称空间中定义。以下示例显示了如何使用<context：load-time-weaver />：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:load-time-weaver/>

</beans>
```

The preceding configuration automatically defines and registers a number of LTW-specific infrastructure beans, such as a `LoadTimeWeaver` and an `AspectJWeavingEnabler`, for you. The default `LoadTimeWeaver` is the `DefaultContextLoadTimeWeaver` class, which attempts to decorate an automatically detected `LoadTimeWeaver`. The exact type of `LoadTimeWeaver` that is “automatically detected” is dependent upon your runtime environment. The following table summarizes various `LoadTimeWeaver` implementations:

前面的配置会自动为您定义并注册许多LTW特定的基础结构Bean，例如LoadTimeWeaver和AspectJWeavingEnabler。默认的LoadTimeWeaver是DefaultContextLoadTimeWeaver类，它试图装饰自动检测到的LoadTimeWeaver。 “自动检测到”的LoadTimeWeaver的确切类型取决于您的运行时环境。下表总结了各种LoadTimeWeaver实现：

| Runtime Environment                                          | `LoadTimeWeaver` implementation |
| :----------------------------------------------------------- | :------------------------------ |
| Running in [Apache Tomcat](https://tomcat.apache.org/)       | `TomcatLoadTimeWeaver`          |
| Running in [GlassFish](https://eclipse-ee4j.github.io/glassfish/) (limited to EAR deployments) | `GlassFishLoadTimeWeaver`       |
| Running in Red Hat’s [JBoss AS](https://www.jboss.org/jbossas/) or [WildFly](https://www.wildfly.org/) | `JBossLoadTimeWeaver`           |
| Running in IBM’s [WebSphere](https://www-01.ibm.com/software/webservers/appserv/was/) | `WebSphereLoadTimeWeaver`       |
| Running in Oracle’s [WebLogic](https://www.oracle.com/technetwork/middleware/weblogic/overview/index-085209.html) | `WebLogicLoadTimeWeaver`        |
| JVM started with Spring `InstrumentationSavingAgent` (`java -javaagent:path/to/spring-instrument.jar`) | `InstrumentationLoadTimeWeaver` |
| Fallback, expecting the underlying ClassLoader to follow common conventions (namely `addTransformer` and optionally a `getThrowawayClassLoader` method) | `ReflectiveLoadTimeWeaver`      |

Note that the table lists only the `LoadTimeWeavers` that are autodetected when you use the `DefaultContextLoadTimeWeaver`. You can specify exactly which `LoadTimeWeaver` implementation to use.

请注意，该表仅列出使用DefaultContextLoadTimeWeaver时自动检测到的LoadTimeWeaver。您可以确切指定要使用的LoadTimeWeaver实现。

To specify a specific `LoadTimeWeaver` with Java configuration, implement the `LoadTimeWeavingConfigurer` interface and override the `getLoadTimeWeaver()` method. The following example specifies a `ReflectiveLoadTimeWeaver`:

要使用Java配置指定特定的LoadTimeWeaver，请实现LoadTimeWeavingConfigurer接口并重写getLoadTimeWeaver（）方法。以下示例指定了ReflectiveLoadTimeWeaver：

```java
@Configuration
@EnableLoadTimeWeaving
public class AppConfig implements LoadTimeWeavingConfigurer {

    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new ReflectiveLoadTimeWeaver();
    }
}
```

If you use XML-based configuration, you can specify the fully qualified classname as the value of the `weaver-class` attribute on the `<context:load-time-weaver/>` element. Again, the following example specifies a `ReflectiveLoadTimeWeaver`:

如果使用基于XML的配置，则可以在<context：load-time-weaver />元素上将完全限定的类名指定为weaver-class属性的值。同样，以下示例指定了ReflectiveLoadTimeWeaver：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:load-time-weaver
            weaver-class="org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver"/>

</beans>
```

The `LoadTimeWeaver` that is defined and registered by the configuration can be later retrieved from the Spring container by using the well known name, `loadTimeWeaver`. Remember that the `LoadTimeWeaver` exists only as a mechanism for Spring’s LTW infrastructure to add one or more `ClassFileTransformers`. The actual `ClassFileTransformer` that does the LTW is the `ClassPreProcessorAgentAdapter` (from the `org.aspectj.weaver.loadtime` package) class. See the class-level javadoc of the `ClassPreProcessorAgentAdapter` class for further details, because the specifics of how the weaving is actually effected is beyond the scope of this document.

稍后可以使用众所周知的名称loadTimeWeaver从Spring容器中检索由配置定义和注册的LoadTimeWeaver。请记住，LoadTimeWeaver仅作为Spring LTW基础结构添加一个或多个ClassFileTransformers的机制而存在。执行LTW的实际ClassFileTransformer是ClassPreProcessorAgentAdapter（来自org.aspectj.weaver.loadtime包）类。有关更多详细信息，请参见ClassPreProcessorAgentAdapter类的类级javadoc，因为实际上如何实现编织的细节不在本文档的讨论范围之内。

There is one final attribute of the configuration left to discuss: the `aspectjWeaving` attribute (or `aspectj-weaving` if you use XML). This attribute controls whether LTW is enabled or not. It accepts one of three possible values, with the default value being `autodetect` if the attribute is not present. The following table summarizes the three possible values:

还需要讨论配置的最后一个属性：aspectjWeaving属性（如果使用XML，则为Aspectj-weaving）。此属性控制是否启用LTW。它接受三个可能的值之一，如果不存在该属性，则默认值为自动检测。下表总结了三个可能的值：

| Annotation Value | XML Value    | Explanation                                                  |
| :--------------- | :----------- | :----------------------------------------------------------- |
| `ENABLED`        | `on`         | AspectJ weaving is on, and aspects are woven at load-time as appropriate. |
| `DISABLED`       | `off`        | LTW is off. No aspect is woven at load-time.                 |
| `AUTODETECT`     | `autodetect` | If the Spring LTW infrastructure can find at least one `META-INF/aop.xml` file, then AspectJ weaving is on. Otherwise, it is off. This is the default value. |

##### Environment-specific Configuration

特定于环境的配置

This last section contains any additional settings and configuration that you need when you use Spring’s LTW support in environments such as application servers and web containers.

最后一部分包含在应用程序服务器和Web容器等环境中使用Spring的LTW支持时所需的任何其他设置和配置。

###### Tomcat, JBoss, WebSphere, WebLogic

Tomcat, JBoss/WildFly, IBM WebSphere Application Server and Oracle WebLogic Server all provide a general app `ClassLoader` that is capable of local instrumentation. Spring’s native LTW may leverage those ClassLoader implementations to provide AspectJ weaving. You can simply enable load-time weaving, as [described earlier](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-using-aspectj). Specifically, you do not need to modify the JVM launch script to add `-javaagent:path/to/spring-instrument.jar`.

Tomcat，JBoss / WildFly，IBM WebSphere Application Server和Oracle WebLogic Server都提供了能够在本地进行检测的通用应用程序ClassLoader。 Spring的本地LTW可以利用这些ClassLoader实现来提供AspectJ编织。如前所述，您可以简单地启用加载时编织。具体来说，您无需修改JVM启动脚本即可添加-javaagent：path / to / spring-instrument.jar。

Note that on JBoss, you may need to disable the app server scanning to prevent it from loading the classes before the application actually starts. A quick workaround is to add to your artifact a file named `WEB-INF/jboss-scanning.xml` with the following content:

请注意，在JBoss上，您可能需要禁用应用程序服务器扫描，以防止它在应用程序实际启动之前加载类。一个快速的解决方法是将一个名为WEB-INF / jboss-scanning.xml的文件添加到您的工件中，其中包含以下内容：

```xml
<scanning xmlns="urn:jboss:scanning:1.0"/>
```

###### Generic Java Applications

When class instrumentation is required in environments that are not supported by specific `LoadTimeWeaver` implementations, a JVM agent is the general solution. For such cases, Spring provides `InstrumentationLoadTimeWeaver` which requires a Spring-specific (but very general) JVM agent, `spring-instrument.jar`, autodetected by common `@EnableLoadTimeWeaving` and `<context:load-time-weaver/>` setups.

通用Java应用程序
如果特定LoadTimeWeaver实现不支持的环境中需要类检测，则JVM代理是通用解决方案。对于这种情况，Spring提供了InstrumentationLoadTimeWeaver，它需要特定于Spring（但非常通用）的JVM代理spring-instrument.jar，并由常见的@EnableLoadTimeWeaving和<context：load-time-weaver />设置自动检测到。

To use it, you must start the virtual machine with the Spring agent by supplying the following JVM options:

要使用它，必须通过提供以下JVM选项来使用Spring代理启动虚拟机：

```
-javaagent:/path/to/spring-instrument.jar
```

Note that this requires modification of the JVM launch script, which may prevent you from using this in application server environments (depending on your server and your operation policies). That said, for one-app-per-JVM deployments such as standalone Spring Boot applications, you typically control the entire JVM setup in any case.

请注意，这需要修改JVM启动脚本，这可能会阻止您在应用程序服务器环境中使用它（取决于您的服务器和您的操作策略）。也就是说，对于每个JVM一个应用程序的部署（例如独立的Spring Boot应用程序），无论如何，您通常都可以控制整个JVM的设置。

### 5.11. Further Resources

More information on AspectJ can be found on the [AspectJ website](https://www.eclipse.org/aspectj).

*Eclipse AspectJ* by Adrian Colyer et. al. (Addison-Wesley, 2005) provides a comprehensive introduction and reference for the AspectJ language.

*AspectJ in Action*, Second Edition by Ramnivas Laddad (Manning, 2009) comes highly recommended. The focus of the book is on AspectJ, but a lot of general AOP themes are explored (in some depth).

可以在AspectJ网站上找到有关AspectJ的更多信息。

Eclipse AspectJ，作者：Adrian Colyer等。等（Addison-Wesley，2005年）为AspectJ语言提供了全面的介绍和参考。

强烈推荐Ramnivas Laddad撰写的《 AspectJ in Action》第二版（Manning，2009年）。

### 6.Spring AOP APIs springaop应用程序接口

### 7.Null-safety 安全零位

###  9.Logging 登录中

### 10.Appendix 附录