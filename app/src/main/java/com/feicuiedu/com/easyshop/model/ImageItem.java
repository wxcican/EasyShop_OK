package com.feicuiedu.com.easyshop.model;

import android.graphics.Bitmap;

import com.feicuiedu.com.easyshop.commons.Bimp;

import java.io.IOException;
import java.io.Serializable;


/**
 * 图片上传时,Item布局对应的实体
 */
public class ImageItem implements Serializable {
    public String imagePath;
    private boolean isCheck = false;
    private boolean isSend = false;
    private Bitmap bitmap;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) {
            try {
                bitmap = Bimp.revisionImageSize(imagePath);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
