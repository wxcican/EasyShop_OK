package com.feicuiedu.com.easyshop;

import android.app.Application;

import com.feicuiedu.com.easyshop.network.EasyShopClient;

public class EasyShopApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        /*对网络请求初始化*/
        EasyShopClient.getInstance().init(this);
    }
}
