package demo4;

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
        if(!(obj instanceof Shape))return false;
        Shape shape = (Shape) obj;
        return this.area == shape.area;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}

