package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2022-10-17
 */

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.*,u.nickname from t_comment c left join sys_user u on c.user_id=u.id " +
            "where c.article_id=#{articleId} order by id desc")
    List<Comment> findCommentDetail(Integer articleId);
    @Select("select c.*,u.nickname from t_comment c left join sys_user u on c.user_id=u.id " +
            "where c.course_id=#{courseId} order by id desc")
    List<Comment> findCommentDetailByCourseId(Long courseId);
}
