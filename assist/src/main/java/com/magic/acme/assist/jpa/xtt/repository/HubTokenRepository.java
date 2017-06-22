package com.magic.acme.assist.jpa.xtt.repository;

import com.magic.acme.assist.jpa.xtt.entity.HubToken;
import com.magic.acme.assist.jpa.xtt.entity.Token;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bert on 17-6-22.
 */
public interface HubTokenRepository extends CrudRepository<HubToken, Serializable>, JpaSpecificationExecutor<HubToken> {


}
