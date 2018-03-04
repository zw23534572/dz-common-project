package com.dazong.common.req;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * page 分页的入参
 *
 * @author ZhouWei
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageRequest {

    /**
     * 当前页码
     */
    private int pageNo;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 排序名称
     */
    private String orderBy;
    /**
     * 排序规则
     */
    private String asc;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 查询的关键字
     */
    private String key;
    /**
     * 创建的用户
     */
    private String createUser;

    public PageRequest() {
        this.pageNo = 0;
        this.pageSize = 10;
    }

    public PageRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageRequest(int pageNo, int pageSize, String orderBy, String asc) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        if (StringUtils.isNotBlank(orderBy)) {
            this.orderBy = SQLFilter.sqlInject(orderBy);
            this.asc = SQLFilter.sqlInject(asc);
        }
    }
}
