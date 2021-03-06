# tcp/ip

計算機網絡，根據其規模可分為WAN（Wide Area Network，广域网）（指覆蓋多個遠距離區域的遠程網絡。比廣域網再小一級的、連接整個城市的網絡叫城域网（MAN，Metropolitan Area Network）。）和LAN（Local Area Network，局域网）（指一個樓層、一棟樓或一個校園等相對較小的區域內的網絡。）。



![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00003.jpeg)

圖1.1　以獨立模式使用計算機

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00004.jpeg)

圖1.2　以網絡互連方式使用計算機

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00005.jpeg)

​												LAN

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00006.jpeg)

圖1.4　WAN

## 1.2计算机与网络发展的7个阶段

#### 1.2.1 批处理

為了能讓更多的人使用計算機，出現了批處理（Batch Processing）系統。所謂批處理，是指事先將用戶程序和數據裝入卡帶或磁帶，並由計算機按照一定的順序讀取，使用戶所要執行的這些程序和數據能夠一併批量得到處理的方式。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00007.jpeg)

圖1.5　批處理

### 1.2.2 分时系统

繼批處理系統之後，20世紀60年代出現了分時系統（TSS（Time Sharing System））。它是指多個終端（由鍵盤、顯示器等輸入輸出設備組成。最初還包括打字機。）與同一個計算機連接，允許多個用戶同時使用一台計算機的系統。當時計算機造價非常昂貴，一人一台專有計算機的費用對一般人來說可望不可即。然而分時系統的產生則實現了「一人一機」的目的，讓用戶感覺就好像「完全是自己在使用一台計算機一樣」。這也體現了分時系統的一個重要特性——獨佔性

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00008.jpeg)

圖1.6　分時系統

### 1.2.3 计算机之间的通信

如圖1.7可見在分時系統中，計算機與每個終端之間用通信線路連接，這並不意味著計算機與計算機之間也已相互連接。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00009.jpeg)

圖1.7　計算機之間的通信

### 1.2.4 计算机网络的产生

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00010.jpeg)

圖1.8　計算機網絡（20世紀80年代）

20世紀70年代初期，人們開始實驗基於分組交換技術的計算機網絡，並著手研究不同廠商的計算機之間相互通信的技術。到了80年代，一種能夠互連多種計算機的網絡隨之誕生。它能夠讓各式各樣的計算機相互連接，從大型的超級計算機或主機到小型的個人電腦

計算機的發展與普及使人們對網絡不再陌生。其中窗口系統（在計算機中可以打開多個圖形窗口進行處理的系統。代表產品有常用於UNIX上的X Window System以及微軟公司的Windows、蘋果公司的Mac OS X。這些系統允許將多個程序分配在多個窗口中運行，還可以依次進行執行切換。）的發明，更是拉近了人們與網絡之間的距離，使用戶更加體會到了網絡的便捷之處。有了窗口系統，用戶不僅可以同時執行多個程序，還能在這些程序之間自由地切換作業。例如，在工作站上創建一個文檔的同時，可以登錄到主機執行其他程序，也可以從數據庫服務器下載必要的數據，還可以通過電子郵件聯繫朋友。隨著窗口系統與網絡的緊密結合，我們已經可以在自己的電腦上自由地進行網上衝浪，享受網上的豐富資源了。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00011.jpeg)

圖1.9　窗口系統的產生與計算機網絡

### 1.2.5 互联网的普及

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00012.jpeg)

圖1.10　公司或家庭接入互聯網

### 1.2.6　以互聯網技術為中心的時代

互聯網的普及和發展著實對通信領域產生了巨大的影響。

許多發展道路各不相同的網絡技術也都正在向互聯網靠攏。例如，曾經一直作為通信基礎設施、支撐通信網絡的電話網，隨著互聯網的快速發展，其地位也隨著時間的推移為IP（Internet Protocol）網所取代，而IP網本身就是互聯網技術的產物。通過IP網，人們不僅可以實現電話通信、電視播放，還能實現計算機之間的通信，建立互聯網。並且，能夠聯網的設備也不僅限於單純的計算機，而是擴展到了手機、家用電器、遊戲機等許多其他產品。或許在未來，可能還會增加更多各式各樣的現在無法想像的設備。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00013.jpeg)

圖1.11　通過IP協議實現通信、播放的統一

### 1.2.7　從「單純建立連接」到「安全建立連接」

互聯網讓世界各地的人們通過計算機跨越國界自由地連接在了一起。通過互聯網人們可以搜索信息、溝通交流、共享信息、查看新聞報道以及實現遠程控制設備。然而，這麼便利的功能，對於20年前的人們來說卻是望塵莫及。互聯網正呈現給現代人一個高度便捷的信息網絡環境。因此，它也正成為一個國家社會基礎設施建設中最基本的要素之一。

正如事物具有兩面性，互聯網的便捷性也給人們的生活帶來了負面問題。計算機病毒的侵害、信息洩露、網絡欺詐等利用互聯網的犯罪行為日益增多。在現實當中，人們可以通過遠離險境避開一些危險，然而對於連接到互聯網的計算機而言，即使是在辦公室或在自己的家裡也有可能會受到網絡所帶來的諸多侵害。此外，由於設備故障導致無法聯網可能會直接影響公司的業務開展或個人的日常生活。這些負面影響所帶來的巨大損失也不容忽視。

在互聯網普及的初期，人們更關注單純的連接性，以不受任何限制地建立互聯網連接為最終目的。然而現在，人們已不再滿足於「單純建立連接」，而是更為追求「安全建立連接」的目標。

公司和社會團體在建立互聯網連接前，應理解通信網絡的機制、充分考慮聯網後的日常運維流程以及基本的「自我防衛」手段。這些已經成為安全生產不可或缺的組成部分。

表1.1　計算機使用模式的演變

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00014.jpeg)

### 1.2.8　手握金剛鑽的TCP/IP

如前面所介紹，互聯網是由許多獨立發展的網絡通信技術融合而成。能夠使它們之間不斷融合併實現統一的正是TCP/IP技術。那麼TCP/IP的機制究竟又是如何呢？

TCP/IP是通信協議的統稱。在學習下一章TCP/IP核心機制之前，有必要先理清「協議」的概念。

**■ 連接人與人的計算機網絡**

計算機網絡最初的目的是連接一個個獨立的計算機，使它們組成一個更強有力的計算環境。簡而言之，就是為了提高生產力。從批處理時代到計算機網絡時代，毋庸置疑，都體現了這一目的。然而，現在卻似乎有了微妙的變化。

現代計算機網絡的首要目的之一，可以說是連接人與人。置身於世界各地的人們可以通過網絡建立聯繫、相互溝通、交流思想。然而這些在計算機網絡初期是無法實現的。這種連接人與人的計算機網絡，已經逐漸給人們的日常生活、學校教育、科學研究、公司發展帶來了巨大的變革。

## 1.3 协议

### 1.3.1　隨處可見的協議

在計算機網絡與信息通信領域裡，人們經常提及「協議」一詞。互聯網中常用的具有代表性的協議有IP、TCP、HTTP等。而LAN（局域網）中常用的協議有IPX/SPX（Novell公司開發的NetWare系統的協議。）等。

「計算機網絡體系結構」將這些網絡協議進行了系統的歸納。TCP/IP就是IP、TCP、HTTP等協議的集合。現在，很多設備都支持TCP/IP。除此之外，還有很多其他類型的網絡體系結構。例如，Novell公司的IPX/SPX、蘋果公司的AppleTalk（僅限蘋果公司計算機使用）、IBM公司開發的用於構建大規模網絡的SNA（System Network Architecture）以及前DEC公司（1998年被收購。）開發的DECnet等。

表1.2　各種網絡體系結構及其協議

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00015.jpeg)

▼ Xerox Network Services

### 1.3.2　協議的必要性

