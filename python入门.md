# python入门

## The World of Variables and Operators

### Basic Operators

除了给变量赋初始值之外，我们还可以对变量执行通常的数学运算。Python中的基本操作符包括+、-、*、/、//、%和**，它们分别代表加、减、乘、除、底除法、模数和指数。

### More Assignment Operators

除了=符号之外，Python(和大多数编程语言)中还有更多的赋值操作符。这包括像+=、-=和*=这样的操作符。

假设我们有变量x，初始值为10。如果我们想让x增加2，可以这样写x = x + 2

程序首先计算右边(x + 2)的表达式，然后把结果赋给左边。所以最后这个式子变成x <- 12。

我们也可以用x += 2来表达相同的意思，而不用x = x + 2。+=符号实际上是将赋值符号和加法运算符组合在一起的一种简写。因此，x += 2仅仅意味着x = x + 2。

类似地，如果我们想做减法，可以写成x = x - 2或者x -= 2。上述7个操作符的工作原理是一样的。

##  Data Types in Python

在这一章中，我们将首先看看Python中的一些基本数据类型，特别是整数、浮点数和字符串。接下来，我们将探讨类型强制转换的概念。最后，我们将讨论Python中更高级的三种数据类型:list、tuple和dictionary。

### Integers

整数是没有小数部分的数字，例如-5、-4、-3、0、5、7等。

要在Python中声明一个整数，只需写入variableName = initial value

Example:

userAge = 20, mobileNumber = 12398724

### Float

浮点数指的是包含小数部分的数字，例如1.234、-0.023、12.01

要在Python中声明浮点数，我们写入variableName = initial value

Example:

userHeight = 1.82, userWeight = 67.2

### String

字符串指的是文本。

声明一个字符串，你可以使用variableName = ' initial value '(单引号)或variableName = ' initial value '(双引号)

Example:

userName = ‘Peter’, userSpouseName = “Janet”, userAge = ‘30’

在上一个例子中，因为我们写了userAge = ' 30 '，所以userAge是一个字符串。相反，如果你写userAge = 30(不带引号)，userAge是一个整数。

我们可以使用连接符号(+)组合多个子字符串。例如，" Peter " + " Lee "等价于字符串" PeterLee "。

#### 内置的字符串函数

Python包含了许多用于操作字符串的内置函数。函数就是执行特定任务的可重用代码块。我们将在第7章中更深入地讨论函数。

Python中可用的一个函数示例是用于字符串的upper()方法。你可以用它来大写一个字符串中的所有字母。例如，' Peter ' .upper()将给我们字符串" Peter "。关于如何使用Python内置字符串方法的更多示例和样例代码，可以参考附录A。

#### 使用%操作符格式化字符串

字符串也可以使用%操作符进行格式化。这使您能够更好地控制字符串的显示和存储方式。使用%操作符的语法是"要格式化的字符串" %(值或变量要插入字符串，用逗号分隔)

该语法由三部分组成。首先，我们编写要用引号格式化的字符串。接下来，我们写下%符号。最后，我们有一对圆括号()，在其中写入要插入到字符串中的值或变量。这个内嵌值的圆括号实际上被称为元组，这是一种我们将在后面章节介绍的数据类型。

在IDLE中输入下面的代码并运行它

brand = ‘Apple’

exchangeRate = 1.235235245

message = ‘The price of this %s laptop is %d USD and the exchange rate is %4.2f USD to 1 EUR’ %(brand, 1299, exchangeRate)

print (message)

在上面的例子中，字符串' the price of this %s laptop is %d USD and the exchange rate is %4.2f USD to 1 EUR '是我们想要格式化的字符串。我们使用%s、%d和%4.2f格式化器作为字符串中的占位符。

这些占位符将分别替换为变量brand、值1299和变量exchangeRate，如圆括号中所示。如果我们运行代码，我们将得到下面的输出

The price of this Apple laptop is 1299 USD and the exchange rate is 1.24 USD to 1 EUR

%s格式化程序用于表示一个字符串(在本例中为“Apple”)，而%d格式化程序则表示一个整数(1299)。如果想在整数前添加空格，可以在%和d之间添加一个数字，以表示所需的字符串长度。例如，“%5d”%(123)将给我们“123”(前面有2个空格，总长度为5)。

格式化程序用于格式化浮点数(带有小数点的数字)。这里我们将它格式化为%4.2f，其中4表示总长度，2表示小数点后两位。如果我们想在数字之前添加空格，我们可以格式化为%7.2f，这将得到“1.24”(有两个小数点后，前面有3个空格，总长度为7)。

#### Formatting Strings using the format() method

除了使用%操作符来格式化字符串之外，Python还提供了format()方法来格式化字符串。语法“需要格式化的字符串”。格式(要插入到字符串中的值或变量，用逗号分隔)

当我们使用格式方法时，我们不使用%s， %f或%d作为占位符。相反，我们使用花括号，像这样:

message = ‘The price of this **{0:s}** laptop is **{1:d}**USD and the exchange rate is **{2:4.2f}** USD to 1 EUR’.format(‘Apple’, 1299, 1.235235245)

在花括号内，我们首先写下要使用的参数的位置，后面跟着冒号。

在冒号之后，我们编写格式化程序。花括号内不应该有任何空格。

当写入format(' Apple '， 1299, 1.235235245)时，我们向format()方法传递了三个参数。参数是方法执行任务所需的数据。参数是“Apple”，1299和1.235235245。

参数“Apple”的位置为0，1299的位置是1和1.235235245的位置为2

位置总是从零开始

当写入{0:s}时，我们要求解释器将{0:s}替换为位置为0的形参，并且它是一个字符串(因为格式化器是' s ')。

当写入{1:d}时，我们引用位置1中的参数，它是一个整型(格式化器是d)。

当写入{2:4.2f}时，我们引用位置2的参数，这是一个浮点数，我们希望它被格式化为2个小数点后，总长度为4 (formatter是4.2f)。

如果我们打印消息，我们将得到

这台苹果笔记本电脑的价格是1299美元，汇率是1.24美元对1欧元

注意:如果你不想格式化字符串，你可以简单的写

message = ‘The price of this {} laptop is {} USD and the exchange rate is {} USD to 1 EUR’.format(‘Apple’, 1299, 1.235235245)

在这里，我们不必指定参数的位置。解释器将根据所提供参数的顺序替换花括号。我们会得到

The price of this Apple laptop is 1299 USD and the exchange rate is 1.235235245 USD to 1 EUR

format()方法可能会让初学者感到困惑。事实上，字符串格式化可能比我们在这里讨论的更奇特，但我们所讨论的对于大多数目的来说已经足够了。要更好地理解format()方法，请尝试下面的程序。

```python
message1 = ‘{0} is easier than {1}’.format(‘Python’, ‘Java’)

message2 = ‘{1} is easier than {0}’.format(‘Python’, ‘Java’)

message3 = ‘{:10.2f} and {:d}’.format(1.234234234, 12)

message4 = ‘{}’.format(1.234234234)

print (message1)

\#You’ll get ‘Python is easier than Java’

print (message2)

\#You’ll get ‘Java is easier than Python’

print (message3)

\#You’ll get ‘      1.23 and 12’

\#You do not need to indicate the positions of the parameters.

print (message4)

\#You’ll get 1.234234234. No formatting is done
```

您可以使用Python Shell来试验format()方法。试着输入不同的字符串，看看你得到什么。

### Type Casting In Python

在我们的程序中，有时需要从一种数据类型转换为另一种数据类型，例如从整数转换为字符串。这就是所谓的铸型。

Python中有三个内置函数允许我们进行类型转换。这些是int()、float()和str()函数。

Python中的int()函数接受浮点数或适当的字符串，并将其转换为整数。要将浮点数更改为整数，可以输入int(5.712987)。结果是5(去掉小数点后的任何数字)要把一个字符串变成整数，可以输入int(“4”)，得到的结果是4。但是，不能键入int (" Hello ")或int(" 4.22321 ")。在这两种情况下，我们都会得到一个错误。

