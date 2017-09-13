package com.otms.support.controller.define;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-1.
 */
public class OrderEventResponse {

    private List<Result> results;

    public static class Result {
        private Long eventId;
        protected String orderNumber;
        protected String erpNumber;
        private Integer responseCode;

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getErpNumber() {
            return erpNumber;
        }

        public void setErpNumber(String erpNumber) {
            this.erpNumber = erpNumber;
        }
    }

    public List<Result> getResults() {
        if (results == null) {
            results = new ArrayList<>();
        }
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
