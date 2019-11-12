package com.community2.enums;

/**
 * @Author weijia
 * @Date 2019/10/21 17:06
 * @Version 1.0
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
