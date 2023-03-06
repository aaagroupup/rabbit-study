package com.rabbit.studyweb.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.LiveCourseConfig;
import com.rabbit.studyweb.live.mapper.LiveCourseConfigMapper;
import com.rabbit.studyweb.live.service.ILiveCourseConfigService;
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
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig> implements ILiveCourseConfigService {

    //根据课程id查询出配置信息
    @Override
    public LiveCourseConfig getCourseConfigByCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseConfig::getLiveCourseId,courseId);
        LiveCourseConfig liveCourseConfig = baseMapper.selectOne(wrapper);
        return liveCourseConfig;
    }
}
