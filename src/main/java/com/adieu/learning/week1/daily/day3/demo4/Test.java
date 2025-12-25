package main.java.com.adieu.learning.week1.daily.day3.demo4;

public class Test {
    public static void main(String[] args){
        MainBus mb = new MainBus();
        mb.mainLogic(()-> System.out.println("回调逻辑开始..."));
    }
}
interface TestInterface{
    void callBack();
}
class MainBus{
    public void mainLogic(TestInterface callBackLogic){
        System.out.println("主逻辑开始...");
        callBackLogic.callBack();
        System.out.println("主逻辑结束...");
    }
}
