# HashMap 讲解

## 目录

本篇文章主要包括以下内容：

- HashMap 的存储结构
- 常用变量说明，如加载因子等
- HashMap 的四个构造函数
- tableSizeFor()方法及作用
- put()方法详解
- hash()方法，以及避免哈希碰撞的原理
- resize()扩容机制及原理
- get()方法
- 为什么HashMap链表会形成死循环，JDK1.8做了哪些优化

## 正文

**说明：**本篇主要以JDK1.8的源码来分析，顺带讲下和JDK1.7的一些区别。

## HashMap存储结构

这里需要区分一下，JDK1.7和 JDK1.8之后的 HashMap 存储结构。在JDK1.7及之前，是用数组加链表的方式存储的。

但是，众所周知，当链表的长度特别长的时候，查询效率将直线下降，查询的时间复杂度为 O(n)。因此，JDK1.8 把它设计为达到一个特定的阈值之后，就将链表转化为红黑树。

这里简单说下红黑树的特点：

1. 每个节点只有两种颜色：红色或者黑色
2. 根节点必须是黑色
3. 每个叶子节点（NIL）都是黑色的空节点
4. 从根节点到叶子节点，不能出现两个连续的红色节点
5. 从任一节点出发，到它下边的子节点的路径包含的黑色节点数目都相同

由于红黑树，是一个自平衡的二叉搜索树，因此可以使查询的时间复杂度降为O(logn)。（红黑树不是本文重点，不了解的童鞋可自行查阅相关资料哈）

HashMap 结构示意图：

