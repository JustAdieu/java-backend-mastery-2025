package demo4;

public class Main {
    public static void main(String[] args){
        /**
         * function:演示equals方法
         * author: Adieu
         * Date: 2025/12/4
         */
        Dog dog1 = new Dog("Tom",18,"running");
        Cat cat1 = new Cat("Tom",18,"maoliang");
        Animal animal1 = new Dog("Tom",18,"running");
        Animal animal2 = new Cat("Tom",18,"maoliang");
        Animal animal3 = new Animal("Tom",18);
        System.out.println(dog1.equals((Object)cat1));//false
        System.out.println(animal2.equals(animal1));//false,因为动态绑定的原因，调用的是Cat中的equals方法
        System.out.println(animal3.equals(animal1));//false,因为这里animal中的equals方法是比对className,如果是instanceof的话就是true
        Circle circle1 = new Circle(4,2.0000000);
        Circle circle2 = new Circle(4,2.0000001);
        Rectangle rectangle1 = new Rectangle(4,4,2);
        System.out.println(circle1.equals(circle2));//false
        System.out.println(circle1.equals(rectangle1));//true
    }
}
