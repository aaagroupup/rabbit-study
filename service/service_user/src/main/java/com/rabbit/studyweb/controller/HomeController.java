package com.rabbit.studyweb.controller;

import com.rabbit.model.pojo.Advertise;
import com.rabbit.model.pojo.Course;
import com.rabbit.model.pojo.dto.HomeMenusBannerDTO;
import com.rabbit.model.pojo.dto.HomeMenusDTO;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IAdvertiseService;
import com.rabbit.studyweb.service.ICourseService;
import com.rabbit.studyweb.service.ISubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@Api(tags = "HomeController",description = "首页内容管理")
public class HomeController {

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IAdvertiseService advertiseService;

    @Autowired
    private ICourseService courseService;

    /**
     * 获取首页导航栏和数据
     */
    @GetMapping("/getMenusAndBanner")
    public R getMenus(){
        //分类
        List<HomeMenusDTO> list = subjectService.getMenus();
        HomeMenusBannerDTO homeMenusBannerDTO = new HomeMenusBannerDTO();
        homeMenusBannerDTO.setHomeMenusList(list);

        //banner
        List<Advertise> advertiseList = advertiseService.getBanners();
        homeMenusBannerDTO.setAdvertiseList(advertiseList);

        //freeCourse
        List<Course> freeCourseList = courseService.getFreeCourseList();
        homeMenusBannerDTO.setFreeCourseList(freeCourseList);
        return R.success(homeMenusBannerDTO);
    }
}
