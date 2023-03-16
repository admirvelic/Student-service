package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.Subject;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

  Subject getSubjectById(Long id);

  List<Subject> getAllSubjects();

  Subject saveSubject(Subject subject);

  Subject updateSubject(Long id, Subject subject) throws IOException;

  void deleteSubject(Long id);
}
