# cenost python更新以及使用

服务器版本：CentOS 7.3 64位

旧Python版本：2.7.5

新Python版本：3.8.0

说明：本次配置使用root用户进行操作，故在代码中未使用sudo语句，请使用非root用户留意

------

 1、切换工作目录至/usr/local

在Linux系统下，路径/usr/local相当于C:/Progrem Files/，通常安装软件时便安装到此目录下。

执行命令：

```
cd /usr/local
```

2、下载目标python版本压缩包

执行命令：

```
wget http://npm.taobao.org/mirrors/python/3.8.0/Python-3.8.0.tgz
```

这里，我使用的是python在国内的镜像，若希望使用官网地址当然也是可以的，不过速度就两说了...

3、解压压缩包

执行命令：

```
tar -xzf Python-3.8.0.tgz
```

4、在当前目录下创建文件夹--python3

执行命令：

```
mkdir python3
```

5、编译与安装

### **执行步骤：**

a.进入解压文件路径

```
cd Python-3.8.0
```

b.生成makefile文件

```
./configure --prefix=/usr/local/python3
```

c. 对makefile文件进行操作

```
make
```

d.安装

```
make install
```

### **命令解释：**

- 在Linux中通过源码安装程序时，对于解压文件，先执行./configure，后执行make，最后执行make install

　　　　make 命令 是对makefile文件操作，make install 是安装命令，那么 ./configure 是干什么呢？./configure 其实就是生成 makefile 文件

——参考“[朝闻道](https://www.cnblogs.com/findumars/p/8206930.html)”

- --prefix作用：编译的时候用来指定程序存放路径。

　　　　不指定prefix，可执行文件默认放在/usr/local/bin，库文件默认放在/usr/local/lib，配置文件默认放在/usr/local/etc，其它的资源文件放在/usr/local/share

　　　　指定prefix，直接删掉一个文件夹就够了

——参考“[百度知道](https://zhidao.baidu.com/question/535223201.html)”

### 问题：

在这个过程中，出现了安装失败的问题：

```
zipimport.ZipImportError: can't decompress data; zlib not available
```

这是由于缺少依赖造成的，可执行以下代码：

```
yum install zlib-devel bzip2 bzip2-devel readline-devel sqlite sqlite-devel openssl-devel xz xz-devel libffi-devel
```

——参考“[寒爵](https://www.cnblogs.com/Jimc/p/10218062.html)”

6、两种版本配置方法

关于配置python版本的方法大致分为**两种**：

1. 直接创建python3软链，利用命令 **python3** 调用新版本python，与自带python不冲突
2. 覆盖现有python，使其指向新安装的python，利用命令 **python** 便可调用新版本python

### 所涉及命令解释

- ln命令用来为文件创建链接，链接类型分为硬链接和符号（软）链接两种，默认的连接类型是硬连接。如果要创建符号链接必须使用"-s"选项，符号链接相当于Windows下的快捷方式，即可以实现启动python时指向python3.8

```
ln -s a b # 建立软连接，b指向a
```

——参考“[颜子](https://www.cnblogs.com/newcolor/p/8194027.html)”

下面分别对两种方法进行介绍。

6.1直接创建python3软链

在/usr/bin路径下创建python3软链，指向已安装的python3

```
ln -s /usr/local/python3/bin/python3 /usr/bin/python3
```

在/usr/bin路径下创建pip3软链，指向已安装的pip3

```
ln -s /usr/local/python3/bin/pip3 /usr/bin/pip3
```

此时系统中存在两个python版本：

命令 **python** 对应的仍是默认2.7版本

命令 **python3** 则对应新安装的3.8版本，

此本方法到此便结束了，可以快乐地打出 **python3** **-V, pip3 -V**查看对应版本了

6.2 覆盖现有python，启动python时指向python3.8

### 备份旧python与pip

```
 mv /usr/bin/python /usr/bin/python2_old
 mv /usr/bin/pip /usr/bin/pip2_old　　#第三部分的文件名可根据本机版本修改
```

### 修改软链接

```
ln -s /usr/local/python3/bin/python3 /usr/bin/python
ln -s /usr/local/python3/bin/pip3 /usr/bin/pip
```

此方法到这里已经完成一半啦，可以使用命令 **python -V, pip -V**查看版本

根据“[撑起风帆](https://blog.csdn.net/u011244708/article/details/82915006)”的经验，还应该对easy_install的指向进行修改，使用python3时可能会用到（未验证），用到时可根据上述“修改软链接”的方法进行修改

6.2.1 收尾工作

由于修改了python的版本，会导致一些依赖于旧版本的程序出现错误，如**yum**，可进行如下配置：

### 打开yum文件

```
vim /usr/bin/yum
```

文件第一行如下图：

![img](https://img2018.cnblogs.com/blog/1849542/201910/1849542-20191027175633589-1793925683.png)

 将python修改为旧的版本号，此处添加2.7：

![img](https://img2018.cnblogs.com/blog/1849542/201910/1849542-20191027175723072-1647453815.png)

 不过在安装软件时仍会报错:

```
SyntaxError: invalid syntax
  File "/usr/libexec/urlgrabber-ext-down", line 28　　except OSError, e:
```

还是因为python更换的原因， 依照上述操作修改文件即可：打开/usr/libexec/urlgrabber-ext-down 文件，将 **#!/usr/bin/python**  修改为 **#!/usr/bin/python2.7**  

其他工具或库如果在安装过程中也报类似错误，同法处理。

此时系统仍存在两个python版本，但命令 **python** 对应的是新安装的3.8版本，命令 **python2** 才对应之前的默认2.7版本
此方法到这里就结束啦！

------

 除文中所列参考文章外，主要参考文章还有：

https://blog.csdn.net/bawenmao/article/details/80216516

https://blog.csdn.net/u011798443/article/details/80825817