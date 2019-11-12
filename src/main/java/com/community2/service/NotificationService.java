package com.community2.service;

import com.community2.dto.NotificationDTO;
import com.community2.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @Author weijia
 * @Date 2019/10/21 17:32
 * @Version 1.0
 */
public interface NotificationService {
    PageInfo<NotificationDTO> getByUserId(Integer pageNum, Integer pageSize, Long id);

    Long unreadCount(Long id);

    NotificationDTO read(Long id, User user);
}
