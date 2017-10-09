package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.AdRole;

import java.io.Serializable;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdRoleRepo extends CrudRepository<AdRole, Serializable>, JpaSpecificationExecutor<AdRole> {
    @Query("from AdRole order by id")
    List<AdRole> queryListAll();

    @Query("from AdRole where id in (:ids)")
    List<AdRole> queryListByIds(@Param("ids") List<Long> ids);
}
