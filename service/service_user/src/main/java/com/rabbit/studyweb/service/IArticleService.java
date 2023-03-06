package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.Article;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-16
 */
public interface IArticleService extends IService<Article> {

    List<Article> getAllArticle(String query, int currentPage, int pageSize);

    List<Article> getArticleByTime();

    boolean addScore(Integer id, Integer score);

    List<Article> getArticleByHot();
}
