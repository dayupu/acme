package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzWatch;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JzWatchRepo extends CrudRepository<JzWatch, Serializable>, JpaSpecificationExecutor<JzWatch> {

}
