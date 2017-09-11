package com.otms.support.kernel.repository;

import com.otms.support.kernel.entity.OrderEvent;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrderEventRepo extends CrudRepository<OrderEvent, Serializable>, JpaSpecificationExecutor<OrderEvent> {


}