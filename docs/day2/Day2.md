---
Day2:封装与继承
Author:Adieu
Date:2025/12/3
---

[toc]

# 封装与继承

## 封装

封装核心是「隐藏对象内部细节，仅通过公开接口交互」，通过这样的实现我们可以达到以下目的：

1. 数据安全：仅提供set方法供修改数据，可以避免外界直接修改数据。如下，我们实现了数据的保护：

```java
public static void main(String[] args) {
        Person p = new Person("Tom", 18);
        p.setAge(-1);//Invalid Age
	}
public void setAge(Integer age) {
        if(age < 0 || age > 100){
            System.out.println("Invalid Age");
        }
        else{
            this.age = age;
        }
    }
```

尽管我们从一个开发者的角度出发来看，我们完全可以不用将内部成员声明为private，针对赋值时候手动添加判断条件来操作，但是如果一段恶意代码来调用则不会想这么多，到这里我不得不联想到反射。如果有机会会在今天的扩展知识里面介绍一下这个黑科技。

2. 低耦合：引用前面这个例子，如果内部需要修改一下年龄检验条件，外部无需关心内部是如何实现的，降低了代码之间的耦合性。

**值得注意**：如果你进行了以下操作会在不经意中间破坏代码的封装性。

```java
public static void main(String[] args) {
        Person p = new Person("Tom", 18, new Address("China"));
        Address a = p.getAddress();
        a.setPosition("USA");
        System.out.println(p.getAddress().getPosition());//USA
    }
public Address getAddress() {
        return address;
    }
```

原因在于返回的是直接的引用对象，我们在外部可以绕过Person这个类中的set方法来进行修改private成员，这无疑破坏了封装性。弥补方法也很简单，就是返回时候返回一个克隆对象。

```java
public Address getAddress() throws CloneNotSupportedException {
        return (Address) address.clone();
    }
```

## 继承

这里默认读者已经对于继承有了一个大致模糊的概念，只是还不了解具体的细节之类的，所以就不介绍extends关键字了直接从super开始介绍

### super

super关键字有两个用途：第一是调用父类的方法，第二是调用父类的构造器。

我们先来学习第一个，代码如下：
```java
    public double getSalary() {//Manager中的getSalary方法，Manager继承Employee
        double salary = super.getSalary();//salary是Employee中的私有成员
        return salary+ this.bonus;//bonus是Manager的私有成员
    }
```

这段代码中我们无法直接使用如下代码：
```java
    public double getSalary() {
        return salary + this.bonus;//错误，salary是父类的私有成员，无法访问
    }
    public double getSalary() {
        return super.salary + this.bonus;//错误,super没有这样
    }
```

值得注意的是，我们super调用的同样只能是父类中非私有的(public,protected)方法，否则也无法调用。

### 动态绑定

在开发过程中我们可能会遇到这种情况：程序操作的对象是动态扩展的，例如在发工资的时候本来公司里面只有普通员工，我们可以直接调用`getSalary()`方法，但是现在添加了普通员工的子类Manager类，同样需要调用`getSalary()`方法进行发工资，针对原来的程序我们可以使用for循环来遍历员工数组，在for循环代码主体部分使用`e.getSalary()`来进行发工资。但是现在添加了新的类，如果没有动态绑定(值得注意，这里默认了多态的情况，for循环中的e是存在多态的特性的)，我们调用的方法就是Employee中的`getSalary()`方法，需要额外编写一个逻辑来判断是否是普通员工，再调用对应的方法。那我们不妨猜想一下如果以后还需要添加一个新的子类呢，我们需要改动的代码是巨量的。代码之间的耦合性很高，因此针对这种情况就有了动态绑定。看下面一个例子：
```java
        Manager boss = new Manager("Tom", 18, 5000, 500);
        Employee emp1 = new Employee("Jerry",18,3000);
        Employee emp2 = new Employee("Mike",18,3000);
        Employee[] emps = new Employee[3];
        emps[0] = boss;
        emps[1] = emp1;
        emps[2] = emp2;
        for (Employee e : emps) {
            System.out.println(e.getName() + " " + e.getSalary());//true
        }
```

