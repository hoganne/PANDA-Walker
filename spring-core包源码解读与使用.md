# spring-core包源码解读与使用

## asm

#### AnnotationVisitor

访问Java注释的访问者。这个类的方法必须按照以下顺序调用:(访问| visitEnum | visitAnnotation | visitArray)* visitEnd。

#### AnnotationWriter

一个AnnotationVisitor，生成相应的‘annotation’或'type_annotation'结构，如Java虚拟机规范(jvm)中定义的那样。AnnotationWriter实例可以被链接到一个双向链接列表中，可以使用putAnnotations方法从该列表中生成运行时[in]可见[Type]注释属性。类似地，这种列表的数组可以用来生成运行时[In]VisibleParameterAnnotations属性。

#### Attribute

Java虚拟机规范(jvm)中定义的非标准类、字段、方法或代码属性。

#### ByteVector

动态扩展的字节向量。这个类大致相当于ByteArrayOutputStream之上的DataOutputStream，但效率更高。

#### ClassReader

使ClassVisitor访问类文件结构的解析器，如Java虚拟机规范(jvm)中定义的那样。该类解析类文件内容，并对遇到的每个字段、方法和字节码指令调用给定ClassVisitor的适当访问方法。

#### ClassTooLargeException

由类编写器生成的类的常量池太大时引发的异常。

#### ClassVisitor

访问Java类的访问者。这个类的方法必须按照以下顺序:访问[visitSource] [visitModule] [visitNestHost] [visitOuterClass] (visitAnnotation | visitTypeAnnotation | visitAttribute) * (visitNestMember | visitInnerClass | visitField | visitMethod) * visitEnd。

#### ClassWriter

一个类访问者，生成相应的类文件结构，如Java虚拟机规范(jvm)中定义的那样。它可以单独使用，“从头开始”生成Java类，也可以与一个或多个ClassReader和适配器ClassVisitor一起使用，从一个或多个现有Java类生成修改过的类。

#### ConstantDynamic

使用bootstrap方法在运行时计算值的常量

#### Constants

定义其他不属于ASM公共API的JVM操作码、访问标志和常量。

#### Context

关于正在类读取器（ ClassReader）中解析的类的信息

#### CurrentFrame

关于方法的“当前”指令的输入堆栈映射帧的信息。这是作为一个只包含一条指令的“基本块”的框架子类实现的。

#### Edge

一种方法的控制流图上的边。这个图的每个节点都是一个基本块，用它的第一条指令对应的标签表示。每条边从一个节点到另一个节点，也就是从一个基本块到另一个(分别称为前任块和后继块)。边对应于跳转指令或ret指令，或异常处理程序。

#### FieldVisitor

访问Java字段的访问者。这个类的方法必须按以下顺序调用:(visitAnnotation | visitTypeAnnotation | visitAttribute)* visitEnd。

#### FieldWriter

一个FieldVisitor，它生成一个相应的'field_info'结构，如Java虚拟机规范(jvm)中定义的那样。

#### Frame

基本块的输入和输出堆栈映射帧。栈映射帧的计算分两步进行:

在MethodWriter中的每条指令访问期间，通过模拟指令在所谓的“输出帧”的前一个状态上的动作来更新当前基本块末尾帧的状态。在访问完所有指令后，MethodWriter使用一个定点算法来计算每个基本块的“输入帧”(即在基本块开始的堆栈映射帧)。看到MethodWriter.computeAllFrames。输出堆栈映射帧相对于基本块的输入帧计算，当输出帧计算时还不知道。因此必须能够代表抽象类型,如“类型在输入框位置x当地人”或“位置x的类型的输入堆栈帧”甚至“类型在输入框的位置x, y更多(或更少)数组维度”。这解释了这个类中使用的相当复杂的类型格式，后面会解释。输入和输出帧的局部变量和操作数堆栈包含以下称为“抽象类型”的值。一个抽象类型由4个字段表示，命名为DIM, KIND, FLAGS和VALUE，为了更好的性能和内存效率打包在一个单一的int值中:===================================== |…DIM|KIND|.F|...............VALUE| ===================================== DIM字段，存储在6个最有效位中，是有符号的数组维数(包括从-32到31)。它可以通过DIM_MASK和DIM_SHIFT右移来检索。KIND字段以4位存储，表示使用的值的类型。这4位可以用KIND_MASK检索，并且不需要任何移位，必须等于CONSTANT_KIND、REFERENCE_KIND、UNINITIALIZED_KIND、LOCAL_KIND或STACK_KIND。FLAGS字段以2位存储，最多包含2个布尔标志。目前只定义了一个标志，即TOP_IF_LONG_OR_DOUBLE_FLAG。VALUE字段存储在剩余的20位中，如果KIND等于CONSTANT_KIND，则包含一个常量ITEM_TOP、ITEM_ASM_BOOLEAN、ITEM_ASM_BYTE、ITEM_ASM_CHAR或ITEM_ASM_SHORT、ITEM_INTEGER、ITEM_FLOAT、ITEM_LONG、ITEM_DOUBLE、ITEM_NULL或ITEM_UNINITIALIZED_THIS。符号的索引。在一个符号表的类型表中输入TYPE_TAG符号，如果KIND等于REFERENCE_KIND。符号的索引。符号表的类型表中的UNINITIALIZED_TYPE_TAG符号，如果KIND等于UNINITIALIZED_KIND。如果KIND等于LOCAL_KIND，则本地变量在输入堆栈帧中的索引。堆栈的顶部的位置相对输入堆栈帧,如果等于STACK_KIND,输出帧可以包含任何类型的抽象类型和一系列积极或消极维度(甚至未赋值的类型,由0——这并不对应任何有效的抽象类型的值)。输入帧只能包含CONSTANT_KIND、REFERENCE_KIND或UNINITIALIZED_KIND正数组维度或空数组维度的抽象类型。在所有情况下，类型表只包含内部类型名(禁止使用数组类型描述符-数组维度必须通过DIM字段表示)。对于局部变量和操作数堆栈，LONG和DOUBLE类型总是使用两个槽(LONG + TOP或DOUBLE + TOP)表示。这对于模拟DUPx_y指令是必要的，因为DUPx_y指令的效果取决于栈中抽象类型所表示的具体类型(这些类型并不总是已知的)。

#### Handle

对字段或方法的引用。

#### Handler

关于异常处理程序的信息。对应于代码属性的exception_table数组的元素，如Java虚拟机规范(jvm)中定义的那样。处理程序实例可以用它们的nextHandler字段链接在一起，以描述完整的jvm exception_table数组。

#### Label

方法字节码中的位置。标签用于跳转、跳转和切换指令以及try catch块。标号指定紧随其后的指令。但是请注意，在标签和它指定的指令之间还可以有其他元素(比如其他标签、堆栈映射帧、行号等)。

#### MethodTooLargeException

由类编写器生成的方法的代码属性太大时引发的异常。

#### MethodVisitor

访问Java方法的访问者。这个类的方法必须按照以下顺序:(visitParameter) * (visitAnnotationDefault) (visitAnnotation | visitAnnotableParameterCount | visitParameterAnnotation visitTypeAnnotation | visitAttribute) * (visitCode (visitFrame |访问X <我> < / i >仍然| visitLabel | visitInsnAnnotation | visitTryCatchBlock | visitTryCatchAnnotation | visitLocalVariable | visitLocalVariableAnnotation | visitLineNumber) * visitMaxs] visitEnd。此外,访问X <我> < / i >仍然和visitLabel方法必须调用一系列顺序的字节码指令访问代码,visitInsnAnnotation必须带注释的指令后,调用visitTryCatchBlock之前必须被称为访问标签作为参数传递,visitTryCatchBlockAnnotation必须调用相应的尝试catch块后,visitLocalVariable,visitLocalVariableAnnotation和visitLineNumber方法必须在作为参数传递的标签被访问之后调用。

#### MethodWriter

生成相应的'method_info'结构的MethodVisitor，如Java虚拟机规范(jvm)中定义的那样。

#### ModuleVisitor

访问Java模块的访问者。这个类的方法必须按以下顺序调用:(visitMainClass | (visitPackage | visitRequire | visitExport | visitOpen | visitUse | visitProvide)*) visitEnd. (visitEnd.)

#### ModuleWriter

一个ModuleVisitor，它生成相应的模块、模块包和ModuleMainClass属性，如Java虚拟机规范(jvm)中定义的那样。

#### Opcodes

