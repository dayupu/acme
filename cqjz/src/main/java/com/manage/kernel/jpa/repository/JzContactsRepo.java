package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.JzContacts;
import com.manage.kernel.jpa.entity.JzSuperStar;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface JzContactsRepo extends CrudRepository<JzContacts, Serializable>, JpaSpecificationExecutor<JzContacts> {

    @Query("from JzContacts where isHistory = false")
    public JzContacts findContactsForPublish();

    @Query("from JzContacts where isHistory = true order by version desc")
    public List<JzContacts> findContactsForHistory();
}
