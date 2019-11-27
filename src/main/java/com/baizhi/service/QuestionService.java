package com.baizhi.service;

import com.baizhi.dto.PaginationDto;
import com.baizhi.dto.QuestionDto;
import com.baizhi.mapper.QuestionMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.model.Question;
import com.baizhi.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size){

        Integer totalCount=questionMapper.count();
        PaginationDto paginationDto=new PaginationDto();
        page= paginationDto.setPagination(totalCount, page, size);
        Integer offset=(page-1)*size;
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator_id());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }

    public PaginationDto list(Integer id, Integer page, Integer size) {

        Integer totalCount=questionMapper.countById(id);
        PaginationDto paginationDto=new PaginationDto();
        page= paginationDto.setPagination(totalCount, page, size);
        Integer offset=(page-1)*size;
        List<Question> questions = questionMapper.listById(id,offset,size);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator_id());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }

    public QuestionDto getById(Integer id) {

        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreator_id());
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;

    }

    public void createOrUpdate(Question question) {

        if(question.getId()==null){
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        }
        else{
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.edit(question);
        }
    }
}
