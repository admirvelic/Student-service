package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Professor;
import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.ProfessorCreateRequest;
import com.vella.stuedntservice.repository.ProfessorRepo;
import com.vella.stuedntservice.repository.SchoolClassRepo;
import com.vella.stuedntservice.repository.SubjectRepo;
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
public class ProfessorServiceImpl implements ProfessorService {
  private final SchoolClassRepo schoolClassRepo;
  private final SubjectRepo subjectRepo;
  private final ProfessorRepo professorRepo;

  public Professor getProfessorById(Long id) {
    try {
      log.info("Fetching professor by id {}", id);
      Optional<Professor> professor = professorRepo.findById(id);
      if (professor.isEmpty()) {
        throw new CustomErrorException("Professor is missing");
      }
      return professor.get();
    } catch (Exception e) {
      log.error("Error updating professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @Override
  public List<Professor> getAllProfessors() {
    try {
      log.info("Fetching all professors");
      return professorRepo.findAll();
    } catch (Exception e) {
      log.error("Error crating new professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @Override
  public Professor saveProfessor(ProfessorCreateRequest request) {
    try {
      if (request.getFirstName() == null)
        throw new IllegalArgumentException("Field 'first name' is missing.");
      else if (request.getLastName() == null)
        throw new IllegalArgumentException("Field 'last name' is missing");
      else if (request.getSubject() == null)
        throw new IllegalArgumentException("Field 'subject' is missing");
      else if (request.getClass() == null)
        throw new IllegalArgumentException("Field 'school class' is missing");

      Optional<Subject> subject = subjectRepo.findByName(request.getSubject());
      if (subject.isEmpty()) {
        throw new CustomErrorException("Subject not found");
      }
      Optional<SchoolClass> schoolClass = schoolClassRepo.findByName(request.getSchoolClass());
      if (schoolClass.isEmpty()) {
        throw new CustomErrorException("SchoolClass not found");
      }

      Professor professor = new Professor();
      professor.setFirstName(request.getFirstName());
      professor.setLastName(request.getLastName());
      professor.setSubject(subject.get());
      professor.setSchoolClass(schoolClass.get());
      log.info("Saving new professor {} to the database", professor.getId());
      return professorRepo.save(professor);
    } catch (Exception e) {
      log.error("Error crating new professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @Override
  public Professor saveProfessor(Professor professor) {
    log.info("Saving new professor {} to the database", professor.getId());
    return professorRepo.save(professor);
  }

  @Override
  public Professor updateProfessor(Long id, Professor professor) throws IOException {
    log.info("Updating professor with id{}", id);
    Optional<Professor> professorData = professorRepo.findById(id);
    if (professorData.isEmpty())
      throw new IOException("Could not fetch professor data with id" + id);

    Professor professorDb = professorData.get();
    if (professor.getId() != null) professorDb.setId(professor.getId());
    if (professor.getFirstName() != null) professorDb.setFirstName(professor.getFirstName());
    if (professor.getLastName() != null) professorDb.setLastName(professor.getLastName());
    if (professor.getSubject() != null) professorDb.setSubject(professor.getSubject());
    if (professor.getClass() != null) professorDb.setSchoolClass(professor.getSchoolClass());

    return professorRepo.save(professorDb);
  }

  @Override
  public Professor updateProfessor(Long id, ProfessorCreateRequest request) throws IOException {
    try {
      log.info("Updating professor with id{}", id);
      Optional<Professor> professorData = professorRepo.findById(id);
      if (professorData.isEmpty())
        throw new IOException("Could not fetch professor data with id" + id);
      Professor professorDb = professorData.get();
      if (request.getFirstName() != null) professorDb.setFirstName(request.getFirstName());
      if (request.getLastName() != null) professorDb.setLastName(request.getLastName());
      if (request.getSubject() != null) {
        Optional<Subject> subject = subjectRepo.findByName(request.getSubject());
        if (subject.isEmpty()) {
          throw new CustomErrorException("Subject is missing");
        }
        professorDb.setSubject(subject.get());
      }
      if (request.getClass() != null) {
        Optional<SchoolClass> schoolClass = schoolClassRepo.findByName(request.getSchoolClass());
        if (schoolClass.isEmpty()) {
          throw new CustomErrorException("SchoolClass is missing");
        }
        professorDb.setSchoolClass(schoolClass.get());
      }
      return professorRepo.save(professorDb);
    } catch (Exception e) {
      log.error("Error updating professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public void deleteProfessor(Long id) {
    try {
      log.info("Deleting professor with id {}", id);
      professorRepo.deleteById(id);
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
