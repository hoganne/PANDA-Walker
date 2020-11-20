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

Java

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

##### 1.3.2. Instantiating Beans

实例化bean

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

ref元素是<constructor arg/>或<property/>定义元素中的最后一个元素。在这里，您将bean的指定属性的值设置为对容器管理的另一个bean（协作者）的引用。被引用的bean是要设置其属性的bean的依赖项，并且在设置属性之前根据需要对其进行初始化。（如果collaborator是一个单例bean，那么它可能已经被容器初始化了。）所有引用最终都是对另一个对象的引用。作用域和验证取决于是否通过bean或父属性指定另一个对象的ID或名称。

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

标识为commandManager的bean在需要myCommand bean的新实例时调用它自己的createCommand()方法。如果真的需要将myCommand bean部署为prototype ，则必须小心。如果是单例，则每次都返回相同的myCommand bean实例。

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

或者，更习惯的做法是，您可以依赖于根据查找方法声明的返回类型解析目标bean:

```java
public abstract class CommandManager {

    public Object process(Object commandState) {
        MyCommand command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    @Lookup
    protected abstract MyCommand createCommand();
}
```

Note that you should typically declare such annotated lookup methods with a concrete stub implementation, in order for them to be compatible with Spring’s component scanning rules where abstract classes get ignored by default. This limitation does not apply to explicitly registered or explicitly imported bean classes.

请注意，您通常应该使用具体的存根实现来声明这种带注释的查找方法，以便它们与Spring的组件扫描规则兼容，其中抽象类在默认情况下会被忽略。此限制不适用于显式注册或显式导入的bean类。

|      | Another way of accessing differently scoped target beans is an `ObjectFactory`/ `Provider` injection point. See [Scoped Beans as Dependencies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes-other-injection).You may also find the `ServiceLocatorFactoryBean` (in the `org.springframework.beans.factory.config` package) to be useful. |
| ---- | ------------------------------------------------------------ |
|      | 访问作用域不同的目标bean的另一种方法是ObjectFactory/ Provider注入点。请将作用域bean视为依赖项。您还可以在org.springframework.beans.factory中找到ServiceLocatorFactoryBean。配置包)是有用的 |

###### Arbitrary Method Replacement 任意的方法替换

A less useful form of method injection than lookup method injection is the ability to replace arbitrary methods in a managed bean with another method implementation. You can safely skip the rest of this section until you actually need this functionality.

方法注入的一种不如查找方法注入有用的形式是用另一个方法实现替换托管bean中的任意方法的能力。您可以安全地跳过本节的其余部分，直到您真正需要此功能。

With XML-based configuration metadata, you can use the `replaced-method` element to replace an existing method implementation with another, for a deployed bean. Consider the following class, which has a method called `computeValue` that we want to override:

对于基于xml的配置元数据，您可以使用replaced-method元素将一个已部署bean的现有方法实现替换为另一个方法实现。考虑下面的类，它有一个名为computeValue的方法，我们想要覆盖它:

```java
public class MyValueCalculator {

    public String computeValue(String input) {
        // some real code...
    }

    // some other methods...
}
```

A class that implements the `org.springframework.beans.factory.support.MethodReplacer` interface provides the new method definition, as the following example shows:

实现org.springframework.bean .factory.support的类。MethodReplacer接口提供了新的方法定义，如下面的示例所示:

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

可以使用< replacement -method/>元素中的一个或多个< argtype />元素来指示被重写的方法的方法签名。只有当方法被重载并且类中存在多个变体时，参数的签名才有必要。为了方便，参数的类型字符串可以是完全限定类型名的子字符串。例如，以下所有匹配

```java
java.lang.String
String
Str
```

Because the number of arguments is often enough to distinguish between each possible choice, this shortcut can save a lot of typing, by letting you type only the shortest string that matches an argument type.

由于参数的数量通常足以区分每种可能的选择，因此通过只输入与参数类型匹配的最短字符串，此快捷方式可以节省大量输入。

#### 1.5. Bean Scopes

1.5条。Bean作用域

#### 1.6. Customizing the Nature of a Bean

1.6条。定制Bean的性质

#### 1.7. Bean Definition Inheritance

1.7条。Bean定义继承

#### 1.8. Container Extension Points

#### 1.8.2. Customizing Configuration Metadata with a `BeanFactoryPostProcessor`

使用BeanFactoryPostProcessor自定义配置元数据

The next extension point that we look at is the `org.springframework.beans.factory.config.BeanFactoryPostProcessor`. The semantics of this interface are similar to those of the `BeanPostProcessor`, with one major difference: `BeanFactoryPostProcessor` operates on the bean configuration metadata. That is, the Spring IoC container lets a `BeanFactoryPostProcessor` read the configuration metadata and potentially change it *before* the container instantiates any beans other than `BeanFactoryPostProcessor` instances.

我们要查看的下一个扩展点是`org.springframework.beans.factory.config.BeanFactoryPostProcessor`。此接口的语义与`BeanPostProcessor`类似，但有一个主要区别:`BeanFactoryPostProcessor`操作`bean配置元数据`。也就是说，Spring IoC容器允许`BeanFactoryPostProcessor`在容器实例化`BeanFactoryPostProcessor`实例之外的任何bean之前读取配置元数据并可能更改它。

You can configure multiple `BeanFactoryPostProcessor` instances, and you can control the order in which these `BeanFactoryPostProcessor` instances run by setting the `order` property. However, you can only set this property if the `BeanFactoryPostProcessor` implements the `Ordered` interface. If you write your own `BeanFactoryPostProcessor`, you should consider implementing the `Ordered` interface, too. See the javadoc of the [`BeanFactoryPostProcessor`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) and [`Ordered`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/core/Ordered.html) interfaces for more details.

您可以配置多个`BeanFactoryPostProcessor`实例，并且您可以通过设置`order`属性来控制这些`BeanFactoryPostProcessor`实例运行的顺序。但是，您只能在`BeanFactoryPostProcessor`实现了有序接口的情况下设置此属性。如果您编写自己的`BeanFactoryPostProcessor`，也应该考虑实现`Ordered`接口。有关更多细节，请参阅BeanFactoryPostProcessor和有序接口的javadoc。

> If you want to change the actual bean instances (that is, the objects that are created from the configuration metadata), then you instead need to use a `BeanPostProcessor` (described earlier in [Customizing Beans by Using a `BeanPostProcessor`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-bpp)). While it is technically possible to work with bean instances within a `BeanFactoryPostProcessor` (for example, by using `BeanFactory.getBean()`), doing so causes premature bean instantiation, violating the standard container lifecycle. This may cause negative side effects, such as bypassing bean post processing.
>
> 如果希望更改实际`bean`实例(即从配置元数据创建的对象)，则需要使用`BeanPostProcessor`(在前面通过使用BeanPostProcessor自定义bean中进行了描述)。虽然在`BeanFactoryPostProcessor`中使用bean实例在技术上是可行的(例如，通过使用`BeanFactory.getBean()`)，但是这样做会导致过早的bean实例化，违反标准的容器生命周期。这可能会导致负面的副作用，比如绕过bean的后处理。
>
> Also, `BeanFactoryPostProcessor` instances are scoped per-container. This is only relevant if you use container hierarchies. If you define a `BeanFactoryPostProcessor` in one container, it is applied only to the bean definitions in that container. Bean definitions in one container are not post-processed by `BeanFactoryPostProcessor` instances in another container, even if both containers are part of the same hierarchy.
>
> 另外，`BeanFactoryPostProcessor`实例的作用域为每个容器。这只有在使用容器层次结构时才有用。如果您在一个容器中定义了`BeanFactoryPostProcessor`，那么它只应用于该容器中的bean定义。一个容器中的Bean定义不会被另一个容器中的`BeanFactoryPostProcessor`实例进行后处理，即使这两个容器属于同一层次结构。

A bean factory post-processor is automatically run when it is declared inside an `ApplicationContext`, in order to apply changes to the configuration metadata that define the container. Spring includes a number of predefined bean factory post-processors, such as `PropertyOverrideConfigurer` and `PropertySourcesPlaceholderConfigurer`. You can also use a custom `BeanFactoryPostProcessor` — for example, to register custom property editors.

当`bean工厂后处理器`在`ApplicationContext`中声明时，它将`自动运行`，以便对定义容器的配置元数据应用更改。Spring包括许多预定义的`bean工厂后处理器`，如`PropertyOverrideConfigurer`和`PropertySourcesPlaceholderConfigurer`。您还可以使用自定义`BeanFactoryPostProcessor`—例如，用于注册自定义属性编辑器。

An `ApplicationContext` automatically detects any beans that are deployed into it that implement the `BeanFactoryPostProcessor` interface. It uses these beans as bean factory post-processors, at the appropriate time. You can deploy these post-processor beans as you would any other bean.

`ApplicationContext`自动检测部署到其中实现`BeanFactoryPostProcessor`接口的任何bean。在适当的时候，它将这些bean用作`bean工厂的后处理器`。可以像部署任何其他bean一样部署这些后处理bean。

> As with `BeanPostProcessor`s , you typically do not want to configure `BeanFactoryPostProcessor`s for lazy initialization. If no other bean references a `Bean(Factory)PostProcessor`, that post-processor will not get instantiated at all. Thus, marking it for lazy initialization will be ignored, and the `Bean(Factory)PostProcessor` will be instantiated eagerly even if you set the `default-lazy-init` attribute to `true` on the declaration of your `<beans />` element.
>
> 与`BeanPostProcessors`一样，您通常不希望将`BeanFactoryPostProcessors`配置为延迟初始化。如果没有其他bean引用bean(工厂)后处理器，则该后处理器根本不会实例化。因此，将其标记为延迟初始化将被忽略，并且即使在声明<beans />元素时将default-lazy-init属性设置为true, Bean(工厂)后处理器也将被快速实例化。

##### Example: The Class Name Substitution `PropertySourcesPlaceholderConfigurer`

示例:类名替换`PropertySourcesPlaceholderConfigurer`

You can use the `PropertySourcesPlaceholderConfigurer` to externalize property values from a bean definition in a separate file by using the standard Java `Properties` format. Doing so enables the person deploying an application to customize environment-specific properties, such as database URLs and passwords, without the complexity or risk of modifying the main XML definition file or files for the container.

您可以使用`PropertySourcesPlaceholderConfigurer`使用标准的Java `Properties`格式将bean定义中的属性值外部化到单独的文件中。这样，部署应用程序的人员就可以自定义特定于环境的属性，比如数据库url和密码，而无需修改主XML定义文件或容器文件的复杂性或风险。

Consider the following XML-based configuration metadata fragment, where a `DataSource` with placeholder values is defined:

考虑以下基于xml的配置元数据片段，其中定义了具有占位符值的数据源:

```xml
<bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
    <property name="locations" value="classpath:com/something/jdbc.properties"/>
</bean>
<bean id="dataSource" destroy-method="close" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

The example shows properties configured from an external `Properties` file. At runtime, a `PropertySourcesPlaceholderConfigurer` is applied to the metadata that replaces some properties of the DataSource. The values to replace are specified as placeholders of the form `${property-name}`, which follows the Ant and log4j and JSP EL style.

The actual values come from another file in the standard Java `Properties` format:

该示例显示了从外部`Properties` 文件配置的属性。在运行时，`PropertySourcesPlaceholderConfigurer`应用于替换数据源的某些属性的元数据。要替换的值指定为表单`${property-name}`的占位符，该表单遵循Ant、log4j和JSP EL样式。

实际值来自另一个标准 Java `Properties`格式的文件:

```
jdbc.driverClassName=org.hsqldb.jdbcDriver
jdbc.url=jdbc:hsqldb:hsql://production:9002
jdbc.username=sa
jdbc.password=root
```

Therefore, the `${jdbc.username}` string is replaced at runtime with the value, 'sa', and the same applies for other placeholder values that match keys in the properties file. The `PropertySourcesPlaceholderConfigurer` checks for placeholders in most properties and attributes of a bean definition. Furthermore, you can customize the placeholder prefix and suffix.

因此,`$ {jdbc.username}`字符串在运行时被替换为值'sa'，对于属性文件中匹配键的其他占位符值也适用同样的方法。`PropertySourcesPlaceholderConfigurer`检查bean定义的大多数属性和属性中的占位符。此外，您还可以自定义占位符前缀和后缀。

With the `context` namespace introduced in Spring 2.5, you can configure property placeholders with a dedicated configuration element. You can provide one or more locations as a comma-separated list in the `location` attribute, as the following example shows:

使用Spring 2.5中引入的上下文名称空间，您可以用一个专用的配置元素配置属性占位符。您可以在location属性中以逗号分隔的列表形式提供一个或多个位置，如下面的示例所示:

```xml
<context:property-placeholder location="classpath:com/something/jdbc.properties"/>
```

The `PropertySourcesPlaceholderConfigurer` not only looks for properties in the `Properties` file you specify. By default, if it cannot find a property in the specified properties files, it checks against Spring `Environment` properties and regular Java `System` properties.

`PropertySourcesPlaceholderConfigurer`不仅在您指定的`Properties` 文件中查找属性。默认情况下，如果在指定的属性文件中找不到属性，它将检查Spring`Environment` 属性和常规Java `System` 属性。

> You can use the `PropertySourcesPlaceholderConfigurer` to substitute class names, which is sometimes useful when you have to pick a particular implementation class at runtime. The following example shows how to do so: 	
>
> 您可以使用`PropertySourcesPlaceholderConfigurer`来替换类名，当您必须在运行时选择特定的实现类时，这有时很有用。下面的例子展示了如何做到这一点:

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

> If the class cannot be resolved at runtime to a valid class, resolution of the bean fails when it is about to be created, which is during the `preInstantiateSingletons()` phase of an `ApplicationContext` for a non-lazy-init bean.
>
> 如果不能在运行时将类解析为有效类，则在即将创建bean时，即在`ApplicationContext`non-lazy-init  bean的预实例化阶段`preInstantiateSingletons()`，对bean的解析将失败。

##### Example: The `PropertyOverrideConfigurer`

The `PropertyOverrideConfigurer`, another bean factory post-processor, resembles the `PropertySourcesPlaceholderConfigurer`, but unlike the latter, the original definitions can have default values or no values at all for bean properties. If an overriding `Properties` file does not have an entry for a certain bean property, the default context definition is used.

他PropertyOverrideConfigurer是另一个bean工厂后处理器，它类似于PropertySourcesPlaceholderConfigurer，但与后者不同的是，原始定义可以有缺省值，也可以没有bean属性的值。如果覆盖属性文件没有针对某个bean属性的条目，则使用默认上下文定义。

Note that the bean definition is not aware of being overridden, so it is not immediately obvious from the XML definition file that the override configurer is being used. In case of multiple `PropertyOverrideConfigurer` instances that define different values for the same bean property, the last one wins, due to the overriding mechanism.

请注意，bean定义不知道被覆盖，因此从XML定义文件中不能立即看出正在使用覆盖配置器。在多个PropertyOverrideConfigurer实例为同一个bean属性定义不同值的情况下，由于覆盖机制，最后一个实例胜出。

Properties file configuration lines take the following format:

```
beanName.property=value
```

The following listing shows an example of the format:

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

|      | Specified override values are always literal values. They are not translated into bean references. This convention also applies when the original value in the XML bean definition specifies a bean reference. |
| ---- | ------------------------------------------------------------ |
|      | 指定的重写值总是文字值。它们没有被转换为bean引用。当XML bean定义中的原始值指定了一个bean引用时，这个约定也适用。 |

With the `context` namespace introduced in Spring 2.5, it is possible to configure property overriding with a dedicated configuration element, as the following example shows:

```xml
<context:property-override location="classpath:override.properties"/>
```

#### 1.8.3. Customizing Instantiation Logic with a `FactoryBean`

使用FactoryBean----->自定义实例化逻辑

You can implement the `org.springframework.beans.factory.FactoryBean` interface for objects that are themselves factories.

The `FactoryBean` interface is a point of pluggability into the Spring IoC container’s instantiation logic. If you have complex initialization code that is better expressed in Java as opposed to a (potentially) verbose amount of XML, you can create your own `FactoryBean`, write the complex initialization inside that class, and then plug your custom `FactoryBean` into the container.

The `FactoryBean` interface provides three methods:

您可以实现`org.springframework.beans.factory.FactoryBean`接口来生成本身是工厂的对象。

`FactoryBean`接口是Spring IoC容器实例化逻辑的可插入点。如果您有复杂的初始化代码(用Java来表达比(可能)冗长的XML更好)，那么您可以创建自己的`FactoryBean`，在类中编写复杂的初始化，然后将定制的`FactoryBean`插入到容器中。

FactoryBean接口提供了三种方法:

- `Object getObject()`: Returns an instance of the object this factory creates. The instance can possibly be shared, depending on whether this factory returns singletons or prototypes.

- `boolean isSingleton()`: Returns `true` if this `FactoryBean` returns singletons or `false` otherwise.

- `Class getObjectType()`: Returns the object type returned by the `getObject()` method or `null` if the type is not known in advance.

  1,对象getObject():返回此工厂创建的对象的实例。实例可能被共享，这取决于这个工厂返回的是单例还是原型。

  2,boolean isSingleton():如果FactoryBean返回单例，返回true;否则返回false。

  3,类getObjectType():返回getObject()方法返回的对象类型，如果事先不知道该类型，则返回null。

The `FactoryBean` concept and interface is used in a number of places within the Spring Framework. More than 50 implementations of the `FactoryBean` interface ship with Spring itself.

When you need to ask a container for an actual `FactoryBean` instance itself instead of the bean it produces, preface the bean’s `id` with the ampersand symbol (`&`) when calling the `getBean()` method of the `ApplicationContext`. So, for a given `FactoryBean` with an `id` of `myBean`, invoking `getBean("myBean")` on the container returns the product of the `FactoryBean`, whereas invoking `getBean("&myBean")` returns the `FactoryBean` instance itself.

`FactoryBean`的概念和接口在Spring框架的许多地方使用。Spring本身附带了50多个`FactoryBean`接口的实现。

当您需要向容器请求实际的`FactoryBean`实例本身而不是它生成的`bean`时，在调用`ApplicationContext`的`getBean()`方法时，在`bean`的`id`前面加上`&`符号`(&)`。因此，对于`id`为`myBean`的给定`FactoryBean`，在容器上调用`getBean(“myBean”)`将返回`FactoryBean`的产品，而调用`getBean(“&myBean”)`返回`FactoryBean`实例本身

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

#### 1.9.1. @Required

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

#### 1.9.2. Using `@Autowired`

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

#### 1.11. Using JSR 330 Standard Annotations 使用JSR的标准注释

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

#### 1.12.2. Instantiating the Spring Container by Using `AnnotationConfigApplicationContext`通过使用“AnnotationConfigApplicationContext”实例化Spring容器

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

#### 1.12.3. Using the `@Bean` Annotation

#### 1.13. Environment Abstraction

1.13条。环境抽象

#### 1.14. Registering a LoadTimeWeaver

1.14条。注册LoadTimeWeaver

#### 1.15. Additional Capabilities of the ApplicationContext ApplicationContext的其他功能

##### 1.15.2. Standard and Custom Events
标准和自定义事件

Event handling in the `ApplicationContext` is provided through the `ApplicationEvent` class and the `ApplicationListener` interface. If a bean that implements the `ApplicationListener` interface is deployed into the context, every time an `ApplicationEvent` gets published to the `ApplicationContext`, that bean is notified. Essentially, this is the standard Observer design pattern.

在` ApplicationContext `中的事件处理是通过` ApplicationEvent `类和` ApplicationListener `接口提供的。如果实现了` ApplicationListener `接口的bean被部署到上下文中，那么每当` ApplicationEvent `被发布到` ApplicationContext `时，该bean就会得到通知。本质上，这就是标准的观察者设计模式。

As of Spring 4.2, the event infrastructure has been significantly improved and offers an [annotation-based model](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-events-annotation) as well as the ability to publish any arbitrary event (that is, an object that does not necessarily extend from `ApplicationEvent`). When such an object is published, we wrap it in an event for you.

Spring 4.2,事件基础设施明显改善,提供了一个(基于注解的模型)以及发布任意事件的能力(也就是说,对象并不一定从ApplicationEvent)延伸。当发布这样的对象时，我们为您将其包装在事件中。

The following table describes the standard events that Spring provides:

下表描述了Spring提供的标准事件:

| Event                        | Explanation                                                  |
| :--------------------------- | :----------------------------------------------------------- |
| `ContextRefreshedEvent`      | Published when the `ApplicationContext` is initialized or refreshed (for example, by using the `refresh()` method on the `ConfigurableApplicationContext` interface). Here, `initialized` means that all beans are loaded, post-processor beans are detected and activated, singletons are pre-instantiated, and the `ApplicationContext` object is ready for use. As long as the context has not been closed, a refresh can be triggered multiple times, provided that the chosen `ApplicationContext` actually supports such `hot` refreshes. For example, `XmlWebApplicationContext` supports hot refreshes, but `GenericApplicationContext` does not. |
|                              | 当` ApplicationContext `初始化或刷新时发布(例如，通过使用` ConfigurableApplicationContext `接口上的` refresh() `方法)。在这里，`initialized`意味着所有的bean被加载，后处理器bean被检测和激活，单例被预先实例化，并且`ApplicationContext`对象已经准备好可以使用了。只要上下文没有被关闭，刷新就可以被触发多次，只要所选的`ApplicationContext`实际上支持这样的“热”刷新。例如，` XmlWebApplicationContext `支持热刷新，但` GenericApplicationContext`不支持。 |
| `ContextStartedEvent`        | Published when the `ApplicationContext` is started by using the `start()` method on the `ConfigurableApplicationContext` interface. Here, `started` means that all `Lifecycle` beans receive an explicit start signal. Typically, this signal is used to restart beans after an explicit stop, but it may also be used to start components that have not been configured for autostart (for example, components that have not already started on initialization). |
|                              | 通过使用` ConfigurableApplicationContext `接口上的` start() `方法启动` ApplicationContext `时发布。这里，`started`意味着所有`生命周期`bean都接收到显式的启动信号。通常，这个信号用于在显式停止后重新启动bean，但是它也可以用于启动尚未配置为自动启动的组件(例如，尚未在初始化时启动的组件)。 |
| `ContextStoppedEvent`        | Published when the `ApplicationContext` is stopped by using the `stop()` method on the `ConfigurableApplicationContext` interface. Here, `stopped` means that all `Lifecycle` beans receive an explicit stop signal. A stopped context may be restarted through a `start()` call. |
|                              | 通过使用`ConfigurableApplicationContext`接口上的`stop()`方法，当`ApplicationContext`停止时发布。这里，`stopped`意味着所有`生命周期`bean都接收到一个显式的停止信号。可以通过` start() `调用重新启动已停止的上下文。 |
| `ContextClosedEvent`         | Published when the `ApplicationContext` is being closed by using the `close()` method on the `ConfigurableApplicationContext` interface or via a JVM shutdown hook. Here, "closed" means that all singleton beans will be destroyed. Once the context is closed, it reaches its end of life and cannot be refreshed or restarted. |
|                              | 当` ApplicationContext `被关闭时发布，通过使用` ConfigurableApplicationContext `接口上的` close() `方法或者通过JVM关闭钩子。在这里，`关闭`意味着所有的单例bean将被销毁。一旦上下文关闭，它就会到达生命的终点，无法刷新或重新启动。 |
| `RequestHandledEvent`        | A web-specific event telling all beans that an HTTP request has been serviced. This event is published after the request is complete. This event is only applicable to web applications that use Spring’s `DispatcherServlet`. |
|                              | 一个特定于web的事件，告诉所有bean一个HTTP请求已经得到服务。此事件在请求完成后发布。这个事件只适用于使用Spring的`DispatcherServlet`的web应用程序。 |
| `ServletRequestHandledEvent` | A subclass of `RequestHandledEvent` that adds Servlet-specific context information. |
|                              | 一个`RequestHandledEvent`的子类，它添加了特定于servlet的上下文信息。 |

You can also create and publish your own custom events. The following example shows a simple class that extends Spring’s `ApplicationEvent` base class:
您还可以创建和发布自己的自定义事件。下面的例子展示了一个简单的类，它扩展了Spring的`ApplicationEvent`基类:


```java
public class BlockedListEvent extends ApplicationEvent {
    private final String address;
    private final String content;
    public BlockedListEvent(Object source, String address, String content) {
        super(source);
        this.address = address;
        this.content = content;
    }

