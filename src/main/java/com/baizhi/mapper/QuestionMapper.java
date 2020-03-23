package com.baizhi.mapper;

import com.baizhi.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator_id,tags) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator_id},#{tags})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from question where title regexp #{search}")
    Integer count(@Param("search")String search);

    @Select("select count(*) from question where id=#{id}")
    Integer countById(@Param("id") Integer id);

    @Select("select * from question where id=#{id} limit #{offset},#{size}")
    List<Question> listById(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id=#{id}")
    Question getById(Integer id);

    @Update("update question set title=#{title},description=#{description},tags=#{tags},gmt_modified=#{gmt_modified} where id=#{id}")
    void edit(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateViewCount(Integer id);

    @Update("update question set comment_count = comment_count+1 where id=#{id}")
    void incCommentCount(Question question);

    @Select("select * from question where id!=#{id} and tags regexp #{tags}")
    List<Question> selectRelated(Question question);
}
