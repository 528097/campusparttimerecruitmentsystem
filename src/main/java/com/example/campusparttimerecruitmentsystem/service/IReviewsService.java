package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Reviews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.request.CommentRequest;
import com.example.campusparttimerecruitmentsystem.request.ReviewRequest;
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
public interface IReviewsService extends IService<Reviews> {

    Response create(Long id, CommentRequest request);

    Response delete(Long id);

    IPage<Reviews> findReviews(ReviewRequest request, int pageNum, int pageSize);
}