JVM操作码、访问标志和数组类型代码。这个接口没有定义所有的JVM操作码，因为有些操作码是自动处理的。例如，xLOAD和xSTORE操作码会在可能的情况下被xLOAD_n和xSTORE_n操作码自动替换。因此，在这个接口中没有定义xLOAD_n和xSTORE_n操作码。同样，对于LDC，必要时自动替换为LDC_W或LDC2_W、WIDE、GOTO_W和JSR_W。部分截图如下：

![image-20210310100526273](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20210310100526273.png)

详细如下：

![image-20210310100620747](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20210310100620747.png)

部分实现继承关系：

![image-20210310100753276](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\image-20210310100753276.png)

#### SpringAsmInfo

实用程序类公开与Spring内部重新打包ASM字节码库相关的常量:目前基于ASM 7。x加上小补丁。

#### Symbol

常量池、BootstrapMethods属性或类的(ASM特定的)类型表的条目。

#### SymbolTable

类的常量池条目、BootstrapMethods属性条目和(特定于ASM的)类型表条目。

#### Type

Java字段或方法类型。可以使用这个类简化对类型和方法描述符的操作。

#### TypePath

到类型参数、通配符绑定、数组元素类型或外围类型中的静态内部类型的路径。

#### TypeReference

出现在类、字段或方法声明或指令上的对类型的引用。这样的引用指明了类中引用类型出现的部分(例如'extends'、'implements'或'throws'子句、'new'指令、'catch'子句、类型转换、局部变量声明等)。

## cglib

## core

### 其他

#### AliasRegistry

管理别名的通用接口。作为一个超级接口。

#### AttributeAccessor

接口定义了一个通用契约，用于将元数据附加到任意对象或从任意对象中访问元数据。

#### AttributeAccessorSupport

支持AttributeAccessors的类，提供所有方法的基实现。通过子类来扩展。Serializable if子类和所有属性值都是可序列化的。

#### BridgeMethodResolver

帮助分辨合成桥接方法和被桥接方法。

给定一个合成桥接方法，返回被桥接的方法。桥接方法可以由编译器在扩展其方法具有参数化参数的参数化类型时创建。在运行时调用期间，可以通过反射调用和/或使用桥方法。当试图定位方法上的注释时，明智的做法是适当地检查桥接方法并找到桥接的方法。有关桥接方法使用的更多细节，请参阅Java语言规范。

#### CollectionFactory

能够识别常见Java和Spring集合类型的集合的工厂。主要用于框架内部使用。

#### ConfigurableObjectInputStream

特殊的ObjectInputStream子类，针对特定的类加载器解析类名。用作springframework.remoting.rmi. codebaseawareobjectinputstream的基类。

#### Constants

该类可用于解析在公共静态最终成员中包含常量定义的其他类。该类的asXXXX方法允许通过它们的字符串名称访问这些常量值。考虑类Foo包含public final static int CONSTANT1 = 66;这个类的一个封装Foo.class的实例在参数为"CONSTANT1"的情况下，将从其asNumber方法返回固定值66。这个类非常适合在propertyeditor中使用，使它们能够识别与常量本身相同的名称，并使它们不必维护自己的映射。

#### Conventions

提供方法来支持在整个框架中使用的各种命名和其他约定。主要用于框架内部使用。

#### CoroutinesUtils.class

#### DecoratingClassLoader

用于装饰类加载器的基类，如overridingclassloader和org.springframework.instrument.classloading.shadowclassloader，提供被排除的包和类的通用处理。

#### DecoratingProxy

接口，通过装饰代理来实现，特别是Spring AOP代理，但也可能使用装饰语义定制代理。请注意，如果被装饰的类不属于最初的代理类的层次结构，则应该只实现这个接口。特别是，像Spring AOP CGLIB代理这样的“目标类”代理不应该实现它，因为在目标类上的任何查找都可以简单地在那里的代理类上执行。在core模块中定义，以便允许org.springframework.core.annotation.Annotationawareordercomparator(以及其他没有spring-aop依赖关系的潜在候选对象)用于内省目的，特别是用于注释查找。

#### DefaultParameterNameDiscoverer

ParameterNameDiscoverer策略接口的默认实现，使用Java 8标准反射机制(如果可用的话)，并返回到基于asm的localvariableparameternamediscoverer来检查类文件中的调试信息。如果存在Kotlin反射实现，则首先将KotlinReflectionParameterNameDiscoverer添加到列表中，并用于Kotlin类和接口。在作为Graal本机映像编译或运行时，不使用ParameterNameDiscoverer。进一步的发现程序可以通过addDiscoverer(ParameterNameDiscoverer)添加。

#### ExceptionDepthComparator

比较器能够根据抛出的异常类型的深度对异常进行排序。

#### GenericTypeResolver

根据类型变量解析泛型类型的助手类。主要用于在框架中使用，解析方法参数类型，即使它们是用泛型声明的。

#### GraalDetector

用于检测GraalVM本机映像环境的常见委托。

#### InfrastructureProxy

接口，该接口由透明的资源代理实现，这些资源代理需要被视为与底层资源相等，例如用于一致的查找键比较。注意，这个接口暗示了这种特殊的语义，并且没有构成一个通用的mixin!在org.springframework. transactions .support. transactionsynchronizationmanager中，这样的包装器将自动展开以进行关键比较。只有完全透明的代理，例如用于重定向或服务查找，才应该实现这个接口。用新行为修饰目标对象的代理(比如AOP代理)在这里不受限制

#### KotlinDetector

用于检测Kotlin的存在和标识Kotlin类型的通用委托。

#### KotlinReflectionParameterNameDiscoverer

ParameterNameDiscoverer实现，它使用Kotlin的反射工具来内省参数名称。与StandardReflectionParameterNameDiscoverer相比，它还允许在不需要Java 8 -parameters编译器标记的情况下确定接口参数名。

#### LocalVariableTableParameterNameDiscoverer

ParameterNameDiscoverer的实现，它使用方法属性中的LocalVariableTable信息来发现参数名。如果编译类文件时没有调试信息，则返回null。使用ObjectWeb的ASM库分析类文件。每个发现程序实例都以线程安全的方式为每个自省类缓存ASM发现的信息。建议尽可能重用ParameterNameDiscoverer实例。

#### MethodClassKey

针对特定目标类的方法的通用键类，包括toString()表示和相应的支持(如Java 8中对自定义HashMap键的建议)。

#### MethodIntrospector

定义彻底搜索与元数据相关的方法的算法，包括接口和父类，同时还处理参数化方法以及使用基于接口和类的代理时遇到的常见场景。

通常(但不一定)用于寻找带注释的处理程序方法。

#### MethodParameter

封装方法参数规范的Helper类，即方法或构造函数加上参数索引和声明的泛型类型的嵌套类型索引。作为要传递的规范对象很有用。

从4.2开始，有了一个org.springframework.core.annotation。SynthesizingMethodParameter子类可用，它用属性别名来合成注释。该子类特别用于web和消息端点处理。

#### NamedInheritableThreadLocal

InheritableThreadLocal子类将指定的名称公开为toString()结果(允许内省)。

#### NamedThreadLocal

ThreadLocal子类将指定的名称公开为toString()结果(允许内省)。

#### NestedCheckedException

用于用根本原因包装已检查异常的方便类。该类是抽象的，以迫使程序员扩展该类。getMessage将包含嵌套的异常信息;printStackTrace和其他类似方法将委托给包装的异常(如果有的话)。该类和NestedRuntimeException类之间的相似性是不可避免的，因为Java强制这两个类具有不同的超类(啊，具体继承的不灵活性!)

#### NestedExceptionUtils

实现能够保存嵌套异常的异常类的Helper类。必要是因为我们不能在不同的异常类型之间共享基类。主要用于框架内。

#### NestedIOException

正确处理根原因的IOException的子类，像NestedChecked/RuntimeException那样暴露根原因。在Java 6之前，标准IOException中没有添加适当的根源处理，这就是为什么我们需要自己做，以兼容Java 5的目的。该类和NestedChecked/RuntimeException类之间的相似性是不可避免的，因为该类需要从IOException派生。

#### NestedRuntimeException

用于用根本原因包装运行时异常的方便类。

该类是抽象的，以迫使程序员扩展该类。getMessage将包含嵌套的异常信息;printStackTrace和其他类似方法将委托给包装的异常(如果有的话)。

该类和NestedCheckedException类之间的相似性是不可避免的，因为Java强制这两个类具有不同的超类(啊，具体继承的灵活性!)

参见:getMessage, printStackTrace, NestedCheckedException

#### OrderComparator

