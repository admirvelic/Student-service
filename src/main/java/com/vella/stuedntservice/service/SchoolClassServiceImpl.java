package com.vella.stuedntservice.service;

import com.vella.stuedntservice.exception.CustomErrorException;import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.requests.SchoolClassCreateRequest;import com.vella.stuedntservice.repository.SchoolClassRepo;
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
public class SchoolClassServiceImpl implements SchoolClassService {
  private final SchoolClassRepo schoolClassRepo;

  public SchoolClass getSchoolClassById(Long id) {
    log.info("Fetching school class by id {}", id);
    Optional<SchoolClass> schoolClass = schoolClassRepo.findById(id);
    if (schoolClass.isEmpty()){
      throw new CustomErrorException("SchoolClass is missing");
    }
    return schoolClass.get();
  }

  @Override
  public List<SchoolClass> getAllSchoolClasses() {
    log.info("Fetching all school classes");
    return schoolClassRepo.findAll();
  }

  @Override
  public SchoolClass saveSchoolClass(SchoolClass schoolClass) {
    log.info("Saving new school class to the database");
    return schoolClassRepo.save(schoolClass);
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
  public SchoolClass updateSchoolClass(Long id, SchoolClassCreateRequest request) throws IOException {



    log.info("Updating school class with id{}", id);
    Optional<SchoolClass> schoolClassData = schoolClassRepo.findById(id);
    if (schoolClassData.isEmpty())
      throw new IOException("Could not fetch professor data with id" + id);

    SchoolClass schoolClassDb = schoolClassData.get();
    if (request.getName() != null) schoolClassDb.setName(request.getName());

    return schoolClassRepo.save(schoolClassDb);
  }

  @Override
  public void deleteSchoolClass(Long id) {
    log.info("Deleting school class with id {}", id);
    schoolClassRepo.deleteById(id);
  }
}
