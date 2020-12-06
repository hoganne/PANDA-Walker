# basic terminal commands（liunx）

grep

## awk  

AWK 是一种处理文本文件的语言，是一个强大的文本分析工具。

之所以叫 AWK 是因为其取了三位创始人 Alfred Aho，Peter Weinberger, 和 Brian Kernighan 的 Family Name 的首字符。

语法

```
awk [选项参数] 'script' var=value file(s) 或 awk [选项参数] -f scriptfile var=value file(s)
```

**选项参数说明：**

- -F fs or --field-separator fs
  指定输入文件折分隔符，fs是一个字符串或者是一个正则表达式，如-F:。

- -v var=value or --asign var=value
  赋值一个用户定义变量。

- -f scripfile or --file scriptfile
  从脚本文件中读取awk命令。

- -mf nnn and -mr nnn
  对nnn值设置内在限制，-mf选项限制分配给nnn的最大块数目；-mr选项限制记录的最大数目。这两个功能是Bell实验室版awk的扩展功能，在标准awk中不适用。

- -W compact or --compat, -W traditional or --traditional
  在兼容模式下运行awk。所以gawk的行为和标准的awk完全一样，所有的awk扩展都被忽略。

- -W copyleft or --copyleft, -W copyright or --copyright
  打印简短的版权信息。

- -W help or --help, -W usage or --usage
  打印全部awk选项和每个选项的简短说明。

- -W lint or --lint
  打印不能向传统unix平台移植的结构的警告。

- -W lint-old or --lint-old
  打印关于不能向传统unix平台移植的结构的警告。

- -W posix
  打开兼容模式。但有以下限制，不识别：/x、函数关键字、func、换码序列以及当fs是一个空格时，将新行作为一个域分隔符；操作符**和**=不能代替^和^=；fflush无效。

- -W re-interval or --re-inerval
  允许间隔正则表达式的使用，参考(grep中的Posix字符类)，如括号表达式[[:alpha:]]。

- -W source program-text or --source program-text
  使用program-text作为源代码，可与-f命令混用。

