package com.rabbit.studyweb.live.mapper;

import com.rabbit.model.pojo.LiveCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.dto.LiveCourseDTO;
import com.rabbit.model.pojo.vo.LiveCourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */

@Mapper
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    @Select("select * from live_course " +
            "where date(start_time) >= date_sub(CURDATE(),interval 1 week)" +
            " and date(start_time) <= date_add(CURDATE(),interval 1 week)" +
            "order by start_time desc ")
    List<LiveCourseDTO> getLatelyList();
}