比较器实现有序对象，按序值升序排序，分别按优先级降序排序。优先级优先级优先级高于普通排序对象。具有相同顺序值的对象将相对于具有相同顺序值的其他对象以任意顺序排序。任何不提供自己的order值的对象都将隐式地赋给Ordered值。因此，相对于其他具有相同顺序值的对象，以任意顺序结束在已排序集合的末尾。

#### Ordered

Ordered是一个接口，可以由应该是有序的对象(例如集合中的对象)实现。实际的顺序可以解释为优先级，第一个对象(具有最低的顺序值)具有最高的优先级。注意，这个接口还有一个优先级标记:PriorityOrdered。有关PriorityOrdered对象相对于普通有序对象的排序方式的详细信息，请参阅Javadoc。有关非有序对象的排序语义的详细信息，请参阅OrderComparator的Javadoc。

#### OverridingClassLoader

类装入器不总是像普通类装入器那样委托给父类装入器。例如，这允许在覆盖类加载器中强制插装，或者“丢弃”类加载行为，在最终在给定的父类加载器中加载类的插装版本之前，为了内省的目的，选择的应用程序类临时加载在覆盖类加载器中。

#### package-info

#### ParameterizedTypeReference

该类的目的是支持捕获和传递泛型类型。为了捕获泛型类型并在运行时保留它，你需要创建一个子类(最好是匿名内联类)，如下所示:

ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>() {};

然后，可以使用产生的typeRef实例在运行时获取携带捕获的参数化类型信息的类型实例。要了解更多关于“super type tokens”的信息，请查看链接到Neal Gafter的博客文章。

#### ParameterNameDiscoverer

接口来发现方法和构造函数的参数名。发现参数名并不总是可能的，但是可以尝试各种策略，例如查找编译时可能发出的调试信息，查找带有AspectJ注释的方法的argname注释值。

#### PrioritizedParameterNameDiscoverer

连续尝试多个发现程序委托的发现者实现。在addDiscoverer方法中首先添加的那些具有最高的优先级。如果一个返回null，将尝试下一个。

如果没有匹配的发现程序，默认行为是返回null。

#### PriorityOrdered

扩展了Ordered接口，表达了优先级排序:无论其顺序值如何，优先级总是在普通有序对象之前应用。当对一组有序对象进行排序时，优先级排序对象和普通有序对象被有效地视为两个独立的子集，优先级排序对象的集合在普通有序对象的集合之前，并在这些子集中应用相对排序。这主要是一个特殊用途的接口，在框架本身中用于首先识别优先级对象特别重要的对象，可能甚至不需要获取剩余的对象。一个典型的例子:Spring的后处理器优先级。注意:PriorityOrdered后处理器bean在一个特殊阶段初始化，比其他后处理器bean先初始化。这微妙地影响了它们的自动连接行为:它们只会自动连接到不需要为类型匹配进行急切初始化的bean上。

#### ReactiveAdapter

响应式流发布者与异步/响应式类型(如CompletableFuture、RxJava可观察对象等)之间的适配器。

适配器通常通过ReactiveAdapterRegistry获得。

#### ReactiveAdapterRegistry

一个适配器注册表，使响应式流发布者与各种异步/响应式类型(如CompletableFuture、RxJava可观察对象等)相适应。

默认情况下，根据类路径可用性，适配器为Reactor、RxJava 1、RxJava 2类型、CompletableFuture、Java 9+ Flow注册。发布者和Kotlin协程延迟和流

#### ReactiveTypeDescriptor

描述响应类型的语义，包括对isMultiValue()、isNoValue()和supportempty()的布尔检查。

#### ResolvableType

封装Java类型，提供对超类型、接口和泛型参数的访问，以及最终解析为类的能力。可解析类型可以从字段、方法参数、方法返回值或类中获得。这个类上的大多数方法本身都将返回可解析类型，这允许很容易

#### ResolvableTypeProvider

任何对象都可以实现这个接口来提供其实际的ResolvableType。

在判断实例是否匹配通用签名时，这些信息非常有用，因为Java在运行时没有传递签名。

在复杂的层次结构场景中，尤其是类的泛型类型签名在子类中发生变化时，该接口的用户应该小心。返回null总是有可能回退到默认行为。

#### SerializableTypeWrapper

可以用来获得java.lang.reflect.Types的包装的可序列化变量的内部实用程序类。字段或方法参数可以用作可序列化类型的根源。另外，也可以使用常规类作为源。返回的类型将是GenericArrayType、ParameterizedType、TypeVariable或WildcardType的一个类或可序列化代理。除了类(这是final)之外，对返回进一步类型(例如GenericArrayType.getGenericComponentType())的方法的调用将被自动包装。

#### SimpleAliasRegistry

AliasRegistry接口的简单实现。作为一个基类用于org.springframework.beans.BeanDefinitionRegistry实现。

#### SmartClassLoader

接口，该接口由支持重载的类加载器(例如，基于groovy的类加载器)实现。例如，由Spring的CGLIB代理工厂检测，以便做出缓存决策。

如果类加载器没有实现这个接口，那么从它获得的所有类都应该被认为是不可重载的(即可缓存的)。

#### SortedProperties

属性的专门化，根据属性的键按字母数字排序。

这在将Properties实例存储在Properties文件中时非常有用，因为它允许以具有一致的属性顺序的可重复方式生成此类文件。

生成的属性文件中的注释也可以省略。

#### SpringProperties

本地Spring属性的静态容器，即在Spring库级别定义。读取一个春天。属性文件，还允许通过setProperty以编程方式设置属性。在检查属性时，首先检查本地条目，然后通过系统退回到jvm级的系统属性。getProperty检查。这是另一种设置与spring相关的系统属性的方法，如“spring.getenv”。忽略”和“spring.beaninfo。忽略”，特别是对于JVM系统属性被锁定在目标平台上的场景(例如WebSphere)。请参阅setFlag以获得一种方便的方式在本地将这些标记设置为"true"。

#### SpringVersion

类，该类公开Spring版本。从jar文件获取“Implementation-Version”清单属性。

注意，有些类加载器不公开包元数据，因此该类可能无法在所有环境中确定Spring的版本。考虑使用基于反射的检查——例如，检查您打算调用的特定Spring 5.2方法是否存在。

#### StandardReflectionParameterNameDiscoverer

ParameterNameDiscoverer实现，它使用JDK 8的反射工具来自省参数名(基于“-parameters”编译器标志)。

### annotation

#### MergedAnnotation<A extends Annotation>

从merged annotation集合返回的单个合并注释。将一个视图展示给一个注释，其中属性值可能已经从不同的源值“合并”了。可以使用各种get方法访问属性值。例如，要访问int属性，将使用getInt(String)方法。注意，访问属性值时不会转换。例如，如果底层属性是int类型，则不可能调用getString(String)。该规则的唯一例外是可以分别作为String和String[]访问的Class和Class[]值，以防止潜在的早期类初始化。如果有必要，可以将merge annotation合成回实际的注释中。

#### AbstractMergedAnnotation

用于合并符号实现的抽象基类。

#### AliasFor

@AliasFor是一个注释，用于为注释属性声明别名

#### AnnotatedElementUtils

用于在已注释元素上查找注释、元注释和可重复注释的通用实用程序方法。AnnotatedElementUtils为Spring的元注释编程模型定义了公共API，支持注释属性覆盖。如果不需要支持注释属性重写，可以考虑使用AnnotationUtils

#### AnnotationAttributes

LinkedHashMap子类表示注释属性的键-值对，由AnnotationUtils、AnnotatedElementUtils和Spring的基于反射和asm的org.springframework.core.type读取。AnnotationMetadata实现。提供“伪具体化”，以避免调用代码中的嘈杂映射泛型，以及以类型安全的方式查找注释属性的方便方法。

#### AnnotationAwareOrderComparator

AnnotationAwareOrderComparator是OrderComparator的扩展，它支持Spring的org.springframework.core.order接口以及@Order和@Priority注释，由有序实例提供的order值覆盖静态定义的注释值(如果有的话)。

有关非有序对象的排序语义的详细信息，请参阅OrderComparator的Javadoc

#### AnnotationConfigurationException

如果注释配置不当，则由AnnotationUtils和合成注释抛出。

#### AnnotationFilter

可用于过滤特定注释类型的回调接口。

#### AnnotationsProcessor

用于处理注释的回调接口。

#### AnnotationsScanner

扫描程序在注释元素的注释层次结构中搜索相关注释。

#### AnnotationTypeMapping

