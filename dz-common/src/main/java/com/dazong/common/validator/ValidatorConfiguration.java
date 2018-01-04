package com.dazong.common.validator;

import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import com.dazong.common.annotation.EnableValiadtor;

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
		advisor.setAdvice(validatorInterceptor);

		String[] names = context.getBeanNamesForAnnotation(EnableValiadtor.class);
		for (String name : names) {
			Object bean = context.getBean(name);
			EnableValiadtor settings = AnnotationUtils.findAnnotation(bean.getClass(), EnableValiadtor.class);
			advisor.setPatterns(settings.patterns());
		}

		return advisor;
	}

	@Bean
	public ValidatorInterceptor getValidatorInterceptor() {
		return new ValidatorInterceptor();
	}
}
