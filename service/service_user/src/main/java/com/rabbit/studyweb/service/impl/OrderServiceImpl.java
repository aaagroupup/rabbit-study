package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.model.pojo.Course;
import com.rabbit.model.pojo.vo.OrderVo;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.mapper.OrderMapper;
import com.rabbit.model.pojo.Order;
import com.rabbit.model.pojo.ShoppingCart;
import com.rabbit.model.pojo.User;
import com.rabbit.studyweb.service.ICourseService;
import com.rabbit.studyweb.service.IOrderService;
import com.rabbit.studyweb.service.IShoppingCartService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-10-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private ICourseService courseService;

    @Override
    @Transactional
    public boolean submit(){
        Order order = new Order();
        //获得用户id
        Integer userId = TokenUtils.getCurrentUser().getId();
        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if(shoppingCarts==null||shoppingCarts.size()==0){
            return false;
        }else{
            //查询用户数据
            User user = TokenUtils.getCurrentUser();

            long orderId = IdWorker.getId();//订单号

            Double amount=0.00;
            //获取总金额
            List<Double> doubleList = shoppingCarts.stream().map(item -> item.getNumber() * item.getAmount()).collect(Collectors.toList());
            for (Double aDouble : doubleList) {
                amount+=aDouble;
            }
            String courseId="";
            //获取购物车里面的课程id
            for (ShoppingCart shoppingCart : shoppingCarts) {
                Long goodsId = shoppingCart.getGoodsId();
                courseId+=goodsId+",";
            }

            order.setNumber(String.valueOf(orderId));
            order.setOrderTime(LocalDateTime.now().toString());
            order.setAmount(amount);
            order.setUserId(userId);
            order.setUsername(user.getUsername());
            order.setTelephone(user.getTelephone());
            order.setStatus(Constants.order_status);
            order.setCourseId(courseId);
            //向订单表插入数据，一条数据
            this.save(order);

            //清空购物车数据
            return shoppingCartService.remove(wrapper);
        }
    }

    @Override
    public List<Order> getOrders(String query, int currentPage, int pageSize) {
        return orderMapper.getOrders(query,currentPage,pageSize);
    }

    //分页查询订单
    @Override
    public Map<String, Object> findPageOrder(int currentPage, int pageSize, OrderVo orderVo) {
        //获取条件值
        String number = orderVo.getNumber();
        String telephone = orderVo.getTelephone();
        String startTime = orderVo.getStartTime();
        String endTime = orderVo.getEndTime();
        String status = orderVo.getStatus();
        //判断条件是否为空，封装条件
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(number)){
            wrapper.eq(Order::getNumber,number);
        }
        if(!StringUtils.isEmpty(telephone)){
            wrapper.eq(Order::getTelephone,telephone);
        }
        if(!StringUtils.isEmpty(startTime)){
            wrapper.ge(Order::getOrderTime,startTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            wrapper.le(Order::getOrderTime,endTime);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq(Order::getStatus,status);
        }
        //调用方法实现条件分页查询
        IPage<Order> page=new Page<>(currentPage,pageSize);
        IPage<Order> orderIPage = orderMapper.selectPage(page, wrapper);
        List<Order> orderList = orderIPage.getRecords();
        int total =(int) this.count(wrapper);

        //将课程id改为课程标题
        for (Order order : orderList) {
            StringBuilder titles= new StringBuilder();
            if (order.getCourseId().contains(",")){
                String[] ids = order.getCourseId().split(",");
                for (String i : ids) {
                    Long id = Long.valueOf(i);
                    Course course = courseService.getById(id);
                    String title = course.getTitle();
                    titles.append(title).append(";");
                }
            }else {
                Long id = Long.valueOf(order.getCourseId());
                Course course = courseService.getById(id);
                String title = course.getTitle();
                titles.append(title).append(";");
            }
            order.getParam().put("titles", titles.toString());
        }
        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("orderList",orderList);
        return map;
    }

    @Override
    public boolean createOrder(Long id) {
        //先根据课程id查询出课程形成一条数据，并创建订单
        Course course = courseService.getById(id);

        long orderId = IdWorker.getId();//订单号
        Order order = new Order();
        User user = TokenUtils.getCurrentUser();
        order.setNumber(String.valueOf(orderId));
        order.setOrderTime(LocalDateTime.now().toString());
        order.setAmount(course.getPrice().doubleValue());
        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setTelephone(user.getTelephone());
        order.setStatus(Constants.order_status);
        order.setCourseId(course.getId().toString());
        return this.save(order);
    }

    @Override
    public List<Long> getPurchasedCourseList() {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId,TokenUtils.getCurrentUser().getId())
                .eq(Order::getStatus,"已支付");
        List<Order> orderList = baseMapper.selectList(wrapper);
        Set<Long> set = new HashSet<>();
        for (Order order : orderList) {
            if(order.getCourseId().contains(",")){
                String[] courseIds = order.getCourseId().split(",");
                for (String id : courseIds) {
                    Long courseId = Long.valueOf(id);
                    set.add(courseId);
                }
            }else{
                Long courseId = Long.valueOf(order.getCourseId());
                set.add(courseId);
            }
        }
        List<Long> list = new ArrayList<>(set);
        return list;
    }

    @Override
    public List<Order> getOrderListById(Integer userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId,userId);
        wrapper.orderByDesc(Order::getOrderTime);
        List<Order> orders = this.list(wrapper);
        //将课程id改为课程标题
        for (Order order : orders) {
            StringBuilder titles= new StringBuilder();
            if (order.getCourseId().contains(",")){
                String[] ids = order.getCourseId().split(",");
                for (String i : ids) {
                    Long id = Long.valueOf(i);
                    Course course = courseService.getById(id);
                    String title = course.getTitle();
                    titles.append(title).append(";");
                }
            }else {
                Long id = Long.valueOf(order.getCourseId());
                Course course = courseService.getById(id);
                String title = course.getTitle();
                titles.append(title).append(";");
            }
            order.getParam().put("titles", titles.toString());
        }
        return orders;
    }

}
