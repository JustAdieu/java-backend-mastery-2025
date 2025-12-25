---
week2	day3
author	adieu
date	2025/12/23
---

[toc]

# HashMap put流程

## 哈希冲突解决方法

### 开放地址法

**核心思想：**当关键字 `key` 映射的哈希地址 `hash(key)` 被占用时，**不使用额外存储空间**（如链地址法的链表），而是按照某种预设规则**连续探测哈希表中的其他空位置**，直到找到可用地址为止。

1. **线性探测**

   1. 探测规则

   线性探测的增量 `d_i` 是**线性递增**的，即：

   ```plaintext
   d_i = i （或 d_i = c*i，c为常数，通常取1）
   ```

   最常用的形式是 `d_i = i`，此时探测地址为：

   ```plaintext
   hash_i(key) = (hash(key) + i) % table_size
   ```

   2. 实现过程示例

   假设哈希表长度 `table_size=11`，哈希函数为 `hash(key) = key % 11`，需插入关键字 `{12, 23, 34, 45, 56}`：

   - 12 % 11 = 1 → 存入地址 1。
   - 23 % 11 = 1 → 冲突，探测 `1+1=2` → 存入地址 2。
   - 34 % 11 = 1 → 冲突，探测 `1+1=2`（冲突）→ 探测 `1+2=3` → 存入地址 3。
   - 45 % 11 = 1 → 冲突，探测地址 2（冲突）、3（冲突）、4 → 存入地址 4。
   - 56 % 11 = 1 → 冲突，探测地址 2-4（均冲突）、5 → 存入地址 5。

   3. 优缺点

   - **优点**：实现最简单，探测速度快（连续内存地址，**缓存友好**）。
   - **缺点**：易产生**初级聚集**—— 当多个关键字冲突时，会形成连续的 “占用块”，后续关键字需要探测更多次才能找到空位置，导致性能急剧下降（如上面的示例中，地址 1-5 被连续占用）。

   > 要理解这里的所谓的缓存友好就需要理解什么是缓存，当 CPU 访问内存中某个地址的数据时，会把这个地址**相邻的一片连续内存数据**也一并加载到缓存中。
   >
   > 比如访问地址 1 的内存，缓存会自动把地址 1、2、3、4…（通常是 64 字节 / 128 字节的 “缓存行”）都加载进来。后续如果 CPU 继续访问地址 2、3，就不用再去慢的内存取，直接从缓存读，速度会快很多。

2. **平方探测**

   1. 探测规则

   平方探测的增量 `d_i` 是**平方数**，即：

   ```plaintext
   d_i = ±i² （或 d_i = i²，仅用正增量）
   ```

   常用的形式是同时使用正负增量，此时探测地址为：

   ```plaintext
   hash_i(key) = (hash(key) + i²) % table_size
   hash_i(key) = (hash(key) - i²) % table_size
   ```

   （交替探测正、负平方增量，避免单向聚集）

   2. 实现过程示例

   沿用上面的哈希表和哈希函数，插入关键字 `{12, 23, 34, 45, 56}`：

   - 12 % 11 = 1 → 存入地址 1。
   - 23 % 11 = 1 → 冲突，探测 `1+1²=2` → 存入地址 2。
   - 34 % 11 = 1 → 冲突，探测 `1+1²=2`（冲突）→ 探测 `1-1²=0` → 存入地址 0。
   - 45 % 11 = 1 → 冲突，探测 `1+1²=2`（冲突）、`1-1²=0`（冲突）、`1+2²=5` → 存入地址 5。
   - 56 % 11 = 1 → 冲突，探测 `1+1²=2`（冲突）、`1-1²=0`（冲突）、`1+2²=5`（冲突）、`1-2²=-3 → 8` → 存入地址 8。

   3. 优缺点

   - **优点**：有效避免**初级聚集**—— 探测地址是平方数，不会形成连续的占用块，分布更均匀。
   - **缺点**：
     1. 可能产生**次级聚集（Secondary Clustering）**—— 具有相同原始哈希地址的关键字，探测路径完全相同（因为增量仅与 `i` 有关）。
     2. 哈希表长度需满足 `table_size=4k+3`（k 为整数）的质数，才能保证探测到所有位置。
     3. 实现比线性探测复杂，探测地址不连续（缓存友好性差）。

### 再哈希法

