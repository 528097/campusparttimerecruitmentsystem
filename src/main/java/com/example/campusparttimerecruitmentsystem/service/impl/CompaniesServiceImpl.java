package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.campusparttimerecruitmentsystem.entity.Companies;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.CompaniesMapper;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;
import com.example.campusparttimerecruitmentsystem.service.ICompaniesService;
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
public class CompaniesServiceImpl extends ServiceImpl<CompaniesMapper, Companies> implements ICompaniesService {

    @Autowired
    private CompaniesMapper companiesMapper;
    @Autowired
    private StudentsServiceImpl studentsServiceimpl;

    @Override
    public Response bean() {
        Users users = studentsServiceimpl.userInfo();
        if (!Objects.equals(users.getRole(), "company")&&!Objects.equals(users.getRole(), "admin")) {
            return new Response(false,null,"当前非企业账号");
        }
        QueryWrapper<Companies> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", users.getUserId());
        Companies companies1 = companiesMapper.selectOne(queryWrapper);
        Companies companies;
        if (companies1 == null) {
            companies = new Companies();
            companies.setCompanyId(users.getUserId());
            int insert = companiesMapper.insert(companies);
            if (insert <= 0) {
                return new Response(false, companies, "企业个人信息初始化失败");
            }
            return new Response(true, companies, "企业个人信息初始化成功");
        } else {
            return new Response(true,companies1,"企业个人信息查询成功");
        }
    }

    @Override
    public Response updateBean(Companies companies) {
        QueryWrapper<Companies> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id",companies.getCompanyId());
        Companies companies1 = companiesMapper.selectOne(queryWrapper);
        companies1.setCompanyName(companies.getCompanyName());
        companies1.setPhone(companies.getPhone());
        companies1.setIndustry(companies.getIndustry());
        companies1.setLocation(companies.getLocation());
        companies1.setContactPerson(companies.getContactPerson());
        companies1.setDescription(companies.getDescription());
        boolean isUpdated = companiesMapper.update(companies1,queryWrapper) > 0;
        if (isUpdated) {
            return new Response(true,companiesMapper.selectOne(queryWrapper),"更新成功");
        } else {
            return new Response(false,companiesMapper.selectOne(queryWrapper),"更新失败");
        }
    }
}
