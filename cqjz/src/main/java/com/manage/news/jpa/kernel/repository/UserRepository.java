package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.User;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Serializable>, JpaSpecificationExecutor<User> {

    @Query("FROM User u WHERE account = :account")
    User findUserByAccount(@Param("account") String account);

}
