package com.dazong.common.trans.jdbc.mapper;

import java.util.List;
import java.util.Map;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 数据库操作mapper
 * @author hujunzhong
 *
 */
public interface DzTransactionObjectMapper {
	
	/**
	 * <B>方法名称：如果不存在则创建表</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	void createTableIfNotExists();
	
	/**
	 * 通过事务id删除一条记录
	 * @param uid
	 * @return
	 */
	int deleteByPrimaryKey(String uid);

	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
	int insert(DzTransactionObject record);

	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
	int insertSelective(DzTransactionObject record);
	
	/**
	 * 通过事务id查询对象
	 * @param uid
	 * @return
	 */
	DzTransactionObject selectByPrimaryKey(String uid);
	
	/**
	 * 更新一条记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(DzTransactionObject record);
	
	/**
	 * 更新一条记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(DzTransactionObject record);

	/**
	 * 通过跟事务id删除记录
	 * @param rid
	 * @return
	 */
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
	List<DzTransactionObject> queryTimeoutTransactions(Map<String,Object> params);

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