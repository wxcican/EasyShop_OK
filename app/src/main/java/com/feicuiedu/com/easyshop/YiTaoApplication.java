package com.feicuiedu.com.easyshop;

import android.app.Application;

import com.feicuiedu.com.easyshop.network.EasyShopClient;

public class YiTaoApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        HttpRequestQueue.init(this);
        /*初始化*/
        EasyShopClient.getInstance().init(this);
    }
}
