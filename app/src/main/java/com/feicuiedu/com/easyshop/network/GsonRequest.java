package com.feicuiedu.com.easyshop.network;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义GsonRequest
 * 通过构造器模式实现
 */
public class GsonRequest extends StringRequest {

    private static final int TIMEOUT = 5000;
    private static final DefaultRetryPolicy mDefaultRetryPolicy = new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy
            .DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private GsonRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public static class Builder<T> {

        /**
         * 请求方式,默认为Get请求
         */
        private int method = Method.GET;
        /**
         * 请求路劲
         */
        private String url;
        /**
         * 请求结束后的回调
         */
        private Callback<T> callback;
        private Map<String, String> params;
        /**
         * 该请求的对象标签
         */
        private Object tag;
        private Class<T> clazz;

        public Builder() {
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setCallback(Callback<T> callback) {
            this.callback = callback;
            return this;
        }

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder setClazz(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public GsonRequest build() {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    T t = new Gson().fromJson(response, clazz);
                    callback.onGsonResponse(t);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onErrorResponse(error);
                }
            };

            GsonRequest request = new GsonRequest(method, url, listener, errorListener) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Charset", "UTF-8");
                    //headers.put("Content-Type", "application/x-javascript");
                    //headers.put("Accept-Encoding", "gzip,deflate");
                    return headers;
                }
            };

            request.setTag(tag);
            request.setRetryPolicy(mDefaultRetryPolicy);
            return request;
        }
    }

    public interface Callback<T> {
        void onErrorResponse(VolleyError error);

        void onGsonResponse(T t);
    }
}
