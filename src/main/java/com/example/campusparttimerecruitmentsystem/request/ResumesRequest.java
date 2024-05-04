package com.example.campusparttimerecruitmentsystem.request;

import lombok.Data;

@Data
public class ResumesRequest extends PageRequest{
    private Integer resumeId;
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