package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.CountTime;
import com.rabbit.studyweb.mapper.CountTimeMapper;
import com.rabbit.studyweb.service.ICountTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2023-03-13
 */
@Service
public class CountTimeServiceImpl extends ServiceImpl<CountTimeMapper, CountTime> implements ICountTimeService {

    @Override
    public Map<String, Object> getCountAndTime() {
        List<CountTime> countTimeList = baseMapper.selectList(null);
        List<Integer> countList= new ArrayList<>();
        List<String> timeList=new ArrayList<>();
        countTimeList.forEach(countTime -> {
            countList.add(countTime.getCount());
            timeList.add(countTime.getTime());
        });
        Map<String,Object> map=new HashMap<>();
        map.put("count",countList);
        map.put("time",timeList);
        return map;
    }
}
