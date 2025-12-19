package com.adieu.pojo;

import java.util.Objects;

/**
 * Person 类
 * @author  adieu
 * @date    2025-12-18
 * @version 1.0
 */

public class Person {
    private String id;
    private String name;
    /**
     * 性别
     * 1. 男
     * 2. 女
     */
    private Integer sex;
    private Integer age;

    public Person(String id, String name, Integer sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        setAge(age);
    }

    public Person() {
    }

    /**
     * 根据id计算哈希值
     * 规则：id为null时候返回0，否则返回id的哈希值
     * @return 哈希值
     */

    @Override
    public int hashCode() {
        //Object的hash自动处理null（id为null时候返回0)
        return Objects.hash(id);
    }

    /**
     * 基于id判断对象是否相等
     *
     * @param obj   the reference object with which to compare.
     * @return
     */

    @Override
    public boolean equals(Object obj) {
        //1. 引用相同，直接相等（性能优化）
        if(this == obj)return true;
        //2. 对象为null，直接不相等
        if(obj == null || getClass() != obj.getClass())return false;
        Person person = (Person) obj;
        //比较id
        return id != null && person.getId() != null && Objects.equals(id,person.id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     * @param age
     */
    public void setAge(Integer age) {
        if(age == null){
            throw new IllegalArgumentException("年龄不能为空");
        }
        else if(age > 150 || age <= 0){
            throw new IllegalArgumentException("年龄不合法");
        } else{
            this.age = age;
        }
    }
}
