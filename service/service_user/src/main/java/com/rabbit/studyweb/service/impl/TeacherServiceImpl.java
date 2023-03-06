package com.rabbit.studyweb.service.impl;

import com.rabbit.model.pojo.Teacher;
import com.rabbit.studyweb.mapper.TeacherMapper;
import com.rabbit.studyweb.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-06
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public List<Teacher> getTeacherList(String query, int currentPage, int pageSize) {
        return baseMapper.getAllTeacher(query,currentPage,pageSize);
    }
}
