package com.vella.studentservice.service;

import com.vella.studentservice.model.Grades;
import com.vella.studentservice.model.Student;
import com.vella.studentservice.model.requests.StudentCreateRequest;
import com.vella.studentservice.model.requests.SubjectCreateRequest;
import java.io.IOException;
import java.util.List;

public interface StudentService {

  Student getStudentById(Long id);

  List<Student> getAllStudents();

  Student saveStudent(Student student);

  Student saveStudent(StudentCreateRequest request);

  Student updateStudent(Long id, StudentCreateRequest request) throws IOException;

  void deleteStudent(Long id);

  List<Grades> getGradesForStudentForSubject(Long id, SubjectCreateRequest request);
}
