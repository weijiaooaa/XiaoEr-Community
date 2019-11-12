package com.community2.dto;

import com.community2.model.User;
import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/10/11 16:02
 * @Version 1.0
 */
@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer likeCount;

    private Integer commentCount;

    private String content;

    private User user;
}
