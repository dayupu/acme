package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.News;
import com.manage.kernel.jpa.news.entity.User;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NewsRepo extends CrudRepository<News, Serializable>, JpaSpecificationExecutor<News> {

}
