package com.magic.acme.assist.common.aspect;

import com.magic.acme.assist.common.model.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAspect {



    @Pointcut("execution(* *(..)) && @annotation(com.magic.acme.assist.common.annotation.CurrentUser)")
    public void doExecution() {
    }

    @Before("doExecution()")
    private User doBefore(){

        User user = new User();
        user.setName("aaa");

        return user;
    }
}
