package com.rabbit.studyweb.live.service;

import com.rabbit.model.pojo.LiveCourseConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
public interface ILiveCourseConfigService extends IService<LiveCourseConfig> {

    LiveCourseConfig getCourseConfigByCourseId(Long courseId);
}
