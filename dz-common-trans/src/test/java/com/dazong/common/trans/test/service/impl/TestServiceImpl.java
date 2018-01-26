package com.dazong.common.trans.test.service.impl;

import org.springframework.stereotype.Service;

import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.common.trans.annotation.Propagation;
import com.dazong.common.trans.test.service.ITestService;

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
}
