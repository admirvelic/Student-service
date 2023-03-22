package com.vella.studentservice.view.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class ProfessorResponse implements Serializable {

    private Map professor;
}
