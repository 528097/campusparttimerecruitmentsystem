package com.example.campusparttimerecruitmentsystem.controller;

import com.example.campusparttimerecruitmentsystem.Util.GiteeImgBedUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/img")
public class ImgController {
    @RequestMapping("/add")
    public String add(@RequestParam("file") MultipartFile multipartFile) {
        return GiteeImgBedUtils.upload("demo", multipartFile);
    }

    @RequestMapping("/del")
    public String del(String path) {
        return "" + GiteeImgBedUtils.delete(path);
    }
}
