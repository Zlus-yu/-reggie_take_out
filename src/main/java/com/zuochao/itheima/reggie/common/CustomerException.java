package com.zuochao.itheima.reggie.common;

/**
 * 自定义异常
 * @author Zuo Chao
 * @date 2022/9/5 15:57
 */
public class CustomerException extends RuntimeException{
    public CustomerException(String msg){
        super(msg);
    }
}
