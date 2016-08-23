package com.feicuiedu.com.easyshop.user.register;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.feicuiedu.com.easyshop.network.GsonRequest;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册页面的逻辑实现
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private final GsonRequest.Builder<LoginResult> builder;
    private final GsonRequest.Callback<LoginResult> callback;

    public RegisterPresenter() {
        callback = new GsonRequest.Callback<LoginResult>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getView().hideProgress();
                getView().showMessage(error.toString());
                CurrentUser.clear();
            }

            @Override
            public void onGsonResponse(LoginResult loginResult) {
                getView().hideProgress();
                if (loginResult.getCode() == 1) {
                    /*还需要登录环信*/
                    CurrentUser.setUser(loginResult.getData());
                    getView().showMessage("注册成功！");
                    getView().navigateToMain();
                } else if (loginResult.getCode() == 2) {
                    CurrentUser.clear();
                    getView().showMessage("用户名已存在！");
                    getView().registerFailed();
                } else {
                    getView().showMessage("未知错误");
                    getView().registerFailed();
                }

            }
        };
        builder = new GsonRequest.Builder<>()
                .setMethod(Request.Method.POST)
                .setUrl(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .setCallback(callback)
                .setTag(this)
                .setClazz(LoginResult.class);
    }

    public void register(String userName, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", pwd);
        CurrentUser.setUserId(userName);
        getView().showProgress();
        EasyShopClient.getInstance().addToRequestQueue(builder.setParams(map).build());
    }

    public void onDestroy() {
        /*页面关闭时,取消网络请求*/
        EasyShopClient.getInstance().cancelRequest(this);
    }
}
