package com.community2.exception;

/**
 * @Author weijia
 * @Date 2019/10/7 15:13
 * @Version 1.0
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论，无法进行回复!!!"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录!!!"),
    SYS_ERROR(2004,"服务器冒烟了，待会儿再试试!!!"),
    TYPE_PARAM_WRONG (2005,"评论类型错误或不存在!!!"),
    COMMENT_NOT_FOUND(2006,"回复的评论不在了，要不要换一个试试？"),
    CONTENT_IS_EMPTY(2007,"回复的内容不能为空哦，请重新输入!!!"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2008,"消息莫非不翼而飞了？"),
    ;




    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
