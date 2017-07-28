package com.manage.kernel.jpa.news.repository;

import com.manage.kernel.jpa.news.entity.User;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends CrudRepository<User, Serializable>, JpaSpecificationExecutor<User> {

    @Query("FROM User u WHERE account = :account")
    User findUserByAccount(@Param("account") String account);

}
