package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzStyle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import java.io.Serializable;

public interface JzStyleRepo extends CrudRepository<JzStyle, Serializable>, JpaSpecificationExecutor<JzStyle> {

}
