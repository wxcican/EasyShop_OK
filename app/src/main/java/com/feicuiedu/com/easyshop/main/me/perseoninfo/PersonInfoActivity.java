package com.feicuiedu.com.easyshop.main.me.perseoninfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.main.me.PicWindow;
import com.feicuiedu.com.easyshop.model.CurrentUser;
import com.feicuiedu.com.easyshop.model.ItemShow;
import com.feicuiedu.com.easyshop.model.User;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.iv_user_head)
    ImageView iv_user_head;
    @BindString(R.string.username_update)
    String str_username_update;
    @BindString(R.string.id_update)
    String str_id_update;
    @BindString(R.string.username)
    String str_username;
    @BindString(R.string.hx_id)
    String str_hx_id;
    @BindString(R.string.nickname)
    String str_nickname;

    private ActivityUtils activityUtils;
    private PicWindow picWindow;
    private ArrayList<ItemShow> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_person_info);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        PersonInfoAdapter adapter = new PersonInfoAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    /*从CurrentUser获取用户数据*/
    private void init() {
        User user = CurrentUser.getUser();
        list.add(new ItemShow(str_username, user.getName()));
        list.add(new ItemShow(str_nickname, user.getNick_Name()));
        list.add(new ItemShow(str_hx_id, user.getHx_Id()));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                activityUtils.showToast(str_username_update);
                break;
            case 1:
                activityUtils.startActivity(NickNameActivity.class);
                break;
            case 2:
                activityUtils.showToast(str_id_update);
                break;
        }
    }

    @OnClick(R.id.iv_user_head)
    public void onClick() {
        if (picWindow == null) {
            picWindow = new PicWindow(this, listener);
        }
        if (picWindow.isShowing()) {
            picWindow.dismiss();
            return;
        }
        picWindow.show();
    }

    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 帮助我们去处理结果(剪切完的图像)
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            File file = new File(uri.getPath());

            Bitmap bitmap= BitmapFactory.decodeFile(uri.getPath());
            if(bitmap!=null){
                iv_user_head.setImageBitmap(bitmap);
            }
            /*上传图片*/
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 300;
            cropParams.aspectY = 300;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonInfoActivity.this;
        }
    };
}
