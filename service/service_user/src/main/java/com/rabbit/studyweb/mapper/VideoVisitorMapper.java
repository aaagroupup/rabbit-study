package com.rabbit.studyweb.mapper;

import com.rabbit.model.pojo.VideoVisitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.vo.VideoVisitorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2023-02-09
 */

@Mapper
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorVo> findCount(@Param("courseId") Long courseId,@Param("startDate") String startDate,@Param("endDate") String endDate);
}
