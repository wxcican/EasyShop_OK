package com.feicuiedu.com.easyshop.network;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EasyClient implements EasyShopApi {


    private static EasyClient sClient;

    public static EasyClient getInstance() {
        if (sClient == null) {
            sClient = new EasyClient();
        }

        return sClient;
    }

    private final EasyShopApi easyShopApi;

    private EasyClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 设置url根节点
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();

        // 由Retrofit来负责GitHubApi接口的实现
        easyShopApi = retrofit.create(EasyShopApi.class);
    }

    @Override
    public Call<ResponseBody> update(@Part("username") String username, @Part("nickname") String nickname, @Part("other") File file) {
        return easyShopApi.update(username, nickname, null);
    }
}
