package com.feicuiedu.com.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * 网络请求返回的状态值
 */
@SuppressWarnings("unused")
public class Result {

    private int code;
    @SerializedName("msg")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
