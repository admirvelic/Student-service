package com.vella.studentservice.repository;

import com.vella.studentservice.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolClassRepo extends JpaRepository<SchoolClass, Long> {

  Optional<SchoolClass> findById(Long id);

  Optional<SchoolClass> findByName(String name);
}
