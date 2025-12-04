package demo4;

public class Circle extends Shape{
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public Circle() {
    }

    public Circle(double area, double radius) {
        super(area);
        this.radius = radius;
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj))return false;
        if(!(obj instanceof Circle))return true;
        Circle circle = (Circle) obj;
        return this.radius == circle.radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
