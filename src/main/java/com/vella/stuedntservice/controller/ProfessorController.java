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
    Professor professorData = professorService.getProfessorById(id);
    if (professorData == null) {
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Professor not found");
    }
    return professorData;
  }

  @PutMapping("/{id}/update")
  public Professor updeteProfessor(@PathVariable Long id, @RequestBody ProfessorCreateRequest request) throws CustomErrorException {
    try {
      return professorService.updateProfessor(id, request);
    } catch (Exception e) {
      log.error("Error updating professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/save")
  public Professor saveProfessor(@RequestBody ProfessorCreateRequest request) throws CustomErrorException {
    try {
      return professorService.saveProfessor(request);
    } catch (Exception e) {
      log.error("Error crating new professor {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/all")
  public List<Professor> getAllProfessors() {
    return professorService.getAllProfessors();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteProfessor(@PathVariable Long id) throws CustomErrorException {
    try {
      professorService.deleteProfessor(id);
      return "Successfully deleted professor with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
