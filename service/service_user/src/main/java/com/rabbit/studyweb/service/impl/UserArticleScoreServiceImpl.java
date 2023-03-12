package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.UserArticleScore;
import com.rabbit.studyweb.mapper.UserArticleScoreMapper;
import com.rabbit.studyweb.service.IUserArticleScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-03-05
 */
@Service
public class UserArticleScoreServiceImpl extends ServiceImpl<UserArticleScoreMapper, UserArticleScore> implements IUserArticleScoreService {

    @Override
    public Integer getScore(Integer articleId) {
        LambdaQueryWrapper<UserArticleScore> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserArticleScore::getUserId, TokenUtils.getCurrentUser().getId())
                        .eq(UserArticleScore::getArticleId,articleId);
        UserArticleScore userArticleScore = baseMapper.selectOne(wrapper);
        if(userArticleScore==null){
            return 0;
        }else{
            return userArticleScore.getScore();
        }
    }
}
