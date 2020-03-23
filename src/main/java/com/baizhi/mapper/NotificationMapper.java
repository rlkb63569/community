package com.baizhi.mapper;

import com.baizhi.model.Notification;
import com.baizhi.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,status) values (#{notifier},#{receiver},#{outerId},#{type},#{gmt_create},#{status})")
    void insert(Notification notification);

    @Select("select count(id) from notification where type=0 and receiver=#{receiver}")
    Integer countById(Integer receiver);

    @Select("select * from notification where receiver =#{receiver} limit #{offset},#{size}")
    List<Notification> listById(Integer receiver, Integer offset, Integer size);
}
