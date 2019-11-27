package com.baizhi.dto;

import com.baizhi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator_id;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String tags;
    private User user;
}
