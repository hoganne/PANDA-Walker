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

## lang

## objenesis

## util