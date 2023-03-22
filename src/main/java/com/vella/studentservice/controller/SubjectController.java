package com.vella.studentservice.controller;

import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.Subject;
import com.vella.studentservice.model.requests.SubjectCreateRequest;
import com.vella.studentservice.service.SubjectService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {
  private final SubjectService subjectService;

  @GetMapping("/{id}")
  public Subject getSubjectId(@PathVariable Long id) throws CustomErrorException {
    return subjectService.getSubjectById(id);
  }

  @PutMapping("/{id}/update")
  public Subject updeteSubject(@PathVariable Long id, @RequestBody SubjectCreateRequest request)
      throws CustomErrorException, IOException {
    return subjectService.updateSubject(id, request);
  }

  @PostMapping("/save")
  public Subject saveSubject(@RequestBody SubjectCreateRequest request)
      throws CustomErrorException {
    return subjectService.saveSubject(request);
  }

  @GetMapping("/all")
  public List<Subject> getAllSubjects() {
    return subjectService.getAllSubjects();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteSubject(@PathVariable Long id) throws CustomErrorException {
    subjectService.deleteSubject(id);
    return "Successfully deleted subject with id " + id;
  }
}
