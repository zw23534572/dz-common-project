package com.dazong.example.service.trans.impl;

import java.util.Random;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import com.dazong.common.trans.annotation.AutoRetry;
import com.dazong.example.service.trans.ITransService;

@Service
@Log4j2
public class TransServiceImpl implements ITransService{

	@Override
	@AutoRetry
	public void test(String name, int type) {
		log.info("TransServiceImpl.test入参：{}，{}",name,type);
	}

	@Override
	@AutoRetry(maxTryTimes=1,timeout=10 * 1000L)
	public void testForException(String name, int type) {
		log.info("TransServiceImpl.testForException入参：{}，{}",name,type);
		throw new RuntimeException("异常测试");
	}
}
