package com.dazong.common.trans.support;

import com.dazong.common.trans.DzTransactionScheduler;

public class DzTransactionTask {
	
	public void task(){
		DzTransactionScheduler.get().scheduleTask();
	}
}