簡單來說，協議就是計算機與計算機之間通過網絡實現通信時事先達成的一種「約定」。這種「約定」使那些由不同廠商的設備、不同的CPU以及不同的操作系統組成的計算機之間，只要遵循相同的協議就能夠實現通信。反之，如果所使用的協議不同，就無法實現通信。這就好比兩個人使用不同國家的語言說話，怎麼也無法相互理解。協議可以分為很多種，每一種協議都明確地界定了它的行為規範。兩台計算機之間必須能夠支持相同的協議，並遵循相同協議進行處理，這樣才能實現相互通信。

**■ CPU与OS**

CPU（Central Processing Unit）譯作中央處理器。它如同一台計算機的「心臟」，每個程序實際上是由它調度執行的。CPU的性能很大程度上也決定著一台計算機的處理性能。因此人們常說計算機的發展史實際上是CPU的發展史。

目前人們常用的CPU有Intel Core、Intel Atom以及ARM Cortex等產品。

OS（Operating System）譯作操作系統，是一種基礎軟件。它集合了CPU管理、內存管理、計算機外圍設備管理以及程序運行管理等重要功能。本書所要介紹的TCP或IP協議的處理，很多情況下其實已經內嵌到具體的操作系統中了。如今在個人電腦中普遍使用的操作系統有UNIX、Windows、Mac OS X、Linux等。

一台計算機中可運行的指令，因其CPU、操作系統的不同而有所差異。因此，如果將針對某些特定的CPU或操作系統設計的程序直接複製到具有其他類型CPU或操作系統的計算機中，就不一定能夠直接運行。計算機中存儲的數據也因CPU和操作系統的差異而有所不同。因此，若在CPU和操作系統不同的計算機之間實現通信，則需要一個各方支持的協議，並遵循這個協議進行數據讀取。

此外，一個CPU通常在同一時間只能運行一個程序。為了讓多個程序同時運行，操作系統採用CPU時間片輪轉機制，在多個程序之間進行切換，合理調度。這種方式叫做多任務調度。前面1.2.2節中提到的分時系統的實現，實際上就是採用了這種方式。

### 1.3.3　協議如同人與人的對話

在此舉一個簡單的例子。有三個人A、B、C。A只會說漢語、B只會說英語、而C既會說漢語又會說英語。現在A與B要聊天，他們之間該如何溝通呢？若A與C要聊天，又會怎樣？這時如果我們：

- 將漢語和英語當作「協議」
- 將聊天當作「通信」
- 將說話的內容當作「數據」

那麼A與B之間由於各持一種語言，恐怕說多久也無法交流。因為他們之間的談話所用的協議（語言）不同，雙方都無法將數據（所說的話）傳遞給對方（若兩人之間有個同聲翻譯，就能夠順利溝通了。在網絡環境中，1.9.7節所要介紹的網關就起著這種翻譯作用。）。

接下來，我們分析A與C之間聊天的情況。兩人都用漢語這個「協議」就能理解對方所要表達的具體含義了。也就是說A與C為了順利溝通，採用同一種協議，使得他們之間能夠傳遞所期望的數據（想要說給對方的話）。

如此看來，協議如同人們平常說話所用的語言。雖然語言是人類才具有的特性，但計算機與計算機之間通過網絡進行通信時，也可以認為是依據類似於人類「語言」實現了相互通信（與之相似，我們在日常生活中理所當然的一些行為，很多情況下都與「協議」這一概念不謀而合。）。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00016.jpeg)

圖1.12　協議如同人與人的對話

### 1.3.4　計算機中的協議

人類具有掌握知識的能力，對所學知識也有一定的應用能力和理解能力。因此在某種程度上，人與人的溝通並不受限於太多規則。即使有任何規則之類的東西，人們也可以通過自己的應變能力很自然地去適應規則。

然而這一切在計算機通信當中，顯然無從實現。因為計算機的智能水平還沒有達到人類的高度。其實，計算機從物理連接層面到應用程序的軟件層面，各個組件都必須嚴格遵循著事先達成的約定才能實現真正的通信。此外，每個計算機還必須裝有實現通信最基本功能的程序。如果將前面例子中提到的A、B與C替換到計算機中，就不難理解為什麼需要明確定義協議，為什麼要遵循既定的協議來設計軟件和製造計算機硬件了。

人們平常說話時根本不需要特別注意就能順其自然地吐字、發音。並且在很多場合，人類能夠根據對方的語義、聲音或表情，合理地調整自己的表達方式和所要傳達的內容，從而避免給對方造成誤解。甚至有時在談話過程中如果不小心漏掉幾個詞，也能從談話的語境和上下文中猜出對方所要表達的大體意思，不至於影響自己的理解。然而計算機做不到這一點。因此，在設計計算機程序與硬件時，要充分考慮通信過程中可能會遇到的各種異常以及對異常的處理。在實際遇到問題時，正在通信的計算機之間也必須具備相應的設備和程序以應對異常。

在計算機通信中，事先達成一個詳細的約定，並遵循這一約定進行處理尤為重要。這種約定其實就是「協議」。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00017.jpeg)

圖1.13　計算機通信協議

### 1.3.5　分組交換協議

分組交換是指將大數據分割為一個個叫做包（Packet）的較小單位進行傳輸的方法。這裡所說的包，如同我們平常在郵局裡見到的郵包。分組交換就是將大數據分裝為一個個這樣的郵包交給對方。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00018.jpeg)

圖1.14　分組通信

當人們郵寄包裹時，通常會填寫一個寄件單貼到包裹上再交給郵局。寄件單上一般會有寄件人和收件人的詳細地址。類似地，計算機通信也會在每一個分組中附加上源主機地址和目標主機地址送給通信線路。這些發送端地址、接收端地址以及分組序號寫入的部分稱為「報文首部」。

一個較大的數據被分為多個分組時，為了標明是原始數據中的哪一部分，就有必要將分組的序號寫入包中。接收端會根據這個序號，再將每個分組按照序號重新裝配為原始數據。

通信協議中，通常會規定報文首部應該寫入哪些信息、應該如何處理這些信息。相互通信的每一台計算機則根據協議構造報文首部、讀取首部內容等。為了雙方能正確通信，分組的發送方和接收方有必要對報文首部和內容保持一致的定義和解釋。

那麼，通信協議到底由誰來規定呢？為了能夠讓不同廠商生產的計算機相互通信，有這麼一個組織，它制定通信協議的規範，定義國際通用的標準。在下一節，我們將詳細說明協議的標準化過程。

## 1.4　協議由誰規定

### 1.4.1　計算機通信的誕生及其標準化

在計算機通信誕生之初，系統化與標準化並未得到足夠的重視。每家計算機廠商都出產各自的網絡產品來實現計算機通信。對於協議的系統化、分層化等事宜沒有特別強烈的意識。

1974年，IBM公司發佈了SNA，將本公司的計算機通信技術作為系統化網絡體系結構公之於眾。從此，計算機廠商也紛紛發佈各自的網絡體系結構，引發了眾多協議的系統化進程。然而，各家廠商的各種網絡體系結構、各種協議之間並不相互兼容。即使是從物理層面上連接了兩台異構的計算機，由於它們之間採用的網絡體系結構不同，支持的協議不同，仍然無法實現正常的通信。

這對用戶來說極其不便。因為這意味著起初採用了哪個廠商的計算機網絡產品就只能一直使用同一廠商的產品。若相應的廠商破產或產品超過服務期限，就得將整套網絡設備全部換掉。此外，因為不同部門之間使用的網絡產品互不相同，所以就算將它們從物理上相互連接起來了也無法實現通信，這種情況亦不在少數。靈活性和可擴展性的缺乏使得當時的用戶對計算機通信難以應用自如。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00019.jpeg)

圖1.15　協議中的方言與普通話

隨著計算機重要性的不斷提高，很多公司逐漸意識到兼容性的重要意義。人們開始著手研究使不同廠商生產的異構機型也能夠互相通信的技術。這促進了網絡的開放性和多供性。

### 1.4.2　協議的標準化

