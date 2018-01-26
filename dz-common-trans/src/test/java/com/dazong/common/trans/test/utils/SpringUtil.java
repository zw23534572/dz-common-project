package com.dazong.common.trans.test.utils;

import org.springframework.aop.framework.AopContext;

public abstract class SpringUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(T t){
		try {
			return (T) AopContext.currentProxy();
		} catch (IllegalStateException e) {
			return t;
		}
	}
}
