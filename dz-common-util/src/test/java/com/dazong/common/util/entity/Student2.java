package com.xingfu.meihua.domain;

/**
 * @author: zisong.wang
 * @date: 2018/1/18
 */
public class Student2 {

    public Student2() {

    }

    public String name;
    protected int age;
    char sex;
    private String phoneNum;

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex
                + ", phoneNum=" + phoneNum + "]";
    }
}
