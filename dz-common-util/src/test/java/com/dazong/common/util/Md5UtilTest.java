package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class Md5UtilTest {

    @Test
    public void md5UtilTest(){
        System.out.println(Md5Util.digest32("3869cd84918b7392b20ebb88b31420891002张三0"));
        System.out.println(Md5Util.digest("3869cd84918b7392b20ebb88b31420891002张三0"));
    }

}
