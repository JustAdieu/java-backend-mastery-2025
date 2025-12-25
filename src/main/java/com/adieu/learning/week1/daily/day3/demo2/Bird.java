package main.java.com.adieu.learning.week1.daily.day3.demo2;

public class Bird implements Flyable{
    private String name;
    private String color;

    public Bird(String name, String color) {
        this.name = name;
        this.color = color;

    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void fly() {
        System.out.println("鸟儿在飞");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Bird() {
    }
}
