package com.baizhi.exception;

public enum CustomizeErrorCode implements ErrorCode {

    QUESTION_NOT_FOUNT("你找的问题丢了，该问题可能已经被删除");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
