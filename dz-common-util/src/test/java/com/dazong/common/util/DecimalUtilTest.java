package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class DecimalUtilTest {

    @Test
    public void decimalUtilTest(){
        System.out.println("" + DecimalUtil.format(3.1415926, "#.##%"));
    }

}
