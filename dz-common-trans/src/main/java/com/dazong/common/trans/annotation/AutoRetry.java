package com.dazong.common.trans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 大宗分布式事务注解,带有此注解的方法会启用事务,如果抛出了需要retry的异常后会有服务去不断的重试这个方法直到成功,保证数据最终一致性
 * @author hujunzhong
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoRetry {
	
	/**
	 * 事务名,不能重复.可为空,默认为当前的方法全名
	 * @return
	 */
	String name() default "";

	/**
	 * 事物嵌套时处理方法
	 * @return
	 */
	Propagation propagation() default Propagation.REQUIRED;

	/**
	 * 需要重试的异常,默认为runtimeexception和error
	 * @return
	 */
	Class<? extends Throwable>[] retryFor() default {};
	
	/**
	 * 业务正常处理完成返回失败的异常类型.不会重试.
	 * @return
	 */
	Class<? extends Throwable>[] commitFailFor() default {};
	
	/**
	 * 重试超时间时间,超过这个时间没有commit重试,单位ms.默认5分钟
	 * @return
	 */
	long timeout() default 5*60*1000;
	
	/**
	 * 最大重试次数,为0时不限
	 * @return
	 */
	int maxTryTimes() default 0;
	
	/**
	 * 是否异步执行
	 * @return
	 */
	boolean async() default false;
	
}
