package com.adieu.pojo;

/**
 * 管理员类
 * @author  adieu
 * @date    2025-12-18
 * @version 1.0
 */
public class Admin extends Person{
    private String adminId;
    private String responsibility;

    public Admin(String id, String name, Integer sex, Integer age, String adminId, String responsibility) {
        super(id, name, sex, age);
        this.adminId = adminId;
        this.responsibility = responsibility;
    }

    public Admin(String adminId, String responsibility) {
        this.adminId = adminId;
        this.responsibility = responsibility;
    }

    public Admin(String id, String name, Integer sex, Integer age) {
        super(id, name, sex, age);
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
