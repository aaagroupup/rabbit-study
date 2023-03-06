package com.rabbit.studyweb.controller;


import com.rabbit.model.pojo.UserCourse;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IUserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-27
 */

@RestController
@RequestMapping("/userCourse")
public class UserCourseController {

    @Autowired
    private IUserCourseService userCourseService;

    @GetMapping("find/{userId}/{id}")
    public R findIsExist(@PathVariable Integer userId,@PathVariable Long id){
        UserCourse userCourse= userCourseService.findCourseIsExist(userId,id);
        if(userCourse!=null){
            return R.success("课程已存在，请勿重复添加!!!");
        }else{
            return R.error("该课程没有添加过");
        }
    }

    @PutMapping("add/{userId}/{id}")
    public R save(@PathVariable Integer userId,@PathVariable Long id){
        UserCourse userCourse = new UserCourse();
        userCourse.setUserId(userId);
        userCourse.setCourseId(id);
        boolean flag = userCourseService.save(userCourse);
        if(flag){
            return R.success("添加成功");
        }else{
            return R.error("添加失败!");
        }
    }

}

