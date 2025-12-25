package main.java.com.adieu.learning.week1.daily.day2.demo4;

import java.util.Objects;

public class Rectangle extends Shape{
    private double width;
    private double height;

    public Rectangle(double area, double width, double height) {
        super(area);
        this.width = width;
        this.height = height;
    }

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj))return false;
        if(!(obj instanceof Rectangle))return true;
        Rectangle rectangle = (Rectangle) obj;
        return this.width == rectangle.width && this.height == rectangle.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode()+this.getHeight()+this.getWidth());
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
