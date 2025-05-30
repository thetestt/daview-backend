package com.daview.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 자동으로 키 생성해주는거임

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1시간

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
