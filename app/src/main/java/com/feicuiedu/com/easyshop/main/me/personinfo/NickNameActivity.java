package com.feicuiedu.com.easyshop.main.me.personinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.commons.RegexUtils;
import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.LoginResult;
import com.feicuiedu.com.easyshop.model.User;
import com.feicuiedu.com.easyshop.network.EasyClient;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class NickNameActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_nickname)
    EditText et_nickname;

    private ActivityUtils activityUtils;
    private String str_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_nickname);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions,ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        str_nickname = et_nickname.getText().toString();
        if (RegexUtils.verifyNickname(str_nickname) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.nickname_rules);
            activityUtils.showToast(msg);
            return;
        }
        init();
    }

    /*昵称修改的*/
    private void init() {
        final User user = CurrentUser.getUser();
        user.setNick_Name(str_nickname);
        String str = new Gson().toJson(user);
        Call<ResponseBody> call = EasyClient.getInstance().update(str, null);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str_body = response.body().string();
                    LoginResult loginResult = new Gson().fromJson(str_body, LoginResult.class);
                    if (loginResult == null) {
                        activityUtils.showToast("未知错误");
                        return;
                    } else if (loginResult.getCode() != 1) {
                        activityUtils.showToast(loginResult.getMessage());
                        return;
                    }
                    CurrentUser.setUser(loginResult.getData());
                    activityUtils.showToast("修改成功");
                    onBackPressed();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                activityUtils.showToast(t.getMessage());
            }
        });

    }

}
