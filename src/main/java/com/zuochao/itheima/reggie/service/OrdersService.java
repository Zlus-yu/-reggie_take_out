package com.zuochao.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuochao.itheima.reggie.entity.Orders;

/**
 * @author Zuo Chao
 * @date 2022/9/7 17:12
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);
}
