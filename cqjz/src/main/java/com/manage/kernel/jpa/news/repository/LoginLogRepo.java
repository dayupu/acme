package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.LoginLog;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LoginLogRepo extends CrudRepository<LoginLog, Serializable>, JpaSpecificationExecutor<LoginLog> {

}
