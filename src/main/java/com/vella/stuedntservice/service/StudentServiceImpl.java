package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Grades;
import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.Student;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.StudentCreateRequest;
import com.vella.stuedntservice.model.requests.SubjectCreateRequest;
import com.vella.stuedntservice.repository.GradesRepo;
import com.vella.stuedntservice.repository.SchoolClassRepo;
import com.vella.stuedntservice.repository.StudentRepo;
import com.vella.stuedntservice.repository.SubjectRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    log.info("Fetching student by id {}", id);
    Optional<Student> student = studentRepo.findById(id);
    if (student.isEmpty()){
      throw new CustomErrorException("Student is missing");
    }
    return student.get();
  }

  @Override
  public List<Student> getAllStudents() {
    log.info("Fetching all students");
    return studentRepo.findAll();
  }

  @Override
  public Student saveStudent(StudentCreateRequest request) {
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
  }

  @Override
  public Student saveStudent(Student student) {
    log.info("Saving new student {} to the database", student.getId());
    return studentRepo.save(student);
  }

  @Override
  public Student updateStudent(Long id, Student student) throws IOException {
    log.info("Updating student with id{}", id);
    Optional<Student> studentData = studentRepo.findById(id);
    if (studentData.isEmpty()) throw new IOException("Could not fetch student data with id" + id);

    Student studentDb = studentData.get();
    if (student.getId() != null) studentDb.setId(student.getId());
    if (student.getFirstName() != null) studentDb.setFirstName(student.getFirstName());
    if (student.getLastName() != null) studentDb.setLastName(student.getLastName());
    if (student.getDateOfBirth() != null) studentDb.setDateOfBirth(student.getDateOfBirth());

    return studentRepo.save(studentDb);
  }

  @Override
  public void deleteStudent(Long id) {
    log.info("Deleting student with id {}", id);
    studentRepo.deleteById(id);
  }

  @Override
  public List<Grades> getGradesForStudentForSubject(Long id, SubjectCreateRequest request) {
    Optional<Student> student = studentRepo.findById(id);
    if (student.isEmpty()) {
      throw new ClassCastException("Student is missing");
    }
    Optional<Subject> subject = subjectRepo.findByName(request.getName());
    if (subject.isEmpty()) {
      throw new CustomErrorException("Subject is missing");
    }
    return gradesRepo.findByStudentAndSubject(student.get(), subject.get());
  }
}
