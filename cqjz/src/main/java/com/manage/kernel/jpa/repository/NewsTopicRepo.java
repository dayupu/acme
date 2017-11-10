package com.manage.kernel.jpa.repository;

import com.manage.base.database.enums.TopicStatus;
import java.io.Serializable;
import com.manage.kernel.jpa.entity.NewsTopic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NewsTopicRepo extends CrudRepository<NewsTopic, Serializable>, JpaSpecificationExecutor<NewsTopic> {

    @Query("from NewsTopic where status = :status and level = 1")
    public List<NewsTopic> queryRootTopics(@Param("status") TopicStatus status);

    @Query("from NewsTopic where code = :code and parentCode = :parentCode")
    public NewsTopic findTopicsByCode(@Param("code") Integer code, @Param("parentCode") Integer parentCode);

}
