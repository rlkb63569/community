package com.baizhi.controller;

import com.baizhi.dto.CommentDto;
import com.baizhi.dto.ResultDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.model.Comment;
import com.baizhi.model.User;
import com.baizhi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/add",method= RequestMethod.POST)
    public Object post(CommentDto commentDto,
                       HttpServletRequest request
                       ){

        User user = (User) request.getSession().getAttribute("user");
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