这里的成功实现就是依赖了动态绑定的使用，当e是boss时候`getSalary()`方法就是使用了Manager中的方法，这里一段代码调用可以是两个不同的方法，我们将调用方法时候，该方法会随着引用对象变化而变化的特性叫做动态绑定。需要明确的是多态是依赖于动态绑定而实现的，是动态绑定使得多态的实现具有可能性。

那么动态绑定是怎么实现的呢，这就借助于方法表来实现了，虚拟机为每一个类创建了一个方法表，方法表上列出了该类的所有方法，包括从它的父类中继承的方法。同时要注意的是子类要覆盖父类的一个方法时候，方法的权限不能低于父类该方法的权限，否则会发生错误。在这个例子中，e引用的是boss这个对象时候，会在它的类中的方法表中寻找对应的方法。

### 强制类型转换

也许有时候我们会出现这样的需求：在上面例子中我们想要针对所有的Manager对象来setBonus, 但是我们不可以直接使用`e.setBonus()`方法，因为这个方法不是Employee中的方法，这样会出现报错。我们就可以采用以下方法来实现：

```java
		Manager boss = new Manager("Tom", 18, 5000, 500);
        Employee emp1 = new Employee("Jerry", 18, 3000);
        Employee emp2 = new Employee("Mike", 18, 3000);
        Employee[] emps = new Employee[3];
        emps[0] = boss;
        emps[1] = emp1;
        emps[2] = emp2;
        for (Employee e : emps) {
            if(e instanceof Manager){
                Manager m = (Manager) e;
                m.setBonus(500);
                System.out.println(m.getName() + " " + m.getSalary());
            }
        }
```

通过强制类型转换来实现。这里的boss本身是子类，由于Java允许向上转型，所以可以是Employee这个父类，后我们想要使用Manager这个子类的特有方法，通过强制类型转换来实现了这个方法。不过强制类型转换会提升代码的耦合性。在未来我们需要添加子类的时候需要修改原来的这些代码，违法了“开闭原则”。所以还是尽量减少这样的实现。

### 抽象类

同样的，我并不会先介绍什么是抽象类，而是通过一个具体的例子来看看我们业务场景中抽象类的实用性：
```java
   	public static void main(String[] args){
        /**
         * function:演示抽象类
         * author: Adieu
         * Date: 2025/12/4
         */
        Student s = new Student("Tom",18, "HUST");
        Employee e = new Employee("Jerry",18,3000.0,"IBM");
        Person[] ps = new Person[2];
        ps[0] = s;
        ps[1] = e;
        for(Person p : ps){
            p.getDescription();
        }
    }

public abstract class Person {
    public abstract void getDescription();
}
```

这个例子中我们通过给Student 和 Employee继承共同的抽象父类以达到了方法的动态绑定。这样做无疑降低了代码的耦合性，同时也符合开闭原则。

使用抽象类的时候需要注意几点：一是抽象类不必要全是抽象方法，二是抽象类的子类如果不是抽象类的话就必须实现继承自父类的抽象方法，三是抽象类不可以有具体的实现对象。

### equals方法

针对这一部分，我认为无法脱离业务逻辑来解释怎么写equals方法，学习这里的时候真正让我头痛的是equals使用场景的复杂性。我没办法直接列举出来所有情况，所以我们先通过这几个例子来看看，这是比较具有代表性的几个场景。

场景一：业务逻辑需要你判断两个对象所有的关键成员相等才算是相等。现在我们有Animal,Dog,Cat三个类，其中Animal是Dog和Cat的父类，他们三个私有成员分别如下：
```java
public class Animal {
    private String name;
    private int age;
}

public class Cat extends Animal{
    private String food;
}

public class Dog extends Animal{
    private String action;
}
```

那么我们的业务逻辑可以这样写
```java
/**
*Animal
*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Animal other = (Animal) obj;
        return age == other.age && name.equals(other.name);
    }
/**
*Dog
*/
@Override
public boolean equals(Object obj) {
    if(obj == this)return true;
    if(!super.equals(obj))return false;
    if(obj.getClass() != this.getClass())return false;
    Dog dog = (Dog) obj;
    return this.action.equals(dog.action);
}
/**
*Cat
*/
@Override
public boolean equals(Object obj) {
    if(obj == this)return true;
    if(!super.equals(obj))return false;
    if(obj == null)return false;
    if(obj.getClass() != this.getClass())return false;
    Cat c = (Cat)obj;
    return this.food.equals(c.food);
}
```

