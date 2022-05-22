package com.zyy.sport.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    /**
     * 根据传入的登录用户信息，生成token
     */
    public String generateToken(UserDetails details) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("username", details.getUsername());
        map.put("created", new Date());
        return this.generateJwt(map);


    }

    /**
     * 根据荷载信息生成token
     */
    public String generateJwt(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }


    /**
     * 根据token获取荷载信息、
     */
    public Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 根据token得到用户名
     */
    public String getUserNameByToken(String token) {
        return (String) this.getTokenBody(token).get("username");
    }

    /**
     * 根据token判断在当前时间内是否过期
     */
    public boolean isExpiration(String token) {
        return this.getTokenBody(token).getExpiration().before(new Date());

    }

    /**
     * token刷新
     */
    public String refreshToken(String token) {
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }

}