- -W version or --version
  打印bug报告信息的版本。

  ### 基本用法

  log.txt文本内容如下：

  ```
  2 this is a test
  3 Are you like awk
  This's a test
  10 There are orange,apple,mongo
  ```

  用法一：

  ```
  awk '{[pattern] action}' {filenames}   # 行匹配语句 awk '' 只能用单引号
  ```

  实例：

  ```
  # 每行按空格或TAB分割，输出文本中的1、4项
   $ awk '{print $1,$4}' log.txt
   ---------------------------------------------
   2 a
   3 like
   This's
   10 orange,apple,mongo
   # 格式化输出
   $ awk '{printf "%-8s %-10s\n",$1,$4}' log.txt
   ---------------------------------------------
   2        a
   3        like
   This's
   10       orange,apple,mongo
   
  ```

  用法二：

  ```shell
  awk -F  #-F相当于内置变量FS, 指定分割字符
  ```

  实例：

  ```powershell
  # 使用","分割
   $  awk -F, '{print $1,$2}'   log.txt
   ---------------------------------------------
   2 this is a test
   3 Are you like awk
   This's a test
   10 There are orange apple
   # 或者使用内建变量
   $ awk 'BEGIN{FS=","} {print $1,$2}'     log.txt
   ---------------------------------------------
   2 this is a test
   3 Are you like awk
   This's a test
   10 There are orange apple
   # 使用多个分隔符.先使用空格分割，然后对分割结果再使用","分割
   $ awk -F '[ ,]'  '{print $1,$2,$5}'   log.txt
   ---------------------------------------------
   2 this test
   3 Are awk
   This's a
   10 There apple
  ```

  用法三：

  ```
  awk -v  # 设置变量
  ```

  实例：

  ```powershell
   $ awk -va=1 '{print $1,$1+a}' log.txt
   ---------------------------------------------
   2 3
   3 4
   This's 1
   10 11
   $ awk -va=1 -vb=s '{print $1,$1+a,$1b}' log.txt
   ---------------------------------------------
   2 3 2s
   3 4 3s
   This's 1 This'ss
   10 11 10s
  ```

  用法四：

  ```powershell
  awk -f {awk脚本} {文件名}
  ```

  实例：

  ```powershell
   $ awk -f cal.awk log.txt
  ```

  ------

  ### 运算符

  | 运算符                  | 描述                             |
  | :---------------------- | :------------------------------- |
  | = += -= *= /= %= ^= **= | 赋值                             |
  | ?:                      | C条件表达式                      |
  | \|\|                    | 逻辑或                           |
  | &&                      | 逻辑与                           |
  | ~ 和 !~                 | 匹配正则表达式和不匹配正则表达式 |
  | < <= > >= != ==         | 关系运算符                       |
  | 空格                    | 连接                             |
  | + -                     | 加，减                           |
  | * / %                   | 乘，除与求余                     |
  | + - !                   | 一元加，减和逻辑非               |
  | ^ ***                   | 求幂                             |
  | ++ --                   | 增加或减少，作为前缀或后缀       |
  | $                       | 字段引用                         |
  | in                      | 数组成员                         |

  过滤第一列大于2的行

  ```
  $ awk '$1>2' log.txt    #命令
  #输出
  3 Are you like awk
  This's a test
  10 There are orange,apple,mongo
  ```

  过滤第一列等于2的行

  ```
  $ awk '$1==2 {print $1,$3}' log.txt    #命令
  #输出
  2 is
  ```

  过滤第一列大于2并且第二列等于'Are'的行

  ```powershell
  $ awk '$1>2 && $2=="Are" {print $1,$2,$3}' log.txt    #命令
  #输出
  3 Are you
  ```

  打印指定列到最后一列

  ```
awk -F ``" "` `'{for (i=2;i<=NF;i++)printf("%s ", $i);print ""}'
  ```
  
  ------
  
  ### 内建变量
  
  | 变量        | 描述                                                         |
  | :---------- | :----------------------------------------------------------- |
  | $n          | 当前记录的第n个字段，字段间由FS分隔                          |
  | $0          | 完整的输入记录                                               |
  | ARGC        | 命令行参数的数目argc                                         |
  | ARGIND      | 命令行中当前文件的位置(从0开始算)argind                      |
  | ARGV        | 包含命令行参数的数组argv                                     |
  | CONVFMT     | 数字转换格式(默认值为%.6g)ENVIRON环境变量关联数组convfmt     |
  | ERRNO       | 最后一个系统错误的描述errno                                  |
  | FIELDWIDTHS | 字段宽度列表(用空格键分隔)fieldwidths                        |
  | FILENAME    | 当前文件名filename                                           |
  | FNR         | 各文件分别计数的行号fnr                                      |
  | FS          | 字段分隔符(默认是任何空格)                                   |
  | IGNORECASE  | 如果为真，则进行忽略大小写的匹配ignorecase                   |
  | NF          | 一条记录的字段的数目nf                                       |
  | NR          | 已经读出的记录数，就是行号，从1开始nr                        |
  | OFMT        | 数字的输出格式(默认值是%.6g)   ofmt                          |
| OFS         | 输出记录分隔符（输出换行符），输出时用指定的符号代替换行符  sfs |
  | ORS         | 输出记录分隔符(默认值是一个换行符)                           |
  | RLENGTH     | 由match函数所匹配的字符串的长度                              |
  | RS          | 记录分隔符(默认是一个换行符)                                 |
  | RSTART      | 由match函数所匹配的字符串的第一个位置                        |
  | SUBSEP      | 数组下标分隔符(默认值是/034)                                 |
  
  ```
  $ awk 'BEGIN{printf "%4s %4s %4s %4s %4s %4s %4s %4s %4s\n","FILENAME","ARGC","FNR","FS","NF","NR","OFS","ORS","RS";printf "---------------------------------------------\n"} {printf "%4s %4s %4s %4s %4s %4s %4s %4s %4s\n",FILENAME,ARGC,FNR,FS,NF,NR,OFS,ORS,RS}'  log.txt
  FILENAME ARGC  FNR   FS   NF   NR  OFS  ORS   RS
  ---------------------------------------------
  log.txt    2    1         5    1
  log.txt    2    2         5    2
  log.txt    2    3         3    3
  log.txt    2    4         4    4
  $ awk -F\' 'BEGIN{printf "%4s %4s %4s %4s %4s %4s %4s %4s %4s\n","FILENAME","ARGC","FNR","FS","NF","NR","OFS","ORS","RS";printf "---------------------------------------------\n"} {printf "%4s %4s %4s %4s %4s %4s %4s %4s %4s\n",FILENAME,ARGC,FNR,FS,NF,NR,OFS,ORS,RS}'  log.txt
  FILENAME ARGC  FNR   FS   NF   NR  OFS  ORS   RS
  ---------------------------------------------
  log.txt    2    1    '    1    1
  log.txt    2    2    '    1    2
  log.txt    2    3    '    2    3
  log.txt    2    4    '    1    4
  # 输出顺序号 NR, 匹配文本行号
  $ awk '{print NR,FNR,$1,$2,$3}' log.txt
  ---------------------------------------------
  1 1 2 this is
  2 2 3 Are you
  3 3 This's a test
  4 4 10 There are
  # 指定输出分割符
  $  awk '{print $1,$2,$5}' OFS=" $ "  log.txt
---------------------------------------------
  2 $ this $ test
3 $ Are $ awk
  This's $ a $
10 $ There $
  ```
  
  ------
  
  ### 使用正则，字符串匹配
  
