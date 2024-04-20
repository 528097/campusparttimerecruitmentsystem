package com.example.campusparttimerecruitmentsystem.service;

import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IStudentsService extends IService<Students> {

    Students show();

    StudentResponse bean();

    StudentResponse updateBean(Students students);

    Users userInfo(String token);
}
