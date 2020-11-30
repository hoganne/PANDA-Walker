# javaPerformance

## 介绍

jvm接收两种标志：boolean flag，需要参数的标志

Boolean flags use this syntax: -XX:+*FlagName* enables the flag, and -XX:-*FlagName* disables the flag.

不应该过早优化的重点是：最后，您应该编写干净，直接，易于阅读和理解的代码。在这种情况下，“优化”应理解为意味着采用算法和设计更改，这些更改会使程序结构复杂但提供更好的性能。确实最好不要进行此类优化，直到对程序进行性能分析表明执行这些优化有很大的好处之前。

```java
log.log(Level.FINE, "I am here, and the value of X is "+ calcX() + " and Y is " + calcY());
```

避免了不必要的日志打印调用，而且可以避免字符串连接：

```java
if (log.isLoggable(Level.FINE)) { 
	log.log(Level.FINE,"I am here, and the value of X is {} and Y is {}",new Object[]{calcX(), calcY()});
}
```

### Look Elsewhere: The Database Is Always the Bottleneck（数据库永远是瓶颈）

出现性能问题，常见案例分析表明，首先要考虑系统的最新部分（通常是JVM中的应用程序），但要准备研究环境的每个可能组件。

另一方面，请不要忽略最初的分析。如果数据库是瓶颈（这里有个提示:它是），那么调整访问数据库的Java应用程序将根本无法提高整体性能。实际上，这可能适得其反。通常，当负载增加到负担过重的系统中时，该系统的性能会变差。如果在Java应用程序中进行了某些更改，使其更加高效（这只会增加已经超载的数据库的负载）总体性能实际上可能会下降。这样一来，危险就得出了不正确的结论，即不应使用特定的JVM改进。

