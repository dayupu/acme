package com.otms.support.controller.define;

import java.util.ArrayList;
import java.util.List;

public class JobSheetResponse {

    private List<Result> results;

    public static class Result {

        private Long eventId;
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
