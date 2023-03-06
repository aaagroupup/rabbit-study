package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.UserArticleScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-03-05
 */
public interface IUserArticleScoreService extends IService<UserArticleScore> {

    Integer getScore(Integer id);
}
