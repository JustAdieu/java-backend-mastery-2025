package main.java.com.adieu.learning.week1.daily.day2.demo4;


import main.java.com.adieu.learning.week1.daily.day2.demo1.Address;

import java.util.Objects;

public class Animal {
    private String name;
    private int age;
    private Address address;

    public Animal(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Animal() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
//      if(!(obj instanceof Animal))return false;
        Animal other = (Animal) obj;
        return age == other.age && name.equals(other.name) && address.equals(other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age,address);
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
}
