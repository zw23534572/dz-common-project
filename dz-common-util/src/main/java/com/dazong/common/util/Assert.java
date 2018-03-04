package com.dazong.common.util;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.exceptions.BusinessException;

import org.apache.commons.lang.StringUtils;
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
    public static void notNull(Object o) {
        notNull(o, "null");
    }

    public static void notNull(Object o, String msg) {
        if (o == null) {
            throwIllegalException(msg);
        }
    }

    /**
     * 判断字符串 为null,"","  "时，抛出异常
     * @param field 字段值
     * @param fieldName 字段名称
     */
    public static void notBlank(String field,String fieldName) {
        if (StringUtils.isBlank(field)){
            throwIllegalException(fieldName);
        }
    }



    /**
     * 判断对象是否为空，为空抛异常
     *
     * @param o
     */
    public static void notEmpty(Object o) {
         notEmpty(o,"filedName");
    }

    /**
     * 判断对象是否为空，为空抛自定义异常
     *
     * @param o
     * @param filedName
     */
    public static void notEmpty(Object o, String filedName) {
        if (ObjectUtils.isEmpty(o)) {
            filedName = CommonStatus.ILLEGAL_PARAM.getMessage().replace("{0}", filedName);
            throw new BusinessException(CommonStatus.ILLEGAL_PARAM.joinSystemStatusCode(filedName));
        }
    }

    /**
     * 抛出参数异常
     * @param filedName 参数名称
     */
    public static void throwIllegalException(String filedName){
        String errMsg = CommonStatus.ILLEGAL_PARAM.getMessage().replace("{0}", filedName);
        throw new ArgumetException(CommonStatus.ILLEGAL_PARAM.joinSystemStatusCode(errMsg));
    }

    /**
     * 抛出业务异常
     */
    public static void throwBusinessException(String msg){
        throw new BusinessException(CommonStatus.FAIL.joinSystemStatusCode(msg));
    }

}