函数的作用是:接受一个整数或一个合适的字符串并将其更改为浮点数。例如，如果我们输入float(2)或float(" 2 ")，我们将得到2.0。如果输入float(" 2.09109 ")，我们将得到2.09109，这是一个浮点数，而不是字符串，因为引号被删除了。

另一方面，str()函数将整数或浮点数转换为字符串。例如，如果我们输入str(2.1)，我们将得到“2.1”。

既然我们已经讨论了Python中的三种基本数据类型及其强制转换，现在让我们转向更高级的数据类型。

### List

List指的是通常相关的数据集合。我们可以将这些数据存储为列表，而不是将它们存储为单独的变量。例如，假设我们的程序需要存储年龄为5岁的用户。与其将它们存储为user1Age、user2Age、user3Age、user4Age和user5Age，不如将它们存储为一个列表更有意义。

要声明一个列表，可以写listName = [initial values]。注意，在声明列表时使用方括号[]。多个值由逗号分隔。

Example:

userAge = [21, 22, 23, 24, 25]

我们也可以声明一个列表而不给它赋任何初始值。我们简单地写listName =[]。我们现在拥有的是一个没有条目的空列表。我们必须使用下面提到的append()方法向列表添加项。

列表中的单个值可以通过它们的索引访问，而且索引总是从0开始，而不是1。这在几乎所有编程语言(如C和Java)中都是常见的实践。因此第一个值的索引值为0，下一个值的索引值为1，依此类推。例如，userAge[0] = 21, userAge[1] = 22

或者，您可以从后面访问列表的值。列表中最后一项的索引值为-1，倒数第二项的索引值为-2，以此类推。因此，userAge[-1] = 25, userAge[-2] = 24。

您可以将列表或列表的一部分分配给变量。如果你写userAge2 = userAge，变量userAge2变成[21,22,23,24,25]。

如果写入userAge3 = userAge[2:4]，则将索引为2到索引4-1的项从列表userAge分配给列表userAge3。也就是说，userAge3 =[23,24]。

记号2:4被称为切片。在Python中，无论何时使用切片表示法，开始索引处的项总是被包含在内，但结束索引处的项总是被排除在外。因此2:4表示从索引2到索引4-1的项(即索引3)，这就是为什么userAge3 =[23,24]而不是[23,24,25]的原因。

切片表示法包括第三个数字，称为步进数。如果写入userAge4 = userAge[1:5:2]，我们将得到一个子列表，包含从索引1到索引5-1的每一秒钟的数字，因为步进为2。因此，userAge4 =[22,24]。

此外，切片表示法还有一些有用的默认值。第一个数字的默认值是零，第二个数字的默认值是被切片的列表的大小。例如，userAge[:4]给你索引0到索引4-1的值，而userAge[1:]给你索引1到索引5-1的值(因为userAge的大小是5，即userAge有5个项目)。

要修改列表中的项目，我们写入listName[要修改项目的索引]= new value。例如，如果您想要修改第二个条目，则写入userAge[1] = 5。您的列表成为userAge = [21, 5, 23, 24, 25]

要添加项，可以使用append()函数。例如，如果你写userAge.append(99)，你

将值99添加到列表的末尾。你的列表现在userAge = [21, 5, 23, 24, 25, 99]

要删除项目，你可以写del listName[要删除项目的索引]。例如，如果你写del userAge[2]，你的列表现在变成userAge =[21, 5, 24, 25, 99](第三项被删除)

要充分了解列表的工作原理，请尝试运行以下程序。

```python
#declaring the list, list elements can be of different data types

myList = [1, 2, 3, 4, 5, “Hello”]      

 

#print the entire list.

print(myList)                              

#You’ll get [1, 2, 3, 4, 5, “Hello”]

 

#print the third item (recall: Index starts from zero).

print(myList[2])                        

#You’ll get 3

 

#print the last item.

print(myList[-1])      

#You’ll get “Hello”
#assign myList (from index 1 to 4) to myList2 and print myList2

myList2 = myList[1:5]

print (myList2)

#You’ll get [2, 3, 4, 5]

#modify the second item in myList and print the updated list

myList[1] = 20

print(myList)                              

#You’ll get [1, 20, 3, 4, 5, 'Hello']
List
 

List refers to a collection of data which are normally related. Instead of storing these data as separate variables, we can store them as a list. For instance, suppose our program needs to store the age of 5 users. Instead of storing them as user1Age, user2Age, user3Age, user4Age and user5Age, it makes more sense to store them as a list.

 

To declare a list, you write listName = [initial values]. Note that we use square brackets [ ] when declaring a list. Multiple values are separated by a comma.

 

Example:

userAge = [21, 22, 23, 24, 25]

 

We can also declare a list without assigning any initial values to it. We simply write listName = []. What we have now is an empty list with no items in it. We have to use the append() method mentioned below to add items to the list.

 

Individual values in the list are accessible by their indexes, and indexes always start from ZERO, not 1. This is a common practice in almost all programming languages, such as C and Java. Hence the first value has an index of 0, the next has an index of 1 and so forth. For instance, userAge[0] = 21, userAge[1] = 22

 

Alternatively, you can access the values of a list from the back. The last item in the list has an index of -1, the second last has an index of -2 and so forth. Hence, userAge[-1] = 25, userAge[-2] = 24.

 

You can assign a list, or part of it, to a variable. If you write userAge2 = userAge, the variable userAge2 becomes [21, 22, 23, 24, 25].

 

If you write userAge3 = userAge[2:4], you are assigning items with index 2 to index 4-1 from the list userAge to the list userAge3. In other words, userAge3 = [23, 24].

 

The notation 2:4 is known as a slice. Whenever we use the slice notation in Python, the item at the start index is always included, but the item at the end is always excluded. Hence the notation 2:4 refers to items from index 2 to index 4-1 (i.e. index 3), which is why userAge3 = [23, 24] and not [23, 24, 25].

 

The slice notation includes a third number known as the stepper. If we write userAge4 = userAge[1:5:2], we will get a sub list consisting of every second number from index 1 to index 5-1 because the stepper is 2. Hence, userAge4 = [22, 24].

 

In addition, slice notations have useful defaults. The default for the first number is zero, and the default for the second number is size of the list being sliced. For instance, userAge[ :4] gives you values from index 0 to index 4-1 while userAge[1: ] gives you values from index 1 to index 5-1 (since the size of userAge is 5, i.e. userAge has 5 items).


To modify items in a list, we write listName[index of item to be modified] = new value. For instance, if you want to modify the second item, you write userAge[1] = 5. Your list becomes userAge = [21, 5, 23, 24, 25]

To add items, you use the append() function. For instance, if you write userAge.append(99), you add the value 99 to the end of the list. Your list is now userAge = [21, 5, 23, 24, 25, 99]

 

To remove items, you write del listName[index of item to be deleted]. For instance, if you write del userAge[2], your list now becomes userAge = [21, 5, 24, 25, 99] (the third item is deleted)

 

To fully appreciate the workings of a list, try running the following program.

 

#declaring the list, list elements can be of different data types

myList = [1, 2, 3, 4, 5, “Hello”]      

 

#print the entire list.

print(myList)                              

#You’ll get [1, 2, 3, 4, 5, “Hello”]

 

#print the third item (recall: Index starts from zero).

print(myList[2])                        

#You’ll get 3

 

#print the last item.

print(myList[-1])      

#You’ll get “Hello”

 

#assign myList (from index 1 to 4) to myList2 and print myList2

myList2 = myList[1:5]

print (myList2)

#You’ll get [2, 3, 4, 5]

 

#modify the second item in myList and print the updated list

myList[1] = 20

print(myList)                              

#You’ll get [1, 20, 3, 4, 5, 'Hello']

            

#append a new item to myList and print the updated list

myList.append(“How are you”)            

print(myList)                              

#You’ll get [1, 20, 3, 4, 5, 'Hello', 'How are you']

 

#remove the sixth item from myList and print the updated list

del myList[5]                              

print(myList)            

#You’ll get [1, 20, 3, 4, 5, 'How are you']
```

