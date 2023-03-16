package com.vella.stuedntservice.service;

import com.vella.stuedntservice.controller.GradesController;import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Grades;
import com.vella.stuedntservice.model.Student;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.GradesCreateRequest;
import com.vella.stuedntservice.repository.GradesRepo;
import com.vella.stuedntservice.repository.StudentRepo;
import com.vella.stuedntservice.repository.SubjectRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GradesServiceImpl implements GradesService {
  private final SubjectRepo subjectRepo;
  private final StudentRepo studentRepo;

  private final GradesRepo gradesRepo;

  @Override
  public Grades getGradesById(Long id) {
    log.info("Fetching grades by id {}", id);
    Optional<Grades> grades = gradesRepo.findById(id);
    if (grades.isEmpty()) throw new CustomErrorException("Could not find grades with id {}" + id);
    return grades.get();
  }

  @Override
  public List<Grades> getAllGrades() {
    log.info("Fetching all grades");
    return gradesRepo.findAll();
  }

  @Override
  public Grades saveGrades(Grades grades) {
    log.info("Saving new grades {} to the database", grades.getId());
    return gradesRepo.save(grades);
  }

  @Override
  public Grades saveGrades(GradesCreateRequest request) {
    Grades grades = new Grades();
    grades.setGrade(request.getGrade());
    Long studentId = request.getStudent();
    Optional<Student> student = studentRepo.findById(studentId);
    if (student.isEmpty()){
      throw new CustomErrorException("Student is missing");
    }
    grades.setStudent(student.get());
    Optional<Subject> subject = subjectRepo.findByName(request.getSubject());
    if (subject.isEmpty()){
      throw new CustomErrorException("Subject is missing");
    }
    grades.setSubject(subject.get());
    log.info("Saving new grades {} to the database", grades.getId());
    return gradesRepo.save(grades);
  }

  @Override
  public Grades updateGrades(Long id, Grades grades) {
    log.info("Updating grades with id {}", id);
    Optional<Grades> gradesData = gradesRepo.findById(id);
    if (gradesData.isEmpty()) throw new RuntimeException("Could not find grades data with id" + id);

    Grades gradesDb = gradesData.get();
    if (grades.getId() != null) gradesDb.setId(grades.getId());
    if (grades.getGrade() != null) gradesDb.setGrade(grades.getGrade());
    if (grades.getStudent() != null) gradesDb.setStudent(grades.getStudent());
    if (grades.getSubject() != null) gradesDb.setSubject(grades.getSubject());

    return gradesRepo.save(gradesDb);
  }

  @Override
  public Grades updateGrades(Long id, GradesCreateRequest request) {
    log.info("Updating grades with id {}", id);
    Optional<Grades> grades = gradesRepo.findById(id);
    if (grades.isEmpty()) {
      throw new IllegalArgumentException("Could not find grades with id" + id);
    }
    if (request.getGrade() != null) grades.get().setGrade(request.getGrade());
    if (request.getStudent() != null) {
      Optional<Student> student = studentRepo.findById(request.getStudent());
      if (student.isEmpty()) {
        throw new IllegalArgumentException("Could not find student");
      }
      grades.get().setStudent(student.get());
    }
    if (request.getSubject() != null) {
      Optional<Subject> subject = subjectRepo.findByName(request.getSubject());
      if (subject.isEmpty()) {
        throw new IllegalArgumentException("Could not find subject");
      }
      grades.get().setSubject(subject.get());
    }

    return gradesRepo.save(grades.get());
  }

  @Override
  public void deleteGrades(Long id) {
    log.info("Deleting grades with id {}", id);
    Optional<Grades> grades = gradesRepo.findById(id);
    if (grades.isEmpty()) {
      throw new IllegalArgumentException("Could not find grades");
    }
    gradesRepo.deleteById(id);
  }
}