為了解決上述問題，ISO（International Organization for Standards，國際標準化組織。）制定了一個國際標準OSI（Open Systems Interconnection，開放式通信系統互聯參考模型。），對通信系統進行了標準化。現在，OSI所定義的協議雖然並沒有得到普及，但是在OSI協議設計之初作為其指導方針的OSI參考模型卻常被用於網絡協議的制定當中。

本書將要說明的TCP/IP並非ISO所制定的某種國際標準。而是由IETF（Internet Engineering Task Force）所建議的、致力於推進其標準化作業的一種協議。在當時，大學等研究機構和計算機行業作為中心力量，推動了TCP/IP的標準化進程。TCP/IP作為互聯網之上的一種標準，也作為業界標準（非國家或國際機構等公共機構所制定的標準，但屬於業界公認的標準。），儼然已成為全世界所廣泛應用的通信協議。那些支持互聯網的設備及軟件，也正著力遵循由IETF標準化的TCP/IP協議。

協議得以標準化也使所有遵循標準協議的設備不再因計算機硬件或操作系統的差異而無法通信。因此，協議的標準化也推動了計算機網絡的普及。

**■ 標準化**

所謂標準化是指使不同廠商所生產的異構產品之間具有兼容性、便於使用的規範化過程。

除計算機通信領域之外，「標準」一詞在日常用品如鉛筆、廁紙、電源插座、音頻、錄音帶等製造行業也屢見不鮮。如果這些產品的大小、形狀總是各不相同，那將會給消費者帶來巨大的麻煩。

標準化組織大致分為三類：國際級標準化機構，國家級標準化機構以及民間團體。目前國際級標準化機構有ISO、ITU-T（International Telecommunication Union Telecommunica-tion Standardization Sector。制定遠程通信相關國際規範的委員會。是ITU（International Telecommunication Union：國際電信聯盟）旗下的一個遠程通信標準化組。前身是國際電報電話咨詢委員會（CCITT：International Telegraph and Telephone Consultative Committee）。）等，而國家級標準化機構有日本的JISC（制定了日本JIS）和美國的ANSI（American National Stand-ards Institute。美國國家標準學會，屬於美國國內的標準化組織。）。民間團體則包括促進互聯網協議標準化的IETF等組織。

在現實世界裡，有很多優秀的技術，由於其開發公司沒有公開相應的開發規範導致這些技術沒有得到廣泛的普及。如果企業能夠將自己的開發規範公之於眾，讓更多業界同行及時使用並成為行業標準，那麼一定會有更多更好的產品可以存活下來供我們使用。

從某種程度上說，標準化是對世界具有極其重要影響的一項工作。

## 1.5　協議分層與OSI參考模型

### 1.5.1　協議的分層

ISO在制定標準化OSI之前，對網絡體系結構相關的問題進行了充分的討論，最終提出了作為通信協議設計指標的OSI參考模型。這一模型將通信協議中必要的功能分成了7層。通過這些分層，使得那些比較複雜的網絡協議更加簡單化。

在這一模型中，每個分層都接收由它下一層所提供的特定服務，並且負責為自己的上一層提供特定的服務。上下層之間進行交互時所遵循的約定叫做「接口」。同一層之間的交互所遵循的約定叫做「協議」。

協議分層就如同計算機軟件中的模塊化開發。OSI參考模型的建議是比較理想化的。它希望實現從第一層到第七層的所有模塊，並將它們組合起來實現網絡通信。分層可以將每個分層獨立使用，即使系統中某些分層發生變化，也不會波及整個系統。因此，可以構造一個擴展性和靈活性都較強的系統。此外，通過分層能夠細分通信功能，更易於單獨實現每個分層的協議，並界定各個分層的具體責任和義務。這些都屬於分層的優點。

而分層的劣勢，可能就在於過分模塊化、使處理變得更加沉重以及每個模塊都不得不實現相似的處理邏輯等問題。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00020.jpeg)

### 1.5.2　通過對話理解分層

關於協議的分層，我們再以A與C的對話為例簡單說明一下。在此，我們只考慮語言層和通信設備層這兩個分層的情況。

首先，以電話聊天為例，圖1.17上半部分中的A與C兩個人正在通過電話（通信設備）用漢語（語言協議）聊天。我們詳細分析一下這張圖。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00021.jpeg)

圖1.17　語言層與設備層兩層模型

表面上看A跟C是在用漢語直接對話，但實際上A與C都是在通過電話機的聽筒聽取聲音，都在對著麥克風說話。想像一下如果有一個素未見過電話機的人見到這

個場景會怎麼想？恐怕他一定會以為A和C在跟電話機聊天吧。

其實在這個圖中，他們所用的語言協議作為麥克風的音頻輸入，在通信設備層被轉換為電波信號傳送出去了。傳送到對方的電話機後，又被通信設備層轉換為音頻輸出，傳遞給了對方。因此，A與C其實是利用電話機之間通過音頻轉化聲音的接口實現了對話。

通常人們會覺得拿起電話與人通話，其實就好像是直接在跟對方對話，然而如果仔細分析，在整個過程中實際上是電話機在做中介，這是不可否認的。如果A的電話機所傳出的電子信號並未能轉換成與C的電話機相同頻率的聲音，那會如何？這就如同A的電話機與C的電話機的協議互不相同。C聽到聲音後可能會覺得自己不是在跟A而是在跟其他人說話。頻率若是相去甚遠，C更有可能會覺得自己聽到的不是漢語。

那麼如果我們假定語言層相同而改變了通信設備層，情況會如何？例如，將電話機改為無線電。通信設備層如果改用無線電，那麼就得學會使用無線電的方法。由於語言層仍然在使用漢語協議，因此使用者可以完全和以往打電話時一樣正常通話（上圖左下部分）。

那麼，如果通信設備層使用電話機，而語言層改為英語的話情況又會如何？很顯然，電話機本身不會受限於使用者使用的語言。因此，這種情況與使用漢語通話時完全一樣，依然可以實現通話（上圖右下部分）。

到此為止，讀者可能會覺得這些都是再簡單不過的、理所當然的事。在此僅舉出簡單的例子，權作對協議分層及其便利性的一個解釋，以加深對分層協議的理解。

### 1.5.3　OSI參考模型

前面只是將協議簡單地分為了兩層進行了舉例說明。然而，實際的分組通信協議會相當複雜。OSI參考模型將這樣一個複雜的協議整理並分為了易於理解的7個分層。

## 1.5　協議分層與OSI參考模型

### 1.5.1　協議的分層

ISO在制定標準化OSI之前，對網絡體系結構相關的問題進行了充分的討論，最終提出了作為通信協議設計指標的OSI參考模型。這一模型將通信協議中必要的功能分成了7層。通過這些分層，使得那些比較複雜的網絡協議更加簡單化。

在這一模型中，每個分層都接收由它下一層所提供的特定服務，並且負責為自己的上一層提供特定的服務。上下層之間進行交互時所遵循的約定叫做「接口」。同一層之間的交互所遵循的約定叫做「協議」。

協議分層就如同計算機軟件中的模塊化開發。OSI參考模型的建議是比較理想化的。它希望實現從第一層到第七層的所有模塊，並將它們組合起來實現網絡通信。分層可以將每個分層獨立使用，即使系統中某些分層發生變化，也不會波及整個系統。因此，可以構造一個擴展性和靈活性都較強的系統。此外，通過分層能夠細分通信功能，更易於單獨實現每個分層的協議，並界定各個分層的具體責任和義務。這些都屬於分層的優點。

而分層的劣勢，可能就在於過分模塊化、使處理變得更加沉重以及每個模塊都不得不實現相似的處理邏輯等問題。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00020.jpeg)

圖1.16　協議的分層

### 1.5.2　通過對話理解分層

關於協議的分層，我們再以A與C的對話為例簡單說明一下。在此，我們只考慮語言層和通信設備層這兩個分層的情況。

首先，以電話聊天為例，圖1.17上半部分中的A與C兩個人正在通過電話（通信設備）用漢語（語言協議）聊天。我們詳細分析一下這張圖。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00021.jpeg)

