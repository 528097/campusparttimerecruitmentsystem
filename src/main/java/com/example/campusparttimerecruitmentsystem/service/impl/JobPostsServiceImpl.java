package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.JobPostsMapper;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.service.IJobPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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
public class JobPostsServiceImpl extends ServiceImpl<JobPostsMapper, JobPosts> implements IJobPostsService {

    @Autowired
    private StudentsServiceImpl studentsServiceimpl;
    @Autowired
    private JobPostsMapper jobPostsMapper;
    @Override
    public Response create(JobPosts jobPosts) {
        Users users = studentsServiceimpl.userInfo();
        QueryWrapper<JobPosts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id",users.getUserId());
        int a = jobPostsMapper.selectCount(queryWrapper);
        if (jobPostsMapper.selectCount(queryWrapper) >= 100) {
            return new Response(false,null,"你的招聘信息超过上限100，请删掉一些再增加简历");
        }
        JobPosts jobPosts1 = new JobPosts();
        Date date = new Date();
        LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        jobPosts1.setCompanyId(users.getUserId());
        jobPosts1.setCompanyName(users.getUserName());
        jobPosts1.setPostDate(localDate);
        jobPosts1.setTitle(jobPosts.getTitle());
        jobPosts1.setSalaryRange(jobPosts.getSalaryRange());
        jobPosts1.setRequirements(jobPosts.getRequirements());
        jobPosts1.setWorkHours(jobPosts.getWorkHours());
        jobPosts1.setDescription(jobPosts.getDescription());
        jobPosts1.setWorkLocation(jobPosts.getWorkLocation());
        int insert = jobPostsMapper.insert(jobPosts1);
        if (insert <= 0 ) {
            return new Response(false,jobPosts1,"发布招聘出现未知错误");
        }
        return new Response(true,jobPosts1,"发布招聘成功");
    }

    @Override
    public IPage<JobPosts> findAll(JobPostRequest request, int pageNum, int pageSize) {
        Users users = studentsServiceimpl.userInfo();
        Page<JobPosts> page =new Page<>(pageNum,pageSize);
        QueryWrapper<JobPosts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version",1);
        if ( request.getCompanyName()!= null ) {
            // 如果描述非空，则添加状态查询条件
            queryWrapper.like("company_name", request.getCompanyName());
        }
        if (StringUtils.isNotEmpty(request.getDescription())) {
            // 如果描述非空，则添加状态查询条件
            queryWrapper.like("description", request.getDescription());
        }
        if (StringUtils.isNotEmpty(request.getRequirements())) {
            // 如果需求非空，则添加状态查询条件
            queryWrapper.like("requirements", request.getRequirements());
        }
        if (StringUtils.isNotEmpty(request.getTitle())) {
            // 如果标题非空，则添加状态查询条件
            queryWrapper.like("title", request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getWorkHours())) {
            // 如果工作时长非空，则添加状态查询条件
            queryWrapper.eq("work_hours", request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getWorkLocation())) {
            // 如果工作地点非空，则添加状态查询条件
            queryWrapper.like("work_location", request.getWorkLocation());
        }
        if (request.getPostDate() != null) {
            // 如果发布日期非空，则添加状态查询条件
            queryWrapper.eq("post_date", request.getPostDate());
        }
        if (request.getJobPostId() != null) {
            // 如果发布日期非空，则添加状态查询条件
            queryWrapper.eq("job_post_id", request.getJobPostId());
        }
        // 使用 MyBatis-Plus 提供的分页查询方法，传入 Page 对象和 QueryWrapper 对象
        IPage<JobPosts> jobPostsIPage = jobPostsMapper.selectPage(page,queryWrapper);
        return jobPostsIPage;
    }

    @Override
    public Response deleteById(Long id, JobPostRequest request) {
        QueryWrapper<JobPosts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_post_id",request.getJobPostId());
        JobPosts jobPosts = jobPostsMapper.selectOne(queryWrapper);
        if (jobPosts != null) {
            jobPosts.setVersion(0);
            jobPostsMapper.update(jobPosts,queryWrapper);
            return new Response(true,jobPosts,"删除成功");
        } else{
            return new Response(false,null,"删除失败,删除对象丢失");
        }
    }

    @Override
    public Response updatePosts(Long id, JobPostRequest request) {
        QueryWrapper<JobPosts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_post_id",request.getJobPostId());
        JobPosts jobPosts = jobPostsMapper.selectOne(queryWrapper);
        jobPosts.setRequirements(request.getRequirements());
        jobPosts.setTitle(request.getTitle());
        jobPosts.setDescription(request.getDescription());
        jobPosts.setSalaryRange(request.getSalaryRange());
        jobPosts.setWorkHours(request.getWorkHours());
        jobPosts.setWorkLocation(request.getWorkLocation());
        boolean isUpdated = jobPostsMapper.update(jobPosts, queryWrapper) > 0;
        // 如果更新成功，返回新的更新后的考勤记录
        if (isUpdated) {
            return new Response(true, jobPosts, "更新成功");
        } else {
            return new Response(false, jobPosts, "更新失败");
        }
    }
}
