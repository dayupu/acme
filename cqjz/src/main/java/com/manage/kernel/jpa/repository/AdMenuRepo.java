package com.manage.kernel.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.manage.kernel.jpa.entity.AdMenu;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface AdMenuRepo extends CrudRepository<AdMenu, Serializable>, JpaSpecificationExecutor<AdMenu> {

    @Query("select distinct m from AdRole o inner join o.menus m where o.id in (:roleIds) order by m.level asc, m.sequence asc")
    List<AdMenu> queryMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);

    @Query("from AdMenu order by level, sequence")
    List<AdMenu> queryListAll();

    @Query("from AdMenu where parentId = :parentId")
    List<AdMenu> queryListByParentId(@Param("parentId") Long parentId);

    @Query("from AdMenu where url = :url")
    List<AdMenu> queryListByUrl(@Param("url") String url);

    @Query("from AdMenu where level = :level")
    List<AdMenu> queryListByLevel(@Param("level") Integer level);

    @Query("from AdMenu where id in (:ids)")
    List<AdMenu> queryListByIds(@Param("ids") List<Long> ids);

}