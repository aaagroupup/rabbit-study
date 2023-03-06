package com.rabbit.studyweb.controller;

import com.rabbit.model.pojo.Course;
import com.rabbit.model.pojo.vo.CourseQueryVo;
import com.rabbit.studyweb.clients.VodClient;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.ICourseService;
import com.rabbit.studyweb.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rabbit.studyweb.common.Constants.*;
import static com.rabbit.studyweb.common.Constants.addSuc_MSG;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @Autowired
    private VodClient vodClient;

    @Autowired
    private IOrderService orderService;

    /**
     * 点播课程列表
     * @param currentPage 当前页
     * @param pageSize  页大小
     * @param courseQueryVo 查询实体信息
     * @return
     */
    @ApiOperation("点播课程列表")
    @GetMapping("{currentPage}/{pageSize}")
    public R getCourseList(@PathVariable int currentPage, @PathVariable int pageSize , CourseQueryVo courseQueryVo){

        Map<String,Object> map= courseService.findPageCourse(currentPage,pageSize,courseQueryVo);
        return R.success(map);
    }

    /**
     * 更改课程状态，是否发布课程
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/updateStatus")
    public R<String> updateStatus(@RequestParam("id") Long id, @RequestParam("status") Boolean status){

        boolean flag = courseService.updateStatus(id, status);
        if(flag){
            return R.success(updateSuc_MSG);
        }else{
            return R.error(updateErr_MSG);
        }
    }

    /**
     * 新增课程
     * @param course
     * @return
     */
    @PostMapping("/addCourse")
    public R<String> addCourse(@RequestBody Course course){
        course.setStatus(false);
        course.setPublishTime(LocalDateTime.now().toString());
        course.setBuyCount(0L);
        boolean flag = courseService.save(course);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCourse")
    public R<String> deleteCourse(Long id){
        //先根据课程id查询出视频,之后删除视频
        vodClient.getVideoAndRemove(id);
        boolean flag = courseService.removeById(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @GetMapping("/getUpdate2")
    public R<Course> getUpdateCourse(Long id){
        Course course = courseService.getById(id);
        return R.success(course);
    }

    /**
     * 更改课程数据
     * @param course
     * @return
     */
    @PutMapping("/updateCourse")
    public R<String> updateCourse(@RequestBody Course course){

        boolean flag = courseService.updateById(course);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/del/batch")
    public R<String> deleteBatch(@RequestBody List<Integer> ids) {

        //根据id找到对应的video视频id，之后删除云端视频和本地记录
        vodClient.delBatchVideo(ids);
        boolean flag = courseService.removeByIds(ids);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    /**
     * 查询所有上线的课程
     * @return
     */
    @GetMapping("findAll")
    public R findAll(){
        List<Course> list = courseService.findList();
        return R.success(list);
    }

    @DeleteMapping("saveOrUpdate")
    public R saveOrUpdate(@RequestBody List<Long> ids){
        List<Course> courseList=courseService.getCourseByIds(ids);

        return R.success(courseList);
    }

    /**
     * 获得课程详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public R getCourseDetail(@PathVariable Long id){
        Course course = courseService.getCourseDetail(id);
        return R.success(course);
    }

    //获得已购买的课程
    @GetMapping("/getPurchasedCourseList")
    public R getPurchasedCourseList(){
        //去订单表里查该用户的订单里有什么课程
        List<Long>  courseIds = orderService.getPurchasedCourseList();
        List<Course> courseList = courseService.getCourseListByIds(courseIds);
        //查询当前用户免费课程
        List<Course> freeCourseList= courseService.getUserFreeCourseList();
        Map<String,Object> map=new HashMap<>();
        map.put("courseList",courseList);
        map.put("freeCourseList",freeCourseList);
        return R.success(map);
    }



    //根据条件查询已买课程
    @GetMapping("/getCourseListByQuery")
    public R getCourseListByQuery(String searchText){
        List<Long>  courseIds = orderService.getPurchasedCourseList();
        List<Course> courseList = courseService.getCourseQuery(searchText,courseIds);
        return R.success(courseList);
    }
}


