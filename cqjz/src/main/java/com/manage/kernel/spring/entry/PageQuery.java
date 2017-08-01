package com.manage.kernel.spring.entry;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageQuery {
    private Integer pageSize;
    private Integer pageNumber;
    private String sortName;
    private String sortOrder;

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

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }


    public PageRequest buildPageRequest(boolean sortable) {
        if (sortable) {
            return buildPageRequest(sortName);
        } else {
            return new PageRequest(this.pageNumber - 1, this.pageSize);
        }
    }

    public PageRequest buildPageRequest(Sort sort) {
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }

    public PageRequest buildPageRequest(String sortName) {
        Sort sort = null;
        if (!StringUtils.isEmpty(sortName)) {
            if ("asc".equalsIgnoreCase(this.sortOrder)) {
                sort = new Sort(Sort.Direction.ASC, sortName);
            } else if ("desc".equalsIgnoreCase(this.sortOrder)) {
                sort = new Sort(Sort.Direction.DESC, sortName);
            }
        }
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }
}
