package com.dazong.common.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class DatesUtilsTest {

    @Test
    public void test() throws Exception{
        Calendar c = Calendar.getInstance();

        //获取当月，yyyy-MM
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        assertThat(DatesUtils.getCurrentMonthAsString()).isEqualTo(monthFormat.format(c.getTime()));

        //获取当天，yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertThat(DatesUtils.getCurrentDayAsString()).isEqualTo(dateFormat.format(c.getTime()));

        //获取当前时间  yyyy-MM-dd HH:mm:ss.SSS
        SimpleDateFormat dateTimeFormatMilli = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        assertThat(DatesUtils.getCurrentMilliAsString().length()).isEqualTo(dateTimeFormatMilli.format(c.getTime()).length());

        //格式化时间,Date转String
        assertThat(DatesUtils.getCurrentMonthAsString()).isEqualTo(DatesUtils.dateToStr(DatesUtils.getCurrentDay(),"yyyy-MM"));

        //格式化字符串，String转Date
        DateFormat myFormatter = new SimpleDateFormat("yyyy-MM");
        assertThat(DatesUtils.strToDate(DatesUtils.getCurrentDayAsString(),"yyyy-MM")).isEqualTo(myFormatter.parse(DatesUtils.getCurrentDayAsString()));

    }

}
