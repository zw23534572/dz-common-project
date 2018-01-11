package com.dazong.common.feign.client.dto.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author yanghui
 */
@Data
public class Company implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5981047830020442410L;

    /**
     * 公司编号
     */
    private Long id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 席位号
     */
    @JSONField(name = "seats_no")
    @JsonProperty("seats_no")
    private String seatsNo;

    /**
     * 组织机构代码证有效期
     */
    @JSONField(name = "ex_code")
    @JsonProperty("ex_code")
    private String exCode;

    /**
     * 组织机构代码
     */
    private String code;

    /**
     * 营业执照住所
     */
    @JSONField(name = "license_address")
    @JsonProperty("license_address")
    private String licenseAddress;

    /**
     * 法人代表
     */
    @JSONField(name = "legal_person")
    @JsonProperty("legal_person")
    private String legalPerson;

    /**
     * 法人代表手机
     */
    private String mobile;

    /**
     * 联系人名
     */
    @JSONField(name = "operation_name")
    private String operationName;

    /**
     * 联系人手机
     */
    @JSONField(name = "operation_mobile")
    private String operationMobile;

    /**
     * 信用代码
     */
    @JSONField(name = "social_credit_code")
    private String socialCreditCode;

    /**
     * 营业执照编码
     */
    @JSONField(name = "license_num")
    private String licenseNum;

    /**
     * 税务登记证号
     */
    @JSONField(name = "tax_number")
    private String taxNumber;

    /**
     * 发票地址
     */
    @JSONField(name = "vat_address")
    private String vatAddress;

    /**
     * 收票电话
     */
    @JSONField(name = "vat_mobile")
    private String vatMobile;

    /**
     * 收票地址
     */
    @JSONField(name = "vat_collector_address")
    private String vatCollectorAddress;

    /**
     * 银行名称
     */
    @JSONField(name = "bank_name")
    private String bankName;

    /**
     * 银行账号
     */
    @JSONField(name = "bank_card")
    private String bankCard;

    public Company() {

    }

    public Company(String name, Long companyId) {
        this.name = name;
        this.id = companyId;
    }

}
