package com.dazong.common.feign.client.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class BankInfoRequest {
	
	@JSONField(name = "company_id")
	private Long companyId;

}
