package com.myssteriion.blindtest.security;

import com.myssteriion.utils.security.CommonSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfiguration extends CommonSecurityConfiguration {
    
    @Override
    protected void configureXsrf(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
    
    @Override
    protected void configureRequest(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
    }
    
}
