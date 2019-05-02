package com.dazong.common.trans.aspect;

import com.dazong.common.trans.TransactionDefinition;

/**
 * 事务属性
 * @author hujunzhong
 *
 */
public interface TransactionAttribute extends TransactionDefinition {

	/**
	 * 是否匹配： 业务正常处理完成返回失败的异常类型.不会重试
	 * @param ex
	 * @return
	 */
	boolean commitFailOn(Throwable ex);

	/**
	 * 是否匹配：需要重试的异常,默认为runtimeexception和error
	 * @param ex
	 * @return
	 */
	boolean retryOn(Throwable ex);

	/**
	 * 是否异步执行
	 * @return
	 */
	boolean isAsync();
}
