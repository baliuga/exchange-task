package com.task.exchange.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final static private String USER_ROLE = "USER";
    final static private String ADMIN_ROLE = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").hasRole(USER_ROLE)
                .antMatchers(HttpMethod.POST, "/api/exchange").hasRole(USER_ROLE)
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(ADMIN_ROLE)
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles(USER_ROLE)
                .and()
                .withUser("admin").password("{noop}password").roles(USER_ROLE, ADMIN_ROLE);
    }
}