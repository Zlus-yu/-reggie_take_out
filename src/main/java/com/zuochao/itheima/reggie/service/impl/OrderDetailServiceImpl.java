package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.entity.OrderDetail;
import com.zuochao.itheima.reggie.mapper.OrderDetailMapper;
import com.zuochao.itheima.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Zuo Chao
 * @date 2022/9/7 17:09
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
