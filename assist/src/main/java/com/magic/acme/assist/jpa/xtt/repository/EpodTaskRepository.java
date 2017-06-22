package com.magic.acme.assist.jpa.xtt.repository;

import com.magic.acme.assist.jpa.xtt.entity.EpodTask;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bert on 17-6-22.
 */
public interface EpodTaskRepository extends CrudRepository<EpodTask, Serializable> , JpaSpecificationExecutor<EpodTask> {


    @Query(value = "select max(id) from epod_task", nativeQuery = true)
    public Long getMaxId();
}
