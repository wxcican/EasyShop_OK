package com.feicuiedu.com.yitao.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface RegisterView extends MvpView {

    void showProgress();

    void hideProgress();

    /*注册失败*/
    void registerFailed();

    /*导航到主页面*/
    void navigateToMain();

    void showMessage(String msg);

    /*用户名和密码不符合要求*/
    void showUserPasswordError(String msg);
}