最后在main中运行如下：

```java
Dog dog1 = new Dog("Tom",18,"running");
Cat cat1 = new Cat("Tom",18,"maoliang");
Animal animal1 = new Dog("Tom",18,"running");
Animal animal2 = new Cat("Tom",18,"maoliang");
Animal animal3 = new Animal("Tom",18);
System.out.println(dog1.equals(cat1));//false
System.out.println(animal2.equals(animal1));//false,因为动态绑定的原因，调用的是Cat中的equals方法
System.out.println(animal3.equals(animal1));//false,因为这里animal中的equals方法是比对className,如果是instanceof的话就是true
```

场景二：现在你有三个类，分别是Shape，Rectangle，Circle，其中Shape是Rectangle和Circle的父类，他们的私有成员分别如下：

```java
public class Shape {
    private double area;
}

public class Circle extends Shape{
    private double radius;
}

public class Rectangle extends Shape{
    private double width;
    private double height;
}
```

我们的业务需求是比较两个对象的面积是否相等，如果相等就判定这两个对象相等。其中为了严谨性，如果两个同为Circle的话，要求不仅面积相等也要radius相等，请注意，对于我们实际问题中，两个圆面积相等半径一定相等，但是这是计算机中，由于double精度限制，会出现例如c1的半径是2.0，c2的半径是2.00000000001，他们的面积相等但是半径并不相等。

我们编写他们各自的equals方法如下：
```java
@Override//shape
public boolean equals(Object obj) {
    if(this == obj)return true;
    if(obj == null)return false;
    if(!(obj instanceof Shape))return false;
    Shape shape = (Shape) obj;
    return this.area == shape.area;
}
@Override//circle
public boolean equals(Object obj) {
    if(!super.equals(obj))return false;
    if(!(obj instanceof Circle))return true;
    Circle circle = (Circle) obj;
    return this.radius == circle.radius;
}
@Override//rectangle
public boolean equals(Object obj) {
    if(!super.equals(obj))return false;
    if(!(obj instanceof Rectangle))return true;
    Rectangle rectangle = (Rectangle) obj;
    return this.width == rectangle.width && this.height == rectangle.height;
}
```

运行结果如下:

```java
Circle circle1 = new Circle(4,2.0000000);
Circle circle2 = new Circle(4,2.0000001);
Rectangle rectangle1 = new Rectangle(4,4,2);
System.out.println(circle1.equals(circle2));//false
System.out.println(circle1.equals(rectangle1));//true
```

ok，那么接下来我们来讲讲为什么要这样写，首先，如果两个引用的是同一个对象的话，那么他们无疑是equals的，所以我们在一开始的euqals方法中都有`if(obj == this)return true`这一条指令，节约了我们后续比较的开销。随后我们进行了`if(obj == null)return false`这一步，这十分具有必要性。可以预防后面比较时候出现空指针异常。这时候如果我们的业务要求是必须得要同类才可以算是相等的话就必须使用getClass否则使用instanceof来判断。具体参照上面的代码中的细节。最后进行强制类型转换，由于我们前面判断过了是否可以强制类型转换所以不必担心异常。最后再根据业务逻辑来判断个体域即可。

### hashCode()

首先，我自认为如果要了解到hashCode，必然不能脱离HashMap相关的知识点，这个疑惑点来自于我们重写hashCode方法时候除了可以使用到Objects类中的hash方法来生成散列码，也可以通过手动编写来生成散列码。此时无论是深入到Objects中的hash方法，发现它是调用Arrays中的hashCode方法来实现，而Arrays中的hashCode方法除了借助于JVM生成的hash码之外，核心部分则是使用了乘以31的迭代散列码；还是从手动写hashCode方法中发现也要用到的31这个神奇的数字，我们不妨会对散列码的生成产生好奇，为什么是31。这跟时候我们可以深入到哈希表来了解以下全貌。但是在此之前我对于JVM中的怎么生成hash码十分好奇，所以先来唠唠这方面的相关知识。

