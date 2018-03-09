package com.dazong.common.req;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;
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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
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
        this.pageNo = 1;
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

    public void setPageNo(Integer pageNo) {
        if (pageNo == null){
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }
}
