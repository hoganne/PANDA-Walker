# gradle build tool（构建工具）

## Installing manually（手动安装）

#### Step 1. [Download](https://gradle.org/releases) the latest Gradle distribution

当前的Gradle版本是6.8.1，于2021年1月22日发布。发行版zip文件有两种形式:

- [Binary-only] -二进制
- [Complete], with docs and sources 完整的

If in doubt, choose the binary-only version and browse [docs](https://docs.gradle.org/current) and [sources](https://github.com/gradle/gradle) online.

Need to work with an older version? See the [releases page](https://gradle.org/releases).

#### Step 2. Unpack the distribution解压分布

##### Linux & MacOS users

Unzip the distribution zip file in the directory of your choosing, e.g.:

解压您选择的目录下的分发压缩文件，例如:

```
$ mkdir /opt/gradle
$ unzip -d /opt/gradle gradle-6.8.1-bin.zip
$ ls /opt/gradle/gradle-6.8.1
LICENSE  NOTICE  bin  getting-started.html  init.d  lib  media
```

##### Microsoft Windows users

Create a new directory `C:\Gradle` with **File Explorer**.

用**文件资源管理器**创建一个新目录' C:\Gradle '。

Open a second **File Explorer** window and go to the directory where the Gradle distribution was downloaded. Double-click the ZIP archive to expose the content. Drag the content folder `gradle-6.8.1` to your newly created `C:\Gradle` folder.

打开第二个**文件资源管理器**窗口，进入Gradle发行版被下载的目录。双击ZIP存档以公开内容。将内容文件夹' Gradle -6.8.1 '拖到新创建的' C:\Gradle '文件夹中。

或者，你也可以使用你选择的存档工具将Gradle发行版压缩包解压到' C:\Gradle '中。

#### Step 3. Configure your system environment 配置您的系统环境

##### Linux & MacOS users

配置你的“PATH”环境变量以包含解压后的发行版的“bin”目录，例如:

```
 $ export PATH=$PATH:/opt/gradle/gradle-6.8.1/bin
```

##### Microsoft Windows users

In **File Explorer** right-click on the `This PC` (or `Computer`) icon, then click `Properties` -> `Advanced System Settings` -> `Environmental Variables`.

在**文件资源管理器**中右键单击' This PC '(或' Computer ')图标，然后单击'属性' -> '高级系统设置' -> '环境变量'。

Under `System Variables` select `Path`, then click `Edit`. Add an entry for `C:\Gradle\gradle-6.8.1\bin`. Click OK to save.

#### Step 4. Verify your installation（验证您的安装）

Open a console (or a Windows command prompt) and run `gradle -v` to run gradle and display the version, e.g.:

打开控制台(或Windows命令提示符)，运行' gradle -v '来运行gradle并显示版本，例如:

```
$ gradle -v
------------------------------------------------------------
Gradle 6.8.1
------------------------------------------------------------
```

#### [ Additional helpful information](https://gradle.org/install/#helpful-information)

额外的有用的信息

## Upgrade with the Gradle Wrapper

使用Gradle包装器进行升级

If your existing Gradle-based build uses the [Gradle Wrapper](https://docs.gradle.org/6.8.1/userguide/gradle_wrapper.html), you can easily upgrade by running the `wrapper` task, specifying the desired Gradle version:

如果你现有的基于Gradle的构建使用[Gradle Wrapper](https://docs.gradle.org/6.8.1/userguide/gradle_wrapper.html)，你可以通过运行' Wrapper '任务，指定所需的Gradle版本来轻松升级:

```
$ ./gradlew wrapper --gradle-version=6.8.1 --distribution-type=bin
```

Note that it is not necessary for Gradle to be installed to use the Gradle wrapper. The next invocation of `gradlew` or `gradlew.bat` will download and cache the specified version of Gradle.

注意，使用Gradle包装器并不需要安装Gradle。下一个' gradlew '或' gradlew '的调用。将下载并缓存指定版本的Gradle。

```sh
$ ./gradlew tasks
Downloading https://services.gradle.org/distributions/gradle-6.8.1-bin.zip
...
```

#### [Additional helpful information](https://gradle.org/install/#helpful-information)

## Older Releases（老版本）

You can find all releases and their checksums on the [releases page](https://gradle.org/releases).

您可以在[releases页面](https://gradle.org/releases)上找到所有发布版本及其校验和。

## Command-Line Completion（命令对齐）

[Command-line completion](https://git.io/gradle-completion) scripts are available for bash and zsh. This provides completion for Gradle tasks and command-line options.

[命令行补全](https://git.io/gradle-completion)脚本可用于bash和zsh。这为Gradle任务和命令行选项提供了完成功能。