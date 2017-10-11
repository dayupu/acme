package com.manage.kernel.jpa.repository;

import com.manage.kernel.jpa.entity.AdOrganization;
import com.manage.kernel.jpa.entity.AdRole;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdOrganRepo extends CrudRepository<AdOrganization, Serializable>, JpaSpecificationExecutor<AdOrganization> {

}
