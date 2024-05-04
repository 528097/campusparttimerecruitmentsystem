package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewRequest extends PageRequest{

    private Integer jobPostId;

    private Integer rating;

    private String comment;

    private LocalDateTime reviewTime;
}
