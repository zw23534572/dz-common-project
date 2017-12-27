package com.dazong.common.trans.support;

import com.dazong.common.trans.TransactionStatus;

/**
 * 默认事务状态
 * @author hujunzhong
 *
 */
public class DefaultTransactionStatus implements TransactionStatus {
	
	private DzTransactionObject transactionObject;
	
	private boolean transaction;
	
	private boolean retry;
	
	/**
	 * 重试结束,已达到最大重试次数
	 */
	private boolean retryEnd;

	private boolean newTransaction;

	public String getTransactionId() {
		if(transactionObject != null){
			return transactionObject.getUid();
		}
		
		return null;
	}

	public boolean isNewTransaction() {
		return newTransaction;
	}

	public boolean isRetry() {
		return retry;
	}

	public DzTransactionObject getTransactionObject() {
		return transactionObject;
	}

	public void setTransactionObject(DzTransactionObject transactionObject) {
		this.transactionObject = transactionObject;
	}

	public boolean hasTransaction() {
		return transaction;
	}

	public void setTransaction(boolean transaction) {
		this.transaction = transaction;
	}

	public void setNewTransaction(boolean newTransaction) {
		this.newTransaction = newTransaction;
	}

	public boolean isRetryEnd() {
		return retryEnd;
	}

	public void setRetryEnd(boolean retryEnd) {
		this.retryEnd = retryEnd;
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}
