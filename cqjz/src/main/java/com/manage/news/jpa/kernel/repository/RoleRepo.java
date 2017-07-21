package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.Menu;
import com.manage.news.jpa.kernel.entity.Role;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends CrudRepository<Role, Serializable> , JpaSpecificationExecutor<Role> {

    @Query("select distinct o.menus from Role o where o.id in (:roleIds)")
    public List<Menu> queryMenuListByRoleIds(@Param("roleIds") List<Long> roleIds);

}
