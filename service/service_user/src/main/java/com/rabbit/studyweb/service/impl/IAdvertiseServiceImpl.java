package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.model.pojo.Advertise;
import com.rabbit.studyweb.mapper.AdvertiseMapper;
import com.rabbit.studyweb.service.IAdvertiseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAdvertiseServiceImpl extends ServiceImpl<AdvertiseMapper, Advertise> implements IAdvertiseService {
    @Override
    public List<Advertise> getBanners() {
        LambdaQueryWrapper<Advertise> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Advertise::getStatus,1).orderByAsc(Advertise::getId);
        return baseMapper.selectList(wrapper);
    }
}
