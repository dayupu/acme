package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.Role;

import java.io.Serializable;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Serializable>, JpaSpecificationExecutor<Role> {
    @Query("from Role order by id")
    List<Role> queryListAll();
}
