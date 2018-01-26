package com.dazong.common.util;

import com.dazong.common.util.entity.Student2;
import com.dazong.common.util.entity.Student3;
import com.dazong.common.util.reflect.BeansUtils;
import com.dazong.common.util.reflect.ReflectUtil;
import org.junit.Test;
import org.mockito.internal.util.reflection.Fields;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/26
 */
public class BeansUtilsTest {

    @Test
    public void Test() {
        System.out.println("-------- 获取clazz的所有属性,包括继承获得的属性 -------- ");
        Student2 student2 = new Student2();
        student2.setId(1);
        student2.setName("test");
        Student3 student3 = new Student3();
        BeansUtils.copyPropertiesWithout(student2, student3,"name");
        System.out.println("id=" + student3.getId() + " name=" + student3.getName());
        assertThat(student3.getName()).isNull();


    }

}
