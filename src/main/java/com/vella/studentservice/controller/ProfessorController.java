package com.vella.studentservice.controller;

import com.vella.studentservice.exception.CustomErrorException;
import com.vella.studentservice.model.Professor;
import com.vella.studentservice.model.requests.ProfessorCreateRequest;
import com.vella.studentservice.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/professors")
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {
  private final ProfessorService professorService;

  @GetMapping("/{id}")
  public Professor getProfessorById(@PathVariable Long id) throws CustomErrorException {
    return professorService.getProfessorById(id);
  }

  @PutMapping("/{id}/update")
  public Professor updeteProfessor(
      @PathVariable Long id, @RequestBody ProfessorCreateRequest request)
      throws CustomErrorException, IOException {
    return professorService.updateProfessor(id, request);
  }

  @PostMapping("/save")
  public Professor saveProfessor(@RequestBody ProfessorCreateRequest request)
      throws CustomErrorException {
    return professorService.saveProfessor(request);
  }

  @GetMapping("/all")
  public List<Professor> getAllProfessors() {
    return professorService.getAllProfessors();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteProfessor(@PathVariable Long id) throws CustomErrorException {
    professorService.deleteProfessor(id);
    return "Successfully deleted professor with id " + id;
  }
}
