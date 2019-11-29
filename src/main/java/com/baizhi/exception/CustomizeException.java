package com.baizhi.exception;

public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException(CustomizeErrorCode errorCode) {

        this.message = errorCode.getMessage();

    }

    @Override
    public String getMessage() {
        return message;
    }
}
