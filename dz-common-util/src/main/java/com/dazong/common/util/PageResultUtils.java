package com.dazong.common.util;

import com.dazong.common.resp.PageResult;
import com.github.pagehelper.Page;

/**
 * <B>说明：将pagehelper 的page类 转换为 dzcommon PageResult</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-04 17:11
 */
public class PageResultUtils {

    public static PageResult convert(Page page) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        /**
         * 总页数
         */
        pageResult.setTotalPage(page.getPages());
        /**
         * 总记录数
         */
        pageResult.setTotalItem((int) page.getTotal());
        /**
         * 实体集
         */
        pageResult.setData(page.getResult());
        return pageResult;
    }
}
