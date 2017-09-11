package com.otms.support.spring.annotation;

import com.otms.support.supplier.database.enums.APISource;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InboundLog {
    APISource value();
}
