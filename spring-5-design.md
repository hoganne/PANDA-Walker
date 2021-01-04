# spring5_design

##  SOLID（单一功能、开闭原则、里氏替换、接口隔离以及依赖反转）

### **1 单一职责原则（SRP）**

一个对象应该只包含单一的职责，并且该职责被完整地封装在一个类中，即又定义有且仅有一个原因使类变更。（甲类负责两个不同的职责：职责A，职责B。当由于职责A需求发生改变而需要修改类T时，有可能会导致原本运行正常的职责B功能发生故障。也就是说职责A和B被耦合在了一起”）。

### **2 开放封闭原则（OCP)**

实体应该对扩展是开放的，对修改是封闭的。即可扩展(extension)，不可修改(modification)。

### **3 里氏替换原则（LSP）**

一个对象在其出现的任何地方，都可以用子类实例做替换，并且不会导致程序的错误。
经典的例子: 正方形不是长方形的子类。原因是正方形多了一个属性“长 == 宽”。这时，对正方形类设置不同的长和宽，计算面积的结果是最后设置那项的平方，而不是长*宽，从而发生了与长方形不一致的行为。如果程序依赖了长方形的面积计算方式，并使用正方形替换了长方形，实际表现与预期不符。

### **4 接口隔离原则（ISP）**

接口隔离原则表明客户端不应该被强迫实现一些他们不会使用的接口，应该把胖接口中的方法分组，然后用多个接口替代它，每个接口服务于一个子模块。简单地说，就是使用多个专门的接口比使用单个接口要好很多。

ISP的主要观点如下：

1）一个类对另外一个类的依赖性应当是建立在最小的接口上的。

ISP可以达到不强迫客户（接口的使用方法）依赖于他们不用的方法，接口的实现类应该只呈现为单一职责的角色（遵循SRP原则）

ISP还可以降低客户之间的相互影响---当某个客户要求提供新的职责（需要变化）而迫使接口发生改变时，影响到其他客户程序的可能性最小。

2）客户端程序不应该依赖它不需要的接口方法（功能）。

客户端程序就应该依赖于它不需要的接口方法（功能），那依赖于什么？依赖它所需要的接口。客户端需要什么接口就是提供什么接口，把不需要的接口剔除，这就要求对接口进行细化，保证其纯洁性。

### **5 依赖倒置原则（DIP）**

抽象不应该依赖于细节，细节应当依赖于抽象。换言之，要针对抽象（接口）编程，而不是针对实现细节编程。

开闭原则（OCP）是面向对象设计原则的基础也是整个设计的一个终极目标，而依赖倒置原则（DIP )则是实现OCP原则的一个基础，换句话说开闭原则（OCP）是你盖一栋大楼的设计蓝图，那么依赖倒置原则就是盖这栋大楼的一个钢构框架。

## 创建模式 create Patterns

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

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_177.jpg)

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

 ![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_189.jpg)

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

## Structural and Behavioral Patterns(结构模式和行为模式)

让我们继续我们的核心设计模式之旅：

结构设计模式：此类别下的模式用于处理类或对象的组成。在企业应用程序中，有两种常见的技术可以在面向对象的系统中重用功能，如下所示：

继承 Inheritance：用于继承其他类的常用状态和行为。

组成 composition：用于将其他对象组成类的实例变量。它定义了组成对象以获得新功能的方法。

行为设计模式：此类别下的模式表征类或对象与责任交互和分配的方式。这些模式定义企业应用程序中对象之间的通信方法。因此，在这里，您将学习如何使用行为模式来减少复杂的流量控制。此外，您将使用行为模式来封装算法并在运行时动态选择它们。

#### 结构设计模式

在上一节中，我们讨论了创意设计模式以及它们如何根据业务需求为对象创建提供最佳解决方案。创新设计模式仅提供一种在应用程序中创建对象的解决方案，这些对象如何针对特定的业务目标在应用程序中相互合并，结构设计模式就应运而生。

在本章中，我们将探讨结构模式，以及这些模式如何用于通过使用继承或组合来定义应用程序的较大结构来定义对象之间的关系。结构化模式使您能够解决与构建对象之间的关系有关的许多问题。它们向您展示了如何以灵活和可扩展的方式将系统的不同部分粘合在一起。

结构模式可帮助您确保当其中一部分发生更改时，整个结构都不需要更改；在汽车中，您可以用其他供应商更换轮胎，而不会影响汽车的其他部件。它们还向您展示了如何将系统中不适合（但需要使用）的部分重铸为适合的部分。

