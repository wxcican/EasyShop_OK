package com.feicuiedu.com.easyshop.user.login;

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
 * 登录页面的逻辑实现
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private final GsonRequest.Builder<LoginResult> builder;

    public LoginPresenter() {
        GsonRequest.Callback<LoginResult> callback = new GsonRequest.Callback<LoginResult>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getView().hideProgress();
                getView().showMessage(error.toString());
            }

            @Override
            public void onGsonResponse(LoginResult loginResult) {
                getView().hideProgress();
                if (loginResult.getCode() == 1) {
                    /*还需要登录环信*/
                    CurrentUser.setUserId(loginResult.getData().getName());
                    CurrentUser.setUser(loginResult.getData());
                    getView().showMessage("登录成功");
                    getView().navigateToMain();
                } else if (loginResult.getCode() == 2) {
                    getView().showMessage(loginResult.getMessage());
                    getView().loginFailed();
                } else {
                    getView().showMessage("未知错误");
                    getView().loginFailed();
                }
            }
        };

        builder = new GsonRequest.Builder<LoginResult>()
                .setMethod(Request.Method.POST)
                .setUrl(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .setCallback(callback)
                .setTag(this)
                .setClazz(LoginResult.class);
    }


    public void login(String userName, String pwd) {
        getView().showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", pwd);

        EasyShopClient.getInstance()
                .addToRequestQueue(builder.setParams(map).build());
    }

    public void onDestroy() {
        /*页面关闭时,取消网络请求*/
        EasyShopClient.getInstance().cancelRequest(this);
    }
}

