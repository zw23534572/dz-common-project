package com.dazong.common.trans;

import java.util.List;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 事务数据持久化接口
 * @author hujunzhong
 *
 */
public interface DzTransactionDurableManger {
	/**
	 * 保存事务
	 * @param transaction
	 */
	void saveTransaction(DzTransactionObject transaction);
	
	/**
	 * 更新事务
	 * @param transaction
	 */
	void updateTransaction(DzTransactionObject transaction);
	
	/**
	 * 提交事务
	 * @param uid
	 * @param isSuccess
	 */
	void commitTransaction(String uid, boolean isSuccess);

	/**
	 * 准备重试事务,把子事务都删除掉,只留根事务
	 * @param to
	 */
	void prepareRetryTransaction(DzTransactionObject to);

	/**
	 * 查询超时的事务
	 * @param retryBatchSize
	 * @return
	 */
	List<DzTransactionObject> queryTimeoutTransactions(int retryBatchSize);

	/**
	 * 按事务id删除
	 * @param bussinessId
	 */
	void deleteByBussinessId(String bussinessId);

	/**
	 * 重试失败
	 * @param updateTo
	 */
	void retryFail(DzTransactionObject updateTo);

	/**
	 * 查询业务已完成的业务id队列
	 * @return
	 */
	List<DzTransactionObject> queryBussinessIdQueue();
}
