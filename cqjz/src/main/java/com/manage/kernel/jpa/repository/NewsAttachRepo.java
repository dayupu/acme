package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.NewsAttach;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NewsAttachRepo extends CrudRepository<NewsAttach, Serializable>, JpaSpecificationExecutor<NewsAttach> {


}
