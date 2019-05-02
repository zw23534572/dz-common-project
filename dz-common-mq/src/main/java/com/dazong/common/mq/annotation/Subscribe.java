package com.dazong.common.mq.annotation;

import com.dazong.common.mq.constant.SubscribeType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Subscribe {

	String topic() default "";

	String queue() default "";

	String name() default "";

	/**
	 * 重复通知最大次数，-1 表示不控制
	 * @return 次数
	 */
	int notifyMaxCount() default -1;

	/**
	 * 配置文件中配置重复通知最大次数的key，"" 或者 -1 表示不控制
	 * @return key
	 */
	String notifyMaxCountPropKey() default "";

	SubscribeType type() default SubscribeType.ACTIVEMQ;
}
