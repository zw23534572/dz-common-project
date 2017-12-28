package com.dazong.example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author huqichao
 */
@Data
public class Company {

	/** 公司编号*/
	private String id;

	/** 公司名称*/
	private String name;

	/** 公司ID*/
	@JSONField(name = "company_id")
	private String companyId;

	/** 席位号*/
	@JSONField(name = "seats_no")
	private String seatsNo;

	/** 组织机构代码证有效期*/
	@JSONField(name = "ex_code")
	private String exCode;

	/** 组织机构代码*/
	private String code;

	/** 营业执照住所*/
	@JSONField(name = "license_address")
	private String licenseAddress;

	/** 法人代表*/
	@JSONField(name = "legal_person")
	private String legalPerson;

	/** 法人代表手机*/
	private String mobile;
	
	public Company(){
		
	}
	public Company(String name, String companyId) {
		this.name = name;
		this.companyId = companyId;
	}
}
