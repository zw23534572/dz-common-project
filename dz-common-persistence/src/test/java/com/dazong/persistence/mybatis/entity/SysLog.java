package com.dazong.persistence.mybatis.entity;

import java.util.Date;

import com.dazong.persistence.annotation.TableField;
import com.dazong.persistence.annotation.TableId;
import com.dazong.persistence.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_log")
public class SysLog {

    @TableId
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户操作
     */
    @TableField("operation")
    private String operation;

    /**
     * 请求方法
     */
    @TableField("method")
    private String method;

    /**
     * 请求参数
     */
    @TableField("params")
    private String params;

    /**
     * 执行时长(毫秒)
     */
    @TableField("time")
    private Long time;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