```
  # 输出第二列包含 "th"，并打印第二列与第四列
$ awk '$2 ~ /th/ {print $2,$4}' log.txt
  ---------------------------------------------
  this a
  ```
  
  **~ 表示模式开始。// 中是模式。**
  
  ```
# 输出包含 "re" 的行
  $ awk '/re/ ' log.txt
---------------------------------------------
  3 Are you like awk
10 There are orange,apple,mongo
  ```
  
  ------
  
  ### 忽略大小写
  
```
  $ awk 'BEGIN{IGNORECASE=1} /this/' log.txt
---------------------------------------------
  2 this is a test
This's a test
  ```
  
  ------
  
  ### 模式取反
  
  ```
  $ awk '$2 !~ /th/ {print $2,$4}' log.txt
  ---------------------------------------------
  Are like
  a
  There orange,apple,mongo
$ awk '!/th/ {print $2,$4}' log.txt
  ---------------------------------------------
Are like
  a
There orange,apple,mongo
  ```

  ------
  
  ### awk脚本

  关于 awk 脚本，我们需要注意两个关键词 BEGIN 和 END。

  - BEGIN{ 这里面放的是执行前的语句 }
  - END {这里面放的是处理完所有的行后要执行的语句 }
  - {这里面放的是处理每一行时要执行的语句}
  
  假设有这么一个文件（学生成绩表）：
  
  ```
  $ cat score.txt
Marry   2143 78 84 77
  Jack    2321 66 78 45
Tom     2122 48 77 71
  Mike    2537 87 97 95
  Bob     2415 40 57 62
  ```
  
  我们的 awk 脚本如下：
  
  ```
  $ cat cal.awk
  #!/bin/awk -f
  #运行前
  BEGIN {
      math = 0
      english = 0
      computer = 0
   
      printf "NAME    NO.   MATH  ENGLISH  COMPUTER   TOTAL\n"
      printf "---------------------------------------------\n"
  }
  #运行中
  {
      math+=$3
      english+=$4
      computer+=$5
      printf "%-6s %-6s %4d %8d %8d %8d\n", $1, $2, $3,$4,$5, $3+$4+$5
  }
  #运行后
END {
      printf "---------------------------------------------\n"
    printf "  TOTAL:%10d %8d %8d \n", math, english, computer
      printf "AVERAGE:%10.2f %8.2f %8.2f\n", math/NR, english/NR, computer/NR
  }
  ```
  
  我们来看一下执行结果：
  
  ```
  $ awk -f cal.awk score.txt
  NAME    NO.   MATH  ENGLISH  COMPUTER   TOTAL
  ---------------------------------------------
  Marry  2143     78       84       77      239
  Jack   2321     66       78       45      189
  Tom    2122     48       77       71      196
Mike   2537     87       97       95      279
  Bob    2415     40       57       62      159
---------------------------------------------
    TOTAL:       319      393      350
AVERAGE:     63.80    78.60    70.00
  ```

  ------
  
  ### 另外一些实例

  AWK 的 hello world 程序为：

  ```
  BEGIN { print "Hello, world!" }
  ```
  
  计算文件大小

  ```
$ ls -l *.txt | awk '{sum+=$5} END {print sum}'
  --------------------------------------------------
  666581
  ```

  从文件中找出长度大于 80 的行：

  ```
  awk 'length>80' log.txt
  ```
  
  打印九九乘法表
  
  ```shell
  seq 9 | sed 'H;g' | awk -v RS='' '{for(i=1;i<=NF;i++)printf("%dx%d=%d%s", i, NR, i*NR, i==NR?"\n":"\t")}'
  ```

sed

lsof

curl

wget

tail

## head

head 命令可用于查看文件的开头部分的内容，有一个常用的参数 **-n** 用于显示行数，默认为 10，即显示 10 行的内容。

