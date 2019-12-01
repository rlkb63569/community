package com.baizhi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Comment {

    private Long id;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;

}