    // accessor and other methods...
}
```

To publish a custom `ApplicationEvent`, call the `publishEvent()` method on an `ApplicationEventPublisher`. Typically, this is done by creating a class that implements `ApplicationEventPublisherAware` and registering it as a Spring bean. The following example shows such a class:

要发布一个自定义的` ApplicationEvent `，在一个`ApplicationEventPublisher`上调用'`publishEvent() `方法。通常，这是通过创建一个实现` ApplicationEventPublisherAware `的类并将其注册为一个Spring bean来完成的。下面的例子展示了这样一个类:

```java
public class EmailService implements ApplicationEventPublisherAware {

    private List<String> blockedList;
    private ApplicationEventPublisher publisher;

    public void setBlockedList(List<String> blockedList) {
        this.blockedList = blockedList;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEmail(String address, String content) {
        if (blockedList.contains(address)) {
            publisher.publishEvent(new BlockedListEvent(this, address, content));
            return;
        }
        // send email...
    }
}
```

At configuration time, the Spring container detects that `EmailService` implements `ApplicationEventPublisherAware` and automatically calls `setApplicationEventPublisher()`. In reality, the parameter passed in is the Spring container itself. You are interacting with the application context through its `ApplicationEventPublisher` interface.

在配置时，Spring容器检测到` EmailService `实现了` ApplicationEventPublisherAware `，并自动调用` setApplicationEventPublisher() `。实际上，传入的参数就是Spring容器本身。你通过它的` ApplicationEventPublisher `接口与应用程序上下文交互。

To receive the custom `ApplicationEvent`, you can create a class that implements `ApplicationListener` and register it as a Spring bean. The following example shows such a class:

要接收自定义的`ApplicationEvent`，您可以创建一个实现`ApplicationListener`的类，并将其注册为一个Spring bean。下面的例子展示了这样一个类:

```java
public class BlockedListNotifier implements ApplicationListener<BlockedListEvent> {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    public void onApplicationEvent(BlockedListEvent event) {
        // notify appropriate parties via notificationAddress...
    }
}
```

Notice that `ApplicationListener` is generically parameterized with the type of your custom event (`BlockedListEvent` in the preceding example). This means that the `onApplicationEvent()` method can remain type-safe, avoiding any need for downcasting. You can register as many event listeners as you wish, but note that, by default, event listeners receive events synchronously. This means that the `publishEvent()` method blocks until all listeners have finished processing the event. One advantage of this synchronous and single-threaded approach is that, when a listener receives an event, it operates inside the transaction context of the publisher if a transaction context is available. If another strategy for event publication becomes necessary, see the javadoc for Spring’s [`ApplicationEventMulticaster`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/event/ApplicationEventMulticaster.html) interface and [`SimpleApplicationEventMulticaster`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/event/SimpleApplicationEventMulticaster.html) implementation for configuration options.

注意，`ApplicationListener`通常是用自定义事件的类型参数化的(在前面的例子中是`BlockedListEvent`)。这意味着` onApplicationEvent() `方法可以保持类型安全，避免任何向下强制转换的需要。您可以注册任意数量的事件监听器，但是请注意，默认情况下，事件监听器同步接收事件。这意味着`publishEvent()`方法会阻塞，直到所有监听器都完成了事件的处理。这种同步和单线程方法的一个优点是，当侦听器接收到事件时，如果事务上下文可用，它将在发布程序的事务上下文内操作。如果事件发表的另一个战略成为必要,看到spring的javadoc年代(`ApplicationEventMulticaster`)接口和(`SimpleApplicationEventMulticaster`)实现配置选项。

The following example shows the bean definitions used to register and configure each of the classes above:

下面的例子显示了用于注册和配置上面每个类的bean定义:

```xml
<bean id="emailService" class="example.EmailService">
    <property name="blockedList">
        <list>
            <value>known.spammer@example.org</value>
            <value>known.hacker@example.org</value>
            <value>john.doe@example.org</value>
        </list>
    </property>
</bean>

<bean id="blockedListNotifier" class="example.BlockedListNotifier">
    <property name="notificationAddress" value="blockedlist@example.org"/>
</bean>
```

Putting it all together, when the `sendEmail()` method of the `emailService` bean is called, if there are any email messages that should be blocked, a custom event of type `BlockedListEvent` is published. The `blockedListNotifier` bean is registered as an `ApplicationListener` and receives the `BlockedListEvent`, at which point it can notify appropriate parties.

把它们放在一起，当` emailService ` bean的` sendEmail() `方法被调用时，如果有任何邮件消息应该被阻止，一个自定义的事件类型` BlockedListEvent `被发布。`blockedListNotifier`bean注册为一个`ApplicationListener`，并接收`BlockedListEvent`，在这一点上它可以通知适当的方。

Spring’s eventing mechanism is designed for simple communication between Spring beans within the same application context. However, for more sophisticated enterprise integration needs, the separately maintained [Spring Integration](https://projects.spring.io/spring-integration/) project provides complete support for building lightweight, [pattern-oriented](https://www.enterpriseintegrationpatterns.com/), event-driven architectures that build upon the well-known Spring programming model. 

Spring的事件机制是为相同应用程序上下文中的Spring bean之间的简单通信而设计的。然而，对于更复杂的企业集成需求，单独维护的[Spring integration](https://projects.spring.io/springing-integration/)项目提供了对构建轻量级、[面向模式](https://www.enterpriseintegrationpatterns.com/)事件驱动架构的完整支持，这些架构构建在著名的Spring编程模型之上。

##### Annotation-based Event Listeners 基于注释的事件监听器

As of Spring 4.2, you can register an event listener on any public method of a managed bean by using the `@EventListener` annotation. The `BlockedListNotifier` can be rewritten as follows:

从Spring 4.2开始，您可以使用` @EventListener `注释在托管bean的任何公共方法上注册事件监听器。`BlockedListNotifier`可以重写如下:

```java
public class BlockedListNotifier {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @EventListener
    public void processBlockedListEvent(BlockedListEvent event) {
        // notify appropriate parties via notificationAddress...
    }
}
```

The method signature once again declares the event type to which it listens, but, this time, with a flexible name and without implementing a specific listener interface. The event type can also be narrowed through generics as long as the actual event type resolves your generic parameter in its implementation hierarchy.

方法签名再次声明它侦听的事件类型，但是这次使用了灵活的名称，并且没有实现特定的侦听器接口。只要实际事件类型在其实现层次结构中解析泛型参数，就可以通过泛型缩小事件类型。

If your method should listen to several events or if you want to define it with no parameter at all, the event types can also be specified on the annotation itself. The following example shows how to do so:

如果您的方法应该侦听多个事件，或者您希望在不使用任何参数的情况下定义它，那么还可以在注释本身上指定事件类型。下面的例子展示了如何做到这一点:

```java
@EventListener({ContextStartedEvent.class, ContextRefreshedEvent.class})
public void handleContextStart() {
    // ...
}
```

It is also possible to add additional runtime filtering by using the `condition` attribute of the annotation that defines a [`SpEL` expression](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions) , which should match to actually invoke the method for a particular event.

还可以添加额外的运行时过滤通过使用注释的`条件`属性,定义了一个(`?`表达式),这实际上应该匹配为一个特定的事件调用该方法。

The following example shows how our notifier can be rewritten to be invoked only if the `content` attribute of the event is equal to `my-event`:

下面的例子展示了我们的通知可以被重写，只有在事件的`内容`属性等于`my-event`时才被调用:

```java
@EventListener(condition = "#blEvent.content == `my-event`")
public void processBlockedListEvent(BlockedListEvent blockedListEvent) {
    // notify appropriate parties via notificationAddress...
}
```

Each `SpEL` expression evaluates against a dedicated context. The following table lists the items made available to the context so that you can use them for conditional event processing:
每个`SpEL`表达式根据一个专用上下文计算。下表列出了上下文可用的项目，以便您可以使用它们进行条件事件处理:

| Name                    | Location           | Description                                                  | Example                                                      |
| :---------------------- | :----------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| Event事件               | root object        | The actual `ApplicationEvent`.实际的“ApplicationEvent”。     | `#root.event` or `event`                                     |
| Arguments array参数数组 | root object        | The arguments (as an object array) used to invoke the method.用于调用方法的参数(作为对象数组)。 | `#root.args` or `args`; `args[0]` to access the first argument, etc. |
| *Argument name*参数名称 | evaluation context | The name of any of the method arguments. If, for some reason, the names are not available (for example, because there is no debug information in the compiled byte code), individual arguments are also available using the `#a<#arg>` syntax where `<#arg>` stands for the argument index (starting from 0).                                                                                                                                                                                         任何方法参数的名称。如果由于某种原因，名称不可用(例如，因为在已编译的字节代码中没有调试信息)，也可以使用` #a<#arg> `语法使用单个参数，其中` <#arg> `表示参数索引(从0开始)。 | `#blEvent` or `#a0` (you can also use `#p0` or `#p<#arg>` parameter notation as an alias) |

Note that `#root.event` gives you access to the underlying event, even if your method signature actually refers to an arbitrary object that was published.

注意,`#根。允许您访问底层事件，即使您的方法签名实际上引用了已发布的任意对象。

If you need to publish an event as the result of processing another event, you can change the method signature to return the event that should be published, as the following example shows:

如果你需要发布一个事件作为处理另一个事件的结果，你可以改变方法签名来返回应该发布的事件，如下面的例子所示:



```java
@EventListener
public ListUpdateEvent handleBlockedListEvent(BlockedListEvent event) {
    // notify appropriate parties via notificationAddress and
    // then publish a ListUpdateEvent...
}
```

This feature is not supported for [asynchronous listeners](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-events-async).

 [异步监听器]不支持该特性。

This new method publishes a new `ListUpdateEvent` for every `BlockedListEvent` handled by the method above. If you need to publish several events, you can return a `Collection` of events instead.

这个新方法为上面方法处理的每个`BlockedListEvent`发布一个新的`ListUpdateEvent`。如果需要发布多个事件，则可以返回事件的`集合`。
##### Asynchronous Listeners 异步听众

If you want a particular listener to process events asynchronously, you can reuse the [regular `@Async` support](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-annotation-support-async). The following example shows how to do so:

如果您想要一个特定的侦听器异步地处理事件，您可以重用[regular ` async `支持](https://docs.spring.io/springing-framework/docs/current/reference/html/integration.html #调度-注释- supportasync)。下面的例子展示了如何做到这一点:

```java
@EventListener
@Async
public void processBlockedListEvent(BlockedListEvent event) {
    // BlockedListEvent is processed in a separate thread
}
```

Be aware of the following limitations when using asynchronous events:
在使用异步事件时要注意以下限制:

- If an asynchronous event listener throws an `Exception`, it is not propagated to the caller. See `AsyncUncaughtExceptionHandler` for more details.
如果异步事件侦听器抛出一个`异常`，它不会传播到调用者。更多细节请参见`AsyncUncaughtExceptionHandler`。
- Asynchronous event listener methods cannot publish a subsequent event by returning a value. If you need to publish another event as the result of the processing, inject an [`ApplicationEventPublisher`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/aop/interceptor/AsyncUncaughtExceptionHandler.html) to publish the event manually.
异步事件监听器方法不能通过返回值来发布后续事件。如果您需要发布另一个事件作为处理的结果，注入一个[` ApplicationEventPublisher `](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/aop/interceptor/AsyncUncaughtExceptionHandler.html)来手动发布事件。

##### Ordering Listeners
要求听众
If you need one listener to be invoked before another one, you can add the `@Order` annotation to the method declaration, as the following example shows:

如果需要先调用一个监听器，可以在方法声明中添加` @Order `注释，如下面的示例所示:

```java
@EventListener
@Order(42)
public void processBlockedListEvent(BlockedListEvent event) {
    // notify appropriate parties via notificationAddress...
}
```

##### Generic Events
通用事件
You can also use generics to further define the structure of your event. Consider using an `EntityCreatedEvent<T>` where `T` is the type of the actual entity that got created. For example, you can create the following listener definition to receive only `EntityCreatedEvent` for a `Person`:

还可以使用泛型来进一步定义事件的结构。考虑使用` EntityCreatedEvent<T> `，其中` T `是所创建的实际实体的类型。例如，您可以创建以下侦听器定义来只接收`Person`的`EntityCreatedEvent`:

```java
@EventListener
public void onPersonCreated(EntityCreatedEvent<Person> event) {
    // ...
}
```

Due to type erasure, this works only if the event that is fired resolves the generic parameters on which the event listener filters (that is, something like `class PersonCreatedEvent extends EntityCreatedEvent<Person> { … }`).

由于类型擦除，只有在触发的事件解析了事件侦听器筛选的泛型参数(即类似于`class PersonCreatedEvent extends EntityCreatedEvent<Person>{…}`)时，这种方法才有效。

In certain circumstances, this may become quite tedious if all events follow the same structure (as should be the case for the event in the preceding example). In such a case, you can implement `ResolvableTypeProvider` to guide the framework beyond what the runtime environment provides. The following event shows how to do so:

在某些情况下，如果所有事件都遵循相同的结构(前面示例中的事件也应该如此)，那么这可能会变得非常乏味。在这种情况下，你可以实现`ResolvableTypeProvider`来引导框架超越运行时环境所提供的范围。下面的事件展示了如何做到这一点:


```java
public class EntityCreatedEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public EntityCreatedEvent(T entity) {
        super(entity);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
```

This works not only for `ApplicationEvent` but any arbitrary object that you send as an event.

 这不仅适用于` ApplicationEvent `，也适用于任何你作为事件发送的对象。

#### 1.16. The BeanFactory

1.16条。豆工厂

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

`returning`属性中使用的名称必须与通知方法中参数的名称相对应。当一个方法执行返回时，返回值作为相应的实参值传递给通知方法。` returning`子句还限制只匹配那些返回指定类型值的方法执行(在本例中是` Object `，它匹配任何返回值)。

Please note that it is not possible to return a totally different reference when using after returning advice.
请注意，它是不可能返回一个完全不同的参考时，使用后返回建议。

##### After Throwing Advice
在抛出通知后
After throwing advice runs when a matched method execution exits by throwing an exception. You can declare it by using the `@AfterThrowing` annotation, as the following example shows:

在抛出通知后，当匹配的方法执行通过抛出异常退出时运行。你可以使用` @afterthrow `注释来声明它，如下面的例子所示:

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

通常，您希望仅在抛出给定类型的异常时才运行通知(增强)，而且您还经常需要访问通知主体中抛出的异常。您可以使用` throw `属性来限制匹配(如果需要—使用` Throwable `作为异常类型)，并将抛出的异常绑定到一个通知参数。下面的例子展示了如何做到这一点:

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

在`throwing`属性中使用的名称必须与通知方法中的参数名称相对应。当方法执行通过抛出异常而退出时，异常将作为相应的参数值传递给通知方法。`throwing`子句还限制只匹配那些抛出指定类型异常(在本例中为`DataAccessException`)的方法执行。

##### After (Finally) Advice
(最后)后的通知

After (finally) advice runs when a matched method execution exits. It is declared by using the `@After` annotation. After advice must be prepared to handle both normal and exception return conditions. It is typically used for releasing resources and similar purposes. The following example shows how to use after finally advice:

当匹配的方法执行（连接点joinPoint）退出时，通知运行。它是通过使用` @After `注释声明的。After advice必须准备好处理正常和异常返回条件。它通常用于释放资源和类似的目的。下面的例子展示了如何使用after finally advice:

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

最后一种通知是环绕通知的。Around advice`around`匹配的方法执行。它有机会在方法运行之前和之后执行工作，并确定方法何时、如何运行，甚至是否真正运行。如果您需要以线程安全的方式(例如启动和停止计时器)在方法执行之前和之后共享状态，则经常使用Around通知。总是使用最弱的形式的通知（增强）来满足你的要求(也就是说，不要使用around advice，如果before advice可以做的话)。

Around advice is declared by using the `@Around` annotation. The first parameter of the advice method must be of type `ProceedingJoinPoint`. Within the body of the advice, calling `proceed()` on the `ProceedingJoinPoint` causes the underlying method to run. The `proceed` method can also pass in an `Object[]`. The values in the array are used as the arguments to the method execution when it proceeds.

Around通知是使用` @Around `注释声明的。通知方法的第一个参数必须是` ProceedingJoinPoint `类型。在通知的主体中，对` ProceedingJoinPoint `调用` proceed() `导致底层方法运行。`proceed`方法还可以传入一个`Object[]`。数组中的值用作方法执行时的参数。

The behavior of `proceed` when called with an `Object[]` is a little different than the behavior of `proceed` for around advice compiled by the AspectJ compiler. For around advice written using the traditional AspectJ language, the number of arguments passed to `proceed` must match the number of arguments passed to the around advice (not the number of arguments taken by the underlying join point), and the value passed to proceed in a given argument position supplants the original value at the join point for the entity the value was bound to (do not worry if this does not make sense right now).

 The approach taken by Spring is simpler and a better match to its proxy-based, execution-only semantics. You only need to be aware of this difference if you compile @AspectJ aspects written for Spring and use `proceed` with arguments with the AspectJ compiler and weaver. There is a way to write such aspects that is 100% compatible across both Spring AOP and AspectJ, and this is discussed in the [following section on advice parameters](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params). 

当`Object[]`被调用时，`proceed`的行为与AspectJ编译器编译的around通知的`proceed`的行为略有不同。对于使用传统AspectJ语言编写的around通知，传递给`proceed`的参数数量必须与传递给around通知的参数数量匹配而不是底层连接点所采用的参数的数量)，并且在给定的参数位置传递给proceed的值取代了该值所绑定到的实体在连接点上的原始值(如果现在没有意义，不要担心)

Spring采用的方法更简单，并且更好地匹配其基于代理的、仅执行的语义.如果您编译了为Spring编写的@AspectJ方面，并使用带有AspectJ编译器和weaver参数的' proceed '，那么您只需要知道这种区别.有一种方法可以编写这样的方面，它在Spring AOP和AspectJ中100%兼容，这将在[关于通知参数的下一节]中讨论

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

通知(增强)参数

Spring offers fully typed advice, meaning that you declare the parameters you need in the advice signature (as we saw earlier for the returning and throwing examples) rather than work with `Object[]` arrays all the time. We see how to make argument and other contextual values available to the advice body later in this section. First, we take a look at how to write generic advice that can find out about the method the advice is currently advising.

Spring提供了充分的通知类型，这意味着您可以在通知签名(方法)中声明所需的参数(正如我们在前面的返回（returning）和抛出（throwing）示例中看到的那样)，而不是一直使用`Object[]`数组。在本节的后面，我们将看到如何使参数和其他上下文值对advice主体可用。首先，我们看一下如何编写通用的建议，以找出该建议目前正在建议的方法。

###### Access to the Current `JoinPoint`

访问当前的`JoinPoint`

Any advice method may declare, as its first parameter, a parameter of type `org.aspectj.lang.JoinPoint` (note that around advice is required to declare a first parameter of type `ProceedingJoinPoint`, which is a subclass of `JoinPoint`. The `JoinPoint` interface provides a number of useful methods:

任何通知方法都可以声明一个类型为` org.aspectj.lang.JoinPoint`的参数作为它的第一个参数。(注意around通知需要声明类型为` ProceedingJoinPoint `的第一个参数，它是` JoinPoint `的子类。`JoinPoint`接口提供了许多有用的方法:

- `getArgs()`: Returns the method arguments.
返回方法参数。
- `getThis()`: Returns the proxy object.
返回代理对象。
- `getTarget()`: Returns the target object.
返回目标对象。
- `getSignature()`: Returns a description of the method that is being advised.
返回被通知的方法的描述。
- `toString()`: Prints a useful description of the method being advised.
打印被通知（增强）的方法的有用描述。

See the [javadoc](https://www.eclipse.org/aspectj/doc/released/runtime-api/org/aspectj/lang/JoinPoint.html) for more detail.

###### Passing Parameters to Advice

向通知传递参数

We have already seen how to bind the returned value or exception value (using after returning and after throwing advice). To make argument values available to the advice body, you can use the binding form of `args`. If you use a parameter name in place of a type name in an args expression, the value of the corresponding argument is passed as the parameter value when the advice is invoked. An example should make this clearer. Suppose you want to advise the execution of DAO operations that take an `Account` object as the first parameter, and you need access to the account in the advice body. You could write the following:

我们已经了解了如何绑定返回值或异常值(在返回和抛出通知之后使用)。要使参数值对通知体可用，可以使用`args`的绑定形式。如果在args表达式中使用参数名代替类型名，则在调用通知时，将将相应实参的值作为参数值传递。一个例子应该会使这一点更清楚。假设您希望通知以`Account`对象作为第一个参数的DAO操作的执行，并且需要访问通知主体中的Account。你可以这样写

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

要做到这一点，我们必须检查集合的每个元素，这是不合理的，因为一般我们也不能决定如何处理`null`值。要实现类似的功能，您必须键入参数` Collection<?>`然后手动检查元素的类型。

###### Determining Argument Names
确定参数的名字
The parameter binding in advice invocations relies on matching names used in pointcut expressions to declared parameter names in advice and pointcut method signatures. Parameter names are not available through Java reflection, so Spring AOP uses the following strategy to determine parameter names:

通知调用中的参数绑定依赖于将切入点表达式中使用的名称与通知和切入点方法签名中声明的参数名称相匹配。参数名(方法签名的参数是无法反射的)在Java反射中是不可用的，所以Spring AOP使用以下策略来确定参数名:

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

对`JoinPoint`的第一个参数、`ProceedingJoinPoint`和`JoinPoint.StaticPart`的特殊处理。对于不收集任何其他连接点上下文的通知实例的类型特别方便。在这种情况下，可以省略` argNames `属性。例如，下面的建议不需要声明` argNames `属性:

```java
@Before("com.xyz.lib.Pointcuts.anyPublicMethod()")
public void audit(JoinPoint jp) {
    // ... use jp
}
```

- Using the ``argNames`` attribute is a little clumsy, so if the ``argNames`` attribute has not been specified, Spring AOP looks at the debug information for the class and tries to determine the parameter names from the local variable table. This information is present as long as the classes have been compiled with debug information ( ``-g:vars`` at a minimum). The consequences of compiling with this flag on are:

-  (1) your code is slightly easier to understand (reverse engineer),

-  (2) the class file sizes are very slightly bigger (typically inconsequential), 

- (3) the optimization to remove unused local variables is not applied by your compiler. In other words, you should encounter no difficulties by building with this flag on.

- 使用" argNames属性有点笨拙，因此如果没有指定" argNames属性，Spring AOP会查看类的调试信息，并尝试从本地变量表中确定参数名。只要类是用调试信息编译的，这个信息就会出现(" -g:vars 至少是这样)。使用这个标志进行编译的结果是:

- (1)您的代码稍微容易理解(反向工程)，

- (2)类文件大小稍微大了一点(通常不重要)，

- (3)你的编译器不会应用移除未使用局部变量的优化。换句话说，您在构建时应该不会遇到任何困难。

 If an @AspectJ aspect has been compiled by the AspectJ compiler (ajc) even without the debug information, you need not add the `argNames` attribute, as the compiler retain the needed information. 
   如果AspectJ编译器(ajc)已经编译了一个@AspectJ方面，即使没有调试信息，也不需要添加` argNames `属性，因为编译器会保留所需的信息。

- If the code has been compiled without the necessary debug information, Spring AOP tries to deduce the pairing of binding variables to parameters (for example, if only one variable is bound in the pointcut expression, and the advice method takes only one parameter, the pairing is obvious). If the binding of variables is ambiguous given the available information, an `AmbiguousBindingException` is thrown.

如果代码是在没有必要的调试信息的情况下编译的，Spring AOP会尝试推断出绑定变量到参数的配对(例如，如果在切入点表达式中只有一个变量被绑定，而advice方法只接受一个参数，这种配对是显而易见的)。如果给定可用信息，变量的绑定是不明确的，则抛出一个`AmbiguousBindingException`。

- If all of the above strategies fail, an `IllegalArgumentException` is thrown.
如果上述所有策略都失败了，就会抛出一个`IllegalArgumentException`。
###### Proceeding with Arguments
继续争论

We remarked earlier that we would describe how to write a `proceed` call with arguments that works consistently across Spring AOP and AspectJ. The solution is to ensure that the advice signature binds each of the method parameters in order. The following example shows how to do so:

我们前面提到过，我们将描述如何使用在Spring AOP和AspectJ中一致工作的参数来编写`proceed`调用。解决方案是确保通知签名（方法）按顺序绑定每个方法参数。下面的例子展示了如何做到这一点:

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
通知订购
What happens when multiple pieces of advice all want to run at the same join point? Spring AOP follows the same precedence rules as AspectJ to determine the order of advice execution. The highest precedence advice runs first "on the way in" (so, given two pieces of before advice, the one with highest precedence runs first). "On the way out" from a join point, the highest precedence advice runs last (so, given two pieces of after advice, the one with the highest precedence will run second).

如果多个通知都希望在同一个连接点上运行，会发生什么情况?Spring AOP遵循与AspectJ相同的优先规则来决定通知的执行顺序。优先级最高的通知在`on the way in`首先运行(因此，给定两个before通知，优先级最高的那通知首先运行)。从连接点`on the way out`，优先级最高的通知最后运行(因此，给定两个after通知，优先级最高的通知将排在第二)。

When two pieces of advice defined in different aspects both need to run at the same join point, unless you specify otherwise, the order of execution is undefined. You can control the order of execution by specifying precedence. This is done in the normal Spring way by either implementing the `org.springframework.core.Ordered` interface in the aspect class or annotating it with the `@Order` annotation. Given two aspects, the aspect returning the lower value from `Ordered.getOrder()` (or the annotation value) has the higher precedence.

在不同方面定义的两个通知都需要在同一个连接点上运行时，除非另行指定，否则执行顺序是未定义的。您可以通过指定优先级来控制执行顺序。这是通过实现` org.springframework.core.Ordered`以正常的Spring方式完成的接口，或者用``@Order `注释它。对于两个方面，从` order . getorder() `(或注释值)返回较低值的方面具有较高的优先级。

As of Spring Framework 5.2.7, advice methods defined in the same `@Aspect` class that need to run at the same join point are assigned precedence based on their advice type in the following order, from highest to lowest precedence: `@Around`, `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`. Note, however, that due to the implementation style in Spring‘s `AspectJAfterAdvice`, an `@After` advice method will effectively be invoked after any `@AfterReturning` or `@AfterThrowing` advice methods in the same aspect.When two pieces of the same type of advice (for example, two `@After` advice methods) defined in the same `@Aspect` class both need to run at the same join point, the ordering is undefined (since there is no way to retrieve the source code declaration order through reflection for javac-compiled classes). Consider collapsing such advice methods into one advice method per join point in each `@Aspect` class or refactor the pieces of advice into separate `@Aspect` classes that you can order at the aspect level via `Ordered` or `@Order`. 

在Spring Framework 5.2.7中，定义在同一个`@Aspect`类中需要在同一个连接点运行的通知方法的优先级是基于它们的通知类型，按照以下顺序从最高到最低的优先级分配的:`@Around`、`@Before`、`@After`、`@ afterreturn`、`@AfterThrowing`。但是请注意，由于Spring s ` AspectJAfterAdvice `中的实现风格，一个` @After `通知方法将有效地在同一方面的任何` @ afterreturn `或` @ afterthrows `通知方法之后被调用。当两块相同类型的通知(例如,两个`@After`通知方法)定义在相同的`@Aspect`类都需要运行在同一连接点,顺序是未定义的(因为通过反射没有办法获取源代码javac-compiled类的声明顺序)。考虑将这样的通知方法分解为每个`@Aspect`类中的每个连接点的一个通知方法，或者将通知片段重构为单独的`@Aspect`类，您可以通过`Ordered`或`@Order`在方面级别对这些类进行排序。

#### 5.4.5. Introductions

Introductions (known as inter-type declarations in AspectJ) enable an aspect to declare that advised objects implement a given interface, and to provide an implementation of that interface on behalf of those objects.

引入(在AspectJ中称为类型间声明)使方面能够声明被通知的对象实现给定的接口，并代表这些对象提供该接口的实现。

You can make an introduction by using the `@DeclareParents` annotation. This annotation is used to declare that matching types have a new parent (hence the name). For example, given an interface named `UsageTracked` and an implementation of that interface named `DefaultUsageTracked`, the following aspect declares that all implementors of service interfaces also implement the `UsageTracked` interface (to expose statistics via JMX for example):

你可以使用` @DeclareParents `注释做一个introduction 。此注释用于声明匹配的类型有一个新的父类(因此得名)。例如，给定一个名为` UsageTracked `的接口和一个名为` DefaultUsageTracked `接口的实现，以下方面声明所有服务接口的实现者也实现了` UsageTracked `接口(例如，通过JMX公开统计数据):

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

要实现的接口由带注释的字段的类型决定。` @DeclareParents `注释的` value `属性是AspectJ类型模式。任何匹配类型的bean都实现了` UsageTracked `接口。注意，在前面示例的before通知中，服务bean可以直接用作` UsageTracked `接口的实现。如果以编程方式访问一个bean，你需要编写以下代码:

```java
UsageTracked usageTracked = (UsageTracked) context.getBean("myService");
```

#### 5.4.6. Aspect Instantiation Models
实例化模型方面

This is an advanced topic. If you are just starting out with AOP, you can safely skip it until later.

这是一个高级主题。如果您刚刚开始使用AOP，您可以放心地跳过它，直到以后。

By default, there is a single instance of each aspect within the application context. AspectJ calls this the singleton instantiation model. It is possible to define aspects with alternate lifecycles. Spring supports AspectJ`s `perthis` and `pertarget` instantiation models; `percflow`, `percflowbelow`, and `pertypewithin` are not currently supported.

默认情况下，应用程序上下文中每个方面都有一个实例。AspectJ称之为单例实例化模型。可以用交替的生命周期来定义方面。Spring支持AspectJ的` perthis `和` pertarget `实例化模型;` percflow `， ` percflowbelow `和` pertypewithin `目前不支持。

You can declare a `perthis` aspect by specifying a `perthis` clause in the `@Aspect` annotation. Consider the following example:

您可以通过在` @Aspect `注释中指定` perthis `子句来声明` perthis `方面。考虑下面的例子:

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

在前面的示例中，` perthis `子句的效果是，为执行业务服务的每个惟一服务对象(在切入点表达式匹配的连接点上绑定到` this `的每个惟一对象)创建一个方面实例。方面实例是在第一次调用服务对象上的方法时创建的。当服务对象超出作用域时，方面超出了作用域。在创建方面实例之前，它里面的任何通知都不会运行。一旦创建了方面实例，它内部声明的通知将在匹配的连接点上运行，但仅当服务对象是与此方面相关联的服务对象时才会运行。有关` per `子句的更多信息，请参阅AspectJ编程指南。

The `pertarget` instantiation model works in exactly the same way as `perthis`, but it creates one aspect instance for each unique target object at matched join points.

` pertarget `实例化模型的工作方式与` perthis `完全相同，但是它在匹配的连接点为每个唯一目标对象创建一个方面实例。

#### 5.4.7. An AOP Example AOP的例子

Now that you have seen how all the constituent parts work, we can put them together to do something useful.

现在您已经了解了所有组成部分的工作方式，我们可以将它们组合在一起来做一些有用的事情。

The execution of business services can sometimes fail due to concurrency issues (for example, a deadlock loser). If the operation is retried, it is likely to succeed on the next try. For business services where it is appropriate to retry in such conditions (idempotent operations that do not need to go back to the user for conflict resolution), we want to transparently retry the operation to avoid the client seeing a `PessimisticLockingFailureException`. This is a requirement that clearly cuts across multiple services in the service layer and, hence, is ideal for implementing through an aspect.

业务服务的执行有时会由于并发问题(例如，死锁失败)而失败。如果重新尝试该操作，则下一次尝试很可能会成功。对于在这种条件下适合重试的业务服务(`等元操作`，不需要返回给用户以解决冲突)，我们希望透明地重试操作，以避免客户端看到`pessimistlockingfailureexception`。这是一个明显跨越服务层中的多个服务的需求，因此非常适合通过一个方面实现。

Because we want to retry the operation, we need to use around advice so that we can call `proceed` multiple times. The following listing shows the basic aspect implementation:

因为我们想重试操作，所以需要使用around通知，以便多次调用` proceed `。下面的清单显示了基本的方面实现:

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

请注意，方面实现了`Ordered`接口，因此我们可以将方面的优先级设置得高于事务通知(我们每次重试时都想要一个新的事务)。`maxRetries`和`order`属性都是由Spring配置的。主要动作发生在围绕通知的`doConcurrentOperation`中。请注意，目前，我们对每个` businessService() `应用了重试逻辑。我们试着继续，如果我们失败了由于`PessimisticLockingFailureException`，我们会再次尝试，除非我们耗尽了所有的重试尝试。

The corresponding Spring configuration follows:
相应的spring配置如下:

```xml
<aop:aspectj-autoproxy/>

<bean id="concurrentOperationExecutor" class="com.xyz.myapp.service.impl.ConcurrentOperationExecutor">
    <property name="maxRetries" value="3"/>
    <property name="order" value="100"/>
</bean>
```

To refine the aspect so that it retries only idempotent operations, we might define the following `Idempotent` annotation:

为了细化方面，使它只重试幂等运算，我们可以定义以下`Idempotent`注释:

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation
}
```

We can then use the annotation to annotate the implementation of service operations. The change to the aspect to retry only idempotent operations involves refining the pointcut expression so that only `@Idempotent` operations match, as follows:

然后我们可以使用注释来注释服务操作的实现。对只重试幂等性操作的方面的更改涉及精化切入点表达式，以便只匹配` @Idempotent`操作，如下所示:

```java
@Around("com.xyz.myapp.CommonPointcuts.businessService() && " +
        "@annotation(com.xyz.myapp.service.Idempotent)")
public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
    // ...
}
```

### 5.5. Schema-based AOP Support

基于AOP支持

If you prefer an XML-based format, Spring also offers support for defining aspects using the `aop` namespace tags. The exact same pointcut expressions and advice kinds as when using the @AspectJ style are supported. Hence, in this section we focus on that syntax and refer the reader to the discussion in the previous section ([@AspectJ support](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj)) for an understanding of writing pointcut expressions and the binding of advice parameters.

如果您喜欢基于xml的格式，Spring还提供了使用`aop`名称空间标记定义方面的支持。支持与使用@AspectJ样式时完全相同的切入点表达式和通知类型。因此,在本节中,我们关注语法和参考读者在前一节中讨论([@ aspectj支持](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html # aop-ataspectj))的理解写作切入点表达式和绑定参数的建议。

To use the aop namespace tags described in this section, you need to import the `spring-aop` schema, as described in [XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas). See [the AOP schema](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas-aop) for how to import the tags in the `aop` namespace.

要使用本节中描述的aop名称空间标记，需要导入`spring-aop`模式，如[基于XML模式的配置](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas)中所述。请参阅[AOP模式](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#xsd-schemas-aop)了解如何导入` AOP `名称空间中的标签。

Within your Spring configurations, all aspect and advisor elements must be placed within an `<aop:config>` element (you can have more than one `<aop:config>` element in an application context configuration). An `<aop:config>` element can contain pointcut, advisor, and aspect elements (note that these must be declared in that order).

在你的Spring配置中，所有的aspect和advisor元素都必须放在` <aop:config> `元素中(在应用程序上下文配置中可以有不止一个` <aop:config> `元素)。` <aop:config> `元素可以包含切入点、advisor和方面元素(注意，这些元素必须按照这个顺序声明)。

The `<aop:config>` style of configuration makes heavy use of Spring`s [auto-proxying](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-autoproxy) mechanism. This can cause issues (such as advice not being woven) if you already use explicit auto-proxying through the use of `BeanNameAutoProxyCreator` or something similar. The recommended usage pattern is to use either only the `<aop:config>` style or only the `AutoProxyCreator` style and never mix them. 

配置的`<aop:config>`风格大量使用了Spring的[自动代理](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-autoproxy)机制。如果您已经通过使用` BeanNameAutoProxyCreator `或类似的东西使用显式自动代理，这可能会导致问题(如未编织建议)。推荐的使用模式是要么只使用` <aop:config> `样式，要么只使用` AutoProxyCreator `样式，并且永远不要混合使用它们。

#### 5.5.1. Declaring an Aspect
声明一个方面

When you use the schema support, an aspect is a regular Java object defined as a bean in your Spring application context. The state and behavior are captured in the fields and methods of the object, and the pointcut and advice information are captured in the XML.

在使用模式支持时，方面是在Spring应用程序上下文中定义为bean的常规Java对象。状态和行为在对象的字段和方法中捕获，切入点和通知信息在XML中捕获。

You can declare an aspect by using the `<aop:aspect>` element, and reference the backing bean by using the `ref` attribute, as the following example shows:

你可以通过使用` <aop:aspect> `元素声明一个方面，并通过使用` ref `属性引用支持bean，如下面的例子所示:

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

支持方面的bean(在本例中是` aBean `)当然可以像其他Spring bean一样配置和注入依赖项。

#### 5.5.2. Declaring a Pointcut

声明一个切入点

You can declare a named pointcut inside an `<aop:config>` element, letting the pointcut definition be shared across several aspects and advisors.

你可以在` <aop:config> `元素中声明一个命名的切入点，让切入点定义在几个方面和顾问之间被共享。

A pointcut that represents the execution of any business service in the service layer can be defined as follows:

表示服务层中任何业务服务的执行的切入点可以定义如下:

```xml
<aop:config>
    <aop:pointcut id="businessService"
        expression="execution(* com.xyz.myapp.service.*.*(..))"/>
</aop:config>
```

Note that the pointcut expression itself is using the same AspectJ pointcut expression language as described in [@AspectJ support](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj). If you use the schema based declaration style, you can refer to named pointcuts defined in types (@Aspects) within the pointcut expression. Another way of defining the above pointcut would be as follows:

注意，切入点表达式本身使用的是与[@AspectJ支持](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj)中描述的AspectJ切入点表达式语言相同的东西。如果使用基于模式的声明样式，可以引用切入点表达式中类型(@Aspects)中定义的命名切入点。定义上述切入点的另一种方法如下:

```xml
<aop:config>
    <aop:pointcut id="businessService"
        expression="com.xyz.myapp.CommonPointcuts.businessService()"/>
</aop:config>
```

Assume that you have a `CommonPointcuts` aspect as described in [Sharing Common Pointcut Definitions](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-common-pointcuts).

假设您有一个`commonpointcut`方面，如[共享公共切入点定义](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-common-pointcuts)中所述。

Then declaring a pointcut inside an aspect is very similar to declaring a top-level pointcut, as the following example shows:

然后在一个方面中声明一个切入点非常类似于声明一个顶级切入点，如下面的例子所示:

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

与@AspectJ方面一样，通过使用基于模式定义风格声明的切入点可以收集连接点上下文。例如，下面的切入点收集` this `对象作为连接点上下文，并将其传递给通知:

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

必须声明通知来接收收集到的连接点上下文，方法是包含匹配名称的参数，如下所示:

```java
public void monitor(Object service) {
    // ...
}
```

When combining pointcut sub-expressions, `&&` is awkward within an XML document, so you can use the `and`, `or`, and `not` keywords in place of `&&`, `||`, and `!`, respectively. For example, the previous pointcut can be better written as follows:

在组合切入点子表达式时，`&&`在XML文档中很不方便，所以可以使用`and`、`or`和`not`关键字来代替`&&`、`|`和`!`分别的。例如，前面的切入点可以写得更好:

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

注意，以这种方式定义的切入点由它们的XML`id`引用，不能用作命名切入点来形成复合切入点。因此，基于模式的定义风格中的命名切入点支持比@AspectJ风格提供的支持更有限。

#### 5.5.3. Declaring Advice
声明通知

The schema-based AOP support uses the same five kinds of advice as the @AspectJ style, and they have exactly the same semantics.
基于模式的AOP支持使用与@AspectJ风格相同的五种通知，并且它们具有完全相同的语义。
##### Before Advice

Before advice runs before a matched method execution. It is declared inside an `<aop:aspect>` by using the `<aop:before>` element, as the following example shows:

在通知在匹配的方法执行之前运行。它在` <aop:aspect> `中声明，方法是使用` <aop:before> `元素，如下面的例子所示:

```xml
<aop:aspect id="beforeExample" ref="aBean">
    <aop:before
        pointcut-ref="dataAccessOperation"
        method="doAccessCheck"/>
    ...
</aop:aspect>
```

Here, `dataAccessOperation` is the `id` of a pointcut defined at the top (`<aop:config>`) level. To define the pointcut inline instead, replace the `pointcut-ref` attribute with a `pointcut` attribute, as follows:

这里，` dataAccessOperation `是在顶层(` <aop:config> `)定义的切入点的` id `。要以内联方式定义切入点，用` pointcut `属性替换` pointcut-ref `属性，如下所示:

```xml
<aop:aspect id="beforeExample" ref="aBean">

    <aop:before
        pointcut="execution(* com.xyz.myapp.dao.*.*(..))"
        method="doAccessCheck"/>
    ...

</aop:aspect>
```

As we noted in the discussion of the @AspectJ style, using named pointcuts can significantly improve the readability of your code.

正如我们在关于@AspectJ风格的讨论中提到的，使用命名切入点可以显著提高代码的可读性。

The `method` attribute identifies a method (`doAccessCheck`) that provides the body of the advice. This method must be defined for the bean referenced by the aspect element that contains the advice. Before a data access operation is performed (a method execution join point matched by the pointcut expression), the `doAccessCheck` method on the aspect bean is invoked.

` method `属性标识一个提供通知主体的方法(` doAccessCheck `)。必须为包含通知的方面元素引用的bean定义此方法。在执行数据访问操作(切入点表达式匹配的方法执行连接点)之前，调用方面bean上的` doAccessCheck `方法。

##### After Returning Advice

After returning advice runs when a matched method execution completes normally. It is declared inside an `<aop:aspect>` in the same way as before advice. The following example shows how to declare it:

返回通知后，当匹配的方法执行正常完成时运行。它在` <aop:aspect> `中声明的方式与之前的通知相同。下面的例子展示了如何声明它:

```xml
<aop:aspect id="afterReturningExample" ref="aBean">

    <aop:after-returning
        pointcut-ref="dataAccessOperation"
        method="doAccessCheck"/>
    ...

</aop:aspect>
```

As in the @AspectJ style, you can get the return value within the advice body. To do so, use the `returning` attribute to specify the name of the parameter to which the return value should be passed, as the following example shows:

就像在@AspectJ样式中一样，您可以在通知体中获得返回值。为此，使用` returns `属性来指定应该传递返回值的参数的名称，如下面的例子所示:

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

` doAccessCheck `方法必须声明一个名为` retVal `的参数。这个参数的类型限制匹配的方式与` @ afterreturn `相同。例如，你可以这样声明方法签名:

```java
public void doAccessCheck(Object retVal) {...
```

##### After Throwing Advice

After throwing advice runs when a matched method execution exits by throwing an exception. It is declared inside an `<aop:aspect>` by using the `after-throwing` element, as the following example shows:

当匹配的方法执行通过抛出异常退出时，在抛出通知之后运行。它在` <aop:aspect> `中通过使用` after-throwing `元素声明，如下面的例子所示:

```xml
<aop:aspect id="afterThrowingExample" ref="aBean">

    <aop:after-throwing
        pointcut-ref="dataAccessOperation"
        method="doRecoveryActions"/>

    ...

</aop:aspect>
```

As in the @AspectJ style, you can get the thrown exception within the advice body. To do so, use the `throwing` attribute to specify the name of the parameter to which the exception should be passed as the following example shows:

就像在@AspectJ样式中一样，您可以在通知体中获得抛出的异常。为此，使用` throwing `属性指定应该将异常传递给的参数的名称，如下面的例子所示:

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

`doRecoveryActions`方法必须声明一个名为`dataAccessEx`的参数。该参数的类型限制匹配的方式与`@ afterthrow`中描述的相同。例如，方法签名可以这样声明:

```java
public void doRecoveryActions(DataAccessException dataAccessEx) {...
```

##### After (Finally) Advice

After (finally) advice runs no matter how a matched method execution exits. You can declare it by using the `after` element, as the following example shows:

After (finally)通知运行，不管匹配的方法执行如何退出。你可以使用` after `元素来声明它，如下面的例子所示:

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

最后一种建议是围绕建议。Around通知`环绕`一个匹配的方法执行。它有机会在方法运行之前和之后做工作，并确定何时、如何、甚至方法是否真的要运行。Around通知通常用于以线程安全的方式在方法执行前后共享状态(例如，启动和停止计时器)。总是使用最不有力的建议来满足你的要求。如果建议还没有起作用，就不要使用迂回建议。

You can declare around advice by using the `aop:around` element. The first parameter of the advice method must be of type `ProceedingJoinPoint`. Within the body of the advice, calling `proceed()` on the `ProceedingJoinPoint` causes the underlying method to run. The `proceed` method may also be called with an `Object[]`. The values in the array are used as the arguments to the method execution when it proceeds. See [Around Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-around-advice) for notes on calling `proceed` with an `Object[]`. The following example shows how to declare around advice in XML:

你可以使用` aop:around `元素声明around通知。advice方法的第一个参数必须是类型为`前进联合点`的。在通知体中，在`前进joinpoint `上调用` proceed() `会导致底层方法运行。` proceed `方法也可以用` Object[] `调用。当方法继续执行时，数组中的值用作方法执行的参数。请参阅[Around Advice](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-around-advice)以` Object[] `调用` proceed `的注释。下面的示例演示如何在XML中声明周围的通知

```xml
<aop:aspect id="aroundExample" ref="aBean">

    <aop:around
        pointcut-ref="businessService"
        method="doBasicProfiling"/>

    ...

</aop:aspect>
```

The implementation of the `doBasicProfiling` advice can be exactly the same as in the @AspectJ example (minus the annotation, of course), as the following example shows:

` doBasicProfiling `通知的实现可以与@AspectJ示例中的实现完全相同(当然，减去注释)，如下面的示例所示:


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

基于模式的声明样式支持完全类型化通知，通过按名称匹配切入点参数与通知方法参数，支持的方式与@AspectJ支持的方式相同。详情请参见[Advice Parameters](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params)。如果你想显式地指定参数名称建议方法(不依赖于先前描述的检测策略),可以通过使用`arg-names`属性元素的建议,即以同样的方式对待作为建议中的`argNames`属性注释(如[确定参数名称]中描述(https://docs.spring.io/spring-framework/docs/current/reference/html/core.html aop-ataspectj-advice-params-names))。下面的示例演示如何在XML中指定参数名称

```xml
<aop:before
    pointcut="com.xyz.lib.Pointcuts.anyPublicMethod() and @annotation(auditable)"
    method="audit"
    arg-names="auditable"/>
```

The `arg-names` attribute accepts a comma-delimited list of parameter names.

` arg-names `属性接受一个逗号分隔的参数名列表。

The following slightly more involved example of the XSD-based approach shows some around advice used in conjunction with a number of strongly typed parameters:

下面这个基于xsd的方法稍微复杂一些的例子展示了一些与一些强类型参数一起使用的around建议:

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

接下来是方面。注意这样一个事实:` profile(..) `方法接受许多强类型参数，第一个参数恰好是用于继续方法调用的连接点。这个参数的存在表明` profile(..) `将被用作` around `通知，如下例所示:

```java
package x.y;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class SimpleProfiler {

    public Object profile(ProceedingJoinPoint call, String name, int age) throws Throwable {
        StopWatch clock = new StopWatch("Profiling for `" + name + "` and `" + age + "`");
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

最后，下面的XML配置示例影响对特定连接点执行前面的通知:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- this is the object that will be proxied by Spring`s AOP infrastructure -->
    <bean id="personService" class="x.y.service.DefaultPersonService"/>

    <!-- this is the actual advice itself -->
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

考虑以下驱动程序脚本:

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
有了这样一个Boot类，我们将得到类似于标准输出的输出:
```
StopWatch `Profiling for `Pengo` and `12``: running time (millis) = 0
-----------------------------------------
ms     %     Task name
-----------------------------------------
00000  ?  execution(getFoo)
```

##### Advice Ordering

When multiple pieces of advice need to run at the same join point (executing method) the ordering rules are as described in [Advice Ordering](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-ordering). The precedence between aspects is determined via the `order` attribute in the `<aop:aspect>` element or by either adding the `@Order` annotation to the bean that backs the aspect or by having the bean implement the `Ordered` interface.

当多个通知需要在同一个连接点(执行方法)上运行时，排序规则如[通知排序](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-ordering)所述。方面之间的优先级是通过` <aop:aspect> `元素中的` order `属性确定的，或者通过向支持方面的bean添加` @Order `注释，或者通过让bean实现` Ordered `接口确定的。

In contrast to the precedence rules for advice methods defined in the same `@Aspect` class, when two pieces of advice defined in the same `<aop:aspect>` element both need to run at the same join point, the precedence is determined by the order in which the advice elements are declared within the enclosing `<aop:aspect>` element, from highest to lowest precedence.For example, given an `around` advice and a `before` advice defined in the same `<aop:aspect>` element that apply to the same join point, to ensure that the `around` advice has higher precedence than the `before` advice, the `<aop:around>` element must be declared before the `<aop:before>` element.As a general rule of thumb, if you find that you have multiple pieces of advice defined in the same `<aop:aspect>` element that apply to the same join point, consider collapsing such advice methods into one advice method per join point in each `<aop:aspect>` element or refactor the pieces of advice into separate `<aop:aspect>` elements that you can order at the aspect level. 

与建议方法的优先规则定义在相同的`@Aspect`类,当两块建议定义在相同的< > aop:方面的元素都需要运行在同一连接点,优先级是由元素声明的顺序建议在封闭> < aop:方面的元素,从最高到最低优先级。例如给定一个` around `通知和一个` before `通知，它们定义在同一个` <aop:aspect> `元素中，并应用于同一个连接点，为了确保` around `通知的优先级高于` before `通知，必须在` <aop:around> `元素之前声明` <aop:around> `元素。一般的经验法则,如果你发现你具有多条建议定义在相同的< > aop:方面的元素,应用到相同的连接点,考虑这样的建议方法合并成一个建议每个连接点在每个方法的< > aop:一方面为独立的元素或重构的建议
#### 5.5.4. Introductions

Introductions (known as inter-type declarations in AspectJ) let an aspect declare that advised objects implement a given interface and provide an implementation of that interface on behalf of those objects.

引入(在AspectJ中称为类型间声明)让方面声明被通知的对象实现给定的接口，并代表这些对象提供该接口的实现。

You can make an introduction by using the `aop:declare-parents` element inside an `aop:aspect`. You can use the `aop:declare-parents` element to declare that matching types have a new parent (hence the name). For example, given an interface named `UsageTracked` and an implementation of that interface named `DefaultUsageTracked`, the following aspect declares that all implementors of service interfaces also implement the `UsageTracked` interface. (In order to expose statistics through JMX for example.)

你可以通过在`aop:方面`中使用`aop:声明-父类`元素来进行介绍。你可以使用` aop:declare-parents `元素来声明匹配的类型有一个新的父类(因此得名)。例如，给定一个名为` UsageTracked `的接口和一个名为` DefaultUsageTracked `的接口实现，下面的方面声明所有服务接口的实现者也实现了` UsageTracked `接口。(例如，为了通过JMX公开统计数据。)

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

返回` usageTracking ` bean的类将包含以下方法:

```java
public void recordUsage(UsageTracked usageTracked) {
    usageTracked.incrementUseCount();
}
```

The interface to be implemented is determined by the `implement-interface` attribute. The value of the `types-matching` attribute is an AspectJ type pattern. Any bean of a matching type implements the `UsageTracked` interface. Note that, in the before advice of the preceding example, service beans can be directly used as implementations of the `UsageTracked` interface. To access a bean programmatically, you could write the following:

要实现的接口由` implementation -interface `属性决定。` type -matching `属性的值是AspectJ类型模式。任何匹配类型的bean都实现了` UsageTracked `接口。注意，在前面示例的before通知中，服务bean可以直接用作` UsageTracked `接口的实现。要以编程方式访问bean，你可以写以下代码:

```java
UsageTracked usageTracked = (UsageTracked) context.getBean("myService");
```

#### 5.5.5. Aspect Instantiation Models
实例化模型方面
The only supported instantiation model for schema-defined aspects is the singleton model. Other instantiation models may be supported in future releases.

模式定义的方面唯一受支持的实例化模型是单例模型。将来的版本可能支持其他实例化模型。

#### 5.5.6. Advisors

The concept of `advisors` comes from the AOP support defined in Spring and does not have a direct equivalent in AspectJ. An advisor is like a small self-contained aspect that has a single piece of advice. The advice itself is represented by a bean and must implement one of the advice interfaces described in [Advice Types in Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-api-advice-types). Advisors can take advantage of AspectJ pointcut expressions.
`顾问`的概念来自于Spring中定义的AOP支持，在AspectJ中没有直接的对等物。顾问就像一个小的自包含的方面，只有一条建议。通知本身由bean表示，并且必须实现[Spring中的通知类型](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-api-advice-types)中描述的通知接口之一。顾问可以利用AspectJ切入点表达式。

Spring supports the advisor concept with the `<aop:advisor>` element. You most commonly see it used in conjunction with transactional advice, which also has its own namespace support in Spring. The following example shows an advisor:
Spring用`<aop:advisor>`元素支持advisor概念。您最常看到它与事务通知一起使用，事务通知在Spring中也有自己的名称空间支持。下面的例子显示了一个advisor工具:
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

除了前面示例中使用的` pointcut-ref `属性，您还可以使用` pointcut `属性来定义一个切入点表达式内联。

To define the precedence of an advisor so that the advice can participate in ordering, use the `order` attribute to define the `Ordered` value of the advisor.
要定义advisor的优先级，以便建议可以参与排序，请使用` order `属性来定义advisor的` Ordered `值。
#### 5.5.7. An AOP Schema Example

This section shows how the concurrent locking failure retry example from [An AOP Example](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-example) looks when rewritten with the schema support.

本节展示了[一个AOP示例](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-example)中的并发锁定失败重试示例在使用模式支持重写时的样子。

The execution of business services can sometimes fail due to concurrency issues (for example, a deadlock loser). If the operation is retried, it is likely to succeed on the next try. For business services where it is appropriate to retry in such conditions (idempotent operations that do not need to go back to the user for conflict resolution), we want to transparently retry the operation to avoid the client seeing a `PessimisticLockingFailureException`. This is a requirement that clearly cuts across multiple services in the service layer and, hence, is ideal for implementing through an aspect.

业务服务的执行有时会由于并发问题(例如，死锁失败)而失败。如果重新尝试该操作，则下一次尝试很可能会成功。对于在这种条件下适合重试的业务服务(等元操作，不需要返回给用户以解决冲突)，我们希望透明地重试操作，以避免客户端看到`悲观lockingfailureexception`。这是一个明显跨越服务层中的多个服务的需求，因此非常适合通过一个方面实现。

Because we want to retry the operation, we need to use around advice so that we can call `proceed` multiple times. The following listing shows the basic aspect implementation (which is a regular Java class that uses the schema support):

因为我们想重试操作，所以需要使用around通知，以便多次调用` proceed `。下面的清单显示了基本的方面实现(这是一个使用模式支持的常规Java类):
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

请注意，方面实现了`Ordered`接口，因此我们可以将方面的优先级设置得高于事务通知(我们每次重试时都想要一个新的事务)。`maxRetries`和`order`属性都是由Spring配置的。主要动作发生在` doConcurrentOperation ` around advice方法中。我们试着继续。如果我们以`悲观失败例外`的方式失败了，我们会再次尝试，除非我们已经耗尽了所有的重试尝试。

This class is identical to the one used in the @AspectJ example, but with the annotations removed. 
这个类与@AspectJ示例中使用的类相同，但删除了注释。

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

请注意，目前我们假设所有业务服务都是幂等的。如果情况不是这样，我们可以改进方面，使它只重试真正的幂等操作，通过引入一个`幂等`注释，并使用该注释来注释服务操作的实现，如下面的示例所示:

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation
}
```

The change to the aspect to retry only idempotent operations involves refining the pointcut expression so that only `@Idempotent` operations match, as follows:
对只重试幂等性操作的方面的更改涉及精化切入点表达式，以便只匹配` @幂等性`操作，如下所示:

```xml
<aop:pointcut id="idempotentOperation"
        expression="execution(* com.xyz.myapp.service.*.*(..)) and
        @annotation(com.xyz.myapp.service.Idempotent)"/>
```

### 5.8. Proxying Mechanisms

Spring AOP uses either JDK dynamic proxies or CGLIB to create the proxy for a given target object. JDK dynamic proxies are built into the JDK, whereas CGLIB is a common open-source class definition library (repackaged into `spring-core`).

If the target object to be proxied implements at least one interface, a JDK dynamic proxy is used. All of the interfaces implemented by the target type are proxied. If the target object does not implement any interfaces, a CGLIB proxy is created.

If you want to force the use of CGLIB proxying (for example, to proxy every method defined for the target object, not only those implemented by its interfaces), you can do so. However, you should consider the following issues:

- With CGLIB, `final` methods cannot be advised, as they cannot be overridden in runtime-generated subclasses.
- As of Spring 4.0, the constructor of your proxied object is NOT called twice anymore, since the CGLIB proxy instance is created through Objenesis. Only if your JVM does not allow for constructor bypassing, you might see double invocations and corresponding debug log entries from Spring’s AOP support.

To force the use of CGLIB proxies, set the value of the `proxy-target-class` attribute of the `<aop:config>` element to true, as follows:

```xml
<aop:config proxy-target-class="true">
    <!-- other beans defined here... -->
</aop:config>
```

To force CGLIB proxying when you use the @AspectJ auto-proxy support, set the `proxy-target-class` attribute of the `<aop:aspectj-autoproxy>` element to `true`, as follows:

```xml
<aop:aspectj-autoproxy proxy-target-class="true"/>
```

|      | Multiple `<aop:config/>` sections are collapsed into a single unified auto-proxy creator at runtime, which applies the *strongest* proxy settings that any of the `<aop:config/>` sections (typically from different XML bean definition files) specified. This also applies to the `<tx:annotation-driven/>` and `<aop:aspectj-autoproxy/>` elements.To be clear, using `proxy-target-class="true"` on `<tx:annotation-driven/>`, `<aop:aspectj-autoproxy/>`, or `<aop:config/>` elements forces the use of CGLIB proxies *for all three of them*. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

#### 5.8.1. Understanding AOP Proxies

Spring AOP is proxy-based. It is vitally important that you grasp the semantics of what that last statement actually means before you write your own aspects or use any of the Spring AOP-based aspects supplied with the Spring Framework.

Consider first the scenario where you have a plain-vanilla, un-proxied, nothing-special-about-it, straight object reference, as the following code snippet shows:

```java
public class SimplePojo implements Pojo {

    public void foo() {
        // this next method invocation is a direct call on the 'this' reference
        this.bar();
    }

    public void bar() {
        // some logic...
    }
}
```

If you invoke a method on an object reference, the method is invoked directly on that object reference, as the following image and listing show:

![aop proxy plain pojo call](https://docs.spring.io/spring-framework/docs/current/reference/html/images/aop-proxy-plain-pojo-call.png)

Java

Kotlin

```java
public class Main {

    public static void main(String[] args) {
        Pojo pojo = new SimplePojo();
        // this is a direct method call on the 'pojo' reference
        pojo.foo();
    }
}
```

Things change slightly when the reference that client code has is a proxy. Consider the following diagram and code snippet:

![aop proxy call](https://docs.spring.io/spring-framework/docs/current/reference/html/images/aop-proxy-call.png)

Java

Kotlin

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

Okay, so what is to be done about this? The best approach (the term "best" is used loosely here) is to refactor your code such that the self-invocation does not happen. This does entail some work on your part, but it is the best, least-invasive approach. The next approach is absolutely horrendous, and we hesitate to point it out, precisely because it is so horrendous. You can (painful as it is to us) totally tie the logic within your class to Spring AOP, as the following example shows:

Java

Kotlin

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

Java

Kotlin

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

### 6. Spring AOP APIs

springaop应用程序接口

The previous chapter described the Spring’s support for AOP with @AspectJ and schema-based aspect definitions. In this chapter, we discuss the lower-level Spring AOP APIs. For common applications, we recommend the use of Spring AOP with AspectJ pointcuts as described in the previous chapter.

上一章通过@AspectJ和基于模式的方面定义描述了Spring对AOP的支持。在本章中，我们讨论了较低级别的Spring AOP API。对于常见的应用程序，我们建议将Spring AOP与AspectJ切入点一起使用，如上一章所述。

### 6.1. Pointcut API in Spring

This section describes how Spring handles the crucial pointcut concept.

本节描述了Spring如何处理关键切入点概念。

#### 6.1.1. Concepts 概念

Spring’s pointcut model enables pointcut reuse independent of advice types. You can target different advice with the same pointcut.

Spring的切入点模型使切入点重用不受通知（增强）类型的影响。您可以使用相同的切入点来定位不同的（通知）增强。

The `org.springframework.aop.Pointcut` interface is the central interface, used to target advices to particular classes and methods. The complete interface follows:

`org.springframework.aop.Pointcut`接口是中央接口，用于将建议(通知、增强)定向到特定的类和方法。完整的界面如下：

```java
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
```

Splitting the `Pointcut` interface into two parts allows reuse of class and method matching parts and fine-grained composition operations (such as performing a “union” with another method matcher).

将` Pointcut`接口分为两部分，可以重用类和方法匹配的部分以及细粒度的组合操作（例如与另一个方法匹配器执行`联合`）。

The `ClassFilter` interface is used to restrict the pointcut to a given set of target classes. If the `matches()` method always returns true, all target classes are matched. The following listing shows the `ClassFilter` interface definition:

`ClassFilter`接口用于将切入点限制为给定的一组目标类。如果`matches（）`方法始终返回true，则所有目标类都匹配。以下清单显示了`ClassFilter`接口定义：

```java
public interface ClassFilter {

    boolean matches(Class clazz);
}
```

The `MethodMatcher` interface is normally more important. The complete interface follows:

` MethodMatcher`接口通常更为重要。完整的接口如下：

```java
public interface MethodMatcher {

    boolean matches(Method m, Class targetClass);

    boolean isRuntime();

    boolean matches(Method m, Class targetClass, Object[] args);
}
```

The `matches(Method, Class)` method is used to test whether this pointcut ever matches a given method on a target class. This evaluation can be performed when an AOP proxy is created to avoid the need for a test on every method invocation. If the two-argument `matches` method returns `true` for a given method, and the `isRuntime()` method for the MethodMatcher returns `true`, the three-argument matches method is invoked on every method invocation. This lets a pointcut look at the arguments passed to the method invocation immediately before the target advice starts.

`matches（Method，Class）`方法用于测试此切入点是否与目标类上的给定方法匹配。创建AOP代理时可以执行此评估，以避免需要对每个方法调用进行测试。如果两个参数的matchs方法为给定方法返回true，而`MethodMatcher的isRuntime（）`方法返回true，则每次调用方法都会调用三参数的matchs方法。这使切入点可以在目标建议(通知)开始之前立即查看传递给方法调用的参数。

Most `MethodMatcher` implementations are static, meaning that their `isRuntime()` method returns `false`. In this case, the three-argument `matches` method is never invoked.

大多数`MethodMatcher`实现都是静态的，这意味着它们的`isRuntime（）`方法返回false。在这种情况下，永远不会调用三参数的matchs方法。

|      | If possible, try to make pointcuts static, allowing the AOP framework to cache the results of pointcut evaluation when an AOP proxy is created. |
| ---- | ------------------------------------------------------------ |
|      | 如果可能，请尝试使切入点成为静态，从而允许AOP框架在创建AOP代理时缓存切入点评估的结果。 |

#### 6.1.2. Operations on Pointcuts

切入点操作

Spring supports operations (notably, union and intersection) on pointcuts.

Spring支持切入点上的操作（特别是联合和相交）。

Union means the methods that either pointcut matches. Intersection means the methods that both pointcuts match. Union is usually more useful. You can compose pointcuts by using the static methods in the `org.springframework.aop.support.Pointcuts` class or by using the `ComposablePointcut` class in the same package. However, using AspectJ pointcut expressions is usually a simpler approach.

联合表示两个切入点都匹配的方法。交集是指两个切入点都匹配的方法。联合通常更有用。您可以使用`org.springframework.aop.support.Pointcuts`类中的静态方法或同一包中的`ComposablePointcut`类来编写切入点。但是，使用AspectJ切入点表达式通常是一种更简单的方法。

#### 6.1.3. AspectJ Expression Pointcuts

Since 2.0, the most important type of pointcut used by Spring is `org.springframework.aop.aspectj.AspectJExpressionPointcut`. This is a pointcut that uses an AspectJ-supplied library to parse an AspectJ pointcut expression string.

从2.0开始，Spring使用的最重要的切入点类型是`org.springframework.aop.aspectj.AspectJExpressionPointcut`。这是一个切入点，该切入点使用AspectJ提供的库来解析AspectJ切入点表达式字符串。

See the [previous chapter](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop) for a discussion of supported AspectJ pointcut primitives.

有关受支持的AspectJ切入点原语的讨论，请参见[上一章节]

#### 6.1.4. Convenience Pointcut Implementations

便捷切入点实现

Spring provides several convenient pointcut implementations. You can use some of them directly; others are intended to be subclassed in application-specific pointcuts.

Spring提供了几种方便的切入点实现。您可以直接使用其中一些。其他的则打算在特定于应用程序的切入点中子类化。

##### Static Pointcuts

静态切入点

Static pointcuts are based on the method and the target class and cannot take into account the method’s arguments. Static pointcuts suffice — and are best — for most usages. Spring can evaluate a static pointcut only once, when a method is first invoked. After that, there is no need to evaluate the pointcut again with each method invocation.

静态切入点基于方法和目标类，并且不能考虑方法的参数。静态切入点足以满足大多数用途。首次调用方法时，Spring只能评估一次静态切入点。之后，无需在每次方法调用时再次评估切入点。

The rest of this section describes some of the static pointcut implementations that are included with Spring.

本节的其余部分描述了Spring附带的一些静态切入点实现。

###### Regular Expression Pointcuts

正则表达式切入点

One obvious way to specify static pointcuts is regular expressions. Several AOP frameworks besides Spring make this possible. `org.springframework.aop.support.JdkRegexpMethodPointcut` is a generic regular expression pointcut that uses the regular expression support in the JDK.

指定静态切入点的一种明显方法是正则表达式。除了Spring之外，还有几个AOP框架使之成为可能。 `org.springframework.aop.support.JdkRegexpMethodPointcut`是一个通用的正则表达式切入点，它使用了JDK中的正则表达式支持。

With the `JdkRegexpMethodPointcut` class, you can provide a list of pattern strings. If any of these is a match, the pointcut evaluates to `true`. (As a consequence, the resulting pointcut is effectively the union of the specified patterns.)

使用`JdkRegexpMethodPointcut`类，您可以提供模式字符串列表。如果其中任何一个匹配，则切入点的评估结果为` true`。 （因此，结果切入点实际上是指定模式的并集。）

The following example shows how to use `JdkRegexpMethodPointcut`:

```xml
<bean id="settersAndAbsquatulatePointcut"
        class="org.springframework.aop.support.JdkRegexpMethodPointcut">
    <property name="patterns">
        <list>
            <value>.*set.*</value>
            <value>.*absquatulate</value>
        </list>
    </property>
</bean>
```

Spring provides a convenience class named `RegexpMethodPointcutAdvisor`, which lets us also reference an `Advice` (remember that an `Advice` can be an interceptor, before advice, throws advice, and others). Behind the scenes, Spring uses a `JdkRegexpMethodPointcut`. Using `RegexpMethodPointcutAdvisor` simplifies wiring, as the one bean encapsulates both pointcut and advice, as the following example shows:

Spring提供了一个名为`RegexpMethodPointcutAdvisor`的便利类，它使我们也可以引用一个Advice（记住，Advice可以是拦截器，前置通知，抛出通知等等）。在幕后，Spring使用了`JdkRegexpMethodPointcut`。使用`RegexpMethodPointcutAdvisor`可以简化链接，因为一个bean封装了切入点和建议(通知)，如以下示例所示：

```xml
<bean id="settersAndAbsquatulateAdvisor"
        class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <property name="advice">
        <ref bean="beanNameOfAopAllianceInterceptor"/>
    </property>
    <property name="patterns">
        <list>
            <value>.*set.*</value>
            <value>.*absquatulate</value>
        </list>
    </property>
</bean>
```

You can use `RegexpMethodPointcutAdvisor` with any `Advice` type.

您可以将`RegexpMethodPointcutAdvisor`与任何`Advice`类型一起使用。

###### Attribute-driven Pointcuts

属性驱动的切入点

An important type of static pointcut is a metadata-driven pointcut. This uses the values of metadata attributes (typically, source-level metadata).

静态切入点的一种重要类型是元数据驱动的切入点。这使用元数据属性的值（通常是源级别的元数据）。

##### Dynamic pointcuts

动态切入点

Dynamic pointcuts are costlier to evaluate than static pointcuts. They take into account method arguments as well as static information. This means that they must be evaluated with every method invocation and that the result cannot be cached, as arguments will vary.

动态切入点比静态切入点更昂贵。它们考虑了方法参数以及静态信息。这意味着必须在每次方法调用时对它们进行评估，并且由于参数会有所不同，因此无法缓存结果。

The main example is the `control flow` pointcut.

主要示例是`control flow`切入点。

###### Control Flow Pointcuts

控制流切入点

Spring control flow pointcuts are conceptually similar to AspectJ `cflow` pointcuts, although less powerful. (There is currently no way to specify that a pointcut runs below a join point matched by another pointcut.) A control flow pointcut matches the current call stack. For example, it might fire if the join point was invoked by a method in the `com.mycompany.web` package or by the `SomeCaller` class. Control flow pointcuts are specified by using the `org.springframework.aop.support.ControlFlowPointcut` class.

spring控制流切入点在概念上类似于AspectJ`cflow`切入点，尽管功能不那么强大。 （当前无法指定切入点在与另一个切入点匹配的连接点下运行。）控制流切入点与当前调用堆栈匹配。例如，如果连接点是由`com.mycompany.web`包中的方法或`SomeCaller`类调用的，则可能会触发。控制流切入点是使用org.springframework.aop.support.ControlFlowPointcut类指定的。

|      | Control flow pointcuts are significantly more expensive to evaluate at runtime than even other dynamic pointcuts. In Java 1.4, the cost is about five times that of other dynamic pointcuts. |
| ---- | ------------------------------------------------------------ |
|      | 与其他动态切入点相比，控制流切入点在运行时进行评估要昂贵得多。在Java 1.4中，成本大约是其他动态切入点的五倍。 |

#### 6.1.5. Pointcut Superclasses

切入点超类

Spring provides useful pointcut superclasses to help you to implement your own pointcuts.

Spring提供了有用的切入点超类，以帮助您实现自己的切入点。

Because static pointcuts are most useful, you should probably subclass `StaticMethodMatcherPointcut`. This requires implementing only one abstract method (although you can override other methods to customize behavior). The following example shows how to subclass `StaticMethodMatcherPointcut`:

因为静态切入点是最有用的，所以您可能应该将`StaticMethodMatcherPointcut`子类化。这仅需要实现一个抽象方法（尽管您可以覆盖其他方法以自定义行为）。以下示例显示了如何对`StaticMethodMatcherPointcut`进行子类化：

```java
class TestStaticPointcut extends StaticMethodMatcherPointcut {

    public boolean matches(Method m, Class targetClass) {
        // return true if custom criteria match
    }
}
```

There are also superclasses for dynamic pointcuts. You can use custom pointcuts with any advice type.

还有动态切入点的超类。您可以将自定义切入点与任何建议(通知，增加)类型一起使用。

#### 6.1.6. Custom Pointcuts

自定义切入点

Because pointcuts in Spring AOP are Java classes rather than language features (as in AspectJ), you can declare custom pointcuts, whether static or dynamic. Custom pointcuts in Spring can be arbitrarily complex. However, we recommend using the AspectJ pointcut expression language, if you can.

由于Spring AOP中的切入点是Java类，而不是语言功能（如AspectJ），因此可以声明自定义切入点，无论是静态还是动态。 Spring中的自定义切入点可以任意复杂。但是，如果可以的话，我们建议使用AspectJ切入点表达语言。

|      | Later versions of Spring may offer support for “semantic pointcuts” as offered by JAC — for example, “all methods that change instance variables in the target object.” |
| ---- | ------------------------------------------------------------ |
|      | 更高版本的Spring可能提供对JAC™提供的`semantic pointcuts语义切入点`的支持，例如，`更改目标对象中实例变量的所有方法`。 |

### 6.2. Advice API in Spring

Now we can examine how Spring AOP handles advice.

现在，我们可以检查Spring AOP如何处理建议(通知，增强)。

#### 6.2.1. Advice Lifecycles

通知（增强）生命周期

Each advice is a Spring bean. An advice instance can be shared across all advised objects or be unique to each advised object. This corresponds to per-class or per-instance advice.

每个建议都是一个Spring bean。通知实例可以在所有被通知的对象之间共享，或者对每个被通知的对象是唯一的。这对应于每个类或每个实例的通知。

Per-class advice is used most often. It is appropriate for generic advice, such as transaction advisors. These do not depend on the state of the proxied object or add new state. They merely act on the method and arguments.

每个类建议最常用。适用于一般建议，例如事务顾问。这些不依赖于代理对象的状态或添加新状态。它们仅作用于方法和参数。

Per-instance advice is appropriate for introductions, to support mixins. In this case, the advice adds state to the proxied object.

每个实例的建议都适合引入，以支持混合。在这种情况下，建议将状态添加到代理对象。

You can use a mix of shared and per-instance advice in the same AOP proxy.

您可以在同一AOP代理中混合使用共享和基于实例的建议。

#### 6.2.2. Advice Types in Spring

在spring中的通知（增强）类型

Spring provides several advice types and is extensible to support arbitrary advice types. This section describes the basic concepts and standard advice types.

Spring提供了几种建议类型，并且可以扩展以支持任意建议(通知)类型。本节介绍基本概念和标准建议类型。

##### Interception Around Advice

围绕拦截

The most fundamental advice type in Spring is interception around advice.

Spring中最基本的建议类型是围绕建议的拦截。

Spring is compliant with the AOP `Alliance` interface for around advice that uses method interception. Classes that implement `MethodInterceptor` and that implement around advice should also implement the following interface:

Spring与使用方法拦截的around通知的AOP“Alliance”接口兼容。实现“MethodInterceptor”的类和实现around advice的类也应该实现以下接口:

```java
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
```

The `MethodInvocation` argument to the `invoke()` method exposes the method being invoked, the target join point, the AOP proxy, and the arguments to the method. The `invoke()` method should return the invocation’s result: the return value of the join point.

`invoke（）`方法的`MethodInvocation`参数公开了`被调用的方法`，`目标连接点`，`AOP代理`以及`该方法的参数`。 `invoke（）`方法应返回调用结果：`连接点的返回值。`

The following example shows a simple `MethodInterceptor` implementation:

下面的例子展示了一个简单的`MethodInterceptor`实现：

```java
public class DebugInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Before: invocation=[" + invocation + "]");
        Object rval = invocation.proceed();
        System.out.println("Invocation returned");
        return rval;
    }
}
```

Note the call to the `proceed()` method of `MethodInvocation`. This proceeds down the interceptor chain towards the join point. Most interceptors invoke this method and return its return value. However, a `MethodInterceptor`, like any around advice, can return a different value or throw an exception rather than invoke the proceed method. However, you do not want to do this without good reason.

注意对`MethodInvocation`的`proceed（）`方法的调用。这沿着拦截器链向下到达连接点。大多数拦截器调用此方法并返回其返回值。但是，`MethodInterceptor`（就像任何周围的建议一样）可以返回不同的值或引发异常，而不是调用`proceed`方法。但是，您没有充分的理由就不想这样做。

注意对“MethodInvocation”的“proceed()”方法的调用。这沿着拦截器链一直到连接点。大多数拦截器调用此方法并返回其返回值。然而，一个“MethodInterceptor”，像任何around通知一样，可以返回一个不同的值或者抛出一个异常，而不是调用proceed方法。但是，如果没有充分的理由，您不希望这样做。

|      | `MethodInterceptor` implementations offer interoperability with other AOP Alliance-compliant AOP implementations. The other advice types discussed in the remainder of this section implement common AOP concepts but in a Spring-specific way. While there is an advantage in using the most specific advice type, stick with `MethodInterceptor` around advice if you are likely to want to run the aspect in another AOP framework. Note that pointcuts are not currently interoperable between frameworks, and the AOP Alliance does not currently define pointcut interfaces. |
| ---- | ------------------------------------------------------------ |
|      | `MethodInterceptor`实现提供与其他符合AOP Alliance的AOP实现的互操作性。本节其余部分讨论的其他建议类型将实现常见的AOP概念，但以特定于Spring的方式。尽管使用最具体的建议类型有一个优势，但是如果您可能想在另一个AOP框架中运行方面，则在around通知坚持使用` MethodInterceptor`。请注意，切入点当前无法在框架之间互操作，并且AOP Alliance当前未定义切入点接口。 |

##### Before Advice

前置增强

A simpler advice type is a before advice. This does not need a `MethodInvocation` object, since it is called only before entering the method.

一种更简单的建议（通知）类型是前置通知（增强）。这不需要`MethodInvocation`对象，因为它仅在进入方法之前被调用。

The main advantage of a before advice is that there is no need to invoke the `proceed()` method and, therefore, no possibility of inadvertently failing to proceed down the interceptor chain.

before通知的主要优点是不需要调用' proceed() '方法，因此，不可能无意中沿着拦截器链失败。

The following listing shows the `MethodBeforeAdvice` interface:

以下清单显示了`MethodBeforeAdvice`接口：

```java
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method m, Object[] args, Object target) throws Throwable;
}
```

(Spring’s API design would allow for field before advice, although the usual objects apply to field interception and it is unlikely for Spring to ever implement it.)

（尽管通常的对象适用于字段拦截，并且Spring不太可能实现它，但Spring的API设计允许先于字段通知。）

Note that the return type is `void`. Before advice can insert custom behavior before the join point runs but cannot change the return value. If a before advice throws an exception, it stops further execution of the interceptor chain. The exception propagates back up the interceptor chain. If it is unchecked or on the signature of the invoked method, it is passed directly to the client. Otherwise, it is wrapped in an unchecked exception by the AOP proxy.

请注意，返回类型为` void`。通知可以在联接点运行之前插入自定义行为，但不能更改返回值。如果before通知抛出异常，它将停止拦截器链的进一步执行..异常向上传播到拦截器链。如果未选中它或在被调用方法的签名上，它将直接传递给客户机。否则，它将被AOP代理包装在一个未检查的异常中。

The following example shows a before advice in Spring, which counts all method invocations:

以下示例显示了Spring中的before建议(通知)，该通知计算所有方法调用：

```java
public class CountingBeforeAdvice implements MethodBeforeAdvice {
    private int count;
    public void before(Method m, Object[] args, Object target) throws Throwable {
        ++count;
    }
    public int getCount() {
        return count;
    }
}
```

|      | Before advice can be used with any pointcut. |
| ---- | -------------------------------------------- |
|      | 前置通知与任何切入点一起使用之前。           |

##### Throws Advice

异常通知

Throws advice is invoked after the return of the join point if the join point threw an exception. Spring offers typed throws advice. Note that this means that the `org.springframework.aop.ThrowsAdvice` interface does not contain any methods. It is a tag interface identifying that the given object implements one or more typed throws advice methods. These should be in the following form:

如果连接点抛出异常，则在连接点返回后调用抛出通知。 Spring提供类型化的抛出通知。注意，这意味着`org.springframework.aop.ThrowsAdvice`接口不包含任何方法。它是一个标签接口，用于标识给定对象实现了一个或多个类型化的throws通知方法。这些应采用以下形式：

```java
afterThrowing([Method, args, target], subclassOfThrowable)
```

Only the last argument is required. The method signatures may have either one or four arguments, depending on whether the advice method is interested in the method and arguments. The next two listing show classes that are examples of throws advice.

仅最后一个参数是必需的。方法签名可以具有一个或四个参数，具体取决于通知方法是否对该方法和参数感兴趣。接下来的两个清单显示了抛出通知示例的类。

The following advice is invoked if a `RemoteException` is thrown (including from subclasses):

如果抛出`RemoteException`（包括来自子类），则调用以下建议：

```java
public class RemoteThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(RemoteException ex) throws Throwable {
        // Do something with remote exception
        //做一些事在远程异常发生时
    }
}
```

Unlike the preceding advice, the next example declares four arguments, so that it has access to the invoked method, method arguments, and target object. The following advice is invoked if a `ServletException` is thrown:

与前面的通知不同，下一个示例声明四个参数，以便可以访问被调用的方法，方法参数和目标对象。如果抛出` ServletException`，则调用以下建议：

```java
public class ServletThrowsAdviceWithArguments implements ThrowsAdvice {

    public void afterThrowing(Method m, Object[] args, Object target, ServletException ex) {
        // Do something with all arguments
    }
}
```

The final example illustrates how these two methods could be used in a single class that handles both `RemoteException` and `ServletException`. Any number of throws advice methods can be combined in a single class. The following listing shows the final example:

最后一个例子说明了这两个方法如何在一个同时处理' RemoteException '和' ServletException '的类中使用。可以在一个类中组合任意数量的抛出通知方法。下面的清单显示了最后的示例:

```java
public static class CombinedThrowsAdvice implements ThrowsAdvice {
    public void afterThrowing(RemoteException ex) throws Throwable {
        // Do something with remote exception
    }
    public void afterThrowing(Method m, Object[] args, Object target, ServletException ex) {
        // Do something with all arguments
    }
}
```

|      | If a throws-advice method throws an exception itself, it overrides the original exception (that is, it changes the exception thrown to the user). The overriding exception is typically a RuntimeException, which is compatible with any method signature. However, if a throws-advice method throws a checked exception, it must match the declared exceptions of the target method and is, hence, to some degree coupled to specific target method signatures. *Do not throw an undeclared checked exception that is incompatible with the target method’s signature!* |
| ---- | ------------------------------------------------------------ |
|      | 如果throw -advice方法本身抛出异常，它将覆盖原始异常(也就是说，它将更改抛出给用户的异常)。覆盖异常通常是一个RuntimeException，它与任何方法签名兼容。但是，如果throw -advice方法抛出检查异常，它必须匹配目标方法声明的异常，因此在某种程度上与特定的目标方法签名耦合。不要抛出与目标方法的签名不兼容的未声明的检查异常 |

|      | Throws advice can be used with any pointcut. |
| ---- | -------------------------------------------- |
|      | 抛出通知可以与任何切入点一起使用。           |

##### After Returning Advice

返回通知

An after returning advice in Spring must implement the `org.springframework.aop.AfterReturningAdvice` interface, which the following listing shows:

Spring中的after return那个纸必须实现`org.springframework.aop.AfterReturningAdvice`接口，以下清单显示：

```java
public interface AfterReturningAdvice extends Advice {
    void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable;
}
```

An after returning advice has access to the return value (which it cannot modify), the invoked method, the method’s arguments, and the target.

After returning通知可以访问返回值（它不能修改），调用的方法，方法的参数和目标。

The following after returning advice counts all successful method invocations that have not thrown exceptions:

返回通知后的以下内容将计数所有未引发异常的成功方法调用：

```java
public class CountingAfterReturningAdvice implements AfterReturningAdvice {

    private int count;

    public void afterReturning(Object returnValue, Method m, Object[] args, Object target)
            throws Throwable {
        ++count;
    }

    public int getCount() {
        return count;
    }
}
```

This advice does not change the execution path. If it throws an exception, it is thrown up the interceptor chain instead of the return value.

这个通知不会改变执行路径。如果它抛出异常，则抛出的地方是拦截器链，而不是返回值。

|      | After returning advice can be used with any pointcut. |
| ---- | ----------------------------------------------------- |
|      | after retrunning通知可以与任何切入点一起使用。        |

##### Introduction Advice

简介建议（通知，增强）

Spring treats introduction advice as a special kind of interception advice.

Spring将介绍通知视为一种特殊的拦截通知。

Introduction requires an `IntroductionAdvisor` and an `IntroductionInterceptor` that implement the following interface:

引入需要实现以下接口的`IntroductionAdvisor和IntroductionInterceptor`：

```java
public interface IntroductionInterceptor extends MethodInterceptor {
    boolean implementsInterface(Class intf);
}
```

The `invoke()` method inherited from the AOP Alliance `MethodInterceptor` interface must implement the introduction. That is, if the invoked method is on an introduced interface, the introduction interceptor is responsible for handling the method call — it cannot invoke `proceed()`.

从AOP Alliance的MethodInterceptor接口继承的invoke（）方法必须实现介绍。也就是说，如果被调用的方法在引入的接口上，则引入拦截器负责处理方法调用-它不能调用` proceed（）`。

Introduction advice cannot be used with any pointcut, as it applies only at the class, rather than the method, level. You can only use introduction advice with the `IntroductionAdvisor`, which has the following methods:

简介通知不能与任何切入点一起使用，因为它仅适用于类，而不适用于方法级别。您只能将IntroductionAdvisor的介绍通知与以下方法一起使用：

```java
public interface IntroductionAdvisor extends Advisor, IntroductionInfo {

    ClassFilter getClassFilter();

    void validateInterfaces() throws IllegalArgumentException;
}
public interface IntroductionInfo {
    Class<?>[] getInterfaces();
}
```

There is no `MethodMatcher` and, hence, no `Pointcut` associated with introduction advice. Only class filtering is logical.

没有`MethodMatcher`，因此没有与介绍通知相关的`Pointcut'。只有类过滤是合乎逻辑的。

The `getInterfaces()` method returns the interfaces introduced by this advisor.

getInterfaces()方法返回这个advisor引入的接口。

The `validateInterfaces()` method is used internally to see whether or not the introduced interfaces can be implemented by the configured `IntroductionInterceptor`.

“validateInterfaces()”方法在内部用于查看引入的接口是否可以由配置的“introtioninterceptor”实现。

Consider an example from the Spring test suite and suppose we want to introduce the following interface to one or more objects:

考虑一个来自Spring测试套件的例子，假设我们想要向一个或多个对象引入以下接口:

```java
public interface Lockable {
    void lock();
    void unlock();
    boolean locked();
}
```

This illustrates a mixin. We want to be able to cast advised objects to `Lockable`, whatever their type and call lock and unlock methods. If we call the `lock()` method, we want all setter methods to throw a `LockedException`. Thus, we can add an aspect that provides the ability to make objects immutable without them having any knowledge of it: a good example of AOP.

这说明了混合。我们希望能够将通知对象强制转换为`Lockable`对象，无论其类型如何，并调用锁定和解锁方法。如果我们调用`lock（）`方法，我们希望所有的setter方法都抛出`LockedException`。因此，我们可以添加一个方面，使对象在不了解对象的情况下不可变：AOP的一个很好的例子。

First, we need an `IntroductionInterceptor` that does the heavy lifting. In this case, we extend the `org.springframework.aop.support.DelegatingIntroductionInterceptor` convenience class. We could implement `IntroductionInterceptor` directly, but using `DelegatingIntroductionInterceptor` is best for most cases.

首先，我们需要一个` IntroductionInterceptor`来完成繁重的工作。在这种情况下，我们扩展了`org.springframework.aop.support.DelegatingIntroductionInterceptor`便利类。我们可以直接实现`IntroductionInterceptor`，但是在大多数情况下最好使用`DelegatingIntroductionInterceptor`。

The `DelegatingIntroductionInterceptor` is designed to delegate an introduction to an actual implementation of the introduced interfaces, concealing the use of interception to do so. You can set the delegate to any object using a constructor argument. The default delegate (when the no-argument constructor is used) is `this`. Thus, in the next example, the delegate is the `LockMixin` subclass of `DelegatingIntroductionInterceptor`. Given a delegate (by default, itself), a `DelegatingIntroductionInterceptor` instance looks for all interfaces implemented by the delegate (other than `IntroductionInterceptor`) and supports introductions against any of them. Subclasses such as `LockMixin` can call the `suppressInterface(Class intf)` method to suppress interfaces that should not be exposed. However, no matter how many interfaces an `IntroductionInterceptor` is prepared to support, the `IntroductionAdvisor` used controls which interfaces are actually exposed. An introduced interface conceals any implementation of the same interface by the target.

` DelegatingIntroductionInterceptor`旨在将引入的接口委派给引入接口的实际实现，从而隐瞒了使用拦截的方式。您可以使用构造函数参数将委托设置为任何对象。默认委托（使用无参数构造函数时）为` this`。因此，在下一个示例中，委托是DelegatingIntroductionInterceptor的LockMixin子类。给定一个委托（默认情况下，它本身），一个`DelegatingIntroductionInterceptor`实例查找该委托实现的所有接口（除了` IntroductionInterceptor`之外），并支持针对其中任何一个的介绍。诸如LockMixin之类的子类可以调用`suppressInterface（Class intf）`方法来禁止不应公开的接口。但是，无论IntroductionInterceptor准备支持多少个接口，IntroductionAdvisor都使用控制实际公开哪些接口。引入的接口隐藏了目标对同一接口的任何实现。

Thus, `LockMixin` extends `DelegatingIntroductionInterceptor` and implements `Lockable` itself. The superclass automatically picks up that `Lockable` can be supported for introduction, so we do not need to specify that. We could introduce any number of interfaces in this way.

因此，` LockMixin`扩展了` DelegatingIntroductionInterceptor`并实现了` Lockable`本身。超类会自动选择可以支持`Lockable'的引入，因此我们不需要指定它。我们可以通过这种方式引入任意数量的接口。

Note the use of the `locked` instance variable. This effectively adds additional state to that held in the target object.

注意使用`锁定`实例变量。这有效地将附加状态添加到目标对象中保存的状态。

The following example shows the example `LockMixin` class:

```java
public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {

    private boolean locked;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean locked() {
        return this.locked;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new LockedException();
        }
        return super.invoke(invocation);
    }

}
```

Often, you need not override the `invoke()` method. The `DelegatingIntroductionInterceptor` implementation (which calls the `delegate` method if the method is introduced, otherwise proceeds towards the join point) usually suffices. In the present case, we need to add a check: no setter method can be invoked if in locked mode.

通常，您不需要重写`invoke（）`方法。通常足以实现DelegatingIntroductionInterceptor实现（如果引入了该方法，则调用`delegate`方法，否则进行到连接点处）。在当前情况下，我们需要添加一个检查：如果处于锁定模式，则不能调用任何setter方法。

The required introduction only needs to hold a distinct `LockMixin` instance and specify the introduced interfaces (in this case, only `Lockable`). A more complex example might take a reference to the introduction interceptor (which would be defined as a prototype). In this case, there is no configuration relevant for a `LockMixin`, so we create it by using `new`. The following example shows our `LockMixinAdvisor` class:

所需的简介只需要保存一个不同的`LockMixin`实例并指定所引入的接口（在这种情况下，只需`Lockable`）。一个更复杂的示例可能引用了引入拦截器（将被定义为原型）。在这种情况下，没有与`LockMixin`相关的配置，因此我们使用`new`创建它。以下示例显示了我们的LockMixinAdvisor类：

```java
public class LockMixinAdvisor extends DefaultIntroductionAdvisor {

    public LockMixinAdvisor() {
        super(new LockMixin(), Lockable.class);
    }
}
```

We can apply this advisor very simply, because it requires no configuration. (However, it is impossible to use an `IntroductionInterceptor` without an `IntroductionAdvisor`.) As usual with introductions, the advisor must be per-instance, as it is stateful. We need a different instance of `LockMixinAdvisor`, and hence `LockMixin`, for each advised object. The advisor comprises part of the advised object’s state.

我们可以非常简单地应用此顾问程序，因为它不需要配置。 （但是，没有IntroductionAdvisor的情况下不能使用IntroductionInterceptor。）与介绍一样，顾问程序必须是按实例的，因为它是有状态的。对于每个通知对象，我们需要一个不同的LockMixinAdvisor实例，因此需要一个LockMixin实例。顾问程序包含建议对象状态的一部分。

We can apply this advisor programmatically by using the `Advised.addAdvisor()` method or (the recommended way) in XML configuration, as any other advisor. All proxy creation choices discussed below, including “auto proxy creators,” correctly handle introductions and stateful mixins.

我们可以像其他任何顾问一样，通过使用XML配置中的` Advised.addAdvisor（）`方法或（推荐方式）以编程方式应用此顾问。下面讨论的所有代理创建选择，包括`auto proxy creators,`，都可以正确处理介绍和有状态的混合。

### 6.3. The Advisor API in Spring

Spring的Advisor API

In Spring, an Advisor is an aspect that contains only a single advice object associated with a pointcut expression.

在Spring中，顾问程序是一个方面，它仅包含与切入点表达式关联的单个通知对象。

Apart from the special case of introductions, any advisor can be used with any advice. `org.springframework.aop.support.DefaultPointcutAdvisor` is the most commonly used advisor class. It can be used with a `MethodInterceptor`, `BeforeAdvice`, or `ThrowsAdvice`.

除了介绍的特殊情况外，任何顾问都可以使用任何建议。 `org.springframework.aop.support.DefaultPointcutAdvisor`是最常用的顾问类。它可以和`MethodInterceptor`，`BeforeAdvice`或`ThrowsAdvice`一起使用。

It is possible to mix advisor and advice types in Spring in the same AOP proxy. For example, you could use an interception around advice, throws advice, and before advice in one proxy configuration. Spring automatically creates the necessary interceptor chain.

可以在同一AOP代理中的Spring中混合使用顾问和通知类型。例如，您可以在一个代理配置中使用围绕通知的拦截，抛出通知以及前置通知。 Spring自动创建必要的拦截器链。

### 6.4. Using the `ProxyFactoryBean` to Create AOP Proxies

使用`ProxyFactoryBean`创建AOP代理

If you use the Spring IoC container (an `ApplicationContext` or `BeanFactory`) for your business objects (and you should be!), you want to use one of Spring’s AOP `FactoryBean` implementations. (Remember that a factory bean introduces a layer of indirection, letting it create objects of a different type.)

如果您为您的业务对象使用Spring IoC容器(一个“ApplicationContext”或“BeanFactory”)(您应该这样做!)，那么您需要使用Spring的AOP“FactoryBean”实现之一。(请记住，工厂bean引入了一个间接层，使它能够创建不同类型的对象。)

|      | The Spring AOP support also uses factory beans under the covers. |
| ---- | ------------------------------------------------------------ |
|      | Spring AOP支持也在幕后使用工厂bean。                         |

The basic way to create an AOP proxy in Spring is to use the `org.springframework.aop.framework.ProxyFactoryBean`. This gives complete control over the pointcuts, any advice that applies, and their ordering. However, there are simpler options that are preferable if you do not need such control.

在Spring中创建AOP代理的基本方法是使用`org.springframework.aop.framework.ProxyFactoryBean`。这样可以完全控制切入点，任何适用的通知及其顺序。但是，如果不需要这样的控制，则有一些更简单的选项是可取的。

#### 6.4.1. Basics

基本

The `ProxyFactoryBean`, like other Spring `FactoryBean` implementations, introduces a level of indirection. If you define a `ProxyFactoryBean` named `foo`, objects that reference `foo` do not see the `ProxyFactoryBean` instance itself but an object created by the implementation of the `getObject()` method in the `ProxyFactoryBean` . This method creates an AOP proxy that wraps a target object.

像其他Spring`FactoryBean`实现一样，`ProxyFactoryBean`引入了一个间接级别。如果定义一个名为foo的ProxyFactoryBean，则引用foo的对象本身不会看到ProxyFactoryBean实例，而是看到由ProxyFactoryBean中的getObject（）方法的实现创建的对象。此方法创建一个包装目标对象的AOP代理。

One of the most important benefits of using a `ProxyFactoryBean` or another IoC-aware class to create AOP proxies is that advices and pointcuts can also be managed by IoC. This is a powerful feature, enabling certain approaches that are hard to achieve with other AOP frameworks. For example, an advice may itself reference application objects (besides the target, which should be available in any AOP framework), benefiting from all the pluggability provided by Dependency Injection.

使用“ProxyFactoryBean”或其他支持ioc-aware的类来创建AOP代理的最重要的好处之一是通知和切入点也可以由IoC管理。这是一项强大的功能，可以实现某些其他AOP框架难以实现的方法。例如，通知本身可以引用应用程序对象(除了目标对象，它应该在任何AOP框架中都可用)，这得益于依赖注入提供的所有可插入性。

#### 6.4.2. JavaBean Properties

JavaBean属性

In common with most `FactoryBean` implementations provided with Spring, the `ProxyFactoryBean` class is itself a JavaBean. Its properties are used to:

与Spring提供的大多数`FactoryBean`实现一样，`ProxyFactoryBean`类本身就是JavaBean。其属性用于：

- Specify the target you want to proxy.

  指定您要代理的目标。

- Specify whether to use CGLIB (described later and see also [JDK- and CGLIB-based proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pfb-proxy-types)).

  指定是否使用CGLIB（稍后说明，另请参见[基于JDK和CGLIB的代理]

Some key properties are inherited from `org.springframework.aop.framework.ProxyConfig` (the superclass for all AOP proxy factories in Spring). These key properties include the following:

一些关键属性是从`org.springframework.aop.framework.ProxyConfig`继承的（Spring中所有AOP代理工厂的超类）。这些关键属性包括：

- `proxyTargetClass`: `true` if the target class is to be proxied, rather than the target class’s interfaces. If this property value is set to `true`, then CGLIB proxies are created (but see also [JDK- and CGLIB-based proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pfb-proxy-types)).

  -`proxyTargetClass`：`true`，如果要代理目标类，而不是目标类的接口。如果此属性值设置为true，则创建CGLIB代理（但另请参见[基于JDK和CGLIB的代理]

- `optimize`: Controls whether or not aggressive optimizations are applied to proxies created through CGLIB. You should not blithely use this setting unless you fully understand how the relevant AOP proxy handles optimization. This is currently used only for CGLIB proxies. It has no effect with JDK dynamic proxies.

  -`optimize`：控制是否对通过CGLIB创建的代理应用激进的优化。除非您完全了解相关的AOP代理如何处理优化，否则不要随意使用此设置。当前仅用于CGLIB代理。它对JDK动态代理无效。

- `frozen`: If a proxy configuration is `frozen`, changes to the configuration are no longer allowed. This is useful both as a slight optimization and for those cases when you do not want callers to be able to manipulate the proxy (through the `Advised` interface) after the proxy has been created. The default value of this property is `false`, so changes (such as adding additional advice) are allowed.

  -`frozen`：如果代理配置为`frozen`，则不再允许对该配置进行更改。这是一个轻微的优化，对于在您不希望调用者在创建代理后能够通过代理（通过`advised`接口）操纵代理的情况下很有用。该属性的默认值为` false`，因此允许进行更改（例如添加其他通知）。

- `exposeProxy`: Determines whether or not the current proxy should be exposed in a `ThreadLocal` so that it can be accessed by the target. If a target needs to obtain the proxy and the `exposeProxy` property is set to `true`, the target can use the `AopContext.currentProxy()` method.

  -`exposeProxy`：确定是否应在ThreadLocal中公开当前代理，以便目标可以访问它。如果目标需要获取代理并且将exposeProxy的属性设置为true，则目标可以使用AopContext.currentProxy（）方法。

Other properties specific to `ProxyFactoryBean` include the following:

`ProxyFactoryBean`特有的其他属性包括：

- `proxyInterfaces`: An array of `String` interface names. If this is not supplied, a CGLIB proxy for the target class is used (but see also [JDK- and CGLIB-based proxies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pfb-proxy-types)).

  -`proxyInterfaces`：`String`接口名称的数组。如果未提供，则使用目标类的CGLIB代理（但另请参见[基于JDK和CGLIB的代理]

- `interceptorNames`: A `String` array of `Advisor`, interceptor, or other advice names to apply. Ordering is significant, on a first come-first served basis. That is to say that the first interceptor in the list is the first to be able to intercept the invocation.

  -`interceptorNames`：要应用的` Advisor`，拦截器或其他通知名称的` String`数组。顺序很重要，先到先得。也就是说，列表中的第一个拦截器是第一个能够拦截调用的拦截器。

  The names are bean names in the current factory, including bean names from ancestor factories. You cannot mention bean references here, since doing so results in the `ProxyFactoryBean` ignoring the singleton setting of the advice.

  这些名称是当前工厂中的bean名称，包括来自祖先工厂的bean名称。。您不能在此提及bean引用，因为这样做会导致ProxyFactoryBean忽通知的单例设置。

  You can append an interceptor name with an asterisk (`*`). Doing so results in the application of all advisor beans with names that start with the part before the asterisk to be applied. You can find an example of using this feature in [Using “Global” Advisors](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-global-advisors).

    可以用星号(' * ')附加拦截器名称。这样做的结果是，所有的advisor bean的应用程序的名称都以要应用的星号之前的部分开头。您可以在[使用`全局`顾问`中找到使用此功能的示例

- singleton: Whether or not the factory should return a single object, no matter how often the `getObject()` method is called. Several `FactoryBean` implementations offer such a method. The default value is `true`. If you want to use stateful advice - for example, for stateful mixins - use prototype advices along with a singleton value of `false`.

  -单例：无论调用getObject（）方法的频率如何，工厂是否应返回单个对象。几种`FactoryBean`实现提供了这种方法。默认值为` true`。如果要使用有状态的通知（例如，对于有状态的混合），请使用原型建议以及单例值` false`。

#### 6.4.3. JDK- and CGLIB-based proxies

This section serves as the definitive documentation on how the `ProxyFactoryBean` chooses to create either a JDK-based proxy or a CGLIB-based proxy for a particular target object (which is to be proxied).

本部分用作有关` ProxyFactoryBean`如何选择为特定目标对象（将被代理）创建基于JDK的代理或基于CGLIB的代理的权威性文档。

|      | The behavior of the `ProxyFactoryBean` with regard to creating JDK- or CGLIB-based proxies changed between versions 1.2.x and 2.0 of Spring. The `ProxyFactoryBean` now exhibits similar semantics with regard to auto-detecting interfaces as those of the `TransactionProxyFactoryBean` class. |
| ---- | ------------------------------------------------------------ |
|      | 在Spring的1.2.x和2.0版本之间，关于创建基于JDK或CGLIB的代理的ProxyFactoryBean行为。在自动检测接口方面，ProxyFactoryBean现在表现出与TransactionProxyFactoryBean类类似的语义。 |

If the class of a target object that is to be proxied (hereafter simply referred to as the target class) does not implement any interfaces, a CGLIB-based proxy is created. This is the easiest scenario, because JDK proxies are interface-based, and no interfaces means JDK proxying is not even possible. You can plug in the target bean and specify the list of interceptors by setting the `interceptorNames` property. Note that a CGLIB-based proxy is created even if the `proxyTargetClass` property of the `ProxyFactoryBean` has been set to `false`. (Doing so makes no sense and is best removed from the bean definition, because it is, at best, redundant, and, at worst confusing.)

如果要代理的目标对象的类（以下简称为目标类）没有实现任何接口，则将创建基于CGLIB的代理。这是最简单的情况，因为JDK代理是基于接口的，并且没有接口意味着甚至无法进行JDK代理。您可以插入目标bean并通过设置`interceptorNames`属性来指定拦截器列表。请注意，即使已将` ProxyFactoryBean`的` proxyTargetClass`属性设置为` false`，也会创建基于CGLIB的代理。 （这样做没有任何意义，最好将其从bean定义中删除，因为它充其量是多余的，并且在最糟的情况下会造成混淆。）

If the target class implements one (or more) interfaces, the type of proxy that is created depends on the configuration of the `ProxyFactoryBean`.

如果目标类实现一个（或多个）接口，则创建的代理类型取决于` ProxyFactoryBean`的配置。

If the `proxyTargetClass` property of the `ProxyFactoryBean` has been set to `true`, a CGLIB-based proxy is created. This makes sense and is in keeping with the principle of least surprise. Even if the `proxyInterfaces` property of the `ProxyFactoryBean` has been set to one or more fully qualified interface names, the fact that the `proxyTargetClass` property is set to `true` causes CGLIB-based proxying to be in effect.

如果` ProxyFactoryBean`的` proxyTargetClass`属性已设置为` true`，则将创建基于CGLIB的代理。这是有道理的，并且符合最小惊讶原则。即使将ProxyFactoryBean的proxyInterfaces属性设置为一个或多个完全限定的接口名称，将proxyTargetClass属性设置为true的事实也会使基于CGLIB的代理生效。

If the `proxyInterfaces` property of the `ProxyFactoryBean` has been set to one or more fully qualified interface names, a JDK-based proxy is created. The created proxy implements all of the interfaces that were specified in the `proxyInterfaces` property. If the target class happens to implement a whole lot more interfaces than those specified in the `proxyInterfaces` property, that is all well and good, but those additional interfaces are not implemented by the returned proxy.

如果` ProxyFactoryBean`的` proxyInterfaces`属性已设置为一个或多个完全限定的接口名称，则会创建一个基于JDK的代理。创建的代理实现了`proxyInterfaces`属性中指定的所有接口。如果目标类恰好实现了比`proxyInterfaces`属性指定的更多的接口，那就很好了，但是那些附加的接口不是由返回的代理实现的。

If the `proxyInterfaces` property of the `ProxyFactoryBean` has not been set, but the target class does implement one (or more) interfaces, the `ProxyFactoryBean` auto-detects the fact that the target class does actually implement at least one interface, and a JDK-based proxy is created. The interfaces that are actually proxied are all of the interfaces that the target class implements. In effect, this is the same as supplying a list of each and every interface that the target class implements to the `proxyInterfaces` property. However, it is significantly less work and less prone to typographical errors.

如果尚未设置`ProxyFactoryBean`的`proxyInterfaces`属性，但是目标类确实实现了一个（或多个）接口，则`ProxyFactoryBean`会自动检测到目标类实际上至少实现了一个接口，并创建了一个基于JDK的代理。实际代理的接口是目标类实现的所有接口。实际上，这与将目标类实现的每个接口的列表提供给`proxyInterfaces`属性相同。但是，它的工作量大大减少，而且不容易出现印刷错误。

#### 6.4.4. Proxying Interfaces

代理接口

Consider a simple example of `ProxyFactoryBean` in action. This example involves:

考虑一个简单的`ProxyFactoryBean`示例。此示例涉及：

- A target bean that is proxied. This is the `personTarget` bean definition in the example.

  -被代理的目标bean。这是示例中的“ personTarget” bean定义。

- An `Advisor` and an `Interceptor` used to provide advice.

  -用于提供通知（增强）的`advisor`和`interceptor`。

- An AOP proxy bean definition to specify the target object (the `personTarget` bean), the interfaces to proxy, and the advices to apply.

  -AOP代理Bean定义，用于指定目标对象（` personTarget` Bean），代理接口以及要应用的（增强，通知）建议。

The following listing shows the example:

以下清单显示了示例：

```xml
<bean id="personTarget" class="com.mycompany.PersonImpl">
    <property name="name" value="Tony"/>
    <property name="age" value="51"/>
</bean>

<bean id="myAdvisor" class="com.mycompany.MyAdvisor">
    <property name="someProperty" value="Custom string property value"/>
</bean>

<bean id="debugInterceptor" class="org.springframework.aop.interceptor.DebugInterceptor">
</bean>

<bean id="person"
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="proxyInterfaces" value="com.mycompany.Person"/>

    <property name="target" ref="personTarget"/>
    <property name="interceptorNames">
        <list>
            <value>myAdvisor</value>
            <value>debugInterceptor</value>
        </list>
    </property>
</bean>
```

Note that the `interceptorNames` property takes a list of `String`, which holds the bean names of the interceptors or advisors in the current factory. You can use advisors, interceptors, before, after returning, and throws advice objects. The ordering of advisors is significant.

注意，` interceptorNames`属性采用` String`列表，其中包含当前工厂中的拦截器或顾问程序的bean名称。您可以使用advisors, interceptors, before, after returning, and throws advice objects、拦截器。顾问的顺序很重要。

|      | You might be wondering why the list does not hold bean references. The reason for this is that, if the singleton property of the `ProxyFactoryBean` is set to `false`, it must be able to return independent proxy instances. If any of the advisors is itself a prototype, an independent instance would need to be returned, so it is necessary to be able to obtain an instance of the prototype from the factory. Holding a reference is not sufficient. |
| ---- | ------------------------------------------------------------ |
|      | 您可能想知道为什么列表不包含bean引用。原因是，如果将ProxyFactoryBean的singleton属性设置为false，则它必须能够返回独立的代理实例。如果任何顾问本身就是原型，则需要返回一个独立的实例，因此必须能够从工厂获得原型的实例。保持引用是不够的 |

The `person` bean definition shown earlier can be used in place of a `Person` implementation, as follows:

可以使用前面显示的`person` bean定义来代替`Person`实现，如下所示：

```java
Person person = (Person) factory.getBean("person");
```

Other beans in the same IoC context can express a strongly typed dependency on it, as with an ordinary Java object. The following example shows how to do so:

与普通Java对象一样，在同一IoC上下文中的其他bean可以表达对此的强类型依赖性。以下示例显示了如何执行此操作：

```xml
<bean id="personUser" class="com.mycompany.PersonUser">
    <property name="person"><ref bean="person"/></property>
</bean>
```

The `PersonUser` class in this example exposes a property of type `Person`. As far as it is concerned, the AOP proxy can be used transparently in place of a “real” person implementation. However, its class would be a dynamic proxy class. It would be possible to cast it to the `Advised` interface (discussed later).

在本例中，` PersonUser`类公开了` Person`类型的属性。就其而言，可以透明地使用AOP代理代替`real`人实现。但是，它的类将是一个动态代理类。可以将其转换为`advised`界面（稍后讨论）。

You can conceal the distinction between target and proxy by using an anonymous inner bean. Only the `ProxyFactoryBean` definition is different. The advice is included only for completeness. The following example shows how to use an anonymous inner bean:

您可以使用匿名内部bean隐藏目标和代理之间的区别。仅` ProxyFactoryBean`定义不同。该(通知)建议仅出于完整性考虑。以下示例显示如何使用匿名内部bean：

```xml
<bean id="myAdvisor" class="com.mycompany.MyAdvisor">
    <property name="someProperty" value="Custom string property value"/>
</bean>

<bean id="debugInterceptor" class="org.springframework.aop.interceptor.DebugInterceptor"/>

<bean id="person" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="proxyInterfaces" value="com.mycompany.Person"/>
    <!-- Use inner bean, not local reference to target -->
    <property name="target">
        <bean class="com.mycompany.PersonImpl">
            <property name="name" value="Tony"/>
            <property name="age" value="51"/>
        </bean>
    </property>
    <property name="interceptorNames">
        <list>
            <value>myAdvisor</value>
            <value>debugInterceptor</value>
        </list>
    </property>
</bean>
```

Using an anonymous inner bean has the advantage that there is only one object of type `Person`. This is useful if we want to prevent users of the application context from obtaining a reference to the un-advised object or need to avoid any ambiguity with Spring IoC autowiring. There is also, arguably, an advantage in that the `ProxyFactoryBean` definition is self-contained. However, there are times when being able to obtain the un-advised target from the factory might actually be an advantage (for example, in certain test scenarios).

使用匿名内部bean的优点是只有一个类型为'Person'的对象。如果我们要防止应用程序上下文的用户获取对未通知（增强）对象的引用，或者需要避免使用Spring IoC自动装配的任何歧义，这将非常有用。可以说，还有一个优点是，`ProxyFactoryBean`的定义是独立的。但是，有时能够从工厂获得未经通知（增强）的目标实际上可能是一个优势（例如，在某些测试方案中）。

#### 6.4.5. Proxying Classes

What if you need to proxy a class, rather than one or more interfaces?

如果您需要代理一类，而不是一个或多个接口，该怎么办？

Imagine that in our earlier example, there was no `Person` interface. We needed to advise a class called `Person` that did not implement any business interface. In this case, you can configure Spring to use CGLIB proxying rather than dynamic proxies. To do so, set the `proxyTargetClass` property on the `ProxyFactoryBean` shown earlier to `true`. While it is best to program to interfaces rather than classes, the ability to advise classes that do not implement interfaces can be useful when working with legacy code. (In general, Spring is not prescriptive. While it makes it easy to apply good practices, it avoids forcing a particular approach.)

<<<<<<< HEAD
想象一下，在我们之前的示例中，没有`person`接口，要建议一个名为` Person`的类，该类没有实现任何业务接口。在这种情况下，您可以配置Spring以使用CGLIB代理而不是动态代理。为此，请将前面显示的` ProxyFactoryBean`上的` proxyTargetClass`属性设置为` true`。尽管最好对接口而不是对类进行编程，但是在处理遗留代码时，建议未实现接口的类的功能可能会很有用。 （通常，Spring并不是规定性的。虽然可以轻松地应用良好实践，但可以避免强制采用特定方法。）
=======
想象一下，在我们之前的示例中，没有`person`界面。我们需要建议一个名为` Person`的类，该类没有实现任何业务接口。在这种情况下，您可以配置Spring以使用CGLIB代理而不是动态代理。为此，请将前面显示的` ProxyFactoryBean`上的` proxyTargetClass`属性设置为` true`。尽管最好对接口而不是对类进行编程，但是在处理遗留代码时，通知（增强）未实现接口的类的功能可能会很有用。 （通常，Spring并不是规定性的。虽然可以轻松地应用良好实践，但可以避免强制采用特定方法。）
>>>>>>> 5337e1c61d148307059012c399ace45c874c719d

If you want to, you can force the use of CGLIB in any case, even if you do have interfaces.

如果需要，即使您有接口，也可以在任何情况下强制使用CGLIB。

CGLIB proxying works by generating a subclass of the target class at runtime. Spring configures this generated subclass to delegate method calls to the original target. The subclass is used to implement the Decorator pattern, weaving in the advice.

CGLIB代理通过在运行时生成目标类的子类来工作。 Spring配置此生成的子类以将方法调用委托给原始目标。子类用于实现Decorator模式（装饰者模式），并编织在通知中。

CGLIB proxying should generally be transparent to users. However, there are some issues to consider:

CGLIB代理通常应对用户透明。但是，有一些问题要考虑：

- `Final` methods cannot be advised, as they cannot be overridden.

<<<<<<< HEAD
  -不能通知使用`final`方法，因为它们不能被覆盖。
=======
  -不能建议`final`方法，因为它们不能被覆盖。
>>>>>>> 5337e1c61d148307059012c399ace45c874c719d

- There is no need to add CGLIB to your classpath. As of Spring 3.2, CGLIB is repackaged and included in the spring-core JAR. In other words, CGLIB-based AOP works “out of the box”, as do JDK dynamic proxies.

  -无需将CGLIB添加到您的类路径中。从Spring 3.2开始，CGLIB被重新打包并包含在spring-core JAR中。换句话说，基于CGLIB的AOP就像JDK动态代理一样`out of the box`。

There is little performance difference between CGLIB proxying and dynamic proxies. Performance should not be a decisive consideration in this case.

CGLIB代理和动态代理之间几乎没有性能差异。在这种情况下，性能不应作为决定性的考虑因素。

#### 6.4.6. Using “Global” Advisors

By appending an asterisk to an interceptor name, all advisors with bean names that match the part before the asterisk are added to the advisor chain. This can come in handy if you need to add a standard set of “global” advisors. The following example defines two global advisors:

通过向拦截器名称附加星号，所有具有与星号之前的部分相匹配的bean名称的顾问都会被添加到顾问链中。如果您需要添加一组标准的“全局”顾问，这将非常有用。下面的示例定义了两个全局顾问:

```xml
<bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="service"/>
    <property name="interceptorNames">
        <list>
            <value>global*</value>
        </list>
    </property>
</bean>

<bean id="global_debug" class="org.springframework.aop.interceptor.DebugInterceptor"/>
<bean id="global_performance" class="org.springframework.aop.interceptor.PerformanceMonitorInterceptor"/>
```

### 6.5. Concise Proxy Definitions

简洁的代理定义

Especially when defining transactional proxies, you may end up with many similar proxy definitions. The use of parent and child bean definitions, along with inner bean definitions, can result in much cleaner and more concise proxy definitions.

特别是在定义事务代理时，您可能会得到许多类似的代理定义。使用父bean和子bean定义以及内部bean定义可以产生更干净、更简洁的代理定义。

First, we create a parent, template, bean definition for the proxy, as follows:

首先，我们为代理创建一个父模板bean定义，如下所示:

```xml
<bean id="txProxyTemplate" abstract="true"
        class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
    <property name="transactionManager" ref="transactionManager"/>
    <property name="transactionAttributes">
        <props>
            <prop key="*">PROPAGATION_REQUIRED</prop>
        </props>
    </property>
</bean>
```

This is never instantiated itself, so it can actually be incomplete. Then, each proxy that needs to be created is a child bean definition, which wraps the target of the proxy as an inner bean definition, since the target is never used on its own anyway. The following example shows such a child bean:

它本身从未实例化，因此实际上可能是不完整的。然后，每个需要创建的代理都是一个子bean定义，它将代理的目标包装为内部bean定义，因为无论如何该目标都不会单独使用。以下示例显示了这样的子bean：

```xml
<bean id="myService" parent="txProxyTemplate">
    <property name="target">
        <bean class="org.springframework.samples.MyServiceImpl">
        </bean>
    </property>
</bean>
```

You can override properties from the parent template. In the following example, we override the transaction propagation settings:

首先，我们为代理创建一个父模板bean定义，如下所示:

```xml
<bean id="mySpecialService" parent="txProxyTemplate">
    <property name="target">
        <bean class="org.springframework.samples.MySpecialServiceImpl">
        </bean>
    </property>
    <property name="transactionAttributes">
        <props>
            <prop key="get*">PROPAGATION_REQUIRED,readOnly</prop>
            <prop key="find*">PROPAGATION_REQUIRED,readOnly</prop>
            <prop key="load*">PROPAGATION_REQUIRED,readOnly</prop>
            <prop key="store*">PROPAGATION_REQUIRED</prop>
        </props>
    </property>
</bean>
```

Note that in the parent bean example, we explicitly marked the parent bean definition as being abstract by setting the `abstract` attribute to `true`, as described [previously](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-child-bean-definitions), so that it may not actually ever be instantiated. Application contexts (but not simple bean factories), by default, pre-instantiate all singletons. Therefore, it is important (at least for singleton beans) that, if you have a (parent) bean definition that you intend to use only as a template, and this definition specifies a class, you must make sure to set the `abstract` attribute to `true`. Otherwise, the application context actually tries to pre-instantiate it.

请注意，在父bean的示例中，我们通过将`abstract`属性设置为`true`来显式地将父bean定义标记为抽象，如[先前]，因此它可能实际上不会被实例化。默认情况下，应用程序上下文（但不是简单的bean工厂）会预先实例化所有单例。因此，重要的是（至少对于单例bean），如果您有一个（父）bean定义仅打算用作模板，并且此定义指定了一个类，则必须确保设置`抽象`。属性为` true`。否则，应用程序上下文实际上会尝试对其进行实例化。

### 6.6. Creating AOP Proxies Programmatically with the `ProxyFactory`

使用`ProxyFactory`以编程方式创建AOP代理

It is easy to create AOP proxies programmatically with Spring. This lets you use Spring AOP without dependency on Spring IoC.

使用Spring以编程方式创建AOP代理很容易。这使您可以在不依赖Spring IoC的情况下使用Spring AOP。

The interfaces implemented by the target object are automatically proxied. The following listing shows creation of a proxy for a target object, with one interceptor and one advisor:

由目标对象实现的接口将被自动代理。以下清单显示了使用一个拦截器和一个顾问程序为目标对象创建代理的过程：

```java
ProxyFactory factory = new ProxyFactory(myBusinessInterfaceImpl);
factory.addAdvice(myMethodInterceptor);
factory.addAdvisor(myAdvisor);
MyBusinessInterface tb = (MyBusinessInterface) factory.getProxy();
```

The first step is to construct an object of type `org.springframework.aop.framework.ProxyFactory`. You can create this with a target object, as in the preceding example, or specify the interfaces to be proxied in an alternate constructor.

第一步是构造一个类型为org.springframework.aop.framework.ProxyFactory的对象。您可以使用目标对象创建此对象，如前面的示例中所述，或指定要在替代构造函数中代理的接口。

You can add advices (with interceptors as a specialized kind of advice), advisors, or both and manipulate them for the life of the `ProxyFactory`. If you add an `IntroductionInterceptionAroundAdvisor`, you can cause the proxy to implement additional interfaces.

您可以添加建议（通知）（使用拦截器作为一种特殊的建议(通知)），建议程序，或同时添加两者，并在`ProxyFactory`的整个生命周期内对其进行操作。如果添加`IntroductionInterceptionAroundAdvisor`，则可以使代理实现其他接口。

There are also convenience methods on `ProxyFactory` (inherited from `AdvisedSupport`) that let you add other advice types, such as before and throws advice. `AdvisedSupport` is the superclass of both `ProxyFactory` and `ProxyFactoryBean`.

在ProxyFactory上还有一些便捷方法（从AdisedSupport继承），可让您添加其他通知类型，例如before，throws通知。 AdvisedSupport是ProxyFactory和ProxyFactoryBean的超类。

|      | Integrating AOP proxy creation with the IoC framework is best practice in most applications. We recommend that you externalize configuration from Java code with AOP, as you should in general. |
| ---- | ------------------------------------------------------------ |
|      | 在大多数应用程序中，将AOP代理创建与IoC框架集成在一起是最佳实践。通常，建议您使用AOP从Java代码外部化配置。 |

### 6.7. Manipulating Advised Objects

However you create AOP proxies, you can manipulate them BY using the `org.springframework.aop.framework.Advised` interface. Any AOP proxy can be cast to this interface, no matter which other interfaces it implements. This interface includes the following methods:

无论如何创建AOP代理，您都可以使用org.springframework.aop.framework.Advised接口来操作它们。任何AOP代理都可以强制转换为该接口，无论它实现了哪个其他接口。该接口包括以下方法：

```java
Advisor[] getAdvisors();

void addAdvice(Advice advice) throws AopConfigException;

void addAdvice(int pos, Advice advice) throws AopConfigException;

void addAdvisor(Advisor advisor) throws AopConfigException;

void addAdvisor(int pos, Advisor advisor) throws AopConfigException;

int indexOf(Advisor advisor);

boolean removeAdvisor(Advisor advisor) throws AopConfigException;

void removeAdvisor(int index) throws AopConfigException;

boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException;

boolean isFrozen();
```

The `getAdvisors()` method returns an `Advisor` for every advisor, interceptor, or other advice type that has been added to the factory. If you added an `Advisor`, the returned advisor at this index is the object that you added. If you added an interceptor or other advice type, Spring wrapped this in an advisor with a pointcut that always returns `true`. Thus, if you added a `MethodInterceptor`, the advisor returned for this index is a `DefaultPointcutAdvisor` that returns your `MethodInterceptor` and a pointcut that matches all classes and methods.

getAdvisors（）方法针对已添加到工厂的每个顾问，拦截器或其他通知类型返回一个`顾问`。如果添加了`顾问`，则在此索引处返回的顾问就是您添加的对象。如果添加了拦截器或其他建议类型，Spring会将其包装在带有指向总是返回` true`的切入点的advisor中。因此，如果添加了MethodInterceptor，则为该索引返回的顾问是DefaultPointcutAdvisor，它返回MethodInterceptor和与所有类和方法匹配的切入点。

The `addAdvisor()` methods can be used to add any `Advisor`. Usually, the advisor holding pointcut and advice is the generic `DefaultPointcutAdvisor`, which you can use with any advice or pointcut (but not for introductions).

`addAdvisor（）`方法可用于添加任何`Advisor`。通常，拥有切入点和通知（增强）的顾问是通用的`DefaultPointcutAdvisor`，您可以将其与任何通知或切入点一起使用（但不能用于介绍（introductions）。）

By default, it is possible to add or remove advisors or interceptors even once a proxy has been created. The only restriction is that it is impossible to add or remove an introduction advisor, as existing proxies from the factory do not show the interface change. (You can obtain a new proxy from the factory to avoid this problem.)

默认情况下，即使已创建代理，也可以添加或删除顾问程序或拦截器。唯一的限制是不可能添加或删除introduction advisor，因为工厂中的现有代理不会显示接口更改。 （您可以从工厂获取新的代理来避免此问题。）

The following example shows casting an AOP proxy to the `Advised` interface and examining and manipulating its advice:

以下示例显示了将AOP代理投射到`advised`接口并检查和处理其建议（通知）：

```java
Advised advised = (Advised) myObject;
Advisor[] advisors = advised.getAdvisors();
int oldAdvisorCount = advisors.length;
System.out.println(oldAdvisorCount + " advisors");

// Add an advice like an interceptor without a pointcut
// Will match all proxied methods
// Can use for interceptors, before, after returning or throws advice
//添加建议，例如没有切入点的拦截器
//将匹配所有代理方法

advised.addAdvice(new DebugInterceptor());

// Add selective advice using a pointcut
//使用切入点添加选择性建议
advised.addAdvisor(new DefaultPointcutAdvisor(mySpecialPointcut, myAdvice));

assertEquals("Added two advisors", oldAdvisorCount + 2, advised.getAdvisors().length);
```

|      | It is questionable whether it is advisable (no pun intended) to modify advice on a business object in production, although there are, no doubt, legitimate usage cases. However, it can be very useful in development (for example, in tests). We have sometimes found it very useful to be able to add test code in the form of an interceptor or other advice, getting inside a method invocation that we want to test. (For example, the advice can get inside a transaction created for that method, perhaps to run SQL to check that a database was correctly updated, before marking the transaction for roll back.) |
| ---- | ------------------------------------------------------------ |
|      | 在生产中修改关于业务对象的通知是否可取(没有双关语)值得怀疑，尽管毫无疑问，存在合法的使用案例。但是，它在开发中（例如在测试中）非常有用。有时我们发现以拦截器或其他通知的形式添加测试代码，并进入我们要测试的方法调用中非常有用。 （例如，建议（通知）可以进入为该方法创建的事务内部，也许可以在将事务标记为回滚之前运行SQL以检查数据库是否已正确更新。） |

Depending on how you created the proxy, you can usually set a `frozen` flag. In that case, the `Advised` `isFrozen()` method returns `true`, and any attempts to modify advice through addition or removal results in an `AopConfigException`. The ability to freeze the state of an advised object is useful in some cases (for example, to prevent calling code removing a security interceptor).

根据创建代理的方式，通常可以设置`frozen`标志。在这种情况下，` Advised` `isFrozen（）方法将返回` true`，而通过添加或删除来修改建议的任何尝试都会导致` AopConfigException`。冻结建议对象状态的功能在某些情况下很有用（例如，防止调用代码删除安全拦截器）。

### 6.8. Using the "auto-proxy" facility

使用`auto-proxy`功能

So far, we have considered explicit creation of AOP proxies by using a `ProxyFactoryBean` or similar factory bean.

到目前为止，我们已经考虑通过使用ProxyFactoryBean或类似的工厂bean来显式创建AOP代理。

Spring also lets us use “auto-proxy” bean definitions, which can automatically proxy selected bean definitions. This is built on Spring’s “bean post processor” infrastructure, which enables modification of any bean definition as the container loads.

Spring还允许我们使用`自动代理` Bean定义，该定义可以自动代理选定的Bean定义。它建立在Spring的` bean后处理器bean post processor`基础结构上，该基础结构允许在容器加载时修改任何bean定义。

In this model, you set up some special bean definitions in your XML bean definition file to configure the auto-proxy infrastructure. This lets you declare the targets eligible for auto-proxying. You need not use `ProxyFactoryBean`.

这个模块，您在XML bean定义文件中设置了一些特殊的bean定义，以配置自动代理基础结构。这使您可以声明有资格进行自动代理的目标。您不必使用`ProxyFactoryBean`。

There are two ways to do this:

有两种方法可以做到这一点：

- By using an auto-proxy creator that refers to specific beans in the current context.

  通过使用在当前上下文中引用特定bean的自动代理创建器。

- A special case of auto-proxy creation that deserves to be considered separately: auto-proxy creation driven by source-level metadata attributes.

  自动代理创建的一种特殊情况，值得单独考虑：由源级元数据属性驱动的自动代理创建。

#### 6.8.1. Auto-proxy Bean Definitions

自动代理Bean定义

This section covers the auto-proxy creators provided by the `org.springframework.aop.framework.autoproxy` package.

本部分介绍了org.springframework.aop.framework.autoproxy包提供的自动代理创建者。

##### `BeanNameAutoProxyCreator`

The `BeanNameAutoProxyCreator` class is a `BeanPostProcessor` that automatically creates AOP proxies for beans with names that match literal values or wildcards. The following example shows how to create a `BeanNameAutoProxyCreator` bean:

BeanNameAutoProxyCreator类是BeanPostProcessor，它会自动为名称与文字值或通配符匹配的bean创建AOP代理。以下示例显示了如何创建`BeanNameAutoProxyCreator` bean：

```xml
<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
    <property name="beanNames" value="jdk*,onlyJdk"/>
    <property name="interceptorNames">
        <list>
            <value>myInterceptor</value>
        </list>
    </property>
</bean>
```

As with `ProxyFactoryBean`, there is an `interceptorNames` property rather than a list of interceptors, to allow correct behavior for prototype advisors. Named “interceptors” can be advisors or any advice type.

与ProxyFactoryBean一样，有一个interceptorNames属性而不是拦截器列表，以允许原型顾问程序具有正确的行为。命名为`interceptors`的可以是顾问或任何通知类型。

As with auto-proxying in general, the main point of using `BeanNameAutoProxyCreator` is to apply the same configuration consistently to multiple objects, with minimal volume of configuration. It is a popular choice for applying declarative transactions to multiple objects.

通常，与自动代理一样，使用BeanNameAutoProxyCreator的要点是将相同的配置一致地应用于多个对象，并且配置量最小。将声明式事务应用于多个对象是一种流行的选择。

Bean definitions whose names match, such as `jdkMyBean` and `onlyJdk` in the preceding example, are plain old bean definitions with the target class. An AOP proxy is automatically created by the `BeanNameAutoProxyCreator`. The same advice is applied to all matching beans. Note that, if advisors are used (rather than the interceptor in the preceding example), the pointcuts may apply differently to different beans.

名称匹配的Bean定义，例如上例中的`jdkMyBean`和`onlyJdk`，是带有目标类的普通旧Bean定义。 AOP代理由`BeanNameAutoProxyCreator`自动创建。注意，如果使用了顾问(而不是前面示例中的拦截器)，切入点可能会以不同的方式应用于不同的bean。

##### `DefaultAdvisorAutoProxyCreator`

A more general and extremely powerful auto-proxy creator is `DefaultAdvisorAutoProxyCreator`. This automagically applies eligible advisors in the current context, without the need to include specific bean names in the auto-proxy advisor’s bean definition. It offers the same merit of consistent configuration and avoidance of duplication as `BeanNameAutoProxyCreator`.

一个更通用，功能更强大的自动代理创建器是`DefaultAdvisorAutoProxyCreator`。这会自动在当前上下文中应用合格的顾问程序，而无需在自动代理顾问程序的Bean定义中包括特定的Bean名称。与`BeanNameAutoProxyCreator`一样，它具有一致配置和避免重复的优点。

Using this mechanism involves:

使用此机制涉及：

- Specifying a `DefaultAdvisorAutoProxyCreator` bean definition.

  -指定`DefaultAdvisorAutoProxyCreator` bean定义。

- Specifying any number of advisors in the same or related contexts. Note that these must be advisors, not interceptors or other advices. This is necessary, because there must be a pointcut to evaluate, to check the eligibility of each advice to candidate bean definitions.

  在相同或相关的上下文中指定任意数量的顾问。请注意，这些必须是顾问程序，而不是拦截器或其他建议(通知)。这是必要的，因为必须有一个评估的切入点，以检查每个建议（通知）是否符合候选bean定义。

The `DefaultAdvisorAutoProxyCreator` automatically evaluates the pointcut contained in each advisor, to see what (if any) advice it should apply to each business object (such as `businessObject1` and `businessObject2` in the example).

`DefaultAdvisorAutoProxyCreator`会自动评估每个顾问程序中包含的切入点，以查看应将其应用于每个业务对象的建议(通知)（如果有）（在示例中为诸如businessObject1和businessObject2）。

This means that any number of advisors can be applied automatically to each business object. If no pointcut in any of the advisors matches any method in a business object, the object is not proxied. As bean definitions are added for new business objects, they are automatically proxied if necessary.

这意味着可以将任意数量的顾问程序自动应用于每个业务对象。如果任何顾问中没有切入点匹配业务对象中的任何方法，则该对象不会被代理。当为新的业务对象添加Bean定义时，如有必要，它们会自动被代理。

Auto-proxying in general has the advantage of making it impossible for callers or dependencies to obtain an un-advised object. Calling `getBean("businessObject1")` on this `ApplicationContext` returns an AOP proxy, not the target business object. (The “inner bean” idiom shown earlier also offers this benefit.)

通常，自动代理的优点是使调用者或依赖项不可能获得未通知的对象。在此`ApplicationContext`上调用`getBean（` businessObject1`）`会返回AOP代理，而不是目标业务对象。 （前面显示的` inner bean`惯用语也提供了这一好处。）

The following example creates a `DefaultAdvisorAutoProxyCreator` bean and the other elements discussed in this section:

下面的示例创建一个`DefaultAdvisorAutoProxyCreator` bean和本节中讨论的其他元素：

```xml
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>

<bean class="org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor">
    <property name="transactionInterceptor" ref="transactionInterceptor"/>
</bean>

<bean id="customAdvisor" class="com.mycompany.MyAdvisor"/>

<bean id="businessObject1" class="com.mycompany.BusinessObject1">
    <!-- Properties omitted -->
</bean>

<bean id="businessObject2" class="com.mycompany.BusinessObject2"/>
```

The `DefaultAdvisorAutoProxyCreator` is very useful if you want to apply the same advice consistently to many business objects. Once the infrastructure definitions are in place, you can add new business objects without including specific proxy configuration. You can also easily drop in additional aspects (for example, tracing or performance monitoring aspects) with minimal change to configuration.

如果要将相同的建议(通知)一致地应用于许多业务对象，则` DefaultAdvisorAutoProxyCreator`非常有用。基础结构定义到位后，您可以添加新的业务对象，而无需包括特定的代理配置。您也可以轻松地添加其他方面（例如，跟踪或性能监视方面），而对配置的更改最少。

The `DefaultAdvisorAutoProxyCreator` offers support for filtering (by using a naming convention so that only certain advisors are evaluated, which allows the use of multiple, differently configured, AdvisorAutoProxyCreators in the same factory) and ordering. Advisors can implement the `org.springframework.core.Ordered` interface to ensure correct ordering if this is an issue. The `TransactionAttributeSourceAdvisor` used in the preceding example has a configurable order value. The default setting is unordered.

DefaultAdvisorAutoProxyCreator提供过滤支持（通过使用命名约定，只有特定的建议被评估，这允许在同一个工厂中使用多个不同配置的`advisorautoproxycreator`）和排序。如果有问题，顾问可以实现`org.springframework.core.Ordered`接口以确保正确的排序。前面示例中使用的` TransactionAttributeSourceAdvisor`具有可配置的订单值。默认设置为无序`

### 6.9. Using `TargetSource` Implementations

使用`TargetSource`实现

Spring offers the concept of a `TargetSource`, expressed in the `org.springframework.aop.TargetSource` interface. This interface is responsible for returning the “target object” that implements the join point. The `TargetSource` implementation is asked for a target instance each time the AOP proxy handles a method invocation.

Spring提供了` TargetSource`的概念，在`org.springframework.aop.TargetSource`接口中表达。该接口负责返回实现*连接点*的`targetSource`。每当AOP代理处理方法调用时，都会向`TargetSource`实现请求目标实例。

Developers who use Spring AOP do not normally need to work directly with `TargetSource` implementations, but this provides a powerful means of supporting pooling, hot swappable, and other sophisticated targets. For example, a pooling `TargetSource` can return a different target instance for each invocation, by using a pool to manage instances.

使用Spring AOP的开发人员通常不需要直接使用`TargetSource`实现，但这提供了一种强大的手段来支持池化，热插拔和其他复杂的目标。例如，池` TargetSource`可以通过使用池来管理实例，从而为每次调用返回不同的目标实例。

If you do not specify a `TargetSource`, a default implementation is used to wrap a local object. The same target is returned for each invocation (as you would expect).

如果您未指定`TargetSource`，则使用默认实现包装本地对象。每次调用都会返回相同的目标（与您期望的一样）。

The rest of this section describes the standard target sources provided with Spring and how you can use them.

本节的其余部分描述了Spring随附的标准目标源以及如何使用它们。

|      | When using a custom target source, your target will usually need to be a prototype rather than a singleton bean definition. This allows Spring to create a new target instance when required. |
| ---- | ------------------------------------------------------------ |
|      | 使用自定义目标源时，目标通常需要是原型而不是单例bean定义。这样，Spring可以在需要时创建一个新的目标实例。 |

#### 6.9.1. Hot-swappable Target Sources

可热交换的目标源

The `org.springframework.aop.target.HotSwappableTargetSource` exists to let the target of an AOP proxy be switched while letting callers keep their references to it.

存在`org.springframework.aop.target.HotSwappableTargetSource`的目的是在允许调用者保留对其引用的同时切换`AOP`代理的目标。

Changing the target source’s target takes effect immediately. The `HotSwappableTargetSource` is thread-safe.

更改目标源的目标会立即生效。 ` HotSwappableTargetSource`是线程安全的。

You can change the target by using the `swap()` method on HotSwappableTargetSource, as the follow example shows:

您可以通过在`HotSwappableTargetSource`上使用`swap()`方法来更改目标，如以下示例所示：

```java
HotSwappableTargetSource swapper = (HotSwappableTargetSource) beanFactory.getBean("swapper");
Object oldTarget = swapper.swap(newTarget);
```

The following example shows the required XML definitions:

```xml
<bean id="initialTarget" class="mycompany.OldTarget"/>

<bean id="swapper" class="org.springframework.aop.target.HotSwappableTargetSource">
    <constructor-arg ref="initialTarget"/>
</bean>

<bean id="swappable" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="targetSource" ref="swapper"/>
</bean>
```

The preceding `swap()` call changes the target of the swappable bean. Clients that hold a reference to that bean are unaware of the change but immediately start hitting the new target.

前面的`swap（）`调用更改了可交换bean的目标。拥有对该bean的引用的客户端不知道更改，但立即开始达到新目标。

Although this example does not add any advice (it is not necessary to add advice to use a `TargetSource`), any `TargetSource` can be used in conjunction with arbitrary advice.

尽管此示例未添加任何建议(通知)（使用` TargetSource`无需添加建议），但可以将任何` TargetSource`与任意建议（通知）结合使用。

#### 6.9.2. Pooling Target Sources

汇集目标源

Using a pooling target source provides a similar programming model to stateless session EJBs, in which a pool of identical instances is maintained, with method invocations going to free objects in the pool.

使用池目标源提供了与无状态会话EJB相似的编程模型，在无状态会话EJB中，维护了相同实例的池，方法调用将释放池中的对象。

A crucial difference between Spring pooling and SLSB pooling is that Spring pooling can be applied to any POJO. As with Spring in general, this service can be applied in a non-invasive way.

Spring池和SLSB池之间的关键区别在于，Spring池可以应用于任何POJO。通常，与Spring一样，可以以非侵入性方式应用此服务。

Spring provides support for Commons Pool 2.2, which provides a fairly efficient pooling implementation. You need the `commons-pool` Jar on your application’s classpath to use this feature. You can also subclass `org.springframework.aop.target.AbstractPoolingTargetSource` to support any other pooling API.

Spring提供对Commons Pool 2.2的支持，该池提供了相当有效的池实现。您需要在应用程序的类路径上使用`commons-pool` Jar才能使用此功能。您也可以将org.springframework.aop.target.AbstractPoolingTargetSource子类化，以支持任何其他池化API。

|      | Commons Pool 1.5+ is also supported but is deprecated as of Spring Framework 4.2. |
| ---- | ------------------------------------------------------------ |
|      | 还支持Commons Pool 1.5+，但从Spring Framework 4.2开始不推荐使用。 |

The following listing shows an example configuration:

以下清单显示了一个示例配置：

```xml
<bean id="businessObjectTarget" class="com.mycompany.MyBusinessObject"
        scope="prototype">
    ... properties omitted
</bean>

<bean id="poolTargetSource" class="org.springframework.aop.target.CommonsPool2TargetSource">
    <property name="targetBeanName" value="businessObjectTarget"/>
    <property name="maxSize" value="25"/>
</bean>

<bean id="businessObject" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="targetSource" ref="poolTargetSource"/>
    <property name="interceptorNames" value="myInterceptor"/>
</bean>
```

Note that the target object (`businessObjectTarget` in the preceding example) must be a prototype. This lets the `PoolingTargetSource` implementation create new instances of the target to grow the pool as necessary. See the [javadoc of `AbstractPoolingTargetSource`](https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframeworkaop/target/AbstractPoolingTargetSource.html) and the concrete subclass you wish to use for information about its properties. `maxSize` is the most basic and is always guaranteed to be present.

请注意，目标对象（上例中为` businessObjectTarget`）必须是原型。这使`PoolingTargetSource`实现创建目标的新实例以根据需要扩展池。请参阅[`AbstractPoolingTargetSource`的javadoc]和所需的具体子类用于获取有关其属性的信息。 ` maxSize`是最基本的，并且始终保证存在。

In this case, `myInterceptor` is the name of an interceptor that would need to be defined in the same IoC context. However, you need not specify interceptors to use pooling. If you want only pooling and no other advice, do not set the `interceptorNames` property at all.

在这种情况下，` myInterceptor`是需要在同一IoC上下文中定义的拦截器的名称。但是，您无需指定拦截器即可使用池。如果您只希望池化而没有其他建议（通知），则完全不要设置`interceptorNames`属性。

You can configure Spring to be able to cast any pooled object to the `org.springframework.aop.target.PoolingConfig` interface, which exposes information about the configuration and current size of the pool through an introduction. You need to define an advisor similar to the following:

您可以将Spring配置为能够将任何池化对象投射到`org.springframework.aop.target.PoolingConfig`接口，该接口通过介绍来公开有关池的配置和当前大小的信息。您需要定义类似于以下内容的顾问程序：

```xml
<bean id="poolConfigAdvisor" class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
    <property name="targetObject" ref="poolTargetSource"/>
    <property name="targetMethod" value="getPoolingConfigMixin"/>
</bean>
```

This advisor is obtained by calling a convenience method on the `AbstractPoolingTargetSource` class, hence the use of `MethodInvokingFactoryBean`. This advisor’s name (`poolConfigAdvisor`, here) must be in the list of interceptors names in the `ProxyFactoryBean` that exposes the pooled object.

The cast is defined as follows:

该顾问程序是通过在AbstractPoolingTargetSource类上调用便捷方法而获得的，因此可以使用MethodInvokingFactoryBean。该顾问的名称（此处为poolConfigAdvisor）必须位于公开池对象的ProxyFactoryBean中的拦截器名称列表中。

```java
PoolingConfig conf = (PoolingConfig) beanFactory.getBean("businessObject");
System.out.println("Max pool size is " + conf.getMaxSize());
```

|      | Pooling stateless service objects is not usually necessary. We do not believe it should be the default choice, as most stateless objects are naturally thread safe, and instance pooling is problematic if resources are cached. |
| ---- | ------------------------------------------------------------ |
|      | 通常不需要合并无状态服务对象。我们不认为它应该是默认选择，因为大多数无状态对象自然是线程安全的，并且如果缓存了资源，实例池会成问题。 |

Simpler pooling is available by using auto-proxying. You can set the `TargetSource` implementations used by any auto-proxy creator.

通过使用自动代理，可以实现更简单的池化。您可以设置任何自动代理创建者使用的`TargetSource`实现。

#### 6.9.3. Prototype Target Sources

原型目标源

Setting up a “prototype” target source is similar to setting up a pooling `TargetSource`. In this case, a new instance of the target is created on every method invocation. Although the cost of creating a new object is not high in a modern JVM, the cost of wiring up the new object (satisfying its IoC dependencies) may be more expensive. Thus, you should not use this approach without very good reason.

设置`prototype`目标源类似于设置池化` TargetSource`。在这种情况下，每次方法调用都会创建目标的新实例。尽管在现代JVM中创建新对象的成本并不高，但是连接新对象（满足其IoC依赖性）的成本可能会更高。因此，没有充分的理由就不应使用此方法。

To do this, you could modify the `poolTargetSource` definition shown earlier as follows (we also changed the name, for clarity):

为此，您可以修改前面显示的`poolTargetSource`定义，如下所示（为清楚起见，我们也更改了名称）：

```xml
<bean id="prototypeTargetSource" class="org.springframework.aop.target.PrototypeTargetSource">
    <property name="targetBeanName" ref="businessObjectTarget"/>
</bean>
```

The only property is the name of the target bean. Inheritance is used in the `TargetSource` implementations to ensure consistent naming. As with the pooling target source, the target bean must be a prototype bean definition.

唯一的属性是目标Bean的名称。在TargetSource的实现中使用继承来确保命名的一致性。与池化目标源一样，目标Bean必须是原型Bean定义。

#### 6.9.4. `ThreadLocal` Target Sources

`ThreadLocal`目标源

`ThreadLocal` target sources are useful if you need an object to be created for each incoming request (per thread that is). The concept of a `ThreadLocal` provides a JDK-wide facility to transparently store a resource alongside a thread. Setting up a `ThreadLocalTargetSource` is pretty much the same as was explained for the other types of target source, as the following example shows:

如果您需要为每个传入请求（每个线程）创建一个对象，则`ThreadLocal`目标源很有用。 ` ThreadLocal`的概念提供了一个JDK范围的功能，可以在线程旁边透明地存储资源。设置` ThreadLocalTargetSource`与其他类型的目标源的说明几乎相同，如以下示例所示：

```xml
<bean id="threadlocalTargetSource" class="org.springframework.aop.target.ThreadLocalTargetSource">
    <property name="targetBeanName" value="businessObjectTarget"/>
</bean>
```

|      | `ThreadLocal` instances come with serious issues (potentially resulting in memory leaks) when incorrectly using them in multi-threaded and multi-classloader environments. You should always consider wrapping a threadlocal in some other class and never directly use the `ThreadLocal` itself (except in the wrapper class). Also, you should always remember to correctly set and unset (where the latter simply involves a call to `ThreadLocal.set(null)`) the resource local to the thread. Unsetting should be done in any case, since not unsetting it might result in problematic behavior. Spring’s `ThreadLocal` support does this for you and should always be considered in favor of using `ThreadLocal` instances without other proper handling code. |
| ---- | ------------------------------------------------------------ |
|      | 在多线程和多类加载器环境中错误使用ThreadLocal实例时，会带来严重问题（可能导致内存泄漏）。您应该始终考虑在其他一些类中包装threadlocal，并且绝对不要直接使用ThreadLocal本身（包装类中除外）。同样，您应该始终记住正确地设置和取消设置线程本地资源的设置和取消设置（后者仅涉及对ThreadLocal.set（null）的调用）。在任何情况下都应进行取消设置，因为不取消设置可能会导致出现问题。 Spring的ThreadLocal支持为您做到了这一点，应该始终考虑使用ThreadLocal实例，而无需其他适当的处理代码。 |

### 6.10. Defining New Advice Types

定义新的通知（增强）类型

Spring AOP is designed to be extensible. While the interception implementation strategy is presently used internally, it is possible to support arbitrary advice types in addition to the interception around advice, before, throws advice, and after returning advice.

Spring AOP被设计为可扩展的。尽管目前在内部使用拦截实现策略，但是除了在环绕通知，在建议之前，抛出建议和返回建议之后进行拦截之外，还可以支持任意建议类型。

The `org.springframework.aop.framework.adapter` package is an SPI package that lets support for new custom advice types be added without changing the core framework. The only constraint on a custom `Advice` type is that it must implement the `org.aopalliance.aop.Advice` marker interface.

org.springframework.aop.framework.adapter软件包是一个SPI软件包，可以在不更改核心框架的情况下添加对新的自定义建议(通知)类型的支持。对自定义` Advice`类型的唯一限制是它必须实现` org.aopalliance.aop.Advice`标记接口。

See the [`org.springframework.aop.framework.adapter`](https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/aop/framework/adapter/package-frame.html) javadoc for further information.

### 7.Null-safety 安全零位

###  9.Logging 登录中

### 10.Appendix 附录