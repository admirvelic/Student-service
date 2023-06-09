package com.vella.studentservice.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorCreateRequest {
    private String firstName;
    private String lastName;
    private String subject;
    private String schoolClass;
}
