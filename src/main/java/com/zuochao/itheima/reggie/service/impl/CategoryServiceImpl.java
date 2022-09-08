package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.common.CustomerException;
import com.zuochao.itheima.reggie.entity.Category;
import com.zuochao.itheima.reggie.entity.Dish;
import com.zuochao.itheima.reggie.entity.Setmeal;
import com.zuochao.itheima.reggie.mapper.CategoryMapper;
import com.zuochao.itheima.reggie.service.CategoryService;
import com.zuochao.itheima.reggie.service.DishService;
import com.zuochao.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zuo Chao
 * @date 2022/9/5 11:15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //抛出业务异常
            throw new CustomerException("当前分类关联了菜品，无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //抛出业务异常
            throw new CustomerException("当前分类关联了套餐，无法删除");
        }

        super.removeById(id);


    }
}