为根注释类型上下文中的单个注释(或元注释)提供映射信息。

#### AnnotationTypeMappings

为单个源注释类型提供AnnotationTypeMapping信息。对所有元注释执行递归的广度优先抓取，最终提供一种快速映射根注释属性的方法。支持基于约定的元注释合并，以及隐式和显式的@AliasFor别名。还提供有关镜像属性的信息。这个类被设计为缓存的，这样元注释只需要搜索一次，而不管它们实际使用了多少次。

#### AnnotationUtils

用于处理注释、处理元注释的通用实用方法、桥接方法(编译器为泛型声明生成)以及超级方法(用于可选的注释继承)。

请注意，该类的大多数特性并不是由JDK的内省工具本身提供的。

#### AttributeMethods

提供了一种快速方法来访问具有一致顺序的注释的属性方法以及一些有用的实用程序方法。

#### IntrospectionFailureLogger

日志facade用于处理注释自省失败(特别是typenotpresentexception)。允许注释处理继续进行，假设当类属性值不能解析时，注释应该有效地消失。

#### MergedAnnotation

从merged notation集合返回的单个合并注释。将一个视图展示给一个注释，其中属性值可能已经从不同的源值“合并”了。可以使用各种get方法访问属性值。例如，要访问int属性，将使用getInt(String)方法。注意，访问属性值时不会转换。例如，如果底层属性是int类型，则不可能调用getString(String)。该规则的唯一例外是可以分别作为String和String[]访问的Class和Class[]值，以防止潜在的早期类初始化。

#### MergedAnnotationCollectors

为合并符号实例提供各种缩减操作的收集器实现。

#### MergedAnnotationPredicates

为合并符号提供各种测试操作的谓词实现。

#### MergedAnnotations

提供对合并注释集合的访问，这些注释通常从类或方法等源获得。每个合并注释代表一个视图的属性值可能会从不同的源“合并”值,通常:显式和隐式@AliasFor声明中的一个或多个属性注释明确@AliasFor声明基于元注释约定为元注释从元注释声明属性别名

#### MergedAnnotationsCollection

由一个代表直接注释的合并符号实例集合支持的合并符号实现。

#### MergedAnnotationSelector

用于在两个合并符号实例之间进行选择的策略接口。

#### MergedAnnotationSelectors

为MergedAnnotationSelector实例提供各种选项的实现

#### MissingMergedAnnotation

用作merged .missing()的实现的abstractmerged annotation。

#### Order

@Order为带注释的组件定义排序顺序。

该值是可选的，表示Ordered接口中定义的order值。值越低优先级越高。默认值是Ordered.LOWEST_PRECEDENCE，表示最低优先级(输给任何其他指定的顺序值)。

#### OrderUtils

用于根据对象的类型声明确定对象顺序的通用实用程序。处理Spring的Order注释以及javax.annotation.Priority。

#### package-info

#### PackagesAnnotationFilter

用于AnnotationFilter.packages(字符串…)的AnnotationFilter实现。

#### RepeatableContainers

用于确定作为其他注释容器的注释的策略。standarrepeatables()方法提供了一种默认策略，它尊重Java的@Repeatable支持，并且应该适合于大多数情况。

可以使用of方法为不希望使用@Repeatable的注释注册关系。要完全禁用可重复支持，请使用none()。

#### SynthesizedAnnotation

由合成的注释代理实现的标记接口。

用于检测注释是否已经合成。

#### SynthesizedMergedAnnotationInvocationHandler

InvocationHandler用于Spring合成的注释(即封装在动态代理中)，该注释具有额外的功能，如属性别名处理。

#### SynthesizingMethodParameter

一个方法参数变体，它通过@AliasFor来合成声明属性别名的注释。

#### TypeMappedAnnotation

通过应用AnnotationTypeMapping的映射和镜像规则来适应根注释的属性的合并符号。根属性值使用提供的双函数从源对象中提取。这允许同一个类支持各种不同的注释模型。例如，attributes源可能是一个实际的注释实例，其中调用注释实例上的方法来提取值。同样，源可以是一个简单的映射，其值可以使用Map.get(Object)提取。提取的根属性值必须与属性返回类型兼容，即

#### TypeMappedAnnotations

使用注释类型映射搜索和调整注释和元注释的合并注释实现。

### codec

#### AbstractDataBufferDecoder

解码器实现的抽象基类，可以将数据缓冲器直接解码到目标元素类型。

子类必须实现decodeDataBuffer以提供一种将DataBuffer转换为目标数据类型的方法。默认的decode实现转换每个单独的数据缓冲区，而decodeToMono应用“reduce”并转换聚合缓冲区。

子类可以重写decode，以便沿着不同的边界分割输入流(例如字符串的新行字符)或总是减少到单个数据缓冲区(例如资源)。

#### AbstractDecoder

解码器实现的抽象基类。

#### AbstractEncoder

编码加密实现的抽象基类。

#### AbstractSingleValueEncoder

只能处理单个值的编码器类的抽象基类。

#### ByteArrayDecoder

字节数组的解码器。

#### ByteArrayEncoder

字节数组的编码器。

#### ByteBufferDecoder

解码器bytebuffer)。

#### ByteBufferEncoder

编码器

#### CharSequenceEncoder

将CharSequence流编码为字节流

#### CodecException

指示对目标流进行编码和解码时出现的问题的一般错误。

#### DataBufferDecoder

数据转换器的简单传递译码器。

注意:数据缓冲区应该在被使用之后通过org.springframework.core.io.buffer. databufferutil .release(DataBuffer)释放。此外，如果使用Flux或Mono操作符，如flatMap、reduce以及其他在内部预取、缓存和跳过或过滤数据项的操作符，请添加doOnDiscard(PooledDataBuffer)。类DataBufferUtils::release)，以确保缓存的数据缓冲区在错误或取消信号之前被释放

#### DataBufferEncoder

简单的数据传输编码器。

#### Decoder

将数据缓冲器输入流解码为<T>类型元素的输出流的策略。

#### DecodingException

指示解码输入流时出现的问题，焦点是与内容相关的问题，如解析失败。与更一般的I/O错误、非法状态或解码器可能选择引发的配置问题等CodecException相反。例如，在服务器web应用程序中，DecodingException将转换为具有400(错误输入)状态的响应，而CodecException将转换为500(服务器错误)状态。

#### Encoder

将<T>类型的对象流编码为字节输出流的策略。

#### EncodingException

指示对输入对象流进行编码时出现的问题，其重点是无法对对象进行编码。而不是更一般的I/O错误或CodecException，比如编码器也可以选择引发的配置问题。

#### Hints

用于处理提示的常量和方便方法。

#### package-info

#### ResourceDecoder

译码器的资源。

#### ResourceEncoder

资源解码器

#### ResourceRegionEncoder

ResourceRegions编码器

#### StringDecoder

解码数据缓冲流到字符串流。解码之前，该解码器重新排列传入的数据缓冲区，以便每个缓冲区以换行符结束。这是为了确保正确解码多字节字符，并且不会跨越缓冲区边界。可以自定义默认分隔符(\n， \r\n)。部分灵感来自Netty的DelimiterBasedFrameDecoder。

### convert

#### converter

ConditionalConverter

允许转换器、GenericConverter或ConverterFactory基于源和目标类型描述符的属性有条件地执行。通常用于根据字段或类级特征(如注释或方法)的存在，有选择地匹配自定义转换逻辑。例如，当从字符串字段转换为日期字段时，如果目标字段也被@DateTimeFormat注释，则实现可能返回true。另一个例子是，在从字符串字段转换为帐户字段时，如果目标帐户类定义了一个公共静态findAccount(String)方法，则实现可能返回true。

ConditionalGenericConverter

可以根据源和目标类型描述符的属性有条件地执行的泛型转换器。有关详细信息，请参阅ConditionalConverter。

Converter

转换器将类型S的源对象转换为类型T的目标。这个接口的实现是线程安全的，可以共享。实现可以另外实现ConditionalConverter。

ConverterFactory

一个用于“远程”转换器的工厂，它可以将对象从S转换为R的子类型。实现可以另外实现ConditionalConverter。

ConverterRegistry

用于在类型转换系统中注册转换器。

ConvertingComparator

在比较值之前进行转换的比较器。指定的转换器将在传递给底层比较器之前对每个值进行转换。

GenericConverter

