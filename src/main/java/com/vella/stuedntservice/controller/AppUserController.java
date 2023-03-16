package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.model.requests.AppUserCreateRequest;
import com.vella.stuedntservice.service.AppUserService;
import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/appUser")
@RequiredArgsConstructor
@Slf4j
public class AppUserController {
  private final AppUserService appUserService;

  @GetMapping("/{id}")
  public AppUser getAppUserById(@PathVariable Long id) throws CustomErrorException {
    AppUser appUserData = appUserService.getAppUserById(id);
    if (appUserData == null) {
      log.error("Error fetching user");
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "App user not found");
    }
    return appUserData;
  }

  @PutMapping("/{id}/update")
  public AppUser updateAppUser(@PathVariable Long id, @RequestBody AppUserCreateRequest request)
      throws CustomErrorException {
    try {
      return appUserService.updateAppUser(id, request);
    } catch (Exception e) {
      log.error("Error updating appUser {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/save")
  public AppUser saveAppUser(@RequestBody AppUserCreateRequest request)
      throws CustomErrorException {
    try {
      return appUserService.saveAppUser(request);
    } catch (Exception e) {
      log.error("Error crating new app user {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/all")
  public List<AppUser> getAllAppUser() throws CustomErrorException{
    try{
    return appUserService.getAllAppUsers();
    }catch (Exception e){
      log.error("Error fetching all students");
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    }


  @DeleteMapping("/{id}/delete")
  public String deleteAppUser(@PathVariable Long id) throws CustomErrorException {
    try {
      appUserService.deleteAppUser(id);

      return "Successfully deleted app user with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
