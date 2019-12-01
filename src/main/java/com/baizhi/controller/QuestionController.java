package com.baizhi.controller;

import com.baizhi.dto.QuestionDto;
import com.baizhi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id, Model model){

        questionService.incView(id);
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("question",questionDto);
        return "question";

    }

}
