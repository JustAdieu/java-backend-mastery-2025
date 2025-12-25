package main.java.com.adieu.learning.week1.daily.day3.demo5;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args){
        TalkingClock tc = new TalkingClock(1000,true);
        tc.start();

        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }
}
