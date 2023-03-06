package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.ShoppingCart;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
public interface IShoppingCartService extends IService<ShoppingCart> {
    boolean updateNumberById(Long id, int number);

    List<ShoppingCart> getShoppingCart();
}
