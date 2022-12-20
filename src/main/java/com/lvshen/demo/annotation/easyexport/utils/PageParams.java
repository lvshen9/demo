package com.lvshen.demo.annotation.easyexport.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:13
 * @since JDK 1.8
 */
public class PageParams {
    private int pageNo = 1;
    private int pageSize = 10;
    private List<OrderBy> orderBys;

    public PageParams() {
    }

    public PageParams(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageParams(int pageNo, int pageSize, OrderBy orderBy) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy(orderBy);
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderBy> getOrderBys() {
        return this.orderBys;
    }

    public void setOrderBys(List<OrderBy> orderBys) {
        this.orderBys = orderBys;
    }

    public PageParams orderBy(OrderBy orderBy) {
        if (orderBy == null) {
            return this;
        } else {
            if (this.orderBys == null) {
                this.orderBys = new ArrayList(2);
            }

            this.orderBys.add(orderBy);
            return this;
        }
    }

    public PageParams orderBys(OrderBy... orderBys) {
        if (this.orderBys == null) {
            this.orderBys = new ArrayList(orderBys.length);
        }

        OrderBy[] var2 = orderBys;
        int var3 = orderBys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            OrderBy order = var2[var4];
            if (order != null) {
                this.orderBys.add(order);
            }
        }

        return this;
    }

    public int offset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public String toString() {
        return "PageParams [pageNo=" + this.pageNo + ", pageSize=" + this.pageSize + "]";
    }
}
