package com.feicuiedu.com.easyshop.main.me.personinfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface PersonInfoView extends MvpView {

    /*更新头像*/
    void updateAvatar(String url);

    /*显示进度条*/
    void showProgress();

    /*隐藏进度条*/
    void hideProgress();

    /*显示信息*/
    void showMessage(String msg);
}
