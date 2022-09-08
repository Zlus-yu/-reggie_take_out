package com.zuochao.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuochao.itheima.reggie.entity.AddressBook;
import com.zuochao.itheima.reggie.mapper.AddressBookMapper;
import com.zuochao.itheima.reggie.service.AddressBookService;
import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Service;

/**
 * @author Zuo Chao
 * @date 2022/9/7 11:01
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
