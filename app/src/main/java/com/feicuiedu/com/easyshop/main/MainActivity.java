package com.feicuiedu.com.easyshop.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_shop)
    TextView tv_shop;
    @Bind(R.id.tv_message)
    TextView tv_message;
    @Bind(R.id.tv_mail_list)
    TextView tv_mail_list;
    @Bind(R.id.tv_me)
    TextView tv_me;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private int pageInt = 0;

    /*点击2次退出程序*/
    private static boolean isExit = false;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle("");
        init();
    }

    private void init() {
        textChange(pageInt);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), pageInt));
        viewPager.setCurrentItem(pageInt);
    }

    @SuppressWarnings("unused")
    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shop:
                pageInt = 0;
                textChange(pageInt);
                viewPager.setCurrentItem(pageInt);
                break;
            case R.id.tv_message:
                pageInt = 1;
                textChange(1);
                viewPager.setCurrentItem(pageInt);
                break;
            case R.id.tv_mail_list:
                pageInt = 2;
                textChange(pageInt);
                viewPager.setCurrentItem(pageInt);
                break;
            case R.id.tv_me:
                pageInt = 3;
                textChange(pageInt);
                viewPager.setCurrentItem(pageInt);
                break;
        }
    }


    /**
     * TextView的选择效果
     *
     * @param showId 要显示的TextView的ID(0~3之间)
     */
    @SuppressWarnings("deprecation")
    private void textChange(int showId) {
        TextView[] textViews = new TextView[]{tv_shop, tv_message, tv_mail_list, tv_me};
        for (TextView textView : textViews) {
            textView.setSelected(false);
            textView.setTextColor(getResources().getColor(R.color.text_color_hint));
        }
        textViews[showId].setSelected(true);
        viewPager.setCurrentItem(showId);
        textViews[showId].setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_title.setText(textViews[showId].getText());
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            activityUtils.showToast("再按一次退出程序");
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
