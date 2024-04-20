package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Applications;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.ApplicationResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.sun.glass.ui.Application;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IApplicationsService extends IService<Applications> {

    Response create(JobPosts jobPosts,int resumesId);

    IPage<ApplicationResponse> findApplications(
            int pageNum,
            int pageSize,
            String realName,
            String title,
            String gender,
            LocalDateTime applyTimeStart,
            LocalDateTime applyTimeEnd,
            LocalDate postDateStart,
            LocalDate postDateEnd
    );

    IPage<ApplicationResponse> findApplicationsStudent(int pageNum, int pageSize, String title, LocalDateTime applyTimeStart, LocalDateTime applyTimeEnd, LocalDate postDateStart, LocalDate postDateEnd);

    Response updateResume(Applications applications, int resumesId);

    Response updateStatus(Applications applications);
}
