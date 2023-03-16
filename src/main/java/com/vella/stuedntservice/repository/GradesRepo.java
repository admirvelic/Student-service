package com.vella.stuedntservice.repository;

import com.vella.stuedntservice.model.Grades;
import com.vella.stuedntservice.model.Student;
import com.vella.stuedntservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;import java.util.Optional;

public interface GradesRepo extends JpaRepository<Grades, Long> {

  Optional<Grades> findById(Long id);

  List<Grades> findByStudentAndSubject(Student student, Subject subject);
}
