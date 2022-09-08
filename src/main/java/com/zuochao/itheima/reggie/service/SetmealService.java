package com.zuochao.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuochao.itheima.reggie.dto.SetmealDto;
import com.zuochao.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author Zuo Chao
 * @date 2022/9/5 15:18
 */
public interface SetmealService extends IService<Setmeal> {


    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联关系
     * @param ids
     */
    public void removeWithDish(List<Long> ids);


    /**
     * 更改套餐状态
     * @param statusValue
     * @param id
     */
    public void updateStatue(Integer statusValue, Long id);
}
