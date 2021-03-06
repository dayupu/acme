package com.manage.base.supplier.bootstrap;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PageQueryBS {

    private Integer pageSize;
    private Integer pageNumber;
    private String searchText;
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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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

    public PageRequest buildPageRequest(String... sortNames) {
        Sort sort = null;
        if (sortNames != null && sortNames.length > 0) {
            if ("asc".equalsIgnoreCase(this.sortOrder)) {
                sort = new Sort(Sort.Direction.ASC, sortNames);
            } else if ("desc".equalsIgnoreCase(this.sortOrder)) {
                sort = new Sort(Sort.Direction.DESC, sortNames);
            }
        }
        return new PageRequest(this.pageNumber - 1, this.pageSize, sort);
    }
}
