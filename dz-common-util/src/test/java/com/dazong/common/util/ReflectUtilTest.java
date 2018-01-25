package com.dazong.common.util;

import com.dazong.common.util.codec.AesUtils;
import com.dazong.common.util.reflect.ReflectUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class ReflectUtilTest {

    @Test
    public void reflectUtilTest(){
        System.out.println(AesUtils.digest("3869cd84918b7392b20ebb88b31420891002张三0"));
        System.out.println(AesUtils.digest("3869cd84918b7392b20ebb88b31420891002张三0"));
        for (Field f : ReflectUtil.getAllFields(AesUtils.class)) {
            System.out.println(f);
        }
    }

}
