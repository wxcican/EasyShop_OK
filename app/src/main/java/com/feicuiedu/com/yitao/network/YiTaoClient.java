package com.feicuiedu.com.yitao.network;

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
public class YiTaoClient {

    private static YiTaoClient mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private YiTaoClient(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

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

    public static synchronized YiTaoClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new YiTaoClient(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            /*getApplicationContext() is key, it keeps you from leaking the
            Activity or BroadcastReceiver if someone passes one in.*/
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * 添加一个请求到RequestQueue队列
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * 获得ImageLoader实类
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 取消RequestQueue队列中所有请求
     */
    public <T> void cancleRequest(Request<T> request) {
        getRequestQueue().cancelAll(request);
    }
}

