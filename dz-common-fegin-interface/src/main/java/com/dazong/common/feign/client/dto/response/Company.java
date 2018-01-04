package com.dazong.common.feign.client.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class Company {

	/** 公司编号*/
	private Long id;

	/** 公司名称*/
	private String name;

	/** 席位号*/
	@JSONField(name = "seats_no")
	@JsonProperty("seats_no")
	private String seatsNo;

	/** 组织机构代码证有效期*/
	@JSONField(name = "ex_code")
	@JsonProperty("ex_code")
	private String exCode;

	/** 组织机构代码*/
	private String code;

	/** 营业执照住所*/
	@JSONField(name = "license_address")
	@JsonProperty("license_address")
	private String licenseAddress;

	/** 法人代表*/
	@JSONField(name = "legal_person")
	@JsonProperty("legal_person")
	private String legalPerson;

	/** 法人代表手机*/
	private String mobile;
	
	public Company(){
		
	}
	public Company(String name, Long companyId) {
		this.name = name;
		this.id = companyId;
	}

}
