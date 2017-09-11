package com.otms.support.kernel.repository;

import com.otms.support.kernel.entity.JobSheetEvent;
import com.otms.support.kernel.entity.OrderEvent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface JobSheetEventRepo extends CrudRepository<JobSheetEvent, Serializable>, JpaSpecificationExecutor<JobSheetEvent> {


}