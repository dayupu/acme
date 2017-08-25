package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.ResourceFile;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ResourceFileRepo
        extends CrudRepository<ResourceFile, Serializable>, JpaSpecificationExecutor<ResourceFile> {

    @Query("from ResourceFile where fileId = :fileId")
    public ResourceFile findByFileId(@Param("fileId") String fileId);
}
