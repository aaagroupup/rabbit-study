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

    @Select("select * from article order BY time desc")
    List<Article> getArticleByTime();

    @Select("select * from article order BY hot desc")
    List<Article> getArticleByHot();
}
