---
Day3
Author:Adieu
Date:2025/12/11
---

[toc]

# 多态

## 前置基础：多态实现的前提条件

通过前一章的学习，我们知道了多态的实现可以依赖于继承关系的实现，通过前面案例的实现我们也能直观感受到由继承关系实现的多态的便捷。但是，在今天的深入学习中，我们将会了解到新的多态实现，这个实现是依赖接口进行实现的，先看这个简单的例子：

```java
public interface Flyable {
    public void fly();
}

public class Bird implements Flyable{
    @Override
    public void fly() {
        System.out.println("鸟儿在飞");
    }
}

public class Rocket implements Flyable{
    @Override
    public void fly() {
        System.out.println("火箭在飞");
    }
}

public static void main(String[] args) {
    Flyable f1 = new Bird();
    Flyable f2 = new Rocket();
    f1.fly();//鸟儿在飞
    f2.fly();//火箭在飞
}
```

上面这个例子就是依赖接口实现的多态。乍一看，我们感觉接口实现的多态似乎有点不太合理，这种感觉是源于我们之前通过继承实现的多态和这里有一些根本上的区别导致的，这两种实现到底是谁更优越是接下来我们需要讨论的重点，这关系着以后我们开发时候的行为倾向。

### 接口实现和继承实现的区别

首先，形式上很容易区分，联系之前的继承学习我们可以明确的是，通过继承实现的多态，需要：父类有抽象 / 可重写的方法；子类重写该方法；父类引用指向子类对象（动态绑定）。换句话说就是要实现多态的前提是，这个对象既要是这个子类本身的实现又需要继承来自父类的属性。这种多态实现并不是简单的方法动态绑定。我们在需要实现多态在行为差异化中包含子类的属性差异化时候就特别合适了，比如说父类是Person，子类是Employee和Student时候，他们都有共有的Person属性，而我们在Person中定义的抽象方法doSomething，在子类实现方法时候需要使用到继承自父类中的属性例如姓名之类的，这个时候我们的实现方法需要继承这种层级扩展关系就适合使用继承。

[^注意]: 如果上面的例子中只是简单的需要子类都实现doSomething而并非需要借助父类中继承的属性方法的话，那不如直接使用接口实现的多态。

而接口实现的多态就直接很多，接口更像是共性方法的提取，如果实现这个接口就是方法的继承和实现。我们在上面的例子中往往不会让Bird和Rocket继承同一个类，这不符合常理，但是他们又具有共性，所以我们提供一个接口Flyable，让他们实现其中的方法fly。

### 从多态的设计初衷出发来选择

多态的设计初衷在于：`同一行为，基于不同的对象有不同的具体实现。是行为的抽象统一和实现的动态差异化。`基于这个设计初衷我们不难发现多态其实强调的是不同对象的行为动态差异化来实现代码在行为实现与定义上的解耦。就这个设计初衷来看的话，多态强调的往往是行为，那么在实现多态的时候为了不必要的耦合，我们往往会选择接口来实现多态。

以下是接口实现的优势点：

1. 接口解耦了继承关系和行为，让无关类也能实现统一的行为。
2. 支持多实现，突破了单继承的限制，灵活组合多个行为。
3. 避免了继承可能会带来的实现污染，让多态更加可靠。

## 进阶应用：多态的”实际价值“

从上面的设计初衷中我们可以很好感受到多态所倡导的解耦性和扩展性。那么最好的体验场景还得回到具体的案例中来体验。

### 多态数组/集合

观察以下代码感受一下：

```java
public static void main(String[] args) {
    Flyable f1 = new Bird();
    Flyable f2 = new Rocket();
    Flyable f3 = new Huamei();
    List<Flyable> lists = new ArrayList<>();
    lists.add(f1);
    lists.add(f2);
    lists.add(f3);
    for (Flyable flyable : lists) {
        flyable.fly();//true
    }
}
```

我们通过多态实现了统一管理不同的子类对象，多态在其中的价值在于它十分符合开闭原则。试想一下以下常见的场景：

1. 现在需要添加一个新的类，比如说plane类，我们需要做的只有让plane实现这个Flyable 这个接口，然后再创建一个实例并添加到数组中来，如果我们创建的所有实例是从前端或者其他方法传递来的话，我们甚至不需要改动这段代码中任何地方。
2. 假设现在我们需要对其中一个类进行重命名，想让Huamei变为Maque，只需要改动Maque的实现地方以及类内部命名，对于方法的调用毫不影响。
3. 放开来讲，针对多态数组而言，我们只需要关注你是否针对Flyable所在的部分进行了改动，如果没有我们毫不关心你是否对于其它地方进行了改动（前提是编译期正常）。如果fly方法不是你想调用的，我们也只需要修改这一行代码。