你还可以用列表做更多的事情。有关使用列表的示例代码和更多示例，请参阅附录B。

### Tuple

元组就像列表，但是您不能修改它们的值。初始值将保留到程序的其余部分。元组的一个有用示例是，当程序需要存储一年中的月份名称时。

要声明一个元组，需要写入tupleName =(初始值)。注意，在声明元组时使用了圆括号()。多个值由逗号分隔。

Example:

monthsOfYear = (“Jan”, “Feb”, “Mar”, “Apr”, “May”, “Jun”, “Jul”, “Aug”, “Sep”, “Oct”, “Nov”, “Dec”)

您可以使用tuple的索引来访问单个值，就像使用list一样。

因此monthsOfYear[0] = " Jan "， monthsOfYear[-1] = " Dec "。

有关使用元组的更多示例，请参阅附录C。

### Dictionary

字典是相关数据对的集合。例如，如果想要存储用户名和年龄为5岁的用户，可以将他们存储在字典中。

要声明一个字典，需要写入dictionaryName = {dictionary key: data}，并且字典键必须是唯一的(在一个字典中)。也就是说，你不能声明像myDictionary = {" Peter ":38， " John ":51， " Peter ":13}这样的字典。

这是因为“Peter”作为字典键使用了两次。注意，在声明dictionary时使用大括号{}。多个对由逗号分隔。

Example:

userNameAndAge = {“Peter”:38, “John”:51, “Alex”:13, “Alvin”:“Not Available”}

您还可以使用dict()方法声明dictionary。要声明上面的userNameAndAge字典，需要编写下面的代码

userNameAndAge = dict(Peter = 38, John = 51, Alex = 13, Alvin = “Not Available”)

使用此方法声明字典时，使用圆方括号()而不是花括号{}，并且不为字典键加上引号

为了访问字典中的各个条目，我们使用字典键，它是{dictionary key: data}对中的第一个值。例如，to get John的年龄，你写上userNameAndAge[" John "]你会得到51的值。

要修改字典中的项，我们写入dictionaryName[要修改项的字典键]= new data。例如，要修改“John”:51对，我们写入userNameAndAge[“John”]= 21。我们的字典现在变成userNameAndAge = {" Peter ":38， " John ":21， " Alex ":13， " Alvin ": " Not Available "}。

我们也可以声明dictionary而不给它赋任何初始值。我们只需写入dictionaryName ={}。我们现在拥有的是一个没有条目的空字典。

要向字典中添加条目，我们写入dictionaryName[dictionary key] = data。例如，如果要将“Joe”:40添加到字典中，则写入userNameAndAge[“Joe”]= 40。我们的字典现在变成userNameAndAge = {" Peter ":38， " John ":21， " Alex ":13， " Alvin ": " Not Available "， " Joe ":40}

要从字典中删除条目，我们写入del dictionaryName[dictionary key]。例如，要删除“Alex”:13对，我们写入del userNameAndAge[“Alex”]。我们的字典现在变成userNameAndAge = {" Peter ":38， " John ":21， " Alvin ": " Not Available "， " Joe ":40}

运行以下程序查看所有这些操作。

```python
#declaring the dictionary, dictionary keys and data can be of different data types
myDict = {“One”:1.35, 2.5:”Two Point Five”, 3:”+”, 7.9:2}

 

#print the entire dictionary

print(myDict)                              

#You’ll get {2.5: 'Two Point Five', 3: '+', 'One': 1.35, 7.9: 2}

#Note that items in a dictionary are not stored in the same order as the way you declare them.

 

#print the item with key = “One”.

print(myDict[“One”])                        

#You’ll get 1.35

 

#print the item with key = 7.9.

print(myDict[7.9])      

#You’ll get 2

 

#modify the item with key = 2.5 and print the updated dictionary

myDict[2.5] = “Two and a Half”

print(myDict)                              

#You’ll get {2.5: 'Two and a Half', 3: '+', 'One': 1.35, 7.9: 2}

            

#add a new item and print the updated dictionary

myDict[“New item”] = “I’m new”            

print(myDict)                              

#You’ll get {'New item': 'I’m new', 2.5: 'Two and a Half', 3: '+', 'One': 1.35, 7.9: 2}

 

#remove the item with key = “One” and print the updated dictionary

del myDict[“One”]                              

print(myDict)      

#You’ll get {'New item': 'I’m new', 2.5: 'Two and a Half', 3: '+', 7.9: 2}
```

有关使用字典的更多示例和示例代码，请参阅附录D。

##  Making Your Program Interactive

既然我们已经讨论了变量的基础知识，让我们编写一个程序来使用它们。我们将重温我们在第二章中编写的“Hello World”程序，但这一次我们将使它具有交互性。除了向世界问好，我们还想让世界知道我们的名字和年龄。为了做到这一点，我们的程序需要能够提示我们获取信息并将它们显示在屏幕上。

两个内置函数可以为我们做这些:input()和print()。

现在，让我们在IDLE中输入下面的程序。保存并运行它。

myName = input("Please enter your name: ")

myAge = input("What about your age: ")

print ("Hello World, my name is", myName, "and I am", myAge, "years old.")

这个程序应该会提示你输入你的名字。

请输入您的姓名:

假设你让詹姆斯进来了。现在按回车键，它会提示你的年龄。

你的年龄:

假设你输入了20。现在再次按Enter键。你应该会得到以下语句:

大家好，我叫詹姆斯，今年20岁。

### Input()

在上面的例子中，我们两次使用input()函数来获取用户的姓名和年龄。

```python
myName = input("Please enter your name: ")
```



字符串“Please enter your name:”将显示在屏幕上，向用户提供指示。用户输入相关信息后，该信息将作为字符串存储在变量myName中。下一个输入语句提示用户输入年龄，并将信息作为字符串存储在变量myAge中。

input()函数在python2和python3中略有不同。在python2中，如果你想接受用户输入的字符串，你必须使用raw_input()函数代替。

### Print()

函数的作用是:向用户显示信息。它接受零个或多个表达式作为参数，用逗号分隔。

在下面的语句中，我们向print()函数传递了5个参数。你能认出他们吗?

```python
print ("Hello World, my name is", myName, "and I am", myAge, "years old.")
```

第一个是字符串" Hello World, my name is "

下一个是前面使用输入函数声明的变量myName。

接下来是字符串“and I am”，接着是变量myAge，最后是字符串“years old.”。

注意，在引用变量myName和myAge时，我们不使用引号。如果使用引号，就会得到输出

你好，世界，我的名字是我的名字，我的年龄。

相反，这显然不是我们想要的。

打印带有变量的语句的另一种方法是使用第4章学到的%格式化器。为了实现与上面第一个print语句相同的输出，我们可以这样写

```python
print ("Hello World, my name is %s and I am %s years old." %(myName, myAge))
```

最后，为了使用format()方法打印相同的语句，我们写入

```python
print (“Hello World, my name is {} and I am {} years old”.format(myName, myAge))
```

print()函数是另一个在python2和python3中不同的函数。在python2中，你可以把它去掉括号，就像这样:

print "Hello World, my name is " + myName + " and I am " + myAge + " years old."

### Triple Quotes

如果你需要显示一个较长的信息，你可以使用三重引号(" '或" " ")来跨越多行信息。例如,

```python
print ('''Hello World.
My name is James and
I am 20 years old.''')
```

这有助于增加你的信息的可读性。

### Escape Characters

有时我们可能需要打印一些特殊的“不可打印”字符，如制表符或换行符。在这种情况下，您需要使用\(反斜杠)字符来转义具有不同含义的字符。

例如，要打印一个制表符，我们在字母t前面输入反斜杠字符，像这样:\t。如果没有\字符，字母t将被打印出来。用它，一个标签被打印出来。因此，如果你输入print (' Hello\tWorld ')，你会得到Hello World

反斜杠字符的其他常用用法如下所示。

显示命令，下面的行显示输出。