![img](https://pic4.zhimg.com/80/v2-4fd17b57ff53d4752cc7a42cfe788517_720w.jpg)



常用的变量

在 HashMap源码中，比较重要的常用变量，主要有以下这些。还有两个内部类来表示普通链表的节点和红黑树节点。

```text
//默认的初始化容量为16，必须是2的n次幂
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

//最大容量为 2^30
static final int MAXIMUM_CAPACITY = 1 << 30;

//默认的加载因子0.75，乘以数组容量得到的值，用来表示元素个数达到多少时，需要扩容。
//为什么设置 0.75 这个值呢，简单来说就是时间和空间的权衡。
//若小于0.75如0.5，则数组长度达到一半大小就需要扩容，空间使用率大大降低，
//若大于0.75如0.8，则会增大hash冲突的概率，影响查询效率。
static final float DEFAULT_LOAD_FACTOR = 0.75f;

//刚才提到了当链表长度过长时，会有一个阈值，超过这个阈值8就会转化为红黑树
static final int TREEIFY_THRESHOLD = 8;

//当红黑树上的元素个数，减少到6个时，就退化为链表
static final int UNTREEIFY_THRESHOLD = 6;

//链表转化为红黑树，除了有阈值的限制，还有另外一个限制，需要数组容量至少达到64，才会树化。
//这是为了避免，数组扩容和树化阈值之间的冲突。
static final int MIN_TREEIFY_CAPACITY = 64;

//存放所有Node节点的数组
transient Node<K,V>[] table;

//存放所有的键值对
transient Set<Map.Entry<K,V>> entrySet;

//map中的实际键值对个数，即数组中元素个数
transient int size;

//每次结构改变时，都会自增，fail-fast机制，这是一种错误检测机制。
//当迭代集合的时候，如果结构发生改变，则会发生 fail-fast，抛出异常。
transient int modCount;

//数组扩容阈值
int threshold;

//加载因子
final float loadFactor;                    

//普通单向链表节点类
static class Node<K,V> implements Map.Entry<K,V> {
    //key的hash值，put和get的时候都需要用到它来确定元素在数组中的位置
    final int hash;
    final K key;
    V value;
    //指向单链表的下一个节点
    Node<K,V> next;

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}

//转化为红黑树的节点类
static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
    //当前节点的父节点
    TreeNode<K,V> parent;  
    //左孩子节点
    TreeNode<K,V> left;
    //右孩子节点
    TreeNode<K,V> right;
    //指向前一个节点
    TreeNode<K,V> prev;    // needed to unlink next upon deletion
    //当前节点是红色或者黑色的标识
    boolean red;
    TreeNode(int hash, K key, V val, Node<K,V> next) {
        super(hash, key, val, next);
    }
}
```

## HashMap 构造函数

HashMap有四个构造函数可供我们使用，一起来看下：

```text
//默认无参构造，指定一个默认的加载因子
public HashMap() {
    this.loadFactor = DEFAULT_LOAD_FACTOR; 
}

//可指定容量的有参构造，但是需要注意当前我们指定的容量并不一定就是实际的容量，下面会说
public HashMap(int initialCapacity) {
    //同样使用默认加载因子
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}

//可指定容量和加载因子，但是笔者不建议自己手动指定非0.75的加载因子
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    //这里就是把我们指定的容量改为一个大于它的的最小的2次幂值，如传过来的容量是14，则返回16
    //注意这里，按理说返回的值应该赋值给 capacity，即保证数组容量总是2的n次幂，为什么这里赋值给了 threshold 呢？
    //先卖个关子，等到 resize 的时候再说
    this.threshold = tableSizeFor(initialCapacity);
}

//可传入一个已有的map
public HashMap(Map<? extends K, ? extends V> m) {
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    putMapEntries(m, false);
}

//把传入的map里边的元素都加载到当前map
final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
    int s = m.size();
    if (s > 0) {
        if (table == null) { // pre-size
            float ft = ((float)s / loadFactor) + 1.0F;
            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                     (int)ft : MAXIMUM_CAPACITY);
            if (t > threshold)
                threshold = tableSizeFor(t);
        }
        else if (s > threshold)
            resize();
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            //put方法的具体实现，后边讲
            putVal(hash(key), key, value, false, evict);
        }
    }
}
```

## tableSizeFor()

上边的第三个构造函数中，调用了 tableSizeFor 方法，这个方法是怎么实现的呢？

```text
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

我们以传入参数为14 来举例，计算这个过程。

首先，14传进去之后先减1，n此时为13。然后是一系列的无符号右移运算。

```text
//13的二进制
0000 0000 0000 0000 0000 0000 0000 1101 
//无右移1位，高位补0
0000 0000 0000 0000 0000 0000 0000 0110 
//然后把它和原来的13做或运算得到，此时的n值
0000 0000 0000 0000 0000 0000 0000 1111 
//再以上边的值，右移2位
0000 0000 0000 0000 0000 0000 0000 0011
//然后和第一次或运算之后的 n 值再做或运算，此时得到的n值
0000 0000 0000 0000 0000 0000 0000 1111
...
//我们会发现，再执行右移 4,8,16位，同样n的值不变
//当n小于0时，返回1，否则判断是否大于最大容量，是的话返回最大容量，否则返回 n+1
return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//很明显我们这里返回的是 n+1 的值，
0000 0000 0000 0000 0000 0000 0000 1111
+                                     1
0000 0000 0000 0000 0000 0000 0001 0000
```

将它转为十进制，就是 2^4 = 16 。我们会发现一个规律，以上的右移运算，最终会把最低位的值都转化为 1111 这样的结构，然后再加1，就是1 0000 这样的结构，它一定是 2的n次幂。因此，这个方法返回的就是大于当前传入值的最小（最接近当前值）的一个2的n次幂的值。

## put()方法详解

```text
//put方法，会先调用一个hash()方法，得到当前key的一个hash值，
//用于确定当前key应该存放在数组的哪个下标位置
//这里的 hash方法，我们姑且先认为是key.hashCode()，其实不是的，一会儿细讲
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

//把hash值和当前的key，value传入进来
//这里onlyIfAbsent如果为true，表明不能修改已经存在的值，因此我们传入false
//evict只有在方法 afterNodeInsertion(boolean evict) { }用到，可以看到它是一个空实现，因此不用关注这个参数
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    //判断table是否为空，如果空的话，会先调用resize扩容
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    //根据当前key的hash值找到它在数组中的下标，判断当前下标位置是否已经存在元素，
    //若没有，则把key、value包装成Node节点，直接添加到此位置。
    // i = (n - 1) & hash 是计算下标位置的，为什么这样算，后边讲
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else { 
        //如果当前位置已经有元素了，分为三种情况。
        Node<K,V> e; K k;
        //1.当前位置元素的hash值等于传过来的hash，并且他们的key值也相等，
        //则把p赋值给e，跳转到①处，后续需要做值的覆盖处理
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        //2.如果当前是红黑树结构，则把它加入到红黑树 
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
        //3.说明此位置已存在元素，并且是普通链表结构，则采用尾插法，把新节点加入到链表尾部
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    //如果头结点的下一个节点为空，则插入新节点
                    p.next = newNode(hash, key, value, null);
                    //如果在插入的过程中，链表长度超过了8，则转化为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    //插入成功之后，跳出循环，跳转到①处
                    break;
                }
                //若在链表中找到了相同key的话，直接退出循环，跳转到①处
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        //① 此时e有两种情况
        //1.说明发生了碰撞，e代表的是旧值，因此节点位置不变，但是需要替换为新值
        //2.说明e是插入链表或者红黑树,成功后的新节点
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            //用新值替换旧值，并返回旧值。
            //oldValue为空，说明e是新增的节点或者也有可能旧值本来就是空的，因为hashmap可存空值
            onlyIfAbsent(只有不存在时，插入新值false)
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            //看方法名字即可知，这是在node被访问之后需要做的操作。其实此处是一个空实现，
            //只有在 LinkedHashMap才会实现，用于实现根据访问先后顺序对元素进行排序，hashmap不提供排序功能
            // Callbacks to allow LinkedHashMap post-actions
            //void afterNodeAccess(Node<K,V> p) { }
            afterNodeAccess(e);
            return oldValue;
        }
    }
    //fail-fast机制
    ++modCount;
    //如果当前数组中的元素个数超过阈值，则扩容
    if (++size > threshold)
        resize();
    //同样的空实现
    afterNodeInsertion(evict);
    return null;
}
```

## hash()计算原理

前面 put 方法中说到，需要先把当前key进行哈希处理，我们看下这个方法是怎么实现的。

```text
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

