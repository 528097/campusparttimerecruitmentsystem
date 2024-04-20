package com.example.campusparttimerecruitmentsystem.response;

import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import lombok.Data;

@Data
public class ResumesResponse {
    private boolean success;
    private Resumes resumes;
    private String message;

    public ResumesResponse(boolean success, Resumes resumes, String message) {
        this.success = success;
        this.resumes = resumes;
        this.message = message;
    }
}
