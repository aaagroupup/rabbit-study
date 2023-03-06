package com.rabbit.studyweb.live.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.LiveCourse;
import com.rabbit.model.pojo.LiveCourseAccount;
import com.rabbit.model.pojo.dto.LiveCourseDTO;
import com.rabbit.model.pojo.vo.LiveCourseConfigVo;
import com.rabbit.model.pojo.vo.LiveCourseVo;
import com.rabbit.studyweb.live.service.ILiveCourseAccountService;
import com.rabbit.studyweb.live.service.ILiveCourseService;
import com.rabbit.studyweb.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */

@RestController
@RequestMapping("/live/liveCourse")
public class LiveCourseController {

    @Autowired
    private ILiveCourseService liveCourseService;

    @Autowired
    private ILiveCourseAccountService liveCourseAccountService;


    /**
     * 直播课程列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public R list(@PathVariable Long currentPage,@PathVariable Long pageSize){
        Page<LiveCourse> liveCoursePage = new Page<LiveCourse>(currentPage,pageSize);
        IPage<LiveCourse> page= liveCourseService.selectPage(liveCoursePage);
        return R.success(page);
    }

    @ApiOperation("直播课程添加")
    @PostMapping("/save")
    public R save(@RequestBody LiveCourseVo liveCourseVo){
        liveCourseService.saveLive(liveCourseVo);
        return R.success("");
    }

    @ApiOperation("删除直播")
    @DeleteMapping("delete/{id}")
    public R deleteLiveCourse(@PathVariable Long id){
        liveCourseService.deleteLive(id);
        return R.success("");
    }

    /**
     * 根据id查询直播课程基本信息
     * @param id
     * @return
     */
    @ApiOperation("获取直播课程基本信息")
    @GetMapping("/get/{id}")
    public R get(@PathVariable Long id){
        LiveCourse liveCourse = liveCourseService.getById(id);
        return R.success(liveCourse);
    }

    //根据id查询直播课程基本信息和描述信息
    @ApiOperation("获取课程描述信息")
    @GetMapping("/getInfo/{id}")
    public R getInfo(@PathVariable Long id){
        LiveCourseVo liveCourseVo = liveCourseService.getLiveCourseVo(id);
        return R.success(liveCourseVo);
    }

    /**
     * 更新直播课程方法
     * @param liveCourseVo
     * @return
     */
    @ApiOperation("更新直播课程方法")
    @PutMapping("update")
    public R update(@RequestBody LiveCourseVo liveCourseVo){
        liveCourseService.updateLiveById(liveCourseVo);
        return R.success("");
    }

    @ApiOperation("获取直播账号信息")
    @GetMapping("getLiveCourseAccount/{id}")
    public R getLiveCourseAccount(@PathVariable Long id){
       LiveCourseAccount liveCourseAccount = liveCourseAccountService.getLiveCourseAccountById(id);
       return R.success(liveCourseAccount);
    }

    @ApiOperation("获取直播配置信息")
    @GetMapping("getCourseConfig/{id}")
    public R getCourseConfig(@PathVariable Long id){
        LiveCourseConfigVo liveCourseConfigVo = liveCourseService.getCourseConfig(id);
        return R.success(liveCourseConfigVo);
    }

    @ApiOperation("修改配置")
    @PutMapping("updateConfig")
    public R updateConfig(@RequestBody LiveCourseConfigVo liveCourseConfigVo){
        liveCourseService.updateConfig(liveCourseConfigVo);
        return R.success("");
    }

    @ApiOperation("获取最近直播")
    @GetMapping("findLatelyList")
    public R findLatelyList(){
        List<LiveCourseDTO> liveCourseDTOList= liveCourseService.getLatelyList();
        return R.success(liveCourseDTOList);
    }

    @ApiOperation("获取用户access_token")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable Long id){
        JSONObject object= liveCourseService.getAccessToken(id);
        return R.success(object);
    }

}

