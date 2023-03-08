package com.rabbit.studyweb.controller;

import com.rabbit.model.pojo.Course;
import com.rabbit.model.pojo.dto.HomeMenusBannerDTO;
import com.rabbit.model.pojo.dto.HomeMenusDTO;
import com.rabbit.model.pojo.dto.SubjectDTO;
import com.rabbit.studyweb.result.R;
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

        //走马灯
        List<Course> advertiseList = courseService.findTopThreeCourse();
        homeMenusBannerDTO.setAdvertiseList(advertiseList);

        return R.success(homeMenusBannerDTO);
    }

    /**
     * 获得学科下所有课程
     * @return
     */
    @GetMapping("getSubjectAndCourse")
    public R getSubjectAndCourse(){
        List<SubjectDTO>  subjectDTOList=courseService.getSubjectAndCourse();
        return R.success(subjectDTOList);
    }
}
