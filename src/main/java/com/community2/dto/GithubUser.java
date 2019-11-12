package com.community2.dto;

import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/9/25 21:37
 * @Version 1.0
 */
@Data
public class GithubUser {
    private String  name;
    private Long    id;
    private String  bio;
    private String  avatar_url;

}
