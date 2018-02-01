package com.dazong.common.util;

import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class JsonUtilsTest {

    @Test
    public void test() {
        String json = "{\n" +
                "    \"errcode\": 0,\n" +
                "    \"errmsg\": \"ok\",\n" +
                "    \"department\": [\n" +
                "        {\n" +
                "           \"id\": 2,\n" +
                "            \"name\": \"钉钉事业部\",\n" +
                "            \"parentid\": 1,\n" +
                "            \"createDeptGroup\": true,\n" +
                "            \"autoAddUser\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"服务端开发组\",\n" +
                "            \"parentid\": 2,\n" +
                "            \"createDeptGroup\": false,\n" +
                "            \"autoAddUser\": false\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String str = JsonUtils.toJsonSuccess("转换", json);
        System.out.println(str);


    }

}
