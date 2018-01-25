package com.dazong.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntity {

    private static final long serialVersionUID = -8543685611997013386L;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 记录创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否有效 1：有效 0：无效
     */
    private Integer yn;

}