### The adapter design pattern（适配器模式）

将类的接口转换为客户期望的另一个接口。适配器使类可以协同工作，否则由于接口不兼容而无法实现。-----GoF设计模式：可重用的面向对象软件的元素

适配器设计模式属于`结构设计模式`，根据该设计模式，两个不兼容的类可以一起工作，由于接口不兼容，这些类不能一起工作。此模式充当两个不兼容接口之间的桥梁。当应用程序的两个推断的功能不兼容时，可以使用此模式，但是这些功能需要作为业务需求进行集成。

在许多实际的示例中，我们可以使用适配器模式。假设您有不同类型的电插头，例如圆柱形和矩形插头，如下图所示。如果满足电压要求，则可以在两者之间使用适配器将矩形插头插入圆柱形插座。

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_210.jpg)

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



![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_225.jpg)

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

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_244.jpg)

让我们在不使用Bridge设计模式的情况下创建设计。首先创建一个接口或抽象类**Bank**。然后创建其派生类：**IciciBank**和**HdfcBank**。要在银行中开设帐户，首先要确定帐户类别的类型-**储蓄帐户**和**活期帐户**，这些类别扩展了特定的银行类别（**HdfcBank**和**IciciBank**）。此应用程序中有一个简单的深度继承层次结构。那么，与上图相比，这种设计有什么问题？您会注意到，在此设计中，有两个部分，一个是抽象部分，另一个是实现部分。客户端代码与抽象部分进行交互。当您更新抽象部分时，客户端代码只能访问实现部分的新更改或新功能，这意味着这些部分，抽象和实现是紧密结合的。



现在，让我们看看如何使用Bridge设计模式来改进此示例：

##### 具有网桥设计模式的系统

在下图中，我们使用Bridge设计模式在Bank and Account接口之间创建关系：

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_245.jpg)

![img](E:\learningforalllife\git-workspace\PANDA-Walker\picture\1528771072-8457-5780d2384acdbb60ec07fc3c71a1.png)

让我们看一下下图，了解Bridge设计模式如何解决这些设计问题，如我们未使用Bridge设计模式的示例所示。桥接模式将抽象和实现分为两个类层次结构：

UML用于桥梁设计模式

![图片](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_246.jpg)

我们有一个Account接口，它充当桥接器的实现者，而具体的类SavingAccount和CurrentAccount实现了Account接口。该银行是一个抽象类，它会使用帐户的对象。

### Composite design pattern(复合模式)

将对象组合成树结构以表示部分整个层次结构。复合可以使客户统一对待单个对象和对象组成。--GoF设计模式

在软件工程中，复合模式属于结构设计模式。根据此模式，客户端会将一组相同类型的对象视为单个对象。

Composite设计模式背后的想法是将一组对象组合成一个树形结构，以表示更大的结构应用程序的模块。而且，这种针对客户的结构统一地是单个单元或实例。

Composite设计模式背后的动机是将系统中的对象分为树形结构，而树形结构是节点叶和分支的组合。在树结构中，节点具有许多叶子和其他节点。叶子没有任何东西，这意味着树上没有叶子的孩子。叶子被视为树状结构数据的终点。

让我们看一下下图，它以节点和叶的形式表示树形结构中的数据：

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_255.jpg)





作为开发人员，设计应用程序以使客户端可以跨应用程序统一访问您的对象更加困难，即使该对象是对象的组合或单个对象也是如此。这种设计模式解决了难题，并允许您以某种方式设计对象，以便可以将该对象用作对象和单个单个对象的组合。

这种模式解决了创建分层树结构以为客户提供访问和操纵树中对象的统一方式时所面临的挑战。复合模式是一个不错的选择。在这种情况下，将基本体和复合体视为同质的复杂性较低。

组合设计模式是基于将相似类型的对象组成到树结构中的，您知道每棵树都有三个主要部分：分支，节点和叶。因此，让我们看一下此设计模式中使用的以下术语。

组件 Component：它基本上是树的一个分支，并且该分支还有其他分支，节点和叶子。组件为所有组件（包括复合对象）提供抽象。在该组合物的图案，组件被基本上声明为对象的接口。

Leaf ：这是一个实现所有组件方法的对象。

Composite 组合：它表示为树结构中的一个节点，它具有其他节点和叶子，并且表示一个复合组件。它具有添加子项的方法，也就是说，它表示相同类型对象的集合。它还有其他子组件方法。

让我们看一下下面这个设计模式的UML图：

![image](E:\learningforalllife\git-workspace\PANDA-Walker\picture\Image_256.jpg)

