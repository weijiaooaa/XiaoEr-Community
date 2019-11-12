package com.community2.dto;

import com.community2.model.User;
import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/9/26 19:56
 * @Version 1.0
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title ;
    private String description ;
    private String tag ;
    private Long gmtCreate;
    private Long gmtModified ;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
