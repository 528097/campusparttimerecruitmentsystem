package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.response.ResumesResponse;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;
import com.example.campusparttimerecruitmentsystem.service.IResumesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/resumes")
public class ResumesController {

    @Autowired
    private IResumesService resumesService;

    @RequestMapping("/create")
    public ResumesResponse createResumes(@RequestBody Resumes resumes) {
        return resumesService.create(resumes);
    }
    @RequestMapping("/page")
    public IPage<Resumes> resumePage(
            @RequestBody ResumesRequest request,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        IPage<Resumes> page = resumesService.findAll(request, pageNum, pageSize);
        return page;
    }
    @RequestMapping("/delete/{id}")
    public Response deleteResumes(@PathVariable Long id) {
        return resumesService.deleteById(id);
    }
    @RequestMapping("/update/{id}")
    public Response updateResumes(@PathVariable Long id,@RequestBody ResumesRequest request) {
        return resumesService.updateResume(id,request);
    }


}
