package com.dazong.common.util;

import com.dazong.common.util.entity.Student1;
import com.dazong.common.util.reflect.ReflectUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class ReflectUtilsTest {

    @Test
    public void reflectUtilTest() throws Exception {

        System.out.println("-------- 获取clazz的所有属性,包括继承获得的属性 -------- ");
        Field[] fields = ReflectUtil.getAllFields(Student1.class);
        for (Field field : fields) {
            System.out.println(field);
        }


        System.out.println("\n-------- 利用反射设置指定对象的指定属性为指定的值 -------- ");
        Student1 student1 = new Student1();
        ReflectUtil.setFieldValue(student1, "stuPhone", "18589020587");

        System.out.println("\n-------- 利用反射获取指定对象的指定属性 -------- ");
        Object obj1 = ReflectUtil.getFieldValue(student1, "stuPhone");
        System.out.println("获取值1:" + obj1);
        Field field1 = ReflectUtil.getField(student1, "DEFAULT_GENDER");
        System.out.println("获取值2:" + field1);

        //获取class对象
        Class stuClass = Class.forName("com.dazong.common.util.entity.Student2");
        Field field = ReflectUtil.getField(stuClass, "name");

        System.out.println("\n-------- 使用setter方法设置属性值 -------- ");
        //实例化对象，等同于  Student2 objs = new Student2();
        Object objs = stuClass.getConstructor().newInstance();
        ReflectUtil.setFieldValueWithSetterMethod(objs, "设置值为110", stuClass, field);

        System.out.println("\n-------- 使用getter方法获取属性值 -------- ");
        Object obj = ReflectUtil.getFieldValueWithGetterMethod(objs, stuClass, field);
        System.out.println("获取值:" + obj);

    }

}
