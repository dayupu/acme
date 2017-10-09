package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.AdUser;

import java.io.Serializable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdUserRepo extends CrudRepository<AdUser, Serializable>, JpaSpecificationExecutor<AdUser> {

    @Query("FROM AdUser WHERE account = :account")
    AdUser findUserByAccount(@Param("account") String account);

    @Query("FROM AdUser WHERE id in (:ids)")
    List<AdUser> findListByIds(@Param("ids") List<Long> ids);

    @Query("SELECT name FROM AdUser WHERE account = :account")
    String getUserNameByAccount(@Param("account") String account);
}
