package com.dazong.common.validator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.parameternameprovider.ParanamerParameterNameProvider;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.ArgumetException;

/**
 * 验证切面，基于HibernateValidator实现
 * 
 * @author luobinwen
 *
 */
public class ValidatorInterceptor implements MethodInterceptor {

	private ValidatorFactory validatorFactory;
	private ExecutableValidator executableValidator;

	public ValidatorInterceptor() {
		if (validatorFactory == null) {
			validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
					.parameterNameProvider(new ParanamerParameterNameProvider()).buildValidatorFactory();
		}
		executableValidator = validatorFactory.getValidator().forExecutables();
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		// 方法参数校验
		Method method = invocation.getMethod();
		Object target = invocation.getThis();
		Object[] args = invocation.getArguments();
		Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(target, method, args);
		if (!violations.isEmpty()) {
			throw new ArgumetException(CommonStatus.ILLEGAL_PARAM.joinSystemStatusCode(getViolationMsg(violations)));
		}

		return invocation.proceed();
	}

	private String getViolationMsg(Set<ConstraintViolation<Object>> violations) {
		StringBuilder sb = new StringBuilder();
		Iterator<ConstraintViolation<Object>> iter = violations.iterator();
		while (iter.hasNext()) {
			ConstraintViolation<Object> cv = iter.next();
			String argsName = cv.getPropertyPath().toString();
			sb.append(argsName);
			sb.append(cv.getMessage());
			if (iter.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
