package com.dazong.common.validator;


import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import com.dazong.common.web.annotation.EnableValiadtor;

/**
 * 
 * @author luobinwen
 *
 */
@Configuration
public class ValidatorConfiguration {
	@Bean
	public RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor(ValidatorInterceptor validatorInterceptor,
			ApplicationContext context) {
		RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();
		String[] patterns = getPatterns(context);
		if (patterns.length > 0) {
			advisor.setAdvice(validatorInterceptor);
			advisor.setPatterns(patterns);
		}

		return advisor;
	}

	private String[] getPatterns(ApplicationContext context) {
		String[] patterns = new String[0];
		String[] names = context.getBeanNamesForAnnotation(EnableValiadtor.class);
		for (String name : names) {
			Object bean = context.getBean(name);
			EnableValiadtor settings = AnnotationUtils.findAnnotation(bean.getClass(), EnableValiadtor.class);
			patterns = settings.patterns();
		}

		return patterns;
	}

	@Bean
	public ValidatorInterceptor getValidatorInterceptor() {
		return new ValidatorInterceptor();
	}
}
