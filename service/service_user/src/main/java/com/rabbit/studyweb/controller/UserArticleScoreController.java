package com.rabbit.studyweb.controller;


import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IUserArticleScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2023-03-05
 */

@RestController
@RequestMapping("/userArticleScore")
public class UserArticleScoreController {

    @Autowired
    private IUserArticleScoreService service;

    @GetMapping("getScore")
    public R getScore(Integer articleId){
        Integer score = service.getScore(articleId);
        return R.success(score);
    }

}

