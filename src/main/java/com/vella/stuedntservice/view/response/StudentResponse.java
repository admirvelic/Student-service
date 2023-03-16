package com.vella.stuedntservice.view.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class StudentResponse implements Serializable {

    private Map student;
}
