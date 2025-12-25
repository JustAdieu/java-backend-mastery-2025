package main.java.com.adieu.learning.week1.daily.day1;

public class Day1_demo {
    public static void main(String[] args) {
        int num1 = 10;
        System.out.println(num1);//10
        changeNum(num1);
        System.out.println(num1);//10

        Person person1 = new Person("张三", 18);
        System.out.println(person1.toString());//Person{name='张三', age}
        changePersonBySet(person1);
        System.out.println(person1.toString());//Person{name='王五', age=20}

        Person person2 = new Person("张三", 18);
        System.out.println(person2.toString());//Person{name='张三', age=18}
        changePersonByRef(person2);
        System.out.println(person2.toString());//Person{name='张三', age=18}

        String str = "hello world";
        System.out.println(str);//hello world
        changeStr(str);
        System.out.println(str);//hello world

        Integer num2 = 10;
        System.out.println(num2);//10
        changeInteger(num2);
        System.out.println(num2);//10
    }

    public static void changeNum(int n){
        n = 20;
    }

    public static void changeInteger(Integer n){
        n = 20;
    }

    public static void changeStr(String n){
        n = "hello java";
    }
    public static void changePersonBySet(Person n){
        n.setName("王五");
        n.setAge(20);
    }

    public static void changePersonByRef(Person n){
        n = new Person("王五", 20);
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