**核心思想**：当关键字 `key` 通过第一个哈希函数计算的地址被占用时，**不依赖固定的探测增量**（如开放定址法的线性 / 平方增量），而是通过**第二个（或多个）哈希函数动态计算探测增量**，然后按照增量序列继续探测空位置，直到找到可用地址。

1. **核心公式与原理**

   再哈希法的探测地址公式是开放定址法的扩展，通用形式为：

   ```plaintext
   hash_i(key) = (hash1(key) + i * hash2(key)) % table_size
   ```

   其中：

   - `hash1(key)`：**主哈希函数**，用于计算关键字的原始哈希地址（如 `key % table_size`）。
   - `hash2(key)`：**次哈希函数**，用于计算探测的**增量步长**（核心是避免步长为 0，且与表长度互质，保证能探测到所有位置）。
   - `i`：探测次数（`i=0,1,2,...`，`i=0` 对应原始地址）。
   - `table_size`：哈希表长度（通常取质数，保证 `hash2(key)` 与表长度互质）。

   **关键要求：次哈希函数 `hash2(key)` 的设计**

   为了保证探测序列能覆盖哈希表的所有位置，`hash2(key)` 必须满足两个条件：

   1. `hash2(key) ≠ 0`（避免步长为 0，导致无限循环探测同一地址）。
   2. `hash2(key)` 与 `table_size` **互质**（即最大公约数为 1，确保探测序列不会重复，能遍历所有位置）。

   常见的次哈希函数设计：

   - `hash2(key) = prime - (key % prime)`（`prime` 是小于 `table_size` 的质数）。
   - `hash2(key) = 1 + (key % (table_size - 2))`（保证步长为正整数，且与 `table_size` 互质）

2. **优缺点分析**

   1. 优点

   - **无聚集问题**：由于探测增量由次哈希函数动态计算，不同关键字的探测路径几乎无交集，彻底解决了线性探测的**初级聚集**和平方探测的**次级聚集**。
   - **探测效率高**：探测次数少，分布均匀，适合大规模数据。
   - **空间利用率高**：与开放定址法一致，无需额外存储空间（如链地址法的链表）。

   2. 缺点

   - **计算开销增加**：需要设计并计算两个哈希函数，比线性探测和平方探测的计算量更大。
   - **次哈希函数设计复杂**：次哈希函数的设计直接影响探测效果，需满足 “非 0、与表长度互质” 等条件，对开发者要求较高。
   - **删除困难**：与开放定址法一致，不能直接删除关键字，需使用 “墓碑（Tombstone）” 标记，否则会破坏探测序列。

3. **企业级应用**

   再哈希法是开放定址法中性能最优的策略，在实际开发中应用广泛，尤其是对性能和空间要求都较高的场景：

   1. **高性能哈希表**：如 C++ 的`unordered_map`（底层实现为哈希表，当负载因子较高时，使用再哈希法减少冲突）。
   2. **数据库索引**：数据库的哈希索引（如 MySQL 的 MEMORY 存储引擎）使用再哈希法解决冲突，保证查询效率。
   3. **缓存系统**：如 Redis 的哈希表（当哈希表较小时，使用开放定址法；当哈希表较大时，切换为链地址法，但再哈希法是备选方案）。
   4. **嵌入式系统**：再哈希法无需额外内存，适合内存资源有限的嵌入式设备，且无聚集问题，性能稳定。

### 拉链法

**核心逻辑**：数组+链表/红黑树

**底层机制**：

- **桶数组**：底层是`Node[] table`（`Node`是 HashMap 的基础节点类，包含`hash`、`key`、`value`、`next`指针）。
- **冲突解决层**：
  - 当桶内元素≤8 个时，用**单向链表**存储冲突元素；
  - 当桶内元素≥8 个且数组长度≥64 时，转为**红黑树**（`TreeNode`），降低查询时间复杂度（从 O (n)→O (logn)）；
  - 当树内元素≤6 个时，回退为链表，平衡性能与空间。

**哈希计算机制**：

步骤 1：计算 key 的原始哈希值

通过`Object.hashCode()`获取 key 的原始哈希值（如 String 的`hashCode()`是基于字符的累加哈希，Integer 的`hashCode()`是自身值）。

步骤 2：扰动函数（哈希值优化）

为了避免原始哈希值的高位特征未被利用，`HashMap`对原始哈希值做**扰动处理**（JDK1.8 简化版）：

