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
    try{
    return gradesService.getGradesById(id);
    }catch (Exception e){
      log.error("Error fetching grade");
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Grade not found");
    }
  }

  @PutMapping("/{id}/update")
  public Grades updateGrades(@PathVariable Long id, @RequestBody GradesCreateRequest request)
      throws CustomErrorException {
    Grades gradesData = gradesService.getGradesById(id);
    if (gradesData == null)
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "Grades not found");
    try {
      return gradesService.updateGrades(id, request);
    } catch (Exception e) {
      log.error("Error updating grades {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/save")
  public Grades saveGrades(@RequestBody GradesCreateRequest request) throws CustomErrorException {

    try {
      if (request.getGrade() == null) throw new IOException("Field 'grade' is missing.");
      else if (request.getStudent() == null) throw new IOException("Field 'student' is missing");
      else if (request.getSubject() == null) throw new IOException("Field 'subject' is missing");

      return gradesService.saveGrades(request);
    } catch (Exception e) {
      log.error("Error crating new grades {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/all")
  public List<Grades> getAllGrades() {
    return gradesService.getAllGrades();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteGrades(@PathVariable Long id) throws CustomErrorException {
    try {
      gradesService.deleteGrades(id);

      return "Successfully deleted grades with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
