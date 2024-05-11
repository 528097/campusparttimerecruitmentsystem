package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Reviews;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.JobPostsMapper;
import com.example.campusparttimerecruitmentsystem.mapper.ReviewsMapper;
import com.example.campusparttimerecruitmentsystem.request.CommentRequest;
import com.example.campusparttimerecruitmentsystem.request.ReviewRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.service.IReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements IReviewsService {

    @Autowired
    private ReviewsMapper reviewsMapper;
    @Autowired
    private JobPostsMapper jobPostsMapper;
    @Autowired
    private StudentsServiceImpl studentsServiceimpl;

    @Override
    public Response create(Long id, CommentRequest request) {
        if (request.getRating()== null) {
            return new Response(false,null,"请选择评价分级");
        }
        if (request.getComment()== null) {
            return new Response(false,null,"评论内容不能为空");
        }
        Users users = studentsServiceimpl.userInfo();
        Reviews reviews = new Reviews();
        reviews.setJobPostId(Math.toIntExact(id));
        reviews.setStudentId(users.getUserId());
        reviews.setComment(request.getComment());
        reviews.setRating(request.getRating());
        reviews.setReviewer(users.getUserName());
        int insert = reviewsMapper.insert(reviews);
        if (insert <= 0 ) {
            return new Response(false,reviews,"评论失败");
        }
        return new Response(true,reviews,"评论成功");
    }

    @Override
    public Response delete(Long id) {
        Users users = studentsServiceimpl.userInfo();
        String email = users.getEmail();
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_id",id).eq("version",1);
        Reviews reviews = reviewsMapper.selectOne(queryWrapper);
        if (reviews == null) {
            return new Response(false,null,"删除对象丢失");
        }
        if (!Objects.equals(reviews.getStudentId(), users.getUserId()) || !Objects.equals(users.getRole(), "admin")) {
            return new Response(false,null,"你无法删除别人的评论");
        }
        reviews.setVersion(0);
        boolean isUpdated = reviewsMapper.update(reviews,queryWrapper) > 0;
        // 如果更新成功，返回新的更新后的考勤记录
        if (isUpdated) {
            return new Response(true, reviews, "删除成功");
        } else {
            return new Response(false, reviews, "删除失败");
        }

    }

    @Override
    public IPage<Reviews> findReviews(ReviewRequest request, int pageNum, int pageSize) {
        Users users = studentsServiceimpl.userInfo();
        Integer id1 = users.getUserId();
        Page<Reviews> page =new Page<>(pageNum,pageSize);
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version",1);
        if (request.getStudentId()!= null) {
            queryWrapper.eq("student_id", request.getStudentId());
        }
        if (request.getJobPostId()!= null) {
            queryWrapper.eq("job_post_id", request.getJobPostId());
        }
        if (request.getRating() != null) {
            // 如果描述非空，则添加状态查询条件
            queryWrapper.eq("rating", request.getRating());
        }
        if (request.getComment() != null) {
            // 如果描述非空，则添加状态查询条件
            queryWrapper.like("comment", request.getComment());
        }
        IPage<Reviews> reviewsIPage = reviewsMapper.selectPage(page,queryWrapper);
        return reviewsIPage;
    }
}
