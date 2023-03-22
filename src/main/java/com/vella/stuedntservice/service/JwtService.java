package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.Optional;

public interface JwtService {

  String getUsername(String jwt);

  boolean isTokenValid(String jwt, UserDetails userDetails);

  public String generateToken(Map<String, ?> extraClaims, UserDetails userDetails);

  public String generateToken(Optional<AppUser> appUser);

  public String generateTokenForAppUser(Map<String, Object> extraClaims, AppUser appUser);
}
