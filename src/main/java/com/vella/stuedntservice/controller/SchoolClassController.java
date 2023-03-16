package com.vella.stuedntservice.controller;

import com.vella.stuedntservice.exception.CustomErrorException;
import com.vella.stuedntservice.model.SchoolClass;
import com.vella.stuedntservice.model.requests.SchoolClassCreateRequest;
import com.vella.stuedntservice.repository.SchoolClassRepo;
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
    return schoolClassService.getSchoolClassById(id);
  }

  @PutMapping("/{id}/update")
  public SchoolClass updeteSchoolClass(
      @PathVariable Long id, @RequestBody SchoolClassCreateRequest request)
      throws CustomErrorException, IOException {
    return schoolClassService.updateSchoolClass(id, request);
  }

  @PostMapping("/save")
  public SchoolClass saveSchoolClass(@RequestBody SchoolClassCreateRequest request)
      throws CustomErrorException {
    return schoolClassService.saveSchoolClass(request);
  }

  @GetMapping("/all")
  public List<SchoolClass> getAllSchoolClasses() {
    return schoolClassService.getAllSchoolClasses();
  }

  @DeleteMapping("/{id}/delete")
  public String deleteSchoolClass(@PathVariable Long id) throws CustomErrorException {
    schoolClassService.deleteSchoolClass(id);
    return "Successfully deleted school class with id " + id;
  }
}
