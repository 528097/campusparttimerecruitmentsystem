package com.example.campusparttimerecruitmentsystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.campusparttimerecruitmentsystem.entity.Favorites;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.service.IFavoritesService;
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
@RequestMapping("/favorites")
public class FavoritesController {
    @Autowired
    private IFavoritesService favoritesService;
    @RequestMapping("/createLike")
    public Response createLike(@RequestBody JobPostRequest request) {
        return favoritesService.createLike(request);
    }
    @RequestMapping("/like/page")
    public IPage<JobPosts> LikePage(
            @RequestBody JobPostRequest request,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        IPage<JobPosts> page = favoritesService.findLike(request,pageNum, pageSize);
        return page;
    }
    @RequestMapping("/deleteLike/{id}")
    public Response deleteLike(@PathVariable Long id,@RequestBody JobPosts jobPosts) {
        return favoritesService.deleteById(id,jobPosts);
    }
}
