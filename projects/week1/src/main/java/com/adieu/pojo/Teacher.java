package com.adieu.pojo;

import com.adieu.attendance.AttendanceAble;

/**
 * 教师类
 *
 * @author  adieu
 * @date    2025-12-18
 * @version 1.0
 */

public class Teacher extends Person implements AttendanceAble {
    private String teacherId;
    private Integer teachAge;
    private String teachClass;

    public Teacher(String id, String name, Integer sex, Integer age, String teacherId, Integer teachAge, String teachClass) {
        super(id, name, sex, age);
        this.teacherId = teacherId;
        this.teachAge = teachAge;
        this.teachClass = teachClass;
    }

    public Teacher(String teacherId, Integer teachAge, String teachClass) {
        this.teacherId = teacherId;
        this.teachAge = teachAge;
        this.teachClass = teachClass;
    }

    public Teacher(String id, String name, Integer sex, Integer age) {
        super(id, name, sex, age);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", teachAge=" + teachAge +
                ", teachClass='" + teachClass + '\'' +
                '}';
    }

    public Teacher() {
    }

    @Override
    public String checkAttendance() {
        return "授课打卡";
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getTeachAge() {
        return teachAge;
    }

    public void setTeachAge(Integer teachAge) {
        this.teachAge = teachAge;
    }

    public String getTeachClass() {
        return teachClass;
    }

    public void setTeachClass(String teachClass) {
        this.teachClass = teachClass;
    }
}
