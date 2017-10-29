package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzSuperStar;
import com.manage.kernel.jpa.entity.JzWatch;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface JzSuperStarRepo extends CrudRepository<JzSuperStar, Serializable>, JpaSpecificationExecutor<JzSuperStar> {


    @Query("from JzSuperStar where year = :year and month = :month order by year desc, month desc")
    public List<JzSuperStar> findByYearMonth(@Param("year") String year, @Param("month") String month);
}
