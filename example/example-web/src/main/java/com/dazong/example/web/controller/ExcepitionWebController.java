package com.dazong.example.web.controller;

import com.dazong.common.exceptions.ArgumetException;
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
     * 统一异常拦截
     */
    @RequestMapping("/test1")
    @ResponseBody
    public List<String> test1() {
        throw new ArgumetException(88500, "业务系统的空指针异常");
    }
}
