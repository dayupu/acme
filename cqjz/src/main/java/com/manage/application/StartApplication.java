package com.manage.application;

import static com.manage.kernel.spring.comm.SpringUtils.getBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.manage.kernel")
@ImportResource({ "classpath:META-INF/spring/applicationContext*.xml" })
public class StartApplication {

    public static void main(String[] args) {

        SpringApplication.run(StartApplication.class, args);
    }
}
