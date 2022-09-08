package com.zuochao.itheima.reggie.controller;

import com.zuochao.itheima.reggie.common.R;
import com.zuochao.itheima.reggie.entity.Orders;
import com.zuochao.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

/**
 * @author Zuo Chao
 * @date 2022/9/7 17:10
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        log.info("订单信息：{}", orders.toString());


        ordersService.submit(orders);

        return R.success("下单成功");
    }
}
