package com.feicuiedu.com.yitao.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.com.yitao.R;
import com.feicuiedu.com.yitao.commons.ActivityUtils;
import com.feicuiedu.com.yitao.user.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityUtils = new ActivityUtils(this);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        }, 1500);

    }
}
