package main.java.com.adieu.learning.week2.daily.day3.demo1;

import java.util.Objects;

public class User {
    private String name;
    private int age;
    private String id;

    public User(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public User() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
