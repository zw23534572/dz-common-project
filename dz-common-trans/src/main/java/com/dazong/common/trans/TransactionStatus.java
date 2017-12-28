package com.dazong.common.trans;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 当前事务的运行状态
 * @author hujunzhong
 *
 */
public interface TransactionStatus {
	
	String getTransactionId();
	
	boolean isNewTransaction();
	
	boolean isRetry();
	
	boolean hasTransaction();
	
	DzTransactionObject getTransactionObject();
	
	boolean isRetryEnd();
}
