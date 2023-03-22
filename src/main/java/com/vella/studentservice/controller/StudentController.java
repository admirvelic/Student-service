package com.vella.studentservice.controller;

import com.vella.studentservice.model.Grades;
import com.vella.studentservice.model.requests.StudentCreateRequest;
import com.vella.studentservice.model.requests.SubjectCreateRequest;
import com.vella.studentservice.service.StudentService;
import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
  private final StudentService studentService;

  @GetMapping("/{id}")
  public Student getStudentById(@PathVariable Long id) throws CustomErrorException {
    return studentService.getStudentById(id);
  }

  @GetMapping("/all")
  public List<Student> getAllStudents() {
    return studentService.getAllStudents();
  }

  @PostMapping("/save")
  public Student saveStudent(@RequestBody StudentCreateRequest request)
      throws CustomErrorException {
    return studentService.saveStudent(request);
  }

  @PutMapping("/{id}/update")
  public Student updateStudent(@PathVariable Long id, @RequestBody StudentCreateRequest request)
      throws CustomErrorException, IOException {
    return studentService.updateStudent(id, request);
  }

  @DeleteMapping("/{id}/delete")
  public String deleteStudent(@PathVariable Long id) throws CustomErrorException {
    studentService.deleteStudent(id);
    return "Successfully deleted student with id " + id;
  }

  @GetMapping("/{id}/subjectGrades")
  public List<Grades> getGradesForStudentForSubject(
      @PathVariable Long studentId, @RequestBody SubjectCreateRequest request)
      throws CustomErrorException {
    return studentService.getGradesForStudentForSubject(studentId, request);
  }
}
