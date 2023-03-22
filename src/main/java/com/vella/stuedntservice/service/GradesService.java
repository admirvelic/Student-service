package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.Grades;
import com.vella.stuedntservice.model.requests.GradesCreateRequest;
import java.io.IOException;
import java.util.List;

public interface GradesService {

  Grades getGradesById(Long id);

  List<Grades> getAllGrades();

  Grades saveGrades(Grades grades);

  Grades saveGrades(GradesCreateRequest request);

  Grades updateGrades(Long id, Grades grades) throws IOException;

  Grades updateGrades(Long id, GradesCreateRequest request) throws IOException;

  void deleteGrades(Long id);
}
