package demo1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args){
//        List<Integer> list = new ArrayList<>();
//        for(int i = 0; i < 10; i++){
//            list.add(i);
//        }
//        Iterator<Integer> it = list.iterator();
//        long start = System.currentTimeMillis();
//        for(;it.hasNext();it.next()){
//        }
//        System.out.println("迭代器遍历耗时："+(System.currentTimeMillis() -  start)+"ms");
//        start = System.currentTimeMillis();
//        for(int i = 0;i < list.size();i++){
//        }
//        System.out.println("普通遍历耗时："+(System.currentTimeMillis() -  start)+"ms");
        List<Integer> list = new LinkedList<>();
        for(int i = 0; i < 10000; i++){
            list.add(i);
        }
        long start = System.currentTimeMillis();
        Iterator<Integer> it = list.iterator();
        for(;it.hasNext();it.next()){
            Integer i = it.next();
        }
        System.out.println("迭代器遍历耗时："+(System.currentTimeMillis() -  start)+"ms");
        start = System.currentTimeMillis();
        for(int i = 0;i < list.size();i++){
            Integer in = list.get(i);
        }
        System.out.println("普通遍历耗时："+(System.currentTimeMillis() -  start)+"ms");
    }
}
