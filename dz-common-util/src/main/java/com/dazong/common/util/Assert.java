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
     *
     * @param o
     */
    public static void isEmpty(Object o) {
        if (ObjectUtils.isEmpty(o)) {
            logger.warn("isEmpty error:{}", o);
            throw new ArgumetException(CommonStatus.ILLEGAL_PARAM);
        }
    }

    /**
     * 判断对象是否为空，为空抛自定义异常
     *
     * @param o
     * @param filedName
     */
    public static void isEmpty(Object o, String filedName) {
        if (ObjectUtils.isEmpty(o)) {
            String errMsg = filedName + "不能为空";
            logger.warn("required warn:{} ", errMsg);
            throw new BusinessException(CommonStatus.ILLEGAL_PARAM.getCode(), errMsg);
        }
    }

    /**
     * 判断是否为空，为空抛异常
     *
     * @param objs
     */
    public static void isNotEmpty(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new PlatformException(CommonStatus.ILLEGAL_PARAM, "对象不能为空", obj);
            }
        }
    }

    public static void pattern(String parameter, String pattern) {
        if (!parameter.matches(pattern)) {
            throw new BusinessException(CommonStatus.ILLEGAL_PARAM);
        }
    }

    /**
     * 判断是否为邮箱，不是抛异常
     *
     * @param parameter
     */
    public static void isEmail(String parameter) {
        pattern(parameter, "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    /**
     * 判断是否URL，不是抛异常
     *
     * @param parameter
     */
    public static void isUrl(String parameter) {
        pattern(parameter, "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    }

}
