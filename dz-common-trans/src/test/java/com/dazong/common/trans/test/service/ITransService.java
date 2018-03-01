package com.dazong.common.trans.test.service;

import java.util.concurrent.CountDownLatch;

public interface ITransService {
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，不抛异常</B><BR>
	 * @param name
	 */
	void doTrans(String name);
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，抛出异常</B><BR>
	 * @param name
	 */
	void doTransException(String name);
	
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，抛出异常,但是指定异常提交事务</B><BR>
	 * @param name
	 */
	void doTransExceptionButRollback(String name);
	
	/**
	 * <B>方法名称：测试事务的传播特性</B><BR>
	 * <B>概要说明：MANDATORY,子事务抛出异常
	 * </B><BR>
	 * @param name
	 */
	void doTransPropagationForMandatory1(String name);
	/**
	 * <B>方法名称：测试事务的传播特性</B><BR>
	 * <B>概要说明：MANDATORY 不存在事务下调用</B><BR>
	 * @param name
	 */
	void doTransPropagationForMandatory2(String name);
	/**
	 * <B>方法名称：测试事务的传播特性</B><BR>
	 * <B>概要说明：INTERRUPT_NOT_NEW 在有事务下运行</B><BR>
	 * @param name
	 */
	void doTransPropagationForInterruptNotNew(String name);
	/**
	 * <B>方法名称：测试事务的传播特性</B><BR>
	 * <B>概要说明：NESTED 在有事务下运行</B><BR>
	 * @param name
	 */
	void doTransPropagationForNested(String name);
	
	void doTransAsync(CountDownLatch cd);
	
	void doTransAsyncException(CountDownLatch cd);
	
	void doTransBussinessId(Long id);
	
	void doTransRetry(String name);
	
	void doTransRetryForTimes(String name);
	
}
