package com.klu.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component   
public class Student {

    private int studentId;
    private String name;
    private String course;
    private int year;

    public Student(@Value("101") int studentId,
                   @Value("HERALD") String name,
                   @Value("FSAD") String course,
                   @Value("2") int year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    public void display() {
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Course     : " + course);
        System.out.println("Year       : " + year);
    }
}