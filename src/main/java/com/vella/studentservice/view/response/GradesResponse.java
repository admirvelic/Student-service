package com.vella.studentservice.view.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class GradesResponse implements Serializable {

  private Map grades;
}
