package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Professor;
import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.Subject;
import com.vella.stuedntservice.model.requests.ProfessorCreateRequest;
import com.vella.stuedntservice.repository.ProfessorRepo;
import com.vella.stuedntservice.repository.SchoolClassRepo;
import com.vella.stuedntservice.repository.SubjectRepo;
import com.vella.stuedntservice.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
  public Professor updeteProfessor(@PathVariable Long id, @RequestBody ProfessorCreateRequest request) throws CustomErrorException, IOException {
      return professorService.updateProfessor(id, request);
  }

  @PostMapping("/save")
  public Professor saveProfessor(@RequestBody ProfessorCreateRequest request) throws CustomErrorException {
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
