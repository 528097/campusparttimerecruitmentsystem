package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IResumesService extends IService<Resumes> {

    ResumesResponse create(Resumes resumes);

    IPage<Resumes> findAll(ResumesRequest request, int pageNum, int pageSize);

    Response deleteById(Long id);

    Response updateResume(Long id, ResumesRequest request);
}
