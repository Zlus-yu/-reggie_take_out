package com.zuochao.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zuochao.itheima.reggie.common.R;
import com.zuochao.itheima.reggie.dto.DishDto;
import com.zuochao.itheima.reggie.entity.Category;
import com.zuochao.itheima.reggie.entity.Dish;
import com.zuochao.itheima.reggie.entity.DishFlavor;
import com.zuochao.itheima.reggie.service.CategoryService;
import com.zuochao.itheima.reggie.service.DishFlavorService;
import com.zuochao.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuo Chao
 * @date 2022/9/5 20:21
 */

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功！");
    }


    /**
     * 菜品分页展示
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = new ArrayList<>();

        for (Dish dish: records) {
            DishDto dishDto = new DishDto();

            Category category = categoryService.getById(dish.getCategoryId());

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            BeanUtils.copyProperties(dish, dishDto);



            list.add(dishDto);
        }

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);

    }

    /**
     * 回显菜品及口味
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return R.success("新增菜品成功！");
    }

    //@GetMapping("/list")
    //public R<List<Dish>> list(Dish dish){
    //    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    //    queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
    //    queryWrapper.eq(Dish::getStatus,1);
    //    queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //
    //    List<Dish> list = dishService.list(queryWrapper);
    //    return R.success(list);
    //}
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> dishDtoList = new ArrayList<>();

        for (Dish dishItem: list) {
            DishDto dishDto = new DishDto();

            Category category = categoryService.getById(dishItem.getCategoryId());

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            BeanUtils.copyProperties(dishItem, dishDto);

            //查询菜品口味
            Long dishItemId = dishItem.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishItemId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);


            dishDtoList.add(dishDto);
        }

        return R.success(dishDtoList);
    }




}
