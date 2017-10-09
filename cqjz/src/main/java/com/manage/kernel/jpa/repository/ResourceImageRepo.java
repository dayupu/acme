package com.manage.kernel.jpa.repository;

import java.io.Serializable;
import com.manage.kernel.jpa.entity.ResourceImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ResourceImageRepo
        extends CrudRepository<ResourceImage, Serializable>, JpaSpecificationExecutor<ResourceImage> {

    @Query("from ResourceImage where imageId = :imageId")
    public ResourceImage findByImageId(@Param("imageId") String imageId);
}
