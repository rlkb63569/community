package com.baizhi.controller;

import com.baizhi.dto.PaginationDto;
import com.baizhi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/","/index.html"})
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size){

        PaginationDto pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }


}