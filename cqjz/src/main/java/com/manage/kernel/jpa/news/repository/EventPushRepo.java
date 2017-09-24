package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.EventPush;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface EventPushRepo extends CrudRepository<EventPush, Serializable>, JpaSpecificationExecutor<EventPush> {

}