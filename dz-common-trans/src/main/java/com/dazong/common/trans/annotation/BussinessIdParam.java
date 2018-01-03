package com.dazong.common.trans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务关连的业务唯一id注解.该注解应用在服务方法的参数上.
 * 如果被注解的参数是基本类型或string,直接取该参数为bussinessid
 * 如果是map类型,取该map中key等于注解的value的值.
 * 如果是其它object类型,取该object中对应field的值
 * 如果是事务嵌套,子事务没有配置BussinessIdParam,则继承父事务的bussinessid
 * @author hujunzhong
 *
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BussinessIdParam {
	String value() default "";
}
