package com.dazong.common.util;

import com.dazong.common.ApplicationInfo;
import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.exceptions.BusinessException;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author: zisong.wang
 * @date: 2018/1/26
 */
public class AssertTest {

    /**
     * 验证参数
     */
    @Test
    public void test1() {
        try {
            String str = null;
            Assert.notNull(str);
            assertThat(true).isFalse();
        } catch (ArgumetException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            String str = null;
            Assert.notNull(str, "str");
            assertThat(true).isFalse();
        } catch (ArgumetException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    /**
     * 包含系统码，测试
     */
    @Test
    public void test3() {
        try {
            ApplicationInfo.instance().setApplicationInfo(110);
            String str = null;
            Assert.notNull(str, "str");
            assertThat(true).isFalse();
        } catch (ArgumetException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    @Test
    public void test4() {
        try {
            Map map = null;
            Assert.notEmpty(map, "map");
            assertThat(true).isFalse();
        } catch (BusinessException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    /**
     * 包含系统码，测试
     */
    @Test
    public void test5() {
        try {
            ApplicationInfo.instance().setApplicationInfo(110);
            Map map = null;
            Assert.notEmpty(map, "map");
            assertThat(true).isFalse();
        } catch (BusinessException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    /**
     * 直接抛出参数异常
     */
    @Test
    public void test6() {
        try {
            ApplicationInfo.instance().setApplicationInfo(110);
            Assert.throwIllegalException("filedName");
            assertThat(true).isFalse();
        } catch (ArgumetException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    /**
     * 直接抛出业务异常
     */
    @Test
    public void test7() {
        try {
            ApplicationInfo.instance().setApplicationInfo(110);
            Assert.throwBusinessException("不能删除出数据");
            assertThat(true).isFalse();
        } catch (BusinessException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }

    /**
     * 直接抛出业务异常
     */
    @Test
    public void test8() {
        try {
            ApplicationInfo.instance().setApplicationInfo(110);
            String field ="   ";
            Assert.notBlank(field,"用户名");
            assertThat(true).isFalse();
        } catch (ArgumetException e) {
            System.out.println("异常捕获code: " + e.getCode() + ",描述:" + e.getMessage());
        }
    }


}