#### JVM是怎么生成hash码的？

首先由于我对于线程相关的知识不是特别得了解，所以我只能简单得概括一下。先来回到Java中我们可见的Arrays中的hashCode方法

```java
public static int hashCode(Object a[]) {
    if (a == null)
        return 0;
    int result = 1;
    for (Object element : a)
        result = 31 * result + (element == null ? 0 : element.hashCode());
    return result;
}
```

没毛病，它是调用Object中的hashCode方法（这里要注意，由于动态绑定的特性，并不一定就是Objcet中的hashCode方法，如果说element本身是继承Object中的String这些封装类的话，会优先调用String中的hashCode方法，只有element没有重写hashCode方法时候才会调用Object中的的hashCode方法，才会深入到JVM）。来看看Object这里面的方法：

```java
@IntrinsicCandidate
public native int hashCode();
```

这里是我们能再java中接触的最后一个方法，`native`关键字表明它是调用了JVM中的方法，而`@IntrinsicCandidate`是告诉HotSpot虚拟机可以被优化替换为更高效的内部优化。Ok，这里已经来到了JVM中的方法了，那么我们需要知道的是JVM其实是用C++来编写的，但是没什么问题，我们只需要理解原理的就好了。JVM生成散列码的调用方法是使用get_next_hash方法，这个方法包含了5种生成散列码的方法，使用switch-case来进行条件判断，我们调用的是其中的xorShift算法来生成，这个方法是基于当前线程的XorShift状态来生成对应的散列码，而这个散列码会随调用一次就更新一次，在此之前会将新生成的散列码和调用的对象进行绑定且不可再变。==了解这方面的知识我们可以学习到的是，JVM将生成的散列码与调用对象进行解耦，而是和线程相关。这不仅仅意味着散列码是和对象属性无关的，对象属性变化不会影响hashCode，同时也起到了将对象隐私隔离，避免了信息外泄。同时XorShift生成是借助于异或和位移，对CPU极其友好，这是性能优化上常常出现的特点，同时散列码是线程私有的，多线程处理下，每个线程独立迭代，不需要加线程锁，并发效率高。同时状态码总位数高达320位，迭代周期高达2^320-1，几乎不可能出现哈希冲突==

下面是几个很有意思的例子，我们一起来看看：

例子1：

```java
Animal animal1 = new Animal("Tom",18);
String str1 = "Tom";
String str2 = animal1.getName();
System.out.println(str1.hashCode());
System.out.println(Objects.hash(str2));//不一致，原因在于上面的是直接调用String中的hashCode，而下面的是调用了Object中的hashCode，由于Object是String父类，由于动态绑定调用了String中的hashCode方法。核心区别如下
```

Arrays中的hashCode方法：

![image-20251206150554857](C:\Users\Adieu\AppData\Roaming\Typora\typora-user-images\image-20251206150554857.png)

他比String多了一个result迭代的过程

例子2：

```java
Animal animal1 = new Animal("Tom",18,new Address("maoliang"));//重写了Address中的hashCode方法
Animal animal2 = new Animal("Tom",18,new Address("maoliang"));
int hashcode1 = animal1.hashCode();
int hashcode2 = animal2.hashCode();
System.out.println(hashcode1);
System.out.println(hashcode2);//两个结果相等
```

例子3：

```java
Animal animal1 = new Animal("Tom",18,new Address("maoliang"));//没重写了Address中的hashCode方法
Animal animal2 = new Animal("Tom",18,new Address("maoliang"));
int hashcode1 = animal1.hashCode();
int hashcode2 = animal2.hashCode();
System.out.println(hashcode1);
System.out.println(hashcode2);//两个结果不等
```

上面两个都是由于动态绑定的特性，例子2通过重写了Address中的equals方法达到了element.hashCode动态绑定的是Address中的方法，而例子3没有重写调用的就是Object.hashCode方法，底层是基于JVM实现的。所以两个对象的散列码并不相同。

#### 哈希表内部运行机制

