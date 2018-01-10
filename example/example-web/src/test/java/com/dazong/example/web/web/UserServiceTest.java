package com.dazong.example.web.web;

import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.example.web.StartupServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhiyuan.wang on 2018/1/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartupServer.class)
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void saveUser() throws Exception {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                UserInfo user = new UserInfo();
                user.setId(3L);
                user.setUserId(3L);
                user.setEmail("xxx.dazong.com");
                userService.saveUser(user);
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(2);
        UserInfo user = new UserInfo();
        user.setId(3L);
        user.setUserId(3L);
        user.setEmail("xxx.dazong.com");
        userService.saveUser(user);
    }

}