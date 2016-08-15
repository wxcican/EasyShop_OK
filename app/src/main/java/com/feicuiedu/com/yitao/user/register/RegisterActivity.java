package com.feicuiedu.com.yitao.user.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.feicuiedu.com.yitao.MainActivity;
import com.feicuiedu.com.yitao.R;
import com.feicuiedu.com.yitao.commons.ActivityUtils;
import com.feicuiedu.com.yitao.commons.RegexUtils;
import com.feicuiedu.com.yitao.user.AlertDialogFragment;
import com.feicuiedu.com.yitao.user.EventFinish;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends MvpActivity<RegisterView, RegisterPresenter>
        implements RegisterView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_username)
    EditText et_userName;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.et_pwdAgain)
    EditText et_pwdAgain;
    @Bind(R.id.btn_register)
    Button btn_register;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    private String username;
    private String password;
    private String pwd_again;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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
            pwd_again = et_pwdAgain.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(pwd_again));
            btn_register.setEnabled(canLogin);
        }
    };

    private void init() {
        et_userName.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
        et_pwdAgain.addTextChangedListener(textWatcher);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_register)
    public void onClick() {
        init();
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.username_rules);
            showUserPasswordError(msg);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.password_rules);
            showUserPasswordError(msg);
            return;
        } else if (!TextUtils.equals(password, pwd_again)) {
            String msg = getString(R.string.username_equal_pwd);
            showUserPasswordError(msg);
            return;
        }
        presenter.register(this, username, password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void registerFailed() {
        et_userName.setText("");
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
        /*通过EventBus发消息通知登录页面关闭*/
        EventBus.getDefault().post(new EventFinish());
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.username_pwd_rule, msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
    }
}