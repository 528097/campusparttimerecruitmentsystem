package com.example.campusparttimerecruitmentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campusparttimerecruitmentsystem.request.UserRequest;
import com.example.campusparttimerecruitmentsystem.response.LoginResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;

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

    IPage<Users> findAll(UserRequest request, int pageNum, int pageSize);

    Response photo(Users users);

    Response deleteById(Long id, Users users);

    Response updateUser(Long id, Users users);

    Response createUser(Users users);
}
