package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.Permission;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepo extends CrudRepository<Permission, Serializable> , JpaSpecificationExecutor<Permission> {


}
