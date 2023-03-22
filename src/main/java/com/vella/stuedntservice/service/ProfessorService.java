package com.vella.stuedntservice.service;

import com.vella.stuedntservice.model.Professor;
import com.vella.stuedntservice.model.requests.ProfessorCreateRequest;
import java.io.IOException;
import java.util.List;

public interface ProfessorService {

  Professor getProfessorById(Long id);

  List<Professor> getAllProfessors();

  Professor saveProfessor(Professor professor);

  Professor saveProfessor(ProfessorCreateRequest request);

  Professor updateProfessor(Long id, Professor professor) throws IOException;

  Professor updateProfessor(Long id, ProfessorCreateRequest request) throws IOException;

  void deleteProfessor(Long id);
}
