package com.otms.support.kernel.repository;

import com.otms.support.kernel.entity.Inbound;
import com.otms.support.kernel.entity.OrderEvent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface InBoundRepo extends CrudRepository<Inbound, Serializable>, JpaSpecificationExecutor<Inbound> {


}