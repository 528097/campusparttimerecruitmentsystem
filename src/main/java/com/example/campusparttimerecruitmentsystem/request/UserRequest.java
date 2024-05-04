package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest extends PageRequest{

    private String userName;

    private String email;

    private String password;

    /**
     * 用户角色（例如，admin, student, company）
     */
    private String role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;
}
