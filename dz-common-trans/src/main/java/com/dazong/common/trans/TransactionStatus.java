package com.dazong.common.trans;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 当前事务的运行状态
 * @author hujunzhong
 *
 */
public interface TransactionStatus {
	
	/**
	 * 事务id
	 * @return
	 */
	String getTransactionId();
	
	/**
	 * 是否新事务
	 * @return
	 */
	boolean isNewTransaction();
	
	/**
	 * 是否重试
	 * @return
	 */
	boolean isRetry();
	
	/**
	 * 是否存在事务
	 * @return
	 */
	boolean hasTransaction();
	
	/**
	 * 获得事务对象
	 * @return
	 */
	DzTransactionObject getTransactionObject();
	
	/**
	 * 是否重试完成
	 * @return
	 */
	boolean isRetryEnd();
}
