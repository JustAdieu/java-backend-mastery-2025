package main.java.com.adieu.learning.week1.daily.day1;

public class Day1_demo2 {

    public static void main(String[] args) {
        String str1 = "hello";
        String str2 = "hello";
        System.out.println(str1 == str2);// true

        String str3 = new String("hello");
        System.out.println(str1 == str3);// false

        String str4 = new String("hello");
        str4 = str4.intern();
        System.out.println(str1 == str4);// true

        String str5 = "he";
        String str6 = "llo";
        String str7 = str5 + str6;
        System.out.println(str7 == str1);//false
        str7 = str7.intern();
        System.out.println(str7 == str1);//true
    }
}
