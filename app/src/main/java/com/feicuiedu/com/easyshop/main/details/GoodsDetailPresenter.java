package com.feicuiedu.com.easyshop.main.details;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.feicuiedu.com.easyshop.model.GoodsDetail;
import com.feicuiedu.com.easyshop.model.GoodsDetailResult;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.feicuiedu.com.easyshop.network.GsonRequest;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.ArrayList;
import java.util.Map;

public class GoodsDetailPresenter extends MvpNullObjectBasePresenter<GoodsDetailView> {

    private final GsonRequest.Builder<GoodsDetailResult> get_data_builder;
    private final GsonRequest.Builder<GoodsDetailResult> delete_builder;

    public GoodsDetailPresenter() {
        GsonRequest.Callback<GoodsDetailResult> callback = new GsonRequest.Callback<GoodsDetailResult>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getView().hideProgress();
                getView().showMessage(error.toString());
            }

            @Override
            public void onGsonResponse(GoodsDetailResult goodsDetailResult) {
                getView().hideProgress();
                if (goodsDetailResult.getCode() == 1) {
                    GoodsDetail goodsDetail = goodsDetailResult.getDatas();
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < goodsDetail.getPages().size(); i++) {
                        list.add(goodsDetail.getPages().get(i).getUri());
                    }
                    getView().setImageData(list);
                    getView().setData(goodsDetail);
                } else {
                    getView().showMessage(goodsDetailResult.getMessage());
                }
            }
        };

        GsonRequest.Callback<GoodsDetailResult> delete_callback = new GsonRequest.Callback<GoodsDetailResult>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getView().hideProgress();
                getView().showMessage(error.toString());
            }

            @Override
            public void onGsonResponse(GoodsDetailResult goodsDetailResult) {
                getView().hideProgress();
                if (goodsDetailResult.getCode() == 1) {
                    getView().deleteEnd();
                    getView().showMessage("删除成功");
                }
            }
        };

        get_data_builder = new GsonRequest.Builder<>()
                .setMethod(Request.Method.POST)
                .setUrl(EasyShopApi.BASE_URL + EasyShopApi.DETAIL)
                .setTag(this)
                .setClazz(GoodsDetailResult.class)
                .setCallback(callback);

        delete_builder = new GsonRequest.Builder<>()
                .setMethod(Request.Method.POST)
                .setUrl(EasyShopApi.BASE_URL + EasyShopApi.DELETE)
                .setTag(this)
                .setClazz(GoodsDetailResult.class)
                .setCallback(delete_callback);
    }

    /*获取商品的详细数据*/
    public void getData(Map<String, String> mapUuid) {
        getView().showProgress();
        EasyShopClient.getInstance().addToRequestQueue(get_data_builder.setParams(mapUuid).build());
    }

    /*删除商品*/
    public void delete(Map<String, String> mapUuid) {
        getView().showProgress();
        EasyShopClient.getInstance().addToRequestQueue(delete_builder.setParams(mapUuid).build());
    }
}
