package com.zuochao.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * @author Zuo Chao
 * @date 2022/9/4 14:24
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler (SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicated entry")){
            String[] split = ex.getMessage().split(" ");
            String errorMsg = split[2] + "已存在";
            return R.error(errorMsg);

        }

        return R.error("未知错误");

    }

    @ExceptionHandler(CustomerException.class)
    public R<String> exceptionHandler (CustomerException ex){
        log.error(ex.getMessage());



        return R.error(ex.getMessage());

    }
}
