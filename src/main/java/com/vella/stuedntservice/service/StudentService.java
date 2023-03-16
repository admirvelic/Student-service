package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.Grades;import com.vella.stuedntservice.model.Student;import com.vella.stuedntservice.model.Subject;import com.vella.stuedntservice.model.requests.StudentCreateRequest;import com.vella.stuedntservice.model.requests.SubjectCreateRequest;

import java.io.IOException;
import java.util.List;

public interface StudentService {

  Student getStudentById(Long id);

  List<Student> getAllStudents();

  Student saveStudent(Student student);
  Student saveStudent(StudentCreateRequest request);

  Student updateStudent(Long id, Student student) throws IOException;

  void deleteStudent(Long id);
  List<Grades> getGradesForStudentForSubject(Long id, SubjectCreateRequest request);
}
