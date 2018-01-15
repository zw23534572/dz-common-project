package com.dazong.common.util;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.dazong.common.util.codec.DigestUtil.HASH_INTERATIONS;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class ChartsetUtilTest {

    @Test
    public void md5UtilTest(){
		System.out.println(CharsetUtil.utf8("你好"));
		String[] obj = {};
		System.out.println(CommonUtil.removeIfEmpty2(obj));
    }

}
