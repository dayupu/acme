package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzWatch;
import java.io.Serializable;
import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface JzWatchRepo extends CrudRepository<JzWatch, Serializable>, JpaSpecificationExecutor<JzWatch> {

    @Query("from JzWatch where watchTime between :watchTime and :watchTimeEnd")
    public List<JzWatch> findByWatchTime(@Param("watchTime") LocalDate watchTime,
            @Param("watchTimeEnd") LocalDate watchTimeEnd);

    @Query("from JzWatch where watchTime = :watchTime")
    public JzWatch findByWatchTime(@Param("watchTime") LocalDate watchTime);
}

