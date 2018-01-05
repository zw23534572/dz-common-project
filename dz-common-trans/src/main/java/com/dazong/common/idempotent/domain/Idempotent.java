package com.dazong.common.idempotent.domain;

import java.util.Date;

/**
 * @author zhiyuan.wang
 */
public class Idempotent {
    /**
     * 主键
     */
    private Long id;

    /**
     * 防重id
     */
    private String idempotentId;

    /**
     * 业务流水号
     */
    private String businessId;

    /**
     * 业务步骤id
     */
    private String stepId;

    /**
     * 返回类型
     */
    private String returnClass;

    /**
     * 返回信息
     */
    private String returnInfo;

    /**
     * 返回信息的二进制存储
     */
    private byte[] returnByte;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态:PROCESSING|SUCCESS|FAIL
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public byte[] getReturnByte() {
        return returnByte;
    }

    public void setReturnByte(byte[] returnByte) {
        this.returnByte = returnByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId == null ? null : idempotentId.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId == null ? null : stepId.trim();
    }

    public String getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(String returnClass) {
        this.returnClass = returnClass == null ? null : returnClass.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo == null ? null : returnInfo.trim();
    }
}