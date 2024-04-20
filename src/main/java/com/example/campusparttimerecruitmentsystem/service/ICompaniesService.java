package com.example.campusparttimerecruitmentsystem.service;

import com.example.campusparttimerecruitmentsystem.entity.Companies;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface ICompaniesService extends IService<Companies> {

    Response bean();

    Response updateBean(Companies companies);
}
