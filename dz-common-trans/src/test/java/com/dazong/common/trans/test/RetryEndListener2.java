package com.dazong.common.trans.test;

import com.dazong.common.trans.listener.RetryFailEvent;
import com.dazong.common.trans.listener.IRetryFailCallback;

public class RetryEndListener2 implements IRetryFailCallback {

	@Override
	public void callBack(RetryFailEvent retryEndEvent) {
		System.out.println("----------------------RetryEndListener2：" + retryEndEvent);
	}
}
