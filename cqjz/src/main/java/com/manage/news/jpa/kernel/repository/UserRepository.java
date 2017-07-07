package com.manage.news.jpa.kernel.repository;

import com.manage.news.jpa.kernel.entity.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Serializable> , JpaSpecificationExecutor<User> {

    @Query(value = "from User where account = :account")
    User findUserByAccount(String account);

}
