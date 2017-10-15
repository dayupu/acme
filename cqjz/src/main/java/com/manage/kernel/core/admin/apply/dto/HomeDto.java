package com.manage.kernel.core.admin.apply.dto;

import com.manage.kernel.basic.model.Newest;

import java.util.List;

/**
 * Created by bert on 2017/10/15.
 */
public class HomeDto {

    private List<Newest<NewsDto>> newses;

    private List<Newest<FlowDto>> flows;

    public List<Newest<NewsDto>> getNewses() {
        return newses;
    }

    public void setNewses(List<Newest<NewsDto>> newses) {
        this.newses = newses;
    }

    public List<Newest<FlowDto>> getFlows() {
        return flows;
    }

    public void setFlows(List<Newest<FlowDto>> flows) {
        this.flows = flows;
    }
}
