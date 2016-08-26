package com.feicuiedu.com.easyshop.network;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(40000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(40000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(40000, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 设置url根节点
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();

        easyShopApi = retrofit.create(EasyShopApi.class);
    }

    @Override
    public Call<ResponseBody> update(@Part("user") String username, @Part MultipartBody.Part file) {
        return easyShopApi.update(username, file);
    }

    @Override
    public Call<ResponseBody> goodsAdd(@Part("good") String good_info, @Part List<MultipartBody.Part> files) {
        return easyShopApi.goodsAdd(good_info, files);
    }

}
