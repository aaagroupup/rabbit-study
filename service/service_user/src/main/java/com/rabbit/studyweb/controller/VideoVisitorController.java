package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IVideoVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-02-09
 */

@RestController
@RequestMapping("/vod/videoVisitor")
public class VideoVisitorController {

    @Autowired
    private IVideoVisitorService videoVisitorService;

    //课程统计接口
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public R findCount(@PathVariable Long courseId,
                       @PathVariable String startDate,
                       @PathVariable String endDate){
            Map<String,Object> map= videoVisitorService.findCount(courseId,startDate,endDate);
            return R.success(map);
    }

}

