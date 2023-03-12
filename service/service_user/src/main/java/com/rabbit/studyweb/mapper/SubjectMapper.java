package com.rabbit.studyweb.mapper;

import com.rabbit.model.pojo.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.dto.HomeMenusDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
public interface SubjectMapper extends BaseMapper<Subject> {
}
