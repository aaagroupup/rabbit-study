package com.rabbit.studyweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.QueryInfo;
import com.rabbit.model.pojo.Teacher;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.ITeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @GetMapping("getTeacherList")
    public R getTeacherList(QueryInfo queryInfo){

        int currentPage = queryInfo.getPageSize() * (queryInfo.getCurrentPage() - 1);

        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Teacher::getName,queryInfo.getQuery());
        int count =(int) teacherService.count(wrapper);
        List<Teacher> teacherList = teacherService.getTeacherList(queryInfo.getQuery(), currentPage, queryInfo.getPageSize());

        return R.success(teacherList,count);
    }


    @PostMapping("/addTeacher")
    public R<String> addTeacher(@RequestBody Teacher teacher){

        boolean flag = teacherService.save(teacher);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    @DeleteMapping("/deleteTeacher")
    public R<String> deleteTeacher(Long id){
        boolean flag = teacherService.removeById(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    @ApiOperation("查询所有讲师")
    @GetMapping("getAllTeachers")
    public R<List<Teacher>> getAllTeachers(){
        List<Teacher> teacherList = teacherService.list();
        return R.success(teacherList,"查询成功");
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @GetMapping("/getUpdate2")
    public R<Teacher> getUpdate(Long id){
        Teacher teacher = teacherService.getById(id);
        return R.success(teacher);
    }

    @PutMapping("/updateTeacher")
    public R<String> updateTeacher(@RequestBody Teacher teacher){

        boolean flag = teacherService.updateById(teacher);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    //根据id查询 远程调用
    @ApiOperation("根据id查询")
    @GetMapping("inner/getTeacher/{id}")
    public Teacher getTeacherInfo(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        return teacher;
    }

}

