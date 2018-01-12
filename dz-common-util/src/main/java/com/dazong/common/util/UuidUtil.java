package com.dazong.common.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author zisong.wang
 * @date 2018/01/09
 */
public class UuidUtil {

    private UuidUtil(){

    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUuid(int number) {
        if (number < 1) {
            return new String[0];
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUuid();
        }
        return ss;
    }

}