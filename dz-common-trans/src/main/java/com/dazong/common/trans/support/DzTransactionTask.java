package com.dazong.common.trans.support;

import com.dazong.common.trans.DzTransactionScheduler;
/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 贸易研发部：yanghui
 * @since 2018年2月1日
 */
public class DzTransactionTask {
	
	public void task(){
		DzTransactionScheduler.get().scheduleTask();
	}
}