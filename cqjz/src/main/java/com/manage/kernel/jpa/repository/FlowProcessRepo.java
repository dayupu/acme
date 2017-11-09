package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.FlowProcess;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FlowProcessRepo
        extends CrudRepository<FlowProcess, Serializable>, JpaSpecificationExecutor<FlowProcess> {

    @Query("from FlowProcess where businessId = :businessId")
    public FlowProcess findByBusinessId(@Param("businessId") String businessId);

}
