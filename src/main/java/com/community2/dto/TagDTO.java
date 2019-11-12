package com.community2.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author weijia
 * @Date 2019/10/20 22:36
 * @Version 1.0
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
