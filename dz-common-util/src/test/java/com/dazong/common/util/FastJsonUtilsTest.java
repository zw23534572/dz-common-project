package com.dazong.common.util;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class FastJsonUtilsTest {

    @Test
    public void md5UtilTest(){
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


    }

}
