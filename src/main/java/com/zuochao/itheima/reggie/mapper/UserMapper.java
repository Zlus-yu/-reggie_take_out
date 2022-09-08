package com.zuochao.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuochao.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zuo Chao
 * @date 2022/9/6 20:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
