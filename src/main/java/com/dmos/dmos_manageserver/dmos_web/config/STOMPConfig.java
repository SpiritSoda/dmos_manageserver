package com.dmos.dmos_manageserver.dmos_web.config;

import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_manageserver.dmos_web.intercepor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class STOMPConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtConfig jwtConfig;
    @Autowired
    STOMPConfig(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //endPoint 注册协议节点,并映射指定的URl点对点-用
        //注册一个名字为"/endpointSocket" 的endpoint,并指定 SockJS协议。
        //允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        //连接前缀
        registry.addEndpoint("/dmos")
                .setAllowedOrigins("*")  // 跨域处理
                .addInterceptors(createSessionInterceptor())
                .withSockJS();  //支持socketJs
    }
    @Bean
    public SessionInterceptor createSessionInterceptor(){
        return new SessionInterceptor(jwtConfig);
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/api/dmos", "/user");

        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        config.setApplicationDestinationPrefixes("/api/dmos");

        // 修改convertAndSendToUser方法前缀
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），
        // 不设置的话，默认也是/user/
        // config.setUserDestinationPrefix ("/user");
    }
}
