package com.baizhi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;
    private Integer notifier;
    private Integer receiver;
    private Long outerId;
    private Integer type;
    private Long gmt_create;
    private Integer status;

}