这里，会先判断key是否为空，若为空则返回0。这也说明了hashMap是支持key传 null 的。若非空，则先计算key的hashCode值，赋值给h，然后把h右移16位，并与原来的h进行异或处理。为什么要这样做，这样做有什么好处呢？

我们知道，hashCode()方法继承自父类Object，它返回的是一个 int 类型的数值，可以保证同一个应用单次执行的每次调用，返回结果都是相同的（这个说明可以在hashCode源码上找到），这就保证了hash的确定性。在此基础上，再进行某些固定的运算，肯定结果也是可以确定的。

我随便运行一段程序，把它的 hashCode的二进制打印出来，如下。

```text
public static void main(String[] args) {
    Object o = new Object();
    int hash = o.hashCode();
    System.out.println(hash);
    System.out.println(Integer.toBinaryString(hash));

}
//1836019240
//1101101011011110110111000101000
```

然后，进行 (h = key.hashCode()) ^ (h >>> 16) 这一段运算。

```text
//h原来的值
0110 1101 0110 1111 0110 1110 0010 1000
//无符号右移16位，其实相当于把低位16位舍去，只保留高16位
0000 0000 0000 0000 0110 1101 0110 1111
//然后高16位和原 h进行异或运算
0110 1101 0110 1111 0110 1110 0010 1000
^
0000 0000 0000 0000 0110 1101 0110 1111
=
0110 1101 0110 1111 0000 0011 0100 0111
```

