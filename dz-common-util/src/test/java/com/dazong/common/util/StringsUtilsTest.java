package com.dazong.common.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.dazong.common.util.CommonUtils.asArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class StringsUtilsTest {

    @Test
    public void test() {
        //下划线转驼峰
        String line = "a_1bdd_cdd";
        assertThat(StringsUtils.underline2Camel(line)).isEqualTo("a1bddCdd");

        //驼峰转下划线
        String line2 = "1aa1BcDd";
        assertThat(StringsUtils.camel2Underline(line2)).isEqualTo("1aa1_Bc_Dd");

        //获取UUID
        assertThat(StringsUtils.getUuid().length()).isEqualTo(32);
        assertThat(StringsUtils.getUuid(2).length).isEqualTo(2);

        //判断是否为空白字符
        assertThat(StringsUtils.isBlank("\t")).isTrue();
        assertThat(StringsUtils.isBlank("\n")).isTrue();
        assertThat(StringsUtils.isBlank("   ")).isTrue();//Tab
        assertThat(StringsUtils.isBlank("")).isTrue();
        assertThat(StringsUtils.isBlank(" ")).isTrue();
        assertThat(StringsUtils.isBlank("t")).isFalse();

        //字符串不为 null 而且不为 "" 并且不等于other
        assertThat(StringsUtils.isNotEmptyAndNotEquelsOther("a", "a", "b", "c")).isFalse();
        assertThat(StringsUtils.isNotEmptyAndNotEquelsOther("d", "a", "b", "c")).isTrue();

        //根据一个正则式，将字符串拆分成数组，空元素将被忽略
        String s = "a  _b _c_ d";
        String regex = "_";
        assertThat(StringsUtils.splitIgnoreBlank(s, regex).length).isEqualTo(4);

        //将一个数组的部分元素转换成字符串
        String[] arr = {"1", "2", "333", "4","555"};
        assertThat(StringsUtils.concat(1,1,"_",arr).toString()).isEqualTo("2");
        assertThat(StringsUtils.concat(1,2,"_",arr).toString()).isEqualTo("2_333");
        assertThat(StringsUtils.concat(1,3,"&",arr).toString()).isEqualTo("2&333&4");

        //将一个集合转换成字符串
        List<String> list = asArrayList("1", "2", "333", "555");
        assertThat(StringsUtils.concat("_", list).toString()).isEqualTo("1_2_333_555");

        //分割字符串为数字集合
        String str2 = "100,3.1415926,200000,8";
        List list2 =  StringsUtils.splitToNumberList(str2,",",BigDecimal.class);
        assertThat(list2.size()).isEqualTo(4);
        assertThat(list2.get(0)).isEqualTo(new BigDecimal(100));
        assertThat(list2.get(1)).isEqualTo(new BigDecimal(3.1415926).setScale(7,BigDecimal.ROUND_DOWN));
        assertThat(list2.get(2)).isEqualTo(new BigDecimal(200000));
        assertThat(list2.get(3)).isEqualTo(new BigDecimal(8));

        //判断是否邮箱
        assertThat(StringsUtils.isEmail("111@@.com")).isFalse();
        assertThat(StringsUtils.isEmail("111@@11.com")).isFalse();
        assertThat(StringsUtils.isEmail("111@11.com")).isTrue();

        //判断是否URL
        assertThat(StringsUtils.isUrl("http:///www.123.com")).isFalse();
        assertThat(StringsUtils.isUrl("http://www.1213.com.cn")).isTrue();
        assertThat(StringsUtils.isUrl("http://www.123.com.cn.cnn")).isTrue();

    }

}