```java
static final int hash(Object key) {
    int h;
    // 1. 若key为null，哈希值为0；否则取key的hashCode
    // 2. 异或：h ^ (h >>> 16) —— 让高16位与低16位混合，增强哈希值的散列性
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

步骤 3：计算桶下标

通过哈希值对桶数组长度取模，确定 key 对应的桶位。为了提升效率，`HashMap`要求桶数组长度为 2 的幂，因此用**位运算替代取模**（`hash & (length-1)` 等价于 `hash % length`，且位运算更快）：

```java
// 假设桶数组长度为length（2的幂），hash为步骤2的扰动后哈希值
int bucketIndex = hash & (length - 1);
```

**拉链法核心操作**：

1. **插入**：

   步骤 1：计算 key 的哈希值（扰动处理）

   插入的第一步是通过哈希函数计算 key 的最终哈希值，目的是让 key 的哈希分布更均匀，减少冲突：

   ```java
   static final int hash(Object key) {
       int h;
       // 核心逻辑：key的hashCode() 异或 其高16位（混合高低位，增强散列性）
       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
   }
   ```

   - 特殊处理：key 为`null`时哈希值固定为 0，会被放入桶数组的第 0 位；
   - 扰动原因：避免原始 hashCode 的高位特征未被利用（数组长度通常较小，取模时高位会被忽略）。

   步骤 2：检查桶数组是否为空，为空则扩容

   若桶数组（`table`）未初始化或长度为 0，先执行`resize()`方法初始化 / 扩容：

   ```java
   if ((tab = table) == null || (n = tab.length) == 0)
       n = (tab = resize()).length; // resize()返回初始化后的桶数组
   ```

   - 首次初始化：桶数组长度设为默认 16，阈值 = 16×0.75=12；
   - 扩容逻辑：后续详细说明，此处仅保证桶数组可用。

   步骤 3：计算桶下标，判断桶是否为空

   通过**位运算**计算 key 对应的桶下标（替代取模，效率更高），并检查该桶是否有元素：

   ```java
   int i = (n - 1) & hash; // n是桶数组长度（2的幂），n-1的二进制全1，与hash按位与等价于hash%n
   Node<K,V> p = tab[i];   // 桶首节点
   
   // 情况1：桶为空，直接新建Node放入该桶
   if (p == null) {
       tab[i] = newNode(hash, key, value, null);
   }
   ```

   - 位运算优势：`hash & (n-1)` 比 `hash % n` 快数倍，且仅当 n 为 2 的幂时等价；
   - 新节点：`newNode()`创建基础链表节点（包含 hash、key、value、next 指针）。

   步骤 4：桶不为空，处理哈希冲突（核心）

   若桶首节点（`p`）不为空，说明发生哈希冲突，分 3 种情况处理：

   子步骤 4.1：桶首节点与插入 key 完全匹配

   先检查桶首节点是否是目标 key（hash 相等 + equals 匹配），若是则直接标记为待覆盖节点：

   ```java
   Node<K,V> e; K k;
   // hash相等（初步筛选） + （引用相等 或 equals匹配）（精准匹配）
   if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
       e = p; // 标记该节点，后续覆盖value
   }
   ```

   子步骤 4.2：桶内是红黑树，执行树插入

   若桶首节点是`TreeNode`类型（说明桶内已转红黑树），调用红黑树的插入方法：

   ```java
   else if (p instanceof TreeNode) {
       // 插入红黑树，返回匹配的节点（若存在则e非空，否则e为空）
       e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
   }
   ```

   - 红黑树插入规则：
     1. 按 hash 值比较节点大小（hash 小的放左子树，大的放右子树）；
     2. hash 相等时，优先用`Comparable`接口比较，无则用系统哈希值兜底；
     3. 插入新节点（默认红色），若破坏红黑树规则，执行旋转 + 变色恢复平衡；
     4. 返回值：若树中已存在该 key，返回对应节点；否则返回 null。

   子步骤 4.3：桶内是链表，遍历链表处理

   若桶内是普通链表，遍历链表直到尾部或找到匹配 key：

   ```java
   else {
       // binCount：链表长度计数器
       for (int binCount = 0; ; ++binCount) {
           // 子步骤4.3.1：遍历到链表尾部，插入新节点
           if ((e = p.next) == null) {
               p.next = newNode(hash, key, value, null); // 尾插法（JDK1.8新增）
               // 检查链表长度是否≥8，触发转红黑树
               if (binCount >= TREEIFY_THRESHOLD - 1) { // TREEIFY_THRESHOLD=8，binCount从0开始
                   treeifyBin(tab, hash); // 链表转红黑树
               }
               break;
           }
           // 子步骤4.3.2：遍历中找到匹配key，跳出循环
           if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
               break;
           }
           // 子步骤4.3.3：未找到，继续遍历下一个节点
           p = e;
       }
   }
   ```

   - 尾插法：JDK1.8 取代 JDK1.7 的头插法，解决扩容时的链表环问题；
   - 转树逻辑：`treeifyBin()`先检查数组长度≥64，否则先扩容而非转树（数组过小是冲突主因）。

   步骤 5：覆盖已有 key 的 value（若存在）

   若步骤 4 中找到匹配的 key（`e != null`），覆盖其 value 并返回旧值：

   ```java
   if (e != null) {
       V oldValue = e.value;
       // onlyIfAbsent=false（默认）：允许覆盖；true：仅当旧值为null时覆盖
       if (!onlyIfAbsent || oldValue == null) {
           e.value = value;
       }
       afterNodeAccess(e); // 空方法，LinkedHashMap重写用于维护访问顺序
       return oldValue; // 插入完成，返回旧值
   }
   ```

   步骤 6：新增节点后，判断是否触发扩容

   若插入的是新节点（未覆盖旧值），更新元素数量并检查是否超过阈值：

   ```java
   ++modCount; // 快速失败（fail-fast）的修改计数器
   if (++size > threshold) {
       resize(); // 扩容：数组长度翻倍，重新分布所有元素
   }
   afterNodeInsertion(evict); // 空方法，LinkedHashMap重写用于删除最久未使用元素
   return null; // 新增节点，返回null
   ```

   - 扩容核心：
     1. 新数组长度 = 旧长度 ×2（仍为 2 的幂）；
     2. 元素迁移：按`hash & 旧长度`判断，结果为 0 则留在原下标，否则移到`原下标+旧长度`；
     3. 红黑树 / 链表节点分别迁移，保证结构不变。

2. **查询**

   核心逻辑：计算 hash→定位桶→遍历链表 / 红黑树匹配 key：

   ```java
   public V get(Object key) {
       Node<K,V> e;
       // 1. 计算hash，调用getNode方法
       return (e = getNode(hash(key), key)) == null ? null : e.value;
   }
   
   final Node<K,V> getNode(int hash, Object key) {
       Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
       // 1. 桶数组非空且目标桶有元素
       if ((tab = table) != null && (n = tab.length) > 0 &&
           (first = tab[(n - 1) & hash]) != null) {
           // 2. 匹配桶首节点
           if (first.hash == hash &&
               ((k = first.key) == key || (key != null && key.equals(k))))
               return first;
           // 3. 遍历后续节点
           if ((e = first.next) != null) {
               // 3.1 红黑树查询
               if (first instanceof TreeNode)
                   return ((TreeNode<K,V>)first).getTreeNode(hash, key);
               // 3.2 链表遍历
               do {
                   if (e.hash == hash &&
                       ((k = e.key) == key || (key != null && key.equals(k))))
                       return e;
               } while ((e = e.next) != null);
           }
       }
       return null;
   }
   ```

3. **删除**

   核心逻辑：定位桶→找到待删除节点→修改链表 / 树的指针（链表只需断开节点，红黑树需调整平衡），**无需墓碑标记**（这是拉链法对比开放定址法的核心优势）。

**扩容机制**：

- 扩容触发条件：元素数量≥阈值（初始容量 × 负载因子，如 16×0.75=12）；

- 扩容流程：

  1. 新建数组（长度翻倍，仍为 2 的幂）；

  2. 遍历旧数组的每个桶，将链表 / 红黑树的元素迁移到新数组：

     由于数组长度翻倍，桶下标仅需判断`hash & 旧长度`是否为 0：为 0 则留在原下标，不为 0 则下标 = 原下标 + 旧长度（JDK1.8 的优化，避免重新计算 hash）；

  3. 替换旧数组，更新阈值。

**线程安全问题的根源**

- `HashMap`非线程安全的具体表现：
  - 多线程`put`时，扩容导致链表环（JDK1.7），遍历会出现死循环；
  - 多线程`put/get`时，数据丢失（如两个线程同时插入同一桶，覆盖对方的节点）；
- `ConcurrentHashMap`的线程安全实现（JDK1.8）：
  - 桶为空时，用 CAS 插入节点；
  - 桶不为空时，用`synchronized`锁定桶首节点（而非分段锁，粒度更细）；
  - 红黑树节点锁定整棵树，保证操作原子性。

**优化措施**：

**场景 1：查询多、插入少 → 降低负载因子（0.6）**

目标：减少哈希冲突，提升查询效率（牺牲少量内存）。

```java
import java.util.HashMap;

