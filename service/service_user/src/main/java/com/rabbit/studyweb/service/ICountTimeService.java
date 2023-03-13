package com.rabbit.studyweb.service;

import com.rabbit.model.pojo.CountTime;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-03-13
 */
public interface ICountTimeService extends IService<CountTime> {

    Map<String, Object> getCountAndTime();
}
