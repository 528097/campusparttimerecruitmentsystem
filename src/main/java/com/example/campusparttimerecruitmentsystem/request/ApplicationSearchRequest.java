package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ApplicationSearchRequest extends PageRequest{

    private String title;

    private String realName;

    private String gender;

    private String status;

    private String result;

    private String interviewRecords;

    private LocalDateTime applyTimeStart;

    private LocalDateTime applyTimeEnd;

    private LocalDate postDateStart;

    private LocalDate postDateEnd;

}
