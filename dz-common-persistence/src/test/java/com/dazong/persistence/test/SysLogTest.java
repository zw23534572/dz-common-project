package com.dazong.persistence.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dazong.persistence.BaseTest;
import com.dazong.persistence.mybatis.entity.SysLog;
import com.dazong.persistence.mybatis.entity.SysMenu;
import com.dazong.persistence.mybatis.entity.User;
import com.dazong.persistence.mybatis.mapper.SysLogDao;
import com.dazong.persistence.mybatis.mapper.SysMenuDao;
import com.dazong.persistence.mybatis.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
public class SysLogTest extends BaseTest {

    @Autowired
    SysLogDao sysLogDao;
    @Autowired
    SysMenuDao sysMenuDao;
    @Autowired
    UserMapper userMapper;

    /**
     * 测试1，查询所有字段
     */
    @Test
    public void n001() {
        Page<SysLog> page = PageHelper.startPage(0, 2);
        List<SysLog> data = sysLogDao.selectList(new SysLog());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (SysLog sysLog : page.getResult()) {
            System.out.println("page result:" + sysLog);
        }
    }

    /**
     * 测试2，pageHelpler 查询
     */
    @Test
    public void n002() {
        Page<SysMenu> page = PageHelper.startPage(1, 2);
        List<SysMenu> data = sysMenuDao.selectList(new SysMenu());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (SysMenu sysMenu : page.getResult()) {
            System.out.println("page result:" + sysMenu);
        }
    }

    /**
     * 测试3，pageHelpler 查询
     */
    @Test
    public void n003() {

        Page<User> page = PageHelper.startPage(1, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }


        page = PageHelper.startPage(2, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }


        page = PageHelper.startPage(3, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }
    }


}
