package com.calm.sqlrunner.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 异常结果类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@Getter
public class ExceptionResult implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 333952135763892852L;

    /**
     * 响应的异常 HTTP Code 值.
     */
    private int code;

    /**
     * 响应的异常 message 值.
     */
    private String message;

    /**
     * 构造方法.
     *
     * @param httpStatus HTTP状态码
     * @param message 消息
     */
    ExceptionResult(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

}
