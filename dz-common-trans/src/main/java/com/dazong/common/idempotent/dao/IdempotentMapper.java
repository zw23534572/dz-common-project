package com.dazong.common.idempotent.dao;

import com.dazong.common.idempotent.domain.Idempotent;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhiyuan.wang
 */
public interface IdempotentMapper {

	/**
	 * 如果不存在表，则创建表md
	 */
	void createTableIfNotExists();

	/**
	 * 添加幂等记录
	 * @param record
	 * @return
	 */
    int add(Idempotent record);

	/**
	 * 更新幂等记录
	 * @param idempotentId
	 * @param newStatus
	 * @param oldStatus
	 * @param returnInfo
	 * @param returnByte
	 * @param returnClass
	 * @param remark
	 * @return
	 */
	int updateStatus(@Param("idempotentId") String idempotentId,
                     @Param("newStatus") String newStatus,
                     @Param("oldStatus") String oldStatus,
                     @Param("returnInfo") String returnInfo,
                     @Param("returnByte") byte[] returnByte,
                     @Param("returnClass") String returnClass,
                     @Param("remark") String remark);

	/**
	 * 查询幂等记录
	 * @param idempotentId
	 * @return
	 */
	Idempotent selectOne(@Param("idempotentId") String idempotentId);

}