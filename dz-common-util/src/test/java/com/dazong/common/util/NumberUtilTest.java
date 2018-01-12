package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class NumberUtilTest {

    @Test
    public void decimalUtilTest(){
        System.out.println("" + NumberUtil.format(3.1415926, "#.##%"));
    }

}