复合设计模式的好处：

1这种模式提供了添加新组件以进行处理的灵活性

2动态地改变现有组件	

3此模式使您可以创建包含单个对象和复合对象的类层次结构

#### Sample implementation of the Composite design pattern

```java
public interface Account {
void accountType();
}
```

```java
public class SavingAccount implements Account{
@Override
public void accountType() {
System.out.println("SAVING ACCOUNT");
}
}
```

```java
public class CurrentAccount implements Account {
@Override
public void accountType() {
System.out.println("CURRENT ACCOUNT");
}
}
```

```java
package com.packt.patterninspring.chapter3.composite.pattern;
import java.util.ArrayList;
import java.util.List;
import com.packt.patterninspring.chapter3.model.Account;
public class CompositeBankAccount implements Account {
//Collection of child accounts.
private List<Account> childAccounts = new ArrayList<Account>();
@Override
public void accountType() {
for (Account account : childAccounts) {
account.accountType();
}
}
//Adds the account to the composition.
public void add(Account account) {
childAccounts.add(account);
}
//Removes the account from the composition.
public void remove(Account account) {
childAccounts.remove(account);
}
}
```



```java
import com.packt.patterninspring.chapter3.model.CurrentAccount;
import com.packt.patterninspring.chapter3.model.SavingAccount;
public class CompositePatternMain {
public static void main(String[] args) {
//Saving Accounts
SavingAccount savingAccount1 = new SavingAccount();
SavingAccount savingAccount2 = new SavingAccount();
//Current Account
CurrentAccount currentAccount1 = new CurrentAccount();
CurrentAccount currentAccount2 = new CurrentAccount();
//Composite Bank Account
CompositeBankAccount compositeBankAccount1 = new
CompositeBankAccount();
CompositeBankAccount compositeBankAccount2 = new
CompositeBankAccount();
CompositeBankAccount compositeBankAccount = new
CompositeBankAccount();
//Composing the bank accounts
compositeBankAccount1.add(savingAccount1);
compositeBankAccount1.add(currentAccount1);
compositeBankAccount2.add(currentAccount2);
compositeBankAccount2.add(savingAccount2);
compositeBankAccount.add(compositeBankAccount2);
compositeBankAccount.add(compositeBankAccount1);
compositeBankAccount.accountType();
}
}
```



### Decorator design pattern

> Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to sub classing for extending functionality.

动态地给对象附加额外的职责。装饰器为扩展功能提供了子类化之外的灵活选择。

在软件工程中，所有GOF结构模式的共同意图是在灵活的企业应用程序中简化对象和类之间的复杂关系。装饰器模式是结构设计模式中的一种特殊类型的设计模式，它使您可以在运行时动态或静态地添加和删除单个对象的行为，而无需从该对象更改其他关联对象的现有行为。类。这种设计模式可以做到这一点，而不会违反面向对象编程的单一职责原则或SOLID原则。

这种设计模式使用继承的构图进行对象关联。它使您可以将功能分为具有特定关注领域的不同具体类。

#### 装饰器设计模式的好处

1此模式使您可以动态和静态地扩展功能，而无需更改现有对象的结构

2通过使用这种模式，您可以动态地向对象添加新职责

3这种模式也称为**包装器**

4该模式使用对象关系的成分来维护SOLID原理

5这种模式通过为每种新的特定功能编写新的类而不是更改应用程序的现有代码来简化编码

![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_271.jpg)

当我将更多的福利计划添加到**SavingAccount时**，此设计将非常复杂，但是当银行为**CurrentAccount**启动相同的计划时会发生什么？显然，这种设计是有缺陷的，但这对于装饰器模式。该模式允许您添加运行时动态行为。在这种情况下，我将创建一个抽象的**AccountDecorator**类来实现**Account**。此外，我将创建**SeniorCitizen**类和**Privilege**类，它扩展了**AccountDecorator，**因为young没有任何额外的好处，因此SavingAccount类不会扩展**AccountDecorator**。设计将是这样的：

![图片](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\Image_272.jpg)

AccountDecorator和Account之间的关系，即正确类型的继承

`AccountDecorator`和`Account`之间的关系，也就是说，为了添加新的行为而不更改现有代码的组合

参与此模式的类和对象是：

1 `Component` （account）：它是对象的接口，可以动态添加职责

2 `ConcreteComponent`（SavingAccount）：这是组件接口的具体类，它定义了可以附加职责的对象

3 `Decorator `（ AccountDecorator ）：它有一个对组件对象的引用，并定义了一个符合组件接口的接口

