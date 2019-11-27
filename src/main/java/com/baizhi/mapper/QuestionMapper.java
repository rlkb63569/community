package com.baizhi.mapper;

import com.baizhi.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator_id,tags) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator_id},#{tags})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where id=#{id}")
    Integer countById(@Param("id") Integer id);

    @Select("select * from question where id=#{id} limit #{offset},#{size}")
    List<Question> listById(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);

}
