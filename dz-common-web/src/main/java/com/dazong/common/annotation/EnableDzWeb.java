package com.dazong.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：
 * 1.统一异常拦截
 * 2.统一消息返回，封装成公司的commonresponse
 * </B><BR>
 * @author 贸易研发部：Zhouwei
 * @since 2018年1月11日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDzWeb {

}
