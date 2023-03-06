package com.rabbit.studyweb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.ShoppingCart;
import com.rabbit.studyweb.service.IShoppingCartService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.delErr_MSG;
import static com.rabbit.studyweb.common.Constants.delSuc_MSG;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService shoppingCartService;

     // 新增或者更新
     @PostMapping
     public R<String> save(@RequestBody ShoppingCart shoppingCart) {
         //增加前先判断购物车是否存在该商品
         Long goodsId = shoppingCart.getGoodsId();
         LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(ShoppingCart::getGoodsId,goodsId);
         ShoppingCart one = shoppingCartService.getOne(wrapper);
         if(one!=null){
            //存在,则在该商品的数量上+1
             one.setNumber(one.getNumber()+1);
             shoppingCartService.saveOrUpdate(one);
             return R.success(Constants.add_shoppingCart_MSG);
         }else{
             //不存在，则添加到购物车上
             boolean flag = shoppingCartService.saveOrUpdate(shoppingCart);
             if(!flag){
                 return R.error(Constants.add_shoppingCartErr_MSG);
             }
             return R.success(Constants.add_shoppingCartSuc_MSG);
         }

     }

      @DeleteMapping("/{id}")
      public R<String> delete(@PathVariable Integer id) {
          boolean flag = shoppingCartService.removeById(id);
          if(!flag){
              return R.error(Constants.delErr_MSG);
          }
          return R.success(Constants.delSuc_MSG);
      }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/del/batch")
    public R<String> deleteBatch(@RequestBody List<Integer> ids) {

        boolean flag = shoppingCartService.removeByIds(ids);
        if(flag){
            return R.success(delSuc_MSG);

        }
        return R.error(delErr_MSG);
    }

      @GetMapping
      public R<List<ShoppingCart>> findAll() {
         //获取该用户id
          Integer userId = TokenUtils.getCurrentUser().getId();
          LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
          wrapper.eq(ShoppingCart::getUserId,userId);
          return R.success(shoppingCartService.list(wrapper));
      }

      @GetMapping("/getNumber")
      public R<Integer> getNumber(@RequestParam Integer userId){
            Integer number=0;

            LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getUserId,userId);
            List<ShoppingCart> goodsList = shoppingCartService.list(wrapper);
            for (ShoppingCart shoppingCart : goodsList) {
                number+=shoppingCart.getNumber();
            }
            return R.success(number);
     }

     //根据id更改数量
     @PutMapping("updateNumber/{id}/{number}")
     public R updateNumber(@PathVariable Long id,@PathVariable int number){
         boolean save = shoppingCartService.updateNumberById(id,number);
         if(!save){
             return R.error("修改出错了！");
         }
         return R.success("数量修改成功");
     }

    @GetMapping("/getShoppingCart")
    public R getShoppingCart(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCart();
        return R.success(shoppingCarts);
    }

}

