package com.dazong.common.trans.support;

import com.dazong.common.trans.TransactionStatus;

/**
 * 事务上下文信息
 * @author hujunzhong
 *
 */
public class DzTransactionInfo {
	private DzTransactionObject transactionObject;

	private DzTransactionInfo oldTransactionInfo;
	
	private TransactionStatus transactionStatus;
	
	public String getUid(){
		return transactionObject.getUid();
	}

	public String getRid(){
		return transactionObject.getRid();
	}
	
	public DzTransactionObject getTransactionObject() {
		return transactionObject;
	}

	public void setTransactionObject(DzTransactionObject transactionObject) {
		this.transactionObject = transactionObject;
	}

	public DzTransactionInfo getOldTransactionInfo() {
		return oldTransactionInfo;
	}

	public void setOldTransactionInfo(DzTransactionInfo oldTransactionInfo) {
		this.oldTransactionInfo = oldTransactionInfo;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
