package com.topawar.manage.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qcloud.cos.utils.Jackson;
import com.topawar.manage.domain.request.LoginParam;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: YJ
 * @date: 2023/04/18 9:47
 */
@Slf4j
public class JwtUtil {
    private static final String SECRET = "topawar";

     public static String generateToke(LoginParam loginParam){
         try {
             Algorithm algorithm = Algorithm.HMAC256(SECRET);
             Map<String, Object> header = new HashMap<>();
             header.put("Type","Jwt");
             header.put("alg","HS256");
             return JWT.create().withHeader(header)
                     .withClaim("token", Jackson.toJsonString(loginParam))
                     .sign(algorithm);
         }catch (Exception e){
             log.error(e.getMessage());
         }
         return null;
     }

     public static LoginParam parseToken(String token){
         Algorithm algorithm = Algorithm.HMAC256(SECRET);
         JWTVerifier jwtVerifier = JWT.require(algorithm).build();
         DecodedJWT decodedJWT = jwtVerifier.verify(token);
         String tokenInfo = decodedJWT.getClaim("token").asString();
         return  Jackson.fromJsonString(tokenInfo,LoginParam.class);
     }
}
