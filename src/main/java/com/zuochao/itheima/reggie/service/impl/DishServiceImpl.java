package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.dto.DishDto;
import com.zuochao.itheima.reggie.entity.Dish;
import com.zuochao.itheima.reggie.entity.DishFlavor;
import com.zuochao.itheima.reggie.mapper.DishMapper;
import com.zuochao.itheima.reggie.service.DishFlavorService;
import com.zuochao.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zuo Chao
 * @date 2022/9/5 15:19
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;



    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor: flavors) {
            dishFlavor.setDishId(dishId);
        }

        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {

        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

        this.updateById(dishDto);

        Long dishId = dishDto.getId();

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishId);
        dishFlavorService.remove(queryWrapper);


        List<DishFlavor> flavors = dishDto.getFlavors();

        for (DishFlavor dishFlavor: flavors) {
            dishFlavor.setDishId(dishId);
        }

        dishFlavorService.saveBatch(flavors);


    }
}
