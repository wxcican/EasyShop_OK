package com.feicuiedu.com.yitao.user.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.feicuiedu.com.yitao.MainActivity;
import com.feicuiedu.com.yitao.R;
import com.feicuiedu.com.yitao.commons.ActivityUtils;
import com.feicuiedu.com.yitao.commons.RegexUtils;
import com.feicuiedu.com.yitao.network.YiTaoClient;
import com.feicuiedu.com.yitao.user.AlertDialogFragment;
import com.feicuiedu.com.yitao.user.EventFinish;
import com.feicuiedu.com.yitao.user.register.RegisterActivity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.et_username)
    EditText et_userName;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.btn_login)
    Button btn_login;


    private ActivityUtils activityUtils;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        init();
        /*注册EventBus*/
        EventBus.getDefault().register(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            username = et_userName.getText().toString();
            password = et_pwd.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
            btn_login.setEnabled(canLogin);
        }
    };

    private void init() {
        et_userName.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
    }

    @SuppressWarnings("unused")
    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                init();
                if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
                    String msg = getString(R.string.username_rules);
                    showUserPasswordError(msg);
                    return;
                } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
                    String msg = getString(R.string.password_rules);
                    showUserPasswordError(msg);
                    return;
                }
                presenter.login(this, username, password);
                break;
            case R.id.btn_register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        /*强制关闭软键盘*/
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loginFailed() {
        et_pwd.setText("");
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.username_pwd_rule, msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEvent(EventFinish str) {
        /*通过EventBus接受注册页面的消息关闭本页面*/
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*页面关闭时,取消网络请求*/
        YiTaoClient.getInstance(this).cancleRequest(presenter.loginRequest);
        /*反注册EventBus*/
        EventBus.getDefault().unregister(this);
    }
}
