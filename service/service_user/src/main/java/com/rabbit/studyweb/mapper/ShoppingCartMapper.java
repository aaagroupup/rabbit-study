package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
