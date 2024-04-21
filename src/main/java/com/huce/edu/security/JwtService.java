package com.huce.edu.security;

import com.huce.edu.advice.exceptions.NotFoundException;
import com.huce.edu.shareds.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public String extractUsername(String token, String publicKey) {
        return extractClaim(token, publicKey, Claims::getSubject);
    }

    public Date extractExpiration(String token, String publicKey) {
        return extractClaim(token, publicKey, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, String publicKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, publicKey);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String publicKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey(publicKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token,String publicKey) {
        return extractExpiration(token, publicKey).before(new Date());
    }

    public Boolean validateToken(String token, String publicKey, UserDetails userDetails) {
        final String username = extractUsername(token, publicKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, publicKey));
//        try {
//            final String username = extractUsername(token, publicKey);
//            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, publicKey));
//        }
//        catch (ExpiredJwtException e) {
//            throw new NotFoundException("Token hết hạn");
//        }
//        catch (IllegalArgumentException e){
//            throw new NotFoundException("Token đối số ngoại lệ");
//        }
    }


    public String generateAccessToken(String userName, String publicKey){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName, publicKey, Constants.ACCESS_TOKEN_EXP);
    }

    public String generateRefreshToken(String userName, String privateKey){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName, privateKey, Constants.REFRESH_TOKEN_EXP);
    }

    private String createToken(Map<String, Object> claims, String userName, String publicKey, long ms) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ ms))
                .signWith(getSignKey(publicKey), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(String publicKey) {
        byte[] keyBytes= Decoders.BASE64.decode(publicKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}