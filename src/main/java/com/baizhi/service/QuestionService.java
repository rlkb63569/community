package com.baizhi.service;

import com.baizhi.dto.PaginationDto;
import com.baizhi.dto.QuestionDto;
import com.baizhi.exception.CustomizeErrorCode;
import com.baizhi.exception.CustomizeException;
import com.baizhi.mapper.QuestionMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.model.Question;
import com.baizhi.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto<QuestionDto> list(String search, Integer page, Integer size) {
        if(!search.equals(".")){
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        Integer totalCount = questionMapper.count(search);
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();
        page = paginationDto.setPagination(totalCount, page, size);
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator_id());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setData(questionDtoList);
        return paginationDto;
    }

    public PaginationDto<QuestionDto> list(Integer id, Integer page, Integer size) {
        Integer totalCount = questionMapper.countById(id);
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();
        page = paginationDto.setPagination(totalCount, page, size);
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.listById(id, offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator_id());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setData(questionDtoList);
        return paginationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
        User user = userMapper.findById(question.getCreator_id());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(user);
        return questionDto;

    }

    public void createOrUpdate(Question question) {
        if (org.springframework.util.StringUtils.isEmpty(question.getId())) {
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        } else {
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.edit(question);
        }
    }

    public void incView(Integer id) {
        questionMapper.updateViewCount(id);
    }

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        if (StringUtils.isEmpty(questionDto.getTags())) {
            return questionDtos;
        }
        String tag = questionDto.getTags().replace(',', '|');
        Question question = new Question();
        question.setTags(tag);
        question.setId(questionDto.getId());
        List<Question> questions = questionMapper.selectRelated(question);
        if (questions.size() != 0) {
            questionDtos = questions.stream().map(q -> {
                QuestionDto questionDto1 = new QuestionDto();
                BeanUtils.copyProperties(q, questionDto1);
                return questionDto1;
            }).limit(10).collect(Collectors.toList());
        }
        return questionDtos;
    }
}
