package com.vella.studentservice.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradesCreateRequest {
    private Integer grade;
    private Long student;
    private String subject;
}
