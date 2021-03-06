# basic terminal commands（liunx）

## top

top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况，类似于Windows的任务管理器

```
top
```

[![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\519608-20180714115858609-1910166416.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714115857900-2110802546.png)

**第一行，任务队列信息，同 `uptime `命令的执行结果**

> 系统时间：19:27:01
>
> 运行时间：up 54 min,
>
> 当前登录用户： 1 user
>
> 负载均衡(uptime) load average: 0.02, 0.03, 0.00
>
> average后面的三个数分别是1分钟、5分钟、15分钟的负载情况。
>
> `load average`数据是每隔5秒钟检查一次活跃的进程数，然后按特定算法计算出的数值。如果这个数除以逻辑CPU的数量，结果高于5的时候就表明系统在超负荷运转了

**第二行，Tasks — 任务（进程）**

> 总进程:159 total, 运行:1 running, 休眠:157 sleeping, 停止: 1 stopped, 僵尸进程: 0 zombie

**第三行，cpu状态信息**

> 0.0%us【user space】— 用户空间占用CPU的百分比。
>
> 0.1%sy【sysctl】— 内核空间占用CPU的百分比。
>
> 0.0%ni【】— 改变过优先级的进程占用CPU的百分比
>
> 99.9%id【idolt】— 空闲CPU百分比
>
> 0.0%wa【wait】— IO等待占用CPU的百分比
>
> 0.0%hi【Hardware IRQ】— 硬中断占用CPU的百分比
>
> 0.0%si【Software Interrupts】— 软中断占用CPU的百分比

**第四行,内存状态**

>  1012288k total,  234464k used,  777824k free,  24084k buffers【缓存的内存量】

**第五行，swap交换分区信息**

> 614396k total,    0k used,  614396k free,  72356k cached【缓冲的交换区总量】

> 备注：
>
> 可用内存=free + buffer + cached
>
> 对于内存监控，在top里我们要时刻监控第五行swap交换分区的used，如果这个数值在不断的变化，说明内核在不断进行内存和swap的数据交换，这是真正的内存不够用了。
>
> 第四行中使用中的内存总量（used）指的是现在系统内核控制的内存数，
>
> 第四行中空闲内存总量（free）是内核还未纳入其管控范围的数量。
>
> 纳入内核管理的内存不见得都在使用中，还包括过去使用过的现在可以被重复利用的内存，内核并不把这些可被重新使用的内存交还到free中去，因此在linux上free内存会越来越少，但不用为此担心。

**第六行，空行**

**第七行以下：各进程（任务）的状态监控**

> `PID` — 进程id
> `USER` — 进程所有者
> `PR` — 进程优先级
> `NI` — nice值。负值表示高优先级，正值表示低优先级
> `VIRT` — 进程使用的虚拟内存总量，单位kb。`VIRT=SWAP+RES`
> `RES` — 进程使用的、未被换出的物理内存大小，单位kb。`RES=CODE+DATA`
> `SHR `— 共享内存大小，单位kb
> `S` —进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
> `%CPU` — 上次更新到现在的`CPU`时间占用百分比
> `%MEM` — 进程使用的物理内存百分比
> `TIME+ `— 进程使用的`CPU`时间总计，单位`1/100`秒
> `COMMAND` — 进程名称（命令名/命令行）

### 其他使用技巧

**1.多U多核CPU监控**

在top基本视图中，按键盘数字“1”，可监控每个逻辑CPU的状况：逻辑上有4个，实际中只有1个，参见上下图

[![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\519608-20180714115859767-1936530688.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714115859293-1808058827.png)

**2.高亮显示当前运行进程**

敲击键盘“b”（打开/关闭加亮效果），top的视图变化如下：

[![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\519608-20180714115900579-980311917.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714115900161-134472744.png)

我们发现进程id为2419的“top”进程被加亮了，top进程就bb是视图第二行显示的唯一的运行态（runing）的那个进程，可以通过敲击“y”键关闭或打开运行态进程的加亮效果。

**3.进程字段排序**

默认进入top时，各进程是按照CPU的占用量来排序的

[![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\519608-20180714115901379-793534025.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714115900952-550788656.png)

敲击键盘“x”（打开/关闭排序列的加亮效果）

**4.通过”shift + >”或”shift + <”可以向右或左改变排序列**

按一次”shift + >”的效果图,视图现在已经按照%MEM来排序，再按一次按时间排

[![image](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\519608-20180714115902185-245678224.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714115901771-140160785.png)

**5.top交互命令**

```
h 显示帮助画面，给出一些简短的命令总结说明
k 终止一个进程。
i 忽略闲置和僵死进程。这是一个开关式命令。``
q 退出程序
r 重新安排一个进程的优先级别
S 切换到累计模式
s 改变两次刷新之间的延迟时间（单位为s``f或者F 从当前显示中添加或者删除项目
o或者O 改变显示项目的顺序
l 切换显示平均负载和启动时间信息
m 切换显示内存信息
t 切换显示进程和CPU状态信息
c 切换显示命令名称和完整命令行
M 根据驻留内存大小进行排序
P 根据CPU使用百分比大小进行排序
T 根据时间/累计时间进行排序
W 将当前设置写入~/.toprc文件中
```

### 常用命令显示

显示 完整命令

```
top -c
```

设置信息更新次数

```
top -n 2 【表示更新两次后终止更新显示】
```

设置信息更新时间

```sh
top -d 3 【表示更新周期为3秒】<br><br><br>【更多参考】
<a `id`="homepage1_HomePageDays_DaysList_ctl00_DayList_TitleUrl_0"` `class=``"postTitle2"` `href=``"https://www.cnblogs.com/ftl1012/p/9585119.html"``>Linxu学习---``top``实践<``/a``><br>
```

## grep

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
  ```
---------------------------------------------
  2 $ this $ test
3 $ Are $ awk
  This's $ a $
10 $ There $
  ```
  
  ------
  
  ### 使用正则，字符串匹配
  
  ```
输出第二列包含 "th"，并打印第二列与第四列

$ awk '$2 ~ /th/ {print $2,$4}' log.txt

  this a
  ```
  
  **~ 表示模式开始。// 中是模式。**
  
  ```
输出包含 "re" 的行

  $ awk '/re/ ' log.txt

  3 Are you like awk
10 There are orange,apple,mongo
  ```
  
  ------
  
  ### 忽略大小写
  
  ```
  $ awk 'BEGIN{IGNORECASE=1} /this/' log.txt

  2 this is a test
This's a test

  ```
  
  ------
  
  ### 模式取反
  
  ```
  $ awk '$2 !~ /th/ {print $2,$4}' log.txt

  Are like
  a
  There orange,apple,mongo

$ awk '!/th/ {print $2,$4}' log.txt

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

  Marry  2143     78       84       77      239
  Jack   2321     66       78       45      189
  Tom    2122     48       77       71      196
Mike   2537     87       97       95      279

  Bob    2415     40       57       62      159

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

| **类别**                                | **项目**                                                 | **含义**                                                     | **说明**                                                     |
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

## liunx磁盘命令

本文介绍几个[命令](https://www.linuxcool.com/)，一部分与磁盘相关，另一部分与文件相关，帮助大家分析磁盘使用情况，进而释放磁盘空间。

**命令介绍**

**ls -al命令**

这个命令大家再熟悉不过了，可以显示当前目录所有文件及目录的详细信息，可以直观看出来当前目录哪些文件占用磁盘空间更大。嗯，如果再加上 -h 选项的话会更香。

![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\1-1.jpg)

**stat命令**

stat 命令可以显示一个指定文件/目录的更详细的信息，包括：大小、块、inode、创建/访问/修改时间，等等信息。

![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\2.jpg)

**df命令**

df命令是词语disk free的缩写，用于显示当前磁盘的可用空间。常用的几个选项为：

df -h ：以用户友好的方式显示磁盘可用空间。

df -a ：显示完整磁盘使用情况，包括那些可用空间为 0 的磁盘区域。

![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\3.jpg)

df -T ：按文件系统类型来显示磁盘使用情况，比如：xfs、ext2、ext3、btrfs，等等。

df -i ：显示已使用及空闲 inodes。

![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\4.jpg)

除了使用命令行之外，你还可以使用一个叫作 disks 的图形工具。使用这个工具就可以很直观看到磁盘的使用情况了。
![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\5.jpg)

**du命令**

du 是英语词组 disk useage 的缩写，它显示了磁盘里文件、目录等元素的使用情况。常用的用法有以下几种：

du -h ：以易于人类阅读的格式显示所有目录及子目录的使用情况;

du -a ：显示当前目录所有文件的磁盘使用情况;

du -s ：仅显示总计，只列出最后加总的值。(用于显示目录的大小)

**fdisk -l命令**

fdisk 是一个创建和维护分区表的程序，它兼容DOS类型的分区表、BSD或者SUN类型的磁盘列表。而 fdisk -l 命令可以显示磁盘分区的各种信息。
![Linux磁盘命令Linux磁盘命令](E:\oldF\learningDocument\git-workspace\PANDA-Walker\picture\6.jpg)

有折腾过分区表的小伙伴们应该对这个步骤很熟悉，接下来就有可以进行分区创建、删除、修改等等相关的操作。但在此，我们只是使用 -l 选项看一眼当前分区的情况，不进行进一步的操作。

友情提示一下，如果不懂分区操作的话，千万不要随意使用 fdisk 的其它选项，有可能会带来毁灭性的伤害，到时想要跑路可能就来不及了~

**小结**

本文介绍了几个文件尺寸及磁盘空间的相关命令，通过这几个命令，可以了解磁盘的一些相关信息，在清理磁盘的时候可以提供一些参考。合理使用这些命令，可以给你的磁盘进行大瘦身哦~

> 原文来自：https://os.51cto.com/art/202005/616330.htm
>
> 本文地址：https://www.linuxprobe.com/linux-disk-command.html编辑：冯瑞涛，审核员：逄增宝
>
> Linux命令大全：https://www.linuxcool.com/