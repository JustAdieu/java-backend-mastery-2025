package main.java.com.adieu.learning.week2.daily.day3.demo1;

import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        User user1 = new User();
        User user2 = new User();
        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
        System.out.println(user1.equals(user2));
        HashMap<Integer,Integer> map = new HashMap<>();
    }
}
