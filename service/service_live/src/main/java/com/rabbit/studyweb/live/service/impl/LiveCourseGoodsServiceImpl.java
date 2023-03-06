package com.rabbit.studyweb.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.LiveCourseGoods;
import com.rabbit.studyweb.live.mapper.LiveCourseGoodsMapper;
import com.rabbit.studyweb.live.service.ILiveCourseGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods> implements ILiveCourseGoodsService {

    @Override
    public List<LiveCourseGoods> getGoodsListByCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseGoods::getLiveCourseId,courseId);
        List<LiveCourseGoods> liveCourseGoodsList = baseMapper.selectList(wrapper);
        return liveCourseGoodsList;
    }
}
