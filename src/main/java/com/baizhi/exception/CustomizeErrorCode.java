package com.baizhi.exception;

public enum CustomizeErrorCode implements ErrorCode {

    QUESTION_NOT_FOUNT(2001,"你找的问题丢了，该问题可能已经被删除"),
    TARGET_PARAM_NOT_FOUNT(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"用户未登录，不能进行该操作"),
    SYSTEM_ERROR(2004,"服务器冒烟了"),
    COMMENT_NOT_FOUND(2005,"未找到相关问题或评论");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode(){
        return code;
    }

    private CustomizeErrorCode(String message) {
        this.message = message;
    }

    private CustomizeErrorCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

}
