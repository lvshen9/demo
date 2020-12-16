package com.lvshen.demo.catchexception;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:26
 * @since JDK 1.8
 */
public class BusinessException extends RuntimeException {
    /** serialVersionUID*/
    private static final long serialVersionUID = 1L;

    private int code;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.FAIL.code();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.FAIL.code();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
