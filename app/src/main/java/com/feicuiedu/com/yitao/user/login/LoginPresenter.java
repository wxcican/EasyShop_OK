package com.feicuiedu.com.yitao.user.login;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.feicuiedu.com.yitao.commons.LogUtils;
import com.feicuiedu.com.yitao.model.LoginResult;
import com.feicuiedu.com.yitao.network.YiTaoApi;
import com.feicuiedu.com.yitao.network.YiTaoClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

        private String userName;
        private String pwd;

        public void login(Context context,String userName, String pwd) {
            getView().showProgress();
            this.userName = userName;
            this.pwd = pwd;
            YiTaoClient.getInstance(context).addToRequestQueue(loginRequest);
        }

    public StringRequest loginRequest = new StringRequest(
            Request.Method.POST,
            YiTaoApi.BASE_URL + YiTaoApi.LOGIN,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    LogUtils.i("response" + response.toString());
                    getView().hideProgress();
                    LoginResult loginResult = new Gson().fromJson(response, LoginResult.class);
                    if (loginResult.getCode() == 1) {
                        /*还需要登录环信*/
                        getView().showMessage("登录成功");
                        getView().navigateToMain();
                        return;
                    } else if (loginResult.getCode() == 2) {
                        getView().showMessage("用户名或密码错误");
                        getView().loginFailed();
                        return;
                    } else {
                        getView().showMessage("未知错误");
                        getView().loginFailed();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getView().hideProgress();
                    getView().showMessage(error.toString());
                    getView().loginFailed();
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();
            map.put("username", userName);
            map.put("password", pwd);
            return map;
        }
    };
}
