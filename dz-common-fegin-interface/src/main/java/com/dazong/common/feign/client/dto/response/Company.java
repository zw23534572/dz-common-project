package com.dazong.common.feign.client.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *  @author yanghui
 */
@Data
public class Company {

	/** ��˾���*/
	private Long id;

	/** ��˾����*/
	private String name;

	/** ϯλ��*/
	@JSONField(name = "seats_no")
	@JsonProperty("seats_no")
	private String seatsNo;

	/** ��֯��������֤��Ч��*/
	@JSONField(name = "ex_code")
	@JsonProperty("ex_code")
	private String exCode;

	/** ��֯��������*/
	private String code;

	/** Ӫҵִ��ס��*/
	@JSONField(name = "license_address")
	@JsonProperty("license_address")
	private String licenseAddress;

	/** ���˴���*/
	@JSONField(name = "legal_person")
	@JsonProperty("legal_person")
	private String legalPerson;

	/** ���˴����ֻ�*/
	private String mobile;
	
	public Company(){
		
	}
	public Company(String name, Long companyId) {
		this.name = name;
		this.id = companyId;
	}
}
