package com.rabbit.studyweb.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.LiveCourseDescription;
import com.rabbit.studyweb.live.mapper.LiveCourseDescriptionMapper;
import com.rabbit.studyweb.live.service.ILiveCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription> implements ILiveCourseDescriptionService {

    //获取直播课程信息
    @Override
    public LiveCourseDescription getLiveCourseById(Long liveCourseId) {
        LambdaQueryWrapper<LiveCourseDescription> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseDescription::getLiveCourseId,liveCourseId);
        LiveCourseDescription liveCourseDescription = baseMapper.selectOne(wrapper);
        return liveCourseDescription;
    }
}
