package com.vella.studentservice.service;

import com.vella.studentservice.model.SchoolClass;
import com.vella.studentservice.model.requests.SchoolClassCreateRequest;
import java.io.IOException;
import java.util.List;

public interface SchoolClassService {

  SchoolClass getSchoolClassById(Long id);

  List<SchoolClass> getAllSchoolClasses();

  SchoolClass saveSchoolClass(SchoolClassCreateRequest request);

  SchoolClass updateSchoolClass(Long id, SchoolClass schoolClass) throws IOException;

  SchoolClass updateSchoolClass(Long id, SchoolClassCreateRequest request) throws IOException;

  void deleteSchoolClass(Long id);
}
