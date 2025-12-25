package main.java.com.adieu.learning.week1.daily.day3.demo1;

//import java.util.ArrayList;
//import java.util.List;

public class Main {

    /**
     * 测试多态
     * author:Adieu
     * Date:2025/12/11
     */
    public static void main(String[] args) {
//        Flyable f1 = new Bird();
//        Flyable f2 = new Rocket();
//        Flyable f3 = new Huamei();
//        List<Flyable> lists = new ArrayList<>();
//        lists.add(f1);
//        lists.add(f2);
//        lists.add(f3);
//        for (Flyable flyable : lists) {
//            flyable.fly();
//        }
        Flyable f1 = new Bird();
        Flyable f2 = new Rocket();
        Flyable f3 = new Huamei();
        makeItFly(f1);//true
        makeItFly(f2);//true
        makeItFly(f3);//true

    }
    public static void makeItFly(Flyable f){
        f.fly();
    }
}
