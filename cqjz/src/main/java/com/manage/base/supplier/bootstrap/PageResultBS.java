package com.manage.base.supplier.bootstrap;

import java.util.List;

public class PageResultBS<T> {

    private Long total;
    private List<T> rows;

    public PageResultBS(){

    }

    public PageResultBS(Long total, List<T> rows){
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
