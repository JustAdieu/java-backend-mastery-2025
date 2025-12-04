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
