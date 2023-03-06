package com.rabbit.studyweb.live.service;

import com.rabbit.model.pojo.LiveCourseAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
public interface ILiveCourseAccountService extends IService<LiveCourseAccount> {

    LiveCourseAccount getLiveCourseAccountById(Long courseId);

    String getZhuboPasswordByCourseId(Long id);
}
