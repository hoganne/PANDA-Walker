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

解码器实现的抽象基类。

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

## lang

## objenesis

## util