```python
\n (Prints a newline)

>>> print (‘Hello\nWorld’)

Hello
World

 

\\ (Prints the backslash character itself)

>>> print (‘\\’)

\

 

\” (Prints double quote, so that the double quote does not signal the end of the string)

>>> print (“I am 5'9\" tall”)

I am 5’9” tall
           
\’ (Print single quote, so that the single quote does not signal the end of the string)
>>> print (‘I am 5\’9” tall’)
I am 5’9” tall
```

如果不希望字符前面的\字符被解释为特殊字符，可以通过在第一个引号前添加r来使用原始字符串。例如，如果你不希望\t被解释为一个制表符，你应该输入print (r ' hello \tWorld ')。您将得到Hello\tWorld作为输出。

## Chapter 6: Making Choices and Decisions

恭喜你，你已经进入了最有趣的一章。我希望到目前为止你们喜欢这门课。在本章中，我们将看看如何使你的程序更智能，能够做出选择和决策。具体地说，我们将学习if语句，for循环和while循环。这些被称为控制流工具;他们控制程序的运行。此外，我们还将研究try, except语句，该语句确定程序在发生错误时应该做什么。

然而，在我们进入这些控制流工具之前，我们必须首先了解条件语句。

### Condition Statements

所有的控制流工具都包含对条件语句的评估。根据条件是否满足，程序将以不同的方式进行。

最常见的条件语句是比较语句。如果我们想比较两个变量是否相同，我们使用==符号(double =)。例如，如果你写x == y，你正在请求程序检查x的值是否等于y的值，如果它们相等，满足条件，语句的计算结果将为真。否则，语句的计算结果将为False。

其他比较符号包括!=(不等于)、<(小于)、>(大于)、<=(小于等于)和>=(大于等于)。下面的列表显示了如何使用这些符号，并给出了将计算为True的语句示例。

```python
Not equals:
5 != 2
Greater than:

5>2

Smaller than:

2<5
 

Greater than or equals to:

5>=2

5>=5

Smaller than or equals to:

2 <= 5
2 <= 2
```

我们还有三个逻辑运算符，和，或，如果我们想组合多个条件，它们并不有用。

如果满足所有条件，and操作符返回True。否则返回False。例如，语句5==5和2>1将返回True，因为两个条件都为真。如果满足至少一个条件，or操作符返回True。否则返回False。语句5>2或7 > 10或3 == 2将返回True，因为第一个条件5>2为True。

如果not关键字后的条件为假，则not操作符返回True。否则返回False。语句not 2>5将返回True，因为2不大于5。

### If Statement

if语句是最常用的控制流语句之一。它允许程序评估是否满足某个条件，并根据评估的结果执行适当的操作。if语句的结构如下:

```py
if condition 1 is met:
do A
elif condition 2 is met:
do B
elif condition 3 is met:
do C
elif condition 4 is met:
do D
else:
do E
```

elif表示“else if”，您可以有任意多的elif语句。

如果您以前用C或Java等其他语言编写代码，您可能会惊讶地发现，在Python中If、elif和else关键字后面不需要括号()。此外，Python没有使用括号来定义if语句的开始和结束。相反，Python使用缩进。如果条件求值为true，则任何缩进的代码块都将被视为执行的代码块。

要完全理解if语句是如何工作的，启动IDLE并输入以下代码。

```python
userInput = input('Enter 1 or 2: ')	

if userInput == "1":
    print ("Hello World")

	print (“How are you?”)

elif userInput == "2":

	print ("Python Rocks!")

	print (“I love Python”)
else:
	print ("You did not enter a valid number")
```

程序首先使用输入函数提示用户输入。结果以字符串形式存储在userInput变量中。

接下来的语句if userInput == "1":将userInput变量与字符串"1"进行比较。如果userInput中存储的值是“1”，程序将执行所有缩进的语句，直到缩进结束。在这个例子中，它将打印“Hello World”，后面跟着“How are you?”

或者，如果userInput中存储的值是“2”，程序将打印“Python Rocks”，然后是“我爱Python”。

对于所有其他值，程序将打印“您没有输入一个有效的数字”。

运行程序三次，每次分别输入1、2和3。您将得到以下输出:

```py
Enter 1 or 2: 1
Hello World
How are you?
Enter 1 or 2: 2
Python Rocks!
I love Python

Enter 1 or 2: 3
您没有输入有效的数字
```

### Inline If

内联if语句是if语句的一种更简单的形式，如果您只需要执行一个简单的任务，则内联if语句更加方便。的语法是:

do Task A if condition is true else do Task B

列如

num1 = 12 if myInt==10 else 13

如果myInt等于10，这个语句将12赋值给num1(任务A)。否则它给num1分配13(任务B)。

Another example is

print (“This is task A” if myInt == 10 else “This is task B”)

如果myInt = 10，该语句打印“This is task A”(task A)。否则输出“This is task B”(task B)。

### For Loop

接下来，让我们看一下for循环。for循环反复执行代码块，直到for语句中的条件不再有效为止。

*Looping through an iterable*

在Python中，`iterable`指的是任何可以循环的对象，比如字符串、列表或元组。遍历iterable的语法如下:

for a in iterable:

print (a)

Example:

```python
pets = ['cats', 'dogs', 'rabbits', 'hamsters']
for myPets in pets:
	print (myPets)
```

在上面的程序中，我们首先声明宠物列表，并给它的成员'猫'，'狗'，'兔子'和'仓鼠'。接下来，pets中的myPets语句:循环遍历pets列表，并将列表中的每个成员赋值给变量myPets。

当程序第一次运行for循环时，它将‘cats’赋值给变量myPets。语句打印(myPets)，然后打印值' cats '。当程序第二次循环for语句时，它将值' dogs '赋值给myPets，并打印值' dogs '。程序继续循环遍历列表，直到到达列表的末尾。

如果你运行这个程序，你会得到

>cats
>
>dogs
>
>rabbits
>
>hamsters

我们还可以显示列表中成员的索引。为此，我们使用enumerate()函数。

for index, myPets in enumerate(pets):

print (index, myPets)

这将会给我们输出

>0 cats
>
>1 dogs
>
>2 rabbits
>
>3 hamster
>
>

下一个示例演示如何遍历字符串。

```python
message = ‘Hello’
for i in message:
	print (i)
The output is
H
e
l
l
o
```

*Looping through a* *sequence of numbers* 循环通过一个的数字序列

要遍历一串数字，内置的range()函数非常有用。函数的作用是:生成一个数字列表，并具有语法范围(start, end, step)。

如果没有给定start，则生成的数字将从0开始。

注意:这里要记住的一个有用的技巧是，在Python(和大多数编程语言)中，除非另有说明，我们总是从零开始。

例如，list和tuple的索引从0开始。

当对字符串使用format()方法时，参数的位置从0开始。当使用range()函数时，如果没有给定start，则生成的数字从0开始。

如果没有给出步长，则会生成一列连续的数字(即步长= 1)。必须提供结束值。然而，关于range()函数的一个奇怪的事情是，给定的结束值从来不是生成列表的一部分。

>For instance,
>
>range(5) will generate the list [0, 1, 2, 3, 4]
>
>range(3, 10) will generate [3, 4, 5, 6, 7, 8, 9]
>
>range(4, 10, 2) will generate [4, 6, 8]

要查看range()函数在for语句中的工作原理，请运行以下代码:

```python
for i in range(5):
	print (i)
You should get
0
1
2
3
4
```

### While Loop

我们要看的下一个控制流语句是while循环。顾名思义，while循环在循环内重复执行指令，而某个条件仍然有效。while语句的结构如下:

while condition is true:

do A

大多数情况下，当使用while循环时，首先需要声明一个变量作为循环计数器。我们称这个变量为counter。while语句中的条件将计算counter的值，以确定它是否小于(或大于)某个值。如果是，则执行循环。让我们看一个示例程序。

counter = 5

while counter > 0:

​	print (“Counter = “, counter)

​	counter = counter - 1

如果运行该程序，将得到以下输出

