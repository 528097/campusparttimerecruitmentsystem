package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

@Data
public class CommentRequest extends PageRequest{
    private String comment;
    private Integer rating;

}
