package com.feicuiedu.com.easyshop.user.register;

import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.feicuiedu.com.easyshop.network.UICallback;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * 注册页面的逻辑实现
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private Call call = null;

    public void register(String userName, String pwd) {
        getView().showProgress();
        call = EasyShopClient.getInstance().register(userName, pwd);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
                CurrentUser.clear();
            }

            @Override
            public void onResponseInUi(Call call, String body) {
                LoginResult loginResult = new Gson().fromJson(body, LoginResult.class);
                if (loginResult.getCode() == 1) {
                    //*还需要登录环信*//*
                    CurrentUser.setUserId(loginResult.getData().getName());
                    CurrentUser.setUser(loginResult.getData());
                    getView().showMessage("注册成功！");
                    getView().navigateToMain();
                } else if (loginResult.getCode() == 2) {
                    CurrentUser.clear();
                    getView().showMessage(loginResult.getMessage());
                    getView().registerFailed();
                }
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
         /*页面解绑时,取消网络请求*/
        if (call != null) call.cancel();
    }
}
