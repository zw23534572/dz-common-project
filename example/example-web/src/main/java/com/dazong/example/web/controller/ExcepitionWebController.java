package com.dazong.example.web.controller;

import com.dazong.common.exceptions.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huqichao
 * @date 2018-01-08 10:19
 */
@RestController
@RequestMapping("/exception")
public class ExcepitionWebController {

    @RequestMapping("/test")
    public List<String> test(String content) {
        List<String> list = new ArrayList<>();
        if (content.equals("1")) {
            list.add("1");
            list.add("2");
            return list;
        } else {
            throw new BusinessException(11, "test");
        }
    }

}
