package com.baizhi.controller;

import com.baizhi.dto.CommentDto;
import com.baizhi.dto.QuestionDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import com.baizhi.service.CommentService;
import com.baizhi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id, Model model){

        questionService.incView(id);
        QuestionDto questionDto = questionService.getById(id);
        if(questionDto==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
        List<CommentDto> commentDto = commentService.getByQuestionId(questionDto.getId());
        model.addAttribute("question",questionDto);
        model.addAttribute("comment",commentDto);
        return "question";

    }

}
