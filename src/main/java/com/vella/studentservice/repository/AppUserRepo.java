package com.vella.studentservice.repository;

import com.vella.studentservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findById(Long id);

  Optional<AppUser> findByUsername(String username);
}
