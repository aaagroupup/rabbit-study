package com.rabbit.studyweb.controller;


import com.rabbit.model.pojo.Subject;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    //课程分类

    @GetMapping("/getChildrenSubject/{id}")
    public R getChildrenSubject(@PathVariable Long id){
        List<Subject> list =subjectService.selectSubjectList(id);
        return R.success(list);
    }

    //课程分类导出
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    //课程分类导入
    @GetMapping("importData")
    public R importData(MultipartFile file){
        subjectService.importData(file);
        return R.success("导入成功");
    }

    //获得一级课程
    @GetMapping("getSubjectList")
    public R getSubjectList(){
        List<Subject> subjectList = subjectService.getSubjectList();
        return R.success(subjectList);
    }

    @GetMapping("inner/getSubjectName/{id}")
    public String getSubjectName(@PathVariable Long id){
        Subject subject = subjectService.getById(id);
        String title = subject.getTitle();
        return title;
    }
}

