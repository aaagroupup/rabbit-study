package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.ShoppingCartMapper;
import com.rabbit.model.pojo.ShoppingCart;
import com.rabbit.studyweb.service.IShoppingCartService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {
    @Override
    public boolean updateNumberById(Long id, int number) {
        ShoppingCart shoppingCart = baseMapper.selectById(id);
        shoppingCart.setNumber(number);
        int i = baseMapper.updateById(shoppingCart);
        return i>0;
    }

    @Override
    public List<ShoppingCart> getShoppingCart() {
        Integer id = TokenUtils.getCurrentUser().getId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> shoppingCarts = baseMapper.selectList(wrapper);
        return shoppingCarts;
    }
}
