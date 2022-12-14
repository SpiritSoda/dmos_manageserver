package com.dmos.dmos_manageserver.bean;

import com.dmos.dmos_common.config.DMOSConfig;
import com.dmos.dmos_common.util.ConfigUtil;
import com.dmos.dmos_common.util.HttpUtil;
import com.dmos.dmos_server.DMOSServerContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DMOSBean {
    @Bean
    public static DMOSServerContext dmosServerContext(){
        return new DMOSServerContext();
    }
    @Bean
    public static DMOSConfig dmosConfig(){
        DMOSConfig config = ConfigUtil.load("config.json", DMOSConfig.class);
        if(config == null)
            config = new DMOSConfig();
        return config;
    }
    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public static RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
