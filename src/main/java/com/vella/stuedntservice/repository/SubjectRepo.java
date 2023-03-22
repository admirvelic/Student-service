package com.vella.stuedntservice.repository;

import com.vella.stuedntservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {

  Optional<Subject> findById(Long id);

  Optional<Subject> findByName(String name);
}
