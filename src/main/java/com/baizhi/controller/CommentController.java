package com.baizhi.controller;

import com.baizhi.dto.CommentDto;
import com.baizhi.dto.ResultDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import com.baizhi.mapper.CommentMapper;
import com.baizhi.model.Comment;
import com.baizhi.model.User;
import com.baizhi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method= RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest request,
                       @SessionAttribute("user") User user
                       ){

        if(user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentDto.getParent_id());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(1);
        commentService.insert(comment);
        ResultDto resultDto = ResultDto.okOf();
        return resultDto;

    }
}
