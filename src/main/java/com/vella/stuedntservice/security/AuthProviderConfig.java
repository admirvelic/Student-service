package com.vella.stuedntservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {
  private final PasswordEncoderConfig passwordEncoder;
  private final UserDetailsConfig userDetails;

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetails.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
    return authProvider;
  }
}
