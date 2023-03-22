package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.SubjectCreateRequest;
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
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepo subjectRepo;

  public Subject getSubjectById(Long id) {
    try {
      log.info("Fetching subject by id {}", id);
      Optional<Subject> subject = subjectRepo.findById(id);
      if (subject.isEmpty()) {
        throw new CustomErrorException("Subject is missing");
      }
      return subject.get();
    } catch (Exception e) {
      log.error("Error fetching subject {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @Override
  public List<Subject> getAllSubjects() {
    try {
      log.info("Fetching all subjects");
      return subjectRepo.findAll();
    } catch (Exception e) {
      log.error("Error fetching all subjects {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public Subject saveSubject(SubjectCreateRequest request) {
    if (request.getName().isEmpty()) {
      throw new CustomErrorException("Field name is missing");
    }
    Subject subject = new Subject();
    subject.setName(request.getName());
    log.info("Saving new subject {} to the database", subject.getId());
    return subjectRepo.save(subject);
  }

  @Override
  public Subject updateSubject(Long id, SubjectCreateRequest request) throws IOException {
    try {
      log.info("Updating subject with id{}", id);
      Optional<Subject> subjectData = subjectRepo.findById(id);
      if (subjectData.isEmpty()) throw new IOException("Could not fetch subject data with id" + id);

      Subject subjectDb = subjectData.get();
      if (request.getName() != null) subjectDb.setName(request.getName());

      return subjectRepo.save(subjectDb);
    } catch (Exception e) {
      log.error("Error updating subject {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public void deleteSubject(Long id) {
    try {
      log.info("Deleting subject with id {}", id);
      subjectRepo.deleteById(id);
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