>Counter = 5
>
>Counter = 4
>
>Counter = 3
>
>Counter = 2
>
>Counter = 1

乍一看，while语句似乎具有最简单的语法，也应该是最容易使用的。然而，在使用while循环时必须小心，因为有无限循环的危险。注意，在上面的程序中，我们有一行counter = counter - 1?这条线至关重要。它将counter的值减少1，并将这个新值赋回给counter，覆盖原始值。

我们需要将counter的值减少1，以便当计数器> 0时，循环条件最终将计算为False。如果我们忘记这样做，这个循环就会无休止地运行，导致一个无限循环。如果你想亲身体验一下，只需删除行counter = counter - 1，然后再试着运行程序。程序将继续打印counter = 5，直到您以某种方式终止程序。这不是一个愉快的体验，特别是如果你有一个大程序，你不知道哪个代码段导致了无限循环。

### Break

在使用循环时，有时您可能希望在满足某个条件时退出整个循环。为此，我们使用break关键字。运行以下程序，看看它是如何工作的。

>j = 0
>
>for i in range(5):
>
>​	j = j + 2
>
>​	print (‘i = ’, i, ‘, j = ’, j)
>
>​	if j == 6:
>
>   		break
>
>You should get the following output.
>
>i = 0 , j = 2
>
>i = 1 , j = 4
>
>i = 2 , j = 6

如果没有break关键字，程序应该从i = 0循环到i = 4，因为我们使用了函数range(5)。但是使用break关键字时，程序在i = 2时提前结束。这是因为当i = 2时，j达到6的值，break关键字导致循环结束。

在上面的例子中，注意我们在for循环中使用了if语句。对我们来说，在编程中“混搭”各种控制工具是很常见的，比如在if语句中使用while循环，或者在while循环中使用for循环。这就是所谓的嵌套控制语句。

### Continue

循环的另一个有用的关键字是continue。当我们使用continue时，关键字后的其余循环将跳过该迭代。举个例子就更清楚了。

```python
j = 0
for i in range(5):
	j = j + 2
	print (‘\ni = ’, i, ‘, j = ’, j)
	if j == 6:
      continue
	print (‘I will be skipped over if j=6’)
```

>You will get the following output:
>
>i = 0 , j = 2
>
>I will be skipped over if j=6
>
>i = 1 , j = 4
>
>I will be skipped over if j=6
>
>i = 2 , j = 6
>
>i = 3 , j = 8
>
>I will be skipped over if j=6
>
>i = 4 , j = 10
>
>I will be skipped over if j=6
>
>

当j = 6时，continue关键字后面的行不打印。除此之外，一切都正常运行。

### Try, Except

最后一个控制语句是try, except语句。这个语句控制错误发生时程序如何运行。语法如下:

```python
try:
do something
except:
do something else when an error occurs
For instance, try running the program below
try:
answer =12/0
print (answer)
except:
print (“An error occurred”)
```

当你运行这个程序时，你会看到“发生错误”的消息。这是因为当程序试图在try块中执行语句answer =12/0时，会发生错误，因为您不能将数字除以0。try块的其余部分将被忽略，而except块中的语句将被执行。

如果希望根据错误向用户显示更具体的错误消息，可以在except关键字后指定错误类型。尝试运行下面的程序。

```python
try:

userInput1 = int(input("Please enter a number: "))

userInput2 = int(input("Please enter another number: "))

answer =userInput1/userInput2

print ("The answer is ", answer)

myFile = open("missing.txt", 'r')

except ValueError:

print ("Error: You did not enter a number")

except ZeroDivisionError:

print ("Error: Cannot divide by zero")

except Exception as e:

print ("Unknown error: ", e)
```

下面的列表显示了不同用户输入的各种输出。>>>表示用户输入，=>表示输出。

\>>> Please enter a number: m

=> Error: You did not enter a number

 原因:用户输入了一个不能转换成整数的字符串。这是一个ValueError。因此，将显示except ValueError块中的语句。

\>>> Please enter a number: 12

\>>> Please enter another number: 0

=> Error: Cannot divide by zero

原因:userInput2 = 0。因为我们不能用零除一个数，所以这是一个零除法错误。将显示except ZeroDivisionError块中的语句。

\>>> Please enter a number: 12

\>>> Please enter another number: 3

=> The answer is 4.0

=> Unknown error: [Errno 2] No such file or directory: 'missing.txt'

原因:用户输入可接受的值，行打印(“答案是”，答案)正确执行。但是，下一行出现错误，因为找不到missing.txt。因为这不是ValueError或ZeroDivisionError，所以将执行最后一个except块。

ValueError和ZeroDivisionError是Python中许多预定义错误类型中的两种。当内置操作或函数接收到具有正确类型但值不合适的参数时，将引发ValueError。当程序试图除以零时，会引发ZeroDivisionError。Python中其他常见的错误包括

IOError:

当I/O操作(例如内置的open()函数)由于I/O相关的原因失败时引发，例如，“未找到文件”。

ImportError:

当import语句未能找到模块定义时引发

IndexError:

当序列(如字符串、列表、元组)索引超出范围时引发。

KeyError:

在找不到字典键时引发。

NameError:

当找不到本地或全局名称时引发。

TypeError:

当操作或函数应用于不适当类型的对象时引发。

有关Python中所有错误类型的完整列表，您可以参考https://docs.python.org/3/library/exceptions.html.

Python还为每种不同类型的错误提供了预定义的错误消息。如果要显示消息，可以在错误类型之后使用as关键字。例如，要显示默认的ValueError消息，可以这样写:

except ValueError as e:

print (e)	

e是分配给错误的变量名。你可以给它起其他名字，但通常使用e。程序中的最后一个except语句

except Exception as e:

print ("Unknown error: ", e)

是使用预定义错误消息的示例。它作为捕获任何未预料到的错误的最后尝试。

## Chapter 7: Functions and Modules

在前面的章节中，我们简要地提到了函数和模块。在这一章中，让我们来详细看看它们。重申一下，所有编程语言都有内置代码，我们可以使用这些代码来简化程序员的生活。这些代码由执行某些常见任务的预先编写的类、变量和函数组成，并保存在称为模块的文件中。让我们先看看函数。

### What are Functions?

函数只是执行特定任务的预先编写的代码。作为一个类比，想想MS Excel中可用的数学函数。要添加数字，可以使用sum()函数和类型sum(A1:A5)，而不是输入A1+A2+A3+A4+A5。

根据函数是如何写的,是否它是一个类的一部分(一个类是面向对象编程的概念,我们将不包括在这本书中)以及如何进口,我们可以调用一个函数,只需输入函数的名称或使用点符号。有些函数需要我们传入数据，以便它们执行它们的任务。这些数据被称为参数，我们将它们的值括在圆括号()中，中间用逗号分隔，从而将它们传递给函数。

例如，要使用print()函数在屏幕上显示文本，我们输入print(“Hello World”)来调用它，其中print是函数名，而“Hello World”是参数。

另一方面，要使用replace()函数来操作文本字符串，我们必须输入" Hello World "。replace(" World "， " Universe ")其中replace是函数名，" World "和" Universe "是参数。点之前的字符串(即“Hello World”)是将受到影响的字符串。因此，“Hello World”将被更改为“Hello Universe”。

### Defining Your Own Functions

我们可以在Python中定义自己的函数，并在整个程序中重用它们。定义函数的语法如下:

```python
def functionName(parameters):
code detailing what the function should do
return [expression]
```

这里有两个关键字，def和return。

def告诉程序，从下一行开始的缩进代码是函数的一部分。return是我们用来从函数返回一个答案的关键字。函数中可以有多个return语句。然而，一旦函数执行return语句，函数将退出。如果你的函数不需要返回任何值，你可以省略return语句。或者，您可以编写return或return None。

现在让我们定义第一个函数。假设我们想要确定一个给定的数是否是质数。下面是如何使用第三章学过的模数(%)操作符以及第六章学过的for循环和if语句定义函数的方法。

