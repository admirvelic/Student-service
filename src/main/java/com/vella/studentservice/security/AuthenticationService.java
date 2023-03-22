package com.vella.studentservice.security;

public interface AuthenticationService {

  public AuthenticationResponse register(RegisterRequest request);

  public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
}
