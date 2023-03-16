package com.vella.stuedntservice.model.requests;

import com.vella.stuedntservice.model.Student;
import com.vella.stuedntservice.model.Subject;
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
