package com.dazong.common.util;

import org.junit.Test;

import java.util.*;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class DateUtilTest {

    @Test
    public void md5UtilTest(){
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        System.out.println(DateUtils.getCurrentDayAsString());

        Map<String, String> FILE_MAGIC_HEADERS = new HashMap<>();
        FILE_MAGIC_HEADERS.put("46726f6d3a203d3f6762", "eml");
        // MS Excel、Word、Msi
        FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "doc");
        // Visio
        FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "vsd");
        FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "wps");

        Set<Map.Entry<String, String>> entrySet = FILE_MAGIC_HEADERS.entrySet();
        //将关系集合entrySet进行迭代，存放到迭代器中
        Iterator<Map.Entry<String, String>> it2 = entrySet.iterator();

        while(it2.hasNext()){
            Map.Entry<String, String> me = it2.next();//获取Map.Entry关系对象me
            String key2 = me.getKey();//通过关系对象获取key
            String value2 = me.getValue();//通过关系对象获取value

            System.out.println("key: "+key2+"-->value: "+value2);
        }

    }

}
