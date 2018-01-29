package com.dazong.common.trans.test.service.impl;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Service;

import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.common.trans.annotation.Propagation;
import com.dazong.common.trans.support.DzTransactionInfo;
import com.dazong.common.trans.support.DzTransactionSyncManager;
import com.dazong.common.trans.test.service.ITestService;
import com.dazong.common.trans.test.utils.CacheUtil;

@Service
public class TestServiceImpl implements ITestService {

	@Override
	@AutoRetry(propagation=Propagation.MANDATORY)
	public void test1(String name) {
		System.out.println(name);
		throw new RuntimeException("发生异常");
	}

	@Override
	@AutoRetry(propagation=Propagation.INTERRUPT_NOT_NEW)
	public void test2(String name) {
		
	}

	@Override
	@AutoRetry(propagation=Propagation.NESTED)
	public void test3(String name) {
		System.out.println(name);
		throw new RuntimeException("发生异常");
	}

	@Override
	@AutoRetry(async=true,propagation=Propagation.NESTED)
	public void test4(CountDownLatch cd) {
		System.out.println("test4");
		DzTransactionInfo info = DzTransactionSyncManager.getTransactionInfo();
		CacheUtil.put("test4-uid", info.getUid());
		cd.countDown();
		throw new RuntimeException("发生异常");
	}
}
