package com.manage.base.supplier.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageQuery {

    private static final String ORDER_DESC = "desc";
    private static final String ORDER_ASC = "asc";

    private Integer pageSize;
    private Integer pageNumber;
    private String sortField;
    private String sortDirection;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public PageRequest sortPage() {
        if (sortField != null) {
            return buildPageRequest(sortField);
        } else {
            return new PageRequest(this.pageNumber - 1, this.pageSize);
        }
    }

    public PageRequest sortPageDefault(String defaultColumn) {
        if (sortField == null) {
            setSortField(defaultColumn);
        }
        return buildPageRequest(sortField);
    }

    private PageRequest buildPageRequest(String sortField) {
        Sort sort = null;
        if (ORDER_ASC.equalsIgnoreCase(this.sortDirection)) {
            sort = new Sort(Sort.Direction.ASC, sortField);
        } else if (ORDER_DESC.equalsIgnoreCase(this.sortDirection)) {
            sort = new Sort(Sort.Direction.DESC, sortField);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortField);
        }
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }

    public int offset() {
        return (pageNumber - 1) * pageSize;
    }

    public int limit() {
        return (pageNumber - 1) * pageSize + pageSize;
    }

}
