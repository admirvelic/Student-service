package com.vella.studentservice.repository;

import com.vella.studentservice.model.Grades;
import com.vella.studentservice.model.Student;
import com.vella.studentservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradesRepo extends JpaRepository<Grades, Long> {

  Optional<Grades> findById(Long id);

  List<Grades> findByStudentAndSubject(Student student, Subject subject);
}
