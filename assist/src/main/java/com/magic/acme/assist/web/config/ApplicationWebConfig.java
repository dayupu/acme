package com.magic.acme.assist.web.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Bean
    public SpringWebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        return new SpringWebMvcConfigurerAdapter();
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SpringWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //registry.addInterceptor(new ControllerValidatorInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {



        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests().antMatchers("/**").permitAll();
//            http.authorizeRequests().antMatchers("/js/**", "/css/**","/img/**","/plugins/**", "/ws/**").permitAll().anyRequest()
//                    .fullyAuthenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
//                    .failureUrl("/login?error").permitAll().and().logout().permitAll();
            http.csrf().disable();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {

        }

    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(600);//单位为S
            }
        };
    }
}
