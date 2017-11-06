package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzStyle;
import com.manage.kernel.jpa.entity.JzStyleLine;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JzStyleLineRepo extends CrudRepository<JzStyleLine, Serializable>, JpaSpecificationExecutor<JzStyleLine> {


}
