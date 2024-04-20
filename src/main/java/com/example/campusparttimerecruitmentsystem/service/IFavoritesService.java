package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Favorites;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IFavoritesService extends IService<Favorites> {

    Response createLike(JobPostRequest request);

    IPage<JobPosts> findLike(JobPostRequest request,int pageNum, int pageSize);

    Response deleteById(Long id,JobPosts jobPosts);
}
