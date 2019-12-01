package com.baizhi.service;


import com.baizhi.enums.CommentTypeEnum;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import com.baizhi.mapper.CommentMapper;
import com.baizhi.mapper.QuestionMapper;
import com.baizhi.model.Comment;
import com.baizhi.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {

        if(comment.getParent_id()==null || comment.getParent_id()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment dbComment=commentMapper.selectByParentId(comment.getParent_id());
            if(dbComment==null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
        }
        else{
            Question question = questionMapper.getById(comment.getParent_id());
            if(question==null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);
        }
    }

}
