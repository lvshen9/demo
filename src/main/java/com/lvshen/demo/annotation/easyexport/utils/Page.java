package com.lvshen.demo.annotation.easyexport.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:16
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page<T> extends PageParams {
    private long total;
    private int pages;
    private List<T> data;

    public Page() {
    }
    public Page(int pageNo, int pageSize, long total, List<T> data) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.total = total;
        this.data = data;
        if (this.total > 0L) {
            this.pages = (int)(this.total / (long)this.getPageSize() + (long)(this.total % (long)this.getPageSize() == 0L ? 0 : 1));
        }

    }

    public Page(PageParams pageParams, long total, List<T> data) {
        this.setPageNo(pageParams.getPageNo());
        this.setPageSize(pageParams.getPageSize());
        this.setOrderBys(pageParams.getOrderBys());
        this.total = total;
        this.data = data;
        if (this.total > 0L) {
            this.pages = (int)(this.total / (long)this.getPageSize() + (long)(this.total % (long)this.getPageSize() == 0L ? 0 : 1));
        }

    }

    public static <T> Page<T> blankPage(int pageNo, int pageSize) {
        Page<T> page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.total = 0L;
        page.data = new ArrayList(0);
        page.setPages(0);
        return page;
    }

    public long getTotal() {
        return this.total;
    }
    public void setTotal(long total) {
        this.total = total;
        if (this.total > 0L) {
            this.pages = (int)(this.total / (long)this.getPageSize() + (long)(this.total % (long)this.getPageSize() == 0L ? 0 : 1));
        }

    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return this.data == null ? (this.data = new ArrayList()) : this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    public String toString() {
        return "Page [total=" + this.total + ", pages=" + this.pages + ", pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + "]";
    }
}
