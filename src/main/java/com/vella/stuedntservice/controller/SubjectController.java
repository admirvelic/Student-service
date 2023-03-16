package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.SubjectCreateRequest;
import com.vella.stuedntservice.repository.SubjectRepo;
import com.vella.stuedntservice.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {
  private final SubjectService subjectService;

  @GetMapping("/{id}")
  public Subject getSubjectId(@PathVariable Long id) throws CustomErrorException {
    Subject subjectData = subjectService.getSubjectById(id);
    if (subjectData == null)
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Subject not found");

    return subjectData;
  }

  @PutMapping("/{id}/update")
  public Subject updeteSubject(@PathVariable Long id, @RequestBody Subject subject)
      throws CustomErrorException {
    Subject subjectData = subjectService.getSubjectById(id);
    if (subject == null) throw new CustomErrorException(HttpStatus.NOT_FOUND, "Subject not found");

    Subject subjectDb = subjectService.getSubjectById(id);

    try {
      return subjectService.updateSubject(id, subjectDb);
    } catch (Exception e) {
      log.error("Error updating subject {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/save")
  public Subject saveSubject(@RequestBody SubjectCreateRequest request) throws CustomErrorException {

    try {
      if (request.getName() == null)
        throw new IOException("Field 'name' is missing.");

      Subject subject = new Subject();
      subject.setName(request.getName());

      return subjectService.saveSubject(subject);
    } catch (Exception e) {
      log.error("Error crating new subject {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/all")
  public List<Subject> getAllSubjects() {
    return subjectService.getAllSubjects();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteSubject(@PathVariable Long id) throws CustomErrorException {
    try {
      subjectService.deleteSubject(id);

      return "Successfully deleted subject with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
