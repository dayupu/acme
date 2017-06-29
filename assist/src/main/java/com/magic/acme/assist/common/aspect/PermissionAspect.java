package com.magic.acme.assist.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PermissionAspect {

    @Pointcut("execution(* *(..)) && @annotation(com.magic.acme.assist.common.annotation.Permission)")
    public void doExecution() {
    }

    @Around("doExecution()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println(111);
        return pjp.proceed();
    }
}
