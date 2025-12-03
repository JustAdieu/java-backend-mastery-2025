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

