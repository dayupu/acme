package com.manage.kernel.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.LocalDateDeserializer;
import com.manage.base.database.serialize.LocalDateSerializer;
import org.joda.time.LocalDate;

/**
 * Created by bert on 17-10-13.
 */
public class WatchDto {

    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate watchTime;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate watchTimeEnd;

    private String leader;

    private String captain;

    private String worker;

    private String phone;

    private String queryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(LocalDate watchTime) {
        this.watchTime = watchTime;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getWatchTimeEnd() {
        return watchTimeEnd;
    }

    public void setWatchTimeEnd(LocalDate watchTimeEnd) {
        this.watchTimeEnd = watchTimeEnd;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}
