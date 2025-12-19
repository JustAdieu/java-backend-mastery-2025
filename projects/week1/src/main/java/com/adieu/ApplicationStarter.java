package com.adieu;

import com.adieu.pojo.Admin;
import com.adieu.pojo.Person;
import com.adieu.pojo.Student;
import com.adieu.pojo.Teacher;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStarter {
    public static void main(String[] args){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1","张三",1,18));
        persons.add(new Student("1","lisi",1,18,"1","软件工程",1));
        persons.add(new Teacher("1","王五",1,18,"1",18,"软件工程"));
        persons.add(new Admin("1","赵六",1,18,"1","管理员"));
        for(Person person : persons){
            System.out.println(person);
        }
    }
}
