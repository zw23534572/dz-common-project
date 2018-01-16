package com.dazong.example.web.controller;

import com.dazong.common.cache.constants.IExpire;
import com.dazong.common.cache.manager.CacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DanielLi
 * @date: 2018/1/12
 * @description:缓存框架使用说明
 */
@RestController
public class RedisController {


    @Autowired
    CacheFactory cacheFactory;


    /**
     * 统一正常封包
     */
    @RequestMapping("/set")
    @ResponseBody
    public List<String> testSetList() {
        List<String> stringList = new ArrayList<>();
        String key = "demo";
        stringList.add("hello");
        stringList.add("world");
        cacheFactory.getDefaultCacheHandler().saveList(key, stringList, IExpire.FIVE_MIN);
        return stringList;
    }

    /**
     * 统一异常拦截
     */
    @RequestMapping("/get")
    @ResponseBody
    public List<String> testGetList() {
        String key = "demo";
        List<String> stringList = cacheFactory.getDefaultCacheHandler().getList(key,String.class);
        return stringList;
    }
}
