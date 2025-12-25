package main.java.com.adieu.learning.week1.daily.day2.demo1;

public class Address implements Cloneable
{
    String position;

    public Address(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
