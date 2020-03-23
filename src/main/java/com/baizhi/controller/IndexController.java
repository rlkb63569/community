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

    @GetMapping({"/", "/index.html"})
    public String index(Model model,
                        @RequestParam(name = "search", defaultValue = ".") String search,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

        if(search.matches("\\s+")){
            search = ".";
        }
        PaginationDto pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }


}
