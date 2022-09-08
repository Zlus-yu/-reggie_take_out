package com.zuochao.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuochao.itheima.reggie.dto.DishDto;
import com.zuochao.itheima.reggie.entity.Dish;
import com.zuochao.itheima.reggie.entity.DishFlavor;

/**
 * @author Zuo Chao
 * @date 2022/9/5 15:18
 */
public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
