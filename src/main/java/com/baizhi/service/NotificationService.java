package com.baizhi.service;

import com.baizhi.dto.NotificationDto;
import com.baizhi.dto.PaginationDto;
import com.baizhi.mapper.NotificationMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.model.Notification;
import com.baizhi.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDto<NotificationDto> list(Integer receiver, Integer page, Integer size) {
        Integer totalCount = notificationMapper.countById(receiver);
        PaginationDto<NotificationDto> paginationDto = new PaginationDto<>();
        page = paginationDto.setPagination(totalCount, page, size);
        Integer offset = (page - 1) * size;
        List<Notification> notifications = notificationMapper.listById(receiver, offset, size);
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (Notification notification : notifications) {
            User user = userMapper.findById(notification.getReceiver());
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setUser(user);
            notificationDtoList.add(notificationDto);
        }
        paginationDto.setData(notificationDtoList);
        return paginationDto;
    }

}
