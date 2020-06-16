package com.calm.sqlrunner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 校验错误拦截处理.
     *
     * @param req Request请求
     * @param e 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Mono<ExceptionResult>> validationBodyException(ServerHttpRequest req,
            MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("请求参数格式不正确：");
        if (result.hasErrors()) {
            errorMessage.append(result.getAllErrors().get(0).getDefaultMessage());
            log.warn("【SqlRunner服务中全局捕获的异常】, Host {}, invokes url {},参数校验未通过：{}", req.getRemoteAddress(),
                    req.getURI(), errorMessage);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(new ExceptionResult(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage.toString())));
    }

    /**
     * SqlRunnerException 异常的处理
     * @param req http 请求对象
     * @param e 异常实例
     * @return 异常信息响应体
     */
    @ExceptionHandler(SqlRunnerException.class)
    public ResponseEntity<Mono<ExceptionResult>> extractExceptionHandler(ServerHttpRequest req, SqlRunnerException e) {
        log.error("【SqlRunner服务中全局捕获的异常】, Host {}, invokes url {}", req.getRemoteAddress(),
                req.getURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(new ExceptionResult(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
    }

}
