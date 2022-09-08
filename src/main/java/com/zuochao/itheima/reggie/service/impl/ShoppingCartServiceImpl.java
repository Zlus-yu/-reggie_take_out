package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.entity.ShoppingCart;
import com.zuochao.itheima.reggie.mapper.ShoppingCartMapper;
import com.zuochao.itheima.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Zuo Chao
 * @date 2022/9/7 15:43
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
