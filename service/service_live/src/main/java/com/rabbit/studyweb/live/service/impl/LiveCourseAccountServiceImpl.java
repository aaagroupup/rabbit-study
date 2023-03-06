package com.rabbit.studyweb.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.LiveCourseAccount;
import com.rabbit.studyweb.live.mapper.LiveCourseAccountMapper;
import com.rabbit.studyweb.live.service.ILiveCourseAccountService;
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
public class LiveCourseAccountServiceImpl extends ServiceImpl<LiveCourseAccountMapper, LiveCourseAccount> implements ILiveCourseAccountService {

    @Override
    public LiveCourseAccount getLiveCourseAccountById(Long courseId) {
        LambdaQueryWrapper<LiveCourseAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseAccount::getLiveCourseId,courseId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public String getZhuboPasswordByCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseAccount::getLiveCourseId,id);
        String password = baseMapper.selectOne(wrapper).getZhuboPassword();
        return password;
    }
}
