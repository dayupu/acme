package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.EventPush;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface EventPushRepo extends CrudRepository<EventPush, Serializable>, JpaSpecificationExecutor<EventPush> {


    @Query("from EventPush where eventPushed = false")
    public List<EventPush> queryEventWithNoPush();
}