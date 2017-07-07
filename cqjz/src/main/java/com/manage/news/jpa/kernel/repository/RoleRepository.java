package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.Role;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Serializable> , JpaSpecificationExecutor<Role> {


}
