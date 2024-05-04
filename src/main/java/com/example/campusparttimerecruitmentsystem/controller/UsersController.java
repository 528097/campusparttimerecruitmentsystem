package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.request.UserRequest;
import com.example.campusparttimerecruitmentsystem.response.LoginResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.service.IStudentsService;
import com.example.campusparttimerecruitmentsystem.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @RequestMapping("/register")
    public String register(@RequestBody Users users) {
        return usersService.register(users);
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        LoginResponse response = usersService.login(email, password);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message","登陆成功");
        if (response.isSuccess()) {
            if (response.getMessage().contains("student")) {
                responseBody.put("role", "student");
            } else if (response.getMessage().contains("company")) {
                responseBody.put("role", "company");
            } else if (response.getMessage().contains("admin")) {
                responseBody.put("role", "admin");
            } else {
                return new ResponseEntity<>(response.getMessage(), HttpStatus.UNAUTHORIZED);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", response.getToken());
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("/page")
    public IPage<Users> usersIPagePage(
            @RequestBody UserRequest request,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        IPage<Users> page = usersService.findAll(request, pageNum, pageSize);
        return page;
    }
    @RequestMapping("/create")
    public Response createUser(@RequestBody Users Users) {
        return usersService.createUser(Users);
    }
    @RequestMapping("/delete/{id}")
    public Response deleteUser(@PathVariable Long id,@RequestBody Users users) {
        return usersService.deleteById(id,users);
    }
    @RequestMapping("/update/{id}")
    public Response updateUser(@PathVariable Long id,@RequestBody Users users) {
        return usersService.updateUser(id,users);
    }
    @RequestMapping("/photo")
    public Response photo(@RequestBody Users users) {
        return usersService.photo(users);
    }
}
