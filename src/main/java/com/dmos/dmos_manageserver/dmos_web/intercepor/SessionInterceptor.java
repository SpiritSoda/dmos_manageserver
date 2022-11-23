package com.dmos.dmos_manageserver.dmos_web.intercepor;

import com.auth0.jwt.interfaces.Claim;
import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_manageserver.dmos_register.exception.NoTokenException;
import com.dmos.dmos_manageserver.dmos_register.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class SessionInterceptor implements HandshakeInterceptor {
    private final JwtConfig jwtConfig;
    @Autowired
    public SessionInterceptor(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        String query = serverHttpRequest.getURI().getQuery();
        if(query == null)
            throw new NoTokenException("token不存在");
        String[] params = query.split("&");
        if(params.length == 0)
            throw new NoTokenException("token不存在");
        String token = params[0].split("=")[1];

        try{
            if (token == null){
                throw new NoTokenException("token不存在");
            }
            Map<String, Claim> verify = JwtUtils.parse(token, jwtConfig);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
