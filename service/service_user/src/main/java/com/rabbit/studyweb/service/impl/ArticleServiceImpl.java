package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.model.pojo.Article;
import com.rabbit.model.pojo.Comment;
import com.rabbit.studyweb.mapper.ArticleMapper;

import com.rabbit.model.pojo.UserArticleScore;
import com.rabbit.studyweb.service.IArticleService;
import com.rabbit.studyweb.service.ICommentService;
import com.rabbit.studyweb.service.IUserArticleScoreService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserArticleScoreService userArticleScoreService;

    @Override
    public List<Article> getAllArticle(String query, int currentPage, int pageSize) {
        return articleMapper.getAllArticle(query,currentPage,pageSize);
    }

    @Override
    public List<Article> getArticleByTime() {
        List<Article> articleList=baseMapper.getArticleByTime();
        return articleList;
    }

    @Override
    public boolean addScore(Integer id, Integer score) {
        Integer userId = TokenUtils.getCurrentUser().getId();
        //user_article_score表添加数据
        //先判断是否存在
        LambdaQueryWrapper<UserArticleScore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticleScore::getArticleId,id)
                .eq(UserArticleScore::getUserId,userId);
        UserArticleScore one = userArticleScoreService.getOne(queryWrapper);
        if(one==null){
            UserArticleScore userArticleScore = new UserArticleScore();
            userArticleScore.setArticleId(id);
            userArticleScore.setUserId(userId);
            userArticleScore.setScore(score);
            userArticleScoreService.save(userArticleScore);
        }else{
            one.setScore(score);
            userArticleScoreService.updateById(one);
        }

        //评分算法
        Article article = baseMapper.selectById(id);
        Integer hot = article.getHot();
        //获得文章评论数量
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId,id);
        long commentCount = commentService.count(wrapper);
        article.setHot(((int)commentCount+hot+score)>>1);
        int count = baseMapper.updateById(article);
        return count>0;
    }

    @Override
    public List<Article> getArticleByHot() {
        List<Article> articleList=baseMapper.getArticleByHot();
        return articleList;
    }
}
