package com.baizhi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class AccessTokenDto {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
