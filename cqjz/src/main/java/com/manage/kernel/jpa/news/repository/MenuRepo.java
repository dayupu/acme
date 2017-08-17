package com.manage.kernel.jpa.news.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.manage.kernel.jpa.news.entity.Menu;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface MenuRepo extends CrudRepository<Menu, Serializable>, JpaSpecificationExecutor<Menu> {

    @Query("select distinct m from Role o inner join o.menus m where o.id in (:roleIds) order by m.level asc, m.sequence asc")
    List<Menu> queryMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);


    @Query("from Menu m order by m.level, m.sequence")
    List<Menu> queryListAll();

    @Query("from Menu m where m.parentId = :parentId")
    List<Menu> queryMenuListByParentId(@Param("parentId") Long parentId);

    @Query("from Menu m where m.url = :url")
    List<Menu> queryMenuListByUrl(@Param("url") String url);

    @Query("from Menu m where m.level = :level")
    List<Menu> queryMenuListByLevel(@Param("level") Integer level);

    @Query("from Menu where id in (:ids)")
    List<Menu> queryListByIds(@Param("ids") List<Long> ids);

}