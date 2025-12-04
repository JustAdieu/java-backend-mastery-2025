package demo2;

public class Main {
    public static void main(String[] args) {
        /**
         * 演示多态和动态绑定
         * author:Adieu
         * Date:2025/12/4
         */
//        Manager boss = new Manager("Tom", 18, 5000, 500);
//        Employee emp1 = new Employee("Jerry",18,3000);
//        Employee emp2 = new Employee("Mike",18,3000);
//        Employee[] emps = new Employee[3];
//        emps[0] = boss;
//        emps[1] = emp1;
//        emps[2] = emp2;
//        for (Employee e : emps) {
//            System.out.println(e.getName() + " " + e.getSalary());
//        }
        /**
         * 演示强制类型转换
         * author:Aideu
         * Date:2025/12/4
         */
        Manager boss = new Manager("Tom", 18, 5000, 500);
        Employee emp1 = new Employee("Jerry", 18, 3000);
        Employee emp2 = new Employee("Mike", 18, 3000);
        Employee[] emps = new Employee[3];
        emps[0] = boss;
        emps[1] = emp1;
        emps[2] = emp2;
        for (Employee e : emps) {
            if(e instanceof Manager){
                Manager m = (Manager) e;
                m.setBonus(500);
                System.out.println(m.getName() + " " + m.getSalary());
            }else{
                System.out.println(e.getName() + " " + e.getSalary());
            }
        }
    }
}
