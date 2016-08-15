package com.feicuiedu.com.yitao.model;

/**
 * 登录返回的实体类
 */
@SuppressWarnings("unused")
public class LoginResult {

    /*响应码*/
    private int code;
    /*响应消息*/
    private String msg;
    /*环信的ID*/
    private String name;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
}