**命令格式：**

```
head [参数] [文件]  
```

**参数：**

- -q 隐藏文件名
- -v 显示文件名
- -c<数目> 显示的字节数。
- -n<行数> 显示的行数。

**实例**

要显示 runoob_notes.log 文件的开头 10 行，请输入以下命令：

```shell
head runoob_notes.log
```

显示 notes.log 文件的开头 5 行，请输入以下命令：

```
head -n 5 runoob_notes.log
```

显示文件前 20 个字节:

```
head -c 20 runoob_notes.log
```

less

find

ssh

kill



## [Vmstat命令详解]

**一、前言**

vmstat命令： 用来获得有关进程、虚存、页面交换空间及 CPU活动的信息。这些信息反映了系统的负载情况

**二、虚拟内存运行原理**

在系统中运行的每个进程都需要使用到内存，但不是每个进程都需要每时每刻使用系统分配的内存空间。当系统运行所需内存超过实际的物理内存，内核会释放某些进程所占用但未使用的部分或所有物理内存，将这部分资料存储在磁盘上直到进程下一次调用，并将释放出的内存提供给有需要的进程使用。

在Linux内存管理中，主要是通过“调页Paging”和“交换Swapping”来完成上述的内存调度。调页算法是将内存中最近不常使用的页面换到磁盘上，把活动页面保留在内存中供进程使用。交换技术是将整个进程，而不是部分页面，全部交换到磁盘上。

分页(Page)写入磁盘的过程被称作Page-Out，分页(Page)从磁盘重新回到内存的过程被称作Page-In。当内核需要一个分页时，但发现此分页不在物理内存中(因为已经被Page-Out了)，此时就发生了分页错误（Page Fault）。

当系统内核发现可运行内存变少时，就会通过Page-Out来释放一部分物理内存。经管Page-Out不是经常发生，但是如果Page-out频繁不断的发生，直到当内核管理分页的时间超过运行程式的时间时，系统效能会急剧下降。这时的系统已经运行非常慢或进入暂停状态，这种状态亦被称作thrashing(颠簸)。

**三、使用****vmstat**

**1.****用法**

vmstat [-a] [-n] [-S unit] [delay [ count]]
vmstat [-s] [-n] [-S unit]
vmstat [-m] [-n] [delay [ count]]
vmstat [-d] [-n] [delay [ count]]
vmstat [-p disk partition] [-n] [delay [ count]]
vmstat [-f]
vmstat [-V]

**-a**：显示活跃和非活跃内存

**-f****：**显示从系统启动至今的fork数量 。

**-m****：**显示slabinfo

**-n****：**只在开始时显示一次各字段名称。

**-s****：**显示内存相关统计信息及多种系统活动数量。

**delay****：**刷新时间间隔。如果不指定，只显示一条结果。

**count****：**刷新次数。如果不指定刷新次数，但指定了刷新时间间隔，这时刷新次数为无穷。

**-d****：**显示磁盘相关统计信息。

**-p****：**显示指定磁盘分区统计信息

**-S****：**使用指定单位显示。参数有 k 、K 、m 、M ，分别代表1000、1024、1000000、1048576字节（byte）。默认单位为K（1024 bytes）

**-V****：**显示vmstat版本信息。

 

**2.****字段含义说明：**

