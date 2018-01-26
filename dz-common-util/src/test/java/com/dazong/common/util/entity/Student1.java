package com.dazong.common.util.entity;

/**
 * @author: zisong.wang
 * @date: 2018/1/18
 */
public class Student1 extends StudentBase {

    private static final String DEFAULT_GENDER = "男";

    //---------------构造方法-------------------
    //（默认的构造方法）
    Student1(String str) {
        System.out.println("(默认)的构造方法 s = " + str);
    }

    //无参构造方法
    public Student1() {
        System.out.println("调用了公有、无参构造方法执行了。。。");
    }

    //有一个参数的构造方法
    public Student1(char name) {
        System.out.println("姓名：" + name);
    }

    //有多个参数的构造方法
    public Student1(String name, int age) {
        System.out.println("姓名：" + name + "年龄：" + age);//这的执行效率有问题，以后解决。
    }

    //受保护的构造方法
    protected Student1(boolean n) {
        System.out.println("受保护的构造方法 n = " + n);
    }

    //私有构造方法
    private Student1(int age) {
        System.out.println("私有的构造方法   年龄：" + age);
    }


    private String stuPhone;

    private String stuGender;

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        if (stuGender == null || "".equals(stuGender)) {
            stuGender = DEFAULT_GENDER;
        }
    }

}
