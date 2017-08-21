package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.Department;
import com.manage.kernel.jpa.news.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface DepartRepo extends CrudRepository<Department, Serializable>, JpaSpecificationExecutor<Department> {

    @Query("from Department order by level, sequence")
    List<Department> queryListAll();

    @Query("from Department where parentCode = :parentCode order by code asc")
    List<Department> queryListByParentCode(@Param("parentCode") String parentCode);

    @Query("from Department where level = :level")
    List<Department> queryListByLevel(@Param("level") Integer level);

    @Query("select count(1) from Department where level = :level")
    int queryListByLevelCount(@Param("level") Integer level);

    @Query("from Department where code in (:codes)")
    List<Department> queryListByCodes(@Param("codes") List<String> codes);

}