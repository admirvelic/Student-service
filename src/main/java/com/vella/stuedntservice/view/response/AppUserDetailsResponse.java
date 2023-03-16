package com.vella.stuedntservice.view.response;

import com.vella.stuedntservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUserDetailsResponse {
  private Long id;
  private String username;

  private Role role;
}
