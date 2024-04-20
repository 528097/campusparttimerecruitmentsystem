package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Applications;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.ApplicationResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.service.IApplicationsService;
import com.sun.glass.ui.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/applications")
public class ApplicationsController {
    @Autowired
    private IApplicationsService applicationsService;

    @RequestMapping("/create")
    public Response create(@RequestBody JobPosts jobPosts, @RequestParam int resumesId) {
        return applicationsService.create(jobPosts,resumesId);
    }

    @RequestMapping("/page")
    public IPage<ApplicationResponse> applicationsPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applyTimeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applyTimeEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate postDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate postDateEnd
    ) {
        IPage<ApplicationResponse> page = applicationsService.findApplications(
                pageNum,
                pageSize,
                realName,
                title,
                gender,
                applyTimeStart,
                applyTimeEnd,
                postDateStart,
                postDateEnd
        );
        return page;
    }
    @RequestMapping("/page/student")
    public IPage<ApplicationResponse> applicationsPageStudent(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applyTimeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applyTimeEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate postDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate postDateEnd
    ) {
        IPage<ApplicationResponse> page = applicationsService.findApplicationsStudent(
                pageNum,
                pageSize,
                title,
                applyTimeStart,
                applyTimeEnd,
                postDateStart,
                postDateEnd
        );
        return page;
    }

    @RequestMapping("/update/resume")
    public Response updateResume(@RequestBody Applications applications, @RequestParam int resumesId) {
        return applicationsService.updateResume(applications,resumesId);
    }
    @RequestMapping("/update/status")
    public Response updateStatus(@RequestBody Applications applications) {
        return applicationsService.updateStatus(applications);
    }


}
