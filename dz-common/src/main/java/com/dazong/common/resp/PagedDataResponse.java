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
    private int pageSize;
    
    /**
     * 当前页。第一页是1
     */
    private int pageNo;
    
    /**
     * 总记录数
     */
    private int totalItem;
    
    /**
     * 总页数
     */
    private int totalPage;

    private List<T> data;

    public PagedDataResponse() {}

    public PagedDataResponse(int pageNo, int pageSize, int totalItem) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        if (pageSize == 0) {
            totalPage = 1;
        } else {
            totalPage = totalItem / pageSize + (totalItem % pageSize > 0 ? 1 : 0);
        }
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

    


}