public class HashMapTuningExample {
    public static void main(String[] args) {
        // 1. 预估业务最大数据量（比如最多存 1000 条数据）
        int expectedMaxSize = 1000;
        // 2. 自定义负载因子 0.6，计算初始容量（避免扩容）
        // 公式：初始容量 = 预估容量 / 负载因子（向上取整，且保证是 2 的幂）
        int initialCapacity = (int) Math.ceil(expectedMaxSize / 0.6);
        // 3. 初始化 HashMap，指定负载因子 0.6
        HashMap<Long, String> queryOptMap = new HashMap<>(initialCapacity, 0.6f);

        // 业务操作：查询多、插入少
        queryOptMap.put(1001L, "用户A");
        queryOptMap.put(1002L, "用户B");
        // 大量查询操作（冲突少，效率高）
        String userName = queryOptMap.get(1001L);
    }
}
```

**场景 2：内存紧张、插入多 → 提高负载因子（0.85）**

目标：提升空间利用率，减少内存占用（允许少量冲突，插入效率优先）。

```java
public class HashMapTuningExample {
    public static void main(String[] args) {
        // 预估最大数据量 10000 条，内存紧张时提高负载因子到 0.85
        int expectedMaxSize = 10000;
        int initialCapacity = (int) Math.ceil(expectedMaxSize / 0.85);
        // 自定义负载因子 0.85
        HashMap<Long, Object> memoryOptMap = new HashMap<>(initialCapacity, 0.85f);

        // 批量插入（提前初始化足够容量，避免扩容）
        for (long i = 1; i <= expectedMaxSize; i++) {
            memoryOptMap.put(i, "业务数据" + i);
        }
    }
}
```

## 剖析HashMap源码

### putVal

**1. 局部变量减少全局访问开销**

代码开头 `tab = table`：把全局的 `table` 数组赋值给局部变量 `tab`，后续操作都用局部变量。

✅ 原因：局部变量存在栈上，访问速度远快于堆上的全局变量（JVM 优化）。

✅ 借鉴：高频访问的全局变量，可先赋值给局部变量再操作。

接下来我们通过一个小例子来验证一下这段代码的实际重要性。

```java
public class Main {
    // 全局变量：模拟HashMap的table数组（堆内存中）
    private static int[] globalArray;

