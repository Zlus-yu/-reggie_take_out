package com.zuochao.itheima.reggie.dto;

import com.zuochao.itheima.reggie.entity.Setmeal;
import com.zuochao.itheima.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
