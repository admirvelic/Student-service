package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.model.requests.GradesCreateRequest;
import com.vella.stuedntservice.service.GradesService;
import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.Grades;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
@Slf4j
public class GradesController {
  private final GradesService gradesService;


  @GetMapping("/{id}")
  public Grades getGradesById(@PathVariable Long id) throws CustomErrorException {
    return gradesService.getGradesById(id);
  }

  @PutMapping("/{id}/update")
  public Grades updateGrades(@PathVariable Long id, @RequestBody GradesCreateRequest request) throws CustomErrorException, IOException {
      return gradesService.updateGrades(id, request);
  }

  @PostMapping("/save")
  public Grades saveGrades(@RequestBody GradesCreateRequest request) throws CustomErrorException {
      return gradesService.saveGrades(request);
  }

  @GetMapping("/all")
  public List<Grades> getAllGrades() {
    return gradesService.getAllGrades();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteGrades(@PathVariable Long id) throws CustomErrorException {
      gradesService.deleteGrades(id);
      return "Successfully deleted grades with id " + id;
  }
}
