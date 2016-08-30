package com.feicuiedu.com.easyshop.main.details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


/**
 * 商品图片展示的详情页面
 */
public class GoodsDetailInfoActivity extends AppCompatActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    /*使用开源库CircleIndicator来实现ViewPager的圆点指示器。*/
    @Bind(R.id.indicator)
    CircleIndicator indicator;

    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_info);
        list = new ArrayList<>();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        list = getIntent().getStringArrayListExtra("images");
        GoodsDetailAdapter adapter =new GoodsDetailAdapter(getImage(list));
        viewPager.setAdapter(adapter);
        adapter.setListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        indicator.setViewPager(viewPager);
    }

    private ArrayList<ImageView> getImage(ArrayList<String> list) {
        ArrayList<ImageView> list_image = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            ImageLoader imageLoader = EasyShopClient.getInstance().getImageLoader();
            ImageLoader.ImageListener imageListener = imageLoader.getImageListener(
                    imageView, R.drawable.user_ico, R.drawable.user_ico
            );
            imageLoader.get(EasyShopApi.IMAGE_URL + list.get(i), imageListener);
            list_image.add(imageView);
        }

        return list_image;
    }

}
