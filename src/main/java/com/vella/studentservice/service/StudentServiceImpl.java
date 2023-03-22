package com.vella.studentservice.service;

import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.Grades;
import com.vella.studentservice.model.SchoolClass;
import com.vella.studentservice.model.Student;
import com.vella.studentservice.model.Subject;
import com.vella.studentservice.model.requests.StudentCreateRequest;
import com.vella.studentservice.model.requests.SubjectCreateRequest;
import com.vella.studentservice.repository.GradesRepo;
import com.vella.studentservice.repository.SchoolClassRepo;
import com.vella.studentservice.repository.StudentRepo;
import com.vella.studentservice.repository.SubjectRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {
  private final SubjectRepo subjectRepo;
  private final SchoolClassRepo schoolClassRepo;
  private final GradesRepo gradesRepo;
  private final StudentRepo studentRepo;

  public Student getStudentById(Long id) {
    try {
      log.info("Fetching student by id {}", id);
      Optional<Student> student = studentRepo.findById(id);
      if (student.isEmpty()) {
        throw new CustomErrorException("Student is missing");
      }
      return student.get();
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @Override
  public List<Student> getAllStudents() {
    try {
      log.info("Fetching all students");
      return studentRepo.findAll();
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public Student saveStudent(StudentCreateRequest request) {
    try {
      if (request.getFirstName() == null) throw new IOException("Field 'first name' is missing.");
      else if (request.getLastName() == null)
        throw new IOException("Field 'last name' is missing.");
      else if (request.getDateOfBirth() == null)
        throw new IOException("Field 'SchoolClass' is missing");
      Student student = new Student();
      student.setFirstName(request.getFirstName());
      student.setLastName(request.getLastName());
      student.setDateOfBirth(request.getDateOfBirth());
      Optional<SchoolClass> schoolClass = schoolClassRepo.findByName(request.getSchoolClass());
      if (schoolClass.isEmpty()) {
        throw new CustomErrorException("SchoolClass is missing");
      }
      student.setSchoolClass(schoolClass.get());
      log.info("Saving new student {} to the database", student.getId());
      return studentRepo.save(student);
    } catch (Exception e) {
      log.error("Error crating new student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @Override
  public Student saveStudent(Student student) {
    log.info("Saving new student {} to the database", student.getId());
    return studentRepo.save(student);
  }

  @Override
  public Student updateStudent(Long id, StudentCreateRequest request) throws IOException {
    try {
      log.info("Updating student with id{}", id);
      Optional<Student> studentData = studentRepo.findById(id);
      if (studentData.isEmpty()) throw new IOException("Could not fetch student data with id" + id);

      Student studentDb = studentData.get();
      if (request.getFirstName() != null) studentDb.setFirstName(request.getFirstName());
      if (request.getLastName() != null) studentDb.setLastName(request.getLastName());
      if (request.getDateOfBirth() != null) studentDb.setDateOfBirth(request.getDateOfBirth());

      return studentRepo.save(studentDb);
    } catch (Exception e) {
      log.error("Error updating student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public void deleteStudent(Long id) {
    try {
      log.info("Deleting student with id {}", id);
      studentRepo.deleteById(id);
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public List<Grades> getGradesForStudentForSubject(Long id, SubjectCreateRequest request) {
    try {
      Optional<Student> student = studentRepo.findById(id);
      if (student.isEmpty()) {
        throw new ClassCastException("Student is missing");
      }
      Optional<Subject> subject = subjectRepo.findByName(request.getName());
      if (subject.isEmpty()) {
        throw new CustomErrorException("Subject is missing");
      }
      return gradesRepo.findByStudentAndSubject(student.get(), subject.get());
    } catch (Exception e) {
      log.error("Error crating new student {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
