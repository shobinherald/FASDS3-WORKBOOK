package com.klu.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import com.klu.model.Student;
import com.klu.exception.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    Map<Integer, Student> students = new HashMap<>();

    public StudentController() {
        students.put(1, new Student(1, "Bharath", "CSE"));
        students.put(2, new Student(2, "Shobin", "ECE"));
        students.put(3, new Student(3, "harsha", "IT"));
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {

        if(id <= 0) {
            throw new InvalidInputException("Student ID must be positive");
        }

        Student student = students.get(id);

        if(student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }

        return student;
    }
}