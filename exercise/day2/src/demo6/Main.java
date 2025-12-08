package demo6;

import java.util.Arrays;

import static demo6.Season.SPRING;

public class Main {
    /**
     * function:演示枚举类
     * author:adieu
     * date:2025/12/8
     */
    public static void main(String[] args){
//        Season s = SPRING;
//        //未重写枚举类中的toString()方法
//        System.out.println(s.name());//SPRING
//        System.out.println(s.toString());//SPRING
//        //重写枚举类中的toString()方法
//        System.out.println(s.name());//SPRING
//        System.out.println(s.toString());//当前枚举值是；SPRING
//        Season s = Enum.valueOf(Season.class,"SPRING");
//        System.out.println(s.toString());
        System.out.println(Arrays.toString(Season.values()));//[SPRING, SUMMER, AUTUMN, WINTER]
        System.out.println(Season.values());//[Ldemo6.Season;@119d7047
    }
}
