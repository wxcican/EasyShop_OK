package com.feicuiedu.com.easyshop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户实体类
 */
@SuppressWarnings("unused")
public class User implements Serializable {

    @SerializedName("username")
    private String name;
    @SerializedName("name")
    private String hx_Id;
    @SerializedName("uuid")
    private String table_Id;
    @SerializedName("other")
    private String head_Image;
    @SerializedName("nickname")
    private String nick_Name;

    public String getName() {
        return name;
    }

    public String getHx_Id() {
        return hx_Id;
    }

    public String getTable_Id() {
        return table_Id;
    }

    public String getHead_Image() {
        return head_Image;
    }

    public String getNick_Name() {
        return nick_Name;
    }

    public void setNick_Name(String nick_Name) {
        this.nick_Name = nick_Name;
    }

}
