package com.example.campusparttimerecruitmentsystem.response;

import com.example.campusparttimerecruitmentsystem.entity.Students;
import lombok.Data;

@Data
public class StudentResponse {
    private boolean success;
    private Students students;
    private String message;

    public StudentResponse(boolean success, Students students, String message) {
        this.success = success;
        this.students = students;
        this.message = message;
    }
}
