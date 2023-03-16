package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;import com.vella.stuedntservice.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JwtServiceImpl implements JwtService {

  private static final String SECRET_KEY =
      "46294A404E635266556A586E327235753878214125442A472D4B615064536756";

  @Override
  public String getUsername(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }
  public String generateToken(Optional<AppUser> appUser) {
    if (appUser.isEmpty()){
      throw new CustomErrorException("AppUser is missing");
    }
    return generateToken(new HashMap<>(), appUser.get());
  }

  public String generateToken(Map<String, ?> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateTokenForAppUser(Map<String, Object> extraClaims, AppUser appUser) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(appUser.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(jwt);
    return claimsResolver.apply(claims);
  }

  public boolean isTokenValid(String jwt, UserDetails userDetails) {
    final String username = getUsername(jwt);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
  }

  private boolean isTokenExpired(String jwt) {
    return extractExpiration(jwt).before(new Date());
  }

  private Date extractExpiration(String jwt) {
    return extractClaim(jwt, Claims::getExpiration);
  }

  private Claims extractAllClaims(String jwt) {
    return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt).getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
