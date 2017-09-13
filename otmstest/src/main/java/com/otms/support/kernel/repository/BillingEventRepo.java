package com.otms.support.kernel.repository;

import com.otms.support.kernel.entity.BillingEvent;
import com.otms.support.kernel.entity.Inbound;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BillingEventRepo extends CrudRepository<BillingEvent, Serializable>, JpaSpecificationExecutor<BillingEvent> {


}