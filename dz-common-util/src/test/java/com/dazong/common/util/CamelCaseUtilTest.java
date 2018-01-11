package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class CamelCaseUtilTest {

    @Test
    public void camelCaseUtilTest() {
        String line = "a_bdd_cdd";
        System.out.println(CamelUnderLineUtil.underline2Camel(line));

        String test2 = "aaBcDd";
        System.out.println(CamelUnderLineUtil.camel2Underline(test2));
    }

}