### 多态作为方法的参数/返回值

```java
public static void makeItFly(Flyable f){
    f.fly();
}

Flyable f1 = new Bird();
Flyable f2 = new Rocket();
Flyable f3 = new Huamei();
makeItFly(f1);//true
makeItFly(f2);//true
makeItFly(f3);//true
```

类似上面的多态数组，我们实现一次定义，多次实现。

## 总结

我们通过多态实现了对扩展开放，对修改关闭，让代码更加灵活与可扩展，降低了代码的耦合度。

# 接口

## 接口的特性

1. 接口可以声明为变量，但是不能生成一个实例

```java
public static void main(String[] args){
    Flyable f1 = new Bird();
}
```

2. 可以通过instanceof来检查对象是否实现了某个接口

```java
Bird b1 = new Bird();
if(b1 instanceof Flyable){
    System.out.println("true");//true
}
```

3. 类似于类的继承关系，我们也可以在接口中实现扩展：

```java
public interface Actions extends Flyable{
    void eat();
}
```

4. 尽管接口中我们不能包含实例域或者静态方法，我们却可以包含常量，和接口中的方法一样自动被设置为public，常量自动被设置为public static：

```java
public interface Flyable {
    int FLYTIME = 10;
    void fly();
}
```

## 克隆

### 简介

在Java中的Java.lang.Object类中提供了`protected Object clone() throws CloneNotSupportedException`方法，直接调用会出现异常，需要满足两个条件：

1. 目标类需要实现`Cloneable`接口（标记接口，但是Cloneable本身无任何抽象方法）
2. 重写clone()方法，将访问权限修改为public，同时还需要处理异常。

此时根据我们重写clone方法的不同区分为浅克隆和深克隆。

### 浅克隆

浅克隆的形式通常为：

```java
@Override
public Person clone(){
    try{
        return (Person)super.clone();
    }catch(CloneNotSupportedException e){
        throw new AssertionError();
    }
}
```

通过调用Object中的clone方法来实现，所以我们不妨看得出来Object的clone本质是浅克隆。针对于浅克隆而言，仅能保障基本数据类型以及不可变类的独立性，而引用类型仍然是共享的。

```java
Person p1 = new Person("张三", 18,new Date("1991.1.1"));
Person p3 = p1.clone();
p3.setName("王五");
p3.getBirthDate().setDate("1992.1.1");
System.out.println(p1.getName());//张三
System.out.println(p3.getName());//王五
System.out.println(p1.getBirthDate().getDate());//1992.1.1
System.out.println(p3.getBirthDate().getDate());//1992.1.1
```

> 值得注意的是如果这里的`p3.getBirthDate().setDate("1992.1.1")；`修改为`p3.setBirthDate(new Date("1992.1.1"));`，这样并不会改变p1的birthDate，因为这样做的实质操作是将p3的birthDate引用对象改变，并非是修改原有的引用对象，这样做并不能说明浅克隆的本质。

### 深克隆

相较于浅克隆而言，深克隆要做的就是将引用类型也clone一份，规范化的操作是将引用类型也手动重写一份clone，并且手动克隆所有引用类型成员。

```java
//Person中
@Override
public Person clone(){
    try{
        Person p = (Person)super.clone();
        p.birthDate = birthDate.clone();
        return p;
    }catch(CloneNotSupportedException e){
        throw new AssertionError();
    }
}
//Date中
@Override
public Date clone(){
    try {
        return (Date)super.clone();
    } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
    }
}

public static void main(String[] args){
    Person p1 = new Person("张三", 18,new Date("1991.1.1"));
    Person p3 = p1.clone();
    p3.setName("王五");
    p3.getBirthDate().setDate("1992.1.1");
    System.out.println(p1.getName());//张三
    System.out.println(p3.getName());//王五
    System.out.println(p1.getBirthDate().getDate());//1991.1.1
    System.out.println(p3.getBirthDate().getDate());//1992.1.1
}
```

### 扩展知识：为什么String，Integer这些类要设计为不可变？

1. **支持常量池的复用**