[这个原则（限制系统中运行不良的组件的负载会降低整个系统的运行速度）并不局限于数据库。当将负载添加到受CPU约束的应用程序服务器时，或者如果更多线程开始访问已经有线程在等待它的锁，或者在许多其他情况下，它将适用。](part242.htm#bookmark636)[第](part242.htm#bookmark636)9[章](part242.htm#bookmark636)[显示了一个仅涉及JVM的极端示例](part242.htm#bookmark636)。

### 对于通常的情况优化

将所有性能方面都视为同等重要，这是很诱人的，尤其是考虑到“千分之多的死亡”综合症。但是应该将重点放在常见的用例场景上。

该原则以几种方式体现出来：

[•通过对代码进行概要分析并专注于花费最多时间的配置文件中的操作来优化代码。但是请注意，这并不意味着仅查看配置文件中的叶子方法（请参见](part53.htm#bookmark144)第3章）。

•将Occam的Razor应用于诊断性能问题。对性能问题的最简单解释是最可能的原因：新代码中的性能错误比计算机上的配置问题更有可能发生，而机器上的配置问题则比JVM或操作系统中的错误更有可能发生。确实存在隐晦的错误，并且由于排除了导致性能问题的更可靠原因，因此有可能以某种方式使所涉及的测试用例触发了此类潜在错误。但是不要先跳到不太可能的情况。

•为应用程序中最常见的操作编写简单的算法。以一个估计一些数学公式的程序为例，用户可以在其中确定自己想要的答案是在10％的误差范围内还是1％的误差范围内。如果大多数用户对10％的余量感到满意，则可以优化该代码路径-即使这意味着减慢提供1％的误差余量的代码。

### 微基准测试（优化）

这些类别中的第一个是微基准。微基准测试是一种旨在测量很小的性能单位的测试：

调用同步方法与非同步方法的时间；

创建线程与使用线程池的开销；

执行一种算术算法与另一种实现的时间；等等。微基准测试似乎是一个好主意，但很难正确编写。考虑以下代码，这是一种尝试编写微基准测试的尝试，该微基准测试用于测试计算第50个斐波那契数的方法的不同实现的性能：

```java
public void doTest() {
// Main Loop
double l;
long then = System.currentTimeMillis();
for (int i = 0; i < nLoops; i++) { 
l = fibImpl1(50);
}
...
long now = System.currentTimeMillis(); 
System.out.println("Elapsed time: " + (now - then));
}


private double fibImpl1(int n) {
if (n < 0) throw new IllegalArgumentException("Must be > 0");
if (n == 0) return 0d;
if (n == 1) return 1d;
double d = fibImpl1(n - 2) + fibImpl(n - 1);
if (Double.isInfinite(d)) throw new ArithmeticException("Overflow");
return d;
}
```

解决该特定问题的方法是：

确保读取每个结果，而不是简单地写入。实际上，将l的定义从局部变量更改为实例变量（使用volatile关键字声明）将可以测量该方法的性能。（l实例变量必须声明为volatile的原因[可以在](part242.htm#bookmark636)第9章中[找到](part242.htm#bookmark636)。）

考虑两个线程在微基准测试中调用同步方法的情况。由于基准代码很小，因此大多数代码将在该同步方法中执行。即使整个微基准测试中只有50％位于同步方法之内，只有两个线程尝试同时执行同步方法的几率也很高。结果，基准测试将运行非常缓慢，并且随着添加更多线程，由争用增加引起的性能问题将变得更糟。最终的结果是，测试最终将衡量JVM如何处理争用而不是微基准测试的目标。

#### 微基准测试不得包含多余的操作

即使那样，仍然存在潜在的陷阱。此代码仅执行一个操作：

计算第50个斐波那契数。一个非常聪明的编译器可以找出并执行一次循环，或者至少丢弃一些循环迭代，因为这些操作是多余的。

此外，fibImpl（1000）的性能可能与fibImpl（1）的性能有很大不同。如果目标是比较不同实施的性能，则必须考虑一定范围的输入值。

为了克服这个问题，传递给fibImpl1（）方法的参数必须变化。解决方案是使用随机值，但也必须小心进行。

编码使用随机数生成器的简单方法是按以下方式处理循环：

```
for (int i = 0; i < nLoops; i++) {
l = fibImpl1(random.nextInteger());
}
```

现在，执行循环的时间中包括了计算随机数的时间，因此测试现在将计算nLoops次的斐波那契数列的时间加上生成nLoops个随机整数的时间。这可能不是目标。

在微基准测试中，必须预先计算输入值，例如：

```java
int[] input = new int[nLoops];

for (int i = 0; i < nLoops; i++) { 
    input[i] = random.nextInt();
}

long then = System.currentTimeMillis();
for (int i = 0; i < nLoops; i++) {

try {

l = fibImpl1(input[i]);

} catch (IllegalArgumentException iae) {

}
}
long now = System.currentTimeMillis();
```

#### 微基准测试必须测量正确的输入

这里的第三个陷阱是测试的输入范围：

选择任意随机值不一定代表如何使用代码。在这种情况下，对被测方法的一半调用（任何带有负值的调用）都会立即引发异常。只要输入参数大于1476，就会抛出异常，因为那是可以用双精度表示的最大斐波那契数。

在斐波那契计算明显更快但在计算结束之前未检测到异常情况的实现中会发生什么？考虑以下替代实现：

```java
public double fibImplSlow(int n) {

	if (n < 0) throw new IllegalArgumentException("Must be > 0"); 
    if (n > 1476) throw new ArithmeticException("Must be < 1476"); 
    return verySlowImpl(n);
}
```

很难想象实现会比原始的递归实现慢，但是假定在此代码中已设计并使用了一个实现。在非常宽的输入值范围内，将该实现与原始实现进行比较将显示此新实现比原始实现快得多，这是因为方法开始时进行了范围检查。

如果在现实世界中，用户只会向该方法传递小于100的值，那么这种比较将给我们错误的答案。在常见情况下，fibImpl（）[方法会更快，并且如](part23.htm#bookmark21)第1章所述，我们应针对常见情况进行优化。（这显然是一个人为的示例，仅向原始实现添加边界测试就可以使其成为更好的实现。在一般情况下，这可能是不可能的。）

**What About a Warm-Up Period?**(预热期)

[Java的性能特征之一是代码执行得越多，性能就会越好，这是](part92.htm#bookmark248)[第](part92.htm#bookmark248)4[章中](part92.htm#bookmark248)[讨论的主题](part92.htm#bookmark248)。因此，微基准测试必须包含一个预热期，这使编译器有机会生成最佳代码。

本章稍后将详细讨论预热期的优缺点。对于微基准测试，需要预热时间；否则，性能指标将衡量编译的性能，而不是它要衡量的代码。

综上所述，微基准的正确编码如下所示：

```java
public class FibonacciTest {

	private volatile double l; 
    private int nLoops; private int[] input;


public static void main(String[] args) {

	FibonacciTest ft = new FibonacciTest(Integer.parseInt(args[0])); 
    ft.doTest(true);

	ft.doTest(false);

}



private FibonacciTest(int n) {
    nLoops = n;

	input = new int[nLoops]; 
    Random r = new Random();

for (int i = 0; i < nLoops; i++) {
    input[i] = r.nextInt(100);

}

}



private void doTest(boolean isWarmup) {
    long then = System.currentTimeMillis(); 
    for (int i = 0; i < nLoops; i++) {
	l = fibImpl1(input[i]);
}

if (!isWarmup) {
	long now = System.currentTimeMillis(); 
    System.out.println("Elapsed time: " + (now - then));
}
}

private double fibImpl1(int n) {

if (n < 0) throw new IllegalArgumentException("Must be > 0");

if (n == 0) return 0d;

if (n == 1) return 1d;

double d = fibImpl1(n - 2) + fibImpl(n - 1);

if (Double.isInfinite(d)) throw new ArithmeticException("Overflow");

return d;

}
}
```

甚至这个微基准测试都测量了一些与Fibonacci实现无关的事情：在设置对fibImpl1（）方法的调用时存在一定数量的循环和方法开销，并且还需要将每个结果写入volatile变量高架。

还要注意其他编译效果。编译器使用代码的配置文件反馈来确定在编译方法时要采用的最佳优化。

配置文件反馈基于频繁调用的方法，调用它们时的堆栈深度，其参数的实际类型（包括子类）等等，这取决于代码实际运行的环境。与在应用程序中使用相同的代码时，编译器经常会在微基准测试中对代码进行不同的优化。如果同一类测量斐波那契方法的第二种实现，则可能发生各种编译效果，尤其是如果实现发生在不同的类中。

[最后，存在微基准实际上意味着什么的问题。对于大量循环，基准（例如此处讨论的基准）中的整体时间差异可能以秒为单位进行度量，但每次迭代的差异通常以纳秒为单位进行度量。是的，十亿分之一秒的总和，“一千次裁员死亡”是一个常见的性能问题。但是，尤其是在回归测试中，请考虑在纳秒级跟踪某些东西是否真正有意义。在每次访问将要访问数百万次的集合时，节省几纳秒的时间可能很重要（例如，请参见](part351.htm#bookmark914)[第](part351.htm#bookmark914)12[章](part351.htm#bookmark914)）。对于不经常发生的操作（例如，每个servlet请求可能一次），修复微基准所发现的纳秒级回归将占用时间，而这些时间本可以更有利地用于优化其他操作。

编写微基准测试很难。有用的时间非常有限。请注意所涉及的陷阱，并确定获得合理的微基准测试所涉及的工作是否值得受益—或者集中精力进行更多的宏观水平测试是否更好。



Macrobenchmarks

衡量应用程序性能的最佳方法是应用程序本身，以及它使用的所有外部资源。如果应用程序通常通过进行LDAP调用来检查用户的凭据，则应在该模式下对其进行测试。进行LDAP调用存根对于模块级测试可能很有意义，但是必须在完整配置下对应用程序进行测试。

随着应用程序的增长，这一准则变得越来越重要，难以实现和实现。复杂的系统不仅仅是它们各个部分的总和。组装这些零件时，它们的行为会大不相同。例如，模拟数据库调用可能意味着您不再需要担心数据库性能-嘿，您是Java人士。您为什么要应对别人的绩效问题？但是数据库连接会为其缓冲区占用大量堆空间；当通过网络发送更多数据时，网络变得饱和；调用更简单的方法集时，代码的优化方式有所不同（与JDBC驱动程序中的复杂代码相反）；与较长的代码路径相比，CPU可以更有效地管线化和缓存较短的代码路径；等等。

测试完整应用程序的另一个原因是资源分配之一。在理想的情况下，将有足够的时间来优化应用程序中的每一行代码。

在现实世界中，最后期限迫在眉睫，仅优化复杂环境的一部分可能不会立即产生收益。

[考虑](part39.htm#bookmark82)图2-1[中](part39.htm#bookmark82)所示[的数据流](part39.htm#bookmark82)。数据来自用户，进行一些专有业务计算，从数据库中加载一些基于该数据的数据，进行更多专有计算，将更改后的数据存储回数据库，并将答案发送回用户。每个框中的数字是每秒进行隔离测试时模块可以处理的每秒请求数（例如200 RPS）。

![图片](file:///E:/oldF/learningDocument/book_html/java_performance/Java_Performance/Image_028.png)

图2-1。典型程序流程

从业务角度来看，专有计算是最重要的；它们是程序存在的原因，也是我们获得报酬的原因。但是，在本示例中，使其速度提高100％绝对不会带来任何好处。可以将任何应用程序（包括一个独立的JVM）建模为一系列这样的步骤，其中数据以框的效率决定的速度从框（模块，子系统等）中流出。（在该模型中，该时间包括该子系统中的代码，还包括网络传输时间，磁盘传输时间等。在模块模型中，该时间仅包括该模块的代码。）数据在以下位置流入子系统由上一个框的输出速率确定的速率。

假设对业务计算进行了算法改进，使其可以处理200 RPS；注入系统的负载相应增加。LDAP系统可以处理增加的负载：到目前为止，效果很好，并且200 RPS将流入计算模块，该模块将输出200 RPS。

但是数据库仍然只能处理100 RPS。即使200 RPS流入数据库，也只有100 RPS流出数据库并流入其他模块。即使业务逻辑的效率提高了一倍，系统的总吞吐量仍仅为100 RPS。在花时间改善环境的其他方面之前，进一步改善业务逻辑的尝试将被证明是徒劳的。

#### 使用多个JVM进行完整的系统测试

当在同一硬件上同时运行多个应用程序时，将发生一个测试整个应用程序的特别重要的情况。默认情况下，JVM的许多方面都进行了调整，以假定所有机器资源均可供他们使用，并且如果对这些JVM进行单独测试，它们将表现良好。如果在存在其他应用程序（包括但不限于其他JVM）的情况下对它们进行了测试，则它们的性能将大不相同。

在后面的章节中将给出示例，但是这里是一个快速预览：执行GC周期时，一个JVM（以其默认配置）将驱动计算机上的CPU使用率达到所有处理器的100％。如果将CPU作为程序执行期间的平均值来衡量，则使用率可能平均为40％，但这确实意味着CPU有时繁忙30％，有时繁忙100％。当JVM独立运行时，可能会很好，但是如果JVM与其他应用程序同时运行，则在GC期间它将无法获得100％的计算机CPU。它的性能将与它自己运行时明显不同。

这就是为什么微基准测试和模块级基准测试不一定能为您提供应用程序性能的全貌的另一个原因。

在此示例中，并非完全浪费了优化计算所花费的时间：一旦将精力投入到系统中其他地方的瓶颈中，性能优势将最终显而易见。相反，这是一个优先事项：

如果不测试整个应用程序，就不可能确定花时间在性能工作上会得到回报。

#### 总结

我同时使用Java SE和EE的性能，并且每个小组都有一组测试，它们将它们描述为微基准。对于Java SE工程师而言，该术语表示的示例甚至比第一部分中的示例还要小：对非常小的内容的度量。Java EE工程师倾向于使用该术语将其应用于其他方面：衡量性能的一个方面，但仍然执行大量代码的基准。

Java EE“微基准”的一个示例可能是衡量从应用服务器返回简单JSP响应的速度的方法。与传统的微生物标记相比，这种请求所涉及的代码是相当多的：

有许多套接字管理代码，用于读取请求的代码，用于查找（并可能编译）JSP的代码，用于编写答案的代码，等等。从传统的角度来看，这不是微基准测试。

这种测试也不是宏基准测试：没有安全性（例如，用户未登录到应用程序），没有会话管理以及没有使用其他Java EE功能的主机。因为它只是实际应用程序的一个子集，所以它落在中间位置—它是中基准。Mesobenchmarks不仅限于Java EE领域：这是我用于基准测试的术语，它可以完成一些实际工作，但不是完整的应用程序。

中基准比微基准具有更少的陷阱，并且比宏基准更易于使用。中基准不太可能包含可以由编译器优化的大量死代码（除非死代码确实存在于应用程序中，在这种情况下将其优化是一件好事）。Mesobenchmark易于线程化：

与在完整应用程序中运行时所遇到的同步瓶颈相比，代码仍然更有可能遇到更多的同步瓶颈，但是这些瓶颈是真正的应用程序最终将在较大负载下在大型硬件系统上遇到的瓶颈。

[但是，中基准并不是完美的。使用这样的基准比较两个应用程序服务器的性能的开发人员可能容易误入歧途。考虑](part40.htm#bookmark88)表2-1中[所示的两个应用服务器的假设响应时间](part40.htm#bookmark88)。

表2-1。两个应用程序服务器的假想响应时间

​                  测试App服务器1   App服务器2

简单的JSP 19毫秒                   50毫秒

会话为      75毫秒的JSP          50毫秒

仅使用简单的JSP比较两个服务器性能的开发人员可能不会意识到服务器2正在为每个请求自动创建会话。然后，她可能会得出结论，服务器1将为她提供最快的性能。但是，如果她的应用程序总是创建一个会话（这是典型的），那么她将做出错误的选择，因为创建会话需要花费更多的服务器1。（后续调用的性能是否有所不同是另一个要考虑的问题，但是无法根据此数据预测一旦创建会话后哪个服务器的性能会更好。）

即便如此，中基准测试还是测试全面应用程序的合理替代方法。与微基准的性能特征相比，它们的性能特征与实际应用更加紧密地吻合。当然这里有一个连续体。本章后面的部分介绍了后续各章中许多示例所用的通用应用程序的概述。该应用程序具有EE模式，但是在大多数示例中，该模式不使用会话复制（高可用性）或基于EE平台的安全性，尽管它可以访问企业资源（即数据库）。它只是组成随机数据。在SE模式下，它模仿一些实际（但快速）的计算：例如，没有GUI或用户交互发生。

```
Mesobenchmarks也适用于自动化测试，尤其是在模块级别。
```

#### 常见代码示例

本书中的许多示例均基于一个示例应用程序，该示例应用程序计算了某个日期范围内股票的“历史”高低价格，以及该时间段内的标准差。这里的历史用引号引起来，因为在应用程序中，所有数据都是虚构的。价格和股票代码是随机生成的。

[本书所有示例的完整源代码都在我的](https://github.com/ScottOaks/JavaPerformanceTuning)GitHub页面上，但是本节介绍了有关代码的基本要点。应用程序内的基本对象是StockPrice对象，它代表给定日期的股票价格范围：

```java
public interface StockPrice {
    String getSymbol(); Date getDate();
    
	BigDecimal getClosingPrice(); 
    
    BigDecimal getHigh(); 
    
    BigDecimal getLow(); 
    
    BigDecimal getOpeningPrice(); 
    
    boolean isYearHigh();

	boolean isYearLow();

	Collection<? extends StockOptionPrice> getOptions();

}
```

示例应用程序通常处理这些价格的集合，这些价格代表一段时间（例如1年或25年，取决于示例）中的库存历史记录：

```java
public interface StockPriceHistory { 
    
    StockPrice getPrice(Date d);

	Collection<StockPrice> getPrices(Date startDate, Date endDate);
	Map<Date, StockPrice> getAllEntries();
    Map<BigDecimal,ArrayList<Date>> getHistogram();

	BigDecimal getAveragePrice();
    Date getFirstDate();

	BigDecimal getHighPrice();
    Date getLastDate();
    BigDecimal getLowPrice();
    BigDecimal getStdDev();
    String getSymbol();

}

//The basic implementation of this class loads a set of prices from the database:
//这个类的基本实现从数据库中加载一组价格:
public class StockPriceHistoryImpl implements StockPriceHistory {


public StockPriceHistoryImpl(String s, Date startDate, Date endDate, EntityManager em) {

Date curDate = new Date(startDate.getTime()); 
symbol = s;

while (!curDate.after(endDate)) {

StockPriceImpl sp = em.find(StockPriceImpl.class,new StockPricePK(s, (Date) curDate.clone()));
if (sp != null) {

Date d = (Date) curDate.clone();

if (firstDate == null) { firstDate = d;

}

prices.put(d, sp); lastDate = d;

}

curDate.setTime(curDate.getTime() + msPerDay);

}

}

...

}
```

[样本的体系结构旨在从数据库中加载，该功能将在](part315.htm#bookmark829)第11章[的示例中使用](part315.htm#bookmark829)[。但是，为了便于运行示例，大多数情况下，他们将使用模拟实体管理器来为系列生成随机数据。从本质上讲，大多数示例都是适合于说明当前性能问题的模块级中标，但是当整个应用程序运行时，我们只能对应用程序的实际性能有所了解（如](part315.htm#bookmark829)第11章中所述）。

一个警告是，许多示例因此取决于所使用的随机数发生器的性能。与微基准示例不同，这是设计使然，因为它可以说明Java中的几个性能问题。（就此而言，示例的目的是测量某种任意事物的性能，而随机数生成器的性能符合该目标。这与微基准测试完全不同，后者包括生成随机数的时间。 ‐贝尔会影响整体计算。）

这些示例还严重依赖于BigDecimal类的性能，该类用于存储所有数据点。这是存储货币的标准选择

数据; 如果将货币数据存储为原始的双重对象，那么半美分和较小金额的取整就变得很成问题。从书写示例的角度来看，该选择也很有用，因为它允许进行一些“业务逻辑”或冗长的计算，尤其是在计算一系列价格的标准差时。标准偏差取决于知道BigDecimal数的平方根。标准的Java API不提供这样的例程，但是示例使用此方法：

```java
public static BigDecimal sqrtB(BigDecimal bd) { 
    
    BigDecimal initial = bd;

	BigDecimal diff;

do {
	BigDecimal sDivX = bd.divide(initial, 8, RoundingMode.FLOOR); 
    BigDecimal sum = sDivX.add(initial);

	BigDecimal div = sum.divide(TWO, 8, RoundingMode.FLOOR); 
    diff = div.subtract(initial).abs();

	diff.setScale(8, RoundingMode.FLOOR); initial = div;

} while (diff.compareTo(error) > 0);

return initial;

}
```

[这是用于估计数字平方根的巴比伦方法的实现。这不是最有效的实现。特别是，最初的猜测可能会更好，这样可以节省一些迭代。这是有意的，因为它允许计算花费一些时间（模拟业务逻辑），尽管它确实说明了](part23.htm#bookmark21)[第](part23.htm#bookmark21)1[章中](part23.htm#bookmark21)[的基本观点](part23.htm#bookmark21)：通常，使Java代码更快的更好方法是编写更好的算法，而与任何Java无关调整或采用的Java编码实践。

StockPriceHistory接口的实现的标准差，平均价格和直方图都是派生值。在不同的示例中，这些值将被急切地计算（当从实体管理器加载数据时）或懒惰地（计算检索数据的方法时）。同样，StockPrice接口引用StockOptionPrice接口，该接口是给定股票在给定日期的某些期权的价格。可以从实体管理器中积极或懒惰地检索那些选项值。在这两种情况下，这些接口的定义都可以在不同情况下比较这些不同的方法。

这些接口也自然适合Java EE应用程序：用户可以访问JSP页面，该页面允许她输入感兴趣的股票的代码和日期范围。在标准示例中，请求将通过解析的标准servlet进行。输入参数，使用嵌入式Java持久性API（JPA）bean调用无状态Enterprise JavaBean（EJB）以获取基础数据，并将响应转发到Java Server Pages（JSP）页面，该页面格式化基础数据转换成HTML演示文稿：

```java
protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

try {

String symbol = request.getParameter("symbol");

if (symbol == null) {

symbol = StockPriceUtils.getRandomSymbol();

}

//... similar processing for date and other params...对日期和其他参数进行类似的处理

StockPriceHistory sph; 
DateFormat df = localDf.get();

sph = stockSessionBean.getHistory(symbol, df.parse(startDate), df.parse(endDate), doMock, impl);

String saveSession = request.getParameter("save");

if (saveSession != null) {

//.... Store the data in the user's session ....将数据存储在用户的会话中

//.... Optionally store the data in a global cache for可选地将数据存储在全局缓存中

//.... use by other requests由其他请求使用

}

if (request.getParameter("long") == null) {

// Send back a page with about 4K of data返回一个包含大约4K数据的页面

request.getRequestDispatcher("history.jsp"). forward(request, response);

}

else {

// Send back a page with about 100K of data返回一个包含大约100K数据的页面

request.getRequestDispatcher("longhistory.jsp"). forward(request, response);
}

}
```



此类可以注入历史Bean的不同实现（尤其是用于急切或延迟初始化）。它可以选择缓存从后端数据库（或模拟实体管理器）检索到的数据。这些是处理企业应用程序性能时的常用选项（特别是在中间层缓存数据有时被认为是应用程序服务器的最大性能优势）。本书中的示例还将研究这些折衷。

被测系统**System Under Test**

即使本书主要侧重于软件，基准测试也可以衡量它们所运行的硬件。

大部分情况下，本书中的示例都在我的台式机系统上运行，该台式机系统具有AMD Athlon X4 640 CPU，该CPU具有四个内核（四个逻辑CPU）和8 GB的物理内存，并运行Ubuntu Linux 12.04 LTS。

#### Understand Throughput, Batching, and Response Time（了解吞吐量，批处理，和响应时间）

#### 经过时间（批次）测量

衡量绩效的最简单方法是查看完成一项任务需要多长时间：检索25年内10,000只股票的历史记录，并计算这些价格的标准差；生成公司50,000名员工的工资福利报告；执行一次循环1,000,000次。

[在非Java世界中，此测试非常简单：编写应用程序，并测量其执行时间。在Java世界中，这有一个缺点：即时编译。该过程在](part92.htm#bookmark248)第4章[中进行了描述](part92.htm#bookmark248)。本质上，这意味着代码需要花费几分钟（或更长时间）来完全优化并以最佳性能运行。因此（或其他）原因，对Java的性能研究非常关注预热期：性能通常是在所讨论的代码执行了足够长的时间以进行编译和优化之后才进行测量。

暖化应用程序的其他因素

通常在等待编译器优化所讨论的代码方面对应用程序进行预热，但是还有其他一些因素可能会影响代码性能，具体取决于运行时间。

[例如，JPA通常将缓存从数据库中读取的数据（请参见](part315.htm#bookmark829)[第](part315.htm#bookmark829)11[章](part315.htm#bookmark829)）。第二次使用数据时，操作通常会更快，因为可以从缓存中获取数据，而不需要访问数据库。同样，当应用程序读取文件时，操作系统通常会将文件分页到内存中。随后读取同一文件（例如，在循环中以测量性能）的测试将第二次运行得更快，因为数据已经驻留在计算机的主内存中，实际上不需要从磁盘读取数据。

通常，可以在许多地方（并非所有人都显而易见）缓存数据以及预热期很重要。

另一方面，在许多情况下，应用程序从头到尾的性能至关重要。处理10,000个数据元素的报告生成器将在一定时间内完成；对于最终用户而言，头5,000个元素的处理速度比后5,000个元素慢50％无关紧要。甚至在像应用程序服务器这样的服务器性能肯定会随着时间而提高的情况下，初始性能也很重要。应用服务器可能需要45分钟在某些配置中达到最高性能。对于那段时间访问应用程序的用户而言，预热期间的性能确实很重要。

由于这些原因，本书中的许多示例都是面向批处理的（即使这很少见）。

####  吞吐量测量（Throughput Measurements）

吞吐量测量基于一定时间内可以完成的工作量。尽管最常见的吞吐量测量示例涉及服务器处理客户端馈送的数据，但这并不是绝对必要的：

单个独立应用程序可以像测量经过时间一样轻松地测量吞吐量。

在客户端服务器测试中，吞吐量测量意味着客户端没有思考时间。如果只有一个客户端，则该客户端将请求发送到服务器。收到响应后，它将立即发送一个新请求。这个过程继续进行；在测试结束时，客户端报告其已完成的操作总数。通常，客户端有多个线程在做同一件事，而吞吐量是所有客户端完成的操作数的总和。通常，此数字报告为每秒的操作数，而不是测量期间的操作总数。此度量通常称为每秒事务（TPS），每秒请求（RPS）或每秒操作（OPS）。

所有客户端-服务器测试都存在客户端无法将数据足够快地发送到服务器的风险。发生这种情况的原因是，客户端计算机上没有足够的CPU周期来运行所需数量的客户端线程，或者因为客户端在发送新请求之前必须花费大量时间来处理请求。在这些情况下，测试实际上是在衡量客户端性能，而不是服务器性能，这通常不是目标。

这种风险取决于每个客户端线程执行的工作量（以及客户端计算机的大小和配置）。零思考时间（面向吞吐量）测试很可能会遇到这种情况，因为每个客户端线程都在执行大量工作。因此，与测试响应时间的相应测试相比，吞吐量测试通常使用更少的客户端线程（更少的负载）执行。

测试吞吐量的测试通常也会报告其请求的平均响应时间。这是一条有趣的信息，但是除非报告的吞吐量相同，否则该数字的更改并不表示性能问题。能够以0.5秒的响应时间维持500个OPS的服务器，比报告0.3秒的响应时间但仅400 OPS的服务器，性能要更好。

吞吐量测量几乎总是在适当的预热期后进行的，特别是因为所测量的不是固定的工作。

##### Response Time Tests 响应时间测试

最后一个通用测试是测量响应时间的测试：从客户端发送请求到接收响应之间经过的时间。

响应时间测试和吞吐量测试（假设后者是基于客户端-服务器的）之间的区别在于，响应时间测试中的客户端线程在两次操作之间会休眠一段时间。这称为*思考时间*。响应时间测试旨在更紧密地模仿用户的操作：她在浏览器中输入URL，花一些时间阅读返回的页面，单击页面中的链接，花一些时间阅读该页面，等等上。

将思考时间引入测试后，吞吐量将变得固定：在给定思考时间下执行请求的给定数量的客户端将始终产生相同的TPS（略有差异；请参见侧栏）。那时，重要的衡量标准是请求的响应时间：服务器的有效性取决于服务器对固定负载的响应速度。

### 思考时间和吞吐量

客户包括思考时间的测试吞吐量可以通过两种方式进行衡量。最简单的方法是让客户端在两次请求之间睡眠一段时间：

```java
while (!done) {
time = executeOperation(); 
Thread.currentThread().sleep(30*1000);
}
```

在这种情况下，吞吐量在某种程度上取决于响应时间。如果响应时间为1秒，则意味着客户端将每31秒发送一次请求，这将产生0.032 OPS的吞吐量。如果响应时间为2秒，则每个客户端将每32秒发送一次请求，从而产生0.031 OPS的吞吐量。

另一种选择是循环时间（而不是思考时间）。周期时间将两次请求之间的总时间设置为30秒，因此客户端休眠的时间取决于响应时间：

```java
while (!done) {
time = executeOperation(); 
Thread.currentThread().sleep(30*1000 - time);

}
```

无论响应时间如何（假设响应时间始终小于30秒），此替代方案将为每个客户端提供0.033 OPS的固定吞吐量。

测试工具中的思考时间通常因设计而异。他们将平均一个特定值，但使用随机变化更好地模拟用户的行为。此外，线程调度永远不会是完全实时的，因此客户端发送请求之间的实际时间会略有不同。

![image](file:///E:/oldF/learningDocument/book_html/java_performance/Java_Performance/Image_033.png)

结果，即使使用提供周期时间而不是思考时间的工具，两次运行之间的报告吞吐量也会略有不同。但是，如果吞吐量远未达到预期值，则在执行测试时会出现问题。

有两种测量响应时间的方法。可以将响应时间报告为平均值：

将各个时间加在一起，然后除以请求数。响应时间也可以报告为百分比请求，例如响应时间的90％。如果90％的响应时间小于1.5秒，而10％的响应时间大于1.5秒，则1.5秒是第90％的响应时间。

这两个数字之间的区别是离群值影响平均值计算的方式：由于它们被包括在平均值中，因此较大的离群值将对平均响应时间产生较大影响。

图2-2显示了20条请求的图表，其中响应时间的范围较为典型。响应时间为1到5秒。平均响应时间（沿x轴的下部粗线表示）为2.35秒，其中90％的响应发生在4秒或更短的时间内（沿x轴的上部粗线表示）。

![image](file:///E:/oldF/learningDocument/book_html/java_performance/Java_Performance/Image_034.png)

图2-2。典型的响应时间

[这是行为良好的测试的通常情况。异常值可能会使该分析偏斜，](part47.htm#bookmark104)如图2-3中[的数据](part47.htm#bookmark104)所示。

了解吞吐量，批处理和响应时间| 27

图2-3。一组具有异常值的响应时间

该数据集包括一个巨大的异常值：一个请求花费了100秒。结果，第90％的位置和平均响应时间相反。平均响应时间为5.95秒，但90％的响应时间为1.0秒。在这种情况下，应着重减少离群值的影响（这将缩短平均响应时间）。

负载发生器 **Load Generators**

[有许多开源和商业负载生成工具。本书中的示例使用](http://faban.org/)Faban（一种基于Java的开源负载生成器）。Faban带有一个简单程序（fhb），可用于测量简单URL的性能：

% **fhb -W 1000 -r 300/300/60 -c 25 http://host:port/StockServlet?stock=SDO**

运算/秒：8.247

错误百分比：0.0

平均 时间：0.022

ops/sec: 8.247

% errors: 0.0

avg. time: 0.022

这样的异常值通常很少见，尽管由于GC引入了暂停时间，它们在Java应用程序中更容易出现。（并不是说应该期望垃圾回收会带来100秒的延迟，但特别是对于平均响应时间较短的测试，GC暂停可能会引入明显的异常值。）在性能测试中，通常将重点放在响应的90％上。时间（有时是95％或99％的响应时间；对于90％而言，没有什么神奇的）。如果您只能关注一个数字，则基于百分位数的数字是更好的选择，因为获得较小的数字将使大多数用户受益。但是最好同时查看平均响应时间和至少一个基于百分位数的响应时间，这样您就不会错过异常值较大的情况。

最长时间：0.045

90th ％：0.030

95th ％：0.035

99th ％：0.035

此示例度量了25个客户端（-c 25）向库存servlet发出请求（请求符号SDO）；每个请求都有一个1秒的循环时间（-W 1000）。基准测试有5分钟（300秒）的预热时间，然后是5分钟的测量时间和1分钟的下降时间（-r 300/300/60）。在测试之后，fhb报告测试的OPS和各种响应时间（并且由于该示例包括思考时间，因此响应时间是重要的指标，而OPS或多或少是恒定的）。

fhb可以使用有限的替换和具有多个URL的有限脚本来处理POST数据。对于更复杂的测试，Faban有一个有用的框架，可以用Java定义基准负载生成器。

47.html