# github搜索技巧

> 符号要用英文的。如果同时使用多个搜索条件，条件之间要用空格分开。

### 一、 仓库搜索

简介：通过仓库搜索限定符来缩小搜索范围。

#### 1.1 仓库创建或上次更新时间

根据仓库创建的时间或仓库上传更新的时间来过滤不符合条件的仓库。

这两个搜索都是用日期作为参数，日期格式必须是`YYYY-MM-DD`，代表年月日（遵循ISO8601标准，日期时间的表示方法）。

##### 1.1.1 根据仓库创建时间

使用 *created* 限定符搜索。

| 限定符             | 举例                                                         |
| ------------------ | ------------------------------------------------------------ |
| created:YYYY-MM-DD | python created:<2020-06-08    意思：搜索2020年6月8日之前创建、具有“python”字样的仓库。 |

##### 1.1.2 根据仓库上传更新时间

使用 *pushed* 限定符搜索。

| 限定符            | 举例                                                         |
| ----------------- | ------------------------------------------------------------ |
| pushed:YYYY-MM-DD | python pushed:>2020-06-08    意思：搜索2020年6月8日之后更新（即收到推送）、具有“python”字样的仓库。                 python pushed:>2020-06-08 fork:only  意思：搜索具有“python”字样、在2020年6月8日之后更新，并且是复制的仓库。 |

#### 1.2 仓库名称或自述文件（README）

使用 *in* 限定符搜索。

通过`in`限定符，将搜索限制为根据仓库创建时间、仓库名称、仓库星标数等条件搜索（或这些条件的任意组合）。如果不用`in`限定符，则只是搜索仓库名称和仓库说明。

| 限定符                     | 举例                                                         |
| -------------------------- | ------------------------------------------------------------ |
| in:name                    | python in:name    意思：搜索名称中有“python”字样的仓库。     |
| in:description  或in:about | in:description demo   意思：搜索简介有“demo”字样的仓库。     |
| in:name,description        | python in:name,description  意思：搜索名称或说明中有“python”字样的仓库。 |
| in:readme                  | python in:readme    意思：搜索自述文件中提及“python”的仓库。 |
| repo:owner/name            | repo:TheAlgorithms/python   意思：搜索TheAlgorithms的python仓库（搜索特定仓库名称）。 |

> 上面以TheAlgorithms的python仓库为例，该仓库是一个81k星标的仓库。

#### 1.3 仓库星标数量

使用 *stars* 限定符搜索。

根据仓库星标数量进行大于、小于或范围限定搜索。

| 限定符                         | 举例                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| stars:n                        | stars:100   意思：搜索刚好100星标的仓库。                    |
| stars:n..m                     | stars:10..20   意思：搜索星标数是10到20的仓库。              |
| stars:>=n fork:true language:m | stars:>=100 fork:true language:python   意思：搜索大于或等于100星标（包括分叉的星标），以python编写的仓库。 |

### 二、 主题搜索

简介：通过特定主题来搜索相关仓库。

使用 *is:featured* 、*is:curated* 、 *repositories:n* 限定符搜索。

| 限定符             | 举例                                                         |
| ------------------ | ------------------------------------------------------------ |
| is:featured        | is:featured python   意思：搜索https://github.com/topics上提供，并且含有“python”字样的主题。 |
| is:not-featured    | is:not-featured python   意思：搜索https://github.com/topics上未提供，并且含有“python”字样的主题。 |
| is:curated         | is:curated python   意思：搜索精选项目中含有“python”字样的主题。 |
| is:not-curated     | is:not-curated python   意思：搜索没有额外说明且含有“python”字样的主题。 |
| repositories:n     | repositories:>100   意思：搜索超过100个仓库的主题。          |
| created:YYYY-MM-DD | python created:>2020-06-08   意思：搜索于2020年6月8日之后创建、含有“python”字样的主题。 |

### 三、 代码搜索

简介：通过全局搜索代码，或在特定仓库内搜索代码。

#### 3.1 根据文件内容或路径

使用 *in* 限定符搜索。

使用`in`限定符，根据文件内容、文件路径来搜索，或满足这两个条件其中之一的条件搜索。如果不使用`in`限定符，则只是搜索文件内容。

| 限定符       | 举例                                                         |
| ------------ | ------------------------------------------------------------ |
| in:file      | demo in:file   意思：搜索文件内容中出现“demo”的代码。        |
| in:path      | demo in:path   意思：搜索文件路径中出现“demo”的代码。        |
| in:file,path | demo in:file,path  意思：搜索文件内容或文件路径中出现“demo”的代码。 |

#### 3.2 根据文件位置

使用 *path* 限定符搜索。

使用`path`限定符搜索仓库中特定位置显示的代码。使用`path:/`限定符搜索位于仓库根目录级别的文件。可以指定目录名称或路径搜索该目录及其子目录中的文件。

| 限定符                 | 举例                                                         |
| ---------------------- | ------------------------------------------------------------ |
| path:/                 | python filename:readme path:/   意思：搜索位于仓库根目录级别、含有“python”字样的readme文件。 |
| path:DIRECTORY         | test path:demo language:python   意思：搜索demo目录及其子目录中含有“test”字样的python文件。 |
| path:PATH/TO/DIRECTORY | test path:bad/written language:python   意思：搜索bad/wirtten目录及其子目录中含有“test”字样的python文件。 |

#### 3.3 根据文件名

使用 *filename* 限定符搜索。

使用`filename`限定符根据文件名搜索代码文件。

| 限定符                                             | 举例                                                         |
| -------------------------------------------------- | ------------------------------------------------------------ |
| filename:FILENAME                                  | filename:demo   意思：搜索名为“demo”的文件。                 |
| filename:FORMAT                                    | filename:.txt demo   意思：搜索具有“demo”字样的.txt文件。    |
| filename:FILENAME path:DIRECTORY language:LANGUAGE | filename:test path:demo language:python   意思：搜索demo目录中名为test的python文件。 |

### 四、 搜索开发者

简介：根据作者搜索相关项目。

使用 *location* 、 *language* 、 *followers* 、 *fullname* 参数搜索。

| 条件                | 举例                                                     |
| ------------------- | -------------------------------------------------------- |
| location:LOCATION   | location:china   意思：搜索填写的地址是在china的开发者。 |
| language:LANGUAGE   | language:python   意思：搜索开发语言为python的开发者。   |
| followers:FOLLOWERS | followers:>=100   意思：搜索拥有超过100关注者的开发者。  |
| in:fullname         | Kobe in:fullname   意思：搜索用户实名为Kobe的开发者。    |

### 五、 GitHub的分区

#### 5.1 GitHub Trend

这个是GitHub的趋势榜，宣传语是看看GitHub今天最激动的是什么。

在这里有多个查找项目的方式，可以选择编程语言，也可以选择月榜、周榜、日榜。还可以选择开发者，查看开发者的优质项目。

地址：https://github.com/trending

#### 5.2 Github Topics

Github Topics展示了最新、最热门的讨论主题，宣传语是浏览GitHub上的热门话题。

在这里不仅可以看到开源项目，还可以看到一些非开发技术的讨论主题。

地址：https://github.com/topics

#### 5.3 Github Explore

Github Explore这里是根据你平时的兴趣，推荐一些项目。

地址：https://github.com/explore

------

好了，以上是Github搜索项目技巧的总结，建议**收藏**起来，在需要找项目时，通过这些技巧去搜索。如果文章对你有帮助，请点个**赞**，留个**评论**，可以给个三连（**点赞**、**收藏**、**关注** (*^_^*) ）就最好啦😁。