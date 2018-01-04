package com.dazong.common.feign.client.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class BankInfoResponse {
	
	private Integer id;
	
	@JSONField(name = "user_id")
	@JsonProperty("user_id")
	private Integer userId;
	
	@JSONField(name = "company_id")
	@JsonProperty("company_id")
	private Integer companyId;
	
	@JSONField(name = "config_id")
	@JsonProperty("config_id")
	private Integer configId;
	
	private String name;
	
	@JSONField(name = "short_tag")
	@JsonProperty("short_tag")
	private String shortTag;
	
	private Integer status;
	
	@JSONField(name = "create_time")
	@JsonProperty("create_time")
	private String createTime;
	
	@JSONField(name = "update_time")
	@JsonProperty("update_time")
	private String updateTime;
	
	private BankInfo info;
	
	
	@Data
	public static class BankInfo {
		private Integer id;
		
		@JSONField(name = "bank_id")
		@JsonProperty("bank_id")
		private Integer bankId;
		
		@JSONField(name = "user_code")
		@JsonProperty("user_code")
		private String userCode;
		
		@JSONField(name = "code_type")
		@JsonProperty("code_type")
		private String codeType;
		
		@JSONField(name = "id_card")
		@JsonProperty("id_card")
		private String idCard;
		
		@JSONField(name = "deal_type")
		@JsonProperty("deal_type")
		private String dealType;
		
		@JSONField(name = "deal_code")
		@JsonProperty("deal_code")
		private String dealCode;
		
		@JSONField(name = "bank_card")
		@JsonProperty("bank_card")
		private String bankCard;
		
		@JSONField(name = "real_name")
		@JsonProperty("real_name")
		private String realName;
		private String mobile;
		
		@JSONField(name = "sign_time")
		@JsonProperty("sign_time")
		private String signTime;
		
		@JSONField(name = "create_time")
		@JsonProperty("create_time")
		private String createTime;
		
		@JSONField(name = "update_time")
		@JsonProperty("update_time")
		private String updateTime;
		
		@JSONField(name = "code_type_name")
		@JsonProperty("code_type_name")
		private String codeTypeName;
	}

}
