package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class CharsetUtilsTest {

    @Test
    public void test(){
		System.out.println(CharsetUtils.utf8("250Abc你好"));
        System.out.println(CharsetUtils.iso8859("250Abc你好"));
        System.out.println(CharsetUtils.gbk("250Abc你好"));
        System.out.println(CharsetUtils.gb2312("250Abc你好"));

    }

}
