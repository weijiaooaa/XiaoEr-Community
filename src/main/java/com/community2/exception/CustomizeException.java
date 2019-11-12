package com.community2.exception;

/**
 * @Author weijia
 * @Date 2019/9/16 20:04
 * @Version 1.0
 */
public class CustomizeException extends RuntimeException {

    private String message;
    private  Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return code;
    }
}