用于在两个或多个类型之间进行转换的通用转换器接口。这是转换器中最灵活的SPI接口，也是最复杂的接口。它很灵活，因为一个GenericConverter可以支持在多个源/目标类型对之间进行转换(请参阅getConvertibleTypes())。此外，GenericConverter实现可以在类型转换过程中访问源/目标字段上下文。这允许解析源和目标字段元数据，比如注释和泛型信息，这些元数据可以用来影响转换逻辑。当简单的转换器或ConverterFactory接口就足够使用时，通常不应使用此接口。实现可以另外实现ConditionalConverter。

package-info

#### support

AbstractConditionalEnumConverter

基于枚举的转换器的ConditionalConverter基本实现。

ArrayToArrayConverter

将一个数组转换为另一个数组。首先使源数组适应于列表，然后委托给CollectionToArrayConverter来执行目标数组转换。

ArrayToCollectionConverter



ArrayToObjectConverter



ArrayToStringConverter



ByteBufferConverter

将一个字节缓冲区直接与[]字节进行转换，也可以直接与[]字节进行转换，也可以通过[]字节间接转换为converonservice支持的任何类型。

CharacterToNumberFactory



CollectionToArrayConverter



CollectionToCollectionConverter



CollectionToObjectConverter



CollectionToStringConverter



ConfigurableConversionService

ConversionServiceFactory

ConversionUtils

ConvertingPropertyEditorAdapter

DefaultConversionService

EnumToIntegerConverter

EnumToStringConverter

FallbackObjectToStringConverter

GenericConversionService

IdToEntityConverter

IntegerToEnumConverterFactory

MapToMapConverter

NumberToCharacterConverter

NumberToNumberConverterFactory

ObjectToArrayConverter

ObjectToCollectionConverter

ObjectToObjectConverter

ObjectToOptionalConverter

ObjectToStringConverter

package-info

PropertiesToStringConverter

StreamConverter

StringToArrayConverter

StringToBooleanConverter

StringToCharacterConverter

StringToCharsetConverter

StringToCollectionConverter

StringToCurrencyConverter

StringToEnumConverterFactory

StringToLocaleConverter

StringToNumberConverterFactory

StringToPropertiesConverter

StringToTimeZoneConverter

StringToUUIDConverter

ZonedDateTimeToCalendarConverter

ZoneIdToTimeZoneConverter

#### ConversionException

转换系统抛出异常的基类。

#### ConversionFailedException

当实际的类型转换尝试失败时引发的异常。

#### ConversionService

用于类型转换的服务接口。这是进入转换系统的入口点。调用convert(对象，类)来使用这个系统执行线程安全的类型转换。

#### ConverterNotFoundException

当在给定的转换服务中找不到合适的转换器时，抛出异常。

#### package-info

#### Property

对JavaBeans属性的描述，允许我们避免依赖java.beans.PropertyDescriptor。java.bean包在许多环境中都不可用(例如Android、Java ME)，因此这对于Spring的核心转换工具的可移植性是可取的。用于从属性位置构建类型描述符。然后，可以使用构建的类型描述符从/转换为属性类型。

#### TypeDescriptor

关于要转换的类型的上下文。

### env

#### AbstractEnvironment

环境实现的抽象基类。支持保留默认概要文件名称的概念，并允许通过ACTIVE_PROFILES_PROPERTY_NAME和DEFAULT_PROFILES_PROPERTY_NAME属性指定活动概要文件和默认概要文件。具体的子类主要不同于它们默认添加的PropertySource对象。AbstractEnvironment补充说没有。子类应该通过受保护的customizePropertySources(MutablePropertySources) hook贡献属性源，而客户端应该使用ConfigurableEnvironment.getPropertySources()并根据MutablePropertySources API进行定制。请参阅ConfigurableEnvironment javadoc了解使用示例。

#### AbstractPropertyResolver

针对任何基础源解析属性的抽象基类。

#### CommandLineArgs

命令行参数的简单表示，分为“选项参数”和“非选项参数”。

#### CommandLinePropertySource

由命令行参数支持的PropertySource实现的抽象基类。参数化类型T表示命令行选项的底层源。这可能像SimpleCommandLinePropertySource情况下的字符串数组一样简单，或者特定于特定的API，如JOpt的OptionSet在JOptCommandLinePropertySource情况下。用于独立的基于spring的应用程序，即那些通过接受命令行参数字符串[]的传统主方法引导的应用程序。在许多情况下，直接在main方法中处理命令行参数可能就足够了，但在其他情况下，可能希望将参数作为值注入到Spring bean中。在后一组情况中，CommandLinePropertySource变得非常有用。CommandLinePropertySource通常会被添加到Spring ApplicationContext的环境中，此时所有命令行参数都可以通过Environment. getproperty (String)方法家族获得

#### CompositePropertySource

迭代一组PropertySource实例的复合PropertySource实现。在多个属性源共享相同名称的情况下是必要的，例如，当多个值提供给@PropertySource时。

从Spring 4.1.2开始，这个类继承了EnumerablePropertySource而不是普通的PropertySource，基于从所有包含的源中积累的属性名(尽可能)暴露getPropertyNames()。

#### ConfigurableEnvironment

大多数(如果不是所有)环境类型都要实现的配置接口。提供用于设置活动和默认概要文件以及操作底层属性源的工具。允许客户端通过ConfigurablePropertyResolver超接口设置和验证所需的属性，自定义转换服务等。属性源可以被移除、重新排序或替换;可以使用从getPropertySources()返回的MutablePropertySources实例添加其他属性源。以下示例与ConfigurableEnvironment的标准环境实现相反，但通常适用于任何实现，尽管特定的默认属性源可能有所不同。例如:添加一个新的具有最高搜索优先级的属性源

#### ConfigurablePropertyResolver

大多数(如果不是全部)PropertyResolver类型要实现的配置接口。提供用于访问和自定义在将属性值从一种类型转换为另一种类型时使用的converonservice的工具。

#### EnumerablePropertySource

PropertySource实现能够查询其基础源对象，以枚举所有可能的属性名/值对。公开getPropertyNames()方法以允许调用者在无需访问底层源对象的情况下内省可用的属性。这也促进了containsProperty(String)更高效的实现，因为它可以调用getPropertyNames()并遍历返回的数组，而不是尝试调用getProperty(String)，因为它的开销可能更大。实现可以考虑缓存getPropertyNames()的结果，以充分利用这个性能机会。大多数框架提供的PropertySource实现都是可枚举的;一个反例是JndiPropertySource，由于JNDI的性质，在任何给定的时间都不可能确定所有可能的属性名;相反，它只能尝试访问一个属性(通过getProperty(String))，以评估它是否存在。

#### Environment

