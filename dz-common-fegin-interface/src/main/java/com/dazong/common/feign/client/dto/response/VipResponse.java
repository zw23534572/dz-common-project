package com.dazong.common.feign.client.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lori.li
 */
@Data
public class VipResponse implements Serializable {

    /**
     * 是否VIP
     */
    @JSONField(name = "is_vip")
    private boolean isVip;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 过户费
     */
    @JSONField(name = "transfer_paid")
    private boolean transferPaid;

    /**
     * 是否能查看
     */
    private boolean can;

    /**
     * 不能查看的理由
     */
    private String msg;

    /**
     * 转权凭证地址
     */
    @JSONField(name = "file_path")
    private String filePath;
}
