package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class CamelCaseUtilTest {

    @Test
    public void camelCaseUtilTest() {
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toUnderlineName("abc_c_ddd"));
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toCamelCase("abc_c_ddd"));
        System.out.println("abc_c_ddd：" + CamelCaseUtil.toCapitalizeCamelCase("abc_c_ddd"));

        System.out.println("abcCDdd：" + CamelCaseUtil.toUnderlineName("abcCDdd"));
        System.out.println("abcCDdd：" + CamelCaseUtil.toCamelCase("abcCDdd"));
        System.out.println("abcCDdd：" + CamelCaseUtil.toCapitalizeCamelCase("abcCDdd"));
    }

}
