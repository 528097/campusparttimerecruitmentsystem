package com.example.campusparttimerecruitmentsystem.controller;


import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;
import com.example.campusparttimerecruitmentsystem.service.IStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    private IStudentsService studentsService;
    @RequestMapping("/identity/bean")
    public StudentResponse bean() {
        return studentsService.bean();
    }
    @RequestMapping("/identity/show")
    public Students show() {
        return studentsService.show();
    }
    @RequestMapping("/identity/update")
    public StudentResponse updateBean(@RequestBody Students students) {
        return studentsService.updateBean(students);
    }
    @RequestMapping("/identity/userInfo")
    public Users userInfo(@RequestParam String token) {
        return studentsService.userInfo(token);
    }

}
