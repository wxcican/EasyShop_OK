package com.feicuiedu.com.easyshop.main.me.goodsload;

import com.feicuiedu.com.easyshop.commons.MyFileUtils;
import com.feicuiedu.com.easyshop.model.GoodsLoad;
import com.feicuiedu.com.easyshop.model.ImageItem;
import com.feicuiedu.com.easyshop.model.Result;
import com.feicuiedu.com.easyshop.network.EasyClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsLoadPresenter extends MvpNullObjectBasePresenter<GoodsLoadView> {

    public void upload(GoodsLoad goodsLoad, List<ImageItem> list) {
        getView().showProgress();
        String str = new Gson().toJson(goodsLoad);
        Call<ResponseBody> call = EasyClient.getInstance().goodsAdd(str, getFiles(list));
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
                Result result = new Gson().fromJson(str_body, Result.class);
                getView().showMessage(result.getMessage());
                if (result.getCode() == 1)
                    getView().upLoadSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(t.getMessage());
            }
        });
    }

    private List<MultipartBody.Part> getFiles(List<ImageItem> list) {
        List<MultipartBody.Part> parts = new ArrayList<>(list.size());
        for (ImageItem imageItem : list) {
            File file = new File(MyFileUtils.SD_PATH + imageItem.getImagePath());
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",
                    file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            parts.add(part);
        }

        return parts;
    }
}
