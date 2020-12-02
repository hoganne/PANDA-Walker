# basic terminal commands（liunx）

grep

awk

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



