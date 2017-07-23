package com.manage.news.jpa.kernel.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.manage.news.jpa.kernel.entity.Menu;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface MenuRepo extends CrudRepository<Menu, Serializable>, JpaSpecificationExecutor<Menu> {

    @Query("select distinct m from Role o inner join o.menus m where o.id in (:roleIds) order by m.level asc, m.sequence asc")
    List<Menu> queryMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);

}
