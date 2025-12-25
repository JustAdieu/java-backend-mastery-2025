package main.java.com.adieu.learning.week1.daily.day2.demo3;

public abstract class Person {
    private String name;

    public abstract void getDescription();

    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
