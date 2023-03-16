package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.AppUser;
import com.vella.stuedntservice.model.requests.AppUserCreateRequest;
import java.io.IOException;
import java.util.List;

public interface AppUserService {

  AppUser getAppUserById(Long id);

  AppUser getAppUserByUsername(String username);

  List<AppUser> getAllAppUsers();

  AppUser saveAppUser(AppUser AppUser);

  AppUser saveAppUser(AppUserCreateRequest requestr);

  AppUser updateAppUser(Long id, AppUser appUser) throws IOException;
  AppUser updateAppUser(Long id, AppUserCreateRequest request) throws IOException;

  void deleteAppUser(Long id);
}
