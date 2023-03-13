package com.rabbit.studyweb.controller;


import com.rabbit.model.pojo.CountTime;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.ICountTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-03-13
 */

@RestController
@RequestMapping("/countTime")
public class CountTimeController {

    @Autowired
    private ICountTimeService countTimeService;

    @GetMapping("getData")
    public R getData(){
        Map<String, Object> map = countTimeService.getCountAndTime();
        return R.success(map);
    }

}