    static {
        // 数组长度设为1亿，保证循环次数足够，耗时差异可观测
        globalArray = new int[100_000_00];
        // 填充随机值（避免JVM优化掉空访问）
        for (int i = 0; i < globalArray.length; i++) {
            globalArray[i] = i;
        }
    }

    // 改造 accessGlobalVar 和 accessLocalVar 方法，多次访问变量本身（而非数组元素）
    public static long accessGlobalVar() {
        long start = System.nanoTime();
        long sum = 0;
        Random random = new Random();
        // 重点：循环访问全局变量的引用（而非数组内容），放大寻址开销
        for (int i = 0; i < 100_000_00; i++) {
            int index = random.nextInt(globalArray.length);
            sum += globalArray[index]; // 每次都要访问堆上的globalArray
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long accessLocalVar() {
        long start = System.nanoTime();
        int[] localArray = globalArray; // 仅一次堆访问
        int sum = 0;
        Random random = new Random();
        for (int i = 0; i < 100_000_00; i++) {
            int index = random.nextInt(localArray.length);
            sum += localArray[index]; // 每次访问栈上的localArray
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {

        System.out.println("===== 正式测试 =====");
        // 多次测试取平均值，减少误差
        long globalTotal = 0;
        long localTotal = 0;
        int testTimes = 1;

        for (int i = 0; i < testTimes; i++) {
            long globalTime = accessGlobalVar();
            long localTime = accessLocalVar();
            globalTotal += globalTime;
            localTotal += localTime;
        }

        // 输出平均耗时
        System.out.println("\n===== 平均耗时 =====");
        long globalAvg = globalTotal / testTimes;
        long localAvg = localTotal / testTimes;
        System.out.println("全局变量平均耗时：" + globalAvg + " 纳秒");
        System.out.println("局部变量平均耗时：" + localAvg + " 纳秒");
        System.out.println("平均优化提升：" + (1 - (double) localAvg / globalAvg) * 100 + "%");
    }
}

```

