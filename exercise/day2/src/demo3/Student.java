package demo3;

public class Student extends Person{
    private int age;
    private String school;

    public void getDescription(){
        System.out.println("I am a student,my name is " + getName() + " and I am " + age + " years old,I study at " + school);
    }

    public Student(String name, int age, String school) {
        super(name);
        this.age = age;
        this.school = school;
    }

    public Student(int age, String school) {
        this.age = age;
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
