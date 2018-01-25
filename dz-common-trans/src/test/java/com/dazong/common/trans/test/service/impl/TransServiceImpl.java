package com.dazong.common.trans.test.service.impl;

import org.springframework.stereotype.Service;

import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.common.trans.support.DzTransactionInfo;
import com.dazong.common.trans.support.DzTransactionSyncManager;
import com.dazong.common.trans.test.TransContext;
import com.dazong.common.trans.test.exception.TransExcetion;
import com.dazong.common.trans.test.service.ITransService;

@Service
public class TransServiceImpl implements ITransService{
	
	
	private void setUid(String methodName){
		DzTransactionInfo info = DzTransactionSyncManager.getTransactionInfo();
		TransContext.getCurrentContext().set(methodName + "-uid", info.getUid());
	}
	
	@Override
	@AutoRetry
	public void doTrans(String name) {
		System.out.println(name);
		setUid("doTrans");
	}

	@Override
	@AutoRetry
	public void doTransException(String name) {
		System.out.println(name);
		setUid("doTransException");
		throw new TransExcetion("doTransException发生异常");
	}

	@Override
	@AutoRetry(commitFailFor=TransExcetion.class)
	public void doTransExceptionButRollback(String name) {
		System.out.println(name);
		setUid("doTransExceptionButRollback");
		throw new TransExcetion("doTransExceptionButRollback发生异常");
	}
}
