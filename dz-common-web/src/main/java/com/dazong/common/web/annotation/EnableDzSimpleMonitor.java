package com.dazong.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：
 * 项目检测
 * 开启后访问  http://localhost/simpleMonitor
 * </B><BR>
 * @author 贸易研发部：Zhouwei
 * @since 2017年12月27日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDzSimpleMonitor {

}
