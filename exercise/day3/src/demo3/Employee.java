package demo3;

public class Employee extends Person implements Cloneable {
    private Date hireDate;
    private double salary;
    public Employee(String name,int age,Date birthDay, Date hireDate, double salary) {
        super(name,age,birthDay);
        this.hireDate = hireDate;
        this.salary = salary;
    }
    public Employee() {
    }
    @Override
    public Employee clone(){
        Employee e = (Employee)super.clone();
        e.setHireDate(hireDate);
        e.setSalary(salary);
        return e;
    }
    public Date getHireDate() {
        return hireDate;
    }
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
