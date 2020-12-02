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