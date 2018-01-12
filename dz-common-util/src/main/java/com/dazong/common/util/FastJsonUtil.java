/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.dazong.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

public class FastJsonUtil {
    public FastJsonUtil() {
    }

    /**
     * SerializerFeature.PrettyFormat -json 格式化
     * SerializerFeature.WriteDateUseDateFormat -时间格式转化
     * SerializerFeature.WriteMapNullValue -保留空的字段
     * SerializerFeature.WriteNullStringAsEmpty -String null -> ""
     * SerializerFeature.WriteNullNumberAsZero -Number null -> 0
     */
    private static SerializerFeature[] serializerFeatures = new SerializerFeature[]{
            SerializerFeature.PrettyFormat,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullNumberAsZero};

    public static SerializerFeature[] getSerializerFeatures() {
        return serializerFeatures;
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, serializerFeatures);
    }

    public static String toJson(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    public static Object parse(String json) {
        return JSON.parse(json);
    }

    public static <T> T parse(String json, Class<T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    public static String toJsonSuccess(String msg, Object obj) {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("status", 1);
        mp.put("state", "success");
        mp.put("msg", msg);
        mp.put("result", obj);
        return toJson(mp);
    }

    public static String toJsonError(String msg, Object obj) {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("status", 0);
        mp.put("state", "error");
        mp.put("msg", msg);
        mp.put("result", obj);
        return toJson(mp);
    }

    public static void main(String[] args) {

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
