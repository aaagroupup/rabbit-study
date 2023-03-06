package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select * from sys_order where number like concat('%',replace(#{query},' ',''),'%') limit #{currentPage},#{pageSize}")
    List<Order> getOrders(String query, int currentPage, int pageSize);
}
