package com.dazong.common.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class NumberUtilsTest {

    @Test
    public void decimalUtilTest() {
        double pai = 3.1415926;
        //格式化long类型，自定义格式
        assertThat(NumberUtils.format(pai, "#.##%")).isEqualTo("314.16%");

        //提供精确的小数位四舍五入处理
        assertThat(NumberUtils.round(pai,2)).isEqualTo(3.14);

        //果输入一个为空的数字返回0
        BigDecimal num = null;
        assertThat(NumberUtils.defaultNumber(num)).isEqualTo("0");

    }

}