```python
def checkIfPrime (numberToCheck):
for x in range(2, numberToCheck):
      if (numberToCheck%x == 0):
            return False
return True
```

在上面的函数中，第2和第3行使用for循环将给定的参数numberToCheck除以从2到numberToCheck - 1的所有数字，以确定余数是否为零。如果余数为零，则要检查的number不是素数。第4行将返回False，函数将退出。

如果在for循环的最后一次迭代中，除法的余数都没有为零，则函数将到达第5行，并返回True。然后函数将退出。

要使用这个函数，我们输入checkIfPrime(13)并将它赋值给这样一个变量

answer = checkIfPrime(13)

这里我们将13作为参数传入。然后我们可以输入print(答案)来打印答案。我们将得到输出:True。

### Variable Scope

定义函数时需要理解的一个重要概念是变量作用域的概念。在函数内部定义的变量与在函数外部定义的变量处理不同。有两个主要区别。

首先，在函数中声明的任何变量都只能在函数中访问。这些被称为局部变量。在函数外声明的任何变量都称为全局变量，在程序中的任何地方都可以访问它。

要理解这一点，请尝试下面的代码:

message1 = "Global Variable"

```python
def myFunction():
print(“\nINSIDE THE FUNCTION”)
#Global variables are accessible inside a function
print (message1)
#Declaring a local variable
message2 = “Local Variable”
print (message2)
#Calling the function
myFunction()
print(“\nOUTSIDE THE FUNCTION”)
#Global variables are accessible outside function
print (message1)
#Local variables are NOT accessible outside function.

print (message2)
```

如果运行该程序，将得到下面的输出。

>INSIDE THE FUNCTION
>
>Global Variable
>
>Local Variable
>
> 
>
>OUTSIDE THE FUNCTION
>
>Global Variable
>
>NameError: name 'message2' is not defined
>
>

在函数中，局部变量和全局变量都可以访问。在函数外部，局部变量message2不再可访问。当我们试图在函数外部访问它时，会得到一个NameError。

关于变量作用域要理解的第二个概念是，如果局部变量与全局变量同名，则函数内的任何代码都在访问该局部变量。外部的任何代码都在访问全局变量。尝试运行下面的代码

```python
message1 = "Global Variable (shares same name as a local variable)"

def myFunction():
message1 = "Local Variable (shares same name as a global variable)"
print(“\nINSIDE THE FUNCTION”)
print (message1)      

# Calling the function
myFunction()
# Printing message1 OUTSIDE the function

print (“\nOUTSIDE THE FUNCTION”)

print (message1)
```

您将得到如下输出:

INSIDE THE FUNCTION

Local Variable (shares same name as a global variable)

OUTSIDE THE FUNCTION

Global Variable (shares same name as a local variable)

当我们在函数内部打印message1时，它在打印局部变量时打印“局部变量(与全局变量共享相同的名称)”。当我们将它输出到外部时，它正在访问全局变量，因此输出“全局变量(与局部变量同名)”。

### Importing Modules

Python提供了大量的内置函数。这些函数保存在称为模块的文件中。要使用Python模块中的内置代码，我们必须首先将它们导入到程序中。我们通过使用import关键字来做到这一点。有三种方法可以做到。

第一种方法是通过编写import moduleName来导入整个模块。

例如，为了导入random模块，我们编写import random。

为了在random模块中使用randrange()函数，我们编写以下代码

随机的。randrange(10)。

如果你发现每次使用函数时都写random太麻烦，你可以通过写import random作为r(其中r是你所选择的任意名称)来导入模块。现在要使用randrange()函数，只需编写r.randrange(1,10)。

第三种导入模块的方法是通过编写从模块中导入特定的函数

name1[， name2[，…以]]。

例如，为了从random模块导入randrange()函数，我们从random import randrange写入。如果要导入多个函数，则用逗号分隔。为了导入randrange()和randint()函数，我们写入random import randrange, randint。现在要使用这个函数，我们不需要再使用点表示法。只要写randrange(1,10)

### Creating our Own Module

除了导入内置模块，我们还可以创建我们自己的模块。如果您希望将来在其他编程项目中重用某些函数，这将非常有用。

创建模块很简单。只需保存扩展名为.py的文件，并将其放入要从其中导入它的Python文件的同一个文件夹中。

假设您想使用前面在另一个Python脚本中定义的checkIfPrime()函数。下面是你该怎么做。首先将上面的代码保存为prime.py保存在桌面上。prime.py应该有以下代码。

```python
def checkIfPrime (numberToCheck):
for x in range(2, numberToCheck):
      if (numberToCheck%x == 0):
            return False
return True
```

接下来，创建另一个Python文件，并将其命名为使用echeckifprime .py。也把它保存在你的桌面上。usecheckifpri .py应该有以下代码。

```python
import prime
answer = prime.checkIfPrime(13)
print (answer)
```

现在useCheckIfPrime.py运行。您应该得到真实的输出。就这么简单。

但是，假设您想存储prime.py并在不同的文件夹中使用echeckifprime .py。您需要添加一些代码来使用echeckifprime .py来告诉Python解释器在哪里找到模块。

假设你在C驱动器中创建了一个名为“MyPythonModules”的文件夹来存储prime.py。您需要将以下代码添加到useCheckIfPrime.py文件的顶部(在import prime行之前)。

```python
import sys
if 'C:\\MyPythonModules' not in sys.path:
    sys.path.append('C:\\MyPythonModules')
```

sys.path指的是Python的系统路径。这是Python用来搜索模块和文件的目录列表。上面的代码将文件夹' C:\MyPythonModules '附加到你的系统路径。

现在你可以把prime.py放在C:\MyPythonModules中，把checkIfPrime.py放在你选择的任何文件夹中。

## Chapter 8: Working with Files

​	太酷了!项目开始前，我们已经读到了这本书的最后一章。在这一章中，我们将看看如何使用外部文件。在前面的第5章中，我们学习了如何使用input()函数从用户那里获取输入。

​	然而，在某些情况下，让用户在我们的程序中输入数据可能不实际，特别是当我们的程序需要处理大量数据时。在这种情况下，更方便的方法是将所需的信息作为外部文件准备，并让我们的程序从文件中读取信息。在这一章中，我们将学习如何做到这一点。准备好了

### Opening and Reading Text Files

我们将要读取的第一种文件类型是具有多行文本的简单文本文件。为此，让我们首先创建一个具有以下行的文本文件。一天之内学会Python并且学好它Python的初学者与动手项目这是唯一一本你需要立即开始用Python编程的书http://www.learncodingfast.com/python

将此文本文件保存为myfile.txt文件到桌面。接下来，启动IDLE并输入下面的代码。也将这段代码保存为fileOperation.py到你的桌面。

```python
f = open (‘myfile.txt’, 'r')
firstline = f.readline()
secondline = f.readline()
print (firstline)
print (secondline)
f.close()
```

代码中的第一行将打开该文件。在我们能够从任何文件中读取之前，我们必须先打开它(就像你需要打开kindle设备或应用程序来阅读这本电子书一样)。open()函数就是这样做的，它需要两个参数:

第一个参数是文件的路径。如果您没有将fileoperations .py和myfile.txt保存在同一个文件夹(本例中是桌面文件夹)中，那么您需要替换' myfile. py '。您存储文本文件的实际路径。例如，如果你将它存储在C驱动器中名为“PythonFiles”的文件夹中，你就必须写入“C:\\PythonFiles\\myfile.txt '(双反斜杠)。

第二个参数是模式。这指定了文件将如何被使用。常用的模式是

'r' mode:

For reading only.

'w' mode:

仅供写作。

如果指定的文件不存在，将创建它。

如果指定的文件存在，文件上的任何现有数据都将被删除。

'a' mode:

附加。

如果指定的文件不存在，将创建它。

如果指定的文件存在，那么写入该文件的任何数据都会自动添加到末尾

'r+' mode:

无论是阅读还是写作

打开文件后，下一个语句firstline = f.r adline()读取文件中的第一行，并将其赋值给变量firstline。

