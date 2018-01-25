package com.dazong.common.trans;

import java.util.concurrent.ThreadFactory;

/**
 * 简单线程工厂，给创建的线程加上线程名
 * @author luobinwen
 *
 */
public class SimpleThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread();
		thread.setName("async_dz_Transaction_thread");
		return thread;
	}

}
