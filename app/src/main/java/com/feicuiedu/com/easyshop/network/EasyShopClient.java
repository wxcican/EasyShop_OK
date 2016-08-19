package com.feicuiedu.com.easyshop.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * <p>
 * 网络连接的工具类,通过单列模式设计.对{@link RequestQueue}进行封装。
 * </p>
 */
@SuppressWarnings("unused")
public class EasyShopClient {

    private static EasyShopClient sInstance;

    public static synchronized EasyShopClient getInstance() {
        if (sInstance == null) {
            sInstance = new EasyShopClient();
        }
        return sInstance;
    }


    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private boolean isInit;

    private EasyShopClient() {

    }

    public void init(Context context) {

        isInit = true;
        requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }


    /**
     * 添加一个请求到RequestQueue队列
     */
    public void addToRequestQueue(Request req) {
        checkInit();
        requestQueue.add(req);
    }

    /**
     * 获取ImageLoader 实例
     */
    public ImageLoader getImageLoader() {
        checkInit();
        return imageLoader;
    }

    /**
     * 取消RequestQueue队列中所有请求
     */
    public <T> void cancelRequest(Object tag) {
        checkInit();
        requestQueue.cancelAll(tag);
    }

    /**
     * 判断该类是否初始化
     */
    private void checkInit() {
        if (!isInit) {
            throw new RuntimeException("You have to init first.");
        }
    }
}

