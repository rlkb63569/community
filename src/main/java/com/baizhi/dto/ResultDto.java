package com.baizhi.dto;

import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultDto<T> {

    Integer code;
    String message;
    private T data;

    public static ResultDto errorOf(Integer code, String message) {

        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;

    }

    public static ResultDto errorOf(CustomizeErrorCode customizeErrorCode) {

        return errorOf(customizeErrorCode.getCode(), customizeErrorCode.getMessage());
    }

    public static ResultDto okOf() {

        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;

    }

    public static ResultDto errorOf(CustomizeException e) {

        return errorOf(e.getCode(), e.getMessage());

    }

    public static <T> ResultDto okOf(T data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(data);
        return resultDto;
    }

}
