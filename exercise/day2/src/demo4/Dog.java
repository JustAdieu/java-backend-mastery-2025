package demo4;

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
        if(obj.getClass() != this.getClass())return false;
        Dog dog = (Dog) obj;
        return super.equals(obj) && this.action.equals(dog.action);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
