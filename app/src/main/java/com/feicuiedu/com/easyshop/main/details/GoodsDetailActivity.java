package com.feicuiedu.com.easyshop.main.details;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.components.ProgressDialogFragment;
import com.feicuiedu.com.easyshop.model.GoodsDetail;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class GoodsDetailActivity extends MvpActivity<GoodsDetailView, GoodsDetailPresenter>
        implements GoodsDetailView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    /*使用开源库CircleIndicator来实现ViewPager的圆点指示器。*/
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.tv_detail_name)
    TextView tv_detail_name;
    @Bind(R.id.tv_detail_price)
    TextView tv_detail_price;
    @Bind(R.id.tv_detail_describe)
    TextView tv_detail_describe;
    @Bind(R.id.tv_goods_delete)
    TextView tv_goods_delete;
    @Bind(R.id.btn_detail_message)
    Button btn_detail_message;
    @Bind(R.id.btn_detail_call)
    Button btn_detail_call;

    private Map<String, String> map;
    private ArrayList<ImageView> list;
    private ArrayList<String> list_uri;
    private GoodsDetailAdapter adapter;
    private ProgressDialogFragment dialogFragment;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        activityUtils = new ActivityUtils(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new GoodsDetailAdapter(list);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        viewPager.setAdapter(adapter);
        adapter.setListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsDetailInfoActivity.class);
                intent.putExtra("images", list_uri);
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    private void init() {
        String str_uuid = getIntent().getStringExtra("uuid");
        int btn_show = getIntent().getIntExtra("state", 0);
        if (btn_show == 1) {
            tv_goods_delete.setVisibility(View.VISIBLE);
            btn_detail_message.setVisibility(View.GONE);
            btn_detail_call.setVisibility(View.GONE);
        }
        map = new HashMap<>();
        map.put("uuid", str_uuid);
        presenter.getData(map);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    /*讲图片路径转化为一个ImageView*/
    private void getImage(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            ImageLoader imageLoader = EasyShopClient.getInstance().getImageLoader();
            ImageLoader.ImageListener imageListener = imageLoader.getImageListener(
                    imageView, R.drawable.user_ico, R.drawable.user_ico
            );
            imageLoader.get(EasyShopApi.IMAGE_URL + list.get(i), imageListener);
            this.list.add(imageView);
        }
    }

    @OnClick({R.id.btn_detail_call, R.id.btn_detail_message, R.id.tv_goods_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_detail_call:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
                try {
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_detail_message:
                activityUtils.showToast("发消息联系");
                break;
            case R.id.tv_goods_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("商品删除");
                builder.setMessage("是否删除该商品？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.delete(map);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
        }
    }

    @Override
    public void showProgress() {
        if (dialogFragment == null) {
            dialogFragment = new ProgressDialogFragment();
        }
        if (dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        dialogFragment.dismiss();
    }

    @Override
    public void setImageData(ArrayList<String> viewList) {
        list_uri = viewList;
        getImage(viewList);
        adapter.notifyDataSetChanged();
        indicator.setViewPager(viewPager);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(GoodsDetail data) {
        tv_detail_name.setText(data.getName());
        tv_detail_price.setText(data.getPrice() + "￥");
        tv_detail_describe.setText(data.getDescription());
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void deleteEnd() {
        finish();
    }
}
