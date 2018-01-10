package com.dazong.common.trans;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread();
		thread.setName("async_dz_Transaction_thread");
		return thread;
	}

}
