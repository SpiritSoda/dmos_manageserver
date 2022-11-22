package com.dmos.dmos_manageserver.dmos_register.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_manageserver.dmos_register.dto.NodeDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String sign(NodeDTO user, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("id", user.getId())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return token;
    }
    public static String sign(String machineToken, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("token", machineToken)
                    .withClaim("timestamp", System.currentTimeMillis())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return token;
    }

    public static Map<String, Claim> parse(String token, JwtConfig jwtConfig){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public static int verify(String token, JwtConfig jwtConfig){
        Map<String, Claim> claims = parse(token, jwtConfig);
        String machineToken = claims.get("token").asString();
        Map<String, Claim> machineClaims = parse(machineToken, new JwtConfig());
        int id = machineClaims.get("id").asInt();
        long time_exceed = claims.get("timestamp").asLong() + jwtConfig.getExceed();
        if(System.currentTimeMillis() > time_exceed)
            return  -1;
        return id;
    }

}