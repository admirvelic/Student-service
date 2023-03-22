package com.vella.stuedntservice.repository;

import com.vella.stuedntservice.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepo extends JpaRepository<Professor, Long> {

  Optional<Professor> findById(Long id);
}