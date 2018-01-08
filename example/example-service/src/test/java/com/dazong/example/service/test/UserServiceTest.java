package com.dazong.example.service.test;

import com.dazong.common.feign.client.api.IUserInfoService;
import com.dazong.common.feign.client.dto.response.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zhiyuan.wang on 2018/1/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void saveUser() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setUserId(1L);
        user.setEmail("xxx.dazong.com");
        userService.saveUser(user);
    }

}