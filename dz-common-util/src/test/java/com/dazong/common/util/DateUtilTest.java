package com.dazong.common.util;

import org.junit.Test;

import java.util.Calendar;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class DateUtilTest {

    @Test
    public void md5UtilTest(){
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        System.out.println(DateUtil.getCurrentDayAsString());
    }

}
