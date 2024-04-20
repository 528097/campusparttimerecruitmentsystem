package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Reviews;
import com.example.campusparttimerecruitmentsystem.request.CommentRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.service.IReviewsService;
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
@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    @Autowired
    private IReviewsService reviewsService;
    @RequestMapping("/create/{id}")
    public Response create(@PathVariable Long id, @RequestBody CommentRequest request) {
        return reviewsService.create(id,request);
    }
    @RequestMapping("/delete/{id}")
    public Response create(@PathVariable Long id) {
        return reviewsService.delete(id);
    }
    @RequestMapping("/page")
    public IPage<Reviews> reviewPage(
            @RequestParam Integer rating,
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        IPage<Reviews> page = reviewsService.findReviews(id, rating, pageNum, pageSize);
        return page;
    }

}
