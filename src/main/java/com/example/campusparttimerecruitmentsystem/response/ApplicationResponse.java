package com.example.campusparttimerecruitmentsystem.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Data
public class ApplicationResponse {

    private Integer applicationId;

    private Integer jobPostId;

    private Integer studentId;

    private LocalDateTime applyTime;

    private String status;

    private Integer resumesId;

    private String interviewRecords;

    private String result;
    //通过jobPostId连表查询得到的岗位信息
    private String title;

    private String description;

    private String salaryRange;

    private String workLocation;

    private String workHours;

    private String requirements;

    private LocalDate postDate;

    private String companyId;

    private String companyName;
    //通过studentId连表查询得到的应聘者信息
    private String realName;

    private String gender;

    private String phone;

    private String college;

    private String major;

    private String education;

    private Year graduateYear;
    //通过resumesId连表查询得到的简历信息
    /**
     * 工作/实习经验
     */
    private String experience;

    /**
     * 技能
     */
    private String skills;

    /**
     * 工作意向
     */
    private String objectives;


}
