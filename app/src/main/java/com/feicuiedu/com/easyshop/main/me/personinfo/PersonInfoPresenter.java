package com.feicuiedu.com.easyshop.main.me.personinfo;

import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.model.User;
import com.feicuiedu.com.easyshop.network.EasyClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonInfoPresenter extends MvpNullObjectBasePresenter<PersonInfoView> {

    public void uploadAvatar(File file) {
        getView().showProgress();
        User user = CurrentUser.getUser();
        String str = new Gson().toJson(user);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),RequestBody.create(MediaType.parse("image/png"), file));
        Call<ResponseBody> call = EasyClient.getInstance().update(str, part);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getView().hideProgress();
                String str_body = null;
                try {
                    str_body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoginResult loginResult = new Gson().fromJson(str_body, LoginResult.class);
                if (loginResult == null) {
                    getView().showMessage("未知错误");
                    return;
                } else if (loginResult.getCode() != 1) {
                    getView().showMessage(loginResult.getMessage());
                    return;
                }
                CurrentUser.setUser(loginResult.getData());
                getView().updateAvatar(loginResult.getData().getHead_Image());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(t.toString());
            }
        });
    }


}
