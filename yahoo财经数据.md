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

我致力于找到一个.NET类来从Yahoo Finance获得有效的令牌（cookie和crumb）

有关从新的Yahoo Finance获取历史数据的完整API库，您可以访问Github中的[YahooFinanceAPI](https://github.com/dennislwy/YahooFinanceAPI)

这是抓住cookie和面包屑的类

**令牌.cs**

```
使用系统；使用System.Diagnostics；使用System.Net；使用System.IO；使用System.Text.RegularExpressions；命名空间YahooFinanceAPI {
    /// <摘要>
    ///用于从Yahoo Finance获取令牌（cookie和面包屑）的类
    ///版权所有Dennis Lee
    /// 2017年5月19日
    /// 
    /// </ summary>
    公共类令牌
    {
        公共静态字符串Cookie {get; 组; }
        公共静态字符串Crumb {get; 组; }
        私有静态正则表达式regex_crumb;
        /// <摘要>
        ///刷新cookie和面包屑值Yahoo Fianance
        /// </ summary>
        /// <param name =“ symbol”>股票报价器符号</ param>
        /// <returns> </ returns>
        公共静态布尔值刷新（字符串符号=“ SPY”）
        {
            尝试
            {
                Token.Cookie =“”;
                Token.Crumb =“”;
                字符串url_scrape =“ https://finance.yahoo.com/quote/{0}?p={0}”“;
                // url_scrape =“ https://finance.yahoo.com/quote/{0}/history”
                字符串url = string.Format（url_scrape，symbol）;
                HttpWebRequest request =（HttpWebRequest）HttpWebRequest.Create（url）;
                request.CookieContainer = new CookieContainer（）;
                request.Method =“ GET”;
                使用（HttpWebResponse response =（HttpWebResponse）request.GetResponse（））
                {
                    字符串cookie = response.GetResponseHeader（“ Set-Cookie”）。Split（';'）[0];
                    字符串html =“”;
                    使用（流stream = response.GetResponseStream（））
                    {
                        html = new StreamReader（stream）.ReadToEnd（）;
                    }
                    如果（html.Length <5000）
                        返回false；
                    字符串屑= getCrumb（html）;
                    html =“”;
                    如果（碎屑！= null）
                    {
                        Token.Cookie = Cookie；
                        令牌。面包屑=面包屑;
                        Debug.Print（“ Crumb：'{0}'，Cookie：'{1}'”，crumb，cookie）;
                        返回true；
                    }
                }
            }
            抓住（前例外）
            {
                Debug.Print（ex.Message）;
            }
            返回false；
        }
        /// <摘要>
        ///从HTML获取面包屑值
        /// </ summary>
        /// <param name =“ html”> HTML代码</ param>
        /// <returns> </ returns>
        私有静态字符串getCrumb（string html）
        {
            字符串屑= null;
            尝试
            {
                //在首次使用时初始化
                如果（regex_crumb == null）
                    regex_crumb = new Regex（“ CrumbStore \”：{\“ crumb \”：\“（？<crumb>。+？）\”}“， 
                RegexOptions.CultureInvariant | RegexOptions.Compiled，TimeSpan.FromSeconds（5））;
                MatchCollection matchs = regex_crumb.Matches（html）;
                如果（matches.Count> 0）
                {
                    crumb = matchs [0] .Groups [“ crumb”]。Value;
                }
                其他
                {
                    Debug.Print（“ Regex no match”）;
                }
                //防止正则表达式内存泄漏
                匹配= null;
            }
            抓住（前例外）
            {
                Debug.Print（ex.Message）;
            }
            GC.Collect（）;
            碎屑
        }
    }}
```

积分@ Ed0906修改crumb正则表达式模式到`Regex("CrumbStore\":{\"crumb\":\"(?<crumb>.+?)\"}"`