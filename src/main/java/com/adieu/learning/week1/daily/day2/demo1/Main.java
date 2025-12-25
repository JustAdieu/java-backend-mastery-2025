package main.java.com.adieu.learning.week1.daily.day2.demo1;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p = new Person("Tom", 18, new Address("China"));
        Address a = p.getAddress();
        a.setPosition("USA");
        System.out.println(p.getAddress().getPosition());//China
    }
}
