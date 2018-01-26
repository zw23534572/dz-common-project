package com.dazong.common.trans.test.service.impl;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.common.trans.annotation.BussinessIdParam;
import com.dazong.common.trans.support.DzTransactionInfo;
import com.dazong.common.trans.support.DzTransactionSyncManager;
import com.dazong.common.trans.test.TransContext;
import com.dazong.common.trans.test.exception.TransExcetion;
import com.dazong.common.trans.test.service.ITestService;
import com.dazong.common.trans.test.service.ITransService;
import com.dazong.common.trans.test.utils.CacheUtil;

@Service
public class TransServiceImpl implements ITransService{
	
	@Autowired
	private ITestService testService;
	
	
	private void setUid(String methodName){
		DzTransactionInfo info = DzTransactionSyncManager.getTransactionInfo();
		TransContext.getCurrentContext().set(methodName + "-uid", info.getUid());
	}
	
	private void setRid(String methodName){
		DzTransactionInfo info = DzTransactionSyncManager.getTransactionInfo();
		TransContext.getCurrentContext().set(methodName + "-rid", info.getRid());
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

	@Override
	@AutoRetry
	public void doTransPropagationForMandatory1(String name) {
		System.out.println(name);
		setRid("doTransPropagationForMandatory1");
		this.testService.test1(name);
	}

	@Override
	public void doTransPropagationForMandatory2(String name) {
		System.out.println(name);
		this.testService.test1(name);
	}

	@Override
	@AutoRetry
	public void doTransPropagationForInterruptNotNew(String name) {
		System.out.println(name);
		this.testService.test2(name);
	}

	@Override
	@AutoRetry
	public void doTransPropagationForNested(String name) {
		System.out.println(name);
		setRid("doTransPropagationForNested");
		this.testService.test3(name);
	}

	@Override
	@AutoRetry(async=true)
	public void doTransAsync(CountDownLatch cd) {
		CacheUtil.put("doTransAsync-thread-id", Thread.currentThread().getId());
		System.out.println("doTransAsync");
		cd.countDown();
	}

	@Override
	@AutoRetry
	public void doTransAsyncException(CountDownLatch cd) {
		System.out.println("doTransAsyncException");
		this.testService.test4(cd);
	}

	@Override
	@AutoRetry
	public void doTransBussinessId(@BussinessIdParam("id") Long id) {
		setUid("doTransBussinessId");
		throw new TransExcetion("doTransBussinessId发生异常");
	}
}
