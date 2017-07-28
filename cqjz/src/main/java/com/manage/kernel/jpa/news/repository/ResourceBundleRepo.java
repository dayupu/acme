package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.ResourceBundle;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ResourceBundleRepo extends CrudRepository<ResourceBundle, Serializable> , JpaSpecificationExecutor<ResourceBundle> {

}
