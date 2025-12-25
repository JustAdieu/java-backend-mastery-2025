package main.java.com.adieu.learning.week1.daily.day2.demo3;

public class Main {
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
}
