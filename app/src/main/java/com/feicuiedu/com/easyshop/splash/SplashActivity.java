package com.feicuiedu.com.easyshop.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.user.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * App的入口页面
 */
public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityUtils = new ActivityUtils(this);

        Timer timer = new Timer();
        /*1.5秒后自动调转到主页面*/
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        }, 1500);

    }
}
