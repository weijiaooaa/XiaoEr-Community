package com.community2.service;

import com.community2.dto.CommentDTO;
import com.community2.enums.CommentTypeEnum;
import com.community2.model.Comment;
import com.community2.model.User;

import java.util.List;

/**
 * @Author weijia
 * @Date 2019/10/7 19:31
 * @Version 1.0
 */
public interface CommentService {

    void insert(Comment comment, User user);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);
}
