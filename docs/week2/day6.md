---
week2	day6
author	adieu
date	2025/12/27
---

[toc]

# 单词计数器

[toc]

## 功能需求

设计一个工具类具备传入一段String时候能够单次统计结果并打印和长期统计结果并存储以便随时访问的功能。

设计类中包含一个私有域:`HashMap<String,Integer>`,实现了`Count`接口
两个静态方法:`public static Collection<String> countOnce(String)`
`public static void showCounter(Map<String,Integer>)`
一个全局方法:`public void insert(String)`

## 代码设计思路

现在我们很明确的即单词计数器是通过将某个String类型的数据存储在HashMap中Key为String，value是Integer。那么现在我们考虑到了代码的扩展性，后续可能不只是有单词计数器，所以设计了一个Count接口，采用面向接口的方式。其中包含了两个待实现方法，分别是show和count。在实现count方法的时候又考虑到了可能传入的是一段话所以最后我们还要通过使用正则表达式的方法来实现划分单词。最后就是具体过程了。