圖1.17　語言層與設備層兩層模型

表面上看A跟C是在用漢語直接對話，但實際上A與C都是在通過電話機的聽筒聽取聲音，都在對著麥克風說話。想像一下如果有一個素未見過電話機的人見到這個場景會怎麼想？恐怕他一定會以為A和C在跟電話機聊天吧。

其實在這個圖中，他們所用的語言協議作為麥克風的音頻輸入，在通信設備層被轉換為電波信號傳送出去了。傳送到對方的電話機後，又被通信設備層轉換為音頻輸出，傳遞給了對方。因此，A與C其實是利用電話機之間通過音頻轉化聲音的接口實現了對話。

通常人們會覺得拿起電話與人通話，其實就好像是直接在跟對方對話，然而如果仔細分析，在整個過程中實際上是電話機在做中介，這是不可否認的。如果A的電話機所傳出的電子信號並未能轉換成與C的電話機相同頻率的聲音，那會如何？這就如同A的電話機與C的電話機的協議互不相同。C聽到聲音後可能會覺得自己不是在跟A而是在跟其他人說話。頻率若是相去甚遠，C更有可能會覺得自己聽到的不是漢語。

那麼如果我們假定語言層相同而改變了通信設備層，情況會如何？例如，將電話機改為無線電。通信設備層如果改用無線電，那麼就得學會使用無線電的方法。由於語言層仍然在使用漢語協議，因此使用者可以完全和以往打電話時一樣正常通話（上圖左下部分）。

那麼，如果通信設備層使用電話機，而語言層改為英語的話情況又會如何？很顯然，電話機本身不會受限於使用者使用的語言。因此，這種情況與使用漢語通話時完全一樣，依然可以實現通話（上圖右下部分）。

到此為止，讀者可能會覺得這些都是再簡單不過的、理所當然的事。在此僅舉出簡單的例子，權作對協議分層及其便利性的一個解釋，以加深對分層協議的理解。

### 1.5.3　OSI參考模型

前面只是將協議簡單地分為了兩層進行了舉例說明。然而，實際的分組通信協議會相當複雜。OSI參考模型將這樣一個複雜的協議整理並分為了易於理解的7個分層。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00022.jpeg)

圖1.18　OSI參考模型與協議的含義

OSI參考模型對通信中必要的功能做了很好的歸納。網絡工程師在討論協議相關問題時也經常以OSI參考模型的分層為原型。對於計算機網絡的初學者，學習OSI參考模型可以說是通往成功的第一步。

不過，OSI參考模型終究是一個「模型」，它也只是對各層的作用做了一系列粗略的界定，並沒有對協議和接口進行詳細的定義。它對學習和設計協議只能起到一個引導的作用。因此，若想要瞭解協議的更多細節，還是有必要參考每個協議本身的具體規範。

許多通信協議，都對應了OSI參考模型7個分層中的某層。通過這一點，可以大致瞭解該協議在整個通信功能中的位置和作用。

雖然要仔細閱讀相應的規範說明書才能瞭解協議的具體內容，但是對於其大致的作用可以通過其所對應的OSI模型層來找到方向。這也是為什麼在學習每一種協議之前，首先要學習OSI模型。

**■ OSI協議與OSI參考模型**

本章所介紹的是OSI參考模型。然而人們也時常會聽到OSI協議這個詞。OSI協議是為了讓異構的計算機之間能夠相互通信的、由ISO和ITU-T推進其標準化的一種網絡體系結構。

OSI（參考模型）將通信功能劃分為7個分層，稱作OSI參考模型。OSI協議以OSI參考模型為基礎界定了每個階層的協議和每個階層之間接口相關的標準。遵循OSI協議的產品叫OSI產品，而它們所遵循的通信則被稱為OSI通信。由於「OSI參考模型」與「OSI協議」指代意義不同，請勿混淆。

本書，通過對照OSI參考模型中通信功能的分類和TCP/IP的功能，逐層深入展開每一個話題。雖然實際的TCP/IP分層模型與OSI還有這若干區別，借助OSI參考模型可以有助於加深對TCP/IP的理解。

### 1.5.4　OSI參考模型中各個分層的作用

在此，以圖1.19為例簡單說明OSI參考模型中各個分層的主要作用。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00023.jpeg)

圖1.19　OSI參考模型各層分工

▼ 互連的網絡終端，如計算機等設備。

## 1.5　協議分層與OSI參考模型

### 1.5.1　協議的分層

ISO在制定標準化OSI之前，對網絡體系結構相關的問題進行了充分的討論，最終提出了作為通信協議設計指標的OSI參考模型。這一模型將通信協議中必要的功能分成了7層。通過這些分層，使得那些比較複雜的網絡協議更加簡單化。

在這一模型中，每個分層都接收由它下一層所提供的特定服務，並且負責為自己的上一層提供特定的服務。上下層之間進行交互時所遵循的約定叫做「接口」。同一層之間的交互所遵循的約定叫做「協議」。

協議分層就如同計算機軟件中的模塊化開發。OSI參考模型的建議是比較理想化的。它希望實現從第一層到第七層的所有模塊，並將它們組合起來實現網絡通信。分層可以將每個分層獨立使用，即使系統中某些分層發生變化，也不會波及整個系統。因此，可以構造一個擴展性和靈活性都較強的系統。此外，通過分層能夠細分通信功能，更易於單獨實現每個分層的協議，並界定各個分層的具體責任和義務。這些都屬於分層的優點。

而分層的劣勢，可能就在於過分模塊化、使處理變得更加沉重以及每個模塊都不得不實現相似的處理邏輯等問題。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00020.jpeg)

圖1.16　協議的分層

### 1.5.2　通過對話理解分層

關於協議的分層，我們再以A與C的對話為例簡單說明一下。在此，我們只考慮語言層和通信設備層這兩個分層的情況。

首先，以電話聊天為例，圖1.17上半部分中的A與C兩個人正在通過電話（通信設備）用漢語（語言協議）聊天。我們詳細分析一下這張圖。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00021.jpeg)

圖1.17　語言層與設備層兩層模型

表面上看A跟C是在用漢語直接對話，但實際上A與C都是在通過電話機的聽筒聽取聲音，都在對著麥克風說話。想像一下如果有一個素未見過電話機的人見到這個場景會怎麼想？恐怕他一定會以為A和C在跟電話機聊天吧。

其實在這個圖中，他們所用的語言協議作為麥克風的音頻輸入，在通信設備層被轉換為電波信號傳送出去了。傳送到對方的電話機後，又被通信設備層轉換為音頻輸出，傳遞給了對方。因此，A與C其實是利用電話機之間通過音頻轉化聲音的接口實現了對話。

通常人們會覺得拿起電話與人通話，其實就好像是直接在跟對方對話，然而如果仔細分析，在整個過程中實際上是電話機在做中介，這是不可否認的。如果A的電話機所傳出的電子信號並未能轉換成與C的電話機相同頻率的聲音，那會如何？這就如同A的電話機與C的電話機的協議互不相同。C聽到聲音後可能會覺得自己不是在跟A而是在跟其他人說話。頻率若是相去甚遠，C更有可能會覺得自己聽到的不是漢語。

那麼如果我們假定語言層相同而改變了通信設備層，情況會如何？例如，將電話機改為無線電。通信設備層如果改用無線電，那麼就得學會使用無線電的方法。由於語言層仍然在使用漢語協議，因此使用者可以完全和以往打電話時一樣正常通話（上圖左下部分）。

那麼，如果通信設備層使用電話機，而語言層改為英語的話情況又會如何？很顯然，電話機本身不會受限於使用者使用的語言。因此，這種情況與使用漢語通話時完全一樣，依然可以實現通話（上圖右下部分）。

到此為止，讀者可能會覺得這些都是再簡單不過的、理所當然的事。在此僅舉出簡單的例子，權作對協議分層及其便利性的一個解釋，以加深對分層協議的理解。

### 1.5.3　OSI參考模型

