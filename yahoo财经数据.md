# yahoo 财经数据

看起来他们已经开始添加必需的cookie，但您可以相当容易地检索它，例如：

```
GET https://uk.finance.yahoo.com/quote/AAPL/history
```

响应表单中的标题：

```
set-cookie:B=xxxxxxxx&b=3&s=qf; expires=Fri, 18-May-2018 00:00:00 GMT; path=/; domain=.yahoo.com
```

您应该能够阅读此内容并将其附加到您的`.csv`请求中：

```
GET https://query1.finance.yahoo.com/v7/finance/download/AAPL?period1=1492524105&period2=1495116105&interval=1d&events=history&crumb=tO1hNZoUQeQcookie: B=xxxxxxxx&b=3&s=qf;
```

请注意`crumb`查询参数，这似乎与您`cookie`的某些方式相对应。您最好的选择是`scrape`从HTML响应到您的初始GET请求。在该响应中，您可以执行正则表达式搜索：`"CrumbStore":\{"crumb":"(?<crumb>[^"]+)"\}`并提取crumb匹配组。

它看起来就像你有了这个`crumb`价值，虽然你可以`cookie`在明年的任何符号/代码上使用它，这意味着你不必`scrape`太频繁地做。

------

要获取当前报价，请加载：

```
https://query1.finance.yahoo.com/v8/finance/chart/AAPL?interval=2m
```

附：

- AAPL替换为您的股票代码
- 间隔之一 `[1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]`
- `period1`带有您的纪元范围开始日期的可选查询参数，例如`period1=1510340760`
- `period2`带有您的纪元范围结束日期的可选查询参数，例`period2=1510663712`





Yahoo已进入Reactjs前端，这意味着如果您分析从客户端到后端的请求标头，您可以获得用于填充客户端存储的实际JSON。

##### 主持人：

