package com.manage.kernel.spring.annotation;

import com.manage.base.extend.enums.Permit;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserPermit {

    Permit value();

    Permit group() default Permit.GROUP_DEFAULT;

}
