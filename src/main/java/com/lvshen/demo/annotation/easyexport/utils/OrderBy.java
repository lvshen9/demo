package com.lvshen.demo.annotation.easyexport.utils;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:13
 * @since JDK 1.8
 */
public class OrderBy {
    private String field;
    private String sortType;

    public OrderBy() {
    }

    public OrderBy(String field) {
        this(field, OrderBy.OrderType.ASC);
    }

    public OrderBy(String field, String sortType) {
        this.field = field;
        this.sortType = sortType;
    }

    public OrderBy(String field, OrderBy.OrderType sortType) {
        this.field = field;
        this.sortType = sortType.name();
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSortType() {
        return this.sortType == null ? OrderBy.OrderType.ASC.name() : this.sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public static enum OrderType {
        DESC,
        ASC;

        private OrderType() {
        }
    }
}
