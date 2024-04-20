package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IJobPostsService extends IService<JobPosts> {

    Response create(JobPosts jobPosts);

    IPage<JobPosts> findAll(JobPostRequest request, int pageNum, int pageSize);

    Response deleteById(Long id, JobPostRequest request);

    Response updatePosts(Long id, JobPostRequest request);
}
