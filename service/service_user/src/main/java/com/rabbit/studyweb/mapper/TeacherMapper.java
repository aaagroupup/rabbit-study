package com.rabbit.studyweb.mapper;

import com.rabbit.model.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2023-02-06
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("select * from sys_teacher where name like concat('%',replace(#{name},' ',''),'%') limit #{currentPage},#{pageSize}")
    List<Teacher> getAllTeacher(String name, int currentPage, int pageSize);
}
