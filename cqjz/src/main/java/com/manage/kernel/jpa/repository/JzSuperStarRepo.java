package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzSuperStar;
import com.manage.kernel.jpa.entity.JzWatch;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JzSuperStarRepo extends CrudRepository<JzSuperStar, Serializable>, JpaSpecificationExecutor<JzSuperStar> {

}
