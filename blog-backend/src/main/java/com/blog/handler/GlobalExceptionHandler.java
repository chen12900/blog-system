package com.blog.handler;

import com.blog.utils.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntime(RuntimeException e) {
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleOther(Exception e) {
        return ApiResult.error("服务器内部错误");
    }
}
