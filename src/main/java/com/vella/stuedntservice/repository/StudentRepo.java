package com.vella.stuedntservice.repository;

import com.vella.stuedntservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

  Optional<Student> findById(Long id);
}