前面只是將協議簡單地分為了兩層進行了舉例說明。然而，實際的分組通信協議會相當複雜。OSI參考模型將這樣一個複雜的協議整理並分為了易於理解的7個分層。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00022.jpeg)

圖1.18　OSI參考模型與協議的含義

OSI參考模型對通信中必要的功能做了很好的歸納。網絡工程師在討論協議相關問題時也經常以OSI參考模型的分層為原型。對於計算機網絡的初學者，學習OSI參考模型可以說是通往成功的第一步。

不過，OSI參考模型終究是一個「模型」，它也只是對各層的作用做了一系列粗略的界定，並沒有對協議和接口進行詳細的定義。它對學習和設計協議只能起到一個引導的作用。因此，若想要瞭解協議的更多細節，還是有必要參考每個協議本身的具體規範。

許多通信協議，都對應了OSI參考模型7個分層中的某層。通過這一點，可以大致瞭解該協議在整個通信功能中的位置和作用。

雖然要仔細閱讀相應的規範說明書才能瞭解協議的具體內容，但是對於其大致的作用可以通過其所對應的OSI模型層來找到方向。這也是為什麼在學習每一種協議之前，首先要學習OSI模型。

**■ OSI協議與OSI參考模型**

本章所介紹的是OSI參考模型。然而人們也時常會聽到OSI協議這個詞。OSI協議是為了讓異構的計算機之間能夠相互通信的、由ISO和ITU-T推進其標準化的一種網絡體系結構。

OSI（參考模型）將通信功能劃分為7個分層，稱作OSI參考模型。OSI協議以OSI參考模型為基礎界定了每個階層的協議和每個階層之間接口相關的標準。遵循OSI協議的產品叫OSI產品，而它們所遵循的通信則被稱為OSI通信。由於「OSI參考模型」與「OSI協議」指代意義不同，請勿混淆。

本書，通過對照OSI參考模型中通信功能的分類和TCP/IP的功能，逐層深入展開每一個話題。雖然實際的TCP/IP分層模型與OSI還有這若干區別，借助OSI參考模型可以有助於加深對TCP/IP的理解。

### 1.5.4　OSI參考模型中各個分層的作用

在此，以圖1.19為例簡單說明OSI參考模型中各個分層的主要作用。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00023.jpeg)

圖1.19　OSI參考模型各層分工

▼ 互連的網絡終端，如計算機等設備。

**■ 應用層**

為應用程序提供服務並規定應用程序中通信相關的細節。包括文件傳輸、電子郵件、遠程登錄（虛擬終端）等協議。

**■ 表示層**

將應用處理的信息轉換為適合網絡傳輸的格式，或將來自下一層的數據轉換為上層能夠處理的格式。因此它主要負責數據格式的轉換。

具體來說，就是將設備固有的數據格式轉換為網絡標準傳輸格式。不同設備對同一比特流解釋的結果可能會不同。因此，使它們保持一致是這一層的主要作用。

**■ 會話層**

負責建立和斷開通信連接（數據流動的邏輯通路），以及數據的分割等數據傳輸相關的管理。

**■ 傳輸層**

起著可靠傳輸的作用。只在通信雙方節點上進行處理，而無需在路由器上處理。

**■ 網絡層**

將數據傳輸到目標地址。目標地址可以是多個網絡通過路由器連接而成的某一個地址。因此這一層主要負責尋址和路由選擇。

**■ 數據鏈路層**

負責物理層面上互連的、節點之間的通信傳輸。例如與1個以太網相連的2個節點之間的通信。

將0、1序列劃分為具有意義的數據幀傳送給對端（數據幀的生成與接收）。

**■ 物理層**

負責0、1比特流（0、1序列）與電壓的高低、光的閃滅之間的互換。

## 1.6　OSI參考模型通信處理舉例

下面舉例說明7層網絡模型的功能。假設使用主機（這裡所指的主機是指連接到網絡上的計算機。按照OSI的慣例，進行通信的計算機稱為節點。然而在TCP/IP中則被叫做主機。本書以TCP/IP為主，因此凡是在進行通信的計算機，多數稱為主機。也可參考4.1節。）A的用戶A要給使用主機B的用戶B發送一封電子郵件。

不過，嚴格來講OSI與互聯網的電子郵件的實際運行機制並非圖例所示那麼簡單。此例只是為了便於讀者理解OSI參考模型而設計的。

### 1.6.1　7層通信

在7層OSI模型中，如何模塊化通信傳輸？

分析方法可以借鑒圖1.17語言與電話機組成的2層模型。發送方從第7層、第6層到第1層由上至下按照順序傳輸數據，而接收端則從第1層、第2層到第7層由下至上向每個上一級分層傳輸數據。每個分層上，在處理由上一層傳過來的數據時可以附上當前分層的協議所必須的「首部」信息。然後接收端對收到的數據進行數據「首部」與「內容」的分離，再轉發給上一分層，並最終將發送端的數據恢復為原狀。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00024.jpeg)

圖1.20　通信與7個分層

### 1.6.2　會話層以上的處理

假定用戶A要給用戶B發送一封內容為「早上好」郵件。網絡究竟會進行哪些處理呢？我們由上至下進行分析。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00025.jpeg)

圖1.21　以電子郵件為例

**■ 應用層**

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00026.jpeg)

圖1.22　應用層的工作

用戶A在主機A上新建一封電子郵件，指定收件人為B，並輸入郵件內容為「早上好」。

收發郵件的這款軟件從功能上可以分為兩大類：一部分是與通信相關的，另一部分是與通信無關的。例如用戶A從鍵盤輸入「早上好」的這一部分就屬於與通信無關的功能，而將「早上好」的內容發送給收件人B則是其與通信相關的功能。因此，此處的「輸入電子郵件內容後發送給目標地址」也就相當於應用層。

從用戶輸入完所要發送的內容並點擊「發送」按鈕的那一刻開始，就進入了應用層協議的處理。該協議會在所要傳送數據的前端附加一個首部（標籤）信息。該首部標明了郵件內容為「早上好」和收件人為「B」。這一附有首部信息的數據傳送給主機B以後由該主機上的收發郵件軟件通過「收信」功能獲取內容。主機B上的應用收到由主機A發送過來的數據後，分析其數據首部與數據正文，並將郵件保存到硬盤或是其他非易失性存儲器（數據不會因為斷電而丟失的一種存儲設備[[3\]](part0008_split_009.html#note_3)）以備進行相應的處理。如果主機B上收件人的郵箱空間已滿無法接收新的郵件，則會返回一個錯誤給發送

方。對這類異常的處理也正屬於應用層需要解決的問題。

主機A與主機B通過它們各自應用層之間的通信，最終實現郵件的存儲。

**■ 表示層**

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00027.jpeg)

圖1.23　表示層的工作

表示層的「表示」有「表現」、「演示」的意思，因此更關注數據的具體表現形式（最有名的就是每款計算機對數據在內存中相異的分配方式。最典型的是大實體和小實體。）。此外，所使用的應用軟件本身的不同也會導致數據的表現形式截然不同。例如有的字處理軟件創建的文件只能由該字處理器廠商所提供的特定版本的軟件才來打開讀取。

那麼，電子郵件中如果遇到此類問題該如何解決呢？如果用戶A與用戶B所使用的郵件客戶端軟件完全一致，就能夠順利收取和閱讀郵件，不會遇到類似的問題。但是這在現實生活當中是不大可能的。讓所有用戶千篇一律地使用同一款客戶端軟件對使用者來說也是極不方便的一件事情（現在，除了個人電腦，還有其他設備如智能手機也都能夠連接到網絡。如何讓它們之間能夠相互讀取通信數據已變得越來越重要。）。

解決這類問題有以下幾種方法。首先是利用表示層，將數據從「某個計算機特定的數據格式」轉換為「網絡通用的標準數據格式」後再發送出去。接收端主機收到數據以後將這些網絡標準格式的數據恢復為「該計算機特定的數據格式」，然後再進行相應處理。

