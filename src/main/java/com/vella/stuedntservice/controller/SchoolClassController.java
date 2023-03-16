package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.requests.SchoolClassCreateRequest;import com.vella.stuedntservice.repository.SchoolClassRepo;
import com.vella.stuedntservice.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schoolClasses")
@RequiredArgsConstructor
@Slf4j
public class SchoolClassController {
  private final SchoolClassService schoolClassService;

  @GetMapping("/{id}")
  public SchoolClass getSchoolClassById(@PathVariable Long id) throws CustomErrorException {
    SchoolClass schoolClassData = schoolClassService.getSchoolClassById(id);
    if (schoolClassData == null)
      throw new CustomErrorException(HttpStatus.NOT_FOUND, "School class not found");

    return schoolClassData;
  }

  @PutMapping("/{id}/update")
  public SchoolClass updeteSchoolClass(@PathVariable Long id, @RequestBody SchoolClassCreateRequest request) throws CustomErrorException {
    if (request.getName() == null) throw new CustomErrorException("Field 'name' is missing.");
    try {
      return schoolClassService.updateSchoolClass(id, request);
    } catch (Exception e) {
      log.error("Error updating school class {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/save")
  public SchoolClass saveSchoolClass(@RequestBody SchoolClassCreateRequest request)
      throws CustomErrorException {

    try {
      if (request.getName() == null) throw new IOException("Field 'name' is missing.");

      SchoolClass schoolClass = new SchoolClass();
      schoolClass.setName(request.getName());

      return schoolClassService.saveSchoolClass(schoolClass);
    } catch (Exception e) {
      log.error("Error crating new school class {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/all")
  public List<SchoolClass> getAllSchoolClasses() {
    return schoolClassService.getAllSchoolClasses();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteSchoolClass(@PathVariable Long id) throws CustomErrorException {
    try {
      schoolClassService.deleteSchoolClass(id);

      return "Successfully deleted school class with id " + id;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
