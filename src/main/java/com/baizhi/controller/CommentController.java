package com.baizhi.controller;

import com.baizhi.dto.CommentCreateDto;
import com.baizhi.dto.CommentDto;
import com.baizhi.dto.ResultDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.model.Comment;
import com.baizhi.model.User;
import com.baizhi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/add",method= RequestMethod.POST)
    public Object post(CommentCreateDto commentCreateDto, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(StringUtils.isEmpty(commentCreateDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.COMMENT_IS_NULL);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentCreateDto.getParent_id());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(1);
        commentService.insert(comment);
        ResultDto resultDto = ResultDto.okOf();
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public ResultDto<List<CommentDto>> comment(@PathVariable(name="id") Long id){
        List<CommentDto> commentDtos = commentService.getByCommentId(id);
        return ResultDto.okOf(commentDtos);
    }

}
