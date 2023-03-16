package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.SchoolClass;import com.vella.stuedntservice.model.requests.SchoolClassCreateRequest;

import java.io.IOException;
import java.util.List;

public interface SchoolClassService {

  SchoolClass getSchoolClassById(Long id);

  List<SchoolClass> getAllSchoolClasses();

  SchoolClass saveSchoolClass(SchoolClass schoolClass);

  SchoolClass updateSchoolClass(Long id, SchoolClass schoolClass) throws IOException;
  SchoolClass updateSchoolClass(Long id, SchoolClassCreateRequest request) throws IOException;

  void deleteSchoolClass(Long id);
}
