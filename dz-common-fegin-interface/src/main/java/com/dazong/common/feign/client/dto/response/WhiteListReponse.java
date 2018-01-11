package com.dazong.common.feign.client.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 *  @author lori.li
 */
@Data
public class WhiteListReponse implements Serializable{

    /**
     * 公司ID
     */
    @JSONField(name = "company_id")
    private Long companyID;

    /**
     * 状态
     */
    private Integer status;
}
