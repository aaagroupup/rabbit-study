package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.CommentMapper;
import com.rabbit.model.pojo.Comment;
import com.rabbit.studyweb.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentDetail(Integer articleId) {
        return commentMapper.findCommentDetail(articleId);
    }

    @Override
    public List<Comment> findCommentDetailByCourseId(Long courseId) {
        return commentMapper.findCommentDetailByCourseId(courseId);
    }
}
