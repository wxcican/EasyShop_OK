package com.feicuiedu.com.easyshop.user.login;

import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.feicuiedu.com.easyshop.network.UICallback;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * 登录页面的逻辑实现
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    public Call call = null;

    public void login(String userName, String pwd) {
        getView().showProgress();
        call = EasyShopClient.getInstance().login(userName, pwd);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call, String body) {
                getView().hideProgress();
                LoginResult loginResult = new Gson().fromJson(body, LoginResult.class);
                if (loginResult.getCode() == 1) {
                    //*还需要登录环信*//*
                    CurrentUser.setUserId(loginResult.getData().getName());
                    CurrentUser.setUser(loginResult.getData());
                    getView().showMessage("登录成功");
                    getView().navigateToMain();
                } else if (loginResult.getCode() == 2) {
                    getView().showMessage(loginResult.getMessage());
                    getView().loginFailed();
                }
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }
}

