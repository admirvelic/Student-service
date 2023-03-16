package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.AppUser;
import com.vella.stuedntservice.model.requests.AppUserCreateRequest;
import com.vella.stuedntservice.repository.AppUserRepo;
import com.vella.stuedntservice.security.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

  private final AppUserRepo appUserRepo;
  private final PasswordEncoderConfig passwordEncoder;

  @Override
  public AppUser getAppUserById(Long id) {
    log.info("Fetching app user by id {}", id);
    Optional<AppUser> appUser = appUserRepo.findById(id);
    if (appUser.isEmpty()) {
      throw new CustomErrorException("AppUser is missing");
    }
    return appUser.get();
  }

  @Override
  public AppUser getAppUserByUsername(String username) {
    log.info("Fetching app user by username {}", username);
    Optional<AppUser> appUser = appUserRepo.findByUsername(username);
    if (appUser.isEmpty()) {
      throw new CustomErrorException("AppUser is missing");
    }
    return appUser.get();
  }

  @Override
  public List<AppUser> getAllAppUsers() {
    log.info("Fetching all app user");
    return appUserRepo.findAll();
  }

  @Override
  public AppUser saveAppUser(AppUser appUser) {
    log.info("Saving new app user {} to the database", appUser.getId());
    appUser.setPassword(passwordEncoder.passwordEncoder().encode(appUser.getPassword()));
    return appUserRepo.save(appUser);
  }

  @Override
  public AppUser saveAppUser(AppUserCreateRequest request) {
    if (request.getName() == null) throw new IllegalArgumentException("Field 'name' is missing");
    else if (request.getPassword() == null)
      throw new IllegalArgumentException("Field 'password' is missing");
    else if (request.getUsername() == null)
      throw new IllegalArgumentException("Field 'username' is missing");
    else if (request.getRole() == null)
      throw new IllegalArgumentException("Field 'role' is missing");
    AppUser appUser = new AppUser();
    appUser.setName(request.getName());
    appUser.setPassword(request.getPassword());
    appUser.setUsername(request.getUsername());
    log.info("Saving new app user {} to the database", appUser.getId());
    appUser.setPassword(passwordEncoder.passwordEncoder().encode(appUser.getPassword()));
    return appUserRepo.save(appUser);
  }

  @Override
  public AppUser updateAppUser(Long id, AppUser appUser) {
    log.info("Updating app user with id {}", id);
    Optional<AppUser> appUserData = appUserRepo.findById(id);
    if (appUserData.isEmpty())
      throw new RuntimeException("Could not fetch app user data with id" + id);

    AppUser appUserDb = appUserData.get();
    if (appUser.getId() != null) appUserDb.setId(appUser.getId());
    if (appUser.getName() != null) appUserDb.setName(appUser.getName());
    if (appUser.getUsername() != null) appUserDb.setUsername(appUser.getUsername());
    if (appUser.getPassword() != null) appUserDb.setPassword(appUser.getPassword());

    return appUserRepo.save(appUserDb);
  }

  @Override
  public AppUser updateAppUser(Long id, AppUserCreateRequest request) {
    log.info("Updating app user with id {}", id);
    Optional<AppUser> appUserData = appUserRepo.findById(id);
    if (appUserData.isEmpty())
      throw new RuntimeException("Could not fetch app user data with id" + id);

    AppUser appUserDb = appUserData.get();
    if (request.getName() != null) appUserDb.setName(request.getName());
    if (request.getUsername() != null) appUserDb.setUsername(request.getUsername());
    if (request.getPassword() != null) appUserDb.setPassword(request.getPassword());

    return appUserRepo.save(appUserDb);
  }

  @Override
  public void deleteAppUser(Long id) {
    log.info("Deleting app user with id {}", id);
    appUserRepo.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUser> appUser = appUserRepo.findByUsername(username);
    if (appUser.isEmpty())
      throw new RuntimeException("Could not fetch app user data with username " + username);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(appUser.get().getRole().name()));
    return new User(appUser.get().getUsername(), appUser.get().getPassword(), authorities);
  }
}
