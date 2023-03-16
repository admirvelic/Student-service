package com.vella.stuedntservice.security;

import com.vella.stuedntservice.repository.AppUserRepo;import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

  private final AppUserRepo appUserRepo;

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return username ->
                appUserRepo
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
