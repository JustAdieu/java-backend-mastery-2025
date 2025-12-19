package com.adieu.pojo;

import com.adieu.attendance.AttendanceAble;

import java.util.Objects;

/**
 * 学生类
 * @author  adieu
 * @date    2025-12-18
 * @version 1.0
 */
public class Student extends  Person implements AttendanceAble {
    private String stuId;
    private String profession;
    private Integer grade;

    public Student(String id, String name, Integer sex, Integer age, String stuId, String profession, Integer grade) {
        super(id, name, sex, age);
        this.stuId = stuId;
        this.profession = profession;
        this.grade = grade;
    }

    public Student(String stuId, String profession, Integer grade) {
        this.stuId = stuId;
        this.profession = profession;
        this.grade = grade;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", profession='" + profession + '\'' +
                ", grade=" + grade +
                '}';
    }

    /**
     * 根据id和stuId生成hashCode
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(stuId,getId());
    }

    /**
     * 基于id和stuId判断对象是否相等
     *
     * @param obj 待比较的对象
     * @return 是否相等
     */

    @Override
    public boolean equals(Object obj) {
        // 1. 引用相同，直接相等（性能优化）
        if(this == obj) return true;
        // 2. 对象为null，直接不相等
        if(obj == null) return false;
        // 3. 非Student类型，直接不相等
        if(!(obj.getClass() == Student.class))return false;
        // 4. 类型转换+字段比较（Objects.equals自动处理null）
        Student student = (Student) obj;
        return Objects.equals(getId(),student.getId())&&Objects.equals(getStuId(),student.getStuId());
    }

    @Override
    public String checkAttendance() {
        return "上课打卡";
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
