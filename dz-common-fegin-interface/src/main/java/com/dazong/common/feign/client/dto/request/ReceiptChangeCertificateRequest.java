package com.dazong.common.feign.client.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lori.li
 */
@Data
public class ReceiptChangeCertificateRequest implements Serializable {
    /**
     * 凭证流水号
     */
    private String serialNumber;
    /**
     * 关联的业务单号
     */
    private String refNo;
    /**
     * 业务类型
     */
    private Integer busType;
    /**
     * 业务类型名称
     */
    private String busName;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 货主名称
     */
    private String ownerName;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 凭证详情
     */
    private List<ReceiptChangeCertificateDetail> list;
}
