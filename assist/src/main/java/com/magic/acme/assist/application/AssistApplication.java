package com.magic.acme.assist.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.magic.acme.assist")
@ImportResource({ "classpath:META-INF/spring/applicationContext*.xml" })
public class AssistApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssistApplication.class, args);
    }
}
