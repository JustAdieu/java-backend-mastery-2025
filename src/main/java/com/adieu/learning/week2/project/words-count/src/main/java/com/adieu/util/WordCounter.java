package com.adieu.util;

import java.util.HashMap;
import java.util.Map;

public class WordCounter implements Count{

    private HashMap<String,Integer> map;

    public WordCounter(){
        map = new HashMap<>();
    }

    public WordCounter(int initialCapacity, float loadFactor){
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    public static Map<String,Integer> countOnce(String tar){
        Map<String,Integer> tmp = new HashMap<>();
        tar = tar.toLowerCase();
        tar = tar.replaceAll("[^a-zA-Z\\s]"," ");
        String[] words = tar.split("\\s+");
        for(String word:words){
            tmp.merge(word,1,Integer::sum);
        }
        return tmp;
    }

    public static void showWordCount(String tar){
        Map<String,Integer> getMap = countOnce(tar);
        for(Map.Entry<String,Integer> entry:getMap.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue()+"次");
        }
    }

    @Override
    public void insert(String str) {
        Map<String, Integer> tmp = countOnce(str);
        for(Map.Entry<String,Integer> entry:tmp.entrySet()){
            map.merge(entry.getKey(),entry.getValue(),Integer::sum);
        }
    }
    public void show(){
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            entry.getKey();
            entry.getValue();
        }
    }
}
