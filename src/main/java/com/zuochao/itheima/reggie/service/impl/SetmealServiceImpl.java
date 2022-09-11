package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.common.CustomerException;
import com.zuochao.itheima.reggie.dto.SetmealDto;
import com.zuochao.itheima.reggie.entity.Setmeal;
import com.zuochao.itheima.reggie.entity.SetmealDish;
import com.zuochao.itheima.reggie.mapper.SetmealMapper;
import com.zuochao.itheima.reggie.service.DishService;
import com.zuochao.itheima.reggie.service.SetmealDishService;
import com.zuochao.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zuo Chao
 * @date 2022/9/5 15:20
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishService dishService;

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        this.save(setmealDto);

        Long setmealId = setmealDto.getId();

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        for (SetmealDish setmealDish:  setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }

        setmealDishService.saveBatch(setmealDishes);


    }

    /**
     * 删除套餐，同时删除套餐和菜品的关联关系
     * @param ids
     */
    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //套餐正在售卖，不能删除,抛出异常
            throw new CustomerException("套餐正在售卖，不能删除");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);


    }

    /**
     * 更改套餐状态
     * @param statusValue
     * @param id
     */
    @Override
    public void updateStatue(Integer statusValue, List<Long> ids) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> setmeals = this.list(queryWrapper);

        if(statusValue == 0){
            for (Setmeal setmeal : setmeals) {
                setmeal.setStatus(statusValue);
                this.updateById(setmeal);
            }

            return;
        }



        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapperqueryWrapper;

        for (Long id : ids) {
            setmealDishLambdaQueryWrapperqueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapperqueryWrapper.eq(SetmealDish::getSetmealId, id);
            List<SetmealDish> dishList = setmealDishService.list(setmealDishLambdaQueryWrapperqueryWrapper);

            for (SetmealDish dish: dishList) {
                Long dishId = dish.getDishId();
                Integer status = dishService.getById(dishId).getStatus();
                if (status == 0){
                    throw new CustomerException("所选套餐已有菜品停售，无法启售");
                }
            }

        }

        for (Setmeal setmeal : setmeals) {
            setmeal.setStatus(statusValue);
            this.updateById(setmeal);
        }




    }
}
