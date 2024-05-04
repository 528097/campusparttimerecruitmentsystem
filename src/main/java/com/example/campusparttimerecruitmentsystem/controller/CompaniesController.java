package com.example.campusparttimerecruitmentsystem.controller;


import com.example.campusparttimerecruitmentsystem.entity.Companies;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;
import com.example.campusparttimerecruitmentsystem.service.ICompaniesService;
import com.example.campusparttimerecruitmentsystem.service.IResumesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/companies")
public class CompaniesController {
    @Autowired
    private ICompaniesService companiesService;
    @RequestMapping("/identity/bean")
    public Response bean() {
        return companiesService.bean();
    }
    @RequestMapping("/identity/update")
    public Response updateBean(@RequestBody Companies companies) {
        return companiesService.updateBean(companies);
    }
    @RequestMapping("/identity/find")
    public Response find(@RequestBody Companies companies) {
        return companiesService.find(companies);
    }
}