接口，表示当前应用程序在其中运行的环境。为应用程序环境的两个关键方面建模:概要文件和属性。与属性访问相关的方法通过PropertyResolver超接口公开。概要文件是一个命名的、逻辑的bean定义组，只有在给定概要文件处于活动状态时才向容器注册。可以将bean分配给一个配置文件，无论是用XML定义的还是通过注释定义的;请参阅spring-beans 3.1模式或@Profile注释了解语法细节。与概要文件相关的环境对象的角色是确定哪些概要文件(如果有的话)当前是活动的，以及哪些概要文件(如果有的话)在默认情况下应该是活动的。属性在几乎所有的应用程序中都扮演着重要的角色，它们可能来自各种来源:属性文件、JVM系统属性、系统环境变量、JNDI、servlet上下文参数、临时属性对象、映射，等等。与属性相关的环境对象的角色是为用户提供一个方便的服务接口，用于配置属性源并从它们解析属性。在ApplicationContext中管理的bean可以注册为environtaware或@Inject环境，以便直接查询概要文件状态或解析属性。然而，在大多数情况下，应用程序级bean不需要直接与环境交互，而是可能必须有${…属性值被属性占位符配置器替换，例如PropertySourcesPlaceholderConfigurer，它本身是一个环境件，从Spring 3.1开始默认使用&lt;context:property-placeholder/&gt;环境对象的配置必须通过ConfigurableEnvironment接口完成，该接口从所有AbstractApplicationContext子类getEnvironment()方法返回。请参阅ConfigurableEnvironment Javadoc，了解在应用程序上下文刷新()之前对属性源进行操作的使用示例。

#### EnvironmentCapable

接口，指示包含并公开环境引用的组件。所有Spring应用程序上下文都是环境支持的，该接口主要用于在框架方法中执行instanceof检查，这些方法接受BeanFactory实例(可能是也可能不是ApplicationContext实例)，以便与环境交互(如果环境确实可用的话)。如前所述，ApplicationContext扩展了EnvironmentCapable，并因此公开了getEnvironment()方法;然而，ConfigurableApplicationContext重新定义了getEnvironment()，并缩小签名以返回一个可配置的环境。其效果是，在从ConfigurableApplicationContext访问它之前，环境对象是“只读”的，此时它也可以被配置。

#### JOptCommandLinePropertySource

由JOpt选项集支持的CommandLinePropertySource实现。

典型用法

针对提供给main方法的参数字符串[]配置并执行OptionParser，并使用生成的OptionSet对象创建一个JOptCommandLinePropertySource:

#### MapPropertySource

PropertySource，从映射对象读取键和值。

#### MissingRequiredPropertiesException

未找到所需的属性时引发的异常。

#### MutablePropertySources

PropertySources接口的默认实现。允许操作所包含的属性源，并提供用于复制现有PropertySources实例的构造函数。

在addFirst和addLast等方法中提到优先级时，这是关于使用PropertyResolver解析给定属性时搜索属性源的顺序。

#### package-info

#### Profiles

可以被环境接受的概要谓词。可以直接实现，或者更常见的是，使用of(…)工厂方法创建。

#### ProfilesParser

Profiles.of使用的内部解析器。

#### PropertiesPropertySource

从属性对象提取属性的PropertySource实现。

注意，因为从技术上讲，Properties对象是一个< object, object > Hashtable，所以可以包含非字符串的键或值。然而，这种实现被限制为仅访问基于字符串的键和值，与属性的方式相同。getProperty和Properties.setProperty。

#### PropertyResolver

接口，用于根据任何底层源解析属性。

#### PropertyResolverExtensionsKt.class

#### PropertySource

表示名称/值属性对源的抽象基类。底层源对象可以是封装属性的任何类型T。例子包括java.util。属性对象,java.util。映射对象、ServletContext和ServletConfig对象(用于访问init参数)。探索PropertySource类型层次结构以查看提供的实现。PropertySource对象通常不是单独使用，而是通过聚合属性源的PropertySources对象使用，并与PropertyResolver实现结合使用，该实现可以跨一组PropertySource执行基于优先级的搜索。PropertySource标识不是基于封装属性的内容确定的，而是仅基于PropertySource的名称确定的。这对于在集合上下文中操作PropertySource对象非常有用。有关详细信息，请参阅MutablePropertySources以及named(String)和toString()方法中的操作。注意，在使用@Configuration类时，@PropertySource注释提供了一种方便的声明性方法，可以将属性源添加到外围环境中。

#### PropertySources

容器，包含一个或多个PropertySource对象。

#### PropertySourcesPropertyResolver

PropertyResolver实现，它根据一组底层PropertySources解析属性值。

#### ReadOnlySystemAttributesMap

只读Map<String, String>实现，由系统属性或环境变量支持。当SecurityManager禁止访问System.getProperties()或System.getenv()时，由AbstractApplicationContext使用。由于这个原因，keySet()、entrySet()和values()的实现总是返回空，即使如果当前安全管理器允许访问单个键，get(Object)实际上可能返回非空。

#### SimpleCommandLineArgsParser

解析命令行参数的字符串[]，以填充CommandLineArgs对象。使用选项参数选项,参数必须遵循精确的语法:

——optName [= optValue]

也就是说，选项必须以“——”作为前缀，并且可以指定也可以不指定值。如果指定了值，则名称和值之间必须用等号("=")分隔，不能有空格。

#### SimpleCommandLinePropertySource

由简单字符串数组支持的CommandLinePropertySource实现。目的CommandLinePropertySource实现旨在提供最简单的方法来解析命令行参数。与所有CommandLinePropertySource实现一样，命令行参数被分成两个不同的组:选项参数和非选项参数，如下所述(一些部分从Javadoc复制SimpleCommandLineArgsParser):

#### StandardEnvironment

适合在“标准”(即非web)应用程序中使用的环境实现。除了一个可配置环境的通常功能，如属性解析和与配置文件相关的操作，这个实现配置了两个默认的属性源，按照以下顺序搜索:系统属性系统环境变量,如果JVM中的关键“xyz”存在两个系统属性和环境变量的设置为当前进程,关键的价值“xyz”从系统属性将返回调用environment.getProperty(“xyz”)。默认情况下选择这种顺序是因为系统属性是针对每个jvm的，而给定系统上的许多jvm的环境变量可能是相同的。给予系统属性优先级允许在每个jvm的基础上重写环境变量。这些默认属性源可以被删除、重新排序或替换;可以使用从getPropertySources()中获得的MutablePropertySources实例添加其他属性源。请参阅ConfigurableEnvironment Javadoc了解使用示例。请参阅SystemEnvironmentPropertySource javadoc了解在shell环境(例如Bash)中对属性名的特殊处理，这些属性名不允许在变量名中使用句点字符。

#### SystemEnvironmentPropertySource

专为系统环境变量使用而设计的MapPropertySource。补偿Bash和其他shell中不允许变量包含句点字符和/或连字符的约束;还允许对属性名使用大写变体，以便更习惯地使用shell。例如，调用getProperty("foo.bar")将尝试为原始属性或任何'等效'属性找到一个值，并返回第一个找到的:foo.bar。bar -原始名称foo_bar -用下划线表示句点(如果有的话)FOO。BAR -原始的，带有大写的FOO_BAR -带有下划线和大写的上述任何连字符变体都可以工作，甚至可以混合使用点/连字符变体。调用containsProperty(String)也一样，如果存在上述任何一个属性，则返回true，否则返回false。当将活动或默认概要文件指定为环境变量时，此特性特别有用。以下内容在Bash下是不允许的:java -classpath…MyApp不过，下面的语法是允许的，而且更传统:SPRING_PROFILES_ACTIVE=p1 java -classpath…MyApp为这个类(或包)启用调试或跟踪级别的日志记录，用于解释这些“属性名解析”何时发生的消息。默认情况下，这个属性源包含在StandardEnvironment及其所有子类中。

### io

#### buffer

DataBuffer

对字节缓冲区的基本抽象。DataBuffers有一个单独的读和写位置，而ByteBuffer只有一个位置。因此，数据缓冲器在写入后不需要翻动读取。一般来说，以下不变式适用于读、写位置和容量:0 &lt;= readPosition &lt;= writePosition &lt;= capacity DataBuffer的容量会根据需要扩展，类似于StringBuilder。DataBuffer抽象的主要目的是为ByteBuffer提供一个方便的包装器，类似于Netty的io.net .buffer. bytebuf，但也可以在非Netty平台上使用(即Servlet容器)。

DataBufferFactory

数据缓冲区的工厂，允许分配和包装数据缓冲区。

DataBufferUtils

用于处理数据缓冲区的实用程序类。

DataBufferWrapper

提供方便的DataBuffer接口实现，可重写该接口以适应委托。这些方法默认调用包装的委托对象。

DefaultDataBuffer

内部使用ByteBuffer的DataBuffer接口的默认实现。有不同的读写位置。使用DefaultDataBufferFactory构造。受到妮蒂的ByteBuf的启发。引入Netty，这样非Netty运行时(例如Servlet)就不需要在类路径上使用Netty。

DefaultDataBufferFactory

DataBufferFactory接口的默认实现。允许指定构造时的默认初始容量，以及首选的是基于堆的缓冲区还是直接缓冲区。

NettyDataBuffer

包装一个网络字节buf的DataBuffer接口的实现。通常使用NettyDataBufferFactory构造。

NettyDataBufferFactory

基于Netty ByteBufAllocator的DataBufferFactory接口实现。

package-info

PooledDataBuffer

DataBuffer的扩展，允许共享内存池的缓冲区。介绍了引用计数的方法。

#### support

DefaultPropertySourceFactory

PropertySourceFactory的默认实现，将每个资源包装在ResourcePropertySource中。

EncodedResource

将资源描述符与用于从资源读取的特定编码或字符集组合在一起的占位符。用作支持使用特定编码读取内容的操作的参数，通常通过java.io.Reader。

LocalizedResourceHelper

用于加载本地化资源的助手类，通过名称、扩展名和当前区域设置指定。

package-info

PathMatchingResourcePatternResolver

一个ResourcePatternResolver实现，它能够将指定的资源位置路径解析为一个或多个匹配的资源。源路径可以是一个简单的路径，它与目标资源一一对应，也可以包含特殊的“classpath*:”前缀和/或内部ant风格的正则表达式(使用Spring的AntPathMatcher实用程序匹配)。后者都是有效的通配符。

PropertiesLoaderSupport

javabean样式组件的基类，需要从一个或多个资源加载属性。也支持本地属性，具有可配置的覆盖。

PropertiesLoaderUtils

用于加载java.util的方便实用程序方法。属性，执行输入流的标准处理。对于更多可配置的属性加载，包括自定义编码的选项，请考虑使用PropertiesLoaderSupport类。

PropertySourceFactory

用于创建基于资源的PropertySource包装器的策略接口

ResourceArrayPropertyEditor

资源数组的编辑器，以自动转换字符串位置模式(例如:“文件:C: / *。或“classpath*:myfile.txt”)到资源数组属性。还可以将位置模式的集合或数组转换为合并的资源数组。路径可以包含${…}占位符，要被解析为环境属性:例如${user.dir}。默认情况下，不可解析的占位符被忽略。委托给ResourcePatternResolver，默认情况下使用PathMatchingResourcePatternResolver。

ResourcePatternResolver

将位置模式(例如，Ant-style路径模式)解析为资源对象的策略接口。这是对ResourceLoader接口的扩展。一个传入的ResourceLoader(例如，org.springframework.context)。通过org.springframework.context传入的ApplicationContext。可以检查它是否也实现了这个扩展的接口。PathMatchingResourcePatternResolver是一个可以在ApplicationContext之外使用的独立实现，也可以被ResourceArrayPropertyEditor用于填充资源数组bean属性。可以用于任何类型的位置模式(例如。/WEB-INF/*-context.xml):输入模式必须匹配策略实现。这个接口只指定转换方法，而不是特定的模式格式。这个接口还为类路径中的所有匹配资源建议了一个新的资源前缀“classpath*:”。注意，在这种情况下，资源位置应该是一个没有占位符的路径(例如。“/它指明”);JAR文件或类目录可以包含多个同名的文件。

ResourcePatternUtils

实用程序类，用于确定给定的URL是否是可以通过ResourcePatternResolver加载的资源位置。如果isUrl(String)方法返回false，调用者通常会假定位置是相对路径

ResourcePropertySource

PropertiesPropertySource的子类，它从给定的资源或资源位置加载一个属性对象，例如"classpath:/com/myco/foo. Properties "。属性”或“文件:/道路/ / file.xml”。同时支持传统的和基于xml的属性文件格式;但是，为了使XML处理生效，底层资源的getFilename()方法必须返回一个以“. XML”结尾的非空值。

ResourceRegion

资源实现的区域，由资源中的一个位置和该区域长度的字节数具体化。

SpringFactoriesLoader

通用工厂装料机构，供内部使用框架内。从"META-INF/spring. printoriesloader "中加载并实例化给定类型的工厂。工厂”文件，可能存在于类路径中的多个JAR文件中。的春天。工厂文件必须采用属性格式，其中键是接口或抽象类的完全限定名，值是一个以逗号分隔的实现类名列表。例如:example.MyService = example.MyServiceImpl1例子。MyServiceImpl2例子。MyService是接口的名称，MyServiceImpl1和MyServiceImpl2是两个实现。

VfsPatternUtils

用于访问VfsUtils方法而不向整个世界公开它们的人工类。

#### 其他

##### AbstractFileResolvingResource

资源的抽象基类，它将url解析为文件引用，如UrlResource或ClassPathResource。检测url中的“文件”协议以及JBoss“vfs”协议，相应解析文件系统引用。

##### AbstractResource

方便的资源实现基类，预实现典型行为。exists方法将检查文件或InputStream是否可以打开;"isOpen"总是返回false;"getURL"和"getFile"抛出异常;"toString"会返回描述信息。

##### ByteArrayResource

给定字节数组的资源实现。为给定的字节数组创建一个ByteArrayInputStream。用于从任何给定的字节数组加载内容，而不必使用一次性的InputStreamResource。对于从本地内容创建邮件附件特别有用，因为JavaMail需要能够多次读取流。

##### ClassPathResource

类路径资源的资源实现。使用给定的类加载器或给定的类来加载资源。支持解析为java.io。如果类路径资源驻留在文件系统中，但不驻留在JAR中的资源中，则文件。始终支持解析为URL。

##### ClassRelativeResourceLoader

相对于给定的java.lang.Class，解释普通资源路径的ResourceLoader实现

##### ContextResource

一个资源的扩展接口，从一个外围的'context'加载，例如从javax.servlet。ServletContext，也可以来自普通的类路径路径或相对文件系统路径(没有显式前缀指定，因此应用相对于本地ResourceLoader的上下文)。

##### DefaultResourceLoader

ResourceLoader接口的缺省实现。由ResourceEditor使用，并作为org.springframework.context.support.AbstractApplicationContext的基类。也可以单独使用。如果位置值是URL，则返回UrlResource;如果是非URL路径或"classpath:"伪URL，则返回ClassPathResource。

##### DescriptiveResource

简单的资源实现，包含资源描述，但不指向实际可读的资源。作为占位符，如果API需要资源参数，但不一定用于实际读取。

##### FileSystemResource

java.io的资源实现。文件和java.nio.file。文件系统目标的路径句柄。支持以文件和URL的形式解析。实现扩展的WritableResource接口。注意:从Spring Framework 5.0开始，这个资源实现使用NIO.2 API进行读/写交互。在5.1中，它可以构造一个路径句柄，在这种情况下，它将通过NIO.2执行所有的文件系统交互，只求助于getFile()上的文件。

##### FileSystemResourceLoader

ResourceLoader实现，将普通路径解析为文件系统资源，而不是类路径资源(后者是DefaultResourceLoader的默认策略)。注意:普通路径将始终被解释为相对于当前虚拟机工作目录，即使它们以斜杠开头。(这与Servlet容器中的语义一致。)使用显式的"file:"前缀来强制文件的绝对路径。

org.springframework.context.support.FileSystemXmlApplicationContext is a full-fledged ApplicationContext implementation that provides the same resource path resolution strategy.

##### FileUrlResource

UrlResource的子类，它假定文件解析，并实现了它的WritableResource接口。这个资源变量还将从getFile()中缓存解析的文件句柄。这是DefaultResourceLoader为“file:…”解析的类。URL位置，允许向下转换到WritableResource。或者，从文件句柄或NIO直接构造java.nio.file。路径，考虑使用FileSystemResource。

##### InputStreamResource

给定InputStream的资源实现。只有当没有其他特定的资源实现适用时才应该使用。特别是，最好使用byterayresource或任何可能的基于文件的资源实现。与其他资源实现不同的是，这是一个已经打开的资源的描述符——因此从isOpen()返回true。如果您需要将资源描述符保存在某个地方，或者您需要从一个流中读取多次，则不要使用InputStreamResource。

##### InputStreamSource

对于作为InputStream源的对象的简单接口。这是Spring更广泛的资源接口的基本接口。

对于一次性使用的流，InputStreamResource可以用于任何给定的InputStream。Spring的ByteArrayResource或任何基于文件的资源实现都可以用作具体实例，允许多次读取底层内容流。例如，这使得该接口可以作为邮件附件的抽象内容源。

##### package-info

##### PathResource

路径句柄的资源实现，通过Path API执行所有操作和转换。支持以文件和URL的形式解析。实现扩展的WritableResource接口。注意:从5.1版本开始，FileSystemResource中也提供了路径支持，它应用Spring的标准基于字符串的路径转换，但通过Files API执行所有操作。5.1.1已弃用，支持FileSystemResource.FileSystemResource(Path)

##### ProtocolResolver

特定于协议的资源句柄的解决策略。

用作DefaultResourceLoader的SPI，允许在不子类化加载器实现(或应用程序上下文实现)的情况下处理自定义协议。

##### Resource

资源描述符的接口，该描述符从基础资源(如文件或类路径资源)的实际类型中抽象出来。如果以物理形式存在，则可以为每个资源打开InputStream，但可以为某些资源返回URL或文件句柄。实际行为是特定于实现的。

##### ResourceEditor

资源描述符的编辑器，自动将字符串位置转换为资源属性，例如file:C:/myfile.txt或classpath:myfile.txt，而不是使用字符串位置属性。路径可以包含${…}占位符，将被解析为org.springframework.core.env。环境属性:例如${user.dir}。默认情况下，不可解析的占位符被忽略。委托给ResourceLoader来完成繁重的工作，默认情况下使用DefaultResourceLoader。

##### ResourceLoader

加载资源的策略接口(e..类路径或文件系统资源)。一个org.springframework.context。ApplicationContext需要提供这个功能，再加上扩展的org.springframework.core.io.support。ResourcePatternResolver支持。DefaultResourceLoader是一个可以在ApplicationContext之外使用的独立实现，也可以由ResourceEditor使用。当在ApplicationContext中运行时，可以使用特定上下文的资源加载策略，从字符串填充类型为Resource和Resource array的Bean属性。

##### UrlResource

url定位器的资源实现。支持以URL的形式解析，在使用“File:”协议的情况下也支持以文件的形式解析。

##### VfsResource

基于JBoss VFS的资源实现。

从Spring 4.0开始，这个类支持VFS 3。它特别兼容JBoss AS 7和WildFly 8+。

##### VfsUtils

用于在类路径中检测和访问JBoss VFS的实用程序。

从Spring 4.0开始，这个类支持VFS 3。它特别兼容JBoss AS 7和WildFly 8+。

感谢马里乌斯·波戈维奇(Marius Bogoevici)最初的补丁。注意:这是一个内部类，不应该在框架外部使用。

##### WritableResource

支持写入资源的扩展接口。提供一个OutputStream访问器。

### log

CompositeLog

Log的实现，它包装了一个记录器列表，并委托给在给定级别上启用日志记录的第一个记录器。

LogAccessor

一个方便的公共日志访问器，不仅提供了基于CharSequence的日志方法，而且还提供了基于供应商的变体，用于Java 8 lambda表达式。

LogDelegateFactory

使用Spring的日志记录约定为普通日志委托提供工厂。主要用于Apache Commons日志记录框架的内部使用，通常以spring-jcl桥的形式，但也与其他Commons日志记录桥兼容。

LogFormatUtils

用于格式化和记录消息的实用程序方法。主要用于Apache Commons日志记录框架的内部使用，通常以spring-jcl桥的形式，但也与其他Commons日志记录桥兼容。

LogMessage

一种用于Commons日志记录的简单日志消息类型，允许方便地延迟解析给定的供应商实例(通常绑定到Java 8 lambda表达式)或其toString()中的printf样式的格式字符串(string .format)。

package-info

### serializer

#### support

DeserializingConverter

委托给反序列化器以将字节数组中的数据转换为对象的转换器。

package-info

SerializationDelegate

一个方便的委托，预先安排了配置状态，以满足常见的序列化需求。实现了序列化器和反序列化器本身，因此也可以传递到这些更具体的回调方法中。

SerializationFailedException

当一个org.springframework.core.serializer。序列化器或org.springframework.core.serializer。反序列化器失败。由SerializingConverter和DeserializingConverter抛出。

SerializingConverter

委托给序列化器以将对象转换为字节数组的转换器。

#### 其他

DefaultDeserializer

使用Java序列化读取输入流的默认反序列化器实现。

DefaultSerializer

使用Java序列化将对象写入输出流的序列化器实现。

Deserializer

将输入流中的数据转换为对象的策略接口。

package-info

Serializer

一个用于将对象流到OutputStream的策略接口。

### style

DefaultToStringStyler

Spring的默认toString()样式。ToStringCreator使用这个类根据Spring约定以一致的方式样式化toString()输出。

DefaultValueStyler

使用Spring的toString样式约定将对象转换为字符串形式，通常是为了调试目的。在底层使用反射访问者模式来很好地封装每种样式对象的样式算法。

package-info

StylerUtils

简单的实用程序类，允许方便地访问值样式逻辑，主要支持描述性日志消息。对于更复杂的需求，可以直接使用valuestler抽象。这个类只是在底层使用一个共享的DefaultValueStyler实例。

ToStringCreator

使用可插入的样式约定构建漂亮打印toString()方法的实用类。默认情况下，ToStringCreator遵循Spring的toString()样式约定。

ToStringStyler

用于优化打印toString()方法的策略接口。封装打印算法;其他一些对象，比如构建器，应该提供工作流。

ValueStyler

根据Spring约定封装值字符串样式算法的策略。

### task

#### support

ConcurrentExecutorAdapter

为任何Spring TaskExecutor公开Executor接口的适配器。在Spring 3.0中，这就没有那么有用了，因为TaskExecutor本身扩展了Executor接口。现在，适配器仅用于隐藏给定对象的TaskExecutor特性，仅向客户机公开标准Executor接口。

ExecutorServiceAdapter

接受Spring TaskExecutor并公开完整java.util.concurrent的适配器。ExecutorService。这主要是为了适应通过java.util.concurrent通信的客户机组件。ExecutorService API。它还可以在Java EE 7环境中用作本地Spring TaskExecutor后端和位于jndi的ManagedExecutorService之间的公共基础。注意:这个ExecutorService适配器不支持java.util.concurrent中的生命周期方法。ExecutorService API(“shutdown()”等)，类似于Java EE 7环境中的服务器范围的ManagedExecutorService。生命周期始终由后端池决定，此适配器充当该目标池的仅访问代理。

package-info

TaskExecutorAdapter

接受JDK的适配器。执行器，并公开一个Spring org.springframework.core.task。TaskExecutor。还检测扩展的java.util.concurrent。执行器服务，适应org.springframework.core.task。AsyncTaskExecutor相应接口。

#### 其他

AsyncListenableTaskExecutor

AsyncTaskExecutor接口的扩展，添加了为ListenableFutures提交任务的功能

AsyncTaskExecutor

异步TaskExecutor实现的扩展接口，提供了一个重载的execute(Runnable, long)变量，带有一个start timeout参数，并支持Callable。

注意:java . util . concurrent。Executors类包含一组方法，这些方法可以转换其他一些常见的闭包类对象，例如java.security。在执行它们之前，PrivilegedAction可以调用它们。实现这个接口还表明execute(Runnable)方法不会在调用者的线程中执行它的Runnable，而是在其他一些线程中异步执行。

package-info

SimpleAsyncTaskExecutor

为每个任务触发一个新线程，并异步执行它的TaskExecutor实现。支持通过“concurrencyLimit”bean属性限制并发线程。缺省情况下，并发线程数是无限的。

注意:这个实现不重用线程!请考虑使用线程池的TaskExecutor实现，特别是用于执行大量短命期任务。

SyncTaskExecutor

在调用线程中同步执行每个任务的TaskExecutor实现。主要用于测试场景。在调用线程中执行具有参与其线程上下文的优点，例如线程上下文类装入器或线程的当前事务关联。也就是说，在很多情况下，异步执行是更可取的:在这种情况下，可以选择异步的TaskExecutor。

TaskDecorator

decorator的回调接口，用于应用于任何即将执行的可运行程序。注意，这样的decorator并不一定要应用到用户提供的可运行文件/可调用文件上，而是要应用到实际的执行回调文件上(它可能是用户提供任务的包装器)。主要用例是围绕任务调用设置一些执行上下文，或者为任务执行提供一些监视/统计信息。

TaskExecutor

简单的任务执行器接口，抽象了可运行程序的执行。实现可以使用各种不同的执行策略，例如:同步、异步、使用线程池等等。相当于JDK 1.5的Executor接口;现在在Spring 3.0中扩展它，这样客户端就可以声明对Executor的依赖，并接收任何TaskExecutor实现。这个接口与标准Executor接口分离，主要是为了向后兼容Spring 2.x中的JDK 1.4。

TaskRejectedException

当TaskExecutor拒绝接受给定任务执行时引发的异常。

TaskTimeoutException

当AsyncTaskExecutor因为指定的超时而拒绝接受给定任务执行时引发的异常。

### type

#### classreading

AbstractRecursiveAnnotationVisitor
AnnotationAttributesReadingVisitor
AnnotationMetadataReadingVisitor
AnnotationReadingVisitorUtils
CachingMetadataReaderFactory
ClassMetadataReadingVisitor
MergedAnnotationReadingVisitor
MetadataReader
MetadataReaderFactory
MethodMetadataReadingVisitor
package-info
RecursiveAnnotationArrayVisitor
RecursiveAnnotationAttributesVisitor
SimpleAnnotationMetadata
SimpleAnnotationMetadataReadingVisitor
SimpleMetadataReader
SimpleMetadataReaderFactory
SimpleMethodMetadata
SimpleMethodMetadataReadingVisitor

#### filter

AbstractClassTestingTypeFilter
AbstractTypeHierarchyTraversingFilter
AnnotationTypeFilter
AspectJTypeFilter
AssignableTypeFilter
package-info
RegexPatternTypeFilter
TypeFilter

#### 其他

AnnotatedTypeMetadata
AnnotationMetadata
ClassMetadata
MethodMetadata
package-info
StandardAnnotationMetadata
StandardClassMetadata
StandardMethodMetadata

## lang

## objenesis

## util