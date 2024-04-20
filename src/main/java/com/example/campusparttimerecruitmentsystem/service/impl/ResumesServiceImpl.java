package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.ResumesMapper;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.service.IResumesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class ResumesServiceImpl extends ServiceImpl<ResumesMapper, Resumes> implements IResumesService {

    @Autowired
    private StudentsServiceImpl studentsServiceimpl;
    @Autowired
    private ResumesMapper resumesMapper;
    @Override
    public ResumesResponse create(Resumes resumes) {
        Users users = studentsServiceimpl.userInfo();
        QueryWrapper<Resumes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",users.getUserId()).eq("version",1);
        int a = resumesMapper.selectCount(queryWrapper);
        if (resumesMapper.selectCount(queryWrapper) > 5) {
            return new ResumesResponse(false,null,"你的简历超过上限5，请删掉一些再增加简历");
        }
        Resumes resumes1 = new Resumes();
        resumes1.setStudentId(users.getUserId());
        resumes1.setExperience(resumes.getExperience());
        resumes1.setSkills(resumes.getSkills());
        resumes1.setObjectives(resumes.getObjectives());
        int insert = resumesMapper.insert(resumes1);
        if (insert <= 0 ) {
            return new ResumesResponse(false,resumes1,"创建简历出现未知错误");
        }
        return new ResumesResponse(true,resumes1,"创建简历成功");
    }

    @Override
    public IPage<Resumes> findAll(ResumesRequest request, int pageNum, int pageSize) {
        Users users = studentsServiceimpl.userInfo();
        Page<Resumes> page =new Page<>(pageNum,pageSize);
        QueryWrapper<Resumes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version",1).eq("student_id",users.getUserId());
        if (StringUtils.isNotEmpty(request.getExperience())) {
            // 如果状态非空，则添加状态查询条件
            queryWrapper.like("experience", request.getExperience());
        }
        if (StringUtils.isNotEmpty(request.getObjectives())) {
            // 如果审核人非空，则添加状态查询条件
            queryWrapper.like("objectives", request.getObjectives());
        }
        if (StringUtils.isNotEmpty(request.getSkills())) {
            // 如果姓名非空，则添加状态查询条件
            queryWrapper.like("skills", request.getSkills());
        }
        // 使用 MyBatis-Plus 提供的分页查询方法，传入 Page 对象和 QueryWrapper 对象
        IPage<Resumes> resumesIPage = resumesMapper.selectPage(page, queryWrapper);
        return resumesIPage;
    }


    @Override
    public Response deleteById(Long id) {
        QueryWrapper<Resumes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resume_id",id);
        Resumes resumes = resumesMapper.selectOne(queryWrapper);
        if (resumes != null) {
            resumes.setVersion(0);
            resumesMapper.update(resumes,queryWrapper);
            return new Response(true,resumes,"删除成功");
        } else{
            return new Response(false,null,"删除失败,删除对象丢失");
        }
    }

    @Override
    public Response updateResume(Long id, ResumesRequest request) {
        QueryWrapper<Resumes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resume_id", request.getResumeId());
        Resumes resumes = resumesMapper.selectOne(queryWrapper);
        resumes.setObjectives(request.getObjectives());
        resumes.setSkills(request.getSkills());
        resumes.setExperience(request.getExperience());
        boolean isUpdated = resumesMapper.update(resumes, queryWrapper) > 0;
        // 如果更新成功，返回新的更新后的考勤记录
        if (isUpdated) {
            return new Response(true, resumes, "更新成功");
        } else {
            return new Response(false, resumes, "更新失败");
        }
    }
}
