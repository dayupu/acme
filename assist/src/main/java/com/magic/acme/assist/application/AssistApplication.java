package com.magic.acme.assist.application;

import com.magic.acme.assist.module.otms.service.DataService;
import org.springframework.boot.SpringApplication;
import static org.springframework.boot.SpringApplication.run;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.magic.acme.assist")
@ImportResource({ "classpath:META-INF/spring/applicationContext*.xml" })
public class AssistApplication {

    public static void main(String[] args) {

        SpringApplication.run(AssistApplication.class, args);

        DataService dataService = SpringUtils.getBeanByClass(DataService.class);
        dataService.batchCreateDataForXtt();
    }
}
