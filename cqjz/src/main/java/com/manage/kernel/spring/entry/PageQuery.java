package com.manage.kernel.spring.entry;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageQuery {
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

    public PageRequest buildPageRequest(boolean sortable) {
        if (sortable && sortField != null) {
            return buildPageRequest(sortField);
        } else {
            return new PageRequest(this.pageNumber - 1, this.pageSize);
        }
    }

    public PageRequest buildPageRequest(Sort sort) {
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }

    public PageRequest buildPageRequest(String sortField) {
        Sort sort = null;
        if (!StringUtils.isEmpty(sortField)) {
            if ("asc".equalsIgnoreCase(this.sortDirection)) {
                sort = new Sort(Sort.Direction.ASC, sortField);
            } else if ("desc".equalsIgnoreCase(this.sortDirection)) {
                sort = new Sort(Sort.Direction.DESC, sortField);
            }
        }
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }
}
