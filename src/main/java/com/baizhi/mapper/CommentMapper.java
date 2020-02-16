package com.baizhi.mapper;

import com.baizhi.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,content) values(#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parent_id} and type=1")
    List<Comment> selectByParentId(@Param("parent_id") Integer parent_id);

    @Select("select * from comment where parent_id=#{parent_id} and type=2")
    List<Comment> selectCommentByParentId(@Param("parent_id")Long parent_id);

    @Update("update from comment set comment_count = comment_count+1 where id =#{parent_id} and type=1")
    void incCommentCount(Integer parent_id);
}
