package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lori.li
 */
@Data
public class ReceiptChangeCertificateDetail implements Serializable {
    /**
     * 商品序列号
     */
    private String sku;

    /**
     * 原仓单号
     */
    private String sourceReceiptNo;

    /**
     * 目标仓单号
     */
    private String targetReceiptNo;

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 数量
     */
    private Integer changeNum;
    /**
     * 重量
     */
    private BigDecimal changeWeight;
}
