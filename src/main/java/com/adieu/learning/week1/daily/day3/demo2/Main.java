package main.java.com.adieu.learning.week1.daily.day3.demo2;

public class Main {
    /**
     * function:测试接口
     * author:Adieu
     * Date:2025/12/11
     */
    public static void main(String[] args){
        Flyable f1 = new Bird();
        Bird b1 = new Bird();
        if(b1 instanceof Flyable){
            System.out.println("true");//true
        }
    }
}
