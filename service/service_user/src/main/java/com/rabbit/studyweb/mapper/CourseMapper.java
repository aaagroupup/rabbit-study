package com.rabbit.studyweb.mapper;

import com.rabbit.model.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2023-02-05
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Update("update sys_course set status=#{status} where id=#{id}")
    boolean updateState(Long id, Boolean status);

    List<Course> selectCourseQuery(String searchText,List<Long> ids);

    @Select("select * from sys_course where `status`=1  order by buy_count desc  limit 3")
    List<Course> findTopThree();
}
