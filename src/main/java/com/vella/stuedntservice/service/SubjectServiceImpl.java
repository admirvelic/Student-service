package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;import com.vella.stuedntservice.model.Subject;
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
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepo subjectRepo;

  public Subject getSubjectById(Long id) {
    log.info("Fetching subject by id {}", id);
    Optional<Subject> subject = subjectRepo.findById(id);
    if(subject.isEmpty()){
      throw new CustomErrorException("Subject is missing");
    }
    return subject.get();
  }

  @Override
  public List<Subject> getAllSubjects() {
    log.info("Fetching all subjects");
    return subjectRepo.findAll();
  }

  @Override
  public Subject saveSubject(Subject subject) {
    log.info("Saving new subject {} to the database", subject.getId());
    return subjectRepo.save(subject);
  }

  @Override
  public Subject updateSubject(Long id, Subject subject) throws IOException {
    log.info("Updating subject with id{}", id);
    Optional<Subject> subjectData = subjectRepo.findById(id);
    if (subjectData.isEmpty()) throw new IOException("Could not fetch subject data with id" + id);

    Subject subjectDb = subjectData.get();
    if (subject.getId() != null) subjectDb.setId(subject.getId());
    if (subject.getName() != null) subjectDb.setName(subject.getName());

    return subjectRepo.save(subjectDb);
  }

  @Override
  public void deleteSubject(Long id) {
    log.info("Deleting subject with id {}", id);
    subjectRepo.deleteById(id);
  }
}