在前面這個例子中，由於數據在前面這個例子中，由於數據被轉換為通用標準的格式後再進行處理，使得異構的機型之間也能保持數據的一致性。這也正是表示層的作用所在。即表示層是進行「統一的網絡數據格式」與「某一台計算機或某一款軟件特有的數據格式」之間相互轉換的分層。

此例中的「早上好」這段文字根據其編碼格式被轉換成為了「統一的網絡數據格式」。即便是一段簡單的文字流，也可以有眾多複雜的編碼格式。就拿日語文字來說，有EUC-JP、Shift_JIS、ISO-2022-JP、UTF-8以及UTF-16等很多編碼格式[[4\]](part0008_split_009.html#note_4)。如果未能按照特定格式編碼，那麼在接收端就是收到郵件也可能會是亂碼（在實際生活當中收發郵件成為亂碼的情況並不罕見。這通常都是由於在表示層未能按照預期的編碼格式運行或編碼格式設置有誤導致。）。

表示層與表示層之間為了識別編碼格式也會附加首部信息，從而將實際傳輸的數據轉交給下一層去處理。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00028.jpeg)

圖1.24　會話層工作

**■ 會話層**

下面，我們來分析在兩端主機的會話層之間是如何高效地進行數據交互、採用何種方法傳輸數據的。

假定用戶A新建了5封電子郵件準備發給用戶B。這5封郵件的發送順序可以有很多種。例如，可以每發一封郵件時建立一次連接（指通信連接。），隨後斷開連接。還可以一經建立好連接後就將5封郵件連續發送給對方。甚至可以同時建立好5個連接，將5封郵件同時發送給對方。決定採用何種連接方法是會話層的主要責任。

會話層也像應用層或表示層那樣，在其收到的數據前端附加首部或標籤信息後再轉發給下一層。而這些首部或標籤中記錄著數據傳送順序的信息。

### 1.6.3　傳輸層以下的處理

到此為止，我們通過例子說明了在應用層寫入的數據會經由表示層格式化編碼、再由會話層標記發送順序後才被發送出去的大致過程。然而，會話層只對何時建立連接、何時發送數據等問題進行管理，並不具有實際傳輸數據的功能。真正負責在網絡上傳輸具體數據的是會話層以下的「無名英雄」。

**■ 傳輸層**

主機A確保與主機B之間的通信並準備發送數據。這一過程叫做「建立連接」。有了這個通信連接就可以使主機A發送的電子郵件到達主機B中，並由主機B的郵件處理程序獲取最終數據。此外，當通信傳輸結束後，有必要將連接斷開。

如上，進行建立連接或斷開連接的處理（此處請注意，會話層負責決定建立連接和斷開連接的時機，而傳輸層進行實際的建立和斷開處理。），在兩個主機之間創建邏輯上的通信連接即是傳輸層的主要作用。此外，傳輸層為確保所傳輸的數據到達目標地址，會在通信兩端的計算機之間進行確認，如果數據沒有到達，它會負責進行重發。

例如，主機A將「早上好」這一數據發送給主機B。期間可能會因為某些原因導致數據被破壞，或由於發生某種網絡異常致使只有一部分數據到達目標地址。假設主機B只收到了「早上」這一部分數據，那麼它會在收到數據後將自己沒有收到「早上」之後那部分數據的事實告知主機A。主機A得知這個情況後就會將後面的「好」重發給主機B，並再次確認對端是否收到。

##  1.6　OSI參考模型通信處理舉例

下面舉例說明7層網絡模型的功能。假設使用主機（這裡所指的主機是指連接到網絡上的計算機。按照OSI的慣例，進行通信的計算機稱為節點。然而在TCP/IP中則被叫做主機。本書以TCP/IP為主，因此凡是在進行通信的計算機，多數稱為主機。也可參考4.1節。）A的用戶A要給使用主機B的用戶B發送一封電子郵件。

不過，嚴格來講OSI與互聯網的電子郵件的實際運行機制並非圖例所示那麼簡單。此例只是為了便於讀者理解OSI參考模型而設計的。

### 1.6.1　7層通信

在7層OSI模型中，如何模塊化通信傳輸？

分析方法可以借鑒圖1.17語言與電話機組成的2層模型。發送方從第7層、第6層到第1層由上至下按照順序傳輸數據，而接收端則從第1層、第2層到第7層由下至上向每個上一級分層傳輸數據。每個分層上，在處理由上一層傳過來的數據時可以附上當前分層的協議所必須的「首部」信息。然後接收端對收到的數據進行數據「首部」與「內容」的分離，再轉發給上一分層，並最終將發送端的數據恢復為原狀。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00024.jpeg)

圖1.20　通信與7個分層

### 1.6.2　會話層以上的處理

假定用戶A要給用戶B發送一封內容為「早上好」郵件。網絡究竟會進行哪些處理呢？我們由上至下進行分析。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00025.jpeg)

圖1.21　以電子郵件為例

**■ 應用層**

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00026.jpeg)

圖1.22　應用層的工作

用戶A在主機A上新建一封電子郵件，指定收件人為B，並輸入郵件內容為「早上好」。

收發郵件的這款軟件從功能上可以分為兩大類：一部分是與通信相關的，另一部分是與通信無關的。例如用戶A從鍵盤輸入「早上好」的這一部分就屬於與通信無關的功能，而將「早上好」的內容發送給收件人B則是其與通信相關的功能。因此，此處的「輸入電子郵件內容後發送給目標地址」也就相當於應用層。

從用戶輸入完所要發送的內容並點擊「發送」按鈕的那一刻開始，就進入了應用層協議的處理。該協議會在所要傳送數據的前端附加一個首部（標籤）信息。該首部標明了郵件內容為「早上好」和收件人為「B」。這一附有首部信息的數據傳送給主機B以後由該主機上的收發郵件軟件通過「收信」功能獲取內容。主機B上的應用收到由主機A發送過來的數據後，分析其數據首部與數據正文，並將郵件保存到硬盤或是其他非易失性存儲器（數據不會因為斷電而丟失的一種存儲設備[[3\]](part0008_split_009.html#note_3)）以備進行相應的處理。如果主機B上收件人的郵箱空間已滿無法接收新的郵件，則會返回一個錯誤給發送方。對這類異常的處理也正屬於應用層需要解決的問題。

主機A與主機B通過它們各自應用層之間的通信，最終實現郵件的存儲。

**■ 表示層**

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00027.jpeg)

圖1.23　表示層的工作

表示層的「表示」有「表現」、「演示」的意思，因此更關注數據的具體表現形式（最有名的就是每款計算機對數據在內存中相異的分配方式。最典型的是大實體和小實體。）。此外，所使用的應用軟件本身的不同也會導致數據的表現形式截然不同。例如有的字處理軟件創建的文件只能由該字處理器廠商所提供的特定版本的軟件才來打開讀取。

那麼，電子郵件中如果遇到此類問題該如何解決呢？如果用戶A與用戶B所使用的郵件客戶端軟件完全一致，就能夠順利收取和閱讀郵件，不會遇到類似的問題。但是這在現實生活當中是不大可能的。讓所有用戶千篇一律地使用同一款客戶端軟件對使用者來說也是極不方便的一件事情（現在，除了個人電腦，還有其他設備如智能手機也都能夠連接到網絡。如何讓它們之間能夠相互讀取通信數據已變得越來越重要。）。

解決這類問題有以下幾種方法。首先是利用表示層，將數據從「某個計算機特定的數據格式」轉換為「網絡通用的標準數據格式」後再發送出去。接收端主機收到數據以後將這些網絡標準格式的數據恢復為「該計算機特定的數據格式」，然後再進行相應處理。

在前面這個例子中，由於數據被轉換為通用標準的格式後再進行處理，使得異構的機型之間也能保持數據的一致性。這也正是表示層的作用所在。即表示層是進行「統一的網絡數據格式」與「某一台計算機或某一款軟件特有的數據格式」之間相互轉換的分層。

