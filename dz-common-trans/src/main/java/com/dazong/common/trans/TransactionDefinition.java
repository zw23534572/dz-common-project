package com.dazong.common.trans;

import com.dazong.common.trans.annotation.Propagation;

/**
 * 事务定义
 * @author hujunzhong
 *
 */
public interface TransactionDefinition {

	/**
	 * 事务名,不能重复.可为空,默认为当前的方法全名
	 * @return
	 */
	String getName();

	/**
	 * 事物嵌套时处理方法,默为Propagation.REQUIRED.详细见Propagation
	 * @return
	 */
	Propagation getPropagation();
	
	/**
	 * 重试超时间时间,超过这个时间没有commit重试,单位ms.默认5分钟
	 * @return
	 */
	long getTimeout();
	
	/**
	 * 最大重试次数,为0时不限
	 * @return
	 */
	int getMaxTryTimes();
	
	/**
	 * 
	 * @return
	 */
	Object[] getParams();
	
	/**
	 * 
	 * @return
	 */
	String getBussinessId();
}
