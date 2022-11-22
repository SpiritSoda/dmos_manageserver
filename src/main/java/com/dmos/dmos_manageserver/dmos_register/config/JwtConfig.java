package com.dmos.dmos_manageserver.dmos_register.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    // 超时时间，180秒
    @Value("${jwt.exceed}")
    private long exceed;
}
