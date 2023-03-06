package com.rabbit.studyweb.live.service;

import com.rabbit.model.pojo.LiveCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
public interface ILiveCourseDescriptionService extends IService<LiveCourseDescription> {

    LiveCourseDescription getLiveCourseById(Long liveCourseId);
}