可以看到，其实相当于，我们把高16位值和当前h的低16位进行了混合，这样可以尽量保留高16位的特征，从而降低哈希碰撞的概率。

思考一下，为什么这样做，就可以降低哈希碰撞的概率呢？先别着急，我们需要结合 i = (n - 1) & hash 这一段运算来理解。

** (n-1) & hash 作用**

```text
//②
//这是 put 方法中用来根据hash()值寻找在数组中的下标的逻辑，
//n为数组长度， hash为调用 hash()方法混合处理之后的hash值。
i = (n - 1) & hash复制代码
```

我们知道，如果给定某个数值，去找它在某个数组中的下标位置时，直接用模运算就可以了（假设数组值从0开始递增）。如，我找 14 在数组长度为16的数组中的下标，即为 14 % 16，等于14 。 18的位置即为 18%16，等于2。

而②中，就是取模运算的位运算形式。以18%16为例

```text
//18的二进制
0001 0010
//16 -1 即 15的二进制
0000 1111
//与运算之后的结果为
0000 0010
// 可以看到，上边的结果转化为十进制就是 2 。
//其实我们会发现一个规律，因为n是2的n次幂，因此它的二进制表现形式肯定是类似于
0001 0000
//这样的形式，只有一个位是1，其他位都是0。而它减 1 之后的形式就是类似于
0000 1111 
//这样的形式，高位都是0，低位都是1，因此它和任意值进行与运算，结果值肯定在这个区间内
0000 0000  ~  0000 1111
//也就是0到15之间，（以n为16为例）
//因此，这个运算就可以实现取模运算，而且位运算还有个好处，就是速度比较快。复制代码
```

**为什么高低位异或运算可以减少哈希碰撞**

我们想象一下，假如用 key 原来的hashCode值，直接和 (n-1) 进行与运算来求数组下标，而不进行高低位混合运算，会产生什么样的结果。

```text
//例如我有另外一个h2，和原来的 h相比较，高16位有很大的不同，但是低16位相似度很高，甚至相同的话。
//原h值
0110 1101 0110 1111 0110 1110 0010 1000
//另外一个h2值
0100 0101 1110 1011 0110 0110 0010 1000
// n -1 ,即 15 的二进制
0000 0000 0000 0000 0000 0000 0000 1111
//可以发现 h2 和 h 的高位不相同，但是低位相似度非常高。
//他们分别和 n -1 进行与运算时，得到的结果却是相同的。（此处n假设为16）
//因为 n-1 的高16位都是0，不管 h 的高 16 位是什么，与运算之后，都不影响最终结果，高位一定全是 0
//因此，哈希碰撞的概率就大大增加了，并且 h 的高16 位特征全都丢失了。
```

爱思考的同学可能就会有疑问了，我进行高低16位混合运算，是可以的，这样可以保证尽量减少高区位的特征。那么，为什么选择用异或运算呢，我用与、或、非运算不行吗？

这是有一定的道理的。我们看一个表格，就能明白了。

