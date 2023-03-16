package com.vella.stuedntservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@RequiredArgsConstructor
public class AuthManagerConfig {
  @Bean
  public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }


}
