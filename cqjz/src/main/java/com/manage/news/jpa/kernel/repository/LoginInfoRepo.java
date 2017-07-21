package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.LoginLog;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LoginInfoRepo extends CrudRepository<LoginLog, Serializable>, JpaSpecificationExecutor<LoginLog> {

}
