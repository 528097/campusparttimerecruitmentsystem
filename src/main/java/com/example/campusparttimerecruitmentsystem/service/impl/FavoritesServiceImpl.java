package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.entity.Favorites;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.FavoritesMapper;
import com.example.campusparttimerecruitmentsystem.mapper.JobPostsMapper;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.service.IFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements IFavoritesService {

    @Autowired
    private StudentsServiceImpl studentsServiceimpl;
    @Autowired
    private FavoritesMapper favoritesMapper;
    @Autowired
    private JobPostsMapper jobPostsMapper;
    @Override
    public Response createLike(JobPostRequest request) {
        Users users = studentsServiceimpl.userInfo();
        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",users.getUserId());
        int a = favoritesMapper.selectCount(queryWrapper);
        if (favoritesMapper.selectCount(queryWrapper) >= 100) {
            return new Response(false,null,"你的收藏超过上限100，请删掉一些再增加收藏");
        }
        Favorites favorites = new Favorites();
        favorites.setJobPostId(request.getJobPostId());
        favorites.setStudentId(users.getUserId());
        queryWrapper.eq("job_post_id",request.getJobPostId());
        if (favoritesMapper.selectOne(queryWrapper) != null) {
            return new Response(true,null,"你已经收藏过这个招聘信息了");
        }
        int insert = favoritesMapper.insert(favorites);
        if (insert <= 0 ) {
            return new Response(false,favorites,"收藏失败");
        }
        return new Response(true,favorites,"收藏成功");
    }

    @Override
    public IPage<JobPosts> findLike(JobPostRequest request,int pageNum, int pageSize) {
        Users users = studentsServiceimpl.userInfo();
        Page<JobPosts> page = new Page<>(pageNum, pageSize);
        QueryWrapper<JobPosts> queryWrapper = new QueryWrapper<>();
        // 根据收藏表和工作岗位表的关系进行连接查询
        queryWrapper.inSql("job_post_id", "SELECT job_post_id FROM favorites WHERE student_id = " + users.getUserId() + " AND version = 1");
        // 添加模糊查询条件
        if (StringUtils.isNotEmpty(request.getDescription())) {
            queryWrapper.like("description", request.getDescription());
        }
        if (StringUtils.isNotEmpty(request.getRequirements())) {
            queryWrapper.like("requirements", request.getRequirements());
        }
        if (StringUtils.isNotEmpty(request.getTitle())) {
            queryWrapper.like("title", request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getWorkLocation())) {
            queryWrapper.like("work_location", request.getWorkLocation());
        }
        if (StringUtils.isNotEmpty(request.getSalaryRange())) {
            queryWrapper.like("salary_range", request.getSalaryRange());
        }
        if (StringUtils.isNotEmpty(request.getWorkHours())) {
            queryWrapper.like("work_hours", request.getWorkHours());
        }
        // 使用 MyBatis-Plus 提供的分页查询方法，传入 Page 对象和 QueryWrapper 对象
        IPage<JobPosts> jobPostsIPage = jobPostsMapper.selectPage(page, queryWrapper);
        return jobPostsIPage;
    }

    @Override
    public Response deleteById(Long id,JobPosts jobPosts) {
        Users users = studentsServiceimpl.userInfo();
        QueryWrapper<Favorites> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_post_id",jobPosts.getJobPostId()).eq("student_id",users.getUserId()).eq("version",1);
        Favorites favorites1 = favoritesMapper.selectOne(queryWrapper);
        if (favorites1 != null) {
            favorites1.setVersion(0);
            favoritesMapper.update(favorites1,queryWrapper);
            return new Response(true,favorites1,"删除成功");
        } else{
            return new Response(false,null,"删除失败,删除对象丢失");
        }
    }
}
