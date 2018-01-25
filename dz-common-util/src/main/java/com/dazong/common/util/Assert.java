package com.dazong.common.util;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.exceptions.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * 断言工具类，用于检查传入的参数是否合法等功能
 *
 * @author zhouwei
 * @date 2018/01/09
 */
public class Assert {

    protected static Logger logger = LoggerFactory.getLogger(Assert.class);

    private Assert() {
    }

    /**
     * 判断对象是否为空，为空抛异常
     * @param o
     */
    public static void notNull(Object o) {
        if (o == null)
            throw new ArgumetException(CommonStatus.ILLEGAL_PARAM);
    }

    public static void notNull(Object o,String msg) {
        if (o == null)
            throw new ArgumetException(CommonStatus.ILLEGAL_PARAM.getCode(),msg);
    }

    /**
     * 判断对象是否为空，为空抛异常
     *
     * @param o
     */
    public static void notEmpty(Object o) {
        if (ObjectUtils.isEmpty(o)) {
            logger.warn("notEmpty error:{}", o);
            throw new ArgumetException(CommonStatus.ILLEGAL_PARAM);
        }
    }

    /**
     * 判断对象是否为空，为空抛自定义异常
     *
     * @param o
     * @param filedName
     */
    public static void notEmpty(Object o, String filedName) {
        if (ObjectUtils.isEmpty(o)) {
            String errMsg = filedName + "不能为空";
            logger.warn("required warn:{} ", errMsg);
            throw new BusinessException(CommonStatus.ILLEGAL_PARAM.getCode(), errMsg);
        }
    }




}
