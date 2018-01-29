package com.dazong.common.trans.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.dazong.common.trans.DzTransactionException;
import com.dazong.common.trans.DzTransactionManager;
import com.dazong.common.trans.DzTransactionScheduler;
import com.dazong.common.trans.TransactionDefinition;
import com.dazong.common.trans.TransactionStatus;
import com.dazong.common.trans.annotation.BussinessIdParam;
import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.common.trans.properties.DzTransactionProperties;
import com.dazong.common.trans.support.DzTransactionInfo;
import com.dazong.common.trans.support.DzTransactionSupport;
import com.dazong.common.trans.support.DzTransactionSyncManager;

/**
 * 事务切面
 * @author hujunzhong
 *
 */
@Configuration
public class TransactionAspect
		implements ApplicationListener<ContextRefreshedEvent>, MethodInterceptor, DzTransactionProcess {

	private static Logger logger = LoggerFactory.getLogger(TransactionAspect.class);
	
	@Autowired
	private DzTransactionProperties dzTransactionConfig;

	private DzTransactionManager dzTransactionManager;

	private DzTransactionProcessAsyncDelegate asyncDelegate;
	
	

	public void onApplicationEvent(ContextRefreshedEvent event) {
		final ApplicationContext ctx = event.getApplicationContext();
		dzTransactionManager = ctx.getBean(this.dzTransactionConfig.getTransactionManagerBeanName(),
				DzTransactionManager.class);
		boolean async = false;
		for (String beanName : ctx.getBeanDefinitionNames()) {
			Object bean = ctx.getBean(beanName);
			final Class<?> type = AopUtils.getTargetClass(bean);
			Method[] methods = type.getDeclaredMethods();
			for (Method method : methods) {
				AutoRetry ann = method.getAnnotation(AutoRetry.class);
				if (ann != null) {
					String transactionName = DzTransactionSupport.getDefaultIfNull(ann.name(),
							type.getName() + "." + method.getName());
					getTransactionManager().registerTransaction(transactionName, type, method, bean);
					if (ann.async()) {
						async = true;
					}
				}
			}
		}

		if (async) {
			asyncDelegate = new DzTransactionProcessAsyncDelegate(this,this.dzTransactionConfig.getAsyncTransactionThreadSize());
		}

		// 初始化Scheduler
		DzTransactionScheduler.get().setManager(dzTransactionManager);
	}

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		DzTransactionManager tm;
		TransactionAttribute define;
		DzTransactionInfo txInfo;
		try {
			tm = getTransactionManager();
			Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis())
					: invocation.getMethod().getDeclaringClass());
			define = getTransactionAttr(invocation, targetClass);
			txInfo = createTransaction(tm, define);
		} catch (DzTransactionException e) {
			logger.warn("开启事务失败:{}", e.getMessage());
			throw e;
		} catch (Throwable e) {
			logger.error("开启事务异常:{}", e.getMessage());
			throw e;
		}

		DzTransactionProcess process = getTxProcessDelegate(define, txInfo);
		return process.doTransaction(invocation, tm, define, txInfo);
	}

	/**
	 * 执行事务
	 * 
	 * @param invocation
	 * @param tm
	 * @param define
	 * @param txInfo
	 * @return
	 * @throws Throwable
	 */
	public Object doTransaction(MethodInvocation invocation, DzTransactionManager tm, TransactionAttribute define,
			DzTransactionInfo txInfo) throws Throwable {
		saveTxinfo(txInfo);
		Object result;
		try {
			logger.debug("执行事务:{}", invocation);
			result = invocation.proceed();
		} catch (DzTransactionException e) {
			// 子事务出现了异常
			transactionFail(tm, txInfo);
			throw e;
		} catch (Throwable e) {
			onException(tm, define, txInfo, e);
			throw e;
		} finally {
			clearTransactionInfo(txInfo);
		}

		commitTransaction(tm, txInfo, true);
		return result;
	}

	/**
	 * 获取事务执行处理对象
	 * 
	 * @param define
	 * @param txInfo
	 * @return
	 */
	private DzTransactionProcess getTxProcessDelegate(TransactionAttribute define, DzTransactionInfo txInfo) {
		// 重试根事务时的时候不用异步
		if (txInfo != null && txInfo.getTransactionStatus().isRetry()
				&& txInfo.getTransactionStatus().isNewTransaction()) {
			return this;
		} else if (define.isAsync()) {
			return asyncDelegate;
		}

		return this;
	}

	/**
	 * 保存事务信息
	 * 
	 * @param txinfo
	 */
	private void saveTxinfo(DzTransactionInfo txinfo) {
		if (txinfo != null && txinfo.getTransactionStatus().hasTransaction()) {
			txinfo.setOldTransactionInfo(DzTransactionSyncManager.getTransactionInfo());
			DzTransactionSyncManager.setTransactionInfo(txinfo);
		}
	}

	/**
	 * 创建事务,更新上下文
	 * 
	 * @param tm
	 * @param define
	 * @return
	 */
	private DzTransactionInfo createTransaction(DzTransactionManager tm, TransactionDefinition define) {
		DzTransactionInfo txinfo = new DzTransactionInfo();
		TransactionStatus status = tm.begin(define);
		if (!status.hasTransaction()) {
			return null;
		}

		txinfo.setTransactionObject(status.getTransactionObject());
		txinfo.setTransactionStatus(status);
		return txinfo;
	}

	protected void clearTransactionInfo(DzTransactionInfo txInfo) {
		if (txInfo != null && txInfo.getTransactionStatus().hasTransaction()) {
			DzTransactionInfo ptxInfo = txInfo.getOldTransactionInfo();
			if (ptxInfo == null) {
				DzTransactionSyncManager.clear();
			} else {
				DzTransactionSyncManager.setTransactionInfo(ptxInfo);
			}
		}
	}

	/**
	 * 获取事务配置
	 * 
	 * @param invocation
	 * @param targetClass
	 * @return
	 */
	protected TransactionAttribute getTransactionAttr(MethodInvocation invocation, Class<?> targetClass) {
		Method method = invocation.getMethod();
		AutoRetry anno = method.getAnnotation(AutoRetry.class);
		DzTransactionAttribute attr = new DzTransactionAttribute();
		String transactionName = DzTransactionSupport.getDefaultIfNull(anno.name(),
				targetClass.getName() + "." + method.getName());
		attr.setName(transactionName);
		attr.setMaxTryTimes(anno.maxTryTimes());
		attr.setPropagation(anno.propagation());
		attr.setTimeout(Math.max(anno.timeout(), this.dzTransactionConfig.getTimeoutMinMs()));
		attr.setAsync(anno.async());
		Object[] args = invocation.getArguments();
		attr.setParams(args);

		attr.setCommitFailOn(anno.commitFailFor());
		attr.setRetryOn(anno.retryFor());
		Annotation[][] parameterAnn = method.getParameterAnnotations();
		if (parameterAnn != null) {
			for (int i = 0; i < parameterAnn.length; i++) {
				Annotation[] anns = parameterAnn[i];
				BussinessIdParam bussinessIdParam = filterBussinessIdParam(anns);
				if (bussinessIdParam != null) {
					attr.setBussinessId(getBussinessId(bussinessIdParam, args[i]));
					break;
				}
			}
		}

		return attr;
	}

	/**
	 * 事务异常处理
	 * 
	 * @param tm
	 * @param attr
	 * @param txInfo
	 * @param e
	 */
	protected void onException(DzTransactionManager tm, TransactionAttribute attr, DzTransactionInfo txInfo,
			Throwable e) {
		if (txInfo != null && attr != null && txInfo.getTransactionStatus().hasTransaction()) {
			TransactionStatus status = txInfo.getTransactionStatus();
			if (attr.commitFailOn(e)) {
				transactionFail(tm, txInfo);
			} else if (attr.retryOn(e)) {
				if (status.isRetry() && status.isNewTransaction()) {
					tm.continueRetry(status);
				}
			} else {
				commitTransaction(tm, txInfo, true);
			}
		}
	}

	/**
	 * 事务正常处理失败了
	 * 
	 * @param tm
	 * @param txInfo
	 */
	private void transactionFail(DzTransactionManager tm, DzTransactionInfo txInfo) {
		if (txInfo != null && txInfo.getTransactionStatus().hasTransaction()) {
			if (txInfo.getTransactionStatus().isNewTransaction()) {
				commitTransaction(tm, txInfo, false);
			} else {
				commitTransaction(tm, txInfo, true);
			}
		}
	}

	/**
	 * 提交事务
	 * 
	 * @param tm
	 * @param txInfo
	 * @param success
	 */
	protected void commitTransaction(DzTransactionManager tm, DzTransactionInfo txInfo, boolean success) {
		if (txInfo != null && txInfo.getTransactionStatus().hasTransaction()) {
			try {
				tm.commit(txInfo.getTransactionStatus(), success);
			} catch (Exception e) {
				logger.error("提交事务异常!", e);
			}
		}
	}

	/**
	 * 筛选BussinessIdParam注解
	 * 
	 * @param anns
	 * @return
	 */
	private BussinessIdParam filterBussinessIdParam(Annotation[] anns) {
		if (anns != null) {
			for (Annotation annotation : anns) {
				if (annotation instanceof BussinessIdParam) {
					return (BussinessIdParam) annotation;
				}
			}
		}

		return null;
	}

	/**
	 * 从参数中获取业务id
	 * 
	 * @param annotation
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getBussinessId(BussinessIdParam annotation, Object param) {
		if (param == null) {
			return null;
		}

		if (param.getClass().isPrimitive()) {
			return String.valueOf(param);
		} else if (param instanceof Number) {
			return param.toString();
		} else if (param instanceof Map) {
			return String.valueOf(((Map) param).get(annotation.value()));
		} else if (param instanceof String) {
			return (String) param;
		} else {
			try {
				Field field = param.getClass().getDeclaredField(annotation.value());
				field.setAccessible(true);
				return String.valueOf(field.get(param));
			} catch (Exception e) {
				logger.error("获取业务id失败!", e);
			}
		}

		return null;
	}

	public DzTransactionManager getTransactionManager() {
		return dzTransactionManager;
	}

	public void setTransactionManager(DzTransactionManager dzTransactionManager) {
		this.dzTransactionManager = dzTransactionManager;
	}
	
	public static void main(String[] args) {
		System.out.println(Math.max(5*60*1000, 10*1000));
	}

}
