package com.calm.sqlrunner.exception;

/**
 * SqlRunner的运行时异常类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
public class SqlRunnerException extends RuntimeException{

    private static final long serialVersionUID = 6176670067346874435L;

    /**
     * no args.
     */
    public SqlRunnerException() {
        super();
    }

    /**
     * 通过 message 消息来构造 SqlRunnerException 实例的构造方法.
     *
     * @param message tips
     */
    public SqlRunnerException(String message) {
        super(message);
    }

    /**
     * SqlRunnerException.
     *
     * @param cause 异常实例
     * @param message String
     */
    public SqlRunnerException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
