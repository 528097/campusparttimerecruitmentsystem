package com.example.campusparttimerecruitmentsystem.service;

import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.response.LoginResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
public interface IUsersService extends IService<Users> {

    String register(Users users);

    LoginResponse login(String email, String password);

}
