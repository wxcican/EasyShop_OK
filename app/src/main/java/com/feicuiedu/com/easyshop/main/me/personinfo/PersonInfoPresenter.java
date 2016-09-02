package com.feicuiedu.com.easyshop.main.me.personinfo;

import com.feicuiedu.com.easyshop.commons.LogUtils;
import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.feicuiedu.com.easyshop.network.UICallback;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

public class PersonInfoPresenter extends MvpNullObjectBasePresenter<PersonInfoView> {

    private okhttp3.Call call;

    /*上传头像*/
    public void uploadAvatar(File file) {
        getView().showProgress();
        call = EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call,String body) {
                getView().hideProgress();
                LoginResult loginResult = new Gson().fromJson(body, LoginResult.class);
                if (loginResult == null) {
                    getView().showMessage("未知错误");
                    return;
                } else if (loginResult.getCode() != 1) {
                    getView().showMessage(loginResult.getMessage());
                    return;
                }
                CurrentUser.setUser(loginResult.getData());
                LogUtils.i("===头像地址===" + loginResult.getData().getHead_Image());
                getView().updateAvatar(loginResult.getData().getHead_Image());
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }
}
