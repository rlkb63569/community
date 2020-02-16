package com.baizhi.service;


import com.baizhi.dto.CommentCreateDto;
import com.baizhi.dto.CommentDto;
import com.baizhi.enums.CommentTypeEnum;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import com.baizhi.mapper.CommentMapper;
import com.baizhi.mapper.QuestionMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.model.Comment;
import com.baizhi.model.Question;
import com.baizhi.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {

        if(comment.getParent_id()==null || comment.getParent_id()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            List<Comment> dbComment=commentMapper.selectByParentId(comment.getParent_id());
            if(dbComment==null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
            commentMapper.incCommentCount(comment.getParent_id());
        }
        else{
            Question question = questionMapper.getById(comment.getParent_id());
            if(question==null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);
        }
    }

    public List<CommentDto> getByQuestionId(Integer id) {

        List<Comment> comments = commentMapper.selectByParentId(id);
        Set<Integer> creatorIds = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        ArrayList<User> users = new ArrayList<>();
        for (Integer creatorId : creatorIds) {
            User user = userMapper.findById(creatorId);
            users.add(user);
        }
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<CommentDto> commentDto = comments.stream().map(comment -> {
            CommentDto commentDto1 = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto1);
            commentDto1.setUser(userMap.get(comment.getCommentator()));
            return commentDto1;
        }).sorted((c1,c2)->{
            if(c1.getGmt_create()>c2.getGmt_create()){
                return -1;
            }else if(c1.getGmt_create()<c2.getGmt_create()){
                return 1;
            }else{
                return 0;
            }
        }).collect(Collectors.toList());
        return commentDto;

    }

    public List<CommentDto> getByCommentId(Long id) {

        List<Comment> comments = commentMapper.selectCommentByParentId(id);
        Set<Integer> creatorIds = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        ArrayList<User> users = new ArrayList<>();
        for (Integer creatorId : creatorIds) {
            User user = userMapper.findById(creatorId);
            users.add(user);
        }
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<CommentDto> commentDto = comments.stream().map(comment -> {
            CommentDto commentDto1 = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto1);
            commentDto1.setUser(userMap.get(comment.getCommentator()));
            return commentDto1;
        }).sorted((c1,c2)->{
            if(c1.getGmt_create()>c2.getGmt_create()){
                return -1;
            }else if(c1.getGmt_create()<c2.getGmt_create()){
                return 1;
            }else{
                return 0;
            }
        }).collect(Collectors.toList());
        return commentDto;
    }

}
