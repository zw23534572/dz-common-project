package com.dazong.common.feign.client.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lori.li
 */
@Data
public class ProtocolResponse implements Serializable {

    /**
     * 协议编号
     */
    private String protocolSn;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 协议地址
     */
    private String pdfPath;
}
