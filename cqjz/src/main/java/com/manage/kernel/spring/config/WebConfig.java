package com.manage.kernel.spring.config;

import com.manage.kernel.spring.config.filter.AdminFilter;
import javax.servlet.MultipartConfigElement;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConfigurationProperties
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${server.port}")
    private int serverPort = 8080;
    @Value("${server.sessionTimeout}")
    private int sessionTimeout = 30;
    @Value("${server.upload.file.max.size}")
    private int maxFileSize = 10;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("admin/login");
        registry.addViewController("/loginLimit").setViewName("error/loginLimit");
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(serverPort);
        factory.setSessionTimeout(sessionTimeout, TimeUnit.MINUTES);
        return factory;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize * 1024 * 1024);
        return factory.createMultipartConfig();
    }

    @Bean
    public FilterRegistrationBean adminFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AdminFilter());
        registration.addUrlPatterns("/admin/*");
        return registration;
    }

}
