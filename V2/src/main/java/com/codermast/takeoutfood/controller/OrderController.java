package com.codermast.takeoutfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codermast.takeoutfood.common.BaseContext;
import com.codermast.takeoutfood.common.R;
import com.codermast.takeoutfood.entity.Order;
import com.codermast.takeoutfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description: 订单管理控制器
 * @author: CoderMast
 * @date: 2022/11/27
 * @Blog: <a href="https://www.codermast.com/">codermast</a>
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * @Description: 获取订单信息列表
     * @param page 页码
     * @param pageSize 页面大小
     * @param number 订单号关键字
     * @param beginTime 开始日期
     * @param endTime 结束日期
     * @Author: CoderMast <a href="https://www.codermast.com/">...</a>
     */
    @GetMapping("/page")
    public R<Page<Order>> page(Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        Page<Order> pageInfo = new Page<>(page,pageSize);

        queryWrapper.like(StringUtils.isNotEmpty(number), Order::getNumber,number);
        queryWrapper.ge(ObjectUtils.isNotNull(beginTime), Order::getOrderTime,beginTime);
        queryWrapper.le(ObjectUtils.isNotNull(endTime), Order::getOrderTime,endTime);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * @Description: 分页获取用户订单
     * @param page 页码
     * @param pageSize  页面大小
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @GetMapping("/userPage")
    public R<Page<Order>> userPage(Integer page, Integer pageSize){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        Page<Order> pageInfo = new Page<>(page,pageSize);
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(Order::getUserId,userId);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * @Description: 提交订单
     * @param order  订单封装
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Order order){
        orderService.submit(order);
        return R.success("下单成功");
    }
}
