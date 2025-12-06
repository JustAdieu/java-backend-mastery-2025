package demo1;

import java.util.Objects;

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
