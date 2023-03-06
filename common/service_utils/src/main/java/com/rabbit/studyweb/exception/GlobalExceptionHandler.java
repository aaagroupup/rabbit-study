package com.rabbit.studyweb.exception;

import com.rabbit.studyweb.result.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice //aop
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error("执行全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error("执行ArithmeticException异常处理");
    }

    //自定义异常处理StudyWebException
    @ExceptionHandler(StudyWebException.class)
    @ResponseBody
    public R error(StudyWebException e){
        e.printStackTrace();
        return R.error(e.getMsg());
    }
}
