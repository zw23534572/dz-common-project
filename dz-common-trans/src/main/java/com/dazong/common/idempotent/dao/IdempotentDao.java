package com.dazong.common.idempotent.dao;

import com.dazong.common.idempotent.domain.Idempotent;
import org.apache.ibatis.annotations.Param;


public interface IdempotentDao {

	void createTableIfNotExists();

    int add(Idempotent record);

	int updateStatus(@Param("idempotentId") String idempotentId,
                     @Param("newStatus") String newStatus,
                     @Param("oldStatus") String oldStatus,
                     @Param("returnInfo") String returnInfo,
                     @Param("returnByte") byte[] returnByte,
                     @Param("returnClass") String returnClass,
                     @Param("remark") String remark);

	Idempotent selectOne(@Param("idempotentId") String idempotentId);

}