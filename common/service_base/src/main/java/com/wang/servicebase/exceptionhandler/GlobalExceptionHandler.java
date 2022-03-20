package com.wang.servicebase.exceptionhandler;

import com.wang.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常管理
    @ExceptionHandler(TreemanException.class)
    @ResponseBody//为了返回数据
    public R error(TreemanException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
