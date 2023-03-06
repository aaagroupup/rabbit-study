package com.rabbit.studyweb.live.service;

import com.rabbit.model.pojo.LiveCourseGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2023-02-08
 */
public interface ILiveCourseGoodsService extends IService<LiveCourseGoods> {

    List<LiveCourseGoods> getGoodsListByCourseId(Long courseId);
}
