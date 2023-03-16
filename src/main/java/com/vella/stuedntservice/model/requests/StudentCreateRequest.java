package com.vella.stuedntservice.model.requests;

import com.vella.stuedntservice.model.SchoolClass;import jakarta.persistence.JoinColumn;import jakarta.persistence.ManyToOne;import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String schoolClass;
}