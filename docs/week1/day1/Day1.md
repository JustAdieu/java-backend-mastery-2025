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

## 扩展知识

### 不可变性

从上面的例子中我们不妨发现Integer这些包装类和String同为引用数据类型，但是却具有不可变性，虽然参数传递的副本指向堆中的字符串常量池以及Integer缓存区。由于知识点较为繁多，先介绍一下Integer这类包装类的设计理念。

#### 包装类

首先需要明确的是基本数据类型由于其本身不可以直接参与到面向对象编程，所以为了弥补这个缺陷就设计了对应的包装类来进行对象化。由于每个包装类对应的核心成员均被private final进行修饰所以其值不具有可改变性，这也是为什么上述场景中会出现这种情况。
此外还有一些很有意思的点可以了解一下。

1. 缓存机制
   对于Integer，Character等等这类包装类，在间接或者直接使用`Integer.valueOf(int)`这个方法时候就会触发缓存，直接使用在缓存值域对应的值，对于Integer来讲是处于-128~127，需要注意的是如果是直接创建例如使用`new Integer(int)` 无论是否对应的int在不在缓存值域内都不会触发，而是在堆中和普通对象一样创建一个对象，同时也不会出现同一个值多次复用的情况，所以我们平时尽量多使用`Integer.valueOf(int)` 而不是直接创建对象，减少开销。

2. 包装类的优越性
   由于基本数据类型被包装为一个类了，所以其具有了支持泛型，支持集合框架，支持反射和序列化等等性质，同时内部也提供了大量的工具类供使用，例如常用的`Integer.parseint()` ，这些都是包装类的优越性。
3. 设计不可变形的必要性
   首先可以保证多线程安全以及数据安全，然后可以适配哈希表，在使用`hashCode()` 时候可以保障其值不变，避免哈希查询异常。

#### String

要了解什么是String就不得不提到串池：

1. 串池，是位于堆中的一个特殊区域，存储的是字符串对象的引用，本质是一个哈希表，key是string实际内容，value是对应堆中的对象。需要注意的是堆中有的字符串不一定在串池中存在，但在串池中存在的一定有对应的字符串对象存在。串池的创造有点像包装类中的缓存，但是区别于缓存的不可变性以及缓存存储的是其值本身，串池是可变的以及存储的是对象引用。我们可以通过串池达到对于同一字符串对象复用的效果，大大减少了内存以及cg开销。但是我们更需要知道的是串池的添加规则是哪些，通常分为自动添加和手动添加。自动添加发生在编译期，通过字面量的值来添加到串池中，对于已在串池中的直接将串池引用对象给其值，如下：
   ```java
   String str1 = "hello";//编译期添加到堆中以及串池中存储其堆中对应对象的引用
   String str2 = "hello";//编译期发现串池存在将对应的引用给予str2
   System.out.println(str1 == str2);//通过地址比较发现引用地址相同，所以结果为true
   
   String str3 = new String("hello");//new String("hello")其实创建了两个对象，第一个是字面量hello，然后new String()创建了一个堆中对象，指向了串池中的hello的引用地址。str3则是指向堆中的new String（）创建的对象
   System.out.println(str1 == str3);//false
   ```

   手动添加则需要使用`intern()`函数
   
   ```java
   String str4 = new String("hello");
   str4 = str4.intern();
   System.out.println(str1 == str4);// true
   String str5 = "he";
   String str6 = "llo";
   String str7 = str5 + str6;
   System.out.println(str7 == str1);//false
   str7 = str7.intern();
   System.out.println(str7 == str1);//true
   ```

Ok, 那么这个时候我们回到原来的问题，String的不可变性就体现在其底层中对于String这个类中的private final byte[]的核心成员以及对应code值private final.联系串池我们不妨发现如果String不可变形不存在就会出现hash可修改导致串池这个哈希表出现混乱. 另一方面，由于String内部也未提供private final byte[]的修改方法，所以我们无法通过除了反射以外的方法来修改其值。