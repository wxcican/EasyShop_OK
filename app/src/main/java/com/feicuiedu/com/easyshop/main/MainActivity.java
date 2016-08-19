package com.feicuiedu.com.easyshop.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.feicuiedu.com.easyshop.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle("");
        init();
    }

    private void init() {
        textChange(0);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), 0));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(listener);
    }

    /*ViewPager的滑动事件*/
    private final ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            textChange(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressWarnings("unused")
    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shop:
                textChange(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_message:
                textChange(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_mail_list:
                textChange(2);
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_me:
                textChange(3);
                viewPager.setCurrentItem(3);
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
        textViews[showId].setTextColor(getResources().getColor(R.color.colorAccent));
        tv_title.setText(textViews[showId].getText());
    }


}
