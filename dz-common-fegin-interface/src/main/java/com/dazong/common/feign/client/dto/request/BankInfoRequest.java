package com.dazong.common.feign.client.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class BankInfoRequest {
	
	@JSONField(name = "company_id")
	@JsonProperty("company_id")
	private Long companyId;

}
