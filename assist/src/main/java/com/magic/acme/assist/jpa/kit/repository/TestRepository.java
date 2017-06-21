package com.magic.acme.assist.jpa.kit.repository;

import com.magic.acme.assist.jpa.kit.entity.Log;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Log, Serializable>, JpaSpecificationExecutor<Log> {

}
