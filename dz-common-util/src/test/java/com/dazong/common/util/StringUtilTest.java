package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class StringUtilTest {

    @Test
    public void stringUtilTest(){
        System.out.println(StringUtil.firstCharToLowerCase("AbcdD"));
        System.out.println(StringUtil.firstCharToUpperCase("aBcded"));
    }

}
