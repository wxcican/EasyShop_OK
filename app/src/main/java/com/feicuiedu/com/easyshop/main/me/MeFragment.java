package com.feicuiedu.com.easyshop.main.me;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.commons.LogUtils;
import com.feicuiedu.com.easyshop.main.me.perseoninfo.PersonInfoActivity;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @Bind(R.id.me_layout)
    RelativeLayout relativeLayout;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
                activityUtils.startActivity(PersonInfoActivity.class);
                break;
            case R.id.tv_person_info:
                activityUtils.startActivity(PersonInfoActivity.class);
                break;
            case R.id.tv_person_goods:
                LogUtils.i("3");
                break;
            case R.id.tv_goods_upload:
                LogUtils.i("4");
                break;
        }
    }
}
