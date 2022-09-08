package com.zuochao.itheima.reggie.controller;

import com.zuochao.itheima.reggie.entity.Orders;
import com.zuochao.itheima.reggie.service.OrderDetailService;
import com.zuochao.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zuo Chao
 * @date 2022/9/7 17:14
 */
@RestController
@Slf4j
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;



}
