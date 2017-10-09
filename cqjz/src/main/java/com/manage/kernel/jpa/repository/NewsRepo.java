package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.News;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NewsRepo extends CrudRepository<News, Serializable>, JpaSpecificationExecutor<News> {

    @Query("from News where number = :number")
    public News findByNumber(@Param("number") String number);
}
