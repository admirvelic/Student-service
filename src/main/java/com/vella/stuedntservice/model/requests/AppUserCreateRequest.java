package com.vella.stuedntservice.model.requests;

import com.vella.stuedntservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserCreateRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
}