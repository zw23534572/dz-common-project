package com.dazong.common.feign.client.dto.request;


import lombok.Data;

/**
 * @author luobinwen
 */
@Data
public class CompanyRequest {

    private Long companyId;

    /**
     * 转权单号
     */
    private String fetchNos;
}