4 `ConcreteDecorator `（SeniorCitizen and Privilege ）它是装饰器的具体实现，并向组件添加了职责

#### Decorator design pattern in the Spring Framework

Spring框架使用Decorator设计模式来构建重要的功能，例如`事务`，`缓存同步`以及与`安全性相关`的任务。让我们看一下Spring透明地实现此模式的一些功能：

1，将建议（`advice`）编织到Spring应用程序中。它通过`CGLib`代理使用`Decorator`模式。它通过在运行时生成目标类的子类来工作。

2，`BeanDefinitionDecorator`：用于通过应用自定义属性装饰Bean定义。

3，`WebSocketHandlerDecorator` ：用于用其他行为装饰`WebSocketHandler`。



现在让我们转到另一个GOF设计模式-外观设计模式。

#### Facade Design Pattern(门面模式)

Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use. - GOF Design Patterns

为子系统中的一组接口提供统一的接口。Facade定义了一个更高级的接口，使子系统更容易使用

Facade设计模式只是一个接口的接口，以简化客户机代码和子系统类之间的交互。本设计采用GOF结构设计模式。

facade模式的好处:

>这种模式降低了客户机与子系统交互的复杂性
>
>此模式将所有业务服务合并为单个接口，使它们更易于理解
>
>这种模式减少了客户端代码对系统内部工作的依赖

##### Knowing when to use the Facade Pattern

​		假设您正在设计一个系统，这个系统有大量的独立类，也有一组需要实现的服务。这个系统将会非常复杂，因此Facade模式就出现了，它减少了大型系统的复杂性，并简化了客户机代码与大型复杂系统子系统中的一组类的交互。

​		假设您想要开发一个具有大量服务的银行企业应用程序来执行一个任务，例如，`AccountService`用于通过`accountId`获取帐户，`PaymentService`用于支付网关服务，`TransferService`用于将金额从一个帐户转移到另一个帐户。应用程序的客户机代码与所有这些服务交互，将资金从一个帐户转移到另一个帐户。

​		这就是不同的客户如何与银行系统的金额转账过程交互。如以下图所示,在这里你可以看到客户端代码直接与子系统交互类和客户也应该意识到子系统的内部工作类,所以它违反了SOLID设计原则,因为客户机代码紧密耦合的子系统的类银行应用程序:

![image-20201231163404951](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201231163404951.png)

与客户机代码直接与子系统的类交互不同，您可以再引入一个接口，这使得子系统更易于使用，如下图所示。这个接口被称为Facade接口，它基于Facade模式，是一种与子系统交互的简单方式:

![image-20201231165326420](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201231165326420.png)

##### Implementing the Facade design pattern

让我们看看下面的清单，以演示Facade设计模式。

为您的银行应用程序创建子系统服务类:让我们看看以下内容子系统的PaymentService类。

下面是PaymentService.java文件:

```java
package com.packt.patterninspring.chapter3.facade.pattern;
public class PaymentService {
public static boolean doPayment(){
return true;
}
}
```

让我们为子系统创建另一个服务类AccountService。

```java
public class AccountService {
public static Account getAccount(String accountId) {
return new SavingAccount();
}
}
```

Following is the TransferService.java file:

```java
public class TransferService {
public static void transfer(int amount, Account fromAccount,
Account toAccount) {
System.out.println("Transfering Money");
}
}

```

创建一个Facade服务类来与子系统交互:让我们看看下面的内容用于子系统的Facade接口，然后将此Facade接口作为应用程序中的全局银行服务实现。

Following is the BankingServiceFacade.java file:

```java
public interface BankingServiceFacade {
void moneyTransfer();
}

```

Following is the BankingServiceFacadeImpl.java file:

```java
public class BankingServiceFacadeImpl implements
BankingServiceFacade{
@Override
public void moneyTransfer() {
if(PaymentService.doPayment()){
Account fromAccount = AccountService.getAccount("1");
Account toAccount = AccountService.getAccount("2");
TransferService.transfer(1000, fromAccount, toAccount);
}
}
}

```

创建Facade的客户端

Following is the FacadePatternClient.java file:

```java
public class FacadePatternClient {
public static void main(String[] args) {
BankingServiceFacade serviceFacade = new
BankingServiceFacadeImpl();
serviceFacade.moneyTransfer();
}
}
```

##### The UML structure for the Facade design pattern

参与此模式的类和对象是:

Facade (BankingServiceFacade)

