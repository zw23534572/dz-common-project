package com.dazong.common.util;

import com.dazong.common.util.entity.Student1;
import com.dazong.common.util.entity.Student2;
import com.dazong.common.util.entity.StudentBase;
import com.dazong.common.util.reflect.ClassWrapper;
import org.junit.Test;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/30
 */
public class ClassWrapperTest {

    @Test
    public void test() throws Exception {

        //根据传进来的参数找到匹配的构造函数实例化一个新的对象
        Class clazz = Class.forName("com.dazong.common.util.entity.Student2");
//        Object obj  = ClassWrapper.wrap(clazz).newOne("");
        //stu1.setStuName("test");
        // assertThat(stu1).isNotNull();
        //assertThat(stu1.getStuName()).isEqualTo("test");

        //获取get,set字段名
        assertThat(ClassWrapper.getSetterName("stuName")).isEqualTo("setStuName");
        assertThat(ClassWrapper.getGetterName("stuName")).isEqualTo("getStuName");
        //Bool 型的 Setter 的名字。如果字段名以 "is"开头，会被截去
        assertThat(ClassWrapper.getBooleanSetterName("isstuName")).isEqualTo("setStuName");
        //Bool 型的 Getter 的名字。以 "is"开头
        assertThat(ClassWrapper.getBooleanGetterName("stuName")).isEqualTo("isStuName");

        //根据一个字段名，获取一组有可能成为 Setter 函数
        assertThat(ClassWrapper.wrap(Student1.class).findSetters("stuPhone")).isNotNull();
        //获取一个字段。这个字段可以是当前类型或者其父类的私有字段。
        assertThat(ClassWrapper.wrap(Student1.class).getField("stuPhone")).isNotNull();

        //获取一个字段。这个字段必须声明特殊的注解，第一遇到的对象会被返回
        //ClassWrapper.wrap(Student2.class).getField(toString());
        //ClassWrapper.wrap(Student2.class).getField(SuppressWarnings.class);

        //获取一组声明了特殊注解的字段


        //根据指定的参数类型数组查找某个匹配的构造器
        assertThat(ClassWrapper.wrap(Student1.class).getConstructor(String.class, int.class)).isNotNull();
        assertThat(ClassWrapper.wrap(Student1.class).getConstructor(char.class)).isNotNull();
        //assertThat(ClassWrapper.wrap(Student1.class).getConstructor(char.class,char.class)).isNotNull();

        //根据名称获取一个 Getter
        assertThat(ClassWrapper.wrap(Student1.class).getGetter("stuPhone").getName()).isEqualTo("getStuPhone");

        //根据字段获取一个 Getter
        assertThat(ClassWrapper.wrap(Student2.class).getGetter(clazz.getField("name")).getName()).isEqualTo("getName");
        //根据字段获取一个 Setter
        assertThat(ClassWrapper.wrap(Student2.class).getSetter(clazz.getField("name")).getName()).isEqualTo("setName");
        //据一个字段名、字段类型获取 Setter
        assertThat(ClassWrapper.wrap(Student1.class).getSetter("stuPhone", String.class).getName()).isEqualTo("setStuPhone");

        // 获得所有的属性，包括私有属性。不包括 Object 的属性
        assertThat(ClassWrapper.wrap(Student2.class).getFields().length).isGreaterThan(5);

        //获取本类型所有的方法，包括私有方法。不包括 Object 的方法
        assertThat(ClassWrapper.wrap(Student2.class).getMethods().length).isGreaterThan(1);

        //获取当前对象，所有的方法，包括私有方法。递归查找至自己某一个父类为止
        assertThat(ClassWrapper.wrap(Student1.class).getAllDeclaredMethods(null).length).isGreaterThan(9);
        assertThat(ClassWrapper.wrap(Student1.class).getAllDeclaredMethods(StudentBase.class).length).isGreaterThan(3);

        //获取所有静态方法
        assertThat(ClassWrapper.wrap(Student1.class).getStaticMethods().length).isGreaterThan(0);

        //为对象的一个字段设值。 不会调用对象的 setter，直接设置字段的值
        Student2 stu2 = new Student2();
        ClassWrapper.wrap(Student2.class).setValue(stu2,clazz.getField("name"),"ttttttttt");
        assertThat(stu2.name).isEqualTo("ttttttttt");
        // 为对象的一个字段设值。优先调用 setter 方法
        ClassWrapper.wrap(Student2.class).setValue(stu2,"name","ffffffffffff");
        assertThat(stu2.name).isEqualTo("ffffffffffff");
        //不调用 getter，直接获得字段的值
        assertThat(ClassWrapper.wrap(Student2.class).getValue(stu2,clazz.getField("name"))).isEqualTo("ffffffffffff");
        //优先通过 getter 获取字段值，如果没有，则直接获取字段值
        assertThat(ClassWrapper.wrap(Student2.class).getValue(stu2,"name")).isEqualTo("ffffffffffff");

        //判断当前对象是否为一个类型。精确匹配，即使是父类和接口，也不相等
        assertThat(ClassWrapper.wrap(Student1.class).is(Student1.class)).isTrue();
        assertThat(ClassWrapper.wrap(Student1.class).is(StudentBase.class)).isFalse();

        //判断当前对象是否为一个类型的子类或者接口的实现类
        assertThat(ClassWrapper.wrap(Student1.class).isOf(Student1.class)).isTrue();
        assertThat(ClassWrapper.wrap(Student1.class).isOf(StudentBase.class)).isTrue();
        assertThat(ClassWrapper.wrap(Student2.class).isOf(StudentBase.class)).isFalse();

        //判断一个类是否是另一个类的子类，或者孙子类、曾孙类....
        assertThat(ClassWrapper.isChildOf(Student1.class,StudentBase.class)).isTrue();
        assertThat(ClassWrapper.isChildOf(StudentBase.class,Student1.class)).isTrue();
        assertThat(ClassWrapper.isChildOf(StudentBase.class,StudentBase.class)).isTrue();
        assertThat(ClassWrapper.isChildOf(Student1.class,Student2.class)).isFalse();

        //判断传进来的类型是否是原型包装类型的一种
        //assertThat(ClassWrapper.wrap(String.class).isPrimitiveWrapClass(String.class)).isTrue();
        //assertThat(ClassWrapper.wrap(StudentBase.class).isPrimitiveWrapClass(Student1.class)).isTrue();

        //判断当前对象是否能直接转换到目标类型，而不产生异常
        assertThat(ClassWrapper.wrap(String.class).canCastToDirectly(String.class)).isTrue();
        assertThat(ClassWrapper.wrap(String.class).canCastToDirectly(int.class)).isFalse();
        assertThat(ClassWrapper.wrap(Student1.class).canCastToDirectly(StudentBase.class)).isTrue();

        //当前对象是否为原生的数字类型 （即不包括 boolean 和 char）
        assertThat(ClassWrapper.wrap(int.class).isPrimitiveNumber()).isTrue();
        assertThat(ClassWrapper.wrap(byte.class).isPrimitiveNumber()).isTrue();
        assertThat(ClassWrapper.wrap(long.class).isPrimitiveNumber()).isTrue();

        //当前对象是否为数字
        assertThat(ClassWrapper.wrap(11).isNumber()).isTrue();
        assertThat(ClassWrapper.wrap(1111.112332).isNumber()).isTrue();
        assertThat(ClassWrapper.wrap(true).isNumber()).isFalse();

        //当前对象是否在表示日期或时间
        assertThat(ClassWrapper.wrap(java.sql.Date.class).isDateTimeLike()).isTrue();
        assertThat(ClassWrapper.wrap(java.util.Date.class).isDateTimeLike()).isTrue();

        //获取一个字段的泛型参数数组，如果这个字段没有泛型，返回空数组


        //如果当前类型不是原生类型，则抛出






//        Method[] methods = ClassWrapper.wrap(Student1.class).getStaticMethods();
//        for (Method m : methods) {
//            System.out.println(m);
//        }
        /* Field[] fileds = ClassWrapper.wrap(Student2.class).getFields();
        for(Field f : fileds){
            System.out.println(f);
        }*/

       /* Method[] methods = ClassWrapper.wrap(Student2.class).getMethods();
        for(Method m : methods){
            System.out.println(m);
        }*/
        System.out.println();


    }

    @Test
    public void test2(){
        int i = 1;
        if(i==1){
            System.out.println("if");
        }
        else if(i==1){
            System.out.println("else if");
        }
    }

}
