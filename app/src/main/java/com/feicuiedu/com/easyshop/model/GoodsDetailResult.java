package com.feicuiedu.com.easyshop.model;


import com.google.gson.annotations.SerializedName;

/**
 * 商品展示对应返回的实体类
 */
@SuppressWarnings("unused")
public class GoodsDetailResult {

    private int code;
    @SerializedName("msg")
    private String message;
    private GoodsDetail datas;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GoodsDetail getDatas() {
        return datas;
    }
}