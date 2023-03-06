package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.Order;
import com.rabbit.model.pojo.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
public interface IOrderService extends IService<Order> {

    boolean submit();

    List<Order> getOrders(String query, int currentPage, int pageSize);

    Map<String, Object> findPageOrder(int currentPage, int pageSize, OrderVo orderVo);

    boolean createOrder(Long id);

    List<Long> getPurchasedCourseList();

    List<Order> getOrderListById(Integer userId);
}
