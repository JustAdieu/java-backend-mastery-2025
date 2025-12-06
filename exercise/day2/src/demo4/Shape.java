package demo4;

import java.util.Objects;

public class Shape {
    private double area;

    public Shape(double area) {
        this.area = area;
    }

    public Shape() {
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null)return false;
        if(!(obj instanceof Shape))return false;
        Shape shape = (Shape) obj;
        return this.area == shape.area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area);
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}

