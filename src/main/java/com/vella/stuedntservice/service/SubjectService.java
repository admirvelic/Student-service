package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.SubjectCreateRequest;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

  Subject getSubjectById(Long id);

  List<Subject> getAllSubjects();

  Subject saveSubject(SubjectCreateRequest request);

  Subject updateSubject(Long id, SubjectCreateRequest request) throws IOException;

  void deleteSubject(Long id);
}
