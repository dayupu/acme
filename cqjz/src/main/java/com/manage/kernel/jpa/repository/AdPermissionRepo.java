package com.manage.kernel.jpa.repository;

import com.manage.base.database.enums.Permit;
import com.manage.base.database.enums.PermitType;
import com.manage.kernel.jpa.entity.AdPermission;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdPermissionRepo extends CrudRepository<AdPermission, Serializable>, JpaSpecificationExecutor<AdPermission> {

    @Query("from AdPermission where permit = :permit and type = :type")
    AdPermission findByPermit(@Param("permit") Permit permit, @Param("type") PermitType type);

    @Query("from AdPermission order by code")
    List<AdPermission> queryListAll();

    @Query("from AdPermission where code in (:codes)")
    List<AdPermission> queryListByCode(@Param("codes") List<String> codes);
}
