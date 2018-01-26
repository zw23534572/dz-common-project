package com.dazong.common.valiadtor;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.validator.ValidatorInterceptor;


public class ValidatorInterceptorTest {
	
	@Test
	public void invokeTest() {
		final ValidatorInterceptor target = new ValidatorInterceptor();
		final ITest testClass = new TestImpl();
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
				MethodInvocation invocation = new MethodInvocation() {

					@Override
					public Object[] getArguments() {
						return args;
					}

					@Override
					public Object proceed() throws Throwable {
						return method.invoke(testClass, args);
					}

					@Override
					public Object getThis() {
						return proxy;
					}

					@Override
					public AccessibleObject getStaticPart() {
						return method;
					}

					@Override
					public Method getMethod() {
						return method;
					}
				};
				return target.invoke(invocation);
			}
		};
		ITest proxyClass = (ITest) Proxy.newProxyInstance(testClass.getClass().getClassLoader(),
				testClass.getClass().getInterfaces(), handler);
		try {
			proxyClass.testMethod(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(ArgumetException.class);
		}
		
		try {
			proxyClass.testMethod(0);
		} catch (Exception e) {
			assertThat(true).isFalse();
		}

	}
}
