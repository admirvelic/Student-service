package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.model.Grades;
import com.vella.stuedntservice.model.requests.StudentCreateRequest;
import com.vella.stuedntservice.model.requests.SubjectCreateRequest;
import com.vella.stuedntservice.repository.GradesRepo;
import com.vella.stuedntservice.repository.SchoolClassRepo;
import com.vella.stuedntservice.repository.StudentRepo;
import com.vella.stuedntservice.repository.SubjectRepo;
import com.vella.stuedntservice.service.StudentService;
import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    Student studentData = studentService.getStudentById(id);
    if (studentData == null)
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Student not found");

    return studentData;
  }

  @GetMapping("/all")
  public List<Student> getAllStudents() {
    return studentService.getAllStudents();
  }

  @PostMapping("/save")
  public Student saveStudent(@RequestBody StudentCreateRequest request)
      throws CustomErrorException {

    try {
      if (request.getFirstName() == null) throw new IOException("Field 'first name' is missing.");
      else if (request.getLastName() == null)
        throw new IOException("Field 'last name' is missing.");
      else if (request.getDateOfBirth() == null)
        throw new IOException("Field 'SchoolClass' is missing");

      return studentService.saveStudent(request);

    } catch (Exception e) {
      log.error("Error crating new student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PutMapping("/{id}/update")
  public Student updateStudent(@PathVariable Long id, @RequestBody Student student)
      throws CustomErrorException {
    Student studentData = studentService.getStudentById(id);
    if (studentData == null)
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Student not found");

    Student studentDb = studentData;

    if (student.getId() != null) studentDb.setId(student.getId());
    if (student.getFirstName() != null) studentDb.setFirstName(student.getFirstName());
    if (student.getLastName() != null) studentDb.setLastName(student.getLastName());
    if (student.getDateOfBirth() != null) studentDb.setDateOfBirth(student.getDateOfBirth());
    if (student.getSchoolClass() != null) studentDb.setSchoolClass(student.getSchoolClass());

    try {
      return studentService.updateStudent(id, studentDb);

    } catch (Exception e) {
      log.error("Error updating student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @DeleteMapping("/{id}/delete")
  public String deleteStudent(@PathVariable Long id) throws CustomErrorException {
    try {
      studentService.deleteStudent(id);

      return "Successfully deleted student with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @GetMapping("/{id}/subjectGrades")
  public List<Grades> getGradesForStudentForSubject(
      @PathVariable Long studentId, @RequestBody SubjectCreateRequest request)
      throws CustomErrorException {
    try {
      if (request.getName() == null) throw new IOException("Field 'subject' is missing.");
      return studentService.getGradesForStudentForSubject(studentId, request);
    } catch (Exception e) {
      log.error("Error crating new student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
