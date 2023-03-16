package com.vella.stuedntservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StudentServiceApplicationMain {

  public static void main(String[] args) {
    SpringApplication.run(StudentServiceApplicationMain.class, args);
  }
}