package com.vella.studentservice.security;

import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.enums.Role;
import com.vella.studentservice.repository.AppUserRepo;
import com.vella.studentservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import com.vella.studentservice.model.AppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final PasswordEncoder passwordEncoder;
  private final AppUserRepo appUserRepo;
  private final JwtService jwtService;
  private final AuthManagerConfig authManager;
  private final AuthenticationConfiguration config;
  private final TokenRepo tokenRepo;

  public AuthenticationResponse register(RegisterRequest request) {

    AppUser appUser = new AppUser();
    appUser.setName(request.getName());
    appUser.setUsername(request.getUsername());
    appUser.setPassword(passwordEncoder.encode(request.getPassword()));
    appUser.setRole(Role.USER);
    Optional<AppUser> savedAppUser = Optional.of(appUserRepo.save(appUser));
    var jwt = jwtService.generateToken(savedAppUser);
    return AuthenticationResponse.builder().token(jwt).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
    authManager
        .authenticationManagerBean(config)
        .authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    Optional<AppUser> appUser = appUserRepo.findByUsername(request.getUsername());
    var jwt = jwtService.generateToken(appUser);
    if (appUser.isEmpty()) {
      throw new CustomErrorException("appUser is empty");
    }
    revokeAllUserTokens(appUser.get());
    saveUserToken(appUser.get(), jwt);
    return AuthenticationResponse.builder().token(jwt).build();
  }

  private void saveUserToken(AppUser user, String jwtToken) {
    var token =
        Token.builder()
            .appUser(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepo.save(token);
  }

  private void revokeAllUserTokens(AppUser user) {
    var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty()) return;
    validUserTokens.forEach(
        token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
    tokenRepo.saveAll(validUserTokens);
  }
}
