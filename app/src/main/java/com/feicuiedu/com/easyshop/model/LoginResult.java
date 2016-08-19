package com.feicuiedu.com.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * 登录返回的实体类
 */
@SuppressWarnings("unused")
public class LoginResult {

    /*响应码*/
    private int code;
    /*响应消息*/
    @SerializedName("msg")
    private String message;
    /*环信的ID*/
    private User data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
