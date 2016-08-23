package com.feicuiedu.com.easyshop.main.me.perseoninfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.commons.LogUtils;
import com.feicuiedu.com.easyshop.commons.RegexUtils;
import com.feicuiedu.com.easyshop.network.EasyClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NickNameActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_nickname)
    EditText et_nickname;
    @Bind(R.id.btn_save)
    Button btn_save;

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

    private void init() {
        LogUtils.i("dao le ------------");
        Call<ResponseBody> call= EasyClient.getInstance().update("feicuicp",str_nickname,null);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtils.i("-----成功----------"+response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.i("----失败-----------");
            }
        });

    }

}
