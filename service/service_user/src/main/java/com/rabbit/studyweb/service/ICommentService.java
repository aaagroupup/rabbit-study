package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.Comment;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-17
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer articleId);
    List<Comment> findCommentDetailByCourseId(Long courseId);
}
