package com.dazong.common.trans.test;

import org.springframework.stereotype.Component;

import com.dazong.common.trans.listener.RetryFailEvent;
import com.dazong.common.trans.listener.IRetryFailCallback;

@Component
public class RetryEndListener1 implements IRetryFailCallback {

	@Override
	public void callBack(RetryFailEvent retryEndEvent) {
		System.out.println("------------------------RetryEndListener1:" + retryEndEvent);
	}

}
