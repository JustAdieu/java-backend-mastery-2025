package demo5;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args){
        TalkingClock tc = new TalkingClock(1000,true);
        tc.start();

        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }
}
