package com.dazong.common.util.entity;

/**
 * @author: zisong.wang
 * @date: 2018/1/18
 */
public class Student2 {

    public Student2() {

    }

    public Integer id;
    public String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        if (name == null || name == "") {
            return "name test";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected int age;
    char sex;
    private String phoneNum;

    @Override
    public String toString() {
        return "Student12 [id=" + id + "name=" + name + ", age=" + age + ", sex=" + sex
                + ", phoneNum=" + phoneNum + "]";
    }
}