此例中的「早上好」這段文字根據其編碼格式被轉換成為了「統一的網絡數據格式」。即便是一段簡單的文字流，也可以有眾多複雜的編碼格式。就拿日語文字來說，有EUC-JP、Shift_JIS、ISO-2022-JP、UTF-8以及UTF-16等很多編碼格式[[4\]](part0008_split_009.html#note_4)。如果未能按照特定格式編碼，那麼在接收端就是收到郵件也可能會是亂碼（在實際生活當中收發郵件成為亂碼的情況並不罕見。這通常都是由於在表示層未能按照預期的編碼格式運行或編碼格式設置有誤導致。）。

表示層與表示層之間為了識別編碼格式也會附加首部信息，從而將實際傳輸的數據轉交給下一層去處理。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00028.jpeg)

圖1.24　會話層工作

**■ 會話層**

下面，我們來分析在兩端主機的會話層之間是如何高效地進行數據交互、採用何種方法傳輸數據的。

假定用戶A新建了5封電子郵件準備發給用戶B。這5封郵件的發送順序可以有很多種。例如，可以每發一封郵件時建立一次連接（指通信連接。），隨後斷開連接。還可以一經建立好連接後就將5封郵件連續發送給對方。甚至可以同時建立好5個連接，將5封郵件同時發送給對方。決定採用何種連接方法是會話層的主要責任。

會話層也像應用層或表示層那樣，在其收到的數據前端附加首部或標籤信息後再轉發給下一層。而這些首部或標籤中記錄著數據傳送順序的信息。

### 1.6.3　傳輸層以下的處理

到此為止，我們通過例子說明了在應用層寫入的數據會經由表示層格式化編碼、再由會話層標記發送順序後才被發送出去的大致過程。然而，會話層只對何時建立連接、何時發送數據等問題進行管理，並不具有實際傳輸數據的功能。真正負責在網絡上傳輸具體數據的是會話層以下的「無名英雄」。

**■ 傳輸層**

主機A確保與主機B之間的通信並準備發送數據。這一過程叫做「建立連接」。有了這個通信連接就可以使主機A發送的電子郵件到達主機B中，並由主機B的郵件處理程序獲取最終數據。此外，當通信傳輸結束後，有必要將連接斷開。

如上，進行建立連接或斷開連接的處理（此處請注意，會話層負責決定建立連接和斷開連接的時機，而傳輸層進行實際的建立和斷開處理。），在兩個主機之間創建邏輯上的通信連接即是傳輸層的主要作用。此外，傳輸層為確保所傳輸的數據到達目標地址，會在通信兩端的計算機之間進行確認，如果數據沒有到達，它會負責進行重發。

例如，主機A將「早上好」這一數據發送給主機B。期間可能會因為某些原因導致數據被破壞，或由於發生某種網絡異常致使只有一部分數據到達目標地址。假設主機B只收到了「早上」這一部分數據，那麼它會在收到數據後將自己沒有收到「早上」之後那部分數據的事實告知主機A。主機A得知這個情況後就會將後面的「好」重發給主機B，並再次確認對端是否收到。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00029.jpeg)

圖1.25　傳輸層的工作

這就好比人們日常會話中的確認語句：「對了，你剛才說什麼來著？」計算機通信協議其實並沒有想像中那麼晦澀難懂，其基本原理是與我們的日常生活緊密相連、大同小異的。

由此可見，保證數據傳輸的可靠性是傳輸層的一個重要作用。為了確保可靠性，在這一層也會為所要傳輸的數據附加首部以識別這一分層的數據。然而，實際上將數據傳輸給對端的處理是由網絡層來完成的。

**■ 網絡層**

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00030.jpeg)

圖1.26　網絡層的工作

網絡層的作用是在網絡與網絡相互連接的環境中，將數據從發送端主機發送到接收端主機。如圖1.27所示，兩端主機之間雖然有眾多數據鏈路，但能夠將數據從主機A送到主機B也都是網絡層的功勞。

##  1.6　OSI參考模型通信處理舉例

下面舉例說明7層網絡模型的功能。假設使用主機（這裡所指的主機是指連接到網絡上的計算機。按照OSI的慣例，進行通信的計算機稱為節點。然而在TCP/IP中則被叫做主機。本書以TCP/IP為主，因此凡是在進行通信的計算機，多數稱為主機。也可參考4.1節。）A的用戶A要給使用主機B的用戶B發送一封電子郵件。

不過，嚴格來講OSI與互聯網的電子郵件的實際運行機制並非圖例所示那麼簡單。此例只是為了便於讀者理解OSI參考模型而設計的。

### 1.6.1　7層通信

在7層OSI模型中，如何模塊化通信傳輸？

分析方法可以借鑒圖1.17語言與電話機組成的2層模型。發送方從第7層、第6層到第1層由上至下按照順序傳輸數據，而接收端則從第1層、第2層到第7層由下至上向每個上一級分層傳輸數據。每個分層上，在處理由上一層傳過來的數據時可以附上當前分層的協議所必須的「首部」信息。然後接收端對收到的數據進行數據「首部」與「內容」的分離，再轉發給上一分層，並最終將發送端的數據恢復為原狀。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00024.jpeg)

圖1.20　通信與7個分層

### 1.6.2　會話層以上的處理

假定用戶A要給用戶B發送一封內容為「早上好」郵件。網絡究竟會進行哪些處理呢？我們由上至下進行分析。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00025.jpeg)

圖1.21　以電子郵件為例

**■ 應用層**

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00026.jpeg)

圖1.22　應用層的工作

用戶A在主機A上新建一封電子郵件，指定收件人為B，並輸入郵件內容為「早上好」。

收發郵件的這款軟件從功能上可以分為兩大類：一部分是與通信相關的，另一部分是與通信無關的。例如用戶A從鍵盤輸入「早上好」的這一部分就屬於與通信無關的功能，而將「早上好」的內容發送給收件人B則是其與通信相關的功能。因此，此處的「輸入電子郵件內容後發送給目標地址」也就相當於應用層。

從用戶輸入完所要發送的內容並點擊「發送」按鈕的那一刻開始，就進入了應用層協議的處理。該協議會在所要傳送數據的前端附加一個首部（標籤）信息。該首部標明了郵件內容為「早上好」和收件人為「B」。這一附有首部信息的數據傳送給主機B以後由該主機上的收發郵件軟件通過「收信」功能獲取內容。主機B上的應用收到由主機A發送過來的數據後，分析其數據首部與數據正文，並將郵件保存到硬盤或是其他非易失性存儲器（數據不會因為斷電而丟失的一種存儲設備[[3\]](part0008_split_009.html#note_3)）以備進行相應的處理。如果主機B上收件人的郵箱空間已滿無法接收新的郵件，則會返回一個錯誤給發送方。對這類異常的處理也正屬於應用層需要解決的問題。

主機A與主機B通過它們各自應用層之間的通信，最終實現郵件的存儲。

**■ 表示層**

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00027.jpeg)

圖1.23　表示層的工作

表示層的「表示」有「表現」、「演示」的意思，因此更關注數據的具體表現形式（最有名的就是每款計算機對數據在內存中相異的分配方式。最典型的是大實體和小實體。）。此外，所使用的應用軟件本身的不同也會導致數據的表現形式截然不同。例如有的字處理軟件創建的文件只能由該字處理器廠商所提供的特定版本的軟件才來打開讀取。

那麼，電子郵件中如果遇到此類問題該如何解決呢？如果用戶A與用戶B所使用的郵件客戶端軟件完全一致，就能夠順利收取和閱讀郵件，不會遇到類似的問題。但是這在現實生活當中是不大可能的。讓所有用戶千篇一律地使用同一款客戶端軟件對使用者來說也是極不方便的一件事情（現在，除了個人電腦，還有其他設備如智能手機也都能夠連接到網絡。如何讓它們之間能夠相互讀取通信數據已變得越來越重要。）。

解決這類問題有以下幾種方法。首先是利用表示層，將數據從「某個計算機特定的數據格式」轉換為「網絡通用的標準數據格式」後再發送出去。接收端主機收到數據以後將這些網絡標準格式的數據恢復為「該計算機特定的數據格式」，然後再進行相應處理。

