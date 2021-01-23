# threadlocal和threadlocalMap总结

一.ThreadLoacl的理解：

官方的讲：

ThreadLocal是一个本地线程副本变量工具类，主要用于将私有线程和该线程存放的副本对象做一个映射，各个线程之间的变量互不干扰

通俗的讲：

ThreadLocal也叫做线程本地变量，ThreadLoacl为变量在每个线程中的都创建了副本，每个线程可以访问自己内部的副本变量，线程之间互不影响

 

二.TreadLocal的原理：

![img](https://upload-images.jianshu.io/upload_images/7432604-ad2ff581127ba8cc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/806)

从上图我们可以初步窥见ThreadLocal的核心机制：

1）每个Thread线程内部都有一个Map

2）Map里面储存线程本地对象key和线程的变量副本value

3）Thread内部的Map是由ThreadLocal维护的，由ThreadLocal负责向Map获取和设置线程的变量值

这样对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，这样就形成了副本隔离，互不干扰

 

三.ThreadLocal的底层源码

ThreadLocal类提供了以下几个核心方法：

> 1.get方法：获取当前线程的副本变量值
>
> 2.set方法：设置当前线程的副本变量值
>
> 3.remove方法：移除当前线程的副本变量值
>
> 4.initilaValue方法：初始化当前线程的副本变量值，初始化null

1）ThreadLocal.get():



```
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null)
            return (T)e.value;
    }
    return setInitialValue();
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

private T setInitialValue() {
    T value = initialValue();
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
    return value;
}

protected T initialValue() {
    return null;
}
```



源码解析：

1.获取当前线程的ThreadLocalMap对象threadLocals（实际储存副本值的Map）

2.Map不为空的话，从Map中获取线程储存的K-V Entry结点，然后从Entry结点中获取Value副本值返回

3.Map为空的话，返回初始值null，之后还需向Map中添加value为null的键值对，避免空指针异常

 

2.ThreadLocal.set():



```
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
```



源码解析：

1.获取当前线程的成员变量Map

2.Map不为空：重新将ThreadLocal对象和Value副本放入Map中

3.Map为空：对线程成员变量ThreadLocalMap进行初始化创建，并将ThreadLocal对象和Value副本放入Map中

 

3.ThreadLocal.remove():



```
public void remove() {
 ThreadLocalMap m = getMap(Thread.currentThread());
 if (m != null)
     m.remove(this);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```



源码分析：

直接调用了ThreadLocalMap的remove方法（后面我们还会探究ThreadLocalMap类的底层源码！，这里先放着）

 

4.ThreadLocal.initialValue() :

```
protected T initialValue() {
    return null;
}
```

就是直接返回null

 

小结一下：我们发现ThreadLocal的底层源码都有一个ThreadLocalMap类，那么ThreadLocalMap类的底层源码又是什么样子的呢？我们一起来看看吧！

 

四.ThreadLocalMap的底层源码分析

ThreadLocalMap是ThreadLocal内部的一个Map实现，然而它没有实现任何集合的接口规范，因为它仅供ThreadLocal内部使用，数据结构采用数组+开方地址法，Entry继承WeakRefrence，是基于ThreadLocal这种特殊场景实现的Map，它的实现方式很值得我们取研究！！

 

1.ThreadLocalMap中Entry的源码



```
static class Entry extends WeakReference<ThreadLocal> {
    /** The value associated with this ThreadLocal. */
    Object value;

    Entry(ThreadLocal k, Object v) {
        super(k);
        value = v;
    }
}
```



源码分析：

1.Entry中key只能是ThreadLocal对象，被规定死了的

2.Entry继承了WeakRefrence（弱引用，生存周期只能活到下次GC前），但是只有Key是弱引用，Value并不是弱引用

ps：value既然不是弱引用，那么key在被回收之后(key=null)Value并没有被回收，如果当前线程被回收了那还好，这样value也和线程一起被回收了，要是当前线程是线程池这样的环境，线程结束没有销毁回收，那么Value永远不会被回收，当存在大量这样的value的时候，就会产生内存泄漏，那么Java 8中如何解决这个问题的呢？

解决办法：

![这里写图片描述](https://img-blog.csdn.net/20180807213028376?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3d5MTQwNDA4MTczNw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

以上是ThreadLocalMap的set方法，for循环遍历整个Entry数组，遇到key=null的就会替换，这样就不存在value内存泄漏的问题了！！！

 

2.ThreaLocalMap中key的HashCode计算

ThreaLocalMap的key是ThreaLocal，它不会传统的调用ThreadLocal的hashcode方法（继承自object的hashcode），而是调用nexthashcode，源码如下：



```
private final int threadLocalHashCode = nextHashCode();

 private static AtomicInteger nextHashCode = new AtomicInteger();

 //1640531527 这是一个神奇的数字，能够让hash槽位分布相当均匀
 private static final int HASH_INCREMENT = 0x61c88647; 

 private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
 }
```



源码分析：

我们发现ThreaLocalMap的hashcode计算没有采用模长度的方法，没有采用拉链法，采用的是开放地址法，其槽位采用静态的AtomicInteger每次增加1640531527实现，冲突了则加1或者减1继续进行增加1640531527

我们把这个数叫做魔数，通过这个魔数我们可以位key产生完美的槽位分配，hahs冲突的次数很少

（据说魔数和黄金比例，斐波那契数列存在某种关系）

 

3.ThreaLocalMap中set方法：



```
private void set(ThreadLocal<?> key, Object value) {

    Entry[] tab = table;
    int len = tab.length;
    int i = key.threadLocalHashCode & (len-1); // 用key的hashCode计算槽位
    // hash冲突时，使用开放地址法
    // 因为独特和hash算法，导致hash冲突很少，一般不会走进这个for循环
    for (Entry e = tab[i];
         e != null;
         e = tab[i = nextIndex(i, len)]) {
        ThreadLocal<?> k = e.get();

        if (k == key) { // key 相同，则覆盖value
            e.value = value; 
            return;
        }

        if (k == null) { // key = null，说明 key 已经被回收了，进入替换方法
            replaceStaleEntry(key, value, i);
            return;
        }
    }
    // 新增 Entry
    tab[i] = new Entry(key, value);
    int sz = ++size;
    if (!cleanSomeSlots(i, sz) && sz >= threshold) // 清除一些过期的值，并判断是否需要扩容
        rehash(); // 扩容
}
```



源码分析：

1.先是计算槽位

2.Entry数组中存在需要插入的key，直接替换即可，存在key=null，也是替换（可以避免value内存泄漏）

3.Entry数组中不存在需要插入的key，也没有key=null，新增一个Entry，然后判断一下需不需要扩容和清除过期的值（关于扩容和清除过期值先不细讲）

 

4.ThreadLocalMap中getEntry方法：



```
private Entry getEntry(ThreadLocal<?> key) {
    int i = key.threadLocalHashCode & (table.length - 1);
    Entry e = table[i];
    if (e != null && e.get() == key) // 无hash冲突情况
        return e;
    else
        return getEntryAfterMiss(key, i, e); // 有hash冲突情况
}
```



源码分析：

1.计算槽位i，判断table[i]是否有目标key，没有（hahs冲突了）则进入getEntryAfterMiss方法

getEntryAfterMiss方法分析：



```
private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
    Entry[] tab = table;
    int len = tab.length;

    while (e != null) {
        ThreadLocal<?> k = e.get();
        if (k == key)
            return e;
        if (k == null)
            expungeStaleEntry(i); // 清除过期的slot
        else
            i = nextIndex(i, len);
        e = tab[i];
    }
    return null;
}
```



源码分析：

遇到hash冲突之后继续向后查找，并且会在查找路上清除过期的slot

 

5.ThreadLocalMap中rehash方法：



```
private void rehash() {
    expungeStaleEntries();

   // 清除过程中，size会减小，在此处重新计算是否需要扩容
   // 并没有直接使用threshold，而是用较低的threshold （约 threshold 的 3/4）提前触发resize
    if (size >= threshold - threshold / 4)
        resize();
}

private void expungeStaleEntries() {
    Entry[] tab = table;
    int len = tab.length;
    for (int j = 0; j < len; j++) {
        Entry e = tab[j];
        if (e != null && e.get() == null)
            expungeStaleEntry(j);
    }
}
```



源码分析：

先调用expungeStaleEntries()清除所有过期的slot，然后提前触发resize（约 threshold 的 3/4的时候）

下面看看resize（）：



```
private void resize() {
    Entry[] oldTab = table;
    int oldLen = oldTab.length;
    int newLen = oldLen * 2;
    Entry[] newTab = new Entry[newLen];
    int count = 0;

    for (int j = 0; j < oldLen; ++j) {
        Entry e = oldTab[j];
        if (e != null) {
            ThreadLocal<?> k = e.get();
            if (k == null) {
                e.value = null; // Help the GC
            } else {
                int h = k.threadLocalHashCode & (newLen - 1);
                while (newTab[h] != null)
                    h = nextIndex(h, newLen);
                newTab[h] = e;
                count++;
            }
        }
    }

    setThreshold(newLen);
    size = count;
    table = newTab;
}
```



扩容2倍，同时在Entry移动过程中会清除一些过期的entry

 

6.ThreadLocal中的remove方法：



```
private void remove(ThreadLocal<?> key) {
    Entry[] tab = table;
    int len = tab.length;
    int i = key.threadLocalHashCode & (len-1);
    for (Entry e = tab[i];
         e != null;
         e = tab[i = nextIndex(i, len)]) {
        if (e.get() == key) {
            e.clear();
            expungeStaleEntry(i);
            return;
        }
    }
}
```



源码分析：

遍历Entry数组寻找需要删除的ThreadLocal，建议在ThreadLocal使用完成之后再调用此方法

 

现在再详细分析一下ThreadLocalMap的set方法中的几个方法：

1.replaceStaleEntry方法：替换



```
private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                               int staleSlot) {
    Entry[] tab = table;
    int len = tab.length;
    Entry e;

    // 往前寻找过期的slot
    int slotToExpunge = staleSlot;
    for (int i = prevIndex(staleSlot, len);
         (e = tab[i]) != null;
         i = prevIndex(i, len))
        if (e.get() == null)
            slotToExpunge = i;

    // 找到 key 或者 直到 遇到null 的slot 才终止循环
    for (int i = nextIndex(staleSlot, len);
         (e = tab[i]) != null;
         i = nextIndex(i, len)) {
        ThreadLocal<?> k = e.get();

        // 如果找到了key，那么需要将它与过期的 slot 交换来维护哈希表的顺序。
        // 然后可以将新过期的 slot 或其上面遇到的任何其他过期的 slot 
        // 给 expungeStaleEntry 以清除或 rehash 这个 run 中的所有其他entries。

        if (k == key) {
            e.value = value;

            tab[i] = tab[staleSlot];
            tab[staleSlot] = e;

            // 如果存在，则开始清除前面过期的entry
            if (slotToExpunge == staleSlot)
                slotToExpunge = i;
            cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
            return;
        }

        // 如果我们没有在向前扫描中找到过期的条目，
        // 那么在扫描 key 时看到的第一个过期 entry 是仍然存在于 run 中的条目。
        if (k == null && slotToExpunge == staleSlot)
            slotToExpunge = i;
    }

    // 如果没有找到 key，那么在 slot 中创建新entry
    tab[staleSlot].value = null;
    tab[staleSlot] = new Entry(key, value);

    // 如果还有其他过期的entries存在 run 中，则清除他们
    if (slotToExpunge != staleSlot)
        cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
}
```



上文中run的意思不好翻译，理解为开放地址中一个slot中前后不为null的连续entry

 

2.cleanSomeSlots方法：清除一些slot（按照规则清除“一些”slot，而不是全部）



```
private boolean cleanSomeSlots(int i, int n) {
    boolean removed = false;
    Entry[] tab = table;
    int len = tab.length;
    do {
        i = nextIndex(i, len);
        Entry e = tab[i];
        if (e != null && e.get() == null) {
            n = len;
            removed = true;
            i = expungeStaleEntry(i); // 清除方法 
        }
    } while ( (n >>>= 1) != 0);  // n = n / 2， 对数控制循环 
    return removed;
}
```



当新元素被添加时，或者另外一个过期元素已经被删除的时候，会调用该方法，该方法会试探性的扫描一些Entry寻找过期的条目，它执行对数数量的扫描，是一种基于不扫描（快速但保留垃圾）和所有元素扫描之间的平衡！！

对数数量的扫描！！！

这是一种折中的方案

 

3.expungeStaleEntry：清除



```
private int expungeStaleEntry(int staleSlot) {
    Entry[] tab = table;
    int len = tab.length;

    // 清除当前过期的slot
    tab[staleSlot].value = null;
    tab[staleSlot] = null;
    size--;

    // Rehash 直到 null 的 slot
    Entry e;
    int i;
    for (i = nextIndex(staleSlot, len);
         (e = tab[i]) != null;
         i = nextIndex(i, len)) {
        ThreadLocal<?> k = e.get();
        if (k == null) {
            e.value = null;
            tab[i] = null;
            size--;
        } else {
            int h = k.threadLocalHashCode & (len - 1);
            if (h != i) {
                tab[i] = null;

                while (tab[h] != null)
                    h = nextIndex(h, len);
                tab[h] = e;
            }
        }
    }
    return i;
}
```



真正的清除，不仅会清除当前过期的slot，还会继续往后查询直到遇到null的slot为止，对于查询遍历中没有被回收的情况，做了一次rehash

推荐大佬的博客：https://www.cnblogs.com/micrari/p/6790229.html