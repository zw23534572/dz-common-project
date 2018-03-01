package com.dazong.example.web.controller;

import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.resp.DataResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ExcepitionWebController {

    /**
     * 统一正常封包
     */
    @RequestMapping("/test")
    @ResponseBody
    public List<String> test() {
        List<String> stringList = new ArrayList<>();
        stringList.add("测试1");
        stringList.add("测试2");
        return stringList;
    }

    /**
     * 统一正常封包
     * 如果为dataResponse类型，无需封装
     */
    @RequestMapping("/test1")
    @ResponseBody
    public DataResponse test1() {
        List<String> stringList = new ArrayList<>();
        stringList.add("测试1");
        stringList.add("测试2");

        DataResponse result = new DataResponse<>();
        result.setData(stringList);

        return result;
    }


    /**
     * 跳过封包的操作
     */
    @RequestMapping("/test2")
    @ResponseBody
    public List<String> test2() {
        List<String> stringList = new ArrayList<>();
        stringList.add("测试1");
        stringList.add("测试2");
        return stringList;
    }


    /**
     * 统一异常拦截
     */
    @RequestMapping("/test3")
    @ResponseBody
    public List<String> test3() {
        throw new ArgumetException(88500, "业务系统的空指针异常");
    }
}
