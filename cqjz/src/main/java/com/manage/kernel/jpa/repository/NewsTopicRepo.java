package com.manage.kernel.jpa.repository;

import java.io.Serializable;
import com.manage.kernel.jpa.entity.NewsTopic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NewsTopicRepo extends CrudRepository<NewsTopic, Serializable>, JpaSpecificationExecutor<NewsTopic> {

}
