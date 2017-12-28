package com.dazong.common.trans.aspect;

import org.aopalliance.intercept.MethodInvocation;

import com.dazong.common.trans.DzTransactionManager;
import com.dazong.common.trans.support.DzTransactionInfo;

/**
 * 事务处理
 * @author hujunzhong
 *
 */
public interface DzTransactionProcess {
	/**
	 * 执行事务方法
	 * @param invocation
	 * @param tm
	 * @param define
	 * @param txInfo
	 * @return
	 * @throws Throwable
	 */
	Object doTransaction(MethodInvocation invocation, DzTransactionManager tm, TransactionAttribute define,
			DzTransactionInfo txInfo) throws Throwable;
}