| **类别**                    | **项目**                                                 | **含义**                                                     | **说明**                                                     |
| --------------------------- | -------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| **Procs****（进程）**       | r                                                        | 等待执行的任务数                                             | 展示了正在执行和等待cpu资源的任务个数。当这个值超过了cpu个数，就会出现cpu瓶颈。 |
| B                           | 等待IO的进程数量                                         |                                                              |                                                              |
| **Memory(****内存****)**    | swpd                                                     | 正在使用虚拟的内存大小，单位k                                |                                                              |
| free                        | 空闲内存大小                                             |                                                              |                                                              |
| buff                        | 已用的buff大小，对块设备的读写进行缓冲                   |                                                              |                                                              |
| cache                       | 已用的cache大小，文件系统的cache                         |                                                              |                                                              |
| inact                       | 非活跃内存大小，即被标明可回收的内存，区别于free和active | 具体含义见：概念补充（当使用-a选项时显示）                   |                                                              |
| active                      | 活跃的内存大小                                           | 具体含义见：概念补充（当使用-a选项时显示）                   |                                                              |
| **Swap**                    | si                                                       | 每秒从交换区写入内存的大小（单位：kb/s）                     |                                                              |
| so                          | 每秒从内存写到交换区的大小                               |                                                              |                                                              |
| **IO**                      | bi                                                       | 每秒读取的块数（读磁盘）                                     | 现在的Linux版本块的大小为1024bytes                           |
| bo                          | 每秒写入的块数（写磁盘）                                 |                                                              |                                                              |
| **system**                  | in                                                       | 每秒中断数，包括时钟中断                                     | 这两个值越大，会看到由内核消耗的cpu时间会越多                |
| cs                          | 每秒上下文切换数                                         |                                                              |                                                              |
| **CPU****（以百分比表示）** | Us                                                       | 用户进程执行消耗cpu时间(user time)                           | us的值比较高时，说明用户进程消耗的cpu时间多，但是如果长期超过50%的使用，那么我们就该考虑优化程序算法或其他措施了 |
| Sy                          | 系统进程消耗cpu时间(system time)                         | sys的值过高时，说明系统内核消耗的cpu资源多，这个不是良性的表现，我们应该检查原因。 |                                                              |
| Id                          | 空闲时间(包括IO等待时间)                                 |                                                              |                                                              |
| wa                          | 等待IO时间                                               | Wa过高时，说明io等待比较严重，这可能是由于磁盘大量随机访问造成的，也有可能是磁盘的带宽出现瓶颈。 |                                                              |

 

**四、常见问题处理**

如果r经常大于4，且id经常少于40，表示cpu的负荷很重。

如果pi，po长期不等于0，表示内存不足。

如果disk经常不等于0，且在b中的队列大于3，表示io性能不好。

1.)如果在processes中运行的序列(process r)是连续的大于在系统中的CPU的个数表示系统现在运行比较慢,有多数的进程等待CPU。

2.)如果r的输出数大于系统中可用CPU个数的4倍的话,则系统面临着CPU短缺的问题,或者是CPU的速率过低,系统中有多数的进程在等待CPU,造成系统中进程运行过慢。

3.)如果空闲时间(cpu id)持续为0并且系统时间(cpu sy)是用户时间的两倍(cpu us)系统则面临着CPU资源的短缺。

解决办法:

当发生以上问题的时候请先调整应用程序对CPU的占用情况.使得应用程序能够更有效的使用CPU.同时可以考虑增加更多的CPU. 关于CPU的使用情况还可以结合mpstat, ps aux top prstat –a等等一些相应的命令来综合考虑关于具体的CPU的使用情况,和那些进程在占用大量的CPU时间.一般情况下，应用程序的问题会比较大一些.比如一些sql语句不合理等等都会造成这样的现象.

内存问题现象:

内存的瓶颈是由scan rate (sr)来决定的.scan rate是通过每秒的始终算法来进行页扫描的.如果scan rate(sr)连续的大于每秒200页则表示可能存在内存缺陷.同样的如果page项中的pi和po这两栏表示每秒页面的调入的页数和每秒调出的页数.如果该值经常为非零值,也有可能存在内存的瓶颈,当然,如果个别的时候不为0的话,属于正常的页面调度这个是虚拟内存的主要原理.

解决办法: 
1.调节applications & servers使得对内存和cache的使用更加有效.

2.增加系统的内存.

\3. Implement priority paging in s in pre solaris 8 versions by adding line "set priority paging=1" in /etc/system. Remove this line if upgrading from Solaris 7 to 8 & retaining old /etc/system file.

关于内存的使用情况还可以结ps aux top prstat –a等等一些相应的命令来综合考虑关于具体的内存的使用情况,和那些进程在占用大量的内存.一般情况下，如果内存的占用率比较高,但是,CPU的占用很低的时候,可以考虑是有很多的应用程序占用了内存没有释放,但是,并没有占用CPU时间,可以考虑应用程序,对于未占用CPU时间和一些后台的程序,释放内存的占用。

**五、概念补充**

**Free memory**

This is RAM that's not being used. 

**Wired memory**

Information in this memory can't be moved to the hard disk, so it must stay in RAM. The amount of Wired memory depends on the applications you are using. 

**Active memory**

This information is currently in memory, and has been recently used. 

**Inactive memory**

This information in memory is not actively being used, but was recently used.

For example, if you've been using Mail and then quit it, the RAM that Mail was using is marked as Inactive memory. This Inactive memory is available for use by another application, just like Free memory.  However, if you open Mail before its Inactive memory is used by a different application, Mail will open quicker because its Inactive memory is converted to Active memory, instead of loading Mail from the slower hard disk.