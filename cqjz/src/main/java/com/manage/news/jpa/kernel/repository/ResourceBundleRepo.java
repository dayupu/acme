package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.ResourceBundle;
import com.manage.news.jpa.kernel.entity.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ResourceBundleRepo extends CrudRepository<ResourceBundle, Serializable> , JpaSpecificationExecutor<ResourceBundle> {

}
