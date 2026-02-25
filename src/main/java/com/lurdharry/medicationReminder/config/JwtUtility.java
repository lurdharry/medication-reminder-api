package com.lurdharry.medicationReminder.config;


import com.lurdharry.medicationReminder.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtility {


    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiryTime}")
    private long EXPIRATION_TIME;


    private SecretKey key;

    @PostConstruct
    private  void  init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public  String generateToken(User user) {
      return   Jwts.builder()
              .subject(user.getEmail())
              .signWith(key)
              .claim("userId", user.getId())
              .issuedAt(new Date())
              .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
              .compact();

    }

    public  String extractEmailFromToken(String token) {

        return getPayload(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        var expiryDate  = getPayload(token).getExpiration();
        return expiryDate.before(new Date());
    }


    private Claims getPayload(String token) {
       return Jwts.parser()
               .verifyWith(key)
               .build()
               .parseSignedClaims(token)
               .getPayload();
    }


}
