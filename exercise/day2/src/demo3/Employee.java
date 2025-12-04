package demo3;

public class Employee extends Person{
    private int age;
    private double salary;
    private String company;
    public Employee(String name, int age, double salary, String company) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.company = company;
    }

    public Employee(int age, double salary) {
        this.age = age;
        this.salary = salary;
    }

    public void getDescription() {
        System.out.println("I am an employee, I work at "+ this.getCompany() + ". I am "+ this.getAge()+"years old.");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
