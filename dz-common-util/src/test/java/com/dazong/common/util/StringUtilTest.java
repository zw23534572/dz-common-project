package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class StringUtilTest {

    @Test
    public void stringUtilTest(){
        System.out.println(StringUtils.firstCharToLowerCase("AbcdD"));
        System.out.println(StringUtils.firstCharToUpperCase("aBcded"));
    }

    @Test
    public void camelCaseUtilTest() {
        String line = "a_1bdd_cdd";
        System.out.println(StringUtils.underline2Camel(line));

        String test2 = "1aa1BcDd";
        System.out.println(StringUtils.camel2Underline(test2));
    }


}
