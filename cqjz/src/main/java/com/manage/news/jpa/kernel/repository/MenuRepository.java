package com.manage.news.jpa.kernel.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.manage.news.jpa.kernel.entity.Menu;
import java.io.Serializable;

public interface MenuRepository extends CrudRepository<Menu, Serializable>,JpaSpecificationExecutor<Menu> {


}
