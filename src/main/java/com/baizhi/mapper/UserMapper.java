package com.baizhi.mapper;

import com.baizhi.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Delete("delete from user where token=#{token}")
    void deleteUserByToken(@Param("token") String token);

    @Select("select * from user where id= #{creator_id}")
    User findById(@Param("creator_id") Integer creator_id);

    @Select("select * from user where account_id=#{account_id}")
    User findByAccountId(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmt_modified},avatar_url=#{avatar_url} where id=#{id}")
    void update(User dbUser);
}
