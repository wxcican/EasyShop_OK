package com.feicuiedu.com.easyshop.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.components.AvatarLoadOptions;
import com.feicuiedu.com.easyshop.main.me.goodsload.GoodsLoadActivity;
import com.feicuiedu.com.easyshop.main.me.persongoods.PersonGoodsActivity;
import com.feicuiedu.com.easyshop.main.me.personinfo.PersonInfoActivity;
import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.network.EasyShopApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面对应的Fragment
 */
public class MeFragment extends Fragment {

    private static final String KEY_GENRE = "key_genre";

    public static MeFragment getInstance(int genre) {
        MeFragment shopFragment = new MeFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_GENRE, genre);
        shopFragment.setArguments(args);
        return shopFragment;
    }

    @Bind(R.id.iv_user_head)
    ImageView iv_user_head;

    private View view;
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*设置头像*/
        com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + CurrentUser.getUser().getHead_Image(),
                        iv_user_head, AvatarLoadOptions.build());
    }


    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
            case R.id.tv_person_info:
                activityUtils.startActivity(PersonInfoActivity.class);
                break;
            case R.id.tv_person_goods:
                activityUtils.startActivity(PersonGoodsActivity.class);
                break;
            case R.id.tv_goods_upload:
                activityUtils.startActivity(GoodsLoadActivity.class);
                break;
        }
    }
}
