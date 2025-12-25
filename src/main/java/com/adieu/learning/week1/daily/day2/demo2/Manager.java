package main.java.com.adieu.learning.week1.daily.day2.demo2;

public class Manager extends Employee{
    private double bonus;

    Manager(String name, int age, double salary, double bonus) {
        super(name, age, salary);
        this.bonus = bonus;
    }

    Manager() {
        super();
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalary() {
        double salary = super.getSalary();
        return salary+ this.bonus;
    }
}
