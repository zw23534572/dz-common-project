package com.dazong.persistence.mybatis.entity;

import com.dazong.persistence.annotation.TableField;
import com.dazong.persistence.annotation.TableId;
import com.dazong.persistence.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <B>说明：用户test表</B><BR>
 *
 * @author 
 * @version 1.0.0.
 * @date 2018-02-27 14:52
 */
@TableName("user")
@Data
public class User {

    /**
     * 用户主键
     */
    @TableId("id1")
    private Long id;
    /**
     * 用户姓名
     */
    @TableField("name")
    private String name;
    /**
     * 用户姓名_扩展
     */
    @TableField("ext_name")
    private String extName;
    /**
     * 状态：0:创建中 1：创建成功， 2:创建失败
     */
    @TableField("status")
    private Integer status;
    /**
     * 用户性别
     */
    @TableField("age")
    private Integer age;
}
