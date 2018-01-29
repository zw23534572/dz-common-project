package com.dazong.common.util;

import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class CommonUtilsTest {

    @Test
    public void test() {
        //1. 为null则返回空字符串
        assertThat(CommonUtils.toString(null, "")).isEqualTo("");

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        //2. 返回list第一个元素
        assertThat(CommonUtils.getFirst(list)).isEqualTo("1");

        //3. 字符串转Map
        Map<String, String> map = CommonUtils.string2Map("a->test1;b->test2;gggg");
        System.out.println("字符串转map,以指定字符串分隔,结果为:b,test2 a,test1");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(key + "," + val);
        }

        Map<String, String> map2 = CommonUtils.string2Map("1&test1。","&","。");
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("test1");
        }

        //4. 数组生成map
        String[] arr1 = {"1","2"};//,"3","4","5","6"

        Map<String, String> map3 = CommonUtils.map(arr1);
        for (Map.Entry<String, String> entry : map3.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("2");
        }

        Map<Object, Object> map4 = CommonUtils.mapByAarray();
        for (Map.Entry<Object, Object> entry : map4.entrySet()) {
            Object key = entry.getKey();
            Object val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("2");
        }

    }

}
