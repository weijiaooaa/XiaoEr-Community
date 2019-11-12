package com.community2.dto;

import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/9/25 20:01
 * @Version 1.0
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
