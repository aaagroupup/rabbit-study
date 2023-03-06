package com.rabbit.studyweb.controller;

import com.rabbit.model.pojo.vo.OrderVo;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.*;
import com.rabbit.studyweb.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(){
        boolean flag = orderService.submit();
        if(!flag){
            return R.error(Constants.shoppingCart_submitErr_MSG);
        }
        return R.success(Constants.shoppingCart_submitSuc_MSG);
    }

    /**
     * 获取所有订单
     * @param currentPage
     * @param pageSize
     * @param orderVo
     * @return
     */
    @ApiOperation("订单列表")
    @GetMapping("{currentPage}/{pageSize}")
    public R getOrderList(@PathVariable int currentPage, @PathVariable int pageSize , OrderVo orderVo){
        Map<String,Object> map= orderService.findPageOrder(currentPage,pageSize,orderVo);
        return R.success(map);
    }



    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        boolean flag = orderService.removeById(id);
        if(!flag){
            return  R.error(Constants.delErr_MSG);
        }
        return R.success(Constants.delSuc_MSG);
    }

    //获取用户所有订单
    @GetMapping("/getOrders")
    public R<List<Order>> getOrders(@RequestParam("userId") Integer userId){
       List<Order> orders=orderService.getOrderListById(userId);
        return R.success(orders);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/del/batch")
    public R<String> deleteBatch(@RequestBody List<Long> ids) {
        boolean flag = orderService.removeByIds(ids);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    //创建立即购买的订单
    @GetMapping("/createOrder/{id}")
    public R createOrder(@PathVariable Long id){
        boolean flag=orderService.createOrder(id);
        if(!flag){
            return R.error("订单创建失败");
        }
        return R.success("订单创建成功");
    }


}

