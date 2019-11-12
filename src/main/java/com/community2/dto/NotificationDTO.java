package com.community2.dto;

import com.community2.model.User;
import lombok.Data;

/**
 * @Author weijia
 * @Date 2019/10/21 17:27
 * @Version 1.0
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private Long outerId;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
