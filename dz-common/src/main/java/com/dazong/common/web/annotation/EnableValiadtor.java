package com.dazong.common.web.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.dazong.common.validator.ValidatorConfiguration;

/**
 * 启用验证 基于JSR 349 Bean Validation 1.1，要提供具体拦截表达式
 * @author luobinwen
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ValidatorConfiguration.class)
@Documented
public @interface EnableValiadtor {
	String[] patterns() default {}; 
}
