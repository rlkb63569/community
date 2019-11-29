package com.baizhi.advice;

import com.baizhi.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(HttpServletRequest request, Throwable throwable, Model model) {

        HttpStatus status = getStatus(request);
        if(throwable instanceof CustomizeException){
            model.addAttribute("message",throwable.getMessage());
        }
        else{
            model.addAttribute("message","服务器冒烟~");
        }
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;

    }

    private HttpStatus getStatus(HttpServletRequest request){

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
