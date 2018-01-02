package com.dazong.common.resp;

import java.util.List;

/**
 * 
 * @author luobinwen
 *
 * @param <T>
 */
public class PagedDataResponse<T> extends CommonResponse {

    private static final long serialVersionUID = 1L;
    
    /**
     * 每页大小
     */
    private int page_size;
    
    /**
     * 当前页。第一页是1
     */
    private int page_no;
    
    /**
     * 总记录数
     */
    private int total_item;
    
    /**
     * 总页数
     */
    private int total_page;

    private List<T> data;

    public PagedDataResponse() {}

    public PagedDataResponse(int page_no, int page_size, int total_item) {
        this.page_no = page_no;
        this.page_size = page_size;
        this.total_item = total_item;
        if (page_size == 0) {
            total_page = 1;
        } else {
            total_page = total_item / page_size + (total_item % page_size > 0 ? 1 : 0);
        }
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
