package com.zuochao.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuochao.itheima.reggie.entity.Category;

/**
 * @author Zuo Chao
 * @date 2022/9/5 11:14
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
