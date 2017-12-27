package com.dazong.common.trans.jdbc.mapper;

import java.util.List;

import com.dazong.common.trans.support.DzTransactionObject;

public interface DzTransactionObjectMapper {
	int deleteByPrimaryKey(String uid);

	int insert(DzTransactionObject record);

	int insertSelective(DzTransactionObject record);

	DzTransactionObject selectByPrimaryKey(String uid);

	int updateByPrimaryKeySelective(DzTransactionObject record);

	int updateByPrimaryKey(DzTransactionObject record);

	int deleteByRid(String rid);

	/**
	 * 根据root id删除所有子事务
	 * 
	 * @param rid
	 * @return
	 */
	int deleteAllChildTransaction(String rid);

	/**
	 * 根据root id查找正个事务调用树
	 * 
	 * @param rid
	 * @return
	 */
	List<DzTransactionObject> queryTransactionsByRid(String rid);

	/**
	 * 查询已超时的retryBatchSize条事务记录
	 * 
	 * @param retryBatchSize
	 * @return
	 */
	List<DzTransactionObject> queryTimeoutTransactions(int retryBatchSize);

	/**
	 * 按bussinessid删除
	 * 
	 * @param bussinessId
	 * @return
	 */
	int deleteByBussinessId(String bussinessId);

	/**
	 * 查询业务已完成的业务id队列
	 * 
	 * @return
	 */
	List<DzTransactionObject> queryBussinessIdQueue();
}