package com.dazong.example.test.curd;


import com.dazong.example.dao.entity.User;
import com.dazong.example.dao.mapper.UserMapper;
import com.dazong.example.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
public class UpdateByIdUserTest extends BaseTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 测试1，更新单个字段测试
     */
    @Test
    public void n001() {
        List<User> list = userMapper.selectList(new User());
        for (User user : list) {
            System.out.println("UpdateByIdUserTest user" + user);
            user.setName("更新字段");
            int result = userMapper.updateById(user);
            Assert.assertTrue(retBool(result));
        }
    }

}
