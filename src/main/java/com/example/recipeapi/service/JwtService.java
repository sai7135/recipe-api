package com.example.recipeapi.service;

import com.example.recipeapi.models.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Repository
public class JwtService {
    private final String SECRET_KEY="I LOVE DIVYA I CAN DO ANYTHING FOR HER I AM IN LOVE WITH HER";
    public String  getUserName(String token){
        return this.getClaim(token,Claims::getSubject);
    }

    public Date getExpiration(String token){
        return this.getClaim(token,Claims::getExpiration);
    }

    public String buildToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(this.getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey(){
        byte[] encodedString= Base64.getEncoder().encode(this.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(encodedString);
    }

    private <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        var claims=this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
