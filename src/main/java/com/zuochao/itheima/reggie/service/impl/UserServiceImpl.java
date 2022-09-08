package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.entity.User;
import com.zuochao.itheima.reggie.mapper.UserMapper;
import com.zuochao.itheima.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Zuo Chao
 * @date 2022/9/6 20:26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
