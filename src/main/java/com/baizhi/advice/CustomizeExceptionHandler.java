package com.baizhi.advice;

import com.alibaba.fastjson.JSON;
import com.baizhi.dto.ResultDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(HttpServletRequest request, Throwable throwable, Model model, HttpServletResponse response) {

        String contentType=request.getContentType();
        ResultDto resultDto=null;
        if("application/json".equals(contentType)){
            if(throwable instanceof CustomizeException){
                resultDto = ResultDto.errorOf((CustomizeException) throwable);
            }
            else{
                resultDto=ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        else{
            if(throwable instanceof CustomizeException){
                model.addAttribute("message",throwable.getMessage());
            }
            else{
                model.addAttribute("message","服务器冒烟~");
            }
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }

    }

}
