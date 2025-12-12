package demo3;

public class Person implements Cloneable{
    private String name;
    private int age;
    private Date birthDate;

    public Person(String name, int age, Date birthDate) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
    }
    public Person() {
    }

    @Override
    public Person clone(){
        try{
            Person p = (Person)super.clone();
            p.birthDate = birthDate.clone();
            return p;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