前面我们已经学过了hash值是怎么生成的，那我在介绍哈希表时候就先概述一下哈希表的存储和查找过程。
针对存储而言，我们先抛开扩容机制，哈希表在拿到了对象后，根据对象的hash值来进行初始处理，确保高位可以用到以及减少低位重复导致的碰撞。预处理后的值再和当前的桶数量-1进行相与得到的值就是存储的桶的下标。然后再将对象和其预处理过后的hash值等等封装得到的Node尾插法插入到链表或者红黑树中。
针对查找而言，同样是找到对应的桶，然后遍历通过比对预处理后的哈希值相等后调用对象的equals方法进行比对得到真正的想要的对象。注意这里允许哈希值相同，因为先比对的哈希值是为了节约调用equals的开销。我们最终确定的对象是要调用equals方法来确定的。这里就回到前面的，我们一定要确保我们重写的equals方法符合我们的业务需求，hash值也必须得要对应equals方法，否则我们根本无法得到正确的对象。

那么哈希表还有两个机制是必要的，第一个是树化，第二个是扩容机制。首先树化的前提要求是数组元素已经扩容到64个以上，并且链表节点已经到达了8个，所以我们先讲扩容机制。
扩容机制的发生和两个因素有关，第一个是当前容量，第二个是负载因子。当前容量是指已有的数组大小，负载因子通常是0.75。阈值等于两者相乘，当添加的新元素导致已有数组的数量大于阈值的时候，会触发扩容，容量扩容至原来的两倍，阈值也随之变化。
这个时候将所有的节点取出来，然后根据预处理后的哈希值和新的容量进行与操作（注意不是容量-1），如果为零就放入原来的索引，不是的话就加到原来索引加原来容量值处。
树化的话是达到上述的两个条件所触发的机制，仅针对当前的链表，最终将链表转换为自平衡的二叉搜索树也就是红黑树。

### toString()方法

Object类中定义的toString()方法返回一个字符串包含了对象所属的类名和散列码。如下：

```java
public static void main(String[] args){
    Person p = new Person("Tom", 18, "nan");
    System.out.println(p);//demo5.Person@119d7047
}
```

如果我们要重写toString方法的话，通常是这种格式：

```java
@Override
public String toString() {
    return "Person{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", sex='" + sex + '\'' +
            '}';
}
```

为了方便我们后续代码的调试，我们最好给每一个类都添加一个toString方法。

### 枚举类

枚举类顾名思义，本身就是一个类。所有的枚举类型都是Enum类的子类。那么接下来我们的学习重点可以选择从Enum类出发。

首先，Enum类中有final修饰的name()方法，返回的是当前枚举对象的name，类中同样提供了toString方法，但是这个方法可以被重写，意味着我们可以客制化这个方法，所以我们通常在业务中也更加推荐使用这个方法。

```java
Season s = Season.SPRING;
//未重写枚举类中的toString()方法
System.out.println(s.name());//SPRING
System.out.println(s.toString());//SPRING
//重写枚举类中的toString()方法
System.out.println(s.name());//SPRING
System.out.println(s.toString());//当前枚举值是；SPRING
```

Enum类中还提供了ordinal方法返回的是当前枚举对象对应的值的序号

```java
Season s = Season.SPRING;
System.out.println(s.ordinal());//0
```

通过刨析Enum类的源码我们不妨发现其中的一些的门道，例如在其中equals方法返回的是this == other的值，也就是说我们可以不需要使用equals方法直接使用==就可以进行比对了。值得注意的是，这个方法同样是使用final进行修饰的，这意味着并不提供重写的方法。类似不提供重写的还有hashCode方法，准确来说的话从Object类中继承的方法应该是仅有toString方法可以重写。

Enum类中提供了toString方法的逆方法valueOf()，这个方法通过接受枚举类的类名，以及其中枚举常量的名称返回一个枚举类的实例。

```java
Season s = Enum.valueOf(Season.class,"SPRING");
System.out.println(s.toString());//SPRING
```

每一个枚举类型都有一个静态的枚举类型，但是这个方法并非继承自Enum， 而是Java 编译器在编译时自动生成 的 “合成方法”。

```java
System.out.println(Arrays.toString(Season.values()));//[SPRING, SUMMER, AUTUMN, WINTER]
System.out.println(Season.values());//[Ldemo6.Season;@119d7047
```

