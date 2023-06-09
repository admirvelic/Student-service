package com.vella.studentservice.controller;

import com.vella.studentservice.model.requests.AppUserCreateRequest;
import com.vella.studentservice.service.AppUserService;
import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    return appUserService.getAppUserById(id);
  }

  @PutMapping("/{id}/update")
  public AppUser updateAppUser(@PathVariable Long id, @RequestBody AppUserCreateRequest request)
      throws CustomErrorException, IOException {
    return appUserService.updateAppUser(id, request);
  }

  @PostMapping("/save")
  public AppUser saveAppUser(@RequestBody AppUserCreateRequest request)
      throws CustomErrorException {
    return appUserService.saveAppUser(request);
  }

  @GetMapping("/all")
  public List<AppUser> getAllAppUser() throws CustomErrorException {
    return appUserService.getAllAppUsers();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteAppUser(@PathVariable Long id) throws CustomErrorException {
    appUserService.deleteAppUser(id);
    return "Successfully deleted app user with id " + id;
  }
}
