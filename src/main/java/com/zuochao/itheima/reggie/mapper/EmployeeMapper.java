package com.zuochao.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuochao.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zuo Chao
 * @date 2022/9/3 15:54
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
