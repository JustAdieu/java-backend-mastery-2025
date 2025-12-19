---
week2	day2
author	adieu
date	2025/12/19
---

[toc]

# LinkedList

## 底层结构

双向链表的每个节点（`Node` 内部类）包含三个属性：

```java
private static class Node<E> {
    E item;        // 节点存储的元素
    Node<E> next;  // 后继节点引用
    Node<E> prev;  // 前驱节点引用
    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

- 链表头节点（`first`）：前驱为 `null`
- 链表尾节点（`last`）：后继为 `null`
- 每个节点可通过 `prev/next` 快速访问前后节点

## 核心特性

- **非连续内存**：元素不存储在连续数组中，节点通过引用关联，内存利用率更高（无需预分配空间）。
- **无容量限制**：默认无初始容量（ArrayList 初始容量 10），元素添加时动态创建节点，仅受内存限制。
- **双向遍历**：支持从头（`first`）或尾（`last`）遍历，实现了 `Deque` 接口，可作为双端队列使用。

**LinkedList 遍历优化**：避免用 `get(i)` 遍历，优先用迭代器 / 增强 for 循环（迭代器直接操作节点引用，时间复杂度 O (n)）：

```java
// 低效遍历
for (int i = 0; i < list.size(); i++) { list.get(i); }
// 高效遍历
for (E e : list) { ... }
Iterator<E> it = list.iterator();
while (it.hasNext()) { it.next(); }
```

**JDK 优化**：LinkedList 的 `get(i)` 会判断索引靠近头部 / 尾部，选择从近的一端遍历（但仍比 ArrayList 慢）。

**线程安全**：多线程场景需使用 `Collections.synchronizedList(new LinkedList<>())` 或 `CopyOnWriteArrayList`（后者更适合读多写少）。