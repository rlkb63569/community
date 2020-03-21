package com.baizhi.dto;

import com.baizhi.model.User;
import lombok.Data;

@Data
public class NotificationDto {

    private Long id;
    private Integer status;
    private Long gmt_create;
    private User user;
    private String outerTitle;
    private String notify;

}
