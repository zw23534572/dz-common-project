package com.dazong.common.trans.aspect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncExecutionInterceptor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import com.dazong.common.trans.DzTransactionManager;
import com.dazong.common.trans.SimpleThreadFactory;
import com.dazong.common.trans.support.DzTransactionInfo;

/**
 * 异步执行事务代理
 * 
 * @author hujunzhong
 *
 */
public class DzTransactionProcessAsyncDelegate implements DzTransactionProcess {

	private static Logger logger = LoggerFactory.getLogger(DzTransactionProcessAsyncDelegate.class);

	private AsyncExecutionInterceptor asyncInterceptor;

	private DzTransactionProcess targetProcess;

	public DzTransactionProcessAsyncDelegate(DzTransactionProcess targetProcess, int nThreads) {
		logger.info("异步事务线程数:{}", nThreads);

		Executor executor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new SimpleThreadFactory());
		asyncInterceptor = new AsyncExecutionInterceptor(executor);
		asyncInterceptor.setExceptionHandler(new AsyncUncaughtExceptionHandler() {

			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				logger.error("async事务" + method + "执行失败!", ex);
			}
		});

		this.targetProcess = targetProcess;
	}

	@Override
	public Object doTransaction(final MethodInvocation invocation, final DzTransactionManager tm,
			final TransactionAttribute define, final DzTransactionInfo txInfo) throws Throwable {
		MethodInvocation delegateInvocation = new MethodInvocation() {
			@Override
			public Object proceed() throws Throwable {
				return targetProcess.doTransaction(invocation, tm, define, txInfo);
			}

			@Override
			public Object getThis() {
				return invocation.getThis();
			}

			@Override
			public AccessibleObject getStaticPart() {
				return invocation.getStaticPart();
			}

			@Override
			public Object[] getArguments() {
				return invocation.getArguments();
			}

			@Override
			public Method getMethod() {
				return invocation.getMethod();
			}
		};

		return asyncInterceptor.invoke(delegateInvocation);
	}

}
