package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class JobPostRequest extends PageRequest{

    private Integer jobPostId;

    private Integer companyId;

    private String title;

    private String description;

    private String salaryRange;

    private String workLocation;

    private String workHours;

    private String requirements;

    private LocalDate postDate;

    private String companyName;
}
