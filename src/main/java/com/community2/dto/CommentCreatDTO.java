package com.community2.dto;

import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/10/7 19:26
 * @Version 1.0
 */
@Data
public class CommentCreatDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
