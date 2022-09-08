package com.zuochao.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zuochao.itheima.reggie.common.R;
import com.zuochao.itheima.reggie.dto.SetmealDto;
import com.zuochao.itheima.reggie.entity.Category;
import com.zuochao.itheima.reggie.entity.Setmeal;
import com.zuochao.itheima.reggie.service.CategoryService;
import com.zuochao.itheima.reggie.service.SetmealDishService;
import com.zuochao.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuo Chao
 * @date 2022/9/6 15:19
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }


    /**
     * 查询套餐信息，进行回显
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){

        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();


        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = new ArrayList<>();

        for (Setmeal setmeal: records) {
            SetmealDto setmealDto = new SetmealDto();

            Category category = categoryService.getById(setmeal.getCategoryId());
            if (category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            BeanUtils.copyProperties(setmeal, setmealDto);

            list.add(setmealDto);

        }

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);


    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids){
        log.info(ids.toString());

        setmealService.removeWithDish(ids);
        return R.success("删除成功！");
    }

    @PostMapping("/status/{value}")
    public R<String> updateStatus(@PathVariable("value") Integer statusValue, @RequestParam("ids") Long id){
        log.info("value = {}, ids = {}", statusValue, id);

        setmealService.updateStatue(statusValue, id);

        return R.success("状态更改成功！");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(queryWrapper);

        return R.success(setmealList);

    }






}
