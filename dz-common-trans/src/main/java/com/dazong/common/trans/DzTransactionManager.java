package com.dazong.common.trans;

import java.lang.reflect.Method;
import java.util.List;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 事务管理器接口.事务主要逻辑实现
 * @author hujunzhong
 *
 */
public interface DzTransactionManager {
	
	/**
	 * 开始事务
	 * @param def
	 * @return
	 */
	TransactionStatus begin(TransactionDefinition def);
	
	/**
	 * 事务完成,提交事务
	 * @param status
	 * @param rootTransactionSuccess
	 */
	void commit(TransactionStatus status, boolean rootTransactionSuccess);

	/**
	 * 继续重试
	 * @param status
	 */
	void continueRetry(TransactionStatus status);

	/**
	 * 注册事务
	 * @param name
	 * @param type
	 * @param method
	 * @param bean 
	 */
	void registerTransaction(String name, Class<?> type, Method method, Object bean);
	
	/**
	 * 查询超时的事务
	 * @param currentDateMsec 当前时间的毫秒数
	 * @return
	 */
	List<DzTransactionObject> queryTimeoutTransactions();
	
	/**
	 * 重试事务
	 * @param to
	 */
	void retryTransaction(DzTransactionObject to);

	/**
	 * 业务处理完成,把业务id加入调度队列
	 * @param bussinessId
	 */
	void bussinessFinished(String bussinessId);

	/**
	 * 调度业务完成任务,删除与业务id关连的事务
	 */
	void scheduleBussinessFinished();
}
