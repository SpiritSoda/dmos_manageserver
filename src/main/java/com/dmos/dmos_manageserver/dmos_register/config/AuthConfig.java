package com.dmos.dmos_manageserver.dmos_register.config;

import com.dmos.dmos_manageserver.dmos_register.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class AuthConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(createAuthenticationInterceptor())
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/register/register")
//                .excludePathPatterns("/api/storage/**")
//                .excludePathPatterns("/api/web/**");
    }
    @Bean
    public AuthenticationInterceptor createAuthenticationInterceptor(){
        return new AuthenticationInterceptor();
    }
}
