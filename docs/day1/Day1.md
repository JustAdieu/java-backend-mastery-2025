---
Day1：参数传递语义
Author: Adieu
Date:2025/11/26
---

[toc]

# Day1:参数传递语义

## 前言

>在c语言中我们学习过参数传递会出现两种类型：值传递和引用传递，而在Java中参数传递仅有值传递，但由于传递参数时候基本数据类型和引用数据类型的存储方式不同导致我们出现了引用传递的错觉。今天我们就通过从存储方式角度来探寻Java中对于参数传递的本质。

## 引入

显然，作为一门成熟的语言，Java在使用方法时必然会允许通过一个方法调用对调用者中的一个变量进行赋值或者是修改值。此时我们不妨先看看下面两个例子：

1. ```java
   public class demo {
       public static void main(String[] args) {
           int a = 1;
           System.out.println(a);//输出1
           change(a);
           System.out.println(a);//输出1
       }
       public static void change(int n){
           n = 2;
       }
   }
   ```

2. ```java
   
   public class demo {
       public static void main(String[] args) {
           Person a = new Person("张三", 18);
           System.out.println(a.toString());//输出Person{name='张三', age=18}
           change(a);
           System.out.println(a.toString());//输出Person{name='王五', age=20}
       }
       public static void change(Person n){
           n.setName("王五");
           n.setAge(20);
       }
   }
   
   class Person{
       private String name;
       private int age;
   
       @Override
       public String toString() {
           return "Person{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public int getAge() {
           return age;
       }
   
       public void setAge(int age) {
           this.age = age;
       }
   
       public Person(String name, int age) {
           this.name = name;
           this.age = age;
       }
   }
   ```

   通过上述两个例子我们很快可以发现，貌似是Java内部有一种magic，可以自动将基本数据类型和引用数据类型给区分开来，然后分别使用了值传递和引用传递，但是此时我必须要澄清一点Java中仅有值传递。而我们也会探寻并非是有什么magic将基本数据类型和引用数据类型区分，而是两者的存储方式不同所导致的结果。接下来我们先回顾一下基本数据类型和引用数据类型的存储方式，再探寻为什么Java中参数传递的本质。

## 正文

### 基本数据类型和引用数据类型的存储方式

​	首先，谈到存储方式我们就离不开硬件，基本数据类型是直接存储在栈中，而引用数据类型是将内存地址存储在栈中，引用类型的本体存储在堆中。至于怎么存储在栈中和堆中又要涉及JVM，那为了方便了解，先将JVM的分配机制作为后续扩展知识来了解。

### Java中参数传递的本质

​	那么这个时候我们只需要结合Java中参数传递的本质为传递一个栈中对应参数的副本，这个时候我们就可以很清楚其本质是做一件非常寻常的事情，只是由于设计者的巧妙构思使得出现了这种魔法。上述例题中，对应的例子1，由于是基本数据类型，JVM自动分配到栈中为其本身值，所以哪怕传递了一个值的副本到方法中，这个方法都无法改变其原来的值。而例子2中，由于是传递引用数据类型在堆中的地址值，所以我们通过调用这个副本所指向的对象的方法可以改变堆中这个值。

### 你真的懂了吗？

这个时候我们不妨拿出几个例子，来判断一下你是否是真的懂了.

```java
 Person person2 = new Person("张三", 18);
 System.out.println(person2.toString());//Person{name='张三', age=18}
 changePersonByRef(person2);
 System.out.println(person2.toString());//Person{name='张三', age=18}

public static void changePersonByRef(Person n){
     n = new Person("王五", 20);
}
```

```java
String str = "hello world";
System.out.println(str);//hello world
changeStr(str);
System.out.println(str);//hello world

Integer num2 = 10;
System.out.println(num2);//10
changeInteger(num2);
System.out.println(num2);//10

public static void changeInteger(Integer n){
     n = 20;
}
public static void changeStr(String n){
     n = "hello java";
}
```

对于第一个，由于传递的地址副本被新对象给覆盖了，所以无法对原对象进行修改。对于第二个你是否会感到疑惑呢，明明String和Integer所创建的对象都是引用数据类型为什么无法改变呢，这是今天的扩展知识1，你现在只需要记住String这类被设计为**不可变**。所以会出现基本数据类型的特点，但是有道是“计算机中无黑魔法”，我们不妨来探寻一下根本原因和设计理念。