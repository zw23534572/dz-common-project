package com.dazong.common.trans.test.service;

import java.util.concurrent.CountDownLatch;

public interface ITestService {
	
	void test1(String name);
	
	void test2(String name);
	
	void test3(String name);
	
	void test4(CountDownLatch cd);

}
