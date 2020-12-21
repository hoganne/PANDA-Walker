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