![img](https://pic1.zhimg.com/80/v2-d8b69309c396823c35682b5d6ce09924_720w.jpg)


可以看到两个值进行与运算，结果会趋向于0；或运算，结果会趋向于1；而只有异或运算，0和1的比例可以达到1:1的平衡状态。（非呢？别扯犊子了，两个值怎么做非运算。。。）

所以，异或运算之后，可以让结果的随机性更大，而随机性大了之后，哈希碰撞的概率当然就更小了。

以上，就是为什么要对一个hash值进行高低位混合，并且选择异或运算来混合的原因。

## resize() 扩容机制

在上边 put 方法中，我们会发现，当数组为空的时候，会调用 resize 方法，当数组的 size 大于阈值的时候，也会调用 resize方法。 那么看下 resize 方法都做了哪些事情吧。

```text
final Node<K,V>[] resize() {
    //旧数组
    Node<K,V>[] oldTab = table;
    //旧数组的容量
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    //旧数组的扩容阈值，注意看，这里取的是当前对象的 threshold 值，下边的第2种情况会用到。
    int oldThr = threshold;
    //初始化新数组的容量和阈值，分三种情况讨论。
    int newCap, newThr = 0;
    //1.当旧数组的容量大于0时，说明在这之前肯定调用过 resize扩容过一次，才会导致旧容量不为0。
    //为什么这样说呢，之前我在 tableSizeFor 卖了个关子，需要注意的是，它返回的值是赋给了 threshold 而不是 capacity。
    //我们在这之前，压根就没有在任何地方看到过，它给 capacity 赋初始值。
    if (oldCap > 0) {
        //容量达到了最大值
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        //新数组的容量和阈值都扩大原来的2倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    //2.到这里，说明 oldCap <= 0，并且 oldThr(threshold) > 0，这就是 map 初始化的时候，第一次调用 resize的情况
    //而 oldThr的值等于 threshold，此时的 threshold 是通过 tableSizeFor 方法得到的一个2的n次幂的值(我们以16为例)。
    //因此，需要把 oldThr 的值，也就是 threshold ，赋值给新数组的容量 newCap，以保证数组的容量是2的n次幂。
    //所以我们可以得出结论，当map第一次 put 元素的时候，就会走到这个分支，把数组的容量设置为正确的值（2的n次幂)
    //但是，此时 threshold 的值也是2的n次幂，这不对啊，它应该是数组的容量乘以加载因子才对。别着急，这个会在③处理。
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    //3.到这里，说明 oldCap 和 oldThr 都是小于等于0的。也说明我们的map是通过默认无参构造来创建的，
    //于是，数组的容量和阈值都取默认值就可以了，即 16 和 12。
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    //③ 这里就是处理第2种情况，因为只有这种情况 newThr 才为0，
    //因此计算 newThr（用 newCap即16 乘以加载因子 0.75，得到 12） ，并把它赋值给 threshold
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    //赋予 threshold 正确的值，表示数组下次需要扩容的阈值（此时就把原来的 16 修正为了 12）。
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    //如果原来的数组不为空，那么我们就需要把原来数组中的元素重新分配到新的数组中
    //如果是第2种情况，由于是第一次调用resize，此时数组肯定是空的，因此也就不需要重新分配元素。
    if (oldTab != null) {
        //遍历旧数组
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            //取到当前下标的第一个元素，如果存在，则分三种情况重新分配位置
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                //1.如果当前元素的下一个元素为空，则说明此处只有一个元素
                //则直接用它的hash()值和新数组的容量取模就可以了，得到新的下标位置。
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                //2.如果是红黑树结构，则拆分红黑树，必要时有可能退化为链表
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                //3.到这里说明，这是一个长度大于 1 的普通链表，则需要计算并
                //判断当前位置的链表是否需要移动到新的位置
                else { // preserve order
                    // loHead 和 loTail 分别代表链表旧位置的头尾节点
                    Node<K,V> loHead = null, loTail = null;
                    // hiHead 和 hiTail 分别代表链表移动到新位置的头尾节点
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        //如果当前元素的hash值和oldCap做与运算为0，则原位置不变
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        //否则，需要移动到新的位置
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    //原位置不变的一条链表，数组下标不变
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    //移动到新位置的一条链表，数组下标为原下标加上旧数组的容量
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

上边还有一个非常重要的运算，我们没有讲解。就是下边这个判断，它用于把原来的普通链表拆分为两条链表，位置不变或者放在新的位置。

```text
if ((e.hash & oldCap) == 0) {} else {}
```

我们以原数组容量16为例，扩容之后容量为32。说明下为什么这样计算。

还是用之前的hash值举例。

```text
//e.hash值
0110 1101 0110 1111 0110 1110 0010 1000
//oldCap值，即16
0000 0000 0000 0000 0000 0000 0001 0000 
//做与运算，我们会发现结果不是0就是非0，
//而且它取决于 e.hash 二进制位的倒数第五位是 0 还是 1，
//若倒数第五位为0，则结果为0，若倒数第五位为1，则结果为非0。
//那这个和新数组有什么关系呢？
//别着急，我们看下新数组的容量是32，如果求当前hash值在新数组中的下标，则为
// e.hash &( 32 - 1) 这样的运算 ，即 hash 与 31 进行与运算，
0110 1101 0110 1111 0110 1110 0010 1000 
&
0000 0000 0000 0000 0000 0000 0001 1111 
=
0000 0000 0000 0000 0000 0000 0000 1000
//接下来，我们对比原来的下标计算结果和新的下标结果，看图
```

看下面的图，我们观察，hash值和旧数组进行与运算的结果 ，跟新数组的与运算结果有什么不同。

![img](https://pic3.zhimg.com/80/v2-9cad0656c413aa6c2e4d8f91f8c1720a_720w.jpg)


**会发现一个规律：**

**若hash值的倒数第五位是0，则新下标与旧下标结果相同，都为 0000 1000**

**若hash值的倒数第五位是1，则新下标（0001 1000）与旧下标（0000 1000）结果值相差了 16 。**

因此，我们就可以根据 （e.hash & oldCap == 0） 这个判断的真假来决定，当前元素应该在原来的位置不变，还是在新的位置(原位置 + 16)。

如果，上边的推理还是不明白的话，我再举个简单的例子。

```text
18%16=2     18%32=18
34%16=2     34%32=2
50%16=2     50%32=18
```

怎么样，发现规律没，有没有那个感觉了？

计算中的18，34 ，50 其实就相当于 e.hash 值，和新旧数组做取模运算，得到的结果，要么就是原来的位置不变，要么就是原来的位置加上旧数组的长度。

## get()方法

有了前面的基础，get方法就比较简单了。

```text
public V get(Object key) {
    Node<K,V> e;
    //如果节点为空，则返回null，否则返回节点的value。这也说明，hashMap是支持value为null的。
    //因此，我们就明白了，为什么hashMap支持Key和value都为null
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    //首先要确保数组不能为空，然后取到当前hash值计算出来的下标位置的第一个元素
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        //若hash值和key都相等，则说明我们要找的就是第一个元素，直接返回
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        //如果不是的话，就遍历当前链表（或红黑树）
        if ((e = first.next) != null) {
            //如果是红黑树结构，则找到当前key所在的节点位置
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            //如果是普通链表，则向后遍历查找，直到找到或者遍历到链表末尾为止。
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    //否则，说明没有找到，返回null
    return null;
}
```

## 为什么HashMap链表会形成死循环

准确的讲应该是 JDK1.7 的 HashMap 链表会有死循环的可能，因为JDK1.7是采用的头插法，在多线程环境下有可能会使链表形成环状，从而导致死循环。JDK1.8做了改进，用的是尾插法，不会产生死循环。

**那么，链表是怎么形成环状的呢？**

关于这一点的解释，我发现网上文章抄来抄去的，而且都来自左耳朵耗子，更惊奇的是，连配图都是一模一样的。（别问我为什么知道，因为我也看过耗子叔的文章，哈哈。然而，菜鸡的我，那篇文章，并没有看懂。。。）

我实在看不下去了，于是一怒之下，就有了这篇文章。我会照着源码一步一步的分析变量之间的关系怎么变化的，并有配图哦。

我们从 put()方法开始，最终找到线程不安全的那个方法。这里省略中间不重要的过程，我只把方法的跳转流程贴出来：

```text
//添加元素方法 -> 添加新节点方法 -> 扩容方法 -> 把原数组元素重新分配到新数组中
put()  --> addEntry()  --> resize() -->  transfer()
```

问题就发生在 transfer 这个方法中。

![img](https://pic4.zhimg.com/80/v2-ae096212831f7fe3cf127a93ec687827_720w.jpg)


我们假设，原数组容量只有2，其中一条链表上有两个元素 A,B，如下图

![img](https://pic1.zhimg.com/80/v2-32cb19c8217c184fc2d01f7f0d07c928_720w.jpg)


现在，有两个线程都执行 transfer 方法。每个线程都会在它们自己的工作内存生成一个newTable 的数组，用于存储变化后的链表，它们互不影响（这里互不影响，指的是两个新数组本身互不影响）。但是，需要注意的是，它们操作的数据却是同一份。

因为，真正的数组中的内容在堆中存储，它们指向的是同一份数据内容。就相当于，有两个不同的引用 X，Y，但是它们都指向同一个对象 Z。这里 X、Y就是两个线程不同的新数组，Z就是堆中的A，B 等元素对象。

假设线程一执行到了上图1中所指的代码①处，恰好 CPU 时间片到了，线程被挂起，不能继续执行了。 **记住此时，线程一中记录的 e = A ， e.next = B。**

然后线程二正常执行，扩容后的数组长度为 4， 假设 A，B两个元素又碰撞到了同一个桶中。然后，通过几次 while 循环后，采用头插法，最终呈现的结构如下：

![img](https://pic4.zhimg.com/80/v2-7c2cf1e5361ab08aff988d4fc2f8c2bf_720w.jpg)


此时，线程一解挂，继续往下执行。注意，此时线程一，记录的还是 e = A，e.next = B，因为它还未感知到最新的变化。

我们主要关注图1中标注的①②③④处的变量变化：

```text
/**
* next = e.next
* e.next = newTable[i]
* newTable[i] = e;
* e = next;
*/

//第一次循环,(伪代码)
e=A;next=B;
e.next=null //此时线程一的新数组刚初始化完成，还没有元素
newTab[i] = A->null //把A节点头插到新数组中
e=B; //下次循环的e值
```

第一次循环结束后，线程一新数组的结构如下图：

![img](https://pic2.zhimg.com/80/v2-381d9d34e07db65e79c6d1c03378dba5_720w.jpg)

然后，由于 e=B，不为空，进入第二次循环。

```text
//第二次循环
e=B;next=A;  //此时A，B的内容已经被线程二修改为 B->A->null，然后被线程一读到，所以B的下一个节点指向A
e.next=A->null  // A->null 为第一次循环后线程一新数组的结构
newTab[i] = B->A->null //新节点B插入之后，线程一新数组的结构
e=A;  //下次循环的 e 值
```

第二次循环结束后，线程一新数组的结构如下图：

![img](https://pic3.zhimg.com/80/v2-3640922f5ab26b8b1a8d12e105987f1e_720w.jpg)

此时，由于 e=A，不为空，继续循环。

```text
//第三次循环
e=A;next=null;  // A节点后边已经没有节点了
e.next= B->A->null  // B->A->null 为第二次循环后线程一新数组的结构
//我们把A插入后，抽象的表达为 A->B->A->null，但是，A只能是一个，不能分身啊
//因此实际上是 e(A).next指向发生了变化，A的 next 由指向 null 改为指向了 B，
//而 B 本身又指向A，因此A和B互相指向，成环
newTab[i] = A->B 且 B->A 
e=next=null; //e此时为空，结束循环
```

第三次循环结束后，看下图，A的指向由 null ，改为指向为 B，因此 A 和 B 之间成环。

![img](https://pic4.zhimg.com/80/v2-0fa13a66c160aef850e26923298b8c63_720w.jpg)

这时，有的同学可能就会问了，就算他们成环了，又怎样，跟死循环有什么关系？

我们看下 get() 方法（最终调用 getEntry 方法），

![img](https://pic3.zhimg.com/80/v2-e1c8510928d39c8935a23bdf63234bbe_720w.jpg)


可以看到查找元素时，只要 e 不为空，就会一直循环查找下去。若有某个元素 C 的 hash 值也落在了和 A，B元素同一个桶中，则会由于， A，B互相指向，e.next 永远不为空，就会形成死循环。