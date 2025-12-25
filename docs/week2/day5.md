---
week2	day5
author	adieu
date	2025/12/25
---

[toc]

# 字符串处理

## StringBuilder的底层结构

**核心成员**：

- `char[] value`：存储字符的数组（与 `String` 不同，此数组可扩容，且非 `final`）。
- `int count`：当前已存储的字符数量。

### char[] value

**初始容量规则**：

- 无参构造：`new StringBuilder()` → `value` 数组初始长度为 16；
- 带字符串构造：`new StringBuilder("abc")` → `value` 数组初始长度 = 16 + 字符串长度（如 "abc" 则初始长度 19）；
- 带容量构造：`new StringBuilder(32)` → `value` 数组初始长度为指定的 32（按需指定可减少扩容次数）。

**扩容触发条件**：

当执行追加（`append`）、插入（`insert`）等操作时，若 `count + 新增字符数 > value.length`（数组剩余空间不足），则触发扩容。

触发扩容函数为AbstractStringBuilder中的ensureCapacityInternal函数，我们可以通过AbstractStringBuilder中的append函数来举例观察:
```java
public AbstractStringBuilder append(String str) {
        if (str == null) {
            return appendNull();
        }
        int len = str.length();
        ensureCapacityInternal(count + len);
        putStringAt(count, str);
        count += len;
        return this;
    }
```

进入到ensureCapacityInternal中：

```java
private void ensureCapacityInternal(int minimumCapacity) {
    // overflow-conscious code
    int oldCapacity = value.length >> coder;
    if (minimumCapacity - oldCapacity > 0) {
        value = Arrays.copyOf(value,
                newCapacity(minimumCapacity) << coder);
    }
}
```

这里的Arrays.copyOf是实现扩容的函数,而扩容大小的数量是来源于newCapacity函数计算得到的值，newCapacity:
```java
private int newCapacity(int minCapacity) {
    int oldLength = value.length;
    int newLength = minCapacity << coder;
    int growth = newLength - oldLength;
    int length = ArraysSupport.newLength(oldLength, growth, oldLength + (2 << coder));
    if (length == Integer.MAX_VALUE) {
        throw new OutOfMemoryError("Required length exceeds implementation limit");
    }
    return length >> coder;
}
```

**扩容计算逻辑（JDK 8 为例）**：

新容量 = 原容量 × 2 + 2

（例如：原容量 16 → 扩容后 34；原容量 34 → 扩容后 70，以此类推）

特殊情况：若计算出的新容量仍不足（如一次性追加超大字符串），则直接将新容量设为 `count + 新增字符数`（保证能容纳所有字符）。

**扩容底层操作**：

扩容不是在原数组上 “加长”（数组长度固定），而是：

1. 新建一个符合新容量的 `char[]` 数组；
2. 通过 `Arrays.copyOf()` 将原 `value` 数组的所有字符复制到新数组；
3. 将 `value` 引用指向新数组（原数组被 GC 回收）。

### int count

**核心作用**

- 表示 `value` 数组中**已存储的有效字符数量**（即 StringBuilder 实际的字符串长度）；
- 是 `length()` 方法的返回值（`public int length() { return count; }`）；
- 作为字符操作的 “指针”：追加字符时，新字符从 `value[count]` 位置开始写入，写入后 `count += 新增字符数`。

**关键特性**

- 初始值：创建 StringBuilder 时，`count = 0`（无参构造）；若传入初始字符串，则 `count = 初始字符串长度`；
- 仅记录有效字符：`value` 数组长度可能远大于 `count`（扩容后剩余空间未使用），`count` 只关注 “已用部分”；
- 可通过 `setLength(int newLength)` 修改：若 `newLength < count`，则截断字符（`count = newLength`）；若 `newLength > count`，则填充 `\0`（空字符）到 `value` 数组，`count = newLength`。

## 拼接性能测试

```java
public static void main(String[] args){
    String s1 = new String();
    StringBuilder s2 = new StringBuilder();
    StringBuffer s3 = new StringBuffer();
    long start = System.nanoTime();
    for(int i = 0; i < 100000; i++){
        s1 += "1";
    }
    System.out.println(System.nanoTime() - start + "ns");//668940500ns
    start = System.nanoTime();
    for(int i = 0; i < 100000; i++){
        s2.append("1");
    }
    System.out.println(System.nanoTime() -  start + "ns");//1546800ns
    start = System.nanoTime();
    for(int i = 0; i < 100000; i++){
        s3.append("1");
    }
    System.out.println(System.nanoTime() - start + "ns");//2380200ns
}
```

