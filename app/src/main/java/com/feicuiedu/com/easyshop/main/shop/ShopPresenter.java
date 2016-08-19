package com.feicuiedu.com.easyshop.main.shop;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.feicuiedu.com.easyshop.model.GoodsResult;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.HashMap;
import java.util.Map;


public class ShopPresenter extends MvpNullObjectBasePresenter<ShopView> {

    private HashMap<String, String> postMap;

    public void refreshData(Context context, HashMap<String, String> postMap) {
        this.postMap = postMap;
        getView().showRefresh();
        EasyShopClient.getInstance().addToRequestQueue(refreshRequest);
    }

    public void loadData(Context context, HashMap<String, String> postMap) {
        this.postMap = postMap;
        getView().showLoadMoreLoading();
        EasyShopClient.getInstance().addToRequestQueue(loadRequest);
    }

    public StringRequest refreshRequest = new StringRequest(
            Request.Method.POST,
            EasyShopApi.BASE_URL + EasyShopApi.ALL_GOODS,
            new Response.Listener<String>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onResponse(String response) {
                    GoodsResult goodsResult = new Gson().fromJson(response, GoodsResult.class);
                    switch (goodsResult.getCode()) {
                        case 1:
                            if (goodsResult.getData().size() == 0) {
                                getView().showRefreshEnd();
                            } else {
                                getView().addRefreshData(goodsResult.getData());
                                getView().hideRefresh();
                            }
                            break;
                        default:
                            getView().showRefreshError("未知错误");
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getView().showRefreshError(error.getMessage());
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return postMap;
        }
    };

    public StringRequest loadRequest = new StringRequest(
            Request.Method.POST,
            EasyShopApi.BASE_URL + EasyShopApi.ALL_GOODS,
            new Response.Listener<String>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onResponse(String response) {
                    GoodsResult goodsResult = new Gson().fromJson(response, GoodsResult.class);
                    switch (goodsResult.getCode()) {
                        case 1:
                            if (goodsResult.getData().size() == 0) {
                                getView().showLoadMoreEnd();
                            } else {
                                getView().addMoreData(goodsResult.getData());
                                getView().hideLoadMore();
                            }
                            break;
                        default:
                            getView().showLoadMoreError("未知错误");

                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getView().showLoadMoreError(error.getMessage());
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return postMap;
        }
    };
}
