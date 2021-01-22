package com.bs.demo.Service;

import java.util.Date;
import java.util.Map;

import com.bs.demo.Entity.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {

  @Value("${jwt.salt")
  private String salt;

  @Value("${jwt.expmin}")
  private Long expireMin;

  public String create(final User user){

    log.trace("time: {}", expireMin);

    final JwtBuilder builder = Jwts.builder();

    builder.setHeaderParam("typ", "JWT");

    builder.setSubject("로그인 토큰")
      .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*expireMin))
      .claim("User", user).claim("second", "토큰에 담을 데이터 추가");

      builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

      final String jwt = builder.compact();

      log.debug("토큰 발행: {}", jwt);

    return jwt;
  }

  public void checkValid(final String jwt){
    log.trace("토큰 점검: {}", jwt);

    Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
  }

  public Map<String, Object> get(final String jwt){

    Jws<Claims> claims = null;

    try{
      claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }catch (Exception e){
      System.out.println(e);
      throw new RuntimeException();
    }

    return claims.getBody();
  }


}