- `query1.finance.yahoo.com` HTTP / 1.0
- `query2.finance.yahoo.com`HTTP / 1.1（[HTTP / 1.0和HTTP / 1.1之间的区别](https://stackoverflow.com/questions/246859/http-1-0-vs-1-1)）

如果您打算使用代理或持久连接使用`query2.finance.yahoo.com`。但是出于本文的目的，用于示例URL的主机并不意味着暗示它正在使用的路径。

------

##### 基本数据

- `/v10/finance/quoteSummary/AAPL?modules=` （以下模块的完整列表）

*（用你的符号代替：AAPL）*

**`?modules=`查询的输入：**

- `modules = [  'assetProfile',  'incomeStatementHistory',  'incomeStatementHistoryQuarterly',  'balanceSheetHistory',  'balanceSheetHistoryQuarterly',  'cashflowStatementHistory',  'cashflowStatementHistoryQuarterly',  'defaultKeyStatistics',  'financialData',  'calendarEvents',  'secFilings',  'recommendationTrend',  'upgradeDowngradeHistory',  'institutionOwnership',  'fundOwnership',  'majorDirectHolders',  'majorHoldersBreakdown',  'insiderTransactions',  'insiderHolders',  'netSharePurchaseActivity',  'earnings',  'earningsHistory',  'earningsTrend',  'industryTrend',  'indexTrend',  'sectorTrend' ]`

**示例网址：**

- `https://query1.finance.yahoo.com/v10/finance/quoteSummary/AAPL?modules=assetProfile%2CearningsHistory`

*查询*：`assetProfile`和`earningsHistory`

的`%2C`是的十六进制表示`,`，需要每次请求模块之间插入。[有关十六进制编码位的详细信息](https://stackoverflow.com/questions/6182356/what-is-2c-in-a-url)（如果您关心）

------

#### 期权合约

- `/v7/finance/options/AAPL` （当前到期）
- `/v7/finance/options/AAPL?date=1579219200` （2020年1月17日到期）

**示例网址：**

- `https://query2.yahoo.finance.com/v7/finance/options/AAPL` （当前到期）
- `https://query2.yahoo.finance.com/v7/finance/options/AAPL?date=1579219200`（2020年1月17日到期）

可以在`?date=`查询中使用表示为UNIX时间戳的任何有效的未来到期。如果查询当前过期，JSON响应将包含可在`?date=`查询中使用的所有有效过期的列表。[*（这是一篇解释在Python中将人类可读日期转换为unix时间戳的帖子）*](https://stackoverflow.com/questions/3682748/converting-unix-timestamp-string-to-readable-date-in-python)

------

#### 价钱

- `/v8/finance/chart/AAPL?symbol=AAPL&period1=0&period2=9999999999&interval=3mo`

**间隔：**

- `&interval=3mo` 3个月，回到初始交易日期。
- `&interval=1d` 1天，回到最初的交易日期。
- `&interval=5m` 5分钟，回到80（ish）天。
- `&interval=1m` 1分钟，回去4-5天。

每个间隔你可以走多远，有点混乱，似乎不一致。我的假设是内部雅虎在交易日计算，而我的天真方法并不是假期。虽然这是一个猜测和YMMV。

`period1=`：unix时间戳表示您希望**开始**的日期。低于初始交易日的价值将四舍五入至初始交易日。

`period2=`：unix时间戳表示您希望**结束**的日期。大于上次交易日期的值将向下舍入到可用的最新时间戳。

**注意：** *如果您使用`period1=`（开始日期）在您选择的时间间隔内查询过多，则`3mo`无论您请求的间隔是多少，雅虎都会在该时间间隔内返回价格。*

**添加前后市场数据**

```
&includePrePost=true
```

**增加股息和分割**

```
&events=div%2Csplit
```

**示例网址：**

- `https://query1.finance.yahoo.com/v8/finance/chart/AAPL?symbol=AAPL&period1=0&period2=9999999999&interval=1d&includePrePost=true&events=div%2Csplit`

上述请求将在1天的时间间隔内返回股票代码AAPL的所有价格数据，包括市场前和市场后数据以及股息和拆分。

**注意：** *价格示例网址中用于`period1=`＆的值用于`period2=`演示每个输入的相应舍入行为。*







### 补充参考



# [股票数据接口](https://www.cnblogs.com/ilovecpp/p/12749980.html)

引用：https://blog.csdn.net/fengmm521/article/details/78446501

一、网易接口

  1.网易实时股票数据api：

（新浪和腾讯用sh、sz来区分上证和深证，网易用0和1来区分）

  单股实时数据

[http://api.money.126.net/data/feed/0601398%2cmoney.api](http://api.money.126.net/data/feed/0601398%2Cmoney.api)

  多股票实时查询

[http://api.money.126.net/data/feed/0601398%2c1000001%2c1000881%2cmoney.api](http://api.money.126.net/data/feed/0601398%2C1000001%2C1000881%2Cmoney.api)

  2.历史数据下载（CSV格式）

下面是获取工商银行0601398，从2014年07月20日到2015年05月08日之间的历史数据，文件为CSV格式

http://quotes.money.163.com/service/chddata.html?code=0601398&start=20140720&end=20150508

code表示股票代码, 上海股票最前边加0,深圳股票最前边加1

创业版股票代码是以3开头的，为深圳股票，所以code=1300142.

上海股票代码都是以6开头的，code=0601398.

深圳股票代码非创业版股票以0开头，code=1000089.

3.后复权数据下载:

http://img1.money.126.net/data/hs/klinederc/day/times/1002095.json

二、新浪接口

  1.新浪的实时数据api:

http://hq.sinajs.cn/?list=sh600127

“sh+股票代码”为上海股票

“sz+股票代码”为深圳股票

  2.新浪的历史数据api:

http://market.finance.sina.com.cn/downxls.php?date=2016-10-28&symbol=sz300127

腾发现新浪的历史数据在mac下用Number打开中文是乱码，可能文本编码有问题吧.

三、腾讯股票实时数据api:

http://qt.gtimg.cn/q=sz000858

四、雅虎接口

雅虎历史数据api:

http://table.finance.yahoo.com/table.csv?s=000002.sz&d=6&e=22&f=2006&g=d&a=11&b=16&c=1991&ignore=.csv

据说雅虎的实时数据有半小时延时，这里就不写雅虎的实时api了.

实际作数据分析时发现会有复权数据的问题,再加几个别人总结的接口,



最近在做股票分析系统，数据获取源头成了一大问题，经过仔细的研究发现了很多获取办法，这里整理一下，方便后来者使用。

获取股票数据的源头主要有：数据超市、雅虎、新浪、Google、和讯、搜狐、ChinaStockWebService、东方财富客户端、证券之星、网易财经。

# 数据超市

*2016年5月6日更新*。根据最近频繁出现的数据超市，可以无限制获取相关数据，而不再需要使用爬虫等方式获取，这样不仅节省了极大资源，也有利于遍历数据。

具体的方法不再赘述，列出来相关网站清单，开发者可自行到这些网站查询调用方法。

聚合数据 https://www.juhe.cn/

百度API数据 http://apistore.baidu.com/

发源地 http://www.finndy.com/

笔者这里推荐使用聚合数据，其次配合百度API使用即可。

## **雅虎**

缺点：某些美国节假日数据会短缺；调用次数不能超过每分钟200次，否则会被警告并封锁IP地址；自定义列获取方法只能获取美股数据。

优点：数据最标准化，可以获取其他国家市场数据；返回数据类型可自定义组合。

#### 方法1：http://table.finance.yahoo.com/table.csv?s=股票代码

返回结果：CSV格式的文件，返回列依次是“日期、开盘价、最高价、最低价、收盘价、成交量、复权价”。

其股票代码需要在末尾标记所属市场，如上证指数股票代码为600000.ss、深圳成指为399001.sz。

#### 方法2：http://finance.yahoo.com/d/quotes.csv?s=股票代码&f=[自定义列]

返回结果：CSV格式的文件，最后一个交易日的数据，列根据设定的自定义列返回。

例如：http://finance.yahoo.com/d/quotes.csv?s=TWTR&f=nt1，返回TWTR股票的名称和最后交易时间。

#### **方法3：http://quote.yahoo.com/d/quotes.csv?s=**股票代码&f=[自定义列]

和方法2类似。

#### 自定义列

# ChinaStockWebService

缺点：不稳定，经常出现维护停止服务；只能获取当前行情和历史走势图。

优点：国内数据提供商，以XML响应，速度较快。

方法参考：http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx



## 东方财富客户端

缺点：手动操作导出；没有历史数据。

优点：数据全面，速度较快。

方法：通过东方财富网客户端自带的功能，导出数据。

相对于其他获取方式，这种获取速度非常快，出错率非常低，而且数据非常全面，如果定期整理可以作为非常有效的数据使用。



## 新浪

缺点：历史股价数据不够完整，只能获取最近1023个数据节点。

优点：速度非常快；可以获取行情图片；返回JSON，容易处理；可以获取历史的分价图数据和分时买卖交易列。

#### 方法1：HTTP://HQ.SINAJS.CN/LIST=[股票代码]

返回结果：JSON实时数据，以逗号隔开相关数据，数据依次是“股票名称、今日开盘价、昨日收盘价、当前价格、今日最高价、今日最低价、竞买价、竞卖价、成交股数、成交金额、买1手、买1报价、买2手、买2报价、…、买5报价、…、卖5报价、日期、时间”。

获取当前的股票行情，如http://hq.sinajs.cn/list=sh601006，注意新浪区分沪深是以sh和sz区分。

#### 方法2：获取各个时间段行情图。

查看日K线图：http://image.sinajs.cn/newchart/daily/n/sh601006.gif分时线的查询：http://image.sinajs.cn/newchart/min/n/sh000001.gif日K线查询：http://image.sinajs.cn/newchart/daily/n/sh000001.gif周K线查询：http://image.sinajs.cn/newchart/weekly/n/sh000001.gif月K线查询：http://image.sinajs.cn/newchart/monthly/n/sh000001.gif

**方法3：http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=[市场][股票代码]&scale=[周期]&ma=no&datalen=[长度]**

返回结果：获取5、10、30、60分钟JSON数据；day日期、open开盘价、high最高价、low最低价、close收盘价、volume成交量；向前复权的数据。

注意，最多只能获取最近的1023个数据节点。

例如，http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sz002095&scale=60&ma=no&datalen=1023，获取深圳市场002095股票的60分钟数据，获取最近的1023个节点。

**方法4：http://finance.sina.com.cn/realstock/company/[市场][股票代码]/[复权].js?d=[日期]**

[复权]：qianfuquan-前复权；houfuquan-后复权。

返回结果：股票日期的股价JSON数据。

例如，http://finance.sina.com.cn/realstock/company/sz002095/qianfuquan.js?d=2015-06-16，获取深圳市场002095股票的前复权2015-06-16的数据。

注意，无法获取未复权的数据。

注意，需要对返回数据进行处理才能使用，新浪会在末尾加入注释语句，打乱日期数据，key值需要自行加入双引号，否则无法解析JSON。

注意，由于新浪的周线和月线数据，是以股票日线所有数据直接计算得到的，所以无法直接通过API获取周线和月线数据，需要自行处理。

**方法5：http://market.finance.sina.com.cn/downxls.php?date=[日期]&symbol=[市场][股票代码]**

返回数据：XLS文件；股票历史成交明细。

例如，http://market.finance.sina.com.cn/downxls.php?date=2015-06-15&symbol=sz002095，获取2015-06-15日期的深圳市长002095数据。

**方法6：http://market.finance.sina.com.cn/pricehis.php?symbol=[市场][股票代码]&startdate=[开始日期]&enddate=[结束日期]**

返回数据：HTML文本；指定日期范围内的股票分价表。

例如，http://market.finance.sina.com.cn/pricehis.php?symbol=sh600900&startdate=2011-08-17&enddate=2011-08-19，获取上证600900股票2011-08-17到2011-08-19日期的分价数据。



## 和讯

（待续）



## Google

Google数据其实是从新浪获取的，所以可以优先考虑从新浪获取，如果喜欢google的API，再考虑。

优点：数据可靠。

缺点：历史数据只能通过分解HTML页面获取；国外数据源速度慢。

方法1：从股票历史数据中获取相关数据。

方法2：https://www.google.com.hk/finance/getprices?q=[股票代码]&x=[市场]&i=[间隔]&p=[周期]

例如,https://www.google.com.hk/finance/getprices?q=002673&x=SHE&i=1800&p=30d，意思就是获取上证002673的股票，周期为30天，间隔1800。



## 搜狐

优点：JSON数据结果容易处理；获取速度快。

缺点：每次只能获取100个节点的数据；API经常变动。

**方法1：http://q.stock.sohu.com/hisHq?code=[股票市场和代码]8&start=[开始日期]&end=[结束日期]&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp**

返回结果：JSON；时间段内的100个数据节点。

例如，http://q.stock.sohu.com/hisHq?code=cn_300228&start=20130930&end=20131231&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp，返回30022股票20130930到20131231时间段内的日线数据。



## 证券之星

缺点：只能获取80条数据。

优点：国内速度快；JSON返回容易解析。

#### 方法1：HTTP://CQ.SSAJAX.CN/INTERACT/GETTRADEDATA.ASHX?PIC=QLPIC_[股票代码]_[市场]_[周期]

其中股票代码如000001；市场1表示沪，2表示深；周期6表示日，7表示周，8表示月。如：http://cq.ssajax.cn/interact/getTradedata.ashx?pic=qlpic_000001_1_6，获取上证指数的日线行情数据。

返回结果：JSON数据。

#### 方法2：HTTP://CQ.SSAJAX.CN/INTERACT/GETTRADEDATA.ASHX?PIC=QMPIC_600308_1_6_N80



## 网易财经

优点：速度快；JSON容易处理。

缺点：不能获取分钟线数据。

**方法1：http://img1.money.126.net/data/[沪深拼音]/time/today/[股票代码].json**

返回结果：当日分时图数据；JSON数据；分时图获取数据依次是count节点数量、symbol股票代码、name股票名称、data数据，其中数据依次是小时分钟时间、价格、均价、成交量。

注意，沪深拼音为简写hs，以此可以推断出其他市场也可以获取，具体请自行判断研究。

例如，http://img1.money.126.net/data/hs/time/today/1399001.json，返回深证成指当日分时图数据。

**方法2：http://img1.money.126.net/data/hs/time/4days/[股票代码].json**

返回结果：获取4天分时数据；和上述分时图相似，但数据是连续4天的数据，不包括当天的数据。

**方法3：http://img1.money.126.net/data/[沪深拼音]/[是否复权]/day/history/[年份]/[股票代码].json**

返回结果：获取日线数据。

其中，是否复权，不复权为kline，复权为klinederc。

例如，http://img1.money.126.net/data/hs/kline/day/history/2015/1399001.json，获取深证成指2015年所有日线数据。

**方法4：http://img1.money.126.net/data/[沪深拼音]/[是否复权]/[周期]/times/[股票代码].json**

返回结果：获取日线所有时间节点和收盘价。

其中，[是否复权]，不复权为kline，复权为klinederc。

其中，[周期]，day为日数据，week周数据，month月数据。

例如，http://img1.money.126.net/data/hs/kline/day/times/1399001.json，获取深证成指所有时间节点数据。

**方法5：http://quotes.money.163.com/cjmx/[今年年份]/[日期]/[股票代码].xls**

返回结果：获取历史成交明细；XLS文件。

注意，只能获取5日内的数据，再之前的数据不会存在。

注意，该方法为网易公开获取数据方法，推荐使用。

例如，http://quotes.money.163.com/cjmx/2015/20150611/0601857.xls，获取0601857股票的2015年6月11日历史成交明细XLS文件。

**方法6：http://quotes.money.163.com/service/chddata.html?code=[股票代码]&start=[开始日期]&end=[结束日期]&fields=[自定义列]**

返回结果：历史股价及相关情况；CSV文件。

注意，该方法为网易公开方法，推荐使用。

其中，自定义列可定义TCLOSE收盘价 ;HIGH最高价;LOW最低价;TOPEN开盘价;LCLOSE前收盘价;CHG涨跌额;PCHG涨跌幅;TURNOVER换手率;VOTURNOVER成交量;VATURNOVER成交金额;TCAP总市值;MCAP流通市值这些值。

例如，http://quotes.money.163.com/service/chddata.html?code=0601857&start=20071105&end=20150618&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP，获取0601857从2007-11-05到2015-06-18区间的数据。



## **参考文献**

http://www.jarloo.com/yahoo_finance/

http://blog.sina.com.cn/s/blog_54fae2350101c7ye.html

http://blog.sina.com.cn/s/blog_7ed3ed3d0102v5y7.html

http://blog.sina.com.cn/s/articlelist_2127818045_10_1.html

http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx

http://blog.sina.com.cn/s/blog_7ed3ed3d010146ti.html

http://www.cnblogs.com/me115/archive/2011/05/09/2040826.html