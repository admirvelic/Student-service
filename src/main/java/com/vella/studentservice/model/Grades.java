package com.vella.studentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Grades {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer grade;

  @ManyToOne
  @JoinColumn(name = "studentId")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "schoolClassId")
  private Subject subject;
}
