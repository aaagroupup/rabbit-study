package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.UserCourse;
import com.rabbit.studyweb.mapper.UserCourseMapper;
import com.rabbit.studyweb.service.IUserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-27
 */
@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements IUserCourseService {

    @Override
    public UserCourse findCourseIsExist(Integer userId, Long id) {
        LambdaQueryWrapper<UserCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCourse::getUserId,userId)
                .eq(UserCourse::getCourseId,id);
        UserCourse userCourse = baseMapper.selectOne(wrapper);
        return userCourse;
    }
}
