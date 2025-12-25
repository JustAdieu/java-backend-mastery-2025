package main.java.com.adieu.learning.week1.daily.day2.demo4;

import java.util.Objects;

public class Dog extends Animal{
    private String action;

    public Dog(String name, int age, String action) {
        super(name, age);
        this.action = action;
    }

    public Dog(String action) {
        this.action = action;
    }

    public Dog(String name, int age) {
        super(name, age);
    }

    public Dog() {
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!super.equals(obj))return false;
        if(obj.getClass() != this.getClass())return false;
        Dog dog = (Dog) obj;
        return this.action.equals(dog.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode() + this.getAction());
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
