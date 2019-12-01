package com.baizhi.mapper;

import com.baizhi.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,content) values(#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{content})")
    public void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parent_id}")
    Comment selectByParentId(@Param("parent_id") Integer parent_id);

}
