package demo4;

import demo1.Address;

import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args){
        /**
         * function:演示equals方法
         * author: Adieu
         * Date: 2025/12/4
         */
//        Dog dog1 = new Dog("Tom",18,"running");
//        Cat cat1 = new Cat("Tom",18,"maoliang");
//        Animal animal1 = new Dog("Tom",18,"running");
//        Animal animal2 = new Cat("Tom",18,"maoliang");
//        Animal animal3 = new Animal("Tom",18);
//        System.out.println(dog1.equals(cat1));//false
//        System.out.println(animal2.equals(animal1));//false,因为动态绑定的原因，调用的是Cat中的equals方法
//        System.out.println(animal3.equals(animal1));//false,因为这里animal中的equals方法是比对className,如果是instanceof的话就是true
//        Circle circle1 = new Circle(4,2.0000000);
//        Circle circle2 = null;
//        Rectangle rectangle1 = new Rectangle(4,4,2);
//        System.out.println(circle1.equals(circle2));//false
//        System.out.println(circle1.equals(rectangle1));//true
        /**
         * function:演示hashCode方法
         * author: Adieu
         * Date: 2025/12/5
         */
//        Animal animal1 = new Animal("Tom",18);
//        Animal animal2 = new Animal("Tom",18);
//        System.out.println("animal1:"+animal1.hashCode());
//        System.out.println("animal2:"+animal2.hashCode());
//        System.out.println("animal1 == animal2为："+animal1.equals(animal2));
//        System.out.println("cat1:"+cat1.hashCode());
//        System.out.println("cat2:"+cat2.hashCode());
//        System.out.println("cat1 == cat2为："+cat1.equals(cat2));
//        Dog dog1 = new Dog("Tom",18,"running");
//        Dog dog2 = new Dog("Tom",18,"running");
//        System.out.println("dog1:"+dog1.hashCode());
//        System.out.println("dog2:"+dog2.hashCode());
//        System.out.println("dog1 == dog2为："+dog1.equals(dog2));
//        String str1 = "Tom";
//        System.out.println(str1.hashCode() == animal1.getName().hashCode());
//        System.out.println(str1.hashCode() == Objects.hash(animal1.getName()));
//        Animal animal1 = new Animal("Tom",18,new Address("maoliang"));//没重写了Address中的hashCode方法
//        Animal animal2 = new Animal("Tom",18,new Address("maoliang"));
//        int hashcode1 = animal1.hashCode();
//        int hashcode2 = animal2.hashCode();
//        System.out.println(hashcode1);
//        System.out.println(hashcode2);//两个结果不等
//        Animal animal1 = new Animal("Tom",18);
//        String str1 = "Tom";
//        String str2 = animal1.getName();
//        System.out.println(str1.hashCode());
//        System.out.println(Objects.hash(str2));
//        Address add1 = new Address("maoliang");
//        Address add2 = new Address("maoliang");
//        System.out.println(add1.hashCode() == add2.hashCode());
        HashMap<Integer,Integer> map = new HashMap<>();
    }
}
