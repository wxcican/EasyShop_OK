package com.feicuiedu.com.easyshop.network;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 此接口中包含了易淘的所有网络连接路径
 */
public interface EasyShopApi {

    String BASE_URL = "http://192.168.1.37:8080/yitao/";
    String LOGIN = "UserWeb?method=login";
    String REGISTER = "UserWeb?method=register";
    String ALL_GOODS = "GoodsServlet?method=getAll";
    String IMAGE_URL = "http://192.168.1.37:8080/";
    String UPDATE = "UserWeb?method=update";

    @Multipart
    @POST("/UserWeb?method=update")
    Call<ResponseBody> update(@Part("username") String username,
                              @Part("nickname") String nickname, @Part("other") File file);
}
