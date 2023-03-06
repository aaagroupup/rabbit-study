package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.UserCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-27
 */
public interface IUserCourseService extends IService<UserCourse> {

    UserCourse findCourseIsExist(Integer userId, Long id);
}
