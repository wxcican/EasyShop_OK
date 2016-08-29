package com.feicuiedu.com.easyshop.network;

import java.util.List;

import okhttp3.MultipartBody;
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
    String UPDATE = "UserWeb?method=update";
    String IMAGE_URL = "http://192.168.1.37:8080";
    String ALL_GOODS = "GoodsServlet?method=getAll";
    String ADD = "GoodsServlet?method=add";
    String DETAIL = "GoodsServlet?method=view";
    String DELETE = "GoodsServlet?method=delete";

    @Multipart
    @POST("UserWeb?method=update")
    Call<ResponseBody> update(@Part("user") String username,
                              @Part MultipartBody.Part file);

    @Multipart
    @POST("GoodsServlet?method=add")
    Call<ResponseBody> goodsAdd(@Part("good") String good_info,
                                @Part List<MultipartBody.Part> files);
}