这是一个Facade接口，它知道哪个子系统类负责请求。该接口负责将客户机请求委托给适当的子系统对象。

子系统类(AccountService、TransferService、PaymentService)

这些接口实际上是银行流程系统应用程序的子系统功能。它们负责处理Facade对象分配的流程。这个类别中的接口没有对Facade对象的引用;它们没有Facade的实现细节。它们完全独立于立面对象。让我们看看下面这个模式的UML图

![image-20201231171037147](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201231171037147.png)

##### Facade Pattern in the Spring Framework

在企业应用程序中，如果您在Spring应用程序中工作，facade模式通常在应用程序的业务服务层中使用，以合并所有服务。您还可以在持久层的dao上应用此模式。

现在我们已经看到了Facade设计模式，让我们转向它的另一个变体——代理设计模式。Proxy design pattern.

#### Proxy design pattern

为另一个对象提供代理项或占位符，以控制对该对象的访问。gof设计模式

代理设计模式提供一个类的对象，该对象具有另一个类的功能。该模式属于GOF的结构设计模式

此设计模式的目的是向外界提供另一个类的替代类及其功能。

##### Purpose of the Proxy pattern

让我们来看看以下几点:

>这种模式将实际对象隐藏起来，不让外界看到。
>
>这个模式可以提高性能，因为它是根据需要创建对象的

##### UML structure for the Proxy design pattern

让我们看看下面这个模式的UML图:

![image-20201231171529093](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201231171529093.png)

现在让我们看看这个UML图的不同组件:

>Subject: 由Proxy和RealSubject实现的实际接口。
>
>RealSubject: 真正实现主体。它是一个由代理表示的真实对象。
>
>Proxy: 它是一个代理对象，也是真实对象的实现
>
>Subject: 它维护对真实对象的引用。

#### Implementing the Proxy design pattern

让我们研究以下代码来演示代理模式。

##### Proxy pattern in the Spring Framework

Spring框架透明地使用Spring AOP模块中的代理设计模式。正如我在第一章“开始使用Spring”中讨论的那样

框架5.0和设计模式。在Spring AOP中，您创建对象的代理，以便在Spring应用程序中的横切点上应用横切关注点。在Spring中，其他模块也实现了代理模式，比如Spring的RMI ,HTTP Invoker，Hessian和 Burlap。

让我们看看下一节关于行为设计模式及其底层模式和示例的内容。

### Behavioral design patterns

​		行为设计模式的意图是一组对象之间的交互和合作，以执行一项单个对象不能单独执行的任务。对象之间的交互应该是松散耦合的。

​		在这个类别下的模式描述了类或对象交互和分配责任的方式。让我们在下一节中看看行为设计模式的不同变体。

#### Chain of Responsibility design pattern(责任链设计模式)

通过给多个对象处理请求的机会，避免将请求的发送方与接收方耦合在一起。将接收对象连接起来，并沿着这个链传递请求，直到一个对象处理它。-GOF Design Patterns

责任链设计模式属于行为设计模式的家庭。根据这个模式，请求的发送方和接收方是解耦的。发送方向接收方链发送请求，链中的任何一个接收方都可以处理请求。在此模式中，receiver对象具有另一个receiver对象的引用，因此如果它不处理请求，则将相同的请求传递给另一个receiver对象。

例如，在银行系统中，您可以使用任何ATM机在任何地方取款，因此它是责任链设计模式的活生生的例子之一。

这种模式有以下好处:

>此模式减少了系统中用于处理请求的发送方和接收方对象之间的耦合。
>
>此模式可以更灵活地将职责分配给另一个被引用的对象。
>
>该模式使用组合生成对象链，并且这组对象作为单个单元工作

让我们看看下面的UML图，它显示了责任链设计模式的所有组件:

![image-20201231173128420](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20201231173128420.png)

>Handler:这是系统中处理请求的抽象类或接口。
>
>`ConcreteHandler`:这些是具体的类，它们实现Handler来处理请求，或者将相同的请求传递给Handler链的下一个继承者。
>
>client:它是向链上的处理程序对象发起请求的主应用程序类。

##### Chain of Responsibility pattern in the Spring Framework

Spring Security project实现了责任链模式在Spring框架。Spring Security允许您通过使用安全过滤器链在应用程序中实现身份验证和授权功能。

这是一个高度可配置的框架。您可以使用此过滤器链添加自定义过滤器，以便根据责任链设计模式自定义功能。

现在我们已经了解了责任链设计模式，让我们转向它的另一个变体——命令设计模式。-Command design pattern.

### Command design pattern