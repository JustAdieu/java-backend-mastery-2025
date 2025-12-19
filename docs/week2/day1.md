---
Week2 	Day1	ArrayList原理
@author	adieu
@date	2025/12/19
---

[toc]

# ArrayList

## 核心底层结构

ArrayList 的核心是一个**可扩容的 Object 数组**，源码核心成员变量如下（JDK 8 为例）：

```java
// 底层存储元素的数组（transient 表示不参与序列化）
transient Object[] elementData;
// 集合中实际元素的数量（≠ elementData.length）
private int size;
// 默认初始容量（无参构造时的初始空数组，首次添加元素时扩容为10）
private static final int DEFAULT_CAPACITY = 10;
// 空数组常量（无参构造初始化时使用）
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
```

### transient

`transient` 是一个**关键字**（修饰符），核心作用是**标记对象的字段不参与序列化 / 反序列化过程**。ArrayList 类中的底层存储数组`elementData`（Object [] 类型）之所以被`transient`标记，是因为 ArrayList 为减少扩容开销会预分配多余的数组空间，导致`elementData`的物理容量（数组长度）大于实际存储元素的数量，ArrayList类中通过自己重写序列化和反序列化的方法来减少Object数组序列化的开销。

### 序列化

序列化（Serialization）是 Java 中一种**对象持久化 / 传输的核心机制**，简单来说：

**把内存中「活的」Java 对象，转换成一串字节流（二进制数据）的过程**；反之，将字节流恢复为原对象的过程，称为「反序列化（Deserialization）」。

**序列化的核心目的**

1. **持久化存储**：把对象存到文件、数据库、硬盘等介质中（比如游戏存档、用户数据落地）；
2. **网络传输**：把对象通过网络发送给其他程序 / 服务器（比如分布式系统、RPC 调用、接口传参）；
3. **跨进程通信**：不同进程间传递对象（本质也是字节流传输）。

**为什么要序列化**

内存中的Java对象是结构化的（有属性，方法，引用关系等等），但是文件存储/网络传输只能处理字节流。序列化就是将对象转换为字节流，反序列就是将字节流转换为对象。

**Java中是如何实现序列化的？**

1. **基础条件**：类必须实现 `Serializable` 接口（这是一个「标记接口」，无任何方法，仅告诉 JVM：这个类的对象可以被序列化）；
2. **核心工具类**：JDK 提供 `ObjectOutputStream`（序列化）和 `ObjectInputStream`（反序列化）；
3. **关键要求**：
   - 类的所有非 `transient`、非 `static` 字段都要支持序列化（基本类型默认支持，引用类型需也实现 `Serializable`）；
   - 建议显式声明 `serialVersionUID`（序列化版本号），避免类结构微调后反序列化失败。

## 核心特性与核心操作原理

### 1. 初始化（构造方法）

| 构造方法                               | 逻辑                                                         |
| -------------------------------------- | ------------------------------------------------------------ |
| `ArrayList()`                          | 初始化为空数组 `DEFAULTCAPACITY_EMPTY_ELEMENTDATA`，首次添加元素时扩容至 10； |
| `ArrayList(int initialCapacity)`       | 指定初始容量，若容量 > 0 则创建对应长度数组，=0 则用空数组，<0 抛异常； |
| `ArrayList(Collection<? extends E> c)` | 把集合元素复制到数组，若数组为空则初始化为空数组；           |

```java
// 无参构造：初始elementData为空，size=0
List<String> list1 = new ArrayList<>();
// 指定容量：elementData长度=20，size=0
List<String> list2 = new ArrayList<>(20);
```

### 2. 动态扩容（核心机制）

数组是定长的，当 `size == elementData.length` 时，添加元素会触发**扩容**，步骤如下：

**（1）扩容触发时机**

调用 `add()`/`addAll()` 时，先检查容量是否足够：

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1); // 检查是否需要扩容（至少需要size+1容量）
    elementData[size++] = e;
    return true;
}
```

**（2）扩容计算规则（JDK 8）**

1. 计算「最小需要容量」：无参构造首次扩容时，最小容量 = `DEFAULT_CAPACITY(10)`；其他情况 = `size + 1`；
2. 如果最小容量 >当前数组长度，会触发扩容：
   * 新容量 = 旧容量 + 旧容量 / 2；
   * 如果计算得到的新容量小于最小容量，则新容量 = 最小需要容量，否则继续扩容；
   * 如果新容量超过了 `Integer.MAX_VALUE - 8`（数组最大容量上限），则修正为 `Integer.MAX_VALUE`；
3. 通过 `Arrays.copyOf()` 复制原数组元素到新数组，替换 `elementData`。

**扩容本质**：创建新数组 + 复制元素，是**耗时操作**（O (n) 时间复杂度），因此若能预估元素数量，建议初始化时指定容量，减少扩容次数。

### 3. 元素添加（add 方法）

ArrayList 提供两种添加方式，原理不同：

**（1）尾部添加 `add(E e)`**

- 检查容量（不足则扩容）；
- 直接将元素放入 `elementData[size]`，`size++`；
- 无元素移动，**平均时间复杂度 O (1)**（扩容时为 O (n)，但扩容频率低）。

**（2）指定位置添加 `add(int index, E e)`**

1. 检查索引合法性（`index < 0 || index > size` 抛异常）；
2. 检查容量（不足则扩容）；
3. 数组拷贝：将 `elementData[index ... size-1]` 向后移动一位（`System.arraycopy`）；
4. `elementData[index] = e`，`size++`；

- 有元素移动，**时间复杂度 O (n)**（最坏情况插入头部，需移动所有元素）。

### 4. 元素获取（get 方法）

```java
public E get(int index) {
    rangeCheck(index); // 检查索引（index >= size 抛异常）
    return elementData(index); // 直接返回数组对应位置元素
}
```

- 基于数组下标随机访问，**时间复杂度 O (1)**（ArrayList 最核心的优势）；
- 注意：返回的是 Object 类型，泛型场景会自动拆箱（可能触发 ClassCastException）。

### 5. 元素删除（remove 方法）

**（1）按索引删除 `remove(int index)`**

1. 检查索引合法性；
2. 记录要删除的元素（用于返回）；
3. 计算需要移动的元素数量：`numMoved = size - index - 1`；
4. 若 `numMoved > 0`，将 `elementData[index+1 ... size-1]` 向前移动一位；
5. `elementData[--size] = null`（释放引用，便于 GC）；

- 时间复杂度 O (n)（需要移动元素）。

**（2）按元素删除 `remove(Object o)`**

1. 遍历数组找到第一个等于 `o` 的元素（null 用 ==，非 null 用 equals）；
2. 调用 `fastRemove()` 执行上述移动逻辑；

- 时间复杂度 O (n)（遍历 + 移动元素）。

## 关键注意事项

1. **非线程安全**：ArrayList 未加同步锁，多线程并发修改（如一边 add 一边遍历）会抛出 `ConcurrentModificationException`；需用 `Collections.synchronizedList()` 或 `CopyOnWriteArrayList` 替代。
2. **迭代器快速失败**：遍历过程中若修改集合（除迭代器自身的 remove 方法），迭代器会检测到 `modCount` 变化并抛异常。
3. **空值允许**：ArrayList 支持存储 null 元素（可通过 `add(null)` 添加）。
4. **序列化优化**：`elementData` 被 `transient` 修饰，因为数组可能有空闲空间（`size < length`），序列化时通过 `writeObject()` 只写入实际元素，减少空间占用。