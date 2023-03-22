package com.vella.studentservice.repository;

import com.vella.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

  Optional<Student> findById(Long id);
}
