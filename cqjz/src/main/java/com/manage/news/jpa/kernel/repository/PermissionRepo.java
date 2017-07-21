package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.Permission;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepo extends CrudRepository<Permission, Serializable> , JpaSpecificationExecutor<Permission> {


}