​	Java中, String有串池，Integer等等封装类有缓存池，这些常量池通过复用相同值的对象，减少了内存占用和GC压力。但是这一切的前提都是他们是不可变的，试想一下，如果串池中的字符串是可变的话，就会出现修改一个字符串，别的引用的字符串也会改变。这样子常量池会失去意义。

2. **支持哈希表的Key安全**

​	 通过前面的学习我们知道了哈希表的查找离不开hashcode，而hashcode的计算是基于String，Integer的内部状态实现的，所以如果出现这些类是可变的话，那么key就也会变化，但是后续我们就无法通过hashcode找到原来存储的value了

3. **保障并发安全**

​	在多线程的的应用场景中，可变对象的状态可能会被多个线程进行修改，这会导致数据不一致，解决这个问题需要额外加锁，这需要额外的性能开销。而不可变对象具有天然的线程安全性，我们无需担心这一点的发生，更不需要添加线程锁。减少了锁开销和并发bug。

4. **简化API的设计**

​	由于这些类的不可变性，使得我们在后续使用到这些类实现的实例时候，可以不必担心由于修改对应的对象所导致的逻辑出错，开发的时候也无需担心被他人调用修改值。

## 回调

回调是一种编程设计模式，这里由于第一次学习相关的知识，所以我们通过具体的案例来理解一下回调的使用，再在后续谈及一下回调的好处。但是由于回调这个知识点要想了解全面的话又离不开线程，所以我们会简单了解一下线程的相关知识。

首先回调按照执行的时机分为同步回调和异步回调：

### 同步回调

首先，我们先来明确一下什么是同步回调，核心在于主逻辑和回调逻辑在同一个线程中顺序执行，主逻辑必须等回调执行完成才可以继续往下走。

#### 核心特征

1. **单线程执行**

​	主逻辑和回调逻辑共用一个线程，没有线程切换，全程顺序执行。

2. **主逻辑阻塞等待**

​	主逻辑调用回调方法后，会暂停自身的执行，等待回调结束。

#### 实现方法

1. 方法一：接口+匿名内部类

   该方法的实现主体是接口作为回调方法的载体，在主逻辑中回调由匿名内部类实现的接口的方法。

   ```java
   public class Test {
       public static void main(String[] args){
           MainBus mb = new MainBus();
           mb.mainLogic(new TestInterface() {
               @Override
               public void callBack() {
                   System.out.println("回调逻辑开始...");
                   System.out.println("回调逻辑结束...");
               }
           });
       }
   }
   interface TestInterface{
       void callBack();
   }
   class MainBus{
       public void mainLogic(TestInterface callBackLogic){
           System.out.println("主逻辑开始...");
           callBackLogic.callBack();
           System.out.println("主逻辑结束...");
       }
   }
   ```

2. 方法二：函数式接口+Lambda

   该方法的实现需要在Java8以及以后实现，借助新的特性Lambda

   ```java
   public class Test {
       public static void main(String[] args){
           MainBus mb = new MainBus();
           mb.mainLogic(()-> System.out.println("回调逻辑开始..."));
       }
   }
   interface TestInterface{
       void callBack();
   }
   class MainBus{
       public void mainLogic(TestInterface callBackLogic){
           System.out.println("主逻辑开始...");
           callBackLogic.callBack();
           System.out.println("主逻辑结束...");
       }
   }
   ```

3. 方法三：普通类实现（不推荐）

   不用接口，直接传递普通类对象，主逻辑调用该类的方法。缺点是主逻辑与回调类强耦合，灵活性差。

   ```java
   // 普通回调类：封装回调逻辑
   class CommonCallback {
       public void onCallback(String msg) {
           System.out.println("普通类回调：" + msg);
       }
   }
   
   // 主逻辑类：直接依赖 CommonCallback
   class MainLogic {
       public void doBusiness(String param, CommonCallback callback) {
           System.out.println("主逻辑：处理业务——" + param);
           callback.onCallback("回调触发：" + param);
           System.out.println("主逻辑：业务流程结束");
       }
   }
   
   // 调用侧
   public class Test {
       public static void main(String[] args) {
           MainLogic logic = new MainLogic();
           logic.doBusiness("测试参数", new CommonCallback());
       }
   }
   ```

   **由于异步回调涉及到多线程的知识，斟酌下还是放到后面来讲。**

## 内部类

我们将为什么使用内部类通常分为三个原因：

* 内部类方法可以访问该类定义所在的作用域中的数据，包括私有数据
* 内部类可以对同一个包中的其他类隐藏
* 当想要定义一个回调函数的时候，我们可以使用匿名内部类来替代。

