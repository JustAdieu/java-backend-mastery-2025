package main.java.com.adieu.learning.week1.daily.day2.demo4;

import java.util.Objects;

public class Cat extends Animal{
    private String food;

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!super.equals(obj))return false;
        if(obj == null)return false;
        if(obj.getClass() != this.getClass())return false;
        Cat c = (Cat)obj;
        return this.food.equals(c.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode() + this.getFood());
    }

    public Cat(String name, int age, String food) {
        super(name, age);
        this.food = food;
    }

    public Cat(String food) {
        this.food = food;
    }

    public Cat() {
    }

    public Cat(String name, int age) {
        super(name, age);
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
