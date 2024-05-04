package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.service.IJobPostsService;
import com.example.campusparttimerecruitmentsystem.service.IResumesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/job-posts")
public class JobPostsController {
    @Autowired
    private IJobPostsService jobPostsService;

    @RequestMapping("/create")
    public Response createJobPost(@RequestBody JobPosts jobPosts) {
        return jobPostsService.create(jobPosts);
    }
    @RequestMapping("/page")
    public IPage<JobPosts> jobPage(
            @RequestBody JobPostRequest request) {
        Integer pageSize = request.getPageSize();
        Integer pageNum = request.getPageNum();
        IPage<JobPosts> page = jobPostsService.findAll(request, pageNum, pageSize);
        return page;
    }
    @RequestMapping("/delete/{id}")
    public Response deleteJobPost(@PathVariable Long id,@RequestBody JobPostRequest request) {
        return jobPostsService.deleteById(id,request);
    }
    @RequestMapping("/update/{id}")
    public Response updateJobPost(@PathVariable Long id,@RequestBody JobPostRequest request) {
        return jobPostsService.updatePosts(id,request);
    }
}