在前面這個例子中，由於數據被轉換為通用標準的格式後再進行處理，使得異構的機型之間也能保持數據的一致性。這也正是表示層的作用所在。即表示層是進行「統一的網絡數據格式」與「某一台計算機或某一款軟件特有的數據格式」之間相互轉換的分層。

此例中的「早上好」這段文字根據其編碼格式被轉換成為了「統一的網絡數據格式」。即便是一段簡單的文字流，也可以有眾多複雜的編碼格式。就拿日語文字來說，有EUC-JP、Shift_JIS、ISO-2022-JP、UTF-8以及UTF-16等很多編碼格式[[4\]](part0008_split_009.html#note_4)。如果未能按照特定格式編碼，那麼在接收端就是收到郵件也可能會是亂碼（在實際生活當中收發郵件成為亂碼的情況並不罕見。這通常都是由於在表示層未能按照預期的編碼格式運行或編碼格式設置有誤導致。）。

表示層與表示層之間為了識別編碼格式也會附加首部信息，從而將實際傳輸的數據轉交給下一層去處理。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00028.jpeg)

圖1.24　會話層工作

**■ 會話層**

下面，我們來分析在兩端主機的會話層之間是如何高效地進行數據交互、採用何種方法傳輸數據的。

假定用戶A新建了5封電子郵件準備發給用戶B。這5封郵件的發送順序可以有很多種。例如，可以每發一封郵件時建立一次連接（指通信連接。），隨後斷開連接。還可以一經建立好連接後就將5封郵件連續發送給對方。甚至可以同時建立好5個連接，將5封郵件同時發送給對方。決定採用何種連接方法是會話層的主要責任。

會話層也像應用層或表示層那樣，在其收到的數據前端附加首部或標籤信息後再轉發給下一層。而這些首部或標籤中記錄著數據傳送順序的信息。

### 1.6.3　傳輸層以下的處理

到此為止，我們通過例子說明了在應用層寫入的數據會經由表示層格式化編碼、再由會話層標記發送順序後才被發送出去的大致過程。然而，會話層只對何時建立連接、何時發送數據等問題進行管理，並不具有實際傳輸數據的功能。真正負責在網絡上傳輸具體數據的是會話層以下的「無名英雄」。

**■ 傳輸層**

主機A確保與主機B之間的通信並準備發送數據。這一過程叫做「建立連接」。有了這個通信連接就可以使主機A發送的電子郵件到達主機B中，並由主機B的郵件處理程序獲取最終數據。此外，當通信傳輸結束後，有必要將連接斷開。

如上，進行建立連接或斷開連接的處理（此處請注意，會話層負責決定建立連接和斷開連接的時機，而傳輸層進行實際的建立和斷開處理。），在兩個主機之間創建邏輯上的通信連接即是傳輸層的主要作用。此外，傳輸層為確保所傳輸的數據到達目標地址，會在通信兩端的計算機之間進行確認，如果數據沒有到達，它會負責進行重發。

例如，主機A將「早上好」這一數據發送給主機B。期間可能會因為某些原因導致數據被破壞，或由於發生某種網絡異常致使只有一部分數據到達目標地址。假設主機B只收到了「早上」這一部分數據，那麼它會在收到數據後將自己沒有收到「早上」之後那部分數據的事實告知主機A。主機A得知這個情況後就會將後面的「好」重發給主機B，並再次確認對端是否收到。

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00029.jpeg)

圖1.25　傳輸層的工作

這就好比人們日常會話中的確認語句：「對了，你剛才說什麼來著？」計算機通信協議其實並沒有想像中那麼晦澀難懂，其基本原理是與我們的日常生活緊密相連、大同小異的。

由此可見，保證數據傳輸的可靠性是傳輸層的一個重要作用。為了確保可靠性，在這一層也會為所要傳輸的數據附加首部以識別這一分層的數據。然而，實際上將數據傳輸給對端的處理是由網絡層來完成的。

**■ 網絡層**

![016-01](http://localhost:8000/35a0c976-3d78-4c04-8f7a-08e2056b4511/images/00030.jpeg)

圖1.26　網絡層的工作

網絡層的作用是在網絡與網絡相互連接的環境中，將數據從發送端主機發送到接收端主機。如圖1.27所示，兩端主機之間雖然有眾多數據鏈路，但能夠將數據從主機A送到主機B也都是網絡層的功勞。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00031.jpeg)

圖1.27　網絡層與數據鏈路層各盡其責

在實際發送數據時，目的地址（關於地址請參考1.8節）至關重要。這個地址是進行通信的網絡中唯一指定的序號。也可以把它想像為我們日常生活中使用的電話號碼。只要這個目標地址確定了，就可以在眾多計算機中選出該目標地址所對應的計算機發送數據。基於這個地址，就可以在網絡層進行數據包的發送處理。而有了地址和網絡層的包發送處理，就可以將數據發送到世界上任何一台互連設備。網絡層中也會將其從上層收到的數據和地址信息等一起發送給下面的數據鏈路層，進行後面的處理。

**■ 傳輸層與網絡層的關係**

在不同的網絡體系結構下，網絡層有時也不能保證數據的可達性。例如在相當於TCP/IP網絡層的IP協議中，就不能保證數據一定會發送到對端地址。因此，數據傳送過程中出現數據丟失、順序混亂等問題可能性會大大增加。像這樣沒有可靠性傳輸要求的網絡層中，可以由傳輸層負責提供「正確傳輸數據的處理」。TCP/IP中，網絡層與傳輸層相互協作以確保數據包能夠傳送到世界各地，實現可靠傳輸。

每個分層的作用與功能越清晰，規範協議的具體內容就越簡單，實現（是指通過軟件編碼實現具體的協議，使其能夠運行於計算機當中。）這些具體協議的工作也將會更加輕鬆。

**■ 數據鏈路層、物理層**

通信傳輸實際上是通過物理的傳輸介質實現的。數據鏈路層的作用就是在這些通過傳輸介質互連的設備之間進行數據處理。

物理層中，將數據的0、1轉換為電壓和脈衝光傳輸給物理的傳輸介質，而相互直連的設備之間使用地址實現傳輸。這種地址被稱為MAC（Media Access Control，介質訪問控制。）地址，也可稱為物理地址或硬件地址。採用MAC地址，目的是為了識別連接到同一個傳輸介質上的設備。因此，在這一分層中將包含MAC地址信息的首部附加到從網路層轉發過來的數據上，將其發送到網絡。

網絡層與數據鏈路層都是基於目標地址將數據發送給接收端的，但是網絡層負責將整個數據發送給最終目標地址，而數據鏈路層則只負責發送一個分段內的數據。關於這一點的更多細節可以參考4.1.2節。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00032.jpeg)

圖1.28　數據鏈路層與物理層的工作

**■ 主機B端的處理**

接收端主機B上的處理流程正好與主機A相反，它從物理層開始將接收到的數據逐層發給上一分層進行處理，從而使用戶B最終在主機B上使用郵件客戶端軟件接收用戶A發送過來的郵件，並可以讀取相應內容為「早上好」。

## 1.7　傳輸方式的分類

網絡與通信中可以根據其數據發送方法進行多種分類。分類方法也有很多，以下我們介紹其中的幾種。

### 1.7.1　面向有連接型與面向無連接型

通過網絡發送數據，大致可以分為面向有連接與面向無連接兩種類型（面向無連接型包括以太網、IP、UDP等協議。面向有連接型包括ATM、幀中繼、TCP等協議。）。

![016-01](E:\learningforalllife\git-workspace\PANDA-Walker\picture\00033.jpeg)

圖1.29　面向有連接型與面向無連接型

**■ 面向有連接型**

面向有連接型中，在發送數據（在面向有連接型的情況下，發送端的數據不一定要分組發送。第6章將要介紹的TCP是以面向有連接的方式分組發送數據的，然