package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2022-10-16
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select * from article where name like concat('%',replace(#{name},' ',''),'%') limit #{currentPage},#{pageSize}")
    List<Article> getAllArticle(String name, int currentPage, int pageSize);

    @Select("select * from article where name like concat('%',replace(#{name},' ',''),'%') order by time desc limit #{currentPage},#{pageSize}")
    List<Article> getArticleByTime(String name, int currentPage, int pageSize);

    @Select("select * from article where name like concat('%',replace(#{name},' ',''),'%') order by hot desc limit #{currentPage},#{pageSize}")
    List<Article> getArticleByHot(String name, int currentPage, int pageSize);
}
