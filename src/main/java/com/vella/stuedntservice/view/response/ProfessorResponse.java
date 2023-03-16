package com.vella.stuedntservice.view.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class ProfessorResponse implements Serializable {

    private Map professor;
}
