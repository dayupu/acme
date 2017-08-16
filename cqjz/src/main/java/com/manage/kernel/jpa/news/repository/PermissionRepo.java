package com.manage.kernel.jpa.news.repository;

import com.manage.base.extend.enums.Permit;
import com.manage.base.extend.enums.PermitType;
import com.manage.kernel.jpa.news.entity.Permission;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PermissionRepo extends CrudRepository<Permission, Serializable>, JpaSpecificationExecutor<Permission> {

    @Query("from Permission where permit = :permit and type = :type")
    Permission findByPermit(@Param("permit") Permit permit, @Param("type") PermitType type);

}
