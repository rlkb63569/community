package com.baizhi.controller;

import com.baizhi.mapper.QuestionMapper;
import com.baizhi.model.Question;
import com.baizhi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping({"/publish","publish.html"})
    public String publish(){

        return "publish";
    }

    @ResponseBody
    @PostMapping("/publish")
    public Map<String,String> publish(Question question, HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Map<String,String> map=new HashMap<>();
        if (user == null) {
            map.put("detail", "用户未登录！请登录以继续发布问题");
            map.put("msg","发布失败");
            return map;
        }
        if (question.getTitle() == null || "".equals(question.getTitle())) {
            map.put("detail", "标题不能为空，请填写标题");
            map.put("msg","发布失败");
            return map;
        }
        if (question.getDescription() == null || "".equals(question.getDescription())) {
            map.put("detail", "问题描述不能为空，请填写问题描述");
            map.put("msg","发布失败");
            return map;
        }
        if (question.getTags() == null || "".equals(question.getTags())) {
            map.put("detail", "标签不能为空，请填写至少一个标签");
            map.put("msg","发布失败");
            return map;
        }

        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        question.setCreator_id(user.getId());
        questionMapper.create(question);
        map.put("msg","发布成功");
        return map;

    }
}