每次调用readline()函数时，它都会从文件中读取一个新行。在我们的程序中，readline()被调用两次。因此将读取前两行。当你运行程序时，你会得到输出:

> Learn Python in One Day and Learn It Well
>
> Python for Beginners with Hands-on Project

您会注意到每行后面都插入了换行符。这是因为readline()函数将' \n '字符添加到每行的末尾。如果不想让每行文本之间有额外的行，可以使用print(firstline, end = ")。这将删除' \n '字符。读取并打印前两行之后，最后一句话f.close()关闭文件。您应该总是在完成文件读取后关闭它，以释放任何系统资源。

### Using a For Loop to Read Text Files

除了使用上面的readline()方法读取文本文件之外，我们还可以使用for循环。事实上，for循环是读取文本文件的一种更优雅、更有效的方式。下面的程序展示了这是如何做到的。

```python
f = open (‘myfile.txt’, 'r')
for line in f:
	print (line, end = ‘’)
f.close()
```

for循环逐行遍历文本文件。当你运行它时，你会得到

>Learn Python in One Day and Learn It Well
>
>Python for Beginners with Hands-on Project
>
>The only book you need to start coding in Python immediately
>
>http://www.learncodingfast.com/python

### Writing to a Text File

现在我们已经学习了如何打开和读取文件，让我们尝试写入它。为此，我们将使用' a ' (append)模式。您也可以使用“w”模式，但如果文件已经存在，您将擦除文件中先前的所有内容。尝试运行以下程序。

```python
f = open (‘myfile.txt’, 'a')
f.write(‘\nThis sentence will be appended.’)
f.write(‘\nPython is Fun!’)
f.close()
```

### Opening and Reading Text Files by Buffer Size

有时，我们可能想按缓冲区大小读取文件，这样程序就不会占用太多的内存资源。为此，我们可以使用read()函数(而不是readline()函数)，它允许我们指定我们想要的缓冲区大小。请尝试以下程序:

```python
inputFile = open (‘myfile.txt’, 'r')s
outputFile = open (‘myoutputfile.txt’, 'w')
msg = inputFile.read(10)
while len(msg):
    outputFile.write(msg)
    msg = inputFile.read(10) 
inputFile.close()
outputFile.close()
```

首先，我们打开两个文件inputFile.txt和outputFile.txt分别用于读取和写入。

接下来，我们使用msg = inputFile.read(10)语句和while循环每次对文件进行10字节的循环。圆括号中的值10告诉read()函数只读取10个字节。len(msg)的while条件:检查变量msg的长度。只要长度不为零，循环就会运行。

在while循环中，语句outputFile.write(msg)将消息写入输出文件。写完消息后，语句msg = inputFile.read(10)读取接下来的10个字节，并一直这样做，直到读取整个文件。当这种情况发生时，程序将关闭两个文件。

当您运行程序时，将创建一个新的文件myoutputfile.txt。打开该文件时，您会注意到它与输入文件myfile.txt具有相同的内容。为了证明一次只读取10个字节，您可以将程序中的行outputFile.write(msg)更改为outputFile.write(msg+ ' \ n ')。现在再次运行程序。myoutputfile.txt现在包含最多10个字符的行。这是你会得到的部分。

>Learn Pyth
>
>on in One
>
>Day and Le
>
>arn It Wel

### Opening, Reading and Writing Binary Files

二进制文件是指任何包含非文本的文件，如图像或视频文件。要处理二进制文件，我们只需使用' rb '或' wb '模式。将一个jpeg文件复制到桌面，并将其重命名为myimage.jpg。现在通过更改前两行来编辑上面的程序

```python
inputFile = open (‘myfile.txt’, 'r')

outputFile = open (‘myoutputfile.txt’, 'w')

to
 
inputFile = open (‘myimage.jpg’, 'rb')

outputFile = open (‘myoutputimage.jpg’, 'wb')
```

确保您也更改了语句outputFile。写(msg + '\n')回outputFile.write(msg)。

运行新程序。您的桌面上应该有一个名为myoutputimage.jpg的附加图像文件。当您打开图像文件时，它应该与myimage.jpg完全相似。

### Deleting and Renaming Files

在处理文件时，我们需要学习的另外两个有用函数是remove()和rename()函数。这些函数在os模块中是可用的，在使用它们之前必须先import它们。

函数的作用是:删除一个文件。语法是remove(filename)。例如，删除myfile。我们写入remove(' myfile.txt ')。

函数的作用是:重命名一个文件。语法是rename(旧名称，新名称)。将oldfile.txt重命名为newfile。txt，我们写rename(' oldfile '。txt”、“newfile.txt”)。

## Project: Math and BODMAS

恭喜你!现在，我们已经学习了足够的Python(以及一般编程)基础知识，可以开始编写第一个完整的程序了。在这一章，我们将编写一个程序来测试我们对算术计算的BODMAS规则的理解。如果你不确定什么是BODMAS，你可以访问这个网站http://www.mathsisfun.com/operation-order-bodmas.html。

我们的程序会随机设置一个算术问题让我们回答。如果我们得到的答案是错误的，程序将显示正确的答案，并询问我们是否想尝试一个新的问题。如果我们答对了，程序会恭维我们，并询问我们是否需要一个新的问题。此外，该程序将跟踪我们的分数，并将分数保存在一个外部文本文件。每个问题结束后，我们可以按“-1”键终止程序。

我已经把程序分解成几个小练习，以便您可以尝试自己编写程序。参考答案之前先试一下练习。答案在附录E中提供，或者你可以访问http://www.learncodingfast.com/python下载Python文件。我强烈建议你下载源代码，因为附录E中的格式可能会导致一些缩进的变形，使代码难以阅读。

记住，学习Python语法很简单，但是很枯燥。解决问题才是乐趣所在。如果你在做这些练习时遇到困难，要更加努力。这是最大的回报。

准备好了吗?我们走吧!

### Part 1: myPythonFunctions.py

我们将为我们的程序编写两个文件。第一个文件是myPythonFunctions.py，第二个是mathGame.py。第1部分将着重于为myPythonFunctions.py编写代码。

首先，让我们创建文件myPythonFunctions.py。我们将在这个文件中定义三个函数。

我们需要为myPythonFunctions导入两个模块。随机模块和操作系统模块。

我们将使用random模块中的randint()函数。函数的作用是:在给定的范围内生成一个随机整数。我们稍后会用它来生成问题中的数字。

在os模块中，我们将使用remove()和rename()函数。

这里我们定义第一个函数。我们将其称为getUserPoint()。这个函数接受一个参数userName。然后打开文件' userScores '。txt为r模式。

userscore .txt看起来是这样的:

>Ann, 100
>
>Benny, 102

每行记录一个用户的信息。第一个值是用户的用户名，第二个值是用户的分数。

接下来，该函数使用for循环逐行读取文件。然后使用split()函数拆分每行(请参阅附录A，以获得使用split()函数的示例)。

让我们将split()函数的结果存储在列表内容中。

接下来，函数检查是否有任何行与作为参数传入的值具有相同的用户名。如果存在，函数关闭文件并返回该用户名旁边的分数。如果不存在，函数关闭文件并返回字符串' -1 '。

现在我们需要对代码做一些修改。当打开我们的文件之前，我们使用' r '模式。这有助于防止对文件的任何意外更改。但是，当以' r '模式打开一个文件时，如果该文件不存在，则会发生IOError。因此，当我们第一次运行该程序时，我们将以一个错误结束，因为文件userScores.txt以前不存在。为了防止这个错误，我们可以做以下任何一种:

我们可以以“w”模式打开文件，而不是以“r”模式打开文件。当以“w”模式打开时，如果该文件以前不存在，将创建一个新文件。这种方法的风险在于，我们可能会意外地写入文件，从而导致之前所有的内容都被擦除。然而，由于我们的程序是一个小程序，我们可以仔细检查我们的代码以防止任何意外的写入。

方法是使用try, except语句来处理IOError。为此，我们需要将所有以前的代码放在try块中，然后使用except IOError:来处理“文件未找到”错误。在except块中，我们将通知用户没有找到文件，然后继续创建文件。我们将使用带有“w”模式的open()函数来创建它。这里的不同之处在于，我们只在没有找到文件时使用“w”模式。由于该文件最初并不存在，因此不存在删除以前任何内容的风险。创建文件后，关闭文件并返回字符串" -1 "。

您可以选择上述方法中的任何一种来完成这个练习。所提供的答案使用第二种方法。一旦你完成了，让我们进入练习3。

练习3:更新用户的分数

在本练习中，我们将定义另一个名为updateUserPoints()的函数，它接受三个参数:newUser、userName和score。

newUser可以为True或False。如果newUser为True，该函数将在append模式下打开userscore .txt文件，并在用户退出游戏时将用户的用户名和分数附加到文件中。

如果newUser为False，该函数将更新该用户在文件中的得分。然而，在Python(或大多数编程语言)中没有函数允许我们更新文本文件。我们只能写入或追加它，但不能更新它。

因此，我们需要创建一个临时文件。这是编程中相当常见的实践。让我们将此文件命名为userScores.tmp，并以“w”模式打开它。现在，我们需要循环userScore.txt，并逐行将数据复制到userScores.tmp。但是，在复制之前，我们将检查该行上的用户名是否与作为参数提供的用户名相同。如果是相同的，我们将在写入临时文件之前将分数更改为新分数。

例如，如果提供给函数的参数是False、' Benny '和' 158 '(即updateUserPoints(False， ' Benny '， ' 158 '))，下面的表显示了原始userscore .txt和新的userscore .tmp之间的差异。

```python
userScores.txt
Ann, 100

Benny, 102

Carol, 214

Darren, 129
userScores.tmp

Ann, 100

Benny, 158

Carol, 214

Darren, 129
```

在我们写完userScore之后。tmp，我们将关闭两个文件并删除userScores.txt。最后，我们将userScores.tmp重命名为userScores.txt。

清楚了吗?试编码…

练习4:生成问题

现在我们进入程序最重要的部分，生成数学问题。准备好了吗?

为了生成问题，我们首先声明三个变量:两个列表和一个字典。

我们将这两个列表命名为operandList和operatorList。

operandList应该存储5个数字，初始值为0。operatorList应该存储四个字符串，以' '作为它们的初始值。

字典由4对组成，用整数1到4作为字典键，“+”、“-”、“*”、“**”作为数据。我们称它为operatorDict。

[练习4.1:用随机数更新operandList]

首先，我们需要用randint()函数生成的随机数替换operandList的初始值。

randint()接受两个参数start和end，并返回一个随机整数N，使start <= N <= end。

例如，如果randint(1,9)被调用，它将随机返回数字1、2、3、4、5、6、7、8、9的整数。

要用随机数更新operandList变量，我们可以一个接一个地这样做，因为operandList只有5个成员。我们可以写

operandList[0] = randint(1, 9)

operandList[1] = randint(1, 9)

operandList[2] = randint(1, 9)

operandList[3] = randint(1, 9)

operandList[4] = randint(1, 9)

每次调用randint(1,9)时，它都会从数字1、2、3、4、5、6、7、8、9中随机返回一个整数。

然而，这并不是更新operandList的最优雅的方法。想象一下，如果operandList有1000个成员，这将是多么麻烦。更好的替代方法是使用for循环。

尝试使用for循环来完成相同的任务。

做了什么?太棒了!

[练习4.2:用数学符号更新operatorList]

现在我们有了要操作的数字，我们需要为我们的问题随机生成数学符号(+、-、*、**)。为此，我们将使用randint()函数和operatorDict字典。randint()将生成字典键，然后使用operatorDict字典将其映射到正确的操作符。例如，要将符号赋值给operatorList[0]，需要写入

operatorList[0] = operatorDict[randint(1, 4)]

与练习4.1类似，您应该使用for循环来完成此任务。然而，有一个问题使这个练习比练习4.1更难。

回想一下，在Python中，**代表指数(即2**3 = 2^3)?

问题是，当Python中有两个连续的指数运算符时，比如2**3**2,Python将它解释为2**(3**2)而不是(2**3)**2。在第一种情况下，答案是2的9次方(即29)也就是512。在第二种情况中，答案是8的2次方，也就是82，也就是64。因此，当我们提出一个像2**3**2这样的问题时，如果用户将其理解为(2**3)**2，那么他就会得到错误的答案。

为了防止这个问题，我们要修改我们的代码，这样我们就不会得到两个连续的**符号。换句话说,operatorList = [‘+’, ‘+’, ‘-’, ‘**’] 很好但operatorList = [‘+’, ‘-’, ‘**’, ‘**’] 不是。

这个练习是所有练习中最难的。尝试想出一个解决方案来防止两个连续的**现象。一旦你完成了，我们可以继续做练习4.3。

提示:如果卡住了，可以考虑在for循环中使用If语句。

[练习4.3:生成数学表达式]

现在我们已经有了操作符和操作数，我们将尝试将数学表达式生成为字符串。这个表达式使用operandList中的5个数字和operatorList中的4个数学符号来构成一个问题。

我们必须声明另一个名为questionString的变量，并将数学表达式赋值给questionString。问题字符串的例子包括

6 – 2*3 – 2**1

4 + 5 – 2*6 + 1

8 – 0*2 + 5 – 8

试着自己产生这个表达。

提示:您可以使用for循环将operandList和operatorList中的各个子字符串连接起来，以获得数学表达式。

[练习4.4:评估结果]

我们现在应该有一个数学表达式作为字符串，赋值给变量questionString。为了计算这个表达式的结果，我们将使用Python自带的一个出色的内置函数eval()。

eval()将字符串解释为代码并执行代码。例如，如果我们写eval(“1+2+4”)，我们会得到数字7。

因此，为了计算数学表达式的结果，我们将questionString传递给eval()函数，并将结果赋值给一个名为result的新变量。

这个练习非常简单，一步就可以完成。

[练习4.5:与用户互动]

最后，我们将与用户进行交互。在这个练习中，我们将做一些事情:步骤1:向用户显示问题步骤2:提示用户回答问题步骤3:评估答案，显示适当的消息并返回用户的分数。对于第一步，我们需要使用一个内置函数来操作字符串。如前所述，在Python中，**符号代表指数。即2**3 = 8。然而，对于大多数用户来说，**没有任何意义。因此，如果我们将一个问题显示为2**3 + 8 -5，用户可能会感到困惑。为了防止这种情况发生，我们将把问题字符串中的任何**符号替换为^符号。

为此，我们将使用内置函数replace()。使用它非常简单，只需写入questionString = questionString。取代 ("**", "^").现在可以将结果表达式打印给用户了。

对于步骤2，您可以使用input()函数来接受用户输入。

对于步骤3，您应该使用if语句来计算答案并显示正确的消息。如果用户得到正确的结果，我们将赞美用户并返回值1。如果用户答错了，我们将显示正确的答案并返回值0。

还记得input()函数以字符串形式返回用户输入吗?因此，当您将用户的输入与正确的答案(在练习4.4中获得)进行比较时，您必须执行某种类型转换以将用户输入更改为整数。当将用户输入更改为整数时，您应该使用try, except语句检查用户是否输入了一个数字。如果用户输入的是字符串，程序应该告诉用户错误，并提示用户输入一个数字。你可以使用while True循环来持续提示用户输入一个数字，只要他/她没有这样做。写while True相当于写while 1==1这样的东西。因为1总是等于1(因此总是为真)，所以循环将无限期地运行。这里有一个关于如何在这个练习中使用while True循环的建议。

```python
while True:

try:

cast user’s answer to an integer and evaluate the answer

return user score based on the answer

except:

print error message if casting fails

prompt user to key in the answer again

 
```

while True循环将继续循环，因为while条件始终为真。只有当try块正确执行并到达return语句时，循环才会退出。

试试这个练习。完成之后，我们可以进入第2部分，在第2部分我们编写实际的程序。

### Part 2: mathGame.py