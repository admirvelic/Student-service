package com.vella.studentservice.service;

import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.SchoolClass;
import com.vella.studentservice.model.requests.SchoolClassCreateRequest;
import com.vella.studentservice.repository.SchoolClassRepo;
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
public class SchoolClassServiceImpl implements SchoolClassService {
  private final SchoolClassRepo schoolClassRepo;

  public SchoolClass getSchoolClassById(Long id) {
    try {
      log.info("Fetching school class by id {}", id);
      Optional<SchoolClass> schoolClass = schoolClassRepo.findById(id);
      if (schoolClass.isEmpty()) {
        throw new CustomErrorException("SchoolClass is missing");
      }
      return schoolClass.get();
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @Override
  public List<SchoolClass> getAllSchoolClasses() {
    try {
      log.info("Fetching all school classes");
      return schoolClassRepo.findAll();
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public SchoolClass saveSchoolClass(SchoolClassCreateRequest request) {
    try {
      SchoolClass schoolClass = new SchoolClass();
      schoolClass.setName(request.getName());
      log.info("Saving new school class to the database");
      return schoolClassRepo.save(schoolClass);
    } catch (Exception e) {
      log.error("Error crating new school class {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @Override
  public SchoolClass updateSchoolClass(Long id, SchoolClass schoolClass) throws IOException {
    log.info("Updating school class with id{}", id);
    Optional<SchoolClass> schoolClassData = schoolClassRepo.findById(id);
    if (schoolClassData.isEmpty())
      throw new IOException("Could not fetch professor data with id" + id);

    SchoolClass schoolClassDb = schoolClassData.get();
    if (schoolClass.getId() != null) schoolClassDb.setId(schoolClass.getId());
    if (schoolClass.getName() != null) schoolClassDb.setName(schoolClass.getName());

    return schoolClassRepo.save(schoolClassDb);
  }

  @Override
  public SchoolClass updateSchoolClass(Long id, SchoolClassCreateRequest request)
      throws IOException {
    try {
      log.info("Updating school class with id{}", id);
      Optional<SchoolClass> schoolClassData = schoolClassRepo.findById(id);
      if (schoolClassData.isEmpty())
        throw new IOException("Could not fetch professor data with id" + id);

      SchoolClass schoolClassDb = schoolClassData.get();
      if (request.getName() != null) schoolClassDb.setName(request.getName());

      return schoolClassRepo.save(schoolClassDb);
    } catch (Exception e) {
      log.error("Error updating school class {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  public void deleteSchoolClass(Long id) {
    try {
      log.info("Deleting school class with id {}", id);
      schoolClassRepo.deleteById(id);
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
