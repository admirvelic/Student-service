package com.vella.studentservice.service;

import com.vella.studentservice.model.Subject;
import com.vella.studentservice.model.requests.SubjectCreateRequest;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

  Subject getSubjectById(Long id);

  List<Subject> getAllSubjects();

  Subject saveSubject(SubjectCreateRequest request);

  Subject updateSubject(Long id, SubjectCreateRequest request) throws IOException;

  void deleteSubject(Long id);
}
