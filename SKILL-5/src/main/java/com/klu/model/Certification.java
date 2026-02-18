package com.klu.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Certification {

    @Value("102")
    private String id;

    @Value("Spring Framework")
    private String name;

    @Value("2026-01-20")
    private String dateOfCompletion;

    public void display() {
        System.out.println("Certification ID   : " + id);
        System.out.println("Certification Name : " + name);
        System.out.println("Date of Completion : " + dateOfCompletion);
    }
}